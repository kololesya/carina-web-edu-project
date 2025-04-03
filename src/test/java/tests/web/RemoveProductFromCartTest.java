package tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;

public class RemoveProductFromCartTest extends BaseTest{
    private final String SAUCE_LABS_ONESIE = "Sauce Labs Onesie";

    @Test
    public void testRemoveProductFromCart() {
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        inventoryPage.addProductToCartByName(SAUCE_LABS_ONESIE);
        inventoryPage.clickCartButton();

        CartPage cartPage = new CartPage(getDriver());

        cartPage.removeProductFromCart(SAUCE_LABS_ONESIE);
        Assert.assertTrue(cartPage.isProductNotInCart(SAUCE_LABS_ONESIE), "Product should be removed from cart");
    }

    @Test
    public void testClearCartAndContinueShopping() {
        InventoryPage inventoryPage = new InventoryPage(getDriver());

        inventoryPage.addProductToCartByName(SAUCE_LABS_ONESIE);
        inventoryPage.addProductToCartByName("Sauce Labs Bike Light");

        Assert.assertEquals(inventoryPage.getCartBadgeText(), "2", "Cart badge should display '2' after adding two products");

        inventoryPage.clickCartButton();
        CartPage cartPage = new CartPage(getDriver());
        cartPage.clearCart();

        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty after clearing");
        cartPage.clickContinueShopping();

        Assert.assertTrue(inventoryPage.isPageOpened(), "User should be returned to the inventory page");
    }

}
