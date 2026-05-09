package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import pageObject.BasePage;

import java.util.List;

/**
 * Page Object for the Production Plans (Insights > Plan) module.
 *
 * Covers: Production Plan list screen (New / In Progress / On Hold /
 *         Template / Completed / Canceled sections),
 *         Add Production Plan popup.
 *
 * Locator priority: accessibilityId → id → AndroidUIAutomator → XPath (last resort)
 * NOTE: Update locators after UI inspection with Appium Inspector.
 */
public class ProductionPlanPage extends BasePage {

    // ═══════════════════════════════════════════════════════════
    //  PLAN LIST SCREEN — SECTION TABS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='New'] | //android.view.ViewGroup[@content-desc='New']")
    private WebElement newSection;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='In Progress'] | //android.view.ViewGroup[@content-desc='In Progress']")
    private WebElement inProgressSection;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='On Hold'] | //android.view.ViewGroup[@content-desc='On Hold']")
    private WebElement onHoldSection;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Template'] | //android.view.ViewGroup[@content-desc='Template']")
    private WebElement templateSection;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Completed'] | //android.view.ViewGroup[@content-desc='Completed']")
    private WebElement completedSection;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Canceled'] | //android.view.ViewGroup[@content-desc='Canceled']")
    private WebElement canceledSection;

    // ═══════════════════════════════════════════════════════════
    //  ADD PRODUCTION PLAN POPUP
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "Add")
    private WebElement addButton;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Machine Name'] | "
            + "//android.widget.TextView[@text='Machine Name']/following-sibling::*[@clickable='true']")
    private WebElement machineNameDropdown;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Schedule Start Date'] | "
            + "//android.widget.EditText[@hint='Start Date' or @hint='Schedule Start']")
    private WebElement scheduleStartDateField;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Schedule End Date'] | "
            + "//android.widget.EditText[@hint='End Date' or @hint='Schedule End']")
    private WebElement scheduleEndDateField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Instruction' or @hint='instruction']")
    private WebElement instructionInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Target Quantity' or @hint='Quantity']")
    private WebElement targetQuantityInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Serial Number' or @hint='S/N']")
    private WebElement serialNumberInput;

    // ═══════════════════════════════════════════════════════════
    //  ADD PRODUCTS SECTION
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Add Products']/following-sibling::*[@content-desc='+' or @text='+'] | "
            + "//android.view.ViewGroup[@content-desc='Add Product']")
    private WebElement addProductButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Select Product' or @text='Products']")
    private WebElement productSelectionBottomSheet;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@clickable='true' and @focusable='true']")
    private List<WebElement> productListItems;

    // ═══════════════════════════════════════════════════════════
    //  TOGGLES
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.Switch[@content-desc='Setup Approval' or contains(@resource-id,'setupApproval')]")
    private WebElement setupApprovalToggle;

    @AndroidFindBy(xpath = "//android.widget.Switch[@content-desc='Production Approval' or contains(@resource-id,'productionApproval')]")
    private WebElement productionApprovalToggle;

    @AndroidFindBy(xpath = "//android.widget.Switch[@content-desc='Save as Template' or contains(@resource-id,'saveAsTemplate')]")
    private WebElement saveAsTemplateToggle;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Template Name']")
    private WebElement templateNameInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Template Description']")
    private WebElement templateDescriptionInput;

    // ═══════════════════════════════════════════════════════════
    //  RAW MATERIALS (product-level)
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Add Raw Material'] | "
            + "//android.widget.TextView[@text='Raw Materials']/following-sibling::*[@content-desc='+']")
    private WebElement rawMaterialsAddButton;

    // ═══════════════════════════════════════════════════════════
    //  POPUP BUTTONS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "Submit")
    private WebElement submitButton;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Close' or @content-desc='close' or @content-desc='X']")
    private WebElement closeButton;

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public ProductionPlanPage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════════
    //  SECTION VISIBILITY
    // ═══════════════════════════════════════════════════════════

    public boolean isNewSectionVisible()        { return isDisplayed(newSection); }
    public boolean isInProgressSectionVisible() { return isDisplayed(inProgressSection); }
    public boolean isOnHoldSectionVisible()     { return isDisplayed(onHoldSection); }
    public boolean isTemplateSectionVisible()   { return isDisplayed(templateSection); }
    public boolean isCompletedSectionVisible()  { return isDisplayed(completedSection); }
    public boolean isCanceledSectionVisible()   { return isDisplayed(canceledSection); }

    // ═══════════════════════════════════════════════════════════
    //  ADD POPUP ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickAddButton() { tap(addButton); }

    public boolean isAddButtonVisible() { return isDisplayed(addButton); }

    public void selectMachineName() {
        tap(machineNameDropdown);
        selectFirstDropdownItem();
    }

    public void selectScheduleStartDate() {
        tap(scheduleStartDateField);
        confirmDateTimePicker();
    }

    public void selectScheduleEndDate() {
        tap(scheduleEndDateField);
        confirmDateTimePicker();
    }

    private void confirmDateTimePicker() {
        try {
            WebElement okBtn = driver.findElement(
                    AppiumBy.androidUIAutomator("new UiSelector().text(\"OK\")"));
            tap(okBtn);
        } catch (Exception ignored) {}
    }

    public void enterInstruction(String text) { clearAndType(instructionInput, text); }

    public void enterTargetQuantity(String qty) { clearAndType(targetQuantityInput, qty); }

    public void enterSerialNumber(String sn) { clearAndType(serialNumberInput, sn); }

    public void clickAddProductButton() { tap(addProductButton); }

    public boolean isProductSelectionBottomSheetVisible() { return isDisplayed(productSelectionBottomSheet); }

    public void selectProducts(int count) {
        try {
            for (int i = 0; i < Math.min(count, productListItems.size()); i++) {
                tap(productListItems.get(i));
            }
        } catch (Exception ignored) {}
    }

    public void enableSetupApprovalToggle() { enableToggle(setupApprovalToggle); }

    public void enableProductionApprovalToggle() { enableToggle(productionApprovalToggle); }

    public void enableSaveAsTemplateToggle() { enableToggle(saveAsTemplateToggle); }

    public void enterTemplateName(String name) { clearAndType(templateNameInput, name); }

    public void enterTemplateDescription(String desc) { clearAndType(templateDescriptionInput, desc); }

    public void clickRawMaterialsAddButton() { tap(rawMaterialsAddButton); }

    public void clickSubmitButton() { tap(submitButton); }

    public void clickCloseButton() { tap(closeButton); }

    private void selectFirstDropdownItem() {
        try {
            List<WebElement> items = driver.findElements(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().className(\"android.view.ViewGroup\").clickable(true)"));
            if (!items.isEmpty()) tap(items.get(0));
        } catch (Exception ignored) {}
    }

    // ═══════════════════════════════════════════════════════════
    //  STATE CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isSubmitButtonVisible() { return isDisplayed(submitButton); }

    public WebElement getPlanInNewSection(String planId) {
        try {
            tap(newSection);
            return driver.findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().textContains(\"" + planId + "\")"));
        } catch (Exception e) { return null; }
    }

    public WebElement getPlanInTemplateSection(String templateName) {
        try {
            tap(templateSection);
            return driver.findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiSelector().textContains(\"" + templateName + "\")"));
        } catch (Exception e) { return null; }
    }
}
