package tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.InventoryPage;
import pages.CartPage;
import pages.ProductPage;

import java.io.IOException;

import static constants.ProjectConstants.EXPECTED_CART_BADGE_COUNT;
import static constants.ProjectConstants.SAUCE_LABS_ONESIE;

public class AddProductInCartTest extends BaseTest {

    @Test
    public void testVerifyCartBadgeText() throws IOException {
        InventoryPage inventoryPage = loginToSauceDemo();
        Assert.assertTrue(inventoryPage.getCartBadgeText().isEmpty(), "Cart badge should be empty initially");
        inventoryPage.addProductToCartByName(SAUCE_LABS_ONESIE);
        Assert.assertEquals(inventoryPage.getCartBadgeText(), EXPECTED_CART_BADGE_COUNT, "Cart badge should display '1' after adding one product");
    }

    @Test
    public void testVerifyProductInCart() throws IOException {
        loginToSauceDemo();
        CartPage cartPage = addProductToCartAndOpenCart(SAUCE_LABS_ONESIE);
        Assert.assertFalse(cartPage.isProductNotInCart(SAUCE_LABS_ONESIE), "Product name in cart should match the expected name");
    }

    @Test
    public void testAddProductToCartFromProductPage() throws IOException {
        InventoryPage inventoryPage = loginToSauceDemo();
        ProductPage productPage = inventoryPage.openProductPageByProductName(SAUCE_LABS_ONESIE);
        Assert.assertTrue(productPage.isPageOpened());
        productPage.addProductToCart();
        Assert.assertEquals(inventoryPage.getCartBadgeText(), EXPECTED_CART_BADGE_COUNT,
                "Cart badge should display '1' after adding one product");
    }

    @Test
    public void testNavigateBackToInventoryFromProductPage() throws IOException {
        InventoryPage inventoryPage = loginToSauceDemo();
        ProductPage productPage = inventoryPage.openProductPageByProductName(SAUCE_LABS_ONESIE);
        Assert.assertTrue(productPage.isPageOpened(), "Product page should be opened.");
        productPage.addProductToCart();
        inventoryPage = productPage.returnToInventoryPage();
        Assert.assertTrue(inventoryPage.isPageOpened(), "Inventory page should be opened after clicking back.");
    }
}