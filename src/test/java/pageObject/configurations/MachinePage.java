package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import pageObject.BasePage;
import utilities.DataGenerator;

import java.util.List;

/**
 * Page Object for the Machines Configuration module.
 *
 * Covers: Machine list screen, Add Machine screen, Update Machine screen,
 *         View Machine screen, License Type sections (Production/Monitor/Maintenance),
 *         Shifts, Toggles, Duration pickers, Dropdowns, Validation messages.
 *
 * Locator priority: accessibilityId → id → AndroidUIAutomator → XPath (last resort)
 * NOTE: Update locators after UI inspection with Appium Inspector.
 */
public class MachinePage extends BasePage {

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "Add")
    private WebElement addButton;

    @AndroidFindBy(accessibility = "Search")
    private WebElement searchIcon;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Search' or @hint='search']")
    private WebElement searchInput;

    // ═══════════════════════════════════════════════════════════
    //  ADD / EDIT SCREEN — COMMON FIELDS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Machine Name' or @hint='Name']")
    private WebElement machineNameInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Machine Code' or @hint='Code']")
    private WebElement machineCodeInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Location']")
    private WebElement locationInput;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Machine Brand' or @content-desc='Machine Brand'] "
            + "| //android.view.ViewGroup[contains(@content-desc,'Machine Brand')]")
    private WebElement machineBrandDropdown;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Machine Type' or @content-desc='Machine Type'] "
            + "| //android.view.ViewGroup[contains(@content-desc,'Machine Type')]")
    private WebElement machineTypeDropdown;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='IoT Device Type' or @content-desc='IoT Device Type'] "
            + "| //android.view.ViewGroup[contains(@content-desc,'IoT Device Type')]")
    private WebElement iotDeviceTypeDropdown;

    // ═══════════════════════════════════════════════════════════
    //  LICENSE TYPE SELECTION
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Production' or @content-desc='Production'] "
            + "| //android.widget.RadioButton[@text='Production']")
    private WebElement licenseTypeProduction;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Monitor' or @content-desc='Monitor'] "
            + "| //android.widget.RadioButton[@text='Monitor']")
    private WebElement licenseTypeMonitor;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Maintenance' or @content-desc='Maintenance'] "
            + "| //android.widget.RadioButton[@text='Maintenance']")
    private WebElement licenseTypeMaintenance;

    // ═══════════════════════════════════════════════════════════
    //  SHIFTS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Add Shift'] "
            + "| //android.widget.Button[contains(@content-desc,'Add Shift') or contains(@text,'Add Shift')]")
    private WebElement addShiftButton;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'Shift') and @clickable='true']")
    private List<WebElement> shiftItems;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Delete Shift' or @content-desc='Remove Shift']")
    private List<WebElement> deleteShiftButtons;

    // ═══════════════════════════════════════════════════════════
    //  PRODUCTION LICENSE — TOGGLES
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.Switch[@content-desc='Production Plan'] "
            + "| //android.widget.CheckBox[@content-desc='Production Plan']")
    private WebElement productionPlanToggle;

    @AndroidFindBy(xpath = "//android.widget.Switch[contains(@content-desc,'Maintenance') "
            + "and not(contains(@content-desc,'Login'))]")
    private WebElement maintenanceToggle;

    @AndroidFindBy(xpath = "//android.widget.Switch[@content-desc='Authorization Feature'] "
            + "| //android.widget.CheckBox[@content-desc='Authorization Feature']")
    private WebElement authorizationFeatureToggle;

    @AndroidFindBy(xpath = "//android.widget.Switch[@content-desc='Machine Setup Approval']")
    private WebElement machineSetupApprovalToggle;

    @AndroidFindBy(xpath = "//android.widget.Switch[@content-desc='Production Approval']")
    private WebElement productionApprovalToggle;

    @AndroidFindBy(xpath = "//android.widget.Switch[contains(@content-desc,'Stop Machine Without PA')]")
    private WebElement stopMachineWithoutPAToggle;

    @AndroidFindBy(xpath = "//android.widget.Switch[@content-desc='Image Required for Stop Reason']")
    private WebElement imageRequiredForStopReasonToggle;

    @AndroidFindBy(xpath = "//android.widget.Switch[@content-desc='Raw Material Entry']")
    private WebElement rawMaterialEntryToggle;

    @AndroidFindBy(xpath = "//android.widget.Switch[@content-desc='Scrap Entry']")
    private WebElement scrapEntryToggle;

    @AndroidFindBy(xpath = "//android.widget.Switch[@content-desc='Maintenance Login']")
    private WebElement maintenanceLoginToggle;

    // ═══════════════════════════════════════════════════════════
    //  PRODUCTION LICENSE — NUMERIC / DURATION FIELDS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Downtime (Days)' or @hint='Downtime']")
    private WebElement downtimeDaysInput;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'Timeout Duration') "
            + "and not(contains(@content-desc,'Idle'))] "
            + "| //android.widget.TextView[@text='Timeout Duration']")
    private WebElement timeoutDurationField;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'Idle Timeout')] "
            + "| //android.widget.TextView[@text='Idle Timeout Duration']")
    private WebElement idleTimeoutDurationField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Stoppage Timer (Min)' or @hint='Stoppage Timer']")
    private WebElement stoppageTimerInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'Reason Image Upload Timer')]")
    private WebElement reasonImageUploadTimerInput;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'Machine Offline Timeout')] "
            + "| //android.widget.EditText[@hint='Machine Offline Timeout (Days)']")
    private WebElement machineOfflineTimeoutField;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'Manual Health Reminder')] "
            + "| //android.widget.EditText[@hint='Manual Health Reminder (Hrs)']")
    private WebElement manualHealthReminderField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Login Type' or @content-desc='Login Type'] "
            + "| //android.view.ViewGroup[contains(@content-desc,'Login Type')]")
    private WebElement loginTypeDropdown;

    // ═══════════════════════════════════════════════════════════
    //  MONITOR LICENSE — FIELDS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Operator' or @content-desc='Operator'] "
            + "| //android.view.ViewGroup[contains(@content-desc,'Operator')]")
    private WebElement operatorDropdown;

    @AndroidFindBy(xpath = "//android.widget.Switch[@content-desc='Default Machine Status']")
    private WebElement defaultMachineStatusToggle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Reason' or @content-desc='Reason'] "
            + "| //android.view.ViewGroup[@content-desc='Reason']")
    private WebElement reasonDropdown;

    // ═══════════════════════════════════════════════════════════
    //  SUBMIT / SAVE BUTTONS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "Submit")
    private WebElement submitButton;

    @AndroidFindBy(accessibility = "Save")
    private WebElement saveButton;

    // ═══════════════════════════════════════════════════════════
    //  SWIPE ACTION MENU BUTTONS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "Edit")
    private WebElement editButton;

    @AndroidFindBy(accessibility = "View")
    private WebElement viewButton;

    // ═══════════════════════════════════════════════════════════
    //  SCREEN HEADERS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Add Machine' or @text='Create Machine']")
    private WebElement addMachineHeader;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Update Machine' or @text='Edit Machine']")
    private WebElement updateMachineHeader;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='View Machine' or @text='Machine Details']")
    private WebElement viewMachineHeader;

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION MESSAGES
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Machine Name is required')]")
    private WebElement machineNameRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Machine Code already exists')]")
    private WebElement machineCodeDuplicateError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Machine Brand is required')]")
    private WebElement machineBrandRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Machine Type is required')]")
    private WebElement machineTypeRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'IoT Device Type is required')]")
    private WebElement iotDeviceTypeRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Downtime is required')]")
    private WebElement downtimeRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Reason is required')]")
    private WebElement reasonRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'overlap') or contains(@text,'Overlap')]")
    private WebElement overlapValidationError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Invalid value') or contains(@text,'invalid value')]")
    private WebElement invalidValueError;

    /** Catches any generic required/validation error on screen */
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'required') or contains(@text,'Required') "
            + "or contains(@text,'invalid') or contains(@text,'Invalid')]")
    private WebElement genericValidationError;

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public MachinePage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickAddButton()        { tap(addButton); }
    public boolean isAddButtonVisible() { return isDisplayed(addButton); }

    public void clickSearchIcon()       { tap(searchIcon); }
    public void tapSearchInput()        { tap(searchInput); }
    public void clearSearchField()      { clearField(searchInput); }
    public void enterSearchText(String text) { clearAndType(searchInput, text); }

    public WebElement getRecordByName(String name) {
        try {
            return driver.findElement(
                    AppiumBy.xpath("//android.widget.TextView[@text='" + name + "']"));
        } catch (Exception e) {
            return null;
        }
    }

    public void swipeMachineRecord(String name) {
        WebElement el = getRecordByName(name);
        if (el != null) swipeRightToLeft(el);
    }

    // ═══════════════════════════════════════════════════════════
    //  COMMON FIELD ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void enterMachineName(String name)   { clearAndType(machineNameInput, name); }
    public void clearMachineNameField()          { clearField(machineNameInput); }
    public boolean isMachineNameFieldVisible()   { return isDisplayed(machineNameInput); }
    public String getMachineNameValue() {
        try { return machineNameInput.getText(); } catch (Exception e) { return ""; }
    }

    public void enterMachineCode(String code)   { clearAndType(machineCodeInput, code); }
    public void clearMachineCodeField()          { clearField(machineCodeInput); }

    public void enterLocation(String loc)        { clearAndType(locationInput, loc); }

    /** Taps a dropdown then picks the first available option in the list/sheet. */
    public void selectDropdownFirstOption(WebElement dropdown) {
        tap(dropdown);
        try {
            WebElement first = driver.findElement(AppiumBy.xpath(
                    "(//android.view.ViewGroup[@clickable='true' and @focusable='true'] "
                  + "| //android.widget.TextView[@clickable='true'])[1]"));
            tap(first);
        } catch (Exception e) {
            System.out.println("[WARN] No dropdown option found: " + e.getMessage());
        }
    }

    public void clickMachineBrandDropdown() { tap(machineBrandDropdown); }
    public void selectMachineBrand()        { selectDropdownFirstOption(machineBrandDropdown); }
    public void selectMachineType()       { selectDropdownFirstOption(machineTypeDropdown); }
    public void selectIoTDeviceType()     { selectDropdownFirstOption(iotDeviceTypeDropdown); }
    public void selectLoginType()         { selectDropdownFirstOption(loginTypeDropdown); }
    public void selectOperator()          { selectDropdownFirstOption(operatorDropdown); }
    public void selectReason()            { selectDropdownFirstOption(reasonDropdown); }

    // ═══════════════════════════════════════════════════════════
    //  LICENSE TYPE
    // ═══════════════════════════════════════════════════════════

    public void selectLicenseTypeProduction()  { tap(licenseTypeProduction); }
    public void selectLicenseTypeMonitor()     { tap(licenseTypeMonitor); }
    public void selectLicenseTypeMaintenance() { tap(licenseTypeMaintenance); }

    // ═══════════════════════════════════════════════════════════
    //  SHIFTS
    // ═══════════════════════════════════════════════════════════

    public void clickAddShift() { tap(addShiftButton); }

    public void addShifts(int count) {
        for (int i = 0; i < count; i++) {
            try { tap(addShiftButton); } catch (Exception e) { System.out.println("[WARN] Add shift attempt " + i + " failed: " + e.getMessage()); }
        }
    }

    public void deleteFirstShift() {
        if (!deleteShiftButtons.isEmpty()) tap(deleteShiftButtons.get(0));
    }

    public boolean hasShifts()  { return !shiftItems.isEmpty(); }
    public int getShiftCount()  { return shiftItems.size(); }

    // ═══════════════════════════════════════════════════════════
    //  TOGGLES  (reuse enableToggle from BasePage)
    // ═══════════════════════════════════════════════════════════

    public void enableProductionPlanToggle()           { enableToggle(productionPlanToggle); }
    public void enableMaintenanceToggle()               { enableToggle(maintenanceToggle); }
    public void enableAuthorizationFeatureToggle()      { enableToggle(authorizationFeatureToggle); }
    public void enableMachineSetupApprovalToggle()      { enableToggle(machineSetupApprovalToggle); }
    public void enableProductionApprovalToggle()        { enableToggle(productionApprovalToggle); }
    public void enableStopMachineWithoutPAToggle()      { enableToggle(stopMachineWithoutPAToggle); }
    public void enableImageRequiredForStopReasonToggle(){ enableToggle(imageRequiredForStopReasonToggle); }
    public void enableRawMaterialEntryToggle()          { enableToggle(rawMaterialEntryToggle); }
    public void enableScrapEntryToggle()                { enableToggle(scrapEntryToggle); }
    public void enableMaintenanceLoginToggle()          { enableToggle(maintenanceLoginToggle); }
    public void enableDefaultMachineStatusToggle()      { enableToggle(defaultMachineStatusToggle); }

    // ═══════════════════════════════════════════════════════════
    //  NUMERIC / DURATION FIELDS
    // ═══════════════════════════════════════════════════════════

    public void enterDowntimeDays(String value)          { clearAndType(downtimeDaysInput, value); }
    public void clickTimeoutDuration()                    { tap(timeoutDurationField); }
    public void clickIdleTimeoutDuration()                { tap(idleTimeoutDurationField); }
    public void enterStoppageTimer(String value)          { clearAndType(stoppageTimerInput, value); }
    public void enterReasonImageUploadTimer(String value) { clearAndType(reasonImageUploadTimerInput, value); }
    public void clickMachineOfflineTimeout()              { tap(machineOfflineTimeoutField); }
    public void clickManualHealthReminder()               { tap(manualHealthReminderField); }

    /** Confirms a duration picker by tapping the Save/OK button inside the popup. */
    public void confirmDurationPicker() {
        try {
            WebElement saveBtn = driver.findElement(AppiumBy.xpath(
                    "//android.widget.Button[@text='Save' or @text='OK' or @content-desc='Save' or @content-desc='OK']"));
            tap(saveBtn);
        } catch (Exception e) {
            System.out.println("[WARN] Duration picker confirm button not found");
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  BUTTONS
    // ═══════════════════════════════════════════════════════════

    public void clickSubmitButton()         { tap(submitButton); }
    public void clickSaveButton()           { tap(saveButton); }
    public void clickEditButton()           { tap(editButton); }
    public void clickViewButton()           { tap(viewButton); }

    public boolean isSaveButtonVisible()    { return isDisplayed(saveButton); }
    public boolean isEditButtonVisible()    { return isDisplayed(editButton); }
    public boolean isViewButtonVisible()    { return isDisplayed(viewButton); }

    // ═══════════════════════════════════════════════════════════
    //  SCREEN VISIBILITY CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isAddMachineScreenVisible()    { return isDisplayed(addMachineHeader); }
    public boolean isUpdateMachineScreenVisible() { return isDisplayed(updateMachineHeader); }
    public boolean isViewMachineScreenVisible()   { return isDisplayed(viewMachineHeader); }

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isMachineNameRequiredErrorDisplayed()   { return isDisplayed(machineNameRequiredError); }
    public boolean isMachineCodeDuplicateErrorDisplayed()  { return isDisplayed(machineCodeDuplicateError); }
    public boolean isMachineBrandRequiredErrorDisplayed()  { return isDisplayed(machineBrandRequiredError); }
    public boolean isMachineTypeRequiredErrorDisplayed()   { return isDisplayed(machineTypeRequiredError); }
    public boolean isIoTDeviceTypeRequiredErrorDisplayed() { return isDisplayed(iotDeviceTypeRequiredError); }
    public boolean isDowntimeRequiredErrorDisplayed()      { return isDisplayed(downtimeRequiredError); }
    public boolean isReasonRequiredErrorDisplayed()        { return isDisplayed(reasonRequiredError); }
    public boolean isOverlapValidationErrorDisplayed()     { return isDisplayed(overlapValidationError); }
    public boolean isInvalidValueErrorDisplayed()          { return isDisplayed(invalidValueError); }
    public boolean isAnyValidationErrorDisplayed()         { return isDisplayed(genericValidationError); }

    // ═══════════════════════════════════════════════════════════
    //  COMPOSITE HELPERS
    // ═══════════════════════════════════════════════════════════

    /**
     * Creates a machine with mandatory fields only, returns the machine name.
     * Used in Background pre-condition step for Update and View features.
     */
    public String createMachineAndReturnName() {
        String name = DataGenerator.randomMachineName();
        String code = DataGenerator.randomMachineCode();
        clickAddButton();
        enterMachineName(name);
        enterMachineCode(code);
        enterLocation("Test Location");
        selectMachineBrand();
        selectMachineType();
        selectIoTDeviceType();
        selectLicenseTypeProduction();
        clickTimeoutDuration();
        confirmDurationPicker();
        clickIdleTimeoutDuration();
        confirmDurationPicker();
        selectLoginType();
        addShifts(2);
        clickSubmitButton();
        return name;
    }

    /**
     * Clicks search icon and types machine name into search field.
     */
    public void searchForMachine(String name) {
        clickSearchIcon();
        tapSearchInput();
        clearSearchField();
        enterSearchText(name);
    }

    /**
     * Searches, swipes, opens Edit screen.
     */
    public void searchSwipeAndOpenEdit(String name) {
        searchForMachine(name);
        swipeMachineRecord(name);
        clickEditButton();
    }

    /**
     * Searches, swipes, opens View screen.
     */
    public void searchSwipeAndOpenView(String name) {
        searchForMachine(name);
        swipeMachineRecord(name);
        clickViewButton();
    }
}
