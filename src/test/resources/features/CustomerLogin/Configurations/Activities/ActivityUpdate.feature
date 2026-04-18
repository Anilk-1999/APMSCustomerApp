Feature: Update Activity via Swipe Action from Activities List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Maintenance" section is displayed
    When User clicks on "Activities" feature
    Then verify user navigates to "Activities" list screen
    And User has already created an Activity

  # =========================================================
  # 🔍 SEARCH + SWIPE + EDIT FLOW
  # =========================================================

  Scenario: Search and open Edit Activity popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Name
    And User waits for search results to load
    Then system should display matching Activity results
    And User verifies Activity appears in list
    When User swipes Activity record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity" popup should be displayed
    And Activity ID should be visible and non-editable
    And Status should be visible and clickable
    And Activity Name field should be pre-filled and editable
    And Description field should be pre-filled and editable
    And "Is Function Applicable" toggle should be visible and editable
    And Save button should be visible
    And Close "X" button should be visible

  # =========================================================
  # 🔁 STATUS CHANGE FLOW
  # =========================================================

  Scenario: Change status from Active to Inactive
    When User opens Edit Activity popup
    And current Activity status is Active
    And User clicks on Status button
    Then Status confirmation popup should be displayed
    And "Yes, Change" button should be visible
    When User clicks on "Yes, Change" button
    Then status should be changed to Inactive
    And updated status should be reflected in Edit popup

  Scenario: Change status from Inactive to Active
    When User opens Edit Activity popup
    And current Activity status is Inactive
    And User clicks on Status button
    Then Status confirmation popup should be displayed
    When User clicks on "Yes, Change" button
    Then status should be changed to Active

  Scenario: Cancel status change
    When User opens Edit Activity popup
    And User clicks on Status button
    And User closes confirmation popup without confirmation
    Then status should remain unchanged

  # =========================================================
  # 🔁 TOGGLE BEHAVIOR (Is Function Applicable)
  # =========================================================

  Scenario: Enable Is Function Applicable toggle
    When User opens Edit Activity popup
    And User turns ON the toggle
    Then toggle state should be ON

  Scenario: Disable Is Function Applicable toggle
    When User opens Edit Activity popup
    And User turns OFF the toggle
    Then toggle state should be OFF

  Scenario: Rapid toggle switching
    When User opens Edit Activity popup
    And User toggles multiple times quickly
    Then final toggle state should be maintained correctly

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Update Activity with all editable fields
    When User opens Edit Activity popup
    And User updates Activity Name
    And User updates Description
    And User updates toggle state
    And User clicks Save button
    Then Activity should be updated successfully
    And updated Activity should be reflected in Activities list screen

  Scenario: Update only Description
    When User opens Edit Activity popup
    And User updates Description
    And User clicks Save button
    Then Activity should be updated successfully

  Scenario: Update only toggle state
    When User opens Edit Activity popup
    And User changes "Is Function Applicable" toggle
    And User clicks Save button
    Then Activity should be updated successfully

  Scenario: Update with status change
    When User opens Edit Activity popup
    And User changes Status
    And User clicks Save button
    Then Activity should be updated successfully

  Scenario: Update Activity Name with trimmed value
    When User opens Edit Activity popup
    And User enters Activity Name with leading and trailing spaces
    And User clicks Save button
    Then system should trim spaces and update Activity successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Update without Activity Name
    When User opens Edit Activity popup
    And User clears Activity Name field
    And User clicks Save button
    Then "Activity Name is required" should be displayed

  Scenario: Invalid Activity Name input
    When User opens Edit Activity popup
    And User enters invalid Activity Name
    And User clicks Save button
    Then validation error should be displayed

  Scenario: Duplicate Activity Name
    When User opens Edit Activity popup
    And User enters existing Activity Name
    And User clicks Save button
    Then duplicate validation error should be displayed

  Scenario: Save without changes
    When User opens Edit Activity popup
    And User clicks Save button without modification
    Then system should show "No changes detected"

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid swipe action
    When User swipes Activity record multiple times quickly
    Then only one Edit option should be displayed

  Scenario: Rapid Save clicks
    When User opens Edit Activity popup
    And User clicks Save button multiple times quickly
    Then system should prevent duplicate updates

  Scenario: Network failure during update
    When User opens Edit Activity popup
    And User clicks Save button without internet connection
    Then system should display error message

  Scenario: Session timeout during update
    When session expires during Activity update
    Then User should be redirected to login screen

  Scenario: Close popup without saving changes
    When User opens Edit Activity popup
    And User modifies Activity details
    And User clicks Close "X" button
    Then popup should be closed without saving changes

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Edit Activity popup UI
    When User opens Edit Activity popup
    Then Activity ID should be visible
    And Status should be visible
    And Activity Name and Description should be editable
    And toggle should be visible and functional
    And Save button should be visible
    And Close "X" button should be visible

  Scenario: Verify status popup UI
    When User opens Edit Activity popup
    And User clicks on Status button
    Then confirmation popup should display correct message
    And "Yes, Change" button should be visible

  Scenario: Verify Close (X) button functionality
    When User clicks on "X" button
    Then popup should be closed successfully

  