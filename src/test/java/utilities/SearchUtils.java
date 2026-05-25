package utilities;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Centralized search-bar operations for list screens.
 *
 * PERFORMANCE DESIGN:
 *  - isSearchBarOpen(): ONE disable + ONE restore for 3 findElements() checks
 *    (not 3 separate disable/restore pairs = 6 extra Appium calls).
 *  - searchRecord(): gets EditText reference ONCE, reuses for click/clear/sendKeys.
 *  - getRecordByName(): delegates to elementUtils.waitForFirst() which manages
 *    zero-wait for the entire polling loop automatically.
 *  - All direct driver.findElements() calls are preceded by disableImplicitWait().
 */
public class SearchUtils {

    private final AndroidDriver  driver;
    private final ElementUtils   elementUtils;

    // Search bar = EditText visible WITHOUT a popup (Submit/Save absent)
    private static final By SEARCH_BAR    = By.className("android.widget.EditText");
    private static final By SUBMIT_BUTTON = AppiumBy.accessibilityId("Submit");
    private static final By SAVE_BUTTON   = AppiumBy.accessibilityId("Save");

    // X button on the right side of the search field.
    // Anchored relative to the EditText so it can never accidentally match a list record.
    private static final By SEARCH_CLOSE_X = AppiumBy.xpath(
            "//android.widget.EditText/following-sibling::android.view.View");

    // Normal list-screen signals
    private static final By FAB_ADD    = AppiumBy.androidUIAutomator("new UiSelector().description(\"+ Add\")");
    private static final By BTN_FILTER = AppiumBy.accessibilityId("Filter");
    private static final By BTN_SORT   = AppiumBy.accessibilityId("Sort");
    private static final By BTN_SEARCH = AppiumBy.accessibilityId("Search");

    public SearchUtils(AndroidDriver driver) {
        this.driver       = driver;
        this.elementUtils = new ElementUtils(driver);
    }

    // ═══════════════════════════════════════════════════════
    //  STATE DETECTION
    // ═══════════════════════════════════════════════════════

