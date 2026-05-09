package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import pageObject.BasePage;
import utilities.DataGenerator;

import java.util.List;

/**
 * Page Object for the Activity Groups Configuration module.
 *
 * Covers: Activity Groups list screen, Add New Activity Group popup,
 *         Edit Activity Group popup, View Activity Group popup,
 *         Activity Checklist (bottom sheet add / inline delete),
 *         Status-change confirmation popup.
 *
 * Locator priority: accessibilityId → id → AndroidUIAutomator → XPath (last resort)
 * NOTE: Update locators after UI inspection with Appium Inspector.
 */
public class ActivityGroupPage extends BasePage {

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

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Form Name']")
    private WebElement formNameInput;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Form Description' or @hint='Description']")
    private WebElement formDescriptionInput;

    /** "+" button inside the Activity Checklist section */
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Activity Checklist']/following-sibling::*[@content-desc='+' or @text='+']")
    private WebElement checklistAddButton;

    /** Submit button inside the main Add/Edit popup */
    @AndroidFindBy(accessibility = "Submit")
    private WebElement submitButton;

    /** Save button inside the Edit popup */
    @AndroidFindBy(accessibility = "Save")
    private WebElement saveButton;

    /** Close (X) button of any popup */
    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Close' or @content-desc='X' or @content-desc='close']")
    private WebElement closeButton;

    // ═══════════════════════════════════════════════════════════
    //  STATUS + CONFIRMATION POPUP
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Active' or @content-desc='Inactive']")
    private WebElement statusButton;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Yes, Change' or @text='Yes, Change']")
    private WebElement yesChangeButton;

    // ═══════════════════════════════════════════════════════════
    //  VIEW / EDIT ID FIELD
    // ═══════════════════════════════════════════════════════════

