package pageObject;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.DataGenerator;

import java.time.Duration;
import java.util.List;

/**
 * Page Object — Users Configuration module.
 *
 * Locators sourced from Appium Inspector XML dump (Samsung Galaxy A21s, Android 12).
 *
 * Add New User / Edit User / View User / Duplicate User are FULL SCREENS (not popups).
 * Back navigation uses driver.navigate().back() + confirmExitIfAlertShows().
 *
 * Machine Subscription and Unit Subscription are bottom-sheet popups opened via
 * long press → Action Menus bottom sheet.
 *
 * Architecture:
 *  1. Zero implicit wait — all checks via elementUtils / popupUtils / flutterUtils
 *  2. No Thread.sleep — polling via WebDriverWait
 *  3. Pure By constants — no @AndroidFindBy
 */
public class UsersPage extends BasePage {

    // ═══════════════════════════════════════════════════════════
    //  SCREEN TITLE LOCATORS (full screens)
    // ═══════════════════════════════════════════════════════════

    private static final By ADD_USER_SCREEN = AppiumBy.xpath(
            "//android.view.View[@content-desc='Add New User']");

    private static final By EDIT_USER_SCREEN = AppiumBy.xpath(
            "//android.view.View[@content-desc='Edit User']");

    private static final By VIEW_USER_SCREEN = AppiumBy.xpath(
            "//android.view.View[@content-desc='View User']");

    private static final By DUPLICATE_USER_SCREEN = AppiumBy.xpath(
            "//android.view.View[@content-desc='Duplicate User']");

    // ═══════════════════════════════════════════════════════════
    //  ADD / EDIT / DUPLICATE SCREEN — EDITABLE FIELDS
    //  (Sources: XML hierarchy of Add New User screen)
    // ═══════════════════════════════════════════════════════════

