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
        String validPass = R.TESTDATA.getDecrypted("valid.password");

        return new Object[][] {
                { R.TESTDATA.getDecrypted("user.test"), validPass, true },
                { R.TESTDATA.getDecrypted("user.locked"), validPass, false },
                { R.TESTDATA.getDecrypted("user.problem"), validPass, true },
                { R.TESTDATA.getDecrypted("user.invalid"), R.TESTDATA.getDecrypted("invalid.password"), false }
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
