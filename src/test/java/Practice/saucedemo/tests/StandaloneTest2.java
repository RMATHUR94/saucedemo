package Practice.saucedemo.tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandaloneTest2 {

	public static void main(String[] args) {
		
		String name = "RAHUL";
		String lastname = "Mathur";
		String pincode = "713104";
		WebDriverManager.chromedriver().setup();
		WebDriverManager.firefoxdriver().setup();
		WebDriver driver = new FirefoxDriver();
		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-sauce-labs-backpack")));
		driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
		//Scrolling on the page
		JavascriptExecutor jse =(JavascriptExecutor)driver;
     	jse.executeScript("window.scrollBy(0,400)");
		//clicking on orange shirt
		driver.findElement(By.xpath("//button[@id='add-to-cart-test.allthethings()-t-shirt-(red)']")).click();
		
		//clicking on shopping cart link
		
		jse.executeScript("window.scrollBy(0,-400)");
	
		//invoking explicit wait for clicking cart button top left corner
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='shopping_cart_link']"))).click();
		
		//clicking check out button.

		driver.findElement(By.id("checkout")).click();

		//customer form
		driver.findElement(By.name("firstName")).sendKeys(name);
		driver.findElement(By.name("lastName")).sendKeys(lastname);
		driver.findElement(By.id("postal-code")).sendKeys(pincode);

		// clicking continue button
		driver.findElement(By.id("continue")).click();

		//scrolling again
		jse.executeScript("window.scrollBy(0,400)");
		
		// clicking on finish button
		
		driver.findElement(By.cssSelector(".cart_button")).click();
		
		//Collecting final text and printing it on the console
		
		String finaltext = driver.findElement(By.cssSelector(".complete-header")).getText();
		System.out.println(finaltext);


	}

}