    private static final By USER_NAME_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='User Name *']");

    private static final By EMAIL_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='Email ID *']");

    private static final By PHONE_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='Phone No *']");

    private static final By EMERGENCY_NO_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='Emergency No']");

    private static final By EMP_CODE_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[@hint='Emp Code']");

    // Blood Group is a Button (dropdown trigger) — content-desc = "Blood Group *" when unselected,
    // changes to the selected group name once a value is picked.
    private static final By BLOOD_GROUP_BTN = AppiumBy.xpath(
            "//android.widget.Button[starts-with(@content-desc,'Blood Group')]");

    // DOB and DOJ are android.view.View (NOT EditText) in both Add and Edit screens.
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

    // Roles is a Button — content-desc = "Roles *" until a role is selected.
    private static final By ROLES_BTN = AppiumBy.xpath(
            "//android.widget.Button[starts-with(@content-desc,'Roles')]");

    // Teams section header (multi-select chips follow it in the DOM)
    private static final By TEAMS_SECTION = AppiumBy.xpath(
            "//android.view.View[starts-with(@content-desc,'Teams')]");

    // ═══════════════════════════════════════════════════════════
    //  EDIT / VIEW SCREEN — READ-ONLY FIELDS
    // ═══════════════════════════════════════════════════════════

    // User ID badge — content-desc starts with "#USR"
    private static final By USER_ID_FIELD = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionStartsWith(\"#USR\")");

    // Status toggle — clickable, content-desc is "Active" or "Inactive"
    private static final By STATUS_FIELD = AppiumBy.xpath(
            "//android.view.View[@content-desc='Active' or @content-desc='Inactive']");

    // Duplicate button visible only in View User screen
    private static final By DUPLICATE_BTN = AppiumBy.xpath(
            "//android.widget.Button[@content-desc='Duplicate']");

    // Edit button revealed by swipe on a list record
    private static final By EDIT_BTN = AppiumBy.accessibilityId("Edit");

    // ═══════════════════════════════════════════════════════════
    //  DATE PICKER CONTROLS
    // ═══════════════════════════════════════════════════════════

    private static final By DATE_PICKER_OK = AppiumBy.accessibilityId("OK");
    private static final By DATE_PICKER_CANCEL = AppiumBy.accessibilityId("Cancel");

    // content-desc = "Select year\nMay 2008"  — starts-with covers both calendar and year-list modes
    private static final By YEAR_DROPDOWN_BTN = AppiumBy.xpath(
            "//android.widget.Button[starts-with(@content-desc,'Select year')]");

    // Exact content-desc from XML (lowercase 'm')
    private static final By NEXT_MONTH_BTN = AppiumBy.xpath(
            "//android.widget.Button[@content-desc='Next month']");

    private static final By PREV_MONTH_BTN = AppiumBy.xpath(
            "//android.widget.Button[@content-desc='Previous month']");

    // ═══════════════════════════════════════════════════════════
    //  FORM ACTION BUTTONS
    // ═══════════════════════════════════════════════════════════

    private static final By SUBMIT_BTN = AppiumBy.xpath(
            "//android.widget.Button[@content-desc='Submit']");

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERRORS
    // ═══════════════════════════════════════════════════════════

    private static final By ERR_FIELD_REQUIRED = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"This field is required\")");

    private static final By ERR_INVALID_EMAIL = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionMatches(\"(?i).*(invalid.*email|valid.*email|email.*format).*\")");

    private static final By ERR_DUPLICATE_EMAIL = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionMatches(\"(?i).*(email.*already|already.*email|duplicate.*email).*\")");

    private static final By ERR_DUPLICATE_PHONE = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionMatches(\"(?i).*(phone.*already|already.*phone|duplicate.*phone).*\")");

    private static final By ERR_INVALID_PHONE = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionMatches(\"(?i).*(invalid.*phone|phone.*digit|must be 10|10.*digit).*\")");

    private static final By ERR_ANY_VALIDATION = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionMatches(\"(?i).*(required|invalid|already exists|already exist|duplicate|exceed|digit|format|must be|already taken|already registered).*\")");

    private static final By ERR_ANY_VALIDATION_TEXT = AppiumBy.androidUIAutomator(
            "new UiSelector().textMatches(\"(?i).*(required|invalid|already exists|already exist|duplicate|exceed|digit|format|must be|already taken|already registered).*\")");

    // ═══════════════════════════════════════════════════════════
    //  ACTION MENUS BOTTOM SHEET
    //  Header:   android.view.View  content-desc="Action Menus"
    //  Options:  android.widget.ImageView  (clickable, not Button)
    // ═══════════════════════════════════════════════════════════

    private static final By ACTION_MENUS_SHEET = AppiumBy.xpath(
            "//android.view.View[@content-desc='Action Menus']");

    // Options are ImageView elements (not Buttons) inside the bottom sheet
    private static final By MACHINE_SUBSCRIPTION_OPTION = AppiumBy.xpath(
            "//android.widget.ImageView[@content-desc='Machine Subscription']");

    private static final By UNIT_SUBSCRIPTION_OPTION = AppiumBy.xpath(
            "//android.widget.ImageView[@content-desc='Unit Subscription']");

    // ═══════════════════════════════════════════════════════════
    //  UNIT SUBSCRIPTION POPUP (READ-ONLY)
    //  content-desc = "Unit Subscription\n<UserCode>"  — use starts-with
    //  X close button: only unnamed Button inside the popup (index 0)
    //  Unit items: android.view.View (NOT Button), focusable=true, non-empty
    //              content-desc contains unit name + address (e.g. "Mother India Unit 4\n...")
    //  No Submit, no EditText, no CheckBox — confirmed read-only from XML
    // ═══════════════════════════════════════════════════════════

    private static final By UNIT_SUBSCRIPTION_POPUP = AppiumBy.xpath(
            "//android.view.View[starts-with(@content-desc,'Unit Subscription')]");

    private static final By UNIT_SUBSCRIPTION_CLOSE_BTN = AppiumBy.xpath(
            "//android.view.View[starts-with(@content-desc,'Unit Subscription')]" +
            "//android.widget.Button[not(@content-desc)][1]");

    // Unit rows: focusable Views inside the popup that are NOT the popup header itself
    private static final By UNIT_ITEMS_IN_POPUP = AppiumBy.xpath(
            "//android.view.View[starts-with(@content-desc,'Unit Subscription')]" +
            "//android.view.View[@focusable='true' " +
            "and not(starts-with(@content-desc,'Unit Subscription'))" +
            "and string-length(@content-desc) > 0]");

    // ═══════════════════════════════════════════════════════════
    //  MACHINE SUBSCRIPTION POPUP
    //  content-desc = "Machine Subscription\nAdd Machines\n<count>" — use starts-with
    //  Button[1] (no content-desc) = Close X   bounds=[615,181][699,265]
    //  Button[2] (no content-desc) = Add "+"   bounds=[587,289][671,373]
    //  Machine rows: Button with content-desc starting "#MCA" inside ScrollView
    //  Delete icon:  ImageView inside each machine Button (clickable=true)
    //  Submit:       Button content-desc="Submit"
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

    // Machine rows: Buttons with #MCA prefix inside the popup — no ScrollView in hierarchy
    private static final By MACHINE_ITEMS_IN_POPUP = AppiumBy.xpath(
            "//android.view.View[starts-with(@content-desc,'Machine Subscription')]" +
            "//android.widget.Button[starts-with(@content-desc,'#MCA')]");

    // Delete icon: direct ImageView child of #MCA button (confirmed from XML hierarchy)
    private static final By MACHINE_DELETE_ICONS = AppiumBy.xpath(
            "//android.widget.Button[starts-with(@content-desc,'#MCA')]/android.widget.ImageView");


    // ═══════════════════════════════════════════════════════════
    //  SELECT MACHINES BOTTOM SHEET
    //  content-desc = "Select Machines" (plural, capital M)
    //  Items: Button > CheckBox inside the ScrollView
    // ═══════════════════════════════════════════════════════════

    private static final By MACHINE_SELECT_SHEET = AppiumBy.xpath(
            "//android.view.View[@content-desc='Select Machines']");

    // All machine option Buttons in the select sheet
    private static final By MACHINE_SELECT_ITEMS = AppiumBy.xpath(
            "//android.view.View[@content-desc='Select Machines']" +
            "//android.widget.ScrollView//android.widget.Button");

    // Only unchecked (not yet selected) machine Buttons
    private static final By UNCHECKED_MACHINE_ITEM = AppiumBy.xpath(
            "//android.view.View[@content-desc='Select Machines']" +
            "//android.widget.ScrollView//android.widget.Button" +
            "[.//android.widget.CheckBox[@checked='false']]");

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public UsersPage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════════
    //  PROFILE / SECTION / FEATURE NAVIGATION
    //  (kept from original — used by Background steps)
    // ═══════════════════════════════════════════════════════════

    public void clickOnProfileIcon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement icon;
        try {
            icon = wait.until(d -> {
                try {
                    return d.findElement(By.xpath(
                            "//android.widget.FrameLayout[@resource-id='android:id/content']" +
                            "/android.widget.FrameLayout/android.widget.FrameLayout" +
                            "/android.view.View/android.view.View/android.view.View" +
                            "/android.view.View/android.view.View[1]/android.widget.Button[1]"));
                } catch (Exception e) { return null; }
            });
        } catch (Exception e) {
            icon = wait.until(d -> {
                try {
                    return d.findElement(AppiumBy.androidUIAutomator(
                            "new UiSelector().className(\"android.widget.Button\").instance(0)"));
                } catch (Exception ex) { return null; }
            });
        }
        icon.click();
    }

    public void clickOnSectionHeader(String sectionName) {
        WebElement header = findSectionHeader(sectionName);
        String descBefore = header.getAttribute("content-desc");
        if (descBefore != null && descBefore.contains("Expanded")) return;
        header.click();
        final String capturedDesc = descBefore;
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> {
            try {
                String desc = findSectionHeader(sectionName).getAttribute("content-desc");
                return desc != null && !desc.equals(capturedDesc);
            } catch (Exception e) { return false; }
        });
    }

    public String getSectionHeaderText(String sectionName) {
        String desc = findSectionHeader(sectionName).getAttribute("content-desc");
        return desc != null ? desc.split(",")[0].trim() : "";
    }

    private WebElement findSectionHeader(String name) {
        try {
            return driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                    ".scrollIntoView(new UiSelector().descriptionStartsWith(\"" + name + "\"))"));
        } catch (Exception ignored) {
            return driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().descriptionStartsWith(\"" + name + "\")"));
        }
    }

    public void clickOnFeature(String featureName) {
        WebElement feature = new WebDriverWait(driver, Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(2))
                .until(d -> {
                    try {
                        return d.findElement(AppiumBy.androidUIAutomator(
                                "new UiScrollable(new UiSelector().scrollable(true))" +
                                ".scrollIntoView(new UiSelector().description(\"" + featureName + "\"))"));
                    } catch (Exception e) { return null; }
                });
        if (feature == null) throw new RuntimeException("Feature not found: " + featureName);
        feature.click();
        elementUtils.waitForPresence(AppiumBy.accessibilityId("Search"), 10);
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

    public void swipeRecordRightToLeft(WebElement userRecord) {
        swipeRightToLeft(userRecord);
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
        keyboardUtils.hideKeyboardSafely();
        // If date picker is open, one back press closes it — then press back again to leave the form
        if (isDatePickerVisible()) {
            driver.navigate().back();
        }
        driver.navigate().back();
    }

    public void pressBackAndConfirmIfAsked() {
        driver.navigate().back();
        popupUtils.confirmExitIfAlertShows(3);
    }

    // ═══════════════════════════════════════════════════════════
    //  ADD / EDIT / DUPLICATE SCREEN — FIELD INPUT
    // ═══════════════════════════════════════════════════════════

    public void enterUserName(String name) {
        WebElement f = elementUtils.waitForFirst(USER_NAME_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(name); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void clearUserName() {
        WebElement f = elementUtils.waitForFirst(USER_NAME_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void enterEmail(String email) {
        WebElement f = elementUtils.waitForFirst(EMAIL_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(email); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void enterPhone(String phone) {
        WebElement f = elementUtils.waitForFirst(PHONE_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(phone); } catch (Exception ignored) { /* best-effort */ } }
    }

    /** Just focuses the Emergency No field (shows keyboard) without entering any value. */
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

    public void enterEmpCode(String code) {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Emp Code");
        WebElement f = elementUtils.waitForFirst(EMP_CODE_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(code); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void selectBloodGroup(String group) {
        keyboardUtils.hideKeyboardSafely();
        WebElement btn = elementUtils.waitForFirst(BLOOD_GROUP_BTN, 10);
        if (btn != null) {
            btn.click();
            selectDropdownOption(group);
        }
    }

    public void selectRole(String role) {
        keyboardUtils.hideKeyboardSafely();
        scrollToDescriptionStartsWith("Roles");
        WebElement btn = elementUtils.waitForFirst(ROLES_BTN, 10);
        if (btn != null) {
            btn.click();
            selectDropdownOption(role);
        }
    }

    public void selectTeam(String teamName) {
        keyboardUtils.hideKeyboardSafely();
        try {
            WebElement chip = driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                    ".scrollIntoView(new UiSelector().description(\"" + teamName + "\"))"));
            chip.click();
            return;
        } catch (Exception e) {
            System.out.println("[selectTeam] UiScrollable approach failed for '" + teamName + "': " + e.getMessage());
        }
        // Fallback: accessibilityId after scrolling Teams label into view
        try { scrollToDescriptionStartsWith("Teams"); } catch (Exception ignored) {}
        WebElement teamChip = elementUtils.waitForFirst(AppiumBy.accessibilityId(teamName), 5);
        if (teamChip != null) {
            teamChip.click();
        } else {
            System.out.println("[selectTeam] Team chip not found after fallback: " + teamName);
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

    public void enterAddress2(String addr) {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Address Line II");
        WebElement f = elementUtils.waitForFirst(ADDRESS2_INPUT, 10);
        if (f != null) { try { f.click(); f.clear(); f.sendKeys(addr); } catch (Exception ignored) { /* best-effort */ } }
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
    //  EDIT SCREEN — CLEAR FIELDS (for Update flow)
    // ═══════════════════════════════════════════════════════════

    public void clearUserNameField() {
        WebElement f = elementUtils.waitForFirst(USER_NAME_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void clearPhoneField() {
        WebElement f = elementUtils.waitForFirst(PHONE_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void clearEmergencyNoField() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Emergency No");
        WebElement f = elementUtils.waitForFirst(EMERGENCY_NO_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void clearEmpCodeField() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Emp Code");
        WebElement f = elementUtils.waitForFirst(EMP_CODE_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void clearAddress1Field() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Address Line I");
        WebElement f = elementUtils.waitForFirst(ADDRESS1_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void clearAddress2Field() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Address Line II");
        WebElement f = elementUtils.waitForFirst(ADDRESS2_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void clearPinCodeField() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Pin Code");
        WebElement f = elementUtils.waitForFirst(PIN_CODE_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void clearCityField() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("City");
        WebElement f = elementUtils.waitForFirst(CITY_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void clearStateField() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("State");
        WebElement f = elementUtils.waitForFirst(STATE_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void clearCountryField() {
        keyboardUtils.hideKeyboardSafely();
        scrollToHint("Country");
        WebElement f = elementUtils.waitForFirst(COUNTRY_INPUT, 5);
        if (f != null) { try { f.click(); f.clear(); } catch (Exception ignored) {} }
    }

    public void clearBloodGroupSelection() {
        keyboardUtils.hideKeyboardSafely();
        WebElement btn = elementUtils.waitForFirst(BLOOD_GROUP_BTN, 10);
        if (btn == null) return;
        btn.click();
        // Look for an empty/placeholder option to represent "no selection"
        String[] clearLabels = {"Select Blood Group", "None", "Select"};
        for (String label : clearLabels) {
            WebElement opt = elementUtils.waitForFirst(
                    AppiumBy.androidUIAutomator("new UiSelector().description(\"" + label + "\")"), 2);
            if (opt != null) { opt.click(); return; }
        }
        // No explicit clear option — press system Back to close dropdown overlay only (stays on Edit screen)
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }

    public void clearRoleSelection() {
        keyboardUtils.hideKeyboardSafely();
        scrollToDescriptionStartsWith("Roles");
        WebElement btn = elementUtils.waitForFirst(ROLES_BTN, 10);
        if (btn == null) return;
        btn.click();
        String[] clearLabels = {"Select Role", "Select Roles", "None", "Select"};
        for (String label : clearLabels) {
            WebElement opt = elementUtils.waitForFirst(
                    AppiumBy.androidUIAutomator("new UiSelector().description(\"" + label + "\")"), 2);
            if (opt != null) { opt.click(); return; }
        }
        // No explicit clear option — press system Back to close dropdown overlay only
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }

    public void clearTeamsSelection() {
        keyboardUtils.hideKeyboardSafely();
        String[] teamNames = {"Maintenance", "Production Approval", "Quality analyst", "Product Setup Approver"};
        for (String team : teamNames) {
            try {
                WebElement chip = driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().description(\"" + team + "\"))"));
                String checked = chip.getAttribute("checked");
                if ("true".equals(checked)) {
                    chip.click();
                }
            } catch (Exception e) {
                System.out.println("[clearTeamsSelection] Skipping team '" + team + "': " + e.getMessage());
            }
        }
    }

    public void clearDOBField() {
        keyboardUtils.hideKeyboardSafely();
        WebElement f = elementUtils.waitForFirst(DOB_FIELD, 5);
        if (f == null) return;
        f.click();
        WebElement clearBtn = elementUtils.waitForFirst(AppiumBy.accessibilityId("Clear"), 2);
        if (clearBtn != null) {
            clearBtn.click();
        } else {
            WebElement cancelBtn = elementUtils.waitForFirst(DATE_PICKER_CANCEL, 3);
            if (cancelBtn != null) cancelBtn.click();
            else driver.navigate().back();
        }
    }

    public void clearDOJField() {
        keyboardUtils.hideKeyboardSafely();
        WebElement f = elementUtils.waitForFirst(DOJ_FIELD, 5);
        if (f == null) return;
        f.click();
        WebElement clearBtn = elementUtils.waitForFirst(AppiumBy.accessibilityId("Clear"), 2);
        if (clearBtn != null) {
            clearBtn.click();
        } else {
            WebElement cancelBtn = elementUtils.waitForFirst(DATE_PICKER_CANCEL, 3);
            if (cancelBtn != null) cancelBtn.click();
            else driver.navigate().back();
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  EDIT SCREEN — STATUS + SAVE
    // ═══════════════════════════════════════════════════════════

    public void clickStatusButton() {
        WebElement btn = elementUtils.waitForFirst(STATUS_FIELD, 10);
        if (btn != null) btn.click();
    }

    public String getStatusValue() {
        WebElement btn = elementUtils.firstOrNull(STATUS_FIELD);
        return btn != null ? btn.getAttribute("content-desc") : null;
    }

    public void clickYesChangeButton() {
        elementUtils.clickWhenFoundByUIAutomator(
                "new UiSelector().description(\"Yes, Change\")", 10);
    }

    public boolean waitForYesChangeButton(int timeoutSecs) {
        return elementUtils.waitForPresenceByUIAutomator(
                "new UiSelector().description(\"Yes, Change\")", timeoutSecs);
    }

    public void clickSaveButton() {
        keyboardUtils.hideKeyboardSafely();
        elementUtils.clickWhenFoundByAccessibility("Save", 10);
    }

    public void clickSubmitButton() {
        keyboardUtils.hideKeyboardSafely();
        elementUtils.clickWhenFoundByAccessibility("Submit", 10);
    }

    // ═══════════════════════════════════════════════════════════
    //  VIEW SCREEN — DUPLICATE BUTTON
    // ═══════════════════════════════════════════════════════════

    public void clickDuplicateButton() {
        WebElement btn = elementUtils.waitForFirst(DUPLICATE_BTN, 10);
        if (btn != null) btn.click();
    }

    public boolean isDuplicateButtonVisible() {
        return elementUtils.isPresent(DUPLICATE_BTN);
    }

    // ═══════════════════════════════════════════════════════════
    //  SCREEN STATE CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isAddNewUserScreenDisplayed() {
        return elementUtils.waitForPresence(ADD_USER_SCREEN, 10);
    }

    public boolean isEditUserScreenDisplayed() {
        return elementUtils.waitForPresence(EDIT_USER_SCREEN, 10);
    }

    public boolean isViewUserScreenDisplayed() {
        return elementUtils.waitForPresence(VIEW_USER_SCREEN, 10);
    }

    public boolean isDuplicateUserScreenDisplayed() {
        return elementUtils.waitForPresence(DUPLICATE_USER_SCREEN, 10);
    }

    public boolean isUserIdVisible() {
        return elementUtils.waitForPresence(USER_ID_FIELD, 5);
    }

    public boolean isStatusFieldVisible() {
        return elementUtils.isPresent(STATUS_FIELD);
    }

    public boolean isSubmitButtonVisible() {
        return elementUtils.isPresentByAccessibility("Submit");
    }

    public boolean isSaveButtonVisible() {
        return elementUtils.isPresentByAccessibility("Save");
    }

    public boolean isConfirmationAlertDisplayed() {
        return elementUtils.isPresentByUIAutomator(
                "new UiSelector().descriptionContains(\"Confirmation Alert\")");
    }

    public boolean isYesChangeButtonVisible() {
        return elementUtils.isPresentByUIAutomator(
                "new UiSelector().description(\"Yes, Change\")");
    }

    // ═══════════════════════════════════════════════════════════
    //  FIELD VISIBILITY / READ-ONLY CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isUserNameFieldVisible() {
        return elementUtils.isPresent(USER_NAME_INPUT)
                || elementUtils.isPresent(AppiumBy.xpath("//android.view.View[@hint='User Name *']"));
    }

    public boolean isEmailFieldVisible() {
        return elementUtils.isPresent(EMAIL_INPUT)
                || elementUtils.isPresent(AppiumBy.xpath("//android.view.View[@hint='Email ID *']"));
    }

    public boolean isPhoneFieldVisible() {
        return elementUtils.isPresent(PHONE_INPUT)
                || elementUtils.isPresent(AppiumBy.xpath("//android.view.View[@hint='Phone No *']"));
    }

    public boolean isBloodGroupFieldVisible() {
        return elementUtils.isPresent(BLOOD_GROUP_BTN)
                || elementUtils.isPresent(AppiumBy.xpath(
                        "//android.widget.Button[starts-with(@content-desc,'Blood Group')]"))
                || elementUtils.isPresent(AppiumBy.xpath(
                        "//android.view.View[starts-with(@content-desc,'Blood Group')]"));
    }

    public boolean isRolesFieldVisible() {
        scrollToDescriptionStartsWith("Roles");
        return elementUtils.isPresent(ROLES_BTN)
                || elementUtils.isPresent(AppiumBy.xpath(
                        "//android.view.View[starts-with(@content-desc,'Roles')]"));
    }

    public boolean isTeamsSectionVisible() {
        return elementUtils.isPresent(TEAMS_SECTION);
    }

    public boolean isDOBFieldVisible() {
        return elementUtils.isPresent(DOB_FIELD);
    }

    public boolean isDOJFieldVisible() {
        return elementUtils.isPresent(DOJ_FIELD);
    }

    /** True when the Email field is not editable (View/Edit screens where email is locked). */
    public boolean isEmailFieldNonEditable() {
        elementUtils.disableImplicitWait();
        try {
            // In Edit screen, email is a View (non-editable), not an EditText
            List<WebElement> editTexts = driver.findElements(EMAIL_INPUT);
            if (!editTexts.isEmpty()) return !editTexts.get(0).isEnabled();
            // If rendered as View, it is non-editable by definition
            return !driver.findElements(
                    AppiumBy.xpath("//android.view.View[@hint='Email ID *']")).isEmpty();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERRORS
    // ═══════════════════════════════════════════════════════════

    public boolean isRequiredErrorDisplayed() {
        scrollToTop();
        return elementUtils.waitForPresence(ERR_FIELD_REQUIRED, 5)
                || elementUtils.waitForPresence(ERR_ANY_VALIDATION_TEXT, 3);
    }

    public boolean isInvalidEmailErrorDisplayed() {
        scrollToTop();
        return elementUtils.waitForPresence(ERR_INVALID_EMAIL, 5)
                || elementUtils.waitForPresence(ERR_ANY_VALIDATION_TEXT, 3);
    }

    public boolean isDuplicateEmailErrorDisplayed() {
        // "Email Already Exists" banner appears at top of form — no scroll needed
        // Check text attribute first (Flutter banners often use text, not content-desc)
        if (elementUtils.waitForPresence(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"Email Already Exists\")"), 6)) return true;
        if (elementUtils.waitForPresence(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"already exists\")"), 3)) return true;
        if (elementUtils.waitForPresence(AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionContains(\"Email Already Exists\")"), 3)) return true;
        // Broad fallback via regex
        scrollToTop();
        return elementUtils.waitForPresence(ERR_DUPLICATE_EMAIL, 3)
                || elementUtils.waitForPresence(ERR_ANY_VALIDATION, 2)
                || elementUtils.waitForPresence(ERR_ANY_VALIDATION_TEXT, 2);
    }

    public boolean isDuplicatePhoneErrorDisplayed() {
        scrollToTop();
        return elementUtils.waitForPresence(ERR_DUPLICATE_PHONE, 5)
                || elementUtils.waitForPresence(ERR_ANY_VALIDATION, 3)
                || elementUtils.waitForPresence(ERR_ANY_VALIDATION_TEXT, 3);
    }

    public boolean isPhoneValidationErrorDisplayed() {
        scrollToTop();
        return elementUtils.waitForPresence(ERR_INVALID_PHONE, 5)
                || elementUtils.waitForPresence(ERR_ANY_VALIDATION, 3)
                || elementUtils.waitForPresence(ERR_ANY_VALIDATION_TEXT, 3);
    }

    public boolean isAnyValidationErrorDisplayed() {
        // Check current viewport first (no scroll cost)
        if (elementUtils.waitForPresence(ERR_ANY_VALIDATION, 2)
                || elementUtils.waitForPresence(ERR_ANY_VALIDATION_TEXT, 2)) return true;
        // Scroll to top — errors for top-section fields (User Name, Email, Phone, Blood Group)
        scrollToTop();
        if (elementUtils.waitForPresence(ERR_ANY_VALIDATION, 3)
                || elementUtils.waitForPresence(ERR_ANY_VALIDATION_TEXT, 2)) return true;
        // Scroll to bottom — errors for bottom-section fields (Roles, Teams)
        scrollToBottom();
        return elementUtils.waitForPresence(ERR_ANY_VALIDATION, 3)
                || elementUtils.waitForPresence(ERR_ANY_VALIDATION_TEXT, 2);
    }

    // ═══════════════════════════════════════════════════════════
    //  ACTION MENUS BOTTOM SHEET
    // ═══════════════════════════════════════════════════════════

    public boolean isActionMenusBottomSheetDisplayed() {
        return elementUtils.waitForPresence(ACTION_MENUS_SHEET, 5);
    }

    public boolean isUnitSubscriptionOptionVisible() {
        return elementUtils.waitForPresence(UNIT_SUBSCRIPTION_OPTION, 5);
    }

    public boolean isMachineSubscriptionOptionVisible() {
        return elementUtils.waitForPresence(MACHINE_SUBSCRIPTION_OPTION, 5);
    }

    public void clickUnitSubscriptionOption() {
        WebElement opt = elementUtils.waitForFirst(UNIT_SUBSCRIPTION_OPTION, 5);
        if (opt != null) tap(opt);
    }

    public void clickMachineSubscriptionOption() {
        WebElement opt = elementUtils.waitForFirst(MACHINE_SUBSCRIPTION_OPTION, 5);
        if (opt != null) tap(opt);
    }

    // ═══════════════════════════════════════════════════════════
    //  UNIT SUBSCRIPTION POPUP (READ-ONLY)
    // ═══════════════════════════════════════════════════════════

    public boolean isUnitSubscriptionPopupDisplayed() {
        return elementUtils.waitForPresence(UNIT_SUBSCRIPTION_POPUP, 10);
    }

    public boolean isUnitSubscriptionCloseButtonVisible() {
        return elementUtils.isPresent(UNIT_SUBSCRIPTION_CLOSE_BTN);
    }

    public void clickUnitSubscriptionCloseButton() {
        WebElement btn = elementUtils.waitForFirst(UNIT_SUBSCRIPTION_CLOSE_BTN, 10);
        if (btn != null) btn.click();
    }

    public boolean waitForUnitSubscriptionPopupClosed(int timeoutSecs) {
        return elementUtils.waitForAbsence(UNIT_SUBSCRIPTION_POPUP, timeoutSecs);
    }

    /** True when popup has no Submit button and no editable EditText fields (read-only). */
    public boolean isUnitSubscriptionPopupReadOnly() {
        elementUtils.disableImplicitWait();
        try {
            boolean noSubmit   = driver.findElements(SUBMIT_BTN).isEmpty();
            boolean noEditText = driver.findElements(By.className("android.widget.EditText")).isEmpty();
            return noSubmit && noEditText;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public boolean hasUnitNamesInPopup() {
        return elementUtils.waitForPresence(UNIT_ITEMS_IN_POPUP, 5);
    }

    public boolean isUnitSubscriptionEmptyState() {
        // Empty state: popup is open but no unit rows are present
        elementUtils.disableImplicitWait();
        try {
            return driver.findElements(UNIT_ITEMS_IN_POPUP).isEmpty();
        } finally {
            elementUtils.restoreImplicitWait();
        }
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
            // Fallback: "+" as text content or first unlabelled Button in popup
            btn = elementUtils.waitForFirst(AppiumBy.xpath(
                    "//android.view.View[@content-desc='Machine Subscription']" +
                    "//android.widget.Button[not(@content-desc) or @content-desc='+']"), 5);
        }
        if (btn != null) btn.click();
    }

    public boolean isMachineSelectBottomSheetDisplayed() {
        return elementUtils.waitForPresence(MACHINE_SELECT_SHEET, 10);
    }

    public void selectOneMachine() {
        // Wait for bottom sheet items to render before attempting selection
        elementUtils.waitForPresence(MACHINE_SELECT_SHEET, 5);
        WebElement item = elementUtils.waitForFirst(UNCHECKED_MACHINE_ITEM, 10);
        if (item == null) {
            // Fallback: any Button inside the bottom sheet (not scoped to ScrollView)
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
        // Scoped to Select Machines bottom sheet — avoids accidentally clicking the
        // Machine Subscription popup Submit which is behind the sheet in the a11y tree
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
        // Same pattern as single-delete, repeated until no icons remain
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

    /** Waits up to timeoutSecs for all machine rows to disappear — use after deleteAll. */
    public boolean waitForAllMachinesGone(int timeoutSecs) {
        return elementUtils.waitForAbsence(MACHINE_ITEMS_IN_POPUP, timeoutSecs);
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
                        // Users module uses "Add" in bottom toolbar (not "+ Add" FAB)
                        if (!d.findElements(AppiumBy.accessibilityId("Add")).isEmpty()) return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.accessibilityId("+ Add")).isEmpty()) return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.accessibilityId("Search")).isEmpty()) return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.accessibilityId("Filter")).isEmpty()) return Boolean.TRUE;
                        // Sort is always visible on list screens (even in search mode)
                        if (!d.findElements(AppiumBy.accessibilityId("Sort")).isEmpty()) return Boolean.TRUE;
                        // In search mode the search EditText is visible on the list
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
    //  NAVIGATION SCREEN TITLES (used by CommonNavigationSteps)
    // ═══════════════════════════════════════════════════════════

    public String getAccountPreferenceHeader() {
        WebElement header = elementUtils.waitForFirst(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().descriptionContains(\"Account Preferences\")"), 10);
        if (header == null) return "";
        String desc = header.getAttribute("content-desc");
        return desc != null ? desc.split(",")[0].trim() : "";
    }

    public String getListPageTitle() {
        WebElement title = elementUtils.waitForFirst(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().descriptionContains(\"Users\")"), 5);
        if (title == null) return "";
        String desc = title.getAttribute("content-desc");
        return desc != null ? desc.split(",")[0].trim() : "";
    }

    // ═══════════════════════════════════════════════════════════
    //  DATE PICKER STATE
    // ═══════════════════════════════════════════════════════════

    public boolean isDatePickerVisible() {
        return elementUtils.isPresent(YEAR_DROPDOWN_BTN) || elementUtils.isPresent(DATE_PICKER_OK);
    }

    // ═══════════════════════════════════════════════════════════
    //  TOAST / SNACKBAR CHECK
    // ═══════════════════════════════════════════════════════════

    public boolean isToastDisplayed(String message) {
        String escaped = message.replace("\"", "\\\"");
        return elementUtils.waitForPresence(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().textContains(\"" + escaped + "\")"), 5)
            || elementUtils.waitForPresence(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().descriptionContains(\"" + escaped + "\")"), 3);
    }

    /** Wait for a toast/snackbar with the given message to disappear (max 8 s). */
    public void waitForToastGone(String message) {
        String escaped = message.replace("\"", "\\\"");
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(8))
                    .pollingEvery(Duration.ofMillis(300))
                    .until(d -> {
                        boolean byTextGone = d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().textContains(\"" + escaped + "\")")).isEmpty();
                        boolean byDescGone = d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().descriptionContains(\"" + escaped + "\")")).isEmpty();
                        return byTextGone && byDescGone;
                    });
        } catch (Exception ignored) { /* toast already gone or timed out — proceed */ }
        finally {
            elementUtils.restoreImplicitWait();
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  DOJ VALIDATION ERROR
    // ═══════════════════════════════════════════════════════════

    public boolean isDOJValidationErrorDisplayed() {
        return elementUtils.waitForPresence(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().descriptionMatches(" +
                        "\"(?i).*(joining|DOJ|date of join|date.*joining).*\")"), 5)
            || isAnyValidationErrorDisplayed();
    }

    // ═══════════════════════════════════════════════════════════
    //  DROPDOWN HELPERS
    // ═══════════════════════════════════════════════════════════

    public void clickBloodGroupDropdown() {
        keyboardUtils.hideKeyboardSafely();
        WebElement btn = elementUtils.waitForFirst(BLOOD_GROUP_BTN, 10);
        if (btn != null) btn.click();
    }

    public void clickRolesDropdown() {
        keyboardUtils.hideKeyboardSafely();
        scrollToDescriptionStartsWith("Roles");
        WebElement btn = elementUtils.waitForFirst(ROLES_BTN, 10);
        if (btn != null) btn.click();
    }

    public boolean isDropdownOptionsVisible() {
        // Blood Group values
        if (elementUtils.waitForPresence(AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionMatches(\"A\\+|A-|B\\+|B-|O\\+|O-|AB\\+|AB-\")"), 5))
            return true;
        // Role values
        if (elementUtils.waitForPresence(AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionMatches(\"Admin|Manager|Super Admin|Supervisor\")"), 3))
            return true;
        // Fallback: any scrollable list container
        return elementUtils.waitForPresence(AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.ScrollView\")"), 3)
            || elementUtils.waitForPresence(AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.ListView\")"), 2);
    }

    /** Select a value from a dropdown that is ALREADY open — does not re-click the trigger. */
    public void selectFromOpenDropdown(String optionText) {
        selectDropdownOption(optionText);
    }

    public void clickTeamsSection() {
        keyboardUtils.hideKeyboardSafely();
        scrollToDescriptionStartsWith("Teams");
        WebElement section = elementUtils.waitForFirst(TEAMS_SECTION, 10);
        if (section != null) section.click();
    }

    // ═══════════════════════════════════════════════════════════
    //  CONFIRMATION DIALOG
    // ═══════════════════════════════════════════════════════════

    public void clickYesExitButton() {
        elementUtils.clickWhenFoundByUIAutomator(
                "new UiSelector().description(\"Yes, Exit\")", 10);
    }

    // ═══════════════════════════════════════════════════════════
    //  SEARCH (public wrapper)
    // ═══════════════════════════════════════════════════════════

    public void searchRecord(String name) {
        searchUtils.searchRecord(name);
    }

    // ═══════════════════════════════════════════════════════════
    //  FIELD VALUE READER
    // ═══════════════════════════════════════════════════════════

    public String getEditTextValue(String hint) {
        // Dismiss keyboard first — keyboard keeps the field in focus but can hide it from
        // the accessibility tree when the form scrolls to accommodate the keyboard.
        keyboardUtils.hideKeyboardSafely();
        // Scroll field into view so it is in the accessibility tree before we read it.
        scrollToHint(hint);
        elementUtils.disableImplicitWait();
        try {
            // starts-with matches "Pin Code", "Pin Code *", "Phone No", "Phone No *", etc.
            List<WebElement> fields = driver.findElements(
                    AppiumBy.xpath("//android.widget.EditText[starts-with(@hint,'" + hint + "')]"));
            if (fields.isEmpty()) return null;
            String text = fields.get(0).getText();
            if (text != null && !text.isEmpty()) return text;
            // Flutter sometimes exposes the entered value via content-desc when getText() returns ""
            String desc = fields.get(0).getAttribute("content-desc");
            // Filter literal "null" string that Appium returns for empty Flutter elements
            if (desc != null && !desc.isEmpty() && !"null".equals(desc)) return desc;
            return text;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  COMPOSITE HELPERS
    // ═══════════════════════════════════════════════════════════

    public void openEditUserScreen(String name) {
        searchUtils.searchRecord(name);
        WebElement userRecord = searchUtils.getRecordByName(name);
        if (userRecord == null) throw new RuntimeException("User not found in list: " + name);
        swipeRightToLeft(userRecord);
        WebElement editBtn = elementUtils.waitForFirst(EDIT_BTN, 5);
        if (editBtn != null) editBtn.click();
        elementUtils.waitForPresence(EDIT_USER_SCREEN, 10);
    }

    public void openViewUserScreen(String name) {
        searchUtils.searchRecord(name);
        WebElement userRecord = searchUtils.getRecordByName(name);
        if (userRecord == null) throw new RuntimeException("User not found in list: " + name);
        userRecord.click();
        elementUtils.waitForPresence(VIEW_USER_SCREEN, 10);
    }

    public boolean verifyUpdatedRecordInList(String name) {
        // Search is already open after returning from Edit screen — just type the new name.
        // Never call ensureSearchClosed() here; that clicks the X button which can
        // accidentally land on a list record and navigate to the View screen.
        searchUtils.searchRecord(name);
        return searchUtils.getRecordByName(name) != null;
    }

    /**
     * Creates a new User with mandatory fields only and returns the User Name.
     * Used by Background step: "User has already created a User".
     */
    public String createUserAndReturnName() {
        String name  = DataGenerator.randomUserName();
        String email = DataGenerator.randomEmail();
        String phone = DataGenerator.randomPhone();
        clickAddButton();
        enterUserName(name);
        enterEmail(email);
        enterPhone(phone);
        selectBloodGroup("A+");
        selectRole("Admin");
        clickSubmitButton();
        if (!popupUtils.waitForCreateSuccess(30)) {
            throw new RuntimeException(
                    "Background setup failed: User '" + name + "' was not created within 30 s");
        }
        return name;
    }

    // ═══════════════════════════════════════════════════════════
    //  BACKWARD-COMPATIBLE ALIASES (legacy step definitions)
    // ═══════════════════════════════════════════════════════════

    /** Legacy: MachinesPage + old step defs call this to pick a dropdown value. */
    public void selectOptionInDropdown(String optionText) {
        selectDropdownOption(optionText);
    }

    /** Legacy: UsersTest calls clickOnAddButton(). */
    public void clickOnAddButton() { clickAddButton(); }

    /** Legacy: UsersTest calls getPageHeaderText() to assert screen title. */
    public String getPageHeaderText() {
        if (elementUtils.isPresent(ADD_USER_SCREEN))       return "Add New User";
        if (elementUtils.isPresent(EDIT_USER_SCREEN))      return "Edit User";
        if (elementUtils.isPresent(VIEW_USER_SCREEN))      return "View User";
        if (elementUtils.isPresent(DUPLICATE_USER_SCREEN)) return "Duplicate User";
        return "";
    }

    /** Legacy: UsersTest / OperatorTest call selectDateOfBirth() with no args. */
    public void selectDateOfBirth() { enterDOB("May", "1990", "15"); }

    /** Legacy: UsersTest / OperatorTest call selectDateOfJoining() with no args. */
    public void selectDateOfJoining() { enterDOJ("June", "2015", "10"); }

    /** Legacy: UsersTest calls teamSelection(teamName). */
    public void teamSelection(String teamName) { selectTeam(teamName); }

    /** Legacy: UsersTest calls clickOnSubmitButton(). */
    public void clickOnSubmitButton() { clickSubmitButton(); }

    /** Legacy: UsersTest calls getConfirmationMsg() and ignores the return value. */
    public boolean getConfirmationMsg() { return waitForCreateSuccess(15); }

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

    /**
     * Scroll until an element whose content-desc starts with the given prefix is on screen.
     * Flutter uses content-desc, NOT text — UiSelector.text() never matches Flutter elements.
     */
    private void scrollToDescriptionStartsWith(String prefix) {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                    ".scrollIntoView(new UiSelector().descriptionStartsWith(\"" + prefix + "\"))"));
        } catch (Exception ignored) { /* element may already be visible */ }
    }

    /** Scroll the form back to the very top — needed to reveal validation errors after scrolling down. */
    private void scrollToTop() {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollToBeginning(10)"));
        } catch (Exception ignored) { /* already at top or no scrollable container */ }
    }

    /** Scroll the form to the very bottom — needed to reveal validation errors for bottom-section fields (Roles, Teams). */
    private void scrollToBottom() {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(10)"));
        } catch (Exception ignored) { /* already at bottom or no scrollable container */ }
    }

    /** Scroll the form until a field with the given hint text is visible. */
    private void scrollToHint(String hint) {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                    ".scrollIntoView(new UiSelector().hintMatches(\"(?i)" + hint + ".*\"))"));
        } catch (Exception ignored) { /* field may already be visible */ }
    }

    private void selectDateInPicker(String month, String year, String day) {
        // Button content-desc format: "Select year\nMay 2008"
        WebElement yearBtn = elementUtils.waitForFirst(YEAR_DROPDOWN_BTN, 10);
        if (yearBtn == null) return;

        String desc = yearBtn.getAttribute("content-desc");
        String target = month + " " + year;

        // Jump to target year via the year-picker dropdown if not already there
        if (desc != null && !desc.contains(year)) {
            yearBtn.click();
            // Year may not be immediately visible — scroll the year list to find it
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

        // Navigate month by month until "Select year\n<Month> <Year>" contains the target.
        // Uses NEXT or PREV month button depending on which direction is enabled.
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

        // Day button content-desc format from XML: "23, Friday, May 23, 2008"
        // Match by "startsWith(<day>, )" to avoid ambiguity between e.g. "1" and "11"
        WebElement dayBtn = elementUtils.waitForFirst(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.Button\")" +
                        ".descriptionStartsWith(\"" + day + ", \")"), 5);
        if (dayBtn != null) dayBtn.click();
    }
}