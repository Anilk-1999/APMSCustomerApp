Feature: User Creation
  As an admin
  I want to create a new user in the system
  So that the user can access the application

  Background:
    When click on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When click on "configurations" section
    Then verify the "User" section displayed
    When click on "Users" feature
    Then verify that the user navigate to the "Users" list screen
    When click on add button
    Then verify that the user should navigate to the "Add New User" screen

  # ==================================================
  # ✅ POSITIVE SCENARIOS (Mapped to Existing Flow)
  # ==================================================

  Scenario: Create user with all valid mandatory fields
    When enter mandatory user details:
      | Field         | Value              |
      | User Name     | anil kumar         |
      | Email Id      | anil123@test.com   |
      | Phone Number  | 9876543210         |
      | Blood Group   | A+                 |
      | Roles         | Admin              |
    And I click on the submit button
    Then I should see a confirmation message "User created successfully"

  Scenario: Create user with all fields
    When enter all the following user details:
      | Field              | Value              |
      | User Name          | anil kumar         |
      | Email Id           | anil456@test.com   |
      | Phone Number       | 8765432109         |
      | Emergency Number   | 9090909090         |
      | Emp Code           | EMP001             |
      | Blood Group        | B+                 |
      | Address 1          | Bangalore          |
      | Address 2          | Karnataka          |
      | Pin Code           | 560001             |
      | Roles              | Admin              |
    And I click on the submit button
    Then I should see a confirmation message "User created successfully"

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  Scenario: Submit form without entering any data
    When I click on the submit button
    Then error should be displayed below each mandatory field

  Scenario Outline: Validate mandatory fields
    When enter user details:
      | Field        | Value    |
      | <Field>      | <Value>  |
    And I click on the submit button
    Then "<Error>" should be displayed

    Examples:
      | Field        | Value | Error                      |
      | User Name    |       | User Name is required      |
      | Email Id     |       | Email ID is required       |
      | Phone Number |       | Phone Number is required   |
      | Roles        |       | Role is required           |

  Scenario: Invalid Email format
    When enter user details:
      | Field     | Value        |
      | Email Id  | invalidemail |
    Then "Invalid email format" should be displayed

  Scenario: Duplicate Email
    When enter user details:
      | Field     | Value              |
      | Email Id  | anil@example1.com  |
    Then "Email already exists" should be displayed

  Scenario: Invalid Phone Number
    When enter user details:
      | Field        | Value |
      | Phone Number | 123   |
    Then "Phone number must be 10 digits" should be displayed

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  Scenario: Enter only spaces in mandatory fields
    When enter user details:
      | Field        | Value |
      | User Name    | "   " |
      | Email Id     | "   " |
    And I click on the submit button
    Then validation errors should be displayed

  Scenario: Network failure during submission
    When user submits form without internet
    Then error message should be displayed

  Scenario: API failure response
    When backend returns error during user creation
    Then proper error message should be shown

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  Scenario: Verify Create User page UI elements
    Then all input fields, dropdowns and buttons should be visible

  Scenario: Verify Submit button behavior
    Then Submit button should be disabled when mandatory fields are empty

  Scenario: Verify dropdown values
    When click on Roles dropdown
    Then list of roles should be displayed