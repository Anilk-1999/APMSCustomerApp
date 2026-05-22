package pageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utilities.*;

import java.time.Duration;
import java.util.List;

/**
 * Abstract base for all Page Objects.
 *
 * Provides protected convenience wrappers so page classes never interact with
 * the driver, wait strategy, or swipe helper directly — all infrastructure
 * lives here.
 *
 * WAIT STRATEGY (enterprise rule — global implicit wait = 0):
 *  - Use elementUtils.isPresent()        for instant absence/presence checks
 *  - Use elementUtils.waitForPresence()  for polling until an element appears
 *  - Use elementUtils.waitForAbsence()   for polling until an element disappears
 *  - Use flutterUtils.waitForFab()       for post-action list-screen detection
 *  - PageFactory fields (via AppiumFieldDecorator 5 s) are still used for
 *    elements that are GUARANTEED present when the method is called (e.g. form
 *    fields inside an open popup). For optional/conditional elements use
 *    elementUtils.firstOrNull() instead of touching the PageFactory proxy.
 */
public abstract class BasePage {

    protected final AndroidDriver  driver;
    protected final WaitHelper     waitHelper;
    protected final SwipeHelper    swipeHelper;
    protected final ActionHelper   actionHelper;
    protected final ElementUtils   elementUtils;
    protected final SearchUtils    searchUtils;
    protected final PopupUtils     popupUtils;
    protected final FlutterUtils   flutterUtils;
    protected final KeyboardUtils  keyboardUtils;
    protected final RetryUtils     retryUtils;

    protected BasePage(AndroidDriver driver) {
        this.driver       = driver;
        this.waitHelper   = new WaitHelper(driver);
        this.swipeHelper  = new SwipeHelper(driver);
        this.actionHelper = new ActionHelper(driver);
        this.elementUtils = new ElementUtils(driver);
        this.searchUtils  = new SearchUtils(driver);
        this.popupUtils   = new PopupUtils(driver);
        this.flutterUtils = new FlutterUtils(driver);
        this.keyboardUtils = new KeyboardUtils(driver);
        this.retryUtils   = new RetryUtils(driver);

        // AppiumFieldDecorator 5 s: PageFactory proxies wait up to 5 s when an element is
        // absent. Use ONLY for elements that are expected to be present (form fields, buttons
        // inside a confirmed-open popup). For presence checks on optional elements, use
        // elementUtils.firstOrNull() or elementUtils.isPresent() instead.
        PageFactory.initElements(
                new AppiumFieldDecorator(driver, Duration.ofSeconds(5)), this);
    }

    // ── Tap / Click ───────────────────────────────────────────────────────────

    protected void tap(WebElement element) {
        actionHelper.tap(element);
    }

    /** W3C PointerInput tap at element center — use for Flutter widgets that ignore .click(). */
    protected void tapCenter(WebElement element) {
        actionHelper.tapCenter(element);
    }

    /** W3C PointerInput tap at absolute screen coordinates. */
    protected void tapAt(int x, int y) {
        actionHelper.tapAt(x, y);
    }

    // ── Text input ────────────────────────────────────────────────────────────

    protected void clearAndType(WebElement element, String text) {
        actionHelper.clearAndType(element, text);
    }

    protected void typeText(WebElement element, String text) {
        actionHelper.typeText(element, text);
    }

    protected void clearField(WebElement element) {
        actionHelper.clearField(element);
    }

    // ── Read ──────────────────────────────────────────────────────────────────

    protected String getText(WebElement element) {
        return actionHelper.getText(element);
    }

    // ── Toggle ────────────────────────────────────────────────────────────────

    protected void enableToggle(WebElement toggle) {
        actionHelper.enableToggle(toggle);
    }

    protected void disableToggle(WebElement toggle) {
        actionHelper.disableToggle(toggle);
    }

    protected void clickToggle(WebElement toggle) {
        actionHelper.clickToggle(toggle);
    }

    protected boolean isToggleOn(WebElement toggle) {
        return actionHelper.isToggleOn(toggle);
    }

    // ── State checks (PageFactory proxy — use for guaranteed-present elements) ─

    protected boolean isDisplayed(WebElement element) {
        return actionHelper.isDisplayed(element);
    }

    protected boolean isEnabled(WebElement element) {
        return actionHelper.isEnabled(element);
    }

    // ── Zero-wait presence check (use for optional / conditional elements) ─────

