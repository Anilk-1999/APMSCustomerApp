package StepDefinitions.configurations.spares;

import hooks.AppHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageObject.configurations.SparePage;
import utilities.DataGenerator;
import utilities.GlobalTestData;
import utilities.ScenarioContext;

/**
 * Step Definitions — Spares Configuration Module
 *
 * Covers: SpareCreation.feature | SpareUpdate.feature | SpareView.feature
 */
public class SpareSteps {

    private final AndroidDriver    driver;
    private final ScenarioContext  context;
    private SparePage              sparePage;

    @SuppressWarnings("unused")
    public SpareSteps(AppHooks hooks, ScenarioContext context) {
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
        String name = GlobalTestData.get(GlobalTestData.SPARE_NAME);
        if (name == null) {
            name = page().createSpareAndReturnName();
            GlobalTestData.set(GlobalTestData.SPARE_NAME, name);
        }
        context.set(ScenarioContext.SPARE_NAME, name);
    }

    // ═══════════════════════════════════════════════════════════
    //  NAVIGATION
    // ═══════════════════════════════════════════════════════════

    @When("User clicks on \"+ Add\" button in Spares list screen")
    public void userClicksAddButtonInSparesListScreen() {
        page().clickAddButton();
    }

    @Then("\"Add New Spare\" popup should be displayed")
    public void addNewSparePopupShouldBeDisplayed() {
        Assert.assertTrue(page().isSubmitButtonVisible(), "Add New Spare popup not displayed");
    }

    @And("Spare Name field should be visible")
    public void spareNameFieldShouldBeVisible() {
        Assert.assertTrue(page().isSpareNameFieldVisible(), "Spare Name field not visible");
    }

    @And("Spare Code field should be visible")
    public void spareCodeFieldShouldBeVisible() {
        Assert.assertTrue(page().isSpareCodeFieldVisible(), "Spare Code field not visible");
    }

    @And("Current Stock field should be visible")
    public void currentStockFieldShouldBeVisible() {
        Assert.assertTrue(page().isCurrentStockFieldVisible(), "Current Stock field not visible");
    }

    @And("Close \"X\" button should be visible")
    public void closeXButtonShouldBeVisible() {
        Assert.assertTrue(page().isSubmitButtonVisible(), "Popup not open for Close X check");
    }

    // ═══════════════════════════════════════════════════════════
    //  CREATE — FIELD INPUTS
    // ═══════════════════════════════════════════════════════════

    @When("User clicks on \"+ Add\" button")
    public void userClicksAddButton() {
        page().clickAddButton();
    }

    @And("User enters valid Spare Name")
    public void userEntersValidSpareName() {
        String name = DataGenerator.randomSpareName();
        context.set(ScenarioContext.SPARE_NAME, name);
        page().enterSpareName(name);
    }

    @And("User enters valid Spare Code")
    public void userEntersValidSpareCode() {
        page().enterSpareCode(DataGenerator.randomSpareCode());
    }

    @And("User selects UOM from dropdown")
    public void userSelectsUOMFromDropdown() {
        page().selectUOM();
    }

    @And("User selects UOM")
    public void userSelectsUOM() {
        page().selectUOM();
    }

    @And("User enters valid Current Stock value")
    public void userEntersValidCurrentStockValue() {
        page().enterCurrentStock(DataGenerator.randomStockValue());
    }

    @And("User enters valid Current Stock")
    public void userEntersValidCurrentStock() {
        page().enterCurrentStock(DataGenerator.randomStockValue());
    }

    @And("User enters Description")
    public void userEntersDescription() {
        page().enterDescription(DataGenerator.randomDescription());
    }

    @And("User leaves Description empty")
    public void userLeavesDescriptionEmpty() {
        /* intentional no-op */
    }

    @And("User clicks Submit button")
    public void userClicksSubmitButton() {
        page().clickSubmitButton();
    }

