Feature: View Spare Details from Spares List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Maintenance" section is displayed
    When User clicks on "Spares" feature
    Then verify user navigates to "Spares" list screen
    And User has already created a Spare

  # =========================================================
  # 🔍 SEARCH + VIEW FLOW
  # =========================================================

  Scenario: Search and open View Spare popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User clicks on the Spare record
    Then "View Spare" popup should be displayed
    And Spare ID should be visible
    And Status should be visible and clickable
    And Spare Name should be visible
    And Spare Code should be visible
    And UOM should be visible
    And Current Stock should be visible
    And Description should be visible if exists
    And Close "X" button should be visible

  # =========================================================
  # 🔒 READ-ONLY VALIDATION
  # =========================================================

  Scenario: Verify fields are non-editable in view popup
    When User opens View Spare popup
    Then Spare Name field should be non-editable
    And Spare Code field should be non-editable
    And UOM field should be non-editable
    And Current Stock field should be non-editable
    And Description field should be non-editable

  Scenario: Verify no Save/Edit buttons
    When User opens View Spare popup
    Then Save button should NOT be visible
    And Edit option should NOT be available

  # =========================================================
  # 🔁 STATUS FLOW (ALLOWED IN VIEW)
  # =========================================================

  Scenario: Change status from Active to Inactive
    When User opens View Spare popup
    And current status is Active
    And User clicks on Status button
    Then Status confirmation popup should be displayed
    And "Yes, Change" button should be visible
    When User clicks "Yes, Change"
    Then status should be changed to Inactive
    And updated status should be displayed in UI

  Scenario: Change status from Inactive to Active
    When User opens View Spare popup
    And current status is Inactive
    And User clicks on Status button
    And User confirms change
    Then status should be changed to Active

  Scenario: Cancel status change
    When User opens View Spare popup
    And User clicks Status button
    And User closes popup without confirmation
    Then status should remain unchanged

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Verify Spare data accuracy
    When User opens View Spare popup
    Then Spare Name should match created data
    And Spare Code should match saved data
    And UOM should match saved data
    And Current Stock should match saved data
    And Description should match saved data if available

  Scenario: Verify popup close functionality
    When User clicks on "X" button
    Then popup should be closed
    And User should return to Spares list screen

  Scenario: Verify Current Stock display format
    When User opens View Spare popup
    Then Current Stock should be displayed in valid numeric format

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Attempt to edit fields
    When User opens View Spare popup
    And User tries to edit Spare Name
    Then Spare Name field should not allow modification
    When User tries to edit Spare Code
    Then Spare Code field should not allow modification
    When User tries to edit UOM
    Then UOM field should not allow modification
    When User tries to edit Current Stock
    Then Current Stock field should not allow modification
    When User tries to edit Description
    Then Description field should not allow modification

  Scenario: View deleted Spare
    When Spare is removed from backend
    And User searches same record
    Then system should display "Record not found"

  Scenario: Unauthorized access
    When User without permission tries to view Spare
    Then system should show "Access Denied"

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid click on record
    When User clicks Spare record multiple times quickly
    Then only one View popup should open

  Scenario: Rapid status clicks
    When User clicks Status multiple times quickly
    Then only one confirmation popup should appear

  Scenario: Large description handling
    When Spare contains large Description text
    Then user should be able to view content properly

  Scenario: Network failure during view
    When User clicks record without internet
    Then system should display error message

  Scenario: Session timeout during view
    When session expires while opening popup
    Then User should be redirected to login screen

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify View Spare popup UI
    When User opens View Spare popup
    Then all fields should be aligned properly
    And labels should be clearly visible
    And values should be readable
    And "X" button should be correctly positioned

  Scenario: Verify read-only UI behavior
    When User opens View Spare popup
    Then all fields except Status should be disabled
    And no input cursor should be visible

  Scenario: Verify status UI
    When User opens View Spare popup
    Then status should be clearly visible
    And clickable for toggle


  # =========================================================
  # 🔁 SEARCH VARIATION COVERAGE
  # =========================================================

  Scenario Outline: Validate search behavior
    When User enters "<input>" in search field
    Then system should return "<result>"

    Examples:
      | input            | result           |
      | exact name       | record found     |
      | partial name     | relevant results |
      | uppercase        | record found     |
      | invalid input    | no results       |
      | special chars    | safe handling    |