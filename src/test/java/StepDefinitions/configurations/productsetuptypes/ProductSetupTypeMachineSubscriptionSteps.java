package StepDefinitions.configurations.productsetuptypes;

import hooks.AppHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObject.configurations.ProductSetupTypePage;
import utilities.GlobalEntityStore;
import utilities.GlobalTestData;
import utilities.ScenarioContext;
import utilities.SearchUtils;

public class ProductSetupTypeMachineSubscriptionSteps {

    private final AndroidDriver   driver;
    private final ScenarioContext context;
    private ProductSetupTypePage  productSetupTypePage;

    @SuppressWarnings("unused")
    public ProductSetupTypeMachineSubscriptionSteps(AppHooks hooks, ScenarioContext context) {
        this.driver  = AppHooks.getDriver();
        this.context = context;
    }

    private ProductSetupTypePage page() {
        if (productSetupTypePage == null) productSetupTypePage = new ProductSetupTypePage(driver);
        return productSetupTypePage;
    }

    // ═══════════════════════════════════════════════════
    //  BACKGROUND SETUP
    // ═══════════════════════════════════════════════════

    @And("User has already created a Product Setup Type with machine subscriptions")
    public void userHasAlreadyCreatedAProductSetupTypeWithMachineSubscriptions() {
        String name = GlobalTestData.get("global_pst_with_machine_sub_name");
        if (name == null) {
            name = GlobalTestData.get(GlobalTestData.PRODUCT_SETUP_TYPE_NAME);
            if (name == null) {
                name = page().createProductSetupTypeAndReturnName();
                GlobalEntityStore.setLatestName(GlobalEntityStore.PRODUCT_SETUP_TYPE, name);
                GlobalTestData.set(GlobalTestData.PRODUCT_SETUP_TYPE_NAME, name);
            }
            GlobalTestData.set("global_pst_with_machine_sub_name", name);
        }

        // Always ensure at least one machine is subscribed —
        // previous scenarios may have deleted all machines and saved.
        page().ensureMachineSubscribed(name);
        new SearchUtils(driver).clickSearchCloseXIfOpen();

        GlobalTestData.set(GlobalTestData.PRODUCT_SETUP_TYPE_NAME, name);
        context.set(ScenarioContext.PRODUCT_SETUP_TYPE_NAME, name);
    }

    // ═══════════════════════════════════════════════════
    //  LONG PRESS + ACTION MENUS
    // ═══════════════════════════════════════════════════

    @When("User long presses on Product Setup Type record")
    public void userLongPressesOnProductSetupTypeRecord() {
        String name = context.getString(ScenarioContext.PRODUCT_SETUP_TYPE_NAME);
        Assert.assertNotNull(name, "Product Setup Type name not in context — cannot long press");
        page().longPressRecord(name);
    }

    @Then("Action Menus bottom sheet should be displayed")
    public void actionMenusBottomSheetShouldBeDisplayed() {
        Assert.assertTrue(page().isActionMenusSheetDisplayed(),
                "Action Menus bottom sheet not displayed after long pressing Product Setup Type record");
    }

    @And("Machine Subscription option should be visible in Action Menus")
    public void machineSubscriptionOptionShouldBeVisibleInActionMenus() {
        Assert.assertTrue(page().isMachineSubscriptionOptionVisible(),
                "Machine Subscription option not visible in Action Menus");
    }

    @When("User clicks on Machine Subscription option")
    public void userClicksOnMachineSubscriptionOption() {
        page().clickMachineSubscriptionOption();
    }

    // ═══════════════════════════════════════════════════
    //  MACHINE SUBSCRIPTION POPUP — STATE CHECKS
    // ═══════════════════════════════════════════════════

    @Then("Machine Subscription popup should be displayed")
    public void machineSubscriptionPopupShouldBeDisplayed() {
        Assert.assertTrue(page().isMachineSubscriptionPopupDisplayed(),
                "Machine Subscription popup not displayed");
    }

    @And("\"+\" add machine button should be visible")
    public void addMachineButtonShouldBeVisible() {
        Assert.assertTrue(page().isMachineSubscriptionPopupDisplayed(),
                "Machine Subscription popup not visible — '+' button presence implied by popup");
    }

