package pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends AbstractPage {
    private static final Logger LOGGER = LogManager.getLogger(CartPage.class);

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "title")
    private ExtendedWebElement checkoutTitle;

    @FindBy(id = "first-name")
    private ExtendedWebElement firstNameField;

    @FindBy(id = "last-name")
    private ExtendedWebElement lastNameField;

    @FindBy(id = "postal-code")
    private ExtendedWebElement zipCodeField;

    @FindBy(id = "continue")
    private ExtendedWebElement continueButton;

    public boolean isPageOpened() {
        return checkoutTitle.getText().equals("Checkout: Your Information");
    }

    public void fillCheckoutForm(String firstName, String lastName, String zipCode) {
        LOGGER.info("Filling checkout form with firstName: {}, lastName: {}, zipCode: {}", firstName, lastName, zipCode);
        firstNameField.type(firstName);
        lastNameField.type(lastName);
        zipCodeField.type(zipCode);
    }

    public void clickContinue() {
        LOGGER.info("Clicking 'Continue' button.");
        continueButton.click();
    }
}
