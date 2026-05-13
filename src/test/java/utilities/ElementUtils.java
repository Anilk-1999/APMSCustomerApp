package utilities;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Zero-implicit-wait element utilities.
 *
 * ROOT CAUSE CONTEXT:
 *  AppHooks.returnToListScreen() restores implicit wait to 10 s after every
 *  scenario. Without explicit management here, every findElements() call waits
 *  up to 10 s before returning an empty list — making state polls (isPresent,
 *  isSearchBarOpen, getRecordByName) catastrophically slow.
 *
 * DESIGN:
 *  - disableImplicitWait() / restoreImplicitWait() are the primitives.
 *  - All public methods call them in a BATCH (one disable, one restore per
 *    method call — NOT one pair per individual findElements() call).
 *  - Callers that need multiple related checks should call disableImplicitWait()
 *    once, do all findElements() calls directly, then restoreImplicitWait().
 *  - This keeps Appium HTTP overhead at 2 extra calls per method, not 2n.
 *
 * RULE: Never call findElements() without FIRST calling disableImplicitWait().
 */
public class ElementUtils {

    private final AndroidDriver driver;

    // Framework baseline — always zero. AppHooks keeps implicit wait at 0
    // throughout the session; all waiting is done via explicit WebDriverWait.
    public static final Duration FRAMEWORK_WAIT = Duration.ZERO;
    public static final Duration POLL_FAST       = Duration.ofMillis(300);
    public static final Duration POLL_STD        = Duration.ofMillis(400);

    public ElementUtils(AndroidDriver driver) {
        this.driver = driver;
    }

    // ═══════════════════════════════════════════════════════
    //  IMPLICIT WAIT MANAGEMENT
    // ═══════════════════════════════════════════════════════

    /**
     * Disables Appium server-side implicit wait.
     * Call ONCE before a batch of findElements() checks.
     * Each findElements() call returns immediately (~50 ms) with zero implicit wait.
     */
    public void disableImplicitWait() {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
    }

    /**
     * Restores implicit wait to the framework baseline (10 s).
     * Call ONCE after a batch of findElements() checks completes.
     */
    public void restoreImplicitWait() {
        driver.manage().timeouts().implicitlyWait(FRAMEWORK_WAIT);
    }

    // ═══════════════════════════════════════════════════════
    //  ZERO-WAIT PRESENCE CHECKS
    // ═══════════════════════════════════════════════════════

    /**
     * Instant presence check — guaranteed zero implicit wait.
     * Disable → findElements → restore. One round-trip to the Appium server.
     * Use for isolated checks. For batched checks, call disableImplicitWait()
     * once before the group and restoreImplicitWait() after.
     */
    public boolean isElementPresent(By locator) {
        disableImplicitWait();
        try {
            return !driver.findElements(locator).isEmpty();
        } finally {
            restoreImplicitWait();
        }
    }

    // Named aliases — backed by the zero-wait isElementPresent().
    public boolean isPresent(By locator) {
        return isElementPresent(locator);
    }

    public boolean isPresentByAccessibility(String id) {
        return isElementPresent(AppiumBy.accessibilityId(id));
    }

    public boolean isPresentByUIAutomator(String selector) {
        return isElementPresent(AppiumBy.androidUIAutomator(selector));
    }

    public boolean isPresentByXPath(String xpath) {
        return isElementPresent(AppiumBy.xpath(xpath));
    }

    // ═══════════════════════════════════════════════════════
    //  INSTANT ELEMENT RETRIEVAL (zero wait)
    // ═══════════════════════════════════════════════════════

    /**
     * Returns the first matching element or null — zero implicit wait.
     * Result cached in local variable; one round-trip to Appium server.
     */
    public WebElement firstOrNull(By locator) {
        disableImplicitWait();
        try {
            List<WebElement> list = driver.findElements(locator);
            return list.isEmpty() ? null : list.get(0);
        } finally {
            restoreImplicitWait();
        }
    }

