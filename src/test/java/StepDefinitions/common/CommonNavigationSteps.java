package StepDefinitions.common;

import hooks.AppHooks;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObject.UsersPage;

import java.time.Duration;

/**
 * Reusable Background navigation steps shared across ALL configuration modules.
 *
 * If the driver is already on a list screen (Search icon visible), every Background
 * step is skipped — no need to navigate back to Dashboard and re-enter.
 */
public class CommonNavigationSteps {

    private final AndroidDriver driver;
    private UsersPage           usersPage;

    @SuppressWarnings("unused")
    public CommonNavigationSteps(AppHooks hooks) {
        this.driver = AppHooks.getDriver();
    }

    /**
     * Returns true if on a module list screen (Search icon present).
     * Polls for up to 2 s with zero implicit wait — the short window lets Flutter
     * finish its post-popup transition animation before we conclude we are NOT on a
     * list screen (which would trigger a full navigation from Dashboard, and would
     * click button.instance(0) on the Activities list that is the back arrow, not
     * the profile icon).
     */
    private boolean alreadyOnListScreen() {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            driver.findElement(AppiumBy.accessibilityId("Search"));
            System.out.println("[CommonNav] Already on list screen — skipping Background navigation");
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        }
    }

    // ── Profile Icon ──────────────────────────────────────────────────────────

    @When("User clicks on profile icon")
    public void userClicksOnProfileIcon() {
        if (alreadyOnListScreen()) return;
        usersPage = new UsersPage(driver);
        usersPage.clickOnProfileIcon();
    }

    // ── Account Preferences header ────────────────────────────────────────────

    @Then("verify that the {string} screen is displayed")
    public void verifyScreenIsDisplayed(String expectedHeader) {
        if (alreadyOnListScreen()) return;
        Assert.assertEquals(usersPage.getAccountPreferenceHeader(), expectedHeader,
                "Screen header mismatch");
    }

    // ── Section header click ──────────────────────────────────────────────────

    @When("User clicks on {string} section")
    public void userClicksOnSection(String sectionName) {
        if (alreadyOnListScreen()) return;
        usersPage.clickOnSectionHeader(sectionName);
    }

    // ── Section header verification ───────────────────────────────────────────

    @Then("verify the {string} section is displayed")
    public void verifySectionIsDisplayed(String sectionName) {
        if (alreadyOnListScreen()) return;
        Assert.assertEquals(usersPage.getSectionHeaderText(sectionName), sectionName,
                "Section header mismatch");
    }

    // ── Feature click ─────────────────────────────────────────────────────────

    @When("User clicks on {string} feature")
    public void userClicksOnFeature(String featureName) {
        if (alreadyOnListScreen()) return;
        usersPage.clickOnFeature(featureName);
    }

    // ── List screen navigation verification ───────────────────────────────────

    @Then("verify user navigates to {string} list screen")
    public void verifyUserNavigatesToListScreen(String expectedTitle) {
        if (alreadyOnListScreen()) return;
        Assert.assertEquals(usersPage.getListPageTitle(), expectedTitle,
                "List screen title mismatch");
    }
}
