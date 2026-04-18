Feature: View Scrap Item Details from Scraps List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Scraps" feature
    Then verify user navigates to "Scraps" list screen
    And User has already created a Scrap Item

  # =========================================================
  # 🔍 SEARCH + VIEW FLOW
  # =========================================================

  Scenario: Search and open View Scrap Item popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Scrap Name
    And User waits for search results to load
    Then system should display matching Scrap results
    And User verifies Scrap appears in list
    When User clicks on the Scrap record
    Then "View Scrap Item" popup should be displayed
    And Scrap ID should be visible
    And Status should be visible
    And Scrap Name should be visible
    And Description should be displayed if exists
    And Scrap Type should be visible
    And Metrics Type should be visible
    And UOM should be visible
    And Close "X" button should be visible

    # ✅ Read-only validation
    And Scrap Name field should be displayed in read-only mode
    And Description field should be displayed in read-only mode
    And Scrap Type field should be displayed in read-only mode
    And Metrics Type field should be displayed in read-only mode
    And UOM field should be displayed in read-only mode
    And Status field should be displayed in read-only mode

    # ❌ No actions allowed
    And Save button should not be visible
    And Edit option should not be visible

  # =========================================================
  # 👁️ POSITIVE SCENARIOS
  # =========================================================

  Scenario: Verify Scrap view data accuracy
    When User opens View Scrap Item popup
    Then all displayed values should match backend stored data

  Scenario: Verify popup close functionality
    When User opens View Scrap Item popup
    And User clicks on "X" button
    Then popup should be closed
    And User should return to Scraps list screen

  Scenario: Verify status display format
    When User opens View Scrap Item popup
    Then status should be clearly displayed as Active or Inactive

  Scenario: Verify dependent field display
    When User opens View Scrap Item popup
    Then Metrics Type and UOM values should be displayed correctly

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Verify fields are not editable
    When User opens View Scrap Item popup
    And User tries to edit Scrap Name field
    Then Scrap Name field should not be editable
    When User tries to edit Description field
    Then Description field should not be editable
    When User tries to edit Scrap Type field
    Then Scrap Type field should not be editable
    When User tries to edit Metrics Type field
    Then Metrics Type field should not be editable
    When User tries to edit UOM field
    Then UOM field should not be editable
    When User tries to edit Status field
    Then Status field should not be editable

  Scenario: Verify no action buttons in view popup
    When User opens View Scrap Item popup
    Then Save button should not be visible
    And Edit option should not be available

  Scenario: View deleted Scrap
    When Scrap is deleted from backend
    And User searches same Scrap
    Then system should display "Record not found"

  Scenario: Unauthorized access to Scrap view
    When User without permission tries to view Scrap
    Then system should show "Access Denied"

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid click on Scrap record
    When User clicks multiple times quickly on Scrap record
    Then only one View popup should open

  Scenario: Network failure during view
    When User clicks Scrap record without internet connection
    Then system should display network error message

  Scenario: Session timeout during view
    When session expires while opening View Scrap Item popup
    Then User should be redirected to login screen

  Scenario: Reopen view popup multiple times
    When User opens and closes View Scrap Item popup multiple times
    Then Scrap details should load correctly each time

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify View Scrap popup UI
    When User opens View Scrap Item popup
    Then all fields should be properly aligned
    And labels should be clearly visible
    And values should be readable
    And "X" button should be positioned correctly

  Scenario: Verify read-only UI behavior
    When User opens View Scrap Item popup
    Then all fields should appear disabled
    And no input cursor should be visible

  Scenario: Verify popup overlay behavior
    When User opens View Scrap Item popup
    Then background screen should not be interactive until popup is closed

  
  # =========================================================
  # 🔁 SEARCH VARIATION COVERAGE
  # =========================================================

  Scenario Outline: Validate Scrap search behavior
    When User enters "<input>" in search field
    Then system should return "<result>"

    Examples:
      | input              | result            |
      | exact name         | record found      |
      | partial name       | relevant results  |
      | uppercase          | record found      |
      | invalid input      | no results        |
      | special characters | safe handling     |