    /** Instant true/false — never blocks. Replaces isDisplayed() for optional elements. */
    protected boolean isPresent(By locator) {
        return elementUtils.isPresent(locator);
    }

    /** First matching element or null — never blocks. */
    protected WebElement firstOrNull(By locator) {
        return elementUtils.firstOrNull(locator);
    }

    /** Polls until element appears, returns element or null. */
    protected WebElement waitForFirst(By locator, int timeoutSecs) {
        return elementUtils.waitForFirst(locator, timeoutSecs);
    }

    // ── Scroll / Swipe ────────────────────────────────────────────────────────

    protected void scrollDown() {
        swipeHelper.scrollDown();
    }

    protected void scrollUp() {
        swipeHelper.scrollUp();
    }

    protected void scrollUntilVisible(WebElement element) {
        swipeHelper.scrollUntilVisible(element);
    }

    protected void scrollToText(String text) {
        swipeHelper.scrollToElementByText(text);
    }

    protected void swipeRightToLeft(WebElement element) {
        swipeHelper.swipeRightToLeft(element);
    }

    protected void swipeLeftToRight(WebElement element) {
        swipeHelper.swipeLeftToRight(element);
    }

    protected void longPress(WebElement element) {
        swipeHelper.longPress(element);
    }

    // ── Wait (WaitHelper — kept for backward compatibility with existing pages) ─

    protected void waitForVisibility(WebElement element) {
        waitHelper.waitForVisibility(element);
    }

    protected void waitForVisibilities(List<WebElement> elements) {
        waitHelper.waitForVisibilities(elements);
    }

    protected void waitForVisibility(WebElement element, int timeoutSeconds) {
        waitHelper.waitForVisibility(element, timeoutSeconds);
    }

    protected void waitForClickability(WebElement element) {
        waitHelper.waitForClickability(element);
    }

    protected WebElement waitForVisible(WebElement element) {
        return waitHelper.waitForVisible(element);
    }

    protected WebElement waitForVisible(WebElement element, int seconds) {
        return waitHelper.waitForVisible(element, seconds);
    }

    protected WebElement waitForClickable(WebElement element) {
        return waitHelper.waitForClickable(element);
    }

    protected WebElement waitForClickable(WebElement element, int seconds) {
        return waitHelper.waitForClickable(element, seconds);
    }

    protected WebElement waitForPresence(By locator) {
        return waitHelper.waitForPresence(locator);
    }

    protected WebElement waitForPresence(By locator, int seconds) {
        return waitHelper.waitForPresence(locator, seconds);
    }

    protected void waitForDisappear(WebElement element) {
        waitHelper.waitForDisappear(element);
    }

    protected void waitForDisappear(WebElement element, int seconds) {
        waitHelper.waitForDisappear(element, seconds);
    }

    protected boolean isDisplayedFast(WebElement element) {
        return waitHelper.isDisplayedFast(element);
    }

    protected void clickWhenReady(WebElement element) {
        waitHelper.clickWhenReady(element);
    }

    protected void clickWhenReady(WebElement element, int seconds) {
        waitHelper.clickWhenReady(element, seconds);
    }

    protected void sendKeysWhenReady(WebElement element, String text) {
        waitHelper.sendKeysWhenReady(element, text);
    }

    protected void hideKeyboard() {
        keyboardUtils.hideKeyboardSafely();
    }

    // ── Global search close (X button) — all modules except Activity ─────────

    /** True when the search bar is open on a list screen (no popup). */
    public boolean isSearchOpen() {
        return searchUtils.isSearchOpen();
    }

    /** Clicks the X button on the right of the search field if currently open. */
    public void clickSearchCloseXIfOpen() {
        searchUtils.clickSearchCloseXIfOpen();
    }

    /**
     * Clicks the X button and waits for the list to return to normal state.
     * Does nothing if search is already closed.
     */
    public void ensureSearchClosed() {
        searchUtils.ensureSearchClosed();
    }

    /**
     * Polls up to 5 s for the list screen to be in normal state
     * (FAB, Filter, or Search button visible).
     */
    public boolean verifyModuleListNormalState() {
        return searchUtils.verifyModuleListNormalState();
    }

    /** Closes search (if open) then verifies the list is in normal state. */
    public void ensureModuleListReady() {
        searchUtils.ensureModuleListReady();
    }
}
