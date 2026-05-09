package utilities;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;

/**
 * All touch-gesture helpers: swipe, scroll, long-press.
 * Uses W3C Actions (PointerInput) — no deprecated TouchActions.
 */
public class SwipeHelper {

    private final AndroidDriver driver;

    public SwipeHelper(AndroidDriver driver) {
        this.driver = driver;
    }

    // ── Swipe on element (right → left) to reveal Edit/Favorite action ────────

    public void swipeRightToLeft(WebElement element) {
        Point     loc  = element.getLocation();
        Dimension size = element.getSize();
        int startX  = loc.getX() + (int) (size.width * 0.85);
        int endX    = loc.getX() + (int) (size.width * 0.15);
        int centerY = loc.getY() + size.height / 2;
        performSwipe(startX, centerY, endX, centerY, 600);
    }

    // ── Swipe on element (left → right) ──────────────────────────────────────

    public void swipeLeftToRight(WebElement element) {
        Point     loc  = element.getLocation();
        Dimension size = element.getSize();
        int startX  = loc.getX() + (int) (size.width * 0.15);
        int endX    = loc.getX() + (int) (size.width * 0.85);
        int centerY = loc.getY() + size.height / 2;
        performSwipe(startX, centerY, endX, centerY, 600);
    }

    // ── Full-screen scroll ────────────────────────────────────────────────────

    public void scrollDown() {
        Dimension size = driver.manage().window().getSize();
        int x      = size.width / 2;
        int startY = (int) (size.height * 0.75);
        int endY   = (int) (size.height * 0.25);
        performSwipe(x, startY, x, endY, 500);
    }

    public void scrollUp() {
        Dimension size = driver.manage().window().getSize();
        int x      = size.width / 2;
        int startY = (int) (size.height * 0.25);
        int endY   = (int) (size.height * 0.75);
        performSwipe(x, startY, x, endY, 500);
    }

    // ── Scroll until element is visible (max 8 attempts) ─────────────────────

    public void scrollUntilVisible(WebElement element) {
        for (int i = 0; i < 8; i++) {
            try {
                if (element.isDisplayed()) return;
            } catch (Exception ignored) { /* not yet visible */ }
            scrollDown();
        }
    }

    // ── Scroll via UiAutomator (text-based) ──────────────────────────────────

    public void scrollToElementByText(String text) {
        driver.findElement(
                new io.appium.java_client.AppiumBy.ByAndroidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"" + text + "\"));"
                )
        );
    }

    public void scrollToElementByDesc(String contentDesc) {
        driver.findElement(
                new io.appium.java_client.AppiumBy.ByAndroidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().description(\"" + contentDesc + "\"));"
                )
        );
    }

    // ── Long press (2 seconds) ────────────────────────────────────────────────

    public void longPress(WebElement element) {
        Point     loc  = element.getLocation();
        Dimension size = element.getSize();
        int x = loc.getX() + size.width  / 2;
        int y = loc.getY() + size.height / 2;

        PointerInput finger    = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence     longPress = new Sequence(finger, 0);
        longPress.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        longPress.addAction(finger.createPointerMove(Duration.ofSeconds(2), PointerInput.Origin.viewport(), x, y));
        longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(longPress));
    }

    // ── Scroll drums (picker wheels) ──────────────────────────────────────────

    public void scrollPickerUp(WebElement picker, int times) {
        for (int i = 0; i < times; i++) {
            Point     loc  = picker.getLocation();
            Dimension size = picker.getSize();
            int x      = loc.getX() + size.width / 2;
            int startY = loc.getY() + (int) (size.height * 0.7);
            int endY   = loc.getY() + (int) (size.height * 0.3);
            performSwipe(x, startY, x, endY, 300);
        }
    }

    public void scrollPickerDown(WebElement picker, int times) {
        for (int i = 0; i < times; i++) {
            Point     loc  = picker.getLocation();
            Dimension size = picker.getSize();
            int x      = loc.getX() + size.width / 2;
            int startY = loc.getY() + (int) (size.height * 0.3);
            int endY   = loc.getY() + (int) (size.height * 0.7);
            performSwipe(x, startY, x, endY, 300);
        }
    }

    // ── Internal ──────────────────────────────────────────────────────────────

    private void performSwipe(int startX, int startY, int endX, int endY, int durationMs) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence     swipe  = new Sequence(finger, 0);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMs), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(swipe));
    }
}
