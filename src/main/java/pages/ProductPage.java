package pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {
    @FindBy(xpath = "//div[@class='inventory_details_desc large_size']")
    private ExtendedWebElement productDescriptionElement;

    @FindBy(id = "add-to-cart")
    private ExtendedWebElement addToCartButton;

    @FindBy(id="back-to-products")
    private ExtendedWebElement backToProductsButton;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageOpened() {
        return productDescriptionElement.isPresent();
    }

    public void addToCart() {
        if (addToCartButton.isPresent() && addToCartButton.isEnabled()) {
            addToCartButton.click();
        } else {
            throw new RuntimeException("Add to Cart button is not clickable.");
        }
    }

    public String getProductDescription() {
        return productDescriptionElement.getText().trim();
    }

    public void returnToInventoryPage(){
        if (backToProductsButton.isPresent()) {
            backToProductsButton.click();
        } else {
            throw new RuntimeException("Back to Products button is not present.");
        }
    }
}