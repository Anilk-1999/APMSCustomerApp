package pageObject.configurations;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import pageObject.BasePage;

import java.util.List;

/**
 * Page Object for the Reason Groups Configuration module.
 *
 * Covers: Reason Groups list screen (read-only — no add/edit),
 *         View Reason Group detail popup.
 *
 * Locator priority: accessibilityId → id → AndroidUIAutomator → XPath (last resort)
 * NOTE: Update locators after UI inspection with Appium Inspector.
 */
public class ReasonGroupPage extends BasePage {

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN ELEMENTS
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "Search")
    private WebElement searchIcon;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Search' or @hint='search']")
    private WebElement searchInput;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@clickable='true' and @focusable='true']")
    private List<WebElement> listRecords;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Reason Group') or contains(@text,'reason group')]")
    private WebElement screenTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'total') or contains(@text,'Total') or contains(@text,'count')]")
    private WebElement totalCountText;

    // ═══════════════════════════════════════════════════════════
    //  VIEW POPUP FIELDS (read-only)
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'RG-') or @content-desc='Group ID']")
    private WebElement groupIdField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Group Name' or @hint='Name'] | "
            + "//android.widget.TextView[@content-desc='Group Name']")
    private WebElement groupNameField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Description' or @hint='description'] | "
            + "//android.widget.TextView[@content-desc='Description']")
    private WebElement descriptionField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Used' or @text='Not Used' "
            + "or @content-desc='Status']")
    private WebElement statusField;

    // ═══════════════════════════════════════════════════════════
    //  CLOSE BUTTON
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Close' or @content-desc='close' or @content-desc='X']")
    private WebElement closeButton;

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public ReasonGroupPage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN ACTIONS
    // ═══════════════════════════════════════════════════════════

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

    public boolean isListDisplayed() {
        return isDisplayed(searchIcon);
    }

    public boolean isTotalCountVisible() {
        return isDisplayed(totalCountText);
    }

    public WebElement getRecordByName(String name) {
        try {
            scrollToText(name);
            return driver.findElement(
                    io.appium.java_client.AppiumBy.androidUIAutomator(
                            "new UiSelector().textContains(\"" + name + "\")"));
        } catch (Exception e) {
            return null;
        }
    }

    public void clickRecord(String name) {
        WebElement record = getRecordByName(name);
        if (record != null) tap(record);
    }

    // ═══════════════════════════════════════════════════════════
    //  VIEW POPUP STATE CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isGroupIdVisible() {
        return isDisplayed(groupIdField);
    }

    public boolean isGroupNameVisible() {
        return isDisplayed(groupNameField);
    }

    public boolean isDescriptionVisible() {
        return isDisplayed(descriptionField);
    }

    public boolean isStatusVisible() {
        return isDisplayed(statusField);
    }

    public void clickClose() {
        tap(closeButton);
    }

    // ═══════════════════════════════════════════════════════════
    //  COMPOSITE HELPER
    // ═══════════════════════════════════════════════════════════

    public void searchAndOpenView(String name) {
        clickSearchIcon();
        tapSearchInput();
        clearSearchField();
        enterSearchText(name);
        clickRecord(name);
    }
}
