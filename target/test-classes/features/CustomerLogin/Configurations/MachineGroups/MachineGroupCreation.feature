Feature: Create Machine Group via Configuration Module

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Machine" section is displayed
    When User clicks on "Machine Groups" feature
    Then verify user navigates to "Machine Groups" list screen

  # =========================================================
  # 🔁 NAVIGATION FLOW
  # =========================================================

  Scenario: Navigate to Add Machine Group popup
    When User clicks on "+ Add" button in Machine Groups list screen
    Then "Add New Machine Group" popup should be displayed
    And Machine Group Name field should be visible
    And Description field should be visible
    And Add Machine "+" icon should be visible
    And Submit button should be visible

  # =========================================================
  # ➕ CREATE MACHINE GROUP FLOW
  # =========================================================

  Scenario: Create Machine Group with valid data and multiple machines
    When User clicks on "+ Add" button in Machine Groups list screen
    And User enters valid Machine Group Name
    And User enters Description
    And User clicks on Add Machine "+" icon
    Then "Select Machines" bottom sheet should be displayed
    When User selects multiple machines from list
    And User clicks on Submit button in machine selection bottom sheet
    Then selected machines should be added to Machine Group popup
    When User clicks on Submit button in Add New Machine Group popup
    Then Machine Group should be created successfully
    And newly created Machine Group should be displayed in Machine Groups list screen

  Scenario: Create Machine Group with mandatory fields only
    When User clicks on "+ Add" button in Machine Groups list screen
    And User enters valid Machine Group Name
    And User clicks on Add Machine "+" icon
    And User selects single machine from list
    And User clicks on Submit button in machine selection bottom sheet
    And User clicks on Submit button in Add New Machine Group popup
    Then Machine Group should be created successfully

  Scenario: Create Machine Group with maximum allowed machine selection
    When User clicks on "+ Add" button in Machine Groups list screen
    And User enters valid Machine Group Name
    And User clicks on Add Machine "+" icon
    And User selects maximum allowed machines from list
    And User clicks on Submit button in machine selection bottom sheet
    Then system should map all selected machines successfully
    When User clicks on Submit button in Add New Machine Group popup
    Then Machine Group should be created successfully

  Scenario: Create Machine Group after selecting and revalidating machine list
    When User clicks on "+ Add" button in Machine Groups list screen
    And User enters valid Machine Group Name
    And User clicks on Add Machine "+" icon
    And User selects multiple machines from list
    And User clicks on Submit button in machine selection bottom sheet
    Then selected machines should be displayed in popup summary section
    When User clicks on Submit button in Add New Machine Group popup
    Then Machine Group should be created successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Create Machine Group without Group Name
    When User clicks on "+ Add" button in Machine Groups list screen
    And User leaves Machine Group Name field empty
    And User clicks on Submit button in Add New Machine Group popup
    Then "Machine Group Name is required" should be displayed

  Scenario: Create Machine Group without selecting machines
    When User clicks on "+ Add" button in Machine Groups list screen
    And User enters valid Machine Group Name
    And User does not add any machine
    And User clicks on Submit button in Add New Machine Group popup
    Then "At least one machine must be selected" should be displayed

  Scenario: Submit popup without adding machines via Add Machine icon
    When User clicks on "+ Add" button in Machine Groups list screen
    And User enters valid Machine Group Name
    And User clicks on Submit button in Add New Machine Group popup
    Then system should display machine selection validation error

  Scenario: Duplicate Machine Group Name
    When User clicks on "+ Add" button in Machine Groups list screen
    And User enters existing Machine Group Name
    And User clicks on Add Machine "+" icon
    And User selects single machine from list
    And User clicks on Submit button in machine selection bottom sheet
    And User clicks on Submit button in Add New Machine Group popup
    Then "Machine Group already exists" should be displayed

  Scenario: Create Machine Group with only spaces in Group Name
    When User clicks on "+ Add" button in Machine Groups list screen
    And User enters only spaces in Machine Group Name field
    And User clicks on Submit button in Add New Machine Group popup
    Then "Machine Group Name is required" should be displayed

  Scenario: Create Machine Group with invalid special characters only in Group Name
    When User clicks on "+ Add" button in Machine Groups list screen
    And User enters only special characters in Machine Group Name field
    And User clicks on Submit button in Add New Machine Group popup
    Then validation error should be displayed for Machine Group Name field

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid clicks on Add Machine icon
    When User clicks on "+ Add" button in Machine Groups list screen
    And User clicks Add Machine "+" icon multiple times quickly
    Then system should open only one machine selection bottom sheet

  Scenario: Select and deselect machines in bottom sheet
    When User clicks on "+ Add" button in Machine Groups list screen
    And User enters valid Machine Group Name
    And User clicks on Add Machine "+" icon
    And User selects multiple machines from list
    And User deselects some machines before submit
    And User clicks on Submit button in machine selection bottom sheet
    Then only final selected machines should be mapped to the Machine Group

  Scenario: Large machine list handling in bottom sheet
    When User clicks on "+ Add" button in Machine Groups list screen
    And User clicks on Add Machine "+" icon
    Then machine list should load successfully in bottom sheet
    And User should be able to scroll machine list
    And machine search support should be available if implemented

  Scenario: Network failure during machine selection
    When User clicks on "+ Add" button in Machine Groups list screen
    And User clicks on Add Machine "+" icon without internet connection
    Then system should display network error message

  Scenario: Session timeout during group creation
    When session expires while User is creating Machine Group
    Then User should be redirected to login screen

  Scenario: Rapid multiple submit clicks during group creation
    When User creates Machine Group with valid details
    And User clicks on Submit button multiple times rapidly
    Then system should prevent duplicate Machine Group creation

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Add Machine Group popup UI
    When User clicks on "+ Add" button in Machine Groups list screen
    Then Machine Group Name field should be visible
    And Description field should be visible
    And Add Machine "+" icon should be visible
    And Submit button should be visible
    And Submit button should be enabled or disabled based on validation rules

  Scenario: Verify Select Machines bottom sheet UI
    When User clicks on "+ Add" button in Machine Groups list screen
    And User clicks on Add Machine "+" icon
    Then machine list should be displayed correctly in bottom sheet
    And each machine should have selectable checkbox

  Scenario: Verify selected machines UI in popup
    When User clicks on "+ Add" button in Machine Groups list screen
    And User enters valid Machine Group Name
    And User clicks on Add Machine "+" icon
    And User selects multiple machines from list
    And User clicks on Submit button in machine selection bottom sheet
    Then selected machines should be shown in popup summary section

  Scenario: Verify scroll behavior in machine selection bottom sheet
    When User clicks on "+ Add" button in Machine Groups list screen
    And User clicks on Add Machine "+" icon
    Then User should be able to scroll machine list in bottom sheet

  Scenario: Verify popup close behavior
    When User clicks on "+ Add" button in Machine Groups list screen
    And User closes Add New Machine Group popup
    Then popup should be dismissed successfully

  # =========================================================
  # 🔍 FIELD VALIDATION SCENARIOS
  # =========================================================

  Scenario Outline: Validate Machine Group Name field
    When User clicks on "+ Add" button in Machine Groups list screen
    And User clicks on Submit button in Add New Machine Group popup
    Then "<error>" should be displayed

    Examples:
      | input  | error                                |
      |        | Machine Group Name is required       |
     
  Scenario: Validate maximum character limit for Machine Group Name
    When User clicks on "+ Add" button in Machine Groups list screen
    And User enters value greater than allowed limit in Machine Group Name field
    Then system should restrict additional characters or show validation error

  Scenario: Validate Description field with long text
    When User clicks on "+ Add" button in Machine Groups list screen
    And User enters long text in Description field
    Then system should allow optional Description input without error

  Scenario: Validate Description field with special characters
    When User clicks on "+ Add" button in Machine Groups list screen
    And User enters special characters in Description field
    Then system should handle Description input safely


  # =========================================================
  # 🔁 MACHINE SELECTION COVERAGE
  # =========================================================

  Scenario Outline: Validate machine selection scenarios
    When User clicks on "+ Add" button in Machine Groups list screen
    And User opens Select Machines bottom sheet
    And User selects "<selectionType>" machines
    Then system should handle machine selection correctly

    Examples:
      | selectionType |
      | single        |
      | multiple      |
      | max limit     |
      | none          |