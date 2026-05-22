@regression @smoke @duplicate @p1
Feature: Duplicate User via View User Screen

  # Duplicate User is accessed via the Duplicate button in the View User screen.
  # The Duplicate screen is a full screen pre-filled with source user data.
  # Email ID and Phone No must be changed (unique) before submitting.

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Users" feature


  # =========================================================
  # NAVIGATION TO DUPLICATE USER SCREEN
  # =========================================================

  @smoke @p1
  Scenario: Open Duplicate User screen from View User screen
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    And all fields should be pre-filled with source User data
    And Email ID field should be editable in Duplicate screen
    And Phone No field should be editable in Duplicate screen
    And Submit button should be visible in Duplicate User screen
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list


  # =========================================================
  # POSITIVE SCENARIOS
  # =========================================================

  @smoke @regression @p1
  Scenario: Duplicate User with new Email ID and Phone No
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Email ID in Duplicate screen
    And User enters new Phone No in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then duplicate User should be created successfully
    And newly duplicated User should be visible in Users list screen

  @regression @p1
  Scenario: Duplicate User with all updated fields
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User updates User Name in Duplicate screen
    And User enters new Email ID in Duplicate screen
    And User enters new Phone No in Duplicate screen
    And User updates Blood Group in Duplicate screen
    And User updates Role in Duplicate screen
    And User updates Emergency No in Duplicate screen
    And User updates Emp Code in Duplicate screen
    And User updates DOB in Duplicate screen
    And User updates DOJ in Duplicate screen
    And User updates Address Line I in Duplicate screen
    And User updates Address Line II in Duplicate screen
    And User updates Pin Code in Duplicate screen
    And User updates City in Duplicate screen
    And User updates State in Duplicate screen
    And User updates Country in Duplicate screen
    And User updates Teams in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then duplicate User should be created successfully
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Duplicate User retains source data when only Email and Phone are changed
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Email ID in Duplicate screen
    And User enters new Phone No in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then duplicate User should be created successfully
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Verify pre-filled data in Duplicate User screen matches source User
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    Then all pre-filled Duplicate User fields should display correct source values
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list

  @regression @p2
  Scenario: Duplicate User with valid DOB and DOJ
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Email ID in Duplicate screen
    And User enters new Phone No in Duplicate screen
    And User enters valid DOB less than DOJ in Duplicate screen
    And User enters valid DOJ greater than DOB in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then duplicate User should be created successfully
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Duplicate User with valid Emergency No
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Email ID in Duplicate screen
    And User enters new Phone No in Duplicate screen
    And User enters valid 10-digit Emergency No in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then duplicate User should be created successfully
    And User should be navigate into "Users" list

  @regression @p2
  Scenario: Duplicate User with valid 6-digit Pin Code
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Email ID in Duplicate screen
    And User enters new Phone No in Duplicate screen
    And User enters valid 6-digit Pin Code in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then duplicate User should be created successfully
    And User should be navigate into "Users" list


  # =========================================================
  # NEGATIVE — EMAIL VALIDATION
  # =========================================================

  @negative @regression @p1
  Scenario: Submit Duplicate User without changing Email ID
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Phone No in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then duplicate Email ID error should be displayed in Duplicate screen
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Duplicate User with invalid Email ID format
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters invalid Email ID format in Duplicate screen
    And User enters new Phone No in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then email format validation error should be displayed in Duplicate screen
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Duplicate User with empty Email ID
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User clears Email ID field in Duplicate screen
    And User enters new Phone No in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then "Email ID is required" should be displayed
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Duplicate User with only spaces in Email ID
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters only spaces in Email ID field in Duplicate screen
    And User enters new Phone No in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then "Email ID is required" should be displayed
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list


  # =========================================================
  # NEGATIVE — PHONE NO VALIDATION
  # =========================================================

  @negative @regression @p1
  Scenario: Submit Duplicate User without changing Phone No
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Email ID in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then duplicate Phone No error should be displayed in Duplicate screen
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list

  @negative @regression @p1
  Scenario: Submit Duplicate User without changing Email ID and Phone No
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User clicks Submit button in Duplicate User screen
    Then duplicate Email ID error should be displayed in Duplicate screen
    And duplicate Phone No error should be displayed in Duplicate screen
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Duplicate User with Phone No less than 10 digits
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Email ID in Duplicate screen
    And User enters Phone No with less than 10 digits in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then phone number format validation error should be displayed in Duplicate screen
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Duplicate User with non-numeric Phone No
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Email ID in Duplicate screen
    And User enters non-numeric value in Phone No in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then phone number format validation error should be displayed in Duplicate screen
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Duplicate User with only spaces in Phone No
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Email ID in Duplicate screen
    And User enters only spaces in Phone No field in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then "Phone No is required" should be displayed
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list


  # =========================================================
  # NEGATIVE — MANDATORY FIELD VALIDATION
  # =========================================================

  @negative @regression @p1
  Scenario: Duplicate User without User Name
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User clears User Name field in Duplicate screen
    And User enters new Email ID in Duplicate screen
    And User enters new Phone No in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then "User Name is required" should be displayed
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list

  @negative @regression @p1
  Scenario: Duplicate User without Blood Group
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Email ID in Duplicate screen
    And User enters new Phone No in Duplicate screen
    And User clears Blood Group selection in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then "Blood Group is required" should be displayed
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list

  @negative @regression @p1
  Scenario: Duplicate User without Role
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Email ID in Duplicate screen
    And User enters new Phone No in Duplicate screen
    And User clears Role selection in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then "Role is required" should be displayed
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Duplicate User with only spaces in User Name
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters only spaces in User Name field in Duplicate screen
    And User enters new Email ID in Duplicate screen
    And User enters new Phone No in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then "User Name is required" should be displayed
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list


  # =========================================================
  # NEGATIVE — DATE VALIDATION
  # =========================================================

  @negative @regression @p2
  Scenario: Duplicate User with DOB greater than DOJ
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Email ID in Duplicate screen
    And User enters new Phone No in Duplicate screen
    And User enters DOB greater than DOJ in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then DOB validation error should be displayed in Duplicate screen
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list

  @negative @regression @p2
  Scenario: Duplicate User with DOB as future date
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Email ID in Duplicate screen
    And User enters new Phone No in Duplicate screen
    And User enters DOB as future date in Duplicate screen
    And User clicks Submit button in Duplicate User screen
    Then DOB future date validation error should be displayed in Duplicate screen
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p2
  Scenario: Close Duplicate User screen with data shows Confirmation Alert
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Email ID in Duplicate screen
    And User clicks back arrow in Duplicate User screen
    Then "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then screen should be closed without saving Duplicate User data
    And User should be navigate into "Users" list

  @sanity @p2
  Scenario: Rapid multiple Submit clicks should prevent duplicate User creation
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    When User enters new Email ID in Duplicate screen
    And User enters new Phone No in Duplicate screen
    When User clicks Submit button multiple times quickly in Duplicate User screen
    Then system should prevent duplicate User creation in Duplicate screen

  @sanity @p3
  Scenario: Verify Duplicate User form can be scrolled
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    Then user should be able to scroll the Duplicate User form
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Duplicate User screen UI elements
    And User has already created a User
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created User Name
    And User waits for search results to load
    Then system should display matching User results
    When User clicks on the User record
    Then "View User" screen should be displayed
    When User clicks on Duplicate button
    Then "Duplicate User" screen should be displayed
    And User Name field should be visible in Duplicate screen
    And Email ID field should be visible in Duplicate screen
    And Phone No field should be visible in Duplicate screen
    And Blood Group dropdown should be visible in Duplicate screen
    And Roles dropdown should be visible in Duplicate screen
    And Submit button should be visible in Duplicate User screen
    When User clicks back arrow in Duplicate User screen
    Then User should be navigate into "Users" list
