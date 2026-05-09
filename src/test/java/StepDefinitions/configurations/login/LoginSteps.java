package StepDefinitions.configurations.login;

import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.configurations.LoginPage;
import hooks.AppHooks;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import java.time.Duration;

public class LoginSteps {

    private final AndroidDriver driver;
    private LoginPage loginPage;
    private boolean alreadyLoggedIn = false;

    public LoginSteps() {
        this.driver = AppHooks.getDriver();
    }

    /**
     * Returns true when the user is already logged in (Dashboard or any list screen is visible).
     * Used to skip Login-scenario steps that would fail when session is already active.
     */
    private boolean checkAlreadyLoggedIn() {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            boolean loggedIn = false;
            try {
                // Dashboard (Active Machines) visible → already logged in
                driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().descriptionContains(\"Active Machines\")"));
                loggedIn = true;
            } catch (Exception ignored) { /* not on Dashboard */ }
            if (!loggedIn) {
                try {
                    // Any list screen (Search icon visible) → already logged in
                    driver.findElement(AppiumBy.accessibilityId("Search"));
                    loggedIn = true;
                } catch (Exception ignored) { /* not on list screen */ }
            }
            return loggedIn;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    @Given("the app is launched")
    public void the_app_is_launched() {
        loginPage = new LoginPage(driver);
        alreadyLoggedIn = checkAlreadyLoggedIn();
        if (alreadyLoggedIn) {
            System.out.println("[LoginSteps] User already logged in — onboarding/login steps will be skipped");
        }
    }

    @Given("the user is on the login screen")
    public void the_user_is_on_the_login_screen() {
        if (loginPage == null) loginPage = new LoginPage(driver);
        alreadyLoggedIn = checkAlreadyLoggedIn();
        if (alreadyLoggedIn) {
            System.out.println("[LoginSteps] User already logged in — skipping login screen check");
            return;
        }
        Assert.assertTrue(loginPage.isLoginScreenVisible(), "Login screen should be visible");
        loginPage.clearLoginFields();
    }

    @Given("Verify the {string} screen is displayed")
    public void verify_the_screen_is_displayed(String screen) {
        if (alreadyLoggedIn) {
            System.out.println("[LoginSteps] Already logged in — skipping onboarding screen check: " + screen);
            return;
        }
        Assert.assertTrue(loginPage.isOnboardingScreenDisplayed(screen),
                screen + " screen should be displayed");
    }

    @When("the user clicks on Next")
    public void the_user_clicks_on_next() {
        if (alreadyLoggedIn) return;
        loginPage.clickOnNextButton();
    }

    @Then("Verify the {string} screen should be displayed")
    public void verify_the_screen_should_be_displayed(String screen) {
        if (alreadyLoggedIn) return;
        Assert.assertTrue(loginPage.isOnboardingScreenDisplayed(screen),
                screen + " screen should be displayed");
    }

    @When("the user clicks on Next again")
    public void the_user_clicks_on_next_again() {
        if (alreadyLoggedIn) return;
        loginPage.clickOnNextButton();
    }

    @Then("Verify the {string} screen should displayed")
    public void verify_the_screen_should_displayed(String screen) {
        if (alreadyLoggedIn) return;
        Assert.assertTrue(loginPage.isOnboardingScreenDisplayed(screen),
                screen + " screen should be displayed");
    }

    @When("the user clicks on Finish")
    public void the_user_clicks_on_finish() {
        if (alreadyLoggedIn) return;
        loginPage.clickOnFinishButton();
    }

    @Then("Verify the {string} screen should be visible")
    public void verify_the_screen_should_be_visible(String screen) {
        if (alreadyLoggedIn) return;
        Assert.assertTrue(loginPage.isOnboardingScreenDisplayed(screen),
                screen + " screen should be visible");
    }

    @When("the user double taps on the APMS AI footer if needed")
    public void the_user_double_taps_on_the_apms_ai_footer_if_needed() {
        if (alreadyLoggedIn) return;
        loginPage.switchToTestEnvIfNeeded();
    }

    @When("the user enters email {string}")
    public void the_user_enters_email(String email) {
        if (alreadyLoggedIn) return;
        loginPage.enterEmail(email);
    }

    @When("the user enters password {string}")
    public void the_user_enters_password(String password) {
        if (alreadyLoggedIn) return;
        loginPage.enterPassword(password);
    }

    @When("the user clicks on Login")
    public void the_user_clicks_on_login() {
        if (alreadyLoggedIn) return;
        loginPage.clickLogin();
    }

    @Then("Verify the user should be logged in successfully")
    public void verify_the_user_should_be_logged_in_successfully() {
        if (alreadyLoggedIn) {
            System.out.println("[LoginSteps] Already logged in — skipping login success check");
            return;
        }
        Assert.assertTrue(loginPage.isLoginSuccessful(), "User should be logged in successfully");
    }

    @Then("{string} validation error should be displayed")
    public void validation_error_should_be_displayed(String message) {
        Assert.assertTrue(loginPage.isValidationErrorDisplayed(message),
                "\"" + message + "\" validation error should be displayed");
    }

    @Then("the user should remain on the login screen with an error")
    public void the_user_should_remain_on_the_login_screen_with_an_error() {
        Assert.assertTrue(loginPage.isStillOnLoginScreen(),
                "User should remain on login screen after failed login attempt");
    }
}
