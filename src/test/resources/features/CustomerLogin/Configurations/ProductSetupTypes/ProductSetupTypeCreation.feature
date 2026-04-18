Feature: Create Product Setup Type via Configuration Module

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Product Setup Types" feature
    Then verify user navigates to "Product Setup Types" list screen

  # =========================================================
  # 🔁 NAVIGATION FLOW
  # =========================================================

  Scenario: Navigate to Product Setup Type list screen
    Then Product Setup Type list should be displayed
    And Add "+" button should be visible

  # =========================================================
  # ➕ OPEN ADD POPUP
  # =========================================================

  Scenario: Open Add Product Setup Type popup
    When User clicks on "+ Add" button in Product Setup Type list screen
    Then "Add Product Setup Type" popup should be displayed
    And Product Setup Name field should be visible
    And Description field should be visible
    And Machine Output Timer field should be visible
    And Product Setup Timer field should be visible
    And Submit button should be visible
    And Close "X" button should be visible in popup header

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Create Product Setup Type with valid data
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User enters valid Product Setup Name
    And User enters Description
    And User clicks on Machine Output Timer field
    Then Duration selection popup should be displayed
    When User selects Hour, Minute and Second by scrolling for Machine Output Timer
    And User clicks Save button in duration popup
    Then selected duration should be displayed in Machine Output Timer field
    When User clicks on Product Setup Timer field
    Then Duration selection popup should be displayed
    When User selects Hour, Minute and Second by scrolling for Product Setup Timer
    And User clicks Save button in duration popup
    Then selected duration should be displayed in Product Setup Timer field
    When User clicks Submit button
    Then Product Setup Type should be created successfully
    And newly created Product Setup Type should be visible in Product Setup Types list screen

  Scenario: Create Product Setup Type with mandatory fields only
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User enters valid Product Setup Name
    And User sets Machine Output Timer duration
    And User sets Product Setup Timer duration
    And User clicks Submit button
    Then Product Setup Type should be created successfully

  Scenario: Create Product Setup Type with maximum allowed duration
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User enters valid Product Setup Name
    And User selects maximum allowed duration for Machine Output Timer
    And User selects maximum allowed duration for Product Setup Timer
    And User clicks Submit button
    Then Product Setup Type should be created successfully

  Scenario: Create Product Setup Type with trimmed Product Setup Name
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User enters Product Setup Name with leading and trailing spaces
    And User sets Machine Output Timer duration
    And User sets Product Setup Timer duration
    And User clicks Submit button
    Then system should trim spaces and create Product Setup Type successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Create without Product Setup Name
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User leaves Product Setup Name empty
    And User clicks Submit button
    Then "Product Setup Name is required" should be displayed

  Scenario: Create without Machine Output Timer
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User enters valid Product Setup Name
    And User does not select Machine Output Timer
    And User clicks Submit button
    Then "Machine Output Timer is required" should be displayed

  Scenario: Create without Product Setup Timer
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User enters valid Product Setup Name
    And User sets Machine Output Timer duration
    And User does not select Product Setup Timer
    And User clicks Submit button
    Then "Product Setup Timer is required" should be displayed

  Scenario: Close duration popup without selecting value
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User enters valid Product Setup Name
    And User opens duration popup for Machine Output Timer
    And User clicks Close "X" button in duration popup without selecting duration
    Then timer value should not be set
    When User clicks Submit button
    Then validation error should be displayed for Machine Output Timer

  Scenario: Invalid zero duration selection for Machine Output Timer
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User enters valid Product Setup Name
    And User selects "00:00:00" duration for Machine Output Timer
    Then system should display validation error

  Scenario: Invalid zero duration selection for Product Setup Timer
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User enters valid Product Setup Name
    And User sets Machine Output Timer duration
    And User selects "00:00:00" duration for Product Setup Timer
    Then system should display validation error

  Scenario: Create Product Setup Type with only spaces in Product Setup Name
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User enters only spaces in Product Setup Name field
    And User clicks Submit button
    Then "Product Setup Name is required" should be displayed

  Scenario: Create Product Setup Type with only special characters in Product Setup Name
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User enters only special characters in Product Setup Name field
    And User clicks Submit button
    Then validation error should be displayed for Product Setup Name field

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid multiple clicks on duration field
    When User clicks timer field multiple times quickly
    Then only one duration popup should open

  Scenario: Rapid multiple submit clicks
    When User creates Product Setup Type with valid data
    And User clicks Submit button multiple times quickly
    Then system should prevent duplicate Product Setup Type creation

  Scenario: Large duration values
    When User selects maximum allowed duration
    Then system should accept valid duration limit

  Scenario: Network failure during creation
    When User clicks Submit button without internet connection
    Then system should display network error message

  Scenario: Session timeout during creation
    When session expires while creating Product Setup Type
    Then User should be redirected to login screen

  Scenario: Close main popup without saving
    When User opens Add Product Setup Type popup
    And User clicks on Close "X" button
    Then popup should be closed without saving data

  Scenario: Reopen popup after closing
    When User closes Add Product Setup Type popup
    And User reopens Add Product Setup Type popup
    Then all fields should be reset

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Add Product Setup Type popup UI
    When User clicks on "+ Add" button in Product Setup Type list screen
    Then all fields should be properly aligned
    And labels should be clearly visible
    And Submit button should be enabled only after mandatory fields are filled

  Scenario: Verify duration popup UI
    When User opens duration popup
    Then Hour selector should be visible
    And Minute selector should be visible
    And Second selector should be visible
    And vertical scrolling should work
    And Save button should be visible
    And Close "X" button should be visible

  Scenario: Verify duration value display
    When User sets timer duration
    Then selected duration should be shown in proper "HH:MM:SS" format

  Scenario: Verify Close button in main popup
    When User clicks on Close "X" button
    Then popup should be dismissed successfully
    And User should return to Product Setup Types list screen

  # =========================================================
  # 🔍 FIELD VALIDATION SCENARIOS
  # =========================================================

  Scenario Outline: Validate Product Setup Name field
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User sets Machine Output Timer duration
    And User sets Product Setup Timer duration
    And User clicks Submit button
    Then "<error>" should be displayed

    Examples:
      | input | error                               |
      |       | Product Setup Name is required      |
      

  Scenario Outline: Validate Machine Output Timer field
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User enters valid Product Setup Name
    And User sets Product Setup Timer duration
    And User enters "<input>" for Machine Output Timer
    And User clicks Submit button
    Then "<error>" should be displayed

    Examples:
      | input    | error                                  |
      |          | Machine Output Timer is required       |
      | 00:00:00 | Invalid Machine Output Timer           |

  Scenario Outline: Validate Product Setup Timer field
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User enters valid Product Setup Name
    And User sets Machine Output Timer duration
    And User enters "<input>" for Product Setup Timer
    And User clicks Submit button
    Then "<error>" should be displayed

    Examples:
      | input    | error                                  |
      |          | Product Setup Timer is required        |
      | 00:00:00 | Invalid Product Setup Timer            |

  Scenario: Validate Description field
    When User clicks on "+ Add" button in Product Setup Type list screen
    And User enters long text in Description field
    Then system should allow optional Description input without error
