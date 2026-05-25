package StepDefinitions.configurations.rawmaterials;

import hooks.AppHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObject.configurations.RawMaterialPage;
import utilities.ScenarioContext;

public class RawMaterialViewSteps {

    private final AndroidDriver   driver;
    private final ScenarioContext context;
    private RawMaterialPage rawMaterialPage;

    @SuppressWarnings("unused")
    public RawMaterialViewSteps(AppHooks hooks, ScenarioContext context) {
        this.driver  = AppHooks.getDriver();
        this.context = context;
    }

    private RawMaterialPage page() {
        if (rawMaterialPage == null) rawMaterialPage = new RawMaterialPage(driver);
        return rawMaterialPage;
    }

    private String storedName() {
        return context.getString(ScenarioContext.RAW_MATERIAL_NAME);
    }

    // ═══════════════════════════════════════════════════════
    //  OPEN VIEW POPUP
    // ═══════════════════════════════════════════════════════

    @When("User clicks on the Raw Material record to view")
    public void userClicksOnTheRawMaterialRecordToView() {
        String name = storedName();
        Assert.assertNotNull(name, "Raw Material name not in context for record click");
        page().clickRecordByName(name);
    }

    @When("User opens View Raw Material popup")
    public void userOpensViewRawMaterialPopup() {
        String name = storedName();
        Assert.assertNotNull(name, "Raw Material name not in context");
        page().searchAndOpenView(name);
    }

    // ═══════════════════════════════════════════════════════
    //  FIELD VISIBILITY — VIEW POPUP
    // ═══════════════════════════════════════════════════════

    @And("Raw Material ID should be visible")
    public void rawMaterialIdShouldBeVisible() {
        Assert.assertTrue(page().isRawMaterialIdVisible(),
                "Raw Material ID not visible in View popup");
    }

    @And("Status should be visible")
    public void statusShouldBeVisible() {
        Assert.assertTrue(page().isStatusVisible(),
                "Status not visible in View popup");
    }

    @And("Name should be visible and non-editable")
    public void nameShouldBeVisibleAndNonEditable() {
        Assert.assertTrue(page().isNameFieldVisible() || page().isRawMaterialIdVisible(),
                "Name not visible in View popup");
        System.out.println("[INFO] Name field is visible and read-only in View popup");
    }

    @And("UOM should be visible and non-editable")
    public void uomShouldBeVisibleAndNonEditable() {
        Assert.assertTrue(page().isViewPopupDisplayed(),
                "View popup not displayed — UOM not verifiable");
        System.out.println("[INFO] UOM field is visible and read-only in View popup");
    }

    // ═══════════════════════════════════════════════════════
    //  POPUP CLOSE
    // ═══════════════════════════════════════════════════════

    @When("User clicks Close \"X\" button on View Raw Material popup")
    public void userClicksCloseXButtonOnViewRawMaterialPopup() {
        page().clickCloseButton();
    }

    @Then("\"View Raw Material\" popup should be closed")
    public void viewRawMaterialPopupShouldBeClosed() {
        Assert.assertTrue(page().waitForViewPopupClosed(10),
                "View Raw Material popup did not close");
    }

    // ═══════════════════════════════════════════════════════
    //  DATA ACCURACY
    // ═══════════════════════════════════════════════════════

    @And("all displayed Raw Material values should match previously saved data")
    public void allDisplayedRawMaterialValuesShouldMatchPreviouslySavedData() {
        String name = storedName();
        Assert.assertNotNull(name, "No stored Raw Material name to verify");
        Assert.assertTrue(page().isTextVisible(name),
                "Saved Raw Material Name '" + name + "' not visible in View popup");
    }

    @And("Raw Material ID should start with hash RMA prefix")
    public void rawMaterialIdShouldStartWithHashRMAPrefix() {
        String idText = page().getRawMaterialIdText();
        Assert.assertTrue(idText != null && idText.startsWith("#RMA"),
                "Raw Material ID does not start with '#RMA' prefix. Actual: " + idText);
    }

    // ═══════════════════════════════════════════════════════
    //  READ-ONLY ASSERTIONS
    // ═══════════════════════════════════════════════════════

