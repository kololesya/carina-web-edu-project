package pages;

import org.openqa.selenium.WebDriver;

import com.zebrunner.carina.webdriver.gui.AbstractPage;

import java.util.Optional;

public class BasePage extends AbstractPage {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        open(getUrl().orElseThrow(() ->
                new IllegalStateException("URL must not be null. Either override getUrl() or pass a URL to open.")));
    }

    public void open(String url) {
        getDriver().get(url); // Открываем URL
    }

    public Optional<String> getUrl() {
        return Optional.empty();
    }
}
