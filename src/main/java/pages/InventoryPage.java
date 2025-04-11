package pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import components.HeaderMenuComponent;
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

    @FindBy(className = "inventory_list")
    private ExtendedWebElement inventoryList;

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

    @FindBy(className="primary_header")
    private HeaderMenuComponent primaryHeader;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public HeaderMenuComponent getHeaderMenuComponent() {
        return primaryHeader;
    }

    @Override
    public boolean isPageOpened() {
        try {
            return inventoryList.isPresent();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void addProductToCartByName(String productName) {
        for (int i = 0; i < inventoryItems.size(); i++) {
            ExtendedWebElement itemNameElement = inventoryItems.get(i).findExtendedWebElement(inventoryItemName.getBy());

            if (itemNameElement.getText().equalsIgnoreCase(productName)) {
                ExtendedWebElement addToCartButton = addToCartButtons.get(i);

                addToCartButton.click();
                System.out.println("Product added to cart: " + itemNameElement.getText());
                return;
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
