@regression @smoke @view @p1
Feature: View Raw Material Details from Raw Materials List

  # Background navigates to Raw Materials list before every scenario.
  # CommonNavigationSteps short-circuits navigation when the list screen is already visible.

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Raw Materials" feature


  # =========================================================
  # SEARCH + VIEW FLOW
  # =========================================================

  @smoke @p1
  Scenario: Search and open View Raw Material popup and verify all fields
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Raw Materials" feature
    Then verify user navigates to "Raw Materials" list screen
    And User has already created a Raw Material
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Raw Material Name
    And User waits for search results to load
    Then system should display matching Raw Material results
    And User verifies Raw Material appears in list
    When User clicks on the Raw Material record to view
    Then "View Raw Material" popup should be displayed
    And Raw Material ID should be visible
    And Status should be visible
    And Name should be visible and non-editable
    And UOM should be visible and non-editable
    And Description should be displayed if exists
    And Close "X" button should be visible
    And Save button should NOT be visible
    When User clicks Close "X" button on View Raw Material popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen


  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @regression @p1
  Scenario: Verify Raw Material view data accuracy
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User clicks on the Raw Material record to view
    Then "View Raw Material" popup should be displayed
    And all displayed Raw Material values should match previously saved data
    When User clicks Close "X" button on View Raw Material popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Verify Raw Material ID format in View popup
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User clicks on the Raw Material record to view
    Then "View Raw Material" popup should be displayed
    And Raw Material ID should be visible
    And Raw Material ID should start with hash RMA prefix
    When User clicks Close "X" button on View Raw Material popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Close View Raw Material popup navigates back without Confirmation Alert
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User clicks on the Raw Material record to view
    Then "View Raw Material" popup should be displayed
    When User clicks Close "X" button on View Raw Material popup
    Then "View Raw Material" popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen


  # =========================================================
  # NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p2
  Scenario: Verify all fields are not editable in View Raw Material popup
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User clicks on the Raw Material record to view
    Then "View Raw Material" popup should be displayed
    When User tries to edit Name field
    Then Name field should not be editable
    When User tries to edit UOM field
    Then UOM field should not be editable
    When User tries to edit Description field
    Then Description field should not be editable
    When User clicks Close "X" button on View Raw Material popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @negative @regression @p2
  Scenario: Verify no Save button in View Raw Material popup
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User clicks on the Raw Material record to view
    Then "View Raw Material" popup should be displayed
    Then Save button should NOT be visible
    When User clicks Close "X" button on View Raw Material popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @negative @regression @p2
  Scenario: Background screen should remain inaccessible until View Raw Material popup is closed
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User clicks on the Raw Material record to view
    Then "View Raw Material" popup should be displayed
    Then background screen should remain inaccessible until Raw Material popup is closed
    When User clicks Close "X" button on View Raw Material popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Rapid click on Raw Material record should open only one View popup
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User clicks multiple times quickly on Raw Material record
    Then only one View Raw Material popup should be displayed
    When User clicks Close "X" button on View Raw Material popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @sanity @p3
  Scenario: Reopen View Raw Material popup multiple times
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User opens and closes View Raw Material popup multiple times
    Then Raw Material details should load correctly each time
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify View Raw Material popup UI elements and alignment
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User clicks on the Raw Material record to view
    Then "View Raw Material" popup should be displayed
    And all View Raw Material popup elements should be aligned properly
    And Raw Material ID should be visible
    And Status should be visible
    And Name should be visible and non-editable
    And UOM should be visible and non-editable
    And Close "X" button should be visible
    And Save button should NOT be visible
    When User clicks Close "X" button on View Raw Material popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Verify all fields are read-only in View Raw Material popup
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User clicks on the Raw Material record to view
    Then "View Raw Material" popup should be displayed
    Then all fields should appear disabled in View Raw Material popup
    When User clicks Close "X" button on View Raw Material popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Verify status display format in View Raw Material popup
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User clicks on the Raw Material record to view
    Then "View Raw Material" popup should be displayed
    Then status should be clearly displayed as Active or Inactive
    When User clicks Close "X" button on View Raw Material popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen
