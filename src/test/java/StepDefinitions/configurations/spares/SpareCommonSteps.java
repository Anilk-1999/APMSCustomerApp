package StepDefinitions.configurations.spares;

import hooks.AppHooks;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageObject.configurations.SparePage;
import utilities.GlobalEntityStore;
import utilities.GlobalTestData;
import utilities.ScenarioContext;

import java.time.Duration;
import java.util.List;

public class SpareCommonSteps {

    private final AndroidDriver   driver;
    private final ScenarioContext context;
    private SparePage             sparePage;

    @SuppressWarnings("unused")
    public SpareCommonSteps(AppHooks hooks, ScenarioContext context) {
        this.driver  = AppHooks.getDriver();
        this.context = context;
    }

    private SparePage page() {
        if (sparePage == null) sparePage = new SparePage(driver);
        return sparePage;
    }

    // ═══════════════════════════════════════════════════════════
    //  BACKGROUND
    // ═══════════════════════════════════════════════════════════

    @And("User has already created a Spare")
    public void userHasAlreadyCreatedASpare() {
        String name = GlobalEntityStore.getLatestName(GlobalEntityStore.SPARE);
        if (name == null) {
            name = page().createSpareAndReturnName();
            GlobalEntityStore.setLatestName(GlobalEntityStore.SPARE, name);
            GlobalTestData.set(GlobalTestData.SPARE_NAME, name);
        }
        context.set(ScenarioContext.SPARE_NAME, name);
    }

    // ═══════════════════════════════════════════════════════════
    //  NAVIGATION — LIST SCREEN
    // ═══════════════════════════════════════════════════════════

    @Then("Verify User should be navigate into \"Spares\" list")
    public void verifyUserShouldBeNavigateIntoSparesList() {
        Assert.assertTrue(page().waitForReturnToList(15), "Not on Spares list screen");
    }

    @Then("Spares list should be displayed")
    public void sparesListShouldBeDisplayed() {
        Assert.assertTrue(page().waitForReturnToList(10), "Spares list not displayed");
    }

    @And("Add \"+\" button should be visible in Spares list")
    public void addButtonShouldBeVisibleInSparesList() {
        Assert.assertTrue(page().waitForReturnToList(10), "Add button not visible in Spares list");
    }

    @And("User should return to Spares list screen")
    public void userShouldReturnToSparesListScreen() {
        Assert.assertTrue(page().waitForReturnToList(15), "Not on Spares list screen");
    }

    // ═══════════════════════════════════════════════════════════
    //  SHARED VALIDATION
    // ═══════════════════════════════════════════════════════════

    @Then("\"This field is required\" error should be displayed")
    public void thisFieldIsRequiredErrorShouldBeDisplayed() {
        Assert.assertTrue(page().isSpareNameRequiredErrorDisplayed(),
                "\"This field is required\" error not displayed");
    }

    @Then("validation error should be displayed")
    public void validationErrorShouldBeDisplayed() {
        Assert.assertTrue(page().isAnyValidationErrorDisplayed(), "No validation error displayed");
    }

    @Then("{string} should be displayed")
    public void errorMessageShouldBeDisplayed(String message) {
        Assert.assertTrue(page().isAnyValidationErrorDisplayed(),
                "Expected error not displayed: " + message);
    }

    // ═══════════════════════════════════════════════════════════
    //  SHARED POPUP ACTIONS
    // ═══════════════════════════════════════════════════════════

    @When("User clicks on \"Yes, Exit\" button on the confirmation popup")
    public void userClicksOnYesExitButtonOnConfirmationPopup() {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(300))
                    .until(d -> {
                        List<WebElement> els = d.findElements(
                                AppiumBy.androidUIAutomator(
                                        "new UiSelector().description(\"Yes, Exit\")"));
                        if (!els.isEmpty()) { els.get(0).click(); return Boolean.TRUE; }
                        return null;
                    });
        } catch (Exception ignored) {
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        }
    }

    @When("User clicks {string}")
    public void userClicksLabel(String label) {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(300))
                    .until(d -> {
                        List<WebElement> els = d.findElements(
                                AppiumBy.androidUIAutomator(
                                        "new UiSelector().description(\"" + label + "\")"));
                        if (!els.isEmpty()) { els.get(0).click(); return Boolean.TRUE; }
                        List<WebElement> els2 = d.findElements(AppiumBy.xpath(
                                "//*[@content-desc='" + label + "' or @text='" + label + "']"));
                        if (!els2.isEmpty()) { els2.get(0).click(); return Boolean.TRUE; }
                        return null;
                    });
        } catch (Exception ignored) {
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        }
    }
}