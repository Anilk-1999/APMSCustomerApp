@regression @view @p2
Feature: View Spare Details from Spares List

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Spares" feature

  # =========================================================
  # SEARCH + VIEW FLOW
  # =========================================================

  @smoke @p1
  Scenario: Search and open View Spare popup
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
    When User clicks on the Spare record
    Then "View Spare" popup should be displayed
    And Spare ID should be visible
    And Status should be visible and clickable
    And Spare Name should be visible
    And Spare Code should be visible
    And UOM should be visible
    And Current Stock should be visible
    And Description should be visible if exists
    And Close "X" button should be visible
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # READ-ONLY VALIDATION
  # =========================================================

  @regression @p1
  Scenario: Verify all fields are non-editable in View Spare popup
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User clicks on the Spare record
    Then "View Spare" popup should be displayed
    Then Spare Name field should be non-editable in View popup
    And Spare Code field should be non-editable in View popup
    And UOM field should be non-editable in View popup
    And Current Stock field should be non-editable in View popup
    And Description field should be non-editable in View popup
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p1
  Scenario: Verify no Save or Edit buttons in View Spare popup
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User clicks on the Spare record
    Then "View Spare" popup should be displayed
    Then Save button should NOT be visible
    And Edit option should NOT be available
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @regression @p1
  Scenario: Verify Spare data accuracy in View popup
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User clicks on the Spare record
    Then "View Spare" popup should be displayed
    Then Spare Name should match created data
    And Spare Code should match saved data
    And UOM should match saved data
    And Current Stock should match saved data
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Verify View Spare popup close functionality
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User clicks on the Spare record
    Then "View Spare" popup should be displayed
    When User clicks Close "X" button in Spare popup
    Then popup should be closed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Verify Current Stock display format in View popup
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User clicks on the Spare record
    Then "View Spare" popup should be displayed
    Then Current Stock should be displayed in valid numeric format
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p2
  Scenario: Attempt to edit fields in View Spare popup
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User clicks on the Spare record
    Then "View Spare" popup should be displayed
    When User tries to edit Spare Name in View popup
    Then system should not allow modification in View Spare popup
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p3
  Scenario: Rapid click on Spare record
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User clicks Spare record multiple times quickly
    Then only one View Spare popup should open
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify View Spare popup UI
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User clicks on the Spare record
    Then "View Spare" popup should be displayed
    Then all Spare View fields should be aligned properly
    And labels should be clearly visible
    And values should be readable
    And "X" button should be correctly positioned
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Verify all fields are read-only in View Spare popup
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User clicks on the Spare record
    Then "View Spare" popup should be displayed
    Then all Spare fields in View popup should be non-editable
    And no input cursor should appear in any field
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Verify status visibility in View Spare popup
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User clicks on the Spare record
    Then "View Spare" popup should be displayed
    Then status should be clearly visible
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list
