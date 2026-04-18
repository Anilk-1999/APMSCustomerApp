Feature: Update Machine Group via Swipe Action from Machine Group List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Machine" section is displayed
    When User clicks on "Machine Groups" feature
    Then verify user navigates to "Machine Groups" list screen
    And User has already created a Machine Group

  # =========================================================
  # 🔍 SEARCH + SWIPE FLOW
  # =========================================================

  Scenario: Search and open Edit Machine Group popup using swipe action
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Machine Group Name
    And User waits for search results to load
    Then system should display matching machine group results
    And User verifies machine group appears in list
    When User swipes machine group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Machine Group" popup should be displayed
    And Machine Group ID should be visible and non-editable
    And Status field should be visible and non-editable
    And Machine Group Name field should be pre-filled
    And Description field should be pre-filled
    And selected machines should be displayed in popup

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Update Machine Group with valid data
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User updates Machine Group Name with valid value
    And User updates Description
    And User clicks Add Machine "+" icon
    Then Select Machines bottom sheet should be displayed
    When User selects additional machines
    And User clicks Submit button in machine selection bottom sheet
    Then selected machines should be updated in popup
    When User clicks Submit button in Edit Machine Group popup
    Then Machine Group should be updated successfully
    And updated data should be reflected in Machine Groups list screen

  Scenario: Update Machine Group without changing machines
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User updates only Machine Group Name
    And User clicks Submit button in Edit Machine Group popup
    Then Machine Group should be updated successfully

  Scenario: Update Machine Group by modifying machine selection
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User clicks Add Machine "+" icon
    And User deselects existing machines
    And User selects new machines
    And User clicks Submit button in machine selection bottom sheet
    And User clicks Submit button in Edit Machine Group popup
    Then machine mapping should be updated successfully

  Scenario: Update Machine Group description only
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User updates only Description
    And User clicks Submit button in Edit Machine Group popup
    Then Machine Group should be updated successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Update Machine Group without Group Name
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User clears Machine Group Name field
    And User clicks Submit button in Edit Machine Group popup
    Then "Machine Group Name is required" should be displayed

  Scenario: Update Machine Group without machines
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User removes all selected machines
    And User clicks Submit button in Edit Machine Group popup
    Then "At least one machine must be selected" should be displayed

  Scenario: Duplicate Machine Group Name during update
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User enters existing Machine Group Name
    And User clicks Submit button in Edit Machine Group popup
    Then "Machine Group already exists" should be displayed

  Scenario: Submit without any changes
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User clicks Submit button without making changes
    Then system should display "No changes detected"

  Scenario: Update Machine Group with only spaces in Group Name
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User enters only spaces in Machine Group Name field
    And User clicks Submit button in Edit Machine Group popup
    Then "Machine Group Name is required" should be displayed

  Scenario: Update Machine Group with special characters only in Group Name
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User enters only special characters in Machine Group Name field
    And User clicks Submit button in Edit Machine Group popup
    Then validation error should be displayed for Machine Group Name field

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid swipe action multiple times
    When User searches for newly created Machine Group Name
    And User performs swipe action multiple times quickly on machine group record
    Then system should display only one Edit option

  Scenario: Rapid multiple submit clicks
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User updates Machine Group Name with valid value
    And User clicks Submit button multiple times quickly
    Then system should prevent duplicate update requests

  Scenario: Large machine list handling in bottom sheet
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User opens machine selection bottom sheet
    Then system should handle large machine list with scroll support

  Scenario: Network failure during update
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User clicks Submit button without internet connection
    Then system should display network error message

  Scenario: Session timeout during update
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And session expires while editing Machine Group
    Then User should be redirected to login screen

  Scenario: Reopen edit popup after cancel
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User closes Edit Machine Group popup
    And User reopens Edit Machine Group popup
    Then previously saved Machine Group details should be displayed correctly

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Edit Machine Group popup UI
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    Then Machine Group ID should be visible
    And Status field should be visible
    And Machine Group Name field should be editable
    And Description field should be editable
    And Add Machine "+" icon should be visible
    And Submit button should be visible

  Scenario: Verify non-editable fields
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    Then Machine Group ID should not be editable
    And Status field should not be editable

  Scenario: Verify selected machines display
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    Then selected machines should be displayed in popup

  Scenario: Verify machine selection bottom sheet UI
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User clicks Add Machine "+" icon
    Then machine selection bottom sheet should appear
    And machines should have selectable checkboxes

  Scenario: Verify popup close behavior
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User closes Edit Machine Group popup
    Then Edit Machine Group popup should be dismissed successfully

  # =========================================================
  # 🔍 FIELD VALIDATION SCENARIOS
  # =========================================================

  Scenario Outline: Validate Machine Group Name field during update
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User clicks Submit button in Edit Machine Group popup
    Then "<error>" should be displayed

    Examples:
      | input | error                                |
      |       | Machine Group Name is required       |


  Scenario: Validate maximum character limit for Machine Group Name during update
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User enters value greater than allowed limit in Machine Group Name field
    Then system should restrict additional characters or display validation error

  Scenario: Validate Description field during update
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User enters large description text
    Then system should accept optional Description input

  # =========================================================
  # 🔁 MACHINE SELECTION SCENARIOS
  # =========================================================

  Scenario Outline: Validate machine selection update behavior
    When User searches for newly created Machine Group Name
    And User swipes machine group record from right to left
    And User clicks on Edit button
    And User opens machine selection bottom sheet
    And User performs "<action>" on machines
    Then system should handle machine selection correctly

    Examples:
      | action          |
      | add machines    |
      | remove machines |
      | replace machines|
      | no selection    |