package utilities;

import java.time.Duration;
import java.util.Collections;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import io.appium.java_client.android.AndroidDriver;

public class ScrollUtils {

    private AndroidDriver driver;

    public ScrollUtils(AndroidDriver driver) {
        this.driver = driver;
    }

    /**
     * Scroll until element with given content-desc is visible
     */
    // public WebElement scrollToElementByDesc(String contentDesc) {
    //     return driver.findElement(
    //         MobileBy.AndroidUIAutomator(
    //             "new UiScrollable(new UiSelector().scrollable(true))" +
    //             ".scrollIntoView(new UiSelector().description(\"" + contentDesc + "\"));"
    //         )
    //     );
    // }

    // /**
    //  * Scroll until element with given text is visible
    //  */
    // public WebElement scrollToElementByText(String text) {
    //     return driver.findElement(
    //         MobileBy.AndroidUIAutomator(
    //             "new UiScrollable(new UiSelector().scrollable(true))" +
    //             ".scrollIntoView(new UiSelector().text(\"" + text + "\"));"
    //         )
    //     );
    // }



    public void scrollUntilVisible(WebElement element) {
    int maxScrolls = 5;  // safety limit
    for (int i = 0; i < maxScrolls; i++) {
        try {
            if (element.isDisplayed()) {
                break;
            }
        } catch (Exception e) {
            // Swipe up using W3C Actions
            Dimension size = driver.manage().window().getSize();
            int startY = (int) (size.height * 0.8);
            int endY = (int) (size.height * 0.2);
            int startX = size.width / 2;

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(swipe));
        }
    }
}

public void scrollWithMouse(int startX, int startY, int endX, int endY) {
    PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "mouse");
    Sequence scroll = new Sequence(mouse, 1);

    // Move mouse to start position
    scroll.addAction(mouse.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
    // Press mouse button down (left-click hold)
    scroll.addAction(mouse.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
    // Drag to end position
    scroll.addAction(mouse.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, endY));
    // Release mouse button
    scroll.addAction(mouse.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

    driver.perform(Collections.singletonList(scroll));
}






public void scrollToBottom() {
    Dimension size = driver.manage().window().getSize();

    int startX = size.width / 2;
    int startY = (int) (size.height * 0.8); // near bottom
    int endY   = (int) (size.height * 0.2); // near top

    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
    Sequence swipe = new Sequence(finger, 1);

    swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
    swipe.addAction(finger.createPointerMove(Duration.ofMillis(800), PointerInput.Origin.viewport(), startX, endY));
    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

    driver.perform(Collections.singletonList(swipe));
}


}
