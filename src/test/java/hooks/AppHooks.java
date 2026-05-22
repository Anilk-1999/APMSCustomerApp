package hooks;

import io.appium.java_client.AppiumBy;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.appium.java_client.android.AndroidDriver;
import testBase.BaseClass;
import utilities.SearchUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        } catch (Exception e) {
            // Session is completely dead — restart it so subsequent scenarios can run
            System.out.println("[AppHooks] Driver session dead before cleanup — restarting: " + e.getMessage());
            restartDriverSession();
            return;
        }
        try {
            hideKeyboardSilently();

            // If the search bar is open, close it via the X button (never Back — Back navigates
            // away from the list screen in Flutter apps).
            if (isInSearchMode()) {
                System.out.println("[AppHooks] Search bar open — closing via X button");
                new SearchUtils(driver).ensureSearchClosed();
            }

            // Fast path — zero back presses needed
            if (isOnListScreen() || isOnDashboard() || isOnLoginScreen()) return;

            // Dismiss any blocking confirmation dialog before navigating
            dismissDialog();

            // Navigate back — at most 4 attempts
            for (int i = 1; i <= 4; i++) {
                pressBack();
                pollUntilOnKnownScreen(3);
                dismissDialog();
                if (isOnListScreen() || isOnDashboard()) return;
            }

        } catch (Exception e) {
            // UiAutomator2 crashed mid-cleanup — restart session to unblock subsequent scenarios
            System.out.println("[AppHooks] Cleanup failed (UiAutomator2 crash) — restarting session: " + e.getMessage());
            restartDriverSession();
        } finally {
            if (driver != null) {
                try { driver.manage().timeouts().implicitlyWait(Duration.ZERO); } catch (Exception ignored) { /* session gone */ }
            }
        }
    }

    /**
     * Quits the dead Appium session and creates a fresh one.
     * The next scenario will start from the app launch screen (login screen).
     * Background steps that call clickOnProfileIcon will fail fast with a clear
     * TimeoutException rather than cascading UiAutomator2 "instrumentation not running" errors.
     */
    private void restartDriverSession() {
        System.out.println("[AppHooks] Restarting Appium session...");
        try { driver.quit(); } catch (Exception ignored) { /* best-effort quit */ }
        // quitDriver() nulls base.driver so the next initDriver() creates a truly fresh session
        base.quitDriver();
        try {
            driver = base.initDriver();
            System.out.println("[AppHooks] Appium session restarted successfully.");
        } catch (Exception e) {
            System.out.println("[AppHooks] Session restart failed: " + e.getMessage());
            driver = null;
        }
    }

    /** Polls every 300 ms up to {@code timeoutSecs} until list screen or Dashboard appears. */
    private void pollUntilOnKnownScreen(int timeoutSecs) {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(Duration.ofMillis(300))
                    .ignoring(Exception.class)
                    .until(d -> isOnListScreen() || isOnDashboard());
        } catch (Exception ignored) { /* timed out — caller will check state */ }
    }

    // ── Screen detection ──────────────────────────────────────────────────────────
    // All methods use findElements().isEmpty() with implicitlyWait = 0:
    //   • Returns in ~50 ms regardless of element presence
    //   • Never throws NoSuchElementException
    //   • Produces zero HTTP 404 responses in Appium logs

    /**
     * True when we are on any list screen.
     * Covers two modes:
     *  - Normal mode:  Search button is visible in the bottom bar.
     *  - Search mode:  Search button is replaced by the search input, but Filter
     *                  button remains visible in the bottom bar — still a list screen.
     */
    private boolean isOnListScreen() {
        if (hasElement(AppiumBy.accessibilityId("Search"))) {
            System.out.println("[AppHooks] On list screen (Search button visible)");
            return true;
        }
        if (hasElement(AppiumBy.accessibilityId("Filter"))) {
            System.out.println("[AppHooks] On list screen in search mode (Filter button visible)");
            return true;
        }
        // FAB ("+ Add") is always visible on list screens — catches the case where Flutter
        // has closed the search bar but the Search button hasn't re-rendered yet.
        if (hasElement(AppiumBy.androidUIAutomator("new UiSelector().description(\"+ Add\")"))) {
            System.out.println("[AppHooks] On list screen (Add FAB visible)");
            return true;
        }
        return false;
    }

    /** True when the login screen "Login to your account" label is visible. */
    private boolean isOnLoginScreen() {
        boolean found = hasElement(AppiumBy.accessibilityId("Login to your account"));
        if (found) System.out.println("[AppHooks] On login screen — stopping");
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
     * True ONLY when the search bar on a list screen is open.
     * Detects by: EditText visible AND Sort/Filter button also visible.
     * Sort/Filter appear on every list screen even when the search input is open,
     * but NOT on the login form, popup forms, or the Dashboard — so this
     * check is safe across all screens and won't match login/creation forms.
     */
    private boolean isInSearchMode() {
        if (!hasElement(AppiumBy.className("android.widget.EditText"))) return false;
        boolean onList = hasElement(AppiumBy.accessibilityId("Filter"))
                      || hasElement(AppiumBy.accessibilityId("Sort"));
        if (onList) System.out.println("[AppHooks] Search bar open on list screen — hiding keyboard");
        return onList;
    }

    // ── Dialog handling ───────────────────────────────────────────────────────────

    /** Clicks the first visible blocking dialog button (Yes, Exit or Yes, Change), if any. */
    private void dismissDialog() {
        By exitBtn   = AppiumBy.androidUIAutomator("new UiSelector().description(\"Yes, Exit\")");
        By changeBtn = AppiumBy.androidUIAutomator("new UiSelector().description(\"Yes, Change\")");
        if (clickFirst(exitBtn)) {
            // Poll until button is gone (dialog dismissed) — no fixed sleep
            try {
                new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(3))
                        .pollingEvery(Duration.ofMillis(200))
                        .ignoring(Exception.class)
                        .until(d -> d.findElements(exitBtn).isEmpty());
            } catch (Exception ignored) {}
            return;
        }
        if (clickFirst(changeBtn)) {
            try {
                new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(3))
                        .pollingEvery(Duration.ofMillis(200))
                        .ignoring(Exception.class)
                        .until(d -> d.findElements(changeBtn).isEmpty());
            } catch (Exception ignored) {}
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

}
