@regression @smoke @create @p1
Feature: Create Operator via Configuration Module

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Operators" feature


  # =========================================================
  # NAVIGATION FLOW
  # =========================================================

  @smoke @p1
  Scenario: Navigate to Operators list screen
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "User" section is displayed
    When User clicks on "Operators" feature
    Then verify user navigates to "Operators" list screen
    Then Operators list should be displayed
    And Add "+" button should be visible in Operators list


  # =========================================================
  # OPEN ADD NEW OPERATOR SCREEN
  # =========================================================

  @smoke @p1
  Scenario: Open Add New Operator screen and verify all fields
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    And Operator Name field should be visible
    And Email ID field should be visible
    And Operator Phone No field should be visible
    And Emergency No field should be visible
    And Operator Code field should be visible
    And Blood Group dropdown should be visible
    And DOB field should be visible
    And DOJ field should be visible
    And Address Line I field should be visible
    And Address Line II field should be visible
    And Pin Code field should be visible
    And City field should be visible
    And State field should be visible
    And Country field should be visible
    And Submit button should be visible in Add New Operator screen
    When User clicks back arrow in Add New Operator screen
    Then Operator should be navigate into "Operators" list


  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Create Operator with mandatory fields only
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters valid Operator Name
    And User enters valid Operator Phone No
    And User selects Blood Group from dropdown
    When User clicks Submit button in Add New Operator screen
    Then Operator should be created successfully
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p1
  Scenario: Create Operator with all fields
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters valid Operator Name
    And User enters valid Operator Email ID
    And User enters valid Operator Phone No
    And User enters valid 10-digit Operator Emergency No
    And User enters Operator Code
    And User selects Blood Group from dropdown
    And User enters Operator DOB
    And User enters Operator DOJ
    And User enters Operator Address Line I
    And User enters Operator Address Line II
    And User enters Operator Pin Code
    And User enters Operator City
    And User enters Operator State
    And User enters Operator Country
    When User clicks Submit button in Add New Operator screen
    Then Operator should be created successfully
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Create Operator with DOB only without DOJ
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters valid Operator Name
    And User enters valid Operator Phone No
    And User selects Blood Group from dropdown
    And User enters Operator DOB
    When User clicks Submit button in Add New Operator screen
    Then Operator should be created successfully
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Create Operator with both DOB and DOJ where DOB is less than DOJ
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters valid Operator Name
    And User enters valid Operator Phone No
    And User selects Blood Group from dropdown
    And User enters valid Operator DOB less than DOJ
    And User enters valid Operator DOJ greater than DOB
    When User clicks Submit button in Add New Operator screen
    Then Operator should be created successfully
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Create Operator with valid Emergency No 
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters valid Operator Name
    And User enters valid Operator Phone No
    And User enters valid 10-digit Operator Emergency No
    And User selects Blood Group from dropdown
    When User clicks Submit button in Add New Operator screen
    Then Operator should be created successfully
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Create Operator with valid 6-digit Pin Code
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters valid Operator Name
    And User enters valid Operator Phone No
    And User selects Blood Group from dropdown
    And User enters valid 6-digit Operator Pin Code
    When User clicks Submit button in Add New Operator screen
    Then Operator should be created successfully
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Create Operator with leading and trailing spaces in Operator Name
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters Operator Name with leading and trailing spaces
    And User enters valid Operator Phone No
    And User selects Blood Group from dropdown
    When User clicks Submit button in Add New Operator screen
    Then system should trim spaces and create Operator successfully
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Create Operator without optional fields
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters valid Operator Name
    And User enters valid Operator Phone No
    And User selects Blood Group from dropdown
    And User leaves Operator Email ID empty
    And User leaves Operator Emergency No empty
    And User leaves Operator Code empty
    And User leaves Operator DOB empty
    And User leaves Operator DOJ empty
    And User leaves Operator Address Line I empty
    And User leaves Operator Address Line II empty
    And User leaves Operator Pin Code empty
    And User leaves Operator City empty
    And User leaves Operator State empty
    And User leaves Operator Country empty
    When User clicks Submit button in Add New Operator screen
    Then Operator should be created successfully
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Create Operator with all address fields
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters valid Operator Name
    And User enters valid Operator Phone No
    And User selects Blood Group from dropdown
    And User enters Operator Address Line I
    And User enters Operator Address Line II
    And User enters valid 6-digit Operator Pin Code
    And User enters Operator City
    And User enters Operator State
    And User enters Operator Country
    When User clicks Submit button in Add New Operator screen
    Then Operator should be created successfully
    And Operator should be navigate into "Operators" list


  # =========================================================
  # DROPDOWN / FIELD INTERACTION
  # =========================================================

  @regression @p2
  Scenario: Verify Blood Group dropdown options
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User clicks on Blood Group dropdown
    Then Blood Group options list should be displayed
    And User should be able to select one Blood Group value
    When User clicks back arrow in Add New Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Operator should be navigate into "Operators" list

  @regression @p3
  Scenario: Verify date picker opens for Operator DOB field
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User clicks on Operator DOB field
    Then date picker should be displayed for Operator DOB
    When User clicks back arrow in Add New Operator screen
    Then Operator should be navigate into "Operators" list


  # =========================================================
  # NEGATIVE — MANDATORY FIELD VALIDATION
  # =========================================================

  @negative @regression @p1
  Scenario: Submit Add New Operator without any data
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User clicks Submit button in Add New Operator screen
    Then "This field is required" should be displayed
    And "Phone No is required" should be displayed
    And "This field is required" should be displayed
    When User clicks back arrow in Add New Operator screen
    Then Operator should be navigate into "Operators" list

  @negative @regression @p1
  Scenario: Create Operator without Operator Name
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User leaves Operator Name empty
    And User enters valid Operator Phone No
    And User selects Blood Group from dropdown
    When User clicks Submit button in Add New Operator screen
    Then "This field is required" should be displayed
    When User clicks back arrow in Add New Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Operator should be navigate into "Operators" list

  @negative @regression @p1
  Scenario: Create Operator without Phone No
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters valid Operator Name
    And User leaves Operator Phone No empty
    And User selects Blood Group from dropdown
    When User clicks Submit button in Add New Operator screen
    Then "Phone No is required" should be displayed
    When User clicks back arrow in Add New Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Operator should be navigate into "Operators" list

  @negative @regression @p1
  Scenario: Create Operator without Blood Group
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters valid Operator Name
    And User enters valid Operator Phone No
    And User does not select Blood Group
    When User clicks Submit button in Add New Operator screen
    Then "This field is required" should be displayed
    When User clicks back arrow in Add New Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Operator should be navigate into "Operators" list


  # =========================================================
  # NEGATIVE — EMAIL VALIDATION
  # =========================================================

  @negative @regression @p2
  Scenario: Create Operator with invalid Email ID format
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters invalid Operator Email ID format
    And User clicks Submit button in Add New Operator screen
    Then Operator email format validation error should be displayed
    When User clicks back arrow in Add New Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Operator should be navigate into "Operators" list


  # =========================================================
  # NEGATIVE — PHONE NO VALIDATION
  # =========================================================

  @negative @regression @p1
  Scenario: Create Operator with duplicate Phone No
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters valid Operator Name
    And User selects Blood Group from dropdown
    And User enters existing Operator Phone No
    When User clicks Submit button in Add New Operator screen
    Then "Phone Number Already Exits" alert should be displayed
    And User dismisses the toast message
    When User clicks back arrow in Add New Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Operator should be navigate into "Operators" list

  @negative @regression @p2
  Scenario: Create Operator with Phone No less than 10 digits
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters Operator Phone No with less than 10 digits
    And User clicks Submit button in Add New Operator screen
    Then Operator phone number format validation error should be displayed
    When User clicks back arrow in Add New Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Verify Operator Phone No field enforces maximum 10 digits
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User attempts to enter more than 10 digits in Operator Phone No field
    Then Operator Phone No field should not accept more than 10 digits
    And Operator Phone No field should contain exactly 10 digits
    When User clicks back arrow in Add New Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Operator should be navigate into "Operators" list

  @negative @regression @p2
  Scenario: Create Operator with non-numeric Phone No
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters non-numeric value in Operator Phone No field
    And User clicks Submit button in Add New Operator screen
    Then Operator phone number format validation error should be displayed
    When User clicks back arrow in Add New Operator screen
    Then Operator should be navigate into "Operators" list

  @negative @regression @p2
  Scenario: Create Operator with only spaces in Operator Phone No
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters only spaces in Operator Phone No field
    And User enters valid Operator Name
    And User selects Blood Group from dropdown
    When User clicks Submit button in Add New Operator screen
    Then "Phone No is required" should be displayed
    When User clicks back arrow in Add New Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Operator should be navigate into "Operators" list


  # =========================================================
  # NEGATIVE — EMERGENCY NO VALIDATION
  # =========================================================

  @regression @p2
  Scenario: Verify Operator Emergency No field shows numeric keyboard only
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User taps on Operator Emergency No field
    Then Operator Emergency No field should show numeric keyboard only
    And Operator Emergency No field should not accept non-numeric characters
    When User clicks back arrow in Add New Operator screen
    Then Operator should be navigate into "Operators" list

  @negative @regression @p2
  Scenario: Create Operator with Emergency No less than 10 digits
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters Operator Emergency No with less than 10 digits
    And User clicks Submit button in Add New Operator screen
    Then Operator emergency number format validation error should be displayed
    When User clicks back arrow in Add New Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Verify Operator Emergency No field enforces maximum 10 digits
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User attempts to enter more than 10 digits in Operator Emergency No field
    Then Operator Emergency No field should not accept more than 10 digits
    And Operator Emergency No field should contain exactly 10 digits
    When User clicks back arrow in Add New Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Operator should be navigate into "Operators" list


  # =========================================================
  # NEGATIVE — PIN CODE VALIDATION
  # =========================================================

  @negative @regression @p2
  Scenario: Create Operator with Pin Code less than 6 digits
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters Operator Pin Code with less than 6 digits
    And User clicks Submit button in Add New Operator screen
    Then Operator pin code format validation error should be displayed
    When User clicks back arrow in Add New Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Verify Operator Pin Code field enforces maximum 6 digits
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User attempts to enter more than 6 digits in Operator Pin Code field
    Then Operator Pin Code field should not accept more than 6 digits
    And Operator Pin Code field should contain exactly 6 digits
    When User clicks back arrow in Add New Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Verify Operator Pin Code field shows numeric keyboard only
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User taps on Operator Pin Code field
    Then Operator Pin Code field should show numeric keyboard only
    And Operator Pin Code field should not accept non-numeric characters
    When User clicks back arrow in Add New Operator screen
    Then Operator should be navigate into "Operators" list


  # =========================================================
  # NEGATIVE — OPERATOR NAME VALIDATION
  # =========================================================

  @negative @regression @p2
  Scenario: Create Operator with only spaces in Operator Name
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters only spaces in Operator Name field
    And User enters valid Operator Phone No
    And User selects Blood Group from dropdown
    When User clicks Submit button in Add New Operator screen
    Then "This field is required" should be displayed
    When User clicks back arrow in Add New Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then Operator should be navigate into "Operators" list


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Close Add New Operator screen with data shows Confirmation Alert
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters valid Operator Name
    And User enters valid Operator Phone No
    And User selects Blood Group from dropdown
    When User clicks back arrow in Add New Operator screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then screen should be closed without saving Operator data
    And Operator should be navigate into "Operators" list

  @sanity @p3
  Scenario: Close Add New Operator screen without any data should not show Confirmation Alert
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User clicks back arrow in Add New Operator screen
    Then Operator should be navigate into "Operators" list

  @sanity @p2
  Scenario: Rapid multiple Submit clicks should prevent duplicate Operator creation
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    When User enters valid Operator Name
    And User enters valid Operator Phone No
    And User selects Blood Group from dropdown
    When User clicks Submit button multiple times quickly in Add New Operator screen
    Then system should prevent duplicate Operator creation
    Then Operator should be navigate into "Operators" list

  @sanity @p3
  Scenario: Verify Add New Operator form can be scrolled
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    Then user should be able to scroll the Add New Operator form
    When User clicks back arrow in Add New Operator screen
    Then Operator should be navigate into "Operators" list


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Add New Operator screen UI elements are aligned properly
    When User clicks on "+ Add" button in Operators list screen
    Then "Add New Operator" screen should be displayed
    And all Add New Operator fields should be aligned properly
    And Operator Name field should be visible
    And Email ID field should be visible
    And Operator Phone No field should be visible
    And Emergency No field should be visible
    And Operator Code field should be visible
    And Blood Group dropdown should be visible
    And DOB field should be visible
    And DOJ field should be visible
    And Address Line I field should be visible
    And Address Line II field should be visible
    And Pin Code field should be visible
    And City field should be visible
    And State field should be visible
    And Country field should be visible
    And Submit button should be visible in Add New Operator screen
    When User clicks back arrow in Add New Operator screen
    Then Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Verify Operators list screen UI elements
    Then Operators list should be displayed
    And each Operator record should show Operator ID
    And each Operator record should show Operator Name
    And each Operator record should show Operator Phone No
    And each Operator record should show Status