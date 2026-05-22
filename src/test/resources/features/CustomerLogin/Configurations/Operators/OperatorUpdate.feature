@regression @smoke @update @p1
Feature: Update Operator via Swipe Action and Status Management

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Operators" feature


  # =========================================================
  # SEARCH + SWIPE + EDIT FLOW
  # =========================================================

  @smoke @p1
  Scenario: Search and open Edit Operator screen and verify all fields
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "User" section is displayed
    When User clicks on "Operators" feature
    Then verify user navigates to "Operators" list screen
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    And Operator ID should be visible and non-editable in Edit Operator screen
    And Operator Name field should be pre-filled and editable
    And Email ID field should be pre-filled and editable in Edit Operator screen
    And Operator Phone No field should be pre-filled and editable
    And Emergency No field should be pre-filled and editable
    And Operator Code field should be pre-filled and editable
    And Blood Group should be pre-filled and editable
    And DOB field should be pre-filled and editable
    And DOJ field should be pre-filled and editable
    And Address Line I field should be pre-filled and editable
    And Address Line II field should be pre-filled and editable
    And Pin Code field should be pre-filled and editable
    And City field should be pre-filled and editable
    And State field should be pre-filled and editable
    And Country field should be pre-filled and editable
    And Save button should be visible in Edit Operator screen
    When User clicks back arrow in Edit Operator screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Update Operator with all fields
    When User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User updates Operator Name
    And User updates Operator Phone No
    And User updates Operator Email ID
    And User updates Operator Emergency No
    And User updates Operator Code
    And User updates Operator Blood Group
    And User updates Operator DOB
    And User updates Operator DOJ
    And User updates Operator Address Line I
    And User updates Operator Address Line II
    And User updates Operator Pin Code
    And User updates Operator City
    And User updates Operator State
    And User updates Operator Country
    And User clicks Save button in Edit Operator screen
    Then Operator should be updated successfully
    And updated Operator should be reflected in list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Update only Operator Name
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User updates Operator Name
    And User clicks Save button in Edit Operator screen
    Then Operator should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Update only Operator Phone No
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User updates Operator Phone No
    And User clicks Save button in Edit Operator screen
    Then Operator should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Update only Operator Blood Group
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User updates Operator Blood Group
    And User clicks Save button in Edit Operator screen
    Then Operator should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Update Operator DOB and DOJ with valid values
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User updates Operator DOB with valid value less than DOJ
    And User updates Operator DOJ with valid value greater than DOB
    And User clicks Save button in Edit Operator screen
    Then Operator should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Update only Operator DOB
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User updates Operator DOB
    And User clicks Save button in Edit Operator screen
    Then Operator should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Update only Operator DOJ
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User updates Operator DOJ
    And User clicks Save button in Edit Operator screen
    Then Operator should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Update only Operator Emergency No
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User updates Operator Emergency No
    And User clicks Save button in Edit Operator screen
    Then Operator should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Clear optional Operator fields and save
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User clears Operator Emergency No field
    And User clears Operator Code field
    And User clears Operator DOB field
    And User clears Operator DOJ field
    And User clears Operator Address Line I field
    And User clears Operator Address Line II field
    And User clears Operator Pin Code field
    And User clears Operator City field
    And User clears Operator State field
    And User clears Operator Country field
    And User clicks Save button in Edit Operator screen
    Then Operator should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Save Edit Operator without making any changes
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User clicks Save button in Edit Operator screen without making changes
    Then Operator should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Update Operator Name with leading and trailing spaces
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User enters Operator Name with leading and trailing spaces in Edit screen
    And User clicks Save button in Edit Operator screen
    Then system should trim spaces and update Operator successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Verify updated data matches in list after update
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # NEGATIVE — MANDATORY FIELD VALIDATION
  # =========================================================

  @negative @regression @p1
  Scenario: Update without Operator Name
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User clears Operator Name field
    And User clicks Save button in Edit Operator screen
    Then "This field is required" should be displayed
    When User clicks back arrow in Edit Operator screen
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @negative @regression @p1
  Scenario: Update without Operator Phone No
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User clears Operator Phone No field
    And User clicks Save button in Edit Operator screen
    Then "Phone No is required" should be displayed
    When User clicks back arrow in Edit Operator screen
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @negative @regression @p1
  Scenario: Update without Operator Blood Group
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User clears Operator Blood Group selection
    And User clicks Save button in Edit Operator screen
    Then Blood Group required validation is not applicable in Edit Operator screen
    When User exits Edit Operator screen if still open
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # NEGATIVE — PHONE NO VALIDATION
  # =========================================================

  @negative @regression @p2
  Scenario: Update with duplicate Operator Phone No
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User enters existing Operator Phone No in Edit Operator screen
    And User clicks Save button in Edit Operator screen
    Then "Phone Number Already Exits" alert should be displayed
    And User dismisses the toast message
    When User clicks back arrow in Edit Operator screen
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @negative @regression @p2
  Scenario: Update with Operator Phone No less than 10 digits
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User enters Operator Phone No with less than 10 digits in Edit screen
    And User clicks Save button in Edit Operator screen
    Then Operator phone number format validation error should be displayed
    When User clicks back arrow in Edit Operator screen
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @negative @regression @p2
  Scenario: Update with only spaces in Operator Name
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User enters only spaces in Operator Name field
    And User clicks Save button in Edit Operator screen
    Then "This field is required" should be displayed
    When User clicks back arrow in Edit Operator screen
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # NEGATIVE — EMERGENCY NO / PIN CODE VALIDATION
  # =========================================================

  @negative @regression @p2
  Scenario: Update with invalid Operator Pin Code format
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User enters invalid Operator Pin Code in Edit screen
    Then Operator Pin Code format validation is not applicable in Edit Operator screen
    When User exits Edit Operator screen if still open
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Close Edit Operator screen with unsaved changes shows Confirmation Alert
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User updates Operator Name
    And User clicks back arrow in Edit Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then changes should not be saved in Operator
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @sanity @p3
  Scenario: Rapid Save clicks should prevent duplicate Operator update
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    When User clicks Save button multiple times quickly in Edit Operator screen
    Then system should prevent duplicate Operator update
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @sanity @p3
  Scenario: Rapid swipe right-to-left on Operator record
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left multiple times quickly
    Then only one Edit option should be visible in Operator list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Edit Operator screen UI elements and alignment
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    And all Edit Operator fields should be aligned properly
    And editable Operator fields should be enabled
    And Save button should be visible in Edit Operator screen
    When User clicks back arrow in Edit Operator screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p3
  Scenario: Verify Edit Operator form can be scrolled
    When User has updated Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User swipes Operator record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Operator" screen should be displayed
    Then user should be able to scroll the Edit Operator form
    When User clicks back arrow in Edit Operator screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list