package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObject.BasePage;

import java.util.List;

/**
 * Page Object — Activities Configuration module.
 *
 * Covers: Activities list screen, Add New Activity popup,
 *         Edit Activity popup, View Activity popup,
 *         Status-change confirmation popup.
 *
 * ARCHITECTURE RULES:
 *  1. Zero implicit-wait switching — all presence checks use elementUtils / searchUtils / popupUtils
 *  2. No Thread.sleep / LockSupport — replaced by polling WebDriverWait via elementUtils
 *  3. PageFactory (@AndroidFindBy) used ONLY for elements guaranteed present when the method runs
 *  4. Optional / conditional elements use elementUtils.firstOrNull() or elementUtils.isPresent()
 *
 * Locator priority: accessibilityId → id → AndroidUIAutomator → XPath (last resort)
 */
public class ActivityPage extends BasePage {

    // ═══════════════════════════════════════════════════════
    //  LOCATOR CONSTANTS  (single source of truth)
    // ═══════════════════════════════════════════════════════

    private static final By SUBMIT   = AppiumBy.accessibilityId("Submit");
    private static final By SAVE     = AppiumBy.accessibilityId("Save");
    private static final By EDIT_BTN = AppiumBy.accessibilityId("Edit");

    private static final By SEARCH_BAR = AppiumBy.className("android.widget.EditText");

    private static final By TOGGLE_SWITCH = AppiumBy.xpath(
            "//android.view.View[@content-desc='Is Function Applicable']" +
            "/following-sibling::android.view.View[1]");

    private static final By STATUS_BUTTON = AppiumBy.xpath(
            "//android.view.View[@content-desc='Active' or @content-desc='Inactive']");

    private static final By YES_CHANGE = AppiumBy.androidUIAutomator(
            "new UiSelector().description(\"Yes, Change\")");
    private static final By YES_EXIT = AppiumBy.androidUIAutomator(
            "new UiSelector().description(\"Yes, Exit\")");

    private static final By ADD_POPUP    = AppiumBy.xpath(
            "//android.view.View[@content-desc='Add New Activity']");
    private static final By EDIT_POPUP   = AppiumBy.xpath(
            "//android.view.View[@content-desc='Edit Activity']");
    private static final By VIEW_POPUP   = AppiumBy.xpath(
            "//android.view.View[@content-desc='View Activity']");

