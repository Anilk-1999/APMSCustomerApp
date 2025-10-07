package pageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utilities.ScrollUtils;
import utilities.WaitForElement;
import org.openqa.selenium.support.PageFactory;
import java.time.Duration;

public class BasePage {

    public ScrollUtils scroll=null;
    public AndroidDriver driver=null;
    public WaitForElement waitUtil=null;
    private static final int TIMEOUT = 15;

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.waitUtil = new WaitForElement(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(TIMEOUT)), this);
    }


    // protected void waitForVisibility(WebElement element) {
    //     new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
    //         .until(ExpectedConditions.visibilityOf(element));
    // }

    //  protected void waitForVisibilities(List<WebElement> elements) {
    //     new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
    //         .until(ExpectedConditions.visibilityOfAllElements(elements));
    // }
}
