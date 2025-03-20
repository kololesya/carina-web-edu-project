package pages;

import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginPage extends AbstractPage {
    private static final Logger LOGGER = LogManager.getLogger(CartPage.class);

    @FindBy(id = "user-name")
    private ExtendedWebElement usernameField;

    @FindBy(id = "password")
    private ExtendedWebElement passwordField;

    @FindBy(id = "login-button")
    private ExtendedWebElement loginButton;

    @FindBy(xpath = "//h3[@data-test='error']")
    private ExtendedWebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        LOGGER.info("Attempting to log in with username: {}", username);

        usernameField.type(username);
        LOGGER.info("Entered username.");

        passwordField.type(password);
        LOGGER.info("Entered password.");

        loginButton.click();
        LOGGER.info("Clicked login button.");
    }

    public boolean isPageOpened() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        try {
            LOGGER.info("Checking if the login page is opened...");
            boolean isVisible = wait.until(ExpectedConditions.visibilityOf(loginButton.getElement())) != null;
            LOGGER.info("Login page is visible: {}", isVisible);
            return isVisible;
        } catch (TimeoutException e) {
            LOGGER.error("Login page did not open within the timeout.", e);
            return false;
        }
    }

    public String getErrorMessage() {
        return errorMessage.getText().trim();
    }
}
