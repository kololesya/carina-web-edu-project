package tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.CartPage;
import pages.InventoryPage;

import static constants.ConstantsForProject.EXPECTED_CART_BADGE_COUNT;
import static constants.ConstantsForProject.SAUCE_LABS_ONESIE;

public class RemoveProductFromCartTest extends AddProductInCartBeforeTest{

    @Test
    public void testRemoveProductFromCart() {

        CartPage cartPage = new CartPage(getDriver());

        cartPage.removeProductFromCart(SAUCE_LABS_ONESIE);
        Assert.assertTrue(cartPage.isProductNotInCart(SAUCE_LABS_ONESIE), "Product should be removed from cart");
    }

    @Test
    public void testClearCartAndContinueShopping() {
        InventoryPage inventoryPage = new InventoryPage(getDriver());

        CartPage cartPage = new CartPage(getDriver());
        Assert.assertEquals(inventoryPage.getHeaderMenuComponent().getCartBadgeText(), EXPECTED_CART_BADGE_COUNT);
        cartPage.clearCart();

        Assert.assertTrue(inventoryPage.getHeaderMenuComponent().isCartEmpty(), "Cart should be empty after clearing");
        cartPage.clickContinueShopping();

        Assert.assertTrue(inventoryPage.isPageOpened(), "User should be returned to the inventory page");
    }

}
