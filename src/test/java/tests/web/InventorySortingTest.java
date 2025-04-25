package tests.web;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.InventoryPage;
import enums.SortType;
import tests.services.InventorySortingService;

public class InventorySortingTest extends BaseTest {

    @DataProvider(name = "sortingOptions")
    public Object[][] sortingOptions() {
        return new Object[][] {
                { SortType.NAME_A_TO_Z },
                { SortType.NAME_Z_TO_A },
                { SortType.PRICE_LOW_TO_HIGH },
                { SortType.PRICE_HIGH_TO_LOW }
        };
    }

    @Test(dataProvider = "sortingOptions")
    public void testInventorySorting(SortType sortType) {
        InventoryPage inventoryPage = loginToSauceDemo();
        InventorySortingService inventorySortingService = new InventorySortingService(inventoryPage);
        Assert.assertTrue(inventorySortingService.isSortingAppliedCorrectly(sortType),
                "Products are sorted correctly by " + sortType);
    }
}
