package Practice.saucedemo.AbstractComponents;

import java.time.Duration;
import java.util.List;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Pracetice.saucedemo.pageobjects.cartPage;

public class AbstractComponent {

	WebDriver driver;

	public AbstractComponent(WebDriver driver) {

		this.driver = driver;
	}

	//PageFactory Method
	@FindBy(xpath = "//a[@class='shopping_cart_link']")
	WebElement cartHeader;
	
	@FindBy(css = "div[class='inventory_item_name']")
	List<WebElement> cartlists;
	
	By curtheader = By.xpath("//div[@class='header_label']");

	
	
 
	
	
	public void waitForElementToAppear(By findBy) {

		// wating for the element to be located

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));

	}
	
	public void waitForWebElementToAppearBydriver(WebElement findBy) {

		// wating for the element to be located

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(findBy));
	}
	

	public void elementToBeclickable(By findby)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(findby)).click();
	}

	public void scrollByPixels(WebDriver driver, int x, int y) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
	}
	
	public cartPage goToCartPage()
	{
		scrollByPixels(driver,0,-400);
		elementToBeclickable(curtheader);
		cartHeader.click();
		cartPage cartpage = new cartPage(driver);
		return cartpage;
	}
	
	public Boolean verifyProductDisplay(final String productName)
	{
		Boolean match = cartlists.stream().anyMatch(new Predicate<WebElement>() {
			@Override
			public boolean test(WebElement product) {
				return product.getText().equalsIgnoreCase(productName);
			}
		});
		return match;
	}
}
