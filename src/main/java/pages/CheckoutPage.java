package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;

public class CheckoutPage extends BasePage {
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

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageOpened() {
        return checkoutTitle.isPresent() && checkoutTitle.getText().equals("Checkout: Your Information");
    }

    public void fillCheckoutForm(String firstName, String lastName, String zipCode) {
        firstNameField.type(firstName);
        lastNameField.type(lastName);
        zipCodeField.type(zipCode);
    }

    public void clickContinue() {
        continueButton.click();
    }
}
