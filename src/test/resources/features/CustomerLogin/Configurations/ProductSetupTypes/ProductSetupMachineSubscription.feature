Feature: Manage Machine Subscription in Product Setup Type

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Product Setup Types" feature
    Then verify user navigates to "Product Setup Types" list screen
    And User has already created a Product Setup Type

  # =========================================================
  # 🔍 SEARCH + LONG PRESS + ACTION MENU FLOW
  # =========================================================

  Scenario: Open Machine Subscription popup via Action Menu
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User performs long press on Product Setup Type record
    Then Action Menu bottom sheet should be displayed
    And Machine Subscription option should be visible
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    And "Add Machine" label should be visible
    And "+" button should be visible
    And machine subscription list should be displayed if machines exist
    And Submit button should be visible

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Add machines to Product Setup Type
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And User clicks on "+" button under Add Machine
    Then "Select Machines" bottom sheet should be displayed
    When User selects multiple machines
    And User clicks Submit button in machine selection bottom sheet
    Then selected machines should be added to Machine Subscription list
    When User clicks Submit button in Machine Subscription popup
    Then machines should be saved successfully

  Scenario: Add single machine to Product Setup Type
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And User clicks on "+" button under Add Machine
    And User selects only one machine
    And User clicks Submit button in machine selection bottom sheet
    Then selected machine should be added to Machine Subscription list
    When User clicks Submit button in Machine Subscription popup
    Then machine subscription should be saved successfully

  Scenario: Open and close Machine Subscription popup without selecting machines
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And User clicks Submit button without selecting machines
    Then system should allow submission successfully

  Scenario: Add machines and verify list in popup before saving
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And User clicks on "+" button under Add Machine
    And User selects multiple machines
    And User clicks Submit button in machine selection bottom sheet
    Then selected machines should be displayed correctly in Machine Subscription popup

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Duplicate machine selection
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And User selects already added machine again
    Then system should prevent duplicate machine entry

  Scenario: Invalid machine selection state
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And machine is inactive or unavailable in selection list
    Then system should restrict machine selection

  Scenario: Submit without changes
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And User clicks Submit button without modification
    Then system should show "No changes detected" or allow safe submission

  Scenario: Select machines and close bottom sheet without submit
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And User clicks on "+" button under Add Machine
    And User selects machines in bottom sheet
    And User closes machine selection bottom sheet without submitting
    Then selected machines should not be added to Machine Subscription list

  # =========================================================
  # 🔁 DELETE FLOW
  # =========================================================

  Scenario: Delete machine from subscription list
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And machines are already added in subscription list
    And User clicks delete button on a machine
    Then machine should be removed from Machine Subscription list
    When User clicks Submit button in Machine Subscription popup
    Then updated machine subscription list should be saved successfully

  Scenario: Delete all machines from subscription list
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And User removes all machines from subscription list
    And User clicks Submit button in Machine Subscription popup
    Then system should allow empty machine subscription list

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid long press action
    When User searches for newly created Product Setup Type Name
    And User performs long press multiple times quickly on Product Setup Type record
    Then only one Action Menu should appear

  Scenario: Rapid multiple Submit clicks
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And User clicks Submit button multiple times quickly
    Then system should prevent duplicate requests

  Scenario: Large machine list handling
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And machine list contains many records
    Then list should support scrolling and multi-selection

  Scenario: Network failure during submission
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And User clicks Submit button without internet connection
    Then system should display network error message

  Scenario: Session timeout during Machine Subscription operation
    When session expires during Machine Subscription operation
    Then User should be redirected to login screen

  Scenario: Reopen Machine Subscription popup after close
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And User closes Machine Subscription popup
    And User reopens Machine Subscription popup
    Then previously saved machine subscription data should be displayed correctly

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Machine Subscription popup UI
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    Then Add Machine label should be visible
    And "+" button should be visible
    And machine subscription list section should be visible
    And Submit button should be visible

  Scenario: Verify Select Machines bottom sheet UI
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And User opens machine selection
    Then machine selection bottom sheet should display machine list
    And multi-selection should be enabled
    And Submit button should be visible

  Scenario: Verify delete button visibility
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    Then each added machine should have delete option

  Scenario: Verify popup close behavior
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And User closes popup using back or outside click
    Then Machine Subscription popup should be dismissed successfully

  # =========================================================
  # 🔍 MACHINE SELECTION SCENARIOS
  # =========================================================

  Scenario Outline: Validate machine selection behavior
    When User searches for newly created Product Setup Type Name
    And User performs long press on Product Setup Type record
    And User clicks on Machine Subscription option
    And User opens Select Machines bottom sheet
    And User selects "<selectionType>" machines
    Then system should handle machine selection correctly

    Examples:
      | selectionType |
      | single        |
      | multiple      |
      | duplicate     |
      | none          |

 