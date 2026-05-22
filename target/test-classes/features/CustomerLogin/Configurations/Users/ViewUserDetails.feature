@regression @smoke @view @p1
Feature: View User Details via Configuration Module

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Users" feature


  # =========================================================
  # NAVIGATION — OPEN VIEW USER SCREEN
  # =========================================================

  @smoke @p1
  Scenario: Open View User screen by clicking on User record
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks back arrow in View User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Back arrow on View User screen navigates back without Confirmation Alert
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks back arrow in View User screen
    Then no Confirmation Alert should appear
    And User should be navigate into "Users" list


  # =========================================================
  # MANDATORY FIELD VISIBILITY
  # =========================================================

  @smoke @regression @p1
  Scenario: Verify all mandatory fields are visible in View User screen
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    And User ID should be visible in View User screen
    And User Name should be visible in View User screen
    And Email ID should be visible in View User screen
    And Phone No should be visible in View User screen
    And Blood Group should be visible in View User screen
    And Roles should be visible in View User screen
    And Status should be visible in View User screen
    And Duplicate button should be visible in View User screen
    When User clicks back arrow in View User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # OPTIONAL FIELD VISIBILITY
  # =========================================================

  @regression @p2
  Scenario: Verify optional fields are visible when filled
    And User has already created a User with all fields
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    And Emergency No should be visible in View User screen
    And Emp Code should be visible in View User screen
    And DOB should be visible in View User screen
    And DOJ should be visible in View User screen
    And Address Line I should be visible in View User screen
    And Address Line II should be visible in View User screen
    And Pin Code should be visible in View User screen
    And City should be visible in View User screen
    And State should be visible in View User screen
    And Country should be visible in View User screen
    And Teams should be visible in View User screen
    When User clicks back arrow in View User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Verify View User screen with mandatory fields only user
    And User has already created a User with mandatory fields only
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    And User Name should be visible in View User screen
    And Email ID should be visible in View User screen
    And Phone No should be visible in View User screen
    And Blood Group should be visible in View User screen
    And Roles should be visible in View User screen
    When User clicks back arrow in View User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # READ-ONLY ASSERTIONS
  # =========================================================

  @regression @p1
  Scenario: Verify all fields are non-editable in View User screen
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    Then all User View fields should be non-editable
    And no input cursor should appear in any field in View User screen
    And system should not allow modification in View User screen
    When User clicks back arrow in View User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Verify User Name field is non-editable in View User screen
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User tries to edit User Name in View screen
    Then system should not allow modification in View User screen
    When User clicks back arrow in View User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Verify all User fields are read-only in View User screen
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    And all User fields should be read-only in View User screen
    When User clicks back arrow in View User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # DATA ACCURACY
  # =========================================================

  @regression @p1
  Scenario: Verify displayed data matches created User
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    And User Name should match created data
    And Email ID should match created data
    And Phone No should match created data
    And Blood Group should match created data
    And Roles should match created data
    When User clicks back arrow in View User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Verify View User screen shows updated data after User update
    And User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    And User Name should match updated data
    And Phone No should match updated data
    When User clicks back arrow in View User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # DUPLICATE BUTTON
  # =========================================================

  @regression @p1
  Scenario: Verify Duplicate button is visible in View User screen
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    And Duplicate button should be visible in View User screen
    When User clicks back arrow in View User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Rapid multiple clicks on User record should open only one View screen
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks User record multiple times quickly
    Then only one View User screen should open
    When User clicks back arrow in View User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @sanity @p3
  Scenario: Verify View User screen can be scrolled to see all fields
    And User has already created a User with all fields
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    Then user should be able to scroll and view all content in View User screen
    When User clicks back arrow in View User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify View User screen UI elements and layout
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    And all User View fields should be aligned properly
    And all User fields should be read-only in View User screen
    And Duplicate button should be visible in View User screen
    When User clicks back arrow in View User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list