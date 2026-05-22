package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageObject.BasePage;
import utilities.DataGenerator;

import java.util.List;

/**
 * Page Object — Spares Configuration Module.
 *
 * Locators sourced from Appium Inspector (real device: Samsung Galaxy A21s, Android 12).
 *
 * Add New Spare popup:
 *   - Popup title:    android.view.View  content-desc="Add New Spare"
 *   - X button:       android.widget.Button  (inside title View, no content-desc)
 *   - Spare Name:     android.widget.EditText  hint="Spare Name *"
 *   - Spare Code:     android.widget.EditText  hint="Spare Code *"
 *   - UOM:            android.widget.Button    content-desc starts with "UOM *"
 *   - Current Stock:  android.widget.EditText  hint="Current Stock *"  (EDITABLE in Add)
 *   - Description:    android.widget.EditText  hint="Description"
 *   - Submit:         android.widget.Button    content-desc="Submit"
 *
 * Edit Spare popup:
 *   - Popup title:    android.view.View  content-desc="Edit Spare"
 *   - Spare ID:       android.view.View  content-desc="#SPA..."
 *   - Status:         android.view.View  content-desc="Active"|"Inactive"  clickable=true
 *   - Current Stock:  android.view.View  hint="Current Stock *"  enabled=false  (NOT EditText)
 *   - Save:           android.widget.Button  content-desc="Save"
 *
 * View Spare popup:
 *   - Popup title:    android.view.View  content-desc="View Spare"
 *   - All fields:     android.view.View  with respective hints, enabled=false
 *   - Status:         android.view.View  content-desc="Active"|"Inactive"  clickable=true
 */
