@regression @smoke @update @p1
Feature: Update Raw Material including Status Change via Swipe Action

  # Background navigates to Raw Materials list before every scenario.
  # CommonNavigationSteps short-circuits navigation when the list screen is already visible.

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Raw Materials" feature


  # =========================================================
  # SEARCH + SWIPE + EDIT FLOW
  # =========================================================

  @smoke @p1
  Scenario: Search and open Edit Raw Material popup and verify all fields
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Raw Materials" feature
    Then verify user navigates to "Raw Materials" list screen
    And User has already created a Raw Material
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
    And Description field should be pre-filled and editable
    And Status should be visible and clickable in Edit Raw Material popup
    And Save button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen


  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Update only Description
    And User has already created a Raw Material
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
    When User updates Description
    And User clicks Save button
    Then Raw Material should be updated successfully
    And updated Raw Material data should be reflected in Raw Materials list screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Clear Description and save
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    When User clears Description field
    And User clicks Save button
    Then Raw Material should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Update Description with trimmed spaces
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    When User enters Description with leading and trailing spaces
    And User clicks Save button
    Then Raw Material should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Save Edit Raw Material without making any changes
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    When User clicks Save button without modification
    Then Raw Material should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen


  # =========================================================
  # STATUS CHANGE FLOW
  # =========================================================

  @smoke @regression @p1
  Scenario: Change Raw Material status from Active to Inactive
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    And current Raw Material status is Active
    When User clicks on Status button
    Then Status confirmation popup should be displayed
    And "Yes, Change" button should be visible
    When User clicks on "Yes, Change" button
    Then status should be changed to Inactive
    And updated status should be displayed in Edit Raw Material popup
    When User clicks Save button
    Then Raw Material should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Change Raw Material status from Inactive to Active
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    And current Raw Material status is Inactive
    When User clicks on Status button
    Then Status confirmation popup should be displayed
    When User clicks on "Yes, Change" button
    Then status should be changed to Active
    And updated status should be displayed in Edit Raw Material popup
    When User clicks Save button
    Then Raw Material should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Cancel status change
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    When User clicks on Status button
    Then Status confirmation popup should be displayed
    When User closes Status confirmation popup without confirming
    Then status should remain unchanged
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Update Description and change Status together
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    When User updates Description
    And User clicks on Status button
    And User clicks on "Yes, Change" button
    And User clicks Save button
    Then Raw Material should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen


  # =========================================================
  # NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p2
  Scenario: Attempt to edit Name field in Edit Raw Material popup
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    When User tries to modify Name field
    Then Name field should not be editable
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @negative @regression @p2
  Scenario: Attempt to edit UOM field in Edit Raw Material popup
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    When User tries to modify UOM field
    Then UOM field should not be editable
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @negative @regression @p2
  Scenario: Attempt to edit Raw Material ID
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    Then Raw Material ID should not be editable
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Close Edit Raw Material popup with unsaved changes shows Confirmation Alert
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    When User updates Description
    And User clicks Close "X" button
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then changes should not be saved in Raw Material
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @sanity @p3
  Scenario: Rapid Save clicks should prevent duplicate Raw Material update
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    When User updates Description
    And User clicks Save button multiple times quickly
    Then system should prevent duplicate Raw Material update
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @sanity @p3
  Scenario: Rapid swipe right-to-left on Raw Material record
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User performs swipe action multiple times quickly on Raw Material record
    Then only one Edit option should be displayed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p3
  Scenario: Rapid status toggle clicks
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    When User clicks Status button multiple times quickly
    Then only one Status confirmation popup should be displayed
    When User closes Status confirmation popup without confirming
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p3
  Scenario: Rapid confirmation clicks on status change
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    When User clicks on Status button
    And User clicks "Yes, Change" button multiple times quickly
    Then system should prevent duplicate status updates
    When User clicks Save button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Edit Raw Material popup UI elements and alignment
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    And all Edit Raw Material fields should be aligned properly
    And Raw Material ID should be visible
    And Name field should be visible and non-editable
    And UOM field should be visible and non-editable
    And Description field should be visible and editable
    And Status should be visible and clickable in Edit Raw Material popup
    And Save button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Verify non-editable fields in Edit Raw Material popup
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    Then Name field should be disabled
    And UOM field should be disabled
    And Raw Material ID should not be editable
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Verify pre-filled data accuracy in Edit Raw Material popup
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    Then all fields should display previously saved Raw Material data correctly
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Verify Status confirmation popup UI
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    When User clicks on Status button
    Then Status confirmation popup should be displayed
    And confirmation popup should display correct status change message
    And "Yes, Change" button should be visible
    And Close option should be available in Status confirmation popup
    When User closes Status confirmation popup without confirming
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen

  @regression @p2
  Scenario: Validate Description field during update
    And User has already created a Raw Material
    When User searches for newly created Raw Material Name
    And User swipes Raw Material record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Raw Material" popup should be displayed
    When User enters long description text
    Then system should allow optional Description input
    When User clicks Close "X" button
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Raw Materials list screen
