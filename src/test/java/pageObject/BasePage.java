package pageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {

    public AndroidDriver driver;
    private static final int TIMEOUT = 15;

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(TIMEOUT)), this);
    }


    protected void waitForVisibility(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
            .until(ExpectedConditions.visibilityOf(element));
    }
}
