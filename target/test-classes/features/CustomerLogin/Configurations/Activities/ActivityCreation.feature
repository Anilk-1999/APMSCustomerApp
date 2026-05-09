@regression @smoke @create @p1
Feature: Create Activity via Configuration Module

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Maintenance" section is displayed
    When User clicks on "Activities" feature
    Then verify user navigates to "Activities" list screen

  # =========================================================
  # 🔁 NAVIGATION FLOW
  # =========================================================

  @smoke @p1
  Scenario: Navigate to Activities list screen
    Then Activities list should be displayed
    And Add "+" button should be visible

  # =========================================================
  # ➕ OPEN ADD ACTIVITY POPUP
  # =========================================================

  @smoke @p1
  Scenario: Open Add New Activity popup
    When User clicks on "+ Add" button in Activities list screen
    Then "Add New Activity" popup should be displayed
    And Activity Name field should be visible
    And Description field should be visible
    And "Is Function Applicable" label with toggle button should be visible
    And Submit button should be visible
    And Close "X" button should be visible in popup header

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Create Activity with all fields
    When User clicks on "+ Add" button in Activities list screen
    And User enters valid Activity Name
    And User enters Description
    And User enables "Is Function Applicable" toggle
    And User clicks Submit button
    Then Activity should be created successfully
    And newly created Activity should be visible in Activities list screen

  @regression @p1
  Scenario: Create Activity with mandatory fields only
    When User clicks on "+ Add" button in Activities list screen
    And User enters valid Activity Name
    And User leaves Description empty
    And User keeps "Is Function Applicable" toggle as default
    And User clicks Submit button
    Then Activity should be created successfully

  @regression @p2
  Scenario: Create Activity with toggle OFF
    When User clicks on "+ Add" button in Activities list screen
    And User enters valid Activity Name
    And User disables "Is Function Applicable" toggle
    And User clicks Submit button
    Then Activity should be created successfully

  @regression @p2
  Scenario: Create Activity with trimmed Activity Name
    When User clicks on "+ Add" button in Activities list screen
    And User enters Activity Name with leading and trailing spaces
    And User clicks Submit button
    Then system should trim spaces and create Activity successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p1
  Scenario: Create Activity without Activity Name
    When User clicks on "+ Add" button in Activities list screen
    And User leaves Activity Name empty
    And User clicks Submit button
    Then "This field is required" should be displayed
    And User clicks Close "X" button

  @negative @regression @p1
  Scenario: Create Activity with only spaces
    When User clicks on "+ Add" button in Activities list screen
    And User enters only spaces in Activity Name
    And User clicks Submit button
    Then "This field is required" should be displayed
    And User clicks Close "X" button

  @regression @p2
  Scenario: Invalid Activity Name input
    When User clicks on "+ Add" button in Activities list screen
    And User enters only special characters in Activity Name
    And User clicks Submit button
    Then Activity should be created successfully


  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Rapid multiple Submit clicks
    When User creates Activity with valid data
    And User clicks Submit button multiple times quickly
    Then system should prevent duplicate Activity creation

  @sanity @p3
  Scenario: Rapid toggle switching
    When User clicks toggle multiple times quickly
    Then system should maintain correct final toggle state

  @sanity @p3
  Scenario: Large input handling
    When User enters very long Activity Name or Description
    Then system should restrict or validate input length

  @negative @p3
  Scenario: Network failure during creation
    When User clicks Submit button without internet connection
    Then system should display error message

  @negative @p3
  Scenario: Session timeout during creation
    When session expires while creating Activity
    Then User should be redirected to login screen

  @regression @p2
  Scenario: Close popup without saving
    When User opens Add Activity popup
    And User enters Activity details
    And User clicks Close "X" button
    Then popup should be closed without saving data

  @sanity @p3
  Scenario: Reopen popup after close
    When User closes Add Activity popup
    And User reopens Add Activity popup
    Then all fields should be reset

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Add Activity popup UI
    When User opens Add Activity popup
    Then all fields should be properly aligned
    And labels should be clearly visible
    And toggle button should be visible and functional
    And Submit button should be enabled only when Activity Name is entered

  @regression @p2
  Scenario: Verify toggle behavior
    When User clicks on "Is Function Applicable" toggle
    Then toggle state should switch between ON and OFF

  @regression @p2
  Scenario: Verify Close button functionality
    When User opens Add Activity popup
    And User clicks Close "X" button
    Then popup should be closed without saving data
    And User should return to Activities list screen

  @negative @p2
  Scenario: Verify validation message UI
    When User triggers validation errors
    Then validation messages should be displayed below Activity Name field

  # =========================================================
  # 🔍 FIELD VALIDATION SCENARIOS
  # =========================================================

  @negative @regression @p1
  Scenario Outline: Validate Activity Name field
    When User clicks on "+ Add" button in Activities list screen
    And User clicks Submit button
    Then "<error>" should be displayed
    And User clicks Close "X" button

    Examples:
      | input | error                     |
      |       | This field is required |


  @regression @p3
  Scenario: Validate Description field
    When User enters long Description
    Then system should allow optional input without error
