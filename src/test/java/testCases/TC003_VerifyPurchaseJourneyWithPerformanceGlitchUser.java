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

public class TC003_VerifyPurchaseJourneyWithPerformanceGlitchUser extends BaseClass {

	@Test(groups = { "Functional", "EndToEnd", "Regression", "Master" }, 
			description = "Verify end-to-end purchase journey with Performance Glitch User.")
	public void verifyPurchaseJourneyWithPerformanceGlitchUser_TC003() {
		
		logger.info("*** Starting verifyPurchaseJourneyWithPerformanceGlitchUser_TC003 ***");

		try {
			
			LoginPage lp = new LoginPage(driver);

			lp.inputUserName("performance_glitch_user");
			lp.inputPassword("secret_sauce");
			lp.clickLoginButton();

			
			ProductsPage pp = new ProductsPage(driver);
			
			// Reset application state to ensure a clean state
			pp.clickBurgerMenu();
			pp.clickResetAppState();					
						
			pp.clickProductSortBy(); 
			pp.clickOptionNameZtoA(); // sort products by 'Name (Z to A)'
			
			// Add first product to the cart
			int productCount = 1;
			pp.addProductsToCart(productCount);
			pp.clickCartIcon();

			
			CartPage cp = new CartPage(driver);
			cp.clickCheckoutButton(); // Proceed to the Checkout page from the Cart	

			
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

			
			Assert.assertEquals(sumOfItemPrices, displayedItemTotalPrice,
					"The calculated total item price does not match the displayed Item total.");

			
			double taxValue= coutp.getDisplayedTax();	//Get Tax value from Checkout Page

			double expectedTotalPrice = sumOfItemPrices + taxValue;	// Calculate Sum of Total Item Price with Tax value
			double displayedTotalPrice= coutp.getDisplayedTotal();	// Get displayed total price (include Tax)
			

			//Verify displayed total price with expected total Price (include tax)
			Assert.assertEquals(displayedTotalPrice, expectedTotalPrice,
					"Displayed Total Price does not match with expected Total Price.");
			
			coutp.clickFinish();	// Complete the purchase by clicking the 'Finish' button

			
			// Verify the order confirmation message
			String expectedOrderMessage = "Thank you for your order!";
			String actualOrderMessage = coutp.getSuccessfulOrderMessage();

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

		logger.info("*** Ending verifyPurchaseJourneyWithPerformanceGlitchUser_TC003 ***");
	}
}
