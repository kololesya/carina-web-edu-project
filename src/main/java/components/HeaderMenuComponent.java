package components;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;

import java.util.List;

public class HeaderMenuComponent extends AbstractUIObject {

    @FindBy(className = "shopping_cart_link")
    private ExtendedWebElement cartButton;

    @FindBy(className = "shopping_cart_badge")
    private List<ExtendedWebElement> cartBadgeElements;

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

    public void logout() {
        logoutButton.click();
    }

    public void clickCartButton() {
        cartButton.click();
    }

    public boolean isCartEmpty() {
        return cartBadgeElements.isEmpty();
    }

    public String getCartBadgeText() {
        if (!cartBadgeElements.isEmpty()) {
            return cartBadgeElements.get(0).getText();
        }
        return "";
    }
}
