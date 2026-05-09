@regression @view @p2
Feature: View Activity Group Details from Activity Groups List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Maintenance" section is displayed
    When User clicks on "Activity Groups" feature
    Then verify user navigates to "Activity Groups" list screen
    And User has already created an Activity Group

  # =========================================================
  # 🔍 SEARCH + VIEW FLOW
  # =========================================================

  @smoke @p1
  Scenario: Search and open View Activity Group popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list

    When User clicks on the Activity Group record
    Then "View Activity Group" popup should be displayed

    # Field validations
    And Activity Group ID should be visible
    And Status should be visible and clickable
    And Form Name should be visible
    And Description should be displayed if exists
    And Activity Checklist should be visible with activities list
    And Close "X" button should be visible

  # =========================================================
  # 🔒 READ-ONLY VALIDATION
  # =========================================================

  @regression @p1
  Scenario: Verify fields are non-editable in view popup
    When User opens View Activity Group popup
    Then Form Name field should be non-editable
    And Description field should be non-editable
    And Activity Checklist should not allow modification
    And delete option should not be available for activities

  @regression @p1
  Scenario: Verify no Save/Edit buttons
    When User opens View Activity Group popup
    Then Save button should NOT be visible
    And Edit option should NOT be available

  # =========================================================
  # 🔁 STATUS FLOW (ALLOWED IN VIEW)
  # =========================================================

  @regression @p1
  Scenario: Change status from Active to Inactive
    When User opens View Activity Group popup
    And current status is Active
    And User clicks on Status button
    Then Status confirmation popup should be displayed
    And "Yes, Change" button should be visible
    When User clicks "Yes, Change"
    Then status should be changed to Inactive
    And updated status should be displayed in UI

  @regression @p1
  Scenario: Change status from Inactive to Active
    When User opens View Activity Group popup
    And current status is Inactive
    And User clicks on Status button
    And User confirms change
    Then status should be changed to Active

  @negative @regression @p2
  Scenario: Cancel status change
    When User opens View Activity Group popup
    And User clicks Status button
    And User closes popup without confirmation
    Then status should remain unchanged

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  @regression @p1
  Scenario: Verify Activity Group data accuracy
    When User opens View Activity Group popup
    Then Form Name should match created data
    And Description should match saved data
    And Activity Checklist should match selected activities

  @regression @p2
  Scenario: Verify popup close functionality
    When User clicks on "X" button
    Then popup should be closed
    And User should return to Activity Groups list screen

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p2
  Scenario: Attempt to edit fields
    When User tries to edit Form Name or Description
    Then system should not allow modification

  @negative @regression @p2
  Scenario: Attempt to delete activities
    When User tries to delete activity from checklist
    Then delete option should not be available

  @negative @p3
  Scenario: View deleted Activity Group
    When Activity Group is removed from backend
    And User searches same record
    Then system should display "Record not found"

  @negative @p3
  Scenario: Unauthorized access
    When User without permission tries to view Activity Group
    Then system should show "Access Denied"

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p3
  Scenario: Rapid click on record
    When User clicks Activity Group multiple times quickly
    Then only one View popup should open

  @sanity @p3
  Scenario: Rapid status clicks
    When User clicks Status multiple times quickly
    Then only one confirmation popup should appear

  @sanity @p3
  Scenario: Large checklist handling
    When Activity Checklist contains many items
    Then user should be able to scroll list properly

  @negative @p3
  Scenario: Network failure during view
    When User clicks record without internet
    Then system should display error message

  @negative @p3
  Scenario: Session timeout during view
    When session expires while opening popup
    Then User should be redirected to login screen

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify View popup UI
    When User opens View Activity Group popup
    Then all fields should be aligned properly
    And labels should be clearly visible
    And values should be readable
    And "X" button should be correctly positioned

  @regression @p2
  Scenario: Verify checklist UI display
    When User opens View Activity Group popup
    Then all selected activities should be listed
    And no edit/delete controls should be visible

  @regression @p2
  Scenario: Verify status UI
    When User opens View Activity Group popup
    Then status should be clearly visible
    And clickable for toggle

  # =========================================================
  # 🔁 SEARCH VARIATION COVERAGE
  # =========================================================

  @regression @p3
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
