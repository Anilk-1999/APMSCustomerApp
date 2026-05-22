package utilities;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Centralized popup synchronization and interaction utilities.
 *
 * PERFORMANCE: Every WebDriverWait polling loop disables implicit wait ONCE
 * before the loop and restores ONCE after, so each findElements() call inside
 * the lambda returns in ~50 ms instead of waiting up to 10 s.
 * Per-poll results are cached in local variables — never re-queried.
 */
public class PopupUtils {

    private final AndroidDriver driver;
    private final ElementUtils  elementUtils;

    private static final Duration POLL = Duration.ofMillis(400);

    public PopupUtils(AndroidDriver driver) {
        this.driver       = driver;
        this.elementUtils = new ElementUtils(driver);
    }

    // ═══════════════════════════════════════════════════════
    //  POPUP STATE DETECTION (instant)
    // ═══════════════════════════════════════════════════════

    public boolean isPopupOpen(String contentDesc) {
        return elementUtils.isPresentByUIAutomator(
                "new UiSelector().descriptionContains(\"" + contentDesc + "\")");
    }

    public boolean isAddActivityPopupOpen()  { return isPopupOpen("Add New Activity"); }
    public boolean isEditActivityPopupOpen() { return isPopupOpen("Edit Activity"); }
    public boolean isViewActivityPopupOpen() { return isPopupOpen("View Activity"); }

    // ═══════════════════════════════════════════════════════
    //  POLLING WAIT — OPEN
    // ═══════════════════════════════════════════════════════

    public boolean waitForPopupOpen(String contentDesc, int timeoutSecs) {
        return elementUtils.waitForPresence(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().descriptionContains(\"" + contentDesc + "\")"),
                timeoutSecs);
    }

    // ═══════════════════════════════════════════════════════
    //  POLLING WAIT — CLOSE
    // ═══════════════════════════════════════════════════════

    public boolean waitForViewPopupClose(int timeoutSecs) {
        return elementUtils.waitForAbsenceByXPath(
                "//android.view.View[@content-desc='View Activity']", timeoutSecs);
    }

    public boolean waitForEditPopupClose(int timeoutSecs) {
        return elementUtils.waitForAbsenceByAccessibility("Save", timeoutSecs);
    }

    // ═══════════════════════════════════════════════════════
    //  CLOSE ACTIONS
    // ═══════════════════════════════════════════════════════

