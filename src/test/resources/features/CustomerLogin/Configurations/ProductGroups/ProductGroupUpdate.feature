@regression @smoke @update @p1
Feature: Update Product Group via Swipe Action from Product Group List

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Product Groups" feature


  # =========================================================
  # SEARCH + SWIPE + EDIT FLOW
  # =========================================================

  @smoke @p1
  Scenario: Search and open Edit Product Group popup
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

    When User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed

    And Product Group ID should be visible and non-editable
    And Product Group Name field should be pre-filled
    And Product Group Code field should be pre-filled
    And Description field should be pre-filled
    And Product Group Name field should be editable
    And Product Group Code field should be editable
    And Description field should be editable
    And Save button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen


  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Update Product Group with all fields
    And User has already created a Product Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Group Name
    And User waits for search results to load
    Then system should display matching product group results
    And User verifies product group appears in list
    When User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    When User updates Product Group Name with valid value
    And User updates Product Group Code with valid value
    And User updates Description
    And User clicks Save button
    Then Product Group should be updated successfully
    And updated data should be reflected in Product Groups list screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Update only Product Group Name
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    When User updates only Product Group Name
    And User clicks Save button
    Then Product Group should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Update only Product Group Code
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    When User updates only Product Group Code with valid value
    And User clicks Save button
    Then Product Group should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Update only Description
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    When User updates only Description
    And User clicks Save button
    Then Product Group should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Update Product Group Name with trimmed value
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    When User enters Product Group Name with leading and trailing spaces
    And User clicks Save button
    Then Product Group should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen


  # =========================================================
  # NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p1
  Scenario: Update Product Group without Product Group Name
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    When User clears Product Group Name field
    And User clicks Save button
    Then "This field is required" error should be displayed for Product Group Name
    When User clicks Close "X" button
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @negative @regression @p1
  Scenario: Update Product Group without Product Group Code
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    When User clears Product Group Code field
    And User clicks Save button
    Then "This field is required" error should be displayed for Product Group Code
    When User clicks Close "X" button
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Update Product Group with only spaces in Product Group Name
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    When User enters only spaces in Product Group Name field
    And User clicks Save button
    Then "This field is required" error should be displayed for Product Group Name
    When User clicks Close "X" button
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Update Product Group with duplicate Product Group Name
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    When User enters existing Product Group Name
    And User clicks Save button
    Then Product Group should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Update Product Group with duplicate Product Group Code
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    When User enters existing Product Group Code
    And User clicks Save button
    Then Product Group should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Update Product Group with special characters
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    When User enters only special characters in Product Group Name and Product Group Code
    And User clicks Save button
    Then Product Group should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Save without any changes
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    When User clicks Save button without modification
    Then Product Group should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p3
  Scenario: Rapid swipe action multiple times
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User performs swipe action multiple times quickly on product group record
    Then only one Edit option should be displayed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @sanity @p3
  Scenario: Rapid multiple Save clicks
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    And User updates Product Group Name with valid value
    And User clicks Save button multiple times quickly
    Then Product Group should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Close popup without saving updated changes
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    When User modifies Product Group details
    And User clicks Close "X" button
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Edit Product Group popup UI elements
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    And Product Group ID should be visible
    And Product Group Name field should be editable
    And Product Group Code field should be editable
    And Description field should be editable
    And Save button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Verify non-editable fields in Edit Product Group popup
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    Then Product Group ID should not be editable
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Verify pre-filled data accuracy
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    Then all fields should display previously saved data correctly
    When User clicks Close "X" button
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Validate Description field during update
    And User has already created a Product Group
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    When User enters long description text
    Then system should allow optional input
    When User clicks Close "X" button
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Groups list screen
