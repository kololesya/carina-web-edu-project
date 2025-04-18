package tests.web;

import com.zebrunner.carina.webdriver.IDriverPool;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.R;

import pages.LoginPage;
import utils.CustomCapabilities;

public class BaseTest implements IAbstractTest, IDriverPool {

    @BeforeMethod
    public void loginToSauceDemo() {
        setCapabilities(CustomCapabilities.getChromeCapabilities());

        WebDriver driver = getDriver();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        Assert.assertTrue(loginPage.isPageOpened(), "Login page is opened.");
        loginPage.login(R.CONFIG.get("sauce_username"), R.CONFIG.getDecrypted("sauce_password"));
    }
}
