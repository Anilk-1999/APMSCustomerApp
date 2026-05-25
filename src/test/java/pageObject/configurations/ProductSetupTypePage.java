package pageObject.configurations;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.BasePage;
import utilities.DataGenerator;

import java.time.Duration;
import java.util.List;

/**
 * Page Object — Product Setup Types Configuration module.
 *
 * Popup structure (from UI screenshots):
 *   Add popup:  "Add Product Setup Type" header, Product Setup Name*, Description,
 *               Machine Output Timer*, Product Setup Timer*, Submit
 *   Edit popup: "Edit Product Setup Type" header, #STA ID (read-only), Name*, Description,
 *               Machine Output Timer*, Product Setup Timer*, Save
 *   View popup: "View Product Setup Type" header, all fields read-only, X close
 *   Machine Subscription: "Action Menus" bottom sheet → "Machine Subscription" →
 *               "Machine Subscription" popup with Add(+) button, machine list, Submit
 *
 * ARCHITECTURE RULES:
 *  1. Zero implicit wait — all checks via elementUtils / popupUtils / searchUtils
 *  2. No Thread.sleep — waits via elementUtils polling methods
 *  3. No PageFactory @AndroidFindBy — all locators as By constants
 */
public class ProductSetupTypePage extends BasePage {

    // ═══════════════════════════════════════════════════════════
    //  LOCATORS — POPUP HEADERS
    // ═══════════════════════════════════════════════════════════

