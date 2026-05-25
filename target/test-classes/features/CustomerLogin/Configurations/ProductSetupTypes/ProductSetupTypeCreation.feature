@regression @smoke @create @p1
Feature: Create Product Setup Type via Configuration Module

  # Background navigates to Product Setup Types list before every scenario.

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Product Setup Types" feature


  # =========================================================
  # NAVIGATION FLOW
  # =========================================================

  @smoke @p1
  Scenario: Navigate to Product Setup Types list screen
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Product Setup Types" feature
    Then verify user navigates to "Product Setup Types" list screen
    And Add "+" button should be visible


  # =========================================================
  # OPEN ADD PRODUCT SETUP TYPE POPUP
  # =========================================================

  @smoke @p1
  Scenario: Open Add Product Setup Type popup and verify all fields
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    And Product Setup Name field should be visible
    And Description field should be visible
    And Machine Output Timer field should be visible
    And Product Setup Timer field should be visible
    And Submit button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button on Product Setup Type popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen


  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Create Product Setup Type with mandatory fields only
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters valid Product Setup Name
    And User sets Machine Output Timer to 0 hours 30 minutes 0 seconds
    And User sets Product Setup Timer to 1 hours 0 minutes 0 seconds
    And User clicks Submit button on Product Setup Type popup
    Then Product Setup Type should be created successfully
    And newly created Product Setup Type should be displayed in Product Setup Types list screen

  @regression @p1
  Scenario: Create Product Setup Type with all fields
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters valid Product Setup Name
    And User enters Description for Product Setup Type
    And User sets Machine Output Timer to 0 hours 30 minutes 0 seconds
    And User sets Product Setup Timer to 1 hours 0 minutes 0 seconds
    And User clicks Submit button on Product Setup Type popup
    Then Product Setup Type should be created successfully
    And newly created Product Setup Type should be displayed in Product Setup Types list screen

  @regression @p2
  Scenario: Create Product Setup Type with trimmed Product Setup Name
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters Product Setup Name with leading and trailing spaces
    And User sets Machine Output Timer to 0 hours 30 minutes 0 seconds
    And User sets Product Setup Timer to 1 hours 0 minutes 0 seconds
    And User clicks Submit button on Product Setup Type popup
    Then system should trim spaces and create Product Setup Type successfully
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Create Product Setup Type with maximum allowed characters in name
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters maximum allowed characters in Product Setup Name
    And User sets Machine Output Timer to 0 hours 30 minutes 0 seconds
    And User sets Product Setup Timer to 1 hours 0 minutes 0 seconds
    And User clicks Submit button on Product Setup Type popup
    Then Product Setup Type should be created successfully
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Create Product Setup Type with special characters in name
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters only special characters in Product Setup Name
    And User sets Machine Output Timer to 0 hours 30 minutes 0 seconds
    And User sets Product Setup Timer to 1 hours 0 minutes 0 seconds
    And User clicks Submit button on Product Setup Type popup
    Then Product Setup Type should be created successfully
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Create Product Setup Type with long description
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters valid Product Setup Name
    And User enters long text in Product Setup Type Description field
    And User sets Machine Output Timer to 0 hours 30 minutes 0 seconds
    And User sets Product Setup Timer to 1 hours 0 minutes 0 seconds
    And User clicks Submit button on Product Setup Type popup
    Then Product Setup Type should be created successfully
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Create Product Setup Type with duplicate name
    And User has already created a Product Setup Type
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters existing Product Setup Type Name
    And User sets Machine Output Timer to 0 hours 30 minutes 0 seconds
    And User sets Product Setup Timer to 1 hours 0 minutes 0 seconds
    And User clicks Submit button on Product Setup Type popup
    Then Product Setup Type should be created successfully
    And User should return to Product Setup Types list screen


  # =========================================================
  # NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p1
  Scenario: Create Product Setup Type without Product Setup Name
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User leaves Product Setup Name empty
    And User sets Machine Output Timer to 0 hours 30 minutes 0 seconds
    And User sets Product Setup Timer to 1 hours 0 minutes 0 seconds
    And User clicks Submit button on Product Setup Type popup
    Then "This field is required" error should be displayed for Product Setup Name
    When User clicks Close "X" button on Product Setup Type popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen

  @negative @regression @p2
  Scenario: Create Product Setup Type with only spaces in Product Setup Name
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters only spaces in Product Setup Name
    And User sets Machine Output Timer to 0 hours 30 minutes 0 seconds
    And User sets Product Setup Timer to 1 hours 0 minutes 0 seconds
    And User clicks Submit button on Product Setup Type popup
    Then "This field is required" error should be displayed for Product Setup Name
    When User clicks Close "X" button on Product Setup Type popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen

  @negative @regression @p1
  Scenario: Submit without filling any fields
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User clicks Submit button on Product Setup Type popup
    Then validation messages should be displayed for Product Setup Type fields
    And "This field is required" error should be displayed for Product Setup Name
    And "This field is required" error should be displayed for Machine Output Timer
    And "This field is required" error should be displayed for Product Setup Timer
    When User clicks Close "X" button on Product Setup Type popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen

  @negative @regression @p2
  Scenario: Create Product Setup Type without Machine Output Timer
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters valid Product Setup Name
    And User sets Product Setup Timer to 1 hours 0 minutes 0 seconds
    And User clicks Submit button on Product Setup Type popup
    Then "This field is required" error should be displayed for Machine Output Timer
    When User clicks Close "X" button on Product Setup Type popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen

  @negative @regression @p2
  Scenario: Save duration picker at 0 hours 0 minutes 0 seconds — minimum duration alert shown
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User taps Machine Output Timer field
    Then "Select Duration" popup should be displayed
    When User clicks Save button on duration picker
    Then "Duration should be at least 1 minute" alert should be displayed
    When User clicks Close "X" button on duration picker
    Then "Add Product Setup Type" popup should be displayed
    When User clicks Close "X" button on Product Setup Type popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen

  @negative @regression @p2
  Scenario: Close Machine Output Timer duration picker without saving — required error shown
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters valid Product Setup Name
    And User sets Product Setup Timer to 1 hours 0 minutes 0 seconds
    When User taps Machine Output Timer field
    Then "Select Duration" popup should be displayed
    When User clicks Close "X" button on duration picker
    Then "Add Product Setup Type" popup should be displayed
    When User clicks Submit button on Product Setup Type popup
    Then "This field is required" error should be displayed for Machine Output Timer
    When User clicks Close "X" button on Product Setup Type popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen

  @negative @regression @p2
  Scenario: Create Product Setup Type without Product Setup Timer
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters valid Product Setup Name
    And User sets Machine Output Timer to 0 hours 30 minutes 0 seconds
    And User clicks Submit button on Product Setup Type popup
    Then "This field is required" error should be displayed for Product Setup Timer
    When User clicks Close "X" button on Product Setup Type popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen

  @negative @regression @p2
  Scenario: Close Product Setup Timer duration picker without saving — required error shown
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters valid Product Setup Name
    And User sets Machine Output Timer to 0 hours 30 minutes 0 seconds
    When User taps Product Setup Timer field
    Then "Select Duration" popup should be displayed
    When User clicks Close "X" button on duration picker
    Then "Add Product Setup Type" popup should be displayed
    When User clicks Submit button on Product Setup Type popup
    Then "This field is required" error should be displayed for Product Setup Timer
    When User clicks Close "X" button on Product Setup Type popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Rapid multiple Submit clicks
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters valid Product Setup Name
    And User sets Machine Output Timer to 0 hours 30 minutes 0 seconds
    And User sets Product Setup Timer to 1 hours 0 minutes 0 seconds
    When User clicks Submit button multiple times quickly on Product Setup Type popup
    Then Product Setup Type should be created successfully
    And User should return to Product Setup Types list screen

  @sanity @p3
  Scenario: Create Product Setup Type with very large text in name
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters very large text in Product Setup Name
    And User sets Machine Output Timer to 0 hours 30 minutes 0 seconds
    And User sets Product Setup Timer to 1 hours 0 minutes 0 seconds
    And User clicks Submit button on Product Setup Type popup
    Then Product Setup Type should be created successfully
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Close popup without saving
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters valid Product Setup Name
    And User sets Machine Output Timer to 0 hours 30 minutes 0 seconds
    When User clicks Close "X" button on Product Setup Type popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Close empty popup without confirmation dialog
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User clicks Close "X" button on Product Setup Type popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Reopen popup after close — fields should be reset
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User clicks Close "X" button on Product Setup Type popup
    Then Product Setup Type popup should be closed without saving data
    When User reopens Add Product Setup Type popup
    Then "Add Product Setup Type" popup should be displayed
    And Product Setup Type name field should be empty
    When User clicks Close "X" button on Product Setup Type popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify Select Duration popup opens for Machine Output Timer
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User taps Machine Output Timer field
    Then "Select Duration" popup should be displayed
    When User clicks Save button on duration picker
    Then "Duration should be at least 1 minute" alert should be displayed
    When User clicks Close "X" button on duration picker
    Then "Add Product Setup Type" popup should be displayed
    When User taps Machine Output Timer field
    Then "Select Duration" popup should be displayed
    When User clicks Close "X" button on duration picker
    Then "Add Product Setup Type" popup should be displayed
    When User clicks Close "X" button on Product Setup Type popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify Select Duration popup opens for Product Setup Timer
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User taps Product Setup Timer field
    Then "Select Duration" popup should be displayed
    When User clicks Save button on duration picker
    Then "Duration should be at least 1 minute" alert should be displayed
    When User clicks Close "X" button on duration picker
    Then "Add Product Setup Type" popup should be displayed
    When User taps Product Setup Timer field
    Then "Select Duration" popup should be displayed
    When User clicks Close "X" button on duration picker
    Then "Add Product Setup Type" popup should be displayed
    When User clicks Close "X" button on Product Setup Type popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Add Product Setup Type popup UI elements
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    And Product Setup Name field should be visible
    And Description field should be visible
    And Machine Output Timer field should be visible
    And Product Setup Timer field should be visible
    And Submit button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button on Product Setup Type popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify Product Setup Types list screen UI elements
    Then verify user navigates to "Product Setup Types" list screen
    And Add "+" button should be visible

  @regression @p2
  Scenario: Validate Description optional field behavior
    When User clicks on "+ Add" button in Product Setup Types list screen
    Then "Add Product Setup Type" popup should be displayed
    When User enters long text in Product Setup Type Description field
    Then system should accept optional Product Setup Type input without error
    When User clicks Close "X" button on Product Setup Type popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Product Setup Type popup should be closed without saving data
    And User should return to Product Setup Types list screen
