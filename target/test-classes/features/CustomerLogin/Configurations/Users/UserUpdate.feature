Feature: User Update
  As an admin
  I want to update existing user details
  So that user information remains accurate

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

  Scenario: Verify navigation to Edit User page via swipe action
    When user swipes a user record from right to left
    And click on edit icon
    Then verify that the user should navigate to the "Edit User" screen

  Scenario: Verify page redirection using menu navigation
    When user navigates to edit screen via menu
    Then verify that the user should navigate to the "Edit User" screen

  Scenario: Update user with valid data
    When user selects a user from list
    And click on edit button
    And update user details:
      | Field        | Value        |
      | User Name    | updated name |
      | Phone Number | 9876543210   |
      | Blood Group  | O+           |
      | Roles        | Admin        |
    And I click on the submit button
    Then I should see a confirmation message "User updated successfully"

  Scenario: Update user with optional fields
    When user selects a user from list
    And click on edit button
    And update user details:
      | Field      | Value      |
      | Address 1  | Bangalore  |
      | Address 2  | Karnataka  |
      | City       | Bangalore  |
      | State      | Karnataka  |
      | Country    | India      |
    And select multiple teams
    And I click on the submit button
    Then I should see a confirmation message "User updated successfully"

  Scenario: Update user with valid Emergency Number
    When user selects a user from list
    And click on edit button
    And update user details:
      | Field            | Value      |
      | Emergency Number | 9090909090 |
    And I click on the submit button
    Then I should see a confirmation message "User updated successfully"

  Scenario: Update user with valid DOB and DOJ
    When user selects a user from list
    And click on edit button
    And update user details:
      | Field | Value      |
      | DOB   | 01-01-2000 |
      | DOJ   | 01-01-2022 |
    And I click on the submit button
    Then I should see a confirmation message "User updated successfully"

  Scenario: Verify Email field is non-editable
    When user selects a user from list
    And click on edit button
    Then Email field should be disabled

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  Scenario: Save without mandatory fields
    When user selects a user from list
    And click on edit button
    And clear all mandatory fields
    And I click on the submit button
    Then error should be displayed below each mandatory field

  Scenario: Missing User Name
    When user clears User Name
    And I click on the submit button
    Then "User Name is required" should be displayed

  Scenario: Missing Phone Number
    When user clears Phone Number
    And I click on the submit button
    Then "Phone Number is required" should be displayed

  Scenario: Missing Blood Group
    When user clears Blood Group
    And I click on the submit button
    Then "Blood Group is required" should be displayed

  Scenario: Missing Role
    When user clears Role
    And I click on the submit button
    Then "Role is required" should be displayed

  Scenario: Invalid Phone Number
    When user selects a user from list
    And click on edit button
    And update user details:
      | Field        | Value |
      | Phone Number | 123   |
    Then "Phone number must be 10 digits" should be displayed

  Scenario: Duplicate Phone Number
    When user selects a user from list
    And click on edit button
    And update user details:
      | Field        | Value      |
      | Phone Number | 8765578188 |
    Then "Phone number already exists" should be displayed

  Scenario: Invalid Emergency Number
    When user enters invalid Emergency Number
    Then "Emergency number must be 10 digits" should be displayed

  Scenario: Invalid Pin Code
    When user enters invalid Pin Code
    Then "Pin Code must be 6 digits" should be displayed

  Scenario: Invalid DOB (future date)
    When user enters future DOB
    Then "DOB cannot be future date" should be displayed

  Scenario: Invalid DOJ (before DOB)
    When user enters DOJ before DOB
    Then "DOJ cannot be before DOB" should be displayed

  Scenario: Attempt to edit Email field
    When user tries to modify Email field
    Then system should not allow editing

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  Scenario: Enter only spaces in mandatory fields
    When user selects a user from list
    And click on edit button
    And update user details:
      | Field     | Value |
      | User Name | "   " |
    And I click on the submit button
    Then validation errors should be displayed

  Scenario: Enter maximum length values
    When user enters maximum allowed characters
    Then system should accept values within limit

  Scenario: Enter special characters
    When user enters special characters
    Then system should validate accordingly

  Scenario: Network failure during update
    When user clicks submit without internet
    Then error message should be displayed

  Scenario: API failure response during update
    When backend returns error during update
    Then proper error message should be shown

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  Scenario: Verify Edit User page UI elements
    When user selects a user from list
    And click on edit button
    Then all fields and submit button should be visible

  Scenario: Verify pre-filled data in Edit screen
    When user selects a user from list
    And click on edit button
    Then existing user data should be displayed

  Scenario: Verify Save button behavior
    Then submit button should validate mandatory fields

  Scenario: Verify swipe gesture for Edit option
    When user swipes record from right to left
    Then edit option should be visible

  Scenario: Verify scroll functionality
    Then user should be able to scroll entire form

  Scenario: Verify keyboard behavior
    Then keyboard should open and close properly

  Scenario: Verify date picker popup
    When user clicks DOB or DOJ field
    Then date picker popup should be displayed

  # ==================================================
  # 🔎 FIELD-LEVEL VALIDATION SCENARIOS
  # ==================================================

  Scenario Outline: Validate User Name field
    When User enters "<input>" in User Name
    Then "<error>" should be displayed below User Name field

    Examples:
      | input | error                 |
      |       | User Name is required |

  Scenario Outline: Validate Phone field
    When User enters "<input>" in Phone
    Then "<error>" should be displayed below Phone field

    Examples:
      | input | error                    |
      |       | Phone Number is required |
      | 123   | Must be 10 digits        |
      | abc   | Invalid number           |

  Scenario Outline: Validate Emergency Number field
    When User enters "<input>" in Emergency Number
    Then "<error>" should be displayed below Emergency field

    Examples:
      | input | error             |
      | 123   | Must be 10 digits |
      | abc   | Invalid number    |

  Scenario Outline: Validate Pin Code field
    When User enters "<input>" in Pin Code
    Then "<error>" should be displayed below Pin Code field

    Examples:
      | input  | error            |
      | 123    | Must be 6 digits |
      | abc123 | Invalid Pin Code |