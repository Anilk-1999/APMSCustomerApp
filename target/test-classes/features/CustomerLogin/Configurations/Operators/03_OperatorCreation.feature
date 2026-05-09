@regression @smoke @create @p1
Feature: Operator Creation with Field and Validation Coverage

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Users" section is displayed
    When User clicks on "Operators" feature
    Then verify user navigates to "Operators" list screen

  # ==================================================
  # ✅ NAVIGATION
  # ==================================================

  @smoke @p1
  Scenario: Navigate to Add Operator screen
    When User clicks on add button
    Then Add Operator screen should be displayed

  # ==================================================
  # ✅ POSITIVE SCENARIOS
  # ==================================================

  @smoke @regression @p1
  Scenario: Create operator with all valid details
    When User clicks on add button
    And user enters all the following operator details:
      | Field              | Value             |
      | Operator Name      | anil kumar        |
      | Email Id           | anil@example1.com |
      | Phone Number       | 8765578188        |
      | Emergency Number   | 9090908165        |
      | Operator Code      | ANIL123           |
      | Blood Group        | A+                |
      | Address 1          | btm layout 1stage |
      | Address 2          | btm layout 2stage |
      | Pin Code           | 570026            |
      | City               | Mysore            |
      | State              | Karnataka         |
      | Country            | India             |
    And User clicks Submit button
    Then operator should be created successfully

  @regression @p1
  Scenario: Create operator with all fields including optional
    When user enters all valid details including optional fields
    And User clicks Submit button
    Then operator should be created successfully

  @regression @p1
  Scenario: Create operator with mandatory fields only
    When user fills only mandatory fields
    And User clicks Submit button
    Then operator should be created successfully

  @regression @p2
  Scenario: Create operator with valid email
    When User clicks on add button
    And user enters valid unique email
    And User clicks Submit button
    Then operator should be created successfully

  @regression @p2
  Scenario: Create operator with star rating
    When User clicks on add button
    And user selects star rating
    Then rating should be captured successfully

  @regression @p2
  Scenario: Create operator with valid DOB and DOJ
    When User clicks on add button
    And user selects valid dates
    And User clicks Submit button
    Then operator should be created successfully

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  @negative @regression @p1
  Scenario: Submit without mandatory fields
    When User clicks on add button
    And User clicks Submit button
    Then error should be displayed below mandatory fields

  @negative @regression @p1
  Scenario: Operator Name is mandatory
    When User clicks on add button
    And user leaves Operator Name empty
    And User clicks Submit button
    Then "Operator Name is required" should be displayed below field

  @negative @regression @p1
  Scenario: Phone Number is mandatory
    When User clicks on add button
    And user leaves Phone Number empty
    Then "Phone Number is required" should be displayed below field

  @negative @regression @p1
  Scenario: Blood Group is mandatory
    When User clicks on add button
    And user does not select Blood Group
    Then "Blood Group is required" should be displayed below field

  @negative @regression @p1
  Scenario: Invalid Phone Number (less than 10 digits)
    When User clicks on add button
    And user enters invalid phone number
    Then "Phone number must be 10 digits" should be displayed below field

  @negative @regression @p2
  Scenario: Invalid Phone Number (more than 10 digits)
    When User clicks on add button
    And user enters more than 10 digits
    Then "Phone number must be 10 digits" should be displayed below field

  @negative @regression @p2
  Scenario: Duplicate Phone Number
    When User clicks on add button
    And user enters existing phone number
    Then "Phone number already exists" should be displayed below field

  @negative @regression @p2
  Scenario: Invalid Email format
    When User clicks on add button
    And user enters invalid email
    Then "Invalid email format" should be displayed below Email field

  @negative @regression @p2
  Scenario: Duplicate Email
    When User clicks on add button
    And user enters existing email
    Then "Email already exists" should be displayed below Email field

  @negative @regression @p2
  Scenario: Invalid Pin Code
    When User clicks on add button
    And user enters invalid pin code
    Then "Pin Code must be 6 digits" should be displayed below field

  @negative @regression @p2
  Scenario: Invalid DOB - future date
    When User clicks on add button
    And user selects future DOB
    Then "DOB cannot be future date" should be displayed below DOB field

  @negative @regression @p2
  Scenario: Invalid DOJ - before DOB
    When User clicks on add button
    And DOJ is before DOB
    Then "DOJ cannot be before DOB" should be displayed below DOJ field

  @negative @regression @p3
  Scenario: Upload invalid image format
    When User clicks on add button
    And user uploads unsupported image format
    Then error message should be displayed

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  @negative @regression @p2
  Scenario: Enter only spaces in mandatory fields
    When User clicks on add button
    And user enters spaces in fields
    Then validation errors should be displayed

  @sanity @p3
  Scenario: Enter maximum length values
    When User clicks on add button
    And user enters maximum allowed characters
    Then system should accept or restrict within limits

  @negative @regression @p3
  Scenario: Enter special characters in fields
    When User clicks on add button
    And user enters special characters in operator fields
    Then system should validate accordingly

  @sanity @p3
  Scenario: Rapid star rating selection
    When User clicks on add button
    And user quickly selects multiple star ratings
    Then correct rating should be captured

  @negative @p3
  Scenario: Network failure during submission
    When User clicks on add button
    And user submits form without internet
    Then error message should be displayed

  @negative @p3
  Scenario: API failure during submission
    When User clicks on add button
    And backend returns failure response
    Then proper error message should be shown

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  @regression @p2
  Scenario: Verify Add Operator screen UI
    When User clicks on add button
    Then all fields and Submit button should be visible

  @regression @p3
  Scenario: Verify image upload UI
    When User clicks on add button
    Then image upload field should be visible and clickable

  @regression @p3
  Scenario: Verify star rating UI
    When User clicks on add button
    Then star rating component should be visible and interactive

  @regression @p2
  Scenario: Verify date picker popup
    When User clicks on add button
    And user clicks DOB or DOJ field
    Then date picker should be displayed

  @regression @p3
  Scenario: Verify scroll functionality
    When User clicks on add button
    Then user should be able to scroll operator form

  @regression @p3
  Scenario: Verify keyboard behavior
    When User clicks on add button
    Then keyboard should open and close properly

  @regression @p2
  Scenario: Verify Submit button validation
    When User clicks on add button
    Then Submit button should validate mandatory fields

  # ==================================================
  # 🔎 FIELD-LEVEL VALIDATION OUTLINES
  # ==================================================

  @regression @p3
  Scenario Outline: Validate Operator Name field
    When User clicks on add button
    And user enters "<input>" in Operator Name
    And User clicks Submit button
    Then "<error>" should be displayed below field

    Examples:
      | input | error                     |
      |       | Operator Name is required |

  @regression @p3
  Scenario Outline: Validate Phone Number field
    When User clicks on add button
    And user enters "<input>" in Phone Number
    Then "<error>" should be displayed below field

    Examples:
      | input | error                    |
      |       | Phone Number is required |
      | 123   | Must be 10 digits        |

  @regression @p3
  Scenario Outline: Validate Email field
    When User clicks on add button
    And user enters "<input>" in Email
    Then "<error>" should be displayed below Email field

    Examples:
      | input   | error                |
      | invalid | Invalid email format |

  @regression @p3
  Scenario Outline: Validate Pin Code field
    When User clicks on add button
    And user enters "<input>" in Pin Code
    Then "<error>" should be displayed below field

    Examples:
      | input | error            |
      | 123   | Must be 6 digits |
