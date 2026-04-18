Feature: Operator Machine Subscription
  As an admin
  I want to manage machine subscriptions for operator
  So that operator can access assigned machines

  Background:
    When click on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When click on "configurations" section
    Then verify the "User" section displayed
    When click on "Operators" feature
    Then verify that the user navigate to the "Operators" list screen

  # ==================================================
  # ✅ NAVIGATION SCENARIOS
  # ==================================================

  Scenario: Navigate to Machine Subscription via long press
    When user searches operator "anil@example.com"
    And user long presses on operator record
    Then action menu bottom sheet should be displayed
    When user clicks on "Machine Subscription"
    Then Machine Subscription popup should be displayed

  # ==================================================
  # ✅ POSITIVE SCENARIOS (ADD FLOW)
  # ==================================================

  Scenario: Add machine subscription successfully
    When user clicks on "+" icon in Machine Subscription popup
    Then Select Machines bottom sheet should be displayed
    When user selects multiple machines
    And clicks Submit
    Then selected machines should be displayed in Machine Subscription popup
    When user clicks final Submit
    Then machine subscription should be saved successfully

  Scenario: Add single machine subscription
    When user selects one machine
    And submits selection
    Then selected machine should be displayed

  Scenario: Add multiple machines
    When user selects multiple machines
    And submits selection
    Then all selected machines should be displayed

  Scenario: Verify persistence after saving machines
    When user saves machine subscription
    And reopens Machine Subscription popup
    Then selected machines should be displayed

  # ==================================================
  # ❌ NEGATIVE SCENARIOS (ADD FLOW)
  # ==================================================

  Scenario: Submit without selecting machines
    When user clicks "+" icon
    And clicks Submit without selecting machines
    Then validation message should be displayed

  Scenario: Add duplicate machine
    When user selects already subscribed machine
    And clicks Submit
    Then system should prevent duplicate machine addition

  Scenario: Network failure during add
    When user submits machine subscription without internet
    Then error message should be displayed

  Scenario: API failure during add
    When backend returns failure response during subscription
    Then proper error message should be shown

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS (ADD FLOW)
  # ==================================================

  Scenario: Select maximum machines
    When user selects maximum number of machines
    Then system should handle correctly

  Scenario: Rapid selection/deselection
    When user quickly selects and deselects machines
    Then selection state should be maintained correctly

  Scenario: Large machine list handling
    When machine list is large
    Then scrolling should work properly

  Scenario: Special character machine names
    When machine names contain special characters
    Then they should display correctly

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS (ADD FLOW)
  # ==================================================

  Scenario: Verify Machine Subscription popup UI
    Then popup should contain machine list, "+" icon, delete icon and Submit button

  Scenario: Verify Select Machines bottom sheet UI
    Then multi-selection machine list should be displayed

  Scenario: Verify selection highlighting
    When user selects machines
    Then selected machines should be highlighted

  Scenario: Verify scroll functionality
    Then user should be able to scroll machine list

  Scenario: Verify submit button behavior
    Then submit button should validate selection

  # ==================================================
  # 🔎 FIELD / SELECTION VALIDATIONS (ADD FLOW)
  # ==================================================

  Scenario: Validate machine selection mandatory
    When no machine is selected
    Then error should be displayed

  Scenario: Validate selected machines display
    When machines are selected
    Then they should appear correctly in popup

  # ==================================================
  # 🗑️ DELETE FLOW
  # ==================================================

  # ==================================================
  # ✅ POSITIVE SCENARIOS (DELETE FLOW)
  # ==================================================

  Scenario: Delete single machine subscription
    When user opens Machine Subscription popup for operator "anil@example.com"
    And clicks delete icon for a machine
    Then machine should be removed from list
    When user clicks Submit
    Then deletion should be saved successfully

  Scenario: Delete multiple machine subscriptions
    When user deletes multiple machines
    And clicks Submit
    Then all selected machines should be removed

  Scenario: Verify persistence after deletion
    When user deletes machines and saves
    And reopens Machine Subscription popup
    Then deleted machines should not be displayed

  # ==================================================
  # ❌ NEGATIVE SCENARIOS (DELETE FLOW)
  # ==================================================

  Scenario: Delete without saving
    When user deletes machine
    And closes popup without submitting
    Then deletion should not be saved

  Scenario: Delete all machines
    When user removes all machines
    And clicks Submit
    Then system should handle empty state correctly

  Scenario: Network failure during delete
    When user deletes machine without internet
    Then error message should be displayed

  Scenario: API failure during delete
    When backend returns failure response during deletion
    Then proper error message should be shown

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS (DELETE FLOW)
  # ==================================================

  Scenario: Rapid delete actions
    When user quickly deletes machines
    Then system should handle correctly

  Scenario: Delete last machine
    When only one machine exists
    And user deletes it
    Then list should become empty

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS (DELETE FLOW)
  # ==================================================

  Scenario: Verify delete icon visibility
    Then delete icon should be visible for each machine

  Scenario: Verify UI updates after deletion
    When machine is deleted
    Then UI should update immediately

  Scenario: Verify empty state UI
    When no machines exist
    Then empty message should be displayed

  # ==================================================
  # 🔎 FIELD / VALIDATION (DELETE FLOW)
  # ==================================================

  Scenario: Validate machine removal
    When machine is deleted
    Then it should not appear in list