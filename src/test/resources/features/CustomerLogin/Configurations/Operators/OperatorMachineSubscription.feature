@regression @p2
Feature: Operator Machine Subscription for Newly Created Operator

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Users" section is displayed
    When User clicks on "Operators" feature
    Then verify user navigates to "Operators" list screen
    And User has created an operator with all mandatory fields

  # ==================================================
  # ✅ NAVIGATION SCENARIOS
  # ==================================================

  @smoke @p1
  Scenario: Navigate to Machine Subscription via long press
    When user long presses on operator record
    Then action menu bottom sheet should be displayed
    When User clicks on "Machine Subscription" option
    Then Machine Subscription popup should be displayed

  # ==================================================
  # ✅ POSITIVE SCENARIOS — ADD FLOW
  # ==================================================

  @smoke @regression @p1
  Scenario: Add machine subscription successfully
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user clicks add icon in Machine Subscription popup
    Then Select Machines bottom sheet should be displayed
    When user selects multiple machines
    And submits selection
    Then selected machines should be displayed in Machine Subscription popup
    When user clicks final Submit
    Then machine subscription should be saved successfully

  @regression @p2
  Scenario: Add single machine subscription
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user clicks add icon in Machine Subscription popup
    And user selects one machine
    And submits selection
    Then selected machines should be displayed

  @regression @p2
  Scenario: Verify persistence after saving machines
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user clicks add icon in Machine Subscription popup
    And user selects multiple machines
    And submits selection
    And user clicks final Submit
    And user saves machine subscription
    And reopens Machine Subscription popup
    Then selected machines should be displayed

  # ==================================================
  # ❌ NEGATIVE SCENARIOS — ADD FLOW
  # ==================================================

  @negative @regression @p2
  Scenario: Submit without selecting machines
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user clicks add icon in Machine Subscription popup
    And clicks Submit without selecting machines
    Then validation message should be displayed

  @negative @regression @p2
  Scenario: Add duplicate machine
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user clicks add icon in Machine Subscription popup
    And user selects already subscribed machine
    And submits selection
    Then system should prevent duplicate machine addition

  @negative @p3
  Scenario: Network failure during machine subscription add
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user submits machine subscription without internet
    Then error message should be displayed

  @negative @p3
  Scenario: API failure during machine subscription add
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And backend returns failure response during subscription
    Then proper error message should be shown

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS — ADD FLOW
  # ==================================================

  @sanity @p3
  Scenario: Select maximum machines
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user clicks add icon in Machine Subscription popup
    And user selects maximum number of machines
    Then system should handle correctly

  @sanity @p3
  Scenario: Rapid selection and deselection
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user clicks add icon in Machine Subscription popup
    And user quickly selects and deselects machines
    Then selection state should be maintained correctly

  @sanity @p3
  Scenario: Large machine list handling
    When machine list is large
    And user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user clicks add icon in Machine Subscription popup
    Then scrolling should work properly

  @sanity @p3
  Scenario: Special character machine names
    When machine names contain special characters
    And user long presses on operator record
    And User clicks on "Machine Subscription" option
    Then they should display correctly

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS — ADD FLOW
  # ==================================================

  @regression @p2
  Scenario: Verify Machine Subscription popup UI
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    Then popup should contain machine list, "+" icon, delete icon and Submit button

  @regression @p2
  Scenario: Verify Select Machines bottom sheet UI
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user clicks add icon in Machine Subscription popup
    Then multi-selection machine list should be displayed

  @regression @p2
  Scenario: Verify selection highlighting
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user clicks add icon in Machine Subscription popup
    And user selects machines
    Then selected machines should be highlighted

  @regression @p3
  Scenario: Verify scroll in machine list
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user clicks add icon in Machine Subscription popup
    Then user should be able to scroll machine list

  @regression @p2
  Scenario: Verify submit validates selection
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user clicks add icon in Machine Subscription popup
    Then submit button should validate selection

  # ==================================================
  # 🗑️ DELETE FLOW — POSITIVE SCENARIOS
  # ==================================================

  @regression @p2
  Scenario: Delete single machine subscription
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And clicks delete icon for a machine
    Then machine should be removed from list
    When user clicks final Submit
    Then deletion should be saved successfully

  @regression @p2
  Scenario: Delete multiple machine subscriptions
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user deletes multiple machines
    And user clicks final Submit
    Then all selected machines should be removed

  @regression @p2
  Scenario: Verify persistence after deletion
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user deletes machines and saves
    And reopens Machine Subscription popup
    Then deleted machines should not be displayed

  # ==================================================
  # ❌ DELETE FLOW — NEGATIVE SCENARIOS
  # ==================================================

  @negative @regression @p2
  Scenario: Delete machine without saving
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user deletes machine
    And closes popup without submitting
    Then deletion should not be saved

  @negative @regression @p2
  Scenario: Delete all machines
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user removes all machines
    And user clicks final Submit
    Then system should handle empty state correctly

  @negative @p3
  Scenario: Network failure during machine deletion
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user deletes machine without internet
    Then error message should be displayed

  @negative @p3
  Scenario: API failure during machine deletion
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And backend returns failure response during deletion
    Then proper error message should be shown

  # ==================================================
  # 📱 DELETE FLOW — UI VALIDATION
  # ==================================================

  @regression @p2
  Scenario: Verify delete icon visibility
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    Then delete icon should be visible for each machine

  @regression @p2
  Scenario: Verify UI updates after deletion
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user deletes machine
    Then UI should update immediately

  @regression @p2
  Scenario: Verify empty state when no machines exist
    When no machines exist
    And user long presses on operator record
    And User clicks on "Machine Subscription" option
    Then empty message should be displayed

  # ==================================================
  # 🔎 VALIDATION SCENARIOS
  # ==================================================

  @regression @p2
  Scenario: Validate machine selection is mandatory
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user clicks add icon in Machine Subscription popup
    And no machine is selected
    Then error should be displayed

  @regression @p2
  Scenario: Validate selected machines display correctly
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user clicks add icon in Machine Subscription popup
    And machines are selected
    Then they should appear correctly in popup

  @regression @p2
  Scenario: Validate machine removal from subscription
    When user long presses on operator record
    And User clicks on "Machine Subscription" option
    And user deletes machine
    Then it should not appear in list
