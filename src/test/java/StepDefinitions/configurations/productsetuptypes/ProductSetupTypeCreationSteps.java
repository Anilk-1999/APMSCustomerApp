package StepDefinitions.configurations.productsetuptypes;

import hooks.AppHooks;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObject.configurations.ProductSetupTypePage;
import utilities.DataGenerator;
import utilities.ElementUtils;
import utilities.GlobalEntityStore;
import utilities.GlobalTestData;
import utilities.ScenarioContext;

public class ProductSetupTypeCreationSteps {

    protected final AndroidDriver  driver;
    protected final ScenarioContext context;
    protected final ElementUtils    elementUtils;
    protected ProductSetupTypePage productSetupTypePage;

    @SuppressWarnings("unused")
    public ProductSetupTypeCreationSteps(AppHooks hooks, ScenarioContext context) {
        this.driver       = AppHooks.getDriver();
        this.context      = context;
        this.elementUtils = new ElementUtils(driver);
    }

    protected ProductSetupTypePage page() {
        if (productSetupTypePage == null) productSetupTypePage = new ProductSetupTypePage(driver);
        return productSetupTypePage;
    }

    protected String storedName() {
        return context.getString(ScenarioContext.PRODUCT_SETUP_TYPE_NAME);
    }

    // ═══════════════════════════════════════════════════════
    //  BACKGROUND — shared by all feature files
    // ═══════════════════════════════════════════════════════

    /**
     * Creates ONE Product Setup Type per test run and reuses it for all scenarios.
     */
    @And("User has already created a Product Setup Type")
    public void userHasAlreadyCreatedAProductSetupType() {
        String name = GlobalEntityStore.getLatestName(GlobalEntityStore.PRODUCT_SETUP_TYPE);
        if (name == null) {
            name = page().createProductSetupTypeAndReturnName();
            elementUtils.waitForPresence(AppiumBy.accessibilityId("Search"), 15);
            GlobalEntityStore.setLatestName(GlobalEntityStore.PRODUCT_SETUP_TYPE, name);
            GlobalTestData.set(GlobalTestData.PRODUCT_SETUP_TYPE_NAME, name);
        }
        context.set(ScenarioContext.PRODUCT_SETUP_TYPE_NAME, name);
    }

    // ═══════════════════════════════════════════════════════
    //  LIST SCREEN
    // ═══════════════════════════════════════════════════════

    @When("User clicks on \"+ Add\" button in Product Setup Types list screen")
    public void userClicksOnAddButtonInProductSetupTypesListScreen() {
        page().clickAddButton();
    }

    // ═══════════════════════════════════════════════════════
    //  SEARCH
    // ═══════════════════════════════════════════════════════

    @When("User enters newly created Product Setup Type Name")
    public void userEntersNewlyCreatedProductSetupTypeName() {
        String name = storedName();
        Assert.assertNotNull(name, "Product Setup Type name not in ScenarioContext");
        page().enterSearchText(name);
    }

    @When("User searches for newly created Product Setup Type Name")
    public void userSearchesForNewlyCreatedProductSetupTypeName() {
        String name = storedName();
        Assert.assertNotNull(name, "Product Setup Type name not in ScenarioContext");
        page().searchForProductSetupType(name);
    }

    @Then("system should display matching Product Setup Type results")
    public void systemShouldDisplayMatchingProductSetupTypeResults() {
        String name = storedName();
        if (name != null) {
            Assert.assertNotNull(page().getRecordByName(name),
                    "No matching Product Setup Type result for: " + name);
        }
    }

    @And("User verifies Product Setup Type appears in list")
    public void userVerifiesProductSetupTypeAppearsInList() {
        systemShouldDisplayMatchingProductSetupTypeResults();
    }

    // ═══════════════════════════════════════════════════════
    //  POPUP FIELD VISIBILITY
    // ═══════════════════════════════════════════════════════

    @And("Product Setup Name field should be visible")
    public void productSetupNameFieldShouldBeVisible() {
        Assert.assertTrue(page().isProductSetupNameFieldVisible(),
                "Product Setup Name field not visible in popup");
    }

    @And("Description field should be visible")
    public void descriptionFieldShouldBeVisible() {
        Assert.assertTrue(page().isDescriptionFieldVisible(),
                "Description field not visible in popup");
    }

    @And("Machine Output Timer field should be visible")
    public void machineOutputTimerFieldShouldBeVisible() {
        Assert.assertTrue(page().isMachineOutputTimerFieldVisible(),
                "Machine Output Timer field not visible in popup");
    }

    @And("Product Setup Timer field should be visible")
    public void productSetupTimerFieldShouldBeVisible() {
        Assert.assertTrue(page().isProductSetupTimerFieldVisible(),
                "Product Setup Timer field not visible in popup");
    }

    // ═══════════════════════════════════════════════════════
    //  FIELD INPUT — CREATE
    // ═══════════════════════════════════════════════════════

