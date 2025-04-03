package tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import java.util.List;

public class CheckoutTest extends BaseTest{
    @Test
    public void testCheckoutProcess() {
        String productName = "Sauce Labs Backpack";

        InventoryPage inventoryPage = new InventoryPage(getDriver());
        inventoryPage.addProductToCartByName(productName);
        inventoryPage.clickCartButton();

        CartPage cartPage = new CartPage(getDriver());
        cartPage.clickCheckoutButton();

        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        Assert.assertTrue(checkoutPage.isPageOpened(), "Checkout page is opened.");

        checkoutPage.fillCheckoutForm("John", "Doe", "12345");
        checkoutPage.clickContinue();

        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(getDriver());
        List<String> expectedProducts = List.of(productName);
        Assert.assertTrue(overviewPage.isProductInSummary(expectedProducts), "Product summary matches expected items!");

        overviewPage.clickFinishButton();

        CheckoutCompletePage completePage = new CheckoutCompletePage(getDriver());
        Assert.assertTrue(completePage.isPageOpened(), "Checkout Complete page is opened!");
        Assert.assertTrue(completePage.isOrderCompleteMessageDisplayed(), "Order confirmation message is displayed!");
    }

    @Test
    public void testCheckoutWithEmptyCart() {
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        inventoryPage.clickCartButton();

        CartPage cartPage = new CartPage(getDriver());
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty before checkout.");

        cartPage.clickCheckoutButton();

        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        Assert.assertTrue(checkoutPage.isPageOpened(), "Checkout page should be displayed.");

        checkoutPage.fillCheckoutForm("Test", "User", "12345");
        checkoutPage.clickContinue();

        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(getDriver());
        Assert.assertTrue(overviewPage.isPageOpened(), "Overview page should be displayed.");

        overviewPage.clickFinishButton();

        CheckoutCompletePage completePage = new CheckoutCompletePage(getDriver());
        Assert.assertTrue(completePage.isPageOpened(), "Checkout complete page should be displayed.");
    }
}
