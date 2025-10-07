package pageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import utilities.WaitForElement;

import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

     public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(accessibility = "Digitization Of Machine\nStay in control, on the go by monitor machines from anywhere, anytime on your mobile device.")
    private WebElement digitizationImage;

    @AndroidFindBy(accessibility = "World of Machines\nReceive timely notifications for abnormal readings, allowing you to address issues proactively.")
    private WebElement worldOfMachinesImage;

    @AndroidFindBy(accessibility = "Accelerating Growth\nData-driven insights from APMS.ai empower you to improve and grow within your manufacturing plant.")
    private WebElement acceleratingGrowthImage;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Next']")
    private WebElement nextButton;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc=\"Finish\"]")
    private WebElement finishButton;

    @AndroidFindBy(accessibility = "Login to your account")
    private WebElement loginToYourAccountText;

    @AndroidFindBy(accessibility = "1.0.0(57) | \nMade in India | Beta")
    private WebElement apmsAiFooter;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='EMAIL']")
    private WebElement emailField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='PASSWORD']")
    private WebElement passwordField;

    @AndroidFindBy(accessibility = "Login")
    private WebElement loginButton;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Show password']")
    private WebElement eyeIcon;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Hide password']")
    private WebElement eyeIconVisible;

    @AndroidFindBy(accessibility = "Active Machines\nUnit's Active Machine Status At A Glance.")
    private WebElement dashBoardElement;


     //🔹 Dynamic elements (match only by static part "All", "Running", etc.)
    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'All')]")
    private WebElement allMachine;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Log Off')]")
    private WebElement logOff;


    
    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Product Setup')]")
    private WebElement productSetup;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Running')]")
    private WebElement running;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Stopped')]")
    private WebElement stopped;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Waiting')]")
    private WebElement waiting;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Idle')]")
    private WebElement idle;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Maintenance')]")
    private WebElement maintenance;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Bypass')]")
    private WebElement bypass;


    // Methods to interact with elements

    public String getDigitizationImageContentDesc() {
         waitUtil = new WaitForElement(driver);
        waitUtil.waitForVisibility(digitizationImage);
        return digitizationImage.getAttribute("content-desc");
    }

    public boolean isDigitizationImageDisplayed() {
        waitUtil.waitForVisibility(digitizationImage);
        return digitizationImage.isDisplayed();
    }

    public boolean clickOnNextOnDigitizationPage() {
        waitUtil.waitForVisibility(digitizationImage);
        if (nextButton.isEnabled()) {
            waitUtil.waitForVisibility(nextButton);
            nextButton.click();
            return true;
        }
        return false;
    }

    public String getWorldOfMachinesImageContentDesc() {
        waitUtil.waitForVisibility(worldOfMachinesImage);
        return worldOfMachinesImage.getAttribute("content-desc");
    }

    public boolean verifyWorldOfMachinesAndClickNext() {
        waitUtil.waitForVisibility(worldOfMachinesImage);
        if (nextButton.isEnabled()) {
            waitUtil.waitForVisibility(nextButton);
            nextButton.click();
            return true;
        }
        return false;
    }

    public String getAcceleratingGrowthImageContentDesc() {
        waitUtil.waitForVisibility(acceleratingGrowthImage);
        return acceleratingGrowthImage.getAttribute("content-desc");
    }

    public boolean verifyAcceleratingGrowthAndClickFinish() {
        waitUtil.waitForVisibility(acceleratingGrowthImage);
        if (finishButton.isEnabled()) {
            waitUtil.waitForVisibility(finishButton);
            finishButton.click();
            return true;
        }
        return false;
    }

    public void clickOnNextButton() {
        waitUtil.waitForVisibility(nextButton);
        if (nextButton.isEnabled()) {
            nextButton.click();
        } else {
            System.out.println("Next button is not displayed");
        }
    }

    public void clickOnFinishButton() {
        waitUtil.waitForVisibility(finishButton);
        if (finishButton.isEnabled()) {
            finishButton.click();
        } else {
            System.out.println("Finish button is not displayed");
        }
    }

    public boolean isLoginToYourAccountVisible() {
        waitUtil.waitForVisibility(loginToYourAccountText);
        return loginToYourAccountText.isDisplayed();
    }

    public void doubleClickApmsAiFooterIfNeeded() {
        waitUtil.waitForVisibility(apmsAiFooter);
        String footerText = apmsAiFooter.getAttribute("content-desc");
        if (footerText.contains("Beta") || footerText.contains("Dev")) {
            apmsAiFooter.click();
            apmsAiFooter.click();
            waitForFooterToBeTest();
        }else{
            System.out.println("Footer is not in Beta or Dev mode currently footer is : " + footerText);
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
        waitUtil.waitForVisibility(emailField);
        emailField.click();
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        waitUtil.waitForVisibility(passwordField);
        passwordField.click();
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void enterCredentials(String email, String password) {
        enterEmail(email);
        enterPassword(password);
    }

    public void showPasswordAndVerifyVisible() {
        if (eyeIcon.isDisplayed()==true) {
            waitUtil.waitForVisibility(eyeIcon);
            eyeIcon.click();
            waitUtil.waitForVisibility(eyeIconVisible);
            if (eyeIconVisible.isDisplayed()) {
                System.out.println("Password is visible");
            } else {
                System.out.println("Password is not visible");
            }
        }
    }

    public boolean clickLogin() {
        waitUtil.waitForVisibility(loginButton);
        if (loginButton.isDisplayed()) {
            loginButton.click();
            return true;
        }
        return false;
    }

    public boolean isLoginSuccessful() {
        try {
            String getDashboard=dashBoardElement.getAttribute("content-desc");
            System.out.println("Dashboard Content Description: " + getDashboard);
            waitUtil.waitForVisibility(dashBoardElement);
            return dashBoardElement.isDisplayed();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private int extractCount(WebElement element, boolean isLastLine) {
        waitUtil.waitForVisibility(element);
        String value = element.getAttribute("content-desc");

        if (value == null || value.trim().isEmpty() || value.trim().equalsIgnoreCase("null")) {
            return 0;
        }

        String[] lines = value.split("\\n");
        String targetLine;

        if (isLastLine) {
            targetLine = lines[lines.length - 1].trim();  // For Running, Stopped, etc.
        } else {
            targetLine = lines[0].trim();  // For All Machines, Log Off
        }

        // Check if the target line is numeric
        if (targetLine.matches("\\d+")) {
            return Integer.parseInt(targetLine);
        } else {
            System.out.println("Failed to parse count: " + targetLine);
            return 0;
        }
    }


    public int getAllMachineCount() {
        return extractCount(allMachine, false);
    }

    public int getLogOffCount() {
        return extractCount(logOff, false);
    }
    public int getProductSetupCount() {
        return extractCount(productSetup, true);
    }

    public int getRunningCount() {
        return extractCount(running, true);
    }

    public int getStoppedCount() {
        return extractCount(stopped, true);
    }

    public int getWaitingCount() {
        return extractCount(waiting, true);
    }

    public int getIdleCount() {
        return extractCount(idle, true);
    }

    public int getMaintenanceCount() {
        return extractCount(maintenance, true);
    }

    // public int getBypassCount() {
    //     return extractCount(bypass, true);
    // }









    }
        
