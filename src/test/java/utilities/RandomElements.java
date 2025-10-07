
package utilities;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;

public class RandomElements {
    private WaitForElement waitUtil;

    public RandomElements(AndroidDriver driver) {
        waitUtil = new WaitForElement(driver);
    }

    public void selectRandomOptionFromDropdown(List<WebElement> dropdownOptions) {
    waitUtil.waitForVisibilities(dropdownOptions);// Wait until dropdown options are visible
    Random rand = new Random();  // Generate a random index
    int randomIndex = rand.nextInt(dropdownOptions.size());
    WebElement randomOption = dropdownOptions.get(randomIndex);// Get the random option
    randomOption.click();// Click it
    System.out.println("Randomly selected option: " + randomOption.getAttribute("text"));
}
}
