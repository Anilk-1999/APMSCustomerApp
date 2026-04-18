Feature: View Holiday Details from Holidays List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Unit" section is displayed
    When User clicks on "Holidays" feature
    Then verify user navigates to "Holidays" list screen
    And User has already created a Holiday

  # =========================================================
  # 🔍 SEARCH + VIEW FLOW
  # =========================================================

  Scenario: Search and open View Holiday popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Holiday Name
    And User waits for search results to load
    Then system should display matching Holiday results
    And User verifies Holiday appears in list
    When User clicks on the Holiday record
    Then "View Holiday" popup should be displayed
    And Holiday ID should be visible
    And Holiday Name should be visible
    And Holiday Date should be visible
    And "Is National Holiday" label with toggle should be visible
    And Close "X" button should be visible

  # =========================================================
  # 🔒 READ-ONLY VALIDATION
  # =========================================================

  Scenario: Verify fields are non-editable in view popup
    When User opens View Holiday popup
    Then Holiday Name field should be non-editable
    And Holiday Date field should be non-editable
    And "Is National Holiday" toggle should be visible but non-editable

  Scenario: Verify no Save/Edit buttons
    When User opens View Holiday popup
    Then Save button should NOT be visible
    And Edit option should NOT be available

  # =========================================================
  # 👁️ POSITIVE SCENARIOS
  # =========================================================

  Scenario: Verify Holiday data accuracy
    When User opens View Holiday popup
    Then Holiday Name should match created data
    And Holiday Date should match saved data
    And "Is National Holiday" toggle should reflect saved state

  Scenario: Verify popup close functionality
    When User clicks on "X" button
    Then popup should be closed
    And User should return to Holidays list screen

  Scenario: Verify Holiday Date display format
    When User opens View Holiday popup
    Then Holiday Date should be displayed in correct format

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Attempt to edit fields
    When User opens View Holiday popup
    And User tries to edit Holiday Name
    Then Holiday Name field should not allow modification
    When User tries to edit Holiday Date
    Then Holiday Date field should not allow modification
    When User tries to change "Is National Holiday" toggle
    Then toggle state should remain unchanged

  Scenario: View deleted Holiday
    When Holiday is removed from backend
    And User searches same Holiday
    Then system should display "Record not found"

  Scenario: Unauthorized access
    When User without permission tries to view Holiday
    Then system should show "Access Denied"

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid click on record
    When User clicks Holiday record multiple times quickly
    Then only one View popup should open

  Scenario: Network failure during view
    When User clicks Holiday record without internet
    Then system should display error message

  Scenario: Session timeout during view
    When session expires while opening Holiday popup
    Then User should be redirected to login screen

  Scenario: Reopen popup multiple times
    When User opens and closes View Holiday popup multiple times
    Then Holiday details should load correctly each time

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify View Holiday popup UI
    When User opens View Holiday popup
    Then all fields should be aligned properly
    And labels should be clearly visible
    And values should be readable
    And "X" button should be correctly positioned

  Scenario: Verify read-only UI behavior
    When User opens View Holiday popup
    Then all fields should appear disabled
    And no input cursor should be visible

  Scenario: Verify National Holiday toggle display
    When User opens View Holiday popup
    Then toggle should correctly display ON or OFF state


  # =========================================================
  # 🔁 SEARCH VARIATION COVERAGE
  # =========================================================

  Scenario Outline: Validate Holiday search behavior
    When User enters "<input>" in search field
    Then system should return "<result>"

    Examples:
      | input            | result           |
      | exact name       | record found     |
      | partial name     | relevant results |
      | uppercase        | record found     |
      | invalid input    | no results       |
      | special chars    | safe handling    |