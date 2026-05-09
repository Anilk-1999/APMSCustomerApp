package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import pageObject.BasePage;
import utilities.DataGenerator;

import java.util.List;

/**
 * Page Object for the Products Configuration module.
 *
 * Covers: Products list screen, Add New Product popup,
 *         Edit Product popup, View Product popup,
 *         Raw Materials bottom sheet, Machine Subscription bottom sheet,
 *         PDF upload, Status-change confirmation.
 *
 * Locator priority: accessibilityId → id → AndroidUIAutomator → XPath (last resort)
 * NOTE: Update locators after UI inspection with Appium Inspector.
 */
public class ProductPage extends BasePage {

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
    //  ADD / EDIT POPUP — TEXT FIELDS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Product Name' or @hint='Name']")
    private WebElement productNameInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Product Code' or @hint='Code']")
    private WebElement productCodeInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Description' or @hint='description']")
    private WebElement descriptionInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Conversion Value' or @hint='Value']")
    private WebElement conversionValueInput;

    // ═══════════════════════════════════════════════════════════
    //  ADD / EDIT POPUP — DROPDOWN FIELDS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Product Group'] | "
            + "//android.widget.TextView[@text='Product Group']/following-sibling::*[@clickable='true']")
    private WebElement productGroupDropdown;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Product UOM'] | "
            + "//android.widget.TextView[@text='Product UOM' or @text='UOM']/following-sibling::*[@clickable='true']")
    private WebElement productUOMDropdown;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Conversion UOM'] | "
            + "//android.widget.TextView[@text='Conversion UOM']/following-sibling::*[@clickable='true']")
    private WebElement conversionUOMDropdown;

    // ═══════════════════════════════════════════════════════════
    //  ADD / EDIT POPUP — RAW MATERIALS + MACHINE SUBSCRIPTION
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Raw Materials']/following-sibling::*[@content-desc='+' or @text='+'] | "
            + "//android.view.ViewGroup[@content-desc='Add Raw Material']")
    private WebElement rawMaterialsAddButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Machine Subscription']/following-sibling::*[@content-desc='+' or @text='+'] | "
            + "//android.view.ViewGroup[@content-desc='Add Machine']")
    private WebElement machineSubAddButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Select Raw Materials' or @text='Raw Materials']")
    private WebElement rawMaterialsBottomSheet;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Select Machines' or @text='Machines']")
    private WebElement machinesBottomSheet;

    // ═══════════════════════════════════════════════════════════
    //  PDF UPLOAD
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Upload PDF'] | "
            + "//android.view.ViewGroup[@content-desc='Upload PDF']")
    private WebElement uploadPDFOption;

    // ═══════════════════════════════════════════════════════════
    //  READ-ONLY FIELDS (Edit / View popup)
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'PRD-') or @content-desc='Product ID']")
    private WebElement productIdField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Active' or @text='Inactive' or @content-desc='Status']")
    private WebElement statusField;

    // ═══════════════════════════════════════════════════════════
    //  POPUP BUTTONS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "Save")
    private WebElement saveButton;

    @AndroidFindBy(accessibility = "Submit")
    private WebElement submitButton;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Close' or @content-desc='close' or @content-desc='X']")
    private WebElement closeButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Edit'] | //android.view.ViewGroup[@content-desc='Edit']")
    private WebElement editButton;

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERROR MESSAGES
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Product Name is required')]")
    private WebElement productNameRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Product UOM is required')]")
    private WebElement productUOMRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Conversion Value is required') "
            + "or contains(@text,'Invalid Conversion') or contains(@text,'numeric')]")
    private WebElement conversionValueError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Conversion UOM is required')]")
    private WebElement conversionUOMRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'duplicate') or contains(@text,'already exists')]")
    private WebElement duplicateError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Invalid file') or contains(@text,'file format')]")
    private WebElement invalidFileError;

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public ProductPage(AndroidDriver driver) {
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

    public void enterProductName(String name) { clearAndType(productNameInput, name); }

    public void enterProductCode(String code) { clearAndType(productCodeInput, code); }

    public void enterDescription(String desc) { clearAndType(descriptionInput, desc); }

    public void enterConversionValue(String value) { clearAndType(conversionValueInput, value); }

    public void selectProductGroup() { tap(productGroupDropdown); selectFirstDropdownItem(); }

    public void selectProductUOM() { tap(productUOMDropdown); selectFirstDropdownItem(); }

    public void selectConversionUOM() { tap(conversionUOMDropdown); selectFirstDropdownItem(); }

    private void selectFirstDropdownItem() {
        try {
            List<WebElement> items = driver.findElements(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().className(\"android.view.ViewGroup\").clickable(true)"));
            if (!items.isEmpty()) tap(items.get(0));
        } catch (Exception ignored) {}
    }

    public void fillMandatoryFields() {
        enterProductName(DataGenerator.randomProductName());
        selectProductUOM();
        enterConversionValue("1.00");
        selectConversionUOM();
    }

    // ═══════════════════════════════════════════════════════════
    //  RAW MATERIALS / MACHINE SUBSCRIPTION
    // ═══════════════════════════════════════════════════════════

    public void clickRawMaterialsAddButton() { tap(rawMaterialsAddButton); }

    public boolean isRawMaterialsBottomSheetDisplayed() { return isDisplayed(rawMaterialsBottomSheet); }

    public void clickMachineSubAddButton() { tap(machineSubAddButton); }

    public boolean isMachinesBottomSheetDisplayed() { return isDisplayed(machinesBottomSheet); }

    public void selectMultipleItems(int count) {
        try {
            List<WebElement> items = driver.findElements(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().className(\"android.view.ViewGroup\").clickable(true)"));
            for (int i = 0; i < Math.min(count, items.size()); i++) tap(items.get(i));
        } catch (Exception ignored) {}
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP BUTTON ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickSaveButton() { tap(saveButton); }

    public void clickSubmitButton() { tap(submitButton); }

    public void clickCloseButton() { tap(closeButton); }

    public void clickEditButton() { tap(editButton); }

    public void swipeRecordRightToLeft(WebElement record) { swipeRightToLeft(record); }

    // ═══════════════════════════════════════════════════════════
    //  STATE CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isSaveButtonVisible() { return isDisplayed(saveButton); }

    public boolean isSubmitButtonVisible() { return isDisplayed(submitButton); }

    public boolean isEditButtonVisible() { return isDisplayed(editButton); }

    public boolean isProductIdVisible() { return isDisplayed(productIdField); }

    public boolean isProductNameFieldVisible() { return isDisplayed(productNameInput); }

    public boolean isProductCodeFieldVisible() { return isDisplayed(productCodeInput); }

    public boolean isProductGroupDropdownVisible() { return isDisplayed(productGroupDropdown); }

    public boolean isDescriptionFieldVisible() { return isDisplayed(descriptionInput); }

    public boolean isProductUOMDropdownVisible() { return isDisplayed(productUOMDropdown); }

    public boolean isConversionValueFieldVisible() { return isDisplayed(conversionValueInput); }

    public boolean isConversionUOMDropdownVisible() { return isDisplayed(conversionUOMDropdown); }

    public boolean isRawMaterialsSectionVisible() { return isDisplayed(rawMaterialsAddButton); }

    public boolean isMachineSubscriptionSectionVisible() { return isDisplayed(machineSubAddButton); }

    public boolean isUploadPDFOptionVisible() { return isDisplayed(uploadPDFOption); }

    public boolean isProductNameRequiredErrorDisplayed() { return isDisplayed(productNameRequiredError); }

    public boolean isProductUOMRequiredErrorDisplayed() { return isDisplayed(productUOMRequiredError); }

    public boolean isConversionValueErrorDisplayed() { return isDisplayed(conversionValueError); }

    public boolean isConversionUOMRequiredErrorDisplayed() { return isDisplayed(conversionUOMRequiredError); }

    public boolean isDuplicateErrorDisplayed() { return isDisplayed(duplicateError); }

    public boolean isInvalidFileErrorDisplayed() { return isDisplayed(invalidFileError); }

    public boolean isAnyValidationErrorDisplayed() {
        return isProductNameRequiredErrorDisplayed() || isProductUOMRequiredErrorDisplayed()
                || isConversionValueErrorDisplayed() || isConversionUOMRequiredErrorDisplayed();
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

    public String createProductAndReturnName() {
        String name = DataGenerator.randomProductName();
        clickAddButton();
        enterProductName(name);
        selectProductUOM();
        enterConversionValue("1.00");
        selectConversionUOM();
        clickSaveButton();
        return name;
    }
}
