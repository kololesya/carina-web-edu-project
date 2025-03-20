package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.zebrunner.carina.core.IAbstractTest;

import pages.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SauceDemoTest implements IAbstractTest {
    private static final Logger LOGGER = LogManager.getLogger(SauceDemoTest.class);
    private WebDriver driver = getDriver();

    private String expectedProductName = "Sauce Labs Backpack";

    private void login(String userName, String password) {
        LOGGER.info("Opening SauceDemo login page");
        driver.get("https://www.saucedemo.com/");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        LOGGER.info("Attempting to log in with username: {}", userName);
        loginPage.login(userName, password);
        LOGGER.info("Login successful");
    }

    private void clearCartAndContinueShopping(){
        LOGGER.info("Navigating to cart to clear items");
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.clickCartButton();

        CartPage cartPage = new CartPage(driver);
        cartPage.clearCart();
        LOGGER.info("Cart cleared");

        cartPage.clickContinueShopping();
        LOGGER.info("Clicked continue shopping button");
    }

    private void addProductToCart(String productName){
        LOGGER.info("Adding product to cart: {}", productName);
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addProductToCartByName(productName);
        LOGGER.info("Product '{}' added to cart", productName);
    }

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
    public void testLogin(){
        LOGGER.info("Starting test: testLogin");
        login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isPageOpened(), "Inventory page should be opened after login.");
        LOGGER.info("Login test passed");
    }

    @Test
    public void testLoginLockedUser(){
        LOGGER.info("Starting test: testLogin");
        login("locked_out_user", "secret_sauce");

        LoginPage loginPage = new LoginPage(driver);

        LOGGER.info("Attempting to log in with locked-out user: {}", "locked_out_user");
        String expectedErrorMessage = "Epic sadface: Sorry, this user has been locked out.";
        String actualErrorMessage = loginPage.getErrorMessage();

        LOGGER.info("Verifying login failure message.");
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message does not match expected text.");

        LOGGER.info("Test testLoginLockedOutUser completed.");
    }

    @Test
    public void testVerifyCartBadgeText() {
        LOGGER.info("Starting test: testVerifyCartBadgeText");
        login("standard_user", "secret_sauce");
        clearCartAndContinueShopping();

        InventoryPage inventoryPage = new InventoryPage(driver);
        LOGGER.info("Checking if cart badge is empty");
        Assert.assertTrue(inventoryPage.getCartBadgeText().isEmpty(), "Cart badge should be empty initially");

        addProductToCart(expectedProductName);
        Assert.assertEquals(inventoryPage.getCartBadgeText(), "1", "Cart badge should display '1' after adding one product");

        LOGGER.info("Cart badge text verification test passed");
    }

    @Test
    public void testAddSpecificProductToCart() {
        LOGGER.info("Starting test: testAddSpecificProductToCart");
        login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertFalse(inventoryPage.isCartBadgeVisible(), "Cart badge should not be visible initially");

        addProductToCart(expectedProductName);
        Assert.assertTrue(inventoryPage.isCartBadgeVisible(), "Cart badge should be visible after adding a product");

        LOGGER.info("Product successfully added to cart and verified");
    }

    @Test
    public void testVerifyProductInCart() {
        LOGGER.info("Starting test: testVerifyProductInCart");
        login("standard_user", "secret_sauce");
        clearCartAndContinueShopping();

        InventoryPage inventoryPage = new InventoryPage(driver);
        addProductToCart(expectedProductName);
        inventoryPage.clickCartButton();

        CartPage cartPage = new CartPage(driver);
        String actualProductName = cartPage.getProductNameInCart();

        LOGGER.info("Expected product: '{}', Actual product: '{}'", expectedProductName, actualProductName);
        Assert.assertEquals(actualProductName, expectedProductName, "Product name in cart should match the expected name");

        LOGGER.info("Product verification in cart test passed");
    }

    @Test
    public void testLogout(){
        LOGGER.info("Starting test: testLogout");
        login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.openMenu();
        inventoryPage.logout();

        Assert.assertFalse(inventoryPage.isPageOpened(), "Inventory page should be closed after logout.");
        LOGGER.info("Logout test passed");
    }

    @Test
    public void testRemoveProductFromCart() {
        LOGGER.info("Starting test: testRemoveProductFromCart");
        login("standard_user", "secret_sauce");
        clearCartAndContinueShopping();

        InventoryPage inventoryPage = new InventoryPage(driver);
        addProductToCart(expectedProductName);
        inventoryPage.clickCartButton();

        CartPage cartPage = new CartPage(driver);
        LOGGER.info("Attempting to remove product '{}' from cart", expectedProductName);
        cartPage.removeProductFromCart(expectedProductName);

        Assert.assertTrue(cartPage.isProductNotInCart(expectedProductName), "Product should be removed from cart");
        LOGGER.info("Product successfully removed from cart");
    }

    @Test
    public void testInventorySortingByNameAZ() {
        LOGGER.info("Starting test: testInventorySorting");
        login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.selectSortingOption("Name (A to Z)");
        List<String> sortedNamesAZ = inventoryPage.getProductNames();
        LOGGER.info("Sorted product names (A to Z): {}", sortedNamesAZ);
        Assert.assertTrue(isSortedAscending(sortedNamesAZ), "Products are sorted correctly by Name (A to Z)");
    }

    @Test
    public void testInventorySortingByNameZA() {
        LOGGER.info("Starting test: testInventorySorting");
        login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.selectSortingOption("Name (Z to A)");
        List<String> sortedNamesZA = inventoryPage.getProductNames();
        LOGGER.info("Sorted product names (Z to A): {}", sortedNamesZA);
        Assert.assertTrue(isSortedDescending(sortedNamesZA), "Products are sorted correctly by Name (Z to A)");
    }

    @Test
    public void testInventorySortingByPriceLowHigh() {
        LOGGER.info("Starting test: testInventorySorting");
        login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.selectSortingOption("Price (low to high)");
        List<Double> sortedPricesLowToHigh = inventoryPage.getProductPrices();
        LOGGER.info("Sorted product prices (Low to High): {}", sortedPricesLowToHigh);
        Assert.assertTrue(isSortedAscending(sortedPricesLowToHigh), "Products are sorted correctly by Price (Low to High)");
    }

    @Test
    public void testInventorySortingByPriceHighLow(){
        LOGGER.info("Starting test: testInventorySorting");
        login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.selectSortingOption("Price (high to low)");
        List<Double> sortedPricesHighToLow = inventoryPage.getProductPrices();
        LOGGER.info("Sorted product prices (High to Low): {}", sortedPricesHighToLow);
        Assert.assertTrue(isSortedDescending(sortedPricesHighToLow), "Products are sorted correctly by Price (High to Low)");

        LOGGER.info("Test testInventorySorting completed.");
    }

    @Test
    public void testCheckoutProcess() {
        String productName = "Sauce Labs Backpack";

        LOGGER.info("Starting test: testCheckoutProcess");
        login("standard_user", "secret_sauce");
        clearCartAndContinueShopping();

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addProductToCartByName(productName);
        LOGGER.info("Added product to cart: {}", productName);
        inventoryPage.clickCartButton();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckoutButton();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Assert.assertTrue(checkoutPage.isPageOpened(), "Checkout page is opened.");

        checkoutPage.fillCheckoutForm("John", "Doe", "12345");
        checkoutPage.clickContinue();
        LOGGER.info("Entered checkout information and proceeded.");

        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(driver);
        List<String> expectedProducts = List.of(productName);
        Assert.assertTrue(overviewPage.isProductInSummary(expectedProducts), "Product summary matches expected items!");

        overviewPage.clickFinishButton();

        CheckoutCompletePage completePage = new CheckoutCompletePage(driver);
        Assert.assertTrue(completePage.isPageOpened(), "Checkout Complete page is opened!");
        Assert.assertTrue(completePage.isOrderCompleteMessageDisplayed(), "Order confirmation message is displayed!");

        LOGGER.info("Checkout process completed successfully.");
    }

    @Test
    public void testCheckoutWithEmptyCart() {
        LOGGER.info("Starting test: testCheckoutWithEmptyCart");

        login("standard_user", "secret_sauce");
        LOGGER.info("Logged in successfully.");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.clickCartButton();
        LOGGER.info("Navigated to the cart page.");

        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty before checkout.");
        LOGGER.info("Cart is empty as expected.");

        cartPage.clickCheckoutButton();
        LOGGER.info("Clicked on checkout button.");

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        Assert.assertTrue(checkoutPage.isPageOpened(), "Checkout page should be displayed.");
        LOGGER.info("Checkout page is displayed.");

        checkoutPage.fillCheckoutForm("Test", "User", "12345");
        checkoutPage.clickContinue();
        LOGGER.info("Entered checkout details and clicked Continue.");

        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(driver);
        List<String> expectedProducts = List.of("");
        Assert.assertTrue(overviewPage.isProductInSummary(expectedProducts), "Product summary matches expected items!");
        Assert.assertTrue(overviewPage.isPageOpened(), "Overview page should be displayed.");
        LOGGER.info("Overview page is displayed.");

        overviewPage.clickFinishButton();
        LOGGER.info("Clicked Finish button to complete order.");

        CheckoutCompletePage completePage = new CheckoutCompletePage(driver);
        Assert.assertTrue(completePage.isPageOpened(), "Checkout complete page should be displayed.");
        LOGGER.error("BUG: Order was successfully completed with an empty cart!");

        LOGGER.info("Test finished: testCheckoutWithEmptyCart");
    }
}