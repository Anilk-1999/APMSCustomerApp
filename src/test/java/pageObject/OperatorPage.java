package pageObject;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import utilities.TestRandomDataGenerator;

public class OperatorPage extends BasePage {

    public OperatorPage(AndroidDriver driver) {
        super(driver);
    }

    // ── List Screen ───────────────────────────────────────────────────────────

    @AndroidFindBy(accessibility = "Add")
    private WebElement addButton;

    @AndroidFindBy(accessibility = "Search")
    private WebElement searchIcon;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Search' or @hint='search']")
    private WebElement searchInput;

    // ── Form — Common Fields ──────────────────────────────────────────────────

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Operator Name *' or @hint='Operator Name']")
    private WebElement operatorNameInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Email ID' or @hint='Email Id' or @hint='Email']")
    private WebElement emailInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Phone Number *' or @hint='Phone Number']")
    private WebElement phoneNumberInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Emergency Number' or @hint='Emergency No']")
    private WebElement emergencyNumberInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Operator Code']")
    private WebElement operatorCodeInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Address 1' or @hint='Address Line 1']")
    private WebElement address1Input;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Address 2' or @hint='Address Line 2']")
    private WebElement address2Input;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Pin Code' or @hint='Pincode']")
    private WebElement pinCodeInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='City']")
    private WebElement cityInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='State']")
    private WebElement stateInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Country']")
    private WebElement countryInput;

    // ── Dropdowns ────────────────────────────────────────────────────────────

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'Blood Group')] "
            + "| //android.widget.Spinner[contains(@content-desc,'Blood Group')]")
    private WebElement bloodGroupDropdown;

    // ── Date Fields ──────────────────────────────────────────────────────────

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'Date of Birth') "
            + "or contains(@content-desc,'DOB')] "
            + "| //android.widget.TextView[contains(@text,'Date of Birth')]")
    private WebElement dobField;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'Date of Joining') "
            + "or contains(@content-desc,'DOJ')] "
            + "| //android.widget.TextView[contains(@text,'Date of Joining')]")
    private WebElement dojField;

    // ── Action Buttons ────────────────────────────────────────────────────────

    @AndroidFindBy(accessibility = "Submit")
    private WebElement submitButton;

    @AndroidFindBy(accessibility = "Edit")
    private WebElement editButton;

    @AndroidFindBy(accessibility = "Delete")
    private WebElement deleteButton;

    @AndroidFindBy(accessibility = "Duplicate")
    private WebElement duplicateButton;

    @AndroidFindBy(accessibility = "Machine Subscription")
    private WebElement machineSubscriptionButton;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Confirm' or @text='Confirm' "
            + "or @content-desc='Yes' or @text='Yes' or @content-desc='Delete' or @text='Delete']")
    private WebElement confirmDeleteButton;

    // ── Machine Subscription Popup ────────────────────────────────────────────

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='+'] "
            + "| //android.widget.Button[contains(@content-desc,'Add Machine') or @content-desc='+']")
    private WebElement addSubscriptionIcon;

    // ── Validation Errors ─────────────────────────────────────────────────────

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Operator Name is required')]")
    private WebElement operatorNameRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Phone Number is required') "
            + "or contains(@text,'Phone number is required')]")
    private WebElement phoneNumberRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Blood Group is required')]")
    private WebElement bloodGroupRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'10 digits')]")
    private WebElement phoneMustBe10DigitsError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Phone number already exists') "
            + "or contains(@text,'phone already')]")
    private WebElement phoneAlreadyExistsError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Invalid email') "
            + "or contains(@text,'invalid email') or contains(@text,'email format')]")
    private WebElement invalidEmailError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Email already exists') "
            + "or contains(@text,'email already exists')]")
    private WebElement emailAlreadyExistsError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Pin Code must be') "
            + "or contains(@text,'6 digits')]")
    private WebElement pinCodeError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'DOB cannot be future') "
            + "or contains(@text,'future date')]")
    private WebElement dobFutureError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'DOJ cannot be before') "
            + "or contains(@text,'before DOB')]")
    private WebElement dojBeforeDobError;

    // ─────────────────────────────────────────────────────────────────────────
    //  LIST SCREEN ACTIONS
    // ─────────────────────────────────────────────────────────────────────────

    public void clickAddButton()  { tap(addButton); }
    public void clickSearchIcon() { tap(searchIcon); }

    public void enterSearchText(String text) {
        waitForVisibility(searchInput);
        searchInput.clear();
        searchInput.sendKeys(text);
    }

    public void clearSearchField() {
        try { searchInput.clear(); } catch (Exception ignored) {}
    }

    public WebElement getRecordByName(String name) {
        try {
            return driver.findElement(AppiumBy.xpath(
                    "//android.widget.TextView[@text='" + name + "'] "
                  + "| //android.view.ViewGroup[contains(@content-desc,'" + name + "')]"));
        } catch (Exception e) {
            return null;
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  FORM FIELD ACTIONS
    // ─────────────────────────────────────────────────────────────────────────

    public void enterOperatorName(String name)   { clearAndType(operatorNameInput, name); }
    public void enterEmail(String email)          { clearAndType(emailInput, email); }
    public void enterPhoneNumber(String phone)    { scrollDown(); clearAndType(phoneNumberInput, phone); }
    public void enterEmergencyNumber(String num)  { scrollDown(); clearAndType(emergencyNumberInput, num); }
    public void enterOperatorCode(String code)    { scrollDown(); clearAndType(operatorCodeInput, code); }
    public void enterAddress1(String addr)        { scrollDown(); clearAndType(address1Input, addr); }
    public void enterAddress2(String addr)        { scrollDown(); clearAndType(address2Input, addr); }
    public void enterPinCode(String pin)          { scrollDown(); clearAndType(pinCodeInput, pin); }
    public void enterCity(String city)            { scrollDown(); clearAndType(cityInput, city); }
    public void enterState(String state)          { scrollDown(); clearAndType(stateInput, state); }
    public void enterCountry(String country)      { scrollDown(); clearAndType(countryInput, country); }

    public void clearOperatorName() { clearField(operatorNameInput); }
    public void clearPhoneNumber()  { clearField(phoneNumberInput); }
    public void clearEmail()        { clearField(emailInput); }

    public void selectBloodGroup() {
        scrollDown();
        tap(bloodGroupDropdown);
        try {
            WebElement first = driver.findElement(AppiumBy.xpath(
                    "(//android.view.ViewGroup[@clickable='true' and @focusable='true'] "
                  + "| //android.widget.TextView[@clickable='true'])[1]"));
            tap(first);
        } catch (Exception e) {
            System.out.println("[WARN] Blood group option not found: " + e.getMessage());
        }
    }

    public void selectDateOfBirth() {
        try {
            scrollDown();
            tap(dobField);
            confirmDatePicker();
        } catch (Exception e) {
            System.out.println("[WARN] DOB selection failed: " + e.getMessage());
        }
    }

    public void selectDateOfJoining() {
        try {
            scrollDown();
            tap(dojField);
            confirmDatePicker();
        } catch (Exception e) {
            System.out.println("[WARN] DOJ selection failed: " + e.getMessage());
        }
    }

    private void confirmDatePicker() {
        try {
            WebElement ok = driver.findElement(AppiumBy.xpath(
                    "//android.widget.Button[@text='OK' or @content-desc='OK' or @text='Done']"));
            tap(ok);
        } catch (Exception e) {
            System.out.println("[WARN] Date picker confirm button not found");
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  ACTION BUTTONS
    // ─────────────────────────────────────────────────────────────────────────

    public void clickSubmitButton()              { tap(submitButton); }
    public void clickEditButton()                { tap(editButton); }
    public void clickDeleteButton()              { tap(deleteButton); }
    public void clickDuplicateButton()           { tap(duplicateButton); }
    public void clickMachineSubscriptionButton() { tap(machineSubscriptionButton); }
    public void clickAddSubscriptionIcon()       { tap(addSubscriptionIcon); }
    public void clickConfirmDelete()             { tap(confirmDeleteButton); }

    // ─────────────────────────────────────────────────────────────────────────
    //  SWIPE / LONG PRESS
    // ─────────────────────────────────────────────────────────────────────────

    public void swipeOperatorRecord(String name) {
        try {
            WebElement record = getRecordByName(name);
            if (record != null) {
                swipeRightToLeft(record);
            } else {
                WebElement any = driver.findElements(AppiumBy.xpath(
                        "//android.view.ViewGroup[@clickable='true']")).get(0);
                swipeRightToLeft(any);
            }
        } catch (Exception e) {
            System.out.println("[WARN] Swipe failed: " + e.getMessage());
        }
    }

    public void longPressOperatorRecord(String name) {
        try {
            WebElement record = getRecordByName(name);
            if (record != null) longPress(record);
        } catch (Exception e) {
            System.out.println("[WARN] Long press failed: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  COMPOSITE HELPERS
    // ─────────────────────────────────────────────────────────────────────────

    public String createOperatorAndReturnName() {
        String name  = TestRandomDataGenerator.randomUserName();
        String phone = TestRandomDataGenerator.randomPhoneNumber();
        String code  = TestRandomDataGenerator.randomEmpCode();
        clickAddButton();
        clearAndType(operatorNameInput, name);
        scrollDown();
        clearAndType(phoneNumberInput, phone);
        scrollDown();
        selectBloodGroup();
        scrollDown();
        clearAndType(operatorCodeInput, code);
        clickSubmitButton();
        return name;
    }

    public void searchForOperator(String name) {
        tap(searchIcon);
        waitForVisibility(searchInput);
        searchInput.clear();
        searchInput.sendKeys(name);
    }

    public void searchSwipeAndEdit(String name) {
        searchForOperator(name);
        swipeOperatorRecord(name);
        clickEditButton();
    }

    public void searchAndLongPress(String name) {
        searchForOperator(name);
        longPressOperatorRecord(name);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  VISIBILITY CHECKS
    // ─────────────────────────────────────────────────────────────────────────

    public boolean isAddButtonVisible()        { return isDisplayed(addButton); }
    public boolean isEditButtonVisible()       { return isDisplayed(editButton); }
    public boolean isDeleteButtonVisible()     { return isDisplayed(deleteButton); }
    public boolean isSubmitButtonVisible()     { return isDisplayed(submitButton); }
    public boolean isOperatorNameFieldVisible(){ return isDisplayed(operatorNameInput); }

    public String getOperatorNameValue() {
        try { return operatorNameInput.getText(); } catch (Exception e) { return ""; }
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  VALIDATION ERROR CHECKS
    // ─────────────────────────────────────────────────────────────────────────

    public boolean isOperatorNameRequiredErrorDisplayed() { return isDisplayed(operatorNameRequiredError); }
    public boolean isPhoneNumberRequiredErrorDisplayed()  { return isDisplayed(phoneNumberRequiredError); }
    public boolean isBloodGroupRequiredErrorDisplayed()   { return isDisplayed(bloodGroupRequiredError); }
    public boolean isPhoneMustBe10DigitsErrorDisplayed()  { return isDisplayed(phoneMustBe10DigitsError); }
    public boolean isPhoneAlreadyExistsErrorDisplayed()   { return isDisplayed(phoneAlreadyExistsError); }
    public boolean isInvalidEmailErrorDisplayed()          { return isDisplayed(invalidEmailError); }
    public boolean isEmailAlreadyExistsErrorDisplayed()   { return isDisplayed(emailAlreadyExistsError); }
    public boolean isPinCodeErrorDisplayed()               { return isDisplayed(pinCodeError); }
    public boolean isDobFutureErrorDisplayed()             { return isDisplayed(dobFutureError); }
    public boolean isDojBeforeDobErrorDisplayed()          { return isDisplayed(dojBeforeDobError); }

    public boolean isAnyValidationErrorDisplayed() {
        return isOperatorNameRequiredErrorDisplayed()
            || isPhoneNumberRequiredErrorDisplayed()
            || isBloodGroupRequiredErrorDisplayed()
            || isPhoneMustBe10DigitsErrorDisplayed()
            || isPhoneAlreadyExistsErrorDisplayed()
            || isInvalidEmailErrorDisplayed()
            || isEmailAlreadyExistsErrorDisplayed()
            || isPinCodeErrorDisplayed();
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  BACKWARD-COMPATIBLE ALIASES (for old OperatorTest.java)
    // ─────────────────────────────────────────────────────────────────────────

    /** @deprecated Use {@link #enterEmail(String)} */
    public void enterOptionalEmail(String email) { enterEmail(email); }

    public void clickBackButton() {
        try { driver.navigate().back(); } catch (Exception e) {
            System.out.println("[WARN] Back navigation failed: " + e.getMessage());
        }
    }
}
