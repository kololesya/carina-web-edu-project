package pages;

import org.openqa.selenium.WebDriver;

import com.zebrunner.carina.webdriver.gui.AbstractPage;

import java.util.Optional;

public abstract class BasePage extends AbstractPage {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public Optional<String> getUrl() {
        return Optional.empty();
    }
}
