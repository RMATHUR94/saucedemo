package Practice.saucedemo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Practice.saucedemo.AbstractComponents.AbstractComponent;

/**
 * Landing Page Object Model for SauceDemo Login Page
 * This class handles all interactions with the login page
 */
public class LandingPage extends AbstractComponent {

	WebDriver driver;

	public LandingPage(WebDriver driver) {
		// Sending driver instance to parent class
		super(driver);
		this.driver = driver;
		// Initialize PageFactory
		PageFactory.initElements(driver, this);
	}

	// ==================== Web Elements ====================

	@FindBy(id = "user-name")
	private WebElement userName;

	@FindBy(id = "password")
	private WebElement passWord;

	@FindBy(id = "login-button")
	private WebElement submitButton;

	@FindBy(css = "h3[data-test='error']")
	private WebElement errorMessage;


	// ==================== Page Actions ====================

	/**
	 * Navigate to the application URL
	 */
	public void goTo() {
		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();
	}

	/**
	 * Perform login with given credentials
	 *
	 * @param email    - Username credential
	 * @param password - Password credential
	 * @return ProductCatalogue page object
	 */
	public ProductCatalogue loginApplication(String email, String password) {
		userName.sendKeys(email);
		passWord.sendKeys(password);
		submitButton.click();
		return new ProductCatalogue(driver);
	}

	/**
	 * Get error message from login page
	 *
	 * @return Error message text
	 */
	public String getErrorMsg() {
		waitForWebElementToAppear(errorMessage);
		String errorMsg = errorMessage.getText();
		return errorMsg;
	}
}

