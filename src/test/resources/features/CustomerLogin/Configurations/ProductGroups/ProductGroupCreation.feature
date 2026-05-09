@regression @smoke @create @p1
Feature: Create Product Group via Configuration Module

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Product Groups" feature
    Then verify user navigates to "Product Groups" list screen

  # ==================================================
  # ✅ NAVIGATION SCENARIOS
  # ==================================================

  @smoke @p1
  Scenario: Navigate to Add Product Group popup
    When User clicks on "+ Add" button in Product Groups list screen
    Then "Add New Product Group" popup should be displayed
    And Product Group Name field should be visible
    And Product Group Code field should be visible
    And Description field should be visible
    And Submit button should be visible
    And Close "X" button should be visible in popup header

  # ==================================================
  # ✅ POSITIVE SCENARIOS
  # ==================================================

  @smoke @regression @p1
  Scenario: Create Product Group with valid data
    When User clicks on "+ Add" button in Product Groups list screen
    And User enters valid Product Group Name
    And User enters valid unique Product Group Code
    And User enters Description
    And User clicks Submit button
    Then Product Group should be created successfully
    And newly created Product Group should be displayed in Product Groups list screen

  @regression @p2
  Scenario: Create Product Group with mandatory fields only
    When User clicks on "+ Add" button in Product Groups list screen
    And User enters valid Product Group Name
    And User enters valid unique Product Group Code
    And User leaves Description empty
    And User clicks Submit button
    Then Product Group should be created successfully

  @regression @p2
  Scenario: Create Product Group with maximum allowed values
    When User clicks on "+ Add" button in Product Groups list screen
    And User enters maximum allowed characters in Product Group Name
    And User enters maximum allowed characters in Product Group Code
    And User clicks Submit button
    Then Product Group should be created successfully

  @regression @p2
  Scenario: Create Product Group with trimmed input values
    When User clicks on "+ Add" button in Product Groups list screen
    And User enters Product Group Name with leading and trailing spaces
    And User enters valid Product Group Code
    And User clicks Submit button
    Then system should trim spaces and create Product Group successfully

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  @negative @regression @p1
  Scenario: Create Product Group without Name
    When User clicks on "+ Add" button in Product Groups list screen
    And User leaves Product Group Name empty
    And User clicks Submit button
    Then "Product Group Name is required" should be displayed

  @negative @regression @p1
  Scenario: Create Product Group without Code
    When User clicks on "+ Add" button in Product Groups list screen
    And User clicks Submit button
    Then "Product Group Code is required" should be displayed

  @negative @regression @p2
  Scenario: Create Product Group with duplicate Code
    When User clicks on "+ Add" button in Product Groups list screen
    And User enters existing Product Group Code
    And User enters valid Product Group Name
    And User clicks Submit button
    Then "Product Group Code already exists" should be displayed

  @negative @regression @p2
  Scenario: Create Product Group with duplicate Name
    When User clicks on "+ Add" button in Product Groups list screen
    And User enters existing Product Group Name
    And User enters valid Product Group Code
    And User clicks Submit button
    Then "Product Group already exists" should be displayed

  @negative @regression @p2
  Scenario: Create Product Group with only spaces
    When User clicks on "+ Add" button in Product Groups list screen
    And User enters only spaces in Product Group Name
    And User clicks Submit button
    Then "Product Group Name is required" should be displayed

  @negative @regression @p2
  Scenario: Create Product Group with invalid special characters
    When User clicks on "+ Add" button in Product Groups list screen
    And User enters only special characters in Name and Code
    And User clicks Submit button
    Then validation error should be displayed

  @negative @regression @p2
  Scenario: Create Product Group with spaces in Code
    When User clicks on "+ Add" button in Product Groups list screen
    And User enters Product Group Code with spaces
    And User clicks Submit button
    Then validation error should be displayed for Product Group Code

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  @sanity @p3
  Scenario: Rapid multiple clicks on Submit button
    When User creates Product Group with valid data
    And User clicks Submit button multiple times quickly
    Then system should prevent duplicate Product Group creation

  @sanity @p3
  Scenario: Long input overflow handling
    When User enters very large text beyond allowed limit
    Then system should restrict additional input or show validation error

  @sanity @p3
  Scenario: Network failure during creation
    When User submits Product Group without internet connection
    Then system should display network error message

  @sanity @p3
  Scenario: Session timeout during creation
    When session expires while creating Product Group
    Then User should be redirected to login screen

  @regression @p2
  Scenario: Close popup without saving
    When User opens Add Product Group popup
    And User clicks Close "X" button
    Then popup should be closed without saving data

  @regression @p2
  Scenario: Reopen popup after close
    When User closes Add Product Group popup
    And User reopens Add Product Group popup
    Then all fields should be reset

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  @regression @p2
  Scenario: Verify Add Product Group popup UI
    When User clicks on "+ Add" button in Product Groups list screen
    Then all fields should be properly aligned
    And labels should be clearly visible
    And Submit button should be enabled only when valid data is entered

  @regression @p2
  Scenario: Verify Close button functionality
    When User clicks on Close "X" button
    Then popup should be dismissed successfully
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Verify input field behavior
    Then Product Group Name and Code should accept valid input
    And Description field should allow optional text

  @regression @p2
  Scenario: Verify validation message UI
    When User triggers validation errors
    Then validation messages should be displayed below respective fields

  @regression @p2
  Scenario: Verify tab navigation between fields
    Then User should be able to navigate between fields using keyboard or input focus

  # ==================================================
  # 🔎 FIELD VALIDATION OUTLINES
  # ==================================================

  @regression @p3
  Scenario Outline: Validate Product Group Name field
    When User clicks on "+ Add" button in Product Groups list screen
    And User clicks Submit button
    Then "<error>" should be displayed

    Examples:
      | input | error                          |
      |       | Product Group Name is required |

  @regression @p3
  Scenario Outline: Validate Product Group Code field
    When User clicks on "+ Add" button in Product Groups list screen
    And User clicks Submit button
    Then "<error>" should be displayed

    Examples:
      | input | error                          |
      |       | Product Group Code is required |

  @regression @p2
  Scenario: Validate Description field
    When User enters long text in Description field
    Then system should accept optional input without error
