Feature: View Product Group Details from Product Group List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Product Groups" feature
    Then verify user navigates to "Product Groups" list screen
    And User has already created a Product Group

  # =========================================================
  # 🔍 SEARCH + VIEW FLOW
  # =========================================================

  Scenario: Search and open Product Group View popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Group Name
    And User waits for search results to load
    Then system should display matching product group results
    And User verifies product group appears in list
    When User clicks on the product group record
    Then "View Product Group" popup should be displayed
    And Product Group ID should be visible
    And Product Group Name should be visible
    And Product Group Code should be visible
    And Description should be visible
    And Close "X" button should be visible
    And all fields should be displayed in read-only mode

  # =========================================================
  # 👁️ POSITIVE SCENARIOS
  # =========================================================

  Scenario: Verify Product Group view data accuracy
    When User opens Product Group View popup
    Then system should display the following fields:
      | Field              |
      | Product Group ID   |
      | Product Group Name |
      | Product Group Code |
      | Description        |
    And all displayed values should match saved Product Group data

  Scenario: Verify popup close functionality
    When User opens Product Group View popup
    And User clicks on "X" button
    Then popup should be closed
    And User should return to Product Groups list screen

  Scenario: Verify Product Group Code format display
    When User opens Product Group View popup
    Then Product Group Code should be displayed correctly
    And Product Group Code should not be editable

  Scenario: Verify Product Group Name display in view popup
    When User opens Product Group View popup
    Then Product Group Name should be displayed correctly
    And Product Group Name should not be editable

  Scenario: Verify Description display in view popup
    When User opens Product Group View popup
    Then Description should be displayed correctly
    And Description should not be editable

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Verify fields are not editable
    When User opens Product Group View popup
    And User tries to edit Product Group Name
    Then Product Group Name field should not be editable

  Scenario: Verify no action buttons in view popup
    When User opens Product Group View popup
    Then Save button should not be visible
    And Edit button should not be visible

  Scenario: View deleted Product Group
    When Product Group is deleted from backend
    And User searches same Product Group
    Then system should display "Product Group not found"

  Scenario: Unauthorized access to view Product Group
    When User without permission tries to view Product Group
    Then system should display "Access Denied"

  Scenario: Verify user cannot modify data from keyboard input
    When User opens Product Group View popup
    And User tries to modify field values using keyboard input
    Then field values should remain unchanged

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid click on Product Group record
    When User clicks multiple times quickly on Product Group record
    Then only one View Product Group popup should open

  Scenario: Long description handling in view popup
    When Product Group contains large Description
    And User opens Product Group View popup
    Then Description content should be displayed properly
    And User should be able to scroll if required

  Scenario: Network failure during view
    When User clicks Product Group record without internet connection
    Then system should display network error message

  Scenario: Session timeout during view
    When session expires while opening Product Group View popup
    Then User should be redirected to login screen

  Scenario: Reopen view popup multiple times
    When User opens and closes Product Group View popup multiple times
    Then Product Group details should load correctly each time

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify View Product Group popup UI
    When User opens Product Group View popup
    Then all fields should be properly aligned
    And labels should be clearly visible
    And values should be readable
    And "X" button should be correctly positioned

  Scenario: Verify read-only UI behavior
    When User opens Product Group View popup
    Then all fields should appear disabled or read-only
    And no editable input cursor should be visible

  Scenario: Verify scroll functionality in popup
    When User opens Product Group View popup
    Then User should be able to scroll popup content if required

  Scenario: Verify UI consistency with Edit popup
    When User opens Product Group View popup
    Then View popup layout should match Edit popup structure
    And all fields should be disabled

  Scenario: Verify popup overlay behavior
    When User opens Product Group View popup
    Then background screen should remain inaccessible until popup is closed

  # =========================================================
  # 🔁 SEARCH VARIATION COVERAGE
  # =========================================================

  Scenario Outline: Validate Product Group search behavior
    When User enters "<searchInput>" in search field
    Then system should return "<expectedResult>"

    Examples:
      | searchInput      | expectedResult   |
      | exact group name | group found      |
      | partial name     | relevant results |
      | uppercase name   | group found      |
      | invalid name     | no results found |
      | special chars    | safe handling    |

  # =========================================================
  # 🔍 READ-ONLY FIELD VALIDATION
  # =========================================================

  Scenario Outline: Validate read-only behavior of Product Group fields
    When User opens Product Group View popup
    Then "<fieldName>" should be displayed in read-only mode

    Examples:
      | fieldName          |
      | Product Group ID   |
      | Product Group Name |
      | Product Group Code |
      | Description        |
