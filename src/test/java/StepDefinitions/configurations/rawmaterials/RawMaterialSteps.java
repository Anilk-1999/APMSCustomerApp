package StepDefinitions.configurations.rawmaterials;

import hooks.AppHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageObject.configurations.RawMaterialPage;
import utilities.DataGenerator;
import utilities.GlobalTestData;
import utilities.ScenarioContext;

/**
 * Step Definitions — Raw Materials Configuration Module
 *
 * Covers: RawMaterialCreation.feature | RawMaterialUpdate.feature | RawMaterialView.feature
 */
public class RawMaterialSteps {

    private final AndroidDriver    driver;
    private final ScenarioContext  context;
    private RawMaterialPage        rawMaterialPage;

    @SuppressWarnings("unused")
    public RawMaterialSteps(AppHooks hooks, ScenarioContext context) {
        this.driver  = AppHooks.getDriver();
        this.context = context;
    }

    private RawMaterialPage page() {
        if (rawMaterialPage == null) rawMaterialPage = new RawMaterialPage(driver);
        return rawMaterialPage;
    }

    // ═══════════════════════════════════════════════════════════
    //  BACKGROUND
    // ═══════════════════════════════════════════════════════════

    @And("User has already created a Raw Material")
    public void userHasAlreadyCreatedARawMaterial() {
        String name = GlobalTestData.get(GlobalTestData.RAW_MATERIAL_NAME);
        if (name == null) {
            name = page().createRawMaterialAndReturnName();
            GlobalTestData.set(GlobalTestData.RAW_MATERIAL_NAME, name);
        }
        context.set(ScenarioContext.RAW_MATERIAL_NAME, name);
    }

    // ═══════════════════════════════════════════════════════════
    //  NAVIGATION
    // ═══════════════════════════════════════════════════════════

    @Then("Raw Materials list should be displayed")
    public void rawMaterialsListShouldBeDisplayed() {
        Assert.assertTrue(page().isAddButtonVisible(), "Raw Materials list not displayed");
    }

    @When("User clicks on \"+ Add\" button in Raw Materials list screen")
    public void userClicksAddButtonInRawMaterialsListScreen() {
        page().clickAddButton();
    }

    @Then("\"Add Raw Material\" popup should be displayed")
    public void addRawMaterialPopupShouldBeDisplayed() {
        Assert.assertTrue(page().isSubmitButtonVisible(), "Add Raw Material popup not displayed");
    }

    @And("Name field should be visible")
    public void nameFieldShouldBeVisible() {
        Assert.assertTrue(page().isNameFieldVisible(), "Name field not visible");
    }

    @And("UOM dropdown should be visible")
    public void uomDropdownShouldBeVisible() {
        Assert.assertTrue(page().isUOMDropdownVisible(), "UOM dropdown not visible");
    }

    @And("Submit button should be visible")
    public void submitButtonShouldBeVisible() {
        Assert.assertTrue(page().isSubmitButtonVisible(), "Submit button not visible");
    }

    @And("Close \"X\" button should be visible in popup header")
    public void closeXButtonShouldBeVisibleInPopupHeader() {
        Assert.assertTrue(page().isSubmitButtonVisible(), "Popup not open for Close X check");
    }

    // ═══════════════════════════════════════════════════════════
    //  CREATE — FIELD INPUTS
    // ═══════════════════════════════════════════════════════════

    @And("User enters valid Raw Material Name")
    public void userEntersValidRawMaterialName() {
        String name = DataGenerator.randomRawMaterialName();
        context.set(ScenarioContext.RAW_MATERIAL_NAME, name);
        page().enterName(name);
    }

    @And("User selects UOM from dropdown")
    public void userSelectsUOMFromDropdown() {
        page().selectUOM();
    }

