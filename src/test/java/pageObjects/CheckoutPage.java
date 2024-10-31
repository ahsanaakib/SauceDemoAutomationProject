package pageObjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

	public CheckoutPage(WebDriver driver) {
		super(driver);
	}

	
	// ***** WebElements *****

	// Checkout step one WebElements
	@FindBy(xpath = "//input[@placeholder='First Name']") WebElement inputFirstName;
	@FindBy(xpath = "//input[@placeholder='Last Name']") WebElement inputLastName;
	@FindBy(xpath = "//input[@placeholder='Zip/Postal Code']") WebElement inputPostalCode;
	@FindBy(xpath = "//input[@id='continue']") WebElement inputContinue;

	
	// Checkout step two WebElements
	@FindBy(xpath = "//div[@class='inventory_item_name']") List<WebElement> cartItemNames;
	@FindBy(xpath = "//div[@class='inventory_item_price']") List<WebElement> itemPrices;
	@FindBy(xpath = "//div[@class='summary_subtotal_label']") WebElement itemTotal;
	@FindBy(xpath = "//div[@class='summary_tax_label']") WebElement summaryTaxLabel;
	@FindBy(xpath = "//div[@class='summary_total_label']") WebElement summaryTotalLabel; 
	@FindBy(xpath = "//button[@id='finish']") WebElement btnFinish;
	
	@FindBy(xpath = "//h2[@class='complete-header']") WebElement txtCompleteOrderMsg;
	@FindBy(xpath = "//button[@id='back-to-products']") WebElement btnBackHome;
	
	
	
	// ***** Actions *****

	// Checkout step one Actions
	public void inputFirstName(String firstName) {
		inputFirstName.clear();
		inputFirstName.sendKeys(firstName);
	}

	public void inputLastName(String lastName) {
		inputLastName.clear();
		inputLastName.sendKeys(lastName);
	}

	public void inputPostalCode(String postalCode) {
		inputPostalCode.clear();
		inputPostalCode.sendKeys(postalCode);
	}

	public void clickContinue() {
		inputContinue.click();
	}

	
	// checkout step two Actions
	public List<String> getCheckoutProductNames() {
		return cartItemNames.stream().map(WebElement::getText).map(String::trim).collect(Collectors.toList());
	}

	
	public double calculateTotalItemPrice() {
		double totalPrice = 0.0;
		for (WebElement priceElement : itemPrices) // Iterate through each item price and add to the total
		{
			String priceText = priceElement.getText().replace("$", ""); // Extract text then remove the dollar sign
			totalPrice += Double.parseDouble(priceText); // Convert the price text to a double and add it to the total price														
		}
		return totalPrice;
	}

	
	public double getDisplayedItemTotalPrice() {
		String displayedItemTotal = itemTotal.getText().replace("Item total: $", "");
		return Double.parseDouble(displayedItemTotal);
	}
	
	public double getDisplayedTax()
	{
		String displayedTax= summaryTaxLabel.getText().replace("Tax: $", "");		
		return Double.parseDouble(displayedTax);
	}
	
	public double getDisplayedTotalPriceAfterIncludingTax() {
		String displayedTotal= summaryTotalLabel.getText().replace("Total: $", "");
		return Double.parseDouble(displayedTotal);
	}
	
	
	public void clickFinish() {
		btnFinish.click();
	}
	
	
	public String getSuccessfulOrderMessage() {
		return txtCompleteOrderMsg.getText();	
	}
	
	
	public void clickBackHome() {
		btnBackHome.click();
	}	
	
}
