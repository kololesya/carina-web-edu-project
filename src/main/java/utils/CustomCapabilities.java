package utils;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomCapabilities {

    public static DesiredCapabilities getChromeCapabilities() {
        ChromeOptions options = new ChromeOptions();

        // Аргументы Chrome
        options.addArguments(
                "--disable-blink-features=AutomationControlled",
                "--disable-infobars",
                "--disable-extensions",
                "--disable-save-password-bubble",
                "--disable-password-manager-reauthentication",
                "--no-default-browser-check",
                "--disable-notifications",
                "--disable-popup-blocking",
                "--disable-translate",
                "--disable-features=PasswordLeakDetection,PasswordCheck,PasswordImport,AutofillAssistant",
                "--remote-debugging-port=9222"
                // "--headless=new" // раскомментируй, если хочешь безголовый режим
        );

        // Предпочтения пользователя
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.default_content_setting_values.notifications", 2); // блок уведомлений
        prefs.put("profile.default_content_setting_values.popups", 0);
        prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
        prefs.put("profile.block_third_party_cookies", true);

        options.setExperimentalOption("prefs", prefs);
        options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        return caps;
    }
}
