package tests.web;

import com.zebrunner.carina.core.IAbstractTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import java.util.List;

import static utility.Utilities.clearCartAndContinueShopping;
import static utility.Utilities.login;

public class CheckoutTest implements IAbstractTest {
    private static final Logger LOGGER = LogManager.getLogger(CheckoutTest.class);
    private WebDriver driver = getDriver();

    @Test
    public void testCheckoutProcess() {
        String productName = "Sauce Labs Backpack";

        LOGGER.info("Starting test: testCheckoutProcess");
        login(driver, "standard_user", "secret_sauce");
        clearCartAndContinueShopping(driver);

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

        login(driver,"standard_user", "secret_sauce");
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
