package tests.web;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.*;

import static constants.ProjectConstants.*;

public class CheckoutTest extends BaseTest {
    @Test
    public void testCheckoutProcess() {
        loginToSauceDemo();
        CartPage cartPage = addProductToCartAndOpenCart(SAUCE_LABS_ONESIE);
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
        Assert.assertTrue(checkoutPage.isPageOpened(), "Checkout page is opened.");
        checkoutPage.fillCheckoutForm(FIRST_NAME, LAST_NAME, ZIP_CODE);
        CheckoutOverviewPage overviewPage = checkoutPage.clickContinue();
        List<String> expectedProducts = List.of(SAUCE_LABS_ONESIE);
        Assert.assertTrue(overviewPage.isProductInOrderSummary(expectedProducts), "Product summary matches expected items!");
        CheckoutCompletePage completePage = overviewPage.clickFinishButton();
        Assert.assertTrue(completePage.isPageOpened(), "Checkout Complete page is opened!");
        Assert.assertTrue(completePage.isOrderCompleteMessageDisplayed(), "Order confirmation message is displayed!");
    }

    @Test
    public void testCheckoutWithEmptyCart() {
        loginToSauceDemo();
        CartPage cartPage = addProductToCartAndOpenCart(SAUCE_LABS_ONESIE);
        cartPage.clearCart();
        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty before checkout.");
        CheckoutPage checkoutPage = cartPage.clickCheckoutButton();
        Assert.assertTrue(checkoutPage.isPageOpened(), "Checkout page should be displayed.");
        checkoutPage.fillCheckoutForm(FIRST_NAME, LAST_NAME, ZIP_CODE);
        CheckoutOverviewPage overviewPage = checkoutPage.clickContinue();
        Assert.assertTrue(overviewPage.isPageOpened(), "Overview page should be displayed.");
        CheckoutCompletePage completePage = overviewPage.clickFinishButton();
        Assert.assertTrue(completePage.isPageOpened(), "Checkout complete page should be displayed.");
    }
}
