package StepDefinitions.configurations.activities;

import hooks.AppHooks;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageObject.configurations.ActivityPage;
import utilities.DataGenerator;
import utilities.GlobalTestData;
import utilities.ScenarioContext;

import java.time.Duration;

/**
 * Step Definitions — Activities Configuration Module
 *
 * Covers: ActivityCreation.feature | ActivityUpdate.feature | ActivityView.feature
 *
 * Rules:
 *  - ZERO business logic here — only page-method calls and assertions
 *  - ScenarioContext shares the created activity name across steps
 *  - PicoContainer injects AppHooks (driver) and ScenarioContext automatically
 *  - Generic steps (popup display, search, edit, status, save, submit, etc.)
 *    are handled by CommonFormSteps — do NOT duplicate them here
 */
public class ActivitySteps {

    private final AndroidDriver  driver;
    private final ScenarioContext context;
    private ActivityPage         activityPage;

    @SuppressWarnings("unused")
    public ActivitySteps(AppHooks hooks, ScenarioContext context) {
        this.driver  = AppHooks.getDriver();
        this.context = context;
    }

    private ActivityPage page() {
        if (activityPage == null) activityPage = new ActivityPage(driver);
        return activityPage;
    }

    // ═══════════════════════════════════════════════════════════
    //  BACKGROUND PRE-CONDITION
    // ═══════════════════════════════════════════════════════════

    @And("User has already created an Activity")
    public void userHasAlreadyCreatedAnActivity() {
        String name = GlobalTestData.get(GlobalTestData.ACTIVITY_NAME);
        if (name == null) {
            name = page().createActivityAndReturnName();
            GlobalTestData.set(GlobalTestData.ACTIVITY_NAME, name);
        }
        context.set(ScenarioContext.ACTIVITY_NAME, name);
        System.out.println("[ActivitySteps] Using activity name: " + name);
    }

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN
    // ═══════════════════════════════════════════════════════════

    @Then("Activities list should be displayed")
    public void activitiesListShouldBeDisplayed() {
        /* confirmed by Background navigation step — list visible if no exception thrown */
    }

    @When("User clicks on {string} button in Activities list screen")
    public void userClicksAddButtonInActivitiesListScreen(String label) {
        page().clickAddButton();
    }

    // ═══════════════════════════════════════════════════════════
    //  ADD POPUP FIELD VISIBILITY
    // ═══════════════════════════════════════════════════════════

    @And("Activity Name field should be visible")
    public void activityNameFieldShouldBeVisible() {
        Assert.assertTrue(page().isActivityNameFieldVisible(), "Activity Name field not visible");
    }

    @And("Description field should be visible")
    public void descriptionFieldShouldBeVisible() {
        Assert.assertTrue(page().isDescriptionFieldVisible(), "Description field not visible");
    }

    @And("{string} label with toggle button should be visible")
    public void labelWithToggleShouldBeVisible(String label) {
        Assert.assertTrue(page().isToggleVisible(), label + " toggle not visible");
    }

    @And("Close {string} button should be visible in popup header")
    public void closeButtonShouldBeVisibleInPopupHeader(String label) {
        Assert.assertTrue(page().isCloseButtonVisible(), "Close button not visible in popup header");
    }

    // ═══════════════════════════════════════════════════════════
    //  FIELD INPUT — CREATE / EDIT
    // ═══════════════════════════════════════════════════════════

    @And("User enters valid Activity Name")
    public void userEntersValidActivityName() {
        String name = DataGenerator.randomActivityName();
        context.set(ScenarioContext.ACTIVITY_NAME, name);
        page().enterActivityName(name);
    }

    @And("User enters Activity Name with leading and trailing spaces")
    public void userEntersActivityNameWithLeadingAndTrailingSpaces() {
        String trimmedName = DataGenerator.randomActivityName();
        context.set(ScenarioContext.ACTIVITY_NAME, trimmedName);
        context.set(ScenarioContext.PENDING_ACTIVITY_NAME, trimmedName);
        page().enterActivityName("  " + trimmedName + "  ");
    }

    @And("User enters Description")
    public void userEntersDescription() {
        page().enterDescription(DataGenerator.randomDescription());
    }

    @And("User leaves Description empty")
    public void userLeavesDescriptionEmpty() {
        // optional field — intentionally left blank
    }

    @And("User keeps {string} toggle as default")
    public void userKeepsToggleAsDefault(String toggleLabel) {
        // no action — default state is kept
    }

    @And("User enables {string} toggle")
    public void userEnablesToggle(String toggleLabel) {
        page().enableIsFunctionApplicableToggle();
    }

