package Pracetice.saucedemo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Practice.saucedemo.AbstractComponents.AbstractComponent;

public class ConfirmationPage extends AbstractComponent {

	WebDriver driver;
	
	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		//Design- method
	    PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(css =".complete-header")
	WebElement confirmationMsg;
	
	
	public String ConfirmationMsg()
	{
		String msg = confirmationMsg.getText();
		driver.close();
		return msg;
		
	}
	
}
