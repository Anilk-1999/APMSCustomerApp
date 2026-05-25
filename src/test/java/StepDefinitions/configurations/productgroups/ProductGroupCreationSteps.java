package StepDefinitions.configurations.productgroups;

import hooks.AppHooks;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObject.configurations.ProductGroupPage;
import utilities.DataGenerator;
import utilities.ElementUtils;
import utilities.GlobalEntityStore;
import utilities.GlobalTestData;
import utilities.ScenarioContext;

public class ProductGroupCreationSteps {

    protected final AndroidDriver  driver;
    protected final ScenarioContext context;
    protected final ElementUtils    elementUtils;
    protected ProductGroupPage productGroupPage;

    @SuppressWarnings("unused")
    public ProductGroupCreationSteps(AppHooks hooks, ScenarioContext context) {
        this.driver       = AppHooks.getDriver();
        this.context      = context;
        this.elementUtils = new ElementUtils(driver);
    }

    protected ProductGroupPage page() {
        if (productGroupPage == null) productGroupPage = new ProductGroupPage(driver);
        return productGroupPage;
    }

    protected String storedName() {
        return context.getString(ScenarioContext.PRODUCT_GROUP_NAME);
    }

    // ═══════════════════════════════════════════════════════
    //  BACKGROUND — shared by all 3 feature files
    // ═══════════════════════════════════════════════════════

    /**
     * Creates ONE Product Group per test run and reuses it for all scenarios.
     * After creation, waits for the Search button — the only list-screen-unique signal —
     * before returning, so the next scenario step always finds the list ready.
     */
    @And("User has already created a Product Group")
    public void userHasAlreadyCreatedAProductGroup() {
        String name = GlobalEntityStore.getLatestName(GlobalEntityStore.PRODUCT_GROUP);
        String code;
        if (name == null) {
            String[] details = page().createProductGroupAndReturnDetails();
            name = details[0];
            code = details[1];
            // Wait for list screen's Search button — confirms navigation back to list is complete
            elementUtils.waitForPresence(AppiumBy.accessibilityId("Search"), 15);
            GlobalEntityStore.setLatestName(GlobalEntityStore.PRODUCT_GROUP, name);
            GlobalTestData.set(GlobalTestData.PRODUCT_GROUP_NAME, name);
            GlobalTestData.set(GlobalTestData.PRODUCT_GROUP_CODE, code);
        } else {
            code = GlobalTestData.get(GlobalTestData.PRODUCT_GROUP_CODE);
        }
        context.set(ScenarioContext.PRODUCT_GROUP_NAME, name);
        if (code != null) context.set(ScenarioContext.PRODUCT_GROUP_CODE, code);
    }

    // ═══════════════════════════════════════════════════════
    //  LIST SCREEN
    // ═══════════════════════════════════════════════════════

    @When("User clicks on \"+ Add\" button in Product Groups list screen")
    public void userClicksOnAddButtonInProductGroupsListScreen() {
        page().clickAddButton();
    }

    // ═══════════════════════════════════════════════════════
    //  SEARCH
    // ═══════════════════════════════════════════════════════

    @When("User enters newly created Product Group Name")
    public void userEntersNewlyCreatedProductGroupName() {
        String name = storedName();
        Assert.assertNotNull(name, "Product Group name not in ScenarioContext");
        page().enterSearchText(name);
    }

    @When("User searches for newly created Product Group Name")
    public void userSearchesForNewlyCreatedProductGroupName() {
        String name = storedName();
        Assert.assertNotNull(name, "Product Group name not in ScenarioContext");
        page().searchForProductGroup(name);
    }

    @Then("system should display matching product group results")
    public void systemShouldDisplayMatchingProductGroupResults() {
        String name = storedName();
        if (name != null) {
            Assert.assertNotNull(page().getRecordByName(name),
                    "No matching Product Group result for: " + name);
        }
    }

    @And("User verifies product group appears in list")
    public void userVerifiesProductGroupAppearsInList() {
        systemShouldDisplayMatchingProductGroupResults();
    }

    // ═══════════════════════════════════════════════════════
    //  POPUP FIELD VISIBILITY
    // ═══════════════════════════════════════════════════════

    @And("Product Group Name field should be visible")
    public void productGroupNameFieldShouldBeVisible() {
        Assert.assertTrue(page().isProductGroupNameFieldVisible(),
                "Product Group Name field not visible in popup");
    }

    @And("Product Group Code field should be visible")
    public void productGroupCodeFieldShouldBeVisible() {
        Assert.assertTrue(page().isProductGroupCodeFieldVisible(),
                "Product Group Code field not visible in popup");
    }

    @And("Description field should be visible")
    public void descriptionFieldShouldBeVisible() {
        Assert.assertTrue(page().isDescriptionFieldVisible(),
                "Description field not visible in popup");
    }

    // ═══════════════════════════════════════════════════════
    //  FIELD INPUT — CREATE
    // ═══════════════════════════════════════════════════════

    @When("User enters valid Product Group Name")
    public void userEntersValidProductGroupName() {
        String name = DataGenerator.randomProductGroupName();
        context.set(ScenarioContext.PRODUCT_GROUP_NAME, name);
        page().enterProductGroupName(name);
    }

    @When("User enters existing Product Group Name")
    public void userEntersExistingProductGroupName() {
        String name = storedName();
        Assert.assertNotNull(name, "No existing Product Group name in context");
        page().enterProductGroupName(name);
    }

    @When("User enters only spaces in Product Group Name")
    public void userEntersOnlySpacesInProductGroupName() {
        page().enterProductGroupName("   ");
    }

    @When("User enters only spaces in Product Group Name field")
    public void userEntersOnlySpacesInProductGroupNameField() {
        context.set(ScenarioContext.PRODUCT_GROUP_NAME, "   ");
        GlobalTestData.set(GlobalTestData.PRODUCT_GROUP_NAME, "   ");
        page().enterProductGroupName("   ");
    }

    @When("User enters only special characters in Name and Code")
    public void userEntersOnlySpecialCharactersInNameAndCode() {
        String name = "@#$%^&*!";
        context.set(ScenarioContext.PRODUCT_GROUP_NAME, name);
        GlobalTestData.set(GlobalTestData.PRODUCT_GROUP_NAME, name);
        page().enterProductGroupName(name);
        page().enterProductGroupCode("@#$%");
    }

    @When("User enters only special characters in Product Group Name and Product Group Code")
    public void userEntersOnlySpecialCharactersInProductGroupNameAndProductGroupCode() {
        userEntersOnlySpecialCharactersInNameAndCode();
    }

    @When("User enters Product Group Name with leading and trailing spaces")
    public void userEntersProductGroupNameWithLeadingAndTrailingSpaces() {
        String trimmed = DataGenerator.randomProductGroupName();
        context.set(ScenarioContext.PRODUCT_GROUP_NAME, trimmed);
        GlobalTestData.set(GlobalTestData.PRODUCT_GROUP_NAME, trimmed);
        page().enterProductGroupName("  " + trimmed + "  ");
    }

    @When("User enters maximum allowed characters in Product Group Name")
    public void userEntersMaximumAllowedCharactersInProductGroupName() {
        page().enterProductGroupName("A".repeat(100));
    }

    @When("User leaves Product Group Name empty")
    public void userLeavesProductGroupNameEmpty() {
        page().clearProductGroupNameField();
    }

    @When("User clears Product Group Name field")
    public void userClearsProductGroupNameField() {
        page().clearProductGroupNameField();
    }

    @When("User enters valid unique Product Group Code")
    public void userEntersValidUniqueProductGroupCode() {
        userEntersValidProductGroupCode();
    }

    @When("User enters valid Product Group Code")
    public void userEntersValidProductGroupCode() {
        String code = DataGenerator.randomProductGroupCode();
        context.set(ScenarioContext.PRODUCT_GROUP_CODE, code);
        page().enterProductGroupCode(code);
    }

    @When("User enters existing Product Group Code")
    public void userEntersExistingProductGroupCode() {
        String code = context.getString(ScenarioContext.PRODUCT_GROUP_CODE);
        Assert.assertNotNull(code, "No existing Product Group code in context");
        page().enterProductGroupCode(code);
    }

    @When("User enters maximum allowed characters in Product Group Code")
    public void userEntersMaximumAllowedCharactersInProductGroupCode() {
        page().enterProductGroupCode("C".repeat(50));
    }

    @When("User enters Product Group Code with spaces")
    public void userEntersProductGroupCodeWithSpaces() {
        page().enterProductGroupCode("CODE 123");
    }

    @When("User leaves Product Group Code empty")
    public void userLeavesProductGroupCodeEmpty() {
        page().clearProductGroupCodeField();
    }

    @When("User clears Product Group Code field")
    public void userClearsProductGroupCodeField() {
        page().clearProductGroupCodeField();
    }

    @When("User enters Description")
    public void userEntersDescription() {
        page().enterDescription("Product Group description");
    }

    @When("User enters long text in Description field")
    public void userEntersLongTextInDescriptionField() {
        page().enterDescription("Long product group description ".repeat(20));
    }

    @When("User leaves Description empty")
    public void userLeavesDescriptionEmpty() {
        System.out.println("[INFO] Description left empty — optional field");
    }

    @When("User enters very large text beyond allowed limit")
    public void userEntersVeryLargeTextBeyondAllowedLimit() {
        String name = "A".repeat(500);
        context.set(ScenarioContext.PRODUCT_GROUP_NAME, name);
        GlobalTestData.set(GlobalTestData.PRODUCT_GROUP_NAME, name);
        page().enterProductGroupName(name);
    }

    // ═══════════════════════════════════════════════════════
    //  POPUP BUTTONS
    // ═══════════════════════════════════════════════════════

    @When("User clicks Close \"X\" button")
    public void userClicksCloseXButton() {
        page().clickCloseButton();
    }

    @When("User clicks Submit button multiple times quickly")
    public void userClicksSubmitButtonMultipleTimesQuickly() {
        page().clickSubmitButtonMultipleTimes();
    }

    @When("User opens Add Product Group popup")
    public void userOpensAddProductGroupPopup() {
        page().clickAddButton();
    }

    @When("User closes Add Product Group popup")
    public void userClosesAddProductGroupPopup() {
        page().clickCloseButton();
    }

    @When("User reopens Add Product Group popup")
    public void userReopensAddProductGroupPopup() {
        page().clickAddButton();
    }

    // ═══════════════════════════════════════════════════════
    //  CREATE — POSITIVE OUTCOMES
    // ═══════════════════════════════════════════════════════

    @Then("Product Group should be created successfully")
    public void productGroupShouldBeCreatedSuccessfully() {
        Assert.assertTrue(page().waitForCreateSuccess(30),
                "Product Group not created — did not return to list screen within 30s");
    }

    @Then("newly created Product Group should be displayed in Product Groups list screen")
    public void newlyCreatedProductGroupShouldBeDisplayedInProductGroupsListScreen() {
        String name = storedName();
        if (name != null) {
            page().searchRecord(name);
            Assert.assertNotNull(page().getRecordByName(name),
                    "Product Group not found in list after creation: " + name);
            page().exitSearch();
        }
    }

    @Then("system should trim spaces and create Product Group successfully")
    public void systemShouldTrimSpacesAndCreateProductGroupSuccessfully() {
        Assert.assertTrue(page().waitForCreateSuccess(30),
                "Product Group not created after entering spaces around name");
    }

    @Then("system should accept optional input without error")
    public void systemShouldAcceptOptionalInputWithoutError() {
        Assert.assertFalse(page().isAnyValidationErrorDisplayed(),
                "Unexpected validation error for optional Description input");
    }

    // ═══════════════════════════════════════════════════════
    //  CREATE — NEGATIVE OUTCOMES
    // ═══════════════════════════════════════════════════════

    @Then("\"This field is required\" error should be displayed for Product Group Name")
    public void thisFieldIsRequiredErrorForProductGroupName() {
        Assert.assertTrue(page().isNameRequiredErrorDisplayed(),
                "\"This field is required\" not displayed below Product Group Name field");
    }

    @Then("\"This field is required\" error should be displayed for Product Group Code")
    public void thisFieldIsRequiredErrorForProductGroupCode() {
        Assert.assertTrue(page().isCodeRequiredErrorDisplayed(),
                "\"This field is required\" not displayed below Product Group Code field");
    }

    @Then("validation messages should be displayed below respective fields")
    public void validationMessagesShouldBeDisplayedBelowRespectiveFields() {
        Assert.assertTrue(page().isAnyValidationErrorDisplayed(),
                "No validation messages displayed below fields");
    }

    // ═══════════════════════════════════════════════════════
    //  POPUP CLOSE / RETURN
    // ═══════════════════════════════════════════════════════

    @Then("popup should be closed without saving data")
    public void popupShouldBeClosedWithoutSavingData() {
        Assert.assertTrue(page().waitForReturnToList(10),
                "Popup did not close — list screen not visible");
    }

    @And("User should return to Product Groups list screen")
    public void userShouldReturnToProductGroupsListScreen() {
        Assert.assertTrue(page().waitForReturnToList(10),
                "Not returned to Product Groups list screen");
    }

    // ═══════════════════════════════════════════════════════
    //  UI VALIDATION
    // ═══════════════════════════════════════════════════════

    @Then("all fields should be reset")
    public void allFieldsShouldBeReset() {
        String name = page().getProductGroupNameValue();
        String code = page().getProductGroupCodeValue();
        Assert.assertTrue(name == null || name.trim().isEmpty(),
                "Product Group Name field should be empty after reopen but was: '" + name + "'");
        Assert.assertTrue(code == null || code.trim().isEmpty(),
                "Product Group Code field should be empty after reopen but was: '" + code + "'");
    }

    @Then("Product Group Name and Code should accept valid input")
    public void productGroupNameAndCodeShouldAcceptValidInput() {
        Assert.assertTrue(page().isProductGroupNameFieldVisible(),
                "Product Group Name field not visible/accepting input");
        Assert.assertTrue(page().isProductGroupCodeFieldVisible(),
                "Product Group Code field not visible/accepting input");
    }

    @And("Description field should allow optional text")
    public void descriptionFieldShouldAllowOptionalText() {
        Assert.assertFalse(page().isAnyValidationErrorDisplayed(),
                "Description field should be optional — unexpected validation error displayed");
    }

    @When("User clicks on \"Yes, Exit\" button on the confirmation popup")
    public void userClicksOnYesExitButtonOnTheConfirmationPopup() {
        page().clickYesExitButton();
    }

    @When("User triggers validation errors")
    public void userTriggersValidationErrors() {
        page().clearProductGroupNameField();
        page().clearProductGroupCodeField();
    }

    @When("User creates Product Group with valid data")
    public void userCreatesProductGroupWithValidData() {
        String name = DataGenerator.randomProductGroupName();
        String code = DataGenerator.randomProductGroupCode();
        context.set(ScenarioContext.PRODUCT_GROUP_NAME, name);
        context.set(ScenarioContext.PRODUCT_GROUP_CODE, code);
        page().clickAddButton();
        page().enterProductGroupName(name);
        page().enterProductGroupCode(code);
        page().clickSubmitButton();
    }
}
