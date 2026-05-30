package Practice.saucedemo.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Practice.saucedemo.AbstractComponents.AbstractComponent;

/**
 * Confirmation Page Object Model
 * This class handles all interactions with the order confirmation page
 */
public class ConfirmationPage extends AbstractComponent {

	WebDriver driver;
	
	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	// ==================== Web Elements ====================
	
	@FindBy(css = ".complete-header")
	private WebElement confirmationMessage;

	
	// ==================== Page Actions ====================
	
	/**
	 * Get confirmation message text from order completion page
	 * 
	 * @return Confirmation message text
	 */
	public String getConfirmationMessage() {
		String message = confirmationMessage.getText();
		return message;
	}
}