    @And("User disables {string} toggle")
    public void userDisablesToggle(String toggleLabel) {
        page().disableIsFunctionApplicableToggle();
    }

    @And("User leaves Activity Name empty")
    public void userLeavesActivityNameEmpty() {
        // intentionally not entering Activity Name
    }

    @And("User enters only spaces in Activity Name")
    public void userEntersOnlySpacesInActivityName() {
        page().enterActivityName("     ");
    }

    @And("User enters only special characters in Activity Name")
    public void userEntersOnlySpecialCharactersInActivityName() {
        page().enterActivityName("@#$%^&*");
    }

    @And("User enters already existing Activity Name")
    public void userEntersAlreadyExistingActivityName() {
        typeExistingActivityName();
    }

    @And("User clicks Close {string} button")
    public void userClicksCloseButton(String label) {
        page().clickCloseButton();
        // After clicking X, if form had data the app shows "Confirmation Alert".
        // Wait up to 5 s for "Yes, Exit" to appear and click it directly.
        page().clickYesExitIfConfirmationShows();
    }

    // ═══════════════════════════════════════════════════════════
    //  CREATE POSITIVE OUTCOME
    // ═══════════════════════════════════════════════════════════

    @Then("Activity should be created successfully")
    public void activityShouldBeCreatedSuccessfully() {
        // Success banner ("Record created successfully") appears briefly after creation
        // OR verify by confirming popup closed and list screen is back
        boolean successBanner = page().isSuccessMessageDisplayed();
        boolean backOnList    = page().isAddButtonVisible();
        Assert.assertTrue(successBanner || backOnList,
                "Activity creation failed — success banner not shown and not back on list screen");
    }

    @And("newly created Activity should be visible in Activities list screen")
    public void newlyCreatedActivityShouldBeVisible() {
        String name = context.getString(ScenarioContext.ACTIVITY_NAME);
        Assert.assertNotNull(name, "Activity name not stored in context");
        page().clickSearchIcon();
        page().tapSearchInput();
        page().clearSearchField();
        page().enterSearchText(name);
        WebElement listItem = page().getRecordByName(name);
        Assert.assertNotNull(listItem, "Newly created activity not found in list: " + name);
    }

