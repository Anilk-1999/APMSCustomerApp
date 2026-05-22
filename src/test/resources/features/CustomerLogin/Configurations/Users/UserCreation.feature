@regression @smoke @create @p1
Feature: Create User via Configuration Module

  # Background navigates to Users list before every scenario.
  # CommonNavigationSteps short-circuits navigation when the list screen is already visible.

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Users" feature


  # =========================================================
  # NAVIGATION FLOW
  # =========================================================

  @smoke @p1
  Scenario: Navigate to Users list screen
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "User" section is displayed
    When User clicks on "Users" feature
    Then verify user navigates to "Users" list screen
    Then Users list should be displayed
    And Add "+" button should be visible in Users list


  # =========================================================
  # OPEN ADD NEW USER SCREEN
  # =========================================================

  @smoke @p1
  Scenario: Open Add New User screen and verify all fields
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    And User Name field should be visible
    And Email ID field should be visible
    And Phone No field should be visible
    And Emergency No field should be visible
    And Emp Code field should be visible
    And Blood Group dropdown should be visible
    And DOB field should be visible
    And DOJ field should be visible
    And Address Line I field should be visible
    And Address Line II field should be visible
    And Pin Code field should be visible
    And City field should be visible
    And State field should be visible
    And Country field should be visible
    And Roles dropdown should be visible
    And Teams field should be visible
    And Submit button should be visible in Add New User screen
    When User clicks back arrow in Add New User screen
    Then User should be navigate into "Users" list


  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Create User with mandatory fields only
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    When User clicks Submit button in Add New User screen
    Then User should be created successfully
    And newly created User should be visible in Users list screen

  @regression @p1
  Scenario: Create User with all fields
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User enters valid Phone No
    And User enters Emergency No
    And User enters Emp Code
    And User selects Blood Group from dropdown
    And User enters DOB
    And User enters DOJ
    And User enters Address Line I
    And User enters Address Line II
    And User enters Pin Code
    And User enters City
    And User enters State
    And User enters Country
    And User selects Role from dropdown
    And User selects Teams
    When User clicks Submit button in Add New User screen
    Then User should be created successfully
    And newly created User should be visible in Users list screen

  @negative @regression @p2
  Scenario: Create User with DOB only without DOJ shows validation error
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    And User enters DOB
    When User clicks Submit button in Add New User screen
    Then DOJ validation error should be displayed below DOJ field
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Clicking DOJ field without selecting DOB first shows toast
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User clicks on DOJ field without selecting DOB first
    Then "Please select a Date of Birth first" toast should be displayed
    And DOJ date picker should not open
    When User clicks back arrow in Add New User screen
    Then User should be navigate into "Users" list

  @regression @p2
  Scenario: Create User with both DOB and DOJ where DOB is less than DOJ
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    And User enters valid DOB less than DOJ
    And User enters valid DOJ greater than DOB
    When User clicks Submit button in Add New User screen
    Then User should be created successfully
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Create User with valid Emergency No
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User enters valid Phone No
    And User enters valid 10-digit Emergency No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    When User clicks Submit button in Add New User screen
    Then User should be created successfully
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Create User with valid 6-digit Pin Code
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    And User enters valid 6-digit Pin Code
    When User clicks Submit button in Add New User screen
    Then User should be created successfully
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Create User with single Team
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    And User selects one Team
    When User clicks Submit button in Add New User screen
    Then User should be created successfully
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Create User with multiple Teams
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    And User selects multiple Teams
    When User clicks Submit button in Add New User screen
    Then User should be created successfully
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Create User with leading and trailing spaces in User Name
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters User Name with leading and trailing spaces
    And User enters valid Email ID
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    When User clicks Submit button in Add New User screen
    Then system should trim spaces and create User successfully
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Create User without optional fields
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User leaves Emergency No empty
    And User leaves Emp Code empty
    And User leaves DOB empty
    And User leaves DOJ empty
    And User leaves Address Line I empty
    And User leaves Address Line II empty
    And User leaves Pin Code empty
    And User leaves City empty
    And User leaves State empty
    And User leaves Country empty
    And User selects Role from dropdown
    And User leaves Teams empty
    When User clicks Submit button in Add New User screen
    Then User should be created successfully
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Create User with all address fields
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    And User enters Address Line I
    And User enters Address Line II
    And User enters valid 6-digit Pin Code
    And User enters City
    And User enters State
    And User enters Country
    When User clicks Submit button in Add New User screen
    Then User should be created successfully
    And User should be navigate into "Users" list


  # =========================================================
  # DROPDOWN / FIELD INTERACTION
  # =========================================================

  @regression @p2
  Scenario: Verify Blood Group dropdown options
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User clicks on Blood Group dropdown
    Then Blood Group options list should be displayed
    And User should be able to select one Blood Group value
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @regression @p2
  Scenario: Verify Roles dropdown options
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User clicks on Roles dropdown
    Then Roles options list should be displayed
    And User should be able to select one Role value
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @regression @p2
  Scenario: Verify Teams multi-select
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User clicks on Teams field
    Then Teams selection should be displayed
    And User should be able to select multiple Teams
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @regression @p3
  Scenario: Verify date picker opens for DOB field
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User clicks on DOB field
    Then date picker should be displayed for DOB
    When User clicks back arrow in Add New User screen
    Then User should be navigate into "Users" list

  @negative @regression @p3
  Scenario: Verify clicking DOJ field without DOB shows toast instead of date picker
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User clicks on DOJ field without selecting DOB first
    Then "Please select a Date of Birth first" toast should be displayed
    And date picker should not be displayed for DOJ
    When User clicks back arrow in Add New User screen
    Then User should be navigate into "Users" list


  # =========================================================
  # NEGATIVE — MANDATORY FIELD VALIDATION
  # =========================================================

  @negative @regression @p1
  Scenario: Submit Add New User without any data
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User clicks Submit button in Add New User screen
    Then "This field is required" should be displayed
    And "Email ID is required" should be displayed
    And "Phone No is required" should be displayed
    And "Blood Group is required" should be displayed
    And "This field is required" should be displayed
    When User clicks back arrow in Add New User screen
    Then User should be navigate into "Users" list

  @negative @regression @p1
  Scenario: Create User without User Name
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User leaves User Name empty
    And User enters valid Email ID
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    When User clicks Submit button in Add New User screen
    Then "This field is required" should be displayed
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @negative @regression @p1
  Scenario: Create User without Email ID
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User leaves Email ID empty
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    When User clicks Submit button in Add New User screen
    Then "Email ID is required" should be displayed
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @negative @regression @p1
  Scenario: Create User without Phone No
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User leaves Phone No empty
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    When User clicks Submit button in Add New User screen
    Then "Phone No is required" should be displayed
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @negative @regression @p1
  Scenario: Create User without Blood Group
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User enters valid Phone No
    And User does not select Blood Group
    And User selects Role from dropdown
    When User clicks Submit button in Add New User screen
    Then "Blood Group is required" should be displayed
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @negative @regression @p1
  Scenario: Create User without Role
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User does not select Role
    When User clicks Submit button in Add New User screen
    Then "This field is required" should be displayed
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list


  # =========================================================
  # NEGATIVE — EMAIL VALIDATION
  # =========================================================

  @negative @regression @p1
  Scenario: Create User with invalid Email ID format
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters invalid Email ID format
    And User clicks Submit button in Add New User screen
    Then email format validation error should be displayed
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @negative @regression @p1
  Scenario: Create User with duplicate Email ID
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters existing Email ID
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    When User clicks Submit button in Add New User screen
    Then duplicate Email ID error should be displayed
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Create User with only spaces in Email ID
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters only spaces in Email ID field
    And User enters valid User Name
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    When User clicks Submit button in Add New User screen
    Then "Email ID is required" should be displayed
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list


  # =========================================================
  # NEGATIVE — PHONE NO VALIDATION
  # =========================================================

  @negative @regression @p1
  Scenario: Create User with duplicate Phone No
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User enters existing Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    When User clicks Submit button in Add New User screen
    Then duplicate Phone No error should be displayed
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Create User with Phone No less than 10 digits
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters Phone No with less than 10 digits
    And User clicks Submit button in Add New User screen
    Then phone number format validation error should be displayed
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @regression @p2
  Scenario: Verify Phone No field enforces maximum 10 digits
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User attempts to enter more than 10 digits in Phone No field
    Then Phone No field should not accept more than 10 digits
    And Phone No field should contain exactly 10 digits
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Create User with non-numeric Phone No
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters non-numeric value in Phone No field
    And User clicks Submit button in Add New User screen
    Then phone number format validation error should be displayed
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Create User with only spaces in Phone No
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters only spaces in Phone No field
    And User enters valid User Name
    And User enters valid Email ID
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    When User clicks Submit button in Add New User screen
    Then "Phone No is required" should be displayed
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list


  # =========================================================
  # NEGATIVE — EMERGENCY NO VALIDATION
  # =========================================================

  @regression @p2
  Scenario: Verify Emergency No field shows numeric keyboard only
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User taps on Emergency No field
    Then Emergency No field should show numeric keyboard only
    And Emergency No field should not accept non-numeric characters
    When User clicks back arrow in Add New User screen
    Then User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Create User with Emergency No less than 10 digits
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters Emergency No with less than 10 digits
    And User clicks Submit button in Add New User screen
    Then emergency number format validation error should be displayed
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @regression @p2
  Scenario: Verify Emergency No field enforces maximum 10 digits
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User attempts to enter more than 10 digits in Emergency No field
    Then Emergency No field should not accept more than 10 digits
    And Emergency No field should contain exactly 10 digits
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list


  # =========================================================
  # NEGATIVE — PIN CODE VALIDATION
  # =========================================================

  @negative @regression @p2
  Scenario: Create User with Pin Code less than 6 digits
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters Pin Code with less than 6 digits
    And User clicks Submit button in Add New User screen
    Then pin code format validation error should be displayed
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @regression @p2
  Scenario: Verify Pin Code field enforces maximum 6 digits
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User attempts to enter more than 6 digits in Pin Code field
    Then Pin Code field should not accept more than 6 digits
    And Pin Code field should contain exactly 6 digits
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list

  @regression @p2
  Scenario: Verify Pin Code field shows numeric keyboard only
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User taps on Pin Code field
    Then Pin Code field should show numeric keyboard only
    And Pin Code field should not accept non-numeric characters
    When User clicks back arrow in Add New User screen
    Then User should be navigate into "Users" list


  # =========================================================
  # NEGATIVE — USER NAME VALIDATION
  # =========================================================

  @negative @regression @p2
  Scenario: Create User with only spaces in User Name
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters only spaces in User Name field
    And User enters valid Email ID
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    When User clicks Submit button in Add New User screen
    Then "This field is required" should be displayed
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then User should be navigate into "Users" list


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Close Add New User screen with data shows Confirmation Alert
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    When User clicks back arrow in Add New User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then screen should be closed without saving User data
    And User should be navigate into "Users" list

  @sanity @p3
  Scenario: Close Add New User screen without any data should not show Confirmation Alert
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User clicks back arrow in Add New User screen
    Then User should be navigate into "Users" list

  @sanity @p2
  Scenario: Rapid multiple Submit clicks should prevent duplicate User creation
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    When User enters valid User Name
    And User enters valid Email ID
    And User enters valid Phone No
    And User selects Blood Group from dropdown
    And User selects Role from dropdown
    When User clicks Submit button multiple times quickly in Add New User screen
    Then system should prevent duplicate User creation
    Then User should be navigate into "Users" list


  @sanity @p3
  Scenario: Verify Add New User form can be scrolled
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    Then user should be able to scroll the Add New User form
    When User clicks back arrow in Add New User screen
    Then User should be navigate into "Users" list


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Add New User screen UI elements are aligned properly
    When User clicks on "+ Add" button in Users list screen
    Then "Add New User" screen should be displayed
    And all Add New User fields should be aligned properly
    And User Name field should be visible
    And Email ID field should be visible
    And Phone No field should be visible
    And Emergency No field should be visible
    And Emp Code field should be visible
    And Blood Group dropdown should be visible
    And DOB field should be visible
    And DOJ field should be visible
    And Address Line I field should be visible
    And Address Line II field should be visible
    And Pin Code field should be visible
    And City field should be visible
    And State field should be visible
    And Country field should be visible
    And Roles dropdown should be visible
    And Teams field should be visible
    And Submit button should be visible in Add New User screen
    When User clicks back arrow in Add New User screen
    Then User should be navigate into "Users" list

  @regression @p2
  Scenario: Verify Users list screen UI elements
    Then Users list should be displayed
    And each User record should show User ID
    And each User record should show User Name
    And each User record should show Email ID
    And each User record should show Phone No
    And each User record should show Status