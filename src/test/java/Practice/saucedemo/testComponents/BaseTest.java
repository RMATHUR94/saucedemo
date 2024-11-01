package Practice.saucedemo.testComponents;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Pracetice.saucedemo.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    // adding comment for checking that webhook-jenkins trigger working as expected or not
	// Jenkins - NgRok Server execution steps
	public WebDriver driver;
	public LandingPage landingpage;
 
	public WebDriver initializeDriver() throws IOException
	{
		Properties prop = new Properties();
		//FileInputStream fis = new FileInputStream("C:\\Users\\user\\eclipse-workspace\\saucedemo\\src\\main\\java\\Practice\\saucedemo\\resources\\GlobalData.properties");
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//Practice//saucedemo//resources//GlobalData.properties");
		prop.load(fis);
	
		// For invoking through maven cmd terminal
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");
	
		//	String browserName = prop.getProperty("browser");  
		
		if (browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			if(browserName.contains("headless"))
			{
				options.addArguments("headless");
			}
			driver = new ChromeDriver(options);
			//driver.manage().window().setSize(new Dimension(1400, 900));
			driver.get("https://www.saucedemo.com/");

		} 
		else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.get("https://www.saucedemo.com/");
			// driver.manage().window().maximize();
		} 
		else if (browserName.equalsIgnoreCase("edge")) {
			// edge handeling code
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
		
	}
	
	public List<HashMap<String, String>> getJsonDataToMap() throws IOException
	{
		//Reading Json to String
		String jsonContent = FileUtils.readFileToString(new File(System.getProperties()+"//src//test//java//Practice//saucedemo//data//PurchaseOrder.json"));
	
		
	// String to HashMap - for that we need Jackson DataBind from MVN Repository
	
	ObjectMapper mapper = new ObjectMapper();
	
	
	List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});

	return data;
	}
	
	//Utility to take screenshot
	public String getScreenShot(String testCaseName,WebDriver driver ) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+ "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}
	
	
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException
	{
		driver = initializeDriver();
		landingpage = new LandingPage(driver);
		landingpage.goTo();
		return landingpage;
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.close();
	}

}
