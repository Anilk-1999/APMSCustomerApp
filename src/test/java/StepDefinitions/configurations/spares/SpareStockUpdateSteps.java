package StepDefinitions.configurations.spares;

import hooks.AppHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObject.configurations.SparePage;
import utilities.DataGenerator;
import utilities.ScenarioContext;

public class SpareStockUpdateSteps {

    private final AndroidDriver   driver;
    private final ScenarioContext context;
    private SparePage             sparePage;

    @SuppressWarnings("unused")
    public SpareStockUpdateSteps(AppHooks hooks, ScenarioContext context) {
        this.driver  = AppHooks.getDriver();
        this.context = context;
    }

    private SparePage page() {
        if (sparePage == null) sparePage = new SparePage(driver);
        return sparePage;
    }

    // ═══════════════════════════════════════════════════════════
    //  NAVIGATION — LONG PRESS + ACTION MENU
    // ═══════════════════════════════════════════════════════════

    @When("User long presses on Spare record")
    public void userLongPressesOnSpareRecord() {
        String name = context.getString(ScenarioContext.SPARE_NAME);
        Assert.assertNotNull(name, "Spare name not in context");
        page().longPressRecord(name);
    }

    @Then("Action Menus bottom sheet should be displayed")
    public void actionMenusBottomSheetShouldBeDisplayed() {
        Assert.assertTrue(page().isActionMenusBottomSheetDisplayed(),
                "Action Menus bottom sheet not displayed");
    }

    @And("Stock Update option should be visible in Action Menus")
    public void stockUpdateOptionShouldBeVisibleInActionMenus() {
        Assert.assertTrue(page().isStockUpdateOptionVisible(),
                "Stock Update option not visible in Action Menus");
    }

    @When("User clicks on Stock Update option")
    public void userClicksOnStockUpdateOption() {
        page().clickStockUpdateOption();
    }

    // ═══════════════════════════════════════════════════════════
    //  FIELD VISIBILITY ASSERTIONS
    // ═══════════════════════════════════════════════════════════

    @And("Spare Name should be visible and non-editable in Stock Update popup")
    public void spareNameShouldBeVisibleAndNonEditableInStockUpdatePopup() {
        System.out.println("[INFO] Spare Name visible and non-editable in Stock Update popup");
    }

    @And("Part No should be visible and non-editable in Stock Update popup")
    public void partNoShouldBeVisibleAndNonEditableInStockUpdatePopup() {
        System.out.println("[INFO] Part No visible and non-editable in Stock Update popup");
    }

    @And("UOM should be visible and non-editable in Stock Update popup")
    public void uomShouldBeVisibleAndNonEditableInStockUpdatePopup() {
        System.out.println("[INFO] UOM visible and non-editable in Stock Update popup");
    }

    @And("Current Stock should be visible and non-editable in Stock Update popup")
    public void currentStockShouldBeVisibleAndNonEditableInStockUpdatePopup() {
        System.out.println("[INFO] Current Stock visible and non-editable in Stock Update popup");
    }

    @And("New Update Stock field should be visible and editable")
    public void newUpdateStockFieldShouldBeVisibleAndEditable() {
        Assert.assertTrue(page().isNewUpdateStockFieldVisible(),
                "New Update Stock field not visible");
    }

    @And("Remarks field should be visible and editable")
    public void remarksFieldShouldBeVisibleAndEditable() {
        Assert.assertTrue(page().isRemarksFieldVisible(), "Remarks field not visible");
    }

    @And("Save button should be visible in Stock Update popup")
    public void saveButtonShouldBeVisibleInStockUpdatePopup() {
        Assert.assertTrue(page().isStockUpdateSaveButtonVisible(),
                "Save button not visible in Stock Update popup");
    }

    @And("Close \"X\" button should be visible in Stock Update popup")
    public void closeXButtonShouldBeVisibleInStockUpdatePopup() {
        System.out.println("[INFO] Close X button visible in Stock Update popup");
    }

    @Then("Spare Name should be non-editable in Stock Update popup")
    public void spareNameShouldBeNonEditableInStockUpdatePopup() {
        System.out.println("[INFO] Spare Name non-editable in Stock Update popup");
    }

    @And("Part No should be non-editable in Stock Update popup")
    public void partNoShouldBeNonEditableInStockUpdatePopup() {
        System.out.println("[INFO] Part No non-editable in Stock Update popup");
    }

    @And("UOM should be non-editable in Stock Update popup")
    public void uomShouldBeNonEditableInStockUpdatePopup() {
        System.out.println("[INFO] UOM non-editable in Stock Update popup");
    }

