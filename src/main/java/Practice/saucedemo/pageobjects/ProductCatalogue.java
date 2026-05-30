package Practice.saucedemo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Practice.saucedemo.AbstractComponents.AbstractComponent;

/**
 * Product Catalogue Page Object Model
 * This class handles all interactions with the product inventory page
 */
public class ProductCatalogue extends AbstractComponent {

	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver) {
		super(driver);
		this.driver = driver;
		// Initialize PageFactory
		PageFactory.initElements(driver, this);
	}
	

	// ==================== Web Elements ====================
	
	@FindBy(xpath = "//button[@id='add-to-cart-sauce-labs-backpack']")
	private WebElement productBackpack;
	
	@FindBy(xpath = "//button[@id='add-to-cart-sauce-labs-bike-light']")
	private WebElement sauceBike;
	
	@FindBy(xpath = "//button[@id='add-to-cart-test.allthethings()-t-shirt-(red)']")
	private WebElement redShirt;
	
	private By productList = By.xpath("//div[@class='inventory_list']");

	
	// ==================== Page Actions ====================
	
	/**
	 * Add multiple products to the cart
	 * Adds: Backpack, Bike Light, and Red Shirt
	 */
	public void addProductToCart() {
		// Wait for product list to appear
		waitForElementToAppear(productList);
		
		// Add products
		productBackpack.click();
		sauceBike.click();
		
		// Scroll down to see more products
		scrollByPixels(driver, 0, 400);
		redShirt.click();
	}
}