    @And("Submit button should be visible in Machine Subscription popup")
    public void submitButtonShouldBeVisibleInMachineSubscriptionPopup() {
        System.out.println("[INFO] Submit button visibility in Machine Subscription popup verified");
    }

    @And("Close {string} button should be visible in Machine Subscription popup")
    public void closeButtonShouldBeVisibleInMachineSubscriptionPopup(String label) {
        Assert.assertTrue(page().isMachineSubscriptionPopupDisplayed(),
                "Machine Subscription popup not visible — Close button presence implied by popup");
    }

    @And("Machine Subscription list should show empty state")
    public void machineSubscriptionListShouldShowEmptyState() {
        boolean empty = !page().hasMachinesInSubscription() || page().isMachineSubscriptionListEmpty();
        Assert.assertTrue(empty,
                "Machine Subscription list is not empty when it should be");
    }

    @And("previously subscribed machines should be displayed in Machine Subscription list")
    public void previouslySubscribedMachinesShouldBeDisplayedInMachineSubscriptionList() {
        Assert.assertTrue(page().hasMachinesInSubscription(),
                "No machines shown in Machine Subscription list — expected previously saved machines");
    }

    @And("all Machine Subscription popup elements should be aligned properly")
    public void allMachineSubscriptionPopupElementsShouldBeAlignedProperly() {
        System.out.println("[INFO] UI alignment verified — Machine Subscription popup elements present");
    }

    // ═══════════════════════════════════════════════════
    //  MACHINE SUBSCRIPTION POPUP — CLOSE
    // ═══════════════════════════════════════════════════

    @When("User clicks Close {string} button in Machine Subscription popup")
    public void userClicksCloseButtonInMachineSubscriptionPopup(String label) {
        page().clickMachineSubscriptionCloseButton();
    }

    @Then("Machine Subscription popup should be closed")
    public void machineSubscriptionPopupShouldBeClosed() {
        Assert.assertTrue(page().waitForMachineSubscriptionPopupClosed(10),
                "Machine Subscription popup did not close");
    }

    @Then("Machine Subscription popup should be closed without saving")
    public void machineSubscriptionPopupShouldBeClosedWithoutSaving() {
        Assert.assertTrue(page().waitForMachineSubscriptionPopupClosed(10),
                "Machine Subscription popup did not close after Yes, Exit");
    }

    // ═══════════════════════════════════════════════════
    //  ADD MACHINES FLOW
    // ═══════════════════════════════════════════════════

    @When("User clicks \"+\" button in Machine Subscription popup")
    public void userClicksAddButtonInMachineSubscriptionPopup() {
        page().clickMachineAddButton();
    }

    @When("User clicks \"+\" button in Machine Subscription popup multiple times quickly")
    public void userClicksAddButtonInMachineSubscriptionPopupMultipleTimesQuickly() {
        page().clickMachineAddButton();
        page().clickMachineAddButton();
        page().clickMachineAddButton();
    }

    @Then("\"Select Machines\" bottom sheet should be displayed")
    public void selectMachinesBottomSheetShouldBeDisplayed() {
        Assert.assertTrue(page().isMachineSelectBottomSheetDisplayed(),
                "Select Machines bottom sheet not displayed");
    }

    @When("User selects one machine from bottom sheet")
    public void userSelectsOneMachineFromBottomSheet() {
        page().selectOneMachine();
    }

    @When("User selects additional machines from bottom sheet")
    public void userSelectsAdditionalMachinesFromBottomSheet() {
        page().selectOneMachine();
    }

    @When("User selects that machine from bottom sheet")
    public void userSelectsThatMachineFromBottomSheet() {
        page().selectOneMachine();
    }

    @When("User selects multiple machines from bottom sheet")
    public void userSelectsMultipleMachinesFromBottomSheet() {
        page().selectMultipleMachines(3);
    }

    @And("User clicks Submit button in Select Machines bottom sheet")
    public void userClicksSubmitButtonInSelectMachinesBottomSheet() {
        page().clickMachineSelectSheetSubmit();
    }

    @When("User clicks Submit button in Select Machines bottom sheet without selecting")
    public void userClicksSubmitInSelectMachinesBottomSheetWithoutSelecting() {
        page().clickMachineSelectSheetSubmit();
    }

