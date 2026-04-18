Feature: Machine Subscription Management
  As an admin
  I want to manage machine subscriptions for a user
  So that machines can be assigned or removed properly

  Background:
    When click on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When click on "configurations" section
    Then verify the "User" section displayed
    When click on "Users" feature
    Then verify that the user navigate to the "Users" list screen

  # ==================================================
  # ✅ POSITIVE SCENARIOS (ADD FLOW)
  # ==================================================

  Scenario: Verify long press opens action menu
    When user long presses on a user record
    Then action menu bottom sheet should be displayed
    And following options should be visible:
      | Machine Subscription |
      | Unit Subscription    |

  Scenario: Verify navigation to Machine Subscription popup
    When user long presses on a user record
    And click on "Machine Subscription"
    Then Machine Subscription popup should be displayed

  Scenario: Add machine subscription successfully
    When user selects user "anil@example.com" from list
    And opens machine subscription popup
    And click on add machine button
    And select machines:
      | Machine1 |
      | Machine2 |
    And confirm machine selection
    Then selected machines should be displayed in popup
    When I click on the submit button
    Then machine subscription should be saved successfully

  Scenario: Add single machine subscription
    When user opens machine subscription popup
    And select machines:
      | Machine1 |
    And confirm machine selection
    Then selected machine should be displayed

  Scenario: Add multiple machine subscriptions
    When user opens machine subscription popup
    And select machines:
      | Machine1 |
      | Machine2 |
      | Machine3 |
    And confirm machine selection
    Then all selected machines should be displayed

  Scenario: Verify persistence after save
    When user saves machine subscription
    And reopens machine subscription popup
    Then previously selected machines should be displayed

  # ==================================================
  # ❌ NEGATIVE SCENARIOS (ADD FLOW)
  # ==================================================

  Scenario: Submit without selecting machines
    When user opens machine subscription popup
    And click on add machine button
    And confirm without selecting machines
    Then validation message should be displayed

  Scenario: Try adding duplicate machine
    When user opens machine subscription popup
    And select already subscribed machines
    And confirm machine selection
    Then system should prevent duplicate entry

  Scenario: Network failure during machine subscription
    When user submits machine subscription without internet
    Then error message should be displayed

  Scenario: API failure during machine subscription
    When backend returns error during subscription
    Then proper error message should be shown

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS (ADD FLOW)
  # ==================================================

  Scenario: Select maximum number of machines
    When user selects maximum allowed machines
    Then system should handle without issues

  Scenario: Rapid multiple selections
    When user quickly selects and deselects machines
    Then system should maintain correct selection state

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
    When user opens machine subscription popup
    Then popup should display:
      | Machine List |
      | Add Button   |
      | Delete Icon  |
      | Submit Button |

  Scenario: Verify Select Machines bottom sheet UI
    When user clicks add machine button
    Then machine list with multi-selection should be displayed

  Scenario: Verify multi-selection behavior
    When user selects multiple machines
    Then all selections should be highlighted

  Scenario: Verify scroll functionality in bottom sheet
    Then user should be able to scroll machine list

  Scenario: Verify submit button behavior
    Then submit button should validate selection

  # ==================================================
  # 🔎 FIELD VALIDATIONS (ADD FLOW)
  # ==================================================

  Scenario: Validate machine selection is mandatory
    When no machine is selected
    Then error should be displayed

  Scenario: Validate selected machines display
    When machines are selected
    Then they should appear correctly in popup

  # ==================================================
  # 🗑️ DELETE MACHINE SUBSCRIPTION FLOW
  # ==================================================

  # ==================================================
  # ✅ POSITIVE SCENARIOS (DELETE FLOW)
  # ==================================================

  Scenario: Delete single machine subscription
    When user opens machine subscription popup
    And remove machine "Machine1"
    Then machine should be removed from list
    When I click on the submit button
    Then deletion should be saved successfully

  Scenario: Delete multiple machine subscriptions
    When user opens machine subscription popup
    And remove machines:
      | Machine1 |
      | Machine2 |
    And I click on the submit button
    Then all selected machines should be removed

  Scenario: Verify persistence after deletion
    When user deletes machines and saves
    And reopens machine subscription popup
    Then deleted machines should not be displayed

  # ==================================================
  # ❌ NEGATIVE SCENARIOS (DELETE FLOW)
  # ==================================================

  Scenario: Delete without saving
    When user removes machine
    And closes popup without submitting
    Then deletion should not be saved

  Scenario: Delete all machines and submit
    When user removes all machines
    And I click on the submit button
    Then system should handle empty state correctly

  Scenario: Network failure during deletion
    When user deletes machine without internet
    Then error message should be displayed

  Scenario: API failure during deletion
    When backend returns error during deletion
    Then proper error message should be shown

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS (DELETE FLOW)
  # ==================================================

  Scenario: Rapid delete actions
    When user quickly deletes multiple machines
    Then system should handle correctly

  Scenario: Delete last remaining machine
    When only one machine exists
    And user deletes it
    Then list should become empty

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS (DELETE FLOW)
  # ==================================================

  Scenario: Verify delete icon visibility
    When user opens machine subscription popup
    Then delete icon should be visible for each machine

  Scenario: Verify UI after deletion
    When machine is deleted
    Then UI should update immediately

  Scenario: Verify empty state UI
    When no machines exist
    Then proper empty message should be displayed

  # ==================================================
  # 🔎 FIELD VALIDATION (DELETE FLOW)
  # ==================================================

  Scenario: Validate machine removal
    When machine is deleted
    Then it should not appear in list