Feature: Update Holiday via Swipe Action from Holidays List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Unit" section is displayed
    When User clicks on "Holidays" feature
    Then verify user navigates to "Holidays" list screen
    And User has already created a Holiday

  # =========================================================
  # 🔍 SEARCH + SWIPE + EDIT FLOW
  # =========================================================

  Scenario: Search and open Edit Holiday popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Holiday Name
    And User waits for search results to load
    Then system should display matching Holiday results
    And User verifies Holiday appears in list
    When User swipes Holiday record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Holiday" popup should be displayed
    And Holiday ID should be visible and non-editable
    And Holiday Name field should be pre-filled and editable
    And Holiday Date field should be pre-filled and editable
    And "Is National Holiday" label with toggle button should be visible and editable
    And Save button should be visible
    And Close "X" button should be visible

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Update Holiday with all editable fields
    When User opens Edit Holiday popup
    And User updates Holiday Name
    And User updates Holiday Date with a valid unique date
    And User enables "Is National Holiday" toggle
    And User clicks Save button
    Then Holiday should be updated successfully
    And updated Holiday should be visible in Holidays list screen

  Scenario: Update Holiday Name only
    When User opens Edit Holiday popup
    And User updates Holiday Name
    And User clicks Save button
    Then Holiday should be updated successfully

  Scenario: Update Holiday Date only
    When User opens Edit Holiday popup
    And User updates Holiday Date with a valid unique date
    And User clicks Save button
    Then Holiday should be updated successfully

  Scenario: Update National Holiday toggle only
    When User opens Edit Holiday popup
    And User changes "Is National Holiday" toggle state
    And User clicks Save button
    Then Holiday should be updated successfully

  Scenario: Update Holiday with trimmed Holiday Name
    When User opens Edit Holiday popup
    And User enters Holiday Name with leading and trailing spaces
    And User clicks Save button
    Then system should trim spaces and update Holiday successfully

  # =========================================================
  # 📅 DATE PICKER FLOW
  # =========================================================

  Scenario: Update Holiday Date using date picker
    When User opens Edit Holiday popup
    And User clicks on Holiday Date field
    Then Select Date popup should be displayed
    And calendar should be visible
    And "OK" button should be visible
    And Close "X" button should be visible in date popup
    When User selects a valid unique Holiday Date
    And User clicks "OK"
    Then selected date should be updated in Holiday Date field

  Scenario: Close date picker without changing date
    When User opens Edit Holiday popup
    And User clicks on Holiday Date field
    And User closes date popup using "X"
    Then previously selected Holiday Date should remain unchanged

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Update Holiday without Holiday Name
    When User opens Edit Holiday popup
    And User clears Holiday Name
    And User clicks Save button
    Then "Holiday Name is required" should be displayed

  Scenario: Update Holiday without Holiday Date
    When User opens Edit Holiday popup
    And User clears Holiday Date
    And User clicks Save button
    Then "Holiday Date is required" should be displayed

  Scenario: Update Holiday with duplicate Holiday Date
    When User opens Edit Holiday popup
    And User selects an already existing Holiday Date
    And User clicks Save button
    Then "Holiday Date already exists" should be displayed

  Scenario: Update Holiday with only spaces in Holiday Name
    When User opens Edit Holiday popup
    And User enters only spaces in Holiday Name
    And User clicks Save button
    Then "Holiday Name is required" should be displayed

  Scenario: Update Holiday with invalid Holiday Name
    When User opens Edit Holiday popup
    And User enters only special characters in Holiday Name
    And User clicks Save button
    Then validation error should be displayed for Holiday Name

  Scenario: Save without changes
    When User opens Edit Holiday popup
    And User clicks Save button without modification
    Then system should show "No changes detected"

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid multiple Save clicks
    When User opens Edit Holiday popup
    And User clicks Save button multiple times quickly
    Then system should prevent duplicate Holiday updates

  Scenario: Rapid toggle clicks
    When User opens Edit Holiday popup
    And User clicks "Is National Holiday" toggle multiple times quickly
    Then system should maintain the final toggle state correctly

  Scenario: Rapid date field clicks
    When User opens Edit Holiday popup
    And User clicks Holiday Date field multiple times quickly
    Then only one date picker popup should be displayed

  Scenario: Network failure during update
    When User opens Edit Holiday popup
    And User clicks Save button without internet connection
    Then system should display error message

  Scenario: Session timeout during update
    When session expires while editing Holiday
    Then User should be redirected to login screen

  Scenario: Close popup without saving changes
    When User opens Edit Holiday popup
    And User modifies Holiday details
    And User clicks on Close "X" button
    Then popup should be closed without saving changes

  Scenario: Reopen popup after close without save
    When User opens Edit Holiday popup
    And User modifies Holiday details
    And User clicks on Close "X" button
    And User reopens Edit Holiday popup
    Then previously saved Holiday data should be displayed

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Edit Holiday popup UI
    When User opens Edit Holiday popup
    Then all fields should be properly aligned
    And labels should be clearly visible
    And Holiday Name field should be editable
    And Holiday Date field should be editable
    And "Is National Holiday" toggle should be visible and functional
    And Save button should be visible
    And Close "X" button should be visible

  Scenario: Verify Holiday Date picker UI from edit popup
    When User opens Edit Holiday popup
    And User clicks on Holiday Date field
    Then Select Date popup should be displayed
    And calendar should be visible
    And "OK" button should be visible
    And Close "X" button should be visible in date popup

  Scenario: Verify pre-filled data accuracy
    When User opens Edit Holiday popup
    Then Holiday Name should display saved value
    And Holiday Date should display saved value
    And National Holiday toggle should reflect saved state

  Scenario: Verify Close button functionality
    When User clicks on Close "X" button in Edit Holiday popup
    Then popup should be dismissed successfully
    And User should return to Holidays list screen

  # =========================================================
  # 🔍 FIELD VALIDATION SCENARIOS
  # =========================================================

  Scenario Outline: Validate Holiday Name field during update
    When User opens Edit Holiday popup
    And User selects a valid unique Holiday Date
    And User clicks Save button
    Then "<error>" should be displayed

    Examples:
      | input | error                    |
      |       | Holiday Name is required |
     

  Scenario: Validate Holiday Date uniqueness during update
    When User opens Edit Holiday popup
    And User selects duplicate Holiday Date
    And User clicks Save button
    Then duplicate date validation should be displayed

  