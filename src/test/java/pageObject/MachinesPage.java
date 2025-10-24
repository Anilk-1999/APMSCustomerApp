package pageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utilities.ScrollUtils;
import utilities.WaitForElement;

public class MachinesPage extends BasePage {


    private UsersPage usersPage;

    public MachinesPage(AndroidDriver driver) {
        super(driver);
        usersPage = new UsersPage(driver);
    }

    // ---------- Locators ----------
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Machine Name *']")
    private WebElement machineName;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Location']")
    private WebElement location;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Machine Code']")
    private WebElement machineCode;

    @AndroidFindBy(accessibility = "Machine Brand *")
    private WebElement machineBrandDropdown;

    @AndroidFindBy(accessibility = "Machine Type *")
    private WebElement machineTypeDropdown;

    @AndroidFindBy(accessibility = "IoT Device Type *")
    private WebElement iotDeviceTypeDropdown;

    @AndroidFindBy(accessibility = "Add Shift")
    private WebElement addShift;

    @AndroidFindBy(accessibility = "Enable As Production Machine\nProduction features are available when the machine is set as operational.")
    private WebElement enableProductionMachineToggle;

    @AndroidFindBy(accessibility = "Enable Production Plan\nWhen enabled, the plan is accessible, else only product selection is available on the HMI device.")
    private WebElement enableProductionPlanToggle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Enable Maintenance']/following-sibling::android.widget.Switch")
    private WebElement toggleEnableMaintenance;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Enable Authorization Feature']/following-sibling::android.widget.Switch")
    private WebElement toggleEnableAuthorizationFeature;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Downtime (Days)']/following-sibling::android.widget.EditText")
    private WebElement inputDowntimeDays;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Timeout Duration']/following-sibling::android.widget.EditText")
    private WebElement inputTimeoutDuration;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Idle Timeout Duration']/following-sibling::android.widget.EditText")
    private WebElement inputIdleTimeoutDuration;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='HMI Login Type']/following-sibling::android.widget.Spinner")
    private WebElement dropdownHmiLoginType;


    // ---------- Actions ----------
    public void enterMachineName(String name) {
        waitUtil.waitForVisibility(machineName);
        if (machineName.isEnabled()) {
            machineName.click();
        if (((AndroidDriver) driver).isKeyboardShown()) {
           ((AndroidDriver) driver).hideKeyboard();
        }
        machineName.clear();
        machineName.sendKeys(name);
     }
    }

    public void enterLocation(String loc) {
        waitUtil.waitForVisibility(location);
        if (location.isEnabled()) {
            location.click();
        if (((AndroidDriver) driver).isKeyboardShown()) {
           ((AndroidDriver) driver).hideKeyboard();
        }
        location.clear();
        location.sendKeys(loc);
      }
    }

    public void enterMachineCode(String code) {
        waitUtil.waitForVisibility(machineCode);
        if (machineCode.isEnabled()) {
            machineCode.click();
        if (((AndroidDriver) driver).isKeyboardShown()) {
           ((AndroidDriver) driver).hideKeyboard();
        }
        machineCode.clear();
        machineCode.sendKeys(code);
      }
    }

    public void selectMachineBrand(String mcBrand) {
        waitUtil.waitForVisibility(machineBrandDropdown);
        if (machineBrandDropdown.isEnabled()) {
            machineBrandDropdown.click();
            usersPage.selectOptionInDropdown(mcBrand);
        }
    }

    public void selectMachineType(String mcType) {
        waitUtil.waitForVisibility(machineTypeDropdown);
        if (machineTypeDropdown.isEnabled()) {
            String isChecked = machineTypeDropdown.getAttribute("checked");
        if (isChecked == null || isChecked.equalsIgnoreCase("false")) {
            machineTypeDropdown.click();
        } else {
            System.out.println("ℹ️ " + " toggle already enabled.");
        }
    }
            usersPage.selectOptionInDropdown(mcType);
    }

    public void selectIoTDeviceType(String iotDeviceType) {
        waitUtil.waitForVisibility(iotDeviceTypeDropdown);
        if (iotDeviceTypeDropdown.isEnabled()) {
            iotDeviceTypeDropdown.click();
            usersPage.selectOptionInDropdown(iotDeviceType);
        }
    }

    public void clickAddShift() {
        scroll.scrollUntilVisible(addShift);
        waitUtil.waitForVisibility(addShift);
        if (addShift.isEnabled()) {
            addShift.click();
        }
    }

    public void toggleEnableAsProductionMachine() {
        scroll.scrollUntilVisible(enableProductionMachineToggle);
        waitUtil.waitForVisibility(enableProductionMachineToggle);
        if (enableProductionMachineToggle.isEnabled()) {
            enableProductionMachineToggle.click();
        }
    }

    public void toggleEnableProductionPlan() {
        scroll.scrollUntilVisible(enableProductionPlanToggle);
        waitUtil.waitForVisibility(enableProductionPlanToggle);
        enableProductionPlanToggle.click();
    }

    /** Generic method to handle toggle */
    public void setToggle(WebElement toggleElement, boolean shouldEnable) {
        String status = toggleElement.getAttribute("checked");
        boolean isEnabled = Boolean.parseBoolean(status);

        if (isEnabled != shouldEnable) {
            toggleElement.click();
            System.out.println("Toggle changed to: " + shouldEnable);
        } else {
            System.out.println("Toggle already in desired state: " + shouldEnable);
        }
    }

    /** Set Enable Maintenance */
    public void setEnableMaintenance(boolean shouldEnable) {
        setToggle(toggleEnableMaintenance, shouldEnable);
    }

    /** Set Enable Authorization Feature */
    public void setEnableAuthorizationFeature(boolean shouldEnable) {
        setToggle(toggleEnableAuthorizationFeature, shouldEnable);
    }

    /** Set Downtime Days */
    public void setDowntimeDays(String days) {
        inputDowntimeDays.clear();
        inputDowntimeDays.sendKeys(days);
    }

    /** Set Timeout Duration */
    public void setTimeoutDuration(String duration) {
        inputTimeoutDuration.clear();
        inputTimeoutDuration.sendKeys(duration);
    }

    /** Set Idle Timeout Duration */
    public void setIdleTimeoutDuration(String duration) {
        inputIdleTimeoutDuration.clear();
        inputIdleTimeoutDuration.sendKeys(duration);
    }

    /** Select HMI Login Type (example: “No Login”, “PIN”, “Password”) */
    public void selectHmiLoginType(String loginType) {
        dropdownHmiLoginType.click();
        usersPage.selectOptionInDropdown(loginType);
    }

}
