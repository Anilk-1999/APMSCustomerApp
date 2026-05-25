@regression @view @p2
Feature: View Product Group Details from Product Groups List

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Product Groups" feature


  # =========================================================
  # SEARCH + VIEW FLOW
  # =========================================================

  @smoke @p1
  Scenario: Search and open View Product Group popup
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Product Groups" feature
    Then verify user navigates to "Product Groups" list screen
    And User has already created a Product Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Group Name
    And User waits for search results to load
    Then system should display matching product group results
    And User verifies product group appears in list

    When User clicks on the product group record
    Then "View Product Group" popup should be displayed

    And Product Group ID should be visible
    And Product Group Name should be visible
    And Product Group Code should be visible
    And Description should be visible
    And Close "X" button should be visible
    And all fields should be displayed in read-only mode
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen


  # =========================================================
  # READ-ONLY VALIDATION
  # =========================================================

  @regression @p1
  Scenario: Verify fields are non-editable in View Product Group popup
    And User has already created a Product Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Group Name
    And User waits for search results to load
    Then system should display matching product group results
    And User verifies product group appears in list
    When User clicks on the product group record
    Then "View Product Group" popup should be displayed
    Then Product Group Name field should not be editable
    And Product Group Code should not be editable
    And Description should not be editable
    And no editable input cursor should be visible
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p1
  Scenario: Verify no Save or Edit buttons in View Product Group popup
    And User has already created a Product Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Group Name
    And User waits for search results to load
    Then system should display matching product group results
    And User verifies product group appears in list
    When User clicks on the product group record
    Then "View Product Group" popup should be displayed
    Then Save button should not be visible
    And Edit button should not be visible
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen


  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @regression @p1
  Scenario: Verify Product Group view data accuracy
    And User has already created a Product Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Group Name
    And User waits for search results to load
    Then system should display matching product group results
    And User verifies product group appears in list
    When User clicks on the product group record
    Then "View Product Group" popup should be displayed
    Then system should display the following fields:
      | Field              |
      | Product Group ID   |
      | Product Group Name |
      | Product Group Code |
      | Description        |
    And all displayed values should match saved Product Group data
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Verify Product Group Code display in View popup
    And User has already created a Product Group
    When User opens Product Group View popup
    Then Product Group Code should be displayed correctly
    And Product Group Code should not be editable
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Verify Product Group Name display in View popup
    And User has already created a Product Group
    When User opens Product Group View popup
    Then Product Group Name should be displayed correctly
    And Product Group Name should not be editable
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Verify Description display in View popup
    And User has already created a Product Group
    When User opens Product Group View popup
    Then Description should be displayed correctly
    And Description should not be editable
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Verify popup close functionality
    And User has already created a Product Group
    When User opens Product Group View popup
    And User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen


  # =========================================================
  # NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p2
  Scenario: Attempt to edit Product Group fields from View popup
    And User has already created a Product Group
    When User opens Product Group View popup
    And User tries to edit Product Group Name
    And User tries to modify field values using keyboard input
    Then field values should remain unchanged
    And all fields should appear disabled or read-only
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @negative @regression @p3
  Scenario: View deleted Product Group
    And User has already created a Product Group
    When Product Group is deleted from backend
    And User searches same Product Group
    Then system should display "Product Group not found"

  @negative @regression @p3
  Scenario: Unauthorized access to view Product Group
    When User without permission tries to view Product Group
    Then system should display "Access Denied"


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p3
  Scenario: Rapid click on Product Group record
    And User has already created a Product Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Group Name
    And User waits for search results to load
    Then system should display matching product group results
    And User verifies product group appears in list
    When User clicks multiple times quickly on Product Group record
    Then only one View Product Group popup should open
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @sanity @p3
  Scenario: Long description handling in View popup
    And User has already created a Product Group
    When Product Group contains large Description
    And User opens Product Group View popup
    Then Description content should be displayed properly
    And User should be able to scroll if required
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @sanity @p3
  Scenario: Reopen View Product Group popup multiple times
    And User has already created a Product Group
    When User opens and closes Product Group View popup multiple times
    Then Product Group details should load correctly each time
    And User should return to Product Groups list screen


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify View Product Group popup UI elements
    And User has already created a Product Group
    When User opens Product Group View popup
    Then "View Product Group" popup should be displayed
    And Product Group ID should be visible
    And Product Group Name should be visible
    And Product Group Code should be visible
    And Description should be visible
    And Close "X" button should be visible
    And all fields should appear disabled or read-only
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Verify View popup layout matches Edit popup structure
    And User has already created a Product Group
    When User opens Product Group View popup
    Then View popup layout should match Edit popup structure
    And all fields should be disabled
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p3
  Scenario: Verify popup overlay behavior
    And User has already created a Product Group
    When User opens Product Group View popup
    Then background screen should remain inaccessible until popup is closed
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen
