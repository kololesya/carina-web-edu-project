package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

import static com.zebrunner.agent.core.webdriver.RemoteWebDriverFactory.getDriver;

public class Utilities {
    public static void login(WebDriver driver, String userName, String password) {
        driver.get("https://www.saucedemo.com/");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(userName, password);
    }

    public static void clearCartAndContinueShopping(WebDriver driver) {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.clickCartButton();

        CartPage cartPage = new CartPage(driver);
        cartPage.clearCart();

        cartPage.clickContinueShopping();
    }
}
