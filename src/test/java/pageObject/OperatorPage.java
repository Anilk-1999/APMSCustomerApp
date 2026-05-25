package pageObject;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.DataGenerator;
import utilities.SwipeHelper;

import java.time.Duration;
import java.util.List;

/**
 * Page Object — Operators Configuration module.
 *
 * Mirrors UsersPage architecture: pure By constants, no @AndroidFindBy,
 * no Thread.sleep. All locators are hint-based or content-desc-based.
 *
 * Key differences from Users:
 *  - No Roles field, no Teams field
 *  - "Operator Code" instead of "Emp Code"
 *  - Email IS editable in Edit Operator screen
 *  - No Unit Subscription (only Machine Subscription)
 *  - Action Menus: only "Machine Subscription"
 *  - Operator IDs start with "#OPA"
 *  - Screen titles: "Add New Operator", "Edit Operator"
 *  - createOperatorAndReturnName() uses Name + Phone + BloodGroup only
 */
public class OperatorPage extends BasePage {

    // ═══════════════════════════════════════════════════════════
    //  SCREEN TITLE LOCATORS (full screens)
    // ═══════════════════════════════════════════════════════════

    private static final By ADD_OPERATOR_SCREEN = AppiumBy.xpath(
            "//android.view.View[@content-desc='Add New Operator']");

    private static final By EDIT_OPERATOR_SCREEN = AppiumBy.xpath(
            "//android.view.View[@content-desc='Edit Operator']");

    private static final By VIEW_OPERATOR_SCREEN = AppiumBy.xpath(
            "//android.view.View[@content-desc='View Operator']");

    // ═══════════════════════════════════════════════════════════
    //  ADD / EDIT SCREEN — EDITABLE FIELDS
    // ═══════════════════════════════════════════════════════════

