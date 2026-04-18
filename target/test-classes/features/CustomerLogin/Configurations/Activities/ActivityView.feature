Feature: View Activity Details from Activities List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Maintenance" section is displayed
    When User clicks on "Activities" feature
    Then verify user navigates to "Activities" list screen
    And User has already created an Activity

  # =========================================================
  # 🔍 SEARCH + VIEW FLOW
  # =========================================================

  Scenario: Search and open View Activity popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Name
    And User waits for search results to load
    Then system should display matching Activity results
    And User verifies Activity appears in list
    When User clicks on the Activity record
    Then "View Activity" popup should be displayed
    And Activity ID should be visible
    And Status should be visible and clickable
    And Activity Name should be visible
    And Description should be displayed if exists
    And "Is Function Applicable" toggle should be visible
    And Close "X" button should be visible

    # 🔒 Read-only validations
    And Activity Name field should be non-editable
    And Description field should be non-editable
    And toggle should be visible but non-editable

  # =========================================================
  # 🔁 STATUS CHANGE FROM VIEW POPUP
  # =========================================================

  Scenario: Change status from View popup
    When User opens View Activity popup
    And User clicks on Status button
    Then Status confirmation popup should be displayed
    And "Yes, Change" button should be visible
    When User clicks on "Yes, Change" button
    Then status should be updated successfully
    And updated status should be reflected in UI

  Scenario: Cancel status change from View popup
    When User opens View Activity popup
    And User clicks on Status button
    And User closes confirmation popup without action
    Then status should remain unchanged

  # =========================================================
  # 👁️ POSITIVE SCENARIOS
  # =========================================================

  Scenario: Verify Activity view data accuracy
    When User opens View Activity popup
    Then all displayed values should match backend stored data

  Scenario: Verify popup close functionality
    When User clicks on "X" button
    Then popup should be closed
    And User should return to Activities list screen

  Scenario: Verify toggle display state
    When User opens View Activity popup
    Then toggle should correctly reflect ON/OFF state

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Verify fields are not editable
    When User tries to edit Activity Name, Description or toggle
    Then fields should not allow modification

  Scenario: Verify no Save/Edit button in view popup
    Then Save button should NOT be visible
    And Edit option should NOT be available

  Scenario: View deleted Activity
    When Activity is deleted from backend
    And User searches same Activity
    Then system should display "Record not found"

  Scenario: Unauthorized access to view
    When User without permission tries to view Activity
    Then system should show "Access Denied"

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid click on record
    When User clicks Activity record multiple times quickly
    Then only one popup should open

  Scenario: Rapid status toggle clicks
    When User clicks Status button multiple times quickly
    Then only one confirmation popup should appear

  Scenario: Network failure during view
    When User opens Activity without internet connection
    Then system should display error message

  Scenario: Session timeout during view
    When session expires while opening Activity
    Then User should be redirected to login screen

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify View Activity popup UI
    When User opens View Activity popup
    Then all fields should be properly aligned
    And labels should be clearly visible
    And values should be readable
    And "X" button should be correctly positioned

  Scenario: Verify read-only UI behavior
    When User opens View Activity popup
    Then all fields except Status should be disabled
    And no input cursor should be visible


  # =========================================================
  # 🔁 SEARCH VARIATION COVERAGE
  # =========================================================

  Scenario Outline: Validate search behavior
    When User enters "<input>" in search field
    Then system should return "<result>"

    Examples:
      | input              | result             |
      | exact name         | record found       |
      | partial name       | relevant results   |
      | uppercase          | record found       |
      | invalid input      | no results         |
      | special characters | safe handling      |