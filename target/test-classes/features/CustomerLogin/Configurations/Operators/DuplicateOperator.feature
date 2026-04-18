Feature: Duplicate Operator Creation
  As an admin
  I want to duplicate an operator
  So that I can quickly create similar operator records

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

  Scenario: Navigate to Duplicate Operator screen
    When user searches operator "anil@example.com"
    And user swipes operator record from right to left
    Then Duplicate option should be visible
    When user clicks on Duplicate option
    Then verify that the user should navigate to the "Add New Operator" screen
    And all fields should be pre-filled with selected operator data

  # ==================================================
  # ✅ POSITIVE SCENARIOS
  # ==================================================

  Scenario: Create duplicate operator with updated unique fields
    When user updates Phone Number with a new unique value
    And user updates Email with a new unique value
    And I click on the submit button
    Then new operator should be created successfully

  Scenario: Create duplicate operator without email
    When user clears Email field
    And user updates Phone Number with unique value
    And I click on the submit button
    Then operator should be created successfully

  Scenario: Create duplicate operator with all updated fields
    When user updates all fields including optional fields
    And I click on the submit button
    Then operator should be created successfully

  Scenario: Verify pre-filled data in duplicate screen
    When user opens duplicate screen for operator "anil@example.com"
    Then all fields should be pre-filled with original operator data

  Scenario: Verify duplicate operator appears in list
    When duplicate operator is created
    Then it should be visible in operator list

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  Scenario: Create duplicate without changing Phone Number
    When user keeps same Phone Number
    And I click on the submit button
    Then "Phone number already exists" should be displayed

  Scenario: Create duplicate without changing Email (if exists)
    When user keeps same Email
    And I click on the submit button
    Then "Email already exists" should be displayed

  Scenario: Missing mandatory fields
    When user clears mandatory fields
    And I click on the submit button
    Then error should be displayed below fields

  Scenario: Operator Name is mandatory
    When user clears Operator Name
    And I click on the submit button
    Then "Operator Name is required" should be displayed

  Scenario: Invalid Phone Number
    When user enters invalid phone number
    Then "Phone number must be 10 digits" should be displayed

  Scenario: Invalid Email format
    When user enters invalid email
    Then "Invalid email format" should be displayed

  Scenario: Invalid Pin Code
    When user enters invalid pin code
    Then "Pin Code must be 6 digits" should be displayed

  Scenario: Invalid DOB and DOJ
    When user selects invalid dates
    Then proper validation messages should be displayed

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  Scenario: Enter only spaces in fields
    When user enters spaces in mandatory fields
    Then validation errors should be displayed

  Scenario: Modify only one field
    When user updates only Phone Number
    And I click on the submit button
    Then operator should be created successfully

  Scenario: Rapid duplicate action
    When user quickly performs duplicate multiple times
    Then system should handle correctly

  Scenario: Large data handling
    When fields contain large values
    Then system should handle properly

  Scenario: Network failure during duplicate creation
    When user submits duplicate without internet
    Then error message should be displayed

  Scenario: API failure during duplicate creation
    When backend returns failure during duplication
    Then proper error message should be shown

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  Scenario: Verify Duplicate screen UI
    Then all fields and Submit button should be visible

  Scenario: Verify pre-filled data UI
    Then all fields should display copied data correctly

  Scenario: Verify image duplication
    Then operator image should be copied and displayed

  Scenario: Verify star rating duplication
    Then rating should be pre-filled correctly

  Scenario: Verify date picker UI
    When user clicks DOB or DOJ
    Then date picker should be displayed

  Scenario: Verify scroll functionality
    Then user should be able to scroll form

  Scenario: Verify swipe gesture for duplicate
    When user swipes record from right to left
    Then Duplicate option should be visible

  # ==================================================
  # 🔎 FIELD-LEVEL VALIDATION SCENARIOS
  # ==================================================

  Scenario Outline: Validate Operator Name field
    When user enters "<input>" in Operator Name
    And I click on the submit button
    Then "<error>" should be displayed below field

    Examples:
      | input | error                     |
      |       | Operator Name is required |

  Scenario Outline: Validate Phone Number field
    When user enters "<input>" in Phone Number
    Then "<error>" should be displayed below field

    Examples:
      | input | error                    |
      |       | Phone Number is required |
      | 123   | Must be 10 digits        |
      | abc   | Invalid number           |

  Scenario Outline: Validate Email field
    When user enters "<input>" in Email
    Then "<error>" should be displayed below field

    Examples:
      | input   | error                |
      | invalid | Invalid email format |

  Scenario Outline: Validate Pin Code field
    When user enters "<input>" in Pin Code
    Then "<error>" should be displayed below field

    Examples:
      | input  | error            |
      | 123    | Must be 6 digits |
      | abc123 | Invalid Pin Code |