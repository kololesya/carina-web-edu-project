package tests.web;

import com.zebrunner.carina.utils.R;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.InventoryPage;
import pages.LoginPage;

public class LoginTest extends BaseTest {
    @DataProvider(name = "loginData")
    private Object[][] loginData() {
        String commonPass = R.TESTDATA.getDecrypted("common.password");

        return new Object[][] {
                { R.TESTDATA.get("user.standard"), commonPass, true },
                { R.TESTDATA.get("user.locked"), commonPass, false },
                { R.TESTDATA.get("user.problem"), commonPass, true },
                { R.TESTDATA.get("user.invalid"), R.TESTDATA.get("pass.invalid"), false }
        };
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, boolean isSuccessful) {
        LoginPage loginPage = openLoginPage();
        InventoryPage inventoryPage = loginPage.login(username, password);
        if (isSuccessful) {
            Assert.assertTrue(inventoryPage.isPageOpened(), "Inventory page should be opened");
        } else {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        }
    }
}
