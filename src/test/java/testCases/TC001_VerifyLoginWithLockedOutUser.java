package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.LoginPage;
import testBase.BaseClass;

public class TC001_VerifyLoginWithLockedOutUser extends BaseClass {

	@Test(groups = { "Functional", "Sanity", "Regression", "Master" }, 
			description = "Verifies that a locked-out user sees the correct error message.")
	public void verifyLoginWithLockedOutUser_TC001() {
				
		logger.info("*** Starting verifyLoginWithLockedOutUser_TC001 ***");

		try {			
			LoginPage lp = new LoginPage(driver); 

			// Provide credentials of a locked-out user and attempt login
			lp.inputUserName("locked_out_user"); 
			lp.inputPassword("secret_sauce"); 
			lp.clickLoginButton(); 

			// Expected and actual error message validation
			String expectedErrorMsg = "Epic sadface: Sorry, this user has been locked out.";
			String actualErrorMsg = lp.getLoginErrorMessage(); 
			

			// Verify that the actual error message matches the expected message
			Assert.assertEquals(actualErrorMsg, expectedErrorMsg,
					"Error message mismatch: Expected '" + expectedErrorMsg + "' but got '" + actualErrorMsg + "'.");

		} catch (Exception e) {
			
			Assert.fail("Test failed due to an exception: " + e.getMessage()); //Fail the test if any exception occurs
		}

		logger.info("*** Ending verifyLoginWithLockedOutUser_TC001 ***");
	}
}
