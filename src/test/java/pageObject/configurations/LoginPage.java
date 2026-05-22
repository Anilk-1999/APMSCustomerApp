package pageObject.configurations;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.BasePage;

import java.time.Duration;

public class LoginPage extends BasePage {

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    // ── Onboarding elements ───────────────────────────────────────────────────

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

    // ── Login elements ────────────────────────────────────────────────────────

    @AndroidFindBy(accessibility = "Login to your account")
    private WebElement loginToYourAccountText;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Made in India')]")
    private WebElement apmsAiFooter;

    // Use instance(0/1) so the locator works whether the field is empty (hint visible)
    // or already filled (hint hidden by Flutter/UIAutomator2) — hint-based XPath breaks
    // when the field contains text from a previous run.
    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(0)")
    private WebElement emailField;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(1)")
    private WebElement passwordField;

    @AndroidFindBy(accessibility = "Login")
    private WebElement loginButton;

    @AndroidFindBy(xpath = "//*[contains(@text,'Email is required') or contains(@content-desc,'Email is required')]")
    private WebElement emailRequiredError;

    @AndroidFindBy(xpath = "//*[contains(@text,'Password is required') or contains(@content-desc,'Password is required')]")
    private WebElement passwordRequiredError;

    @AndroidFindBy(xpath = "//*[contains(@text,'valid Email') or contains(@content-desc,'valid Email')]")
    private WebElement invalidEmailFormatError;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Information Alert') or contains(@text,'Information Alert')]")
    private WebElement informationAlertDialog;

    @AndroidFindBy(xpath = "//*[@content-desc='OK' or @text='OK']")
    private WebElement alertOkButton;

