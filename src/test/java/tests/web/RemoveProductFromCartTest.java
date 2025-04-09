package tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;

import static constants.ConstantsForProject.SAUCE_LABS_ONESIE;

public class RemoveProductFromCartTest extends AddToCartBeforeTest{

    @Test
    public void testRemoveProductFromCart() {
        CartPage cartPage = new CartPage(getDriver());

        cartPage.removeProductFromCart(SAUCE_LABS_ONESIE);
        Assert.assertTrue(cartPage.isProductNotInCart(SAUCE_LABS_ONESIE), "Product should be removed from cart");
    }

    @Test
    public void testClearCartAndContinueShopping() {
        CartPage cartPage = new CartPage(getDriver());
        Assert.assertFalse(cartPage.isProductNotInCart(SAUCE_LABS_ONESIE), "Product should be in cart");
        cartPage.clearCart();

        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty after clearing");
        cartPage.clickContinueShopping();

        InventoryPage inventoryPage = new InventoryPage(getDriver());
        Assert.assertTrue(inventoryPage.isPageOpened(), "User should be returned to the inventory page");
    }
}