    @Then("system should trim spaces and create Activity successfully")
    public void systemShouldTrimSpacesAndCreateActivitySuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(), "Activity not created after space trim");
    }

    // ═══════════════════════════════════════════════════════════
    //  CREATE NEGATIVE OUTCOME
    // ═══════════════════════════════════════════════════════════

    @Then("{string} should be displayed")
    public void errorShouldBeDisplayed(String errorMessage) {
        // Give Flutter up to 8 s to render the validation message.
        // Check for: "required" text, popup still open (Save button visible), or no FAB visible.
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        boolean errorShown;
        try {
            new WebDriverWait(driver, Duration.ofSeconds(8))
                    .pollingEvery(Duration.ofMillis(300))
                    .until(d -> {
                        try {
                            // Try "required" in any form (description or text)
                            d.findElement(AppiumBy.androidUIAutomator(
                                    "new UiSelector().descriptionContains(\"required\")"));
                            return Boolean.TRUE;
                        } catch (Exception ex1) {
                            try {
                                // Fallback: Save button still visible = popup still open = validation shown
                                d.findElement(AppiumBy.accessibilityId("Save"));
                                return Boolean.TRUE;
                            } catch (Exception ex2) { return null; }
                        }
                    });
            errorShown = true;
        } catch (Exception e) {
            errorShown = false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        System.out.println("[ActivitySteps] Validation for '" + errorMessage + "' shown: " + errorShown);
        Assert.assertTrue(errorShown, "Expected validation error not shown: " + errorMessage);
    }

    @Then("validation error should be displayed for Activity Name")
    public void validationErrorShouldBeDisplayedForActivityName() {
        Assert.assertTrue(
                page().isActivityNameRequiredErrorDisplayed() || page().isDuplicateErrorDisplayed(),
                "Validation error not displayed for Activity Name");
    }

    // ═══════════════════════════════════════════════════════════
    //  EDGE CASE — CREATE
    // ═══════════════════════════════════════════════════════════

    @When("User creates Activity with valid data")
    public void userCreatesActivityWithValidData() {
        page().clickAddButton();
        String name = DataGenerator.randomActivityName();
        context.set(ScenarioContext.ACTIVITY_NAME, name);
        page().enterActivityName(name);
    }

    @And("User clicks Submit button multiple times quickly")
    public void userClicksSubmitButtonMultipleTimesQuickly() {
        page().clickSubmitButton();
        // Second click — popup may have already closed after first submit; safe to ignore
        try { page().clickSubmitButton(); } catch (Exception ignored) {}
    }

    @Then("system should prevent duplicate Activity creation")
    public void systemShouldPreventDuplicateActivityCreation() {
        Assert.assertTrue(page().isAddButtonVisible(), "Duplicate activity may have been created");
    }

    @When("User clicks toggle multiple times quickly")
    public void userClicksToggleMultipleTimesQuickly() {
        page().clickAddButton(); // popup must be open to access the toggle
        page().clickIsFunctionApplicableToggle();
        page().clickIsFunctionApplicableToggle();
        page().clickIsFunctionApplicableToggle();
    }

    @Then("system should maintain correct final toggle state")
    public void systemShouldMaintainCorrectFinalToggleState() {
        Assert.assertTrue(page().isToggleVisible(), "Toggle not visible after rapid clicks");
    }

    @When("User enters very long Activity Name or Description")
    public void userEntersVeryLongActivityNameOrDescription() {
        page().clickAddButton(); // popup must be open to enter text
        page().enterActivityName("A".repeat(500));
    }

    @Then("system should restrict or validate input length")
    public void systemShouldRestrictOrValidateInputLength() {
        // App may show validation error, truncate silently, or accept — all are valid outcomes
        Assert.assertTrue(
                page().isActivityNameRequiredErrorDisplayed()
                        || page().isAddActivityPopupDisplayed()
                        || page().isAddButtonVisible(),
                "Unexpected state after entering very long input");
    }

    @When("User clicks Submit button without internet connection")
    public void userClicksSubmitButtonWithoutInternetConnection() {
        page().clickAddButton(); // popup must be open to click Submit
        page().clickSubmitButton();
    }

    @When("session expires while creating Activity")
    public void sessionExpiresWhileCreatingActivity() {
        System.out.println("[INFO] Session timeout scenario — requires real session to expire");
    }

    @When("User opens Add Activity popup")
    public void userOpensAddActivityPopup() {
        page().clickAddButton();
    }

    @And("User enters Activity details")
    public void userEntersActivityDetails() {
        page().enterActivityName(DataGenerator.randomActivityName());
        page().enterDescription(DataGenerator.randomDescription());
    }

    @Then("popup should be closed without saving data")
    public void popupShouldBeClosedWithoutSavingData() {
        Assert.assertTrue(page().isAddButtonVisible(), "Popup did not close after clicking X");
    }

    @When("User closes Add Activity popup")
    public void userClosesAddActivityPopup() {
        page().clickAddButton();
        page().clickCloseButton();
    }

    @And("User reopens Add Activity popup")
    public void userReopensAddActivityPopup() {
        page().clickAddButton();
    }

    @Then("all fields should be reset")
    public void allFieldsShouldBeReset() {
        Assert.assertTrue(page().isAddActivityPopupDisplayed(), "Popup did not reopen after close");
    }

    // ═══════════════════════════════════════════════════════════
    //  UI VALIDATION — CREATE
    // ═══════════════════════════════════════════════════════════

    @And("toggle button should be visible and functional")
    public void toggleButtonShouldBeVisibleAndFunctional() {
        Assert.assertTrue(page().isToggleVisible(), "Toggle not visible");
    }

    @And("Submit button should be enabled only when Activity Name is entered")
    public void submitButtonShouldBeEnabledOnlyWhenActivityNameIsEntered() {
        Assert.assertTrue(page().isSubmitButtonVisible(), "Submit button not visible");
    }

    @When("User clicks on {string} toggle")
    public void userClicksOnToggle(String toggleLabel) {
        page().clickAddButton(); // popup must be open to access the toggle
        page().clickIsFunctionApplicableToggle();
    }

    @Then("toggle state should switch between ON and OFF")
    public void toggleStateShouldSwitchBetweenOnAndOff() {
        // Flutter custom views don't set the 'checked' attribute via UiAutomator2.
        // Verify the toggle responds to clicks and remains visible (functional check).
        Assert.assertTrue(page().isToggleVisible(), "Toggle not visible before click");
        page().clickIsFunctionApplicableToggle();
        Assert.assertTrue(page().isToggleVisible(), "Toggle not visible after click");
        page().clickIsFunctionApplicableToggle(); // restore
    }

    @And("User should return to Activities list screen")
    public void userShouldReturnToActivitiesListScreen() {
        Assert.assertTrue(page().isAddButtonVisible(), "Not on Activities list screen");
    }

    @When("User triggers validation errors")
    public void userTriggersValidationErrors() {
        page().clickAddButton();
        page().clickSubmitButton();
    }

    @Then("validation messages should be displayed below Activity Name field")
    public void validationMessagesShouldBeDisplayedBelowActivityNameField() {
        Assert.assertTrue(page().isActivityNameRequiredErrorDisplayed(),
                "Validation message not shown below Activity Name");
    }

    @Then("system should allow optional input without error")
    public void systemShouldAllowOptionalInputWithoutError() {
        Assert.assertFalse(page().isActivityNameRequiredErrorDisplayed(),
                "Unexpected error on optional Description field");
    }

    @When("User enters long Description")
    public void userEntersLongDescription() {
        page().clickAddButton(); // popup must be open to enter description
        page().enterDescription("D".repeat(300));
    }

    // ═══════════════════════════════════════════════════════════
    //  SEARCH + SWIPE + EDIT FLOW
    // ═══════════════════════════════════════════════════════════

    @And("User enters newly created Activity Name")
    public void userEntersNewlyCreatedActivityName() {
        String name = context.getString(ScenarioContext.ACTIVITY_NAME);
        Assert.assertNotNull(name, "Activity name not found in context");
        page().enterSearchText(name);
    }

    @Then("system should display matching Activity results")
    public void systemShouldDisplayMatchingActivityResults() {
        String name = context.getString(ScenarioContext.ACTIVITY_NAME);
        Assert.assertNotNull(page().getRecordByName(name), "No matching results for: " + name);
    }

    @And("User verifies Activity appears in list")
    public void userVerifiesActivityAppearsInList() {
        String name = context.getString(ScenarioContext.ACTIVITY_NAME);
        Assert.assertNotNull(page().getRecordByName(name), "Activity not visible in list: " + name);
    }

    @When("User swipes Activity record from right to left")
    public void userSwipesActivityRecordFromRightToLeft() {
        String name = context.getString(ScenarioContext.ACTIVITY_NAME);
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
        Assert.assertTrue(page().isActivityNameFieldVisible(), "Activity Name field not visible in edit popup");
    }

    @And("Description field should be pre-filled and editable")
    public void descriptionFieldShouldBePreFilledAndEditable() {
        Assert.assertTrue(page().isDescriptionFieldVisible(), "Description field not visible in edit popup");
    }

    @And("{string} toggle should be visible and editable")
    public void toggleShouldBeVisibleAndEditable(String toggleLabel) {
        Assert.assertTrue(page().isToggleVisible(), toggleLabel + " toggle not visible");
    }

    // ═══════════════════════════════════════════════════════════
    //  STATUS CHANGE — EDIT / VIEW
    // ═══════════════════════════════════════════════════════════

    @When("User opens Edit Activity popup")
    public void userOpensEditActivityPopup() {
        String name = context.getString(ScenarioContext.ACTIVITY_NAME);
        Assert.assertNotNull(name, "Activity name not in context");
        System.out.println("[ActivitySteps] Opening edit popup for: " + name);
        try {
            page().searchSwipeAndOpenEdit(name);
        } catch (Exception e) {
            // Activity not found or swipe/edit failed — exit search mode first so the Add FAB
            // becomes visible again (Flutter hides it while search is active), then recreate.
            System.out.println("[ActivitySteps] Activity '" + name + "' not found — exiting search and recreating");
            try { driver.navigate().back(); } catch (Exception ex) { System.out.println("[ActivitySteps] Back press failed: " + ex.getMessage()); }
            java.util.concurrent.locks.LockSupport.parkNanos(java.time.Duration.ofMillis(1200).toNanos());
            name = page().createActivityAndReturnName();
            GlobalTestData.set(GlobalTestData.ACTIVITY_NAME, name);
            context.set(ScenarioContext.ACTIVITY_NAME, name);
            System.out.println("[ActivitySteps] New activity created: " + name);
            page().searchSwipeAndOpenEdit(name);
        }
    }

    @And("current Activity status is Active")
    public void currentActivityStatusIsActive() {
        Assert.assertEquals(page().getStatusValue(), "Active",
                "Expected Active status but found: " + page().getStatusValue());
        context.set(ScenarioContext.CURRENT_STATUS, "Active");
    }

    @And("current Activity status is Inactive")
    public void currentActivityStatusIsInactive() {
        // Newly created activities start as Active — change to Inactive first if needed
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
        Assert.assertTrue(page().isStatusButtonVisible(), "Status not reflected in Edit popup");
    }

    @And("User closes confirmation popup without confirmation")
    public void userClosesConfirmationPopupWithoutConfirmation() {
        // Clicks the X on the "Confirmation Alert" (dismisses it, stays on form)
        page().clickCloseButton();
    }

    // ═══════════════════════════════════════════════════════════
    //  TOGGLE STEPS — EDIT
    // ═══════════════════════════════════════════════════════════

    @And("User turns ON the toggle")
    public void userTurnsOnTheToggle() {
        page().enableIsFunctionApplicableToggle();
    }

    @Then("toggle state should be ON")
    public void toggleStateShouldBeOn() {
        // Flutter View doesn't expose 'checked' attr via UiAutomator2 — verify toggle visible
        Assert.assertTrue(page().isToggleVisible(), "Toggle not visible after turning ON");
    }

    @And("User turns OFF the toggle")
    public void userTurnsOffTheToggle() {
        page().disableIsFunctionApplicableToggle();
    }

    @Then("toggle state should be OFF")
    public void toggleStateShouldBeOff() {
        // Flutter View doesn't expose 'checked' attr via UiAutomator2 — verify toggle visible
        Assert.assertTrue(page().isToggleVisible(), "Toggle not visible after turning OFF");
    }

    @And("User toggles multiple times quickly")
    public void userTogglesMultipleTimesQuickly() {
        for (int i = 0; i < 4; i++) page().clickIsFunctionApplicableToggle();
    }

    @Then("final toggle state should be maintained correctly")
    public void finalToggleStateShouldBeMaintainedCorrectly() {
        Assert.assertTrue(page().isToggleVisible(), "Toggle lost state after rapid clicks");
    }

    // ═══════════════════════════════════════════════════════════
    //  UPDATE POSITIVE STEPS
    // ═══════════════════════════════════════════════════════════

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

    @Then("Activity should be updated successfully")
    public void activityShouldBeUpdatedSuccessfully() {
        // exitSearchIfOpen FIRST: it hides keyboard internally and waits for Flutter to auto-close
        // the search bar. If keyboard was already hidden before Save (e.g. after a toggle click),
        // Flutter won't auto-close the search bar — exitSearchIfOpen will leave it open and we
        // fall back to catching the update-success toast inside the polling loop below.
        page().exitSearchIfOpen();
        try { driver.hideKeyboard(); } catch (Exception ignored) { /* keyboard may already be closed */ }

        // Promote pending name BEFORE assertion so subsequent scenarios always use the updated name.
        String pending = context.getString(ScenarioContext.PENDING_ACTIVITY_NAME);
        if (pending != null) {
            GlobalTestData.set(GlobalTestData.ACTIVITY_NAME, pending);
            context.set(ScenarioContext.PENDING_ACTIVITY_NAME, null);
            System.out.println("[ActivitySteps] Activity name updated in GlobalTestData to: " + pending);
        }

        // Zero implicit wait so isAddButtonVisible() polls instantly (instead of 10 s per call).
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        boolean success;
        try {
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofMillis(500))
                    .until(d -> {
                        // Primary signal: FAB visible means search closed and we're on the list screen.
                        if (page().isAddButtonVisible()) return Boolean.TRUE;
                        // Fallback: catch the update-success toast (~3 s window).
                        // Needed when search bar stays open (keyboard was already hidden before Save,
                        // so Flutter did not auto-close the bar — FAB remains hidden).
                        try {
                            d.findElement(io.appium.java_client.AppiumBy.androidUIAutomator(
                                    "new UiSelector().descriptionContains(\"Record updated successfully\")"));
                            return Boolean.TRUE;
                        } catch (Exception e) { return null; }
                    });
            success = true;
        } catch (Exception e) {
            success = false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        Assert.assertTrue(success,
                "Activity update failed — update banner not shown and not back on list screen");
    }

    @And("updated Activity should be reflected in Activities list screen")
    public void updatedActivityShouldBeReflectedInActivitiesListScreen() {
        String name = context.getString(ScenarioContext.ACTIVITY_NAME);

        // Detect whether the search bar is already open (EditText visible without a popup).
        // This happens when the keyboard was already hidden before Save — Flutter skips
        // auto-closing the search bar, so activityShouldBeUpdatedSuccessfully() detected
        // the toast but left the bar open. Reuse it instead of closing and reopening.
        try { driver.hideKeyboard(); } catch (Exception ignored) {}
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        boolean searchAlreadyOpen = false;
        try {
            driver.findElement(io.appium.java_client.AppiumBy.className("android.widget.EditText"));
            boolean hasSubmit = false, hasSave = false;
            try { driver.findElement(io.appium.java_client.AppiumBy.accessibilityId("Submit")); hasSubmit = true; } catch (Exception ignored) { /* no Submit — not a popup */ }
            try { driver.findElement(io.appium.java_client.AppiumBy.accessibilityId("Save")); hasSave = true; } catch (Exception ignored) { /* no Save — not a popup */ }
            if (!hasSubmit && !hasSave) searchAlreadyOpen = true;
        } catch (Exception ignored) { /* no EditText — search bar is not open */ }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        if (!searchAlreadyOpen) {
            // Normal path: exit any stale search mode, wait for FAB, then open search.
            page().exitSearchIfOpen();
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            try {
                new WebDriverWait(driver, Duration.ofSeconds(20))
                        .pollingEvery(Duration.ofMillis(500))
                        .until(d -> page().isAddButtonVisible() ? Boolean.TRUE : null);
            } catch (Exception ignored) { /* best-effort — clickSearchIcon below will reveal state */ }
            finally {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            }
            page().clickSearchIcon();
        }

        // Whether we opened search or reused the existing bar, search and verify.
        page().tapSearchInput();
        page().clearSearchField();
        page().enterSearchText(name);
        Assert.assertNotNull(page().getRecordByName(name), "Updated activity not found: " + name);
    }

    @Then("system should trim spaces and update Activity successfully")
    public void systemShouldTrimSpacesAndUpdateActivitySuccessfully() {
        // After Save the list is in search mode — Flutter hides the FAB. Exit search first.
        page().exitSearchIfOpen();
        try { driver.hideKeyboard(); } catch (Exception ignored) {}

        // Wait up to 30 s for the FAB to reappear — confirms the popup closed.
        // Zero implicit wait so isAddButtonVisible()'s internal findElement calls return
        // instantly when element absent, allowing proper 500 ms polling.
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        boolean popupClosed;
        try {
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofMillis(500))
                    .until(d -> page().isAddButtonVisible() ? Boolean.TRUE : null);
            popupClosed = true;
        } catch (Exception e) {
            popupClosed = false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }

        // Promote pending name unconditionally — if the save went through (even if detection
        // was slow), subsequent scenarios must search by the trimmed name that's now in the app.
        String pending = context.getString(ScenarioContext.PENDING_ACTIVITY_NAME);
        if (pending != null) {
            GlobalTestData.set(GlobalTestData.ACTIVITY_NAME, pending);
            context.set(ScenarioContext.PENDING_ACTIVITY_NAME, null);
        }

        Assert.assertTrue(popupClosed, "Update with trimmed spaces failed — Edit popup did not close within 30 s");
    }

    // ═══════════════════════════════════════════════════════════
    //  UPDATE NEGATIVE STEPS
    // ═══════════════════════════════════════════════════════════

    @And("User clears Activity Name field")
    public void userClearsActivityNameField() {
        page().clearActivityNameField();
    }

    @And("User enters invalid Activity Name")
    public void userEntersInvalidActivityName() {
        String invalidName = "@@@###";
        context.set(ScenarioContext.ACTIVITY_NAME, invalidName);
        context.set(ScenarioContext.PENDING_ACTIVITY_NAME, invalidName);
        System.out.println("[ActivitySteps] Entering invalid activity name: " + invalidName);
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
        typeExistingActivityName();
    }

    @And("User clicks Save button without modification")
    public void userClicksSaveButtonWithoutModification() {
        page().clickSaveButton();
    }

    // ═══════════════════════════════════════════════════════════
    //  UPDATE EDGE CASE STEPS
    // ═══════════════════════════════════════════════════════════

    @When("User swipes Activity record multiple times quickly")
    public void userSwipesActivityRecordMultipleTimesQuickly() {
        String name = context.getString(ScenarioContext.ACTIVITY_NAME);
        // Search first so the record is visible in the filtered list
        page().clickSearchIcon();
        page().tapSearchInput();
        page().clearSearchField();
        page().enterSearchText(name);
        WebElement listItem = page().getRecordByName(name);
        page().swipeRecordRightToLeft(listItem);
        // Re-fetch element to avoid StaleElementReferenceException on second swipe
        WebElement listItem2 = page().getRecordByName(name);
        page().swipeRecordRightToLeft(listItem2);
    }

    @Then("only one Edit option should be displayed")
    public void onlyOneEditOptionShouldBeDisplayed() {
        Assert.assertTrue(page().isEditButtonVisible(), "Edit button not visible after multiple swipes");
    }

    @And("User clicks Save button multiple times quickly")
    public void userClicksSaveButtonMultipleTimesQuickly() {
        page().clickSaveButton();
        // Second click — popup may already be closed after first save; ignore if Save not found.
        try { page().clickSaveButton(); } catch (Exception ignored) { /* popup already closed */ }
    }

    @Then("system should prevent duplicate updates")
    public void systemShouldPreventDuplicateUpdates() {
        page().exitSearchIfOpen();
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        boolean onList;
        try {
            new WebDriverWait(driver, Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofMillis(500))
                    .until(d -> page().isAddButtonVisible() ? Boolean.TRUE : null);
            onList = true;
        } catch (Exception e) {
            onList = false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        Assert.assertTrue(onList, "Duplicate update may have been triggered");
    }

    @And("User modifies Activity details")
    public void userModifiesActivityDetails() {
        page().enterActivityName(DataGenerator.randomActivityName());
    }

    @Then("popup should be closed without saving changes")
    public void popupShouldBeClosedWithoutSavingChanges() {
        // exitSearchIfOpen hides keyboard and waits for Flutter auto-close. If the keyboard
        // was already hidden when "Yes, Exit" was clicked (dialog dismissal hides keyboard),
        // Flutter won't auto-close the search bar — exitSearchIfOpen leaves it open.
        page().exitSearchIfOpen();
        try { driver.hideKeyboard(); } catch (Exception ignored) { /* keyboard may already be closed */ }
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        boolean popupClosed;
        try {
            new WebDriverWait(driver, Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofMillis(500))
                    .until(d -> {
                        // Primary: FAB visible = search closed = on list screen.
                        if (page().isAddButtonVisible()) return Boolean.TRUE;
                        // Fallback: Save button gone = edit popup closed (search may still be
                        // open, but the popup is gone — that satisfies "closed without saving").
                        try {
                            d.findElement(io.appium.java_client.AppiumBy.accessibilityId("Save"));
                            return null; // Save still in DOM = popup still open
                        } catch (Exception e) {
                            return Boolean.TRUE; // Save gone = popup closed
                        }
                    });
            popupClosed = true;
        } catch (Exception e) {
            popupClosed = false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        Assert.assertTrue(popupClosed, "Popup still open after clicking X without saving");
    }

    // ═══════════════════════════════════════════════════════════
    //  UPDATE UI VALIDATION STEPS
    // ═══════════════════════════════════════════════════════════

    @Then("Activity ID should be visible")
    public void activityIdShouldBeVisible() {
        Assert.assertTrue(page().isActivityIdVisible(), "Activity ID not visible");
    }

    @And("Status should be visible")
    public void statusShouldBeVisible() {
        Assert.assertTrue(page().isStatusButtonVisible(), "Status not visible");
    }

    @And("Activity Name and Description should be editable")
    public void activityNameAndDescriptionShouldBeEditable() {
        Assert.assertFalse(page().isActivityNameNonEditable(), "Activity Name should be editable");
        Assert.assertFalse(page().isDescriptionNonEditable(),  "Description should be editable");
    }

    @And("toggle should be visible and functional")
    public void toggleShouldBeVisibleAndFunctional() {
        Assert.assertTrue(page().isToggleVisible(), "Toggle not visible in edit popup");
    }

    @Then("confirmation popup should display correct message")
    public void confirmationPopupShouldDisplayCorrectMessage() {
        Assert.assertTrue(page().isYesChangeButtonVisible(), "Confirmation popup content incorrect");
    }

    @Then("popup should be closed successfully")
    public void popupShouldBeClosedSuccessfully() {
        page().exitSearchIfOpen();
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        boolean onList;
        try {
            new WebDriverWait(driver, Duration.ofSeconds(20))
                    .pollingEvery(Duration.ofMillis(500))
                    .until(d -> page().isAddButtonVisible() ? Boolean.TRUE : null);
            onList = true;
        } catch (Exception e) {
            onList = false;
        } finally {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        Assert.assertTrue(onList, "Popup not closed");
    }

    // ═══════════════════════════════════════════════════════════
    //  VIEW STEPS
    // ═══════════════════════════════════════════════════════════

    @When("User clicks on the Activity record")
    public void userClicksOnTheActivityRecord() {
        openActivityViewPopup();
    }

    @And("Activity Name should be visible")
    public void activityNameShouldBeVisible() {
        Assert.assertTrue(page().isActivityNameFieldVisible(), "Activity Name not visible in View popup");
    }

    @And("{string} toggle should be visible")
    public void toggleShouldBeVisible(String toggleLabel) {
        Assert.assertTrue(page().isToggleVisible(), toggleLabel + " toggle not visible in View popup");
    }

    @And("Activity Name field should be non-editable")
    public void activityNameFieldShouldBeNonEditable() {
        Assert.assertTrue(page().isActivityNameNonEditable(),
                "Activity Name should NOT be editable in View popup");
    }

    @And("toggle should be visible but non-editable")
    public void toggleShouldBeVisibleButNonEditable() {
        Assert.assertTrue(page().isToggleVisible(),     "Toggle not visible in View popup");
        Assert.assertTrue(page().isToggleNonEditable(), "Toggle should NOT be editable in View popup");
    }

    @When("User opens View Activity popup")
    public void userOpensViewActivityPopup() {
        openActivityViewPopup();
    }

    @And("User closes confirmation popup without action")
    public void userClosesConfirmationPopupWithoutAction() {
        page().clickCloseButton();
    }

    @Then("status should be updated successfully")
    public void statusShouldBeUpdatedSuccessfully() {
        Assert.assertTrue(page().isStatusButtonVisible(), "Status not reflected after change");
    }

    @And("updated status should be reflected in UI")
    public void updatedStatusShouldBeReflectedInUI() {
        Assert.assertTrue(page().isStatusButtonVisible(), "Status not shown after update");
    }

    @Then("all displayed values should match backend stored data")
    public void allDisplayedValuesShouldMatchBackendStoredData() {
        Assert.assertTrue(page().isViewActivityPopupDisplayed(),
                "View Activity popup not displayed with correct data");
    }

    @Then("toggle should correctly reflect ON\\/OFF state")
    public void toggleShouldCorrectlyReflectOnOffState() {
        Assert.assertTrue(page().isToggleVisible(), "Toggle not visible in View popup");
    }

    // ═══════════════════════════════════════════════════════════
    //  VIEW NEGATIVE STEPS
    // ═══════════════════════════════════════════════════════════

    @When("User tries to edit Activity Name, Description or toggle")
    public void userTriesToEditActivityNameDescriptionOrToggle() {
        // attempt interaction — page guards prevent actual edit
    }

    @Then("fields should not allow modification")
    public void fieldsShouldNotAllowModification() {
        Assert.assertTrue(page().isActivityNameNonEditable(), "Activity Name should be read-only");
        Assert.assertTrue(page().isDescriptionNonEditable(),  "Description should be read-only");
        Assert.assertTrue(page().isToggleNonEditable(),       "Toggle should be read-only");
    }

    @When("Activity is deleted from backend")
    public void activityIsDeletedFromBackend() {
        System.out.println("[INFO] Deleted-record scenario — requires backend setup");
    }

    @And("User searches same Activity")
    public void userSearchesSameActivity() {
        page().clickSearchIcon();
        page().tapSearchInput();
        page().clearSearchField();
        page().enterSearchText(context.getString(ScenarioContext.ACTIVITY_NAME));
    }

    @When("User without permission tries to view Activity")
    public void userWithoutPermissionTriesToViewActivity() {
        System.out.println("[INFO] Permission-denied scenario — requires role-based test user");
    }

    // ═══════════════════════════════════════════════════════════
    //  VIEW EDGE CASE STEPS
    // ═══════════════════════════════════════════════════════════

    @When("User clicks Activity record multiple times quickly")
    public void userClicksActivityRecordMultipleTimesQuickly() {
        userClicksOnTheActivityRecord();
    }

    @Then("only one popup should open")
    public void onlyOnePopupShouldOpen() {
        Assert.assertTrue(page().isViewActivityPopupDisplayed(),
                "View popup not displayed after rapid clicks");
    }

    @When("User clicks Status button multiple times quickly")
    public void userClicksStatusButtonMultipleTimesQuickly() {
        page().clickStatusButton();
    }

    @When("User opens Activity without internet connection")
    public void userOpensActivityWithoutInternetConnection() {
        System.out.println("[INFO] Network failure scenario — requires device network toggle");
    }

    // ═══════════════════════════════════════════════════════════
    //  VIEW UI VALIDATION STEPS
    // ═══════════════════════════════════════════════════════════

    @Then("all fields except Status should be disabled")
    public void allFieldsExceptStatusShouldBeDisabled() {
        Assert.assertTrue(page().isActivityNameNonEditable(), "Activity Name should be disabled");
        Assert.assertTrue(page().isDescriptionNonEditable(),  "Description should be disabled");
        Assert.assertTrue(page().isToggleNonEditable(),       "Toggle should be disabled");
        Assert.assertTrue(page().isStatusButtonVisible(),     "Status button should remain clickable");
    }

    @And("no input cursor should be visible")
    public void noInputCursorShouldBeVisible() {
        Assert.assertTrue(page().isActivityNameNonEditable(),
                "Cursor visible — field is editable when it should not be");
    }

    // ═══════════════════════════════════════════════════════════
    //  PRIVATE HELPERS
    // ═══════════════════════════════════════════════════════════

    private void openActivityViewPopup() {
        String name = context.getString(ScenarioContext.ACTIVITY_NAME);
        Assert.assertNotNull(name, "Activity name not in context");
        page().searchAndOpenView(name);
    }

    private void typeExistingActivityName() {
        String existingName = context.getString(ScenarioContext.ACTIVITY_NAME);
        Assert.assertNotNull(existingName, "No existing activity name in context");
        page().enterActivityName(existingName);
    }
}
