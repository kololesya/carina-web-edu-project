package pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {
    @FindBy(id = "react-burger-menu-btn")
    private ExtendedWebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private ExtendedWebElement logoutButton;

    @FindBy(css = "#shopping_cart_container a")
    private ExtendedWebElement cartButton;

    @FindBy(className = "inventory_list")
    private ExtendedWebElement inventoryList;

    @FindBy(className = "shopping_cart_badge")
    private List<ExtendedWebElement> cartBadgeElements;

    @FindBy(className = "inventory_item")
    private List<ExtendedWebElement> inventoryItems;

    @FindBy(className = "inventory_item_name")
    private ExtendedWebElement inventoryItemName;

    @FindBy(xpath = "//div[contains(@class, 'inventory_item')]//a[contains(@id, 'title_link')]")
    private List<ExtendedWebElement> productLinks;

    @FindBy(xpath = "//button[contains(@class, 'btn_inventory')]")
    private List<ExtendedWebElement> addToCartButtons;

    @FindBy(className = "product_sort_container")
    private ExtendedWebElement sortingDropdown;

    @FindBy(className = "inventory_item_name")
    private List<ExtendedWebElement> productNames;

    @FindBy(className = "inventory_item_price")
    private List<ExtendedWebElement> productPrices;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageOpened() {
        try {
            return inventoryList.isPresent();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void openMenu() {
        menuButton.click();
    }

    public void logout() {
        logoutButton.click();
    }

    public void clickCartButton() {
        cartButton.click();
    }

    public boolean isCartBadgeVisible() {
        return !cartBadgeElements.isEmpty();
    }

    public String getCartBadgeText() {
        if (!cartBadgeElements.isEmpty()) {
            return cartBadgeElements.get(0).getText();
        }
        return "";
    }

    public void addProductToCartByName(String productName) {
        for (ExtendedWebElement item : inventoryItems) {
            ExtendedWebElement itemNameElement = item.findExtendedWebElement(inventoryItemName.getBy());

            if (itemNameElement.getText().equalsIgnoreCase(productName)) {
                String buttonId = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");

                for (ExtendedWebElement button : addToCartButtons) {
                    if (button.getAttribute("id").contains(buttonId)) {
                        button.click();
                        System.out.println("Product added to cart: " + productName);
                        return;
                    }
                }
            }
        }

        throw new RuntimeException("Product not found in inventory: " + productName);
    }

    public void openProductPage(String productName) {
        for (ExtendedWebElement productLink : productLinks) {
            String itemName = productLink.getText().trim();

            if (itemName.equalsIgnoreCase(productName)) {
                productLink.click();
                return;
            }
        }
        throw new RuntimeException("Product not found in inventory: " + productName);
    }

    public void selectSortingOption(String option) {
        sortingDropdown.select(option);
    }

    public List<String> getProductNames() {
        return productNames.stream()
                .map(ExtendedWebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return productPrices.stream()
                .map(priceElement -> Double.parseDouble(priceElement.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    public boolean isSortedAscending(List<? extends Comparable> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isSortedDescending(List<? extends Comparable> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) < 0) {
                return false;
            }
        }
        return true;
    }
}