    public WebElement firstOrNullByAccessibility(String id) {
        return firstOrNull(AppiumBy.accessibilityId(id));
    }

    public WebElement firstOrNullByUIAutomator(String selector) {
        return firstOrNull(AppiumBy.androidUIAutomator(selector));
    }

    // ═══════════════════════════════════════════════════════
    //  POLLING WAIT — FOR PRESENCE
    // ═══════════════════════════════════════════════════════

    /**
     * Polls until the first match appears. Returns element or null on timeout.
     *
     * Implicit wait disabled ONCE before the polling loop starts so every
     * findElements() inside the lambda returns in ~50 ms. Restored ONCE after.
     * This keeps the poll interval honest (300 ms) regardless of element absence.
     */
    public WebElement waitForFirst(By locator, int timeoutSecs) {
        disableImplicitWait();
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(POLL_FAST)
                    .until(d -> {
                        // Cached per-poll: result stored in found, not re-queried.
                        List<WebElement> found = d.findElements(locator);
                        return found.isEmpty() ? null : found.get(0);
                    });
        } catch (Exception e) {
            return null;
        } finally {
            restoreImplicitWait();
        }
    }

    public WebElement waitForFirstByAccessibility(String id, int timeoutSecs) {
        return waitForFirst(AppiumBy.accessibilityId(id), timeoutSecs);
    }

    public WebElement waitForFirstByUIAutomator(String selector, int timeoutSecs) {
        return waitForFirst(AppiumBy.androidUIAutomator(selector), timeoutSecs);
    }

    /** Returns true when the element appears within timeoutSecs. */
    public boolean waitForPresence(By locator, int timeoutSecs) {
        disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(POLL_STD)
                    .until(d -> d.findElements(locator).isEmpty() ? null : Boolean.TRUE);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            restoreImplicitWait();
        }
    }

    public boolean waitForPresenceByAccessibility(String id, int timeoutSecs) {
        return waitForPresence(AppiumBy.accessibilityId(id), timeoutSecs);
    }

    public boolean waitForPresenceByUIAutomator(String selector, int timeoutSecs) {
        return waitForPresence(AppiumBy.androidUIAutomator(selector), timeoutSecs);
    }

    // ═══════════════════════════════════════════════════════
    //  POLLING WAIT — FOR ABSENCE
    // ═══════════════════════════════════════════════════════

    /** Returns true when the locator matches nothing within timeoutSecs. */
    public boolean waitForAbsence(By locator, int timeoutSecs) {
        disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(POLL_STD)
                    .until(d -> d.findElements(locator).isEmpty() ? Boolean.TRUE : null);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            restoreImplicitWait();
        }
    }

    public boolean waitForAbsenceByAccessibility(String id, int timeoutSecs) {
        return waitForAbsence(AppiumBy.accessibilityId(id), timeoutSecs);
    }

    public boolean waitForAbsenceByXPath(String xpath, int timeoutSecs) {
        return waitForAbsence(AppiumBy.xpath(xpath), timeoutSecs);
    }

    // ═══════════════════════════════════════════════════════
    //  POLLING CLICK
    // ═══════════════════════════════════════════════════════

    /**
     * Polls until the element appears, then clicks it. Returns true on success.
     * Zero implicit wait for the entire polling duration.
     */
    public boolean clickWhenFound(By locator, int timeoutSecs) {
        disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(POLL_FAST)
                    .until(d -> {
                        List<WebElement> found = d.findElements(locator);
                        if (found.isEmpty()) return null;
                        found.get(0).click();
                        return Boolean.TRUE;
                    });
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            restoreImplicitWait();
        }
    }

    public boolean clickWhenFoundByAccessibility(String id, int timeoutSecs) {
        return clickWhenFound(AppiumBy.accessibilityId(id), timeoutSecs);
    }

    public boolean clickWhenFoundByUIAutomator(String selector, int timeoutSecs) {
        return clickWhenFound(AppiumBy.androidUIAutomator(selector), timeoutSecs);
    }
}
