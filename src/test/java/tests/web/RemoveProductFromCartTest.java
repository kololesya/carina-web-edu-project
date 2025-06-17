package tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.InventoryPage;

import java.io.IOException;

import static constants.ProjectConstants.EXPECTED_CART_BADGE_COUNT;
import static constants.ProjectConstants.SAUCE_LABS_ONESIE;

public class RemoveProductFromCartTest extends BaseTest{

    @Test
    public void testRemoveProductFromCart() throws IOException {
        loginToSauceDemo();
        CartPage cartPage = addProductToCartAndOpenCart(SAUCE_LABS_ONESIE);
        Assert.assertTrue(cartPage.isPageOpened(), "Cart Page should be opened");
        cartPage.removeProductFromCart(SAUCE_LABS_ONESIE);
        Assert.assertTrue(cartPage.isProductNotInCart(SAUCE_LABS_ONESIE), "Product should be removed from cart");
    }

    @Test
    public void testClearCartAndContinueShopping() throws IOException {
        loginToSauceDemo();
        CartPage cartPage = addProductToCartAndOpenCart(SAUCE_LABS_ONESIE);
        Assert.assertEquals(cartPage.getCartBadgeText(), EXPECTED_CART_BADGE_COUNT, "Cart badge should display '1' after adding one product");
        cartPage.clearCart();
        Assert.assertTrue(cartPage.getCartBadgeText().isEmpty(), "Cart should be empty after clearing");
        InventoryPage inventoryPage = cartPage.clickContinueShopping();
        Assert.assertTrue(inventoryPage.isPageOpened(), "User should be returned to the inventory page");
    }
}
