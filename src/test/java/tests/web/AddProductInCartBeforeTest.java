package tests.web;

import org.testng.annotations.BeforeMethod;

import pages.InventoryPage;

import static constants.ConstantsForProject.SAUCE_LABS_ONESIE;

public class AddProductInCartBeforeTest extends BaseTest{

    @BeforeMethod
    public void addProductInCart() {
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        inventoryPage.addProductToCartByName(SAUCE_LABS_ONESIE);
        inventoryPage.getHeaderMenuComponent().clickCartButton();
    }
}
