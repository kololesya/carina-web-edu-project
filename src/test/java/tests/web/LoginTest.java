package tests.web;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.zebrunner.carina.core.IAbstractTest;

import pages.InventoryPage;
import pages.LoginPage;

public class LoginTest implements IAbstractTest {
    @DataProvider(name = "loginData")
    private Object[][] loginData() {
        return new Object[][] {
                { "standard_user", "secret_sauce", true },
                { "locked_out_user", "secret_sauce", false },
                { "problem_user", "secret_sauce", true },
                { "invalid_user", "wrong_password", false }
        };
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, boolean isSuccessful) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();

        loginPage.login(username, password);

        if (isSuccessful) {
            InventoryPage inventoryPage = new InventoryPage(getDriver());
            Assert.assertTrue(inventoryPage.isPageOpened(), "Inventory page should be opened");
        } else {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        }
    }
}