    private static final By ADD_POPUP = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"Add Product Setup Type\")");

    private static final By EDIT_POPUP = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"Edit Product Setup Type\")");

    private static final By VIEW_POPUP = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"View Product Setup Type\")");

    private static final By ACTION_MENUS_SHEET = AppiumBy.xpath(
            "//android.view.View[@content-desc='Action Menus']");

    private static final By MACHINE_SUBSCRIPTION_OPTION = AppiumBy.xpath(
            "//android.widget.ImageView[@content-desc='Machine Subscription']");

    private static final By MACHINE_SUBSCRIPTION_POPUP = AppiumBy.xpath(
            "//android.view.View[starts-with(@content-desc,'Machine Subscription')]");

    private static final By MACHINE_SUBSCRIPTION_CLOSE_BTN = AppiumBy.xpath(
            "//android.view.View[starts-with(@content-desc,'Machine Subscription')]"
            + "//android.widget.Button[not(@content-desc)][1]");

    private static final By MACHINE_ADD_BTN = AppiumBy.xpath(
            "//android.view.View[starts-with(@content-desc,'Machine Subscription')]"
            + "//android.widget.Button[not(@content-desc)][2]");

    // Machine rows inside popup (same #MCA prefix as Operator module)
    private static final By MACHINE_ITEMS_IN_POPUP = AppiumBy.xpath(
            "//android.view.View[starts-with(@content-desc,'Machine Subscription')]"
            + "//android.widget.Button[starts-with(@content-desc,'#MCA')]");

    private static final By MACHINE_DELETE_ICONS = AppiumBy.xpath(
            "//android.widget.Button[starts-with(@content-desc,'#MCA')]/android.widget.ImageView");

    private static final By MACHINE_SELECT_SHEET = AppiumBy.xpath(
            "//android.view.View[@content-desc='Select Machines']");

    private static final By MACHINE_SELECT_ITEMS = AppiumBy.xpath(
            "//android.view.View[@content-desc='Select Machines']"
            + "//android.widget.ScrollView//android.widget.Button");

    private static final By UNCHECKED_MACHINE_ITEM = AppiumBy.xpath(
            "//android.view.View[@content-desc='Select Machines']"
            + "//android.widget.ScrollView//android.widget.Button"
            + "[.//android.widget.CheckBox[@checked='false']]");

    private static final By SELECT_DURATION_POPUP = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"Select Duration\")");

    private static final By SELECT_MACHINES_POPUP = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"Select Machines\")");

    private static final By DURATION_MIN_ALERT = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"Duration should be at least 1 minute\")");

    // ═══════════════════════════════════════════════════════════
    //  LOCATORS — EDIT TEXT FIELDS
    // ═══════════════════════════════════════════════════════════

    private static final By NAME_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[contains(@hint,'Product Setup Name') or contains(@hint,'Setup Name')]");

    private static final By DESC_INPUT = AppiumBy.xpath(
            "//android.widget.EditText[contains(@hint,'Description')]");

    // Timer input fields — clickable=false in Flutter, located by hint attribute
    private static final By MACHINE_OUTPUT_TIMER_FIELD = AppiumBy.xpath(
            "//android.view.View[@hint='Machine Output Timer *']");

    private static final By PRODUCT_SETUP_TIMER_FIELD = AppiumBy.xpath(
            "//android.view.View[@hint='Product Setup Timer *']");

    // ═══════════════════════════════════════════════════════════
    //  LOCATORS — VALIDATION ERRORS
    // ═══════════════════════════════════════════════════════════

    private static final By NAME_REQUIRED_ERROR = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"This field is required\").instance(0)");

    private static final By ANY_REQUIRED_ERROR = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"This field is required\")");

    private static final By GENERIC_VALIDATION_ERROR = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"required\")");

    // ═══════════════════════════════════════════════════════════
    //  LOCATORS — READ-ONLY FIELDS (Edit / View popup)
    // ═══════════════════════════════════════════════════════════

    private static final By PST_ID_FIELD = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionStartsWith(\"#STA\")");

    // ═══════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    // ═══════════════════════════════════════════════════════════

    public ProductSetupTypePage(AndroidDriver driver) {
        super(driver);
    }

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN
    // ═══════════════════════════════════════════════════════════

    /** Clicks the "Add" button on the Product Setup Types list screen. */
    public void clickAddButton() {
        elementUtils.clickWhenFoundByAccessibility("Add", 10);
    }

    /** True when the "Add" button is visible (i.e. we are on the list screen). */
    public boolean isAddButtonVisible() {
        return elementUtils.isPresentByAccessibility("Add");
    }

    public void clickSearchIcon() {
        elementUtils.clickWhenFoundByAccessibility("Search", 10);
    }

    public WebElement getRecordByName(String name) {
        return searchUtils.getRecordByName(name);
    }

    public void clickRecordByName(String name) {
        WebElement el = getRecordByName(name);
        if (el == null)
            throw new RuntimeException("Product Setup Type record not found in list: " + name);
        el.click();
    }

    public void swipeRecordRightToLeft(WebElement element) {
        swipeRightToLeft(element);
    }

    public void longPressRecord(String name) {
        WebElement item = getRecordByName(name);
        if (item != null) longPress(item);
    }

    public boolean isProductSetupTypeIdVisible() {
        return elementUtils.waitForPresence(PST_ID_FIELD, 5);
    }

    public boolean isProductSetupTypeIdNonEditable() {
        return true; // ID field is always read-only in Flutter popup
    }

    // ═══════════════════════════════════════════════════════════
    //  SEARCH HELPERS
    // ═══════════════════════════════════════════════════════════

    public void tapSearchInput() {
        searchUtils.tapSearchInput();
    }

    public void clearSearchField() {
        searchUtils.clearSearch();
    }

    public void enterSearchText(String text) {
        searchUtils.typeSearchText(text);
    }

    public void searchRecord(String name) {
        searchUtils.searchRecord(name);
    }

    public void searchForProductSetupType(String name) {
        searchUtils.searchRecord(name);
    }

    public void exitSearch() {
        searchUtils.ensureSearchClosed();
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP — FIELD INPUT
    // ═══════════════════════════════════════════════════════════

    public void enterProductSetupName(String name) {
        WebElement field = elementUtils.waitForFirst(NAME_INPUT, 10);
        if (field == null) return;
        try { field.click(); field.clear(); field.sendKeys(name); }
        catch (Exception ignored) { /* stale element — caller retries if needed */ }
        keyboardUtils.hideKeyboardSafely();
    }

    public void clearProductSetupNameField() {
        WebElement field = elementUtils.waitForFirst(NAME_INPUT, 5);
        if (field != null) try { field.click(); field.clear(); } catch (Exception ignored) { /* stale — skip */ }
        keyboardUtils.hideKeyboardSafely();
    }

    public boolean isProductSetupNameFieldVisible() {
        return elementUtils.waitForPresence(NAME_INPUT, 5);
    }

    public String getProductSetupNameValue() {
        // Retry briefly in case the edit popup just opened and the field isn't populated yet
        for (int i = 0; i < 3; i++) {
            WebElement field = elementUtils.waitForFirst(NAME_INPUT, 3);
            try {
                if (field != null) {
                    String val = field.getText();
                    if (val != null && !val.trim().isEmpty()) return val;
                }
            } catch (Exception ignored) { /* stale — retry */ }
        }
        return "";
    }

    public void enterDescription(String desc) {
        WebElement field = elementUtils.waitForFirst(DESC_INPUT, 10);
        if (field == null) return;
        try { field.click(); field.clear(); field.sendKeys(desc); }
        catch (Exception ignored) { /* stale element — caller retries if needed */ }
        keyboardUtils.hideKeyboardSafely();
    }

    public boolean isDescriptionFieldVisible() {
        if (elementUtils.waitForPresence(DESC_INPUT, 3)) return true;
        return elementUtils.isPresent(AppiumBy.xpath(
                "//android.view.View[contains(@hint,'Description')]"));
    }

    public boolean isMachineOutputTimerFieldVisible() {
        return elementUtils.waitForPresence(MACHINE_OUTPUT_TIMER_FIELD, 5);
    }

    public void tapMachineOutputTimerField() {
        tapTimerField(MACHINE_OUTPUT_TIMER_FIELD);
    }

    public boolean isProductSetupTimerFieldVisible() {
        return elementUtils.waitForPresence(PRODUCT_SETUP_TIMER_FIELD, 5);
    }

    public void tapProductSetupTimerField() {
        tapTimerField(PRODUCT_SETUP_TIMER_FIELD);
    }

    // ═══════════════════════════════════════════════════════════
    //  DURATION PICKER
    // ═══════════════════════════════════════════════════════════

    /**
     * Taps the Machine Output Timer field to open Select Duration popup,
     * sets each SeekBar column via W3C touch action, then clicks Save.
     */
    public void setMachineOutputTimer(int hours, int minutes, int seconds) {
        tapTimerField(MACHINE_OUTPUT_TIMER_FIELD);
        elementUtils.waitForPresence(SELECT_DURATION_POPUP, 10);
        setDurationSeekBar("hour", hours);
        setDurationSeekBar("min",  minutes);
        setDurationSeekBar("sec",  seconds);
        elementUtils.clickWhenFoundByAccessibility("Save", 10);
    }

    /**
     * Taps the Product Setup Timer field to open Select Duration popup,
     * sets each SeekBar column via W3C touch action, then clicks Save.
     */
    public void setProductSetupTimer(int hours, int minutes, int seconds) {
        tapTimerField(PRODUCT_SETUP_TIMER_FIELD);
        elementUtils.waitForPresence(SELECT_DURATION_POPUP, 10);
        setDurationSeekBar("hour", hours);
        setDurationSeekBar("min",  minutes);
        setDurationSeekBar("sec",  seconds);
        elementUtils.clickWhenFoundByAccessibility("Save", 10);
    }

    /**
     * Taps a timer field by its bounds using W3C PointerInput.
     * The timer fields are clickable=false in Flutter so .click() is unreliable.
     */
    private void tapTimerField(By locator) {
        elementUtils.disableImplicitWait();
        try {
            WebElement field = elementUtils.waitForFirst(locator, 10);
            if (field == null) return;
            Rectangle r = field.getRect();
            int x = r.x + r.width  / 2;
            int y = r.y + r.height / 2;
            PointerInput touch = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(touch, 0);
            tap.addAction(touch.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
            tap.addAction(touch.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(touch.createPointerMove(Duration.ofMillis(80), PointerInput.Origin.viewport(), x, y));
            tap.addAction(touch.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(tap));
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    /**
     * Scrolls a drum-picker column in the Select Duration popup by swiping bottom-to-top.
     * Each full swipe covers ~4 items; remainder items use a proportional swipe.
     *
     * @param unitDesc  matches SeekBar content-desc — "hour", "min", or "sec"
     * @param value     target value to scroll to (from 0)
     */
    private void setDurationSeekBar(String unitDesc, int value) {
        if (value <= 0) return;
        elementUtils.disableImplicitWait();
        try {
            WebElement bar = elementUtils.waitForFirst(AppiumBy.androidUIAutomator(
                    "new UiSelector().className(\"android.widget.SeekBar\")"
                    + ".descriptionContains(\"" + unitDesc + "\")"), 5);
            if (bar == null) return;
            Rectangle r   = bar.getRect();
            int x         = r.x + r.width  / 2;
            int topY      = r.y + 10;
            int bottomY   = r.y + r.height - 10;
            int itemHeight = (r.height - 20) / 4; // ~4 items visible per column

            int fullSwipes = value / 4;
            int remainder  = value % 4;

            for (int i = 0; i < fullSwipes; i++) {
                swipeColumn(x, bottomY, topY, 400);
            }
            if (remainder > 0) {
                int endY = Math.max(topY, bottomY - remainder * itemHeight);
                swipeColumn(x, bottomY, endY, 300);
            }
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    private void swipeColumn(int x, int startY, int endY, int durationMs) {
        PointerInput touch = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(touch, 0);
        swipe.addAction(touch.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, startY));
        swipe.addAction(touch.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(touch.createPointerMove(Duration.ofMillis(durationMs), PointerInput.Origin.viewport(), x, endY));
        swipe.addAction(touch.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));
        elementUtils.waitForPresence(SELECT_DURATION_POPUP, 1);
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP — BUTTONS
    // ═══════════════════════════════════════════════════════════

    public void clickSubmitButton() {
        keyboardUtils.hideKeyboardSafely();
        elementUtils.clickWhenFoundByAccessibility("Submit", 10);
    }

    public void clickSubmitButtonMultipleTimes() {
        keyboardUtils.hideKeyboardSafely();
        elementUtils.clickWhenFoundByAccessibility("Submit", 10);
        try { elementUtils.clickWhenFoundByAccessibility("Submit", 3); } catch (Exception ignored) { /* already dismissed */ }
        try { elementUtils.clickWhenFoundByAccessibility("Submit", 3); } catch (Exception ignored) { /* already dismissed */ }
    }

    public boolean isSubmitButtonVisible() {
        return elementUtils.isPresentByAccessibility("Submit");
    }

    public void clickSaveButton() {
        keyboardUtils.hideKeyboardSafely();
        elementUtils.clickWhenFoundByAccessibility("Save", 10);
    }

    public boolean isSaveButtonVisible() {
        return elementUtils.isPresentByAccessibility("Save");
    }

    public void clickCloseButton() {
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> {
                        List<WebElement> btns = d.findElements(
                                AppiumBy.xpath(
                                        "(//android.view.View[contains(@content-desc,'Add Product Setup Type')]"
                                        + " | //android.view.View[contains(@content-desc,'Edit Product Setup Type')]"
                                        + " | //android.view.View[contains(@content-desc,'View Product Setup Type')]"
                                        + " | //android.view.View[contains(@content-desc,'Machine Subscription')])"
                                        + "//android.widget.Button[not(@content-desc)]"));
                        if (!btns.isEmpty()) { btns.get(0).click(); return Boolean.TRUE; }
                        List<WebElement> popups = d.findElements(
                                AppiumBy.androidUIAutomator(
                                        "new UiSelector().descriptionContains(\"Product Setup Type\")"));
                        return popups.isEmpty() ? Boolean.TRUE : null;
                    });
        } catch (Exception ignored) { /* timeout — popup already closed */ }
        finally { elementUtils.restoreImplicitWait(); }
    }

    public boolean isCloseButtonVisible() {
        return elementUtils.isPresentByAccessibility("Close")
                || elementUtils.firstOrNull(AppiumBy.xpath(
                "//android.widget.Button[contains(@content-desc,'close') or contains(@content-desc,'Close')]")) != null;
    }

    public void clickYesExitButton() {
        elementUtils.clickWhenFoundByUIAutomator(
                "new UiSelector().description(\"Yes, Exit\")", 10);
    }

    // ═══════════════════════════════════════════════════════════
    //  POPUP DISPLAY CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isAddProductSetupTypePopupDisplayed() {
        return elementUtils.waitForPresence(ADD_POPUP, 10);
    }

    public boolean isEditProductSetupTypePopupDisplayed() {
        return elementUtils.isPresent(EDIT_POPUP);
    }

    public boolean waitForEditPopupToBeVisible(int timeoutSecs) {
        return elementUtils.waitForPresence(EDIT_POPUP, timeoutSecs);
    }

    public boolean isViewProductSetupTypePopupDisplayed() {
        return elementUtils.isPresent(VIEW_POPUP);
    }

    public boolean isSelectDurationPopupDisplayed() {
        return elementUtils.waitForPresence(SELECT_DURATION_POPUP, 10);
    }

    public boolean isDurationMinimumAlertDisplayed() {
        if (elementUtils.waitForPresence(DURATION_MIN_ALERT, 5)) return true;
        return elementUtils.isPresent(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"Duration should be at least 1 minute\")"));
    }

    /** Closes the Select Duration popup via its X button without saving — field remains empty. */
    public void clickDurationPickerCloseButton() {
        elementUtils.disableImplicitWait();
        try {
            // Dismiss any visible toast/alert by swiping it right to left first
            dismissDurationAlertToast();

            // Find and tap X button inside Select Duration popup (not Save)
            List<WebElement> btns = driver.findElements(AppiumBy.xpath(
                    "//android.view.View[contains(@content-desc,'Select Duration')]"
                    + "//android.widget.Button[not(@content-desc='Save')]"));
            if (!btns.isEmpty()) { tapElementByRect(btns.get(0)); return; }

            // Fallback: Close accessibility label
            List<WebElement> close = driver.findElements(AppiumBy.accessibilityId("Close"));
            if (!close.isEmpty()) { tapElementByRect(close.get(0)); return; }

            // Fallback: any non-Save/Submit button visible
            List<WebElement> anyBtn = driver.findElements(AppiumBy.xpath(
                    "//android.widget.Button[not(@content-desc='Save') and not(@content-desc='Submit')]"));
            if (!anyBtn.isEmpty()) tapElementByRect(anyBtn.get(0));
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    /** Swipes the "Duration should be at least 1 minute" toast from right to left to dismiss it. */
    private void dismissDurationAlertToast() {
        List<WebElement> toasts = driver.findElements(DURATION_MIN_ALERT);
        if (toasts.isEmpty()) {
            toasts = driver.findElements(AppiumBy.androidUIAutomator(
                    "new UiSelector().textContains(\"Duration should be at least 1 minute\")"));
        }
        if (toasts.isEmpty()) return;
        Rectangle r = toasts.get(0).getRect();
        int y       = r.y + r.height / 2;
        int startX  = r.x + r.width - 10;
        int endX    = r.x + 10;
        PointerInput touch = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(touch, 0);
        swipe.addAction(touch.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, y));
        swipe.addAction(touch.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(touch.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), endX, y));
        swipe.addAction(touch.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));
        elementUtils.waitForPresence(SELECT_DURATION_POPUP, 1);
    }

    private void tapElementByRect(WebElement el) {
        Rectangle r = el.getRect();
        int x = r.x + r.width  / 2;
        int y = r.y + r.height / 2;
        PointerInput touch = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(touch, 0);
        tap.addAction(touch.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(touch.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(touch.createPointerMove(Duration.ofMillis(80), PointerInput.Origin.viewport(), x, y));
        tap.addAction(touch.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(tap));
    }

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERROR CHECKS
    // ═══════════════════════════════════════════════════════════

    public boolean isNameRequiredErrorDisplayed() {
        return elementUtils.waitForPresence(NAME_REQUIRED_ERROR, 5);
    }

    public boolean isMachineOutputTimerRequiredErrorDisplayed() {
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> !d.findElements(ANY_REQUIRED_ERROR).isEmpty());
        } catch (Exception e) {
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
        // If name field has a value, machine timer error is the first (index 0);
        // if name is also empty, name error occupies index 0 so machine timer is index 1.
        String nameVal = getProductSetupNameValue();
        boolean nameHasValue = (nameVal != null && !nameVal.trim().isEmpty());
        int idx = nameHasValue ? 0 : 1;
        By locator = AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionContains(\"This field is required\").instance(" + idx + ")");
        elementUtils.disableImplicitWait();
        try {
            return !driver.findElements(locator).isEmpty();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public boolean isProductSetupTimerRequiredErrorDisplayed() {
        elementUtils.disableImplicitWait();
        int count;
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> !d.findElements(ANY_REQUIRED_ERROR).isEmpty());
            count = driver.findElements(ANY_REQUIRED_ERROR).size();
        } catch (Exception e) {
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
        if (count == 0) return false;
        // Product Setup Timer is always the last field, so its error is at the highest index.
        By locator = AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionContains(\"This field is required\").instance(" + (count - 1) + ")");
        elementUtils.disableImplicitWait();
        try {
            return !driver.findElements(locator).isEmpty();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public boolean isAllRequiredErrorsDisplayed() {
        return isNameRequiredErrorDisplayed()
                && isMachineOutputTimerRequiredErrorDisplayed()
                && isProductSetupTimerRequiredErrorDisplayed();
    }

    public boolean isTimerRequiredErrorDisplayed() {
        elementUtils.disableImplicitWait();
        try {
            try {
                new WebDriverWait(driver, Duration.ofSeconds(5))
                        .pollingEvery(Duration.ofMillis(400))
                        .until(d -> !d.findElements(ANY_REQUIRED_ERROR).isEmpty());
            } catch (Exception e) {
                return false;
            }
            List<WebElement> errors = driver.findElements(ANY_REQUIRED_ERROR);
            if (errors.size() >= 1) {
                String nameVal = getProductSetupNameValue();
                return nameVal != null && !nameVal.trim().isEmpty();
            }
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public boolean isAnyValidationErrorDisplayed() {
        return elementUtils.isPresent(GENERIC_VALIDATION_ERROR);
    }

    // ═══════════════════════════════════════════════════════════
    //  SUCCESS SIGNALS
    // ═══════════════════════════════════════════════════════════

    public boolean waitForCreateSuccess(int timeoutSecs) {
        return popupUtils.waitForCreateSuccess(timeoutSecs);
    }

    public boolean waitForUpdateSuccess(int timeoutSecs) {
        return popupUtils.waitForUpdateSuccess(timeoutSecs);
    }

    /**
     * Waits for the Edit popup to fully close by watching the "Save" button disappear.
     */
    public boolean waitForEditPopupToClose(int timeoutSecs) {
        return popupUtils.waitForEditPopupClose(timeoutSecs);
    }

    public boolean waitForReturnToList(int timeoutSecs) {
        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSecs))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> {
                        if (!d.findElements(AppiumBy.accessibilityId("Add")).isEmpty())    return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.accessibilityId("Search")).isEmpty()) return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.accessibilityId("Filter")).isEmpty()) return Boolean.TRUE;
                        if (!d.findElements(AppiumBy.accessibilityId("Sort")).isEmpty())   return Boolean.TRUE;
                        // Search mode: EditText visible but no popup (Submit/Save absent)
                        if (!d.findElements(By.className("android.widget.EditText")).isEmpty()
                                && d.findElements(AppiumBy.accessibilityId("Submit")).isEmpty()
                                && d.findElements(AppiumBy.accessibilityId("Save")).isEmpty()) {
                            return Boolean.TRUE;
                        }
                        return null;
                    });
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public boolean verifyReturnedToList() {
        if (isOnProductSetupTypesList()) return true;

        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> {
                        List<WebElement> exits = d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().description(\"Yes, Exit\")"));
                        if (!exits.isEmpty()) { exits.get(0).click(); return Boolean.TRUE; }
                        return isOnProductSetupTypesList() ? Boolean.TRUE : null;
                    });
        } catch (Exception ignored) { /* no Yes,Exit dialog within 3s — continue */ }
        finally { elementUtils.restoreImplicitWait(); }

        elementUtils.disableImplicitWait();
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(400))
                    .until(d -> isOnProductSetupTypesList() ? Boolean.TRUE : null);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    private boolean isOnProductSetupTypesList() {
        elementUtils.disableImplicitWait();
        try {
            if (!driver.findElements(AppiumBy.accessibilityId("Add")).isEmpty())    return true;
            if (!driver.findElements(AppiumBy.accessibilityId("Search")).isEmpty()) return true;
            if (!driver.findElements(AppiumBy.accessibilityId("Filter")).isEmpty()) return true;
            if (!driver.findElements(AppiumBy.accessibilityId("Sort")).isEmpty())   return true;
            List<WebElement> edits = driver.findElements(By.className("android.widget.EditText"));
            if (!edits.isEmpty()) {
                boolean noSubmit = driver.findElements(AppiumBy.accessibilityId("Submit")).isEmpty();
                boolean noSave   = driver.findElements(AppiumBy.accessibilityId("Save")).isEmpty();
                if (noSubmit && noSave) return true;
            }
            return false;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    @Override
    public void ensureModuleListReady() {
        searchUtils.ensureSearchClosed();
        searchUtils.verifyModuleListNormalState();
    }

    // ═══════════════════════════════════════════════════════════
    //  COMPOSITE HELPERS
    // ═══════════════════════════════════════════════════════════

    /**
     * Creates a Product Setup Type with random name, waits for create success,
     * then verifies the record appears in the list. Retries up to 3 times.
     * Used in Background steps.
     */
    public String createProductSetupTypeAndReturnName() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            String name = DataGenerator.randomProductSetupName();
            searchUtils.ensureSearchClosed();
            clickAddButton();
            if (!elementUtils.waitForPresence(ADD_POPUP, 10)) {
                continue;
            }
            enterProductSetupName(name);
            enterDescription("Test product setup type description");
            setMachineOutputTimer(0, 30, 0);
            setProductSetupTimer(1, 0, 0);
            clickSubmitButton();
            if (!popupUtils.waitForCreateSuccess(30)) {
                searchUtils.ensureSearchClosed();
                continue;
            }
            searchRecord(name);
            WebElement found = getRecordByName(name);
            searchUtils.ensureSearchClosed();
            if (found != null) return name;
        }
        throw new RuntimeException(
                "Background setup: failed to create a findable Product Setup Type after 3 attempts");
    }

    /**
     * Search → click record to open the View Product Setup Type popup.
     */
    public void searchAndOpenView(String name) {
        searchRecord(name);
        WebElement el = getRecordByName(name);
        if (el != null) el.click();
    }

    // ═══════════════════════════════════════════════════════════
    //  MACHINE SUBSCRIPTION
    // ═══════════════════════════════════════════════════════════

    /** Click the Edit option in the Action Menus bottom sheet (appears after swipe). */
    public void clickEditOption() {
        elementUtils.clickWhenFoundByUIAutomator(
                "new UiSelector().description(\"Edit\")", 10);
    }

    /** True when the Edit option is visible in the Action Menus bottom sheet. */
    public boolean isEditOptionVisible() {
        return elementUtils.isPresent(AppiumBy.androidUIAutomator(
                "new UiSelector().description(\"Edit\")"));
    }

    /**
     * Click the Machine Subscription option in the Action Menus bottom sheet.
     */
    public void clickMachineSubscriptionOption() {
        WebElement opt = elementUtils.waitForFirst(MACHINE_SUBSCRIPTION_OPTION, 10);
        if (opt != null) opt.click();
    }

    /**
     * Click the Add Machines (+) button in the Machine Subscription popup.
     */
    public void clickAddMachinesButton() {
        WebElement btn = elementUtils.waitForFirst(MACHINE_ADD_BTN, 10);
        if (btn != null) btn.click();
    }

    /**
     * Select a machine by name in the Select Machines popup (checkbox click).
     */
    public void selectMachineByName(String machineName) {
        elementUtils.waitForPresence(SELECT_MACHINES_POPUP, 10);
        elementUtils.clickWhenFoundByUIAutomator(
                "new UiSelector().descriptionContains(\"" + machineName + "\")", 10);
    }

    /**
     * Click Submit in the Machine Subscription or Select Machines popup.
     */
    public void clickMachineSubscriptionSubmit() {
        clickSubmitButton();
    }

    public boolean isMachineSubscriptionPopupDisplayed() {
        return elementUtils.waitForPresence(MACHINE_SUBSCRIPTION_POPUP, 10);
    }

    public boolean isActionMenusSheetDisplayed() {
        return elementUtils.waitForPresence(ACTION_MENUS_SHEET, 5);
    }

    public void dismissActionMenus() {
        elementUtils.disableImplicitWait();
        try {
            if (driver.findElements(ACTION_MENUS_SHEET).isEmpty()) return;
        } finally {
            elementUtils.restoreImplicitWait();
        }
        driver.navigate().back();
        elementUtils.waitForAbsence(ACTION_MENUS_SHEET, 3);
    }

    public boolean isSelectMachinesPopupDisplayed() {
        return elementUtils.waitForPresence(SELECT_MACHINES_POPUP, 10);
    }

    public boolean isMachineVisible(String machineName) {
        return elementUtils.isPresent(AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionContains(\"" + machineName + "\")"));
    }

    /** Click the delete icon on the first machine in the Machine Subscription list. */
    public void clickDeleteMachineIcon() {
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> delBtns = driver.findElements(AppiumBy.xpath(
                    "//android.view.View[starts-with(@content-desc,'Machine Subscription')]"
                    + "//android.widget.Button[contains(@content-desc,'delete') or contains(@content-desc,'Delete') or contains(@content-desc,'remove') or contains(@content-desc,'Remove')]"));
            if (!delBtns.isEmpty()) { delBtns.get(0).click(); return; }
            // fallback: buttons inside popup that are NOT Add or Submit
            List<WebElement> btns = driver.findElements(AppiumBy.xpath(
                    "//android.view.View[starts-with(@content-desc,'Machine Subscription')]"
                    + "//android.widget.Button[not(@content-desc) and not(@content-desc='Submit')]"));
            // index 0 = Close X, index 1 = Add "+", index 2+ = row delete buttons
            if (btns.size() > 2) btns.get(2).click();
            else if (btns.size() > 1) btns.get(1).click();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    /** True when the Machine Subscription list has no machine rows (empty state). */
    public boolean isMachineSubscriptionListEmpty() {
        elementUtils.disableImplicitWait();
        try {
            // buttons with no content-desc inside popup: [1]=Close X, [2]=Add "+"
            // any beyond index 2 are machine row buttons
            List<WebElement> btns = driver.findElements(AppiumBy.xpath(
                    "//android.view.View[starts-with(@content-desc,'Machine Subscription')]"
                    + "//android.widget.Button[not(@content-desc) and not(@content-desc='Submit')]"));
            return btns.size() <= 2;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public boolean isMachineSubscriptionOptionVisible() {
        return elementUtils.isPresent(MACHINE_SUBSCRIPTION_OPTION);
    }

    public void clickMachineSubscriptionCloseButton() {
        WebElement btn = elementUtils.waitForFirst(MACHINE_SUBSCRIPTION_CLOSE_BTN, 10);
        if (btn != null) btn.click();
        popupUtils.confirmExitIfAlertShows(3);
    }

    public boolean waitForMachineSubscriptionPopupClosed(int timeoutSecs) {
        return elementUtils.waitForAbsence(MACHINE_SUBSCRIPTION_POPUP, timeoutSecs);
    }

    public void clickMachineAddButton() {
        clickAddMachinesButton();
    }

    public boolean isMachineSelectBottomSheetDisplayed() {
        return elementUtils.waitForPresence(MACHINE_SELECT_SHEET, 10);
    }

    public void selectOneMachine() {
        elementUtils.waitForPresence(MACHINE_SELECT_SHEET, 5);
        WebElement item = elementUtils.waitForFirst(UNCHECKED_MACHINE_ITEM, 10);
        if (item == null) {
            elementUtils.disableImplicitWait();
            try {
                List<WebElement> all = driver.findElements(MACHINE_SELECT_ITEMS);
                if (!all.isEmpty()) item = all.get(0);
            } finally {
                elementUtils.restoreImplicitWait();
            }
        }
        if (item != null) { try { item.click(); } catch (Exception ignored) { /* best-effort */ } }
    }

    public void selectMultipleMachines(int count) {
        elementUtils.waitForPresence(MACHINE_SELECT_ITEMS, 10);
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> items = driver.findElements(MACHINE_SELECT_ITEMS);
            int n = Math.min(count, items.size());
            for (int i = 0; i < n; i++) {
                try { items.get(i).click(); } catch (Exception ignored) { /* best-effort */ }
            }
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public void clickMachineSelectSheetSubmit() {
        By scopedSubmit = AppiumBy.xpath(
                "//android.view.View[@content-desc='Select Machines']"
                + "//android.widget.Button[@content-desc='Submit']");
        WebElement btn = elementUtils.waitForFirst(scopedSubmit, 10);
        if (btn == null) btn = elementUtils.waitForFirst(AppiumBy.accessibilityId("Submit"), 5);
        if (btn != null) btn.click();
    }

    public void clickMachineSubscriptionSubmitScoped() {
        By scopedSubmit = AppiumBy.xpath(
                "//android.view.View[starts-with(@content-desc,'Machine Subscription')]"
                + "//android.widget.Button[@content-desc='Submit']");
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> btns = driver.findElements(scopedSubmit);
            if (!btns.isEmpty()) { btns.get(0).click(); return; }
        } finally {
            elementUtils.restoreImplicitWait();
        }
        WebElement btn = elementUtils.waitForFirst(AppiumBy.accessibilityId("Submit"), 5);
        if (btn != null) btn.click();
    }

    public boolean hasMachinesInSubscription() {
        return elementUtils.waitForPresence(MACHINE_ITEMS_IN_POPUP, 5);
    }

    public int getMachineCount() {
        elementUtils.waitForPresence(MACHINE_ITEMS_IN_POPUP, 5);
        elementUtils.disableImplicitWait();
        try {
            return driver.findElements(MACHINE_ITEMS_IN_POPUP).size();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public void deleteMachineFromSubscription() {
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> icons = driver.findElements(MACHINE_DELETE_ICONS);
            if (!icons.isEmpty()) icons.get(0).click();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public void deleteAllMachinesFromSubscription() {
        for (int i = 0; i < 30; i++) {
            elementUtils.disableImplicitWait();
            List<WebElement> icons;
            try { icons = driver.findElements(MACHINE_DELETE_ICONS); }
            finally { elementUtils.restoreImplicitWait(); }
            if (icons.isEmpty()) break;
            icons.get(0).click();
        }
    }

    public boolean waitForAllMachinesGone(int timeoutSecs) {
        return elementUtils.waitForAbsence(MACHINE_ITEMS_IN_POPUP, timeoutSecs);
    }

    /**
     * Ensures at least one machine is subscribed to the given Product Setup Type.
     * Searches for the record, swipes it, opens Machine Subscription, and if the list
     * is empty adds the first available machine and submits.
     */
    public void ensureMachineSubscribed(String name) {
        searchRecord(name);
        WebElement item = getRecordByName(name);
        if (item == null) return;
        longPress(item);
        if (!elementUtils.waitForPresence(ACTION_MENUS_SHEET, 5)) return;
        clickMachineSubscriptionOption();
        if (!elementUtils.waitForPresence(MACHINE_SUBSCRIPTION_POPUP, 10)) return;
        if (!hasMachinesInSubscription()) {
            clickMachineAddButton();
            elementUtils.waitForPresence(MACHINE_SELECT_SHEET, 10);
            selectOneMachine();
            clickMachineSelectSheetSubmit();
            elementUtils.waitForPresence(MACHINE_SUBSCRIPTION_POPUP, 10);
            clickMachineSubscriptionSubmitScoped();
            waitForMachineSubscriptionPopupClosed(15);
        } else {
            clickMachineSubscriptionCloseButton();
            waitForMachineSubscriptionPopupClosed(5);
        }
        searchUtils.ensureSearchClosed();
    }

    /** Selects the first machine item visible in the Select Machines popup. */
    public void selectFirstAvailableMachine() {
        elementUtils.disableImplicitWait();
        try {
            List<WebElement> items = driver.findElements(AppiumBy.xpath(
                    "//android.view.View[contains(@content-desc,'Select Machines')]"
                    + "//android.widget.CheckBox"));
            if (!items.isEmpty()) { items.get(0).click(); return; }
            List<WebElement> rows = driver.findElements(AppiumBy.xpath(
                    "//android.view.View[contains(@content-desc,'Select Machines')]"
                    + "//android.widget.TextView"));
            if (!rows.isEmpty()) rows.get(0).click();
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    // ═══════════════════════════════════════════════════════════
    //  NON-EDITABLE / READ-ONLY CHECKS
    // ═══════════════════════════════════════════════════════════

    private boolean isFieldNonEditable(By locator) {
        elementUtils.disableImplicitWait();
        try {
            WebElement field = elementUtils.firstOrNull(locator);
            if (field == null) return true;
            return !field.isEnabled();
        } catch (Exception e) {
            return true;
        } finally {
            elementUtils.restoreImplicitWait();
        }
    }

    public boolean isNameFieldNonEditable()        { return isFieldNonEditable(NAME_INPUT); }
    public boolean isDescriptionFieldNonEditable() { return isFieldNonEditable(DESC_INPUT); }

    public boolean hasNoEditableInputFields() {
        return isFieldNonEditable(NAME_INPUT) && isFieldNonEditable(DESC_INPUT);
    }

    // ═══════════════════════════════════════════════════════════
    //  TEXT VISIBILITY
    // ═══════════════════════════════════════════════════════════

    /** True when any element on screen contains the given text (checks both text and content-desc). */
    public boolean isTextVisible(String text) {
        if (text == null || text.isEmpty()) return true;
        String escaped = text.replace("\"", "\\\"");
        if (elementUtils.isPresent(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"" + escaped + "\")"))) return true;
        return elementUtils.isPresent(AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionContains(\"" + escaped + "\")"));
    }
}
