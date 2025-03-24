package tests;

import com.zebrunner.carina.core.IAbstractTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

import java.util.List;

import static utility.Utilities.login;

public class InventorySortingTest implements IAbstractTest {
    private static final Logger LOGGER = LogManager.getLogger(AddRemoveProductInCartTest.class);
    private WebDriver driver = getDriver();
    private boolean isSortedAscending(List<? extends Comparable> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isSortedDescending(List<? extends Comparable> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) < 0) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testInventorySortingByNameAZ() {
        LOGGER.info("Starting test: testInventorySorting");
        login(driver, "standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.selectSortingOption("Name (A to Z)");
        List<String> sortedNamesAZ = inventoryPage.getProductNames();
        LOGGER.info("Sorted product names (A to Z): {}", sortedNamesAZ);
        Assert.assertTrue(isSortedAscending(sortedNamesAZ), "Products are sorted correctly by Name (A to Z)");
    }

    @Test
    public void testInventorySortingByNameZA() {
        LOGGER.info("Starting test: testInventorySorting");
        login(driver, "standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.selectSortingOption("Name (Z to A)");
        List<String> sortedNamesZA = inventoryPage.getProductNames();
        LOGGER.info("Sorted product names (Z to A): {}", sortedNamesZA);
        Assert.assertTrue(isSortedDescending(sortedNamesZA), "Products are sorted correctly by Name (Z to A)");
    }

    @Test
    public void testInventorySortingByPriceLowHigh() {
        LOGGER.info("Starting test: testInventorySorting");
        login(driver, "standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.selectSortingOption("Price (low to high)");
        List<Double> sortedPricesLowToHigh = inventoryPage.getProductPrices();
        LOGGER.info("Sorted product prices (Low to High): {}", sortedPricesLowToHigh);
        Assert.assertTrue(isSortedAscending(sortedPricesLowToHigh), "Products are sorted correctly by Price (Low to High)");
    }

    @Test
    public void testInventorySortingByPriceHighLow(){
        LOGGER.info("Starting test: testInventorySorting");
        login(driver, "standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.selectSortingOption("Price (high to low)");
        List<Double> sortedPricesHighToLow = inventoryPage.getProductPrices();
        LOGGER.info("Sorted product prices (High to Low): {}", sortedPricesHighToLow);
        Assert.assertTrue(isSortedDescending(sortedPricesHighToLow), "Products are sorted correctly by Price (High to Low)");

        LOGGER.info("Test testInventorySorting completed.");
    }

}
