package pageObject;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

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
        waitForVisibility(machineName);
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
        waitForVisibility(location);
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
        waitForVisibility(machineCode);
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
        waitForVisibility(machineBrandDropdown);
        if (machineBrandDropdown.isEnabled()) {
            machineBrandDropdown.click();
            usersPage.selectOptionInDropdown(mcBrand);
        }
    }

    public void selectMachineType(String mcType) {
        waitForVisibility(machineTypeDropdown);
        if (machineTypeDropdown.isEnabled()) {
            String isChecked = machineTypeDropdown.getAttribute("checked");
            if (isChecked == null || isChecked.equalsIgnoreCase("false")) {
                machineTypeDropdown.click();
            } else {
                System.out.println("toggle already enabled.");
            }
        }
        usersPage.selectOptionInDropdown(mcType);
    }

    public void selectIoTDeviceType(String iotDeviceType) {
        waitForVisibility(iotDeviceTypeDropdown);
        if (iotDeviceTypeDropdown.isEnabled()) {
            iotDeviceTypeDropdown.click();
            usersPage.selectOptionInDropdown(iotDeviceType);
        }
    }

    public void clickAddShift() {
        scrollUntilVisible(addShift);
        waitForVisibility(addShift);
        if (addShift.isEnabled()) {
            addShift.click();
        }
    }

    public void toggleEnableAsProductionMachine() {
        scrollUntilVisible(enableProductionMachineToggle);
        waitForVisibility(enableProductionMachineToggle);
        if (enableProductionMachineToggle.isEnabled()) {
            enableProductionMachineToggle.click();
        }
    }

    public void toggleEnableProductionPlan() {
        scrollUntilVisible(enableProductionPlanToggle);
        waitForVisibility(enableProductionPlanToggle);
        enableProductionPlanToggle.click();
    }

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

    public void setEnableMaintenance(boolean shouldEnable) {
        setToggle(toggleEnableMaintenance, shouldEnable);
    }

    public void setEnableAuthorizationFeature(boolean shouldEnable) {
        setToggle(toggleEnableAuthorizationFeature, shouldEnable);
    }

    public void setDowntimeDays(String days) {
        inputDowntimeDays.clear();
        inputDowntimeDays.sendKeys(days);
    }

    public void setTimeoutDuration(String duration) {
        inputTimeoutDuration.clear();
        inputTimeoutDuration.sendKeys(duration);
    }

    public void setIdleTimeoutDuration(String duration) {
        inputIdleTimeoutDuration.clear();
        inputIdleTimeoutDuration.sendKeys(duration);
    }

    public void selectHmiLoginType(String loginType) {
        dropdownHmiLoginType.click();
        usersPage.selectOptionInDropdown(loginType);
    }
}
