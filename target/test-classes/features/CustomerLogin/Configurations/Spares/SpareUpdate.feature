Feature: Update Spare via Swipe Action with Non-Editable Current Stock

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Maintenance" section is displayed
    When User clicks on "Spares" feature
    Then verify user navigates to "Spares" list screen
    And User has already created a Spare

  # =========================================================
  # 🔍 SEARCH + SWIPE + EDIT FLOW
  # =========================================================

  Scenario: Search and open Edit Spare popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list

    When User swipes Spare record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Spare" popup should be displayed

    # Field validations
    And Spare ID should be visible and non-editable
    And Status should be visible and clickable
    And Spare Name should be pre-filled and editable
    And Spare Code should be pre-filled and editable
    And UOM should be pre-filled and editable
    And Current Stock should be visible and non-editable
    And Description should be pre-filled and editable
    And Save button should be visible
    And Close "X" button should be visible

  # =========================================================
  # 🔒 CURRENT STOCK VALIDATION (CRITICAL)
  # =========================================================

  Scenario: Verify Current Stock is non-editable
    When User opens Edit Spare popup
    Then Current Stock field should be disabled
    And User should not be able to modify Current Stock

  Scenario: Attempt to edit Current Stock
    When User tries to change Current Stock value
    Then system should block modification
    And original value should remain unchanged

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Update Spare with all editable fields
    When User updates Spare Name
    And User updates Spare Code
    And User updates UOM
    And User updates Description
    And User clicks Save button
    Then Spare should be updated successfully
    And updated data should be reflected in list

  Scenario: Update only Spare Name
    When User updates Spare Name
    And clicks Save button
    Then Spare should be updated successfully

  Scenario: Update only Description
    When User updates Description
    And clicks Save button
    Then Spare should be updated successfully

  Scenario: Update without changing Current Stock
    When User updates other fields
    And clicks Save button
    Then Current Stock should remain unchanged

  Scenario: Update with trimmed inputs
    When User enters values with leading/trailing spaces
    And clicks Save button
    Then system should trim values and update successfully

  # =========================================================
  # 🔁 STATUS FLOW
  # =========================================================

  Scenario: Change status from Active to Inactive
    When current status is Active
    And User clicks on Status button
    Then confirmation popup should be displayed
    When User clicks "Yes, Change"
    Then status should be updated to Inactive

  Scenario: Change status from Inactive to Active
    When current status is Inactive
    And User clicks Status button
    And confirms change
    Then status should be updated to Active

  Scenario: Cancel status change
    When User clicks Status button
    And closes popup without confirmation
    Then status should remain unchanged

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Update without Spare Name
    When User clears Spare Name
    And clicks Save button
    Then "Spare Name is required" error should be displayed

  Scenario: Update without Spare Code
    When User clears Spare Code
    And clicks Save button
    Then "Spare Code is required" error should be displayed

  Scenario: Update without UOM
    When User removes UOM selection
    And clicks Save button
    Then "UOM is required" error should be displayed

  Scenario: Duplicate Spare Code
    When User enters existing Spare Code
    And clicks Save button
    Then duplicate validation error should be displayed

  Scenario: Invalid input in fields
    When User enters invalid characters
    Then validation error should be displayed

  Scenario: Save without changes
    When User clicks Save button without modification
    Then system should show "No changes detected"

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid Save clicks
    When User clicks Save button multiple times quickly
    Then system should prevent duplicate updates

  Scenario: Rapid swipe action
    When User swipes multiple times quickly
    Then only one Edit option should be visible

  Scenario: Network failure during update
    When User clicks Save without internet
    Then system should display error message

  Scenario: Session timeout during update
    When session expires during editing
    Then User should be redirected to login screen

  Scenario: Close popup without saving
    When User modifies data and clicks "X"
    Then changes should not be saved

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Edit Spare popup UI
    When User opens Edit Spare popup
    Then all fields should be aligned properly
    And editable fields should be enabled
    And Current Stock should be disabled
    And Save button should be visible

  Scenario: Verify Close (X) button
    When User clicks "X"
    Then popup should be closed

  Scenario: Verify pre-filled data accuracy
    Then all fields should display correct existing values

  