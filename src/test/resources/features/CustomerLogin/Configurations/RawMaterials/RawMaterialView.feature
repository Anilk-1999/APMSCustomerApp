Feature: View Raw Material Details from Raw Materials List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Raw Materials" feature
    Then verify user navigates to "Raw Materials" list screen
    And User has already created a Raw Material

  # =========================================================
  # 🔍 SEARCH + VIEW FLOW
  # =========================================================

  Scenario: Search and open View Raw Material popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Raw Material Name
    And User waits for search results to load
    Then system should display matching Raw Material results
    And User verifies Raw Material appears in list
    When User clicks on the Raw Material record
    Then "View Raw Material" popup should be displayed
    And Raw Material ID should be visible
    And Status should be visible
    And Name should be visible
    And UOM should be visible
    And Description should be displayed if exists
    And Close "X" button should be visible
    And Name field should be displayed in read-only mode
    And UOM field should be displayed in read-only mode
    And Description field should be displayed in read-only mode
    And Status field should be clickable
    And Save button should not be visible
    And Edit option should not be visible

  # =========================================================
  # 🔁 STATUS CHANGE FROM VIEW POPUP
  # =========================================================

  Scenario: Change status from View popup
    When User opens View Raw Material popup
    And User clicks on Status button
    Then Status confirmation popup should be displayed
    And "Yes, Change" button should be visible
    When User clicks on "Yes, Change" button
    Then status should be toggled successfully
    And updated status should be reflected in View popup

  Scenario: Cancel status change from View popup
    When User opens View Raw Material popup
    And User clicks on Status button
    And User closes confirmation popup without confirmation
    Then status should remain unchanged

  # =========================================================
  # 👁️ POSITIVE SCENARIOS
  # =========================================================

  Scenario: Verify Raw Material view data accuracy
    When User opens View Raw Material popup
    Then all displayed values should match backend stored data

  Scenario: Verify popup close functionality
    When User opens View Raw Material popup
    And User clicks on "X" button
    Then popup should be closed
    And User should return to Raw Materials list screen

  Scenario: Verify status display format
    When User opens View Raw Material popup
    Then status should be clearly displayed as Active or Inactive

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Verify fields are not editable
    When User opens View Raw Material popup
    And User tries to edit Name field
    Then Name field should not be editable
    When User tries to edit UOM field
    Then UOM field should not be editable
    When User tries to edit Description field
    Then Description field should not be editable

  Scenario: Verify no action buttons in view popup
    When User opens View Raw Material popup
    Then Save button should not be visible
    And Edit option should not be available

  Scenario: View deleted Raw Material
    When Raw Material is deleted from backend
    And User searches same Raw Material
    Then system should display "Record not found"

  Scenario: Unauthorized access to Raw Material view
    When User without permission tries to view Raw Material
    Then system should show "Access Denied"

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid click on Raw Material record
    When User clicks multiple times quickly on Raw Material record
    Then only one View popup should open

  Scenario: Rapid status toggle clicks
    When User opens View Raw Material popup
    And User clicks Status button multiple times quickly
    Then only one confirmation popup should be displayed

  Scenario: Network failure during view
    When User clicks Raw Material record without internet connection
    Then system should display network error message

  Scenario: Session timeout during view
    When session expires while opening View Raw Material popup
    Then User should be redirected to login screen

  Scenario: Reopen view popup multiple times
    When User opens and closes View Raw Material popup multiple times
    Then Raw Material details should load correctly each time

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify View Raw Material popup UI
    When User opens View Raw Material popup
    Then all fields should be properly aligned
    And labels should be clearly visible
    And values should be readable
    And "X" button should be positioned correctly

  Scenario: Verify read-only UI behavior
    When User opens View Raw Material popup
    Then all fields except Status should appear disabled
    And no input cursor should be visible

  Scenario: Verify popup overlay behavior
    When User opens View Raw Material popup
    Then background screen should not be interactive until popup is closed

 
  # =========================================================
  # 🔁 SEARCH VARIATION COVERAGE
  # =========================================================

  Scenario Outline: Validate Raw Material search behavior
    When User enters "<input>" in search field
    Then system should return "<result>"

    Examples:
      | input              | result            |
      | exact name         | record found      |
      | partial name       | relevant results  |
      | uppercase          | record found      |
      | invalid input      | no results        |
      | special characters | safe handling     |