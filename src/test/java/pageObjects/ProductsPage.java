package pageObjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductsPage extends BasePage {

	public ProductsPage(WebDriver driver) {
		super(driver);
	}

	
	
	// ***** WebElements *****
	
	@FindBy(xpath = "//button[@id='react-burger-menu-btn']") WebElement btnBurgerMenu;
	@FindBy(xpath = "//a[@id='logout_sidebar_link']") WebElement linkLogout;
	@FindBy(xpath = "//a[@id='reset_sidebar_link']") WebElement linkResetAppState;
	@FindBy(xpath = "//button[text()='Add to cart']") List<WebElement> btnsAddtoCart;
	@FindBy(xpath = "//a[@class='shopping_cart_link']") WebElement linkCartIcon;
	@FindBy(xpath = "//div[@data-test='inventory-item-name']") List<WebElement> txtItemNames;
	@FindBy(xpath = "//select[@class='product_sort_container']") WebElement selectProductSort;
	@FindBy(xpath = "//select[@class='product_sort_container']/option[2]") WebElement selectOptionNameZtoA;
	
	
	
	// ***** Actions *****

	public void clickBurgerMenu() {
		btnBurgerMenu.click();
	}

	
	public void clickLogout() {
		linkLogout.click();
	}

	
	public void clickResetAppState() {
		linkResetAppState.click();
		driver.navigate().refresh();
	}

	
	public List<String> getAvailableProductNames() {
		return txtItemNames.stream().map(WebElement::getText).map(String::trim).collect(Collectors.toList());
	}

	
	public void addProductsToCart(int productCount) {
		int availableProducts = btnsAddtoCart.size();
		
		if(productCount>availableProducts) 
		{
			productCount = availableProducts; // Limit to the number of available products
		}

		for(int i=0; i<productCount; i++) 
		{
			btnsAddtoCart.get(i).click();
		}
	}

	
	public void clickCartIcon() {
		linkCartIcon.click();
	}
	
	
	public void clickProductSortBy() {
		selectProductSort.click();
	}
	
	
	public void clickOptionNameZtoA() {
		selectOptionNameZtoA.click();
	}
	
	
}
