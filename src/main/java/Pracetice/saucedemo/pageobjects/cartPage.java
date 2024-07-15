package Pracetice.saucedemo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Practice.saucedemo.AbstractComponents.AbstractComponent;

public class cartPage extends AbstractComponent {

	WebDriver driver;

	public cartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath ="//button[@id='checkout']")
	WebElement checkout;

	public void checkOutButton()  {
		
		scrollByPixels(driver,0,400);
		checkout.click();
		
	}

}
