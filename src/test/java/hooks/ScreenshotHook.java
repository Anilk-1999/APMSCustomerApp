package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.appium.java_client.android.AndroidDriver;


public class ScreenshotHook {
    private AndroidDriver driver = AppHooks.getDriver();

    @After
    public void attachScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
            } catch (Exception e) {
                // UiAutomator2 instrumentation may have crashed — skip screenshot
                // so the failure does NOT propagate and kill subsequent scenarios.
                System.out.println("[ScreenshotHook] Screenshot skipped (driver unavailable): " + e.getMessage());
            }
        }
    }
}

