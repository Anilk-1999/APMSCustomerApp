package pageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import utilities.ActionHelper;
import utilities.SwipeHelper;
import utilities.WaitHelper;

import java.time.Duration;

/**
 * Abstract base for all Page Objects.
 *
 * Provides protected convenience wrappers so subclasses never interact
 * with drivers, waits or swipe helpers directly — all logic stays here.
 */
public abstract class BasePage {

    protected final AndroidDriver driver;
    protected final WaitHelper    waitHelper;
    protected final SwipeHelper   swipeHelper;
    protected final ActionHelper  actionHelper;

    protected BasePage(AndroidDriver driver) {
        this.driver      = driver;
        this.waitHelper  = new WaitHelper(driver);
        this.swipeHelper = new SwipeHelper(driver);
        this.actionHelper = new ActionHelper(driver);
        PageFactory.initElements(
                new AppiumFieldDecorator(driver, Duration.ofSeconds(15)), this);
    }

    // ── Tap / Click ───────────────────────────────────────────────────────────

    protected void tap(WebElement element) {
        actionHelper.tap(element);
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

    // ── State checks ──────────────────────────────────────────────────────────

    protected boolean isDisplayed(WebElement element) {
        return actionHelper.isDisplayed(element);
    }

    protected boolean isEnabled(WebElement element) {
        return actionHelper.isEnabled(element);
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

    // ── Wait ──────────────────────────────────────────────────────────────────

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

    protected void hideKeyboard() {
        try { driver.hideKeyboard(); } catch (Exception e) { /* keyboard not shown — safe to ignore */ }
    }
}
