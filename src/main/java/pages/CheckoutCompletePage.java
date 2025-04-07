package pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class CheckoutCompletePage extends BasePage {

    @FindBy(className = "complete-header")
    private ExtendedWebElement orderCompleteMessage;

    @FindBy(className = "title")
    private ExtendedWebElement completeTitle;

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageOpened() {
        return completeTitle.getText().equals("Checkout: Complete!");
    }

    public boolean isOrderCompleteMessageDisplayed() {
        return orderCompleteMessage.getText().equals("Thank you for your order!");
    }
}
