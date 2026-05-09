@regression @smoke @update @p1
Feature: Operator Update via Swipe Action for Newly Created Operator

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
  Scenario: Navigate to Edit Operator via swipe
    When User searches for newly created operator
    And User swipes operator record right to left
    And User clicks on "Edit" option
    Then User should navigate to Edit Operator screen

  # ==================================================
  # ✅ POSITIVE SCENARIOS
  # ==================================================

  @smoke @regression @p1
  Scenario: Update operator with valid data
    When User opens edit screen for newly created operator
    And user edits Operator Name
    And user updates Phone Number with valid unique number
    And user selects Blood Group
    And User clicks Submit button
    Then operator should be updated successfully

  @regression @p1
  Scenario: Update operator with all fields including optional
    When User opens edit screen for newly created operator
    And user updates all fields including optional fields
    And User clicks Submit button
    Then operator should be updated successfully

  @regression @p1
  Scenario: Update operator with mandatory fields only
    When User opens edit screen for newly created operator
    And user updates only mandatory fields
    And User clicks Submit button
    Then operator should be updated successfully

  @regression @p2
  Scenario: Update operator email with valid unique email
    When User opens edit screen for newly created operator
    And user updates Email with valid unique value
    And User clicks Submit button
    Then operator should be updated successfully

  @regression @p2
  Scenario: Update operator star rating
    When User opens edit screen for newly created operator
    And user changes star rating
    Then updated rating should be saved successfully

  @regression @p2
  Scenario: Update operator with valid DOB and DOJ
    When User opens edit screen for newly created operator
    And user updates Date of Birth and Date of Joining
    And User clicks Submit button
    Then operator should be updated successfully

  @regression @p2
  Scenario: Verify pre-filled data in Edit screen
    When User opens edit screen for newly created operator
    Then all fields should be pre-filled with existing operator data

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  @negative @regression @p1
  Scenario: Submit without mandatory fields
    When User opens edit screen for newly created operator
    And user clears mandatory fields
    And User clicks Submit button
    Then error should be displayed below mandatory fields

  @negative @regression @p1
  Scenario: Operator Name is mandatory
    When User opens edit screen for newly created operator
    And user clears Operator Name
    And User clicks Submit button
    Then "Operator Name is required" should be displayed below field

  @negative @regression @p1
  Scenario: Phone Number is mandatory
    When User opens edit screen for newly created operator
    And user clears Phone Number
    Then "Phone Number is required" should be displayed below field

  @negative @regression @p1
  Scenario: Blood Group is mandatory
    When User opens edit screen for newly created operator
    And user does not select Blood Group
    Then "Blood Group is required" should be displayed below field

  @negative @regression @p2
  Scenario: Invalid Phone Number
    When User opens edit screen for newly created operator
    And user enters invalid phone number
    Then "Phone number must be 10 digits" should be displayed below field

  @negative @regression @p2
  Scenario: Duplicate Phone Number
    When User opens edit screen for newly created operator
    And user enters existing phone number
    Then "Phone number already exists" should be displayed below field

  @negative @regression @p2
  Scenario: Invalid Email format
    When User opens edit screen for newly created operator
    And user enters invalid email
    Then "Invalid email format" should be displayed below Email field

  @negative @regression @p2
  Scenario: Duplicate Email
    When User opens edit screen for newly created operator
    And user enters existing email
    Then "Email already exists" should be displayed below Email field

  @negative @regression @p2
  Scenario: Invalid Pin Code
    When User opens edit screen for newly created operator
    And user enters invalid pin code
    Then "Pin Code must be 6 digits" should be displayed below field

  @negative @regression @p2
  Scenario: Invalid DOB - future date
    When User opens edit screen for newly created operator
    And user selects future DOB
    Then "DOB cannot be future date" should be displayed below DOB field

  @negative @regression @p2
  Scenario: Invalid DOJ - before DOB
    When User opens edit screen for newly created operator
    And DOJ is before DOB
    Then "DOJ cannot be before DOB" should be displayed below DOJ field

  @negative @regression @p3
  Scenario: Upload invalid image format
    When User opens edit screen for newly created operator
    And user uploads unsupported image
    Then error message should be displayed

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  @negative @regression @p2
  Scenario: Enter only spaces in mandatory fields
    When User opens edit screen for newly created operator
    And user enters spaces in fields
    And User clicks Submit button
    Then validation errors should be displayed

  @sanity @p3
  Scenario: Enter maximum length values
    When User opens edit screen for newly created operator
    And user enters maximum allowed characters
    Then system should accept or restrict within limits

  @negative @regression @p3
  Scenario: Enter special characters in fields
    When User opens edit screen for newly created operator
    And user enters special characters in operator fields
    Then system should validate accordingly

  @sanity @p3
  Scenario: Rapid rating change
    When User opens edit screen for newly created operator
    And user quickly changes star rating
    Then correct rating should be saved

  @negative @p3
  Scenario: Network failure during update
    When User opens edit screen for newly created operator
    And user submits update without internet
    Then error message should be displayed

  @negative @p3
  Scenario: API failure during update
    When User opens edit screen for newly created operator
    And backend returns failure during update
    Then proper error message should be shown

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  @regression @p2
  Scenario: Verify Edit Operator screen UI
    When User opens edit screen for newly created operator
    Then all fields and Submit button should be visible

  @regression @p2
  Scenario: Verify pre-filled data display
    When User opens edit screen for newly created operator
    Then all fields should display existing operator data

  @regression @p3
  Scenario: Verify image upload UI
    When User opens edit screen for newly created operator
    Then image field should be visible and clickable

  @regression @p3
  Scenario: Verify star rating UI
    When User opens edit screen for newly created operator
    Then rating component should be interactive

  @regression @p2
  Scenario: Verify date picker popup
    When User opens edit screen for newly created operator
    And user clicks DOB or DOJ field
    Then date picker should be displayed

  @regression @p3
  Scenario: Verify scroll functionality
    When User opens edit screen for newly created operator
    Then user should be able to scroll operator form

  @regression @p3
  Scenario: Verify keyboard behavior
    When User opens edit screen for newly created operator
    Then keyboard should open and close properly

  @regression @p2
  Scenario: Verify swipe gesture reveals Edit option
    When User searches for newly created operator
    And User swipes operator record right to left
    Then Edit option should be visible

  # ==================================================
  # 🔎 FIELD-LEVEL VALIDATION OUTLINES
  # ==================================================

  @regression @p3
  Scenario Outline: Validate Operator Name field on update
    When User opens edit screen for newly created operator
    And user enters "<input>" in Operator Name
    And User clicks Submit button
    Then "<error>" should be displayed below field

    Examples:
      | input | error                     |
      |       | Operator Name is required |

  @regression @p3
  Scenario Outline: Validate Phone Number field on update
    When User opens edit screen for newly created operator
    And user enters "<input>" in Phone Number
    Then "<error>" should be displayed below field

    Examples:
      | input | error                    |
      |       | Phone Number is required |
      | 123   | Must be 10 digits        |

  @regression @p3
  Scenario Outline: Validate Email field on update
    When User opens edit screen for newly created operator
    And user enters "<input>" in Email
    Then "<error>" should be displayed below Email field

    Examples:
      | input   | error                |
      | invalid | Invalid email format |

  @regression @p3
  Scenario Outline: Validate Pin Code field on update
    When User opens edit screen for newly created operator
    And user enters "<input>" in Pin Code
    Then "<error>" should be displayed below field

    Examples:
      | input | error            |
      | 123   | Must be 6 digits |