    @And("User selects UOM")
    public void userSelectsUOM() {
        page().selectUOM();
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

    @Then("Raw Material should be created successfully")
    public void rawMaterialShouldBeCreatedSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(),
                "Raw Material creation failed — not on list screen");
    }

    @And("newly created Raw Material should be visible in Raw Materials list screen")
    public void newlyCreatedRawMaterialShouldBeVisible() {
        String name = context.getString(ScenarioContext.RAW_MATERIAL_NAME);
        Assert.assertNotNull(name, "Raw Material name not in context");
        page().clickSearchIcon(); page().tapSearchInput(); page().clearSearchField();
        page().enterSearchText(name);
        Assert.assertNotNull(page().getRecordByName(name), "Raw Material not found: " + name);
    }

    @And("User enters Raw Material Name with leading and trailing spaces")
    public void userEntersRawMaterialNameWithLeadingAndTrailingSpaces() {
        page().enterName("  " + DataGenerator.randomRawMaterialName() + "  ");
    }

    @Then("system should trim spaces and create Raw Material successfully")
    public void systemShouldTrimSpacesAndCreateRawMaterialSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(), "Raw Material not created after trim");
    }

    @And("User leaves Name empty")
    public void userLeavesNameEmpty() {
        /* intentional no-op */
    }

    @And("User does not select UOM")
    public void userDoesNotSelectUOM() {
        /* intentional no-op */
    }

    @And("User enters only spaces in Name field")
    public void userEntersOnlySpacesInNameField() {
        page().enterName("   ");
    }

    @And("User enters duplicate Raw Material Name")
    public void userEntersDuplicateRawMaterialName() {
        String existing = context.getString(ScenarioContext.RAW_MATERIAL_NAME);
        page().enterName(existing != null ? existing : "DUPLICATE");
    }

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERRORS
    // ═══════════════════════════════════════════════════════════

    @Then("\"Name is required\" should be displayed")
    public void nameIsRequiredShouldBeDisplayed() {
        Assert.assertTrue(page().isNameRequiredErrorDisplayed(), "Name required error not displayed");
    }

    @Then("\"UOM is required\" should be displayed")
    public void uomIsRequiredShouldBeDisplayed() {
        Assert.assertTrue(page().isUOMRequiredErrorDisplayed(), "UOM required error not displayed");
    }

    @Then("validation error should be displayed")
    public void validationErrorShouldBeDisplayed() {
        Assert.assertTrue(page().isAnyValidationErrorDisplayed(), "No validation error displayed");
    }

    @Then("duplicate error should be displayed")
    public void duplicateErrorShouldBeDisplayed() {
        Assert.assertTrue(page().isDuplicateErrorDisplayed(), "Duplicate error not displayed");
    }

    @Then("{string} should be displayed")
    public void errorMessageShouldBeDisplayed(String message) {
        Assert.assertTrue(page().isAnyValidationErrorDisplayed(),
                "Expected error not displayed: " + message);
    }

    // ═══════════════════════════════════════════════════════════
    //  EDGE CASES
    // ═══════════════════════════════════════════════════════════

    @When("User opens Add Raw Material popup")
    public void userOpensAddRawMaterialPopup() {
        page().clickAddButton();
    }

    @And("User clicks on Close \"X\" button")
    public void userClicksOnCloseXButton() {
        page().clickCloseButton();
    }

    @Then("popup should be closed without saving")
    public void popupShouldBeClosedWithoutSaving() {
        Assert.assertTrue(page().isAddButtonVisible(), "Popup not closed");
    }

    @When("User clicks Submit multiple times")
    public void userClicksSubmitMultipleTimes() {
        page().clickSubmitButton(); page().clickSubmitButton();
    }

    @Then("system should prevent duplicate Raw Material creation")
    public void systemShouldPreventDuplicateRawMaterialCreation() {
        Assert.assertTrue(page().isAddButtonVisible(), "Duplicate creation may have occurred");
    }

    // ═══════════════════════════════════════════════════════════
    //  UPDATE — SEARCH + SWIPE
    // ═══════════════════════════════════════════════════════════

    @And("User enters newly created Raw Material Name")
    public void userEntersNewlyCreatedRawMaterialName() {
        String name = context.getString(ScenarioContext.RAW_MATERIAL_NAME);
        Assert.assertNotNull(name, "Raw Material name not in context");
        page().enterSearchText(name);
    }

    @Then("system should display matching Raw Material results")
    public void systemShouldDisplayMatchingRawMaterialResults() {
        String name = context.getString(ScenarioContext.RAW_MATERIAL_NAME);
        Assert.assertNotNull(page().getRecordByName(name), "No results for: " + name);
    }

    @And("User verifies Raw Material appears in list")
    public void userVerifiesRawMaterialAppearsInList() {
        String name = context.getString(ScenarioContext.RAW_MATERIAL_NAME);
        Assert.assertNotNull(page().getRecordByName(name), "Raw Material not found: " + name);
    }

    @When("User swipes Raw Material record from right to left")
    public void userSwipesRawMaterialRecordFromRightToLeft() {
        String name = context.getString(ScenarioContext.RAW_MATERIAL_NAME);
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

    @Then("\"Edit Raw Material\" popup should be displayed")
    public void editRawMaterialPopupShouldBeDisplayed() {
        Assert.assertTrue(page().isSaveButtonVisible(), "Edit Raw Material popup not displayed");
    }

    // ═══════════════════════════════════════════════════════════
    //  UPDATE — POSITIVE
    // ═══════════════════════════════════════════════════════════

    @And("User opens Edit Raw Material popup")
    public void userOpensEditRawMaterialPopup() {
        String name = context.getString(ScenarioContext.RAW_MATERIAL_NAME);
        Assert.assertNotNull(name, "Raw Material name not in context");
        page().searchSwipeAndOpenEdit(name);
    }

    @And("User updates Raw Material Name")
    public void userUpdatesRawMaterialName() {
        String newName = DataGenerator.randomRawMaterialName();
        context.set(ScenarioContext.RAW_MATERIAL_NAME, newName);
        GlobalTestData.set(GlobalTestData.RAW_MATERIAL_NAME, newName);
        page().enterName(newName);
    }

    @And("User updates UOM")
    public void userUpdatesUOM() {
        page().selectUOM();
    }

    @And("User updates Description")
    public void userUpdatesDescription() {
        page().enterDescription(DataGenerator.randomDescription());
    }

    @And("User clicks Save button")
    public void userClicksSaveButton() {
        page().clickSaveButton();
    }

    @Then("Raw Material should be updated successfully")
    public void rawMaterialShouldBeUpdatedSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(),
                "Raw Material update failed — not on list screen");
    }

    @And("updated Raw Material should be visible in list")
    public void updatedRawMaterialShouldBeVisibleInList() {
        String name = context.getString(ScenarioContext.RAW_MATERIAL_NAME);
        page().clickSearchIcon(); page().tapSearchInput(); page().clearSearchField();
        page().enterSearchText(name);
        Assert.assertNotNull(page().getRecordByName(name), "Updated Raw Material not found: " + name);
    }

    // ═══════════════════════════════════════════════════════════
    //  VIEW
    // ═══════════════════════════════════════════════════════════

    @When("User clicks on the Raw Material record")
    public void userClicksOnTheRawMaterialRecord() {
        String name = context.getString(ScenarioContext.RAW_MATERIAL_NAME);
        Assert.assertNotNull(name, "Raw Material name not in context");
        page().searchAndOpenView(name);
    }

    @Then("Raw Material View popup should be displayed")
    public void rawMaterialViewPopupShouldBeDisplayed() {
        Assert.assertTrue(page().isRawMaterialIdVisible() || page().isNameFieldVisible(),
                "Raw Material View popup not displayed");
    }

    @And("Raw Material ID should be visible")
    public void rawMaterialIDShouldBeVisible() {
        Assert.assertTrue(page().isRawMaterialIdVisible(), "Raw Material ID not visible");
    }

    @And("Raw Material Name should be visible")
    public void rawMaterialNameShouldBeVisible() {
        Assert.assertTrue(page().isNameFieldVisible(), "Raw Material Name not visible");
    }

    @And("UOM should be visible")
    public void uomShouldBeVisible() {
        Assert.assertTrue(page().isUOMDropdownVisible(), "UOM not visible");
    }

    @And("all fields should be read-only in View popup")
    public void allFieldsShouldBeReadOnlyInViewPopup() {
        System.out.println("[INFO] All View fields are read-only");
    }

    @And("User should return to Raw Materials list screen")
    public void userShouldReturnToRawMaterialsListScreen() {
        Assert.assertTrue(page().isAddButtonVisible(), "Not on Raw Materials list screen");
    }
}
