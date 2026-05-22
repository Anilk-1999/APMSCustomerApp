@regression @p2
Feature: Duplicate Operator Creation from Newly Created Operator

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
  Scenario: Navigate to Duplicate Operator screen
    When User searches for newly created operator
    And User swipes operator record right to left
    Then Duplicate option should be visible
    When User clicks on "Duplicate" option
    Then Add Operator screen should be displayed
    And all fields should be pre-filled with selected operator data

  # ==================================================
  # ✅ POSITIVE SCENARIOS
  # ==================================================

  @smoke @regression @p1
  Scenario: Create duplicate operator with updated unique fields
    When User opens duplicate screen for newly created operator
    And user updates Phone Number with a new unique value
    And user updates Email with a new unique value
    And User clicks Submit button
    Then new operator should be created successfully

  @regression @p2
  Scenario: Create duplicate operator without email
    When User opens duplicate screen for newly created operator
    And user clears Email field
    And user updates Phone Number with unique value
    And User clicks Submit button
    Then new operator should be created successfully

  @regression @p2
  Scenario: Create duplicate operator with all updated fields
    When User opens duplicate screen for newly created operator
    And user updates all fields including optional fields
    And User clicks Submit button
    Then new operator should be created successfully

  @regression @p2
  Scenario: Verify pre-filled data on duplicate screen
    When User opens duplicate screen for newly created operator
    Then all fields should be pre-filled with selected operator data

  @regression @p2
  Scenario: Verify duplicate operator appears in list
    When User opens duplicate screen for newly created operator
    And user updates Phone Number with a new unique value
    And User clicks Submit button
    And duplicate operator is created
    Then it should be visible in operator list

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  @negative @regression @p1
  Scenario: Create duplicate without changing Phone Number
    When User opens duplicate screen for newly created operator
    And user keeps same Phone Number
    And User clicks Submit button
    Then "Phone number already exists" should be displayed below field

  @negative @regression @p2
  Scenario: Create duplicate without changing Email
    When User opens duplicate screen for newly created operator
    And user keeps same Email
    And User clicks Submit button
    Then "Email already exists" should be displayed below Email field

  @negative @regression @p1
  Scenario: Missing mandatory fields in duplicate
    When User opens duplicate screen for newly created operator
    And user clears mandatory fields
    And User clicks Submit button
    Then error should be displayed below mandatory fields

  @negative @regression @p1
  Scenario: Operator Name is mandatory in duplicate
    When User opens duplicate screen for newly created operator
    And user clears Operator Name
    And User clicks Submit button
    Then "Operator Name is required" should be displayed below field

  @negative @regression @p2
  Scenario: Invalid Phone Number in duplicate
    When User opens duplicate screen for newly created operator
    And user enters invalid phone number
    Then "Phone number must be 10 digits" should be displayed below field

  @negative @regression @p2
  Scenario: Invalid Email format in duplicate
    When User opens duplicate screen for newly created operator
    And user enters invalid email
    Then "Invalid email format" should be displayed below Email field

  @negative @regression @p2
  Scenario: Invalid Pin Code in duplicate
    When User opens duplicate screen for newly created operator
    And user enters invalid pin code
    Then "Pin Code must be 6 digits" should be displayed below field

  @negative @regression @p2
  Scenario: Invalid DOB and DOJ in duplicate
    When User opens duplicate screen for newly created operator
    And user selects future DOB
    Then "DOB cannot be future date" should be displayed below DOB field

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  @negative @regression @p2
  Scenario: Enter only spaces in mandatory fields
    When User opens duplicate screen for newly created operator
    And user enters spaces in mandatory fields
    Then validation errors should be displayed

  @regression @p2
  Scenario: Modify only Phone Number
    When User opens duplicate screen for newly created operator
    And user updates Phone Number with unique value
    And User clicks Submit button
    Then new operator should be created successfully

  @sanity @p3
  Scenario: Rapid duplicate action
    When User opens duplicate screen for newly created operator
    And user quickly performs duplicate multiple times
    Then system should handle correctly

  @sanity @p3
  Scenario: Large data handling in duplicate
    When User opens duplicate screen for newly created operator
    And fields contain large values
    Then system should handle properly

  @negative @p3
  Scenario: Network failure during duplicate creation
    When User opens duplicate screen for newly created operator
    And user submits duplicate without internet
    Then error message should be displayed

  @negative @p3
  Scenario: API failure during duplicate creation
    When User opens duplicate screen for newly created operator
    And backend returns failure during duplication
    Then proper error message should be shown

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  @regression @p2
  Scenario: Verify Duplicate screen UI
    When User opens duplicate screen for newly created operator
    Then all fields and Submit button should be visible

  @regression @p2
  Scenario: Verify pre-filled data UI
    When User opens duplicate screen for newly created operator
    Then all fields should display copied data correctly

  @regression @p3
  Scenario: Verify image duplication
    When User opens duplicate screen for newly created operator
    Then operator image should be copied and displayed

  @regression @p3
  Scenario: Verify star rating duplication
    When User opens duplicate screen for newly created operator
    Then rating should be pre-filled correctly

  @regression @p2
  Scenario: Verify date picker on duplicate screen
    When User opens duplicate screen for newly created operator
    And user clicks DOB or DOJ field
    Then date picker should be displayed

  @regression @p3
  Scenario: Verify scroll functionality
    When User opens duplicate screen for newly created operator
    Then user should be able to scroll operator form

  @regression @p2
  Scenario: Verify swipe reveals Duplicate option
    When User searches for newly created operator
    And User swipes operator record right to left
    Then Duplicate option should be visible

  # ==================================================
  # 🔎 FIELD-LEVEL VALIDATION OUTLINES
  # ==================================================

  @regression @p3
  Scenario Outline: Validate Operator Name field on duplicate
    When User opens duplicate screen for newly created operator
    And user enters "<input>" in Operator Name
    And User clicks Submit button
    Then "<error>" should be displayed below field

    Examples:
      | input | error                     |
      |       | Operator Name is required |

  @regression @p3
  Scenario Outline: Validate Phone Number field on duplicate
    When User opens duplicate screen for newly created operator
    And user enters "<input>" in Phone Number
    Then "<error>" should be displayed below field

    Examples:
      | input | error                    |
      |       | Phone Number is required |
      | 123   | Must be 10 digits        |

  @regression @p3
  Scenario Outline: Validate Email field on duplicate
    When User opens duplicate screen for newly created operator
    And user enters "<input>" in Email
    Then "<error>" should be displayed below Email field

    Examples:
      | input   | error                |
      | invalid | Invalid email format |

  @regression @p3
  Scenario Outline: Validate Pin Code field on duplicate
    When User opens duplicate screen for newly created operator
    And user enters "<input>" in Pin Code
    Then "<error>" should be displayed below field

    Examples:
      | input | error            |
      | 123   | Must be 6 digits |
