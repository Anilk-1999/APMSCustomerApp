@regression @smoke @unit-subscription @p1
Feature: User Unit Subscription via Action Menu

  # Unit Subscription is accessed via long press on a User record in the list.
  # User must be searched first, then long pressed to open Action Menus bottom sheet.
  # Action Menus bottom sheet appears — user clicks "Unit Subscription" option.
  # Unit Subscription popup is read-only: shows subscribed units, has Close "X" button only.

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Users" feature


  # =========================================================
  # NAVIGATION — SEARCH + LONG PRESS + ACTION MENU
  # =========================================================

  @smoke @p1
  Scenario: Search User and long press to show Action Menus with Unit Subscription option
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User long presses on User record
    Then Action Menus bottom sheet should be displayed
    And Unit Subscription option should be visible in Action Menus
    And Machine Subscription option should be visible in Action Menus
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @smoke @p1
  Scenario: Open Unit Subscription popup from Action Menu
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User long presses on User record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Unit Subscription option
    Then Unit Subscription popup should be displayed
    And Close "X" button should be visible in Unit Subscription popup
    When User clicks Close "X" button in Unit Subscription popup
    Then Unit Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # CONTENT DISPLAY ASSERTIONS
  # =========================================================

  @regression @p1
  Scenario: Verify Unit Subscription popup displays assigned units
    And User has already created a User with unit subscriptions
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User long presses on User record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Unit Subscription option
    Then Unit Subscription popup should be displayed
    And unit names should be visible in Unit Subscription popup
    And all assigned units should be displayed correctly
    When User clicks Close "X" button in Unit Subscription popup
    Then Unit Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  @regression @p2
  Scenario: Verify Unit Subscription popup shows empty state when no units assigned
    And User has already created a User with no unit subscriptions
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User long presses on User record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Unit Subscription option
    Then Unit Subscription popup should be displayed
    And Unit Subscription popup should show empty state message
    When User clicks Close "X" button in Unit Subscription popup
    Then Unit Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # READ-ONLY ASSERTIONS
  # =========================================================

  @regression @p1
  Scenario: Verify Unit Subscription popup is read-only
    And User has already created a User with unit subscriptions
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User long presses on User record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Unit Subscription option
    Then Unit Subscription popup should be displayed
    And Unit Subscription popup should be read-only
    And no editable fields should be present in Unit Subscription popup
    And no Submit button should be present in Unit Subscription popup
    When User clicks Close "X" button in Unit Subscription popup
    Then Unit Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # CLOSE POPUP SCENARIOS
  # =========================================================

  @regression @p1
  Scenario: Close Unit Subscription popup via Close X button
    And User has already created a User with unit subscriptions
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User long presses on User record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Unit Subscription option
    Then Unit Subscription popup should be displayed
    When User clicks Close "X" button in Unit Subscription popup
    Then Unit Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Unit Subscription popup should close without affecting user data
    And User has already created a User with unit subscriptions
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User long presses on User record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Unit Subscription option
    Then Unit Subscription popup should be displayed
    When User clicks Close "X" button in Unit Subscription popup
    Then Unit Subscription popup should be closed without saving
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p3
  Scenario: Rapid open and close of Unit Subscription popup
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User long presses on User record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Unit Subscription option
    Then Unit Subscription popup should be displayed
    When User clicks Close "X" button in Unit Subscription popup
    Then Unit Subscription popup should be closed
    When User long presses on User record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Unit Subscription option
    Then Unit Subscription popup should be displayed
    When User clicks Close "X" button in Unit Subscription popup
    Then Unit Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @sanity @p3
  Scenario: Verify Unit Subscription popup can be scrolled when unit list is large
    And User has already created a User with multiple unit subscriptions
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User long presses on User record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Unit Subscription option
    Then Unit Subscription popup should be displayed
    Then user should be able to scroll and view all units in Unit Subscription popup
    When User clicks Close "X" button in Unit Subscription popup
    Then Unit Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @sanity @p3
  Scenario: Open Unit Subscription after opening Machine Subscription in same session
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User long presses on User record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User clicks Close "X" button in Machine Subscription popup
    Then Machine Subscription popup should be closed
    When User long presses on User record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Unit Subscription option
    Then Unit Subscription popup should be displayed
    When User clicks Close "X" button in Unit Subscription popup
    Then Unit Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Unit Subscription popup UI elements
    And User has already created a User with unit subscriptions
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User long presses on User record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Unit Subscription option
    Then Unit Subscription popup should be displayed
    And unit names should be visible in Unit Subscription popup
    And Close "X" button should be visible in Unit Subscription popup
    And all Unit Subscription fields should be aligned properly
    When User clicks Close "X" button in Unit Subscription popup
    Then Unit Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Verify Action Menus bottom sheet shows both subscription options
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User long presses on User record
    Then Action Menus bottom sheet should be displayed
    And Machine Subscription option should be visible in Action Menus
    And Unit Subscription option should be visible in Action Menus
    When User clicks on Unit Subscription option
    Then Unit Subscription popup should be displayed
    When User clicks Close "X" button in Unit Subscription popup
    Then Unit Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list