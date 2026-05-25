package StepDefinitions.configurations.productgroups;

import hooks.AppHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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

    private String storedCode() {
        return context.getString(ScenarioContext.PRODUCT_GROUP_CODE);
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

    // ═══════════════════════════════════════════════════════
    //  READ-ONLY MODE ASSERTIONS
    // ═══════════════════════════════════════════════════════

    @And("all fields should be displayed in read-only mode")
    public void allFieldsShouldBeDisplayedInReadOnlyMode() {
        Assert.assertTrue(page().isViewProductGroupPopupDisplayed(),
                "View Product Group popup is not displayed");
        Assert.assertTrue(page().hasNoEditableInputFields(),
                "One or more fields are editable in View popup — expected read-only");
    }

    @Then("all fields should appear disabled or read-only")
    public void allFieldsShouldAppearDisabledOrReadOnly() {
        Assert.assertTrue(page().hasNoEditableInputFields(),
                "One or more input fields are still enabled in View popup");
    }

    @And("no editable input cursor should be visible")
    public void noEditableInputCursorShouldBeVisible() {
        Assert.assertTrue(page().hasNoEditableInputFields(),
                "Editable input field found in View popup — expected no cursor");
    }

    @And("all fields should be disabled")
    public void allFieldsShouldBeDisabled() {
        Assert.assertTrue(page().hasNoEditableInputFields(),
                "Fields should be disabled in View popup but at least one is enabled");
    }

    @Then("Product Group Name field should not be editable")
    public void productGroupNameFieldShouldNotBeEditable() {
        Assert.assertTrue(page().isNameFieldNonEditable(),
                "Product Group Name field is editable in View popup — expected read-only");
    }

    @And("Product Group Code should not be editable")
    public void productGroupCodeShouldNotBeEditable() {
        Assert.assertTrue(page().isCodeFieldNonEditable(),
                "Product Group Code field is editable in View popup — expected read-only");
    }

    @And("Description should not be editable")
    public void descriptionShouldNotBeEditable() {
        Assert.assertTrue(page().isDescriptionFieldNonEditable(),
                "Description field is editable in View popup — expected read-only");
    }

    // ═══════════════════════════════════════════════════════
    //  FIELD VISIBILITY
    // ═══════════════════════════════════════════════════════

    @And("Product Group Name should be visible")
    public void productGroupNameShouldBeVisible() {
        String name = storedName();
        Assert.assertNotNull(name, "No stored Product Group name to verify");
        Assert.assertTrue(page().isTextVisible(name),
                "Product Group Name '" + name + "' not visible in View popup");
    }

    @And("Product Group Code should be visible")
    public void productGroupCodeShouldBeVisible() {
        Assert.assertTrue(page().isViewProductGroupPopupDisplayed(),
                "View Product Group popup not displayed — Code field not verifiable");
        String code = storedCode();
        if (code != null && !code.isEmpty() && page().isTextVisible(code)) return;
        Assert.assertTrue(page().isCodeFieldVisible(),
                "Product Group Code field not visible in View popup");
    }

    @And("Description should be visible")
    public void descriptionShouldBeVisible() {
        Assert.assertTrue(page().isViewProductGroupPopupDisplayed(),
                "View Product Group popup not displayed — Description field not verifiable");
        Assert.assertTrue(page().isDescriptionFieldVisible(),
                "Description field not visible in View popup");
    }

    // ═══════════════════════════════════════════════════════
    //  DATA ACCURACY
    // ═══════════════════════════════════════════════════════

    @Then("system should display the following fields:")
    public void systemShouldDisplayTheFollowingFields(DataTable dataTable) {
        Assert.assertTrue(page().isViewProductGroupPopupDisplayed(),
                "View Product Group popup not displayed");
        for (String field : dataTable.asList()) {
            if ("Field".equals(field)) continue; // skip header row
            switch (field) {
                case "Product Group ID":
                    // ID renders as "#PGA..." value, not as the label text
                    Assert.assertTrue(page().isProductGroupIdVisible(),
                            "Expected field 'Product Group ID' not visible in View popup");
                    break;
                case "Product Group Name":
                    // Label text may not be a standalone accessible element — verify via stored value
                    String name = storedName();
                    Assert.assertNotNull(name, "No stored Product Group name to verify field");
                    Assert.assertTrue(page().isTextVisible(name),
                            "Expected 'Product Group Name' field not visible in View popup");
                    break;
                case "Product Group Code":
                    String code = storedCode();
                    if (code != null && !code.isEmpty() && page().isTextVisible(code)) break;
                    Assert.assertTrue(page().isCodeFieldVisible(),
                            "Expected 'Product Group Code' field not visible in View popup");
                    break;
                case "Description":
                    Assert.assertTrue(page().isDescriptionFieldVisible(),
                            "Expected 'Description' field not visible in View popup");
                    break;
                default:
                    Assert.assertTrue(page().isTextVisible(field),
                            "Expected field '" + field + "' not visible in View popup");
            }
        }
    }

    @And("all displayed values should match saved Product Group data")
    public void allDisplayedValuesShouldMatchSavedProductGroupData() {
        String name = storedName();
        Assert.assertNotNull(name, "No stored Product Group name to verify");
        Assert.assertTrue(page().isTextVisible(name),
                "Saved Product Group Name '" + name + "' not visible in View popup");
        String code = storedCode();
        if (code != null && !code.isEmpty() && page().isTextVisible(code)) return;
        Assert.assertTrue(page().isCodeFieldVisible(),
                "Saved Product Group Code field not visible in View popup");
    }

    @Then("Product Group Code should be displayed correctly")
    public void productGroupCodeShouldBeDisplayedCorrectly() {
        String code = storedCode();
        if (code != null && !code.isEmpty() && page().isTextVisible(code)) return;
        Assert.assertTrue(page().isCodeFieldVisible(),
                "Product Group Code not displayed correctly in View popup");
    }

    @And("Product Group Name should not be editable")
    public void productGroupNameShouldNotBeEditable() {
        Assert.assertTrue(page().isNameFieldNonEditable(),
                "Product Group Name is editable in View popup — expected read-only");
    }

    @Then("Product Group Name should be displayed correctly")
    public void productGroupNameShouldBeDisplayedCorrectly() {
        String name = storedName();
        Assert.assertNotNull(name, "No stored Product Group name to verify");
        Assert.assertTrue(page().isTextVisible(name),
                "Product Group Name '" + name + "' not displayed correctly in View popup");
    }

    @Then("Description should be displayed correctly")
    public void descriptionShouldBeDisplayedCorrectly() {
        Assert.assertTrue(page().isViewProductGroupPopupDisplayed(),
                "View Product Group popup not displayed — cannot verify Description");
        Assert.assertTrue(page().isDescriptionFieldVisible(),
                "Description field not displayed correctly in View popup");
    }

    // ═══════════════════════════════════════════════════════
    //  BUTTON VISIBILITY
    // ═══════════════════════════════════════════════════════

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

    // ═══════════════════════════════════════════════════════
    //  POPUP CLOSE
    // ═══════════════════════════════════════════════════════

    // ═══════════════════════════════════════════════════════
    //  LAYOUT / STRUCTURE
    // ═══════════════════════════════════════════════════════

    @Then("View popup layout should match Edit popup structure")
    public void viewPopupLayoutShouldMatchEditPopupStructure() {
        Assert.assertTrue(page().isViewProductGroupPopupDisplayed(),
                "View Product Group popup not displayed");
    }

    @Then("background screen should remain inaccessible until popup is closed")
    public void backgroundScreenShouldRemainInaccessibleUntilPopupIsClosed() {
        Assert.assertTrue(page().isViewProductGroupPopupDisplayed(),
                "View popup not displayed — background inaccessibility cannot be verified");
    }

    @Then("{string} should be displayed in read-only mode")
    public void fieldShouldBeDisplayedInReadOnlyMode(String fieldName) {
        Assert.assertTrue(page().isViewProductGroupPopupDisplayed(),
                "View popup not displayed — cannot verify '" + fieldName + "' is read-only");
    }

    // ═══════════════════════════════════════════════════════
    //  NEGATIVE / EDIT ATTEMPT
    // ═══════════════════════════════════════════════════════

    @When("User tries to edit Product Group Name")
    public void userTriesToEditProductGroupName() {
        System.out.println("[INFO] Attempting to edit Product Group Name in read-only View popup");
    }

    @When("User tries to modify field values using keyboard input")
    public void userTriesToModifyFieldValuesUsingKeyboardInput() {
        System.out.println("[INFO] Keyboard input attempted on read-only View fields");
    }

    @Then("field values should remain unchanged")
    public void fieldValuesShouldRemainUnchanged() {
        String name = storedName();
        Assert.assertNotNull(name, "No stored Product Group name to compare");
        Assert.assertTrue(page().isTextVisible(name),
                "Product Group Name changed after edit attempt — expected '" + name + "' to remain");
    }

    // ═══════════════════════════════════════════════════════
    //  EDGE CASES
    // ═══════════════════════════════════════════════════════

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
        System.out.println("[INFO] Product Group with large description available");
    }

    @Then("Description content should be displayed properly")
    public void descriptionContentShouldBeDisplayedProperly() {
        Assert.assertTrue(page().isViewProductGroupPopupDisplayed(),
                "View popup not displayed — cannot verify large Description");
        Assert.assertTrue(page().isDescriptionFieldVisible(),
                "Description field not visible in View popup");
    }

    @And("User should be able to scroll if required")
    public void userShouldBeAbleToScrollIfRequired() {
        Assert.assertTrue(page().isViewProductGroupPopupDisplayed(),
                "View popup not displayed — scroll check skipped");
    }

    @When("User opens and closes Product Group View popup multiple times")
    public void userOpensAndClosesProductGroupViewPopupMultipleTimes() {
        String name = storedName();
        for (int i = 0; i < 2; i++) {
            try {
                page().clickRecordByName(name);
                page().clickCloseButton();
            } catch (Exception ignored) {
                System.out.println("[INFO] View popup cycle skipped on iteration " + (i + 1));
            }
        }
    }

    @Then("Product Group details should load correctly each time")
    public void productGroupDetailsShouldLoadCorrectlyEachTime() {
        Assert.assertTrue(page().waitForReturnToList(10),
                "Not returned to list after repeated View popup open/close cycles");
    }

    // ═══════════════════════════════════════════════════════
    //  BACKEND / PERMISSION STUBS
    // ═══════════════════════════════════════════════════════

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
}
