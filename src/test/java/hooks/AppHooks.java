package hooks;

import io.appium.java_client.AppiumBy;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.appium.java_client.android.AndroidDriver;
import testBase.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class AppHooks {

    private static BaseClass base;
    private static AndroidDriver driver;

    @BeforeAll
    public static void setUp() throws Exception {
        base = new BaseClass();
        driver = base.initDriver();
    }

    public static AndroidDriver getDriver() {
        return driver;
    }

    /**
     * After each scenario: return to list screen or Dashboard so the next
     * scenario starts from a known state.
     *
     * Strategy (fastest-first):
     *   1. Fast-path: already on list screen or Dashboard → return immediately (0 back presses)
     *   2. Dismiss any blocking dialog (Yes, Exit / Yes, Change)
     *   3. If in search mode, one back press closes the search bar
     *   4. Press back up to 4 more times until list screen or Dashboard is detected
     *
     * All screen checks use findElements().isEmpty() — returns in ~50 ms with
     * implicitlyWait = 0. No WebDriverWait, no exceptions, no 404 Appium logs.
     */
    @After
    public void returnToListScreen(Scenario scenario) {
        if (driver == null) return;
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            hideKeyboardSilently();

            // Fast path — zero back presses needed
            if (isOnListScreen() || isOnDashboard()) return;

            // Dismiss any blocking confirmation dialog before navigating
            dismissDialog();

            // If search bar is open, one back press closes it
            if (isInSearchMode()) {
                pressBack();
                waitMs(300);
                if (isOnListScreen() || isOnDashboard()) return;
            }

            // Navigate back — at most 4 attempts
            for (int i = 1; i <= 4; i++) {
                pressBack();
                waitMs(300);
                dismissDialog();
                if (isOnListScreen() || isOnDashboard()) return;
            }

        } finally {
            // Keep implicit wait at ZERO — all checks use explicit waits or
            // ElementUtils.waitForFirst(). A non-zero implicit wait makes every
            // findElements() call slow when the element is absent.
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        }
    }

    // ── Screen detection ──────────────────────────────────────────────────────────
    // All methods use findElements().isEmpty() with implicitlyWait = 0:
    //   • Returns in ~50 ms regardless of element presence
    //   • Never throws NoSuchElementException
    //   • Produces zero HTTP 404 responses in Appium logs

    /** True when the Search icon is visible — indicates a list screen in normal mode. */
    private boolean isOnListScreen() {
        boolean found = hasElement(AppiumBy.accessibilityId("Search"));
        if (found) System.out.println("[AppHooks] On list screen");
        return found;
    }

    /** True when the Dashboard "Active Machines" label is visible. */
    private boolean isOnDashboard() {
        boolean found = hasElement(AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionContains(\"Active Machines\")"));
        if (found) System.out.println("[AppHooks] On Dashboard — stopping");
        return found;
    }

    /**
     * True when an EditText is visible on screen.
     * Save/Submit checks removed — those elements only appear inside popups,
     * never on the list screen. A single hasElement() call is sufficient.
     * Pressing Back from either search mode or an open popup navigates back
     * toward the list screen (confirmation dialogs are handled by dismissDialog).
     */
    private boolean isInSearchMode() {
        boolean found = hasElement(AppiumBy.className("android.widget.EditText"));
        if (found) System.out.println("[AppHooks] EditText visible — pressing back to exit");
        return found;
    }

    // ── Dialog handling ───────────────────────────────────────────────────────────

    /** Clicks the first visible blocking dialog button (Yes, Exit or Yes, Change), if any. */
    private void dismissDialog() {
        if (clickFirst(AppiumBy.androidUIAutomator("new UiSelector().description(\"Yes, Exit\")"))) {
            waitMs(300);
            return;
        }
        if (clickFirst(AppiumBy.androidUIAutomator("new UiSelector().description(\"Yes, Change\")"))) {
            waitMs(300);
        }
    }

    // ── Core helpers ──────────────────────────────────────────────────────────────

    /**
     * Returns true when at least one element matches the locator.
     * With implicitlyWait = 0, findElements() returns immediately — no 404, no exception.
     */
    private boolean hasElement(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    /**
     * Finds and clicks the first matching element. Returns true if clicked.
     * Safe to call when the element may not be present.
     */
    private boolean clickFirst(By locator) {
        List<WebElement> matches = driver.findElements(locator);
        if (!matches.isEmpty()) {
            matches.get(0).click();
            return true;
        }
        return false;
    }

    private void hideKeyboardSilently() {
        try { driver.hideKeyboard(); } catch (Exception ignored) { /* keyboard not shown — safe to ignore */ }
    }

    /**
     * Presses the hardware Back key via ADB — works even when UiAutomator2
     * instrumentation has crashed. Falls back to mobile:shell, then navigate().back().
     */
    private void pressBack() {
        try {
            new ProcessBuilder("adb", "shell", "input", "keyevent", "4")
                    .start().waitFor(3, TimeUnit.SECONDS);
            return;
        } catch (Exception ignored) { /* adb not on PATH — fall through to mobile:shell */ }

        try {
            Map<String, Object> args = new HashMap<>();
            args.put("command", "input");
            args.put("args", Arrays.asList("keyevent", "4"));
            driver.executeScript("mobile: shell", args);
        } catch (Exception e) {
            try { driver.navigate().back(); } catch (Exception ignored) { /* best-effort — nothing more to try */ }
        }
    }

    private void waitMs(long ms) {
        LockSupport.parkNanos(Duration.ofMillis(ms).toNanos());
    }
}
