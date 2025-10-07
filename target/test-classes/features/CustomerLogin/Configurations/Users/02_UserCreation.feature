Feature: User Creation
  As an admin
  I want to create a new user in the system
  So that the user can access the application

  Scenario: Create a new user with valid details
    When click on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When click on "configurations" section
    Then verify the "User" section displayed
    When click on "Users" feature
    Then verify that the user navigate to the "Users" list screen
    When click on add button
    Then verify that the user should navigate to the "Add New User" screen
    When enter all the following user details:
      | Field              | Value            |
      | User Name          | anil kumar       |
      | Email Id           | anil@example1.com|
      | Phone Number       | 8765578188       |
      | Emergency Number   | 9090908165       |
      | Emp Code           | ANIL123          |
      | Blood Group        | A+               |
      | Address 1          | btm layout 1stage|
      | Address 2          | btm layout 2stage|
      | Pin Code           | 570026           |
      | Roles              | Admin            |
    And I click on the submit button
    Then I should see a confirmation message "User created successfully"
    And the user should be created successfully