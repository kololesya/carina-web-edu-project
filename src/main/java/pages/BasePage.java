package pages;

import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;

import java.util.Optional;

public class BasePage extends AbstractPage {
    public BasePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        open(null);
    }

    public void open(String url) {
        String targetUrl = (url != null) ? url : getUrl().orElse(null);
        if (targetUrl != null) {
            getDriver().get(targetUrl);
        } else {
            throw new IllegalStateException("URL must not be null. Either override getUrl() or pass a URL to open().");
        }
    }

    public Optional<String> getUrl() {
        return Optional.empty();
    }
}
