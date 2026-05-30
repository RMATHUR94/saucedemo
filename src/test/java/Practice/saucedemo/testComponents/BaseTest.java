package Practice.saucedemo.testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Practice.saucedemo.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Base Test Class - Contains setup, teardown, and common utilities for all tests
 * Provides WebDriver initialization, data reading, and screenshot functionality
 */
public class BaseTest {

	protected WebDriver driver;
	protected LandingPage landingPage;

	private static final String APPLICATION_URL = "https://www.saucedemo.com/";
	private static final String GLOBAL_DATA_PATH = "//src//main//java//Practice//saucedemo//resources//GlobalData.properties";
	private static final String TEST_DATA_PATH = "//src//test//java//Practice//saucedemo//data//PurchaseOrder.json";
	private static final int IMPLICIT_WAIT_SECONDS = 10;

	/**
	 * Initialize WebDriver based on browser specified in properties or system parameter
	 *
	 * @return WebDriver instance
	 * @throws IOException if properties file cannot be read
	 */
	public WebDriver initializeDriver() throws IOException {
		Properties properties = new Properties();
		FileInputStream fileInputStream = new FileInputStream(
				System.getProperty("user.dir") + GLOBAL_DATA_PATH);
		properties.load(fileInputStream);

		// Get browser from system property (Maven) or properties file
		String browserName = System.getProperty("browser") != null
				? System.getProperty("browser")
				: properties.getProperty("browser");

		WebDriver driver = null;

		if (browserName != null && browserName.toLowerCase().contains("chrome")) {
			ChromeOptions options = new ChromeOptions();

			// ✅ Disable Google Password Manager popup & breach detection
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			prefs.put("profile.password_manager_leak_detection", false);
			options.setExperimentalOption("prefs", prefs);

			options.addArguments("--disable-blink-features=AutomationControlled");
			options.addArguments("--disable-features=PasswordLeakDetection"); // ✅ Disable leak detection
			options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

			if (browserName.toLowerCase().contains("headless")) {
				options.addArguments("--headless");
			}

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(options);

		} else if (browserName != null && browserName.toLowerCase().equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (browserName != null && browserName.toLowerCase().equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			throw new IllegalArgumentException("Invalid browser specified: " + browserName);
		}

		// Configure driver
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
		driver.manage().window().maximize();
		driver.get(APPLICATION_URL);

		return driver;
	}

	/**
	 * Read JSON data file and convert to List of HashMap
	 * Useful for data-driven testing
	 *
	 * @return List of HashMaps containing test data
	 * @throws IOException if JSON file cannot be read
	 */
	public List<HashMap<String, String>> getJsonDataToMap() throws IOException {
		// Read JSON file to String
		String jsonContent = FileUtils.readFileToString(
				new File(System.getProperty("user.dir") + TEST_DATA_PATH));

		// Convert String to List of HashMap using Jackson
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(
				jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {});

		return data;
	}

	/**
	 * Capture screenshot of current page state
	 * Useful for failure reporting
	 *
	 * @param testCaseName - Name of the test case (used for file naming)
	 * @param driver       - WebDriver instance
	 * @return File path of screenshot
	 * @throws IOException if screenshot cannot be saved
	 */
	public String captureScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
		File destinationFile = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(sourceFile, destinationFile);
		return destinationFile.getAbsolutePath();
	}

	/**
	 * Setup method - Executed before each test
	 * Initializes WebDriver and navigates to application
	 *
	 * @return LandingPage object
	 * @throws IOException if driver initialization fails
	 */
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}

	/**
	 * Teardown method - Executed after each test
	 * Closes the WebDriver session
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}