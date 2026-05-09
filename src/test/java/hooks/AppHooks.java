package hooks;

import io.appium.java_client.AppiumBy;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.appium.java_client.android.AndroidDriver;
import testBase.BaseClass;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
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
     * After each scenario: close any open popup/dialog and navigate back to either:
     *   (a) the module list screen  → next scenario skips Background navigation entirely
     *   (b) the Dashboard (Active Machines) → next scenario runs Background navigation once
     *
     * Search-mode is handled explicitly: one back press exits search (reveals the Search icon),
     * then the normal check detects the list screen and stops. This prevents the next scenario
     * from starting in search mode where button.instance(0) is not the profile icon.
     *
     * Back is pressed at most 5 times — enough to close a popup + exit search + navigate back.
     * The loop stops immediately when the list screen OR Dashboard is detected.
     */
    @After
    public void returnToListScreen(Scenario scenario) {
        if (driver == null) return;
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            // Hide keyboard first — Back #1 would only dismiss keyboard otherwise.
            try { driver.hideKeyboard(); } catch (Exception ignored) { /* keyboard not shown — safe to ignore */ }
            dismissAnyOpenDialog();

            // If search bar is open, press back once to exit search mode.
            // This reveals the Search icon so isOnListScreen() can detect the list screen.
            if (isInSearchMode()) {
                System.out.println("[AppHooks] In search mode — pressing back to exit search");
                pressBack();
                waitMs(600);
            }

            if (isOnListScreen() || isOnDashboard()) { waitMs(800); return; }

            for (int attempt = 1; attempt <= 5; attempt++) {
                System.out.println("[AppHooks] Back press attempt " + attempt + " of 5");
                pressBack();
                waitMs(700);
                dismissAnyOpenDialog();
                if (isOnListScreen() || isOnDashboard()) { waitMs(800); return; }
            }

            // Last resort: reactivate the app if still stuck after 5 backs
            try { driver.activateApp("com.apms.ai"); } catch (Exception e) { /* best-effort restart */ }
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    /**
     * Returns true when the current screen is a module list screen in normal mode
     * (Search icon visible). Does NOT match search mode — search mode is handled
     * separately by isInSearchMode() before this is called.
     */
    private boolean isOnListScreen() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> {
                        try {
                            d.findElement(AppiumBy.accessibilityId("Search"));
                            return Boolean.TRUE;
                        } catch (Exception e) { return null; }
                    });
            System.out.println("[AppHooks] On list screen (Search icon visible)");
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Returns true when the search bar is open (EditText visible, no Add/Edit popup).
     * Used to press back once to exit search mode before the main list-screen check,
     * so the Search icon becomes visible again and isOnListScreen() can detect it.
     */
    private boolean isInSearchMode() {
        try {
            driver.findElement(AppiumBy.className("android.widget.EditText"));
            // Distinguish search bar from Add/Edit popup — popups have a Submit or Save button
            try { driver.findElement(AppiumBy.accessibilityId("Submit")); return false; } catch (Exception ignored) { /* no Submit */ }
            try { driver.findElement(AppiumBy.accessibilityId("Save"));   return false; } catch (Exception ignored) { /* no Save   */ }
            System.out.println("[AppHooks] Detected search mode (EditText without popup)");
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Presses the hardware Back key via ADB — works even when UiAutomator2
     * instrumentation has crashed. Falls back to mobile:shell, then navigate().back().
     */
    private void pressBack() {
        try {
            new ProcessBuilder("adb", "shell", "input", "keyevent", "4")
                    .start()
                    .waitFor(3, TimeUnit.SECONDS);
            return;
        } catch (Exception e) { /* adb not on PATH — try mobile:shell next */ }

        try {
            Map<String, Object> args = new HashMap<>();
            args.put("command", "input");
            args.put("args", Arrays.asList("keyevent", "4"));
            driver.executeScript("mobile: shell", args);
        } catch (Exception e) {
            try { driver.navigate().back(); } catch (Exception ex) { /* best-effort */ }
        }
    }

    /**
     * Returns true when the current screen is the Dashboard (Active Machines).
     * Stops back-navigation so the app is never pressed past the Dashboard.
     */
    private boolean isOnDashboard() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(2))
                    .pollingEvery(Duration.ofMillis(500))
                    .until(d -> {
                        try {
                            d.findElement(AppiumBy.androidUIAutomator(
                                    "new UiSelector().descriptionContains(\"Active Machines\")"));
                            return Boolean.TRUE;
                        } catch (Exception e) { return null; }
                    });
            System.out.println("[AppHooks] On Dashboard — stopping back navigation");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Closes any blocking dialog so back-navigation can proceed cleanly:
     *   - Unsaved-changes alert  → clicks "Yes, Exit"
     *   - Status-change popup    → clicks "Yes, Change"
     */
    private void dismissAnyOpenDialog() {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().description(\"Yes, Exit\")")).click();
            waitMs(400);
            return;
        } catch (Exception e) { /* no unsaved-changes dialog present */ }

        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().description(\"Yes, Change\")")).click();
            waitMs(400);
        } catch (Exception e) { /* no status-change dialog present */ }
    }

    private void waitMs(long ms) {
        LockSupport.parkNanos(Duration.ofMillis(ms).toNanos());
    }
}
