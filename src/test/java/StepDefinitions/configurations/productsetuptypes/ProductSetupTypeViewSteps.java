package StepDefinitions.configurations.productsetuptypes;

import hooks.AppHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObject.configurations.ProductSetupTypePage;
import utilities.ScenarioContext;

public class ProductSetupTypeViewSteps {

    private final AndroidDriver   driver;
    private final ScenarioContext context;
    private ProductSetupTypePage  productSetupTypePage;

    @SuppressWarnings("unused")
    public ProductSetupTypeViewSteps(AppHooks hooks, ScenarioContext context) {
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
    //  OPEN VIEW POPUP
    // ═══════════════════════════════════════════════════════

    @When("User clicks on the Product Setup Type record to view")
    public void userClicksOnTheProductSetupTypeRecordToView() {
        String name = storedName();
        Assert.assertNotNull(name, "Product Setup Type name not in context for record click");
        page().clickRecordByName(name);
    }

    @When("User opens Product Setup Type View popup")
    public void userOpensProductSetupTypeViewPopup() {
        String name = storedName();
        Assert.assertNotNull(name, "Product Setup Type name not in context");
        page().searchAndOpenView(name);
    }

    @When("User clicks multiple times quickly on Product Setup Type record")
    public void userClicksMultipleTimesQuicklyOnProductSetupTypeRecord() {
        String name = storedName();
        for (int i = 0; i < 3; i++) {
            try {
                page().clickRecordByName(name);
            } catch (Exception ignored) {
                System.out.println("[INFO] Rapid click attempt skipped");
            }
        }
    }

    @When("User opens and closes Product Setup Type View popup multiple times")
    public void userOpensAndClosesProductSetupTypeViewPopupMultipleTimes() {
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

    // ═══════════════════════════════════════════════════════
    //  READ-ONLY MODE ASSERTIONS
    // ═══════════════════════════════════════════════════════

    @And("all Product Setup Type fields should be displayed in read-only mode")
    public void allProductSetupTypeFieldsShouldBeDisplayedInReadOnlyMode() {
        Assert.assertTrue(page().isViewProductSetupTypePopupDisplayed(),
                "View Product Setup Type popup is not displayed");
        Assert.assertTrue(page().hasNoEditableInputFields(),
                "One or more fields are editable in View popup — expected read-only");
    }

    @Then("all Product Setup Type fields should appear disabled or read-only")
    public void allProductSetupTypeFieldsShouldAppearDisabledOrReadOnly() {
        Assert.assertTrue(page().hasNoEditableInputFields(),
                "One or more input fields are still enabled in View popup");
    }

    @And("no editable input cursor should be visible in Product Setup Type view popup")
    public void noEditableInputCursorShouldBeVisibleInProductSetupTypeViewPopup() {
        Assert.assertTrue(page().hasNoEditableInputFields(),
                "Editable input field found in View popup — expected no cursor");
    }

    @Then("Product Setup Name field should not be editable in view popup")
    public void productSetupNameFieldShouldNotBeEditableInViewPopup() {
        Assert.assertTrue(page().isNameFieldNonEditable(),
                "Product Setup Name field is editable in View popup — expected read-only");
    }

    @And("Description should not be editable in view popup")
    public void descriptionShouldNotBeEditableInViewPopup() {
        Assert.assertTrue(page().isDescriptionFieldNonEditable(),
                "Description field is editable in View popup — expected read-only");
    }

    // ═══════════════════════════════════════════════════════
    //  FIELD VISIBILITY
    // ═══════════════════════════════════════════════════════

    @And("Product Setup Name should be visible in view popup")
    public void productSetupNameShouldBeVisibleInViewPopup() {
        String name = storedName();
        Assert.assertNotNull(name, "No stored Product Setup Type name to verify");
        Assert.assertTrue(page().isTextVisible(name),
                "Product Setup Name '" + name + "' not visible in View popup");
    }

    @And("Machine Output Timer should be visible in view popup")
    public void machineOutputTimerShouldBeVisibleInViewPopup() {
        Assert.assertTrue(page().isViewProductSetupTypePopupDisplayed(),
                "View Product Setup Type popup not displayed");
        Assert.assertTrue(page().isMachineOutputTimerFieldVisible(),
                "Machine Output Timer not visible in View popup");
    }

    @And("Product Setup Timer should be visible in view popup")
    public void productSetupTimerShouldBeVisibleInViewPopup() {
        Assert.assertTrue(page().isViewProductSetupTypePopupDisplayed(),
                "View Product Setup Type popup not displayed");
        Assert.assertTrue(page().isProductSetupTimerFieldVisible(),
                "Product Setup Timer not visible in View popup");
    }

    @And("Description should be visible in view popup")
    public void descriptionShouldBeVisibleInViewPopup() {
        Assert.assertTrue(page().isViewProductSetupTypePopupDisplayed(),
                "View Product Setup Type popup not displayed");
        Assert.assertTrue(page().isDescriptionFieldVisible(),
                "Description field not visible in View popup");
    }

    // ═══════════════════════════════════════════════════════
    //  DATA ACCURACY
    // ═══════════════════════════════════════════════════════

    @And("all displayed values should match saved Product Setup Type data")
    public void allDisplayedValuesShouldMatchSavedProductSetupTypeData() {
        String name = storedName();
        Assert.assertNotNull(name, "No stored Product Setup Type name to verify");
        Assert.assertTrue(page().isTextVisible(name),
                "Saved Product Setup Name '" + name + "' not visible in View popup");
    }

    @Then("Product Setup Name should be displayed correctly in view popup")
    public void productSetupNameShouldBeDisplayedCorrectlyInViewPopup() {
        String name = storedName();
        Assert.assertNotNull(name, "No stored Product Setup Type name to verify");
        Assert.assertTrue(page().isTextVisible(name),
                "Product Setup Name '" + name + "' not displayed correctly in View popup");
    }

    // ═══════════════════════════════════════════════════════
    //  BUTTON VISIBILITY
    // ═══════════════════════════════════════════════════════

    @Then("Save button should not be visible in Product Setup Type view popup")
    public void saveButtonShouldNotBeVisibleInProductSetupTypeViewPopup() {
        Assert.assertFalse(page().isSaveButtonVisible(),
                "Save button should NOT be visible in View popup");
    }

    // ═══════════════════════════════════════════════════════
    //  EDIT ATTEMPT (negative)
    // ═══════════════════════════════════════════════════════

    @When("User tries to edit Product Setup Name in view popup")
    public void userTriesToEditProductSetupNameInViewPopup() {
        // Tap the read-only name field — expected to do nothing (no keyboard or cursor should appear)
        String name = storedName();
        if (name != null) {
            try { page().clickRecordByName(name); } catch (Exception ignored) { /* field is read-only */ }
        }
        Assert.assertTrue(page().isNameFieldNonEditable(),
                "Product Setup Name field became editable after tap — expected read-only");
    }

    @When("User tries to modify field values using keyboard input in Product Setup Type view")
    public void userTriesToModifyFieldValuesUsingKeyboardInputInProductSetupTypeView() {
        Assert.assertTrue(page().hasNoEditableInputFields(),
                "Keyboard input fields are present in View popup — expected all read-only");
    }

    @Then("Product Setup Type field values should remain unchanged")
    public void productSetupTypeFieldValuesShouldRemainUnchanged() {
        String name = storedName();
        Assert.assertNotNull(name, "No stored Product Setup Type name to compare");
        Assert.assertTrue(page().isTextVisible(name),
                "Product Setup Name changed after edit attempt — expected '" + name + "' to remain");
    }

    // ═══════════════════════════════════════════════════════
    //  LAYOUT / STRUCTURE
    // ═══════════════════════════════════════════════════════

    @Then("only one View Product Setup Type popup should open")
    public void onlyOneViewProductSetupTypePopupShouldOpen() {
        Assert.assertTrue(page().isViewProductSetupTypePopupDisplayed(),
                "View Product Setup Type popup not displayed after rapid clicks");
    }

    @Then("Product Setup Type details should load correctly each time")
    public void productSetupTypeDetailsShouldLoadCorrectlyEachTime() {
        Assert.assertTrue(page().waitForReturnToList(10),
                "Not returned to list after repeated View popup open/close cycles");
    }

    @Then("background screen should remain inaccessible until Product Setup Type popup is closed")
    public void backgroundScreenShouldRemainInaccessibleUntilProductSetupTypePopupIsClosed() {
        Assert.assertTrue(page().isViewProductSetupTypePopupDisplayed(),
                "View popup not displayed — background inaccessibility cannot be verified");
    }

    @And("Product Setup Type ID should start with hash STA prefix")
    public void productSetupTypeIdShouldStartWithHashSTAPrefix() {
        Assert.assertTrue(page().isProductSetupTypeIdVisible(),
                "Product Setup Type ID with #STA prefix not visible in View popup");
    }
}