    /**
     * Clicks the X button inside any Activity popup header.
     * Zero implicit wait for the entire polling duration.
     */
    public void clickCloseX(int timeoutSecs) {
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(Duration.ofMillis(500))
                    .until(d -> {
                        // Only look inside Activity popups — NOT inside Confirmation Alert or
                        // Status Update, because those views also contain unlabelled Buttons and
                        // clicking them would silently dismiss the dialog before the test verifies it.
                        List<WebElement> btns = d.findElements(AppiumBy.xpath(
                                "//android.view.View[@content-desc='Add Activity Group'" +
                                " or @content-desc='Edit Activity Group'" +
                                " or @content-desc='View Activity Group']" +
                                "//android.widget.Button[not(@content-desc)]"));
                        if (!btns.isEmpty()) {
                            btns.get(0).click();
                            return Boolean.TRUE;
                        }
                        // X not found — check if the popup itself is already gone
                        List<WebElement> popups = d.findElements(AppiumBy.xpath(
                                "//android.view.View[@content-desc='Add Activity Group'" +
                                " or @content-desc='Edit Activity Group'" +
                                " or @content-desc='View Activity Group']"));
                        return popups.isEmpty() ? Boolean.TRUE : null;
                    });
        } catch (Exception ignored) { /* timeout — popup already gone or X not found */ } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    /**
     * Clicks "Yes, Exit" if the Confirmation Alert appears after closing a popup.
     * Zero implicit wait — result cached per poll in local list.
     */
    public void confirmExitIfAlertShows(int timeoutSecs) {
        elementUtils.disableImplicitWait();
        int[] polls = {0};
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> {
                        List<WebElement> exits = d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().description(\"Yes, Exit\")"));
                        if (!exits.isEmpty()) {
                            exits.get(0).click();
                            return Boolean.TRUE;
                        }
                        // Wait at least 2 polls (~800 ms) before concluding no alert will appear
                        polls[0]++;
                        return polls[0] >= 2 ? Boolean.TRUE : null;
                    });
        } catch (Exception ignored) { /* timeout — no exit alert shown within window */ } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    // ═══════════════════════════════════════════════════════
    //  SUCCESS SIGNALS
    // ═══════════════════════════════════════════════════════

    /**
     * Waits until FAB OR success toast appears.
     *
     * PERFORMANCE: implicit wait disabled ONCE before the loop. Each poll checks
     * three conditions with zero wait per findElements(). Per-poll results cached
     * in local lists — no repeated lookups for the same locator within one poll.
     */
    public boolean waitForSuccessSignal(String successMsg, int timeoutSecs) {
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(POLL)
                    .until(d -> {
                        // Cache FAB check result — two locators, one cached each
                        List<WebElement> fab = d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().description(\"+ Add\")"));
                        if (!fab.isEmpty()) return Boolean.TRUE;

                        List<WebElement> fabFallback = d.findElements(AppiumBy.xpath(
                                "//*[contains(@content-desc,'Add') and @clickable='true']"));
                        if (!fabFallback.isEmpty()) return Boolean.TRUE;

                        // Cache toast check result
                        List<WebElement> toast = d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().descriptionContains(\"" + successMsg + "\")"));
                        return toast.isEmpty() ? null : Boolean.TRUE;
                    });
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public boolean waitForCreateSuccess(int timeoutSecs) {
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(POLL)
                    .until(d -> {
                        // 1. FAB exact match "+" Add
                        if (!d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().description(\"+ Add\")")).isEmpty()) return Boolean.TRUE;

                        // 2. Bottom toolbar "Add" button (no "+") — Flutter bottom nav
                        if (!d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().description(\"Add\")")).isEmpty()) return Boolean.TRUE;

                        // 3. List screen Search / Filter toolbar icon
                        if (!d.findElements(AppiumBy.accessibilityId("Search")).isEmpty()) return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.accessibilityId("Filter")).isEmpty()) return Boolean.TRUE;

                        // 4. Actual success banner — "User Created Successfully"
                        if (!d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().descriptionContains(\"Created Successfully\")")).isEmpty())
                            return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().textContains(\"Created Successfully\")")).isEmpty())
                            return Boolean.TRUE;

                        return null;
                    });
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public boolean waitForUpdateSuccess(int timeoutSecs) {
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(POLL)
                    .until(d -> {
                        // 1. Bottom toolbar "Add" button (Users list)
                        if (!d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().description(\"Add\")")).isEmpty()) return Boolean.TRUE;

                        // 2. FAB exact "+" Add
                        if (!d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().description(\"+ Add\")")).isEmpty()) return Boolean.TRUE;

                        // 3. List screen Search / Filter toolbar icon
                        if (!d.findElements(AppiumBy.accessibilityId("Search")).isEmpty()) return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.accessibilityId("Filter")).isEmpty()) return Boolean.TRUE;

                        // 4. Success banner — content-desc (Flutter semantic label)
                        if (!d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().descriptionContains(\"Updated Successfully\")")).isEmpty())
                            return Boolean.TRUE;

                        // 5. Success banner — text attribute (some Flutter toast implementations)
                        if (!d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().textContains(\"Updated Successfully\")")).isEmpty())
                            return Boolean.TRUE;

                        return null;
                    });
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    // ═══════════════════════════════════════════════════════
    //  STATUS POPUP
    // ═══════════════════════════════════════════════════════

    public boolean isStatusConfirmationOpen() {
        return elementUtils.isPresentByUIAutomator(
                "new UiSelector().description(\"Yes, Change\")");
    }

    public boolean waitForStatusConfirmation(int timeoutSecs) {
        return elementUtils.waitForPresenceByUIAutomator(
                "new UiSelector().description(\"Yes, Change\")", timeoutSecs);
    }
}
