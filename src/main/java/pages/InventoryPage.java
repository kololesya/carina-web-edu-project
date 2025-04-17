package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;

import components.HeaderMenuComponent;
import components.InventoryItemComponent;
import enums.SortType;

public class InventoryPage extends BasePage {

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

    @FindBy(id = "inventory_container")
    private InventoryItemComponent inventoryContainer;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public HeaderMenuComponent getHeaderMenuComponent() {
        return primaryHeader;
    }

    public InventoryItemComponent getInventoryItemComponent() {
        return inventoryContainer;
    }

    @Override
    public boolean isPageOpened() {
        return inventoryList.isPresent();
    }

    public void addProductToCartByName(String productName) {
        for (int i = 0; i < inventoryItems.size(); i++) {
            ExtendedWebElement itemNameElement = inventoryItems.get(i).findExtendedWebElement(inventoryItemName.getBy());

            if (itemNameElement.getText().equalsIgnoreCase(productName)) {
                ExtendedWebElement addToCartButton = addToCartButtons.get(i);

                addToCartButton.click();
                return;
            }
        }

        throw new RuntimeException("Product not found in inventory: " + productName);
    }

    public ProductPage openProductPage(String productName) {
        for (ExtendedWebElement productLink : productLinks) {
            String itemName = productLink.getText().trim();

            if (itemName.equalsIgnoreCase(productName)) {
                productLink.click();
                return new ProductPage(getDriver());
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
}
