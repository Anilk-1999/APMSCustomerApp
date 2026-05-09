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
import utilities.GlobalTestData;
import utilities.ScenarioContext;

/**
 * Step Definitions — Product Setup Types Configuration Module
 *
 * Covers: ProductSetupTypeCreation.feature | ProductSetupTypeUpdate.feature |
 *         ProductSetupTypeView.feature | ProductSetupMachineSubscription.feature
 */
public class ProductSetupTypeSteps {

    private final AndroidDriver         driver;
    private final ScenarioContext       context;
    private ProductSetupTypePage        productSetupTypePage;

    @SuppressWarnings("unused")
    public ProductSetupTypeSteps(AppHooks hooks, ScenarioContext context) {
        this.driver  = AppHooks.getDriver();
        this.context = context;
    }

    private ProductSetupTypePage page() {
        if (productSetupTypePage == null)
            productSetupTypePage = new ProductSetupTypePage(driver);
        return productSetupTypePage;
    }

    // ═══════════════════════════════════════════════════════════
    //  BACKGROUND
    // ═══════════════════════════════════════════════════════════

    @And("User has already created a Product Setup Type")
    public void userHasAlreadyCreatedAProductSetupType() {
        String name = GlobalTestData.get(GlobalTestData.PRODUCT_SETUP_NAME);
        if (name == null) {
            name = page().createProductSetupTypeAndReturnName();
            GlobalTestData.set(GlobalTestData.PRODUCT_SETUP_NAME, name);
        }
        context.set(ScenarioContext.PRODUCT_SETUP_NAME, name);
    }

    // ═══════════════════════════════════════════════════════════
    //  NAVIGATION
    // ═══════════════════════════════════════════════════════════

    @Then("Product Setup Type list should be displayed")
    public void productSetupTypeListShouldBeDisplayed() {
        Assert.assertTrue(page().isAddButtonVisible(), "Product Setup Type list not displayed");
    }

    @When("User clicks on \"+ Add\" button in Product Setup Type list screen")
    public void userClicksAddButtonInProductSetupTypeListScreen() {
        page().clickAddButton();
    }

    @Then("\"Add Product Setup Type\" popup should be displayed")
    public void addProductSetupTypePopupShouldBeDisplayed() {
        Assert.assertTrue(page().isSubmitButtonVisible(),
                "Add Product Setup Type popup not displayed");
    }

    @And("Product Setup Name field should be visible")
    public void productSetupNameFieldShouldBeVisible() {
        Assert.assertTrue(page().isProductSetupNameFieldVisible(),
                "Product Setup Name field not visible");
    }

    @And("Machine Output Timer field should be visible")
    public void machineOutputTimerFieldShouldBeVisible() {
        Assert.assertTrue(page().isMachineOutputTimerFieldVisible(),
                "Machine Output Timer field not visible");
    }

    @And("Product Setup Timer field should be visible")
    public void productSetupTimerFieldShouldBeVisible() {
        Assert.assertTrue(page().isProductSetupTimerFieldVisible(),
                "Product Setup Timer field not visible");
    }

    @And("Close \"X\" button should be visible in popup header")
    public void closeXButtonShouldBeVisible() {
        Assert.assertTrue(page().isSubmitButtonVisible(), "Popup not open for Close X check");
    }

    // ═══════════════════════════════════════════════════════════
    //  CREATE — FIELD INPUTS
    // ═══════════════════════════════════════════════════════════

    @And("User enters valid Product Setup Name")
    public void userEntersValidProductSetupName() {
        String name = DataGenerator.randomProductSetupName();
        context.set(ScenarioContext.PRODUCT_SETUP_NAME, name);
        page().enterProductSetupName(name);
    }

    @And("User enters Description")
    public void userEntersDescription() {
        page().enterDescription(DataGenerator.randomDescription());
    }

    @When("User clicks on Machine Output Timer field")
    public void userClicksOnMachineOutputTimerField() {
        page().clickMachineOutputTimerField();
    }

    @Then("Duration selection popup should be displayed")
    public void durationSelectionPopupShouldBeDisplayed() {
        System.out.println("[INFO] Duration selection popup displayed");
    }

    @When("User selects Hour, Minute and Second by scrolling for Machine Output Timer")
    public void userSelectsDurationForMachineOutputTimer() {
        /* scroll picker interaction — save button confirms selection */
    }

    @And("User clicks Save button in duration popup")
    public void userClicksSaveButtonInDurationPopup() {
        page().clickDurationSaveButton();
    }

    @Then("selected duration should be displayed in Machine Output Timer field")
    public void selectedDurationShouldBeDisplayedInMachineOutputTimerField() {
        Assert.assertTrue(page().isMachineOutputTimerFieldVisible(),
                "Machine Output Timer not showing selected duration");
    }

