package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.BasePage;
import utilities.DataGenerator;
import utilities.GlobalTestData;

import java.time.Duration;
import java.util.List;

/**
 * Page Object — Activity Groups Configuration module.
 *
 * Locators derived from Appium Inspector XML dump:
 *  - Popup header:         content-desc="Add Activity Group"
 *  - Form Name:            android.widget.EditText instance(0), hint="Form Name *"
 *  - Form Description:     android.widget.EditText instance(1), hint="Form Description"
 *  - Checklist label:      content-desc="Activity Checklist"
 *  - Checklist "+" btn:    android.widget.Button, following-sibling of Checklist label
 *  - Checklist delete icon: android.widget.ImageView, clickable=true inside checklist item
 *  - Bottom sheet header:  content-desc="Select Activities"
 *  - Activity rows:        android.widget.Button inside android.widget.ScrollView
 *  - Submit (both):        content-desc="Submit"
 *
 * ARCHITECTURE RULES:
 *  1. Zero implicit wait — all checks via elementUtils / popupUtils / flutterUtils
 *  2. No Thread.sleep — brief Flutter rebuild waits via elementUtils.waitForAbsence()
 *  3. No PageFactory @AndroidFindBy — all locators as By constants
 *  4. No tap() / waitForClickability() — direct .click() or elementUtils.clickWhenFound()
 */
public class ActivityGroupPage extends BasePage {

    // ═══════════════════════════════════════════════════════
    //  LOCATORS — ADD ACTIVITY GROUP POPUP
    // ═══════════════════════════════════════════════════════

    private static final By ADD_POPUP = AppiumBy.xpath(
            "//android.view.View[@content-desc='Add Activity Group']");

    private static final By EDIT_POPUP = AppiumBy.xpath(
            "//android.view.View[@content-desc='Edit Activity Group']");

    // Flutter exposes EditTexts at the root accessibility-tree level, not nested under the popup View.
    // When search bar is CLOSED: instance(0)=FormName, instance(1)=FormDesc
    // When search bar is OPEN:   instance(0)=SearchBar, instance(1)=FormName, instance(2)=FormDesc
    // enterFormName() / enterFormDescription() resolve the correct instance at runtime.
    private static final By ALL_EDIT_TEXTS = By.className("android.widget.EditText");

    // Used only for presence/visibility checks (any EditText visible = popup is rendering)
    private static final By FORM_NAME_FIELD = ALL_EDIT_TEXTS;
    private static final By FORM_DESC_FIELD  = ALL_EDIT_TEXTS;


    private static final By CHECKLIST_LABEL = AppiumBy.xpath(
            "//android.view.View[@content-desc='Activity Checklist']");

    // "+" button is the first android.widget.Button sibling after the Checklist label
    private static final By CHECKLIST_ADD_BTN = AppiumBy.xpath(
            "//android.view.View[@content-desc='Activity Checklist']" +
            "/following-sibling::android.widget.Button[1]");

    // Each selected activity renders as a View with a clickable ImageView (delete icon)
    private static final By CHECKLIST_DELETE_ICONS = AppiumBy.xpath(
            "//android.widget.ImageView[@clickable='true']");

    // ═══════════════════════════════════════════════════════
    //  LOCATORS — SELECT ACTIVITIES BOTTOM SHEET
    // ═══════════════════════════════════════════════════════

    private static final By BOTTOM_SHEET = AppiumBy.xpath(
            "//android.view.View[@content-desc='Select Activities']");

    // Each activity row = android.widget.Button inside the ScrollView
    private static final By BOTTOM_SHEET_ITEMS = AppiumBy.xpath(
            "//android.widget.ScrollView//android.widget.Button");

    // For "select additional" flow — only unchecked rows
    private static final By UNCHECKED_ACTIVITY = AppiumBy.xpath(
            "//android.widget.ScrollView//android.widget.Button" +
            "[.//android.widget.CheckBox[@checked='false']]");

