package tests.web;

import enums.SortType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;

import java.util.List;

public class InventorySortingTest extends BaseTest {

    @Test
    public void testInventorySorting() {
        InventoryPage inventoryPage = new InventoryPage(getDriver());

        for (SortType sortType : SortType.values()) {
            inventoryPage.selectSortingOption(sortType.getOption());

            switch (sortType) {
                case NAME_A_TO_Z:
                    List<String> sortedNamesAZ = inventoryPage.getProductNames();
                    Assert.assertTrue(inventoryPage.isSortedAscending(sortedNamesAZ),
                            "Products are sorted correctly by Name (A to Z)");
                    break;

                case NAME_Z_TO_A:
                    List<String> sortedNamesZA = inventoryPage.getProductNames();
                    Assert.assertTrue(inventoryPage.isSortedDescending(sortedNamesZA),
                            "Products are sorted correctly by Name (Z to A)");
                    break;

                case PRICE_LOW_TO_HIGH:
                    List<Double> sortedPricesLowToHigh = inventoryPage.getProductPrices();
                    Assert.assertTrue(inventoryPage.isSortedAscending(sortedPricesLowToHigh),
                            "Products are sorted correctly by Price (Low to High)");
                    break;

                case PRICE_HIGH_TO_LOW:
                    List<Double> sortedPricesHighToLow = inventoryPage.getProductPrices();
                    Assert.assertTrue(inventoryPage.isSortedDescending(sortedPricesHighToLow),
                            "Products are sorted correctly by Price (High to Low)");
                    break;

                default:
                    throw new IllegalArgumentException("Unexpected sorting type: " + sortType);
            }
        }
    }
}
