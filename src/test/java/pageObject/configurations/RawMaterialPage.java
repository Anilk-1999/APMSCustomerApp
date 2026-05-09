package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import pageObject.BasePage;
import utilities.DataGenerator;

import java.util.List;

/**
 * Page Object for the Raw Materials Configuration module.
 *
 * Covers: Raw Materials list screen, Add Raw Material popup,
 *         Edit Raw Material popup, View Raw Material popup.
 *
 * Locator priority: accessibilityId → id → AndroidUIAutomator → XPath (last resort)
 * NOTE: Update locators after UI inspection with Appium Inspector.
 */
public class RawMaterialPage extends BasePage {

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

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Name' or @hint='Raw Material Name']")
    private WebElement nameInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Description' or @hint='description']")
    private WebElement descriptionInput;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='UOM'] | "
            + "//android.widget.TextView[@text='UOM']/following-sibling::*[@clickable='true']")
    private WebElement uomDropdown;

    // ═══════════════════════════════════════════════════════════
    //  READ-ONLY FIELDS (Edit / View popup)
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'RM-') or @content-desc='Raw Material ID']")
    private WebElement rawMaterialIdField;

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

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Name is required') or contains(@text,'name is required')]")
    private WebElement nameRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'UOM is required')]")
    private WebElement uomRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'already exists') or contains(@text,'duplicate')]")
    private WebElement duplicateError;

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public RawMaterialPage(AndroidDriver driver) {
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

    public void enterName(String name) { clearAndType(nameInput, name); }

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

    public boolean isNameFieldVisible() { return isDisplayed(nameInput); }

    public boolean isUOMDropdownVisible() { return isDisplayed(uomDropdown); }

    public boolean isDescriptionFieldVisible() { return isDisplayed(descriptionInput); }

    public boolean isRawMaterialIdVisible() { return isDisplayed(rawMaterialIdField); }

    public boolean isNameRequiredErrorDisplayed() { return isDisplayed(nameRequiredError); }

    public boolean isUOMRequiredErrorDisplayed() { return isDisplayed(uomRequiredError); }

    public boolean isDuplicateErrorDisplayed() { return isDisplayed(duplicateError); }

    public boolean isAnyValidationErrorDisplayed() {
        return isNameRequiredErrorDisplayed() || isUOMRequiredErrorDisplayed();
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

    public String createRawMaterialAndReturnName() {
        String name = DataGenerator.randomRawMaterialName();
        clickAddButton();
        enterName(name);
        selectUOM();
        clickSubmitButton();
        return name;
    }
}
