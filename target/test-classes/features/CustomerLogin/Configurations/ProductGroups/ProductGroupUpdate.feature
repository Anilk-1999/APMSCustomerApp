@regression @smoke @update @p1
Feature: Update Product Group via Swipe Action from Product Group List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Product Groups" feature
    Then verify user navigates to "Product Groups" list screen
    And User has already created a Product Group

  # ==================================================
  # ✅ NAVIGATION SCENARIOS
  # ==================================================

  @smoke @p1
  Scenario: Search and open Edit Product Group popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Group Name
    And User waits for search results to load
    Then system should display matching product group results
    And User verifies product group appears in list
    When User swipes product group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Group" popup should be displayed
    And Product Group ID should be visible and non-editable
    And Status field should be visible and non-editable
    And Product Group Name field should be pre-filled
    And Product Group Code field should be pre-filled
    And Description field should be pre-filled
    And Close "X" button should be visible

  # ==================================================
  # ✅ POSITIVE SCENARIOS
  # ==================================================

  @smoke @regression @p1
  Scenario: Update Product Group with valid data
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User updates Product Group Name with valid value
    And User updates Product Group Code with unique value
    And User updates Description
    And User clicks Save button
    Then Product Group should be updated successfully
    And updated data should be reflected in Product Groups list screen

  @regression @p2
  Scenario: Update Product Group with mandatory fields only
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User updates Product Group Name
    And User updates Product Group Code with unique value
    And User clears Description
    And User clicks Save button
    Then Product Group should be updated successfully

  @regression @p2
  Scenario: Update Product Group without modifying all fields
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User updates only Product Group Name
    And User clicks Save button
    Then Product Group should be updated successfully

  @regression @p2
  Scenario: Update Product Group description only
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User updates only Description
    And User clicks Save button
    Then Product Group should be updated successfully

  @regression @p2
  Scenario: Update Product Group code only
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User updates only Product Group Code with unique value
    And User clicks Save button
    Then Product Group should be updated successfully

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  @negative @regression @p1
  Scenario: Update Product Group without Name
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User clears Product Group Name field
    And User clicks Save button
    Then "Product Group Name is required" should be displayed

  @negative @regression @p1
  Scenario: Update Product Group without Code
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User clears Product Group Code field
    And User clicks Save button
    Then "Product Group Code is required" should be displayed

  @negative @regression @p2
  Scenario: Duplicate Product Group Code during update
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User enters existing Product Group Code
    And User clicks Save button
    Then "Product Group Code already exists" should be displayed

  @negative @regression @p2
  Scenario: Duplicate Product Group Name during update
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User enters existing Product Group Name
    And User clicks Save button
    Then "Product Group already exists" should be displayed

  @negative @regression @p2
  Scenario: Save without any changes
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User clicks Save button without modification
    Then system should display "No changes detected"

  @negative @regression @p2
  Scenario: Update Product Group with only spaces in Name
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User enters only spaces in Product Group Name field
    And User clicks Save button
    Then "Product Group Name is required" should be displayed

  @negative @regression @p2
  Scenario: Update Product Group with invalid special characters in fields
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User enters only special characters in Product Group Name and Product Group Code
    And User clicks Save button
    Then validation error should be displayed

  @negative @regression @p2
  Scenario: Update Product Group Code with spaces
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User enters Product Group Code with spaces
    And User clicks Save button
    Then validation error should be displayed for Product Group Code

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  @sanity @p3
  Scenario: Rapid swipe action multiple times
    When User searches for newly created Product Group Name
    And User performs swipe action multiple times quickly on product group record
    Then only one Edit option should be displayed

  @sanity @p3
  Scenario: Rapid multiple Save clicks
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User updates Product Group Name with valid value
    And User clicks Save button multiple times quickly
    Then system should prevent duplicate update requests

  @sanity @p3
  Scenario: Long input overflow handling
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User enters very large text beyond allowed limit
    Then system should restrict additional input or show validation error

  @sanity @p3
  Scenario: Network failure during update
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User clicks Save button without internet connection
    Then system should display network error message

  @sanity @p3
  Scenario: Session timeout during update
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And session expires while editing Product Group
    Then User should be redirected to login screen

  @regression @p2
  Scenario: Close popup without saving updated changes
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User modifies Product Group details
    And User clicks on Close "X" button
    Then popup should be closed without saving updated changes

  @regression @p2
  Scenario: Reopen popup after closing without save
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User updates Product Group Name
    And User clicks on Close "X" button
    And User reopens Edit Product Group popup
    Then previously saved Product Group data should be displayed

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  @regression @p2
  Scenario: Verify Edit Product Group popup UI
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    Then Product Group ID should be visible
    And Status field should be visible
    And Product Group Name field should be editable
    And Product Group Code field should be editable
    And Description field should be editable
    And Save button should be visible
    And Close "X" button should be visible

  @regression @p2
  Scenario: Verify non-editable fields
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    Then Product Group ID should not be editable
    And Status field should not be editable

  @regression @p2
  Scenario: Verify pre-filled data accuracy
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    Then all fields should display previously saved data correctly

  @regression @p2
  Scenario: Verify Close button functionality
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User clicks on Close "X" button
    Then popup should be closed
    And User should return to Product Groups list screen

  @regression @p2
  Scenario: Verify validation message UI in edit popup
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User triggers validation errors
    Then validation messages should be displayed below respective fields

  # ==================================================
  # 🔎 FIELD VALIDATION OUTLINES
  # ==================================================

  @regression @p3
  Scenario Outline: Validate Product Group Name field during update
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User enters valid Product Group Code
    And User clicks Save button
    Then "<error>" should be displayed

    Examples:
      | input | error                          |
      |       | Product Group Name is required |

  @regression @p3
  Scenario Outline: Validate Product Group Code field during update
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User enters valid Product Group Name
    And User clicks Save button
    Then "<error>" should be displayed

    Examples:
      | input | error                          |
      |       | Product Group Code is required |

  @regression @p2
  Scenario: Validate Description field during update
    When User searches for newly created Product Group Name
    And User swipes product group record from right to left
    And User clicks on Edit button
    And User enters long description text
    Then system should allow optional input
