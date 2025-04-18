package tests.web;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.CheckoutPage;
import pages.CheckoutOverviewPage;
import pages.CheckoutCompletePage;

import static constants.ConstantsForProject.SAUCE_LABS_ONESIE;

public class CheckoutTest extends BaseTest{
    @Test
    public void testCheckoutProcess() {

        CartPage cartPage = new CartPage(getDriver());
        cartPage.clickCheckoutButton();

        CheckoutPage checkoutPage = new CheckoutPage(getDriver());
        Assert.assertTrue(checkoutPage.isPageOpened(), "Checkout page is opened.");

        checkoutPage.fillCheckoutForm("John", "Doe", "12345");
        checkoutPage.clickContinue();

        CheckoutOverviewPage overviewPage = new CheckoutOverviewPage(getDriver());
        List<String> expectedProducts = List.of(SAUCE_LABS_ONESIE);
        Assert.assertTrue(overviewPage.isProductInOrderSummary(expectedProducts), "Product summary matches expected items!");

        overviewPage.clickFinishButton();

        CheckoutCompletePage completePage = new CheckoutCompletePage(getDriver());
        Assert.assertTrue(completePage.isPageOpened(), "Checkout Complete page is opened!");
        Assert.assertTrue(completePage.isOrderCompleteMessageDisplayed(), "Order confirmation message is displayed!");
    }

    @Test
    public void testCheckoutWithEmptyCart() {

        CartPage cartPage = new CartPage(getDriver());
        cartPage.clearCart();
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
