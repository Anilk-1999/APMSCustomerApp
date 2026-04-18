Feature: Duplicate User Creation
  As an admin
  I want to duplicate an existing user
  So that I can quickly create similar users

  Background:
    When click on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When click on "configurations" section
    Then verify the "User" section displayed
    When click on "Users" feature
    Then verify that the user navigate to the "Users" list screen

  # ==================================================
  # ✅ POSITIVE SCENARIOS
  # ==================================================

  Scenario: Verify navigation to Duplicate User page via swipe
    When user swipes a user record from right to left
    And click on duplicate icon
    Then verify that the user should navigate to the "Duplicate User" screen

  Scenario: Verify pre-filled data in duplicate screen
    When user selects a user from list
    And click on duplicate button
    Then all fields should be pre-filled with existing user data

  Scenario: Create duplicate user with new Email and Phone
    When user selects a user from list
    And click on duplicate button
    And update user details:
      | Field        | Value              |
      | Email Id     | newuser@test.com   |
      | Phone Number | 9876543210         |
    And I click on the submit button
    Then I should see a confirmation message "User created successfully"

  Scenario: Create duplicate user with all valid updated fields
    When user selects a user from list
    And click on duplicate button
    And update all the following user details:
      | Field            | Value              |
      | Email Id         | newuser1@test.com  |
      | Phone Number     | 8765432109         |
      | User Name        | copied user        |
      | Blood Group      | A+                 |
      | Roles            | Admin              |
    And I click on the submit button
    Then I should see a confirmation message "User created successfully"

  Scenario: Create duplicate user with optional fields
    When user selects a user from list
    And click on duplicate button
    And update user details:
      | Field        | Value              |
      | Email Id     | newuser2@test.com  |
      | Phone Number | 7654321098         |
      | Address 1    | Bangalore          |
    And I click on the submit button
    Then I should see a confirmation message "User created successfully"

  Scenario: Verify duplicate creation with valid DOB and DOJ
    When user selects a user from list
    And click on duplicate button
    And update user details:
      | Field        | Value              |
      | Email Id     | newuser3@test.com  |
      | Phone Number | 6543210987         |
      | DOB          | 01-01-2000         |
      | DOJ          | 01-01-2022         |
    And I click on the submit button
    Then I should see a confirmation message "User created successfully"

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  Scenario: Attempt duplicate without changing Email and Phone
    When user selects a user from list
    And click on duplicate button
    And I click on the submit button
    Then "Email already exists" should be displayed
    And "Phone number already exists" should be displayed

  Scenario: Duplicate with existing Email only
    When user selects a user from list
    And click on duplicate button
    And update user details:
      | Field        | Value        |
      | Phone Number | 9876543210   |
    And I click on the submit button
    Then "Email already exists" should be displayed

  Scenario: Duplicate with existing Phone only
    When user selects a user from list
    And click on duplicate button
    And update user details:
      | Field    | Value            |
      | Email Id | new@test.com     |
    And I click on the submit button
    Then "Phone number already exists" should be displayed

  Scenario: Missing Email in duplicate flow
    When user selects a user from list
    And click on duplicate button
    And clear Email field
    And I click on the submit button
    Then "Email ID is required" should be displayed

  Scenario: Missing Phone Number in duplicate flow
    When user selects a user from list
    And click on duplicate button
    And clear Phone Number field
    And I click on the submit button
    Then "Phone Number is required" should be displayed

  Scenario: Invalid Email format
    When user enters invalid email
    Then "Invalid email format" should be displayed

  Scenario: Invalid Phone Number
    When user enters invalid phone number
    Then "Phone number must be 10 digits" should be displayed

  Scenario: Missing mandatory fields in duplicate flow
    When user selects a user from list
    And click on duplicate button
    And clear all mandatory fields
    And I click on the submit button
    Then error should be displayed below respective fields

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  Scenario: Enter only spaces in Email and Phone
    When user enters spaces in Email and Phone
    And I click on the submit button
    Then validation errors should be displayed

  Scenario: Enter maximum length values
    When user enters maximum allowed characters
    Then system should accept values within limit

  Scenario: Enter special characters in fields
    When user enters special characters
    Then system should validate accordingly

  Scenario: Network failure during duplicate creation
    When user clicks submit without internet
    Then error message should be displayed

  Scenario: API failure during duplicate creation
    When backend returns error
    Then proper error message should be shown

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  Scenario: Verify Duplicate page UI elements
    When user selects a user from list
    And click on duplicate button
    Then all fields and submit button should be visible

  Scenario: Verify pre-filled values
    When user selects a user from list
    And click on duplicate button
    Then Email and Phone should be editable
    And other fields should be pre-filled

  Scenario: Verify swipe gesture for Duplicate option
    When user swipes record from right to left
    Then duplicate option should be visible

  Scenario: Verify Submit button behavior
    Then submit button should validate mandatory fields

  Scenario: Verify scroll functionality
    Then user should be able to scroll form

  Scenario: Verify keyboard behavior
    Then keyboard should open and close properly

  Scenario: Verify date picker popup
    When user clicks DOB or DOJ field
    Then date picker popup should be displayed

  # ==================================================
  # 🔎 FIELD-LEVEL VALIDATION SCENARIOS
  # ==================================================

  Scenario Outline: Validate Email field
    When User enters "<input>" in Email
    Then "<error>" should be displayed below Email field

    Examples:
      | input   | error                |
      |         | Email ID is required |
      | invalid | Invalid email format |

  Scenario Outline: Validate Phone field
    When User enters "<input>" in Phone
    Then "<error>" should be displayed below Phone field

    Examples:
      | input | error                    |
      |       | Phone Number is required |
      | 123   | Must be 10 digits        |
      | abc   | Invalid number           |

  Scenario Outline: Validate Pin Code field
    When User enters "<input>" in Pin Code
    Then "<error>" should be displayed below Pin Code field

    Examples:
      | input  | error            |
      | 123    | Must be 6 digits |
      | abc123 | Invalid Pin Code |