    /** Read-only Activity Group ID shown in Edit / View popup */
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='Activity Group ID' or contains(@text,'AG-')]")
    private WebElement activityGroupIdField;

    // ═══════════════════════════════════════════════════════════
    //  EDIT BUTTON (after swipe)
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "Edit")
    private WebElement editButton;

    // ═══════════════════════════════════════════════════════════
    //  CHECKLIST ITEMS & DELETE ICONS
    // ═══════════════════════════════════════════════════════════

    /** All activity items currently shown inside the checklist */
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='Checklist Item' or contains(@resource-id,'checklist_item')]")
    private List<WebElement> checklistItems;

    /** Delete icons next to each checklist item */
    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Delete' or @content-desc='Remove']")
    private List<WebElement> deleteIcons;

    // ═══════════════════════════════════════════════════════════
    //  BOTTOM SHEET — SELECT ACTIVITIES
    // ═══════════════════════════════════════════════════════════

    /** Submit button inside the "Select Activities" bottom sheet */
    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Submit' or @text='Submit']")
    private WebElement bottomSheetSubmitButton;

    /** All selectable activity rows inside the bottom sheet */
    @AndroidFindBy(xpath = "//android.widget.CheckBox | //android.view.ViewGroup[@checkable='true']")
    private List<WebElement> bottomSheetActivityItems;

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION MESSAGES
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Form Name is required') or contains(@text,'required')]")
    private WebElement formNameRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'already exists') or contains(@text,'duplicate')]")
    private WebElement duplicateError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'No changes')]")
    private WebElement noChangesMessage;

    // ═══════════════════════════════════════════════════════════
    //  POPUP HEADER TEXT ELEMENTS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Add New Activity Group']")
    private WebElement addActivityGroupHeader;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Edit Activity Group']")
    private WebElement editActivityGroupHeader;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='View Activity Group']")
    private WebElement viewActivityGroupHeader;

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public ActivityGroupPage(AndroidDriver driver) {
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
    //  POPUP — FIELD ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void enterFormName(String name) {
        clearAndType(formNameInput, name);
    }

    public void clearFormNameField() {
        clearField(formNameInput);
    }

    public void enterFormDescription(String description) {
        clearAndType(formDescriptionInput, description);
    }

    public boolean isFormNameFieldVisible() {
        return isDisplayed(formNameInput);
    }

    public boolean isFormDescriptionFieldVisible() {
        return isDisplayed(formDescriptionInput);
    }

    public boolean isFormNameNonEditable() {
        try {
            return !formNameInput.isEnabled();
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isFormDescriptionNonEditable() {
        try {
            return !formDescriptionInput.isEnabled();
        } catch (Exception e) {
            return true;
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP — BUTTONS
    // ═══════════════════════════════════════════════════════════

    public void clickSubmitButton() {
        tap(submitButton);
    }

    public boolean isSubmitButtonVisible() {
        return isDisplayed(submitButton);
    }

    public void clickSaveButton() {
        tap(saveButton);
    }

    public boolean isSaveButtonVisible() {
        return isDisplayed(saveButton);
    }

    public void clickCloseButton() {
        tap(closeButton);
    }

    public boolean isCloseButtonVisible() {
        return isDisplayed(closeButton);
    }

    public void clickEditButton() {
        tap(editButton);
    }

    public boolean isEditButtonVisible() {
        return isDisplayed(editButton);
    }

    // ═══════════════════════════════════════════════════════════
    //  STATUS ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickStatusButton() {
        tap(statusButton);
    }

    public boolean isStatusButtonVisible() {
        return isDisplayed(statusButton);
    }

    public String getStatusValue() {
        try {
            return statusButton.getAttribute("content-desc");
        } catch (Exception e) {
            return "";
        }
    }

    public void clickYesChangeButton() {
        tap(yesChangeButton);
    }

    public boolean isYesChangeButtonVisible() {
        return isDisplayed(yesChangeButton);
    }

    // ═══════════════════════════════════════════════════════════
    //  ACTIVITY GROUP ID
    // ═══════════════════════════════════════════════════════════

    public boolean isActivityGroupIdVisible() {
        return isDisplayed(activityGroupIdField);
    }

    public boolean isActivityGroupIdNonEditable() {
        try {
            return !activityGroupIdField.isEnabled();
        } catch (Exception e) {
            return true;
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  ACTIVITY CHECKLIST ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickChecklistAddButton() {
        tap(checklistAddButton);
    }

    public boolean isChecklistAddButtonVisible() {
        return isDisplayed(checklistAddButton);
    }

    public boolean isChecklistVisible() {
        return !checklistItems.isEmpty();
    }

    public boolean isChecklistEmpty() {
        return checklistItems.isEmpty();
    }

    public int getChecklistItemCount() {
        return checklistItems.size();
    }

    /** Click the first available delete icon in the checklist */
    public void clickFirstDeleteIcon() {
        if (!deleteIcons.isEmpty()) {
            tap(deleteIcons.get(0));
        }
    }

    /** Click all delete icons to clear the entire checklist */
    public void deleteAllChecklistItems() {
        while (!deleteIcons.isEmpty()) {
            tap(deleteIcons.get(0));
        }
    }

    /** Click multiple delete icons (up to count) */
    public void deleteChecklistItems(int count) {
        int toDelete = Math.min(count, deleteIcons.size());
        for (int i = 0; i < toDelete; i++) {
            tap(deleteIcons.get(0));
        }
    }

    public boolean areDeleteIconsVisible() {
        return !deleteIcons.isEmpty();
    }

    // ═══════════════════════════════════════════════════════════
    //  BOTTOM SHEET — SELECT ACTIVITIES
    // ═══════════════════════════════════════════════════════════

    public boolean isSelectActivitiesBottomSheetVisible() {
        try {
            WebElement header = driver.findElement(
                    AppiumBy.xpath("//android.widget.TextView[@text='Select Activities']"));
            return header.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Select the first N available activities in the bottom sheet */
    public void selectMultipleActivities(int count) {
        int toSelect = Math.min(count, bottomSheetActivityItems.size());
        for (int i = 0; i < toSelect; i++) {
            tap(bottomSheetActivityItems.get(i));
        }
    }

    public void clickBottomSheetSubmitButton() {
        tap(bottomSheetSubmitButton);
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP DISPLAY CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isAddActivityGroupPopupDisplayed() {
        return isDisplayed(addActivityGroupHeader);
    }

    public boolean isEditActivityGroupPopupDisplayed() {
        return isDisplayed(editActivityGroupHeader);
    }

    public boolean isViewActivityGroupPopupDisplayed() {
        return isDisplayed(viewActivityGroupHeader);
    }

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION MESSAGE CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isFormNameRequiredErrorDisplayed() {
        return isDisplayed(formNameRequiredError);
    }

    public boolean isDuplicateErrorDisplayed() {
        return isDisplayed(duplicateError);
    }

    public boolean isNoChangesMessageDisplayed() {
        return isDisplayed(noChangesMessage);
    }

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
     * Creates an Activity Group with a random Form Name (no activities) and returns the name.
     * Used in Background pre-condition steps.
     */
    public String createActivityGroupAndReturnName() {
        String name = DataGenerator.randomActivityGroupName();
        clickAddButton();
        enterFormName(name);
        clickSubmitButton();
        return name;
    }

    /**
     * Searches for the given Form Name, swipes the record right-to-left,
     * then clicks Edit to open the Edit Activity Group popup.
     */
    public void searchSwipeAndOpenEdit(String formName) {
        clickSearchIcon();
        tapSearchInput();
        clearSearchField();
        enterSearchText(formName);
        WebElement listItem = getRecordByName(formName);
        swipeRecordRightToLeft(listItem);
        clickEditButton();
    }

    /**
     * Searches for the given Form Name and clicks the record to open the View popup.
     */
    public void searchAndOpenView(String formName) {
        clickSearchIcon();
        tapSearchInput();
        clearSearchField();
        enterSearchText(formName);
        clickRecordByName(formName);
    }
}
