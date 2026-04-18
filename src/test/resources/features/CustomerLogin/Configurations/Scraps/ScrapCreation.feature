Feature: Create Scrap Item via Configuration Module

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Scraps" feature
    Then verify user navigates to "Scraps" list screen

  # =========================================================
  # 🔁 NAVIGATION FLOW
  # =========================================================

  Scenario: Navigate to Scraps list screen
    Then Scrap list should be displayed
    And Add "+" button should be visible

  # =========================================================
  # ➕ OPEN ADD SCRAP POPUP
  # =========================================================

  Scenario: Open Add New Scrap Item popup
    When User clicks on "+ Add" button in Scraps list screen
    Then "Add New Scrap Item" popup should be displayed
    And Scrap Name field should be visible
    And Description field should be visible
    And Scrap Type dropdown should be visible
    And Metrics Type dropdown should be visible
    And UOM dropdown should be visible but disabled initially
    And Submit button should be visible
    And Close "X" button should be visible in popup header

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Create Scrap with all fields
    When User clicks on "+ Add" button in Scraps list screen
    And User enters valid Scrap Name
    And User enters Description
    And User selects Scrap Type from dropdown
    And User selects Metrics Type from dropdown
    Then UOM dropdown should be enabled
    When User selects UOM from dropdown
    And User clicks Submit button
    Then Scrap should be created successfully
    And newly created Scrap should be visible in Scraps list screen

  Scenario: Create Scrap with mandatory fields only
    When User clicks on "+ Add" button in Scraps list screen
    And User enters valid Scrap Name
    And User selects Scrap Type
    And User selects Metrics Type
    Then UOM dropdown should be enabled
    When User selects UOM
    And User clicks Submit button
    Then Scrap should be created successfully

  Scenario: Create Scrap without Description
    When User clicks on "+ Add" button in Scraps list screen
    And User enters valid Scrap Name
    And User selects Scrap Type
    And User selects Metrics Type
    And User selects UOM
    And User leaves Description empty
    And User clicks Submit button
    Then Scrap should be created successfully

  Scenario: Create Scrap with trimmed Scrap Name
    When User clicks on "+ Add" button in Scraps list screen
    And User enters Scrap Name with leading and trailing spaces
    And User selects Scrap Type
    And User selects Metrics Type
    And User selects UOM
    And User clicks Submit button
    Then system should trim spaces and create Scrap successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Create Scrap without Scrap Name
    When User clicks on "+ Add" button in Scraps list screen
    And User leaves Scrap Name empty
    And User clicks Submit button
    Then "Scrap Name is required" should be displayed

  Scenario: Create Scrap without Scrap Type
    When User clicks on "+ Add" button in Scraps list screen
    And User enters valid Scrap Name
    And User does not select Scrap Type
    And User clicks Submit button
    Then "Scrap Type is required" should be displayed

  Scenario: Create Scrap without Metrics Type
    When User clicks on "+ Add" button in Scraps list screen
    And User enters valid Scrap Name
    And User selects Scrap Type
    And User does not select Metrics Type
    And User clicks Submit button
    Then "Metrics Type is required" should be displayed

  Scenario: Create Scrap without UOM after selecting Metrics Type
    When User clicks on "+ Add" button in Scraps list screen
    And User enters valid Scrap Name
    And User selects Scrap Type
    And User selects Metrics Type
    And User does not select UOM
    And User clicks Submit button
    Then "UOM is required" should be displayed

  Scenario: UOM should not be selectable before Metrics Type
    When User clicks on "+ Add" button in Scraps list screen
    And User clicks on UOM dropdown without selecting Metrics Type
    Then UOM dropdown should remain disabled

  Scenario: Invalid Scrap Name input
    When User clicks on "+ Add" button in Scraps list screen
    And User enters only spaces or special characters in Scrap Name field
    And User clicks Submit button
    Then validation error should be displayed for Scrap Name

  Scenario: Duplicate Scrap Name
    When User clicks on "+ Add" button in Scraps list screen
    And User enters existing Scrap Name
    And User selects Scrap Type
    And User selects Metrics Type
    And User selects UOM
    And User clicks Submit button
    Then duplicate validation error should be displayed

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid multiple Submit clicks
    When User creates Scrap with valid data
    And User clicks Submit button multiple times quickly
    Then system should prevent duplicate Scrap creation

  Scenario: Metrics Type change resets UOM
    When User clicks on "+ Add" button in Scraps list screen
    And User selects Metrics Type
    And User selects UOM
    And User changes Metrics Type again
    Then UOM field should be reset
    And UOM dropdown should be disabled
    And User must reselect UOM

  Scenario: Large input handling
    When User enters very long Scrap Name or Description
    Then system should restrict or validate input length

  Scenario: Network failure during creation
    When User clicks Submit button without internet connection
    Then system should display error message

  Scenario: Session timeout during creation
    When session expires while creating Scrap
    Then User should be redirected to login screen

  Scenario: Close popup without saving
    When User opens Add Scrap popup
    And User clicks Close "X" button
    Then popup should be closed without saving data

  Scenario: Reopen popup after close
    When User closes Add Scrap popup
    And User reopens Add Scrap popup
    Then all fields should be reset

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Add Scrap popup UI
    When User clicks on "+ Add" button in Scraps list screen
    Then all fields should be properly aligned
    And labels should be clearly visible
    And UOM dropdown should be disabled initially
    And Submit button should be enabled only after mandatory fields are filled

  Scenario: Verify dropdown behavior
    When User clicks on Scrap Type or Metrics Type dropdown
    Then respective dropdown options should be displayed correctly

  Scenario: Verify dependent field behavior
    When User selects Metrics Type
    Then UOM dropdown should be enabled

  Scenario: Verify Close button functionality
    When User clicks on "X" button
    Then popup should be closed
    And User should return to Scraps list screen

  # =========================================================
  # 🔍 FIELD VALIDATION SCENARIOS
  # =========================================================

  Scenario Outline: Validate Scrap Name field
    When User clicks on "+ Add" button in Scraps list screen
    And User selects Scrap Type
    And User selects Metrics Type
    And User selects UOM
    And User clicks Submit button
    Then "<error>" should be displayed

    Examples:
      | input | error                     |
      |       | Scrap Name is required   |
    

  Scenario: Validate UOM dependency behavior
    Then system should enforce:
      | Rule |
      | Disabled before Metrics Type |
      | Mandatory after Metrics Type |