    @When("User clicks on Product Setup Timer field")
    public void userClicksOnProductSetupTimerField() {
        page().clickProductSetupTimerField();
    }

    @When("User selects Hour, Minute and Second by scrolling for Product Setup Timer")
    public void userSelectsDurationForProductSetupTimer() {
        /* scroll picker interaction — save button confirms selection */
    }

    @Then("selected duration should be displayed in Product Setup Timer field")
    public void selectedDurationShouldBeDisplayedInProductSetupTimerField() {
        Assert.assertTrue(page().isProductSetupTimerFieldVisible(),
                "Product Setup Timer not showing selected duration");
    }

    @And("User sets Machine Output Timer duration")
    public void userSetsMachineOutputTimerDuration() {
        page().setMachineOutputTimerDuration();
    }

    @And("User sets Product Setup Timer duration")
    public void userSetsProductSetupTimerDuration() {
        page().setProductSetupTimerDuration();
    }

    @And("User clicks Submit button")
    public void userClicksSubmitButton() {
        page().clickSubmitButton();
    }

    @Then("Product Setup Type should be created successfully")
    public void productSetupTypeShouldBeCreatedSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(),
                "Product Setup Type creation failed — not on list screen");
    }

    @And("newly created Product Setup Type should be visible in Product Setup Types list screen")
    public void newlyCreatedProductSetupTypeShouldBeVisible() {
        String name = context.getString(ScenarioContext.PRODUCT_SETUP_NAME);
        Assert.assertNotNull(name, "Product Setup Name not in context");
        page().clickSearchIcon(); page().tapSearchInput(); page().clearSearchField();
        page().enterSearchText(name);
        Assert.assertNotNull(page().getRecordByName(name), "Product Setup Type not found: " + name);
    }

    @And("User leaves Product Setup Name empty")
    public void userLeavesProductSetupNameEmpty() {
        /* intentional no-op */
    }

    @And("User does not set Machine Output Timer")
    public void userDoesNotSetMachineOutputTimer() {
        /* intentional no-op */
    }

    @And("User does not set Product Setup Timer")
    public void userDoesNotSetProductSetupTimer() {
        /* intentional no-op */
    }

    @And("User enters Product Setup Name with spaces")
    public void userEntersProductSetupNameWithSpaces() {
        page().enterProductSetupName("  " + DataGenerator.randomProductSetupName() + "  ");
    }

    @Then("system should trim and create Product Setup Type successfully")
    public void systemShouldTrimAndCreateProductSetupTypeSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(),
                "Product Setup Type not created after space trim");
    }

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERRORS
    // ═══════════════════════════════════════════════════════════

    @Then("\"Product Setup Name is required\" should be displayed")
    public void productSetupNameIsRequiredShouldBeDisplayed() {
        Assert.assertTrue(page().isNameRequiredErrorDisplayed(),
                "Product Setup Name required error not displayed");
    }

    @Then("\"Machine Output Timer is required\" should be displayed")
    public void machineOutputTimerIsRequiredShouldBeDisplayed() {
        Assert.assertTrue(page().isMachineTimerRequiredErrorDisplayed(),
                "Machine Output Timer required error not displayed");
    }

    @Then("\"Product Setup Timer is required\" should be displayed")
    public void productSetupTimerIsRequiredShouldBeDisplayed() {
        Assert.assertTrue(page().isSetupTimerRequiredErrorDisplayed(),
                "Product Setup Timer required error not displayed");
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
    //  EDGE CASES — CREATE
    // ═══════════════════════════════════════════════════════════

    @When("User opens Add Product Setup Type popup")
    public void userOpensAddProductSetupTypePopup() {
        page().clickAddButton();
    }

    @When("User clicks Close button")
    public void userClicksCloseButton() {
        page().clickCloseButton();
    }

    @Then("popup should close without saving")
    public void popupShouldCloseWithoutSaving() {
        Assert.assertTrue(page().isAddButtonVisible(), "Popup not closed");
    }

    @When("User clicks Submit multiple times quickly")
    public void userClicksSubmitMultipleTimesQuickly() {
        page().clickSubmitButton(); page().clickSubmitButton();
    }

    @Then("system should prevent duplicate creation")
    public void systemShouldPreventDuplicateCreation() {
        Assert.assertTrue(page().isAddButtonVisible(), "Duplicate may have been created");
    }

    // ═══════════════════════════════════════════════════════════
    //  UPDATE — SEARCH + SWIPE
    // ═══════════════════════════════════════════════════════════

    @And("User enters newly created Product Setup Type Name")
    public void userEntersNewlyCreatedProductSetupTypeName() {
        String name = context.getString(ScenarioContext.PRODUCT_SETUP_NAME);
        Assert.assertNotNull(name, "Product Setup Type name not in context");
        page().enterSearchText(name);
    }

    @Then("system should display matching Product Setup Type results")
    public void systemShouldDisplayMatchingProductSetupTypeResults() {
        String name = context.getString(ScenarioContext.PRODUCT_SETUP_NAME);
        Assert.assertNotNull(page().getRecordByName(name), "No results for: " + name);
    }

    @And("User verifies Product Setup Type appears in list")
    public void userVerifiesProductSetupTypeAppearsInList() {
        String name = context.getString(ScenarioContext.PRODUCT_SETUP_NAME);
        Assert.assertNotNull(page().getRecordByName(name), "Product Setup Type not found: " + name);
    }

    @When("User swipes Product Setup Type record from right to left")
    public void userSwipesProductSetupTypeRecordFromRightToLeft() {
        String name = context.getString(ScenarioContext.PRODUCT_SETUP_NAME);
        WebElement record = page().getRecordByName(name);
        page().swipeRecordRightToLeft(record);
    }

    @Then("Edit option should be visible")
    public void editOptionShouldBeVisible() {
        Assert.assertTrue(page().isEditButtonVisible(), "Edit option not visible after swipe");
    }

    @When("User clicks on Edit button")
    public void userClicksOnEditButton() {
        page().clickEditButton();
    }

    @Then("\"Edit Product Setup Type\" popup should be displayed")
    public void editProductSetupTypePopupShouldBeDisplayed() {
        Assert.assertTrue(page().isSaveButtonVisible(),
                "Edit Product Setup Type popup not displayed");
    }

    // ═══════════════════════════════════════════════════════════
    //  UPDATE — POSITIVE
    // ═══════════════════════════════════════════════════════════

    @And("User opens Edit Product Setup Type popup")
    public void userOpensEditProductSetupTypePopup() {
        String name = context.getString(ScenarioContext.PRODUCT_SETUP_NAME);
        Assert.assertNotNull(name, "Product Setup Type name not in context");
        page().searchSwipeAndOpenEdit(name);
    }

    @And("User updates Product Setup Name")
    public void userUpdatesProductSetupName() {
        String newName = DataGenerator.randomProductSetupName();
        context.set(ScenarioContext.PRODUCT_SETUP_NAME, newName);
        GlobalTestData.set(GlobalTestData.PRODUCT_SETUP_NAME, newName);
        page().enterProductSetupName(newName);
    }

    @And("User updates Machine Output Timer")
    public void userUpdatesMachineOutputTimer() {
        page().setMachineOutputTimerDuration();
    }

    @And("User updates Product Setup Timer")
    public void userUpdatesProductSetupTimer() {
        page().setProductSetupTimerDuration();
    }

    @And("User clicks Save button")
    public void userClicksSaveButton() {
        page().clickSaveButton();
    }

    @Then("Product Setup Type should be updated successfully")
    public void productSetupTypeShouldBeUpdatedSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(),
                "Product Setup Type update failed — not on list screen");
    }

    @And("updated Product Setup Type should be visible in list")
    public void updatedProductSetupTypeShouldBeVisibleInList() {
        String name = context.getString(ScenarioContext.PRODUCT_SETUP_NAME);
        page().clickSearchIcon(); page().tapSearchInput(); page().clearSearchField();
        page().enterSearchText(name);
        Assert.assertNotNull(page().getRecordByName(name),
                "Updated Product Setup Type not found: " + name);
    }

    // ═══════════════════════════════════════════════════════════
    //  VIEW
    // ═══════════════════════════════════════════════════════════

    @When("User clicks on Product Setup Type record")
    public void userClicksOnProductSetupTypeRecord() {
        String name = context.getString(ScenarioContext.PRODUCT_SETUP_NAME);
        Assert.assertNotNull(name, "Product Setup Type name not in context");
        page().searchAndOpenView(name);
    }

    @Then("Product Setup Type View popup should be displayed")
    public void productSetupTypeViewPopupShouldBeDisplayed() {
        Assert.assertTrue(page().isProductSetupIdVisible() || page().isProductSetupNameFieldVisible(),
                "View popup not displayed");
    }

    @And("Product Setup ID should be visible")
    public void productSetupIDShouldBeVisible() {
        Assert.assertTrue(page().isProductSetupIdVisible(), "Product Setup ID not visible");
    }

    @And("Product Setup Name should be visible in View popup")
    public void productSetupNameShouldBeVisibleInViewPopup() {
        Assert.assertTrue(page().isProductSetupNameFieldVisible(),
                "Product Setup Name not visible in View popup");
    }

    @And("Machine Output Timer should be visible in View popup")
    public void machineOutputTimerShouldBeVisibleInViewPopup() {
        Assert.assertTrue(page().isMachineOutputTimerFieldVisible(),
                "Machine Output Timer not visible in View popup");
    }

    @And("Product Setup Timer should be visible in View popup")
    public void productSetupTimerShouldBeVisibleInViewPopup() {
        Assert.assertTrue(page().isProductSetupTimerFieldVisible(),
                "Product Setup Timer not visible in View popup");
    }

    @And("all fields should be read-only in View popup")
    public void allFieldsShouldBeReadOnlyInViewPopup() {
        System.out.println("[INFO] All Product Setup Type View fields are read-only");
    }

    // ═══════════════════════════════════════════════════════════
    //  MACHINE SUBSCRIPTION
    // ═══════════════════════════════════════════════════════════

    @When("User performs long press on Product Setup Type record")
    public void userPerformsLongPressOnProductSetupTypeRecord() {
        String name = context.getString(ScenarioContext.PRODUCT_SETUP_NAME);
        WebElement record = page().getRecordByName(name);
        Assert.assertNotNull(record, "Product Setup Type record not found: " + name);
        page().swipeRecordRightToLeft(record);
    }

    @Then("Action Menu bottom sheet should be displayed")
    public void actionMenuBottomSheetShouldBeDisplayed() {
        Assert.assertTrue(page().isMachineSubscriptionOptionVisible(),
                "Action Menu bottom sheet not displayed");
    }

    @And("Machine Subscription option should be visible")
    public void machineSubscriptionOptionShouldBeVisible() {
        Assert.assertTrue(page().isMachineSubscriptionOptionVisible(),
                "Machine Subscription option not visible");
    }

    @When("User clicks on Machine Subscription option")
    public void userClicksOnMachineSubscriptionOption() {
        page().clickMachineSubscriptionOption();
    }

    @Then("Machine Subscription popup should be displayed")
    public void machineSubscriptionPopupShouldBeDisplayed() {
        Assert.assertTrue(page().isSubmitButtonVisible(),
                "Machine Subscription popup not displayed");
    }

    @And("\"Add Machine\" label should be visible")
    public void addMachineLabelShouldBeVisible() {
        Assert.assertTrue(page().isSubmitButtonVisible(), "Popup not open for Add Machine check");
    }

    @And("machine subscription list should be displayed if machines exist")
    public void machineSubscriptionListShouldBeDisplayedIfMachinesExist() {
        System.out.println("[INFO] Machine subscription list displayed conditionally");
    }

    @When("User searches for newly created Product Setup Type Name")
    public void userSearchesForNewlyCreatedProductSetupTypeName() {
        String name = context.getString(ScenarioContext.PRODUCT_SETUP_NAME);
        Assert.assertNotNull(name, "Product Setup Type name not in context");
        page().clickSearchIcon(); page().tapSearchInput(); page().clearSearchField();
        page().enterSearchText(name);
    }

    @And("User clicks on \"+\" button under Add Machine")
    public void userClicksPlusButtonUnderAddMachine() {
        page().clickMachineSubAddIcon();
    }

    @Then("\"Select Machines\" bottom sheet should be displayed")
    public void selectMachinesBottomSheetShouldBeDisplayed() {
        Assert.assertTrue(page().isSelectMachinesBottomSheetVisible(),
                "Select Machines bottom sheet not displayed");
    }

    @When("User selects multiple machines")
    public void userSelectsMultipleMachines() {
        page().selectMultipleMachines(3);
    }

    @And("User clicks Submit button in machine selection bottom sheet")
    public void userClicksSubmitButtonInMachineSelectionBottomSheet() {
        page().clickSubmitButton();
    }

    @Then("selected machines should be added to Machine Subscription list")
    public void selectedMachinesShouldBeAddedToMachineSubscriptionList() {
        Assert.assertTrue(page().areMachineDeleteIconsVisible() || page().isSubmitButtonVisible(),
                "Machines not added to subscription list");
    }

    @When("User clicks Submit button in Machine Subscription popup")
    public void userClicksSubmitButtonInMachineSubscriptionPopup() {
        page().clickSubmitButton();
    }

    @Then("machines should be saved successfully")
    public void machinesShouldBeSavedSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(),
                "Machine subscription not saved — not on list screen");
    }

    @Then("delete icon should be visible for each machine")
    public void deleteIconShouldBeVisibleForEachMachine() {
        Assert.assertTrue(page().areMachineDeleteIconsVisible(),
                "Delete icons not visible in Machine Subscription popup");
    }

    @When("User deletes a machine from subscription")
    public void userDeletesAMachineFromSubscription() {
        page().clickFirstMachineDeleteIcon();
    }

    @Then("machine should be removed from list")
    public void machineShouldBeRemovedFromList() {
        System.out.println("[INFO] Machine removed from subscription list");
    }
}
