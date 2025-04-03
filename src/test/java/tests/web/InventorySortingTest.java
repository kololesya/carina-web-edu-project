package tests.web;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.InventoryPage;

import java.util.List;

public class InventorySortingTest extends BaseTest {
    @DataProvider(name = "sortingOptions")
    public Object[][] sortingOptions() {
        return new Object[][] {
                { "Name (A to Z)", true, false },
                { "Name (Z to A)", false, true },
                { "Price (low to high)", true, false },
                { "Price (high to low)", false, true }
        };
    }

    @Test(dataProvider = "sortingOptions")
    public void testInventorySortingByNameAZ(String sortingOption, boolean isAscending, boolean isDescending) {
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        inventoryPage.selectSortingOption(sortingOption);

        List<String> sortedNames;
        List<Double> sortedPrices;

        if (sortingOption.contains("Name")) {
            sortedNames = inventoryPage.getProductNames();
            if (isAscending) {
                Assert.assertTrue(inventoryPage.isSortedAscending(sortedNames), "Products are sorted correctly by " + sortingOption);
            } else if (isDescending) {
                Assert.assertTrue(inventoryPage.isSortedDescending(sortedNames), "Products are sorted correctly by " + sortingOption);
            }
        } else if (sortingOption.contains("Price")) {
            sortedPrices = inventoryPage.getProductPrices();
            if (isAscending) {
                Assert.assertTrue(inventoryPage.isSortedAscending(sortedPrices), "Products are sorted correctly by " + sortingOption);
            } else if (isDescending) {
                Assert.assertTrue(inventoryPage.isSortedDescending(sortedPrices), "Products are sorted correctly by " + sortingOption);
            }
        }
    }
}
