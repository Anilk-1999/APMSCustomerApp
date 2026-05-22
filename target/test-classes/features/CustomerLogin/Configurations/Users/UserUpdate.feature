@regression @smoke @update @p1
Feature: Update User via Swipe Action and Status Management

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Users" feature


  # =========================================================
  # SEARCH + SWIPE + EDIT FLOW
  # =========================================================

  @smoke @p1
  Scenario: Search and open Edit User screen and verify all fields
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "User" section is displayed
    When User clicks on "Users" feature
    Then verify user navigates to "Users" list screen
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    And User ID should be visible and non-editable in Edit User screen
    And User Name field should be pre-filled and editable
    And Email ID field should be visible and non-editable in Edit User screen
    And Phone No field should be pre-filled and editable
    And Emergency No field should be pre-filled and editable
    And Emp Code field should be pre-filled and editable
    And Blood Group should be pre-filled and editable
    And DOB field should be pre-filled and editable
    And DOJ field should be pre-filled and editable
    And Address Line I field should be pre-filled and editable
    And Address Line II field should be pre-filled and editable
    And Pin Code field should be pre-filled and editable
    And City field should be pre-filled and editable
    And State field should be pre-filled and editable
    And Country field should be pre-filled and editable
    And Roles should be pre-filled and editable
    And Teams should be pre-filled and editable
    And Status should be visible and clickable in Edit User screen
    And Save button should be visible in Edit User screen
    When User clicks back arrow in Edit User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Update User with all fields
    When User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User updates User Name
    And User updates Phone No
    And User updates Emergency No
    And User updates Emp Code
    And User updates Blood Group
    And User updates DOB
    And User updates DOJ
    And User updates Address Line I
    And User updates Address Line II
    And User updates Pin Code
    And User updates City
    And User updates State
    And User updates Country
    And User updates Role
    And User updates Teams
    And User clicks Save button in Edit User screen
    Then User should be updated successfully
    And updated User should be reflected in list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Update only User Name
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User updates User Name
    And User clicks Save button in Edit User screen
    Then User should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Update only Phone No
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User updates Phone No
    And User clicks Save button in Edit User screen
    Then User should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Update only Blood Group
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User updates Blood Group
    And User clicks Save button in Edit User screen
    Then User should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Update only Role
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User updates Role
    And User clicks Save button in Edit User screen
    Then User should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Update only Teams
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User updates Teams
    And User clicks Save button in Edit User screen
    Then User should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Update DOB and DOJ with valid values
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User updates DOB with valid value less than DOJ
    And User updates DOJ with valid value greater than DOB
    And User clicks Save button in Edit User screen
    Then User should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Update only DOB
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User updates DOB
    And User clicks Save button in Edit User screen
    Then DOJ validation error should be displayed when only DOB is entered
    When User clicks back arrow in Edit User screen
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then changes should not be saved in User
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Update only DOJ
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User updates DOJ
    And User clicks Save button in Edit User screen
    Then User should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Update only Emergency No
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User updates Emergency No
    And User clicks Save button in Edit User screen
    Then User should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Clear optional fields and save
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User clears Emergency No field
    And User clears Emp Code field
    And User clears DOB field
    And User clears DOJ field
    And User clears Address Line I field
    And User clears Address Line II field
    And User clears Pin Code field
    And User clears City field
    And User clears State field
    And User clears Country field
    And User clears Teams selection
    And User clicks Save button in Edit User screen
    Then User should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Save Edit User without making any changes
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User clicks Save button in Edit User screen without making changes
    Then User should be updated successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Update User Name with leading and trailing spaces
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User enters User Name with leading and trailing spaces in Edit screen
    And User clicks Save button in Edit User screen
    Then system should trim spaces and update User successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Verify Email ID is non-editable in Edit User screen
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    Then Email ID field should be non-editable in Edit User screen
    And User should not be able to modify Email ID value
    When User clicks back arrow in Edit User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # NEGATIVE — MANDATORY FIELD VALIDATION
  # =========================================================

  @negative @regression @p1
  Scenario: Update without User Name
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User clears User Name field
    And User clicks Save button in Edit User screen
    Then "This field is required" should be displayed
    When User clicks back arrow in Edit User screen
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @negative @regression @p1
  Scenario: Update without Phone No
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User clears Phone No field
    And User clicks Save button in Edit User screen
    Then "Phone No is required" should be displayed
    When User clicks back arrow in Edit User screen
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @negative @regression @p1
  Scenario: Update without Blood Group
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User clears Blood Group selection
    And User clicks Save button in Edit User screen
    Then Blood Group required validation is not applicable in Edit User screen
    When User exits Edit User screen if still open
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Update without Role
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User clears Role selection
    And User clicks Save button in Edit User screen
    Then Role required validation is not applicable in Edit User screen
    When User exits Edit User screen if still open
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # NEGATIVE — PHONE NO VALIDATION
  # =========================================================

  @negative @regression @p2
  Scenario: Update with duplicate Phone No
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User enters existing Phone No in Edit User screen
    And User clicks Save button in Edit User screen
    Then duplicate Phone No error should be displayed
    When User clicks back arrow in Edit User screen
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Update with Phone No less than 10 digits
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User enters Phone No with less than 10 digits in Edit screen
    And User clicks Save button in Edit User screen
    Then phone number format validation error should be displayed
    When User clicks back arrow in Edit User screen
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Update with non-numeric Phone No
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User enters non-numeric value in Phone No in Edit screen
    And User clicks Save button in Edit User screen
    Then phone number format validation error should be displayed
    When User clicks back arrow in Edit User screen
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Update with only spaces in User Name
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User enters only spaces in User Name field
    And User clicks Save button in Edit User screen
    Then "This field is required" should be displayed
    When User clicks back arrow in Edit User screen
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # NEGATIVE — EMERGENCY NO / PIN CODE VALIDATION
  # =========================================================

  @negative @regression @p2
  Scenario: Update with non-numeric Emergency No
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User enters non-numeric value in Emergency No in Edit screen
    Then Emergency No field only accepts numeric input in Edit User screen
    When User exits Edit User screen if still open
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Update with invalid Pin Code format
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User enters invalid Pin Code in Edit screen
    Then Pin Code format validation is not applicable in Edit User screen
    When User exits Edit User screen if still open
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Close Edit User screen with unsaved changes shows Confirmation Alert
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User updates User Name
    And User clicks back arrow in Edit User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then changes should not be saved in User
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @sanity @p3
  Scenario: Rapid Save clicks should prevent duplicate User update
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    When User clicks Save button multiple times quickly in Edit User screen
    Then system should prevent duplicate User update
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @sanity @p3
  Scenario: Rapid swipe right-to-left on User record
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left multiple times quickly
    Then only one Edit option should be visible in User list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Edit User screen UI elements and alignment
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    And all Edit User fields should be aligned properly
    And editable User fields should be enabled
    And Email ID field should be non-editable in Edit User screen
    And Save button should be visible in Edit User screen
    When User clicks back arrow in Edit User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list

  @regression @p3
  Scenario: Verify Edit User form can be scrolled
    When User has updated User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated User Name
    And User waits for search results to load
    Then system should display matching User results
    And User verifies User appears in list
    When User swipes User record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit User" screen should be displayed
    Then user should be able to scroll the Edit User form
    When User clicks back arrow in Edit User screen
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And User should be navigate into "Users" list
