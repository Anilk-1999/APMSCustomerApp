@regression @smoke @update @p1
Feature: Machine Update via Swipe Action for Newly Created Machine

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Machine" section is displayed
    When User clicks on "Machines" feature
    Then verify user navigates to "Machines" list screen
    And User has created a machine with all mandatory fields

  # =========================================================
  # 🔁 NAVIGATION & SWIPE FLOW
  # =========================================================

  @smoke @p1
  Scenario: Navigate to Machine Update screen via swipe action
    When User searches for newly created Machine Name
    And User verifies machine appears in list
    And User performs swipe right to left on machine record
    Then action menu should be displayed
    When User clicks on "Edit" option
    Then User should be navigated to Machine Update screen
    And all fields should be pre-filled with existing machine data

  # =========================================================
  # 🧪 POSITIVE SCENARIOS - LICENSE TYPES
  # =========================================================

  @smoke @regression @p1
  Scenario: Update machine with License Type - Production
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User selects License Type as "Production"
    And User updates Machine Name
    And User updates Location
    And User updates Machine Code
    And User selects Machine Brand
    And User selects Machine Type
    And User selects IoT Device Type
    And User clicks Save button
    Then machine should be updated successfully
    And updated data should be reflected in machine list

  @regression @p1
  Scenario: Update machine with License Type - Monitor
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User selects License Type as "Monitor"
    And User updates mandatory Monitor fields
    And User clicks Save button
    Then machine should be updated successfully

  @regression @p1
  Scenario: Update machine with License Type - Maintenance
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User selects License Type as "Maintenance"
    And User updates available fields
    And User clicks Save button
    Then machine should be updated successfully

  @regression @p2
  Scenario: Update machine without changing License Type
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User keeps existing License Type unchanged
    And User updates Machine Name and Machine Code
    And User clicks Save button
    Then machine should be updated successfully

  # =========================================================
  # 🧪 FIELD VALIDATION SCENARIOS
  # =========================================================

  @negative @regression @p1
  Scenario: Validate Machine Name is mandatory
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User clears Machine Name field
    And User clicks Save button
    Then "Machine Name is required" should be displayed

  @negative @regression @p2
  Scenario: Validate Machine Code uniqueness
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User enters duplicate Machine Code
    And User clicks Save button
    Then "Machine Code already exists" should be displayed

  @negative @regression @p2
  Scenario: Validate invalid Location input
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User enters invalid Location format
    Then validation error should be displayed

  @negative @regression @p1
  Scenario: Validate Machine Brand is mandatory
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User does not select Machine Brand
    And User clicks Save button
    Then "Machine Brand is required" should be displayed

  @negative @regression @p1
  Scenario: Validate Machine Type is mandatory
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User does not select Machine Type
    And User clicks Save button
    Then "Machine Type is required" should be displayed

  @negative @regression @p1
  Scenario: Validate IoT Device Type is mandatory
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User does not select IoT Device Type
    And User clicks Save button
    Then "IoT Device Type is required" should be displayed

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p2
  Scenario: Save without any changes
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User clicks Save button without making changes
    Then system should display "No changes detected"

  @negative @regression @p2
  Scenario: Invalid Machine Code format
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User enters special characters in Machine Code
    And User clicks Save button
    Then validation error should be displayed

  @negative @regression @p2
  Scenario: Empty mandatory fields
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User clears all mandatory fields
    And User clicks Save button
    Then all field-level validation messages should be displayed

  @negative @regression @p3
  Scenario: Excessively long input values
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User enters very large text in Machine Name field
    Then system should handle or restrict input safely

  @negative @regression @p3
  Scenario: SQL injection attempt
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User enters SQL scripts in input fields
    Then system should sanitize input and block execution

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p3
  Scenario: Rapid multiple Save clicks
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User clicks Save button multiple times rapidly
    Then system should prevent duplicate submissions

  @negative @p3
  Scenario: Network failure during update
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User submits update without internet connection
    Then error message should be displayed

  @negative @p3
  Scenario: Session timeout during update
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And session expires during editing
    Then User should be redirected to login screen

  @regression @p2
  Scenario: Partial field update
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User updates only Machine Name
    And User clicks Save button
    Then only modified fields should be updated

  @regression @p3
  Scenario: Large dropdown data handling
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User opens dropdown with large dataset
    Then dropdown should load efficiently

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Machine Update screen UI
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    Then all fields should be visible
    And Save button should be enabled

  @regression @p2
  Scenario: Verify pre-filled data accuracy
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    Then all fields should match original machine data

  @regression @p2
  Scenario: Verify dropdown selection UI
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    Then selected values should be highlighted correctly

  @regression @p3
  Scenario: Verify scroll behavior
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    Then user should be able to scroll entire form

  @regression @p2
  Scenario: Verify swipe action UI
    When User searches for newly created Machine Name
    And User swipes machine record
    Then Edit option should be visible

  # =========================================================
  # 🔁 SWIPE ACTION COVERAGE (ALL LICENSE TYPES)
  # =========================================================

  @regression @p2
  Scenario Outline: Swipe and update machine for each License Type
    Given Machine exists with License Type "<licenseType>"
    When User searches machine
    And User swipes right to left on machine
    And User clicks Edit option
    And User updates Machine Name
    And User clicks Save button
    Then machine should be updated successfully

    Examples:
      | licenseType |
      | Production  |
      | Monitor     |
      | Maintenance |

  # =========================================================
  # 🔎 FIELD VALIDATION OUTLINE
  # =========================================================

  @regression @p3
  Scenario Outline: Validate Machine Name field
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User clicks Save button
    Then "<error>" should be displayed

    Examples:
      | input | error                    |
      |       | Machine Name is required |
