package tests.web;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.R;

import pages.LoginPage;

public class BaseTest implements IAbstractTest {

    @BeforeMethod
    public void loginToSauceDemo() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();
        Assert.assertTrue(loginPage.isPageOpened(), "Login page is opened.");

        loginPage.login(R.CONFIG.get("sauce_username"), R.CONFIG.getDecrypted("sauce_password"));
    }
}
