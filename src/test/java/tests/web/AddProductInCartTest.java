package tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.*;

public class AddProductInCartTest extends BaseTest {

    private final String SAUCE_LABS_ONESIE = "Sauce Labs Onesie";

    @Test
    public void testVerifyCartBadgeText() {
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        Assert.assertTrue(inventoryPage.getCartBadgeText().isEmpty(), "Cart badge should be empty initially");

        inventoryPage.addProductToCartByName(SAUCE_LABS_ONESIE);
        Assert.assertEquals(inventoryPage.getCartBadgeText(), "1", "Cart badge should display '1' after adding one product");
    }

    @Test
    public void testVerifyProductInCart() {
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        inventoryPage.addProductToCartByName(SAUCE_LABS_ONESIE);
        Assert.assertTrue(inventoryPage.isCartBadgeVisible(), "Cart badge should be visible after adding a product");

        inventoryPage.clickCartButton();

        CartPage cartPage = new CartPage(getDriver());
        String actualProductName = cartPage.getProductNameInCart();

        Assert.assertEquals(actualProductName, SAUCE_LABS_ONESIE, "Product name in cart should match the expected name");
    }

    @Test
    public void testAddProductToCartFromProductPage(){
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        inventoryPage.openProductPage(SAUCE_LABS_ONESIE);

        ProductPage productPage = new ProductPage(getDriver());
        Assert.assertTrue(productPage.isPageOpened());

        productPage.addToCart();

        Assert.assertTrue(inventoryPage.isCartBadgeVisible(), "Cart badge should be visible after adding a product");
        Assert.assertEquals(inventoryPage.getCartBadgeText(), "1", "Cart badge should display '1' after adding one product");

    }

    @Test
    public void testNavigateBackToInventoryFromProductPage() {
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        Assert.assertTrue(inventoryPage.isPageOpened(), "Inventory page should be opened.");

        inventoryPage.openProductPage(SAUCE_LABS_ONESIE);

        ProductPage productPage = new ProductPage(getDriver());
        Assert.assertTrue(productPage.isPageOpened(), "Product page should be opened.");

        productPage.returnToInventoryPage();
        Assert.assertTrue(inventoryPage.isPageOpened(), "Inventory page should be opened after clicking back.");
    }
}