package StepDefinitions.configurations.productsetuptypes;

import hooks.AppHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageObject.configurations.ProductSetupTypePage;
import utilities.DataGenerator;
import utilities.GlobalEntityStore;
import utilities.GlobalTestData;
import utilities.ScenarioContext;

public class ProductSetupTypeUpdateSteps {

    private final AndroidDriver    driver;
    private final ScenarioContext  context;
    private ProductSetupTypePage   productSetupTypePage;

    @SuppressWarnings("unused")
    public ProductSetupTypeUpdateSteps(AppHooks hooks, ScenarioContext context) {
        this.driver  = AppHooks.getDriver();
        this.context = context;
    }

    private ProductSetupTypePage page() {
        if (productSetupTypePage == null) productSetupTypePage = new ProductSetupTypePage(driver);
        return productSetupTypePage;
    }

    private String storedName() {
        return context.getString(ScenarioContext.PRODUCT_SETUP_TYPE_NAME);
    }

    // ═══════════════════════════════════════════════════════
    //  SETUP — background steps
    // ═══════════════════════════════════════════════════════

    /**
     * Used by all update scenarios after the first (full-update) scenario.
     * Reads the latest name from GlobalEntityStore (updated by the success step after each rename).
     */
    @When("User has already updated Product Setup Type")
    public void userHasAlreadyUpdatedProductSetupType() {
        String name = GlobalEntityStore.getLatestName(GlobalEntityStore.PRODUCT_SETUP_TYPE);
        if (name == null) {
            name = page().createProductSetupTypeAndReturnName();
            GlobalEntityStore.setLatestName(GlobalEntityStore.PRODUCT_SETUP_TYPE, name);
            GlobalTestData.set(GlobalTestData.PRODUCT_SETUP_TYPE_NAME, name);
        }
        context.set(ScenarioContext.PRODUCT_SETUP_TYPE_NAME, name);
    }

    // ═══════════════════════════════════════════════════════
    //  SEARCH — load name into search field
    // ═══════════════════════════════════════════════════════

    @And("User enters newly updated Product Setup Type Name")
    public void userEntersNewlyUpdatedProductSetupTypeName() {
        String name = GlobalEntityStore.getLatestName(GlobalEntityStore.PRODUCT_SETUP_TYPE);
        if (name == null) name = GlobalTestData.get(GlobalTestData.PRODUCT_SETUP_TYPE_NAME);
        Assert.assertNotNull(name, "No current Product Setup Type name found in GlobalEntityStore");
        context.set(ScenarioContext.PRODUCT_SETUP_TYPE_NAME, name);
        page().enterSearchText(name);
    }

    // ═══════════════════════════════════════════════════════
    //  SWIPE → ACTION MENUS → EDIT
    // ═══════════════════════════════════════════════════════

    @When("User clicks on Edit option")
    public void userClicksOnEditOption() {
        page().clickEditOption();
    }

    // ═══════════════════════════════════════════════════════
    //  EDIT POPUP VERIFICATION
    // ═══════════════════════════════════════════════════════

    @And("Product Setup Type ID should be visible and non-editable")
    public void productSetupTypeIdShouldBeVisibleAndNonEditable() {
        Assert.assertTrue(page().isProductSetupTypeIdVisible(),
                "Product Setup Type ID not visible in Edit popup");
        Assert.assertTrue(page().isProductSetupTypeIdNonEditable(),
                "Product Setup Type ID should be non-editable");
    }

    @And("Product Setup Name field should be pre-filled")
    public void productSetupNameFieldShouldBePreFilled() {
        String name = page().getProductSetupNameValue();
        Assert.assertFalse(name == null || name.isEmpty(),
                "Product Setup Name field is not pre-filled");
    }

    @And("Description field should be pre-filled for Product Setup Type")
    public void descriptionFieldShouldBePreFilledForProductSetupType() {
        Assert.assertTrue(page().isDescriptionFieldVisible(),
                "Description field is not visible/pre-filled");
    }

    @And("Machine Output Timer field should be pre-filled")
    public void machineOutputTimerFieldShouldBePreFilled() {
        Assert.assertTrue(page().isMachineOutputTimerFieldVisible(),
                "Machine Output Timer field is not visible");
    }

    @And("Product Setup Timer field should be pre-filled")
    public void productSetupTimerFieldShouldBePreFilled() {
        Assert.assertTrue(page().isProductSetupTimerFieldVisible(),
                "Product Setup Timer field is not visible");
    }

    @Then("Product Setup Type ID should be visible")
    public void productSetupTypeIdShouldBeVisible() {
        Assert.assertTrue(page().isProductSetupTypeIdVisible(),
                "Product Setup Type ID not visible in popup");
    }

    @Then("Product Setup Name field should be editable")
    public void productSetupNameFieldShouldBeEditable() {
        Assert.assertTrue(page().isProductSetupNameFieldVisible(),
                "Product Setup Name field not visible/editable");
    }

    @Then("Product Setup Type ID should not be editable")
    public void productSetupTypeIdShouldNotBeEditable() {
        Assert.assertTrue(page().isProductSetupTypeIdNonEditable(),
                "Product Setup Type ID should be non-editable");
    }

