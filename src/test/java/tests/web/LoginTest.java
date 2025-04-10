package tests.web;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.R;
import com.zebrunner.carina.utils.config.Configuration;

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

    @DataProvider(name = "localeDataFromConfig")
    public Object[][] localeDataFromConfig() {
        String[] locales = Configuration.get("locales").orElse("").split(",");
        Object[][] data = new Object[locales.length][1];
        for (int i = 0; i < locales.length; i++) {
            data[i][0] = locales[i].trim();
        }
        return data;
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

    @Test(dataProvider = "localeDataFromConfig")
    public void testLoginWithLocaleFromConfig(String locale) {
        String url = R.CONFIG.get("url");
        String localeParam = R.CONFIG.get("locale_param");

        String localeUrl = url + localeParam + locale;
        getDriver().get(localeUrl);

        String username = R.CONFIG.get("sauce_username");
        String password = R.CONFIG.getDecrypted("sauce_password");

        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.isPageOpened(), "Login page is not opened.");

        loginPage.login(username, password);

        InventoryPage inventoryPage = new InventoryPage(getDriver());
        Assert.assertTrue(inventoryPage.isPageOpened(), "Inventory page should be opened for locale: " + locale);
    }
}
