package pages;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutOverviewPage extends AbstractPage {
    private static final Logger LOGGER = LogManager.getLogger(CartPage.class);

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "title")
    private ExtendedWebElement overviewTitle;

    @FindBy(id = "finish")
    private ExtendedWebElement finishButton;

    @FindBy(className = "inventory_item_name")
    private List<ExtendedWebElement> orderSummaryItems;

    public void clickFinishButton() {
        LOGGER.info("Clicking 'Finish' button to complete checkout.");
        finishButton.click();
    }

    public boolean isPageOpened() {
        return overviewTitle.getText().equals("Checkout: Overview");
    }

    public boolean isProductInSummary(List<String> expectedProducts) {
        LOGGER.info("Verifying products in the order summary. Expected: {}", expectedProducts);

        List<String> actualProducts = orderSummaryItems.stream()
                .map(item -> item.getText().trim())
                .collect(Collectors.toList());

        LOGGER.debug("Actual products in summary: {}", actualProducts);

        return actualProducts.equals(expectedProducts);
    }

}