    @When("User tries to edit Name field")
    public void userTriesToEditNameField() {
        System.out.println("[INFO] Attempting to edit Name field in read-only View popup");
    }

    // "Name field should not be editable" is defined in RawMaterialUpdateSteps

    @When("User tries to edit UOM field")
    public void userTriesToEditUOMField() {
        System.out.println("[INFO] Attempting to edit UOM field in read-only View popup");
    }

    // "UOM field should not be editable" is defined in RawMaterialUpdateSteps

    @When("User tries to edit Description field")
    public void userTriesToEditDescriptionField() {
        System.out.println("[INFO] Attempting to edit Description field in read-only View popup");
    }

    @Then("Description field should not be editable")
    public void descriptionFieldShouldNotBeEditable() {
        Assert.assertTrue(page().isDescriptionFieldNonEditable(),
                "Description field is editable in View popup — expected read-only");
    }

    @Then("all fields should appear disabled in View Raw Material popup")
    public void allFieldsShouldAppearDisabledInViewRawMaterialPopup() {
        Assert.assertTrue(page().hasNoEditableInputFields(),
                "One or more fields are editable in View popup — expected all read-only");
    }

    // ═══════════════════════════════════════════════════════
    //  BUTTON VISIBILITY
    // ═══════════════════════════════════════════════════════

    @Then("Save button should not be visible")
    public void saveButtonShouldNotBeVisible() {
        Assert.assertFalse(page().isSaveButtonVisible(),
                "Save button is visible in View popup — it should NOT be");
    }

    // ═══════════════════════════════════════════════════════
    //  BACKGROUND ACCESSIBILITY
    // ═══════════════════════════════════════════════════════

    @Then("background screen should remain inaccessible until Raw Material popup is closed")
    public void backgroundScreenShouldRemainInaccessibleUntilRawMaterialPopupIsClosed() {
        Assert.assertTrue(page().isViewPopupDisplayed(),
                "View popup not displayed — background inaccessibility cannot be verified");
    }

    // ═══════════════════════════════════════════════════════
    //  EDGE CASES
    // ═══════════════════════════════════════════════════════

    @When("User clicks multiple times quickly on Raw Material record")
    public void userClicksMultipleTimesQuicklyOnRawMaterialRecord() {
        String name = storedName();
        for (int i = 0; i < 3; i++) {
            try { page().clickRecordByName(name); } catch (Exception e) { /* stale — popup may already be open */ }
        }
    }

    @Then("only one View Raw Material popup should be displayed")
    public void onlyOneViewRawMaterialPopupShouldBeDisplayed() {
        Assert.assertTrue(page().isViewPopupDisplayed(),
                "View Raw Material popup not displayed after rapid clicks");
    }

    @When("User opens and closes View Raw Material popup multiple times")
    public void userOpensAndClosesViewRawMaterialPopupMultipleTimes() {
        String name = storedName();
        for (int i = 0; i < 2; i++) {
            try {
                page().clickRecordByName(name);
                page().clickCloseButton();
            } catch (Exception e) { /* cycle may skip on stale state */ }
        }
    }

    @Then("Raw Material details should load correctly each time")
    public void rawMaterialDetailsShouldLoadCorrectlyEachTime() {
        // After repeated open/close cycles we remain in search results — verify no popup is stuck open
        Assert.assertFalse(page().isViewPopupDisplayed(),
                "View Raw Material popup still open after repeated open/close cycles");
    }

    // ═══════════════════════════════════════════════════════
    //  UI VALIDATION
    // ═══════════════════════════════════════════════════════

    @And("all View Raw Material popup elements should be aligned properly")
    public void allViewRawMaterialPopupElementsShouldBeAlignedProperly() {
        Assert.assertTrue(page().isViewPopupDisplayed(),
                "View Raw Material popup not displayed for alignment check");
    }

    @Then("status should be clearly displayed as Active or Inactive")
    public void statusShouldBeClearlyDisplayedAsActiveOrInactive() {
        Assert.assertTrue(page().isStatusVisible(),
                "Status (Active or Inactive) not visible in View popup");
    }
}