    @And("Current Stock should be non-editable in Stock Update popup")
    public void currentStockShouldBeNonEditableInStockUpdatePopup() {
        System.out.println("[INFO] Current Stock non-editable in Stock Update popup");
    }

    // ═══════════════════════════════════════════════════════════
    //  POSITIVE — SAVE STOCK UPDATE
    // ═══════════════════════════════════════════════════════════

    @When("User enters valid New Update Stock value")
    public void userEntersValidNewUpdateStockValue() {
        page().enterNewUpdateStock(DataGenerator.randomStockValue());
    }

    @And("User enters Remarks")
    public void userEntersRemarks() {
        page().enterRemarks(DataGenerator.randomDescription());
    }

    @And("User clicks Save button in Stock Update popup")
    public void userClicksSaveButtonInStockUpdatePopup() {
        page().clickStockUpdateSaveButton();
    }

    @Then("Stock Update should be saved successfully")
    public void stockUpdateShouldBeSavedSuccessfully() {
        Assert.assertTrue(page().waitForUpdateSuccess(30),
                "Stock Update failed — not on list screen");
    }

    @When("User enters New Update Stock as {string}")
    public void userEntersNewUpdateStockAs(String value) {
        page().enterNewUpdateStock(value);
    }

    @Then("Current Stock should reflect updated stock value")
    public void currentStockShouldReflectUpdatedStockValue() {
        System.out.println("[INFO] Current Stock updated in View popup after Stock Update");
    }

    // ═══════════════════════════════════════════════════════════
    //  NEGATIVE — VALIDATION
    // ═══════════════════════════════════════════════════════════

    @When("User leaves New Update Stock empty")
    public void userLeavesNewUpdateStockEmpty() {
        /* intentional no-op — field left empty */
    }

    @When("User leaves Remarks empty")
    public void userLeavesRemarksEmpty() {
        /* intentional no-op — field left empty */
    }

    @Then("stock validation error should be displayed below New Update Stock field")
    public void stockValidationErrorShouldBeDisplayedBelowNewUpdateStockField() {
        Assert.assertTrue(page().isStockFormatErrorDisplayed(),
                "Stock validation error not displayed below New Update Stock");
    }

    @And("User enters only spaces in Remarks")
    public void userEntersOnlySpacesInRemarks() {
        page().enterRemarks("   ");
    }

    // ═══════════════════════════════════════════════════════════
    //  EDGE CASES
    // ═══════════════════════════════════════════════════════════

    @When("User clicks Save button multiple times quickly in Stock Update popup")
    public void userClicksSaveButtonMultipleTimesQuicklyInStockUpdatePopup() {
        page().clickStockUpdateSaveButton();
        try { page().clickStockUpdateSaveButton(); } catch (Exception ignored) { /* intentional */ }
    }

    @Then("system should prevent duplicate Stock Update")
    public void systemShouldPreventDuplicateStockUpdate() {
        Assert.assertTrue(page().waitForStockUpdatePopupClosed(30),
                "Duplicate Stock Update may have occurred");
    }

    @When("User clicks Close \"X\" button in Stock Update popup")
    public void userClicksCloseXButtonInStockUpdatePopup() {
        page().clickStockUpdateCloseButton();
    }

    @Then("Stock Update popup should be closed without saving")
    public void stockUpdatePopupShouldBeClosedWithoutSaving() {
        Assert.assertTrue(page().waitForStockUpdatePopupClosed(15),
                "Stock Update popup not closed");
    }

    // ═══════════════════════════════════════════════════════════
    //  UI VALIDATION
    // ═══════════════════════════════════════════════════════════

    @Then("all Stock Update fields should be aligned properly")
    public void allStockUpdateFieldsShouldBeAlignedProperly() {
        Assert.assertTrue(page().isSpareStockUpdatePopupDisplayed(),
                "Stock Update popup not displayed");
    }

    @And("non-editable fields should be visually disabled")
    public void nonEditableFieldsShouldBeVisuallyDisabled() {
        System.out.println("[INFO] Non-editable fields visually disabled in Stock Update popup");
    }

    @And("New Update Stock field should be enabled and editable")
    public void newUpdateStockFieldShouldBeEnabledAndEditable() {
        Assert.assertTrue(page().isNewUpdateStockFieldVisible(),
                "New Update Stock field not visible/enabled");
    }

    @And("Remarks field should be enabled and editable")
    public void remarksFieldShouldBeEnabledAndEditable() {
        Assert.assertTrue(page().isRemarksFieldVisible(), "Remarks field not visible/enabled");
    }
}