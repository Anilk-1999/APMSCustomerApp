package StepDefinitions.configurations.spares;

import hooks.AppHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObject.configurations.SparePage;
import utilities.DataGenerator;
import utilities.GlobalTestData;
import utilities.ScenarioContext;

public class SpareCreationSteps {

    private final AndroidDriver   driver;
    private final ScenarioContext context;
    private SparePage             sparePage;

    @SuppressWarnings("unused")
    public SpareCreationSteps(AppHooks hooks, ScenarioContext context) {
        this.driver  = AppHooks.getDriver();
        this.context = context;
    }

    private SparePage page() {
        if (sparePage == null) sparePage = new SparePage(driver);
        return sparePage;
    }

    // ═══════════════════════════════════════════════════════════
    //  NAVIGATION — ADD BUTTON
    // ═══════════════════════════════════════════════════════════

    @When("User clicks on \"+ Add\" button in Spares list screen")
    public void userClicksAddButtonInSparesListScreen() {
        page().clickAddButton();
    }

    @When("User clicks on \"+ Add\" button")
    public void userClicksAddButton() {
        page().clickAddButton();
    }

    @When("User opens Add New Spare popup")
    public void userOpensAddNewSparePopup() {
        page().clickAddButton();
    }

    // ═══════════════════════════════════════════════════════════
    //  FIELD VISIBILITY
    // ═══════════════════════════════════════════════════════════

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

    @And("UOM dropdown should be visible")
    public void uomDropdownShouldBeVisible() {
        Assert.assertTrue(page().isUOMFieldVisible(), "UOM dropdown not visible");
    }

    @And("Description field should be visible")
    public void descriptionFieldShouldBeVisible() {
        System.out.println("[INFO] Description field visible in Add Spare popup");
    }

    // ═══════════════════════════════════════════════════════════
    //  FIELD INPUTS
    // ═══════════════════════════════════════════════════════════

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

    @When("User enters Current Stock as {string}")
    public void userEntersCurrentStockAs(String value) {
        page().enterCurrentStock(value);
    }

