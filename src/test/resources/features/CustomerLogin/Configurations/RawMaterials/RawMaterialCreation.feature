@regression @smoke @create @p1
Feature: Create Raw Material via Configuration Module

  # Background navigates to Raw Materials list before every scenario.
  # CommonNavigationSteps short-circuits navigation when the list screen is already visible.

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Raw Materials" feature


  # =========================================================
  # NAVIGATION FLOW
  # =========================================================

  @smoke @p1
  Scenario: Navigate to Raw Materials list screen
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Raw Materials" feature
    Then verify user navigates to "Raw Materials" list screen
    And Add "+" button should be visible


  # =========================================================
  # OPEN ADD RAW MATERIAL POPUP
  # =========================================================

  @smoke @p1
  Scenario: Open Add Raw Material popup and verify all fields
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    And Raw Material Name field should be visible
    And UOM dropdown should be visible
    And Description field should be visible
    And Submit button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button
    Then popup should be closed without saving data
    And User should return to Raw Materials list screen


  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Create Raw Material with all fields
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User enters valid Raw Material Name
    And User selects UOM from dropdown
    And User enters Description
    And User clicks Submit button
    Then Raw Material should be created successfully
    And newly created Raw Material should be displayed in Raw Materials list screen

  @regression @p1
  Scenario: Create Raw Material with mandatory fields only
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User enters valid Raw Material Name
    And User selects UOM from dropdown
    And User leaves Description empty
    And User clicks Submit button
    Then Raw Material should be created successfully
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Create Raw Material with trimmed Raw Material Name
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User enters Raw Material Name with leading and trailing spaces
    And User selects UOM from dropdown
    And User clicks Submit button
    Then system should trim spaces and create Raw Material successfully
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Create Raw Material with maximum allowed characters in Name
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User enters maximum allowed characters in Raw Material Name
    And User selects UOM from dropdown
    And User clicks Submit button
    Then Raw Material should be created successfully
    And User should return to Raw Materials list screen


  # =========================================================
  # NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p1
  Scenario: Create Raw Material without Name
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User leaves Raw Material Name empty
    And User selects UOM from dropdown
    And User clicks Submit button
    Then "This field is required" error should be displayed for Raw Material Name
    When User clicks Close "X" button
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then popup should be closed without saving data
    And User should return to Raw Materials list screen

  @negative @regression @p1
  Scenario: Create Raw Material without UOM
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User enters valid Raw Material Name
    And User does not select UOM
    And User clicks Submit button
    Then "This field is required" error should be displayed for UOM
    When User clicks Close "X" button
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then popup should be closed without saving data
    And User should return to Raw Materials list screen

  @negative @regression @p1
  Scenario: Submit Add Raw Material popup without any data
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User clicks Submit button
    Then "This field is required" error should be displayed for Raw Material Name
    And validation messages should be displayed below respective fields
    When User clicks Close "X" button
    Then popup should be closed without saving data
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Create Raw Material with only spaces in Raw Material Name
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User enters only spaces in Raw Material Name field
    And User selects UOM from dropdown
    And User clicks Submit button
    Then "This field is required" error should be displayed for Raw Material Name
    When User clicks Close "X" button
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then popup should be closed without saving data
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Create Raw Material with duplicate Name
    And User has already created a Raw Material
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User enters existing Raw Material Name
    And User selects UOM from dropdown
    And User clicks Submit button
    Then Raw Material should be created successfully
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Create Raw Material with special characters in Name
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User enters only special characters in Raw Material Name field
    And User selects UOM from dropdown
    And User clicks Submit button
    Then Raw Material should be created successfully
    And User should return to Raw Materials list screen


  # =========================================================
  # DROPDOWN INTERACTION
  # =========================================================

  @regression @p2
  Scenario: Verify UOM dropdown options
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User clicks on UOM dropdown
    Then UOM options list should be displayed
    And User should be able to select one UOM value
    When User clicks Close "X" button
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then popup should be closed without saving data
    And User should return to Raw Materials list screen


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Close Add Raw Material popup with data shows Confirmation Alert
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User enters valid Raw Material Name
    And User selects UOM from dropdown
    When User clicks Close "X" button
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then popup should be closed without saving data
    And User should return to Raw Materials list screen

  @sanity @p3
  Scenario: Close Add Raw Material popup without any data should not show Confirmation Alert
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User clicks Close "X" button
    Then popup should be closed without saving data
    And User should return to Raw Materials list screen

  @sanity @p2
  Scenario: Rapid multiple Submit clicks should prevent duplicate Raw Material creation
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User enters valid Raw Material Name
    And User selects UOM from dropdown
    When User clicks Submit button multiple times quickly
    Then system should prevent duplicate Raw Material creation
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Reopen popup after close
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User clicks Close "X" button
    Then popup should be closed without saving data
    When User reopens Add Raw Material popup
    Then "Add Raw Material" popup should be displayed
    And all fields should be reset
    When User clicks Close "X" button
    Then popup should be closed without saving data
    And User should return to Raw Materials list screen


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Add Raw Material popup UI elements are aligned properly
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    And all Add Raw Material fields should be aligned properly
    And Raw Material Name field should be visible
    And UOM dropdown should be visible
    And Description field should be visible
    And Submit button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button
    Then popup should be closed without saving data
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Verify Raw Materials list screen UI elements
    Then verify user navigates to "Raw Materials" list screen
    And Add "+" button should be visible
    And each Raw Material record should show Raw Material ID
    And each Raw Material record should show Raw Material Name
    And each Raw Material record should show Status

  @regression @p2
  Scenario: Verify input field behavior
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    Then Raw Material Name field should accept valid input
    And UOM dropdown should show selectable options
    And Description field should allow optional text
    When User clicks Close "X" button
    Then popup should be closed without saving data
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Verify validation message UI
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User clicks Submit button
    Then validation messages should be displayed below respective fields
    When User clicks Close "X" button
    Then popup should be closed without saving data
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Validate Description field
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    When User enters long text in Description field
    Then system should accept optional Description input without error
    When User clicks Close "X" button
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then popup should be closed without saving data
    And User should return to Raw Materials list screen
