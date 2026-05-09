@regression @smoke @create @p1
Feature: Create Holiday via Configuration Module

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Unit" section is displayed
    When User clicks on "Holidays" feature
    Then verify user navigates to "Holidays" list screen

  # =========================================================
  # 🔁 NAVIGATION FLOW
  # =========================================================

  @smoke @p1
  Scenario: Navigate to Holidays list screen
    Then Holidays list should be displayed
    And Add "+" button should be visible

  # =========================================================
  # ➕ OPEN ADD HOLIDAY POPUP
  # =========================================================

  @smoke @p1
  Scenario: Open Add New Holiday popup
    When User clicks on "+ Add" button
    Then "Add New Holiday" popup should be displayed
    And Holiday Name field should be visible
    And Holiday Date field should be visible
    And "Is National Holiday" label with toggle button should be visible
    And Submit button should be visible
    And Close "X" button should be visible

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Create Holiday with mandatory fields only
    When User enters valid Holiday Name
    And User clicks on Holiday Date field
    Then Select Date popup should be displayed
    And calendar should be visible
    And "OK" button should be visible
    And Close "X" button should be visible in date popup

    When User selects a valid unique Holiday Date
    And User clicks "OK"
    Then selected date should be displayed in Holiday Date field

    When User clicks Submit button
    Then Holiday should be created successfully
    And newly created Holiday should be visible in Holidays list screen

  @regression @p1
  Scenario: Create Holiday with National Holiday enabled
    When User enters valid Holiday Name
    And User selects a valid unique Holiday Date
    And User enables "Is National Holiday" toggle
    And User clicks Submit button
    Then Holiday should be created successfully
    And Holiday should be marked as National Holiday

  @regression @p2
  Scenario: Create Holiday with trimmed Holiday Name
    When User enters Holiday Name with leading and trailing spaces
    And User selects a valid unique Holiday Date
    And User clicks Submit button
    Then system should trim spaces and create Holiday successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p1
  Scenario: Create Holiday without Holiday Name
    When User leaves Holiday Name empty
    And User selects a valid unique Holiday Date
    And User clicks Submit button
    Then "Holiday Name is required" should be displayed

  @negative @regression @p1
  Scenario: Create Holiday without Holiday Date
    When User enters valid Holiday Name
    And User does not select Holiday Date
    And User clicks Submit button
    Then "Holiday Date is required" should be displayed

  @negative @regression @p2
  Scenario: Create Holiday with duplicate Holiday Date
    When User enters valid Holiday Name
    And User selects an already existing Holiday Date
    And User clicks Submit button
    Then "Holiday Date already exists" should be displayed

  @negative @regression @p2
  Scenario: Create Holiday with only spaces in Holiday Name
    When User enters only spaces in Holiday Name
    And User selects a valid unique Holiday Date
    And User clicks Submit button
    Then "Holiday Name is required" should be displayed

  @negative @regression @p2
  Scenario: Create Holiday with invalid characters
    When User enters only special characters in Holiday Name
    And User selects a valid unique Holiday Date
    And User clicks Submit button
    Then validation error should be displayed

  @negative @regression @p2
  Scenario: Close date popup without selecting date
    When User clicks on Holiday Date field
    And User closes date popup using "X"
    Then Holiday Date field should remain empty

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p3
  Scenario: Rapid multiple Submit clicks
    When User enters valid Holiday data
    And User clicks Submit button multiple times
    Then system should prevent duplicate Holiday creation

  @sanity @p3
  Scenario: Rapid toggle clicks
    When User toggles "Is National Holiday" multiple times
    Then final toggle state should be maintained correctly

  @sanity @p3
  Scenario: Rapid date field clicks
    When User clicks Holiday Date field multiple times
    Then only one date picker popup should be displayed

  @negative @p3
  Scenario: Network failure during creation
    When User clicks Submit button without internet
    Then system should display error message

  @negative @p3
  Scenario: Session timeout during creation
    When session expires while creating Holiday
    Then User should be redirected to login screen

  @regression @p2
  Scenario: Close popup without saving
    When User enters Holiday details
    And User clicks "X"
    Then popup should close without saving data

  @sanity @p3
  Scenario: Reopen popup after close
    When User reopens Add Holiday popup
    Then all fields should be reset

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Add Holiday popup UI
    Then all fields should be aligned properly
    And labels should be clearly visible
    And toggle should be functional
    And Submit button should be enabled only after mandatory fields

  @regression @p2
  Scenario: Verify Date Picker UI
    When User opens Holiday Date picker
    Then calendar should be visible
    And "OK" button should be visible
    And Close "X" button should be visible

  @regression @p2
  Scenario: Verify Holiday Date format
    When User selects a date
    Then date should be displayed in correct format

  @regression @p2
  Scenario: Verify Close button functionality
    When User clicks "X"
    Then popup should be closed
    And User should return to Holidays list screen

  # =========================================================
  # 🔍 FIELD VALIDATION SCENARIOS
  # =========================================================

  @regression @p3
  Scenario Outline: Validate Holiday Name field
    And User selects valid date
    And User clicks Submit button
    Then "<error>" should be displayed

    Examples:
      | input | error                    |
      |       | Holiday Name is required |

  @regression @p3
  Scenario: Validate Holiday Date uniqueness
    When User selects duplicate Holiday Date
    Then duplicate validation should be displayed
