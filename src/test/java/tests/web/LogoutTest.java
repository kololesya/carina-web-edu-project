package tests.web;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.InventoryPage;
import pages.LoginPage;

import java.io.IOException;

public class LogoutTest extends BaseTest
{
    @Test
    public void testLogout() throws IOException {
        InventoryPage inventoryPage = loginToSauceDemo();
        Assert.assertTrue(inventoryPage.isPageOpened(), "Inventory page is opened after login.");
        inventoryPage.getHeaderMenuComponent().openMenu();
        LoginPage loginPage = inventoryPage.getHeaderMenuComponent().logout();
        Assert.assertTrue(loginPage.isPageOpened(), "Login page should be shown after logout.");
    }
}
