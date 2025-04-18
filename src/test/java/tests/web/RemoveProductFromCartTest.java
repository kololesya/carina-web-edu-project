package tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.InventoryPage;

import static constants.ConstantsForProject.EXPECTED_CART_BADGE_COUNT;
import static constants.ConstantsForProject.SAUCE_LABS_ONESIE;

public class RemoveProductFromCartTest extends BaseTest{

    @Test
    public void testRemoveProductFromCart() {
        loginToSauceDemo();
        addProductToCartAndOpenCart(SAUCE_LABS_ONESIE);

        CartPage cartPage = new CartPage(getDriver());

        cartPage.removeProductFromCart(SAUCE_LABS_ONESIE);
        Assert.assertTrue(cartPage.isProductNotInCart(SAUCE_LABS_ONESIE), "Product should be removed from cart");
    }

    @Test
    public void testClearCartAndContinueShopping() {
        loginToSauceDemo();
        addProductToCartAndOpenCart(SAUCE_LABS_ONESIE);

        CartPage cartPage = new CartPage(getDriver());
        Assert.assertEquals(cartPage.getHeaderMenuComponent().getCartBadgeText(), EXPECTED_CART_BADGE_COUNT);
        cartPage.clearCart();

        Assert.assertTrue(cartPage.getHeaderMenuComponent().getCartBadgeText().isEmpty(), "Cart should be empty after clearing");
        cartPage.clickContinueShopping();

        InventoryPage inventoryPage = new InventoryPage(getDriver());
        Assert.assertTrue(inventoryPage.isPageOpened(), "User should be returned to the inventory page");
    }
}
