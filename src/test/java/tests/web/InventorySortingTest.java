package tests.web;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pages.InventoryPage;

import enums.SortType;

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
        SoftAssert softAssert = new SoftAssert();
        InventoryPage inventoryPage = new InventoryPage(getDriver());

        inventoryPage.selectSortingOption(sortType.getOption());

        List<Double> sortedPrices;
        List<String> sortedNames;

        switch (sortType) {
            case NAME_A_TO_Z:
                sortedNames = inventoryPage.getProductNames();
                softAssert.assertTrue(inventoryPage.isSorted(sortedNames, sortType),
                        "Products are sorted correctly by Name (A to Z)");
                break;

            case NAME_Z_TO_A:
                sortedNames = inventoryPage.getProductNames();
                softAssert.assertTrue(inventoryPage.isSorted(sortedNames, sortType),
                        "Products are sorted correctly by Name (Z to A)");
                break;

            case PRICE_LOW_TO_HIGH:
                sortedPrices = inventoryPage.getProductPrices();
                softAssert.assertTrue(inventoryPage.isSorted(sortedPrices, sortType),
                        "Products are sorted correctly by Price (Low to High)");
                break;

            case PRICE_HIGH_TO_LOW:
                sortedPrices = inventoryPage.getProductPrices();
                softAssert.assertTrue(inventoryPage.isSorted(sortedPrices, sortType),
                        "Products are sorted correctly by Price (High to Low)");
                break;

            default:
                throw new IllegalArgumentException("Unexpected sorting type: " + sortType);
        }

        softAssert.assertAll();
    }
}
