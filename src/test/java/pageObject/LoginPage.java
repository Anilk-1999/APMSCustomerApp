package pageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

     public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(accessibility = "Digitization Of Machine Stay in control, on the go by monitor machines from anywhere, anytime on your mobile device.")
    private WebElement digitizationImage;

    @AndroidFindBy(accessibility = "World of Machines Receive timely notifications for abnormal readings, allowing you to address issues proactively.")
    private WebElement worldOfMachinesImage;

    @AndroidFindBy(accessibility = "Accelerating Growth Data-driven insights from APMS.ai empower you to improve and grow within your manufacturing plant.")
    private WebElement acceleratingGrowthImage;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Next']")
    private WebElement nextButton;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Finish']")
    private WebElement finishButton;

    @AndroidFindBy(accessibility = "Login to your account")
    private WebElement loginToYourAccountText;

    @AndroidFindBy(accessibility = "APMS.ai 1.0.0(51) | Made in India | Beta")
    private WebElement apmsAiFooter;

    @AndroidFindBy(accessibility = "EMAIL")
    private WebElement emailField;

    @AndroidFindBy(accessibility = "PASSWORD")
    private WebElement passwordField;

    @AndroidFindBy(accessibility = "Login")
    private WebElement loginButton;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Show password']")
    private WebElement eyeIcon;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Hide password']")
    private WebElement eyeIconVisible;

    public String getDigitizationImageContentDesc() {
        waitForVisibility(digitizationImage);
        return digitizationImage.getAttribute("content-desc");
    }

    public boolean isDigitizationImageDisplayed() {
        waitForVisibility(digitizationImage);
        return digitizationImage.isDisplayed();
    }

    public boolean clickOnNextOnDigitizationPage() {
        waitForVisibility(digitizationImage);
        if (nextButton.isEnabled()) {
            waitForVisibility(nextButton);
            nextButton.click();
            return true;
        }
        return false;
    }

    public String getWorldOfMachinesImageContentDesc() {
        waitForVisibility(worldOfMachinesImage);
        return worldOfMachinesImage.getAttribute("content-desc");
    }

    public boolean verifyWorldOfMachinesAndClickNext() {
        waitForVisibility(worldOfMachinesImage);
        if (nextButton.isEnabled()) {
            waitForVisibility(nextButton);
            nextButton.click();
            return true;
        }
        return false;
    }

    public String getAcceleratingGrowthImageContentDesc() {
        waitForVisibility(acceleratingGrowthImage);
        return acceleratingGrowthImage.getAttribute("content-desc");
    }

    public boolean verifyAcceleratingGrowthAndClickFinish() {
        waitForVisibility(acceleratingGrowthImage);
        if (finishButton.isEnabled()) {
            waitForVisibility(finishButton);
            finishButton.click();
            return true;
        }
        return false;
    }

    public void clickOnNextButton() {
        waitForVisibility(nextButton);
        if (nextButton.isDisplayed()) {
            nextButton.click();
        } else {
            System.out.println("Next button is not displayed");
        }
    }

    public void clickOnFinishButton() {
        waitForVisibility(finishButton);
        if (finishButton.isDisplayed()) {
            finishButton.click();
        } else {
            System.out.println("Finish button is not displayed");
        }
    }

    public boolean isLoginToYourAccountVisible() {
        waitForVisibility(loginToYourAccountText);
        return loginToYourAccountText.isDisplayed();
    }

    public void doubleClickApmsAiFooterIfNeeded() {
        waitForVisibility(apmsAiFooter);
        String footerText = apmsAiFooter.getAttribute("content-desc");
        if (footerText.contains("Beta") || footerText.contains("Dev")) {
            apmsAiFooter.click();
            apmsAiFooter.click();
            waitForFooterToBeTest();
        }
    }

    private void waitForFooterToBeTest() {
        for (int i = 0; i < 5; i++) {
            String footerText = apmsAiFooter.getAttribute("content-desc");
            if (footerText.contains("Test")) {
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void enterEmail(String email) {
        waitForVisibility(emailField);
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        waitForVisibility(passwordField);
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void enterCredentials(String email, String password) {
        enterEmail(email);
        enterPassword(password);
    }

    public boolean showPasswordAndVerifyVisible() {
        waitForVisibility(eyeIcon);
        eyeIcon.click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String isPasswordVisible = passwordField.getAttribute("password");
        return "false".equals(isPasswordVisible);
    }

    public boolean clickLogin() {
        waitForVisibility(loginButton);
        if (loginButton.isDisplayed()) {
            loginButton.click();
            return true;
        }
        return false;
    }

    public boolean isLoginSuccessful() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isLoginSuccessful'");
    }

    // public boolean isLoginSuccessful() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'isLoginSuccessful'");
    // }

// public boolean isLoginSuccessful() {
//     try {
//         WebElement dashboard = driver.findElement(By.accessibilityId("Dashboard")); 
//         waitForVisibility(dashboard);
//         return dashboard.isDisplayed();
//     } catch (Exception e) {
//         return false;
//     }
// }

}