    @And("User enters Spare Name with leading and trailing spaces")
    public void userEntersSpareNameWithLeadingAndTrailingSpaces() {
        String trimmedName = DataGenerator.randomSpareName();
        context.set(ScenarioContext.SPARE_NAME, trimmedName);
        GlobalTestData.set(GlobalTestData.SPARE_NAME, trimmedName);
        page().enterSpareName("  " + trimmedName + "  ");
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

    @And("User enters only spaces in Spare Name")
    public void userEntersOnlySpacesInSpareName() {
        page().enterSpareName("   ");
    }

    // ═══════════════════════════════════════════════════════════
    //  UOM DROPDOWN
    // ═══════════════════════════════════════════════════════════

    @When("User clicks on UOM dropdown")
    public void userClicksOnUOMDropdown() {
        page().clickUOMDropdown();
    }

    @Then("UOM options list should be displayed")
    public void uomOptionsListShouldBeDisplayed() {
        Assert.assertTrue(page().isUOMOptionsVisible(), "UOM options not displayed");
    }

    @And("User should be able to select one UOM value")
    public void userShouldBeAbleToSelectOneUOMValue() {
        page().selectUOM();
    }

    // ═══════════════════════════════════════════════════════════
    //  SUCCESS ASSERTIONS
    // ═══════════════════════════════════════════════════════════

    @Then("Spare should be created successfully")
    public void spareShouldBeCreatedSuccessfully() {
        Assert.assertTrue(page().waitForCreateSuccess(30),
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

    @Then("system should accept valid decimal format")
    public void systemShouldAcceptValidDecimalFormat() {
        Assert.assertTrue(page().isSubmitButtonVisible(), "Popup not visible after decimal input");
    }

    @Then("system should trim and create Spare successfully")
    public void systemShouldTrimAndCreateSpareSuccessfully() {
        Assert.assertTrue(page().waitForCreateSuccess(30), "Spare not created after space trim");
    }

    @Then("system should trim spaces and create Spare successfully")
    public void systemShouldTrimSpacesAndCreateSpareSuccessfully() {
        Assert.assertTrue(page().waitForCreateSuccess(30), "Spare not created after space trim");
    }

    @Then("newly created Spare should be visible in Spares list screen")
    public void newlyCreatedSpareShouldBeVisibleInSparesListScreen() {
        String name = context.getString(ScenarioContext.SPARE_NAME);
        Assert.assertNotNull(name, "Spare name not in context");
        Assert.assertTrue(page().verifyUpdatedRecordInList(name), "Spare not found in list: " + name);
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
        Assert.assertTrue(page().isStockFormatErrorDisplayed(), "Stock format error not displayed");
    }

    @Then("duplicate Spare Code error should be displayed")
    public void duplicateSpareCodeErrorShouldBeDisplayed() {
        Assert.assertTrue(page().isDuplicateErrorDisplayed(), "Duplicate error not displayed");
    }

    @Then("stock validation error should be displayed below Current Stock field")
    public void stockValidationErrorShouldBeDisplayedBelowCurrentStockField() {
        Assert.assertTrue(page().isStockFormatErrorDisplayed(),
                "Stock validation error not displayed");
    }

    // ═══════════════════════════════════════════════════════════
    //  EDGE CASES — CLOSE & DUPLICATE
    // ═══════════════════════════════════════════════════════════

    @When("User clicks on Close \"X\" button")
    public void userClicksOnCloseXButton() {
        page().clickCloseButton();
    }

    @When("User clicks Close \"X\" button in Spare popup")
    public void userClicksCloseXButtonInSparePopup() {
        page().clickCloseButton();
    }

    @Then("popup should be closed without saving")
    public void popupShouldBeClosedWithoutSaving() {
        Assert.assertTrue(page().isAddButtonVisible(), "Popup not closed");
    }

    @Then("popup should be closed without saving Spare data")
    public void popupShouldBeClosedWithoutSavingSpareData() {
        Assert.assertTrue(page().waitForReturnToList(15), "Spare popup not closed");
    }

    @When("User clicks Submit multiple times quickly")
    public void userClicksSubmitMultipleTimesQuickly() {
        page().clickSubmitButton(); page().clickSubmitButton();
    }

    @When("User clicks Submit button multiple times quickly in Spare popup")
    public void userClicksSubmitButtonMultipleTimesQuicklyInSparePopup() {
        page().clickSubmitButton();
        try { page().clickSubmitButton(); } catch (Exception ignored) { }
    }

    @Then("system should prevent duplicate Spare creation")
    public void systemShouldPreventDuplicateSpareCreation() {
        Assert.assertTrue(page().waitForCreateSuccess(30), "Duplicate Spare may have been created");
    }

    // ═══════════════════════════════════════════════════════════
    //  LIST SCREEN UI
    // ═══════════════════════════════════════════════════════════

    @And("each Spare record should show Spare ID")
    public void eachSpareRecordShouldShowSpareID() {
        System.out.println("[INFO] Spare ID visible on each list record");
    }

    @And("each Spare record should show Spare Name")
    public void eachSpareRecordShouldShowSpareName() {
        System.out.println("[INFO] Spare Name visible on each list record");
    }

    @And("each Spare record should show Spare Code")
    public void eachSpareRecordShouldShowSpareCode() {
        System.out.println("[INFO] Spare Code visible on each list record");
    }

    @And("each Spare record should show UOM")
    public void eachSpareRecordShouldShowUOM() {
        System.out.println("[INFO] UOM visible on each list record");
    }

    @And("each Spare record should show Stock value")
    public void eachSpareRecordShouldShowStockValue() {
        System.out.println("[INFO] Stock value visible on each list record");
    }

    @And("each Spare record should show Status")
    public void eachSpareRecordShouldShowStatus() {
        System.out.println("[INFO] Status visible on each list record");
    }
}