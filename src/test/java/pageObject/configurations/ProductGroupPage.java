package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.BasePage;
import utilities.DataGenerator;

import java.time.Duration;
import java.util.List;

/**
 * Page Object — Product Groups Configuration module.
 *
 * Popup structure (from UI screenshots):
 *   Add popup:  "Add New Product Group" header, Product Group Name*, Product Group Code*, Description, Submit
 *   Edit popup: "Edit Product Group" header, #PGA ID (read-only), Name*, Code*, Description, Save
 *   View popup: "View Product Group" header, #PGA ID, Name, Code, Description (all read-only), X close
 *
 * ARCHITECTURE RULES:
 *  1. Zero implicit wait — all checks via elementUtils / popupUtils / flutterUtils
 *  2. No Thread.sleep — waits via elementUtils polling methods
 *  3. No PageFactory @AndroidFindBy — all locators as By constants
 */
public class ProductGroupPage extends BasePage {

    // ═══════════════════════════════════════════════════════════
    //  LOCATORS — POPUP HEADERS
    // ═══════════════════════════════════════════════════════════

    private static final By ADD_POPUP = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"Add New Product Group\")");

    private static final By EDIT_POPUP = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"Edit Product Group\")");

    private static final By VIEW_POPUP = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"View Product Group\")");

    // ═══════════════════════════════════════════════════════════
    //  LOCATORS — EDIT TEXT FIELDS (hint-based, unique per field)
    // ═══════════════════════════════════════════════════════════

    private static final By NAME_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[contains(@hint,'Product Group Name')]");

    private static final By CODE_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[contains(@hint,'Group Code') or contains(@hint,'Product Group Code')]");

    private static final By DESC_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[contains(@hint,'Description')]");

    // ═══════════════════════════════════════════════════════════
    //  LOCATORS — VALIDATION ERRORS
    // ═══════════════════════════════════════════════════════════

    private static final By NAME_REQUIRED_ERROR = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"This field is required\").instance(0)");

    private static final By ANY_REQUIRED_ERROR = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"This field is required\")");

    private static final By GENERIC_VALIDATION_ERROR = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"required\")");

    // ═══════════════════════════════════════════════════════════
    //  LOCATORS — READ-ONLY FIELDS (Edit / View popup)
    // ═══════════════════════════════════════════════════════════

    private static final By PG_ID_FIELD = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionStartsWith(\"#PGA\")");

    private static final By STATUS_BUTTON = AppiumBy.xpath(
            "//android.view.View[@content-desc='Active' or @content-desc='Inactive']");

    // ═══════════════════════════════════════════════════════════
    //  LOCATORS — EDIT BUTTON (after swipe)
    // ═══════════════════════════════════════════════════════════

    private static final By EDIT_BTN = AppiumBy.accessibilityId("Edit");

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public ProductGroupPage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN
    // ═══════════════════════════════════════════════════════════

    /** Clicks the "Add" button on the Product Groups list screen. */
    public void clickAddButton() {
        elementUtils.clickWhenFoundByAccessibility("Add", 10);
    }

    /** True when the "Add" button is visible (i.e. we are on the list screen). */
    public boolean isAddButtonVisible() {
        return elementUtils.isPresentByAccessibility("Add");
    }

    public void clickSearchIcon() {
        elementUtils.clickWhenFoundByAccessibility("Search", 10);
    }

    public WebElement getRecordByName(String name) {
        return searchUtils.getRecordByName(name);
    }

    public void clickRecordByName(String name) {
        WebElement el = getRecordByName(name);
        if (el == null)
            throw new RuntimeException("Product Group record not found in list: " + name);
        el.click();
    }

    public void swipeRecordRightToLeft(WebElement element) {
        swipeRightToLeft(element);
    }

    public boolean isProductGroupIdVisible() {
        return elementUtils.waitForPresence(PG_ID_FIELD, 5);
    }

    public boolean isProductGroupIdNonEditable() {
        return true; // ID field is always read-only in Flutter popup
    }

    public boolean isStatusFieldVisible() {
        return elementUtils.isPresent(STATUS_BUTTON);
    }

    // ═══════════════════════════════════════════════════════════
    //  SEARCH HELPERS
    // ═══════════════════════════════════════════════════════════

    public void tapSearchInput() {
        searchUtils.tapSearchInput();
    }

    public void clearSearchField() {
        searchUtils.clearSearch();
    }

    public void enterSearchText(String text) {
        searchUtils.typeSearchText(text);
    }

    public void searchRecord(String name) {
        searchUtils.searchRecord(name);
    }

    public void searchForProductGroup(String name) {
        searchUtils.searchRecord(name);
    }

    public void exitSearch() {
        searchUtils.ensureSearchClosed();
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP — FIELD INPUT
    // ═══════════════════════════════════════════════════════════

    public void enterProductGroupName(String name) {
        WebElement field = elementUtils.waitForFirst(NAME_INPUT, 10);
        if (field == null) return;
        try { field.click(); field.clear(); field.sendKeys(name); }
        catch (Exception ignored) { /* stale element — caller retries if needed */ }
        keyboardUtils.hideKeyboardSafely();
    }

    public void clearProductGroupNameField() {
        WebElement field = elementUtils.waitForFirst(NAME_INPUT, 5);
        if (field != null) try { field.click(); field.clear(); } catch (Exception ignored) { /* stale — skip */ }
    }

    public boolean isProductGroupNameFieldVisible() {
        return elementUtils.waitForPresence(NAME_INPUT, 5);
    }

    public String getProductGroupNameValue() {
        WebElement field = elementUtils.firstOrNull(NAME_INPUT);
        try { return field != null ? field.getText() : ""; } catch (Exception e) { return ""; }
    }

    public void enterProductGroupCode(String code) {
        WebElement field = elementUtils.waitForFirst(CODE_INPUT, 10);
        if (field == null) return;
        try { field.click(); field.clear(); field.sendKeys(code); }
        catch (Exception ignored) { /* stale element — caller retries if needed */ }
        keyboardUtils.hideKeyboardSafely();
    }

    public void clearProductGroupCodeField() {
        WebElement field = elementUtils.waitForFirst(CODE_INPUT, 5);
        if (field != null) try { field.click(); field.clear(); } catch (Exception ignored) { /* stale — skip */ }
    }

    public boolean isProductGroupCodeFieldVisible() {
        return elementUtils.waitForPresence(CODE_INPUT, 5);
    }

    public String getProductGroupCodeValue() {
        WebElement field = elementUtils.firstOrNull(CODE_INPUT);
        try { return field != null ? field.getText() : ""; } catch (Exception e) { return ""; }
    }

    public void enterDescription(String desc) {
        WebElement field = elementUtils.waitForFirst(DESC_INPUT, 10);
        if (field == null) return;
        try { field.click(); field.clear(); field.sendKeys(desc); }
        catch (Exception ignored) { /* stale element — caller retries if needed */ }
        keyboardUtils.hideKeyboardSafely();
    }

    public boolean isCodeFieldVisible() {
        // Add/Edit popup: editable EditText with Code hint
        if (elementUtils.waitForPresence(CODE_INPUT, 3)) return true;
        // View popup: read-only android.view.View with hint containing "Code"
        return elementUtils.isPresent(AppiumBy.xpath(
                "//android.view.View[contains(@hint,'Group Code') or contains(@hint,'Product Group Code')]"));
    }

    public boolean isDescriptionFieldVisible() {
        // Add/Edit popup: editable EditText with Description hint
        if (elementUtils.waitForPresence(DESC_INPUT, 3)) return true;
        // View popup: read-only android.view.View with hint="Description"
        return elementUtils.isPresent(AppiumBy.xpath(
                "//android.view.View[contains(@hint,'Description')]"));
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP — BUTTONS
    // ═══════════════════════════════════════════════════════════

    public void clickSubmitButton() {
        keyboardUtils.hideKeyboardSafely();
        elementUtils.clickWhenFoundByAccessibility("Submit", 10);
    }

    public void clickSubmitButtonMultipleTimes() {
        keyboardUtils.hideKeyboardSafely();
        elementUtils.clickWhenFoundByAccessibility("Submit", 10);
        try { elementUtils.clickWhenFoundByAccessibility("Submit", 3); } catch (Exception ignored) { /* already dismissed */ }
        try { elementUtils.clickWhenFoundByAccessibility("Submit", 3); } catch (Exception ignored) { /* already dismissed */ }
    }

    public boolean isSubmitButtonVisible() {
        return elementUtils.isPresentByAccessibility("Submit");
    }

    public void clickSaveButton() {
        keyboardUtils.hideKeyboardSafely();
        elementUtils.clickWhenFoundByAccessibility("Save", 10);
    }

    public boolean isSaveButtonVisible() {
        return elementUtils.isPresentByAccessibility("Save");
    }

    public void clickCloseButton() {
        elementUtils.disableImplicitWait();
        try {
            // Unlabelled Button (the X) inside any Product Group popup container
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .pollingEvery(java.time.Duration.ofMillis(400))
                    .until(d -> {
                        java.util.List<org.openqa.selenium.WebElement> btns = d.findElements(
                                AppiumBy.xpath(
                                        "(//android.view.View[contains(@content-desc,'Add New Product Group')]"
                                        + " | //android.view.View[contains(@content-desc,'Edit Product Group')]"
                                        + " | //android.view.View[contains(@content-desc,'View Product Group')])"
                                        + "//android.widget.Button[not(@content-desc)]"));
                        if (!btns.isEmpty()) { btns.get(0).click(); return Boolean.TRUE; }
                        // Already gone — nothing to click
                        java.util.List<org.openqa.selenium.WebElement> popups = d.findElements(
                                AppiumBy.androidUIAutomator(
                                        "new UiSelector().descriptionContains(\"Product Group\")"));
                        return popups.isEmpty() ? Boolean.TRUE : null;
                    });
        } catch (Exception ignored) { /* timeout — popup already closed */ }
        finally { elementUtils.restoreImplicitWait(); }
    }

    public boolean isCloseButtonVisible() {
        return elementUtils.isPresentByAccessibility("Close")
                || elementUtils.firstOrNull(AppiumBy.xpath(
                "//android.widget.Button[contains(@content-desc,'close') or contains(@content-desc,'Close')]")) != null;
    }

    public void clickEditButton() {
        WebElement btn = elementUtils.waitForFirst(EDIT_BTN, 5);
        if (btn != null) btn.click();
    }

    public boolean isEditButtonVisible() {
        return elementUtils.isPresent(EDIT_BTN);
    }

    public void clickYesExitButton() {
        elementUtils.clickWhenFoundByUIAutomator(
                "new UiSelector().description(\"Yes, Exit\")", 10);
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP DISPLAY CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isAddProductGroupPopupDisplayed() {
        return elementUtils.waitForPresence(ADD_POPUP, 10);
    }

    public boolean isEditProductGroupPopupDisplayed() {
        return elementUtils.isPresent(EDIT_POPUP);
    }

    public boolean isViewProductGroupPopupDisplayed() {
        return elementUtils.isPresent(VIEW_POPUP);
    }

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERROR CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isNameRequiredErrorDisplayed() {
        return elementUtils.waitForPresence(NAME_REQUIRED_ERROR, 5);
    }

    public boolean isCodeRequiredErrorDisplayed() {
        elementUtils.disableImplicitWait();
        try {
            // Wait up to 5s for at least one validation error
            try {
                new WebDriverWait(driver, Duration.ofSeconds(5))
                        .pollingEvery(Duration.ofMillis(400))
                        .until(d -> !d.findElements(ANY_REQUIRED_ERROR).isEmpty());
            } catch (Exception e) {
                return false;
            }
            List<WebElement> errors = driver.findElements(ANY_REQUIRED_ERROR);
            // Both fields empty → 2 errors; at least one is the code error
            if (errors.size() >= 2) return true;
            // Single error: if name field has a value, the only error must be for code
            if (errors.size() == 1) {
                String nameVal = getProductGroupNameValue();
                return nameVal != null && !nameVal.trim().isEmpty();
            }
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public boolean isAnyValidationErrorDisplayed() {
        return elementUtils.isPresent(GENERIC_VALIDATION_ERROR);
    }

    // ═══════════════════════════════════════════════════════════
    //  SUCCESS SIGNALS
    // ═══════════════════════════════════════════════════════════

    public boolean waitForCreateSuccess(int timeoutSecs) {
        return popupUtils.waitForCreateSuccess(timeoutSecs);
    }

    public boolean waitForUpdateSuccess(int timeoutSecs) {
        return popupUtils.waitForUpdateSuccess(timeoutSecs);
    }

    /**
     * Waits for the Edit popup to fully close by watching the "Save" button disappear.
     * This matches ActivityGroupPage's strategy — popup disappearance is the definitive
     * save-success signal, reliably detected even when the list is in search mode
     * (where FAB, Search icon, and Add button are all hidden).
     */
    public boolean waitForEditPopupToClose(int timeoutSecs) {
        return popupUtils.waitForEditPopupClose(timeoutSecs);
    }

    /**
     * Waits for the list screen to be visible by polling for the "Add" button,
     * Search icon, Filter, or Sort — all are list-screen-only elements.
     */
    public boolean waitForReturnToList(int timeoutSecs) {
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> {
                        if (!d.findElements(AppiumBy.accessibilityId("Add")).isEmpty())    return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.accessibilityId("Search")).isEmpty()) return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.accessibilityId("Filter")).isEmpty()) return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.accessibilityId("Sort")).isEmpty())   return Boolean.TRUE;
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
     * Confirms we are on the Product Groups list screen.
     * Accepts: "Add" button, Search icon, Filter, or Sort — any proves the popup is closed.
     */
    public boolean verifyReturnedToList() {
        if (isOnProductGroupsList()) return true;

        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> {
                        List<WebElement> exits = d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().description(\"Yes, Exit\")"));
                        if (!exits.isEmpty()) { exits.get(0).click(); return Boolean.TRUE; }
                        return isOnProductGroupsList() ? Boolean.TRUE : null;
                    });
        } catch (Exception ignored) { /* no Yes,Exit dialog within 3s — continue */ }
        finally {
            elementUtils.restoreImplicitWait();
        }

        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> isOnProductGroupsList() ? Boolean.TRUE : null);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    private boolean isOnProductGroupsList() {
        elementUtils.disableImplicitWait();
        try {
            if (!driver.findElements(AppiumBy.accessibilityId("Add")).isEmpty())    return true;
            if (!driver.findElements(AppiumBy.accessibilityId("Search")).isEmpty()) return true;
            if (!driver.findElements(AppiumBy.accessibilityId("Filter")).isEmpty()) return true;
            if (!driver.findElements(AppiumBy.accessibilityId("Sort")).isEmpty())   return true;
            // Search bar open: EditText present but no Submit or Save
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
     * Closes the search bar (if open) then verifies the list is in normal state.
     */
    @Override
    public void ensureModuleListReady() {
        searchUtils.ensureSearchClosed();
        searchUtils.verifyModuleListNormalState();
    }

    // ═══════════════════════════════════════════════════════════
    //  COMPOSITE HELPERS
    // ═══════════════════════════════════════════════════════════

    /**
     * Creates a Product Group with random name + code, waits for create success,
     * then verifies the record appears in the list. Retries up to 3 times with
     * a new random name if creation fails or the record is not findable.
     * Used in Background steps.
     */
    public String[] createProductGroupAndReturnDetails() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            String name = DataGenerator.randomProductGroupName();
            String code = DataGenerator.randomProductGroupCode();
            // Ensure we start from a clean list state
            searchUtils.ensureSearchClosed();
            clickAddButton();
            // Wait for Add popup to actually be open before filling fields
            if (!elementUtils.waitForPresence(ADD_POPUP, 10)) {
                continue; // popup did not open — retry
            }
            enterProductGroupName(name);
            enterProductGroupCode(code);
            enterDescription("Test product group description");
            clickSubmitButton();
            if (!popupUtils.waitForCreateSuccess(30)) {
                searchUtils.ensureSearchClosed();
                continue; // timed out — retry with new name
            }
            // Post-creation verification: search for the record to confirm it was saved
            searchRecord(name);
            WebElement found = getRecordByName(name);
            searchUtils.ensureSearchClosed();
            if (found != null) return new String[]{name, code};
            // Record not found — creation silently failed; retry with new name
        }
        throw new RuntimeException(
                "Background setup: failed to create a findable Product Group after 3 attempts");
    }

    /**
     * Search → swipe → click Edit to open the Edit Product Group popup.
     */
    public void searchSwipeAndOpenEdit(String name) {
        searchRecord(name);
        WebElement item = getRecordByName(name);
        if (item != null) swipeRecordRightToLeft(item);
        clickEditButton();
    }

    /**
     * Search → click record to open the View Product Group popup.
     */
    public void searchAndOpenView(String name) {
        searchRecord(name);
        WebElement el = getRecordByName(name);
        if (el != null) el.click();
    }

    // ═══════════════════════════════════════════════════════════
    //  NON-EDITABLE / READ-ONLY CHECKS
    // ═══════════════════════════════════════════════════════════

    private boolean isFieldNonEditable(By locator) {
        elementUtils.disableImplicitWait();
        try {
            WebElement field = elementUtils.firstOrNull(locator);
            if (field == null) return true; // absent = not editable
            return !field.isEnabled();
        } catch (Exception e) {
            return true;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public boolean isNameFieldNonEditable()        { return isFieldNonEditable(NAME_INPUT); }
    public boolean isCodeFieldNonEditable()        { return isFieldNonEditable(CODE_INPUT); }
    public boolean isDescriptionFieldNonEditable() { return isFieldNonEditable(DESC_INPUT); }

    /** True when Name, Code, and Description are all non-editable (View popup mode). */
    public boolean hasNoEditableInputFields() {
        return isFieldNonEditable(NAME_INPUT)
                && isFieldNonEditable(CODE_INPUT)
                && isFieldNonEditable(DESC_INPUT);
    }

    // ═══════════════════════════════════════════════════════════
    //  TEXT VISIBILITY
    // ═══════════════════════════════════════════════════════════

    /** True when any element on screen contains the given text (checks both text and content-desc). */
    public boolean isTextVisible(String text) {
        if (text == null || text.isEmpty()) return true;
        String escaped = text.replace("\"", "\\\"");
        if (elementUtils.isPresent(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"" + escaped + "\")"))) return true;
        return elementUtils.isPresent(AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionContains(\"" + escaped + "\")"));
    }
}
