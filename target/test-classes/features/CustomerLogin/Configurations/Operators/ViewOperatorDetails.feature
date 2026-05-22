@regression @smoke @view @p1
Feature: View Operator Details via Configuration Module

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Operators" feature


  # =========================================================
  # NAVIGATION — OPEN VIEW OPERATOR SCREEN
  # =========================================================

  @smoke @p1
  Scenario: Open View Operator screen by clicking on Operator record
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User clicks on the Operator record
    Then "View Operator" screen should be displayed
    When User clicks back arrow in View Operator screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Back arrow on View Operator screen navigates back without Confirmation Alert
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User clicks on the Operator record
    Then "View Operator" screen should be displayed
    When User clicks back arrow in View Operator screen
    Then no Confirmation Alert should appear
    And Operator should be navigate into "Operators" list


  # =========================================================
  # MANDATORY FIELD VISIBILITY
  # =========================================================

  @smoke @regression @p1
  Scenario: Verify all mandatory fields are visible in View Operator screen
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User clicks on the Operator record
    Then "View Operator" screen should be displayed
    And Operator ID should be visible in View Operator screen
    And Operator Name should be visible in View Operator screen
    And Operator Phone No should be visible in View Operator screen
    And Blood Group should be visible in View Operator screen
    And Status should be visible in View Operator screen
    When User clicks back arrow in View Operator screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # OPTIONAL FIELD VISIBILITY
  # =========================================================

  @regression @p2
  Scenario: Verify optional fields are visible when filled
    And User has already created an Operator with all fields
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User clicks on the Operator record
    Then "View Operator" screen should be displayed
    And Email ID should be visible in View Operator screen
    And Emergency No should be visible in View Operator screen
    And Operator Code should be visible in View Operator screen
    And DOB should be visible in View Operator screen
    And DOJ should be visible in View Operator screen
    And Address Line I should be visible in View Operator screen
    And Address Line II should be visible in View Operator screen
    And Pin Code should be visible in View Operator screen
    And City should be visible in View Operator screen
    And State should be visible in View Operator screen
    And Country should be visible in View Operator screen
    When User clicks back arrow in View Operator screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Verify View Operator screen with mandatory fields only operator
    And User has already created an Operator with mandatory fields only
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User clicks on the Operator record
    Then "View Operator" screen should be displayed
    And Operator Name should be visible in View Operator screen
    And Operator Phone No should be visible in View Operator screen
    And Blood Group should be visible in View Operator screen
    When User clicks back arrow in View Operator screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # READ-ONLY ASSERTIONS
  # =========================================================

  @regression @p1
  Scenario: Verify all fields are non-editable in View Operator screen
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User clicks on the Operator record
    Then "View Operator" screen should be displayed
    Then all Operator View fields should be non-editable
    And no input cursor should appear in any field in View Operator screen
    And system should not allow modification in View Operator screen
    When User clicks back arrow in View Operator screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Verify Operator Name field is non-editable in View Operator screen
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User clicks on the Operator record
    Then "View Operator" screen should be displayed
    When User tries to edit Operator Name in View screen
    Then system should not allow modification in View Operator screen
    When User clicks back arrow in View Operator screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Verify all Operator fields are read-only in View Operator screen
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User clicks on the Operator record
    Then "View Operator" screen should be displayed
    And all Operator fields should be read-only in View Operator screen
    When User clicks back arrow in View Operator screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # DATA ACCURACY
  # =========================================================

  @regression @p1
  Scenario: Verify displayed data matches created Operator
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User clicks on the Operator record
    Then "View Operator" screen should be displayed
    And Operator Name should match created data
    And Operator Phone No should match created data
    And Blood Group should match created data
    When User clicks back arrow in View Operator screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Verify View Operator screen shows updated data after Operator update
    And User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User clicks on the Operator record
    Then "View Operator" screen should be displayed
    And Operator Name should match updated data
    And Operator Phone No should match updated data
    When User clicks back arrow in View Operator screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Rapid multiple clicks on Operator record should open only one View screen
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User clicks Operator record multiple times quickly
    Then only one View Operator screen should open
    When User clicks back arrow in View Operator screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @sanity @p3
  Scenario: Verify View Operator screen can be scrolled to see all fields
    And User has already created an Operator with all fields
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User clicks on the Operator record
    Then "View Operator" screen should be displayed
    Then user should be able to scroll and view all content in View Operator screen
    When User clicks back arrow in View Operator screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify View Operator screen UI elements and layout
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User clicks on the Operator record
    Then "View Operator" screen should be displayed
    And all Operator View fields should be aligned properly
    And all Operator fields should be read-only in View Operator screen
    When User clicks back arrow in View Operator screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list