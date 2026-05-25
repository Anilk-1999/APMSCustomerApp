@regression @smoke @view @p1
Feature: View Product Setup Type Details from Product Setup Types List

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Product Setup Types" feature


  # =========================================================
  # NAVIGATION — OPEN VIEW PRODUCT SETUP TYPE POPUP
  # =========================================================

  @smoke @p1
  Scenario: Open View Product Setup Type popup by clicking on record
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Product Setup Types" feature
    Then verify user navigates to "Product Setup Types" list screen
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record to view
    Then "View Product Setup Type" popup should be displayed
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Close View popup navigates back without Confirmation Alert
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record to view
    Then "View Product Setup Type" popup should be displayed
    When User clicks Close "X" button on Product Setup Type popup
    Then User should return to Product Setup Types list screen


  # =========================================================
  # MANDATORY FIELD VISIBILITY
  # =========================================================

  @smoke @regression @p1
  Scenario: Verify all fields are visible in View Product Setup Type popup
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record to view
    Then "View Product Setup Type" popup should be displayed
    And Product Setup Type ID should be visible
    And Product Setup Name should be visible in view popup
    And Machine Output Timer should be visible in view popup
    And Product Setup Timer should be visible in view popup
    And Close "X" button should be visible
    And all Product Setup Type fields should be displayed in read-only mode
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen


  # =========================================================
  # READ-ONLY VALIDATION
  # =========================================================

  @regression @p1
  Scenario: Verify fields are non-editable in View Product Setup Type popup
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record to view
    Then "View Product Setup Type" popup should be displayed
    Then Product Setup Name field should not be editable in view popup
    And Description should not be editable in view popup
    And no editable input cursor should be visible in Product Setup Type view popup
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p1
  Scenario: Verify no Save button in View Product Setup Type popup
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record to view
    Then "View Product Setup Type" popup should be displayed
    Then Save button should not be visible in Product Setup Type view popup
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen


  # =========================================================
  # POSITIVE SCENARIOS — DATA ACCURACY
  # =========================================================

  @regression @p1
  Scenario: Verify Product Setup Type view data accuracy
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record to view
    Then "View Product Setup Type" popup should be displayed
    And all displayed values should match saved Product Setup Type data
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify Product Setup Name display in View popup
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record to view
    Then "View Product Setup Type" popup should be displayed
    Then Product Setup Name should be displayed correctly in view popup
    And Product Setup Name field should not be editable in view popup
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify Machine Output Timer display in View popup
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record to view
    Then "View Product Setup Type" popup should be displayed
    Then Machine Output Timer should be visible in view popup
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify Product Setup Timer display in View popup
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record to view
    Then "View Product Setup Type" popup should be displayed
    Then Product Setup Timer should be visible in view popup
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify Description display in View popup
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record to view
    Then "View Product Setup Type" popup should be displayed
    Then Description should be visible in view popup
    And Description should not be editable in view popup
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify popup close functionality
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record to view
    Then "View Product Setup Type" popup should be displayed
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen


  # =========================================================
  # NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p2
  Scenario: Attempt to edit Product Setup Type fields from View popup
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record to view
    Then "View Product Setup Type" popup should be displayed
    When User tries to edit Product Setup Name in view popup
    And User tries to modify field values using keyboard input in Product Setup Type view
    Then Product Setup Type field values should remain unchanged
    And all Product Setup Type fields should appear disabled or read-only
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p3
  Scenario: Rapid click on Product Setup Type record
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks multiple times quickly on Product Setup Type record
    Then only one View Product Setup Type popup should open
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @sanity @p3
  Scenario: Reopen View Product Setup Type popup multiple times
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User opens and closes Product Setup Type View popup multiple times
    Then Product Setup Type details should load correctly each time
    And User should return to Product Setup Types list screen


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify View Product Setup Type popup UI elements
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record to view
    Then "View Product Setup Type" popup should be displayed
    And Product Setup Type ID should be visible
    And Product Setup Name should be visible in view popup
    And Machine Output Timer should be visible in view popup
    And Product Setup Timer should be visible in view popup
    And Close "X" button should be visible
    And all Product Setup Type fields should appear disabled or read-only
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p3
  Scenario: Verify popup overlay behavior
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record to view
    Then "View Product Setup Type" popup should be displayed
    Then background screen should remain inaccessible until Product Setup Type popup is closed
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify Product Setup Type ID format in View popup
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User clicks on the Product Setup Type record to view
    Then "View Product Setup Type" popup should be displayed
    And Product Setup Type ID should be visible
    And Product Setup Type ID should start with hash STA prefix
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen
