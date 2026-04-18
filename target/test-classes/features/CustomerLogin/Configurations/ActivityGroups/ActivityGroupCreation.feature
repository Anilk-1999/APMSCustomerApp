Feature: Create Activity Group (Form) with Activity Checklist Management

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Maintenance" section is displayed
    When User clicks on "Activity Groups" feature
    Then verify user navigates to "Activity Groups" list screen

  # =========================================================
  # 🔁 NAVIGATION FLOW
  # =========================================================

  Scenario: Navigate to Activity Groups list screen
    Then Activity Groups list should be displayed
    And Add "+" button should be visible

  # =========================================================
  # ➕ OPEN ADD ACTIVITY GROUP POPUP
  # =========================================================

  Scenario: Open Add New Activity Group popup
    When User clicks on "+ Add" button in Activity Groups list screen
    Then "Add New Activity Group" popup should be displayed
    And Form Name field should be visible
    And Form Description field should be visible
    And Activity Checklist label with "+" button should be visible
    And Submit button should be visible
    And Close "X" button should be visible

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Create Activity Group with add, delete and re-add flow
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters valid Form Name
    And User enters Form Description

    # ➕ Add Activities
    And User clicks on "+" button in Activity Checklist
    Then "Select Activities" bottom sheet should be displayed
    When User selects multiple activities
    And User clicks Submit button in bottom sheet
    Then selected activities should be added to checklist

    # 🗑️ Delete Activity
    When User clicks delete icon on one activity
    Then that activity should be removed from checklist
    And remaining activities should be displayed

    # ➕ Re-add Activity
    When User clicks "+" button again
    And User selects additional activities
    And User clicks Submit button
    Then new activities should be appended to checklist

    # ✔ Final Submit
    When User clicks Submit button in popup
    Then Activity Group should be created successfully
    And Activity Group should be visible in list

  Scenario: Create Activity Group without selecting activities
    When User clicks on "+ Add" button
    And User enters valid Form Name
    And User clicks on "+" button in Activity Checklist
    And User clicks Submit button without selection
    Then checklist should remain empty
    When User clicks Submit button in popup
    Then Activity Group should be created successfully

  Scenario: Create Activity Group with mandatory fields only
    When User clicks on "+ Add" button
    And User enters valid Form Name
    And User clicks Submit button
    Then Activity Group should be created successfully

  Scenario: Create Activity Group with trimmed Form Name
    When User clicks on "+ Add" button
    And User enters Form Name with leading and trailing spaces
    And User clicks Submit button
    Then system should trim spaces and create Activity Group successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Create Activity Group without Form Name
    When User clicks on "+ Add" button
    And User leaves Form Name empty
    And User clicks Submit button
    Then "Form Name is required" should be displayed

  Scenario: Invalid Form Name input
    When User clicks on "+ Add" button
    And User enters only spaces or special characters
    And User clicks Submit button
    Then validation error should be displayed

  Scenario: Duplicate Form Name
    When User clicks on "+ Add" button
    And User enters existing Form Name
    And User clicks Submit button
    Then duplicate validation error should be displayed

  Scenario: Skip Activity Checklist interaction
    When User clicks on "+ Add" button
    And User enters Form Name
    And User does not open Activity Checklist
    And User clicks Submit button
    Then system should allow creation or enforce validation based on business rule

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Delete all activities before submit
    When User adds multiple activities
    And User deletes all activities
    Then checklist should be empty
    When User clicks Submit button
    Then Activity Group should be created successfully

  Scenario: Rapid "+" clicks
    When User clicks "+" multiple times quickly
    Then only one bottom sheet should be displayed

  Scenario: Rapid delete clicks
    When User clicks delete icon multiple times
    Then activity should be removed only once

  Scenario: Rapid submit clicks
    When User clicks Submit button multiple times
    Then system should prevent duplicate creation

  Scenario: Network failure during creation
    When User clicks Submit button without internet connection
    Then system should display error message

  Scenario: Session timeout during creation
    When session expires during creation
    Then User should be redirected to login screen

  Scenario: Close popup without saving
    When User enters data and closes popup
    Then data should not be saved

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Add Activity Group popup UI
    When User opens Add Activity Group popup
    Then all fields should be properly aligned
    And labels should be clearly visible
    And "+" button should be visible
    And Submit button should be enabled based on validation

  Scenario: Verify checklist UI behavior
    When activities are selected
    Then they should be displayed in checklist
    And each item should have delete icon

  Scenario: Verify empty checklist state
    When no activities are selected
    Then checklist should display empty state

  Scenario: Verify Close (X) button
    When User clicks "X"
    Then popup should be closed
    And User should return to list screen

  