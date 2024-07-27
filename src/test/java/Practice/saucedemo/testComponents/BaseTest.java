package Practice.saucedemo.testComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import Pracetice.saucedemo.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingpage;
 
	public WebDriver initializeDriver() throws IOException
	{
		Properties prop = new Properties();
		//FileInputStream fis = new FileInputStream("C:\\Users\\user\\eclipse-workspace\\saucedemo\\src\\main\\java\\Practice\\saucedemo\\resources\\GlobalData.properties");
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//Practice//saucedemo//resources//GlobalData.properties");
		prop.load(fis);
		String browserName = prop.getProperty("browser");  
		
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.get("https://www.saucedemo.com/");

		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.get("https://www.saucedemo.com/");
			// driver.manage().window().maximize();
		} else if (browserName.equalsIgnoreCase("edge")) {
			// edge handeling code
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
		
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
