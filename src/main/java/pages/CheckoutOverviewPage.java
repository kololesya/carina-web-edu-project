package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutOverviewPage extends BasePage {

    @FindBy(className = "title")
    private ExtendedWebElement overviewTitle;

    @FindBy(id = "finish")
    private ExtendedWebElement finishButton;

    @FindBy(className = "inventory_item_name")
    private List<ExtendedWebElement> inventoryItemNames;

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ExtendedWebElement getUniqueElement() {
        return finishButton;
    }

    public CheckoutCompletePage clickFinishButton() {
        finishButton.click();
        return new CheckoutCompletePage(getDriver());
    }

    public boolean isProductInOrderSummary(List<String> expectedProducts) {
        return getActualProductNames().equals(expectedProducts);
    }

    private List<String> getActualProductNames() {
        return inventoryItemNames.stream()
                .map(item -> item.getText().trim())
                .collect(Collectors.toList());
    }
}
