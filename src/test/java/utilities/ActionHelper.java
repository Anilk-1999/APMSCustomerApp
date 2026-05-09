package utilities;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Reusable UI-action helpers: tap, type, toggle, getText.
 * Always waits before acting — no raw element.click() without wait.
 */
public class ActionHelper {

    private final AndroidDriver driver;
    private final WaitHelper    waitHelper;

    public ActionHelper(AndroidDriver driver) {
        this.driver    = driver;
        this.waitHelper = new WaitHelper(driver);
    }

    // ── Tap / Click ───────────────────────────────────────────────────────────

    public void tap(WebElement element) {
        waitHelper.waitForClickability(element);
        element.click();
    }

    // ── Text input ────────────────────────────────────────────────────────────

    public void clearAndType(WebElement element, String text) {
        waitHelper.waitForVisibility(element);
        element.click();
        // After clicking a Flutter text field, wait until it accepts clear() —
        // this replaces Thread.sleep and correctly handles the Flutter render cycle.
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(300))
                .until(d -> {
                    try { element.clear(); return true; }
                    catch (Exception e) { return false; }
                });
        element.sendKeys(text);
    }

    public void typeText(WebElement element, String text) {
        waitHelper.waitForVisibility(element);
        element.sendKeys(text);
    }

    public void clearField(WebElement element) {
        waitHelper.waitForVisibility(element);
        element.clear();
    }

    // ── Read text ─────────────────────────────────────────────────────────────

    public String getText(WebElement element) {
        waitHelper.waitForVisibility(element);
        return element.getText();
    }

    // ── Toggle ────────────────────────────────────────────────────────────────

    /**
     * Turns toggle ON if it isn't already.
     */
    public void enableToggle(WebElement toggle) {
        waitHelper.waitForVisibility(toggle);
        if (!"true".equals(toggle.getAttribute("checked"))) {
            toggle.click();
        }
    }

    /**
     * Turns toggle OFF if it isn't already.
     */
    public void disableToggle(WebElement toggle) {
        waitHelper.waitForVisibility(toggle);
        if ("true".equals(toggle.getAttribute("checked"))) {
            toggle.click();
        }
    }

    /**
     * Simply flips the toggle (use when you don't care about current state).
     */
    public void clickToggle(WebElement toggle) {
        waitHelper.waitForClickability(toggle);
        toggle.click();
    }

    public boolean isToggleOn(WebElement toggle) {
        waitHelper.waitForVisibility(toggle);
        return "true".equals(toggle.getAttribute("checked"));
    }

    // ── State checks ──────────────────────────────────────────────────────────

    public boolean isDisplayed(WebElement element) {
        return waitHelper.isElementPresent(element);
    }

    public boolean isEnabled(WebElement element) {
        try {
            waitHelper.waitForVisibility(element, 5);
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
}
