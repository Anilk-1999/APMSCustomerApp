Feature: Update Raw Material including Status Change via Swipe Action

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Raw Materials" feature
    Then verify user navigates to "Raw Materials" list screen
    And User has already created a Raw Material

  # =========================================================
  # 🔍 SEARCH + SWIPE + EDIT FLOW
  # =========================================================

  Scenario: Search and open Edit Raw Material popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Raw Material Name
    And User waits for search results to load
    Then system should display matching Raw Material results
    And User verifies Raw Material appears in list
    When User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    And Raw Material ID should be visible and non-editable
    And Name field should be pre-filled and non-editable
    And UOM field should be pre-filled and non-editable
    And Description field should be editable
    And Status field should be visible and clickable
    And Save button should be visible
    And Close "X" button should be visible

  # =========================================================
  # 🔁 STATUS CHANGE FLOW
  # =========================================================

  Scenario: Change status from Active to Inactive
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And current Raw Material status is Active
    And User clicks on Status button
    Then Status confirmation popup should be displayed
    And "Yes, Change" button should be visible
    When User clicks on "Yes, Change" button
    Then status should be changed to Inactive
    And updated status should be displayed in Edit Raw Material popup

  Scenario: Change status from Inactive to Active
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And current Raw Material status is Inactive
    And User clicks on Status button
    Then Status confirmation popup should be displayed
    When User clicks on "Yes, Change" button
    Then status should be changed to Active

  Scenario: Cancel status change
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And User clicks on Status button
    And Status confirmation popup is displayed
    And User closes popup without confirmation
    Then status should remain unchanged

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Update Description with status change
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And User updates Description
    And User changes Status
    And User clicks Save button
    Then Raw Material should be updated successfully
    And updated Raw Material data should be reflected in Raw Materials list screen

  Scenario: Update only Description
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And User updates Description
    And User clicks Save button
    Then Raw Material should be updated successfully

  Scenario: Clear Description and save update
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And User clears Description field
    And User clicks Save button
    Then Raw Material should be updated successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Attempt to edit Name field
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And User tries to modify Name field
    Then Name field should not be editable

  Scenario: Attempt to edit UOM field
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And User tries to modify UOM field
    Then UOM field should not be editable

  Scenario: Save without changes
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And User clicks Save button without any modification
    Then system should show "No changes detected"

  Scenario: Invalid Description input
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And User enters only spaces in Description field
    And User clicks Save button
    Then validation error should be displayed

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid status toggle clicks
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And User clicks Status button multiple times quickly
    Then only one confirmation popup should be displayed

  Scenario: Rapid confirmation clicks
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And User clicks on Status button
    And User clicks "Yes, Change" multiple times quickly
    Then system should prevent duplicate status updates

  Scenario: Network failure during status change
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And User confirms status change without internet connection
    Then system should display error message

  Scenario: Session timeout during status update
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And session expires during status change
    Then User should be redirected to login screen

  Scenario: Close popup without saving changes
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And User modifies Description
    And User clicks on Close "X" button
    Then popup should be closed without saving updated changes

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Status popup UI
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And User clicks on Status button
    Then confirmation popup should display correct status change message
    And "Yes, Change" button should be visible
    And Close option should be available

  Scenario: Verify status display
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    Then status should be clearly shown as Active or Inactive

  Scenario: Verify non-editable fields
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    Then Name field should be disabled
    And UOM field should be disabled

  Scenario: Verify Edit Raw Material popup UI
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    Then all fields should be visible and aligned properly
    And Save button should be visible
    And Close "X" button should be visible

  # =========================================================
  # 🔍 FIELD VALIDATION SCENARIOS
  # =========================================================

  Scenario Outline: Validate Description field during update
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    And User clicks on Edit button
    And User clicks Save button
    Then "<error>" should be displayed

    Examples:
      | input | error                    |
      |       | No changes detected      |
      |       | Description cleared      |

