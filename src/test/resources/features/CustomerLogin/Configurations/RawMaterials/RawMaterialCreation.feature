Feature: Create Raw Material via Configuration Module

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Raw Materials" feature
    Then verify user navigates to "Raw Materials" list screen

  # =========================================================
  # 🔁 NAVIGATION FLOW
  # =========================================================

  Scenario: Navigate to Raw Materials list screen
    Then Raw Materials list should be displayed
    And Add "+" button should be visible

  # =========================================================
  # ➕ OPEN ADD RAW MATERIAL POPUP
  # =========================================================

  Scenario: Open Add Raw Material popup
    When User clicks on "+ Add" button in Raw Materials list screen
    Then "Add Raw Material" popup should be displayed
    And Name field should be visible
    And UOM dropdown should be visible
    And Description field should be visible
    And Submit button should be visible
    And Close "X" button should be visible in popup header

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Create Raw Material with all fields
    When User clicks on "+ Add" button in Raw Materials list screen
    And User enters valid Raw Material Name
    And User selects UOM from dropdown
    And User enters Description
    And User clicks Submit button
    Then Raw Material should be created successfully
    And newly created Raw Material should be visible in Raw Materials list screen

  Scenario: Create Raw Material with mandatory fields only
    When User clicks on "+ Add" button in Raw Materials list screen
    And User enters valid Raw Material Name
    And User selects UOM
    And User clicks Submit button
    Then Raw Material should be created successfully

  Scenario: Create Raw Material without Description
    When User clicks on "+ Add" button in Raw Materials list screen
    And User enters valid Raw Material Name
    And User selects UOM
    And User leaves Description empty
    And User clicks Submit button
    Then Raw Material should be created successfully

  Scenario: Create Raw Material with trimmed Name
    When User clicks on "+ Add" button in Raw Materials list screen
    And User enters Raw Material Name with leading and trailing spaces
    And User selects UOM
    And User clicks Submit button
    Then system should trim spaces and create Raw Material successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Create Raw Material without Name
    When User clicks on "+ Add" button in Raw Materials list screen
    And User leaves Name field empty
    And User clicks Submit button
    Then "Name is required" should be displayed

  Scenario: Create Raw Material without UOM
    When User clicks on "+ Add" button in Raw Materials list screen
    And User enters valid Raw Material Name
    And User does not select UOM
    And User clicks Submit button
    Then "UOM is required" should be displayed

  Scenario: Create Raw Material with invalid Name input
    When User clicks on "+ Add" button in Raw Materials list screen
    And User enters only special characters or spaces in Name field
    And User clicks Submit button
    Then validation error should be displayed for Name field

  Scenario: Create Raw Material with duplicate Name
    When User clicks on "+ Add" button in Raw Materials list screen
    And User enters existing Raw Material Name
    And User selects UOM
    And User clicks Submit button
    Then system should show duplicate validation error

  Scenario: Create Raw Material with only spaces in Name
    When User clicks on "+ Add" button in Raw Materials list screen
    And User enters only spaces in Name field
    And User clicks Submit button
    Then "Name is required" should be displayed

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid multiple Submit clicks
    When User creates Raw Material with valid data
    And User clicks Submit button multiple times quickly
    Then system should prevent duplicate Raw Material creation

  Scenario: Large input handling
    When User enters very long Name or Description
    Then system should restrict additional input or validate input length

  Scenario: Network failure during creation
    When User clicks Submit button without internet connection
    Then system should display error message

  Scenario: Session timeout during creation
    When session expires while creating Raw Material
    Then User should be redirected to login screen

  Scenario: Close popup without saving
    When User opens Add Raw Material popup
    And User clicks on Close "X" button
    Then popup should be closed without saving data

  Scenario: Reopen popup after close
    When User closes Add Raw Material popup
    And User reopens Add Raw Material popup
    Then all fields should be reset

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Add Raw Material popup UI
    When User clicks on "+ Add" button in Raw Materials list screen
    Then all fields should be properly aligned
    And labels should be clearly visible
    And Submit button should be enabled only when mandatory fields are filled

  Scenario: Verify UOM dropdown behavior
    When User clicks on UOM dropdown
    Then list of UOM values should be displayed

  Scenario: Verify Close button functionality
    When User clicks on "X" button
    Then popup should be closed
    And User should return to Raw Materials list screen

  Scenario: Verify validation message UI
    When User triggers validation errors
    Then validation messages should be displayed below respective fields

  # =========================================================
  # 🔍 FIELD VALIDATION SCENARIOS
  # =========================================================

  Scenario Outline: Validate Name field
    When User clicks on "+ Add" button in Raw Materials list screen
    And User selects UOM
    And User clicks Submit button
    Then "<error>" should be displayed

    Examples:
      | input | error                         |
      |       | Name is required              |
     

  Scenario Outline: Validate UOM dropdown
    When User clicks on "+ Add" button in Raw Materials list screen
    And User enters valid Raw Material Name
    And User performs "<action>" on UOM dropdown
    And User clicks Submit button
    Then "<error>" should be displayed

    Examples:
      | action              | error            |
      | does not select UOM | UOM is required  |

  Scenario: Validate Description field
    When User clicks on "+ Add" button in Raw Materials list screen
    And User enters long text in Description field
    Then system should allow optional Description input without error

  