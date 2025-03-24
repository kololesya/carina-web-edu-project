package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.zebrunner.carina.core.IAbstractTest;

import pages.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utility.Utilities;

import static utility.Utilities.*;

public class AddRemoveProductInCartTest implements IAbstractTest {
    private static final Logger LOGGER = LogManager.getLogger(AddRemoveProductInCartTest.class);
    private WebDriver driver = getDriver();

    private String expectedProductName = "Sauce Labs Backpack";

    @Test
    public void testVerifyCartBadgeText() {
        LOGGER.info("Starting test: testVerifyCartBadgeText");
        Utilities.login(driver, "standard_user", "secret_sauce");
        Utilities.clearCartAndContinueShopping(driver);

        InventoryPage inventoryPage = new InventoryPage(driver);
        LOGGER.info("Checking if cart badge is empty");
        Assert.assertTrue(inventoryPage.getCartBadgeText().isEmpty(), "Cart badge should be empty initially");

        inventoryPage.addProductToCartByName(expectedProductName);
        Assert.assertEquals(inventoryPage.getCartBadgeText(), "1", "Cart badge should display '1' after adding one product");

        LOGGER.info("Cart badge text verification test passed");
    }

    @Test
    public void testAddSpecificProductToCart() {
        LOGGER.info("Starting test: testAddSpecificProductToCart");
        login(driver, "standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertFalse(inventoryPage.isCartBadgeVisible(), "Cart badge should not be visible initially");

        inventoryPage.addProductToCartByName(expectedProductName);
        Assert.assertTrue(inventoryPage.isCartBadgeVisible(), "Cart badge should be visible after adding a product");

        LOGGER.info("Product successfully added to cart and verified");
    }

    @Test
    public void testVerifyProductInCart() {
        LOGGER.info("Starting test: testVerifyProductInCart");
        login(driver, "standard_user", "secret_sauce");
        clearCartAndContinueShopping(driver);

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addProductToCartByName(expectedProductName);
        inventoryPage.clickCartButton();

        CartPage cartPage = new CartPage(driver);
        String actualProductName = cartPage.getProductNameInCart();

        LOGGER.info("Expected product: '{}', Actual product: '{}'", expectedProductName, actualProductName);
        Assert.assertEquals(actualProductName, expectedProductName, "Product name in cart should match the expected name");

        LOGGER.info("Product verification in cart test passed");
    }

    @Test
    public void testRemoveProductFromCart() {
        LOGGER.info("Starting test: testRemoveProductFromCart");
        login(driver, "standard_user", "secret_sauce");
        clearCartAndContinueShopping(driver);

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addProductToCartByName(expectedProductName);
        inventoryPage.clickCartButton();

        CartPage cartPage = new CartPage(driver);
        LOGGER.info("Attempting to remove product '{}' from cart", expectedProductName);
        cartPage.removeProductFromCart(expectedProductName);

        Assert.assertTrue(cartPage.isProductNotInCart(expectedProductName), "Product should be removed from cart");
        LOGGER.info("Product successfully removed from cart");
    }
}