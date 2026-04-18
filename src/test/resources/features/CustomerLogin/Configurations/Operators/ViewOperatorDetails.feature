Feature: View Operator Details
  As an admin
  I want to view operator details
  So that I can verify operator information

  Background:
    When click on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When click on "configurations" section
    Then verify the "User" section displayed
    When click on "Operators" feature
    Then verify that the user navigate to the "Operators" list screen

  # ==================================================
  # ✅ NAVIGATION SCENARIOS
  # ==================================================

  Scenario: Navigate to Operator View via search
    When user searches operator "anil@example.com"
    And user clicks on the operator record
    Then Operator View screen should be displayed

  Scenario: Navigate to Operator View via scrolling
    When user scrolls and selects operator "anil@example.com"
    And user clicks on the operator record
    Then Operator View screen should be displayed

  # ==================================================
  # ✅ POSITIVE SCENARIOS
  # ==================================================

  Scenario: Verify all operator details are displayed
    When user opens operator view screen
    Then following fields should be displayed:
      | Operator Image   |
      | Operator Name    |
      | Rating           |
      | Email            |
      | Phone Number     |
      | Emergency Number |
      | Operator Code    |
      | Blood Group      |
      | Date of Birth    |
      | Date of Joining  |
      | Address Line 1   |
      | Address Line 2   |
      | Pin Code         |
      | City             |
      | State            |
      | Country          |

  Scenario: Verify data accuracy for operator
    When user views operator "anil@example.com"
    Then all displayed data should match saved operator details

  Scenario: Verify optional fields display
    When optional fields are available
    Then they should be displayed correctly
    And if not available, fields should be empty or hidden

  Scenario: Verify rating display
    When operator has rating
    Then correct number of stars should be displayed

  Scenario: Verify date format display
    When user views DOB and DOJ
    Then dates should be displayed in correct format

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  Scenario: Verify invalid operator selection
    When user selects invalid or deleted operator
    Then error message should be displayed

  Scenario: Verify API failure while loading view
    When backend API fails during operator fetch
    Then error message should be displayed

  Scenario: Verify network failure while opening view
    When user opens operator view without internet
    Then proper error message should be shown

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  Scenario: Verify long operator name handling
    When operator name is very long
    Then it should be properly displayed or truncated

  Scenario: Verify long address handling
    When address fields are long
    Then text should wrap correctly

  Scenario: Verify special characters display
    When operator data contains special characters
    Then they should be displayed correctly

  Scenario: Verify empty optional fields
    When optional fields are empty
    Then UI should not break

  Scenario: Verify rapid navigation
    When user quickly opens multiple operator records
    Then application should not crash

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  Scenario: Verify Operator View screen UI elements
    Then all labels and values should be visible

  Scenario: Verify fields are read-only
    Then all fields should be non-editable

  Scenario: Verify scroll functionality
    Then user should be able to scroll entire screen

  Scenario: Verify layout alignment
    Then all fields should be properly aligned

  Scenario: Verify back navigation
    When user clicks back button
    Then verify that the user navigate to the "Operators" list screen

  Scenario: Verify loading indicator
    When user opens operator view screen
    Then loading indicator should be displayed until data loads

  Scenario: Verify image display
    Then operator image should be displayed correctly

  Scenario: Verify rating UI
    Then star rating should be visible and properly aligned

  # ==================================================
  # 🔎 FIELD-LEVEL / DISPLAY VALIDATION
  # ==================================================

  Scenario: Verify phone number format
    Then phone number should be displayed as 10 digits

  Scenario: Verify email format display
    Then email should be displayed in valid format

  Scenario: Verify blood group display
    Then blood group should be displayed correctly

  Scenario: Verify operator code display
    Then operator code should be displayed correctly