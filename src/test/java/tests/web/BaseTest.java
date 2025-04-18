package tests.web;

import com.zebrunner.carina.webdriver.IDriverPool;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.R;

import pages.LoginPage;
import utils.CustomCapabilities;

public class BaseTest implements IAbstractTest {

    public void loginToSauceDemo() {
        setCapabilities(CustomCapabilities.getChromeCapabilities());

        WebDriver driver = getDriver();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(
                R.TESTDATA.get("user.standard"),
                R.TESTDATA.getDecrypted("common.password")
        );
    }
}
