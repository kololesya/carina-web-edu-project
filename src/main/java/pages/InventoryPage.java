package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.support.FindBy;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends AbstractPage {
    private static final Logger LOGGER = LogManager.getLogger(CartPage.class);
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

    @FindBy(xpath = ".//button[contains(@id, 'add-to-cart')]")
    private ExtendedWebElement addToCartButton;

    @FindBy(className = "product_sort_container")
    private ExtendedWebElement sortingDropdown;

    @FindBy(xpath = "//div[@class='inventory_item_name']")
    private List<ExtendedWebElement> productNames;

    @FindBy(xpath = "//div[@class='inventory_item_price']")
    private List<ExtendedWebElement> productPrices;

    public InventoryPage(WebDriver driver) {
            super(driver);
    }

    public boolean isPageOpened() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        try {
            LOGGER.info("Checking if the login page is opened...");
            boolean isVisible = wait.until(ExpectedConditions.visibilityOf(inventoryList.getElement())) != null;
            LOGGER.info("Inventory page is visible: {}", isVisible);
            return isVisible;
        } catch (TimeoutException e) {
            LOGGER.error("Inventory page did not open within the timeout.", e);
            return false;
        }
    }

    public void openMenu() {
        menuButton.click();
        LOGGER.info("Clicked menu button.");
    }

    public void logout() {
        logoutButton.click();
        LOGGER.info("Clicked logout button.");
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
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        for (ExtendedWebElement item : inventoryItems) {
            ExtendedWebElement itemNameElement = item.findExtendedWebElement(inventoryItemName.getBy());

            if (itemNameElement.getText().equalsIgnoreCase(productName)) {
                LOGGER.info("Adding product to cart: " + productName);
                addToCartButton.click();
                return;
            }
        }

        LOGGER.warn("Product not found: " + productName);
        throw new RuntimeException("Product not found in inventory: " + productName);
    }

    public void selectSortingOption(String option) {
        LOGGER.info("Selecting sorting option: {}", option);
        sortingDropdown.select(option);
    }

    public List<String> getProductNames() {
        return productNames.stream().map(ExtendedWebElement::getText).collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return productPrices.stream()
                .map(priceElement -> Double.parseDouble(priceElement.getText().replace("$", "")))
                .collect(Collectors.toList());
    }
}
