package pages;

import components.CartItemComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;

import components.HeaderMenuComponent;


import java.util.List;

public class CartPage extends BasePage {

    @FindBy(className = "cart_item")
    private List<CartItemComponent> cartItemComponents;

    @FindBy(id = "continue-shopping")
    private ExtendedWebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private ExtendedWebElement checkoutButton;

    @FindBy(className = "primary_header")
    private HeaderMenuComponent primaryHeader;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public HeaderMenuComponent getHeaderMenuComponent() {
        return primaryHeader;
    }

    @Override
    protected ExtendedWebElement getUniqueElement() {
        return checkoutButton;
    }

    public void removeProductFromCart(String productName) {
        cartItemComponents.stream()
                .filter(item -> item.getProductName().equalsIgnoreCase(productName))
                .findFirst()
                .ifPresentOrElse(
                        CartItemComponent::remove,
                        () -> {
                            throw new RuntimeException("Product not found in cart: " + productName);
                        }
                );
    }

    public void clearCart() {
        while (!cartItemComponents.isEmpty()) {
            cartItemComponents.get(0).remove();
        }
    }

    public boolean isProductNotInCart(String productName) {
        return cartItemComponents.stream()
                .noneMatch(item -> item.getProductName().equalsIgnoreCase(productName));
    }

    public InventoryPage clickContinueShopping() {
        continueShoppingButton.click();
        return new InventoryPage(getDriver());
    }

    public CheckoutPage clickCheckoutButton() {
        checkoutButton.click();
        return new CheckoutPage(getDriver());
    }

    public boolean isCartEmpty() {
        return cartItemComponents.isEmpty();
    }

    public String getCartBadgeText() {
        return primaryHeader.getCartBadgeText();
    }
}
