@regression @smoke @update @p1
Feature: Update Product Setup Type via Swipe Action from Product Setup Types List

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Product Setup Types" feature


  # =========================================================
  # SEARCH + SWIPE + EDIT FLOW
  # =========================================================

  @smoke @p1
  Scenario: Search and open Edit Product Setup Type popup and verify all fields
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
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    And Product Setup Type ID should be visible and non-editable
    And Product Setup Name field should be pre-filled
    And Description field should be pre-filled for Product Setup Type
    And Machine Output Timer field should be pre-filled
    And Product Setup Timer field should be pre-filled
    And Save button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen


  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Update Product Setup Type with all fields
    And User has already created a Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User updates Product Setup Name with valid value
    And User updates Description for Product Setup Type
    And User updates Machine Output Timer to 1 hours 0 minutes 0 seconds
    And User updates Product Setup Timer to 2 hours 0 minutes 0 seconds
    And User clicks Save button on Product Setup Type popup
    Then Product Setup Type should be updated successfully
    And updated data should be reflected in Product Setup Types list screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Update only Product Setup Name
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User updates only Product Setup Name
    And User clicks Save button on Product Setup Type popup
    Then Product Setup Type should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Update only Description
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User updates only Description for Product Setup Type
    And User clicks Save button on Product Setup Type popup
    Then Product Setup Type should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Update only Machine Output Timer
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User updates Machine Output Timer to 0 hours 45 minutes 0 seconds
    And User clicks Save button on Product Setup Type popup
    Then Product Setup Type should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Update only Product Setup Timer
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User updates Product Setup Timer to 1 hours 30 minutes 0 seconds
    And User clicks Save button on Product Setup Type popup
    Then Product Setup Type should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Update Product Setup Name with trimmed value
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User enters Product Setup Name with leading and trailing spaces
    And User clicks Save button on Product Setup Type popup
    Then Product Setup Type should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Save without any changes
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User clicks Save button on Product Setup Type popup without modification
    Then Product Setup Type should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen


  # =========================================================
  # NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p1
  Scenario: Update Product Setup Type without Product Setup Name
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User clears Product Setup Name field
    And User clicks Save button on Product Setup Type popup
    Then "This field is required" error should be displayed for Product Setup Name
    When User clicks Close "X" button on Product Setup Type popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Open Machine Output Timer duration picker and close with X — existing value preserved
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User taps Machine Output Timer field
    Then "Select Duration" popup should be displayed
    When User clicks Close "X" button on duration picker
    Then "Edit Product Setup Type" popup should be displayed
    And Machine Output Timer field should be pre-filled
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @negative @regression @p2
  Scenario: Update Product Setup Type with only spaces in Product Setup Name
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User enters only spaces in Product Setup Name
    And User clicks Save button on Product Setup Type popup
    Then "This field is required" error should be displayed for Product Setup Name
    When User clicks Close "X" button on Product Setup Type popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Update Product Setup Type with special characters in name
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User enters only special characters in Product Setup Name
    And User clicks Save button on Product Setup Type popup
    Then Product Setup Type should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p3
  Scenario: Rapid multiple Save clicks
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User updates Product Setup Name with valid value
    And User clicks Save button multiple times quickly on Product Setup Type popup
    Then Product Setup Type should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Close edit popup without saving updated changes
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User modifies Product Setup Type details
    And User clicks Close "X" button on Product Setup Type popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Reopen Edit popup after closing — data should be preserved
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    And User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    Then previously saved Product Setup Type data should be displayed
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Edit Product Setup Type popup UI elements
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    And Product Setup Type ID should be visible
    And Product Setup Name field should be editable
    And Machine Output Timer field should be visible
    And Product Setup Timer field should be visible
    And Save button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify non-editable fields in Edit Product Setup Type popup
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    Then Product Setup Type ID should not be editable
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Verify pre-filled data accuracy in Edit popup
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    Then all fields should display previously saved Product Setup Type data correctly
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Validate Description field during update
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User enters long text in Product Setup Type Description field
    Then system should allow optional Product Setup Type input without error
    When User clicks Close "X" button on Product Setup Type popup
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Update Machine Output Timer via Select Duration popup
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User taps Machine Output Timer field
    Then "Select Duration" popup should be displayed
    When User clicks Save button on duration picker
    Then "Edit Product Setup Type" popup should be displayed
    When User taps Machine Output Timer field
    Then "Select Duration" popup should be displayed
    When User clicks Close "X" button on duration picker
    Then "Edit Product Setup Type" popup should be displayed
    And Machine Output Timer field should be pre-filled
    When User clicks Save button on Product Setup Type popup
    Then Product Setup Type should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Update Product Setup Timer via Select Duration popup
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User taps Product Setup Timer field
    Then "Select Duration" popup should be displayed
    When User clicks Save button on duration picker
    Then "Edit Product Setup Type" popup should be displayed
    When User taps Product Setup Timer field
    Then "Select Duration" popup should be displayed
    When User clicks Close "X" button on duration picker
    Then "Edit Product Setup Type" popup should be displayed
    And Product Setup Timer field should be pre-filled
    When User clicks Save button on Product Setup Type popup
    Then Product Setup Type should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen

  @regression @p2
  Scenario: Open Product Setup Timer duration picker and close with X — existing value preserved
    When User has already updated Product Setup Type
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    And Edit option should be visible
    When User clicks on Edit option
    Then "Edit Product Setup Type" popup should be displayed
    When User taps Product Setup Timer field
    Then "Select Duration" popup should be displayed
    When User clicks Close "X" button on duration picker
    Then "Edit Product Setup Type" popup should be displayed
    And Product Setup Timer field should be pre-filled
    When User clicks Close "X" button on Product Setup Type popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should return to Product Setup Types list screen
