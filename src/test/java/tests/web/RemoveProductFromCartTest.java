package tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.InventoryPage;

import static constants.ProjectConstant.EXPECTED_CART_BADGE_COUNT;
import static constants.ProjectConstant.SAUCE_LABS_ONESIE;

public class RemoveProductFromCartTest extends BaseTest{

    @Test
    public void testRemoveProductFromCart() {
        loginToSauceDemo();
        CartPage cartPage = addProductToCartAndOpenCart(SAUCE_LABS_ONESIE);
        Assert.assertTrue(cartPage.isPageOpened());
        cartPage.removeProductFromCart(SAUCE_LABS_ONESIE);
        Assert.assertTrue(cartPage.isProductNotInCart(SAUCE_LABS_ONESIE), "Product should be removed from cart");
    }

    @Test
    public void testClearCartAndContinueShopping() {
        loginToSauceDemo();
        CartPage cartPage = addProductToCartAndOpenCart(SAUCE_LABS_ONESIE);
        Assert.assertEquals(cartPage.getCartBadgeText(), EXPECTED_CART_BADGE_COUNT);
        cartPage.clearCart();
        Assert.assertTrue(cartPage.getCartBadgeText().isEmpty(), "Cart should be empty after clearing");
        InventoryPage inventoryPage = cartPage.clickContinueShopping();
        Assert.assertTrue(inventoryPage.isPageOpened(), "User should be returned to the inventory page");
    }
}
