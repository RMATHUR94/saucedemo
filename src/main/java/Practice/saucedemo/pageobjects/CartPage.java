package Practice.saucedemo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Practice.saucedemo.AbstractComponents.AbstractComponent;

/**
 * Cart Page Object Model
 * This class handles all interactions with the shopping cart page
 */
public class CartPage extends AbstractComponent {

	WebDriver driver;

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	
	// ==================== Web Elements ====================
	
	@FindBy(xpath = "//button[@id='checkout']")
	private WebElement checkoutButton;

	
	// ==================== Page Actions ====================
	
	/**
	 * Click on checkout button to proceed to checkout
	 * 
	 * @return CheckoutPage object for page chaining
	 */
	public CheckoutPage clickCheckoutButton() {
		scrollByPixels(driver, 0, 400);
		checkoutButton.click();
		return new CheckoutPage(driver);
	}

}

