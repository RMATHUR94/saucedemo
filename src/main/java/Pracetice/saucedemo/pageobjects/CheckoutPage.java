package Pracetice.saucedemo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Practice.saucedemo.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent {

	WebDriver driver;
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		//Design- method
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(id ="first-name")
	WebElement firstName;
	
	@FindBy(id ="last-name")
	WebElement lastName;
	
	@FindBy(id ="postal-code")
	WebElement postal;

	@FindBy(id ="continue")
	WebElement submit;
	
	@FindBy(xpath = "//button[@id='finish']")
	WebElement FinishBtn;
	
	public void CheckoutForm(String name ,String last, String pincode)
	{
		firstName.sendKeys(name);
		lastName.sendKeys(last);
		postal.sendKeys(pincode);
		submit.click();
	}
	
	public void CheckOutStepTwoPage()
	{
		FinishBtn.click();
	}
	

}
