package Practice.saucedemo.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Pracetice.saucedemo.pageobjects.CheckoutPage;
import Pracetice.saucedemo.pageobjects.ConfirmationPage;
import Pracetice.saucedemo.pageobjects.LandingPage;
import Pracetice.saucedemo.pageobjects.ProductCatalogue;
import Pracetice.saucedemo.pageobjects.cartPage;
import Practice.saucedemo.testComponents.BaseTest;

public class StandaloneTest extends BaseTest{

	@Test(dataProvider ="getData" , groups = {"purchase"})
	public void StandaloneTest(String email , String password) throws IOException {
		
		String name = "RAHUL";
		String lastname = "Mathur";
		String pincode = "713104";
		
		
		//LandingPage landingpage = launchApplication();
		ProductCatalogue Productcatalogue = landingpage.loginApplication(email,password);
		//ProductCatalogue Productcatalogue = new ProductCatalogue(driver);
		Productcatalogue.addProductTocart();
//		cartPage cartpage = new cartPage(driver);
		cartPage cartpage = Productcatalogue.goToCartPage();
		//CheckoutPage checkoutpage = new CheckoutPage(driver);
		//CheckoutPage checkoutpage = cartpage.checkOutButton();
		cartpage.checkOutButton();
		//Selecting the Bag
		CheckoutPage checkoutpage = new CheckoutPage(driver);
		checkoutpage.CheckoutForm(name, lastname, pincode);
		checkoutpage.CheckOutStepTwoPage();
		ConfirmationPage confirmationpage = new ConfirmationPage(driver);
		String text = confirmationpage.ConfirmationMsg();
		System.out.println(text);
		Assert.assertEquals(text, "Thank you for your order!");
		
	}
	
	@DataProvider
	public Object[][] getData()
	{
		return new Object [] []  {{"standard_user","secret_sauce"},{"visual_user","secret_sauce"}};
	}

}
