Feature: Create Spare via Configuration Module

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Maintenance" section is displayed
    When User clicks on "Spares" feature
    Then verify user navigates to "Spares" list screen

  # =========================================================
  # ➕ OPEN ADD SPARE POPUP
  # =========================================================

  Scenario: Open Add New Spare popup
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    And Spare Name field should be visible
    And Spare Code field should be visible
    And UOM dropdown should be visible
    And Current Stock field should be visible
    And Description field should be visible
    And Submit button should be visible
    And Close "X" button should be visible

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Create Spare with all fields
    When User clicks on "+ Add" button
    And User enters valid Spare Name
    And User enters valid Spare Code
    And User selects UOM from dropdown
    And User enters valid Current Stock value
    And User enters Description
    And User clicks Submit button
    Then Spare should be created successfully
    And Spare should be visible in Spares list screen

  Scenario: Create Spare with mandatory fields only
    When User clicks on "+ Add" button
    And User enters valid Spare Name
    And User enters valid Spare Code
    And User selects UOM
    And User enters valid Current Stock
    And User leaves Description empty
    And User clicks Submit button
    Then Spare should be created successfully

  Scenario: Create Spare with decimal stock value
    When User enters Current Stock as "12345.67"
    Then system should accept valid decimal format

  Scenario: Create Spare with minimum value
    When User enters Current Stock as "0"
    Then Spare should be created successfully

  Scenario: Create Spare with trimmed inputs
    When User enters Spare Name with leading and trailing spaces
    And User enters Spare Code with spaces
    And User clicks Submit button
    Then system should trim spaces and create Spare successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Create Spare without Spare Name
    When User clicks on "+ Add" button
    And User leaves Spare Name empty
    And User clicks Submit button
    Then "Spare Name is required" error should be displayed

  Scenario: Create Spare without Spare Code
    When User clicks on "+ Add" button
    And User leaves Spare Code empty
    And User clicks Submit button
    Then "Spare Code is required" error should be displayed

  Scenario: Create Spare without UOM
    When User clicks on "+ Add" button
    And User does not select UOM
    And User clicks Submit button
    Then "UOM is required" error should be displayed

  Scenario: Create Spare without Current Stock
    When User clicks on "+ Add" button
    And User leaves Current Stock empty
    And User clicks Submit button
    Then "Current Stock is required" error should be displayed

  Scenario: Duplicate Spare Code
    When User enters existing Spare Code
    And User clicks Submit button
    Then duplicate validation error should be displayed

  Scenario: Invalid Current Stock format
    When User enters alphabets in Current Stock
    Then "Invalid number format" error should be displayed

  Scenario: Current Stock exceeds limit
    When User enters value more than 6 digits before decimal
    Then validation error should be displayed

  Scenario: More than 2 decimal places
    When User enters "123.456"
    Then "Only 2 decimal places allowed" error should be displayed

  Scenario: Negative Current Stock
    When User enters negative value
    Then validation error should be displayed

  Scenario: Invalid Spare Name or Code
    When User enters only special characters
    Then validation error should be displayed

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Enter only spaces in fields
    When User enters spaces in mandatory fields
    Then validation errors should be displayed

  Scenario: Max boundary value
    When User enters "999999.99"
    Then system should accept value

  Scenario: Exceed max boundary
    When User enters "1000000"
    Then validation error should be displayed

  Scenario: Rapid submit clicks
    When User clicks Submit button multiple times quickly
    Then system should prevent duplicate creation

  Scenario: Large text in Description
    When User enters large text in Description
    Then system should handle input properly

  Scenario: Network failure during creation
    When User clicks Submit button without internet
    Then system should display error message

  Scenario: Session timeout
    When session expires during creation
    Then User should be redirected to login screen

  Scenario: Close popup without saving
    When User enters data and clicks "X"
    Then popup should be closed without saving

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Add Spare popup UI
    When User opens Add Spare popup
    Then all fields should be aligned properly
    And labels should be clearly visible
    And input fields should be enabled
    And Submit button should be enabled only after mandatory fields are filled

  Scenario: Verify UOM dropdown
    When User clicks UOM dropdown
    Then list of UOM values should be displayed
    And User should be able to select one value

  Scenario: Verify error message placement
    When validation fails
    Then error messages should be displayed below respective fields

  Scenario: Verify Close (X) button
    When User clicks "X"
    Then popup should be closed

  # =========================================================
  # 🔍 FIELD VALIDATION SCENARIOS
  # =========================================================

  Scenario: Validate Current Stock field rules
    Then system should enforce:
      | Rule |
      | Required field |
      | Numeric only |
      | Max 6 digits before decimal |
      | Max 2 digits after decimal |
      | No negative values |