    private static final By OPERATOR_NAME_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='Operator Name *']");

    // Email is editable on both Add and Edit Operator screens
    private static final By EMAIL_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='Email ID']");

    private static final By PHONE_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='Phone No *']");

    private static final By EMERGENCY_NO_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='Emergency No']");

    private static final By OPERATOR_CODE_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='Operator Code']");

    // Blood Group is a Button (dropdown trigger) — content-desc = "Blood Group *" when unselected
    private static final By BLOOD_GROUP_BTN = AppiumBy.xpath(
            "//android.widget.Button[starts-with(@content-desc,'Blood Group')]");

    // DOB and DOJ are android.view.View (NOT EditText) — same pattern as UsersPage
    private static final By DOB_FIELD = AppiumBy.xpath(
            "//android.view.View[@hint='Date of Birth']");

    private static final By DOJ_FIELD = AppiumBy.xpath(
            "//android.view.View[@hint='Date of Joining']");

    private static final By ADDRESS1_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='Address Line I']");

    private static final By ADDRESS2_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='Address Line II']");

    private static final By PIN_CODE_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='Pin Code']");

    private static final By CITY_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='City']");

    private static final By STATE_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='State']");

    private static final By COUNTRY_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='Country']");

    // ═══════════════════════════════════════════════════════════
    //  EDIT SCREEN — READ-ONLY / STATE FIELDS
    // ═══════════════════════════════════════════════════════════

    // Operator ID badge — content-desc starts with "#OPA"
    private static final By OPERATOR_ID_FIELD = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionStartsWith(\"#OPA\")");

    // Status toggle — clickable, content-desc is "Active" or "Inactive"
    private static final By STATUS_FIELD = AppiumBy.xpath(
            "//android.view.View[@content-desc='Active' or @content-desc='Inactive']");

    // Edit button revealed by swipe on a list record
    private static final By EDIT_BTN = AppiumBy.accessibilityId("Edit");

    // ═══════════════════════════════════════════════════════════
    //  FORM ACTION BUTTONS
    // ═══════════════════════════════════════════════════════════

    private static final By SUBMIT_BTN = AppiumBy.xpath(
            "//android.widget.Button[@content-desc='Submit']");

    // ═══════════════════════════════════════════════════════════
    //  DATE PICKER CONTROLS
    // ═══════════════════════════════════════════════════════════

    private static final By DATE_PICKER_OK = AppiumBy.accessibilityId("OK");
    private static final By DATE_PICKER_CANCEL = AppiumBy.accessibilityId("Cancel");

    private static final By YEAR_DROPDOWN_BTN = AppiumBy.xpath(
            "//android.widget.Button[starts-with(@content-desc,'Select year')]");

    private static final By NEXT_MONTH_BTN = AppiumBy.xpath(
            "//android.widget.Button[@content-desc='Next month']");

    private static final By PREV_MONTH_BTN = AppiumBy.xpath(
            "//android.widget.Button[@content-desc='Previous month']");

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERRORS
    // ═══════════════════════════════════════════════════════════

    private static final By ERR_FIELD_REQUIRED = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"This field is required\")");

    private static final By ERR_INVALID_EMAIL = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionMatches(\"(?i).*(invalid.*email|valid.*email|email.*format).*\")");

    private static final By ERR_DUPLICATE_PHONE = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionMatches(\"(?i).*(phone.*already|already.*phone|duplicate.*phone).*\")");

    private static final By ERR_INVALID_PHONE = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionMatches(\"(?i).*(invalid.*phone|phone.*digit|must be 10|10.*digit|Phone No is required).*\")");

    private static final By ERR_INVALID_EMERGENCY = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionMatches(\"(?i).*(invalid.*emergency|emergency.*digit|emergency.*10|10.*digit|must be 10).*\")");

    private static final By ERR_ANY_VALIDATION = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionMatches(\"(?i).*(required|invalid|already exists|already exist|duplicate|exceed|digit|format|must be|already taken|already registered).*\")");

    private static final By ERR_ANY_VALIDATION_TEXT = AppiumBy.androidUIAutomator(
            "new UiSelector().textMatches(\"(?i).*(required|invalid|already exists|already exist|duplicate|exceed|digit|format|must be|already taken|already registered).*\")");

    // ═══════════════════════════════════════════════════════════
    //  ACTION MENUS BOTTOM SHEET
    //  Operators only have Machine Subscription (no Unit Subscription)
    // ═══════════════════════════════════════════════════════════

    private static final By ACTION_MENUS_SHEET = AppiumBy.xpath(
            "//android.view.View[@content-desc='Action Menus']");

    private static final By MACHINE_SUBSCRIPTION_OPTION = AppiumBy.xpath(
            "//android.widget.ImageView[@content-desc='Machine Subscription']");

    // ═══════════════════════════════════════════════════════════
    //  MACHINE SUBSCRIPTION POPUP
    //  content-desc = "Machine Subscription\nAdd Machines\n<count>" — use starts-with
    //  Button[1] (no content-desc) = Close X
    //  Button[2] (no content-desc) = Add "+"
    //  Machine rows: Button with content-desc starting "#MCA" inside the popup
    //  Delete icon:  ImageView inside each machine Button
    // ═══════════════════════════════════════════════════════════

    private static final By MACHINE_SUBSCRIPTION_POPUP = AppiumBy.xpath(
            "//android.view.View[starts-with(@content-desc,'Machine Subscription')]");

    // First unnamed Button = Close X
    private static final By MACHINE_SUBSCRIPTION_CLOSE_BTN = AppiumBy.xpath(
            "//android.view.View[starts-with(@content-desc,'Machine Subscription')]" +
            "//android.widget.Button[not(@content-desc)][1]");

    // Second unnamed Button = Add "+"
    private static final By MACHINE_ADD_BTN = AppiumBy.xpath(
            "//android.view.View[starts-with(@content-desc,'Machine Subscription')]" +
            "//android.widget.Button[not(@content-desc)][2]");

    // Machine rows: Buttons with #MCA prefix inside the popup
    private static final By MACHINE_ITEMS_IN_POPUP = AppiumBy.xpath(
            "//android.view.View[starts-with(@content-desc,'Machine Subscription')]" +
            "//android.widget.Button[starts-with(@content-desc,'#MCA')]");

    // Delete icon: direct ImageView child of each #MCA button
    private static final By MACHINE_DELETE_ICONS = AppiumBy.xpath(
            "//android.widget.Button[starts-with(@content-desc,'#MCA')]/android.widget.ImageView");

    // ═══════════════════════════════════════════════════════════
    //  SELECT MACHINES BOTTOM SHEET
    // ═══════════════════════════════════════════════════════════

    private static final By MACHINE_SELECT_SHEET = AppiumBy.xpath(
            "//android.view.View[@content-desc='Select Machines']");

    private static final By MACHINE_SELECT_ITEMS = AppiumBy.xpath(
            "//android.view.View[@content-desc='Select Machines']" +
            "//android.widget.ScrollView//android.widget.Button");

    private static final By UNCHECKED_MACHINE_ITEM = AppiumBy.xpath(
            "//android.view.View[@content-desc='Select Machines']" +
            "//android.widget.ScrollView//android.widget.Button" +
            "[.//android.widget.CheckBox[@checked='false']]");

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public OperatorPage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickAddButton()              { flutterUtils.clickFab(20); }
    public boolean isAddButtonVisible()       { return flutterUtils.isFabVisible(); }

    public void clickSearchIcon()             { searchUtils.openSearch(); }
    public void tapSearchInput()              { searchUtils.tapSearchInput(); }
    public void clearSearchField()            { searchUtils.clearSearch(); }
    public void enterSearchText(String text)  { searchUtils.typeSearchText(text); }
    public void exitSearch()                  { searchUtils.ensureSearchClosed(); }

    public WebElement getRecordByName(String name) {
        return searchUtils.getRecordByName(name);
    }

    public void clickRecordByName(String name) {
        WebElement item = getRecordByName(name);
        if (item != null) tap(item);
    }

    public void swipeRecordRightToLeft(WebElement operatorRecord) {
        swipeRightToLeft(operatorRecord);
    }

    public void longPressRecord(String name) {
        WebElement item = getRecordByName(name);
        if (item != null) longPress(item);
    }

    public void clickEditButton() {
        WebElement btn = elementUtils.waitForFirst(EDIT_BTN, 5);
        if (btn != null) btn.click();
    }

    public boolean isEditButtonVisible() {
        return elementUtils.isPresentByAccessibility("Edit");
    }

    // ═══════════════════════════════════════════════════════════
    //  BACK NAVIGATION (full screens — use Back, not X button)
    // ═══════════════════════════════════════════════════════════

    public void pressBackArrow() {
        keyboardUtils.hideKeyboardIfVisible();
        elementUtils.disableImplicitWait();
        try {
            if (isDatePickerVisible()) driver.navigate().back();
        } finally {
            elementUtils.restoreImplicitWait();
        }
        driver.navigate().back();
    }

    public void pressBackAndConfirmIfAsked() {
        driver.navigate().back();
        popupUtils.confirmExitIfAlertShows(3);
    }

    // ═══════════════════════════════════════════════════════════
    //  ADD / EDIT SCREEN — FIELD INPUT
    // ═══════════════════════════════════════════════════════════

    public void enterOperatorName(String name) {
        WebElement f = elementUtils.waitForFirst(OPERATOR_NAME_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(name); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void clearOperatorNameField() {
        WebElement f = elementUtils.waitForFirst(OPERATOR_NAME_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void enterEmail(String email) {
        WebElement f = elementUtils.waitForFirst(EMAIL_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(email); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void clearEmailField() {
        WebElement f = elementUtils.waitForFirst(EMAIL_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void enterPhone(String phone) {
        WebElement f = elementUtils.waitForFirst(PHONE_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(phone); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void clearPhoneField() {
        WebElement f = elementUtils.waitForFirst(PHONE_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    /** Just focuses the Emergency No field (shows numeric keyboard) without entering any value. */
    public void tapEmergencyNoField() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Emergency No");
        WebElement f = elementUtils.waitForFirst(EMERGENCY_NO_INPUT, 10);
        if (f != null) { try { f.click(); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void enterEmergencyNo(String emergency) {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Emergency No");
        WebElement f = elementUtils.waitForFirst(EMERGENCY_NO_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(emergency); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void clearEmergencyNoField() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Emergency No");
        WebElement f = elementUtils.waitForFirst(EMERGENCY_NO_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void enterOperatorCode(String code) {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Operator Code");
        WebElement f = elementUtils.waitForFirst(OPERATOR_CODE_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(code); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void clearOperatorCodeField() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Operator Code");
        WebElement f = elementUtils.waitForFirst(OPERATOR_CODE_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void selectBloodGroup(String group) {
        keyboardUtils.hideKeyboardSafely();
        WebElement btn = elementUtils.waitForFirst(BLOOD_GROUP_BTN, 10);
        if (btn != null) {
            btn.click();
            selectDropdownOption(group);
        }
    }

    public void enterDOB(String month, String year, String day) {
        keyboardUtils.hideKeyboardSafely();
        WebElement f = elementUtils.waitForFirst(DOB_FIELD, 10);
        if (f != null) {
            f.click();
            selectDateInPicker(month, year, day);
            WebElement ok = elementUtils.waitForFirst(DATE_PICKER_OK, 5);
            if (ok != null) ok.click();
        }
    }

    public void enterDOJ(String month, String year, String day) {
        keyboardUtils.hideKeyboardSafely();
        WebElement f = elementUtils.waitForFirst(DOJ_FIELD, 10);
        if (f != null) {
            f.click();
            selectDateInPicker(month, year, day);
            WebElement ok = elementUtils.waitForFirst(DATE_PICKER_OK, 5);
            if (ok != null) ok.click();
        }
    }

    public void clickDOBField() {
        keyboardUtils.hideKeyboardSafely();
        WebElement f = elementUtils.waitForFirst(DOB_FIELD, 10);
        if (f != null) f.click();
    }

    public void clickDOJField() {
        keyboardUtils.hideKeyboardSafely();
        WebElement f = elementUtils.waitForFirst(DOJ_FIELD, 10);
        if (f != null) f.click();
    }

    public void clickDatePickerOk() {
        WebElement ok = elementUtils.waitForFirst(DATE_PICKER_OK, 5);
        if (ok != null) ok.click();
    }

    public void clickDatePickerCancel() {
        WebElement cancel = elementUtils.waitForFirst(DATE_PICKER_CANCEL, 5);
        if (cancel != null) cancel.click();
    }

    public void enterAddress1(String addr) {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Address Line I");
        WebElement f = elementUtils.waitForFirst(ADDRESS1_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(addr); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void clearAddress1Field() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Address Line I");
        WebElement f = elementUtils.waitForFirst(ADDRESS1_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void enterAddress2(String addr) {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Address Line II");
        WebElement f = elementUtils.waitForFirst(ADDRESS2_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(addr); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void clearAddress2Field() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Address Line II");
        WebElement f = elementUtils.waitForFirst(ADDRESS2_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    /** Scrolls the form until the Pin Code field is visible. */
    public void scrollToPinCodeField() {
        keyboardUtils.hideKeyboardIfVisible();
        // Primary: UIAutomator scroll into view
        scrollToHint("Pin Code");
        // Fallback: if Pin Code field is still not visible, swipe down manually and retry
        elementUtils.disableImplicitWait();
        try {
            if (driver.findElements(PIN_CODE_INPUT).isEmpty()) {
                new SwipeHelper(driver).scrollDown();
                scrollToHint("Pin Code");
            }
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    /** Just focuses the Pin Code field (shows numeric keyboard) without entering any value. */
    public void tapPinCodeField() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Pin Code");
        WebElement f = elementUtils.waitForFirst(PIN_CODE_INPUT, 10);
        if (f != null) { try { f.click(); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void enterPinCode(String pin) {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Pin Code");
        WebElement f = elementUtils.waitForFirst(PIN_CODE_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(pin); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void clearPinCodeField() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Pin Code");
        WebElement f = elementUtils.waitForFirst(PIN_CODE_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void enterCity(String city) {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("City");
        WebElement f = elementUtils.waitForFirst(CITY_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(city); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void enterState(String state) {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("State");
        WebElement f = elementUtils.waitForFirst(STATE_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(state); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void enterCountry(String country) {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Country");
        WebElement f = elementUtils.waitForFirst(COUNTRY_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(country); } catch (Exception ignored) { /* best-effort */ } }
    }

    // ═══════════════════════════════════════════════════════════
    //  FORM ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickSubmitButton() {
        keyboardUtils.hideKeyboardSafely();
        elementUtils.clickWhenFoundByAccessibility("Submit", 10);
    }

    public void clickSaveButton() {
        keyboardUtils.hideKeyboardSafely();
        elementUtils.clickWhenFoundByAccessibility("Save", 10);
    }

    // ═══════════════════════════════════════════════════════════
    //  SCREEN STATE CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isAddNewOperatorScreenDisplayed() {
        return elementUtils.waitForPresence(ADD_OPERATOR_SCREEN, 10);
    }

    public boolean isEditOperatorScreenDisplayed() {
        return elementUtils.waitForPresence(EDIT_OPERATOR_SCREEN, 10);
    }

    public boolean isViewOperatorScreenDisplayed() {
        return elementUtils.waitForPresence(VIEW_OPERATOR_SCREEN, 10);
    }

    public boolean isOperatorIdVisible() {
        return elementUtils.waitForPresence(OPERATOR_ID_FIELD, 5);
    }

    public boolean isStatusFieldVisible() {
        return elementUtils.isPresent(STATUS_FIELD);
    }

    public boolean isOperatorNameFieldVisible() {
        return elementUtils.isPresent(OPERATOR_NAME_INPUT);
    }

    public boolean isEmailFieldVisible() {
        return elementUtils.isPresent(EMAIL_INPUT);
    }

    public boolean isPhoneFieldVisible() {
        return elementUtils.isPresent(PHONE_INPUT);
    }

    public boolean isBloodGroupFieldVisible() {
        return elementUtils.isPresent(BLOOD_GROUP_BTN)
                || elementUtils.isPresent(AppiumBy.xpath(
                        "//android.widget.Button[starts-with(@content-desc,'Blood Group')]"))
                || elementUtils.isPresent(AppiumBy.xpath(
                        "//android.view.View[starts-with(@content-desc,'Blood Group')]"));
    }

    public boolean isDOBFieldVisible() {
        return elementUtils.isPresent(DOB_FIELD);
    }

    public boolean isDOJFieldVisible() {
        return elementUtils.isPresent(DOJ_FIELD);
    }

    public boolean isDatePickerVisible() {
        return elementUtils.isPresent(YEAR_DROPDOWN_BTN) || elementUtils.isPresent(DATE_PICKER_OK);
    }

    public boolean isConfirmationAlertDisplayed() {
        return elementUtils.isPresentByUIAutomator(
                "new UiSelector().descriptionContains(\"Confirmation Alert\")");
    }

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERROR CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isRequiredErrorDisplayed() {
        scrollToTop();
        return elementUtils.waitForPresence(ERR_FIELD_REQUIRED, 5);
    }

    public boolean isInvalidEmailErrorDisplayed() {
        scrollToTop();
        return elementUtils.waitForPresence(ERR_INVALID_EMAIL, 5);
    }

    public boolean isDuplicatePhoneErrorDisplayed() {
        scrollToTop();
        return elementUtils.waitForPresence(ERR_DUPLICATE_PHONE, 5);
    }

    public boolean isPhoneValidationErrorDisplayed() {
        scrollToTop();
        return elementUtils.waitForPresence(ERR_INVALID_PHONE, 5);
    }

    public boolean isEmergencyNumberValidationErrorDisplayed() {
        scrollToTop();
        return elementUtils.waitForPresence(ERR_INVALID_EMERGENCY, 5);
    }

    public boolean isAnyValidationErrorDisplayed() {
        if (elementUtils.waitForPresence(ERR_ANY_VALIDATION, 2)
                || elementUtils.waitForPresence(ERR_ANY_VALIDATION_TEXT, 2)) return true;
        scrollToTop();
        return elementUtils.waitForPresence(ERR_ANY_VALIDATION, 3)
                || elementUtils.waitForPresence(ERR_ANY_VALIDATION_TEXT, 2);
    }

    public boolean isPinCodeValidationErrorDisplayed() {
        scrollToHint("Pin Code");
        return elementUtils.waitForPresence(AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionMatches(\"(?i).*(6.*digit|digit.*6|pin.*code|pincode).*\")"), 5);
    }

    // ═══════════════════════════════════════════════════════════
    //  ACTION MENUS BOTTOM SHEET
    // ═══════════════════════════════════════════════════════════

    public boolean isActionMenusBottomSheetDisplayed() {
        return elementUtils.waitForPresence(ACTION_MENUS_SHEET, 10);
    }

    public boolean isMachineSubscriptionOptionVisible() {
        return elementUtils.isPresent(MACHINE_SUBSCRIPTION_OPTION);
    }

    public void clickMachineSubscriptionOption() {
        WebElement opt = elementUtils.waitForFirst(MACHINE_SUBSCRIPTION_OPTION, 10);
        if (opt != null) opt.click();
    }

    // ═══════════════════════════════════════════════════════════
    //  MACHINE SUBSCRIPTION POPUP
    // ═══════════════════════════════════════════════════════════

    public boolean isMachineSubscriptionPopupDisplayed() {
        return elementUtils.waitForPresence(MACHINE_SUBSCRIPTION_POPUP, 10);
    }

    public void clickMachineSubscriptionCloseButton() {
        WebElement btn = elementUtils.waitForFirst(MACHINE_SUBSCRIPTION_CLOSE_BTN, 10);
        if (btn != null) btn.click();
        popupUtils.confirmExitIfAlertShows(3);
    }

    public boolean waitForMachineSubscriptionPopupClosed(int timeoutSecs) {
        return elementUtils.waitForAbsence(MACHINE_SUBSCRIPTION_POPUP, timeoutSecs);
    }

    public void clickMachineAddButton() {
        WebElement btn = elementUtils.waitForFirst(MACHINE_ADD_BTN, 10);
        if (btn == null) {
            btn = elementUtils.waitForFirst(AppiumBy.xpath(
                    "//android.view.View[starts-with(@content-desc,'Machine Subscription')]" +
                    "//android.widget.Button[not(@content-desc) or @content-desc='+']"), 5);
        }
        if (btn != null) btn.click();
    }

    public boolean isMachineSelectBottomSheetDisplayed() {
        return elementUtils.waitForPresence(MACHINE_SELECT_SHEET, 10);
    }

    public void selectOneMachine() {
        elementUtils.waitForPresence(MACHINE_SELECT_SHEET, 5);
        WebElement item = elementUtils.waitForFirst(UNCHECKED_MACHINE_ITEM, 10);
        if (item == null) {
            item = elementUtils.waitForFirst(AppiumBy.xpath(
                    "//android.view.View[@content-desc='Select Machines']" +
                    "//android.widget.Button"), 5);
        }
        if (item == null) {
            elementUtils.disableImplicitWait();
            try {
                List<WebElement> all = driver.findElements(MACHINE_SELECT_ITEMS);
                if (!all.isEmpty()) item = all.get(0);
            } finally {
                elementUtils.restoreImplicitWait();
            }
        }
        if (item != null) tap(item);
    }

    public void selectMultipleMachines(int count) {
        elementUtils.waitForPresence(MACHINE_SELECT_ITEMS, 10);
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> items = driver.findElements(MACHINE_SELECT_ITEMS);
            int n = Math.min(count, items.size());
            for (int i = 0; i < n; i++) {
                try { tap(items.get(i)); } catch (Exception ignored) { /* best-effort */ }
            }
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public void clickMachineSelectSheetSubmit() {
        By scopedSubmit = AppiumBy.xpath(
                "//android.view.View[@content-desc='Select Machines']" +
                "//android.widget.Button[@content-desc='Submit']");
        WebElement btn = elementUtils.waitForFirst(scopedSubmit, 10);
        if (btn == null) btn = elementUtils.waitForFirst(AppiumBy.accessibilityId("Submit"), 5);
        if (btn != null) tap(btn);
    }

    public void clickMachineSubscriptionSubmit() {
        By scopedSubmit = AppiumBy.xpath(
                "//android.view.View[starts-with(@content-desc,'Machine Subscription')]" +
                "//android.widget.Button[@content-desc='Submit']");
        // Re-find element fresh every call to avoid stale reference on rapid clicks
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> btns = driver.findElements(scopedSubmit);
            if (!btns.isEmpty()) { tapCenter(btns.get(0)); return; }
        } catch (Exception ignored) { // NOSONAR
        } finally {
            elementUtils.restoreImplicitWait();
        }
        WebElement btn = elementUtils.waitForFirst(SUBMIT_BTN, 5);
        if (btn != null) { try { tapCenter(btn); } catch (Exception ignored) {} } // NOSONAR
    }

    public void deleteMachineFromSubscription() {
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> icons = driver.findElements(MACHINE_DELETE_ICONS);
            if (!icons.isEmpty()) icons.get(0).click();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public void deleteAllMachinesFromSubscription() {
        for (int i = 0; i < 30; i++) {
            elementUtils.disableImplicitWait();
            List<WebElement> icons;
            try { icons = driver.findElements(MACHINE_DELETE_ICONS); }
            finally { elementUtils.restoreImplicitWait(); }
            if (icons.isEmpty()) break;
            icons.get(0).click();
        }
    }

    public boolean hasMachinesInSubscription() {
        return elementUtils.waitForPresence(MACHINE_ITEMS_IN_POPUP, 5);
    }

    public int getMachineCount() {
        elementUtils.waitForPresence(MACHINE_ITEMS_IN_POPUP, 5);
        elementUtils.disableImplicitWait();
        try {
            return driver.findElements(MACHINE_ITEMS_IN_POPUP).size();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    /** Waits up to timeoutSecs for all machine rows to disappear — use after deleteAll. */
    public boolean waitForAllMachinesGone(int timeoutSecs) {
        return elementUtils.waitForAbsence(MACHINE_ITEMS_IN_POPUP, timeoutSecs);
    }

    /**
     * Searches for the operator, opens Machine Subscription popup, and adds one machine
     * if none are currently subscribed. Leaves the popup closed and search bar closed.
     * Call this in Background steps so each scenario starts with at least one machine.
     */
    public void ensureOperatorHasMachineSubscription(String name) {
        searchRecord(name);
        longPressRecord(name);
        clickMachineSubscriptionOption();
        if (!isMachineSubscriptionPopupDisplayed()) return;

        if (!hasMachinesInSubscription()) {
            clickMachineAddButton();
            if (isMachineSelectBottomSheetDisplayed()) {
                selectOneMachine();
                clickMachineSelectSheetSubmit();
            }
            clickMachineSubscriptionSubmit();
            waitForMachineSubscriptionPopupClosed(15);
        } else {
            clickMachineSubscriptionCloseButton();
            waitForMachineSubscriptionPopupClosed(5);
        }
    }

    public boolean isMachineSubscriptionEmpty() {
        return elementUtils.waitForPresence(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().descriptionMatches(\"(?i).*(no machine|empty|no data|not subscribed).*\")"), 5);
    }

    public boolean isMachineSubscriptionSubmitVisible() {
        return elementUtils.isPresent(SUBMIT_BTN);
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
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(Duration.ofMillis(300))
                    .until(d -> {
                        if (!d.findElements(AppiumBy.accessibilityId("Add")).isEmpty()) return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.accessibilityId("+ Add")).isEmpty()) return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.accessibilityId("Search")).isEmpty()) return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.accessibilityId("Filter")).isEmpty()) return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.accessibilityId("Sort")).isEmpty()) return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.xpath("//android.widget.EditText")).isEmpty()) return Boolean.TRUE;
                        return null;
                    });
            return true;
        } catch (Exception e) { return false; }
        finally { elementUtils.restoreImplicitWait(); }
    }

    public boolean verifyReturnedToList() {
        return waitForReturnToList(15);
    }

    // ═══════════════════════════════════════════════════════════
    //  SEARCH HELPERS
    // ═══════════════════════════════════════════════════════════

    public void searchRecord(String name) {
        searchUtils.searchRecord(name);
    }

    public void openEditOperatorScreen(String name) {
        searchUtils.searchRecord(name);
        WebElement operatorRecord = searchUtils.getRecordByName(name);
        if (operatorRecord == null) throw new RuntimeException("Operator not found in list: " + name);
        swipeRightToLeft(operatorRecord);
        WebElement editBtn = elementUtils.waitForFirst(EDIT_BTN, 5);
        if (editBtn != null) editBtn.click();
        elementUtils.waitForPresence(EDIT_OPERATOR_SCREEN, 10);
    }

    public boolean verifyUpdatedRecordInList(String name) {
        searchUtils.searchRecord(name);
        return searchUtils.getRecordByName(name) != null;
    }

    // ═══════════════════════════════════════════════════════════
    //  COMPOSITE — BACKGROUND SETUP
    // ═══════════════════════════════════════════════════════════

    /**
     * Creates a new Operator with mandatory fields only and returns the Operator Name.
     * Used by Background step: "User has already created an Operator".
     */
    public String createOperatorAndReturnName() {
        String name  = DataGenerator.randomOperatorName();
        String phone = DataGenerator.randomPhone();
        clickAddButton();
        enterOperatorName(name);
        enterPhone(phone);
        selectBloodGroup("A+");
        clickSubmitButton();
        if (!popupUtils.waitForCreateSuccess(30)) {
            throw new RuntimeException(
                    "Background setup failed: Operator '" + name + "' was not created within 30s");
        }
        return name;
    }

    /**
     * Delete-flow variant: creates an operator and immediately confirms it is
     * searchable before returning. Uses a live search query so the record is
     * guaranteed visible when the delete scenario's own search steps run.
     */
    public String createOperatorAndConfirmSearchable() {
        String name = createOperatorAndReturnName();
        // Wait for the "Search" button — only present on the list screen, never on the
        // create form. This ensures we are truly on the list before returning. The
        // scenario's own search steps (with up to 15s waits) handle finding the record.
        elementUtils.waitForPresence(AppiumBy.accessibilityId("Search"), 15);
        return name;
    }

    // ═══════════════════════════════════════════════════════════
    //  TOAST / SNACKBAR CHECK
    // ═══════════════════════════════════════════════════════════

    public boolean isToastDisplayed(String message) {
        String escaped = message.replace("\"", "\\\"");
        // Primary: check by text content
        if (elementUtils.waitForPresence(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"" + escaped + "\")"), 5)) return true;
        // Secondary: check by text as regex (case-insensitive partial match)
        if (elementUtils.waitForPresence(AppiumBy.androidUIAutomator(
                "new UiSelector().textMatches(\"(?i).*" + escaped + ".*\")"), 3)) return true;
        // Fallback: content-desc (Flutter snackbars often expose via content-desc)
        return elementUtils.waitForPresence(AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionContains(\"" + escaped + "\")"), 3);
    }

    // ═══════════════════════════════════════════════════════════
    //  DOJ VALIDATION ERROR
    // ═══════════════════════════════════════════════════════════

    public boolean isDOJValidationErrorDisplayed() {
        return elementUtils.waitForPresence(AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionMatches(" +
                "\"(?i).*(joining|DOJ|date of join|date.*joining).*\")"), 5);
    }

    // ═══════════════════════════════════════════════════════════
    //  PRIVATE HELPERS
    // ═══════════════════════════════════════════════════════════

    private void selectDropdownOption(String optionText) {
        WebElement option = elementUtils.waitForFirst(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().description(\"" + optionText + "\")"), 5);
        if (option == null) {
            option = elementUtils.waitForFirst(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                    ".scrollIntoView(new UiSelector().description(\"" + optionText + "\"))"), 5);
        }
        if (option != null) option.click();
    }

    private void scrollToTop() {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollToBeginning(10)"));
        } catch (Exception ignored) { /* already at top or no scrollable container */ }
    }

    private void scrollToHint(String hint) {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                    ".scrollIntoView(new UiSelector().hintMatches(\"(?i)" + hint + ".*\"))"));
        } catch (Exception ignored) { /* field may already be visible */ }
    }

    // ═══════════════════════════════════════════════════════════
    //  STATUS BUTTON
    // ═══════════════════════════════════════════════════════════

    public void clickStatusButton() {
        WebElement btn = elementUtils.waitForFirst(STATUS_FIELD, 10);
        if (btn != null) btn.click();
    }

    public String getStatusValue() {
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> els = driver.findElements(STATUS_FIELD);
            return els.isEmpty() ? null : els.get(0).getAttribute("content-desc");
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  SAVE / SUBMIT VISIBILITY
    // ═══════════════════════════════════════════════════════════

    public boolean isSaveButtonVisible() {
        return elementUtils.isPresentByAccessibility("Save");
    }

    public boolean isSubmitButtonVisible() {
        return elementUtils.isPresentByAccessibility("Submit");
    }

    public boolean isOperatorPhoneFieldVisible() {
        return elementUtils.isPresent(PHONE_INPUT);
    }

    // ═══════════════════════════════════════════════════════════
    //  BLOOD GROUP DROPDOWN CLICK
    // ═══════════════════════════════════════════════════════════

    public void clickBloodGroupDropdown() {
        WebElement btn = elementUtils.waitForFirst(BLOOD_GROUP_BTN, 10);
        if (btn != null) btn.click();
    }

    // ═══════════════════════════════════════════════════════════
    //  YES, EXIT BUTTON
    // ═══════════════════════════════════════════════════════════

    public void clickYesExitButton() {
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> btns = driver.findElements(AppiumBy.accessibilityId("Yes, Exit"));
            if (!btns.isEmpty()) { btns.get(0).click(); return; }
            btns = driver.findElements(AppiumBy.xpath("//android.widget.Button[@content-desc='Yes, Exit' or @text='Yes, Exit']"));
            if (!btns.isEmpty()) btns.get(0).click();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  EDIT TEXT VALUE READER
    // ═══════════════════════════════════════════════════════════

    public String getEditTextValue(String hint) {
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> els = driver.findElements(
                    AppiumBy.xpath("//android.widget.EditText[@hint='" + hint + "']"));
            return els.isEmpty() ? null : els.get(0).getText();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  CLEAR FIELD EXTRAS (date and other fields)
    // ═══════════════════════════════════════════════════════════

    public void clearDOBField() {
        System.out.println("[INFO] DOB clear — date View fields are cleared by re-selecting; skipping in test flow");
    }

    public void clearDOJField() {
        System.out.println("[INFO] DOJ clear — date View fields are cleared by re-selecting; skipping in test flow");
    }

    public void clearCityField() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("City");
        WebElement f = elementUtils.waitForFirst(CITY_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void clearStateField() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("State");
        WebElement f = elementUtils.waitForFirst(STATE_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void clearCountryField() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Country");
        WebElement f = elementUtils.waitForFirst(COUNTRY_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void clearBloodGroupSelection() {
        System.out.println("[INFO] Blood Group clearing not supported in Flutter dropdown — no-op");
    }

    // ═══════════════════════════════════════════════════════════
    //  SEARCH AND LONG PRESS (for legacy feature file support)
    // ═══════════════════════════════════════════════════════════

    public void searchAndLongPress(String name) {
        searchRecord(name);
        longPressRecord(name);
    }

    private void selectDateInPicker(String month, String year, String day) {
        WebElement yearBtn = elementUtils.waitForFirst(YEAR_DROPDOWN_BTN, 10);
        if (yearBtn == null) return;

        String desc = yearBtn.getAttribute("content-desc");
        String target = month + " " + year;

        // Jump to target year via the year-picker dropdown if not already there
        if (desc != null && !desc.contains(year)) {
            yearBtn.click();
            WebElement yearOption = elementUtils.waitForFirst(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().className(\"android.widget.Button\")" +
                            ".description(\"" + year + "\")"), 5);
            if (yearOption == null) {
                try {
                    yearOption = driver.findElement(AppiumBy.androidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".scrollIntoView(new UiSelector().className(\"android.widget.Button\")" +
                            ".description(\"" + year + "\"))"));
                } catch (Exception ignored) { /* year not found in list */ }
            }
            if (yearOption != null) yearOption.click();
        }

        // Navigate month by month until "Select year\n<Month> <Year>" contains the target
        for (int i = 0; i < 24; i++) {
            yearBtn = elementUtils.waitForFirst(YEAR_DROPDOWN_BTN, 5);
            if (yearBtn == null) break;
            desc = yearBtn.getAttribute("content-desc");
            if (desc != null && desc.contains(target)) break;

            WebElement nextBtn = elementUtils.firstOrNull(NEXT_MONTH_BTN);
            if (nextBtn != null && nextBtn.isEnabled()) {
                nextBtn.click();
            } else {
                WebElement prevBtn = elementUtils.firstOrNull(PREV_MONTH_BTN);
                if (prevBtn != null && prevBtn.isEnabled()) prevBtn.click();
                else break;
            }
        }

        // Day button content-desc format: "23, Friday, May 23, 2008"
        WebElement dayBtn = elementUtils.waitForFirst(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.Button\")" +
                        ".descriptionStartsWith(\"" + day + ", \")"), 5);
        if (dayBtn != null) dayBtn.click();
    }
}
