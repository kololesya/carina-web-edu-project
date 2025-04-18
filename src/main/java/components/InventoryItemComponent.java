package components;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;

import static org.openqa.selenium.support.PageFactory.initElements;

public class InventoryItemComponent extends AbstractUIObject {
    @FindBy(xpath = ".//div[contains(@class, 'inventory_item_name')]")
    private ExtendedWebElement productName;

    @FindBy(className = "inventory_item_name")
    private ExtendedWebElement productLink;

    @FindBy(xpath = ".//div[contains(@class, 'inventory_item_price')]")
    private ExtendedWebElement productPrice;

    @FindBy(xpath = ".//button[contains(@class, 'btn_inventory')]")
    private ExtendedWebElement addToCartButton;

    public InventoryItemComponent(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getProductName() {
        return productName.getText().trim();
    }

    public String getProductPrice() {
        return productPrice.getText().trim();
    }

    public void addToCart() {
        addToCartButton.click();
    }

    public ExtendedWebElement getProductLink() {
        return productLink;
    }
}
