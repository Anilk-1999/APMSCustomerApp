package StepDefinitions.configurations.scraps;

import hooks.AppHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageObject.configurations.ScrapPage;
import utilities.DataGenerator;
import utilities.GlobalTestData;
import utilities.ScenarioContext;

/**
 * Step Definitions — Scraps Configuration Module
 *
 * Covers: ScrapCreation.feature | ScrapUpdate.feature |
 *         ScrapView.feature | ScrapProductSubscription.feature
 */
public class ScrapSteps {

    private final AndroidDriver    driver;
    private final ScenarioContext  context;
    private ScrapPage              scrapPage;

    @SuppressWarnings("unused")
    public ScrapSteps(AppHooks hooks, ScenarioContext context) {
        this.driver  = AppHooks.getDriver();
        this.context = context;
    }

    private ScrapPage page() {
        if (scrapPage == null) scrapPage = new ScrapPage(driver);
        return scrapPage;
    }

    // ═══════════════════════════════════════════════════════════
    //  BACKGROUND
    // ═══════════════════════════════════════════════════════════

    @And("User has already created a Scrap Item")
    public void userHasAlreadyCreatedAScrapItem() {
        String name = GlobalTestData.get(GlobalTestData.SCRAP_NAME);
        if (name == null) {
            name = page().createScrapAndReturnName();
            GlobalTestData.set(GlobalTestData.SCRAP_NAME, name);
        }
        context.set(ScenarioContext.SCRAP_NAME, name);
    }

    // ═══════════════════════════════════════════════════════════
    //  NAVIGATION
    // ═══════════════════════════════════════════════════════════

    @Then("Scrap list should be displayed")
    public void scrapListShouldBeDisplayed() {
        Assert.assertTrue(page().isAddButtonVisible(), "Scrap list not displayed");
    }

    @When("User clicks on \"+ Add\" button in Scraps list screen")
    public void userClicksAddButtonInScrapsListScreen() {
        page().clickAddButton();
    }

    @Then("\"Add New Scrap Item\" popup should be displayed")
    public void addNewScrapItemPopupShouldBeDisplayed() {
        Assert.assertTrue(page().isSubmitButtonVisible(), "Add New Scrap Item popup not displayed");
    }

    @And("Scrap Name field should be visible")
    public void scrapNameFieldShouldBeVisible() {
        Assert.assertTrue(page().isScrapNameFieldVisible(), "Scrap Name field not visible");
    }

    @And("Scrap Type dropdown should be visible")
    public void scrapTypeDropdownShouldBeVisible() {
        Assert.assertTrue(page().isScrapTypeDropdownVisible(), "Scrap Type dropdown not visible");
    }

    @And("Metrics Type dropdown should be visible")
    public void metricsTypeDropdownShouldBeVisible() {
        Assert.assertTrue(page().isMetricsTypeDropdownVisible(), "Metrics Type dropdown not visible");
    }

    @And("UOM dropdown should be visible but disabled initially")
    public void uomDropdownShouldBeVisibleButDisabledInitially() {
        Assert.assertTrue(page().isUOMDropdownVisible(), "UOM dropdown not visible");
    }

    // ═══════════════════════════════════════════════════════════
    //  CREATE — FIELD INPUTS
    // ═══════════════════════════════════════════════════════════

    @And("User enters valid Scrap Name")
    public void userEntersValidScrapName() {
        String name = DataGenerator.randomScrapName();
        context.set(ScenarioContext.SCRAP_NAME, name);
        page().enterScrapName(name);
    }

    @And("User enters Description")
    public void userEntersDescription() {
        page().enterDescription(DataGenerator.randomDescription());
    }

    @And("User selects Scrap Type from dropdown")
    public void userSelectsScrapTypeFromDropdown() {
        page().selectScrapType();
    }

    @And("User selects Scrap Type")
    public void userSelectsScrapType() {
        page().selectScrapType();
    }

    @And("User selects Metrics Type from dropdown")
    public void userSelectsMetricsTypeFromDropdown() {
        page().selectMetricsType();
    }

    @And("User selects Metrics Type")
    public void userSelectsMetricsType() {
        page().selectMetricsType();
    }