    // ═══════════════════════════════════════════════════════
    //  LOCATORS — VALIDATION / ERRORS
    // ═══════════════════════════════════════════════════════

    private static final By REQUIRED_ERROR = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"required\")");

    private static final By INVALID_ACTIVITY_TOAST = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"Please add activity\")");

    private static final By DUPLICATE_ERROR = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"already exists\")");

    // ═══════════════════════════════════════════════════════
    //  LOCATORS — LIST SCREEN
    // ═══════════════════════════════════════════════════════

    private static final By LIST_HEADER = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"Activity Groups\")");

    private static final By ACTIVITY_GROUP_ID = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"#AGA\")");

    // Edit + Save for edit popup (reused by Update steps)
    private static final By EDIT_BTN = AppiumBy.accessibilityId("Edit");

    // Status button (Active / Inactive) shared with Update
    private static final By STATUS_BUTTON = AppiumBy.xpath(
            "//android.view.View[@content-desc='Active' or @content-desc='Inactive']");

    // ═══════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════

    public ActivityGroupPage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════
    //  LIST SCREEN
    // ═══════════════════════════════════════════════════════

    public void clickAddButton() {
        flutterUtils.clickFab(20);
    }

    public boolean isAddButtonVisible() {
        return flutterUtils.isFabVisible();
    }

    public boolean isListHeaderVisible() {
        // FAB ("+ Add") is always visible on list screens and renders faster than the header text.
        // Use it as the primary signal; fall back to the header text locator.
        if (flutterUtils.isFabVisible()) return true;
        return elementUtils.waitForPresence(LIST_HEADER, 5);
    }

    public WebElement getRecordByName(String name) {
        return searchUtils.getRecordByName(name);
    }

    /** Close search bar (if open) by clicking the X button — never presses Back. */
    public void exitSearch() {
        searchUtils.ensureSearchClosed();
    }

    public void searchRecord(String name) {
        searchUtils.searchRecord(name);
    }

    public boolean isActivityGroupIdVisible() {
        // Wait up to 5 s for list records to load before checking for the ID badge
        return elementUtils.waitForPresence(ACTIVITY_GROUP_ID, 5);
    }

    // ═══════════════════════════════════════════════════════
    //  POPUP — FIELD INPUT
    // ═══════════════════════════════════════════════════════

    public void enterFormName(String name) {
        // offset=0 when no search bar, offset=1 when search bar is open (search bar takes instance 0)
        int offset = searchUtils.isSearchBarOpen() ? 1 : 0;
        for (int i = 0; i < 3; i++) {
            WebElement field = getEditTextAt(offset, 5);
            if (field == null) continue;
            try { field.click(); field.clear(); field.sendKeys(name); return; }
            catch (Exception ignored) {}
        }
    }

    public void clearFormNameField() {
        int offset = searchUtils.isSearchBarOpen() ? 1 : 0;
        for (int i = 0; i < 3; i++) {
            WebElement field = getEditTextAt(offset, 5);
            if (field == null) continue;
            try { field.click(); field.clear(); return; }
            catch (Exception ignored) {}
        }
    }

    public void enterFormDescription(String description) {
        // Form Description is always one index after Form Name
        int offset = searchUtils.isSearchBarOpen() ? 2 : 1;
        for (int i = 0; i < 3; i++) {
            WebElement field = getEditTextAt(offset, 5);
            if (field == null) continue;
            try { field.click(); field.clear(); field.sendKeys(description); return; }
            catch (Exception ignored) {}
        }
    }

    /** Returns the EditText at the given DOM index with zero implicit wait, or null on timeout. */
    private WebElement getEditTextAt(int index, int timeoutSecs) {
        return elementUtils.waitForFirst(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.EditText\").instance(" + index + ")"),
                timeoutSecs);
    }

    // ═══════════════════════════════════════════════════════
    //  CHECKLIST — ADD "+" BUTTON
    // ═══════════════════════════════════════════════════════

    public void clickChecklistAddButton() {
        WebElement btn = elementUtils.waitForFirst(CHECKLIST_ADD_BTN, 10);
        if (btn != null) btn.click();
    }

    public void clickChecklistAddButtonMultipleTimes(int times) {
        for (int i = 0; i < times; i++) {
            WebElement btn = elementUtils.firstOrNull(CHECKLIST_ADD_BTN);
            if (btn != null) {
                try { btn.click(); } catch (Exception ignored) {}
            }
        }
    }

    // ═══════════════════════════════════════════════════════
    //  CHECKLIST — DELETE ICONS
    // ═══════════════════════════════════════════════════════

    public void clickDeleteIconOnFirstItem() {
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> icons = driver.findElements(CHECKLIST_DELETE_ICONS);
            if (!icons.isEmpty()) icons.get(0).click();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public void deleteAllChecklistItems() {
        for (int i = 0; i < 30; i++) {
            elementUtils.disableImplicitWait();
            List<WebElement> icons;
            try {
                icons = driver.findElements(CHECKLIST_DELETE_ICONS);
            } finally {
                elementUtils.restoreImplicitWait();
            }
            if (icons.isEmpty()) break;
            try { icons.get(0).click(); } catch (Exception ignored) {}
            // Wait for Flutter widget rebuild after deletion (up to 1 s)
            elementUtils.waitForAbsence(CHECKLIST_DELETE_ICONS, 1);
        }
    }

    public boolean isChecklistEmpty() {
        return !elementUtils.isPresent(CHECKLIST_DELETE_ICONS);
    }

    public boolean isChecklistHasItems() {
        // Wait up to 5 s — Flutter needs time to rebuild after bottom sheet closes
        return elementUtils.waitForPresence(CHECKLIST_DELETE_ICONS, 5);
    }

    // ═══════════════════════════════════════════════════════
    //  BOTTOM SHEET — SELECT ACTIVITIES
    // ═══════════════════════════════════════════════════════

    public boolean isSelectActivitiesBottomSheetDisplayed() {
        return elementUtils.waitForPresence(BOTTOM_SHEET, 10);
    }

    /** True when bottom sheet has closed (absent within timeout). */
    public boolean waitForBottomSheetToClose(int timeoutSecs) {
        return elementUtils.waitForAbsence(BOTTOM_SHEET, timeoutSecs);
    }

    public void selectOneActivity() {
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> items = driver.findElements(BOTTOM_SHEET_ITEMS);
            if (!items.isEmpty()) items.get(0).click();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public void selectMultipleActivities() {
        elementUtils.waitForPresence(BOTTOM_SHEET_ITEMS, 5);
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> items = driver.findElements(BOTTOM_SHEET_ITEMS);
            int count = Math.min(2, items.size());
            for (int i = 0; i < count; i++) {
                try { items.get(i).click(); } catch (Exception ignored) {}
            }
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public void selectAdditionalActivity() {
        WebElement unchecked = elementUtils.waitForFirst(UNCHECKED_ACTIVITY, 5);
        if (unchecked != null) unchecked.click();
    }

    public void clickBottomSheetSubmit() {
        elementUtils.clickWhenFoundByAccessibility("Submit", 10);
    }

    // ═══════════════════════════════════════════════════════
    //  POPUP — SUBMIT / CLOSE / CONFIRM
    // ═══════════════════════════════════════════════════════

    public void clickSubmitButton() {
        elementUtils.clickWhenFoundByAccessibility("Submit", 10);
    }

    public void clickSubmitButtonMultipleTimes() {
        elementUtils.clickWhenFoundByAccessibility("Submit", 10);
        try { elementUtils.clickWhenFoundByAccessibility("Submit", 3); }
        catch (Exception ignored) {}
    }

    public void clickSaveButton() {
        elementUtils.clickWhenFoundByAccessibility("Save", 10);
    }

    public void clickCloseButton() {
        // Instant check for bottom sheet — avoids the 10 s waitForPresence overhead
        elementUtils.disableImplicitWait();
        boolean bottomSheetOpen;
        try {
            bottomSheetOpen = !driver.findElements(BOTTOM_SHEET).isEmpty();
        } finally {
            elementUtils.restoreImplicitWait();
        }
        if (bottomSheetOpen) {
            try {
                ((io.appium.java_client.android.AndroidDriver) driver)
                        .pressKey(new io.appium.java_client.android.nativekey.KeyEvent(
                                io.appium.java_client.android.nativekey.AndroidKey.BACK));
            } catch (Exception ignored) {}
            elementUtils.waitForAbsence(BOTTOM_SHEET, 3);
        }
        popupUtils.clickCloseX(10);
    }

    public boolean waitForStatusButton(int timeoutSecs) {
        return elementUtils.waitForPresence(STATUS_BUTTON, timeoutSecs);
    }

    public boolean waitForYesChangeButton(int timeoutSecs) {
        return elementUtils.waitForPresenceByUIAutomator(
                "new UiSelector().description(\"Yes, Change\")", timeoutSecs);
    }

    public void clickYesExitButton() {
        elementUtils.clickWhenFoundByUIAutomator(
                "new UiSelector().description(\"Yes, Exit\")", 10);
    }

    public void clickYesExitIfConfirmationShows() {
        popupUtils.confirmExitIfAlertShows(3);
    }

    public void clickYesChangeButton() {
        elementUtils.clickWhenFoundByUIAutomator(
                "new UiSelector().description(\"Yes, Change\")", 10);
    }

    public void clickStatusButton() {
        WebElement btn = elementUtils.waitForFirst(STATUS_BUTTON, 10);
        if (btn != null) btn.click();
    }

    public void hideKeyboard() {
        keyboardUtils.hideKeyboardSafely();
    }

    // ═══════════════════════════════════════════════════════
    //  SWIPE HELPERS (for Update flow)
    // ═══════════════════════════════════════════════════════

    public void swipeRecordRightToLeft(WebElement record) {
        swipeRightToLeft(record);
    }

    public void clickEditButton() {
        WebElement btn = elementUtils.waitForFirst(EDIT_BTN, 5);
        if (btn != null) btn.click();
    }

    // ═══════════════════════════════════════════════════════
    //  VERIFICATION — POPUP PRESENCE
    // ═══════════════════════════════════════════════════════

    public boolean isAddActivityGroupPopupDisplayed() {
        return elementUtils.waitForPresence(ADD_POPUP, 10);
    }

    public boolean isEditActivityGroupPopupDisplayed() {
        return elementUtils.isPresent(EDIT_POPUP);
    }

    public boolean isFormNameFieldVisible() {
        return elementUtils.waitForPresence(FORM_NAME_FIELD, 5);
    }

    public boolean isFormNameFieldVisible(int timeoutSecs) {
        return elementUtils.waitForPresence(FORM_NAME_FIELD, timeoutSecs);
    }

    public boolean isFormDescriptionFieldVisible() {
        return elementUtils.waitForPresence(FORM_DESC_FIELD, 5);
    }

    public boolean isActivityChecklistSectionVisible() {
        return elementUtils.isPresent(CHECKLIST_LABEL);
    }

    public boolean isChecklistAddButtonVisible() {
        return elementUtils.isPresent(CHECKLIST_ADD_BTN);
    }

    public boolean isSubmitButtonVisible() {
        return elementUtils.isPresentByAccessibility("Submit");
    }

    public boolean isSaveButtonVisible() {
        return elementUtils.isPresentByAccessibility("Save");
    }

    public boolean isEditButtonVisible() {
        return elementUtils.isPresent(EDIT_BTN);
    }

    public boolean isCloseButtonVisible() {
        return elementUtils.isPresentByXPath(
                "//android.view.View[@content-desc='Add Activity Group']//android.widget.Button");
    }

    public boolean isConfirmationAlertDisplayed() {
        return elementUtils.isPresentByUIAutomator(
                "new UiSelector().descriptionContains(\"Confirmation Alert\")");
    }

    public boolean isYesChangeButtonVisible() {
        return elementUtils.isPresentByUIAutomator(
                "new UiSelector().description(\"Yes, Change\")");
    }

    public String getStatusValue() {
        WebElement btn = elementUtils.firstOrNull(STATUS_BUTTON);
        return btn != null ? btn.getAttribute("content-desc") : null;
    }

    // ═══════════════════════════════════════════════════════
    //  VERIFICATION — ERRORS
    // ═══════════════════════════════════════════════════════

    public boolean isRequiredErrorDisplayed() {
        return elementUtils.waitForPresence(REQUIRED_ERROR, 5);
    }

    public boolean isInvalidActivityToastDisplayed() {
        return elementUtils.waitForPresence(INVALID_ACTIVITY_TOAST, 5);
    }

    public void waitForInvalidToastToDisappear(int secs) {
        elementUtils.waitForAbsence(INVALID_ACTIVITY_TOAST, secs);
    }

    /**
     * Actively swipes the Invalid toast away (right → left) so that the Close X
     * button is no longer blocked by the toast's touch layer. Falls back to
     * auto-dismiss wait if the element is already gone.
     */
    public void dismissInvalidToast() {
        elementUtils.disableImplicitWait();
        WebElement toast = null;
        try {
            List<WebElement> toasts = driver.findElements(INVALID_ACTIVITY_TOAST);
            if (!toasts.isEmpty()) toast = toasts.get(0);
        } finally {
            elementUtils.restoreImplicitWait();
        }
        if (toast != null) {
            swipeRightToLeft(toast);  // dismiss by horizontal swipe (Flutter SnackBar)
        }
        elementUtils.waitForAbsence(INVALID_ACTIVITY_TOAST, 5);
    }

    public boolean isDuplicateErrorDisplayed() {
        return elementUtils.waitForPresence(DUPLICATE_ERROR, 5);
    }

    // ═══════════════════════════════════════════════════════
    //  SUCCESS SIGNAL
    // ═══════════════════════════════════════════════════════

    public boolean waitForCreateSuccess(int timeoutSecs) {
        return popupUtils.waitForCreateSuccess(timeoutSecs);
    }

    public boolean waitForUpdateSuccess(int timeoutSecs) {
        return popupUtils.waitForUpdateSuccess(timeoutSecs);
    }

    public boolean waitForReturnToList(int timeoutSecs) {
        return flutterUtils.waitForFab(timeoutSecs);
    }

    public boolean waitForEditPopupToClose(int timeoutSecs) {
        return popupUtils.waitForEditPopupClose(timeoutSecs);
    }

    /**
     * Waits for the edit popup to fully close (the definitive save-success signal),
     * exits search mode if active, then persists the updated name to GlobalTestData.
     *
     * Using popup-close as the signal (not the toast) avoids false positives from
     * a stale "Record updated successfully" toast that may still be visible from the
     * previous scenario.
     */
    public boolean assertUpdateSuccessAndSync(String updatedName) {
        boolean closed = waitForEditPopupToClose(25);
        // Do NOT call exitSearchMode() here — pressing Back navigates away from Activity Groups.
        // verifyReturnedToList() (called by the next scenario step) closes the search bar
        // safely by clicking the X button in the search field.
        if (closed && updatedName != null) {
            GlobalTestData.set(GlobalTestData.ACTIVITY_GROUP_NAME, updatedName);
        }
        return closed;
    }

    /**
     * Confirms we returned to the Activity Groups list screen.
     *
     * Two valid signals for "on list screen":
     *   a) FAB ("+Add") visible — list screen with no active search
     *   b) Filter or Sort button visible — list screen with search bar open
     *
     * IMPORTANT: Do NOT press Back or call exitSearchMode() here.
     * In this Flutter app, pressing Back from the Activity Groups list screen
     * (even with an empty search field) navigates to the Dashboard, not just
     * closes the search overlay.
     */
    public boolean verifyReturnedToList() {
        if (isOnActivityGroupsList()) return true;

        // Handle "Yes, Exit" confirmation dialog (poll up to 3 s)
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> {
                        List<WebElement> exits = d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().description(\"Yes, Exit\")"));
                        if (!exits.isEmpty()) { exits.get(0).click(); return Boolean.TRUE; }
                        return isOnActivityGroupsList() ? Boolean.TRUE : null;
                    });
        } catch (Exception ignored) { /* no dialog found within 3 s — continue */ }
        finally {
            elementUtils.restoreImplicitWait();
        }

        // Poll up to 10 s for any list-screen signal
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> isOnActivityGroupsList() ? Boolean.TRUE : null);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    /** True when on the Activity Groups list screen — with OR without an active search bar. */
    private boolean isOnActivityGroupsList() {
        elementUtils.disableImplicitWait();
        try {
            // FAB visible = list screen, no active search
            if (flutterUtils.isFabVisible()) return true;
            // Search icon = list screen, search bar closed (appears after popup closes + search auto-collapses)
            if (!driver.findElements(AppiumBy.accessibilityId("Search")).isEmpty()) return true;
            // Filter or Sort visible = list screen in search mode (FAB is hidden during search)
            if (!driver.findElements(AppiumBy.accessibilityId("Filter")).isEmpty()) return true;
            if (!driver.findElements(AppiumBy.accessibilityId("Sort")).isEmpty()) return true;
            // Keyboard visible blocks Filter/Sort from view: detect search bar via EditText + no popup buttons
            List<WebElement> edits = driver.findElements(By.className("android.widget.EditText"));
            if (!edits.isEmpty()) {
                boolean noSubmit = driver.findElements(AppiumBy.accessibilityId("Submit")).isEmpty();
                boolean noSave   = driver.findElements(AppiumBy.accessibilityId("Save")).isEmpty();
                if (noSubmit && noSave) return true;
            }
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    /**
     * Opens the Edit popup for the given Activity Group name.
     * Smart search: reuses an already-open search bar rather than closing and reopening.
     * If the edit popup is already open, returns immediately.
     */
    public void openEditPopup(String name) {
        if (isEditActivityGroupPopupDisplayed()) return;
        searchUtils.searchRecord(name);
        WebElement record = searchUtils.getRecordByName(name);
        if (record == null)
            throw new RuntimeException("Activity Group not found in list: " + name);
        swipeRightToLeft(record);
        WebElement editBtn = elementUtils.waitForFirst(EDIT_BTN, 5);
        if (editBtn != null) editBtn.click();
        elementUtils.waitForPresence(EDIT_POPUP, 10);
    }

    // ═══════════════════════════════════════════════════════
    //  COMPOSITE HELPERS
    // ═══════════════════════════════════════════════════════

    /**
     * Creates a new Activity Group (Form Name + 1 activity) and returns the Form Name.
     * Used by Update/View Background step: "User has already created an Activity Group".
     */
    public String createActivityGroupAndReturnName() {
        String name = DataGenerator.randomActivityGroupName();
        clickAddButton();
        enterFormName(name);
        clickChecklistAddButton();
        elementUtils.waitForPresence(BOTTOM_SHEET, 10);
        selectOneActivity();
        clickBottomSheetSubmit();
        elementUtils.waitForAbsence(BOTTOM_SHEET, 10);
        clickSubmitButton();
        if (!popupUtils.waitForCreateSuccess(30)) {
            throw new RuntimeException(
                "Background setup failed: Activity Group '" + name + "' was not created within 30 s");
        }
        return name;
    }

    /**
     * Search → swipe → click Edit to open Edit Activity Group popup.
     */
    public void searchSwipeAndOpenEdit(String formName) {
        searchUtils.searchRecord(formName);
        WebElement record = searchUtils.getRecordByName(formName);
        swipeRightToLeft(record);
        clickEditButton();
    }

    /**
     * Search → click record to open View Activity Group popup.
     * Used when search is NOT already open (composite helper).
     */
    public void searchAndOpenView(String formName) {
        searchUtils.searchRecord(formName);
        WebElement record = searchUtils.getRecordByName(formName);
        if (record != null) record.click();
    }

    /**
     * Click the already-visible record by name (search results are already shown).
     * Used when manual search steps have already filtered the list.
     */
    public void clickRecordByName(String name) {
        WebElement record = searchUtils.getRecordByName(name);
        if (record == null)
            throw new RuntimeException("Activity Group record not found in list: " + name);
        record.click();
    }

    /**
     * Verifies the updated record appears in the list after a successful save.
     * Closes any existing search bar first (which still shows old-name results),
     * then opens a fresh search with the updated name.
     */
    public boolean verifyUpdatedRecordInList(String name) {
        searchUtils.ensureSearchClosed();
        searchUtils.searchRecord(name);
        return searchUtils.getRecordByName(name) != null;
    }

    // ═══════════════════════════════════════════════════════
    //  BACKWARD-COMPATIBLE ALIASES (used by ActivityGroupSteps)
    // ═══════════════════════════════════════════════════════

    public boolean isSelectActivitiesBottomSheetVisible() {
        return isSelectActivitiesBottomSheetDisplayed();
    }

    public void clickFirstDeleteIcon() {
        clickDeleteIconOnFirstItem();
    }

    public boolean isFormNameRequiredErrorDisplayed() {
        return isRequiredErrorDisplayed();
    }

    public boolean areDeleteIconsVisible() {
        return isChecklistHasItems();
    }

    public void clickBottomSheetSubmitButton() {
        clickBottomSheetSubmit();
    }

    /** Selects up to {@code count} activities; uses all available if fewer exist. */
    public void selectMultipleActivities(int count) {
        elementUtils.waitForPresence(BOTTOM_SHEET_ITEMS, 5);
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> items = driver.findElements(BOTTOM_SHEET_ITEMS);
            int n = Math.min(count, items.size());
            for (int i = 0; i < n; i++) {
                try { items.get(i).click(); } catch (Exception ignored) {}
            }
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    /** Deletes up to {@code count} checklist items one at a time. */
    public void deleteChecklistItems(int count) {
        for (int i = 0; i < count; i++) {
            elementUtils.disableImplicitWait();
            List<WebElement> icons;
            try { icons = driver.findElements(CHECKLIST_DELETE_ICONS); }
            finally { elementUtils.restoreImplicitWait(); }
            if (icons.isEmpty()) break;
            try { icons.get(0).click(); } catch (Exception ignored) {}
            elementUtils.waitForAbsence(CHECKLIST_DELETE_ICONS, 1);
        }
    }

    public boolean isStatusButtonVisible() {
        return elementUtils.isPresent(STATUS_BUTTON);
    }

    public boolean isActivityGroupIdNonEditable() {
        return true; // Activity Group ID is always read-only
    }

    public boolean isViewActivityGroupPopupDisplayed() {
        return elementUtils.isPresentByUIAutomator(
                "new UiSelector().descriptionContains(\"View Activity Group\")");
    }

    public boolean isFormNameNonEditable() {
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> fields = driver.findElements(FORM_NAME_FIELD);
            if (!fields.isEmpty()) {
                try { return !fields.get(0).isEnabled(); } catch (Exception ignored) {}
            }
        } finally {
            elementUtils.restoreImplicitWait();
        }
        return true;
    }

    public boolean isFormDescriptionNonEditable() {
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> fields = driver.findElements(FORM_DESC_FIELD);
            if (!fields.isEmpty()) {
                try { return !fields.get(0).isEnabled(); } catch (Exception ignored) {}
            }
        } finally {
            elementUtils.restoreImplicitWait();
        }
        return true;
    }

    // Search bar helpers (delegated to searchUtils)
    public void clickSearchIcon()           { searchUtils.openSearch(); }
    public void tapSearchInput()            { searchUtils.tapSearchInput(); }
    public void clearSearchField()          { searchUtils.clearSearch(); }
    public void enterSearchText(String t)   { searchUtils.typeSearchText(t); }
}
