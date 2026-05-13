@regression @smoke @create @p1
Feature: Create Activity Group via Configuration Module

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Maintenance" section is displayed
    When User clicks on "Activity Groups" feature
    Then verify user navigates to "Activity Groups" list screen

  # =========================================================
  # NAVIGATION FLOW
  # =========================================================

  @smoke @p1
  Scenario: Navigate to Activity Groups list screen
    Then Activity Groups list should be displayed
    And Add "+" button should be visible in Activity Groups list

  # =========================================================
  # OPEN ADD ACTIVITY GROUP POPUP
  # =========================================================

  @smoke @p1
  Scenario: Open Add Activity Group popup
    When User clicks on "+ Add" button in Activity Groups list screen
    Then "Add Activity Group" popup should be displayed
    And Form Name field should be visible in popup
    And Form Description field should be visible in popup
    And Activity Checklist section with "+" button should be visible
    And Submit button should be visible in Activity Group popup
    And Close "X" button should be visible in Activity Group popup

  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Create Activity Group with all fields
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters valid Form Name for Activity Group
    And User enters Form Description for Activity Group
    And User clicks on "+" button in Activity Checklist
    Then "Select Activities" bottom sheet should be displayed
    When User selects multiple activities from bottom sheet
    And User clicks Submit button in Activities bottom sheet
    Then selected activities should be added to Activity Checklist
    And each activity in checklist should have a delete icon
    When User clicks Submit button in Activity Group popup
    Then Activity Group should be created successfully
    And newly created Activity Group should be visible in Activity Groups list screen

  @regression @p1
  Scenario: Create Activity Group with mandatory fields only
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters valid Form Name for Activity Group
    And User leaves Form Description empty
    And User clicks on "+" button in Activity Checklist
    Then "Select Activities" bottom sheet should be displayed
    When User selects one activity from bottom sheet
    And User clicks Submit button in Activities bottom sheet
    Then selected activity should be added to Activity Checklist
    When User clicks Submit button in Activity Group popup
    Then Activity Group should be created successfully

  @regression @p2
  Scenario: Create Activity Group with add, delete and re-add flow
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters valid Form Name for Activity Group
    And User clicks on "+" button in Activity Checklist
    When User selects multiple activities from bottom sheet
    And User clicks Submit button in Activities bottom sheet
    Then selected activities should be added to Activity Checklist
    When User clicks delete icon on one activity in checklist
    Then that activity should be removed from checklist
    And remaining activities should still be displayed in checklist
    When User clicks on "+" button in Activity Checklist
    And User selects additional activities from bottom sheet
    And User clicks Submit button in Activities bottom sheet
    Then new activities should be appended to checklist
    When User clicks Submit button in Activity Group popup
    Then Activity Group should be created successfully

  @regression @p2
  Scenario: Create Activity Group with trimmed Form Name
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters Form Name with leading and trailing spaces for Activity Group
    And User clicks on "+" button in Activity Checklist
    When User selects one activity from bottom sheet
    And User clicks Submit button in Activities bottom sheet
    And User clicks Submit button in Activity Group popup
    Then system should trim spaces and create Activity Group successfully

  # =========================================================
  # NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p1
  Scenario: Create Activity Group without Form Name
    When User clicks on "+ Add" button in Activity Groups list screen
    And User leaves Form Name empty
    And User clicks on "+" button in Activity Checklist
    When User selects one activity from bottom sheet
    And User clicks Submit button in Activities bottom sheet
    When User clicks Submit button in Activity Group popup
    Then "This field is required" error should be displayed for Form Name
    And User clicks Close "X" button in Activity Group popup

  @negative @regression @p1
  Scenario: Create Activity Group without selecting any activities
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters valid Form Name for Activity Group
    And User clicks Submit button in Activity Group popup
    Then "Invalid - Please add activity." toast should be displayed
    And User clicks Close "X" button in Activity Group popup

  @negative @regression @p2
  Scenario: Submit bottom sheet without selecting any activity
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters valid Form Name for Activity Group
    And User clicks on "+" button in Activity Checklist
    Then "Select Activities" bottom sheet should be displayed
    When User clicks Submit button in Activities bottom sheet without selecting
    Then bottom sheet should close with no activities added
    When User clicks Submit button in Activity Group popup
    Then "Invalid - Please add activity." toast should be displayed
    And User clicks Close "X" button in Activity Group popup

  @negative @regression @p2
  Scenario: Create Activity Group with only spaces in Form Name
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters only spaces in Form Name for Activity Group
    And User clicks on "+" button in Activity Checklist
    When User selects one activity from bottom sheet
    And User clicks Submit button in Activities bottom sheet
    When User clicks Submit button in Activity Group popup
    Then "This field is required" error should be displayed for Form Name
    And User clicks Close "X" button in Activity Group popup

  @negative @regression @p2
  Scenario: Delete all activities from checklist then submit
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters valid Form Name for Activity Group
    And User clicks on "+" button in Activity Checklist
    When User selects multiple activities from bottom sheet
    And User clicks Submit button in Activities bottom sheet
    Then selected activities should be added to Activity Checklist
    When User deletes all activities from checklist
    Then checklist should be empty
    When User clicks Submit button in Activity Group popup
    Then "Invalid - Please add activity." toast should be displayed
    And User clicks Close "X" button in Activity Group popup

  @negative @regression @p2
  Scenario: Duplicate Form Name
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters existing Form Name for Activity Group
    And User clicks on "+" button in Activity Checklist
    When User selects one activity from bottom sheet
    And User clicks Submit button in Activities bottom sheet
    When User clicks Submit button in Activity Group popup
    Then duplicate validation error should be displayed for Activity Group

  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Rapid multiple Submit clicks
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters valid Form Name for Activity Group
    And User clicks on "+" button in Activity Checklist
    When User selects one activity from bottom sheet
    And User clicks Submit button in Activities bottom sheet
    When User clicks Submit button in Activity Group popup multiple times quickly
    Then system should prevent duplicate Activity Group creation

  @sanity @p3
  Scenario: Rapid "+" button clicks in Activity Checklist
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters valid Form Name for Activity Group
    When User clicks "+" button in Activity Checklist multiple times quickly
    Then only one "Select Activities" bottom sheet should be displayed
    And User clicks Close "X" button in Activity Group popup

  @negative @p3
  Scenario: Network failure during Activity Group creation
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters valid Form Name for Activity Group
    And User clicks on "+" button in Activity Checklist
    When User selects one activity from bottom sheet
    And User clicks Submit button in Activities bottom sheet
    When User clicks Submit button without internet connection in Activity Group popup
    Then system should display network error message

  @negative @p3
  Scenario: Session timeout during Activity Group creation
    When session expires during Activity Group creation
    Then User should be redirected to login screen

  @regression @p2
  Scenario: Close popup without saving
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters valid Form Name for Activity Group
    And User clicks on "+" button in Activity Checklist
    When User selects one activity from bottom sheet
    And User clicks Submit button in Activities bottom sheet
    When User clicks Close "X" button in Activity Group popup
    Then popup should be closed without saving Activity Group data
    And User should return to Activity Groups list screen

  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Add Activity Group popup UI elements
    When User clicks on "+ Add" button in Activity Groups list screen
    Then "Add Activity Group" popup should be displayed
    And Form Name field should be visible in popup
    And Form Description field should be visible in popup
    And Activity Checklist section with "+" button should be visible
    And Submit button should be visible in Activity Group popup
    And Close "X" button should be visible in Activity Group popup
    And User clicks Close "X" button in Activity Group popup

  @regression @p2
  Scenario: Verify Activity Checklist item UI
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters valid Form Name for Activity Group
    And User clicks on "+" button in Activity Checklist
    When User selects multiple activities from bottom sheet
    And User clicks Submit button in Activities bottom sheet
    Then each activity in checklist should have a delete icon
    And User clicks Close "X" button in Activity Group popup

  @regression @p2
  Scenario: Verify Activity Groups list screen UI
    Then Activity Groups list should be displayed
    And each Activity Group record should show Activity Group ID
    And each Activity Group record should show Form Name
    And each Activity Group record should show Description
    And each Activity Group record should show Activity Count badge
    And each Activity Group record should show Status

  @regression @p2
  Scenario: Verify Close X button closes popup
    When User clicks on "+ Add" button in Activity Groups list screen
    And User enters valid Form Name for Activity Group
    When User clicks Close "X" button in Activity Group popup
    Then popup should be closed
    And User should return to Activity Groups list screen
