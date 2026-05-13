package utilities;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Flutter-specific stability utilities for Appium on Android.
 *
 * PERFORMANCE: Every polling loop disables implicit wait ONCE before the
 * WebDriverWait starts and restores ONCE in the finally block. This ensures
 * each findElements() call inside a lambda returns in ~50 ms regardless of
 * element absence, instead of waiting up to 10 s per call.
 *
 * Per-poll results are cached in local variables — no repeated findElements()
 * for the same locator within one poll cycle.
 */
public class FlutterUtils {

    private final AndroidDriver driver;
    private final ElementUtils  elementUtils;

    private static final String FAB_UIAUTOMATOR = "new UiSelector().description(\"+ Add\")";
    private static final String FAB_XPATH       = "//*[contains(@content-desc,'Add') and @clickable='true']";

    private static final Duration POLL = Duration.ofMillis(400);

    public FlutterUtils(AndroidDriver driver) {
        this.driver       = driver;
        this.elementUtils = new ElementUtils(driver);
    }

    // ═══════════════════════════════════════════════════════
    //  ADD FAB
    // ═══════════════════════════════════════════════════════

    /**
     * Instant FAB presence check.
     * BATCH: both locators checked under one disable/restore pair.
     */
    public boolean isFabVisible() {
        elementUtils.disableImplicitWait();
        try {
            // Cache UIAutomator result — if found, skip XPath call entirely
            if (!driver.findElements(AppiumBy.androidUIAutomator(FAB_UIAUTOMATOR)).isEmpty())
                return true;
            return !driver.findElements(AppiumBy.xpath(FAB_XPATH)).isEmpty();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    /**
     * Polls until the Add FAB appears.
     * Implicit wait disabled for the entire polling duration.
     */
    public boolean waitForFab(int timeoutSecs) {
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(POLL)
                    .until(d -> {
                        // Cache per-poll results
                        List<WebElement> fab = d.findElements(
                                AppiumBy.androidUIAutomator(FAB_UIAUTOMATOR));
                        if (!fab.isEmpty()) return Boolean.TRUE;
                        List<WebElement> fallback = d.findElements(AppiumBy.xpath(FAB_XPATH));
                        return fallback.isEmpty() ? null : Boolean.TRUE;
                    });
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    /**
     * Clicks the Add FAB, polling until it appears and responds.
     * Implicit wait disabled for the entire polling duration.
     */
    public void clickFab(int timeoutSecs) {
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(Duration.ofMillis(300))
                    .until(d -> {
                        // Cache UIAutomator result first (faster than XPath)
                        List<WebElement> fab = d.findElements(
                                AppiumBy.androidUIAutomator(FAB_UIAUTOMATOR));
                        if (!fab.isEmpty()) { fab.get(0).click(); return Boolean.TRUE; }
                        List<WebElement> fallback = d.findElements(AppiumBy.xpath(FAB_XPATH));
                        if (!fallback.isEmpty()) { fallback.get(0).click(); return Boolean.TRUE; }
                        return null;
                    });
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    // ═══════════════════════════════════════════════════════
    //  FLUTTER ELEMENT INTERACTION
    // ═══════════════════════════════════════════════════════

    public void clickFlutterElement(String accessibilityId, int timeoutSecs) {
        elementUtils.clickWhenFoundByAccessibility(accessibilityId, timeoutSecs);
    }

    public WebElement getFreshToggle(String xpath) {
        return elementUtils.waitForFirst(AppiumBy.xpath(xpath), 10);
    }

    // ═══════════════════════════════════════════════════════
    //  TOGGLE STATE
    // ═══════════════════════════════════════════════════════

    public boolean getToggleState(WebElement toggleSwitch) {
        try {
            String checked = toggleSwitch.getAttribute("checked");
            if (checked != null) return Boolean.parseBoolean(checked);
            String desc = toggleSwitch.getAttribute("content-desc");
            return desc != null && (desc.contains("true") || desc.equalsIgnoreCase("on"));
        } catch (Exception e) {
            return false;
        }
    }

    public void enableToggle(WebElement toggle) {
        if (!getToggleState(toggle)) toggle.click();
    }

    public void disableToggle(WebElement toggle) {
        if (getToggleState(toggle)) toggle.click();
    }

    // ═══════════════════════════════════════════════════════
    //  RETURN-TO-LIST DETECTION
    // ═══════════════════════════════════════════════════════

    /**
     * Waits for FAB OR success toast — accepts either as "returned to list" signal.
     *
     * PERFORMANCE: implicit wait disabled ONCE before the loop. Each poll caches
     * its findElements() results in local variables — no repeated server calls
     * for the same locator within one poll cycle.
     */
    public boolean waitForReturnToList(String successToastContains, int timeoutSecs) {
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(POLL)
                    .until(d -> {
                        // Cache FAB check — UIAutomator first (faster than XPath)
                        List<WebElement> fab = d.findElements(
                                AppiumBy.androidUIAutomator(FAB_UIAUTOMATOR));
                        if (!fab.isEmpty()) return Boolean.TRUE;

                        List<WebElement> fabFallback = d.findElements(AppiumBy.xpath(FAB_XPATH));
                        if (!fabFallback.isEmpty()) return Boolean.TRUE;

                        // Cache toast check
                        List<WebElement> toast = d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().descriptionContains(\""
                                        + successToastContains + "\")"));
                        return toast.isEmpty() ? null : Boolean.TRUE;
                    });
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    // ═══════════════════════════════════════════════════════
    //  VIEW-POPUP DETECTION
    // ═══════════════════════════════════════════════════════

    public boolean isViewField(By locator) {
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> fields = driver.findElements(locator);
            return !fields.isEmpty() && !fields.get(0).isEnabled();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public boolean isViewPopupContext() {
        elementUtils.disableImplicitWait();
        try {
            // Both checks in one zero-wait block — 2 toggle calls, not 4
            boolean savePresent   = !driver.findElements(AppiumBy.accessibilityId("Save")).isEmpty();
            boolean submitPresent = !driver.findElements(AppiumBy.accessibilityId("Submit")).isEmpty();
            return !savePresent && !submitPresent;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }
}
