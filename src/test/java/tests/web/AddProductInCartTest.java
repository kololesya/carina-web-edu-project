package tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.InventoryPage;
import pages.CartPage;
import pages.ProductPage;

import static constants.ConstantsForProject.EXPECTED_CART_BADGE_COUNT;
import static constants.ConstantsForProject.SAUCE_LABS_ONESIE;

public class AddProductInCartTest extends BaseTest {

    @Test
    public void testVerifyCartBadgeText() {
        loginToSauceDemo();
        InventoryPage inventoryPage = new InventoryPage(getDriver());

        Assert.assertTrue(inventoryPage.getHeaderMenuComponent().getCartBadgeText().isEmpty(), "Cart badge should be empty initially");

        inventoryPage.addProductToCartByName(SAUCE_LABS_ONESIE);
        Assert.assertEquals(inventoryPage.getHeaderMenuComponent().getCartBadgeText(), EXPECTED_CART_BADGE_COUNT, "Cart badge should display '1' after adding one product");
    }

    @Test
    public void testVerifyProductInCart() {
        loginToSauceDemo();
        addProductToCartAndOpenCart(SAUCE_LABS_ONESIE);

        CartPage cartPage = new CartPage(getDriver());
        String actualProductName = cartPage.getProductNameInCart();

        Assert.assertEquals(actualProductName, SAUCE_LABS_ONESIE, "Product name in cart should match the expected name");
    }

    @Test
    public void testAddProductToCartFromProductPage(){
        loginToSauceDemo();
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        inventoryPage.openProductPageByProductName(SAUCE_LABS_ONESIE);

        ProductPage productPage = new ProductPage(getDriver());
        Assert.assertTrue(productPage.isPageOpened());

        productPage.addToCart();

        Assert.assertFalse(inventoryPage.getHeaderMenuComponent().getCartBadgeText().isEmpty(), "Cart badge should be visible after adding a product");
        Assert.assertEquals(inventoryPage.getHeaderMenuComponent().getCartBadgeText(), EXPECTED_CART_BADGE_COUNT,
                "Cart badge should display '1' after adding one product");
    }

    @Test
    public void testNavigateBackToInventoryFromProductPage() {
        loginToSauceDemo();
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        inventoryPage.openProductPageByProductName(SAUCE_LABS_ONESIE);

        ProductPage productPage = new ProductPage(getDriver());
        Assert.assertTrue(productPage.isPageOpened(), "Product page should be opened.");
        productPage.addToCart();

        productPage.returnToInventoryPage();
        Assert.assertTrue(inventoryPage.isPageOpened(), "Inventory page should be opened after clicking back.");
    }
}