public class SparePage extends BasePage {

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN ELEMENTS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "Search")
    private WebElement searchIcon;

    @AndroidFindBy(xpath = "//android.widget.EditText")
    private WebElement searchInput;

    // ═══════════════════════════════════════════════════════════
    //  ADD / EDIT POPUP — INPUT FIELDS
    //  (EditText elements present in Add popup; some become View in Edit/View)
    // ═══════════════════════════════════════════════════════════

    // Spare Name: EditText in Add & Edit; android.view.View in View popup
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Spare Name *']")
    private WebElement spareNameInput;

    // Spare Code: EditText in Add & Edit; android.view.View in View popup
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Spare Code *']")
    private WebElement spareCodeInput;

    // UOM: android.widget.Button with content-desc starting "UOM *" in all modes
    @AndroidFindBy(xpath = "//android.widget.Button[starts-with(@content-desc,'UOM')]")
    private WebElement uomButton;

    // Current Stock: EditText in Add popup; android.view.View (enabled=false) in Edit/View
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Current Stock *']")
    private WebElement currentStockInput;

    // Description: EditText in Add & Edit; android.view.View in View popup
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Description']")
    private WebElement descriptionInput;

    // ═══════════════════════════════════════════════════════════
    //  READ-ONLY FIELD LOCATORS (Edit & View popup)
    // ═══════════════════════════════════════════════════════════

    // Spare ID — content-desc matches "#SPA..." pattern
    @AndroidFindBy(xpath = "//android.view.View[starts-with(@content-desc,'#SPA')]")
    private WebElement spareIdField;

    // Status toggle — content-desc is "Active" or "Inactive"
    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Active' or @content-desc='Inactive']")
    private WebElement statusField;

    // ═══════════════════════════════════════════════════════════
    //  POPUP BUTTONS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Submit']")
    private WebElement submitButton;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Save']")
    private WebElement saveButton;

    // X button: unlabelled Button inside any Spare popup header View
    private static final By SPARE_CLOSE_BTN = AppiumBy.xpath(
            "//android.view.View[@content-desc='Add New Spare'" +
            " or @content-desc='Edit Spare'" +
            " or @content-desc='View Spare'" +
            " or @content-desc='Spare Stock Update']" +
            "//android.widget.Button[not(@content-desc)]");

    // Swipe-revealed Edit action button
    private static final By EDIT_BTN = AppiumBy.accessibilityId("Edit");

    // ═══════════════════════════════════════════════════════════
    //  STOCK UPDATE POPUP FIELDS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'New Update Stock') or contains(@hint,'New Stock') or contains(@hint,'Update Stock')]")
    private WebElement newUpdateStockInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'Remarks') or contains(@hint,'remarks')]")
    private WebElement remarksInput;

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERRORS
    //  Flutter renders these as android.view.View with content-desc.
    //  All required-field errors show "This field is required".
    // ═══════════════════════════════════════════════════════════

    private static final By ERR_FIELD_REQUIRED = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"This field is required\")");

    private static final By ERR_STOCK_FMT = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionMatches(\"(?i).*(invalid|exceed|digit|format|decimal|range|numeric).*\")");

    private static final By ERR_DUPLICATE = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionMatches(\"(?i).*(already exists|duplicate).*\")");

    private static final By ERR_ANY_VALIDATION = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionMatches(\"(?i).*(required|invalid|already exists|duplicate|exceed|digit|format).*\")");

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public SparePage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickAddButton()          { flutterUtils.clickFab(20); }
    public boolean isAddButtonVisible()   { return flutterUtils.isFabVisible(); }
    public void clickSearchIcon()         { tap(searchIcon); }
    public void tapSearchInput()          { tap(searchInput); }
    public void clearSearchField()        { clearField(searchInput); }
    public void enterSearchText(String t) { clearAndType(searchInput, t); }

    public WebElement getRecordByName(String name) {
        return searchUtils.getRecordByName(name);
    }

    public void clickRecordByName(String name) {
        WebElement item = getRecordByName(name);
        if (item != null) tap(item);
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP FIELD ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void enterSpareName(String name)  { clearAndType(spareNameInput, name); }
    public void enterSpareCode(String code)  { clearAndType(spareCodeInput, code); }
    public void enterCurrentStock(String s)  { clearAndType(currentStockInput, s); }
    public void enterDescription(String d) {
        // Scroll Description into view — safe inside popup (no search overlay).
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                    ".scrollIntoView(new UiSelector().hint(\"Description\"))"));
        } catch (Exception ignored) { /* scroll best-effort */ }

        // UIAutomator hintMatches is case-insensitive and doesn't require visibility.
        org.openqa.selenium.By descUIA = AppiumBy.androidUIAutomator(
                "new UiSelector().className(\"android.widget.EditText\").hintMatches(\"(?i)description\")");
        WebElement desc = elementUtils.waitForFirst(descUIA, 5);

        // Fallback: last EditText inside Edit Spare popup
        if (desc == null) {
            desc = elementUtils.waitForFirst(AppiumBy.xpath(
                    "//android.view.View[@content-desc='Edit Spare']" +
                    "//android.widget.EditText[last()]"), 3);
        }

        if (desc != null) {
            try { desc.click(); } catch (Exception ignored) { /* intentional */ }
            // Poll until clear() succeeds (Flutter render cycle)
            final WebElement field = desc;
            try {
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                        .pollingEvery(java.time.Duration.ofMillis(300))
                        .until(dr -> { try { field.clear(); return true; } catch (Exception e) { return false; } });
            } catch (Exception ignored) { /* intentional */ }
            desc.sendKeys(d);
        }
    }

    public void clearSpareName()  { clearAndType(spareNameInput, ""); }
    public void clearSpareCode()  { clearAndType(spareCodeInput, ""); }

    // Dismiss backdrop appears when UOM dropdown is open
    private static final By DISMISS_BACKDROP = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"Dismiss\")");

    // UOM option buttons have simple names (e.g. "Kilograms", "Pcs") — exclude all known form buttons
    private static final By UOM_OPTION_BTN = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.widget.Button\")" +
            ".descriptionMatches(\"^(?!UOM|Submit|Save|Close|Cancel|X$)[A-Za-z0-9][A-Za-z0-9 /()._-]*$\")");

    /** Clicks UOM button then selects first available option. */
    public void selectUOM() {
        // Open dropdown only if not already open (Dismiss backdrop signals it is open)
        boolean alreadyOpen = elementUtils.waitForPresence(DISMISS_BACKDROP, 2);
        if (!alreadyOpen) {
            tap(uomButton);
            elementUtils.waitForPresence(DISMISS_BACKDROP, 5);
        }
        // Pick the first option (not Submit/Save/UOM/Close buttons)
        WebElement option = elementUtils.waitForFirst(UOM_OPTION_BTN, 5);
        if (option != null) tap(option);
    }

    public void clickUOMDropdown() { tap(uomButton); }

    public void clickSubmitButton() { tap(submitButton); }
    public void clickSaveButton()   { tap(saveButton); }
    public void clickCloseButton() {
        // Check popup type before clicking — confirmation only possible in Edit Spare (status change)
        boolean isEditPopup = isEditSparePopupDisplayed();
        WebElement btn = elementUtils.waitForFirst(SPARE_CLOSE_BTN, 10);
        if (btn != null) btn.click();
        if (isEditPopup) {
            popupUtils.confirmExitIfAlertShows(3);
        }
    }
    public void clickEditButton() {
        WebElement btn = elementUtils.waitForFirst(EDIT_BTN, 5);
        if (btn != null) btn.click();
    }

    public void swipeRecordRightToLeft(WebElement item) { swipeRightToLeft(item); }

    // ═══════════════════════════════════════════════════════════
    //  STATE CHECKS — POPUPS
    // ═══════════════════════════════════════════════════════════

    public boolean isAddNewSparePopupDisplayed() {
        return elementUtils.isPresentByUIAutomator(
                "new UiSelector().description(\"Add New Spare\")");
    }

    public boolean isEditSparePopupDisplayed() {
        return elementUtils.isPresentByUIAutomator(
                "new UiSelector().description(\"Edit Spare\")");
    }

    public boolean isViewSparePopupDisplayed() {
        return elementUtils.isPresentByUIAutomator(
                "new UiSelector().description(\"View Spare\")");
    }

    public boolean isSpareStockUpdatePopupDisplayed() {
        return elementUtils.isPresentByUIAutomator(
                "new UiSelector().descriptionContains(\"Spare Stock Update\")");
    }

    // ═══════════════════════════════════════════════════════════
    //  STATE CHECKS — FIELDS
    // ═══════════════════════════════════════════════════════════

    public boolean isSubmitButtonVisible()  { return elementUtils.isPresentByUIAutomator("new UiSelector().description(\"Submit\")"); }
    public boolean isSaveButtonVisible()    { return elementUtils.isPresentByUIAutomator("new UiSelector().description(\"Save\")"); }
    public boolean isEditButtonVisible()    { return elementUtils.isPresent(EDIT_BTN); }

    public boolean isSpareNameFieldVisible() {
        // EditText in Add/Edit; View in View popup — check both
        return elementUtils.isPresent(AppiumBy.xpath("//android.widget.EditText[@hint='Spare Name *']"))
                || elementUtils.isPresent(AppiumBy.xpath("//android.view.View[@hint='Spare Name *']"));
    }

    public boolean isSpareCodeFieldVisible() {
        return elementUtils.isPresent(AppiumBy.xpath("//android.widget.EditText[@hint='Spare Code *']"))
                || elementUtils.isPresent(AppiumBy.xpath("//android.view.View[@hint='Spare Code *']"));
    }

    public boolean isUOMFieldVisible() {
        // UOM is a Button with content-desc starting "UOM *"
        return elementUtils.isPresent(
                AppiumBy.xpath("//android.widget.Button[starts-with(@content-desc,'UOM')]"));
    }

    public boolean isUOMOptionsVisible() {
        return elementUtils.waitForPresence(DISMISS_BACKDROP, 3)
                || elementUtils.waitForPresence(UOM_OPTION_BTN, 3);
    }

    public boolean isCurrentStockFieldVisible() {
        // EditText in Add; View in Edit/View
        return elementUtils.isPresent(AppiumBy.xpath("//android.widget.EditText[@hint='Current Stock *']"))
                || elementUtils.isPresent(AppiumBy.xpath("//android.view.View[@hint='Current Stock *']"));
    }

    /**
     * Returns true when Current Stock is editable (only in Add popup).
     * In Edit/View popup, Current Stock is android.view.View with enabled=false.
     */
    public boolean isCurrentStockEditable() {
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> editables = driver.findElements(
                    AppiumBy.xpath("//android.widget.EditText[@hint='Current Stock *']"));
            return !editables.isEmpty();
        } catch (Exception e) {
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public boolean isSpareIdVisible() {
        return elementUtils.isPresent(
                AppiumBy.xpath("//android.view.View[starts-with(@content-desc,'#SPA')]"));
    }

    public boolean isSpareNameRequiredErrorDisplayed()  { return elementUtils.waitForPresence(ERR_FIELD_REQUIRED, 5); }
    public boolean isSpareCodeRequiredErrorDisplayed()  { return elementUtils.waitForPresence(ERR_FIELD_REQUIRED, 5); }
    public boolean isUOMRequiredErrorDisplayed()        { return elementUtils.waitForPresence(ERR_FIELD_REQUIRED, 5); }
    public boolean isStockRequiredErrorDisplayed()      { return elementUtils.waitForPresence(ERR_FIELD_REQUIRED, 5); }
    public boolean isStockFormatErrorDisplayed()        { return elementUtils.waitForPresence(ERR_STOCK_FMT, 5); }
    public boolean isDuplicateErrorDisplayed()          { return elementUtils.waitForPresence(ERR_DUPLICATE, 5); }

    public boolean isAnyValidationErrorDisplayed() {
        return elementUtils.waitForPresence(ERR_ANY_VALIDATION, 5);
    }

    // ═══════════════════════════════════════════════════════════
    //  STATE CHECKS — STOCK UPDATE FIELDS
    // ═══════════════════════════════════════════════════════════

    public boolean isNewUpdateStockFieldVisible()       { return isDisplayed(newUpdateStockInput); }
    public boolean isRemarksFieldVisible()              { return isDisplayed(remarksInput); }
    public boolean isStockUpdateSaveButtonVisible()     { return isSaveButtonVisible(); }
    public boolean isNewUpdateStockRequiredErrorDisplayed() { return elementUtils.waitForPresence(ERR_FIELD_REQUIRED, 5); }
    public boolean isRemarksRequiredErrorDisplayed()    { return elementUtils.waitForPresence(ERR_FIELD_REQUIRED, 5); }

    // ═══════════════════════════════════════════════════════════
    //  ACTION MENUS — LONG PRESS + STOCK UPDATE
    // ═══════════════════════════════════════════════════════════

    public void longPressRecord(String name) {
        WebElement item = getRecordByName(name);
        if (item != null) longPress(item);
    }

    public boolean isActionMenusBottomSheetDisplayed() {
        return elementUtils.waitForPresence(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().descriptionContains(\"Action Menus\")"), 5);
    }

    public boolean isStockUpdateOptionVisible() {
        return elementUtils.waitForPresence(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().descriptionContains(\"Stock Update\")"), 5);
    }

    public void clickStockUpdateOption() {
        WebElement option = elementUtils.waitForFirst(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().descriptionContains(\"Stock Update\")"), 5);
        if (option != null) tap(option);
    }

    // ═══════════════════════════════════════════════════════════
    //  STOCK UPDATE ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void enterNewUpdateStock(String value) {
        // Scroll popup to bring field into view before typing
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                    ".scrollIntoView(new UiSelector().className(\"android.widget.EditText\")" +
                    ".instance(0))"));
        } catch (Exception ignored) { /* scroll best-effort */ }
        org.openqa.selenium.By stockUIA = AppiumBy.androidUIAutomator(
                "new UiSelector().className(\"android.widget.EditText\").instance(0)");
        WebElement f = elementUtils.waitForFirst(stockUIA, 10);
        if (f != null) {
            try { f.click(); } catch (Exception ignored) { /* intentional */ }
            try { f.clear(); } catch (Exception ignored) { /* intentional */ }
            f.sendKeys(value);
        } else {
            clearAndType(newUpdateStockInput, value);
        }
    }

    public void enterRemarks(String remarks) {
        // Scroll popup to bring Remarks field (second EditText) into view
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                    ".scrollIntoView(new UiSelector().className(\"android.widget.EditText\")" +
                    ".instance(1))"));
        } catch (Exception ignored) { /* scroll best-effort */ }
        org.openqa.selenium.By remarksUIA = AppiumBy.androidUIAutomator(
                "new UiSelector().className(\"android.widget.EditText\").instance(1)");
        WebElement f = elementUtils.waitForFirst(remarksUIA, 10);
        if (f == null) {
            f = elementUtils.waitForFirst(AppiumBy.androidUIAutomator(
                    "new UiSelector().className(\"android.widget.EditText\")" +
                    ".hintMatches(\"(?i)remarks\")"), 5);
        }
        if (f != null) {
            try { f.click(); } catch (Exception ignored) { /* intentional */ }
            try { f.clear(); } catch (Exception ignored) { /* intentional */ }
            f.sendKeys(remarks);
        } else {
            clearAndType(remarksInput, remarks);
        }
        // Keyboard remains active after remarks entry. Callers that need the keyboard
        // gone (e.g. clickStockUpdateSaveButton) call dismissKeyboardByTappingHeader()
        // themselves. clickStockUpdateCloseButton does NOT need dismissal because the
        // X button sits at Y≈337, above the keyboard fold (~Y1136).
    }
    public void clickStockUpdateSaveButton() {
        // Ensure keyboard is gone before clicking Save (Save is at Y=1247, below keyboard fold).
        // Called from both positive flows (enterRemarks already dismissed) and negative flows
        // (enterRemarks was skipped — keyboard still showing from stock field).
        if (keyboardUtils.isKeyboardVisible()) {
            dismissKeyboardByTappingHeader();
        }
        WebElement btn = elementUtils.waitForFirstByAccessibility("Save", 5);
        if (btn != null) btn.click();
    }

    private void dismissKeyboardByTappingHeader() {
        // Click the "Spare Stock Update" header view (Y≈337, above the keyboard zone).
        // This removes focus from the active EditText → keyboard closes without BACK navigation.
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().descriptionContains(\"Spare Stock Update\")")).click();
        } catch (Exception ignored) { /* intentional */ }
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                    .pollingEvery(java.time.Duration.ofMillis(200))
                    .until(d -> !keyboardUtils.isKeyboardVisible());
        } catch (Exception ignored) { /* intentional */ }
    }
    public void clickStockUpdateCloseButton() {
        WebElement btn = elementUtils.waitForFirst(SPARE_CLOSE_BTN, 10);
        if (btn != null) btn.click();
    }

    // ═══════════════════════════════════════════════════════════
    //  COMPOSITE HELPERS
    // ═══════════════════════════════════════════════════════════

    public void searchSwipeAndOpenEdit(String name) {
        clickSearchIcon(); tapSearchInput(); clearSearchField(); enterSearchText(name);
        WebElement item = getRecordByName(name);
        if (item != null) swipeRecordRightToLeft(item);
        clickEditButton();
    }

    public void searchAndOpenView(String name) {
        clickSearchIcon(); tapSearchInput(); clearSearchField(); enterSearchText(name);
        WebElement item = getRecordByName(name);
        if (item != null) tap(item);
    }

    public boolean verifyUpdatedRecordInList(String name) {
        searchUtils.searchRecord(name);
        return searchUtils.getRecordByName(name) != null;
    }

    // ═══════════════════════════════════════════════════════════
    //  SUCCESS / NAVIGATION WAITS
    // ═══════════════════════════════════════════════════════════

    public boolean waitForCreateSuccess(int timeoutSecs) {
        return popupUtils.waitForCreateSuccess(timeoutSecs);
    }

    public boolean waitForUpdateSuccess(int timeoutSecs) {
        return popupUtils.waitForUpdateSuccess(timeoutSecs);
    }

    public boolean waitForReturnToList(int timeoutSecs) {
        return flutterUtils.waitForFab(timeoutSecs);
    }

    public boolean waitForStockUpdatePopupClosed(int timeoutSecs) {
        // FAB is hidden in search mode, so detect closure by polling for popup absence.
        elementUtils.disableImplicitWait();
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(java.time.Duration.ofMillis(400))
                    .until(d -> d.findElements(AppiumBy.androidUIAutomator(
                            "new UiSelector().descriptionContains(\"Spare Stock Update\")")).isEmpty()
                            ? Boolean.TRUE : null);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public String createSpareAndReturnName() {
        String name = DataGenerator.randomSpareName();
        clickAddButton();
        enterSpareName(name);
        enterSpareCode(DataGenerator.randomSpareCode());
        selectUOM();
        enterCurrentStock(DataGenerator.randomStockValue());
        clickSubmitButton();
        if (!popupUtils.waitForCreateSuccess(30)) {
            throw new RuntimeException(
                    "Background setup failed: Spare '" + name + "' was not created within 30 s");
        }
        return name;
    }
}
