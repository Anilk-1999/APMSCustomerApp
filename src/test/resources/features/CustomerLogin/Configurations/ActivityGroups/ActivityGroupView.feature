@regression @view @p2
Feature: View Activity Group Details from Activity Groups List

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Activity Groups" feature

  # =========================================================
  # 🔍 SEARCH + VIEW FLOW
  # =========================================================

  @smoke @p1
  Scenario: Search and open View Activity Group popup
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Maintenance" section is displayed
    When User clicks on "Activity Groups" feature
    Then verify user navigates to "Activity Groups" list screen
    And User has already created an Activity Group
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
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  # =========================================================
  # 🔒 READ-ONLY VALIDATION
  # =========================================================

  @regression @p1
  Scenario: Verify fields are non-editable in view popup
    When User has already created an Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User clicks on the Activity Group record
    Then "View Activity Group" popup should be displayed
    Then Form Name field should be non-editable
    And Description field should be non-editable
    And Activity Checklist should not allow modification
    And delete option should not be available for activities
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @regression @p1
  Scenario: Verify no Save/Edit buttons
    When User has already created an Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User clicks on the Activity Group record
    Then "View Activity Group" popup should be displayed
    Then Save button should NOT be visible
    And Edit option should NOT be available
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  @regression @p1
  Scenario: Verify Activity Group data accuracy
    When User has already created an Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User clicks on the Activity Group record
    Then "View Activity Group" popup should be displayed
    Then Form Name should match created data
    And Description should match saved data
    And Activity Checklist should match selected activities
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @regression @p2
  Scenario: Verify popup close functionality
    When User has already created an Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User clicks on the Activity Group record
    Then "View Activity Group" popup should be displayed
    When User clicks Close "X" button in Activity Group popup
    Then popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p2
  Scenario: Attempt to edit fields
    When User has already created an Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User clicks on the Activity Group record
    Then "View Activity Group" popup should be displayed
    When User tries to edit Form Name or Description
    Then system should not allow modification
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @negative @regression @p2
  Scenario: Attempt to delete activities
    When User has already created an Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User clicks on the Activity Group record
    Then "View Activity Group" popup should be displayed
    When User tries to delete activity from checklist
    Then delete option should not be available
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @negative @p3
  Scenario: View deleted Activity Group
    When User has already created an Activity Group
    When Activity Group is removed from backend
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
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
    When User has already created an Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User clicks Activity Group multiple times quickly
    Then only one View popup should open
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @sanity @p3
  Scenario: Large checklist handling
    When User has already created an Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User clicks on the Activity Group record
    Then "View Activity Group" popup should be displayed
    When Activity Checklist contains many items
    Then user should be able to scroll list properly
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @negative @p3
  Scenario: Network failure during view
    When User has already created an Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
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
    When User has already created an Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User clicks on the Activity Group record
    Then "View Activity Group" popup should be displayed
    Then all fields should be aligned properly
    And labels should be clearly visible
    And values should be readable
    And "X" button should be correctly positioned
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @regression @p2
  Scenario: Verify checklist UI display
    When User has already created an Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User clicks on the Activity Group record
    Then "View Activity Group" popup should be displayed
    Then all selected activities should be listed
    And no edit or delete controls should be visible
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @regression @p2
  Scenario: Verify status UI
    When User has already created an Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User clicks on the Activity Group record
    Then "View Activity Group" popup should be displayed
    Then status should be clearly visible
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

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
