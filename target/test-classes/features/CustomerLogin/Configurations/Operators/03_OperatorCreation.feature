Feature: Operator Creation
  As an admin
  I want to create a new operator in the system
  So that the user can access the application

  Scenario: Create a new operator with valid details
    # When user is come out from the user list screen
    When click on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When click on "configurations" section
    And verify the "User" section displayed
    When click on "Operators" feature
    Then verify that the user navigate to the "Operators" list screen
    When click on add button
    Then verify that the user should navigate to the "Add New Operator" screen
    When enter all the following operator details:
      | Field              | Value            |
      | Operator Name      | anil kumar       |
      | Email Id           | anil@example1.com|
      | Phone Number       | 8765578188       |
      | Emergency Number   | 9090908165       |
      | Operator Code      | ANIL123          |
      | Blood Group        | A+               |
      | Address 1          | btm layout 1stage|
      | Address 2          | btm layout 2stage|
      | Pin Code           | 570026           |
      | City               | Mysore           |
      | State              | Karnataka        |
      | Country            | India            |
    And I click on the submit button
    Then I should see a confirmation message "Operator created successfully"
    And the operator should be created successfully