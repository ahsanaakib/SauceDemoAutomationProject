package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage{

	public CartPage(WebDriver driver) {
		super(driver);		
	}
	
	
	// ***** WebElement *****
		
	@FindBy(xpath = "//button[@id='checkout']") WebElement btnCheckout;
	
	
	
	
	// ***** Actions *****		
	
	public void clickCheckoutButton() {
		btnCheckout.click();
	}

}