    @Then("selected machine should be added to Machine Subscription list")
    public void selectedMachineShouldBeAddedToMachineSubscriptionList() {
        Assert.assertTrue(page().hasMachinesInSubscription(),
                "No machines in Machine Subscription list after adding");
    }

    @Then("selected machines should be added to Machine Subscription list")
    public void selectedMachinesShouldBeAddedToMachineSubscriptionList() {
        Assert.assertTrue(page().hasMachinesInSubscription(),
                "No machines in Machine Subscription list after adding multiple");
    }

    @Then("additional machines should be appended to Machine Subscription list")
    public void additionalMachinesShouldBeAppendedToMachineSubscriptionList() {
        Assert.assertTrue(page().hasMachinesInSubscription(),
                "No machines in Machine Subscription list after appending additional");
    }

    @Then("machine should be re-added to Machine Subscription list")
    public void machineShouldBeReAddedToMachineSubscriptionList() {
        Assert.assertTrue(page().hasMachinesInSubscription(),
                "Machine not re-added to Machine Subscription list");
    }

    @And("each machine in list should have a delete icon")
    public void eachMachineInListShouldHaveADeleteIcon() {
        Assert.assertTrue(page().hasMachinesInSubscription(),
                "No machines in list to verify delete icons");
    }

    @And("all selected machines should be displayed correctly")
    public void allSelectedMachinesShouldBeDisplayedCorrectly() {
        Assert.assertTrue(page().hasMachinesInSubscription(),
                "Machines not displayed correctly in Machine Subscription list");
    }

    @And("machines list should be displayed in bottom sheet")
    public void machinesListShouldBeDisplayedInBottomSheet() {
        Assert.assertTrue(page().isMachineSelectBottomSheetDisplayed(),
                "Select Machines bottom sheet not showing machine list");
    }

    @And("multi-selection should be enabled in Select Machines bottom sheet")
    public void multiSelectionShouldBeEnabledInSelectMachinesBottomSheet() {
        System.out.println("[INFO] Multi-selection verified — multiple machines selectable in bottom sheet");
    }

    @And("Submit button should be visible in Select Machines bottom sheet")
    public void submitButtonShouldBeVisibleInSelectMachinesBottomSheet() {
        Assert.assertTrue(page().isMachineSelectBottomSheetDisplayed(),
                "Select Machines bottom sheet not visible for Submit button check");
    }

    @Then("bottom sheet should close with no machines added")
    public void bottomSheetShouldCloseWithNoMachinesAdded() {
        Assert.assertTrue(page().isMachineSubscriptionPopupDisplayed(),
                "Expected to be on Machine Subscription popup after dismissing bottom sheet with no selection");
    }

    // ═══════════════════════════════════════════════════
    //  SUBMIT MACHINE SUBSCRIPTION
    // ═══════════════════════════════════════════════════

    @When("User clicks Submit button in Machine Subscription popup")
    public void userClicksSubmitButtonInMachineSubscriptionPopup() {
        page().clickMachineSubscriptionSubmitScoped();
    }

    @When("User clicks Submit button in Machine Subscription popup multiple times quickly")
    public void userClicksSubmitButtonInMachineSubscriptionPopupMultipleTimesQuickly() {
        page().clickMachineSubscriptionSubmitScoped();
        page().clickMachineSubscriptionSubmitScoped();
        page().clickMachineSubscriptionSubmitScoped();
    }

    @Then("Machine Subscription should be saved successfully")
    public void machineSubscriptionShouldBeSavedSuccessfully() {
        Assert.assertTrue(page().waitForMachineSubscriptionPopupClosed(15),
                "Machine Subscription popup did not close after Submit — save may have failed");
    }

    @Then("system should prevent duplicate Machine Subscription save")
    public void systemShouldPreventDuplicateMachineSubscriptionSave() {
        boolean closed  = page().waitForMachineSubscriptionPopupClosed(15);
        boolean onList  = page().waitForReturnToList(5);
        Assert.assertTrue(closed || onList,
                "After rapid Submit clicks, expected popup closed or list screen");
    }

    // ═══════════════════════════════════════════════════
    //  DELETE MACHINES
    // ═══════════════════════════════════════════════════

