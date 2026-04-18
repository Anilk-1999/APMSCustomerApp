Feature: Machine View Flow via Search for Newly Created Machine

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Machine" section is displayed
    When User clicks on "Machines" feature
    Then verify user navigates to "Machines" list screen
    And User has created a machine with all mandatory fields

  # =========================================================
  # 🔍 SEARCH & NAVIGATION FLOW
  # =========================================================

  Scenario: Search and open Machine View screen using detailed steps
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Machine Name
    And User waits for search results to load
    Then system should display matching machine results
    And User verifies machine appears in search results
    When User performs swipe right to left on the machine record
    Then action menu should be displayed
    When User selects "View" option
    Then User should be navigated to Machine View screen
    And all machine details should be displayed in read-only mode
    And no editable fields should be enabled

  # =========================================================
  # 👁️ POSITIVE VIEW SCENARIOS
  # =========================================================

  Scenario: Verify Machine View screen data accuracy
    When User opens Machine View screen for newly created machine
    Then system should display the following fields in read-only mode:
      | Field           |
      | Machine Name    |
      | Machine Code    |
      | Location        |
      | Machine Brand   |
      | Machine Type    |
      | License Type    |
      | IoT Device Type |
    And all values should match saved machine data exactly

  Scenario: Verify License Type display in view mode
    When User opens Machine View screen for newly created machine
    Then License Type should be displayed as read-only value
    And License Type value should match created machine data

  Scenario: Verify dropdown fields in view mode
    When User opens Machine View screen for newly created machine
    Then Machine Brand should be displayed as non-editable
    And Machine Type should be displayed as non-editable
    And IoT Device Type should be displayed as non-editable
    And all dropdown values should be displayed correctly

  Scenario: Verify Machine Code display format in view mode
    When User opens Machine View screen for newly created machine
    Then Machine Code should be visible in correct format
    And Machine Code should not be editable

  Scenario: Verify all machine detail fields are visible in view mode
    When User opens Machine View screen for newly created machine
    Then all machine detail fields should be visible properly
    And all labels and values should be readable

  # =========================================================
  # 🔍 SEARCH VALIDATION SCENARIOS
  # =========================================================

  Scenario: Search with exact Machine Name
    When User enters exact Machine Name in search field
    Then system should return the correct machine result

  Scenario: Search with partial Machine Name
    When User enters partial Machine Name in search field
    Then system should return relevant matching results

  Scenario: Search with uppercase and lowercase variation
    When User enters Machine Name in different letter case
    Then system should return correct result for case-insensitive search

  Scenario: Search with invalid machine name
    When User enters non-existing machine name in search field
    Then system should display "No results found"

  Scenario: Search with special characters
    When User enters "@@@###" in search field
    Then system should handle input safely without crash

  Scenario: Search with empty input
    When User leaves search field empty
    Then system should display default machine list or no filtered results based on application behavior

  Scenario: Search after clearing search input
    When User searches for newly created Machine Name
    And User clears search input
    Then full machine list should be displayed

  # =========================================================
  # ❌ NEGATIVE SCENARIOS - VIEW MODE
  # =========================================================

  Scenario: Verify no edit controls in view screen
    When User opens Machine View screen for newly created machine
    Then Save button should not be visible
    And editable input fields should not be enabled
    And machine details should remain read-only

  Scenario: Unauthorized access to machine view
    When User without permission tries to access Machine View screen
    Then system should show "Access Denied" message

  Scenario: View machine after deletion
    When machine is deleted from system
    And User searches the same machine name
    Then system should show "Machine not found"

  Scenario: Open view screen for unavailable machine record
    When machine record is unavailable in list
    Then User should not be able to open Machine View screen

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid search input handling
    When User types rapidly in search field
    Then system should debounce search requests properly
    And application should remain stable

  Scenario: Large dataset search performance
    Given system contains large number of machine records
    When User searches machine name
    Then search results should load within acceptable response time

  Scenario: Network failure during search
    When User searches machine without internet connection
    Then system should display network error message

  Scenario: Session timeout during view access
    When session expires during navigation to Machine View screen
    Then User should be redirected to login screen

  Scenario: Back navigation from view screen
    When User clicks back button from Machine View screen
    Then User should return to Machine List screen

  Scenario: Reopen same machine view multiple times
    When User opens and closes Machine View screen multiple times
    Then machine details should load correctly each time

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Machine View UI layout
    When User opens Machine View screen for newly created machine
    Then all fields should be properly aligned
    And labels and values should be clearly visible
    And no input field should be editable

  Scenario: Verify scroll behavior in view screen
    When User opens Machine View screen for newly created machine
    Then User should be able to scroll through entire machine details

  Scenario: Verify data formatting in view screen
    When User opens Machine View screen for newly created machine
    Then codes, text values, and displayed details should be properly formatted

  Scenario: Verify UI consistency with update screen structure
    When User opens Machine View screen for newly created machine
    Then view screen structure should match update screen layout in read-only mode

  Scenario: Verify action menu UI from swipe
    When User searches for newly created Machine Name
    And User performs swipe right to left on the machine record
    Then action menu should be displayed properly


  # =========================================================
  # 🔁 SEARCH VARIATIONS COVERAGE
  # =========================================================

  Scenario Outline: Validate machine search behavior
    When User enters "<searchInput>" in search field
    Then system should return "<expectedResult>"

    Examples:
      | searchInput    | expectedResult   |
      | exact name     | machine found    |
      | partial name   | relevant results |
      | uppercase name | machine found    |
      | invalid name   | no results found |
      | special chars  | safe handling    |

  # =========================================================
  # 🔎 READ-ONLY FIELD VALIDATION
  # =========================================================

  Scenario Outline: Validate read-only behavior of view fields
    When User opens Machine View screen for newly created machine
    Then "<fieldName>" should be displayed in read-only mode

    Examples:
      | fieldName       |
      | Machine Name    |
      | Machine Code    |
      | Location        |
      | Machine Brand   |
      | Machine Type    |
      | License Type    |
      | IoT Device Type |