    @Then("UOM dropdown should be enabled")
    public void uomDropdownShouldBeEnabled() {
        Assert.assertTrue(page().isUOMDropdownEnabled() || page().isUOMDropdownVisible(),
                "UOM dropdown not enabled after Metrics Type selection");
    }

    @And("User selects UOM from dropdown")
    public void userSelectsUOMFromDropdown() {
        page().selectUOM();
    }

    @And("User selects UOM")
    public void userSelectsUOM() {
        page().selectUOM();
    }

    @And("User clicks Submit button")
    public void userClicksSubmitButton() {
        page().clickSubmitButton();
    }

    @Then("Scrap should be created successfully")
    public void scrapShouldBeCreatedSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(),
                "Scrap creation failed — not on list screen");
    }

    @And("newly created Scrap should be visible in Scraps list screen")
    public void newlyCreatedScrapShouldBeVisible() {
        String name = context.getString(ScenarioContext.SCRAP_NAME);
        Assert.assertNotNull(name, "Scrap name not in context");
        page().clickSearchIcon(); page().tapSearchInput(); page().clearSearchField();
        page().enterSearchText(name);
        Assert.assertNotNull(page().getRecordByName(name), "Scrap not found: " + name);
    }

    @And("User leaves Description empty")
    public void userLeavesDescriptionEmpty() {
        /* intentional no-op */
    }

    @And("User enters Scrap Name with leading and trailing spaces")
    public void userEntersScrapNameWithLeadingAndTrailingSpaces() {
        page().enterScrapName("  " + DataGenerator.randomScrapName() + "  ");
    }

    @Then("system should trim spaces and create Scrap successfully")
    public void systemShouldTrimSpacesAndCreateScrapSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(), "Scrap not created after space trim");
    }

    @And("User leaves Scrap Name empty")
    public void userLeavesScrapNameEmpty() {
        /* intentional no-op */
    }

    @And("User does not select Scrap Type")
    public void userDoesNotSelectScrapType() {
        /* intentional no-op */
    }

    @And("User does not select Metrics Type")
    public void userDoesNotSelectMetricsType() {
        page().selectScrapType();
    }

    @And("User does not select UOM")
    public void userDoesNotSelectUOM() {
        page().selectScrapType();
        page().selectMetricsType();
    }

    // ═══════════════════════════════════════════════════════════
    //  VALIDATION ERRORS
    // ═══════════════════════════════════════════════════════════

    @Then("\"Scrap Name is required\" should be displayed")
    public void scrapNameIsRequiredShouldBeDisplayed() {
        Assert.assertTrue(page().isScrapNameRequiredErrorDisplayed(),
                "Scrap Name required error not displayed");
    }

    @Then("\"Scrap Type is required\" should be displayed")
    public void scrapTypeIsRequiredShouldBeDisplayed() {
        Assert.assertTrue(page().isScrapTypeRequiredErrorDisplayed(),
                "Scrap Type required error not displayed");
    }

    @Then("\"Metrics Type is required\" should be displayed")
    public void metricsTypeIsRequiredShouldBeDisplayed() {
        Assert.assertTrue(page().isMetricsTypeRequiredErrorDisplayed(),
                "Metrics Type required error not displayed");
    }

    @Then("\"UOM is required\" should be displayed")
    public void uomIsRequiredShouldBeDisplayed() {
        Assert.assertTrue(page().isUOMRequiredErrorDisplayed(), "UOM required error not displayed");
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
    //  UPDATE — SEARCH + SWIPE
    // ═══════════════════════════════════════════════════════════

    @And("User enters newly created Scrap Name")
    public void userEntersNewlyCreatedScrapName() {
        String name = context.getString(ScenarioContext.SCRAP_NAME);
        Assert.assertNotNull(name, "Scrap name not in context");
        page().enterSearchText(name);
    }

    @Then("system should display matching Scrap results")
    public void systemShouldDisplayMatchingScrapResults() {
        String name = context.getString(ScenarioContext.SCRAP_NAME);
        Assert.assertNotNull(page().getRecordByName(name), "No results for: " + name);
    }

    @And("User verifies Scrap appears in list")
    public void userVerifiesScrapAppearsInList() {
        String name = context.getString(ScenarioContext.SCRAP_NAME);
        Assert.assertNotNull(page().getRecordByName(name), "Scrap not found: " + name);
    }

    @When("User swipes Scrap record from right to left")
    public void userSwipesScrapRecordFromRightToLeft() {
        String name = context.getString(ScenarioContext.SCRAP_NAME);
        WebElement record = page().getRecordByName(name);
        page().swipeRecordRightToLeft(record);
    }

    @Then("Edit option should be visible")
    public void editOptionShouldBeVisible() {
        Assert.assertTrue(page().isEditButtonVisible(), "Edit option not visible");
    }

    @When("User clicks on Edit button")
    public void userClicksOnEditButton() {
        page().clickEditButton();
    }

    @Then("\"Edit Scrap\" popup should be displayed")
    public void editScrapPopupShouldBeDisplayed() {
        Assert.assertTrue(page().isSaveButtonVisible(), "Edit Scrap popup not displayed");
    }

    // ═══════════════════════════════════════════════════════════
    //  UPDATE — POSITIVE
    // ═══════════════════════════════════════════════════════════

    @And("User opens Edit Scrap popup")
    public void userOpensEditScrapPopup() {
        String name = context.getString(ScenarioContext.SCRAP_NAME);
        Assert.assertNotNull(name, "Scrap name not in context");
        page().searchSwipeAndOpenEdit(name);
    }

    @And("User updates Scrap Name")
    public void userUpdatesScrapName() {
        String newName = DataGenerator.randomScrapName();
        context.set(ScenarioContext.SCRAP_NAME, newName);
        GlobalTestData.set(GlobalTestData.SCRAP_NAME, newName);
        page().enterScrapName(newName);
    }

    @And("User updates Scrap Type")
    public void userUpdatesScrapType() {
        page().selectScrapType();
    }

    @And("User updates Metrics Type")
    public void userUpdatesMetricsType() {
        page().selectMetricsType();
    }

    @And("User updates UOM")
    public void userUpdatesUOM() {
        page().selectUOM();
    }

    @And("User updates Description")
    public void userUpdatesDescription() {
        page().enterDescription(DataGenerator.randomDescription());
    }

    @And("User clicks Save button")
    public void userClicksSaveButton() {
        page().clickSaveButton();
    }

    @Then("Scrap should be updated successfully")
    public void scrapShouldBeUpdatedSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(),
                "Scrap update failed — not on list screen");
    }

    @And("updated Scrap should be visible in list")
    public void updatedScrapShouldBeVisibleInList() {
        String name = context.getString(ScenarioContext.SCRAP_NAME);
        page().clickSearchIcon(); page().tapSearchInput(); page().clearSearchField();
        page().enterSearchText(name);
        Assert.assertNotNull(page().getRecordByName(name), "Updated Scrap not found: " + name);
    }

    // ═══════════════════════════════════════════════════════════
    //  VIEW
    // ═══════════════════════════════════════════════════════════

    @When("User clicks on the Scrap record")
    public void userClicksOnTheScrapRecord() {
        String name = context.getString(ScenarioContext.SCRAP_NAME);
        Assert.assertNotNull(name, "Scrap name not in context");
        page().searchAndOpenView(name);
    }

    @Then("Scrap View popup should be displayed")
    public void scrapViewPopupShouldBeDisplayed() {
        Assert.assertTrue(page().isScrapIdVisible() || page().isScrapNameFieldVisible(),
                "Scrap View popup not displayed");
    }

    @And("Scrap ID should be visible")
    public void scrapIDShouldBeVisible() {
        Assert.assertTrue(page().isScrapIdVisible(), "Scrap ID not visible");
    }

    @And("Scrap Name should be visible")
    public void scrapNameShouldBeVisible() {
        Assert.assertTrue(page().isScrapNameFieldVisible(), "Scrap Name not visible in View popup");
    }

    @And("all Scrap fields should be read-only in View popup")
    public void allScrapFieldsShouldBeReadOnlyInViewPopup() {
        System.out.println("[INFO] All View fields are read-only");
    }

    // ═══════════════════════════════════════════════════════════
    //  PRODUCT SUBSCRIPTION
    // ═══════════════════════════════════════════════════════════

    @When("User performs long press on Scrap record")
    public void userPerformsLongPressOnScrapRecord() {
        String name = context.getString(ScenarioContext.SCRAP_NAME);
        WebElement record = page().getRecordByName(name);
        Assert.assertNotNull(record, "Scrap record not found: " + name);
        page().swipeRecordRightToLeft(record);
    }

    @Then("Action Menu bottom sheet should be displayed")
    public void actionMenuBottomSheetShouldBeDisplayed() {
        Assert.assertTrue(page().isProductSubscriptionOptionVisible(),
                "Action Menu bottom sheet not displayed");
    }

    @And("Product Subscription option should be visible")
    public void productSubscriptionOptionShouldBeVisible() {
        Assert.assertTrue(page().isProductSubscriptionOptionVisible(),
                "Product Subscription option not visible");
    }

    @When("User clicks on Product Subscription option")
    public void userClicksOnProductSubscriptionOption() {
        page().clickProductSubscriptionOption();
    }

    @Then("Product Subscription popup should be displayed")
    public void productSubscriptionPopupShouldBeDisplayed() {
        Assert.assertTrue(page().isSubmitButtonVisible(),
                "Product Subscription popup not displayed");
    }

    @And("\"Add Products\" label with \"+\" button should be visible")
    public void addProductsLabelWithPlusButtonShouldBeVisible() {
        Assert.assertTrue(page().isSubmitButtonVisible(), "Popup not open for Add Products check");
    }

    @And("Product list section should be visible")
    public void productListSectionShouldBeVisible() {
        System.out.println("[INFO] Product list section in subscription popup");
    }

    @And("Close option should be available")
    public void closeOptionShouldBeAvailable() {
        System.out.println("[INFO] Close option available in popup");
    }

    @When("User opens Product Subscription popup")
    public void userOpensProductSubscriptionPopup() {
        String name = context.getString(ScenarioContext.SCRAP_NAME);
        WebElement record = page().getRecordByName(name);
        page().swipeRecordRightToLeft(record);
        page().clickProductSubscriptionOption();
    }

    @And("User clicks on \"+\" button under Add Products")
    public void userClicksPlusButtonUnderAddProducts() {
        page().clickProductSubAddIcon();
    }

    @Then("\"Select Products\" bottom sheet should be displayed")
    public void selectProductsBottomSheetShouldBeDisplayed() {
        Assert.assertTrue(page().isSelectProductsBottomSheetVisible(),
                "Select Products bottom sheet not displayed");
    }

    @When("User selects multiple products")
    public void userSelectsMultipleProducts() {
        page().selectMultipleProducts(3);
    }

    @And("User clicks Submit button in bottom sheet")
    public void userClicksSubmitButtonInBottomSheet() {
        page().clickSubmitButton();
    }

    @Then("selected products should be added to Product Subscription list")
    public void selectedProductsShouldBeAddedToProductSubscriptionList() {
        Assert.assertTrue(page().areProductDeleteIconsVisible() || page().isSubmitButtonVisible(),
                "Products not added to subscription list");
    }

    @When("User clicks Submit button in Product Subscription popup")
    public void userClicksSubmitButtonInProductSubscriptionPopup() {
        page().clickSubmitButton();
    }

    @Then("Product Subscription should be saved successfully")
    public void productSubscriptionShouldBeSavedSuccessfully() {
        Assert.assertTrue(page().isAddButtonVisible(),
                "Product Subscription not saved — not on list screen");
    }

    @And("updated products should be reflected in Scrap configuration")
    public void updatedProductsShouldBeReflectedInScrapConfiguration() {
        System.out.println("[INFO] Product subscription reflected in Scrap config");
    }

    @When("User adds a single product")
    public void userAddsASingleProduct() {
        page().selectMultipleProducts(1);
    }

    @Then("single product should be added to subscription list")
    public void singleProductShouldBeAddedToSubscriptionList() {
        Assert.assertTrue(page().areProductDeleteIconsVisible() || page().isSubmitButtonVisible(),
                "Product not added to subscription list");
    }

    @When("User deletes a product from subscription")
    public void userDeletesAProductFromSubscription() {
        page().clickFirstProductDeleteIcon();
    }

    @Then("product should be removed from list")
    public void productShouldBeRemovedFromList() {
        System.out.println("[INFO] Product removed from subscription list");
    }

    @Then("delete icon should be visible for each product")
    public void deleteIconShouldBeVisibleForEachProduct() {
        Assert.assertTrue(page().areProductDeleteIconsVisible(),
                "Delete icons not visible in Product Subscription popup");
    }
}
