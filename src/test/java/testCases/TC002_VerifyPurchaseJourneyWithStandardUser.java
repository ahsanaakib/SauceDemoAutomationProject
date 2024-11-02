package testCases;

import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CartPage;
import pageObjects.CheckoutPage;
import pageObjects.LoginPage;
import pageObjects.ProductsPage;
import testBase.BaseClass;

public class TC002_VerifyPurchaseJourneyWithStandardUser extends BaseClass {

	@Test(groups = { "Functional", "EndToEnd", "Regression", "Master" }, 
			description = "Verify end-to-end purchase journey with standard user.")
	public void verifyPurchaseJourneyWithStandardUser_TC002() {
		
		
		logger.info("*** Starting verifyPurchaseJourneyWithStandardUser_TC002 ***");

		try {			
			
			LoginPage lp = new LoginPage(driver);
			
			// Login with standard user credentials from the properteis file
			lp.inputUserName(p.getProperty("username"));
			lp.inputPassword(p.getProperty("password"));
			lp.clickLoginButton();
	
			ProductsPage pp = new ProductsPage(driver);
			
			pp.clickBurgerMenu();
			pp.clickResetAppState(); // Reset application state to ensure a clean state

			
			// Add multiple products (3) to the cart
			int productCount = 3;
			pp.addProductsToCart(productCount);
			pp.clickCartIcon();	// Navigate to the Cart

			// Proceed to Checkout
			CartPage cp = new CartPage(driver);
			cp.clickCheckoutButton();

			
			// Fill out checkout information with random data
			CheckoutPage coutp = new CheckoutPage(driver);
			coutp.inputFirstName(randomString(5));
			coutp.inputLastName(randomString(5));
			coutp.inputPostalCode(randomNumber(4));
			coutp.clickContinue();

			
			// Retrieve and compare product names from Products and Checkout pages
			List<String> productNamesFromProductsPage = pp.getAvailableProductNames().stream().limit(productCount)
					.sorted().collect(Collectors.toList());
		
			List<String> productNamesFromCheckoutPage = coutp.getCheckoutProductNames().stream().limit(productCount)
					.sorted().collect(Collectors.toList());

			
			// Verify product names between ProductsPage and CheckoutPage
			Assert.assertEquals(productNamesFromCheckoutPage, productNamesFromProductsPage,
					"Mismatch between product names on ProductsPage and CheckoutPage.");
			
			
			// Calculate the item total price and verify it with the displayed item total price (exclude tax)
			double sumOfItemPrices = coutp.calculateTotalItemPrice();
			double displayedItemTotalPrice = coutp.getDisplayedItemTotalPrice();


			Assert.assertEquals(displayedItemTotalPrice, sumOfItemPrices,
					"Price mismatch:");

			
			double taxValue= coutp.getDisplayedTax();	//Get Tax value from Checkout Page
			
			double expectedTotalPriceIncTax = sumOfItemPrices + taxValue;	// Calculate Sum of Total Item Price with Tax value
			double displayedTotalPriceIncTax= coutp.getDisplayedTotalPriceAfterIncludingTax();	// Get displayed total price (include Tax)
			

			//Verify displayed total price with expected total Price (include tax)
			Assert.assertEquals(displayedTotalPriceIncTax, expectedTotalPriceIncTax,
					"Price mismatch:");
			
			
			coutp.clickFinish();	// Complete the purchase by clicking the 'Finish' button
			
			
			// Verify the order confirmation message
			String expectedOrderMessage="Thank you for your order!";
			String actualOrderMessage= coutp.getSuccessfulOrderMessage();
			
			Assert.assertEquals(actualOrderMessage, expectedOrderMessage,
					"The order confirmation message does not match the expected message.");
			
			
			coutp.clickBackHome();	// Navigate back to the home page
			
			// Reset the application state to clean up after the test
			pp.clickBurgerMenu();
			pp.clickResetAppState();
			
			// Logout from the application
			pp.clickBurgerMenu();
			pp.clickLogout();

		} catch (Exception e) {
			// Fail the test if any exception occurs
			Assert.fail("Test failed due to an exception: " + e.getMessage());
		}

		logger.info("*** Ending verifyPurchaseJourneyWithStandardUser_TC002 ***");
	}
}
