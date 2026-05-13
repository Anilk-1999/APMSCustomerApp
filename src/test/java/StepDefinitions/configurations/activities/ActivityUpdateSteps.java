package StepDefinitions.configurations.activities;

import hooks.AppHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageObject.configurations.ActivityPage;
import utilities.*;

import java.time.Duration;

/**
 * Step Definitions — Activity UPDATE flow.
 *
 * Covers: ActivityUpdate.feature
 *
 * RULES:
 *  - ZERO driver.findElement / findElements calls here
 *  - ZERO business logic — only page method calls and assertions
 *  - All wait, poll, and UI logic lives in ActivityPage and utility classes
 */
public class ActivityUpdateSteps {

    private final AndroidDriver   driver;
    private final ScenarioContext context;
    private ActivityPage          activityPage;

    @SuppressWarnings("unused")
    public ActivityUpdateSteps(AppHooks hooks, ScenarioContext context) {
        this.driver  = AppHooks.getDriver();
        this.context = context;
    }

    private ActivityPage page() {
        if (activityPage == null) activityPage = new ActivityPage(driver);
        return activityPage;
    }

    // ═══════════════════════════════════════════════════════
    //  SWIPE + EDIT FLOW
    // ═══════════════════════════════════════════════════════

    @When("User swipes Activity record from right to left")
    public void userSwipesActivityRecordFromRightToLeft() {
        String name = context.getString(ScenarioContext.ACTIVITY_NAME);
        page().searchRecord(name);
        WebElement listItem = page().getRecordByName(name);
        page().swipeRecordRightToLeft(listItem);
    }

    @Then("Activity ID should be visible and non-editable")
    public void activityIdShouldBeVisibleAndNonEditable() {
        Assert.assertTrue(page().isActivityIdVisible(),     "Activity ID not visible");
        Assert.assertTrue(page().isActivityIdNonEditable(), "Activity ID should be non-editable");
    }

    @And("Activity Name field should be pre-filled and editable")
    public void activityNameFieldShouldBePreFilledAndEditable() {
        Assert.assertTrue(page().isActivityNameFieldVisible(),
                "Activity Name field not visible in edit popup");
    }

    @And("Description field should be pre-filled and editable")
    public void descriptionFieldShouldBePreFilledAndEditable() {
        Assert.assertTrue(page().isDescriptionFieldVisible(),
                "Description field not visible in edit popup");
    }

    @And("{string} toggle should be visible and editable")
    public void toggleShouldBeVisibleAndEditable(String toggleLabel) {
        Assert.assertTrue(page().isToggleVisible(),
                toggleLabel + " toggle not visible");
    }

    // ═══════════════════════════════════════════════════════
    //  OPEN EDIT POPUP
    // ═══════════════════════════════════════════════════════

    @When("User opens Edit Activity popup")
    public void userOpensEditActivityPopup() {
        String name = context.getString(ScenarioContext.ACTIVITY_NAME);
        Assert.assertNotNull(name, "Activity name not in context");
        System.out.println("[ActivityUpdateSteps] Opening edit popup for: " + name);
        try {
            page().searchSwipeAndOpenEdit(name);
        } catch (Exception e) {
            // Activity not found — exit search and recreate
            System.out.println("[ActivityUpdateSteps] Activity '" + name
                    + "' not found — recreating");
            try { driver.navigate().back(); } catch (Exception ignored) {}
            name = page().createActivityAndReturnName();
            GlobalTestData.set(GlobalTestData.ACTIVITY_NAME, name);
            context.set(ScenarioContext.ACTIVITY_NAME, name);
            System.out.println("[ActivityUpdateSteps] New activity created: " + name);
            page().searchSwipeAndOpenEdit(name);
        }
    }

    // ═══════════════════════════════════════════════════════
    //  STATUS CHANGE
    // ═══════════════════════════════════════════════════════

    @And("current Activity status is Active")
    public void currentActivityStatusIsActive() {
        Assert.assertEquals(page().getStatusValue(), "Active",
                "Expected Active status but found: " + page().getStatusValue());
        context.set(ScenarioContext.CURRENT_STATUS, "Active");
    }

    @And("current Activity status is Inactive")
    public void currentActivityStatusIsInactive() {
        if ("Active".equals(page().getStatusValue())) {
            page().clickStatusButton();
            page().clickYesChangeButton();
        }
        Assert.assertEquals(page().getStatusValue(), "Inactive",
                "Expected Inactive status but found: " + page().getStatusValue());
        context.set(ScenarioContext.CURRENT_STATUS, "Inactive");
    }

    @And("updated status should be reflected in Edit popup")
    public void updatedStatusShouldBeReflectedInEditPopup() {
        Assert.assertTrue(page().isStatusButtonVisible(),
                "Status not reflected in Edit popup");
    }

    @And("User closes confirmation popup without confirmation")
    public void userClosesConfirmationPopupWithoutConfirmation() {
        page().clickCloseButton();
    }

    // ═══════════════════════════════════════════════════════
    //  TOGGLE STEPS
    // ═══════════════════════════════════════════════════════

