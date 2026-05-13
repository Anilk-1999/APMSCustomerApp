package utilities;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Centralised explicit-wait helper.
 * No Thread.sleep() anywhere in this class.
 */
public class WaitHelper {

    private final AndroidDriver driver;
    private static final int DEFAULT_TIMEOUT = 15;
    private static final int SHORT_TIMEOUT   = 5;

    public WaitHelper(AndroidDriver driver) {
        this.driver = driver;
    }

    // ── visibility ────────────────────────────────────────────────────────────

    public void waitForVisibility(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForVisibility(WebElement element, int timeoutSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForVisibilities(List<WebElement> elements) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public void waitForInvisibility(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.invisibilityOf(element));
    }

    // ── clickability ──────────────────────────────────────────────────────────

    public void waitForClickability(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    // ── text ──────────────────────────────────────────────────────────────────

    public void waitForTextInElement(WebElement element, String text) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    // ── safe presence check ───────────────────────────────────────────────────

    public boolean isElementPresent(WebElement element) {
        try {
            waitForVisibility(element, SHORT_TIMEOUT);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementPresent(WebElement element, int timeoutSeconds) {
        try {
            waitForVisibility(element, timeoutSeconds);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ── explicit wait — clean named aliases (300 ms poll for faster detection) ─

    public WebElement waitForVisible(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .pollingEvery(Duration.ofMillis(300))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForVisible(WebElement element, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .pollingEvery(Duration.ofMillis(300))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForClickable(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .pollingEvery(Duration.ofMillis(300))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForClickable(WebElement element, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .pollingEvery(Duration.ofMillis(300))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    // ── by-locator presence (element may not exist in DOM yet) ───────────────

    public WebElement waitForPresence(By locator, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .pollingEvery(Duration.ofMillis(300))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitForPresence(By locator) {
        return waitForPresence(locator, DEFAULT_TIMEOUT);
    }

    // ── disappear (loading spinner, popup dismiss, etc.) ─────────────────────

    public void waitForDisappear(WebElement element, int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .pollingEvery(Duration.ofMillis(300))
                .until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitForDisappear(WebElement element) {
        waitForDisappear(element, DEFAULT_TIMEOUT);
    }

    // ── zero-wait fast check (optional / conditional elements) ───────────────

    public boolean isDisplayedFast(WebElement element) {
        try { return element.isDisplayed(); }
        catch (Exception e) { return false; }
    }

    // ── combined wait + action ────────────────────────────────────────────────

    public void clickWhenReady(WebElement element) {
        waitForClickable(element).click();
    }

    public void clickWhenReady(WebElement element, int seconds) {
        waitForClickable(element, seconds).click();
    }

    public void sendKeysWhenReady(WebElement element, String text) {
        waitForVisible(element).sendKeys(text);
    }
}
