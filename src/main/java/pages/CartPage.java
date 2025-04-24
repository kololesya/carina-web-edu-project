package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;

import components.HeaderMenuComponent;
import components.CartItemComponent;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(xpath = "//div[@id='cart_contents_container']//div[@data-test='inventory-item']")
    private List<CartItemComponent> cartItemComponents;

    @FindBy(id = "continue-shopping")
    private ExtendedWebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private ExtendedWebElement checkoutButton;

    @FindBy(className = "primary_header")
    private HeaderMenuComponent primaryHeader;

    public CartPage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(checkoutButton);
    }

    public HeaderMenuComponent getHeaderMenuComponent() {
        return primaryHeader;
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

    public void debugCartItems() {
        System.out.println("=== Debugging cart items ===");
        System.out.println("Number of elements found: " + cartItemComponents.size());

        for (int i = 0; i < cartItemComponents.size(); i++) {
            CartItemComponent item = cartItemComponents.get(i);
            try {
                System.out.println("Item " + (i + 1) + ": " + item.getProductName());
            } catch (Exception e) {
                System.out.println("Item " + (i + 1) + ": unable to get product name. Exception: " + e.getMessage());
            }
        }

        System.out.println("=== End of cart items debug ===");
    }

}
