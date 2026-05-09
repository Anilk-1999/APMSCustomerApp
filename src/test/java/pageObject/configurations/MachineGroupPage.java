package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import pageObject.BasePage;
import utilities.DataGenerator;

import java.util.List;

/**
 * Page Object for the Machine Groups Configuration module.
 *
 * Covers: Machine Groups list screen, Add New Machine Group popup,
 *         Edit Machine Group popup, Select Machines bottom sheet,
 *         Status field (read-only in Edit popup), validation messages.
 *
 * Locator priority: accessibilityId → id → AndroidUIAutomator → XPath (last resort)
 * NOTE: Update locators after UI inspection with Appium Inspector.
 */
public class MachineGroupPage extends BasePage {

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

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Machine Group Name' or @hint='Group Name']")
    private WebElement machineGroupNameInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Description' or @hint='description']")
    private WebElement descriptionInput;

    /**
     * "+" icon that opens the Select Machines bottom sheet.
     * May be an ImageButton next to a "Machines" label.
     */
    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Add Machine' or @content-desc='Add Machines'] "
            + "| //android.widget.TextView[(@content-desc='+' or @text='+') "
            + "and (following-sibling::*[contains(@text,'Machine')] "
            + "or preceding-sibling::*[contains(@text,'Machine')])]")
    private WebElement addMachineIcon;

    // ═══════════════════════════════════════════════════════════
    //  VIEW / EDIT — ID & STATUS (read-only)
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='Machine Group ID' "
            + "or contains(@text,'MG-')]")
    private WebElement machineGroupIdField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='Status' "
            + "or @text='Active' or @text='Inactive']")
    private WebElement statusField;

    // ═══════════════════════════════════════════════════════════
    //  POPUP BUTTONS
    // ═══════════════════════════════════════════════════════════

    /** Submit button — serves both main popup and bottom sheet (only one visible at a time) */
    @AndroidFindBy(accessibility = "Submit")
    private WebElement submitButton;

    /** Close (X) button of any popup */
    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Close' "
            + "or @content-desc='X' or @content-desc='close']")
    private WebElement closeButton;

    // ═══════════════════════════════════════════════════════════
    //  EDIT BUTTON (after swipe)
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "Edit")
    private WebElement editButton;

    // ═══════════════════════════════════════════════════════════
    //  BOTTOM SHEET — SELECT MACHINES
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Select Machines']")
    private WebElement selectMachinesHeader;

    /** All selectable machine rows in the bottom sheet */
    @AndroidFindBy(xpath = "//android.widget.CheckBox | //android.view.ViewGroup[@checkable='true']")
    private List<WebElement> machineListItems;

    // ═══════════════════════════════════════════════════════════
    //  SELECTED MACHINES (shown inside popup summary section)
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'selected_machine') "
            + "or @content-desc='Selected Machine']")
    private List<WebElement> selectedMachinesInPopup;

    // ═══════════════════════════════════════════════════════════
    //  POPUP HEADER TEXT ELEMENTS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Add New Machine Group']")
    private WebElement addMachineGroupHeader;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Edit Machine Group']")
    private WebElement editMachineGroupHeader;

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION MESSAGES
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Machine Group Name is required') "
            + "or contains(@text,'Group Name is required')]")
    private WebElement machineGroupNameRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'At least one machine')]")
    private WebElement machineSelectionRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Machine Group already exists') "
            + "or contains(@text,'already exists')]")
    private WebElement machineGroupDuplicateError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'invalid') "
            + "or contains(@text,'Invalid') or contains(@text,'validation')]")
    private WebElement validationError;

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public MachineGroupPage(AndroidDriver driver) {
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
    //  MACHINE GROUP NAME ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void enterMachineGroupName(String name) { clearAndType(machineGroupNameInput, name); }
    public void clearMachineGroupNameField()        { clearField(machineGroupNameInput); }
    public boolean isMachineGroupNameFieldVisible() { return isDisplayed(machineGroupNameInput); }
    public String getMachineGroupNameValue() {
        try { return machineGroupNameInput.getText(); } catch (Exception e) { return ""; }
    }

    // ═══════════════════════════════════════════════════════════
    //  DESCRIPTION ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void enterDescription(String desc) { clearAndType(descriptionInput, desc); }
    public boolean isDescriptionFieldVisible() { return isDisplayed(descriptionInput); }
    public String getDescriptionValue() {
        try { return descriptionInput.getText(); } catch (Exception e) { return ""; }
    }

    // ═══════════════════════════════════════════════════════════
    //  ADD MACHINE "+" ICON
    // ═══════════════════════════════════════════════════════════

    public void clickAddMachineIcon()        { tap(addMachineIcon); }
    public boolean isAddMachineIconVisible() { return isDisplayed(addMachineIcon); }

    // ═══════════════════════════════════════════════════════════
    //  POPUP BUTTONS
    // ═══════════════════════════════════════════════════════════

    public void clickSubmitButton()         { tap(submitButton); }
    public boolean isSubmitButtonVisible()  { return isDisplayed(submitButton); }

    public void clickCloseButton()          { tap(closeButton); }
    public boolean isCloseButtonVisible()   { return isDisplayed(closeButton); }

    public void clickEditButton()           { tap(editButton); }
    public boolean isEditButtonVisible()    { return isDisplayed(editButton); }

    // ═══════════════════════════════════════════════════════════
    //  MACHINE GROUP ID & STATUS (read-only)
    // ═══════════════════════════════════════════════════════════

    public boolean isMachineGroupIdVisible() { return isDisplayed(machineGroupIdField); }
    public boolean isMachineGroupIdNonEditable() {
        try { return !machineGroupIdField.isEnabled(); } catch (Exception e) { return true; }
    }

    public boolean isStatusFieldVisible() { return isDisplayed(statusField); }
    public boolean isStatusFieldNonEditable() {
        try { return !statusField.isEnabled(); } catch (Exception e) { return true; }
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP DISPLAY CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isAddMachineGroupPopupDisplayed()  { return isDisplayed(addMachineGroupHeader); }
    public boolean isEditMachineGroupPopupDisplayed() { return isDisplayed(editMachineGroupHeader); }

    // ═══════════════════════════════════════════════════════════
    //  BOTTOM SHEET — SELECT MACHINES
    // ═══════════════════════════════════════════════════════════

    public boolean isSelectMachinesBottomSheetVisible() {
        try { return selectMachinesHeader.isDisplayed(); } catch (Exception e) { return false; }
    }

    /** Selects the first N machines available in the bottom sheet. */
    public void selectMachines(int count) {
        int toSelect = Math.min(count, machineListItems.size());
        for (int i = 0; i < toSelect; i++) {
            tap(machineListItems.get(i));
        }
    }

    /** Deselects already-checked machines (first N). */
    public void deselectMachines(int count) {
        int toDeselect = Math.min(count, machineListItems.size());
        for (int i = 0; i < toDeselect; i++) {
            WebElement item = machineListItems.get(i);
            // Only deselect if currently selected
            String checked = item.getAttribute("checked");
            if ("true".equals(checked)) {
                tap(item);
            }
        }
    }

    public boolean areMachineListItemsDisplayed() { return !machineListItems.isEmpty(); }

    public int getSelectedMachineCountInPopup() { return selectedMachinesInPopup.size(); }

    public boolean areSelectedMachinesVisibleInPopup() { return !selectedMachinesInPopup.isEmpty(); }

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION MESSAGE CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isMachineGroupNameRequiredErrorDisplayed()  { return isDisplayed(machineGroupNameRequiredError); }
    public boolean isMachineSelectionRequiredErrorDisplayed()  { return isDisplayed(machineSelectionRequiredError); }
    public boolean isMachineGroupDuplicateErrorDisplayed()     { return isDisplayed(machineGroupDuplicateError); }
    public boolean isValidationErrorDisplayed()                { return isDisplayed(validationError); }

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
     * Creates a Machine Group with a random name, selects one machine, and returns the name.
     * Used in Background pre-condition steps.
     */
    public String createMachineGroupAndReturnName() {
        String name = DataGenerator.randomMachineGroupName();
        clickAddButton();
        enterMachineGroupName(name);
        clickAddMachineIcon();
        selectMachines(1);
        clickSubmitButton();   // submit bottom sheet
        clickSubmitButton();   // submit main popup
        return name;
    }

    /**
     * Opens search, types the given name, and waits for results.
     */
    public void searchForMachineGroup(String name) {
        clickSearchIcon();
        tapSearchInput();
        clearSearchField();
        enterSearchText(name);
    }

    /**
     * Searches for the given name, swipes the record, then clicks Edit.
     */
    public void searchSwipeAndOpenEdit(String name) {
        searchForMachineGroup(name);
        WebElement listItem = getRecordByName(name);
        swipeRecordRightToLeft(listItem);
        clickEditButton();
    }
}