    // ── Dashboard elements ────────────────────────────────────────────────────

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Active Machines')]")
    private WebElement dashBoardElement;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'All')]")
    private WebElement allMachine;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Log Off')]")
    private WebElement logOff;

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

    // ── Onboarding ────────────────────────────────────────────────────────────

    public boolean isOnboardingScreenDisplayed(String screenName) {
        return switch (screenName) {
            case "Digitization" -> isDisplayed(digitizationImage);
            case "World of Machines" -> isDisplayed(worldOfMachinesImage);
            case "Accelerating Growth" -> isDisplayed(acceleratingGrowthImage);
            case "Login to Your Account" -> isDisplayed(loginToYourAccountText);
            default -> false;
        };
    }

    public void clickOnNextButton() {
        waitForVisibility(nextButton);
        tap(nextButton);
    }

    public void clickOnFinishButton() {
        waitForVisibility(finishButton);
        tap(finishButton);
    }

    // ── Login ─────────────────────────────────────────────────────────────────

    public boolean isLoginScreenVisible() {
        try {
            waitForVisibility(loginToYourAccountText);
            return isDisplayed(loginToYourAccountText);
        } catch (Exception e) {
            return false;
        }
    }

    public void clearLoginFields() {
        if (isLoginScreenVisible()) {
            // Already on the login screen — clear any pre-filled credentials left by a
            // previous test run, then ensure we are on the Test environment.
            try {
                waitForClickability(emailField);
                clearField(emailField);
                clearField(passwordField);
            } catch (Exception ignored) { /* fields already empty — safe to continue */ }
            switchToTestEnvIfNeeded();
            return;
        }

        // Not on login screen (e.g. on dashboard after a successful login).
        // Restart the app — the only reliable way to reset Flutter TextEditingController state.
        driver.terminateApp("com.apms.ai");
        driver.activateApp("com.apms.ai");

        // After restart the app may show onboarding (flag not persisted across process kill).
        if (isDisplayed(digitizationImage)) {
            clickOnNextButton();
            clickOnNextButton();
            clickOnFinishButton();
        }

        waitForVisibility(loginToYourAccountText, 30);
        waitForClickability(emailField);
        switchToTestEnvIfNeeded();
    }

    public boolean isValidationErrorDisplayed(String message) {
        WebElement target = switch (message) {
            case "Email is required"    -> emailRequiredError;
            case "Password is required" -> passwordRequiredError;
            case "Enter valid email"    -> invalidEmailFormatError;
            default -> null;
        };
        if (target == null) return false;
        try {
            waitForVisibility(target);
            return isDisplayed(target);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isStillOnLoginScreen() {
        try {
            // Wait for the "Information Alert – Invalid username or password" popup.
            // Its presence confirms the server rejected the login attempt.
            waitForVisibility(informationAlertDialog, 20);
            tap(alertOkButton);
            // After dismissing, login screen must still be visible.
            waitForVisibility(loginToYourAccountText, 10);
            return isDisplayed(loginToYourAccountText);
        } catch (Exception e) {
            return false;
        }
    }

    public void switchToTestEnvIfNeeded() {
        waitForVisibility(apmsAiFooter);
        for (int i = 0; i < 10; i++) {
            if (apmsAiFooter.getAttribute("content-desc").contains("Test")) return;
            apmsAiFooter.click();
            // Wait up to 800ms for footer to switch to Test env; if not, keep clicking
            try {
                new WebDriverWait(driver, Duration.ofMillis(800))
                        .pollingEvery(Duration.ofMillis(200))
                        .until(d -> {
                            try { return apmsAiFooter.getAttribute("content-desc").contains("Test"); }
                            catch (Exception e) { return false; }
                        });
                return; // switched to Test env successfully
            } catch (Exception ignored) {
                // Not on Test env yet — continue clicking
            }
        }
    }

    public void enterEmail(String email) {
        // clearAndType's clear() poll can time out on cold Flutter start because the
        // text field is in the accessibility tree but not yet interactable. Use
        // UIAutomator instance(0) (first EditText = email) with a direct click + sendKeys
        // fallback so it works on both cold and warm app starts.
        try {
            org.openqa.selenium.By emailUIA = io.appium.java_client.AppiumBy.androidUIAutomator(
                    "new UiSelector().className(\"android.widget.EditText\").instance(0)");
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15))
                    .pollingEvery(java.time.Duration.ofMillis(300))
                    .until(d -> !d.findElements(emailUIA).isEmpty());
            org.openqa.selenium.WebElement f = driver.findElement(emailUIA);
            f.click();
            try { f.clear(); } catch (Exception ignored) { }
            f.sendKeys(email);
        } catch (Exception e) {
            clearAndType(emailField, email);
        }
    }

    public void enterPassword(String password) {
        // Same cold-start robustness for password field (second EditText on login screen).
        try {
            org.openqa.selenium.By pwdUIA = io.appium.java_client.AppiumBy.androidUIAutomator(
                    "new UiSelector().className(\"android.widget.EditText\").instance(1)");
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15))
                    .pollingEvery(java.time.Duration.ofMillis(300))
                    .until(d -> !d.findElements(pwdUIA).isEmpty());
            org.openqa.selenium.WebElement f = driver.findElement(pwdUIA);
            f.click();
            try { f.clear(); } catch (Exception ignored) { }
            f.sendKeys(password);
        } catch (Exception e) {
            clearAndType(passwordField, password);
        }
    }

    public void clickLogin() {
        hideKeyboard();
        waitForVisibility(loginButton);
        tap(loginButton);
    }

    // ── Dashboard ─────────────────────────────────────────────────────────────

    public boolean isLoginSuccessful() {
        try {
            // PageFactory proxy has a 5s internal timeout per poll, so using it inside
            // WebDriverWait effectively limits each attempt to 5s, not the full timeout.
            // Use direct findElements (zero implicit wait) for a true 45s polling wait.
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(45))
                    .pollingEvery(java.time.Duration.ofMillis(500))
                    .until(d -> !d.findElements(io.appium.java_client.AppiumBy.androidUIAutomator(
                            "new UiSelector().descriptionContains(\"Active Machines\")")).isEmpty());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int extractCount(WebElement element, boolean isLastLine) {
        waitForVisibility(element);
        String value = element.getAttribute("content-desc");
        if (value == null || value.trim().isEmpty() || value.trim().equalsIgnoreCase("null")) {
            return 0;
        }
        String[] lines = value.split("\\n");
        String targetLine = isLastLine ? lines[lines.length - 1].trim() : lines[0].trim();
        if (targetLine.matches("\\d+")) {
            return Integer.parseInt(targetLine);
        }
        return 0;
    }

    public int getAllMachineCount()  { return extractCount(allMachine, false); }
    public int getLogOffCount()      { return extractCount(logOff, false); }
    public int getRunningCount()     { return extractCount(running, true); }
    public int getStoppedCount()     { return extractCount(stopped, true); }
    public int getWaitingCount()     { return extractCount(waiting, true); }
    public int getIdleCount()        { return extractCount(idle, true); }
    public int getMaintenanceCount() { return extractCount(maintenance, true); }
}
