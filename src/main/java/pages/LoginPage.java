package pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.utils.config.Configuration;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;

import java.util.NoSuchElementException;
import java.util.Optional;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private ExtendedWebElement usernameField;

    @FindBy(id = "password")
    private ExtendedWebElement passwordField;

    @FindBy(id = "login-button")
    private ExtendedWebElement loginButton;

    @FindBy(xpath = "//h3[contains(@data-test, 'error')]")
    private ExtendedWebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public Optional<String> getUrl() {
        return Configuration.get("url");
    }

    public InventoryPage login(String username, String password) {
        usernameField.type(username);
        passwordField.type(password);
        loginButton.click();
        return new InventoryPage(getDriver());
    }

    @Override
    public boolean isPageOpened() {
        return isAnyElementPresent();
    }

    public boolean isErrorMessageDisplayed() {
        return errorMessage.isDisplayed();

    }
}
