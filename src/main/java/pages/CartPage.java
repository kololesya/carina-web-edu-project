package pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(xpath = "//div[@class='inventory_item_name']")
    private ExtendedWebElement productNameElement;

    @FindBy(className = "cart_item")
    private List<ExtendedWebElement> cartItems;

    @FindBy(xpath = "//button[contains(@id, 'remove')]")
    private ExtendedWebElement removeButton;

    @FindBy(id = "continue-shopping")
    private ExtendedWebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private ExtendedWebElement checkoutButton;

    @FindBy(xpath = "//button[contains(@id, 'remove')]")
    private List<ExtendedWebElement> removeButtons;


    public CartPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageOpened() {
        try {
            return checkoutButton.isPresent();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getProductNameInCart() {
        return productNameElement.getText();
    }

    public void removeProductFromCart(String productName) {
        for (ExtendedWebElement item : cartItems) {
            ExtendedWebElement nameElement = item.findExtendedWebElement(productNameElement.getBy());
            if (nameElement.getText().trim().equalsIgnoreCase(productName)) {
                ExtendedWebElement deleteButton = item.findExtendedWebElement(removeButton.getBy());
                deleteButton.click();
                return;
            }
        }
        throw new RuntimeException("Product not found in cart: " + productName);
    }

    public void clearCart() {
        while (!removeButtons.isEmpty()) {
            removeButtons.get(0).click();
        }
    }

    public boolean isProductNotInCart(String productName) {
        return cartItems.stream().noneMatch(item -> {
            ExtendedWebElement itemNameElement = item.findExtendedWebElement(productNameElement.getBy());
            return itemNameElement.getText().trim().equalsIgnoreCase(productName);
        });
    }

    public void clickContinueShopping() {
        continueShoppingButton.click();
    }

    public void clickCheckoutButton() {
        checkoutButton.click();
    }

    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }
}