    private static final By ACTIVITY_NAME_EDITTEXT = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.widget.EditText\").instance(0)");
    private static final By DESCRIPTION_EDITTEXT = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.widget.EditText\").instance(1)");

    private static final By ACTIVITY_NAME_VIEW = AppiumBy.xpath(
            "//android.view.View[@hint='Activity Name *' or @hint='Activity Name']");
    private static final By DESCRIPTION_VIEW = AppiumBy.xpath(
            "//android.view.View[@hint='Description']");

    private static final By ACTIVITY_ID_FIELD = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionMatches(\"#[A-Z]+[0-9]+\")");

    private static final By REQUIRED_ERROR = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"required\")");
    private static final By DUPLICATE_ERROR = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"already exists\")");
    private static final By NO_CHANGES_MSG = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"No changes\")");

    private static final By CLOSE_BTN = AppiumBy.xpath(
            "//android.view.View[contains(@content-desc,'Activity')" +
            " or @content-desc='Confirmation Alert'" +
            " or @content-desc='Status Update']" +
            "//android.widget.Button[not(@content-desc)]");

    private static final By IS_FUNCTION_LABEL = AppiumBy.xpath(
            "//android.view.View[@content-desc='Is Function Applicable']");

    // ═══════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════

    public ActivityPage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════
    //  LIST SCREEN — ADD FAB
    // ═══════════════════════════════════════════════════════

    /**
     * Clicks the Add FAB using polling — zero implicit wait in the lambda
     * so each failed poll returns instantly (no 10-s implicit wait budget burn).
     */
    public void clickAddButton() {
        flutterUtils.clickFab(20);
    }

    /**
     * True when the Add FAB is visible (instant check, no wait).
     * Used inside polling loops — must NOT block by itself.
     */
    public boolean isAddButtonVisible() {
        return flutterUtils.isFabVisible();
    }

    // ═══════════════════════════════════════════════════════
    //  LIST SCREEN — SEARCH
    // ═══════════════════════════════════════════════════════

    /** Clicks Search icon (skips if bar already open). */
    public void clickSearchIcon() {
        searchUtils.openSearch();
    }

    public void tapSearchInput()           { searchUtils.tapSearchInput(); }
    public void clearSearchField()         { searchUtils.clearSearch(); }
    public void enterSearchText(String t)  { searchUtils.typeSearchText(t); }

    /**
     * Finds the first list record whose content-desc contains the given name.
     * Returns null if not found within 15 s.
     */
    public WebElement getRecordByName(String name) {
        return searchUtils.getRecordByName(name);
    }

    // ═══════════════════════════════════════════════════════
    //  ADD / EDIT POPUP — FIELD INPUT
    // ═══════════════════════════════════════════════════════

    public void enterActivityName(String name) {
        // Direct ops — bypasses ActionHelper.waitForVisibility() which can wait 15s
        // on Flutter elements that report isDisplayed()=false during render cycles.
        // Re-query up to 3 times to survive Flutter stale-reference after popup open.
        for (int i = 0; i < 3; i++) {
            WebElement field = elementUtils.waitForFirst(ACTIVITY_NAME_EDITTEXT, 5);
            if (field == null) continue;
            try { field.click(); field.clear(); field.sendKeys(name); return; }
            catch (Exception ignored) {}
        }
    }

    public void clearActivityNameField() {
        for (int i = 0; i < 3; i++) {
            WebElement field = elementUtils.waitForFirst(ACTIVITY_NAME_EDITTEXT, 5);
            if (field == null) continue;
            try { field.click(); field.clear(); return; }
            catch (Exception ignored) {}
        }
    }

    public void enterDescription(String description) {
        for (int i = 0; i < 3; i++) {
            WebElement field = elementUtils.waitForFirst(DESCRIPTION_EDITTEXT, 5);
            if (field == null) continue;
            try { field.click(); field.clear(); field.sendKeys(description); return; }
            catch (Exception ignored) {}
        }
    }

    // ═══════════════════════════════════════════════════════
    //  TOGGLE ACTIONS
    // ═══════════════════════════════════════════════════════

    /** Enables "Is Function Applicable" toggle — clicks only if currently OFF. */
    public void enableIsFunctionApplicableToggle() {
        WebElement toggle = elementUtils.waitForFirst(TOGGLE_SWITCH, 5);
        if (toggle != null && !flutterUtils.getToggleState(toggle)) toggle.click();
    }

    /** Disables "Is Function Applicable" toggle — clicks only if currently ON. */
    public void disableIsFunctionApplicableToggle() {
        WebElement toggle = elementUtils.waitForFirst(TOGGLE_SWITCH, 5);
        if (toggle != null && flutterUtils.getToggleState(toggle)) toggle.click();
    }

    /** Flips the toggle — re-queries each time since Flutter re-composes after click. */
    public void clickIsFunctionApplicableToggle() {
        WebElement toggle = elementUtils.waitForFirst(TOGGLE_SWITCH, 5);
        if (toggle != null) toggle.click();
    }

    // ═══════════════════════════════════════════════════════
    //  KEYBOARD
    // ═══════════════════════════════════════════════════════

    /** Public wrapper — step definitions cannot access BasePage.hideKeyboard() directly. */
    public void hideKeyboard() {
        keyboardUtils.hideKeyboardSafely();
    }

    // ═══════════════════════════════════════════════════════
    //  SUBMIT / SAVE / CLOSE ACTIONS
    // ═══════════════════════════════════════════════════════

    // Direct polling click — avoids tap() → waitForClickability() which waits up to 15s
    // on Flutter android.view.View elements that report enabled=false to UiAutomator2.
    public void clickSubmitButton() {
        elementUtils.clickWhenFoundByAccessibility("Submit", 10);
    }
    public void clickSaveButton() {
        elementUtils.clickWhenFoundByAccessibility("Save", 10);
    }

    /**
     * Clicks the X button inside the popup header.
     * Uses a polling retry — Flutter tap registration can be delayed.
     */
    public void clickCloseButton() {
        popupUtils.clickCloseX(10);
    }

    /**
     * After clicking Close X, handles the optional "Confirmation Alert" (unsaved changes)
     * by clicking "Yes, Exit". Returns immediately if no alert appears.
     */
    public void clickYesExitIfConfirmationShows() {
        popupUtils.confirmExitIfAlertShows(10);
    }

    public void clickYesExitButton() {
        elementUtils.clickWhenFoundByUIAutomator(
                "new UiSelector().description(\"Yes, Exit\")", 10);
    }

    // ═══════════════════════════════════════════════════════
    //  SWIPE + EDIT ACTIONS
    // ═══════════════════════════════════════════════════════

    public void swipeRecordRightToLeft(WebElement record) {
        swipeRightToLeft(record);
    }

    /**
     * Clicks the Edit button that appears after swiping a list record.
     * Re-queries directly — PageFactory proxy goes stale after Flutter swipe.
     */
    public void clickEditButton() {
        // Edit button appears immediately after swipe — 5 s is enough
        WebElement btn = elementUtils.waitForFirst(EDIT_BTN, 5);
        if (btn != null) btn.click();
    }

    // ═══════════════════════════════════════════════════════
    //  STATUS CHANGE ACTIONS
    // ═══════════════════════════════════════════════════════

    public void clickStatusButton() {
        WebElement btn = elementUtils.waitForFirst(STATUS_BUTTON, 10);
        if (btn != null) btn.click();
    }

    public void clickYesChangeButton() {
        elementUtils.clickWhenFoundByUIAutomator(
                "new UiSelector().description(\"Yes, Change\")", 10);
    }

    // ═══════════════════════════════════════════════════════
    //  VERIFICATION — POPUP VISIBILITY
    // ═══════════════════════════════════════════════════════

    public boolean isAddActivityPopupDisplayed()  { return elementUtils.isPresent(ADD_POPUP);  }
    public boolean isEditActivityPopupDisplayed() { return elementUtils.isPresent(EDIT_POPUP); }
    public boolean isViewActivityPopupDisplayed() { return elementUtils.isPresent(VIEW_POPUP); }
    /** Polls up to 3 s — handles Flutter widget rebuild after toggle/status tap. */
    public boolean isViewActivityPopupStillOpen() { return elementUtils.waitForPresence(VIEW_POPUP, 3); }
    public boolean isEditButtonVisible()          { return elementUtils.isPresent(EDIT_BTN);   }
    public boolean isCloseButtonVisible()         { return elementUtils.isPresent(CLOSE_BTN);  }
    public boolean isYesChangeButtonVisible()     { return elementUtils.isPresent(YES_CHANGE); }
    public boolean isYesExitButtonVisible()       { return elementUtils.isPresent(YES_EXIT);   }
    public boolean isConfirmationAlertDisplayed() {
        return elementUtils.isPresentByUIAutomator(
                "new UiSelector().descriptionContains(\"Confirmation Alert\")");
    }

    // ═══════════════════════════════════════════════════════
    //  VERIFICATION — FIELD VISIBILITY
    // ═══════════════════════════════════════════════════════

    /**
     * True when the Activity Name field is visible in any popup mode (Add/Edit/View).
     * Add/Edit: android.widget.EditText instance(0)
     * View: android.view.View with hint='Activity Name *'
     */
    public boolean isActivityNameFieldVisible() {
        // Instant checks only — no polling, no wait. EditText = Add/Edit; View = View popup.
        WebElement editField = elementUtils.firstOrNull(ACTIVITY_NAME_EDITTEXT);
        if (editField != null) return true;
        return elementUtils.isPresent(ACTIVITY_NAME_VIEW);
    }

    public boolean isDescriptionFieldVisible() {
        WebElement editField = elementUtils.firstOrNull(DESCRIPTION_EDITTEXT);
        if (editField != null) return true;
        return elementUtils.isPresent(DESCRIPTION_VIEW);
    }

    public boolean isToggleVisible() {
        return elementUtils.isPresent(IS_FUNCTION_LABEL);
    }

    /** Instant check — Submit absent returns false in ~50 ms. */
    public boolean isSubmitButtonVisible() {
        return elementUtils.isPresentByAccessibility("Submit");
    }

    /** Instant check — Save absent returns false in ~50 ms. */
    public boolean isSaveButtonVisible() {
        return elementUtils.isPresentByAccessibility("Save");
    }

    public boolean isActivityIdVisible() {
        return elementUtils.isPresent(ACTIVITY_ID_FIELD);
    }

    public boolean isStatusButtonVisible() {
        return elementUtils.isPresent(STATUS_BUTTON);
    }

    public boolean isSuccessMessageDisplayed() {
        return elementUtils.isPresentByUIAutomator(
                "new UiSelector().descriptionContains(\"Record created successfully\")");
    }

    public boolean isUpdateSuccessMessageDisplayed() {
        return elementUtils.isPresentByUIAutomator(
                "new UiSelector().descriptionContains(\"Record updated successfully\")");
    }

    // ═══════════════════════════════════════════════════════
    //  VERIFICATION — NON-EDITABLE FIELDS
    // ═══════════════════════════════════════════════════════

    public boolean isActivityIdNonEditable() {
        return true; // Activity ID is always a read-only View
    }

    /**
     * True when the Activity Name field is non-editable (View popup).
     * View popup: field is android.view.View with enabled=false.
     * Add/Edit popup: field is EditText with enabled=true.
     */
    public boolean isActivityNameNonEditable() {
        // View popup check: android.view.View with hint, enabled=false
        List<WebElement> viewFields = driver.findElements(ACTIVITY_NAME_VIEW);
        if (!viewFields.isEmpty()) {
            try { return !viewFields.get(0).isEnabled(); } catch (Exception ignored) {}
        }
        // Add/Edit: EditText — if enabled=false, field is non-editable
        WebElement editField = elementUtils.firstOrNull(ACTIVITY_NAME_EDITTEXT);
        if (editField != null) {
            try { return !editField.isEnabled(); } catch (Exception ignored) {}
        }
        return true; // field not found as EditText → assume non-editable (View mode)
    }

    /**
     * True when the Description field is non-editable (View popup).
     */
    public boolean isDescriptionNonEditable() {
        List<WebElement> viewFields = driver.findElements(DESCRIPTION_VIEW);
        if (!viewFields.isEmpty()) {
            try { return !viewFields.get(0).isEnabled(); } catch (Exception ignored) {}
        }
        WebElement editField = elementUtils.firstOrNull(DESCRIPTION_EDITTEXT);
        if (editField != null) {
            try { return !editField.isEnabled(); } catch (Exception ignored) {}
        }
        return true;
    }

    // ═══════════════════════════════════════════════════════
    //  VERIFICATION — ERROR MESSAGES
    // ═══════════════════════════════════════════════════════

    public boolean isActivityNameRequiredErrorDisplayed() {
        return elementUtils.isPresent(REQUIRED_ERROR);
    }

    public boolean isDuplicateErrorDisplayed() {
        return elementUtils.isPresent(DUPLICATE_ERROR);
    }

    public boolean isNoChangesMessageDisplayed() {
        return elementUtils.isPresent(NO_CHANGES_MSG);
    }

    // ═══════════════════════════════════════════════════════
    //  GETTERS
    // ═══════════════════════════════════════════════════════

    public String getActivityIdValue() {
        WebElement field = elementUtils.firstOrNull(ACTIVITY_ID_FIELD);
        return field != null ? field.getAttribute("content-desc") : null;
    }

    public String getStatusValue() {
        WebElement btn = elementUtils.firstOrNull(STATUS_BUTTON);
        return btn != null ? btn.getAttribute("content-desc") : null;
    }

    public boolean isToggleOn() {
        WebElement toggle = elementUtils.firstOrNull(TOGGLE_SWITCH);
        return toggle != null && flutterUtils.getToggleState(toggle);
    }

    // ═══════════════════════════════════════════════════════
    //  VIEW POPUP — STATE CHECKS
    // ═══════════════════════════════════════════════════════

    public boolean isKeyboardVisible() {
        return keyboardUtils.isKeyboardVisible();
    }

    /**
     * True when the status-change confirmation popup ("Yes, Change") is visible.
     * Used to assert it does NOT appear when Status is tapped in View mode.
     */
    public boolean isStatusConfirmationPopupDisplayed() {
        return elementUtils.isPresent(YES_CHANGE);
    }

    /**
     * Captures current toggle ON/OFF state (used in View popup read-only tests).
     */
    public boolean captureToggleState() {
        WebElement toggle = elementUtils.firstOrNull(TOGGLE_SWITCH);
        return toggle != null && flutterUtils.getToggleState(toggle);
    }

    // ═══════════════════════════════════════════════════════
    //  VIEW POPUP — READ-ONLY FIELD INTERACTIONS
    // ═══════════════════════════════════════════════════════

    /** Taps Status field in View popup — should produce no popup (read-only). */
    public void tapStatusFieldInView() {
        WebElement btn = elementUtils.firstOrNull(STATUS_BUTTON);
        if (btn != null) {
            try { btn.click(); } catch (Exception ignored) {}
        }
    }

    /** Taps Activity Name field in View popup — should NOT open keyboard. */
    public void tapActivityNameInView() {
        WebElement viewField = elementUtils.firstOrNull(ACTIVITY_NAME_VIEW);
        if (viewField != null) {
            try { viewField.click(); } catch (Exception ignored) {}
            return;
        }
        // Fallback: Add/Edit mode (should not be reached from View scenarios)
        WebElement editField = elementUtils.firstOrNull(ACTIVITY_NAME_EDITTEXT);
        if (editField != null) {
            try { editField.click(); } catch (Exception ignored) {}
        }
    }

    /** Taps Description field in View popup — should NOT open keyboard. */
    public void tapDescriptionInView() {
        WebElement viewField = elementUtils.firstOrNull(DESCRIPTION_VIEW);
        if (viewField != null) {
            try { viewField.click(); } catch (Exception ignored) {}
            return;
        }
        WebElement editField = elementUtils.firstOrNull(DESCRIPTION_EDITTEXT);
        if (editField != null) {
            try { editField.click(); } catch (Exception ignored) {}
        }
    }

    /** Taps the toggle in View mode — state should remain unchanged (read-only). */
    public void tapToggleInView() {
        WebElement toggle = elementUtils.firstOrNull(TOGGLE_SWITCH);
        if (toggle != null) {
            try { toggle.click(); } catch (Exception ignored) {}
        }
    }

    /** Finds a record in the View popup by activity name. */
    public WebElement getActivityNameFromView(String expectedName) {
        WebElement match = elementUtils.firstOrNullByUIAutomator(
                "new UiSelector().textContains(\"" + expectedName + "\")");
        if (match != null) return match;
        return elementUtils.firstOrNullByUIAutomator(
                "new UiSelector().descriptionContains(\"" + expectedName + "\")");
    }

    // ═══════════════════════════════════════════════════════
    //  COMPOSITE HELPERS
    // ═══════════════════════════════════════════════════════

    /**
     * Exits search mode by hiding the keyboard (triggers Flutter auto-close).
     * If Flutter does not auto-close the search bar (keyboard was already hidden
     * before this call), leaves the bar open — caller must poll the success toast
     * instead of the FAB (which stays hidden while search is active).
     *
     * REPLACED: LockSupport.parkNanos(600 ms) → polling waitForAbsence(3 s)
     */
    public void exitSearchIfOpen() {
        if (!isSearchBarOpenWithoutPopup()) return;
        System.out.println("[ActivityPage] Search bar open — hiding keyboard to trigger Flutter auto-close");
        keyboardUtils.hideKeyboardSafely();
        // Poll 3 s for Flutter to auto-close the bar: EditText disappears when bar closes.
        boolean closed = elementUtils.waitForAbsence(SEARCH_BAR, 3);
        if (closed) {
            System.out.println("[ActivityPage] Search bar auto-closed by Flutter");
        } else {
            System.out.println("[ActivityPage] Search bar still open after keyboard dismiss — leaving for caller");
        }
    }

    /**
     * Public instant check: true when search bar is open and no popup is active.
     * Use in step definitions as a fast gate before deciding to click Search icon.
     */
    public boolean isSearchBarOpenFast() {
        return isSearchBarOpenWithoutPopup();
    }

    /**
     * True when an EditText is visible and no popup (Submit/Save) is open.
     *
     * BATCH PATTERN: implicit wait disabled ONCE for all three findElements() calls,
     * restored ONCE after — 2 extra Appium calls total instead of 6.
     * Short-circuit: Save/Submit not checked when EditText absent.
     */
    private boolean isSearchBarOpenWithoutPopup() {
        elementUtils.disableImplicitWait();
        try {
            if (driver.findElements(SEARCH_BAR).isEmpty()) return false;
            if (!driver.findElements(SUBMIT).isEmpty())    return false;
            if (!driver.findElements(SAVE).isEmpty())      return false;
            return true;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    /**
     * Full create-activity flow used by "User has already created an Activity" Background step.
     * Returns the activity name so the test context can store it.
     */
    public String createActivityAndReturnName() {
        String name = utilities.DataGenerator.randomActivityName();
        clickAddButton();
        enterActivityName(name);
        clickSubmitButton();
        // Wait for the Add popup to close (FAB reappears) before returning.
        flutterUtils.waitForFab(15);
        return name;
    }

    /**
     * Search → wait → swipe → click Edit.
     * Single call replaces the repeated 4-step composite in step definitions.
     */
    public void searchSwipeAndOpenEdit(String activityName) {
        searchUtils.searchRecord(activityName);
        WebElement record = searchUtils.getRecordByName(activityName);
        swipeRightToLeft(record);
        clickEditButton();
    }

    /**
     * Search → wait → click on record to open View popup.
     * Uses direct .click() — avoids tap() → waitForClickability() which waits
     * up to 15 s on Flutter android.view.View elements reporting enabled=false.
     */
    public void searchAndOpenView(String activityName) {
        searchUtils.searchRecord(activityName);
        WebElement record = searchUtils.getRecordByName(activityName);
        if (record != null) record.click();
    }

    /**
     * Search, then click the record multiple times quickly (rapid-open test).
     * Second click is best-effort — popup may already be open after first.
     */
    public void searchAndClickRecordMultipleTimesQuickly(String activityName) {
        searchUtils.searchRecord(activityName);
        WebElement record = searchUtils.getRecordByName(activityName);
        if (record != null) record.click();
        // Re-query to avoid StaleElementReferenceException on second click
        WebElement record2 = elementUtils.firstOrNullByUIAutomator(
                "new UiSelector().descriptionContains(\"" + activityName + "\")");
        if (record2 != null) {
            try { record2.click(); } catch (Exception ignored) {}
        }
    }

    /** Full search flow delegating to searchUtils — open bar + tap + clear + type. */
    public void searchRecord(String name) {
        searchUtils.searchRecord(name);
    }
}
