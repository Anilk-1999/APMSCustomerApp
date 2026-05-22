@regression @smoke @stockupdate @p1
Feature: Spare Stock Update via Long Press Action Menu

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Spares" feature

  # =========================================================
  # NAVIGATION FLOW — LONG PRESS + ACTION MENU
  # =========================================================

  @smoke @p1
  Scenario: Long press Spare record and open Stock Update popup
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
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    And Stock Update option should be visible in Action Menus
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    And Spare Name should be visible and non-editable in Stock Update popup
    And Part No should be visible and non-editable in Stock Update popup
    And UOM should be visible and non-editable in Stock Update popup
    And Current Stock should be visible and non-editable in Stock Update popup
    And New Update Stock field should be visible and editable
    And Remarks field should be visible and editable
    And Save button should be visible in Stock Update popup
    And Close "X" button should be visible in Stock Update popup
    When User clicks Close "X" button in Stock Update popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # NON-EDITABLE FIELDS VALIDATION
  # =========================================================

  @regression @p1
  Scenario: Verify non-editable fields in Stock Update popup
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    Then Spare Name should be non-editable in Stock Update popup
    And Part No should be non-editable in Stock Update popup
    And UOM should be non-editable in Stock Update popup
    And Current Stock should be non-editable in Stock Update popup
    When User clicks Close "X" button in Stock Update popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Update stock with valid New Update Stock and Remarks
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    When User enters valid New Update Stock value
    And User enters Remarks
    And User clicks Save button in Stock Update popup
    Then Stock Update should be saved successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p1
  Scenario: Update stock with decimal New Update Stock value
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    When User enters New Update Stock as "12345.67"
    And User enters Remarks
    And User clicks Save button in Stock Update popup
    Then Stock Update should be saved successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Update stock with minimum New Update Stock value
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    When User enters New Update Stock as "0"
    And User enters Remarks
    And User clicks Save button in Stock Update popup
    Then Stock Update should be saved successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Update stock with maximum boundary New Update Stock value
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    When User enters New Update Stock as "999999.99"
    And User enters Remarks
    And User clicks Save button in Stock Update popup
    Then Stock Update should be saved successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Verify Current Stock is updated after Stock Update
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    When User enters valid New Update Stock value
    And User enters Remarks
    And User clicks Save button in Stock Update popup
    Then Stock Update should be saved successfully
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User clicks on the Spare record
    Then "View Spare" popup should be displayed
    Then Current Stock should reflect updated stock value
    When User clicks Close "X" button in Spare popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p1
  Scenario: Save Stock Update without New Update Stock value
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    When User leaves New Update Stock empty
    And User enters Remarks
    And User clicks Save button in Stock Update popup
    Then "This field is required" error should be displayed
    When User clicks Close "X" button in Stock Update popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @negative @regression @p1
  Scenario: Save Stock Update without Remarks
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    When User enters valid New Update Stock value
    And User leaves Remarks empty
    And User clicks Save button in Stock Update popup
    Then "This field is required" error should be displayed
    When User clicks Close "X" button in Stock Update popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @negative @regression @p1
  Scenario: Save Stock Update with both fields empty
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    When User leaves New Update Stock empty
    And User leaves Remarks empty
    And User clicks Save button in Stock Update popup
    Then "This field is required" error should be displayed
    When User clicks Close "X" button in Stock Update popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @negative @regression @p2
  Scenario: New Update Stock exceeds 6 digits before decimal
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    When User enters New Update Stock as "1000000"
    Then stock validation error should be displayed below New Update Stock field
    When User clicks Close "X" button in Stock Update popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @negative @regression @p2
  Scenario: New Update Stock with more than 2 decimal places
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    When User enters New Update Stock as "123.456"
    Then stock validation error should be displayed below New Update Stock field
    When User clicks Close "X" button in Stock Update popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @negative @regression @p2
  Scenario: Remarks contains only spaces
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    When User enters valid New Update Stock value
    And User enters only spaces in Remarks
    And User clicks Save button in Stock Update popup
    Then "This field is required" error should be displayed
    When User clicks Close "X" button in Stock Update popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Rapid Save clicks in Stock Update popup
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    When User enters valid New Update Stock value
    And User enters Remarks
    When User clicks Save button multiple times quickly in Stock Update popup
    Then system should prevent duplicate Stock Update
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Close Stock Update popup without saving
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    When User enters valid New Update Stock value
    And User enters Remarks
    When User clicks Close "X" button in Stock Update popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Stock Update popup should be closed without saving
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Stock Update popup UI
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    Then all Stock Update fields should be aligned properly
    And non-editable fields should be visually disabled
    And New Update Stock field should be enabled and editable
    And Remarks field should be enabled and editable
    And Save button should be visible in Stock Update popup
    And "X" button should be correctly positioned
    When User clicks Close "X" button in Stock Update popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list

  @regression @p2
  Scenario: Verify Action Menus bottom sheet UI
    When User has already created a Spare
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Spare Name
    And User waits for search results to load
    Then system should display matching Spare records
    And User verifies Spare appears in list
    When User long presses on Spare record
    Then Action Menus bottom sheet should be displayed
    And Stock Update option should be visible in Action Menus
    And labels should be clearly visible
    When User clicks on Stock Update option
    Then "Spare Stock Update" popup should be displayed
    When User clicks Close "X" button in Stock Update popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Spares" list
