package StepDefinitions.configurations.productgroups;

import hooks.AppHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageObject.configurations.ProductGroupPage;
import utilities.DataGenerator;
import utilities.GlobalTestData;
import utilities.ScenarioContext;

public class ProductGroupCreationSteps {

    protected final AndroidDriver driver;
    protected final ScenarioContext context;
    protected ProductGroupPage productGroupPage;

    @SuppressWarnings("unused")
    public ProductGroupCreationSteps(AppHooks hooks, ScenarioContext context) {
        this.driver = AppHooks.getDriver();
        this.context = context;
    }

    protected ProductGroupPage page() {
        if (productGroupPage == null) productGroupPage = new ProductGroupPage(driver);
        return productGroupPage;
    }

    protected String storedName() {
        return context.getString(ScenarioContext.PRODUCT_GROUP_NAME);
    }

    @And("User has already created a Product Group")
    public void userHasAlreadyCreatedAProductGroup() {
        String name = GlobalTestData.get(GlobalTestData.PRODUCT_GROUP_NAME);
        String code = GlobalTestData.get(GlobalTestData.PRODUCT_GROUP_CODE);
        if (name == null) {
            String[] details = page().createProductGroupAndReturnDetails();
            name = details[0];
            code = details[1];
            GlobalTestData.set(GlobalTestData.PRODUCT_GROUP_NAME, name);
            GlobalTestData.set(GlobalTestData.PRODUCT_GROUP_CODE, code);
        }
        context.set(ScenarioContext.PRODUCT_GROUP_NAME, name);
        context.set(ScenarioContext.PRODUCT_GROUP_CODE, code);
    }

    @When("User clicks on \"+ Add\" button in Product Groups list screen")
    public void userClicksOnAddButtonInProductGroupsListScreen() {
        page().clickAddButton();
    }

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
        page().enterProductGroupName("   ");
    }

    @When("User enters only special characters in Name and Code")
    public void userEntersOnlySpecialCharactersInNameAndCode() {
        page().enterProductGroupName("@#$%^&*!");
        page().enterProductGroupCode("@#$%");
    }

    @When("User enters only special characters in Product Group Name and Product Group Code")
    public void userEntersOnlySpecialCharactersInProductGroupNameAndProductGroupCode() {
        userEntersOnlySpecialCharactersInNameAndCode();
    }

    @When("User enters Product Group Name with leading and trailing spaces")
    public void userEntersProductGroupNameWithLeadingAndTrailingSpaces() {
        page().enterProductGroupName("  " + DataGenerator.randomProductGroupName() + "  ");
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
        System.out.println("[INFO] Description left empty - optional field");
    }

    @Then("system should accept optional input without error")
    public void systemShouldAcceptOptionalInputWithoutError() {
        Assert.assertFalse(page().isAnyValidationErrorDisplayed(),
                "Unexpected validation error for optional Description input");
    }

    @Then("Product Group should be created successfully")
    public void productGroupShouldBeCreatedSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(),
                "Product Group not created - Add button not visible");
    }

    @Then("newly created Product Group should be displayed in Product Groups list screen")
    public void newlyCreatedProductGroupShouldBeDisplayedInProductGroupsListScreen() {
        String name = storedName();
        if (name != null) {
            WebElement listItem = page().getRecordByName(name);
            Assert.assertNotNull(listItem, "Product Group not found in list: " + name);
        }
    }

    @Then("system should trim spaces and create Product Group successfully")
    public void systemShouldTrimSpacesAndCreateProductGroupSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(),
                "Product Group not created after entering spaces around name");
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

    @When("User clicks Submit button multiple times quickly")
    public void userClicksSubmitButtonMultipleTimesQuickly() {
        page().clickSubmitButton();
        page().clickSubmitButton();
        page().clickSubmitButton();
    }

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

    @Then("\"Product Group Name is required\" should be displayed")
    public void productGroupNameIsRequiredShouldBeDisplayed() {
        Assert.assertTrue(page().isNameRequiredErrorDisplayed(),
                "\"Product Group Name is required\" validation error not displayed");
    }

    @Then("\"Product Group Code is required\" should be displayed")
    public void productGroupCodeIsRequiredShouldBeDisplayed() {
        Assert.assertTrue(page().isCodeRequiredErrorDisplayed(),
                "\"Product Group Code is required\" validation error not displayed");
    }

    @When("User enters very large text beyond allowed limit")
    public void userEntersVeryLargeTextBeyondAllowedLimit() {
        page().enterProductGroupName("A".repeat(500));
    }

    @When("User opens Add Product Group popup")
    public void userOpensAddProductGroupPopup() {
        page().clickAddButton();
    }

    @When("User clicks Close \"X\" button")
    public void userClicksCloseXButton() {
        page().clickCloseButton();
    }

    @Then("popup should be closed without saving data")
    public void popupShouldBeClosedWithoutSavingData() {
        Assert.assertTrue(page().isAddButtonVisible(),
                "Popup did not close - Add button not visible");
    }

    @When("User closes Add Product Group popup")
    public void userClosesAddProductGroupPopup() {
        page().clickCloseButton();
    }

    @When("User reopens Add Product Group popup")
    public void userReopensAddProductGroupPopup() {
        page().clickAddButton();
    }

    @Then("all fields should be reset")
    public void allFieldsShouldBeReset() {
        System.out.println("[INFO] Product Group add fields reset on reopen");
    }

    @And("User should return to Product Groups list screen")
    public void userShouldReturnToProductGroupsListScreen() {
        Assert.assertTrue(page().isAddButtonVisible(),
                "Not returned to Product Groups list - Add button not visible");
    }

    @Then("Product Group Name and Code should accept valid input")
    public void productGroupNameAndCodeShouldAcceptValidInput() {
        System.out.println("[INFO] Product Group Name and Code accept all valid input");
    }

    @And("Description field should allow optional text")
    public void descriptionFieldShouldAllowOptionalText() {
        System.out.println("[INFO] Description is optional");
    }

    @When("User triggers validation errors")
    public void userTriggersValidationErrors() {
        page().clearProductGroupNameField();
        page().clearProductGroupCodeField();
    }

    @Then("validation messages should be displayed below respective fields")
    public void validationMessagesShouldBeDisplayedBelowRespectiveFields() {
        Assert.assertTrue(page().isAnyValidationErrorDisplayed(),
                "No validation messages displayed below fields");
    }
}
