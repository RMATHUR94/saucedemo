package Practice.saucedemo.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Practice.saucedemo.pageobjects.CheckoutPage;
import Practice.saucedemo.pageobjects.ConfirmationPage;
import Practice.saucedemo.pageobjects.ProductCatalogue;
import Practice.saucedemo.pageobjects.CartPage;
import Practice.saucedemo.testComponents.BaseTest;

/**
 * StandaloneTest - Purchase Order Test
 * Tests the complete purchase flow from login to order confirmation
 */
public class StandaloneTest extends BaseTest {

	@Test(dataProvider = "getData", groups = {"purchase"})
	public void completePurchaseFlow(HashMap<String, String> input) throws IOException {
		
		// Test data
		String firstName = "RAHUL";
		String lastName = "Mathur";
		String zipCode = "713104";
		
		// Step 1: Login to application
		ProductCatalogue productCatalogue = landingPage.loginApplication(
			input.get("email"), 
			input.get("password")
		);
		
		// Step 2: Add products to cart
		productCatalogue.addProductToCart();
		
		// Step 3: Navigate to cart
		CartPage cartPage = productCatalogue.goToCartPage();
		
		// Step 4: Proceed to checkout
		CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
		
		// Step 5: Fill checkout form
		checkoutPage.fillCheckoutForm(firstName, lastName, zipCode);
		
		// Step 6: Complete checkout
		ConfirmationPage confirmationPage = checkoutPage.completeCheckout();
		
		// Step 7: Verify order confirmation
		String confirmationMessage = confirmationPage.getConfirmationMessage();
		Assert.assertEquals(confirmationMessage, "Thank you for your order!");
	}
	
	/**
	 * DataProvider - Supplies test data from JSON file
	 * 
	 * @return Array of test data (HashMap objects)
	 * @throws IOException if JSON file cannot be read
	 */
	@DataProvider 
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = getJsonDataToMap();
		return new Object[][] {
			{data.get(0)}, 
			{data.get(1)}
		};
	}
}
