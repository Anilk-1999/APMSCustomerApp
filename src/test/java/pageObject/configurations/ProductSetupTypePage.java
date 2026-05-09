package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import pageObject.BasePage;
import utilities.DataGenerator;

import java.util.List;

/**
 * Page Object for the Product Setup Types Configuration module.
 *
 * Covers: Product Setup Types list screen, Add popup,
 *         Edit popup, View popup, Machine Subscription popup.
 *
 * Duration fields (Machine Output Timer, Product Setup Timer) open a
 * HH:MM:SS scroll-picker popup — tap the field, scroll, then tap Save.
 *
 * Locator priority: accessibilityId → id → AndroidUIAutomator → XPath (last resort)
 * NOTE: Update locators after UI inspection with Appium Inspector.
 */
public class ProductSetupTypePage extends BasePage {

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN
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

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Product Setup Name' or @hint='Setup Name' or @hint='Name']")
    private WebElement productSetupNameInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Description' or @hint='description']")
    private WebElement descriptionInput;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Machine Output Timer'] | "
            + "//android.widget.TextView[@text='Machine Output Timer']/following-sibling::*[@clickable='true'] | "
            + "//android.widget.EditText[@hint='Machine Output Timer' or @hint='HH:MM:SS']")
    private WebElement machineOutputTimerField;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Product Setup Timer'] | "
            + "//android.widget.TextView[@text='Product Setup Timer']/following-sibling::*[@clickable='true'] | "
            + "//android.widget.EditText[@hint='Product Setup Timer']")
    private WebElement productSetupTimerField;

    // ═══════════════════════════════════════════════════════════
    //  DURATION PICKER POPUP
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Save'] | //android.view.ViewGroup[@content-desc='Save']")
    private WebElement durationSaveButton;

    // ═══════════════════════════════════════════════════════════
    //  MACHINE SUBSCRIPTION
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Machine Subscription'] | "
            + "//android.view.ViewGroup[@content-desc='Machine Subscription']")
    private WebElement machineSubscriptionOption;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='+'] | //android.view.ViewGroup[@content-desc='+']")
    private WebElement machineSubAddIcon;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Select Machines' or @text='Machines']")
    private WebElement selectMachinesBottomSheet;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='delete' or @content-desc='Delete']")
    private List<WebElement> machineDeleteIcons;

    // ═══════════════════════════════════════════════════════════
    //  READ-ONLY FIELDS (Edit / View popup)
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'PST-') or @content-desc='Product Setup ID']")
    private WebElement productSetupIdField;

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

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Edit'] | //android.view.ViewGroup[@content-desc='Edit']")
    private WebElement editButton;

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERRORS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Setup Name is required') or contains(@text,'Name is required')]")
    private WebElement nameRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Machine Output Timer is required') or contains(@text,'Timer is required')]")
    private WebElement machineTimerRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Product Setup Timer is required')]")
    private WebElement setupTimerRequiredError;

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public ProductSetupTypePage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickAddButton() { tap(addButton); }

    public boolean isAddButtonVisible() { return isDisplayed(addButton); }

    public void clickSearchIcon() { tap(searchIcon); }

    public void tapSearchInput() { tap(searchInput); }

    public void clearSearchField() { clearField(searchInput); }

    public void enterSearchText(String text) { clearAndType(searchInput, text); }

    public WebElement getRecordByName(String name) {
        try {
            scrollToText(name);
            return driver.findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().textContains(\"" + name + "\")"));
        } catch (Exception e) { return null; }
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP FIELD ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void enterProductSetupName(String name) { clearAndType(productSetupNameInput, name); }

    public void enterDescription(String desc) { clearAndType(descriptionInput, desc); }

    public void clickMachineOutputTimerField() { tap(machineOutputTimerField); }

    public void clickProductSetupTimerField() { tap(productSetupTimerField); }

    public void clickDurationSaveButton() { tap(durationSaveButton); }

    public void setMachineOutputTimerDuration() {
        tap(machineOutputTimerField);
        tap(durationSaveButton);
    }

    public void setProductSetupTimerDuration() {
        tap(productSetupTimerField);
        tap(durationSaveButton);
    }

    public void clickSubmitButton() { tap(submitButton); }

    public void clickSaveButton() { tap(saveButton); }

    public void clickCloseButton() { tap(closeButton); }

    public void clickEditButton() { tap(editButton); }

    public void swipeRecordRightToLeft(WebElement record) { swipeRightToLeft(record); }

    // ═══════════════════════════════════════════════════════════
    //  MACHINE SUBSCRIPTION ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickMachineSubscriptionOption() { tap(machineSubscriptionOption); }

    public boolean isMachineSubscriptionOptionVisible() { return isDisplayed(machineSubscriptionOption); }

    public void clickMachineSubAddIcon() { tap(machineSubAddIcon); }

    public boolean isSelectMachinesBottomSheetVisible() { return isDisplayed(selectMachinesBottomSheet); }

    public void selectMultipleMachines(int count) {
        try {
            List<WebElement> items = driver.findElements(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().className(\"android.view.ViewGroup\").clickable(true)"));
            for (int i = 0; i < Math.min(count, items.size()); i++) tap(items.get(i));
        } catch (Exception ignored) {}
    }

    public boolean areMachineDeleteIconsVisible() { return !machineDeleteIcons.isEmpty(); }

    public void clickFirstMachineDeleteIcon() {
        if (!machineDeleteIcons.isEmpty()) tap(machineDeleteIcons.get(0));
    }

    // ═══════════════════════════════════════════════════════════
    //  STATE CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isSubmitButtonVisible() { return isDisplayed(submitButton); }

    public boolean isSaveButtonVisible() { return isDisplayed(saveButton); }

    public boolean isEditButtonVisible() { return isDisplayed(editButton); }

    public boolean isProductSetupNameFieldVisible() { return isDisplayed(productSetupNameInput); }

    public boolean isDescriptionFieldVisible() { return isDisplayed(descriptionInput); }

    public boolean isMachineOutputTimerFieldVisible() { return isDisplayed(machineOutputTimerField); }

    public boolean isProductSetupTimerFieldVisible() { return isDisplayed(productSetupTimerField); }

    public boolean isProductSetupIdVisible() { return isDisplayed(productSetupIdField); }

    public boolean isNameRequiredErrorDisplayed() { return isDisplayed(nameRequiredError); }

    public boolean isMachineTimerRequiredErrorDisplayed() { return isDisplayed(machineTimerRequiredError); }

    public boolean isSetupTimerRequiredErrorDisplayed() { return isDisplayed(setupTimerRequiredError); }

    public boolean isAnyValidationErrorDisplayed() {
        return isNameRequiredErrorDisplayed() || isMachineTimerRequiredErrorDisplayed()
                || isSetupTimerRequiredErrorDisplayed();
    }

    // ═══════════════════════════════════════════════════════════
    //  COMPOSITE HELPERS
    // ═══════════════════════════════════════════════════════════

    public void searchSwipeAndOpenEdit(String name) {
        clickSearchIcon(); tapSearchInput(); clearSearchField(); enterSearchText(name);
        WebElement record = getRecordByName(name);
        if (record != null) swipeRecordRightToLeft(record);
        clickEditButton();
    }

    public void searchAndOpenView(String name) {
        clickSearchIcon(); tapSearchInput(); clearSearchField(); enterSearchText(name);
        WebElement record = getRecordByName(name);
        if (record != null) tap(record);
    }

    public String createProductSetupTypeAndReturnName() {
        String name = DataGenerator.randomProductSetupName();
        clickAddButton();
        enterProductSetupName(name);
        setMachineOutputTimerDuration();
        setProductSetupTimerDuration();
        clickSubmitButton();
        return name;
    }
}
