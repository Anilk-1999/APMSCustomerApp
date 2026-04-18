Feature: View User Details
  As an admin
  I want to view user details
  So that I can verify user information

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

  Scenario: Verify navigation to User View screen
    When user selects a user from list
    Then verify that the user should navigate to the "User View" screen

  Scenario: Verify all user details are displayed
    When user selects a user from list
    Then following fields should be displayed:
      | User Name        |
      | Email ID         |
      | Phone Number     |
      | Emergency Number |
      | Emp Code         |
      | Blood Group      |
      | Date of Birth    |
      | Date of Joining  |
      | Address Line 1   |
      | Address Line 2   |
      | Pin Code         |
      | City             |
      | State            |
      | Country          |
      | Role             |
      | Teams            |

  Scenario: Verify data accuracy in view screen
    When user selects user "anil@example.com" from list
    Then all displayed data should match saved user details

  Scenario: Verify optional fields display
    When user selects a user from list
    Then optional fields should be displayed if available
    And if not available, fields should be empty or hidden

  Scenario: Verify multiple team display
    When user selects a user from list
    Then all assigned teams should be displayed correctly

  Scenario: Verify date formats
    When user selects a user from list
    Then DOB and DOJ should be displayed in correct format

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  Scenario: Verify behavior when user data is missing
    When backend returns partial data for user
    Then system should handle gracefully without crash

  Scenario: Verify invalid user selection
    When user selects invalid or deleted user record
    Then error message should be displayed

  Scenario: Verify API failure on loading view screen
    When backend API fails during user fetch
    Then error message should be displayed

  Scenario: Verify network failure while loading screen
    When user opens view screen without internet
    Then proper error message should be shown

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  Scenario: Verify long text handling
    When user data contains long text
    Then text should be properly wrapped or truncated

  Scenario: Verify special characters display
    When user data contains special characters
    Then they should be displayed correctly

  Scenario: Verify empty optional fields
    When optional fields are empty
    Then UI should not break

  Scenario: Verify large number of teams
    When user has many teams assigned
    Then all should be displayed properly with scroll support

  Scenario: Verify rapid navigation
    When user quickly opens multiple user records
    Then app should handle without crash

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  Scenario: Verify UI elements on View screen
    When user selects a user from list
    Then all labels and values should be visible

  Scenario: Verify fields are read-only
    When user selects a user from list
    Then all fields should be non-editable

  Scenario: Verify scroll functionality
    Then user should be able to scroll full page

  Scenario: Verify layout alignment
    Then all fields should be properly aligned

  Scenario: Verify navigation back to User List
    When user clicks back button
    Then verify that the user navigate to the "Users" list screen

  Scenario: Verify loading indicator
    When user selects a user from list
    Then loading indicator should be displayed until data loads

  # ==================================================
  # 🔎 FIELD-LEVEL VALIDATION SCENARIOS
  # ==================================================

  Scenario Outline: Validate field values are displayed correctly
    When user views "<field>"
    Then "<value>" should be displayed correctly

    Examples:
      | field        | value           |
      | User Name    | Valid Name      |
      | Email ID     | valid@mail.com  |
      | Phone Number | 9876543210      |
      | Blood Group  | O+              |

  Scenario: Verify Email field is read-only
    When user tries to edit Email field
    Then editing should not be allowed

  Scenario: Verify Phone field is read-only
    When user tries to edit Phone field
    Then editing should not be allowed

  Scenario: Verify Role field is read-only
    When user tries to edit Role field
    Then editing should not be allowed