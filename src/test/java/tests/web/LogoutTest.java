package tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.InventoryPage;
import pages.LoginPage;

public class LogoutTest extends BaseTest {
    @Test
    public void testLogout() {
        loginToSauceDemo();

        InventoryPage inventoryPage = new InventoryPage(getDriver());
        Assert.assertTrue(inventoryPage.isPageOpened(), "Inventory page is opened after login.");

        inventoryPage.getHeaderMenuComponent().openMenu();
        inventoryPage.getHeaderMenuComponent().logout();

        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.isPageOpened(), "Login page should be shown after logout.");
    }
}
