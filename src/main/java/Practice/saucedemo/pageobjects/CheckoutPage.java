package Practice.saucedemo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Practice.saucedemo.AbstractComponents.AbstractComponent;

/**
 * Checkout Page Object Model
 * This class handles all interactions with the checkout form pages
 */
public class CheckoutPage extends AbstractComponent {

	WebDriver driver;

	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	// ==================== Web Elements ====================
	
	@FindBy(id = "first-name")
	private WebElement firstName;
	
	@FindBy(id = "last-name")
	private WebElement lastName;
	
	@FindBy(id = "postal-code")
	private WebElement postalCode;

	@FindBy(id = "continue")
	private WebElement continueButton;
	
	@FindBy(xpath = "//button[@id='finish']")
	private WebElement finishButton;

	
	// ==================== Page Actions ====================
	
	/**
	 * Fill checkout form with customer information (Step 1)
	 * 
	 * @param firstName - Customer first name
	 * @param lastName  - Customer last name
	 * @param zipCode   - Customer postal code
	 */
	public void fillCheckoutForm(String firstName, String lastName, String zipCode) {
		this.firstName.sendKeys(firstName);
		this.lastName.sendKeys(lastName);
		this.postalCode.sendKeys(zipCode);
		continueButton.click();
	}
	
	/**
	 * Complete checkout by clicking finish button (Step 2)
	 * 
	 * @return ConfirmationPage object
	 */
	public ConfirmationPage completeCheckout() {
		finishButton.click();
		return new ConfirmationPage(driver);
	}
}

