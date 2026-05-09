package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import pageObject.BasePage;
import utilities.DataGenerator;

import java.util.List;

/**
 * Page Object for the Holidays Configuration module.
 *
 * Covers: Holidays list screen, Add New Holiday popup, Edit Holiday popup,
 *         View Holiday popup, Date Picker popup, National Holiday toggle,
 *         Status-change confirmation popup.
 *
 * Locator priority: accessibilityId → id → AndroidUIAutomator → XPath (last resort)
 * NOTE: Update locators after UI inspection with Appium Inspector.
 */
public class HolidayPage extends BasePage {

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN ELEMENTS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "Add")
    private WebElement addButton;

    @AndroidFindBy(accessibility = "Search")
    private WebElement searchIcon;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Search' or @hint='search']")
    private WebElement searchInput;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@clickable='true' and @focusable='true']")
    private List<WebElement> listRecords;

    // ═══════════════════════════════════════════════════════════
    //  ADD / EDIT POPUP FIELDS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Holiday Name' or @hint='Name']")
    private WebElement holidayNameInput;

    /**
     * Holiday Date field — tapping this opens the native date picker.
     * React Native apps often render this as a ViewGroup/TouchableOpacity.
     */
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Holiday Date' or @hint='Date'] | "
            + "//android.view.ViewGroup[@content-desc='Holiday Date'] | "
            + "//android.widget.TextView[@content-desc='Holiday Date']")
    private WebElement holidayDateField;

    /** "Is National Holiday" toggle — Switch or CheckBox depending on RN version */
    @AndroidFindBy(xpath = "//android.widget.Switch[@content-desc='Is National Holiday' or "
            + "contains(@resource-id,'national')] | "
            + "//android.widget.CheckBox[@content-desc='Is National Holiday']")
    private WebElement nationalHolidayToggle;

    // ═══════════════════════════════════════════════════════════
    //  VIEW / EDIT ID FIELD
    // ═══════════════════════════════════════════════════════════

    /** Read-only Holiday ID shown in Edit / View popup */
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='Holiday ID' "
            + "or contains(@text,'HOL-') or contains(@text,'HD-')]")
    private WebElement holidayIdField;

    // ═══════════════════════════════════════════════════════════
    //  POPUP BUTTONS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "Submit")
    private WebElement submitButton;

    @AndroidFindBy(accessibility = "Save")
    private WebElement saveButton;

    /** Close (X) button of any popup */
    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Close' "
            + "or @content-desc='X' or @content-desc='close']")
    private WebElement closeButton;

    // ═══════════════════════════════════════════════════════════
    //  DATE PICKER ELEMENTS
    // ═══════════════════════════════════════════════════════════

    /** OK button inside the native date picker */
    @AndroidFindBy(xpath = "//android.widget.Button[@text='OK' or @content-desc='OK']")
    private WebElement datePickerOkButton;

    /** Cancel / Close button inside the native date picker */
    @AndroidFindBy(xpath = "//android.widget.Button[@text='Cancel' or @content-desc='Cancel' "
            + "or @content-desc='X' or @content-desc='close']")
    private WebElement datePickerCancelButton;

    /** Calendar grid — visible when date picker is open */
    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'calendar') "
            + "or contains(@content-desc,'Calendar')] | //android.widget.DatePicker")
    private WebElement calendarView;

    // ═══════════════════════════════════════════════════════════
    //  STATUS + CONFIRMATION POPUP
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Active' or @content-desc='Inactive']")
    private WebElement statusButton;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Yes, Change' or @text='Yes, Change']")
    private WebElement yesChangeButton;

    // ═══════════════════════════════════════════════════════════
    //  EDIT BUTTON (after swipe)
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "Edit")
    private WebElement editButton;

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION MESSAGES
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Holiday Name is required') "
            + "or contains(@text,'Name is required')]")
    private WebElement holidayNameRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Holiday Date is required') "
            + "or contains(@text,'Date is required')]")
    private WebElement holidayDateRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Holiday Date already exists') "
            + "or (contains(@text,'already exists') and not(contains(@text,'Name'))) "
            + "or contains(@text,'duplicate')]")
    private WebElement holidayDateDuplicateError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'invalid') "
            + "or contains(@text,'Invalid') or contains(@text,'validation')]")
    private WebElement validationError;

    // ═══════════════════════════════════════════════════════════
    //  POPUP HEADER TEXT ELEMENTS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Add New Holiday']")
    private WebElement addHolidayHeader;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Edit Holiday']")
    private WebElement editHolidayHeader;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='View Holiday']")
    private WebElement viewHolidayHeader;

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public HolidayPage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickAddButton()         { tap(addButton); }
    public boolean isAddButtonVisible()  { return isDisplayed(addButton); }

    public void clickSearchIcon()        { tap(searchIcon); }
    public void tapSearchInput()         { tap(searchInput); }
    public void clearSearchField()       { clearField(searchInput); }
    public void enterSearchText(String text) { clearAndType(searchInput, text); }

    public WebElement getRecordByName(String name) {
        try {
            return driver.findElement(
                    AppiumBy.xpath("//android.widget.TextView[@text='" + name + "']"));
        } catch (Exception e) {
            return null;
        }
    }

    public void clickRecordByName(String name) {
        WebElement el = getRecordByName(name);
        if (el != null) tap(el);
    }

    // ═══════════════════════════════════════════════════════════
    //  HOLIDAY NAME FIELD ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void enterHolidayName(String name) { clearAndType(holidayNameInput, name); }
    public void clearHolidayName()            { clearField(holidayNameInput); }

    public boolean isHolidayNameFieldVisible() { return isDisplayed(holidayNameInput); }
    public boolean isHolidayNameNonEditable() {
        try { return !holidayNameInput.isEnabled(); } catch (Exception e) { return true; }
    }
    public String getHolidayNameValue() {
        try { return holidayNameInput.getText(); } catch (Exception e) { return ""; }
    }

    // ═══════════════════════════════════════════════════════════
    //  HOLIDAY DATE FIELD ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickHolidayDateField()       { tap(holidayDateField); }
    public boolean isHolidayDateFieldVisible() { return isDisplayed(holidayDateField); }
    public boolean isHolidayDateNonEditable() {
        try { return !holidayDateField.isEnabled(); } catch (Exception e) { return true; }
    }
    public String getHolidayDateValue() {
        try { return holidayDateField.getText(); } catch (Exception e) { return ""; }
    }
    public void clearHolidayDate() { clearField(holidayDateField); }

    // ═══════════════════════════════════════════════════════════
    //  DATE PICKER ACTIONS
    // ═══════════════════════════════════════════════════════════

    public boolean isDatePickerDisplayed() {
        try {
            return driver.findElement(AppiumBy.xpath(
                    "//android.widget.TextView[@text='Select Date'] | "
                    + "//android.widget.DatePicker | "
                    + "//android.view.ViewGroup[contains(@content-desc,'calendar')]"))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCalendarVisible() {
        try { return calendarView.isDisplayed(); } catch (Exception e) { return false; }
    }

    public void clickDatePickerOk()            { tap(datePickerOkButton); }
    public boolean isDatePickerOkVisible()     { return isDisplayed(datePickerOkButton); }

    public void clickDatePickerCancel()        { tap(datePickerCancelButton); }
    public boolean isDatePickerCancelVisible() { return isDisplayed(datePickerCancelButton); }

    /** Selects a specific day number in the currently-open date picker. */
    public void selectDayInCalendar(int dayNumber) {
        try {
            WebElement dayEl = driver.findElement(AppiumBy.xpath(
                    "//android.view.ViewGroup[@content-desc='" + dayNumber + "'] | "
                    + "//android.widget.TextView[@text='" + dayNumber + "' "
                    + "and contains(@resource-id,'day')]"));
            tap(dayEl);
        } catch (Exception e) {
            System.out.println("[WARN] Could not select day " + dayNumber
                    + " in calendar: " + e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  NATIONAL HOLIDAY TOGGLE ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void enableNationalHolidayToggle()     { enableToggle(nationalHolidayToggle); }
    public void clickNationalHolidayToggle()      { clickToggle(nationalHolidayToggle); }
    public boolean isNationalHolidayToggleOn()    { return isToggleOn(nationalHolidayToggle); }
    public boolean isNationalHolidayToggleVisible() { return isDisplayed(nationalHolidayToggle); }
    public boolean isNationalHolidayToggleEnabled() {
        try { return nationalHolidayToggle.isEnabled(); } catch (Exception e) { return false; }
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP BUTTONS
    // ═══════════════════════════════════════════════════════════

    public void clickSubmitButton()         { tap(submitButton); }
    public boolean isSubmitButtonVisible()  { return isDisplayed(submitButton); }

    public void clickSaveButton()           { tap(saveButton); }
    public boolean isSaveButtonVisible()    { return isDisplayed(saveButton); }

    public void clickCloseButton()          { tap(closeButton); }
    public boolean isCloseButtonVisible()   { return isDisplayed(closeButton); }

    public void clickEditButton()           { tap(editButton); }
    public boolean isEditButtonVisible()    { return isDisplayed(editButton); }

    // ═══════════════════════════════════════════════════════════
    //  HOLIDAY ID
    // ═══════════════════════════════════════════════════════════

    public boolean isHolidayIdVisible() { return isDisplayed(holidayIdField); }
    public boolean isHolidayIdNonEditable() {
        try { return !holidayIdField.isEnabled(); } catch (Exception e) { return true; }
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP DISPLAY CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isAddHolidayPopupDisplayed()  { return isDisplayed(addHolidayHeader); }
    public boolean isEditHolidayPopupDisplayed() { return isDisplayed(editHolidayHeader); }
    public boolean isViewHolidayPopupDisplayed() { return isDisplayed(viewHolidayHeader); }

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION MESSAGE CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isHolidayNameRequiredErrorDisplayed()   { return isDisplayed(holidayNameRequiredError); }
    public boolean isHolidayDateRequiredErrorDisplayed()   { return isDisplayed(holidayDateRequiredError); }
    public boolean isHolidayDateDuplicateErrorDisplayed()  { return isDisplayed(holidayDateDuplicateError); }
    public boolean isValidationErrorDisplayed()            { return isDisplayed(validationError); }

    // ═══════════════════════════════════════════════════════════
    //  SWIPE HELPER
    // ═══════════════════════════════════════════════════════════

    public void swipeRecordRightToLeft(WebElement element) {
        swipeRightToLeft(element);
    }

    // ═══════════════════════════════════════════════════════════
    //  COMPOSITE HELPERS
    // ═══════════════════════════════════════════════════════════

    /**
     * Creates a Holiday with a random name and a future date, returns the name.
     * Used in Background pre-condition steps.
     */
    public String createHolidayAndReturnName() {
        String name = DataGenerator.randomHolidayName();
        clickAddButton();
        enterHolidayName(name);
        clickHolidayDateField();
        selectDayInCalendar(DataGenerator.randomFutureDay());
        clickDatePickerOk();
        clickSubmitButton();
        return name;
    }

    /**
     * Searches for the given Holiday Name, swipes the record right-to-left,
     * then clicks Edit to open the Edit Holiday popup.
     */
    public void searchSwipeAndOpenEdit(String holidayName) {
        clickSearchIcon();
        tapSearchInput();
        clearSearchField();
        enterSearchText(holidayName);
        WebElement listItem = getRecordByName(holidayName);
        swipeRecordRightToLeft(listItem);
        clickEditButton();
    }

    /**
     * Searches for the given Holiday Name and clicks the record to open the View popup.
     */
    public void searchAndOpenView(String holidayName) {
        clickSearchIcon();
        tapSearchInput();
        clearSearchField();
        enterSearchText(holidayName);
        clickRecordByName(holidayName);
    }
}
