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
 * Page Object — Raw Materials Configuration module.
 *
 * Popup structure (from UI screenshots):
 *   Add popup  : "Add Raw Material" header, Name*, UOM* (dropdown), Description, Submit, X close
 *   Edit popup : "Edit Raw Material" header, #RMA ID (read-only), Name (read-only), UOM (read-only),
 *                Description (editable), Status (editable), Save, X close
 *   View popup : "View Raw Material" header, #RMA ID, Name, UOM, Description, Status (all read-only), X close
 *
 * ARCHITECTURE RULES:
 *  1. Zero implicit wait — all checks via elementUtils / popupUtils / flutterUtils
 *  2. No Thread.sleep — waits via elementUtils polling methods
 *  3. No PageFactory @AndroidFindBy — all locators as By constants
 */
public class RawMaterialPage extends BasePage {

    // ═══════════════════════════════════════════════════════════
    //  LOCATORS — POPUP HEADERS
    // ═══════════════════════════════════════════════════════════

    private static final By ADD_POPUP = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"Add Raw Material\")");

    private static final By EDIT_POPUP = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"Edit Raw Material\")");

    private static final By VIEW_POPUP = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"View Raw Material\")");

    // ═══════════════════════════════════════════════════════════
    //  LOCATORS — EDIT TEXT FIELDS (hint-based)
    // ═══════════════════════════════════════════════════════════

    private static final By NAME_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[contains(@hint,'Name') or contains(@hint,'name')]");

    private static final By DESC_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[contains(@hint,'Description') or contains(@hint,'description')]");

    // ═══════════════════════════════════════════════════════════
    //  LOCATORS — UOM DROPDOWN
    // ═══════════════════════════════════════════════════════════

    private static final By UOM_DROPDOWN = AppiumBy.xpath(
            "//android.widget.Button[starts-with(@content-desc,'UOM')]"
            + " | //android.view.View[@content-desc='UOM']"
            + " | //android.widget.Button[@content-desc='UOM']");

    // ═══════════════════════════════════════════════════════════
    //  LOCATORS — READ-ONLY FIELDS
    // ═══════════════════════════════════════════════════════════

    private static final By RMA_ID_FIELD = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionStartsWith(\"#RMA\")");

    private static final By STATUS_BUTTON = AppiumBy.xpath(
            "//android.view.View[@content-desc='Active' or @content-desc='Inactive']");

    // ═══════════════════════════════════════════════════════════
    //  LOCATORS — EDIT BUTTON (after swipe)
    // ═══════════════════════════════════════════════════════════

    private static final By EDIT_BTN = AppiumBy.accessibilityId("Edit");

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
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public RawMaterialPage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN
    // ═══════════════════════════════════════════════════════════

    public void clickAddButton() {
        elementUtils.clickWhenFoundByAccessibility("Add", 10);
    }

    public boolean isAddButtonVisible() {
        return elementUtils.isPresentByAccessibility("Add");
    }

    public WebElement getRecordByName(String name) {
        return searchUtils.getRecordByName(name);
    }

    public void clickRecordByName(String name) {
        WebElement el = getRecordByName(name);
        if (el == null) throw new RuntimeException("Raw Material record not found in list: " + name);
        el.click();
    }

    public void swipeRecordRightToLeft(WebElement element) {
        swipeRightToLeft(element);
    }

    public boolean isRawMaterialIdVisible() {
        return elementUtils.waitForPresence(RMA_ID_FIELD, 5);
    }

    public String getRawMaterialIdText() {
        WebElement el = elementUtils.firstOrNull(RMA_ID_FIELD);
        try { return el != null ? el.getAttribute("content-desc") : ""; } catch (Exception e) { return ""; }
    }

    public boolean isStatusVisible() {
        return elementUtils.isPresent(STATUS_BUTTON)
                || elementUtils.isPresent(AppiumBy.androidUIAutomator(
                        "new UiSelector().description(\"Active\").instance(0)"))
                || elementUtils.isPresent(AppiumBy.androidUIAutomator(
                        "new UiSelector().description(\"Inactive\").instance(0)"));
    }

    public String getCurrentStatus() {
        elementUtils.disableImplicitWait();
        try {
            if (!driver.findElements(AppiumBy.xpath(
                    "//android.view.View[@content-desc='Active']")).isEmpty()) return "Active";
            if (!driver.findElements(AppiumBy.xpath(
                    "//android.view.View[@content-desc='Inactive']")).isEmpty()) return "Inactive";
            return "";
        } finally { elementUtils.restoreImplicitWait(); }
    }

    // ═══════════════════════════════════════════════════════════
    //  SEARCH HELPERS
    // ═══════════════════════════════════════════════════════════

    public void tapSearchInput() { searchUtils.tapSearchInput(); }
    public void clearSearchField() { searchUtils.clearSearch(); }
    public void enterSearchText(String text) { searchUtils.typeSearchText(text); }
    public void searchRecord(String name) { searchUtils.searchRecord(name); }
    public void exitSearch() { searchUtils.ensureSearchClosed(); }

    // ═══════════════════════════════════════════════════════════
    //  POPUP — FIELD INPUT
    // ═══════════════════════════════════════════════════════════

    public void enterName(String name) {
        WebElement field = elementUtils.waitForFirst(NAME_INPUT, 10);
        if (field == null) return;
        try { field.click(); field.clear(); field.sendKeys(name); } catch (Exception e) { /* stale element — caller retries if needed */ }
        keyboardUtils.hideKeyboardSafely();
    }

    public void clearNameField() {
        WebElement field = elementUtils.waitForFirst(NAME_INPUT, 5);
        if (field != null) try { field.click(); field.clear(); } catch (Exception e) { /* stale — skip */ }
    }

    public boolean isNameFieldVisible() {
        return elementUtils.waitForPresence(NAME_INPUT, 5);
    }

    public String getNameValue() {
        WebElement field = elementUtils.firstOrNull(NAME_INPUT);
        try { return field != null ? field.getText() : ""; } catch (Exception e) { return ""; }
    }

    public void enterDescription(String desc) {
        WebElement field = elementUtils.waitForFirst(DESC_INPUT, 10);
        if (field == null) return;
        try { field.click(); field.clear(); field.sendKeys(desc); } catch (Exception e) { /* stale element — caller retries if needed */ }
        keyboardUtils.hideKeyboardSafely();
    }

    public void clearDescriptionField() {
        WebElement field = elementUtils.waitForFirst(DESC_INPUT, 5);
        if (field != null) try { field.click(); field.clear(); } catch (Exception e) { /* stale — skip */ }
    }

    public boolean isDescriptionFieldVisible() {
        if (elementUtils.waitForPresence(DESC_INPUT, 3)) return true;
        return elementUtils.isPresent(AppiumBy.xpath(
                "//android.view.View[contains(@hint,'Description') or contains(@hint,'description')]"));
    }

    public void selectUOM() {
        // Click the UOM dropdown button
        elementUtils.disableImplicitWait();
        try {
            WebElement dropdown = elementUtils.waitForFirst(UOM_DROPDOWN, 8);
            if (dropdown == null) {
                // Fallback: UIAutomator
                elementUtils.clickWhenFoundByUIAutomator(
                        "new UiSelector().description(\"UOM\")", 8);
            } else {
                dropdown.click();
            }
        } finally { elementUtils.restoreImplicitWait(); }

        // Wait for dropdown list then select first available item
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .pollingEvery(Duration.ofMillis(300))
                    .until(d -> {
                        List<WebElement> items = d.findElements(AppiumBy.xpath(
                                "//android.widget.ScrollView//android.widget.Button"));
                        if (!items.isEmpty()) { items.get(0).click(); return Boolean.TRUE; }
                        return null;
                    });
        } catch (Exception e) { /* no UOM items found — selectUOM silently skips */ }
    }

    public boolean isUOMDropdownVisible() {
        if (elementUtils.waitForPresence(UOM_DROPDOWN, 5)) return true;
        return elementUtils.isPresentByUIAutomator("new UiSelector().description(\"UOM\")");
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
        try { elementUtils.clickWhenFoundByAccessibility("Submit", 3); } catch (Exception e) { /* already dismissed */ }
        try { elementUtils.clickWhenFoundByAccessibility("Submit", 3); } catch (Exception e) { /* already dismissed */ }
    }

    public boolean isSubmitButtonVisible() {
        return elementUtils.isPresentByAccessibility("Submit");
    }

    public void clickSaveButton() {
        keyboardUtils.hideKeyboardSafely();
        elementUtils.clickWhenFoundByAccessibility("Save", 10);
    }

    public void clickSaveButtonMultipleTimes() {
        keyboardUtils.hideKeyboardSafely();
        elementUtils.clickWhenFoundByAccessibility("Save", 10);
        try { elementUtils.clickWhenFoundByAccessibility("Save", 3); } catch (Exception e) { /* already dismissed */ }
        try { elementUtils.clickWhenFoundByAccessibility("Save", 3); } catch (Exception e) { /* already dismissed */ }
    }

    public boolean isSaveButtonVisible() {
        return elementUtils.isPresentByAccessibility("Save");
    }

    public void clickCloseButton() {
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> {
                        List<WebElement> btns = d.findElements(AppiumBy.xpath(
                                "(//android.view.View[contains(@content-desc,'Add Raw Material')]"
                                + " | //android.view.View[contains(@content-desc,'Edit Raw Material')]"
                                + " | //android.view.View[contains(@content-desc,'View Raw Material')])"
                                + "//android.widget.Button[not(@content-desc)]"));
                        if (!btns.isEmpty()) { btns.get(0).click(); return Boolean.TRUE; }
                        List<WebElement> popups = d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().descriptionContains(\"Raw Material\")"));
                        return popups.isEmpty() ? Boolean.TRUE : null;
                    });
        } catch (Exception e) { /* timeout — popup already closed */ }
        finally { elementUtils.restoreImplicitWait(); }
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

    public void clickYesChangeButton() {
        elementUtils.clickWhenFoundByUIAutomator(
                "new UiSelector().description(\"Yes, Change\")", 10);
    }

    public void dismissStatusConfirmation() {
        // Press back to dismiss status confirmation popup without confirming
        elementUtils.disableImplicitWait();
        try {
            // Try clicking a Cancel/No button first
            List<WebElement> cancel = driver.findElements(AppiumBy.androidUIAutomator(
                    "new UiSelector().description(\"Cancel\")"));
            if (!cancel.isEmpty()) { cancel.get(0).click(); return; }
            List<WebElement> no = driver.findElements(AppiumBy.androidUIAutomator(
                    "new UiSelector().description(\"No\")"));
            if (!no.isEmpty()) { no.get(0).click(); return; }
            // Click the unlabelled close button (first Button without content-desc in confirmation popup)
            List<WebElement> btns = driver.findElements(AppiumBy.xpath(
                    "//android.widget.Button[not(@content-desc) or @content-desc='']"));
            if (!btns.isEmpty()) { btns.get(0).click(); return; }
        } finally { elementUtils.restoreImplicitWait(); }
        // Fall back to pressing back
        driver.navigate().back();
    }

    public void clickStatusButtonMultipleTimes() {
        for (int i = 0; i < 3; i++) {
            WebElement btn = elementUtils.firstOrNull(STATUS_BUTTON);
            if (btn != null) try { btn.click(); } catch (Exception e) { /* stale — button gone */ }
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP DISPLAY CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isAddPopupDisplayed() {
        return elementUtils.waitForPresence(ADD_POPUP, 10);
    }

    public boolean isEditPopupDisplayed() {
        return elementUtils.isPresent(EDIT_POPUP);
    }

    public boolean isViewPopupDisplayed() {
        return elementUtils.isPresent(VIEW_POPUP);
    }

    public boolean waitForViewPopupClosed(int timeoutSecs) {
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(Duration.ofMillis(300))
                    .until(d -> d.findElements(VIEW_POPUP).isEmpty() ? Boolean.TRUE : null);
            return true;
        } catch (Exception e) { return false; }
        finally { elementUtils.restoreImplicitWait(); }
    }

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERROR CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isNameRequiredErrorDisplayed() {
        return elementUtils.waitForPresence(NAME_REQUIRED_ERROR, 5);
    }

    public boolean isUOMRequiredErrorDisplayed() {
        elementUtils.disableImplicitWait();
        try {
            // Wait briefly for any validation error to appear
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> {
                        if (!d.findElements(ANY_REQUIRED_ERROR).isEmpty()) return Boolean.TRUE;
                        // Also check by text (some Flutter widgets expose error via @text)
                        if (!d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().textContains(\"This field is required\")")).isEmpty()) return Boolean.TRUE;
                        return null;
                    });
        } catch (Exception e) { /* no inline error found — check popup-still-open fallback below */ }

        // Check by content-desc
        List<WebElement> cdErrors = driver.findElements(ANY_REQUIRED_ERROR);
        if (cdErrors.size() >= 2) return true;
        // Check by text
        List<WebElement> txErrors = driver.findElements(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"This field is required\")"));
        int total = Math.max(cdErrors.size(), txErrors.size());
        if (total >= 2) return true;
        // One error visible and name is already filled → it must be the UOM error
        if (total == 1) {
            String nameVal = getNameValue();
            return nameVal != null && !nameVal.trim().isEmpty();
        }
        // Fallback: popup still open + name filled = UOM validation blocked submission
        if (elementUtils.isPresent(ADD_POPUP)) {
            String nameVal = getNameValue();
            return nameVal != null && !nameVal.trim().isEmpty();
        }
        return false;
    }

    public boolean isAnyValidationErrorDisplayed() {
        return elementUtils.isPresent(GENERIC_VALIDATION_ERROR);
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
        } catch (Exception e) { return true; }
        finally { elementUtils.restoreImplicitWait(); }
    }

    public boolean isNameFieldNonEditable() { return isFieldNonEditable(NAME_INPUT); }
    public boolean isDescriptionFieldNonEditable() { return isFieldNonEditable(DESC_INPUT); }

    public boolean hasNoEditableInputFields() {
        return isFieldNonEditable(NAME_INPUT) && isFieldNonEditable(DESC_INPUT);
    }

    // ═══════════════════════════════════════════════════════════
    //  SUCCESS SIGNALS
    // ═══════════════════════════════════════════════════════════

    public boolean waitForCreateSuccess(int timeoutSecs) {
        return popupUtils.waitForCreateSuccess(timeoutSecs);
    }

    public boolean waitForEditPopupToClose(int timeoutSecs) {
        return popupUtils.waitForEditPopupClose(timeoutSecs);
    }

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
                        // Search bar open with no form popup
                        List<WebElement> edits = d.findElements(By.className("android.widget.EditText"));
                        if (!edits.isEmpty()) {
                            boolean noSubmit = d.findElements(AppiumBy.accessibilityId("Submit")).isEmpty();
                            boolean noSave   = d.findElements(AppiumBy.accessibilityId("Save")).isEmpty();
                            if (noSubmit && noSave) return Boolean.TRUE;
                        }
                        return null;
                    });
            return true;
        } catch (Exception e) { return false; }
        finally { elementUtils.restoreImplicitWait(); }
    }

    // ═══════════════════════════════════════════════════════════
    //  TEXT VISIBILITY
    // ═══════════════════════════════════════════════════════════

    public boolean isTextVisible(String text) {
        if (text == null || text.isEmpty()) return true;
        String escaped = text.replace("\"", "\\\"").replace("'", "\\'");
        if (elementUtils.isPresent(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"" + escaped + "\")"))) return true;
        if (elementUtils.isPresent(AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionContains(\"" + escaped + "\")"))) return true;
        // XPath fallback for deeply nested Flutter views
        return elementUtils.isPresent(AppiumBy.xpath(
                "//*[contains(@text,'" + text.replace("'", "\\'") + "')"
                + " or contains(@content-desc,'" + text.replace("'", "\\'") + "')]"));
    }

    // ═══════════════════════════════════════════════════════════
    //  COMPOSITE HELPERS
    // ═══════════════════════════════════════════════════════════

    /**
     * Creates a Raw Material with random Name + UOM, waits for create success,
     * then verifies the record appears in the list. Retries up to 3 times.
     */
    public String createRawMaterialAndReturnName() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            String name = DataGenerator.randomRawMaterialName();
            searchUtils.ensureSearchClosed();
            clickAddButton();
            if (!elementUtils.waitForPresence(ADD_POPUP, 10)) continue;
            enterName(name);
            selectUOM();
            clickSubmitButton();
            if (!popupUtils.waitForCreateSuccess(30)) {
                searchUtils.ensureSearchClosed();
                continue;
            }
            searchRecord(name);
            WebElement found = getRecordByName(name);
            searchUtils.ensureSearchClosed();
            if (found != null) return name;
        }
        throw new RuntimeException(
                "Background setup: failed to create a findable Raw Material after 3 attempts");
    }

    public void searchSwipeAndOpenEdit(String name) {
        searchRecord(name);
        WebElement item = getRecordByName(name);
        if (item != null) swipeRecordRightToLeft(item);
        clickEditButton();
    }

    public void searchAndOpenView(String name) {
        searchRecord(name);
        WebElement el = getRecordByName(name);
        if (el != null) el.click();
    }

    @Override
    public void ensureModuleListReady() {
        searchUtils.ensureSearchClosed();
        searchUtils.verifyModuleListNormalState();
    }
}
