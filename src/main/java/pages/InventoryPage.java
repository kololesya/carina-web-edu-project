package pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import components.HeaderMenuComponent;
import components.InventoryItemComponent;
import enums.SortType;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {

    @FindBy(className = "primary_header")
    private HeaderMenuComponent primaryHeader;

    @FindBy(className = "inventory_item")
    private List<InventoryItemComponent> inventoryItems;

    @FindBy(className = "product_sort_container")
    private ExtendedWebElement sortingDropdown;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public HeaderMenuComponent getHeaderMenuComponent() {
        return primaryHeader;
    }

    @Override
    public boolean isPageOpened() {
        return !inventoryItems.isEmpty() && inventoryItems.get(0).isElementPresent();
    }

    public void addProductToCartByName(String productName) {
        for (InventoryItemComponent item : inventoryItems) {
            if (item.getProductName().equalsIgnoreCase(productName)) {
                item.addToCart();
                return;
            }
        }
        throw new RuntimeException("Product not found in inventory: " + productName);
    }

    public ProductPage openProductPageByProductName(String productName) {
        for (InventoryItemComponent item : inventoryItems) {
            if (item.getProductName().equalsIgnoreCase(productName)) {
                item.getProductLink().click();
                return new ProductPage(getDriver());
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    public void selectSortingOption(String option) {
        sortingDropdown.select(option);
    }

    public boolean isSorted(List<? extends Comparable> list, SortType sortType) {
        for (int i = 0; i < list.size() - 1; i++) {
            int comparisonResult = list.get(i).compareTo(list.get(i + 1));
            if ((sortType == SortType.NAME_A_TO_Z || sortType == SortType.PRICE_LOW_TO_HIGH) && comparisonResult > 0) {
                return false;
            }
            else if ((sortType == SortType.NAME_Z_TO_A || sortType == SortType.PRICE_HIGH_TO_LOW) && comparisonResult < 0) {
                return false;
            }
        }
        return true;
    }

    public List<String> getProductNames() {
        return inventoryItems.stream()
                .map(InventoryItemComponent::getProductName)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return inventoryItems.stream()
                .map(item -> Double.parseDouble(item.getProductPrice().replace("$", "")))
                .collect(Collectors.toList());
    }
}
