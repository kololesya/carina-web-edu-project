package tests.web;

import com.zebrunner.carina.core.IAbstractTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

import static utility.Utilities.login;
public class LoginLogoutTest implements IAbstractTest {
    private static final Logger LOGGER = LogManager.getLogger(LoginLogoutTest.class);
    private WebDriver driver = getDriver();

    @Test
    public void testLogin(){
        LOGGER.info("Starting test: testLogin");
        login(driver, "standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isPageOpened(), "Inventory page should be opened after login.");
        LOGGER.info("Login test passed");
    }

    @Test
    public void testLoginLockedUser(){
        LOGGER.info("Starting test: testLogin");
        login(driver, "locked_out_user", "secret_sauce");

        LoginPage loginPage = new LoginPage(driver);

        LOGGER.info("Attempting to log in with locked-out user: {}", "locked_out_user");
        String expectedErrorMessage = "Epic sadface: Sorry, this user has been locked out.";
        String actualErrorMessage = loginPage.getErrorMessage();

        LOGGER.info("Verifying login failure message.");
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message does not match expected text.");

        LOGGER.info("Test testLoginLockedOutUser completed.");
    }

    @Test
    public void testLogout(){
        LOGGER.info("Starting test: testLogout");
        login(driver, "standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.openMenu();
        inventoryPage.logout();

        Assert.assertFalse(inventoryPage.isPageOpened(), "Inventory page should be closed after logout.");
        LOGGER.info("Logout test passed");
    }
}
