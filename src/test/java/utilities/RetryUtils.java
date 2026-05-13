package utilities;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

/**
 * Retry utilities for flaky Flutter/Appium interactions.
 *
 * Use when:
 *  - An element appears intermittently (stale / Flutter re-compose)
 *  - A click does not register on first attempt (Flutter tap latency)
 *  - An action needs to be verified before continuing
 */
public class RetryUtils {

    private final AndroidDriver driver;
    private final ElementUtils  elementUtils;

    private static final Duration POLL = Duration.ofMillis(300);

    public RetryUtils(AndroidDriver driver) {
        this.driver       = driver;
        this.elementUtils = new ElementUtils(driver);
    }

    // ═══════════════════════════════════════════════════════
    //  RETRY CLICK
    // ═══════════════════════════════════════════════════════

    /**
     * Finds and clicks the first matching element, retrying on stale/absent.
     * Returns true if at least one click was performed within timeoutSecs.
     */
    public boolean retryClick(By locator, int timeoutSecs) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(POLL)
                    .until(d -> {
                        List<WebElement> found = d.findElements(locator);
                        if (found.isEmpty()) return null;
                        found.get(0).click();
                        return Boolean.TRUE;
                    });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clicks the element and verifies it responded by checking the given
     * confirmation locator afterwards. Retries up to maxAttempts times.
     */
    public boolean retryClickUntilConfirmed(By clickTarget, By confirmLocator,
                                             int maxAttempts, int confirmTimeoutSecs) {
        for (int i = 0; i < maxAttempts; i++) {
            List<WebElement> targets = driver.findElements(clickTarget);
            if (!targets.isEmpty()) {
                try { targets.get(0).click(); } catch (Exception ignored) {}
            }
            if (elementUtils.waitForPresence(confirmLocator, confirmTimeoutSecs)) return true;
        }
        return false;
    }

    // ═══════════════════════════════════════════════════════
    //  RETRY FIND
    // ═══════════════════════════════════════════════════════

    /**
     * Polls until the element is found, refreshing from DOM each time.
     * Avoids StaleElementReferenceException from cached references.
     */
    public WebElement retryFind(By locator, int timeoutSecs) {
        return elementUtils.waitForFirst(locator, timeoutSecs);
    }

    // ═══════════════════════════════════════════════════════
    //  RETRY ARBITRARY ACTION
    // ═══════════════════════════════════════════════════════

    /**
     * Retries a boolean-returning action up to maxAttempts times.
     * Stops as soon as the action returns true.
     */
    public boolean retryAction(Supplier<Boolean> action, int maxAttempts) {
        for (int i = 0; i < maxAttempts; i++) {
            try {
                if (Boolean.TRUE.equals(action.get())) return true;
            } catch (Exception ignored) {}
        }
        return false;
    }

    /**
     * Retries an action with a small delay (via polling WebDriverWait).
     * Use when the action itself throws and you want structured retry.
     */
    public <T> T retryUntilNonNull(java.util.function.Function<AndroidDriver, T> fn,
                                    int timeoutSecs) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(POLL)
                    .until(d -> fn.apply((AndroidDriver) d));
        } catch (Exception e) {
            return null;
        }
    }
}
