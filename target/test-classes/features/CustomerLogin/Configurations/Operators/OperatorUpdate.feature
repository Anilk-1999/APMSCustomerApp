Feature: Operator Update
  As an admin
  I want to update operator details
  So that operator information remains accurate

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

  Scenario: Navigate to Edit Operator via search
    When user searches operator "anil@example.com"
    And user selects operator from search results
    And user swipes record from right to left
    And click on "Edit" option
    Then verify that the user should navigate to the "Edit Operator" screen

  Scenario: Navigate to Edit Operator via scrolling
    When user scrolls and selects operator "anil@example.com"
    And user swipes record from right to left
    And click on "Edit" option
    Then Edit Operator screen should be displayed

  # ==================================================
  # ✅ POSITIVE SCENARIOS
  # ==================================================

  Scenario: Update operator with valid data
    When user opens edit screen for operator "anil@example.com"
    And user edits Operator Name
    And user updates Phone Number with valid unique number
    And user selects Blood Group
    And I click on the submit button
    Then operator should be updated successfully

  Scenario: Update operator with all fields
    When user updates all fields including optional fields
    And I click on the submit button
    Then operator should be updated successfully

  Scenario: Update operator without optional fields
    When user updates only mandatory fields
    And I click on the submit button
    Then operator should be updated successfully

  Scenario: Update operator email with valid unique email
    When user updates Email with valid unique value
    And I click on the submit button
    Then operator should be updated successfully

  Scenario: Update operator rating
    When user changes star rating
    Then updated rating should be saved successfully

  Scenario: Update operator with valid DOB and DOJ
    When user updates Date of Birth and Date of Joining
    And I click on the submit button
    Then operator should be updated successfully

  Scenario: Verify pre-filled data in Edit screen
    When user opens edit screen for operator "anil@example.com"
    Then all fields should be pre-filled with existing operator data

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  Scenario: Submit without mandatory fields
    When user clears mandatory fields
    And I click on the submit button
    Then error should be displayed below mandatory fields

  Scenario: Operator Name is mandatory
    When user clears Operator Name
    And I click on the submit button
    Then "Operator Name is required" should be displayed below field

  Scenario: Missing Phone Number
    When user clears Phone Number
    Then "Phone Number is required" should be displayed below field

  Scenario: Missing Blood Group
    When user does not select Blood Group
    Then "Blood Group is required" should be displayed below field

  Scenario: Invalid Phone Number
    When user enters invalid phone number
    Then "Phone number must be 10 digits" should be displayed below field

  Scenario: Duplicate Phone Number
    When user enters existing phone number
    Then "Phone number already exists" should be displayed below field

  Scenario: Invalid Email format
    When user enters invalid email
    Then "Invalid email format" should be displayed below Email field

  Scenario: Duplicate Email
    When user enters existing email
    Then "Email already exists" should be displayed below Email field

  Scenario: Invalid Pin Code
    When user enters invalid pin code
    Then "Pin Code must be 6 digits" should be displayed below field

  Scenario: Invalid DOB (future date)
    When user selects future DOB
    Then "DOB cannot be future date" should be displayed below DOB field

  Scenario: Invalid DOJ (before DOB)
    When DOJ is before DOB
    Then "DOJ cannot be before DOB" should be displayed below DOJ field

  Scenario: Upload invalid image format
    When user uploads unsupported image
    Then error message should be displayed

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  Scenario: Enter only spaces in mandatory fields
    When user enters spaces in fields
    And I click on the submit button
    Then validation errors should be displayed

  Scenario: Enter maximum length values
    When user enters maximum allowed characters
    Then system should accept within limits

  Scenario: Enter special characters in fields
    When user enters special characters
    Then system should validate accordingly

  Scenario: Rapid rating change
    When user quickly changes star rating
    Then correct rating should be saved

  Scenario: Network failure during update
    When user submits update without internet
    Then error message should be displayed

  Scenario: API failure during update
    When backend returns failure during update
    Then proper error message should be shown

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  Scenario: Verify Edit Operator screen UI
    Then all fields and Submit button should be visible

  Scenario: Verify pre-filled data
    Then all fields should display existing operator data

  Scenario: Verify image upload UI
    Then image field should be visible and clickable

  Scenario: Verify star rating UI
    Then rating component should be interactive

  Scenario: Verify date picker popup
    When user clicks DOB or DOJ
    Then date picker should be displayed

  Scenario: Verify scroll functionality
    Then user should be able to scroll form

  Scenario: Verify keyboard behavior
    Then keyboard should open and close properly

  Scenario: Verify swipe gesture for Edit
    When user swipes record from right to left
    Then Edit option should be visible

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