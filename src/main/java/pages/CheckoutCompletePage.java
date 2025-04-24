package pages;

import com.zebrunner.carina.webdriver.decorator.PageOpeningStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;

public class CheckoutCompletePage extends BasePage {

    @FindBy(className = "complete-header")
    private ExtendedWebElement orderCompleteMessage;

    @FindBy(className = "title")
    private ExtendedWebElement completeTitle;

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
        setPageOpeningStrategy(PageOpeningStrategy.BY_ELEMENT);
        setUiLoadedMarker(completeTitle);
    }

    public boolean isOrderCompleteMessageDisplayed() {
        return orderCompleteMessage.getText().equals("Thank you for your order!");
    }
}
