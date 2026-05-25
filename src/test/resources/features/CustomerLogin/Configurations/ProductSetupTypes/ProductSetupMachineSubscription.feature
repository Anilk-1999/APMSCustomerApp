@regression @smoke @machine-subscription @p1
Feature: Product Setup Type Machine Subscription via Action Menu

  # Machine Subscription is accessed via long press on a Product Setup Type record in the list.
  # Product Setup Type must be searched first, then long pressed to open Action Menus bottom sheet.
  # Action Menus bottom sheet appears with "Machine Subscription" option.
  # Machine Subscription popup: "+" to add machines, delete icon per machine, Submit button, Close X button.

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Product Setup Types" feature


  # =========================================================
  # NAVIGATION — SEARCH + LONG PRESS + ACTION MENU
  # =========================================================

  @smoke @p1
  Scenario: Search Product Setup Type and long press to open Action Menus bottom sheet
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    And Machine Subscription option should be visible in Action Menus
    When User closes Action Menus bottom sheet
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @smoke @p1
  Scenario: Open Product Setup Type Machine Subscription popup and verify UI elements
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    And "+" add machine button should be visible
    And Submit button should be visible in Machine Subscription popup
    And Close "X" button should be visible in Machine Subscription popup
    When User clicks Close "X" button in Machine Subscription popup
    Then Machine Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Open Product Setup Type Machine Subscription popup when no machines assigned
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    And Machine Subscription list should show empty state
    And "+" add machine button should be visible
    When User clicks Close "X" button in Machine Subscription popup
    Then Machine Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen


  # =========================================================
  # POSITIVE SCENARIOS — ADD MACHINES
  # =========================================================

  @smoke @regression @p1
  Scenario: Add single Product Setup Type machine subscription
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User clicks "+" button in Machine Subscription popup
    Then "Select Machines" bottom sheet should be displayed
    When User selects one machine from bottom sheet
    And User clicks Submit button in Select Machines bottom sheet
    Then selected machine should be added to Machine Subscription list
    And each machine in list should have a delete icon
    When User clicks Submit button in Machine Subscription popup
    Then Machine Subscription should be saved successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p1
  Scenario: Add multiple Product Setup Type machine subscriptions
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User clicks "+" button in Machine Subscription popup
    Then "Select Machines" bottom sheet should be displayed
    When User selects multiple machines from bottom sheet
    And User clicks Submit button in Select Machines bottom sheet
    Then selected machines should be added to Machine Subscription list
    And each machine in list should have a delete icon
    When User clicks Submit button in Machine Subscription popup
    Then Machine Subscription should be saved successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Add machine and then add more machines in same session
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User clicks "+" button in Machine Subscription popup
    And User selects one machine from bottom sheet
    And User clicks Submit button in Select Machines bottom sheet
    Then selected machine should be added to Machine Subscription list
    When User clicks "+" button in Machine Subscription popup
    And User selects additional machines from bottom sheet
    And User clicks Submit button in Select Machines bottom sheet
    Then additional machines should be appended to Machine Subscription list
    When User clicks Submit button in Machine Subscription popup
    Then Machine Subscription should be saved successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify Product Setup Type Machine Subscription list persists after save
    And User has already created a Product Setup Type with machine subscriptions
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    And previously subscribed machines should be displayed in Machine Subscription list
    When User clicks Close "X" button in Machine Subscription popup
    Then Machine Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen


  # =========================================================
  # POSITIVE SCENARIOS — DELETE MACHINES
  # =========================================================

  @regression @p1
  Scenario: Delete single machine from Product Setup Type Machine Subscription
    And User has already created a Product Setup Type with machine subscriptions
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User clicks delete icon on one machine in list
    Then that machine should be removed from Machine Subscription list
    And remaining machines should still be displayed
    When User clicks Submit button in Machine Subscription popup
    Then Machine Subscription should be saved successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Delete multiple machines from Product Setup Type Machine Subscription
    And User has already created a Product Setup Type with machine subscriptions
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User deletes multiple machines from Machine Subscription list
    Then all deleted machines should be removed from list
    When User clicks Submit button in Machine Subscription popup
    Then Machine Subscription should be saved successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Delete machine and re-add in same session
    And User has already created a Product Setup Type with machine subscriptions
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User clicks delete icon on one machine in list
    Then that machine should be removed from Machine Subscription list
    When User clicks "+" button in Machine Subscription popup
    And User selects that machine from bottom sheet
    And User clicks Submit button in Select Machines bottom sheet
    Then machine should be re-added to Machine Subscription list
    When User clicks Submit button in Machine Subscription popup
    Then Machine Subscription should be saved successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Delete all machines from Product Setup Type Machine Subscription and submit
    And User has already created a Product Setup Type with machine subscriptions
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User deletes all machines from Machine Subscription list
    Then Machine Subscription list should be empty
    When User clicks Submit button in Machine Subscription popup
    Then Machine Subscription should be saved successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify Product Setup Type Machine Subscription list updated after deletion save
    And User has already created a Product Setup Type with machine subscriptions
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User clicks delete icon on one machine in list
    And User clicks Submit button in Machine Subscription popup
    Then Machine Subscription should be saved successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    And deleted machine should not be displayed in Machine Subscription list
    When User clicks Close "X" button in Machine Subscription popup
    Then Machine Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen


  # =========================================================
  # NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p2
  Scenario: Submit Select Machines bottom sheet without selecting any machine
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User clicks "+" button in Machine Subscription popup
    Then "Select Machines" bottom sheet should be displayed
    When User clicks Submit button in Select Machines bottom sheet without selecting
    Then bottom sheet should close with no machines added
    When User clicks Close "X" button in Machine Subscription popup
    Then Machine Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Close Product Setup Type Machine Subscription popup without saving discards changes
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User clicks "+" button in Machine Subscription popup
    And User selects one machine from bottom sheet
    And User clicks Submit button in Select Machines bottom sheet
    Then selected machine should be added to Machine Subscription list
    When User clicks Close "X" button in Machine Subscription popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Machine Subscription popup should be closed without saving
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @sanity @p3
  Scenario: Rapid "+" button clicks in Machine Subscription popup opens only one bottom sheet
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User clicks "+" button in Machine Subscription popup multiple times quickly
    Then only one "Select Machines" bottom sheet should be displayed
    When User clicks Submit button in Select Machines bottom sheet without selecting
    When User clicks Close "X" button in Machine Subscription popup
    Then Machine Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @sanity @p3
  Scenario: Rapid Submit clicks in Product Setup Type Machine Subscription popup should prevent duplicate save
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User clicks "+" button in Machine Subscription popup
    And User selects one machine from bottom sheet
    And User clicks Submit button in Select Machines bottom sheet
    When User clicks Submit button in Machine Subscription popup multiple times quickly
    Then system should prevent duplicate Machine Subscription save
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @sanity @p3
  Scenario: Rapid delete icon clicks should remove machine only once
    And User has already created a Product Setup Type with machine subscriptions
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User clicks delete icon multiple times quickly on one machine
    Then that machine should be removed only once from Machine Subscription list
    When User clicks Close "X" button in Machine Subscription popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Machine Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Product Setup Type Machine Subscription popup UI elements
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    And "+" add machine button should be visible
    And Submit button should be visible in Machine Subscription popup
    And Close "X" button should be visible in Machine Subscription popup
    And all Machine Subscription popup elements should be aligned properly
    When User clicks Close "X" button in Machine Subscription popup
    Then Machine Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify Select Machines bottom sheet UI elements
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User clicks "+" button in Machine Subscription popup
    Then "Select Machines" bottom sheet should be displayed
    And machines list should be displayed in bottom sheet
    And multi-selection should be enabled in Select Machines bottom sheet
    And Submit button should be visible in Select Machines bottom sheet
    When User clicks Submit button in Select Machines bottom sheet without selecting
    When User clicks Close "X" button in Machine Subscription popup
    Then Machine Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify each machine in Product Setup Type Machine Subscription list has delete icon
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User clicks "+" button in Machine Subscription popup
    And User selects multiple machines from bottom sheet
    And User clicks Submit button in Select Machines bottom sheet
    Then each machine in list should have a delete icon
    And all selected machines should be displayed correctly
    When User clicks Close "X" button in Machine Subscription popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Machine Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify Product Setup Type Action Menus bottom sheet shows Machine Subscription option
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User long presses on Product Setup Type record
    Then Action Menus bottom sheet should be displayed
    And Machine Subscription option should be visible in Action Menus
    When User clicks on Machine Subscription option
    Then Machine Subscription popup should be displayed
    When User clicks Close "X" button in Machine Subscription popup
    Then Machine Subscription popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen
