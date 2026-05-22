@regression @smoke @create @p1
Feature: Create Product Group via Configuration Module

  # Background navigates to Product Groups list before every scenario.
  # CommonNavigationSteps.alreadyOnListScreen() skips navigation when already
  # on a module list screen, same as the Activity Group module pattern.

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Product Groups" feature


  # =========================================================
  # NAVIGATION FLOW
  # =========================================================

  @smoke @p1
  Scenario: Navigate to Product Groups list screen
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Product Groups" feature
    Then verify user navigates to "Product Groups" list screen
    And Add "+" button should be visible


  # =========================================================
  # OPEN ADD PRODUCT GROUP POPUP
  # =========================================================

  @smoke @p1
  Scenario: Open Add Product Group popup
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    And Product Group Name field should be visible
    And Product Group Code field should be visible
    And Description field should be visible
    And Submit button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button
    Then popup should be closed without saving data
    And User should return to Product Groups list screen


  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Create Product Group with all fields
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    When User enters valid Product Group Name
    And User enters valid Product Group Code
    And User enters Description
    And User clicks Submit button
    Then Product Group should be created successfully
    And newly created Product Group should be displayed in Product Groups list screen

  @regression @p1
  Scenario: Create Product Group with mandatory fields only
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    When User enters valid Product Group Name
    And User enters valid Product Group Code
    And User leaves Description empty
    And User clicks Submit button
    Then Product Group should be created successfully
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Create Product Group with trimmed Product Group Name
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    When User enters Product Group Name with leading and trailing spaces
    And User enters valid Product Group Code
    And User clicks Submit button
    Then system should trim spaces and create Product Group successfully
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Create Product Group with maximum allowed values
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    When User enters maximum allowed characters in Product Group Name
    And User enters maximum allowed characters in Product Group Code
    And User clicks Submit button
    Then Product Group should be created successfully
    And User should return to Product Groups list screen


  # =========================================================
  # NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p1
  Scenario: Create Product Group without Product Group Name
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    When User leaves Product Group Name empty
    And User enters valid Product Group Code
    And User clicks Submit button
    Then "Product Group Name is required" should be displayed
    When User clicks Close "X" button

    Then popup should be closed without saving data
    And User should return to Product Groups list screen

  @negative @regression @p1
  Scenario: Create Product Group without Product Group Code
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    When User enters valid Product Group Name
    And User leaves Product Group Code empty
    And User clicks Submit button
    Then "Product Group Code is required" should be displayed
    When User clicks Close "X" button

    Then popup should be closed without saving data
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Create Product Group with only spaces in Product Group Name
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    When User enters only spaces in Product Group Name
    And User enters valid Product Group Code
    And User clicks Submit button
    Then "Product Group Name is required" should be displayed
    When User clicks Close "X" button

    Then popup should be closed without saving data
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Create Product Group with duplicate Product Group Name
    And User has already created a Product Group
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    When User enters existing Product Group Name
    And User enters valid Product Group Code
    And User clicks Submit button
    Then Product Group should be created successfully
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Create Product Group with duplicate Product Group Code
    And User has already created a Product Group
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    When User enters valid Product Group Name
    And User enters existing Product Group Code
    And User clicks Submit button
    Then Product Group should be created successfully
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Create Product Group with special characters
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    When User enters only special characters in Name and Code
    And User clicks Submit button
    Then Product Group should be created successfully
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Create Product Group with spaces in Product Group Code
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    When User enters valid Product Group Name
    And User enters Product Group Code with spaces
    And User clicks Submit button
    Then Product Group should be created successfully
    And User should return to Product Groups list screen


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Rapid multiple Submit clicks
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    And User enters valid Product Group Name
    And User enters valid Product Group Code
    When User clicks Submit button multiple times quickly
    Then Product Group should be created successfully

  @sanity @p3
  Scenario: Create Product Group with very large text
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    When User enters very large text beyond allowed limit
    And User enters valid Product Group Code
    And User clicks Submit button
    Then Product Group should be created successfully
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Close popup without saving
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    And User enters valid Product Group Name
    And User enters valid Product Group Code
    When User clicks Close "X" button
    Then popup should be closed without saving data
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Reopen popup after close
    When User opens Add Product Group popup
    And User clicks Close "X" button
    Then popup should be closed without saving data
    When User reopens Add Product Group popup
    Then "Add New Product Group" popup should be displayed
    And all fields should be reset


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Add Product Group popup UI elements
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    And Product Group Name field should be visible
    And Product Group Code field should be visible
    And Description field should be visible
    And Submit button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button
    Then popup should be closed without saving data
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Verify Product Groups list screen UI
    Then verify user navigates to "Product Groups" list screen
    And Add "+" button should be visible

  @regression @p2
  Scenario: Verify input field behavior
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    Then Product Group Name and Code should accept valid input
    And Description field should allow optional text

  @regression @p2
  Scenario: Verify validation message UI
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    When User triggers validation errors
    And User clicks Submit button
    Then validation messages should be displayed below respective fields

  @regression @p2
  Scenario: Validate Description field
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    When User enters long text in Description field
    Then system should accept optional input without error
