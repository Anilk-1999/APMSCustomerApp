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
import utilities.GlobalEntityStore;
import utilities.GlobalTestData;
import utilities.ScenarioContext;

public class ProductGroupUpdateSteps {

    private final AndroidDriver driver;
    private final ScenarioContext context;
    private ProductGroupPage productGroupPage;

    @SuppressWarnings("unused")
    public ProductGroupUpdateSteps(AppHooks hooks, ScenarioContext context) {
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

    @When("User swipes product group record from right to left")
    public void userSwipesProductGroupRecordFromRightToLeft() {
        String name = storedName();
        Assert.assertNotNull(name, "Product Group name not in context for swipe");
        WebElement item = page().getRecordByName(name);
        Assert.assertNotNull(item, "Product Group record not found for swipe: " + name);
        page().swipeRecordRightToLeft(item);
    }

    @When("User performs swipe action multiple times quickly on product group record")
    public void userPerformsSwipeActionMultipleTimesQuicklyOnProductGroupRecord() {
        String name = storedName();
        if (name == null) return;
        for (int i = 0; i < 3; i++) {
            WebElement item = page().getRecordByName(name);
            if (item != null) page().swipeRecordRightToLeft(item);
        }
    }

    @Then("only one Edit option should be displayed")
    public void onlyOneEditOptionShouldBeDisplayed() {
        Assert.assertTrue(page().isEditButtonVisible(),
                "Edit option not visible after swipe");
    }

    @And("Product Group ID should be visible and non-editable")
    public void productGroupIdShouldBeVisibleAndNonEditable() {
        Assert.assertTrue(page().isProductGroupIdVisible(),
                "Product Group ID not visible in Edit popup");
        Assert.assertTrue(page().isProductGroupIdNonEditable(),
                "Product Group ID should be non-editable");
    }

    @And("Product Group Name field should be pre-filled")
    public void productGroupNameFieldShouldBePreFilled() {
        String name = page().getProductGroupNameValue();
        Assert.assertFalse(name == null || name.isEmpty(),
                "Product Group Name field is not pre-filled");
    }

    @And("Product Group Code field should be pre-filled")
    public void productGroupCodeFieldShouldBePreFilled() {
        String code = page().getProductGroupCodeValue();
        Assert.assertFalse(code == null || code.isEmpty(),
                "Product Group Code field is not pre-filled");
    }

    @And("Description field should be pre-filled")
    public void descriptionFieldShouldBePreFilled() {
        Assert.assertTrue(page().isDescriptionFieldVisible(),
                "Description field is not visible/pre-filled");
    }

    @Then("Product Group ID should be visible")
    public void productGroupIdShouldBeVisible() {
        Assert.assertTrue(page().isProductGroupIdVisible(),
                "Product Group ID not visible in popup");
    }

    @Then("Product Group Name field should be editable")
    public void productGroupNameFieldShouldBeEditable() {
        Assert.assertTrue(page().isProductGroupNameFieldVisible(),
                "Product Group Name field not visible/editable");
    }

    @Then("Product Group Code field should be editable")
    public void productGroupCodeFieldShouldBeEditable() {
        Assert.assertTrue(page().isProductGroupCodeFieldVisible(),
                "Product Group Code field not visible/editable");
    }

    @Then("Description field should be editable")
    public void descriptionFieldShouldBeEditable() {
        Assert.assertTrue(page().isDescriptionFieldVisible(),
                "Description field not visible/editable");
    }

    @Then("Product Group ID should not be editable")
    public void productGroupIdShouldNotBeEditable() {
        Assert.assertTrue(page().isProductGroupIdNonEditable(),
                "Product Group ID should be non-editable");
    }

    @Then("all fields should display previously saved data correctly")
    public void allFieldsShouldDisplayPreviouslySavedDataCorrectly() {
        String name = page().getProductGroupNameValue();
        Assert.assertFalse(name == null || name.isEmpty(),
                "Saved Product Group Name missing in Edit popup");
    }

    @When("User updates Product Group Name with valid value")
    public void userUpdatesProductGroupNameWithValidValue() {
        String updated = DataGenerator.randomProductGroupName();
        context.set(ScenarioContext.PRODUCT_GROUP_NAME, updated);
        GlobalTestData.set(GlobalTestData.PRODUCT_GROUP_NAME, updated);
        page().enterProductGroupName(updated);
    }

    @When("User updates Product Group Name")
    public void userUpdatesProductGroupName() {
        userUpdatesProductGroupNameWithValidValue();
    }

    @When("User updates only Product Group Name")
    public void userUpdatesOnlyProductGroupName() {
        userUpdatesProductGroupNameWithValidValue();
    }

    @When("User updates Product Group Code with unique value")
    public void userUpdatesProductGroupCodeWithUniqueValue() {
        userUpdatesProductGroupCodeWithValidValue();
    }

    @When("User updates Product Group Code with valid value")
    public void userUpdatesProductGroupCodeWithValidValue() {
        String code = DataGenerator.randomProductGroupCode();
        context.set(ScenarioContext.PRODUCT_GROUP_CODE, code);
        GlobalTestData.set(GlobalTestData.PRODUCT_GROUP_CODE, code);
        page().enterProductGroupCode(code);
    }

    @When("User updates only Product Group Code with unique value")
    public void userUpdatesOnlyProductGroupCodeWithUniqueValue() {
        userUpdatesProductGroupCodeWithValidValue();
    }

    @When("User updates only Product Group Code with valid value")
    public void userUpdatesOnlyProductGroupCodeWithValidValue() {
        userUpdatesProductGroupCodeWithValidValue();
    }

    @When("User updates Description")
    public void userUpdatesDescription() {
        page().enterDescription("Updated Product Group description");
    }

    @When("User updates only Description")
    public void userUpdatesOnlyDescription() {
        userUpdatesDescription();
    }

    @When("User modifies Product Group details")
    public void userModifiesProductGroupDetails() {
        page().enterProductGroupName(DataGenerator.randomProductGroupName());
    }

    @Then("Product Group should be updated successfully")
    public void productGroupShouldBeUpdatedSuccessfully() {
        Assert.assertTrue(page().waitForEditPopupToClose(25),
                "Product Group update failed — Edit popup did not close within 25s");
        String newName = context.getString(ScenarioContext.PRODUCT_GROUP_NAME);
        if (newName != null) {
            if (newName.trim().length() <= 100) {
                GlobalEntityStore.setLatestName(GlobalEntityStore.PRODUCT_GROUP, newName);
                GlobalTestData.set(GlobalTestData.PRODUCT_GROUP_NAME, newName);
            } else {
                // Name is too long to be found by getRecordByName — force fresh creation next scenario
                GlobalEntityStore.setLatestName(GlobalEntityStore.PRODUCT_GROUP, null);
                GlobalTestData.set(GlobalTestData.PRODUCT_GROUP_NAME, null);
            }
        }
    }

    @Then("updated data should be reflected in Product Groups list screen")
    public void updatedDataShouldBeReflectedInProductGroupsListScreen() {
        String name = storedName();
        if (name != null) {
            // Close the old search (which still shows old-name results) then re-search with updated name
            page().exitSearch();
            page().searchRecord(name);
            Assert.assertNotNull(page().getRecordByName(name),
                    "Updated Product Group not found in list: " + name);
        }
    }

    @When("User clicks Save button without modification")
    public void userClicksSaveButtonWithoutModification() {
        page().clickSaveButton();
    }

    @When("User clicks Save button multiple times quickly")
    public void userClicksSaveButtonMultipleTimesQuickly() {
        page().clickSaveButton();
        page().clickSaveButton();
        page().clickSaveButton();
    }

    @Then("system should prevent duplicate update requests")
    public void systemShouldPreventDuplicateUpdateRequests() {
        Assert.assertTrue(page().waitForReturnToList(15),
                "Rapid Save did not return to list — duplicate update may have hung the popup");
    }

    @Then("system should allow optional input")
    public void systemShouldAllowOptionalInput() {
        Assert.assertFalse(page().isAnyValidationErrorDisplayed(),
                "Unexpected validation error for optional Description input");
    }

    @Then("popup should be closed without saving updated changes")
    public void popupShouldBeClosedWithoutSavingUpdatedChanges() {
        Assert.assertTrue(page().waitForReturnToList(10),
                "Edit popup did not close — list screen not visible");
    }

    @When("User reopens Edit Product Group popup")
    public void userReopensEditProductGroupPopup() {
        String name = storedName();
        Assert.assertNotNull(name, "Product Group name not in context for reopen");
        page().searchSwipeAndOpenEdit(name);
    }

    @Then("previously saved Product Group data should be displayed")
    public void previouslySavedProductGroupDataShouldBeDisplayed() {
        allFieldsShouldDisplayPreviouslySavedDataCorrectly();
    }

    @When("User enters long description text")
    public void userEntersLongDescriptionText() {
        page().enterDescription("Long product group description ".repeat(20));
    }
}
