@regression @view @p2
Feature: View Operator Details for Newly Created Operator

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Users" section is displayed
    When User clicks on "Operators" feature
    Then verify user navigates to "Operators" list screen
    And User has created an operator with all mandatory fields

  # ==================================================
  # ✅ NAVIGATION SCENARIOS
  # ==================================================

  @smoke @p1
  Scenario: Navigate to Operator View screen
    When User searches for newly created operator
    And User opens view screen for newly created operator
    Then Operator View screen should be displayed

  # ==================================================
  # ✅ POSITIVE SCENARIOS
  # ==================================================

  @regression @p1
  Scenario: Verify all operator details are displayed
    When User opens view screen for newly created operator
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

  @regression @p2
  Scenario: Verify data accuracy in view screen
    When User opens view screen for newly created operator
    Then all displayed data should match saved operator details

  @regression @p2
  Scenario: Verify optional fields display
    When User opens view screen for newly created operator
    And optional fields are available
    Then they should be displayed correctly
    And if not available, fields should be empty or hidden

  @regression @p2
  Scenario: Verify star rating display
    When operator has rating
    And User opens view screen for newly created operator
    Then correct number of stars should be displayed

  @regression @p2
  Scenario: Verify date format in view
    When User opens view screen for newly created operator
    And user views DOB and DOJ
    Then dates should be displayed in correct format

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  @negative @regression @p3
  Scenario: Verify invalid operator selection
    When user selects invalid or deleted operator
    Then error message should be displayed

  @negative @p3
  Scenario: Verify API failure while loading view
    When backend API fails during operator fetch
    Then proper error message should be shown

  @negative @p3
  Scenario: Verify network failure while opening view
    When user opens operator view without internet
    Then proper error message should be shown

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  @sanity @p3
  Scenario: Verify long operator name handling
    When operator name is very long
    Then it should be properly displayed or truncated

  @sanity @p3
  Scenario: Verify long address handling
    When address fields are long
    Then text should wrap correctly

  @sanity @p3
  Scenario: Verify special characters display
    When operator data contains special characters
    Then they should be displayed correctly

  @sanity @p3
  Scenario: Verify empty optional fields
    When optional fields are empty
    Then UI should not break

  @sanity @p3
  Scenario: Verify rapid navigation stability
    When user quickly opens multiple operator records
    Then application should not crash

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  @regression @p2
  Scenario: Verify Operator View screen UI elements
    When User opens view screen for newly created operator
    Then all labels and values should be visible

  @regression @p2
  Scenario: Verify fields are read-only
    When User opens view screen for newly created operator
    Then all fields should be non-editable

  @regression @p3
  Scenario: Verify scroll functionality
    When User opens view screen for newly created operator
    Then user should be able to scroll entire screen

  @regression @p2
  Scenario: Verify layout alignment
    When User opens view screen for newly created operator
    Then all fields should be properly aligned

  @regression @p3
  Scenario: Verify loading indicator
    When User opens view screen for newly created operator
    Then loading indicator should be displayed until data loads

  @regression @p3
  Scenario: Verify image display
    When User opens view screen for newly created operator
    Then operator image should be displayed correctly

  @regression @p3
  Scenario: Verify rating UI alignment
    When User opens view screen for newly created operator
    Then star rating should be visible and properly aligned

  # ==================================================
  # 🔎 FIELD DISPLAY VALIDATION
  # ==================================================

  @regression @p2
  Scenario: Verify phone number format
    When User opens view screen for newly created operator
    Then phone number should be displayed as 10 digits

  @regression @p2
  Scenario: Verify email format display
    When User opens view screen for newly created operator
    Then email should be displayed in valid format

  @regression @p2
  Scenario: Verify blood group display
    When User opens view screen for newly created operator
    Then blood group should be displayed correctly

  @regression @p2
  Scenario: Verify operator code display
    When User opens view screen for newly created operator
    Then operator code should be displayed correctly
