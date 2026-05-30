package Practice.saucedemo.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import Practice.saucedemo.pageobjects.CartPage;
import Practice.saucedemo.pageobjects.LandingPage;
import Practice.saucedemo.pageobjects.ProductCatalogue;
import Practice.saucedemo.testComponents.BaseTest;
import Practice.saucedemo.testComponents.retry;

/**
 * ErrorValidation Test Class
 * Tests error handling and validation scenarios
 * Includes retry mechanism for flaky tests
 */
@Test(retryAnalyzer = retry.class)
public class ErrorValidation extends BaseTest {

	/**
	 * Test - Invalid login credentials validation
	 * Verifies error message is displayed for wrong credentials
	 *
	 * @throws IOException if operations fail
	 */
	public void invalidLoginCredentialsTest() throws IOException {
		landingPage.loginApplication("xyz_wrongusername", "12345");
		String errorMessage = landingPage.getErrorMsg();
		Assert.assertEquals(errorMessage, " Username and password do not match any user in this service");
	}
	
	/**
	 * Test - Product validation in cart
	 * Adds product to cart and verifies it appears in cart
	 *
	 * @throws IOException if operations fail
	 */
	public void productValidationTest() throws IOException {
		final String productName = "Sauce Labs Backpack";
		
		// Login with valid credentials
		ProductCatalogue productCatalogue = landingPage.loginApplication("standard_user", "secret_sauce");
		
		// Add products to cart
		productCatalogue.addProductToCart();
		
		// Navigate to cart
		CartPage cartPage = productCatalogue.goToCartPage();
		
		// Verify product is in cart
		boolean isProductPresent = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(isProductPresent, "Product should be present in cart");
	}
}