    @When("User enters valid Product Setup Name")
    public void userEntersValidProductSetupName() {
        String name = DataGenerator.randomProductSetupName();
        context.set(ScenarioContext.PRODUCT_SETUP_TYPE_NAME, name);
        page().enterProductSetupName(name);
    }

    @When("User enters existing Product Setup Type Name")
    public void userEntersExistingProductSetupTypeName() {
        String name = storedName();
        Assert.assertNotNull(name, "No existing Product Setup Type name in context");
        page().enterProductSetupName(name);
    }

    @When("User enters only spaces in Product Setup Name")
    public void userEntersOnlySpacesInProductSetupName() {
        page().enterProductSetupName("   ");
    }

    @When("User enters Product Setup Name with leading and trailing spaces")
    public void userEntersProductSetupNameWithLeadingAndTrailingSpaces() {
        String trimmed = DataGenerator.randomProductSetupName();
        context.set(ScenarioContext.PRODUCT_SETUP_TYPE_NAME, trimmed);
        GlobalTestData.set(GlobalTestData.PRODUCT_SETUP_TYPE_NAME, trimmed);
        page().enterProductSetupName("  " + trimmed + "  ");
    }

    @When("User enters maximum allowed characters in Product Setup Name")
    public void userEntersMaximumAllowedCharactersInProductSetupName() {
        page().enterProductSetupName("P".repeat(100));
    }

    @When("User enters only special characters in Product Setup Name")
    public void userEntersOnlySpecialCharactersInProductSetupName() {
        String name = "@#$%^&*!";
        context.set(ScenarioContext.PRODUCT_SETUP_TYPE_NAME, name);
        GlobalTestData.set(GlobalTestData.PRODUCT_SETUP_TYPE_NAME, name);
        page().enterProductSetupName(name);
    }

    @When("User enters very large text in Product Setup Name")
    public void userEntersVeryLargeTextInProductSetupName() {
        String name = "PST_" + "A".repeat(500);
        context.set(ScenarioContext.PRODUCT_SETUP_TYPE_NAME, name);
        GlobalTestData.set(GlobalTestData.PRODUCT_SETUP_TYPE_NAME, name);
        page().enterProductSetupName(name);
    }

    @When("User leaves Product Setup Name empty")
    public void userLeavesProductSetupNameEmpty() {
        page().clearProductSetupNameField();
    }

    @When("User clears Product Setup Name field")
    public void userClearsProductSetupNameField() {
        page().clearProductSetupNameField();
    }

    @When("User enters Description for Product Setup Type")
    public void userEntersDescriptionForProductSetupType() {
        page().enterDescription("Product Setup Type description");
    }

    @When("User enters long text in Product Setup Type Description field")
    public void userEntersLongTextInProductSetupTypeDescriptionField() {
        page().enterDescription("Long product setup type description ".repeat(20));
    }

    @When("User leaves Description empty for Product Setup Type")
    public void userLeavesDescriptionEmptyForProductSetupType() {
        System.out.println("[INFO] Description left empty — optional field");
    }

    // ═══════════════════════════════════════════════════════
    //  TIMER INPUT
    // ═══════════════════════════════════════════════════════

    @When("User sets Machine Output Timer to {int} hours {int} minutes {int} seconds")
    public void userSetsMachineOutputTimer(int hours, int minutes, int seconds) {
        page().setMachineOutputTimer(hours, minutes, seconds);
    }

    @When("User sets Product Setup Timer to {int} hours {int} minutes {int} seconds")
    public void userSetsProductSetupTimer(int hours, int minutes, int seconds) {
        page().setProductSetupTimer(hours, minutes, seconds);
    }

    @When("User taps Machine Output Timer field")
    public void userTapsMachineOutputTimerField() {
        page().tapMachineOutputTimerField();
    }

    @When("User taps Product Setup Timer field")
    public void userTapsProductSetupTimerField() {
        page().tapProductSetupTimerField();
    }

    @When("User clicks Save button on duration picker")
    public void userClicksSaveButtonOnDurationPicker() {
        elementUtils.clickWhenFoundByAccessibility("Save", 10);
    }

    @When("User clicks Close \"X\" button on duration picker")
    public void userClicksCloseXButtonOnDurationPicker() {
        page().clickDurationPickerCloseButton();
    }

    // ═══════════════════════════════════════════════════════
    //  POPUP BUTTONS
    // ═══════════════════════════════════════════════════════

    @When("User clicks Close \"X\" button on Product Setup Type popup")
    public void userClicksCloseXButtonOnProductSetupTypePopup() {
        page().clickCloseButton();
    }

    @When("User clicks Submit button on Product Setup Type popup")
    public void userClicksSubmitButtonOnProductSetupTypePopup() {
        page().clickSubmitButton();
    }

