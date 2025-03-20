package pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CartPage extends AbstractPage {
    private static final Logger LOGGER = LogManager.getLogger(CartPage.class);

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[@class='inventory_item_name']")
    private ExtendedWebElement productNameElement;

    @FindBy(className = "cart_item")
    private List<ExtendedWebElement> cartItems;

    @FindBy(xpath = "//button[contains(@id, 'remove')]")
    private List<ExtendedWebElement> removeButtons;

    @FindBy(xpath = "//button[contains(@data-test, 'remove-')]")
    private ExtendedWebElement removeButton;

    @FindBy(id = "continue-shopping")
    private ExtendedWebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private ExtendedWebElement checkoutButton;

    public String getProductNameInCart() {
        LOGGER.info("Fetching product name from cart.");
        return productNameElement.getText();
    }

    public void removeProductFromCart(String productName) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        List<ExtendedWebElement> items = getCartItems();
        wait.until(driver -> !items.isEmpty());

        LOGGER.info("Attempting to remove product: {}", productName);

        boolean productFound = false;

        for (ExtendedWebElement item : items) {
            ExtendedWebElement itemNameElement = item.findExtendedWebElement(productNameElement.getBy());
            String itemName = itemNameElement.getText().trim();
            LOGGER.debug("Checking cart item: {}", itemName);

            if (itemName.equalsIgnoreCase(productName)) {
                LOGGER.info("Found product in cart, removing: {}", productName);
                ExtendedWebElement deleteButton = item.findExtendedWebElement(removeButton.getBy());
                wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
                LOGGER.info("Product successfully removed: {}", productName);

                productFound = true;
                break;
            }
        }

        if (!productFound) {
            LOGGER.error("Product not found in cart: {}", productName);
            throw new RuntimeException("Product not found in cart: " + productName);
        }
    }

    public void clearCart() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        LOGGER.info("Attempting to clear the cart.");

        while (!cartItems.isEmpty()) {
            try {
                int initialCount = cartItems.size();
                LOGGER.debug("Items in cart before removal: {}", initialCount);

                List<ExtendedWebElement> removeButtons = getRemoveButtons();
                if (!removeButtons.isEmpty()) {
                    LOGGER.info("Clicking remove button for first item.");
                    removeButtons.get(0).click();
                }

                wait.until(driver -> getCartItems().size() < initialCount);
                LOGGER.debug("Item removed, checking remaining items.");

            } catch (Exception e) {
                LOGGER.error("Exception while clearing cart: {}", e.getMessage());
            }
        }

        LOGGER.info("Cart is now empty.");
    }

    public boolean isProductNotInCart(String productName) {
        LOGGER.info("Checking if product '{}' is in cart.", productName);
        boolean isNotInCart = cartItems.stream().noneMatch(item -> {
            ExtendedWebElement itemNameElement = item.findExtendedWebElement(productNameElement.getBy());
            return itemNameElement.getText().trim().equalsIgnoreCase(productName);
        });

        if (isNotInCart) {
            LOGGER.info("Product '{}' is NOT in the cart.", productName);
        } else {
            LOGGER.warn("Product '{}' is still in the cart!", productName);
        }

        return isNotInCart;
    }

    public void clickContinueShopping() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        LOGGER.info("Clicking 'Continue Shopping' button.");
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton.getElement())).click();
    }

    public List<ExtendedWebElement> getCartItems() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(driver -> !cartItems.isEmpty());
        LOGGER.debug("Retrieved cart items: {}", cartItems.size());
        return cartItems;
    }

    public List<ExtendedWebElement> getRemoveButtons() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(driver -> !removeButtons.isEmpty());
        LOGGER.debug("Retrieved remove buttons: {}", removeButtons.size());
        return removeButtons;
    }

    public void clickCheckoutButton() {
        LOGGER.info("Clicking 'Checkout' button.");
        checkoutButton.click();
    }

    public boolean isCartEmpty() {
        LOGGER.info("Checking if the cart is empty.");
        return cartItems.isEmpty();
    }
}
