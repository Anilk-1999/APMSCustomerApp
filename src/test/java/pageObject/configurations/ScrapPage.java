package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import pageObject.BasePage;
import utilities.DataGenerator;

import java.util.List;

/**
 * Page Object for the Scraps Configuration module.
 *
 * Covers: Scraps list screen, Add New Scrap Item popup,
 *         Edit Scrap popup, View Scrap popup,
 *         Product Subscription popup.
 *
 * Locator priority: accessibilityId → id → AndroidUIAutomator → XPath (last resort)
 * NOTE: Update locators after UI inspection with Appium Inspector.
 */
public class ScrapPage extends BasePage {

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

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Scrap Name' or @hint='Name']")
    private WebElement scrapNameInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Description' or @hint='description']")
    private WebElement descriptionInput;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Scrap Type'] | "
            + "//android.widget.TextView[@text='Scrap Type']/following-sibling::*[@clickable='true']")
    private WebElement scrapTypeDropdown;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Metrics Type'] | "
            + "//android.widget.TextView[@text='Metrics Type']/following-sibling::*[@clickable='true']")
    private WebElement metricsTypeDropdown;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='UOM'] | "
            + "//android.widget.TextView[@text='UOM']/following-sibling::*[@clickable='true']")
    private WebElement uomDropdown;

    // ═══════════════════════════════════════════════════════════
    //  READ-ONLY FIELDS (Edit / View popup)
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'SCR-') or @content-desc='Scrap ID']")
    private WebElement scrapIdField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Active' or @text='Inactive' or @content-desc='Status']")
    private WebElement statusField;

    // ═══════════════════════════════════════════════════════════
    //  PRODUCT SUBSCRIPTION
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Product Subscription'] | "
            + "//android.view.ViewGroup[@content-desc='Product Subscription']")
    private WebElement productSubscriptionOption;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='+'] | //android.view.ViewGroup[@content-desc='+']")
    private WebElement productSubAddIcon;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Select Products' or @text='Products']")
    private WebElement selectProductsBottomSheet;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='delete' or @content-desc='Delete']")
    private List<WebElement> productDeleteIcons;

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

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Scrap Name is required') or contains(@text,'Name is required')]")
    private WebElement scrapNameRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Scrap Type is required')]")
    private WebElement scrapTypeRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Metrics Type is required')]")
    private WebElement metricsTypeRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'UOM is required')]")
    private WebElement uomRequiredError;

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public ScrapPage(AndroidDriver driver) {
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

    public void enterScrapName(String name) { clearAndType(scrapNameInput, name); }

    public void enterDescription(String desc) { clearAndType(descriptionInput, desc); }

    public void selectScrapType() { tap(scrapTypeDropdown); selectFirstDropdownItem(); }

    public void selectMetricsType() { tap(metricsTypeDropdown); selectFirstDropdownItem(); }

    public void selectUOM() { tap(uomDropdown); selectFirstDropdownItem(); }

    private void selectFirstDropdownItem() {
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
    //  PRODUCT SUBSCRIPTION ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickProductSubscriptionOption() { tap(productSubscriptionOption); }

    public boolean isProductSubscriptionOptionVisible() { return isDisplayed(productSubscriptionOption); }

    public void clickProductSubAddIcon() { tap(productSubAddIcon); }

    public boolean isSelectProductsBottomSheetVisible() { return isDisplayed(selectProductsBottomSheet); }

    public void selectMultipleProducts(int count) {
        try {
            List<WebElement> items = driver.findElements(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().className(\"android.view.ViewGroup\").clickable(true)"));
            for (int i = 0; i < Math.min(count, items.size()); i++) tap(items.get(i));
        } catch (Exception ignored) {}
    }

    public boolean areProductDeleteIconsVisible() { return !productDeleteIcons.isEmpty(); }

    public void clickFirstProductDeleteIcon() {
        if (!productDeleteIcons.isEmpty()) tap(productDeleteIcons.get(0));
    }

    // ═══════════════════════════════════════════════════════════
    //  STATE CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isSubmitButtonVisible() { return isDisplayed(submitButton); }

    public boolean isSaveButtonVisible() { return isDisplayed(saveButton); }

    public boolean isEditButtonVisible() { return isDisplayed(editButton); }

    public boolean isScrapNameFieldVisible() { return isDisplayed(scrapNameInput); }

    public boolean isDescriptionFieldVisible() { return isDisplayed(descriptionInput); }

    public boolean isScrapTypeDropdownVisible() { return isDisplayed(scrapTypeDropdown); }

    public boolean isMetricsTypeDropdownVisible() { return isDisplayed(metricsTypeDropdown); }

    public boolean isUOMDropdownVisible() { return isDisplayed(uomDropdown); }

    public boolean isUOMDropdownEnabled() { return isEnabled(uomDropdown); }

    public boolean isScrapIdVisible() { return isDisplayed(scrapIdField); }

    public boolean isScrapNameRequiredErrorDisplayed() { return isDisplayed(scrapNameRequiredError); }

    public boolean isScrapTypeRequiredErrorDisplayed() { return isDisplayed(scrapTypeRequiredError); }

    public boolean isMetricsTypeRequiredErrorDisplayed() { return isDisplayed(metricsTypeRequiredError); }

    public boolean isUOMRequiredErrorDisplayed() { return isDisplayed(uomRequiredError); }

    public boolean isAnyValidationErrorDisplayed() {
        return isScrapNameRequiredErrorDisplayed() || isScrapTypeRequiredErrorDisplayed()
                || isMetricsTypeRequiredErrorDisplayed() || isUOMRequiredErrorDisplayed();
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

    public String createScrapAndReturnName() {
        String name = DataGenerator.randomScrapName();
        clickAddButton();
        enterScrapName(name);
        selectScrapType();
        selectMetricsType();
        selectUOM();
        clickSubmitButton();
        return name;
    }
}
