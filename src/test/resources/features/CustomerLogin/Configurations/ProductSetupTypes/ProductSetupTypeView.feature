Feature: View Product Setup Type Details from List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Product Setup Types" feature
    Then verify user navigates to "Product Setup Types" list screen
    And User has already created a Product Setup Type

  # =========================================================
  # 🔍 SEARCH + VIEW FLOW
  # =========================================================

  Scenario: Search and open Product Setup Type View popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record
    Then "View Product Setup Type" popup should be displayed
    And Product Setup Type ID should be visible
    And Product Setup Name should be visible
    And Machine Output Timer should be visible
    And Product Setup Timer should be visible
    And Description should be displayed if exists
    And Close "X" button should be visible
    And all fields should be displayed in read-only mode
    And Save button should not be visible
    And Submit button should not be visible

  # =========================================================
  # 👁️ POSITIVE SCENARIOS
  # =========================================================

  Scenario: Verify Product Setup Type view data accuracy
    When User opens Product Setup Type View popup
    Then all displayed values should match saved Product Setup Type data
    And duration fields should display correct formatted values

  Scenario: Verify popup close functionality
    When User opens Product Setup Type View popup
    And User clicks on "X" button
    Then popup should be closed
    And User should return to Product Setup Types list screen

  Scenario: Verify duration display format
    When User opens Product Setup Type View popup
    Then Machine Output Timer should be displayed in "HH:MM:SS" format
    And Product Setup Timer should be displayed in "HH:MM:SS" format

  Scenario: Verify Description display in view popup
    When Product Setup Type has Description
    And User opens Product Setup Type View popup
    Then Description should be displayed correctly

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Verify fields are not editable
    When User opens Product Setup Type View popup
    And User tries to edit Product Setup Name
    Then Product Setup Name field should not be editable

  Scenario: Verify no duration popup opens in view mode
    When User opens Product Setup Type View popup
    And User clicks on Machine Output Timer field
    Then duration popup should not be displayed

  Scenario: Verify no duration popup opens for Product Setup Timer in view mode
    When User opens Product Setup Type View popup
    And User clicks on Product Setup Timer field
    Then duration popup should not be displayed

  Scenario: View deleted Product Setup Type
    When Product Setup Type is deleted from backend
    And User searches same Product Setup Type record
    Then system should display "Record not found"

  Scenario: Unauthorized access to view Product Setup Type
    When User without permission tries to view Product Setup Type
    Then system should show "Access Denied"

  Scenario: Attempt modification through keyboard input
    When User opens Product Setup Type View popup
    And User tries to modify field values using keyboard input
    Then field values should remain unchanged

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid click on Product Setup Type record
    When User clicks multiple times quickly on Product Setup Type record
    Then only one View Product Setup Type popup should open

  Scenario: Large Description handling
    When Product Setup Type contains large Description text
    And User opens Product Setup Type View popup
    Then User should be able to scroll popup content properly

  Scenario: Network failure during view
    When User clicks Product Setup Type record without internet connection
    Then system should display network error message

  Scenario: Session timeout during view
    When session expires while opening Product Setup Type View popup
    Then User should be redirected to login screen

  Scenario: Reopen view popup multiple times
    When User opens and closes Product Setup Type View popup multiple times
    Then Product Setup Type details should load correctly each time

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify View Product Setup Type popup UI
    When User opens Product Setup Type View popup
    Then all fields should be properly aligned
    And labels should be clearly visible
    And values should be readable
    And "X" button should be positioned correctly

  Scenario: Verify read-only UI behavior
    When User opens Product Setup Type View popup
    Then all fields should appear disabled or read-only
    And no editable input cursor should be visible

  Scenario: Verify UI consistency with Edit popup
    When User opens Product Setup Type View popup
    Then View popup layout should match Edit Product Setup Type popup structure
    And all fields should be disabled

  Scenario: Verify popup overlay behavior
    When User opens Product Setup Type View popup
    Then background screen should not be interactive until popup is closed

  
  # =========================================================
  # 🔁 SEARCH VARIATION COVERAGE
  # =========================================================

  Scenario Outline: Validate Product Setup Type search behavior
    When User enters "<input>" in search field
    Then system should return "<result>"

    Examples:
      | input              | result            |
      | exact name         | record found      |
      | partial name       | relevant results  |
      | uppercase          | record found      |
      | invalid input      | no results        |
      | special characters | safe handling     |

  # =========================================================
  # 🔍 READ-ONLY FIELD VALIDATION
  # =========================================================

  Scenario Outline: Validate read-only behavior of Product Setup Type fields
    When User opens Product Setup Type View popup
    Then "<fieldName>" should be displayed in read-only mode

    Examples:
      | fieldName             |
      | Product Setup Name    |
      | Machine Output Timer  |
      | Product Setup Timer   |
      | Description           |