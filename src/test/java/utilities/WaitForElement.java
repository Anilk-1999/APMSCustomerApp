package utilities;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

public class WaitForElement {

    public AndroidDriver driver;
    private static final int TIMEOUT = 15;
    public WaitForElement(AndroidDriver driver) {
        this.driver = driver;
    }

     public void waitForVisibility(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
            .until(ExpectedConditions.visibilityOf(element));
    }

     public void waitForVisibilities(List<WebElement> elements) {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
            .until(ExpectedConditions.visibilityOfAllElements(elements));
    }
}