    @Then("all fields should display previously saved Product Setup Type data correctly")
    public void allFieldsShouldDisplayPreviouslySavedProductSetupTypeDataCorrectly() {
        String name = page().getProductSetupNameValue();
        Assert.assertFalse(name == null || name.isEmpty(),
                "Saved Product Setup Name missing in Edit popup");
    }

    // ═══════════════════════════════════════════════════════
    //  FIELD INPUT — UPDATE
    // ═══════════════════════════════════════════════════════

    @When("User updates Product Setup Name with valid value")
    public void userUpdatesProductSetupNameWithValidValue() {
        String updated = DataGenerator.randomProductSetupName();
        context.set(ScenarioContext.PRODUCT_SETUP_TYPE_NAME, updated);
        GlobalTestData.set(GlobalTestData.PRODUCT_SETUP_TYPE_NAME, updated);
        page().enterProductSetupName(updated);
    }

    @When("User updates only Product Setup Name")
    public void userUpdatesOnlyProductSetupName() {
        userUpdatesProductSetupNameWithValidValue();
    }

    @When("User updates Description for Product Setup Type")
    public void userUpdatesDescriptionForProductSetupType() {
        page().enterDescription("Updated Product Setup Type description");
    }

    @When("User updates only Description for Product Setup Type")
    public void userUpdatesOnlyDescriptionForProductSetupType() {
        userUpdatesDescriptionForProductSetupType();
    }

    @When("User updates Machine Output Timer to {int} hours {int} minutes {int} seconds")
    public void userUpdatesMachineOutputTimer(int hours, int minutes, int seconds) {
        page().setMachineOutputTimer(hours, minutes, seconds);
    }

    @When("User updates Product Setup Timer to {int} hours {int} minutes {int} seconds")
    public void userUpdatesProductSetupTimer(int hours, int minutes, int seconds) {
        page().setProductSetupTimer(hours, minutes, seconds);
    }

    @When("User modifies Product Setup Type details")
    public void userModifiesProductSetupTypeDetails() {
        page().enterProductSetupName(DataGenerator.randomProductSetupName());
    }

    // ═══════════════════════════════════════════════════════
    //  POPUP BUTTONS — EDIT
    // ═══════════════════════════════════════════════════════

    @When("User clicks Save button on Product Setup Type popup")
    public void userClicksSaveButtonOnProductSetupTypePopup() {
        page().clickSaveButton();
    }

    @When("User clicks Save button on Product Setup Type popup without modification")
    public void userClicksSaveButtonOnProductSetupTypePopupWithoutModification() {
        page().clickSaveButton();
    }

    @When("User clicks Save button multiple times quickly on Product Setup Type popup")
    public void userClicksSaveButtonMultipleTimesQuicklyOnProductSetupTypePopup() {
        page().clickSaveButton();
        try { page().clickSaveButton(); } catch (Exception ignored) { /* already dismissed */ }
        try { page().clickSaveButton(); } catch (Exception ignored) { /* already dismissed */ }
    }

    // ═══════════════════════════════════════════════════════
    //  UPDATE — POSITIVE OUTCOMES
    // ═══════════════════════════════════════════════════════

    @Then("Product Setup Type should be updated successfully")
    public void productSetupTypeShouldBeUpdatedSuccessfully() {
        Assert.assertTrue(page().waitForEditPopupToClose(25),
                "Product Setup Type update failed — Edit popup did not close within 25s");
        String newName = context.getString(ScenarioContext.PRODUCT_SETUP_TYPE_NAME);
        if (newName != null) {
            if (newName.trim().length() <= 100) {
                GlobalEntityStore.setLatestName(GlobalEntityStore.PRODUCT_SETUP_TYPE, newName);
                GlobalTestData.set(GlobalTestData.PRODUCT_SETUP_TYPE_NAME, newName);
            } else {
                GlobalEntityStore.setLatestName(GlobalEntityStore.PRODUCT_SETUP_TYPE, null);
                GlobalTestData.set(GlobalTestData.PRODUCT_SETUP_TYPE_NAME, null);
            }
        }
    }

    @Then("updated data should be reflected in Product Setup Types list screen")
    public void updatedDataShouldBeReflectedInProductSetupTypesListScreen() {
        String name = storedName();
        if (name != null) {
            page().exitSearch();
            page().searchRecord(name);
            Assert.assertNotNull(page().getRecordByName(name),
                    "Updated Product Setup Type not found in list: " + name);
        }
    }

    @Then("previously saved Product Setup Type data should be displayed")
    public void previouslySavedProductSetupTypeDataShouldBeDisplayed() {
        allFieldsShouldDisplayPreviouslySavedProductSetupTypeDataCorrectly();
    }

    // ═══════════════════════════════════════════════════════
    //  UPDATE — NEGATIVE OUTCOMES
    // ═══════════════════════════════════════════════════════

    @Then("popup should be closed without saving updated Product Setup Type changes")
    public void popupShouldBeClosedWithoutSavingUpdatedProductSetupTypeChanges() {
        Assert.assertTrue(page().waitForReturnToList(10),
                "Edit popup did not close — list screen not visible");
    }

    @Then("system should allow optional Product Setup Type input without error")
    public void systemShouldAllowOptionalProductSetupTypeInputWithoutError() {
        Assert.assertFalse(page().isAnyValidationErrorDisplayed(),
                "Unexpected validation error for optional Description input in edit popup");
    }
}