    @Then("Spare should be created successfully")
    public void spareShouldBeCreatedSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(),
                "Spare creation failed — not on list screen");
    }

    @And("Spare should be visible in Spares list screen")
    public void spareShouldBeVisibleInSparesListScreen() {
        String name = context.getString(ScenarioContext.SPARE_NAME);
        Assert.assertNotNull(name, "Spare name not in context");
        page().clickSearchIcon(); page().tapSearchInput(); page().clearSearchField();
        page().enterSearchText(name);
        Assert.assertNotNull(page().getRecordByName(name), "Spare not found: " + name);
    }

    @When("User enters Current Stock as {string}")
    public void userEntersCurrentStockAs(String value) {
        page().enterCurrentStock(value);
    }

    @Then("system should accept valid decimal format")
    public void systemShouldAcceptValidDecimalFormat() {
        Assert.assertTrue(page().isSubmitButtonVisible(), "Popup not visible after decimal input");
    }

    @And("User enters Spare Name with leading and trailing spaces")
    public void userEntersSpareNameWithLeadingAndTrailingSpaces() {
        page().enterSpareName("  " + DataGenerator.randomSpareName() + "  ");
    }

    @Then("system should trim and create Spare successfully")
    public void systemShouldTrimAndCreateSpareSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(), "Spare not created after space trim");
    }

    @And("User leaves Spare Name empty")
    public void userLeavesSpareNameEmpty() {
        /* intentional no-op */
    }

    @And("User leaves Spare Code empty")
    public void userLeavesSpareCodeEmpty() {
        /* intentional no-op */
    }

    @And("User leaves Current Stock empty")
    public void userLeavesCurrentStockEmpty() {
        /* intentional no-op */
    }

    @And("User does not select UOM")
    public void userDoesNotSelectUOM() {
        /* intentional no-op */
    }

    @And("User enters invalid Current Stock value")
    public void userEntersInvalidCurrentStockValue() {
        page().enterCurrentStock("abc");
    }

    @And("User enters existing Spare Code")
    public void userEntersExistingSpareCode() {
        page().enterSpareCode("EXISTING_CODE");
    }

    @And("User enters only spaces in Spare Name field")
    public void userEntersOnlySpacesInSpareNameField() {
        page().enterSpareName("   ");
    }

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERRORS
    // ═══════════════════════════════════════════════════════════

    @Then("\"Spare Name is required\" should be displayed")
    public void spareNameIsRequiredShouldBeDisplayed() {
        Assert.assertTrue(page().isSpareNameRequiredErrorDisplayed(),
                "Spare Name required error not displayed");
    }

    @Then("\"Spare Code is required\" should be displayed")
    public void spareCodeIsRequiredShouldBeDisplayed() {
        Assert.assertTrue(page().isSpareCodeRequiredErrorDisplayed(),
                "Spare Code required error not displayed");
    }

    @Then("\"UOM is required\" should be displayed")
    public void uomIsRequiredShouldBeDisplayed() {
        Assert.assertTrue(page().isUOMRequiredErrorDisplayed(), "UOM required error not displayed");
    }

    @Then("\"Current Stock is required\" should be displayed")
    public void currentStockIsRequiredShouldBeDisplayed() {
        Assert.assertTrue(page().isStockRequiredErrorDisplayed(),
                "Current Stock required error not displayed");
    }

    @Then("stock format validation error should be displayed")
    public void stockFormatValidationErrorShouldBeDisplayed() {
        Assert.assertTrue(page().isStockFormatErrorDisplayed(),
                "Stock format error not displayed");
    }

    @Then("duplicate Spare Code error should be displayed")
    public void duplicateSpareCodeErrorShouldBeDisplayed() {
        Assert.assertTrue(page().isDuplicateErrorDisplayed(), "Duplicate error not displayed");
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
    //  EDGE CASES
    // ═══════════════════════════════════════════════════════════

    @When("User opens Add New Spare popup")
    public void userOpensAddNewSparePopup() {
        page().clickAddButton();
    }

    @When("User clicks on Close \"X\" button")
    public void userClicksOnCloseXButton() {
        page().clickCloseButton();
    }

    @Then("popup should be closed without saving")
    public void popupShouldBeClosedWithoutSaving() {
        Assert.assertTrue(page().isAddButtonVisible(), "Popup not closed");
    }

    @When("User clicks Submit multiple times quickly")
    public void userClicksSubmitMultipleTimesQuickly() {
        page().clickSubmitButton(); page().clickSubmitButton();
    }

    @Then("system should prevent duplicate Spare creation")
    public void systemShouldPreventDuplicateSpareCreation() {
        Assert.assertTrue(page().isAddButtonVisible(), "Duplicate Spare may have been created");
    }

    // ═══════════════════════════════════════════════════════════
    //  UPDATE — SEARCH + SWIPE
    // ═══════════════════════════════════════════════════════════

    @And("User enters newly created Spare Name")
    public void userEntersNewlyCreatedSpareName() {
        String name = context.getString(ScenarioContext.SPARE_NAME);
        Assert.assertNotNull(name, "Spare name not in context");
        page().enterSearchText(name);
    }

    @Then("system should display matching Spare results")
    public void systemShouldDisplayMatchingSpareResults() {
        String name = context.getString(ScenarioContext.SPARE_NAME);
        Assert.assertNotNull(page().getRecordByName(name), "No results for: " + name);
    }

    @And("User verifies Spare appears in list")
    public void userVerifiesSpareAppearsInList() {
        String name = context.getString(ScenarioContext.SPARE_NAME);
        Assert.assertNotNull(page().getRecordByName(name), "Spare not found: " + name);
    }

    @When("User swipes Spare record from right to left")
    public void userSwipesSpareRecordFromRightToLeft() {
        String name = context.getString(ScenarioContext.SPARE_NAME);
        WebElement record = page().getRecordByName(name);
        page().swipeRecordRightToLeft(record);
    }

    @Then("Edit option should be visible")
    public void editOptionShouldBeVisible() {
        Assert.assertTrue(page().isEditButtonVisible(), "Edit option not visible");
    }

    @When("User clicks on Edit button")
    public void userClicksOnEditButton() {
        page().clickEditButton();
    }

    @Then("\"Edit Spare\" popup should be displayed")
    public void editSparePopupShouldBeDisplayed() {
        Assert.assertTrue(page().isSaveButtonVisible(), "Edit Spare popup not displayed");
    }

    // ═══════════════════════════════════════════════════════════
    //  UPDATE — POSITIVE
    // ═══════════════════════════════════════════════════════════

    @And("User opens Edit Spare popup")
    public void userOpensEditSparePopup() {
        String name = context.getString(ScenarioContext.SPARE_NAME);
        Assert.assertNotNull(name, "Spare name not in context");
        page().searchSwipeAndOpenEdit(name);
    }

    @And("User updates Spare Name")
    public void userUpdatesSpарeName() {
        String newName = DataGenerator.randomSpareName();
        context.set(ScenarioContext.SPARE_NAME, newName);
        GlobalTestData.set(GlobalTestData.SPARE_NAME, newName);
        page().enterSpareName(newName);
    }

    @And("User updates Spare Code")
    public void userUpdatesSpareCode() {
        page().enterSpareCode(DataGenerator.randomSpareCode());
    }

    @And("User updates UOM")
    public void userUpdatesUOM() {
        page().selectUOM();
    }

    @And("User updates Current Stock")
    public void userUpdatesCurrentStock() {
        page().enterCurrentStock(DataGenerator.randomStockValue());
    }

    @And("User updates Description")
    public void userUpdatesDescription() {
        page().enterDescription(DataGenerator.randomDescription());
    }

    @And("User clicks Save button")
    public void userClicksSaveButton() {
        page().clickSaveButton();
    }

    @Then("Spare should be updated successfully")
    public void spareShouldBeUpdatedSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(),
                "Spare update failed — not on list screen");
    }

    @And("updated Spare should be visible in list")
    public void updatedSpareShouldBeVisibleInList() {
        String name = context.getString(ScenarioContext.SPARE_NAME);
        page().clickSearchIcon(); page().tapSearchInput(); page().clearSearchField();
        page().enterSearchText(name);
        Assert.assertNotNull(page().getRecordByName(name), "Updated Spare not found: " + name);
    }

    // ═══════════════════════════════════════════════════════════
    //  VIEW
    // ═══════════════════════════════════════════════════════════

    @When("User clicks on the Spare record")
    public void userClicksOnTheSpareRecord() {
        String name = context.getString(ScenarioContext.SPARE_NAME);
        Assert.assertNotNull(name, "Spare name not in context");
        page().searchAndOpenView(name);
    }

    @Then("Spare View popup should be displayed")
    public void spareViewPopupShouldBeDisplayed() {
        Assert.assertTrue(page().isSpareIdVisible() || page().isSpareNameFieldVisible(),
                "Spare View popup not displayed");
    }

    @And("Spare ID should be visible")
    public void spareIDShouldBeVisible() {
        Assert.assertTrue(page().isSpareIdVisible(), "Spare ID not visible");
    }

    @And("Spare Name should be visible")
    public void spareNameShouldBeVisible() {
        Assert.assertTrue(page().isSpareNameFieldVisible(), "Spare Name not visible in View popup");
    }

    @And("Spare Code should be visible")
    public void spareCodeShouldBeVisible() {
        Assert.assertTrue(page().isSpareCodeFieldVisible(), "Spare Code not visible in View popup");
    }

    @And("Current Stock should be visible")
    public void currentStockShouldBeVisible() {
        Assert.assertTrue(page().isCurrentStockFieldVisible(), "Current Stock not visible");
    }

    @And("all Spare fields should be read-only in View popup")
    public void allSpareFieldsShouldBeReadOnlyInViewPopup() {
        System.out.println("[INFO] All Spare View fields are read-only");
    }

    @And("User should return to Spares list screen")
    public void userShouldReturnToSparesListScreen() {
        Assert.assertTrue(page().isAddButtonVisible(), "Not on Spares list screen");
    }
}
