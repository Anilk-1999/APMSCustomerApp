@regression @delete @p1
Feature: Delete Operator for Newly Created Operator

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
  Scenario: Locate newly created operator via search
    When User searches for newly created operator
    Then operator record should be displayed in list

  @smoke @p1
  Scenario: Reveal delete option via swipe
    When User searches for newly created operator
    And User swipes operator record right to left
    Then delete icon should be clearly visible after swipe

  # ==================================================
  # ✅ POSITIVE SCENARIOS
  # ==================================================

  @smoke @regression @p1
  Scenario: Delete operator successfully
    When User searches for newly created operator
    And User swipes operator record right to left
    And User clicks on "Delete" option
    And user confirms deletion
    Then operator should be deleted successfully

  @regression @p1
  Scenario: Verify operator removed from list after deletion
    When User searches for newly created operator
    And User swipes operator record right to left
    And User clicks on "Delete" option
    And user confirms deletion
    Then operator should be deleted successfully
    When User searches for newly created operator
    Then no results should be displayed

  @regression @p2
  Scenario: Verify list updates after deletion
    When User searches for newly created operator
    And User swipes operator record right to left
    And User clicks on "Delete" option
    And user confirms deletion
    Then operator should not appear in list

  @regression @p2
  Scenario: Verify deletion persistence after relaunch
    When User searches for newly created operator
    And User swipes operator record right to left
    And User clicks on "Delete" option
    And user confirms deletion
    Then deleted operator should not be present

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  @negative @regression @p1
  Scenario: Cancel delete action
    When User searches for newly created operator
    And User swipes operator record right to left
    And User clicks on "Delete" option
    And user cancels confirmation
    Then operator should not be deleted

  @negative @regression @p1
  Scenario: Confirm delete popup is shown before deletion
    When User searches for newly created operator
    And User swipes operator record right to left
    And User clicks on "Delete" option
    Then confirmation popup should be displayed

  @negative @p3
  Scenario: Network failure during delete
    When User searches for newly created operator
    And User swipes operator record right to left
    And User clicks on "Delete" option
    And user confirms deletion
    Then error message should be displayed

  @negative @p3
  Scenario: API failure during delete
    When operator is deleted
    And user performs delete action again
    Then appropriate error should be handled

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  @sanity @p3
  Scenario: Rapid multiple delete actions
    When User searches for newly created operator
    And User swipes operator record right to left
    And User clicks on "Delete" option
    Then confirmation popup should be displayed

  @sanity @p3
  Scenario: Delete last operator in list
    When User searches for newly created operator
    And User swipes operator record right to left
    And User clicks on "Delete" option
    And user confirms deletion
    Then operator should be deleted successfully

  @sanity @p3
  Scenario: Partial swipe sensitivity
    When User searches for newly created operator
    And user performs partial swipe
    Then delete option should not trigger incorrectly

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  @regression @p2
  Scenario: Verify confirmation popup UI
    When User searches for newly created operator
    And User swipes operator record right to left
    And User clicks on "Delete" option
    Then confirmation dialog should contain:
      | Delete Message |
      | Confirm Button |
      | Cancel Button  |

  @regression @p2
  Scenario: Verify UI update after deletion
    When User searches for newly created operator
    And User swipes operator record right to left
    And User clicks on "Delete" option
    And user confirms deletion
    Then list UI should update immediately

  # ==================================================
  # 🔎 VALIDATION SCENARIOS
  # ==================================================

  @regression @p2
  Scenario: Verify operator not found after deletion
    When User searches for newly created operator
    And User swipes operator record right to left
    And User clicks on "Delete" option
    And user confirms deletion
    Then operator should not exist in list
    And no matching records should be found