    /**
     * True when the search bar (EditText) is visible and no popup is open.
     *
     * BATCH PATTERN: implicit wait disabled ONCE before all three findElements()
     * calls, restored ONCE after — 2 extra Appium calls total, not 6.
     * Each findElements() returns in ~50 ms with zero implicit wait.
     * Short-circuit: if EditText absent, Save/Submit checks are skipped entirely.
     */
    public boolean isSearchBarOpen() {
        elementUtils.disableImplicitWait();
        try {
            // Result cached in local list — not re-queried
            List<WebElement> searchBars = driver.findElements(SEARCH_BAR);
            if (searchBars.isEmpty()) return false;

            List<WebElement> submits = driver.findElements(SUBMIT_BUTTON);
            if (!submits.isEmpty()) return false;

            List<WebElement> saves = driver.findElements(SAVE_BUTTON);
            return saves.isEmpty();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    // ═══════════════════════════════════════════════════════
    //  OPEN / CLOSE
    // ═══════════════════════════════════════════════════════

    /**
     * Clicks the Search icon. Skips if the bar is already open.
     * One batch zero-wait block for the presence check + click.
     */
    public void openSearch() {
        elementUtils.disableImplicitWait();
        try {
            // Check search bar state in same zero-wait block as the click
            List<WebElement> searchBars = driver.findElements(SEARCH_BAR);
            if (!searchBars.isEmpty()) {
                // Bar already open — no need to click Search icon
                List<WebElement> submits = driver.findElements(SUBMIT_BUTTON);
                List<WebElement> saves   = driver.findElements(SAVE_BUTTON);
                if (submits.isEmpty() && saves.isEmpty()) return; // already in search mode
            }
            // Click the Search icon
            List<WebElement> icons = driver.findElements(AppiumBy.accessibilityId("Search"));
            if (!icons.isEmpty()) icons.get(0).click();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    /**
     * Exits search mode without navigating away from the current list screen.
     *
     * Strategy:
     *   1. Clear the EditText content first — an empty field is the precondition for Flutter
     *      to close ONLY the search overlay (not the whole list screen route) on Back.
     *   2. Hide keyboard.
     *   3. Wait 2 s — some Flutter builds auto-collapse search when the field is empty
     *      and the keyboard is dismissed.
     *   4. If still open: press Back.  With an empty field, Flutter's SearchDelegate /
     *      SearchBar calls close() which pops ONLY the search route, staying on the list.
     *   5. Wait up to 3 s for the EditText to disappear.
     */
    public void exitSearchMode() {
        if (!isSearchBarOpen()) return;
        // Clear field first — empty field ensures Back closes ONLY the search overlay, not the list route
        WebElement input = elementUtils.waitForFirst(SEARCH_BAR, 3);
        if (input != null) try { input.clear(); } catch (Exception ignored) {}
        try { driver.hideKeyboard(); } catch (Exception ignored) {}
        // Press Back to close search overlay and wait for it to disappear
        try { driver.pressKey(new KeyEvent(AndroidKey.BACK)); } catch (Exception e) { /* best-effort — Back key unavailable */ }
        elementUtils.waitForAbsence(SEARCH_BAR, 5);
    }

    // ═══════════════════════════════════════════════════════
    //  GLOBAL SEARCH CLOSE — X BUTTON (all modules except Activity)
    // ═══════════════════════════════════════════════════════

    /** True when the search bar is open on a list screen (alias for isSearchBarOpen). */
    public boolean isSearchOpen() {
        return isSearchBarOpen();
    }

    /**
     * Clicks the X button to close the search bar.
     * Flutter X button first clears text, then on a second click closes the bar.
     * Handles both cases: click once, check if still open, click again if needed.
     */
    public void clickSearchCloseXIfOpen() {
        if (!isSearchBarOpen()) return;
        clickSearchCloseX();
        // Flutter's X button clears text on the first click, then closes on the second.
        if (isSearchBarOpen()) clickSearchCloseX();
        // Bar should close within one Flutter frame; 3 s is a generous upper bound.
        elementUtils.waitForAbsence(SEARCH_BAR, 3);
    }

    /**
     * Ensures search is closed — delegates to clickSearchCloseXIfOpen for consistency.
     * Called from "search field should be closed" step as a safety retry.
     */
    public void ensureSearchClosed() {
        clickSearchCloseXIfOpen();
    }

    /**
     * Polls up to 5 s for the list screen to be in normal (non-search) state.
     * Returns true when FAB, Filter, Sort, Search, or "Add" button is visible.
     * Users module uses an "Add" bottom-bar button (not "+ Add" FAB).
     */
    public boolean verifyModuleListNormalState() {
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .pollingEvery(Duration.ofMillis(300))
                    .until(d -> {
                        if (!d.findElements(FAB_ADD).isEmpty())                               return Boolean.TRUE;
                        if (!d.findElements(BTN_FILTER).isEmpty())                            return Boolean.TRUE;
                        if (!d.findElements(BTN_SORT).isEmpty())                              return Boolean.TRUE;
                        if (!d.findElements(BTN_SEARCH).isEmpty())                            return Boolean.TRUE;
                        // Users list uses "Add" in the bottom toolbar (no "+" prefix)
                        if (!d.findElements(AppiumBy.accessibilityId("Add")).isEmpty())       return Boolean.TRUE;
                        return null;
                    });
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    /**
     * Closes the search bar (if open) then verifies the module list is in normal state.
     * Use at the start of a step that expects to be on a clean list screen.
     */
    public void ensureModuleListReady() {
        ensureSearchClosed();
        verifyModuleListNormalState();
    }

    /**
     * Clicks the X (clear) button on the right side of the search field via XPath sibling.
     */
    private void clickSearchCloseX() {
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> xBtns = driver.findElements(SEARCH_CLOSE_X);
            if (!xBtns.isEmpty()) {
                try { xBtns.get(0).click(); } catch (Exception ignored) {}
            }
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    // ═══════════════════════════════════════════════════════
    //  FULL SEARCH FLOW
    // ═══════════════════════════════════════════════════════

    /**
     * Full search flow: open bar → get EditText ONCE → tap → clear → type.
     *
     * OPTIMIZATION: EditText reference obtained once via waitForFirst() (which
     * disables implicit wait for the entire poll, not just one findElements call).
     * The same reference is reused for click/clear/sendKeys — zero repeated lookups.
     */
    public void searchRecord(String text) {
        openSearch();
        // waitForFirst() disables implicit wait for the entire poll, restores after.
        WebElement input = elementUtils.waitForFirst(SEARCH_BAR, 10);
        if (input == null) return;
        // Reuse cached reference — no additional findElements() calls
        try { input.click();         } catch (Exception ignored) {}
        try { input.clear();         } catch (Exception ignored) {}
        try { input.sendKeys(text);  } catch (Exception ignored) {}
    }

    // ═══════════════════════════════════════════════════════
    //  INDIVIDUAL FIELD OPERATIONS (used by ActivityPage direct callers)
    // ═══════════════════════════════════════════════════════

    public void tapSearchInput() {
        WebElement input = elementUtils.waitForFirst(SEARCH_BAR, 10);
        if (input != null) { try { input.click(); } catch (Exception ignored) {} }
    }

    public void clearSearch() {
        WebElement input = elementUtils.waitForFirst(SEARCH_BAR, 10);
        if (input != null) { try { input.clear(); } catch (Exception ignored) {} }
    }

    public void typeSearchText(String text) {
        WebElement input = elementUtils.waitForFirst(SEARCH_BAR, 10);
        if (input != null) { try { input.sendKeys(text); } catch (Exception ignored) {} }
        try { driver.hideKeyboard(); } catch (Exception ignored) { /* keyboard already hidden */ }
    }

    // ═══════════════════════════════════════════════════════
    //  RECORD RETRIEVAL
    // ═══════════════════════════════════════════════════════

    /**
     * Returns the first list record whose content-desc contains the given name.
     *
     * Delegates to elementUtils.waitForFirst() which:
     *  - Disables implicit wait ONCE before the WebDriverWait loop starts
     *  - Each poll's findElements() returns in ~50 ms (not 10 s)
     *  - Restores implicit wait ONCE after polling ends
     * Poll result cached per cycle — no repeated findElements() for same locator.
     */
    public WebElement getRecordByName(String name) {
        return elementUtils.waitForFirst(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().descriptionContains(\"" + name + "\")"),
                15);
    }
}
