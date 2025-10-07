package StepDefinitions;

import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.LoginPage;
import hooks.AppHooks;
import io.appium.java_client.android.AndroidDriver;

public class LoginTest {

    private AndroidDriver driver;
    private LoginPage loginPage;
    AppHooks hooks; 

    public LoginTest(AppHooks hooks) {
    this.driver = hooks.getDriver();
}


    @Given("the app is launched")
    public void the_app_is_launched() {
        this.loginPage = new LoginPage(driver);
        System.out.println("✅ App launched, driver is ready");
    }

    @Given("Verify the {string} screen is displayed")
    public void verify_the_screen_is_displayed(String string) {
        Assert.assertEquals(loginPage.getDigitizationImageContentDesc(),
                "Digitization Of Machine\nStay in control, on the go by monitor machines from anywhere, anytime on your mobile device.");
    }

    @When("the user clicks on Next")
    public void the_user_clicks_on_next() {
        loginPage.clickOnNextButton();
    }

    @Then("Verify the {string} screen should be displayed")
    public void verify_the_screen_should_be_displayed(String string) {
        Assert.assertEquals(loginPage.getWorldOfMachinesImageContentDesc(),
                "World of Machines\nReceive timely notifications for abnormal readings, allowing you to address issues proactively.");
    }

    @When("the user clicks on Next again")
    public void the_user_clicks_on_next_again() {
        loginPage.clickOnNextButton();
    }

    @Then("Verify the {string} screen should displayed")
    public void verify_the_screen_should_displayed(String string) {
        Assert.assertEquals(loginPage.getAcceleratingGrowthImageContentDesc(),
                "Accelerating Growth\nData-driven insights from APMS.ai empower you to improve and grow within your manufacturing plant.");
    }

    @When("the user clicks on Finish")
    public void the_user_clicks_on_finish() {
        loginPage.clickOnFinishButton();
    }

    @Then("Verify the {string} screen should be visible")
    public void verify_the_screen_should_be_visible(String string) {
        Assert.assertTrue(loginPage.isLoginToYourAccountVisible());
    }

    @When("the user double taps on the APMS AI footer if needed")
    public void the_user_double_taps_on_the_apms_ai_footer_if_needed() {
        loginPage.doubleClickApmsAiFooterIfNeeded();
    }

    @When("the user enters email {string}")
    public void the_user_enters_email(String string) {
        loginPage.enterEmail(string);
    }

    @When("the user enters password {string}")
    public void the_user_enters_password(String string) {
        loginPage.enterPassword(string);
    }

    @When("the user clicks on Login")
    public void the_user_clicks_on_login() {
        loginPage.clickLogin();
    }

    @Then("Verify the user should be logged in successfully")
    public void verify_the_user_should_be_logged_in_successfully() {
        Assert.assertTrue(loginPage.isLoginSuccessful());

    System.out.println("All Machines: " + loginPage.getAllMachineCount());
    System.out.println("All logged off machines: " + loginPage.getLogOffCount());


    System.out.println("Running: " + loginPage.getRunningCount());
    System.out.println("Stopped: " + loginPage.getStoppedCount());
    System.out.println("Waiting: " + loginPage.getWaitingCount());
    System.out.println("Idle: " + loginPage.getIdleCount());
    System.out.println("Maintenance: " + loginPage.getMaintenanceCount());
   //  System.out.println("Bypass: " + loginPage.getBypassCount());
    
    }





}