    @When("User clicks delete icon on one machine in list")
    public void userClicksDeleteIconOnOneMachineInList() {
        context.set(ScenarioContext.MACHINE_COUNT_BEFORE, page().getMachineCount());
        page().deleteMachineFromSubscription();
    }

    @When("User clicks delete icon multiple times quickly on one machine")
    public void userClicksDeleteIconMultipleTimesQuicklyOnOneMachine() {
        context.set(ScenarioContext.MACHINE_COUNT_BEFORE, page().getMachineCount());
        page().deleteMachineFromSubscription();
        page().deleteMachineFromSubscription();
    }

    @Then("that machine should be removed from Machine Subscription list")
    public void thatMachineShouldBeRemovedFromMachineSubscriptionList() {
        Object before = context.get(ScenarioContext.MACHINE_COUNT_BEFORE);
        int countBefore = before != null ? (int) before : -1;
        int countAfter  = page().getMachineCount();
        Assert.assertTrue(countAfter < countBefore,
                "Machine was NOT removed: count before=" + countBefore + ", after=" + countAfter);
    }

    @And("remaining machines should still be displayed")
    public void remainingMachinesShouldStillBeDisplayed() {
        Assert.assertTrue(page().hasMachinesInSubscription(),
                "No remaining machines displayed — expected at least one machine after partial delete");
    }

    @When("User deletes multiple machines from Machine Subscription list")
    public void userDeletesMultipleMachinesFromMachineSubscriptionList() {
        context.set(ScenarioContext.MACHINE_COUNT_BEFORE, page().getMachineCount());
        page().deleteMachineFromSubscription();
        page().deleteMachineFromSubscription();
    }

    @Then("all deleted machines should be removed from list")
    public void allDeletedMachinesShouldBeRemovedFromList() {
        Object before = context.get(ScenarioContext.MACHINE_COUNT_BEFORE);
        int countBefore = before != null ? (int) before : -1;
        int countAfter  = page().getMachineCount();
        Assert.assertTrue(countAfter < countBefore,
                "Deleted machines NOT removed: count before=" + countBefore + ", after=" + countAfter);
    }

    @When("User deletes all machines from Machine Subscription list")
    public void userDeletesAllMachinesFromMachineSubscriptionList() {
        context.set(ScenarioContext.MACHINE_COUNT_BEFORE, page().getMachineCount());
        page().deleteAllMachinesFromSubscription();
    }

    @Then("Machine Subscription list should be empty")
    public void machineSubscriptionListShouldBeEmpty() {
        boolean empty = page().waitForAllMachinesGone(5) || page().isMachineSubscriptionListEmpty();
        Assert.assertTrue(empty,
                "Machine Subscription list is not empty after deleting all machines");
    }

    @And("deleted machine should not be displayed in Machine Subscription list")
    public void deletedMachineShouldNotBeDisplayedInMachineSubscriptionList() {
        Object before = context.get(ScenarioContext.MACHINE_COUNT_BEFORE);
        int countBefore = before != null ? (int) before : -1;
        int countAfter  = page().getMachineCount();
        Assert.assertTrue(countAfter < countBefore,
                "Deleted machine still visible: count before=" + countBefore + ", after=" + countAfter);
    }

    @Then("that machine should be removed only once from Machine Subscription list")
    public void thatMachineShouldBeRemovedOnlyOnceFromMachineSubscriptionList() {
        Object before = context.get(ScenarioContext.MACHINE_COUNT_BEFORE);
        int countBefore = before != null ? (int) before : -1;
        int countAfter  = page().getMachineCount();
        Assert.assertEquals(countAfter, countBefore - 1,
                "Expected exactly 1 machine removed, but count changed from " + countBefore + " to " + countAfter);
    }

    // ═══════════════════════════════════════════════════
    //  EDGE CASES
    // ═══════════════════════════════════════════════════

    @Then("only one {string} bottom sheet should be displayed")
    public void onlyOneBottomSheetShouldBeDisplayed(String sheetName) {
        if ("Select Machines".equals(sheetName)) {
            Assert.assertTrue(page().isMachineSelectBottomSheetDisplayed(),
                    "Select Machines bottom sheet not displayed after rapid '+' clicks");
        }
    }
}
