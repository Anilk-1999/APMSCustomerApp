package StepDefinitions.common;

import hooks.AppHooks;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObject.UsersPage;
import utilities.SearchUtils;

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
     * Returns true if already on a module list screen (normal mode OR search mode).
     * Polls up to 1 s (300 ms intervals).
     * Implicit wait stays at ZERO — polling via WebDriverWait is the correct pattern.
     *
     * Detects three states:
     *   1. Normal:      Search button OR FAB visible
     *   2. Search mode: Filter/Sort visible (keyboard down) OR EditText + no popup (keyboard up)
     */
    private boolean alreadyOnListScreen() {
        if (driver == null) return false;
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, Duration.ofSeconds(1))
                    .pollingEvery(Duration.ofMillis(300))
                    .ignoring(Exception.class)
                    .until(d -> {
                        if (!d.findElements(AppiumBy.accessibilityId("Search")).isEmpty()) return true;
                        if (!d.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().description(\"+ Add\")")).isEmpty()) return true;
                        if (!d.findElements(AppiumBy.accessibilityId("Filter")).isEmpty()) return true;
                        if (!d.findElements(AppiumBy.accessibilityId("Sort")).isEmpty()) return true;
                        // Keyboard visible pushes Filter/Sort off-screen — detect via EditText + no popup
                        if (!d.findElements(AppiumBy.className("android.widget.EditText")).isEmpty()
                                && d.findElements(AppiumBy.accessibilityId("Submit")).isEmpty()
                                && d.findElements(AppiumBy.accessibilityId("Save")).isEmpty()) return true;
                        return false;
                    });
            System.out.println("[CommonNav] Already on list screen — skipping Background navigation");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ── Profile Icon ──────────────────────────────────────────────────────────

    @When("User clicks on profile icon")
    public void userClicksOnProfileIcon() {
        if (driver == null) throw new IllegalStateException("Driver is null — Appium session restart failed (ADB unauthorized). Reconnect device and re-run.");
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
        if (alreadyOnListScreen()) {
            // Close search bar via X button if open so the list is in normal state
            new SearchUtils(driver).ensureSearchClosed();
            return;
        }
        Assert.assertEquals(usersPage.getListPageTitle(), expectedTitle,
                "List screen title mismatch");
    }
}
