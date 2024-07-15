package Pracetice.saucedemo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Practice.saucedemo.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {

	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		//Design- method
		PageFactory.initElements(driver, this);
	}
	

	//PageFactory
	//driver.findElement(By.id("user-name")).sendKeys("standard_user");
	
	@FindBy(xpath ="//button[@id='add-to-cart-sauce-labs-backpack']")
	WebElement productBackpack;
	
	@FindBy(xpath ="//button[@id='add-to-cart-sauce-labs-bike-light']")
	WebElement saucebike;
	
	@FindBy(xpath ="//button[@id='add-to-cart-test.allthethings()-t-shirt-(red)']")
	WebElement RedShirt;
	
	By productlist = By.xpath("//div[@class='inventory_list']");
	
	//By backPacks = By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']");
	             
	public void addProductTocart()
	{
		//waitForElementToAppearBydriver(productBackpack);
		waitForElementToAppear(productlist);
		productBackpack.click();
		//waitForElementToAppearBydriver(saucebike);
		saucebike.click();
		scrollByPixels(driver,0,400);
		//waitForElementToAppearBydriver(RedShirt);
		RedShirt.click();
	}
	

	

	

	
	
}
