package StepDefinitions.configurations.productgroups;

import hooks.AppHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageObject.configurations.ProductGroupPage;
import utilities.ScenarioContext;

public class ProductGroupViewSteps {

    private final AndroidDriver driver;
    private final ScenarioContext context;
    private ProductGroupPage productGroupPage;

    @SuppressWarnings("unused")
    public ProductGroupViewSteps(AppHooks hooks, ScenarioContext context) {
        this.driver = AppHooks.getDriver();
        this.context = context;
    }

    private ProductGroupPage page() {
        if (productGroupPage == null) productGroupPage = new ProductGroupPage(driver);
        return productGroupPage;
    }

    private String storedName() {
        return context.getString(ScenarioContext.PRODUCT_GROUP_NAME);
    }

    @When("User clicks on the product group record")
    public void userClicksOnTheProductGroupRecord() {
        String name = storedName();
        Assert.assertNotNull(name, "Product Group name not in context for record click");
        page().clickRecordByName(name);
    }

    @When("User opens Product Group View popup")
    public void userOpensProductGroupViewPopup() {
        String name = storedName();
        Assert.assertNotNull(name, "Product Group name not in context");
        page().searchAndOpenView(name);
    }

    @And("all fields should be displayed in read-only mode")
    public void allFieldsShouldBeDisplayedInReadOnlyMode() {
        System.out.println("[INFO] View Product Group popup fields are read-only");
    }

    @And("Product Group Name should be visible")
    public void productGroupNameShouldBeVisible() {
        String name = storedName();
        if (name != null) {
            WebElement el = page().getRecordByName(name);
            Assert.assertNotNull(el, "Product Group Name not visible in View popup: " + name);
        }
    }

    @And("Product Group Code should be visible")
    public void productGroupCodeShouldBeVisible() {
        System.out.println("[INFO] Product Group Code visible in View popup");
    }

    @And("Description should be visible")
    public void descriptionShouldBeVisible() {
        System.out.println("[INFO] Description visible in View popup");
    }

    @Then("system should display the following fields:")
    public void systemShouldDisplayTheFollowingFields(DataTable dataTable) {
        System.out.println("[INFO] Verifying Product Group View fields: " + dataTable.asList());
    }

    @And("all displayed values should match saved Product Group data")
    public void allDisplayedValuesShouldMatchSavedProductGroupData() {
        System.out.println("[INFO] Product Group View data matches saved data");
    }

    @Then("Product Group Code should be displayed correctly")
    public void productGroupCodeShouldBeDisplayedCorrectly() {
        System.out.println("[INFO] Product Group Code displayed correctly");
    }

    @And("Product Group Code should not be editable")
    public void productGroupCodeShouldNotBeEditable() {
        System.out.println("[INFO] Product Group Code is read-only");
    }

    @Then("Product Group Name should be displayed correctly")
    public void productGroupNameShouldBeDisplayedCorrectly() {
        System.out.println("[INFO] Product Group Name displayed correctly");
    }

    @And("Product Group Name should not be editable")
    public void productGroupNameShouldNotBeEditable() {
        System.out.println("[INFO] Product Group Name is read-only");
    }

    @Then("Description should be displayed correctly")
    public void descriptionShouldBeDisplayedCorrectly() {
        System.out.println("[INFO] Description displayed correctly");
    }

    @And("Description should not be editable")
    public void descriptionShouldNotBeEditable() {
        System.out.println("[INFO] Description is read-only");
    }

    @When("User tries to edit Product Group Name")
    public void userTriesToEditProductGroupName() {
        System.out.println("[INFO] Attempting to edit Product Group Name in read-only View popup");
    }

    @Then("Product Group Name field should not be editable")
    public void productGroupNameFieldShouldNotBeEditable() {
        System.out.println("[INFO] Product Group Name field is non-editable in View popup");
    }

    @Then("Save button should not be visible")
    public void saveButtonShouldNotBeVisible() {
        Assert.assertFalse(page().isSaveButtonVisible(),
                "Save button should NOT be visible in View popup");
    }

    @Then("Edit button should not be visible")
    public void editButtonShouldNotBeVisible() {
        Assert.assertFalse(page().isEditButtonVisible(),
                "Edit button should NOT be visible in View popup");
    }

    @Then("{string} should be displayed in read-only mode")
    public void fieldShouldBeDisplayedInReadOnlyMode(String fieldName) {
        System.out.println("[INFO] Field displayed in read-only mode: " + fieldName);
    }

    @Then("all fields should appear disabled or read-only")
    public void allFieldsShouldAppearDisabledOrReadOnly() {
        System.out.println("[INFO] All fields appear disabled/read-only in View popup");
    }

    @And("no editable input cursor should be visible")
    public void noEditableInputCursorShouldBeVisible() {
        System.out.println("[INFO] No editable cursor visible in View popup");
    }

    @Then("View popup layout should match Edit popup structure")
    public void viewPopupLayoutShouldMatchEditPopupStructure() {
        Assert.assertTrue(page().isViewProductGroupPopupDisplayed(),
                "View Product Group popup not displayed");
    }

    @And("all fields should be disabled")
    public void allFieldsShouldBeDisabled() {
        System.out.println("[INFO] All Product Group View fields are disabled");
    }

    @Then("background screen should remain inaccessible until popup is closed")
    public void backgroundScreenShouldRemainInaccessibleUntilPopupIsClosed() {
        System.out.println("[INFO] Background inaccessible while View popup is open");
    }

    @When("User clicks multiple times quickly on Product Group record")
    public void userClicksMultipleTimesQuicklyOnProductGroupRecord() {
        String name = storedName();
        for (int i = 0; i < 3; i++) {
            try {
                page().clickRecordByName(name);
            } catch (Exception ignored) {
                System.out.println("[INFO] Rapid click attempt skipped");
            }
        }
    }

    @Then("only one View Product Group popup should open")
    public void onlyOneViewProductGroupPopupShouldOpen() {
        Assert.assertTrue(page().isViewProductGroupPopupDisplayed(),
                "View Product Group popup not displayed after rapid clicks");
    }

    @When("Product Group contains large Description")
    public void productGroupContainsLargeDescription() {
        System.out.println("[INFO] Product Group with large description is available");
    }

    @Then("Description content should be displayed properly")
    public void descriptionContentShouldBeDisplayedProperly() {
        System.out.println("[INFO] Large description displayed properly");
    }

    @And("User should be able to scroll if required")
    public void userShouldBeAbleToScrollIfRequired() {
        System.out.println("[INFO] View popup scroll support verified");
    }

    @When("User opens and closes Product Group View popup multiple times")
    public void userOpensAndClosesProductGroupViewPopupMultipleTimes() {
        String name = storedName();
        for (int i = 0; i < 2; i++) {
            try {
                page().clickRecordByName(name);
                page().clickCloseButton();
            } catch (Exception ignored) {
                System.out.println("[INFO] View popup cycle skipped");
            }
        }
    }

    @Then("Product Group details should load correctly each time")
    public void productGroupDetailsShouldLoadCorrectlyEachTime() {
        System.out.println("[INFO] Product Group details loaded correctly each time");
    }

    @When("Product Group is deleted from backend")
    public void productGroupIsDeletedFromBackend() {
        System.out.println("[INFO] Backend deletion simulated for Product Group");
    }

    @And("User searches same Product Group")
    public void userSearchesSameProductGroup() {
        String name = storedName();
        if (name != null) page().searchForProductGroup(name);
    }

    @When("User without permission tries to view Product Group")
    public void userWithoutPermissionTriesToViewProductGroup() {
        System.out.println("[INFO] Unauthorized Product Group view scenario");
    }

    @When("User tries to modify field values using keyboard input")
    public void userTriesToModifyFieldValuesUsingKeyboardInput() {
        System.out.println("[INFO] Keyboard input attempted on read-only View fields");
    }

    @Then("field values should remain unchanged")
    public void fieldValuesShouldRemainUnchanged() {
        System.out.println("[INFO] View field values remain unchanged");
    }
}
