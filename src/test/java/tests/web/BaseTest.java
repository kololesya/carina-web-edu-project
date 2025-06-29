package tests.web;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.R;

import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.CustomCapabilities;

import java.io.IOException;

public class BaseTest implements IAbstractTest {

    public LoginPage openLoginPage(){
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.open();
        return new LoginPage(getDriver());
    }

    public InventoryPage loginToSauceDemo() throws IOException {
        setCapabilities(CustomCapabilities.getChromeCapabilities());
        LoginPage loginPage = openLoginPage();
        loginPage.login(
                R.TESTDATA.getDecrypted("user.test"),
                R.TESTDATA.getDecrypted("valid.password")
        );

        return new InventoryPage(getDriver());
    }

    public CartPage addProductToCartAndOpenCart(String productName) {
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        inventoryPage.addProductToCartByName(productName);
        inventoryPage.getHeaderMenuComponent().clickCartButton();
        return new CartPage(getDriver());
    }
}
