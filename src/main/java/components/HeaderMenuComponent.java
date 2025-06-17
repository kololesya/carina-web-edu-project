package components;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;


import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import pages.LoginPage;

import java.time.Duration;

import static constants.ProjectConstants.TIMEOUT_FOR_WAIT;

public class HeaderMenuComponent extends AbstractUIObject {

    @FindBy(className = "shopping_cart_link")
    private ExtendedWebElement cartButton;

    @FindBy(id="shopping_cart_container")
    private ExtendedWebElement cartBadge;

    @FindBy(id = "react-burger-menu-btn")
    private ExtendedWebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private ExtendedWebElement logoutButton;

    public HeaderMenuComponent(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void openMenu() {
        menuButton.click();
    }

    public LoginPage logout() {
        logoutButton.click(
                TIMEOUT_FOR_WAIT,
                ExpectedConditions.elementToBeClickable(logoutButton.getElement())
        );
        return new LoginPage(getDriver());
    }

    public CartPage clickCartButton() {
        cartButton.click(TIMEOUT_FOR_WAIT,
                ExpectedConditions.elementToBeClickable(cartButton.getElement()));
        return new CartPage(getDriver());
    }

    public String getCartBadgeText() {
        return cartBadge.getText().trim();
    }
}