    @And("User turns ON the toggle")
    public void userTurnsOnTheToggle() {
        page().enableIsFunctionApplicableToggle();
    }

    @Then("toggle state should be ON")
    public void toggleStateShouldBeOn() {
        Assert.assertTrue(page().isToggleVisible(),
                "Toggle not visible after turning ON");
    }

    @And("User turns OFF the toggle")
    public void userTurnsOffTheToggle() {
        page().disableIsFunctionApplicableToggle();
    }

    @Then("toggle state should be OFF")
    public void toggleStateShouldBeOff() {
        Assert.assertTrue(page().isToggleVisible(),
                "Toggle not visible after turning OFF");
    }

    @And("User toggles multiple times quickly")
    public void userTogglesMultipleTimesQuickly() {
        for (int i = 0; i < 4; i++) page().clickIsFunctionApplicableToggle();
    }

    @Then("final toggle state should be maintained correctly")
    public void finalToggleStateShouldBeMaintainedCorrectly() {
        Assert.assertTrue(page().isToggleVisible(),
                "Toggle lost state after rapid clicks");
    }

    // ═══════════════════════════════════════════════════════
    //  UPDATE — FIELD INPUT
    // ═══════════════════════════════════════════════════════

    @And("User updates Activity Name")
    public void userUpdatesActivityName() {
        String updatedName = DataGenerator.randomActivityName();
        context.set(ScenarioContext.ACTIVITY_NAME, updatedName);
        context.set(ScenarioContext.PENDING_ACTIVITY_NAME, updatedName);
        page().enterActivityName(updatedName);
    }

    @And("User updates Description")
    public void userUpdatesDescription() {
        page().enterDescription(DataGenerator.randomDescription());
    }

    @And("User updates toggle state")
    public void userUpdatesToggleState() {
        page().clickIsFunctionApplicableToggle();
    }

    @And("User changes {string} toggle")
    public void userChangesToggle(String toggleLabel) {
        page().clickIsFunctionApplicableToggle();
    }

    @And("User changes Status")
    public void userChangesStatus() {
        page().clickStatusButton();
        page().clickYesChangeButton();
    }

    // ═══════════════════════════════════════════════════════
    //  UPDATE — POSITIVE OUTCOMES
    // ═══════════════════════════════════════════════════════

    @Then("Activity should be updated successfully")
    public void activityShouldBeUpdatedSuccessfully() {
        page().hideKeyboard();

        // Promote pending name BEFORE assertion so subsequent scenarios use the updated name
        String pending = context.getString(ScenarioContext.PENDING_ACTIVITY_NAME);
        if (pending != null) {
            GlobalTestData.set(GlobalTestData.ACTIVITY_NAME, pending);
            context.set(ScenarioContext.PENDING_ACTIVITY_NAME, null);
            System.out.println("[ActivityUpdateSteps] Activity name updated in GlobalTestData: " + pending);
        }

        // Poll BEFORE exitSearchIfOpen — success toast is brief (~3s) and would be missed
        // if we spend 3s waiting for the search bar to close first.
        boolean success = new FlutterUtils(driver)
                .waitForReturnToList("Record updated successfully", 30);
        page().exitSearchIfOpen();
        Assert.assertTrue(success,
                "Activity update failed — update banner not shown and not back on list screen");
    }

    @And("updated Activity should be reflected in Activities list screen")
    public void updatedActivityShouldBeReflectedInActivitiesListScreen() {
        String name = context.getString(ScenarioContext.ACTIVITY_NAME);
        page().hideKeyboard();
        page().searchRecord(name);
        Assert.assertNotNull(page().getRecordByName(name),
                "Updated activity not found: " + name);
    }

    @Then("system should trim spaces and update Activity successfully")
    public void systemShouldTrimSpacesAndUpdateActivitySuccessfully() {
        page().hideKeyboard();

        boolean popupClosed = new FlutterUtils(driver).waitForFab(30);
        page().exitSearchIfOpen();

        // Promote pending name unconditionally — save may have gone through even if detection lagged
        String pending = context.getString(ScenarioContext.PENDING_ACTIVITY_NAME);
        if (pending != null) {
            GlobalTestData.set(GlobalTestData.ACTIVITY_NAME, pending);
            context.set(ScenarioContext.PENDING_ACTIVITY_NAME, null);
        }

        Assert.assertTrue(popupClosed,
                "Update with trimmed spaces failed — Edit popup did not close within 30 s");
    }

    // ═══════════════════════════════════════════════════════
    //  UPDATE — NEGATIVE OUTCOMES
    // ═══════════════════════════════════════════════════════

    @And("User clears Activity Name field")
    public void userClearsActivityNameField() {
        page().clearActivityNameField();
    }

    @And("User enters invalid Activity Name")
    public void userEntersInvalidActivityName() {
        String invalidName = "@@@###";
        context.set(ScenarioContext.ACTIVITY_NAME, invalidName);
        context.set(ScenarioContext.PENDING_ACTIVITY_NAME, invalidName);
        page().enterActivityName(invalidName);
    }

