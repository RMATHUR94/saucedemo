package Practice.saucedemo.AbstractComponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Practice.saucedemo.pageobjects.CartPage;

/**
 * Abstract Component - Base class for all Page Object Models
 * Contains common methods and web elements used across multiple pages
 */
public class AbstractComponent {

	protected WebDriver driver;
	protected static final int WAIT_TIMEOUT = 10;

	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
	}

	
	// ==================== Common Web Elements ====================
	
	@FindBy(xpath = "//a[@class='shopping_cart_link']")
	protected WebElement shoppingCartLink;
	
	@FindBy(css = "div[class='inventory_item_name']")
	protected List<WebElement> productList;
	
	private final By headerLabel = By.xpath("//div[@class='header_label']");

	
	// ==================== Common Waits & Utilities ====================
	
	/**
	 * Wait for element to be visible on the page
	 * 
	 * @param locator - By locator of the element
	 */
	public void waitForElementToAppear(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	/**
	 * Wait for WebElement to be visible
	 * 
	 * @param element - WebElement to wait for
	 */
	public void waitForWebElementToAppear(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	/**
	 * Wait for element to be clickable and then click it
	 * 
	 * @param locator - By locator of the element
	 */
	public void waitAndClick(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	/**
	 * Scroll page by given pixels
	 * 
	 * @param xPixels - Horizontal scroll amount
	 * @param yPixels - Vertical scroll amount
	 */
	public void scrollByPixels(WebDriver driver, int xPixels, int yPixels) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("window.scrollBy(arguments[0], arguments[1]);", xPixels, yPixels);
	}
	
	/**
	 * Navigate to shopping cart page
	 * 
	 * @return CartPage object
	 */
	public CartPage goToCartPage() {
		scrollByPixels(driver, 0, -400);
		waitAndClick(headerLabel);
		shoppingCartLink.click();
		return new CartPage(driver);
	}
	
	/**
	 * Verify if a product is displayed on the page
	 * 
	 * @param productName - Name of the product to verify
	 * @return true if product is found, false otherwise
	 */
	public boolean verifyProductDisplay(final String productName) {
		return productList.stream()
			.anyMatch(product -> product.getText().equalsIgnoreCase(productName));
	}
}
