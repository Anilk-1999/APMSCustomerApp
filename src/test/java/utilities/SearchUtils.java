package utilities;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
     * Exits search mode by hiding the keyboard (triggers Flutter auto-close).
     * Polls 3 s for Flutter to close the bar.
     */
    public void exitSearchMode() {
        if (!isSearchBarOpen()) return;
        try { driver.hideKeyboard(); } catch (Exception ignored) {}
        elementUtils.waitForAbsence(SEARCH_BAR, 3);
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
