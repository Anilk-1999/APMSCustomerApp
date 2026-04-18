Feature: Update Scrap Item via Swipe Action from Scraps List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Scraps" feature
    Then verify user navigates to "Scraps" list screen
    And User has already created a Scrap Item

  # =========================================================
  # 🔍 SEARCH + SWIPE + EDIT FLOW
  # =========================================================

  Scenario: Search and open Edit Scrap Item popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Scrap Name
    And User waits for search results to load
    Then system should display matching Scrap results
    And User verifies Scrap appears in list
    When User swipes Scrap record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Scrap Item" popup should be displayed
    And Scrap ID should be visible and non-editable
    And Status should be visible and clickable
    And Scrap Name field should be pre-filled and editable
    And Description field should be pre-filled and editable
    And Scrap Type dropdown should be pre-filled and editable
    And Metrics Type field should be pre-filled and non-editable
    And UOM field should be pre-filled and non-editable
    And Save button should be visible
    And Close "X" button should be visible

  # =========================================================
  # 🔁 STATUS CHANGE FLOW
  # =========================================================

  Scenario: Change status from Active to Inactive
    When User opens Edit Scrap Item popup
    And current Scrap status is Active
    And User clicks on Status button
    Then Status confirmation popup should be displayed
    And "Yes, Change" button should be visible
    When User clicks on "Yes, Change" button
    Then status should be changed to Inactive
    And updated status should be reflected in Edit popup

  Scenario: Change status from Inactive to Active
    When User opens Edit Scrap Item popup
    And current Scrap status is Inactive
    And User clicks on Status button
    Then Status confirmation popup should be displayed
    When User clicks on "Yes, Change" button
    Then status should be changed to Active

  Scenario: Cancel status change
    When User opens Edit Scrap Item popup
    And User clicks on Status button
    And User closes confirmation popup without confirmation
    Then status should remain unchanged

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Update Scrap with valid editable fields
    When User opens Edit Scrap Item popup
    And User updates Scrap Name
    And User updates Description
    And User changes Scrap Type
    And User clicks Save button
    Then Scrap should be updated successfully
    And updated Scrap should be reflected in Scraps list screen

  Scenario: Update only Description
    When User opens Edit Scrap Item popup
    And User updates Description
    And User clicks Save button
    Then Scrap should be updated successfully

  Scenario: Update with status change
    When User opens Edit Scrap Item popup
    And User changes Status
    And User clicks Save button
    Then Scrap should be updated successfully

  Scenario: Update Scrap Name with trimmed value
    When User opens Edit Scrap Item popup
    And User enters Scrap Name with leading and trailing spaces
    And User clicks Save button
    Then system should trim spaces and update Scrap successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Attempt to edit Metrics Type
    When User opens Edit Scrap Item popup
    And User tries to modify Metrics Type field
    Then Metrics Type field should not be editable

  Scenario: Attempt to edit UOM
    When User opens Edit Scrap Item popup
    And User tries to modify UOM field
    Then UOM field should not be editable

  Scenario: Invalid Scrap Name input
    When User opens Edit Scrap Item popup
    And User enters invalid Scrap Name (only spaces or special characters)
    And User clicks Save button
    Then validation error should be displayed for Scrap Name

  Scenario: Duplicate Scrap Name
    When User opens Edit Scrap Item popup
    And User enters existing Scrap Name
    And User clicks Save button
    Then duplicate validation error should be displayed

  Scenario: Save without changes
    When User opens Edit Scrap Item popup
    And User clicks Save button without modification
    Then system should show "No changes detected"

  Scenario: Invalid Description input
    When User opens Edit Scrap Item popup
    And User enters only spaces in Description
    And User clicks Save button
    Then validation error should be displayed

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid swipe action
    When User swipes Scrap record multiple times quickly
    Then only one Edit option should be displayed

  Scenario: Rapid Save clicks
    When User opens Edit Scrap Item popup
    And User clicks Save button multiple times quickly
    Then system should prevent duplicate updates

  Scenario: Rapid status toggle
    When User opens Edit Scrap Item popup
    And User clicks Status button multiple times quickly
    Then only one confirmation popup should be displayed

  Scenario: Network failure during update
    When User opens Edit Scrap Item popup
    And User clicks Save button without internet connection
    Then system should display error message

  Scenario: Session timeout during update
    When session expires during Scrap update
    Then User should be redirected to login screen

  Scenario: Close popup without saving changes
    When User opens Edit Scrap Item popup
    And User modifies Scrap details
    And User clicks Close "X" button
    Then popup should be closed without saving changes

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Edit Scrap popup UI
    When User opens Edit Scrap Item popup
    Then Scrap ID should be visible
    And Status should be visible
    And Scrap Name, Description, Scrap Type should be editable
    And Metrics Type and UOM should be disabled
    And Save button should be visible
    And Close "X" button should be visible

  Scenario: Verify non-editable fields behavior
    When User opens Edit Scrap Item popup
    Then Metrics Type field should not allow interaction
    And UOM field should not allow interaction

  Scenario: Verify status popup UI
    When User opens Edit Scrap Item popup
    And User clicks on Status button
    Then confirmation popup should display correct message
    And "Yes, Change" button should be visible

  Scenario: Verify Close button functionality
    When User clicks on "X" button in Edit Scrap popup
    Then popup should be closed successfully

