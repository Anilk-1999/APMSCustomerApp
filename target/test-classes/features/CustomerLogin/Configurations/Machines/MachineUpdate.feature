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

  Scenario: Update machine with License Type - Monitor
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User selects License Type as "Monitor"
    And User updates mandatory Monitor fields
    And User clicks Save button
    Then machine should be updated successfully

  Scenario: Update machine with License Type - Maintenance
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User selects License Type as "Maintenance"
    And User updates available fields
    And User clicks Save button
    Then machine should be updated successfully

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

  Scenario: Validate Machine Name is mandatory
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User clears Machine Name field
    And User clicks Save button
    Then "Machine Name is required" should be displayed

  Scenario: Validate Machine Code uniqueness
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User enters duplicate Machine Code
    And User clicks Save button
    Then "Machine Code already exists" should be displayed

  Scenario: Validate invalid Location input
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User enters invalid Location format
    Then validation error should be displayed

  Scenario: Validate Machine Brand is mandatory
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User does not select Machine Brand
    And User clicks Save button
    Then "Machine Brand is required" should be displayed

  Scenario: Validate Machine Type is mandatory
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User does not select Machine Type
    And User clicks Save button
    Then "Machine Type is required" should be displayed

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

  Scenario: Save without any changes
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User clicks Save button without making changes
    Then system should display "No changes detected"

  Scenario: Invalid Machine Code format
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User enters special characters in Machine Code
    And User clicks Save button
    Then validation error should be displayed

  Scenario: Empty mandatory fields
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User clears all mandatory fields
    And User clicks Save button
    Then all field-level validation messages should be displayed

  Scenario: Excessively long input values
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User enters very large text in Machine Name field
    Then system should handle or restrict input safely

  Scenario: SQL injection attempt
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User enters SQL scripts in input fields
    Then system should sanitize input and block execution

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid multiple Save clicks
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User clicks Save button multiple times rapidly
    Then system should prevent duplicate submissions

  Scenario: Network failure during update
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User submits update without internet connection
    Then error message should be displayed

  Scenario: Session timeout during update
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And session expires during editing
    Then User should be redirected to login screen

  Scenario: Partial field update
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User updates only Machine Name
    And User clicks Save button
    Then only modified fields should be updated

  Scenario: Large dropdown data handling
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User opens dropdown with large dataset
    Then dropdown should load efficiently

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Machine Update screen UI
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    Then all fields should be visible
    And Save button should be enabled

  Scenario: Verify pre-filled data accuracy
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    Then all fields should match original machine data

  Scenario: Verify dropdown selection UI
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    Then selected values should be highlighted correctly

  Scenario: Verify scroll behavior
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    Then user should be able to scroll entire form

  Scenario: Verify swipe action UI
    When User searches for newly created Machine Name
    And User swipes machine record
    Then Edit option should be visible

  # =========================================================
  # 🔁 SWIPE ACTION COVERAGE (ALL LICENSE TYPES)
  # =========================================================

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

  Scenario Outline: Validate Machine Name field
    When User searches for newly created Machine Name
    And User performs swipe right to left on machine record
    And User clicks on "Edit" option
    And User clicks Save button
    Then "<error>" should be displayed

    Examples:
      | input | error                     |
      |       | Machine Name is required |

 