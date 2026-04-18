Feature: Operator Creation
  As an admin
  I want to create a new operator in the system
  So that the operator can access the application

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

  Scenario: Verify navigation to Add Operator screen
    When click on add button
    Then verify that the user should navigate to the "Add New Operator" screen

  # ==================================================
  # ✅ POSITIVE SCENARIOS
  # ==================================================

  Scenario: Create operator with all valid details
    When click on add button
    And user enters all the following operator details:
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
    Then operator should be created successfully

  Scenario: Create operator with all fields filled
    When user enters all valid details including optional fields
    And I click on the submit button
    Then operator should be created successfully

  Scenario: Create operator without optional fields
    When user fills only mandatory fields
    And I click on the submit button
    Then operator should be created successfully

  Scenario: Create operator with valid email
    When user enters valid unique email
    And I click on the submit button
    Then operator should be created successfully

  Scenario: Create operator with rating
    When user selects star rating
    Then rating should be captured successfully

  Scenario: Create operator with valid DOB and DOJ
    When user selects valid dates
    And I click on the submit button
    Then operator should be created successfully

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  Scenario: Submit without mandatory fields
    When I click on the submit button without entering data
    Then error should be displayed below mandatory fields

  Scenario: Operator Name is mandatory
    When user leaves Operator Name empty
    And I click on the submit button
    Then "Operator Name is required" should be displayed below field

  Scenario: Missing Phone Number
    When user leaves Phone Number empty
    Then "Phone Number is required" should be displayed below field

  Scenario: Missing Blood Group
    When user does not select Blood Group
    Then "Blood Group is required" should be displayed below field

  Scenario: Invalid Phone Number (less than 10 digits)
    When user enters invalid phone number
    Then "Phone number must be 10 digits" should be displayed below field

  Scenario: Invalid Phone Number (more than 10 digits)
    When user enters more than 10 digits
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
    When user uploads unsupported image format
    Then error message should be displayed

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  Scenario: Enter only spaces in mandatory fields
    When user enters spaces in fields
    Then validation errors should be displayed

  Scenario: Enter maximum length values
    When user enters maximum allowed characters
    Then system should accept within limits

  Scenario: Enter special characters in fields
    When user enters special characters
    Then system should validate accordingly

  Scenario: Rapid star rating selection
    When user quickly selects multiple star ratings
    Then correct rating should be captured

  Scenario: Network failure during submission
    When user submits form without internet
    Then error message should be displayed

  Scenario: API failure during submission
    When backend returns failure response
    Then proper error message should be shown

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  Scenario: Verify Add Operator screen UI
    Then all fields and Submit button should be visible

  Scenario: Verify image upload UI
    Then image upload field should be visible and clickable

  Scenario: Verify star rating UI
    Then star rating component should be visible and interactive

  Scenario: Verify date picker popup
    When user clicks DOB or DOJ field
    Then date picker should be displayed

  Scenario: Verify scroll functionality
    Then user should be able to scroll entire form

  Scenario: Verify keyboard behavior
    Then keyboard should open and close properly

  Scenario: Verify Submit button behavior
    Then Submit button should validate mandatory fields

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