package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import pageObject.BasePage;
import utilities.DataGenerator;

/**
 * Page Object for the Product Groups Configuration module.
 *
 * Covers: Product Groups list screen, Add New Product Group popup,
 *         Edit Product Group popup, View Product Group popup,
 *         ID/Status (read-only in edit), validation messages.
 */
public class ProductGroupPage extends BasePage {

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
    //  ADD / EDIT POPUP FIELDS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Product Group Name' or @hint='Group Name']")
    private WebElement productGroupNameInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Product Group Code' or @hint='Group Code' or @hint='Code']")
    private WebElement productGroupCodeInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Description' or @hint='description']")
    private WebElement descriptionInput;

    // ═══════════════════════════════════════════════════════════
    //  READ-ONLY FIELDS (Edit / View popup)
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'PG-') or @content-desc='Product Group ID']")
    private WebElement productGroupIdField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Active' or @text='Inactive' "
            + "or @content-desc='Status']")
    private WebElement statusField;

    // ═══════════════════════════════════════════════════════════
    //  POPUP BUTTONS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "Submit")
    private WebElement submitButton;

    @AndroidFindBy(accessibility = "Save")
    private WebElement saveButton;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Close' "
            + "or @content-desc='X' or @content-desc='close']")
    private WebElement closeButton;

    @AndroidFindBy(accessibility = "Edit")
    private WebElement editButton;

    // ═══════════════════════════════════════════════════════════
    //  POPUP HEADER TEXT ELEMENTS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Add New Product Group']")
    private WebElement addProductGroupHeader;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Edit Product Group']")
    private WebElement editProductGroupHeader;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='View Product Group']")
    private WebElement viewProductGroupHeader;

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION MESSAGES
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Product Group Name is required')]")
    private WebElement nameRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Product Group Code is required')]")
    private WebElement codeRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Product Group Code already exists')]")
    private WebElement codeDuplicateError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Product Group already exists')]")
    private WebElement groupDuplicateError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'invalid') "
            + "or contains(@text,'Invalid') or contains(@text,'required') "
            + "or contains(@text,'exists')]")
    private WebElement genericValidationError;

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public ProductGroupPage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickAddButton()        { tap(addButton); }
    public boolean isAddButtonVisible() { return isDisplayed(addButton); }

    public void clickSearchIcon()       { tap(searchIcon); }
    public void enterSearchText(String text) { clearAndType(searchInput, text); }
    public void clearSearchField()      { clearField(searchInput); }

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

    public void swipeRecordRightToLeft(WebElement element) {
        swipeRightToLeft(element);
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP FIELD ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void enterProductGroupName(String name)   { clearAndType(productGroupNameInput, name); }
    public void clearProductGroupNameField()          { clearField(productGroupNameInput); }
    public boolean isProductGroupNameFieldVisible()   { return isDisplayed(productGroupNameInput); }
    public String getProductGroupNameValue() {
        try { return productGroupNameInput.getText(); } catch (Exception e) { return ""; }
    }

    public void enterProductGroupCode(String code)   { clearAndType(productGroupCodeInput, code); }
    public void clearProductGroupCodeField()          { clearField(productGroupCodeInput); }
    public boolean isProductGroupCodeFieldVisible()   { return isDisplayed(productGroupCodeInput); }
    public String getProductGroupCodeValue() {
        try { return productGroupCodeInput.getText(); } catch (Exception e) { return ""; }
    }

    public void enterDescription(String desc)        { clearAndType(descriptionInput, desc); }
    public boolean isDescriptionFieldVisible()        { return isDisplayed(descriptionInput); }

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
    //  POPUP DISPLAY CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isAddProductGroupPopupDisplayed()  { return isDisplayed(addProductGroupHeader); }
    public boolean isEditProductGroupPopupDisplayed() { return isDisplayed(editProductGroupHeader); }
    public boolean isViewProductGroupPopupDisplayed() { return isDisplayed(viewProductGroupHeader); }

    // ═══════════════════════════════════════════════════════════
    //  READ-ONLY FIELD CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isProductGroupIdVisible()      { return isDisplayed(productGroupIdField); }
    public boolean isProductGroupIdNonEditable() {
        try { return !productGroupIdField.isEnabled(); } catch (Exception e) { return true; }
    }

    public boolean isStatusFieldVisible()         { return isDisplayed(statusField); }
    public boolean isStatusFieldNonEditable() {
        try { return !statusField.isEnabled(); } catch (Exception e) { return true; }
    }

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERROR CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isNameRequiredErrorDisplayed()   { return isDisplayed(nameRequiredError); }
    public boolean isCodeRequiredErrorDisplayed()   { return isDisplayed(codeRequiredError); }
    public boolean isCodeDuplicateErrorDisplayed()  { return isDisplayed(codeDuplicateError); }
    public boolean isGroupDuplicateErrorDisplayed() { return isDisplayed(groupDuplicateError); }
    public boolean isAnyValidationErrorDisplayed()  { return isDisplayed(genericValidationError); }

    // ═══════════════════════════════════════════════════════════
    //  COMPOSITE HELPERS
    // ═══════════════════════════════════════════════════════════

    /**
     * Creates a Product Group with random name + code and returns [name, code].
     * Used in Background pre-condition steps.
     */
    public String[] createProductGroupAndReturnDetails() {
        String name = DataGenerator.randomProductGroupName();
        String code = DataGenerator.randomProductGroupCode();
        clickAddButton();
        enterProductGroupName(name);
        enterProductGroupCode(code);
        clickSubmitButton();
        return new String[]{name, code};
    }

    public void searchForProductGroup(String name) {
        clickSearchIcon();
        clearSearchField();
        enterSearchText(name);
    }

    /**
     * Searches, swipes, and clicks Edit to open the Edit Product Group popup.
     */
    public void searchSwipeAndOpenEdit(String name) {
        searchForProductGroup(name);
        WebElement item = getRecordByName(name);
        if (item != null) swipeRecordRightToLeft(item);
        clickEditButton();
    }

    /**
     * Searches and clicks the record to open the View Product Group popup.
     */
    public void searchAndOpenView(String name) {
        searchForProductGroup(name);
        clickRecordByName(name);
    }
}
