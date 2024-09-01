package Practice.saucedemo.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import Pracetice.saucedemo.pageobjects.CheckoutPage;
import Pracetice.saucedemo.pageobjects.ConfirmationPage;
import Pracetice.saucedemo.pageobjects.LandingPage;
import Pracetice.saucedemo.pageobjects.ProductCatalogue;
import Pracetice.saucedemo.pageobjects.cartPage;
import Practice.saucedemo.testComponents.BaseTest;
import Practice.saucedemo.testComponents.retry;

@Test
public class ErrorValidation extends BaseTest {

	//Under this test we added test for retry test for flasky test
	public void StandaloneTest() throws IOException {

//		Assert.assertEquals("Epic sadface: Username and password do not match any user in this service", landingpage.getErrorMsg());

		landingpage.loginApplication("xyz_wrongusername", "12345");
		Assert.assertEquals(" Username and password do not match any user in this service", landingpage.getErrorMsg());

	}
	
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
