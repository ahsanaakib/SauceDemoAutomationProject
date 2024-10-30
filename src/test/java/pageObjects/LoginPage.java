package pageObjects;


import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	
	// ***** WebElements *****
	
	@FindBy(xpath = "//input[@placeholder='Username']") WebElement inputUserName;
	@FindBy(xpath = "//input[@placeholder='Password']") WebElement inputPassword;
	@FindBy(xpath = "//input[@id='login-button']") WebElement btnLogin;
	@FindBy(xpath = "//h3[contains(text(),'Epic sadface:')]") WebElement txtLoginErrorMsg;
	
	
	
	// ***** Actions *****
	
	public void inputUserName(String uname) {
		inputUserName.clear();			// Clear any pre-filled data
		inputUserName.sendKeys(uname);	// Enter the username
	}
	
	
	public void inputPassword(String pwd) {
		inputPassword.clear();			// Clear any pre-filled data
		inputPassword.sendKeys(pwd);	// Enter the password
	}
	
	
	public void clickLoginButton() {
		btnLogin.click();			// Click the login button to submit the credentials
	}
	
	
	public String getLoginErrorMessage() {
		try {
			return txtLoginErrorMsg.getText();	// Retrieves the login error message displayed on the page	
		
		} catch (NoSuchElementException e) {			
			return "Login error message element not found.";
		}
	}
	
	
}
