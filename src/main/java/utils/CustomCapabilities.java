package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class CustomCapabilities {

    public static DesiredCapabilities getChromeCapabilities() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments(
                "--disable-blink-features=AutomationControlled",
                "--disable-infobars",
                "--disable-extensions",
                "--disable-save-password-bubble",
                "--disable-password-manager-reauthentication",
                "--disable-notifications",
                "--disable-popup-blocking",
                "--disable-translate",
                "--disable-features=PasswordLeakDetection,PasswordCheck,PasswordImport,AutofillAssistant,TranslateUI",
                "--no-default-browser-check",
                "--safebrowsing-disable-download-protection",
                "--disable-background-networking",
                "--disable-background-timer-throttling",
                "--disable-renderer-backgrounding",
                "--disable-device-discovery-notifications",
                "--metrics-recording-only",
                "--disable-client-side-phishing-detection",
                "--disable-component-update",
                "--remote-debugging-port=9222"
        );

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("profile.default_content_setting_values.popups", 0);
        prefs.put("profile.default_content_setting_values.automatic_downloads", 1);
        prefs.put("profile.block_third_party_cookies", true);
        prefs.put("safebrowsing.enabled", true);

        if (R.CONFIG.get("chrome_prefs") != null) {
            try {
                Map<String, Object> externalPrefs = new Gson().fromJson(R.CONFIG.get("chrome_prefs"), Map.class);
                prefs.putAll(externalPrefs); // добавляем или переопределяем
            } catch (Exception e) {
                System.err.println("⚠ Не удалось распарсить chrome_prefs из config.properties: " + e.getMessage());
            }
        }

        options.setExperimentalOption("prefs", prefs);
        options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(ChromeOptions.CAPABILITY, options);
        return caps;
    }
}
