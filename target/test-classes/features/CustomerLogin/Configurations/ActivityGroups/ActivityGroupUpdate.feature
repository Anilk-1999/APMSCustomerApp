Feature: Update Activity Group via Swipe Action with Checklist and Status Management

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Maintenance" section is displayed
    When User clicks on "Activity Groups" feature
    Then verify user navigates to "Activity Groups" list screen
    And User has already created an Activity Group

  # =========================================================
  # 🔍 SEARCH + SWIPE + EDIT FLOW
  # =========================================================

  Scenario: Search and open Edit Activity Group popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list

    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed

    # Field validations
    And Activity Group ID should be visible and non-editable
    And Status should be visible and clickable
    And Form Name field should be pre-filled and editable
    And Form Description field should be pre-filled and editable
    And Activity Checklist should display previously selected activities
    And Save button should be visible
    And Close "X" button should be visible

  # =========================================================
  # 🧪 POSITIVE SCENARIOS (UPDATE)
  # =========================================================

  Scenario: Update Activity Group with all fields
    When User opens Edit Activity Group popup
    And User updates Form Name with valid value
    And User updates Form Description
    And User adds new activities to checklist
    And User deletes existing activity from checklist
    And User clicks Save button
    Then Activity Group should be updated successfully
    And updated Activity Group should be reflected in list

  Scenario: Update only Form Name
    When User opens Edit Activity Group popup
    And User updates Form Name
    And User clicks Save button
    Then Activity Group should be updated successfully

  Scenario: Update only Description
    When User opens Edit Activity Group popup
    And User updates Form Description
    And User clicks Save button
    Then Activity Group should be updated successfully

  Scenario: Update only checklist
    When User opens Edit Activity Group popup
    And User adds activities
    And User deletes activities
    And User clicks Save button
    Then Activity Group should be updated successfully

  Scenario: Update Form Name with trimmed value
    When User opens Edit Activity Group popup
    And User enters Form Name with leading and trailing spaces
    And User clicks Save button
    Then system should trim spaces and update successfully

  # =========================================================
  # 🔁 CHECKLIST ADD FLOW
  # =========================================================

  Scenario: Add activities in edit popup
    When User opens Edit Activity Group popup
    And User clicks "+" button in Activity Checklist
    Then "Select Activities" bottom sheet should be displayed
    When User selects multiple activities
    And User clicks Submit button
    Then selected activities should be added to checklist

  Scenario: Add activities without selection
    When User opens Edit Activity Group popup
    And User opens Activity Checklist bottom sheet
    And User clicks Submit without selecting
    Then checklist should remain unchanged

  # =========================================================
  # 🗑️ CHECKLIST DELETE FLOW
  # =========================================================

  Scenario: Delete single activity
    When User opens Edit Activity Group popup
    And User clicks delete icon on one activity
    Then that activity should be removed
    And remaining activities should be visible

  Scenario: Delete multiple activities
    When User opens Edit Activity Group popup
    And User deletes multiple activities
    Then all selected activities should be removed correctly

  Scenario: Delete all activities
    When User opens Edit Activity Group popup
    And User deletes all activities
    Then checklist should become empty

  Scenario: Delete and re-add activities
    When User opens Edit Activity Group popup
    And User deletes activity
    And User adds new activity
    Then checklist should update correctly

  # =========================================================
  # 🔁 STATUS FLOW
  # =========================================================

  Scenario: Change status from Active to Inactive
    When User opens Edit Activity Group popup
    And current status is Active
    And User clicks on Status button
    Then Status confirmation popup should be displayed
    And "Yes, Change" button should be visible
    When User clicks "Yes, Change"
    Then status should be changed to Inactive

  Scenario: Change status from Inactive to Active
    When User opens Edit Activity Group popup
    And current status is Inactive
    And User clicks Status button
    And User confirms change
    Then status should be changed to Active

  Scenario: Cancel status change
    When User opens Edit Activity Group popup
    And User clicks Status button
    And User closes popup without confirmation
    Then status should remain unchanged

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Update without Form Name
    When User opens Edit Activity Group popup
    And User clears Form Name
    And User clicks Save button
    Then "Form Name is required" error should be displayed

  Scenario: Duplicate Form Name
    When User opens Edit Activity Group popup
    And User enters existing Form Name
    And User clicks Save button
    Then duplicate validation error should be displayed

  Scenario: Invalid Form Name input
    When User opens Edit Activity Group popup
    And User enters invalid characters or spaces
    And User clicks Save button
    Then validation error should be displayed

  Scenario: Save without changes
    When User opens Edit Activity Group popup
    And User clicks Save without making changes
    Then system should show "No changes detected"

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid Save clicks
    When User opens Edit Activity Group popup
    And User clicks Save button multiple times
    Then system should prevent duplicate update

  Scenario: Rapid delete clicks
    When User clicks delete multiple times quickly
    Then activity should be removed only once

  Scenario: Rapid "+" clicks
    When User clicks "+" multiple times
    Then only one bottom sheet should open

  Scenario: Large checklist handling
    When checklist contains many activities
    Then system should support scrolling and proper UI rendering

  Scenario: Network failure during update
    When User clicks Save without internet
    Then error message should be displayed

  Scenario: Session timeout during update
    When session expires during editing
    Then User should be redirected to login screen

  Scenario: Close popup without saving
    When User modifies data and clicks "X"
    Then changes should not be saved

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Edit popup UI
    When User opens Edit Activity Group popup
    Then all fields should be aligned properly
    And Form Name and Description should be editable
    And Activity Checklist should display correctly
    And delete icons should be visible for each activity
    And Save button should be visible

  Scenario: Verify bottom sheet UI
    When User clicks "+"
    Then activity list should be displayed
    And multi-selection should be enabled
    And Submit button should be visible

  Scenario: Verify Close (X) button
    When User clicks "X"
    Then popup should be closed
