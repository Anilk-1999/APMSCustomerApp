package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import pageObject.BasePage;
import utilities.DataGenerator;

import java.util.List;

/**
 * Page Object for the Reasons Configuration module.
 *
 * Covers: Reasons list screen, Add New Reason popup,
 *         Edit Reason popup, View Reason popup,
 *         Machine Subscription popup, Status-change confirmation.
 *
 * Locator priority: accessibilityId → id → AndroidUIAutomator → XPath (last resort)
 * NOTE: Update locators after UI inspection with Appium Inspector.
 */
public class ReasonPage extends BasePage {

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
    //  ADD / EDIT POPUP — DROPDOWN FIELDS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Reason Name'] | "
            + "//android.widget.TextView[@text='Reason Name']/following-sibling::*[@clickable='true']")
    private WebElement reasonNameDropdown;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Reason Type'] | "
            + "//android.widget.TextView[@text='Reason Type']/following-sibling::*[@clickable='true']")
    private WebElement reasonTypeDropdown;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Reason Group'] | "
            + "//android.widget.TextView[@text='Reason Group']/following-sibling::*[@clickable='true']")
    private WebElement reasonGroupDropdown;

    // ═══════════════════════════════════════════════════════════
    //  ADD / EDIT POPUP — TIMEOUT TOGGLE + STOP TRIGGER
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.Switch[@content-desc='Timeout' or contains(@resource-id,'timeout')] | "
            + "//android.widget.CheckBox[@content-desc='Timeout']")
    private WebElement timeoutToggle;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Stop Trigger'] | "
            + "//android.widget.TextView[@text='Stop Trigger']/following-sibling::*[@clickable='true'] | "
            + "//android.widget.EditText[@hint='Stop Trigger' or @hint='HH:MM:SS']")
    private WebElement stopTriggerField;

    // ═══════════════════════════════════════════════════════════
    //  VIEW / EDIT — READ-ONLY FIELDS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'RSN-') or @content-desc='Reason ID']")
    private WebElement reasonIdField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Active' or @text='Inactive' or @content-desc='Status']")
    private WebElement statusField;

    // ═══════════════════════════════════════════════════════════
    //  POPUP BUTTONS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "Submit")
    private WebElement submitButton;

    @AndroidFindBy(accessibility = "Save")
    private WebElement saveButton;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Close' or @content-desc='close' or @content-desc='X']")
    private WebElement closeButton;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Yes, Change' or @text='Yes, Change']")
    private WebElement yesChangeButton;

    // ═══════════════════════════════════════════════════════════
    //  SWIPE ACTION — EDIT BUTTON
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Edit'] | //android.view.ViewGroup[@content-desc='Edit']")
    private WebElement editButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Machine Subscription'] | "
            + "//android.view.ViewGroup[@content-desc='Machine Subscription']")
    private WebElement machineSubscriptionOption;

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERROR MESSAGES
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Reason Name is required')]")
    private WebElement reasonNameRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Reason Type is required')]")
    private WebElement reasonTypeRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Reason Group is required')]")
    private WebElement reasonGroupRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Stop Trigger is required') "
            + "or contains(@text,'Duration must be') or contains(@text,'greater than 30')]")
    private WebElement stopTriggerError;

    // ═══════════════════════════════════════════════════════════
    //  MACHINE SUBSCRIPTION POPUP
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='+'] | //android.view.ViewGroup[@content-desc='+']")
    private WebElement machineSubAddIcon;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Select Machines' or @text='Machines']")
    private WebElement selectMachinesBottomSheet;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@clickable='true']")
    private List<WebElement> machineListItems;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='delete' or @content-desc='Delete']")
    private List<WebElement> machineDeleteIcons;

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public ReasonPage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickAddButton() {
        tap(addButton);
    }

    public boolean isAddButtonVisible() {
        return isDisplayed(addButton);
    }

    public void clickSearchIcon() {
        tap(searchIcon);
    }

    public void tapSearchInput() {
        tap(searchInput);
    }

    public void clearSearchField() {
        clearField(searchInput);
    }

    public void enterSearchText(String text) {
        clearAndType(searchInput, text);
    }

    public WebElement getRecordByName(String name) {
        try {
            scrollToText(name);
            return driver.findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().textContains(\"" + name + "\")"));
        } catch (Exception e) {
            return null;
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  ADD / EDIT POPUP ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void selectReasonNameFromDropdown() {
        tap(reasonNameDropdown);
        selectFirstDropdownItem();
    }

    public void selectReasonTypeFromDropdown() {
        tap(reasonTypeDropdown);
        selectFirstDropdownItem();
    }

    public void selectReasonGroupFromDropdown() {
        tap(reasonGroupDropdown);
        selectFirstDropdownItem();
    }

    private void selectFirstDropdownItem() {
        try {
            List<WebElement> items = driver.findElements(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().className(\"android.view.ViewGroup\").clickable(true)"));
            if (!items.isEmpty()) tap(items.get(0));
        } catch (Exception e) {
            // no items available
        }
    }

    public void fillAllMandatoryFields() {
        selectReasonNameFromDropdown();
        selectReasonTypeFromDropdown();
        selectReasonGroupFromDropdown();
    }

    public void enableTimeoutToggle() {
        enableToggle(timeoutToggle);
    }

    public void disableTimeoutToggle() {
        disableToggle(timeoutToggle);
    }

    public boolean isTimeoutEnabled() {
        return isToggleOn(timeoutToggle);
    }

    public void clickStopTriggerField() {
        tap(stopTriggerField);
    }

    public void selectValidDuration() {
        try {
            WebElement saveBtn = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"Save\")"));
            tap(saveBtn);
        } catch (Exception e) {
            // duration picker may not be open
        }
    }

    public void clickSubmitButton() {
        tap(submitButton);
    }

    public void clickSaveButton() {
        tap(saveButton);
    }

    public void clickCloseButton() {
        tap(closeButton);
    }

    public void clickYesChangeButton() {
        tap(yesChangeButton);
    }

    public void clickStatusButton() {
        tap(statusField);
    }

    // ═══════════════════════════════════════════════════════════
    //  SWIPE TO EDIT / MACHINE SUBSCRIPTION
    // ═══════════════════════════════════════════════════════════

    public void swipeRecordRightToLeft(WebElement record) {
        swipeRightToLeft(record);
    }

    public void clickEditButton() {
        tap(editButton);
    }

    public void clickMachineSubscriptionOption() {
        tap(machineSubscriptionOption);
    }

    public void longPressRecord(WebElement record) {
        longPress(record);
    }

    // ═══════════════════════════════════════════════════════════
    //  STATE CHECKS — POPUP VISIBILITY
    // ═══════════════════════════════════════════════════════════

    public boolean isSubmitButtonVisible() {
        return isDisplayed(submitButton);
    }

    public boolean isSaveButtonVisible() {
        return isDisplayed(saveButton);
    }

    public boolean isEditButtonVisible() {
        return isDisplayed(editButton);
    }

    public boolean isMachineSubscriptionOptionVisible() {
        return isDisplayed(machineSubscriptionOption);
    }

    public boolean isStopTriggerFieldVisible() {
        return isDisplayed(stopTriggerField);
    }

    public boolean isReasonIdVisible() {
        return isDisplayed(reasonIdField);
    }

    public boolean isStatusVisible() {
        return isDisplayed(statusField);
    }

    public String getStatusValue() {
        return getText(statusField);
    }

    // ═══════════════════════════════════════════════════════════
    //  STATE CHECKS — VALIDATION ERRORS
    // ═══════════════════════════════════════════════════════════

    public boolean isReasonNameRequiredErrorDisplayed() {
        return isDisplayed(reasonNameRequiredError);
    }

    public boolean isReasonTypeRequiredErrorDisplayed() {
        return isDisplayed(reasonTypeRequiredError);
    }

    public boolean isReasonGroupRequiredErrorDisplayed() {
        return isDisplayed(reasonGroupRequiredError);
    }

    public boolean isStopTriggerErrorDisplayed() {
        return isDisplayed(stopTriggerError);
    }

    public boolean isAnyValidationErrorDisplayed() {
        return isReasonNameRequiredErrorDisplayed()
                || isReasonTypeRequiredErrorDisplayed()
                || isReasonGroupRequiredErrorDisplayed()
                || isStopTriggerErrorDisplayed();
    }

    // ═══════════════════════════════════════════════════════════
    //  MACHINE SUBSCRIPTION ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickMachineSubAddIcon() {
        tap(machineSubAddIcon);
    }

    public boolean isSelectMachinesBottomSheetVisible() {
        return isDisplayed(selectMachinesBottomSheet);
    }

    public void selectMultipleMachines(int count) {
        try {
            for (int i = 0; i < Math.min(count, machineListItems.size()); i++) {
                tap(machineListItems.get(i));
            }
        } catch (Exception e) {
            // fewer items than requested
        }
    }

    public boolean areMachineDeleteIconsVisible() {
        return !machineDeleteIcons.isEmpty();
    }

    public void clickFirstMachineDeleteIcon() {
        if (!machineDeleteIcons.isEmpty()) tap(machineDeleteIcons.get(0));
    }

    // ═══════════════════════════════════════════════════════════
    //  COMPOSITE HELPERS
    // ═══════════════════════════════════════════════════════════

    public void searchSwipeAndOpenEdit(String name) {
        clickSearchIcon();
        tapSearchInput();
        clearSearchField();
        enterSearchText(name);
        WebElement record = getRecordByName(name);
        if (record != null) swipeRecordRightToLeft(record);
        clickEditButton();
    }

    public void searchAndOpenView(String name) {
        clickSearchIcon();
        tapSearchInput();
        clearSearchField();
        enterSearchText(name);
        WebElement record = getRecordByName(name);
        if (record != null) tap(record);
    }

    public String createReasonAndReturnName() {
        clickAddButton();
        fillAllMandatoryFields();
        clickSubmitButton();
        return DataGenerator.randomReasonName();
    }
}
