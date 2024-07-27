package Pracetice.saucedemo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Practice.saucedemo.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {

	WebDriver driver;
	
	public LandingPage(WebDriver driver)
	{
		//sending driver instacnes to superclass AbstractComponents
		super(driver);
		this.driver = driver;
		//Design- method
		PageFactory.initElements(driver, this);
	}
	
	//driver.findElement(By.id("user-name")).sendKeys("standard_user");
	//driver.findElement(By.id("password")).sendKeys("secret_sauce");
	//driver.findElement(By.id("login-button")).click();
	
	//PageFactory
	
	//driver.findElement(By.id("user-name")).sendKeys("standard_user");
	@FindBy(id ="user-name")
	WebElement userName;
	
	//driver.findElement(By.id("password")).sendKeys("secret_sauce");
	@FindBy(id ="password")
	WebElement passWord;
	
	//driver.findElement(By.id("login-button")).click();
	@FindBy(id ="login-button")
	WebElement submit;
	
	@FindBy(css ="h3[data-test='error']")
	WebElement errorMsg;
	

	public void goTo()
	{
		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();
	}
	
	
	public ProductCatalogue loginApplication(String email , String password)
	{
		userName.sendKeys(email);
		passWord.sendKeys(password);
		submit.click();
		ProductCatalogue Productcatalogue = new ProductCatalogue(driver);
		return Productcatalogue;

	}
	
	public String getErrorMsg()
	{
		waitForWebElementToAppearBydriver(errorMsg);
		String errormsg = errorMsg.getText();
		return errormsg;
		
	}
}