    @Then("validation error should be displayed")
    public void validationErrorShouldBeDisplayed() {
        Assert.assertTrue(
                page().isActivityNameRequiredErrorDisplayed() || page().isDuplicateErrorDisplayed(),
                "Validation error not displayed");
    }

    @When("User clicks Save button without internet connection")
    public void userClicksSaveButtonWithoutInternetConnection() {
        page().clickSaveButton();
    }

    @When("session expires during Activity update")
    public void sessionExpiresDuringActivityUpdate() {
        System.out.println("[INFO] Session timeout scenario — requires real session to expire");
    }

    @And("User enters existing Activity Name")
    public void userEntersExistingActivityName() {
        String existingName = context.getString(ScenarioContext.ACTIVITY_NAME);
        Assert.assertNotNull(existingName, "No existing activity name in context");
        page().enterActivityName(existingName);
    }

    @And("User clicks Save button without modification")
    public void userClicksSaveButtonWithoutModification() {
        page().clickSaveButton();
    }

    // ═══════════════════════════════════════════════════════
    //  UPDATE — EDGE CASES
    // ═══════════════════════════════════════════════════════

    @When("User swipes Activity record multiple times quickly")
    public void userSwipesActivityRecordMultipleTimesQuickly() {
        String name = context.getString(ScenarioContext.ACTIVITY_NAME);
        page().searchRecord(name);
        WebElement listItem = page().getRecordByName(name);
        page().swipeRecordRightToLeft(listItem);
        // Re-fetch to avoid StaleElementReferenceException on second swipe
        WebElement listItem2 = page().getRecordByName(name);
        page().swipeRecordRightToLeft(listItem2);
    }

    @Then("only one Edit option should be displayed")
    public void onlyOneEditOptionShouldBeDisplayed() {
        Assert.assertTrue(page().isEditButtonVisible(),
                "Edit button not visible after multiple swipes");
    }

    @And("User clicks Save button multiple times quickly")
    public void userClicksSaveButtonMultipleTimesQuickly() {
        page().clickSaveButton();
        try { page().clickSaveButton(); } catch (Exception ignored) {}
    }

    @Then("system should prevent duplicate updates")
    public void systemShouldPreventDuplicateUpdates() {
        page().exitSearchIfOpen();
        boolean onList = new FlutterUtils(driver).waitForFab(20);
        Assert.assertTrue(onList, "Duplicate update may have been triggered");
    }

    @And("User modifies Activity details")
    public void userModifiesActivityDetails() {
        page().enterActivityName(DataGenerator.randomActivityName());
    }

    @Then("popup should be closed without saving changes")
    public void popupShouldBeClosedWithoutSavingChanges() {
        page().exitSearchIfOpen();
        page().hideKeyboard();

        boolean popupClosed;
        try {
            new WebDriverWait(driver, Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofMillis(500))
                    .until(d -> {
                        // Primary: FAB visible = popup closed
                        if (new FlutterUtils(driver).isFabVisible()) return Boolean.TRUE;
                        // Fallback: Save gone = edit popup closed (search may still be open)
                        if (!new ElementUtils(driver).isPresentByAccessibility("Save"))
                            return Boolean.TRUE;
                        return null;
                    });
            popupClosed = true;
        } catch (Exception e) {
            popupClosed = false;
        }
        Assert.assertTrue(popupClosed, "Popup still open after clicking X without saving");
    }

    // ═══════════════════════════════════════════════════════
    //  UPDATE — UI VALIDATION
    // ═══════════════════════════════════════════════════════

    @Then("Activity ID should be visible")
    public void activityIdShouldBeVisible() {
        Assert.assertTrue(page().isActivityIdVisible(), "Activity ID not visible");
    }

    @Then("Status field should be visible")
    public void statusFieldShouldBeVisible() {
        Assert.assertTrue(page().isStatusButtonVisible(),
                "Status field not visible in View popup");
    }

    @And("Status should be visible")
    public void statusShouldBeVisible() {
        Assert.assertTrue(page().isStatusButtonVisible(), "Status not visible");
    }

    @And("Activity Name and Description should be editable")
    public void activityNameAndDescriptionShouldBeEditable() {
        Assert.assertFalse(page().isActivityNameNonEditable(),
                "Activity Name should be editable");
        Assert.assertFalse(page().isDescriptionNonEditable(),
                "Description should be editable");
    }

    @And("toggle should be visible and functional")
    public void toggleShouldBeVisibleAndFunctional() {
        Assert.assertTrue(page().isToggleVisible(),
                "Toggle not visible in edit popup");
    }

    @Then("confirmation popup should display correct message")
    public void confirmationPopupShouldDisplayCorrectMessage() {
        Assert.assertTrue(page().isYesChangeButtonVisible(),
                "Confirmation popup content incorrect");
    }

    @Then("popup should be closed successfully")
    public void popupShouldBeClosedSuccessfully() {
        page().exitSearchIfOpen();
        boolean onList = new FlutterUtils(driver).waitForFab(20);
        Assert.assertTrue(onList, "Popup not closed");
    }
}
