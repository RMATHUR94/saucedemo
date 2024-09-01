package Practice.saucedemo.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Pracetice.saucedemo.pageobjects.CheckoutPage;
import Pracetice.saucedemo.pageobjects.ConfirmationPage;
import Pracetice.saucedemo.pageobjects.ProductCatalogue;
import Pracetice.saucedemo.pageobjects.cartPage;
import Practice.saucedemo.testComponents.BaseTest;

public class StandaloneTest extends BaseTest{

	@Test(dataProvider ="getData" , groups = {"purchase"})

	public void StandaloneTest(HashMap<String,String> input) throws IOException {
//	public void StandaloneTest(String email , String password) throws IOException {
		
		String name = "RAHUL";
		String lastname = "Mathur";
		String pincode = "713104";
		
		
		//LandingPage landingpage = launchApplication();
		ProductCatalogue Productcatalogue = landingpage.loginApplication(input.get("email"),input.get("password"));
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
	
	// Using Map to fetch the credentials 
//	@DataProvider 
//	public Object[][] getData()
//	{
//		HashMap<String,String> map = new HashMap<String,String>();
//		map.put("email","standard_user");
//		map.put("password","secret_sauce");
//	    
//		HashMap<String,String> map1 = new HashMap<String,String>();
//		map1.put("email","visual_user");
//		map1.put("password", "secret_sauce");
//		return new Object [] []  {{map},{map1}};
//	}
	
	@DataProvider 
	public Object[][] getData() throws IOException
	{
		List<HashMap<String,String>> data = getJsonDataToMap();
		return new Object [] []  {{data.get(0)},{data.get(1)}};
	}
	
	
// Using array onject to fetch the credentials	
//	@DataProvider  
//	public Object[][] getData()
//	{
//		return new Object [] []  {{"standard_user","secret_sauce"},{"visual_user","secret_sauce"}};
//	}

}
