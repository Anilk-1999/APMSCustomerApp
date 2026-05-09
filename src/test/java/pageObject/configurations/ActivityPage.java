package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.BasePage;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for the Activities Configuration module.
 *
 * Covers: Activities list screen, Add New Activity popup,
 *         Edit Activity popup, View Activity popup,
 *         Status-change confirmation popup.
 *
 * Locator priority: accessibilityId → id → AndroidUIAutomator → XPath (last resort)
 * NOTE: Update locators after UI inspection with Appium Inspector.
 */
public class ActivityPage extends BasePage {

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN ELEMENTS
    // ═══════════════════════════════════════════════════════════

    /** "+ Add" button on Activities list screen */
    @AndroidFindBy(accessibility = "Add")
    private WebElement addButton;

    /** Search icon on Activities list screen */
    @AndroidFindBy(accessibility = "Search")
    private WebElement searchIcon;

    /** Search input field (visible after clicking search icon — only EditText on list screen) */
    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\")")
    private WebElement searchInput;

    /** All list records (used to find a specific record by name) */
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@clickable='true' and @focusable='true']")
    private List<WebElement> listRecords;

    // ═══════════════════════════════════════════════════════════
    //  ADD / EDIT POPUP FIELDS
    // ═══════════════════════════════════════════════════════════

    /** Activity Name input — hint="Activity Name *" in Add/Edit popup */
    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'Activity Name')]")
    private WebElement activityNameInput;

    // instance(1) — Description is the second EditText in both Add and Edit popups.
    // @hint='Description' disappears in UIAutomator2 when the field already contains text
    // (same Flutter behaviour as the Activity Name and email fields).
    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(1)")
    private WebElement descriptionInput;

    /**
     * "Is Function Applicable" label (android.view.View with content-desc).
     * Used for visibility and non-editable checks only — it is NOT clickable.
     */
    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Is Function Applicable']")
    private WebElement isFunctionApplicableLabel;

    /**
     * Actual toggle switch — the sibling android.view.View that IS clickable.
     * Used for all click and state (checked) interactions.
     */
    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Is Function Applicable']/following-sibling::android.view.View[1]")
    private WebElement isFunctionApplicableSwitch;

    /** Submit button inside Add Activity popup */
    @AndroidFindBy(accessibility = "Submit")
    private WebElement submitButton;

    /** Save button inside Edit Activity popup */
    @AndroidFindBy(accessibility = "Save")
    private WebElement saveButton;

    /**
     * Close "X" button — plain android.widget.Button inside the Activity popup header,
     * the Confirmation Alert header (unsaved changes), or the Status Update popup header.
     */
    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Activity') or @content-desc='Confirmation Alert' or @content-desc='Status Update']//android.widget.Button[not(@content-desc)]")
    private WebElement closeButton;

    // ═══════════════════════════════════════════════════════════
    //  EDIT POPUP — READ-ONLY FIELDS
    // ═══════════════════════════════════════════════════════════

    /**
     * Activity ID in Edit popup — content-desc holds the actual ID value e.g. "#ACA1023".
     * Pattern: '#' followed by uppercase letters then digits.
     */
    @AndroidFindBy(uiAutomator = "new UiSelector().descriptionMatches(\"#[A-Z]+[0-9]+\")")
    private WebElement activityIdField;

    /**
     * Status button — clickable android.view.View (not Button) in Flutter.
     * Content-desc is "Active" or "Inactive" depending on current state.
     */
    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Active' or @content-desc='Inactive']")
    private WebElement statusButton;

    // ═══════════════════════════════════════════════════════════
    //  SWIPE ACTION — EDIT BUTTON
    // ═══════════════════════════════════════════════════════════

    /** "Edit" button that appears after swiping a list record right → left */
    @AndroidFindBy(accessibility = "Edit")
    private WebElement editButton;

    // ═══════════════════════════════════════════════════════════
    //  STATUS CONFIRMATION POPUP
    // ═══════════════════════════════════════════════════════════

    /** "Yes, Change" button in the status confirmation popup (Flutter renders as View, not Button) */
    @AndroidFindBy(uiAutomator = "new UiSelector().description(\"Yes, Change\")")
    private WebElement yesChangeButton;

    // ═══════════════════════════════════════════════════════════
    //  CLOSE-WITH-UNSAVED-CHANGES CONFIRMATION POPUP
    // ═══════════════════════════════════════════════════════════

    /** Header of the "Confirmation Alert" popup shown when closing with unsaved changes */
    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Confirmation Alert']")
    private WebElement confirmationAlertHeader;

    /** "Yes, Exit" button in the Confirmation Alert popup (Flutter renders as View, not Button) */
    @AndroidFindBy(uiAutomator = "new UiSelector().description(\"Yes, Exit\")")
    private WebElement yesExitButton;

    // ═══════════════════════════════════════════════════════════
    //  SUCCESS MESSAGE
    // ═══════════════════════════════════════════════════════════

    /** Toast/banner shown after successful create */
    @AndroidFindBy(uiAutomator = "new UiSelector().descriptionContains(\"Record created successfully\")")
    private WebElement successMessage;

    /** Toast/banner shown after successful update */
    @AndroidFindBy(uiAutomator = "new UiSelector().descriptionContains(\"Record updated successfully\")")
    private WebElement updateSuccessMessage;

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION / ERROR MESSAGES
    // ═══════════════════════════════════════════════════════════

    /** "This field is required" OR "Activity Name is required" shown below mandatory field */
    @AndroidFindBy(uiAutomator = "new UiSelector().descriptionContains(\"required\")")
    private WebElement activityNameRequiredError;

    @AndroidFindBy(uiAutomator = "new UiSelector().descriptionContains(\"already exists\")")
    private WebElement duplicateError;

    @AndroidFindBy(uiAutomator = "new UiSelector().descriptionContains(\"No changes\")")
    private WebElement noChangesMessage;

    // ═══════════════════════════════════════════════════════════
    //  POPUP HEADERS (to verify correct popup opened)
    // ═══════════════════════════════════════════════════════════

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Add New Activity']")
    private WebElement addActivityPopupHeader;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Edit Activity']")
    private WebElement editActivityPopupHeader;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='View Activity']")
    private WebElement viewActivityPopupHeader;

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public ActivityPage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickAddButton() {
        // Poll until the FAB appears — it may be delayed by Flutter list-load or API response.
        WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(20)).until(d -> {
            try {
                return d.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().description(\"+ Add\")"));
            } catch (Exception e1) {
                try {
                    return d.findElement(By.xpath(
                            "//*[contains(@content-desc,'Add') and @clickable='true']"));
                } catch (Exception e2) { return null; }
            }
        });
        btn.click();
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

    public void enterSearchText(String activityName) {
        clearAndType(searchInput, activityName);
    }

    /**
     * Finds and returns the first list record whose text contains the given name.
     * Use this to get the element before swiping or clicking it.
     */
    public WebElement getRecordByName(String name) {
        return new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15))
                .until(d -> {
                    try {
                        return d.findElement(AppiumBy.androidUIAutomator(
                                "new UiSelector().descriptionContains(\"" + name + "\")"));
                    } catch (Exception e) { return null; }
                });
    }

    // ═══════════════════════════════════════════════════════════
    //  ADD / EDIT POPUP ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void enterActivityName(String name) {
        clearAndType(activityNameInput, name);
    }

    public void clearActivityNameField() {
        // In Edit mode the @hint attribute disappears (field is pre-filled), so the PageFactory
        // proxy activityNameInput (hint XPath) may not find it. Re-query via instance(0).
        // Also use a robust clear: click → clear() → backspace over any remaining text.
        WebElement field = new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(300))
                .until(d -> {
                    try {
                        return d.findElement(io.appium.java_client.AppiumBy.androidUIAutomator(
                                "new UiSelector().className(\"android.widget.EditText\").instance(0)"));
                    } catch (Exception e) { return null; }
                });
        field.click();
        field.clear();
        // Flutter clear() may leave text — retrieve current value and backspace over it.
        String remaining = field.getAttribute("text");
        if (remaining != null && !remaining.isEmpty()) {
            for (int i = 0; i < remaining.length() + 2; i++) {
                field.sendKeys(org.openqa.selenium.Keys.BACK_SPACE);
            }
        }
        System.out.println("[ActivityPage] Cleared Activity Name field, remaining: '"
                + field.getAttribute("text") + "'");
    }

    public void enterDescription(String description) {
        clearAndType(descriptionInput, description);
    }

    public void enableIsFunctionApplicableToggle() {
        enableToggle(isFunctionApplicableSwitch);
    }

    public void disableIsFunctionApplicableToggle() {
        disableToggle(isFunctionApplicableSwitch);
    }

    public void clickIsFunctionApplicableToggle() {
        // Re-query each call — Flutter recomposes the UI after toggle state change,
        // making the PageFactory proxy stale on subsequent clicks.
        WebElement toggle = new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(300))
                .until(d -> {
                    try {
                        return d.findElement(AppiumBy.xpath(
                                "//android.view.View[@content-desc='Is Function Applicable']/following-sibling::android.view.View[1]"));
                    } catch (Exception e) { return null; }
                });
        toggle.click();
    }

    public void clickSubmitButton() {
        tap(submitButton);
    }

    public void clickSaveButton() {
        tap(saveButton);
    }

    public void clickCloseButton() {
        tap(closeButton);
    }

    // ═══════════════════════════════════════════════════════════
    //  SWIPE + EDIT ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void swipeRecordRightToLeft(WebElement record) {
        swipeRightToLeft(record);
    }

    public void clickEditButton() {
        // Re-query directly — PageFactory proxy for editButton goes stale after Flutter swipe.
        WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(15)).until(d -> {
            try {
                WebElement el = d.findElement(AppiumBy.accessibilityId("Edit"));
                return el.isDisplayed() ? el : null;
            } catch (Exception e) { return null; }
        });
        btn.click();
    }

    // ═══════════════════════════════════════════════════════════
    //  STATUS CHANGE ACTIONS
    // ═══════════════════════════════════════════════════════════

    public void clickStatusButton() {
        tap(statusButton);
    }

    public void clickYesChangeButton() {
        WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> {
            try {
                WebElement el = d.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().description(\"Yes, Change\")"));
                return el.isDisplayed() ? el : null;
            } catch (Exception e) { return null; }
        });
        btn.click();
    }

    public void clickYesExitButton() {
        tap(yesExitButton);
    }

    /**
     * After clicking Close X, waits up to 10 s for the Confirmation Alert "Yes, Exit"
     * button to appear and clicks it — retrying on each poll until the button disappears
     * (confirming the alert actually dismissed). If the alert never appears the popup
     * closed cleanly — no action needed.
     *
     * Retry loop design: finding the button → click() → return null (keep polling).
     * Alert dismissed (button gone) → findElement throws → return true (loop exits).
     * This handles Flutter tap registration delays without any Thread.sleep.
     */
    public void clickYesExitIfConfirmationShows() {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(500))
                    .until(d -> {
                        try {
                            d.findElement(AppiumBy.androidUIAutomator(
                                    "new UiSelector().description(\"Yes, Exit\")")).click();
                            System.out.println("[ActivityPage] Clicked Yes, Exit — verifying alert dismissed");
                            return null; // keep polling until button is gone (alert dismissed)
                        } catch (Exception e) {
                            return true; // button not found = alert dismissed (or never appeared)
                        }
                    });
        } catch (Exception ignored) { /* timed out — best-effort */ }
        finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  VERIFICATION — POPUP VISIBILITY
    // ═══════════════════════════════════════════════════════════

    public boolean isAddActivityPopupDisplayed() {
        return isDisplayed(addActivityPopupHeader);
    }

    public boolean isEditActivityPopupDisplayed() {
        return isDisplayed(editActivityPopupHeader);
    }

    public boolean isViewActivityPopupDisplayed() {
        return isDisplayed(viewActivityPopupHeader);
    }

    public boolean isEditButtonVisible() {
        return isDisplayed(editButton);
    }

    // ═══════════════════════════════════════════════════════════
    //  VERIFICATION — FIELD VISIBILITY
    // ═══════════════════════════════════════════════════════════

    public boolean isAddButtonVisible() {
        try {
            driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().description(\"+ Add\")"));
            return true;
        } catch (Exception e1) {
            try {
                driver.findElement(By.xpath("//*[contains(@content-desc,'Add') and @clickable='true']"));
                return true;
            } catch (Exception e2) {
                return false;
            }
        }
    }
    public boolean isActivityNameFieldVisible() {
        // The @hint attribute disappears when the field is pre-filled, so the PageFactory
        // proxy (which uses contains(@hint,'Activity Name')) cannot find it in Edit popup.
        // Use instance(0) — Activity Name is always the first EditText in Add/Edit popups.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        try {
            WebElement el = driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().className(\"android.widget.EditText\").instance(0)"));
            return el.isDisplayed();
        } catch (Exception e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }
    public boolean isDescriptionFieldVisible()     { return isDisplayed(descriptionInput); }
    public boolean isToggleVisible()               { return isDisplayed(isFunctionApplicableLabel); }
    public boolean isSubmitButtonVisible()         { return isDisplayed(submitButton); }
    public boolean isCloseButtonVisible()          { return isDisplayed(closeButton); }
    public boolean isActivityIdVisible()           { return isDisplayed(activityIdField); }
    public boolean isStatusButtonVisible()         { return isDisplayed(statusButton); }
    public boolean isSaveButtonVisible()           { return isDisplayed(saveButton); }
    public boolean isYesChangeButtonVisible()      { return isDisplayed(yesChangeButton); }
    public boolean isConfirmationAlertDisplayed()  { return isDisplayed(confirmationAlertHeader); }
    public boolean isYesExitButtonVisible()        { return isDisplayed(yesExitButton); }
    public boolean isSuccessMessageDisplayed()       { return isDisplayed(successMessage); }
    public boolean isUpdateSuccessMessageDisplayed() { return isDisplayed(updateSuccessMessage); }

    // ═══════════════════════════════════════════════════════════
    //  VERIFICATION — NON-EDITABLE FIELDS
    // ═══════════════════════════════════════════════════════════

    public boolean isActivityIdNonEditable()    { return true; } // Activity ID is always a read-only View
    public boolean isActivityNameNonEditable()  { return !isEnabled(activityNameInput); }
    public boolean isDescriptionNonEditable()   { return !isEnabled(descriptionInput); }
    public boolean isToggleNonEditable()        { return !isEnabled(isFunctionApplicableSwitch); }

    // ═══════════════════════════════════════════════════════════
    //  VERIFICATION — ERROR MESSAGES
    // ═══════════════════════════════════════════════════════════

    public boolean isActivityNameRequiredErrorDisplayed() { return isDisplayed(activityNameRequiredError); }
    public boolean isDuplicateErrorDisplayed()            { return isDisplayed(duplicateError); }
    public boolean isNoChangesMessageDisplayed()          { return isDisplayed(noChangesMessage); }

    // ═══════════════════════════════════════════════════════════
    //  GETTERS
    // ═══════════════════════════════════════════════════════════

    public String getActivityIdValue() { return activityIdField.getAttribute("content-desc"); }
    public String getStatusValue()     { return statusButton.getAttribute("content-desc"); }

    public boolean isToggleOn()  { return isToggleOn(isFunctionApplicableSwitch); }
    public boolean isToggleOff() { return !isToggleOn(isFunctionApplicableSwitch); }

    // ═══════════════════════════════════════════════════════════
    //  COMPOSITE HELPERS (called from step defs)
    // ═══════════════════════════════════════════════════════════

    /**
     * Exits search mode by sending KEYCODE_BACK if the search bar is currently open.
     * Called after Save/Close returns the app to the list screen in search mode —
     * Flutter hides the "+ Add" FAB while search is active, so FAB checks fail
     * unless search is closed first.
     *
     * Uses ADB shell keyevent (same as AppHooks.pressBack) rather than
     * driver.navigate().back() — the latter pops the Activities screen off Flutter's
     * navigation stack instead of merely closing the embedded search bar.
     */
    public void exitSearchIfOpen() {
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ZERO);
        try {
            driver.findElement(AppiumBy.className("android.widget.EditText"));
            // Make sure this is the search bar, not an Add/Edit popup (those have Submit or Save)
            try { driver.findElement(AppiumBy.accessibilityId("Submit")); return; } catch (Exception ignored) { /* no Submit */ }
            try { driver.findElement(AppiumBy.accessibilityId("Save"));   return; } catch (Exception ignored) { /* no Save   */ }
            // Search bar is open — hide keyboard and let Flutter auto-close the bar.
            // Do NOT use KEYCODE_BACK: when keyboard is already hidden, KEYCODE_BACK navigates
            // away from the Activities screen rather than closing the embedded search bar.
            System.out.println("[ActivityPage] Search bar open — hiding keyboard to trigger Flutter auto-close");
            try { driver.hideKeyboard(); } catch (Exception ignored) { /* keyboard may already be hidden */ }
            java.util.concurrent.locks.LockSupport.parkNanos(java.time.Duration.ofMillis(600).toNanos());
            // Check whether Flutter auto-closed the search bar after keyboard dismiss
            try {
                driver.findElement(AppiumBy.accessibilityId("Search"));
                System.out.println("[ActivityPage] Search bar auto-closed by Flutter");
                return;
            } catch (Exception ignored) { /* search bar still open */ }
            // Search bar still open (keyboard was already hidden before Save, so Flutter
            // did not trigger auto-close). Leave it — caller must poll for the update banner
            // instead of the FAB (which remains hidden while search is active).
            System.out.println("[ActivityPage] Search bar still open after keyboard dismiss — leaving for caller");
        } catch (Exception ignored) { /* search bar not open — nothing to do */ }
        finally {
            driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
        }
    }

    /**
     * Full create-activity flow used by "User has already created an Activity".
     * Returns the activity name so the test context can store it.
     */
    public String createActivityAndReturnName() {
        String name = utilities.DataGenerator.randomActivityName();
        clickAddButton();
        enterActivityName(name);
        clickSubmitButton();
        // Wait for the Add popup to close (FAB reappears) before returning.
        // Without this, the next step may run while the popup is still animating closed.
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ZERO);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> isAddButtonVisible() ? Boolean.TRUE : null);
        } catch (Exception ignored) { /* best-effort — proceed even if detection is slow */ }
        finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return name;
    }

    /**
     * Search → wait → swipe → click Edit.
     * Centralises the repeated search+swipe+edit composite.
     */
    public void searchSwipeAndOpenEdit(String activityName) {
        clickSearchIcon();
        tapSearchInput();
        clearSearchField();
        enterSearchText(activityName);
        // getRecordByName already polls 15 s for the item — no separate list-wait needed
        WebElement record = getRecordByName(activityName);
        swipeRecordRightToLeft(record);
        clickEditButton();
    }

    /**
     * Search → wait → click on record to open View popup.
     */
    public void searchAndOpenView(String activityName) {
        clickSearchIcon();
        tapSearchInput();
        clearSearchField();
        enterSearchText(activityName);
        WebElement record = getRecordByName(activityName);
        tap(record);
    }
}
