package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import pageObject.BasePage;
import utilities.DataGenerator;

import java.util.List;

/**
 * Page Object for the Spares Configuration module.
 *
 * Covers: Spares list screen, Add New Spare popup,
 *         Edit Spare popup, View Spare popup.
 *
 * Locator priority: accessibilityId → id → AndroidUIAutomator → XPath (last resort)
 * NOTE: Update locators after UI inspection with Appium Inspector.
 */
public class SparePage extends BasePage {

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

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Spare Name' or @hint='Name']")
    private WebElement spareNameInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Spare Code' or @hint='Code']")
    private WebElement spareCodeInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Current Stock' or @hint='Stock']")
    private WebElement currentStockInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Description' or @hint='description']")
    private WebElement descriptionInput;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='UOM'] | "
            + "//android.widget.TextView[@text='UOM']/following-sibling::*[@clickable='true']")
    private WebElement uomDropdown;

    // ═══════════════════════════════════════════════════════════
    //  READ-ONLY FIELDS (Edit / View popup)
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'SPR-') or @content-desc='Spare ID']")
    private WebElement spareIdField;

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

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Spare Name is required') or contains(@text,'Name is required')]")
    private WebElement spareNameRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Spare Code is required') or contains(@text,'Code is required')]")
    private WebElement spareCodeRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'UOM is required')]")
    private WebElement uomRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Current Stock is required') or contains(@text,'Stock is required')]")
    private WebElement stockRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'invalid') or contains(@text,'numeric') or contains(@text,'format')]")
    private WebElement stockFormatError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'already exists') or contains(@text,'duplicate')]")
    private WebElement duplicateError;

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public SparePage(AndroidDriver driver) {
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

    public void enterSpareName(String name) { clearAndType(spareNameInput, name); }

    public void enterSpareCode(String code) { clearAndType(spareCodeInput, code); }

    public void enterCurrentStock(String stock) { clearAndType(currentStockInput, stock); }

    public void enterDescription(String desc) { clearAndType(descriptionInput, desc); }

    public void selectUOM() {
        tap(uomDropdown);
        try {
            List<WebElement> items = driver.findElements(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().className(\"android.view.ViewGroup\").clickable(true)"));
            if (!items.isEmpty()) tap(items.get(0));
        } catch (Exception ignored) {}
    }

    public void clickSubmitButton() { tap(submitButton); }

    public void clickSaveButton() { tap(saveButton); }

    public void clickCloseButton() { tap(closeButton); }

    public void clickEditButton() { tap(editButton); }

    public void swipeRecordRightToLeft(WebElement record) { swipeRightToLeft(record); }

    // ═══════════════════════════════════════════════════════════
    //  STATE CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isSubmitButtonVisible() { return isDisplayed(submitButton); }

    public boolean isSaveButtonVisible() { return isDisplayed(saveButton); }

    public boolean isEditButtonVisible() { return isDisplayed(editButton); }

    public boolean isSpareNameFieldVisible() { return isDisplayed(spareNameInput); }

    public boolean isSpareCodeFieldVisible() { return isDisplayed(spareCodeInput); }

    public boolean isUOMDropdownVisible() { return isDisplayed(uomDropdown); }

    public boolean isCurrentStockFieldVisible() { return isDisplayed(currentStockInput); }

    public boolean isDescriptionFieldVisible() { return isDisplayed(descriptionInput); }

    public boolean isSpareIdVisible() { return isDisplayed(spareIdField); }

    public boolean isSpareNameRequiredErrorDisplayed() { return isDisplayed(spareNameRequiredError); }

    public boolean isSpareCodeRequiredErrorDisplayed() { return isDisplayed(spareCodeRequiredError); }

    public boolean isUOMRequiredErrorDisplayed() { return isDisplayed(uomRequiredError); }

    public boolean isStockRequiredErrorDisplayed() { return isDisplayed(stockRequiredError); }

    public boolean isStockFormatErrorDisplayed() { return isDisplayed(stockFormatError); }

    public boolean isDuplicateErrorDisplayed() { return isDisplayed(duplicateError); }

    public boolean isAnyValidationErrorDisplayed() {
        return isSpareNameRequiredErrorDisplayed() || isSpareCodeRequiredErrorDisplayed()
                || isUOMRequiredErrorDisplayed() || isStockRequiredErrorDisplayed()
                || isStockFormatErrorDisplayed();
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

    public String createSpareAndReturnName() {
        String name = DataGenerator.randomSpareName();
        clickAddButton();
        enterSpareName(name);
        enterSpareCode(DataGenerator.randomSpareCode());
        selectUOM();
        enterCurrentStock(DataGenerator.randomStockValue());
        clickSubmitButton();
        return name;
    }
}
