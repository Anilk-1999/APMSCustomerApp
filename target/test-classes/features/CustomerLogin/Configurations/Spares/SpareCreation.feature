@regression @smoke @create @p1
Feature: Create Spare via Configuration Module

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Spares" feature

  # =========================================================
  # NAVIGATION FLOW
  # =========================================================

  @smoke @p1
  Scenario: Navigate to Spares list screen
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Maintenance" section is displayed
    When User clicks on "Spares" feature
    Then verify user navigates to "Spares" list screen
    Then Spares list should be displayed
    And Add "+" button should be visible in Spares list

  # =========================================================
  # OPEN ADD NEW SPARE POPUP
  # =========================================================

  @smoke @p1
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
    When User clicks Close "X" button in Spare popup
    Then Verify User should be navigate into "Spares" list

  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Create Spare with all fields
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters valid Spare Name
    And User enters valid Spare Code
    And User selects UOM from dropdown
    And User enters valid Current Stock value
    And User enters Description
    And User clicks Submit button
    Then Spare should be created successfully
    And newly created Spare should be visible in Spares list screen

  @regression @p1
  Scenario: Create Spare with mandatory fields only
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters valid Spare Name
    And User enters valid Spare Code
    And User selects UOM from dropdown
    And User enters valid Current Stock value
    And User leaves Description empty
    And User clicks Submit button
    Then Spare should be created successfully
    And Verify User should be navigate into "Spares" list

  @regression @p1
  Scenario: Create Spare with valid decimal stock value
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters valid Spare Name
    And User enters valid Spare Code
    And User selects UOM from dropdown
    And User enters Current Stock as "12345.67"
    And User clicks Submit button
    Then Spare should be created successfully
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Create Spare with zero stock value
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters valid Spare Name
    And User enters valid Spare Code
    And User selects UOM from dropdown
    And User enters Current Stock as "0"
    And User clicks Submit button
    Then Spare should be created successfully
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Create Spare with maximum boundary stock value
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters valid Spare Name
    And User enters valid Spare Code
    And User selects UOM from dropdown
    And User enters Current Stock as "999999.99"
    And User clicks Submit button
    Then Spare should be created successfully
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Create Spare with trimmed Spare Name
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters Spare Name with leading and trailing spaces
    And User enters valid Spare Code
    And User selects UOM from dropdown
    And User enters valid Current Stock value
    And User clicks Submit button
    Then system should trim spaces and create Spare successfully
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p1
  Scenario: Create Spare without Spare Name
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User leaves Spare Name empty
    And User enters valid Spare Code
    And User selects UOM from dropdown
    And User enters valid Current Stock value
    And User clicks Submit button
    Then "This field is required" error should be displayed
    When User clicks Close "X" button in Spare popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Verify User should be navigate into "Spares" list

  @negative @regression @p1
  Scenario: Create Spare without Spare Code
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters valid Spare Name
    And User leaves Spare Code empty
    And User selects UOM from dropdown
    And User enters valid Current Stock value
    And User clicks Submit button
    Then "This field is required" error should be displayed
    When User clicks Close "X" button in Spare popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Verify User should be navigate into "Spares" list

  @negative @regression @p1
  Scenario: Create Spare without UOM selection
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters valid Spare Name
    And User enters valid Spare Code
    And User does not select UOM
    And User enters valid Current Stock value
    And User clicks Submit button
    Then "This field is required" error should be displayed
    When User clicks Close "X" button in Spare popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Verify User should be navigate into "Spares" list

  @negative @regression @p1
  Scenario: Create Spare without Current Stock
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters valid Spare Name
    And User enters valid Spare Code
    And User selects UOM from dropdown
    And User leaves Current Stock empty
    And User clicks Submit button
    Then "This field is required" error should be displayed
    When User clicks Close "X" button in Spare popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Verify User should be navigate into "Spares" list

  @negative @regression @p2
  Scenario: Current Stock exceeds 6 digits before decimal
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters valid Spare Name
    And User enters valid Spare Code
    And User selects UOM from dropdown
    And User enters Current Stock as "1000000"
    Then stock validation error should be displayed below Current Stock field
    When User clicks Close "X" button in Spare popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Verify User should be navigate into "Spares" list

  @negative @regression @p2
  Scenario: Current Stock has more than 2 decimal places
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters valid Spare Name
    And User enters valid Spare Code
    And User selects UOM from dropdown
    And User enters Current Stock as "123.456"
    Then stock validation error should be displayed below Current Stock field
    When User clicks Close "X" button in Spare popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Verify User should be navigate into "Spares" list

  @negative @regression @p2
  Scenario: Spare Name contains only spaces
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters only spaces in Spare Name
    And User enters valid Spare Code
    And User selects UOM from dropdown
    And User enters valid Current Stock value
    And User clicks Submit button
    Then "This field is required" error should be displayed
    When User clicks Close "X" button in Spare popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Verify User should be navigate into "Spares" list

  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Rapid multiple Submit clicks
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters valid Spare Name
    And User enters valid Spare Code
    And User selects UOM from dropdown
    And User enters valid Current Stock value
    When User clicks Submit button multiple times quickly in Spare popup
    Then system should prevent duplicate Spare creation

  @regression @p2
  Scenario: Close popup without saving
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters valid Spare Name
    And User enters valid Spare Code
    And User selects UOM from dropdown
    And User enters valid Current Stock value
    When User clicks Close "X" button in Spare popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then popup should be closed without saving Spare data
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Add New Spare popup UI elements
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    And Spare Name field should be visible
    And Spare Code field should be visible
    And UOM dropdown should be visible
    And Current Stock field should be visible
    And Description field should be visible
    And Submit button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button in Spare popup
    Then Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Verify Spares list screen UI
    Then Spares list should be displayed
    And each Spare record should show Spare ID
    And each Spare record should show Spare Name
    And each Spare record should show Spare Code
    And each Spare record should show UOM
    And each Spare record should show Stock value
    And each Spare record should show Status

  @regression @p2
  Scenario: Verify UOM dropdown options
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User clicks on UOM dropdown
    Then UOM options list should be displayed
    And User should be able to select one UOM value
    When User clicks Close "X" button in Spare popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Verify Close X button closes popup
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters valid Spare Name
    When User clicks Close "X" button in Spare popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then popup should be closed without saving Spare data
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Verify stock validation error message placement
    When User clicks on "+ Add" button in Spares list screen
    Then "Add New Spare" popup should be displayed
    When User enters valid Spare Name
    And User enters valid Spare Code
    And User selects UOM from dropdown
    And User enters Current Stock as "1234567.89"
    Then stock validation error should be displayed below Current Stock field
    When User clicks Close "X" button in Spare popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Verify User should be navigate into "Spares" list
