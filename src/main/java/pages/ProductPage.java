package pages;

import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;

public class ProductPage extends BasePage {

    @FindBy(xpath = "//div[contains(@class, 'inventory_details_desc')]")
    private ExtendedWebElement productDescriptionElement;

    @FindBy(id = "add-to-cart")
    private ExtendedWebElement addToCartButton;

    @FindBy(id="back-to-products")
    private ExtendedWebElement backToProductsButton;

    public ProductPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(productDescriptionElement);
    }

    public void addProductToCart() {
        addToCartButton.click();
    }

    public InventoryPage returnToInventoryPage(){
        backToProductsButton.click();
        return new InventoryPage(getDriver());
    }
}