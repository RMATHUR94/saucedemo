package Practice.saucedemo.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import Pracetice.saucedemo.pageobjects.CheckoutPage;
import Pracetice.saucedemo.pageobjects.ConfirmationPage;
import Pracetice.saucedemo.pageobjects.LandingPage;
import Pracetice.saucedemo.pageobjects.ProductCatalogue;
import Pracetice.saucedemo.pageobjects.cartPage;
import Practice.saucedemo.testComponents.BaseTest;

public class ErrorValidation extends BaseTest {

	@Test
	public void StandaloneTest() throws IOException {


		landingpage.loginApplication("xyz_wrongusername", "12345");
		Assert.assertEquals("Epic sadface: Username and password do not match any user in this service", landingpage.getErrorMsg());

	}
	
	@Test(groups={"Errorhandling"})
	public void Productvalidation() throws IOException {

		
        final String productName = "Sauce Labs Backpack";
		//LandingPage landingpage = launchApplication();
		ProductCatalogue Productcatalogue = landingpage.loginApplication("standard_user","secret_sauce");
		//ProductCatalogue Productcatalogue = new ProductCatalogue(driver);
		Productcatalogue.addProductTocart();
//		cartPage cartpage = new cartPage(driver);
		cartPage cartpage = Productcatalogue.goToCartPage();
		Boolean match = cartpage.verifyProductDisplay(productName);
		Assert.assertTrue(match);

	}

}
