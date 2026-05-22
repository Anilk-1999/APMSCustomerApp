@regression @smoke @update @p1
Feature: Update Spare via Swipe Action with Non-Editable Current Stock

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Spares" feature

  # =========================================================
  # SEARCH + SWIPE + EDIT FLOW
  # =========================================================

  @smoke @p1
  Scenario: Search and open Edit Spare popup
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Maintenance" section is displayed
    When User clicks on "Spares" feature
    Then verify user navigates to "Spares" list screen
    And User has already created a Spare
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
    And Spare ID should be visible and non-editable
    And Status should be visible and clickable
    And Spare Name should be pre-filled and editable
    And Spare Code should be pre-filled and editable
    And UOM should be pre-filled and editable
    And Current Stock should be visible and non-editable
    And Description should be pre-filled and editable
    And Save button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # CURRENT STOCK READ-ONLY VALIDATION
  # =========================================================

  @regression @p1
  Scenario: Verify Current Stock is non-editable in Edit popup
    When User has already created a Spare
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
    Then Current Stock field should be disabled in Edit Spare popup
    And User should not be able to modify Current Stock value
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Update Spare with all editable fields
    When User has already created a Spare
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
    When User updates Spare Name
    And User updates Spare Code
    And User updates UOM
    And User updates Description
    And User clicks Save button
    Then Spare should be updated successfully
    And updated Spare should be reflected in list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p1
  Scenario: Update only Spare Name
    When User has already created a Spare
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
    When User updates Spare Name
    And User clicks Save button
    Then Spare should be updated successfully
    And updated Spare should be reflected in list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Update only Description
    When User has already created a Spare
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
    When User updates Description
    And User clicks Save button
    Then Spare should be updated successfully
    And updated Spare should be reflected in list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Update Spare and verify Current Stock remains unchanged
    When User has already created a Spare
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
    Then Current Stock should be visible and non-editable
    When User updates Spare Name
    And User clicks Save button
    Then Spare should be updated successfully
    And updated Spare should be reflected in list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Update Spare with trimmed inputs
    When User has already created a Spare
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
    When User enters Spare Name with leading and trailing spaces
    And User clicks Save button
    Then system should trim spaces and update Spare successfully
    And updated Spare should be reflected in list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # STATUS FLOW
  # =========================================================

  @regression @p1
  Scenario: Change status from Active to Inactive in Edit Spare popup
    When User has already created a Spare
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
    When User clicks on Status button
    Then Status confirmation popup should be displayed
    And "Yes, Change" button should be visible
    When User clicks "Yes, Change"
    Then status should be changed to Inactive
    And updated status should be displayed in Spare Edit popup
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p1
  Scenario: Change status from Inactive to Active in Edit Spare popup
    When User has already created a Spare
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
    When User clicks on Status button
    Then Status confirmation popup should be displayed
    When User clicks "Yes, Change"
    Then status should be changed to Inactive
    When User clicks on Status button
    Then Status confirmation popup should be displayed
    When User clicks "Yes, Change"
    Then status should be changed to Active
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Cancel status change in Edit Spare popup
    When User has already created a Spare
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
    When User clicks on Status button
    Then Status confirmation popup should be displayed
    When User clicks on "X" button
    Then status should remain unchanged in Spare Edit popup
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list


  @regression @p1
  Scenario: update the spare with inactive status
    When User has already created a Spare
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
    When User clicks on Status button
    Then Status confirmation popup should be displayed
    And "Yes, Change" button should be visible
    When User clicks "Yes, Change"
    Then status should be changed to Inactive
    When User clicks Save button
    Then Spare should be updated successfully
    And updated Spare should be reflected in list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list


  # =========================================================
  # NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p1
  Scenario: Update Spare without Spare Name
    When User has already created a Spare
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
    When User clears Spare Name field
    And User clicks Save button
    Then "This field is required" error should be displayed
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @negative @regression @p1
  Scenario: Update Spare without Spare Code
    When User has already created a Spare
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
    When User clears Spare Code field
    And User clicks Save button
    Then "This field is required" error should be displayed
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @negative @regression @p2
  Scenario: Update Spare with only spaces in Spare Name
    When User has already created a Spare
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
    When User enters only spaces in Spare Name
    And User clicks Save button
    Then "This field is required" error should be displayed
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Rapid Save clicks in Edit Spare popup
    When User has already created a Spare
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
    When User updates Spare Name
    When User clicks Save button multiple times quickly in Spare popup
    Then system should prevent duplicate Spare update
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @sanity @p2
  Scenario: Rapid swipe action on Spare record
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User swipes Spare record from right to left multiple times quickly
    Then only one Edit option should be visible
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Close Edit Spare popup without saving
    When User has already created a Spare
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
    When User updates Spare Name
    When User clicks Close "X" button in Spare popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then changes should not be saved in Spare
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Edit Spare popup UI
    When User has already created a Spare
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
    Then all Spare Edit fields should be aligned properly
    And editable Spare fields should be enabled
    And Current Stock should be disabled in Edit Spare popup
    And Save button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Verify pre-filled data accuracy in Edit Spare popup
    When User has already created a Spare
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
    Then all pre-filled Spare fields should display correct existing values
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list