    @When("User clicks Submit button multiple times quickly on Product Setup Type popup")
    public void userClicksSubmitButtonMultipleTimesQuicklyOnProductSetupTypePopup() {
        page().clickSubmitButtonMultipleTimes();
    }

    @When("User opens Add Product Setup Type popup")
    public void userOpensAddProductSetupTypePopup() {
        page().clickAddButton();
    }

    @When("User reopens Add Product Setup Type popup")
    public void userReopensAddProductSetupTypePopup() {
        page().clickAddButton();
    }

    // ═══════════════════════════════════════════════════════
    //  CREATE — POSITIVE OUTCOMES
    // ═══════════════════════════════════════════════════════

    @Then("Product Setup Type should be created successfully")
    public void productSetupTypeShouldBeCreatedSuccessfully() {
        Assert.assertTrue(page().waitForCreateSuccess(30),
                "Product Setup Type not created — did not return to list screen within 30s");
    }

    @Then("newly created Product Setup Type should be displayed in Product Setup Types list screen")
    public void newlyCreatedProductSetupTypeShouldBeDisplayedInListScreen() {
        String name = storedName();
        if (name != null) {
            page().searchRecord(name);
            Assert.assertNotNull(page().getRecordByName(name),
                    "Product Setup Type not found in list after creation: " + name);
            page().exitSearch();
        }
    }

    @Then("system should trim spaces and create Product Setup Type successfully")
    public void systemShouldTrimSpacesAndCreateProductSetupTypeSuccessfully() {
        Assert.assertTrue(page().waitForCreateSuccess(30),
                "Product Setup Type not created after entering spaces around name");
    }

    @Then("system should accept optional Product Setup Type input without error")
    public void systemShouldAcceptOptionalProductSetupTypeInputWithoutError() {
        Assert.assertFalse(page().isAnyValidationErrorDisplayed(),
                "Unexpected validation error for optional Description input");
    }

    // ═══════════════════════════════════════════════════════
    //  CREATE — NEGATIVE OUTCOMES
    // ═══════════════════════════════════════════════════════

    @Then("\"This field is required\" error should be displayed for Product Setup Name")
    public void thisFieldIsRequiredErrorForProductSetupName() {
        Assert.assertTrue(page().isNameRequiredErrorDisplayed(),
                "\"This field is required\" not displayed below Product Setup Name field");
    }

    @Then("\"Duration should be at least 1 minute\" alert should be displayed")
    public void durationShouldBeAtLeast1MinuteAlertShouldBeDisplayed() {
        Assert.assertTrue(page().isDurationMinimumAlertDisplayed(),
                "\"Duration should be at least 1 minute\" alert not displayed");
    }

    @Then("\"This field is required\" error should be displayed for Machine Output Timer")
    public void thisFieldIsRequiredErrorForMachineOutputTimer() {
        Assert.assertTrue(page().isMachineOutputTimerRequiredErrorDisplayed(),
                "\"This field is required\" not displayed below Machine Output Timer field");
    }

    @Then("\"This field is required\" error should be displayed for Product Setup Timer")
    public void thisFieldIsRequiredErrorForProductSetupTimer() {
        Assert.assertTrue(page().isProductSetupTimerRequiredErrorDisplayed(),
                "\"This field is required\" not displayed below Product Setup Timer field");
    }

    @Then("validation messages should be displayed for Product Setup Type fields")
    public void validationMessagesShouldBeDisplayedForProductSetupTypeFields() {
        Assert.assertTrue(page().isAllRequiredErrorsDisplayed(),
                "Expected \"This field is required\" on Product Setup Name, Machine Output Timer, and Product Setup Timer");
    }

    // ═══════════════════════════════════════════════════════
    //  POPUP CLOSE / RETURN
    // ═══════════════════════════════════════════════════════

    @Then("Product Setup Type popup should be closed without saving data")
    public void productSetupTypePopupShouldBeClosedWithoutSavingData() {
        Assert.assertTrue(page().waitForReturnToList(10),
                "Popup did not close — list screen not visible");
    }

    @And("User should return to Product Setup Types list screen")
    public void userShouldReturnToProductSetupTypesListScreen() {
        Assert.assertTrue(page().waitForReturnToList(10),
                "Not returned to Product Setup Types list screen");
    }

    // ═══════════════════════════════════════════════════════
    //  UI VALIDATION
    // ═══════════════════════════════════════════════════════

    @Then("Product Setup Type name field should be empty")
    public void productSetupTypeNameFieldShouldBeEmpty() {
        String name = page().getProductSetupNameValue();
        Assert.assertTrue(name == null || name.trim().isEmpty(),
                "Product Setup Name field should be empty after reopen but was: '" + name + "'");
    }

    @When("User clicks on \"Yes, Exit\" button on the confirmation popup")
    public void userClicksOnYesExitButtonOnTheConfirmationPopup() {
        page().clickYesExitButton();
    }
}
