Feature: Delete Operator
  As an admin
  I want to delete an operator
  So that inactive operators are removed from system

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

  Scenario: Locate operator via search
    When user searches operator "anil@example.com"
    Then operator record should be displayed in list

  Scenario: Reveal delete option via swipe
    When user swipes operator record from right to left
    Then Delete option with icon should be visible

  # ==================================================
  # ✅ POSITIVE SCENARIOS
  # ==================================================

  Scenario: Delete operator successfully
    When user searches operator "anil@example.com"
    And user swipes record from right to left
    And user clicks on Delete option
    And user confirms deletion
    Then operator should be deleted successfully

  Scenario: Verify operator is removed from list
    When user deletes operator "anil@example.com"
    And user searches operator "anil@example.com"
    Then no results should be displayed

  Scenario: Verify list updates after deletion
    When operator is deleted
    Then operator should not appear in list without refresh

  Scenario: Verify deletion persistence
    When user deletes operator
    And relaunches or refreshes list
    Then deleted operator should not be present

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  Scenario: Cancel delete action
    When user clicks Delete option
    And user cancels confirmation
    Then operator should not be deleted

  Scenario: Delete without confirmation (if popup required)
    When user clicks Delete option
    Then confirmation popup should be displayed

  Scenario: Network failure during delete
    When user deletes operator without internet
    Then error message should be displayed
    And operator should not be deleted

  Scenario: API failure during delete
    When backend returns failure response during deletion
    Then proper error message should be shown
    And operator should not be deleted

  Scenario: Try deleting already deleted operator
    When operator is already deleted
    And user performs delete action again
    Then appropriate error should be handled

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  Scenario: Rapid multiple delete actions
    When user repeatedly clicks delete quickly
    Then system should handle gracefully

  Scenario: Delete operator from large list
    When list contains many operators
    Then delete action should still work correctly

  Scenario: Swipe sensitivity check
    When user performs partial swipe
    Then delete option should not trigger incorrectly

  Scenario: Delete last operator in list
    When only one operator exists
    And user deletes it
    Then empty state message should be displayed

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  Scenario: Verify delete icon UI
    Then delete icon should be clearly visible after swipe

  Scenario: Verify confirmation popup UI
    Then confirmation dialog should contain:
      | Delete Message |
      | Confirm Button |
      | Cancel Button  |

  Scenario: Verify UI update after deletion
    When operator is deleted
    Then list UI should update immediately

  Scenario: Verify empty state UI
    When no operators exist
    Then empty message should be displayed

  Scenario: Verify swipe animation
    When user swipes record
    Then smooth animation should be observed

  # ==================================================
  # 🔎 VALIDATION SCENARIOS
  # ==================================================

  Scenario: Verify operator removal
    When operator is deleted
    Then operator should not exist in list

  Scenario: Verify search result after deletion
    When user searches operator "anil@example.com"
    Then no matching records should be found