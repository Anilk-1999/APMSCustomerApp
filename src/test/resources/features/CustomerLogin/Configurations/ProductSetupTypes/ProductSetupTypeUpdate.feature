Feature: Update Product Setup Type via Swipe Action from List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Product Setup Types" feature
    Then verify user navigates to "Product Setup Types" list screen
    And User has already created a Product Setup Type

  # =========================================================
  # 🔍 SEARCH + SWIPE + EDIT FLOW
  # =========================================================

  Scenario: Search and open Edit Product Setup Type popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Setup Type Name
    And User waits for search results to load
    Then system should display matching Product Setup Type results
    And User verifies Product Setup Type appears in list
    When User swipes Product Setup Type record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product Setup Type" popup should be displayed
    And Product Setup Type ID should be visible and non-editable
    And Product Setup Name field should be pre-filled
    And Machine Output Timer field should be pre-filled
    And Product Setup Timer field should be pre-filled
    And Description field should be displayed if data exists
    And Save button should be visible
    And Close "X" button should be visible

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Update Product Setup Type with valid data
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User updates Product Setup Name
    And User updates Description
    And User updates Machine Output Timer duration
    And User updates Product Setup Timer duration
    And User clicks Save button
    Then Product Setup Type should be updated successfully
    And updated Product Setup Type data should be reflected in Product Setup Types list screen

  Scenario: Update only mandatory fields
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User updates Product Setup Name
    And User updates Machine Output Timer
    And User updates Product Setup Timer
    And User clicks Save button
    Then Product Setup Type should be updated successfully

  Scenario: Partial update of Product Setup Type
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User updates only Description
    And User clicks Save button
    Then Product Setup Type should be updated successfully

  Scenario: Update Product Setup Type with trimmed name
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User enters Product Setup Name with leading and trailing spaces
    And User clicks Save button
    Then system should trim spaces and update Product Setup Type successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Update without Product Setup Name
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User clears Product Setup Name
    And User clicks Save button
    Then "Product Setup Name is required" should be displayed

  Scenario: Update without Machine Output Timer
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User removes Machine Output Timer value
    And User clicks Save button
    Then "Machine Output Timer is required" should be displayed

  Scenario: Update without Product Setup Timer
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User removes Product Setup Timer value
    And User clicks Save button
    Then "Product Setup Timer is required" should be displayed

  Scenario: Invalid zero duration selection for Machine Output Timer
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User sets "00:00:00" for Machine Output Timer
    And User clicks Save button
    Then validation error should be displayed for Machine Output Timer

  Scenario: Invalid zero duration selection for Product Setup Timer
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User sets "00:00:00" for Product Setup Timer
    And User clicks Save button
    Then validation error should be displayed for Product Setup Timer

  Scenario: Save without changes
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User clicks Save button without modification
    Then system should show "No changes detected"

  Scenario: Update Product Setup Type with only spaces in name
    When User searches for newly created Product Setup Type Name
    And User.swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User enters only spaces in Product Setup Name field
    And User clicks Save button
    Then "Product Setup Name is required" should be displayed

  Scenario: Update Product Setup Type with invalid special characters in name
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User enters only special characters in Product Setup Name field
    And User clicks Save button
    Then validation error should be displayed for Product Setup Name field

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid swipe actions
    When User searches for newly created Product Setup Type Name
    And User performs swipe action multiple times quickly on Product Setup Type record
    Then only one Edit option should be displayed

  Scenario: Rapid Save clicks
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User clicks Save button multiple times quickly
    Then system should prevent duplicate update

  Scenario: Large duration values
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User selects maximum allowed duration
    Then system should accept valid duration limit

  Scenario: Network failure during update
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User clicks Save button without internet connection
    Then error message should be displayed

  Scenario: Session timeout during update
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And session expires during editing
    Then User should be redirected to login screen

  Scenario: Close popup without saving changes
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User modifies Product Setup Type details
    And User clicks on Close "X" button
    Then popup should be closed without saving updated changes

  Scenario: Reopen popup after close without save
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User updates Product Setup Name
    And User clicks on Close "X" button
    And User reopens Edit Product Setup Type popup
    Then previously saved Product Setup Type data should be displayed

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Edit Product Setup Type popup UI
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    Then all fields should be visible and aligned properly
    And Save button should be visible
    And Close "X" button should be visible

  Scenario: Verify non-editable ID field
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    Then Product Setup Type ID should not be editable

  Scenario: Verify duration popup behavior for Machine Output Timer
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User clicks on Machine Output Timer field
    Then duration popup should appear
    And Hour selector should be visible
    And Minute selector should be visible
    And Second selector should be visible

  Scenario: Verify duration popup behavior for Product Setup Timer
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User clicks on Product Setup Timer field
    Then duration popup should appear
    And Hour selector should be visible
    And Minute selector should be visible
    And Second selector should be visible

  Scenario: Verify Close button functionality
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User clicks on Close "X" button
    Then popup should be closed
    And User should return to Product Setup Types list screen

  # =========================================================
  # 🔍 FIELD VALIDATION SCENARIOS
  # =========================================================

  Scenario Outline: Validate Product Setup Name field during update
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User sets valid Machine Output Timer duration
    And User sets valid Product Setup Timer duration
    And User clicks Save button
    Then "<error>" should be displayed

    Examples:
      | input | error                               |
      |       | Product Setup Name is required      |

  Scenario Outline: Validate Machine Output Timer field during update
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User enters valid Product Setup Name
    And User.sets valid Product Setup Timer duration
    And User enters "<input>" for Machine Output Timer
    And User clicks Save button
    Then "<error>" should be displayed

    Examples:
      | input    | error                                  |
      |          | Machine Output Timer is required       |
      | 00:00:00 | Invalid Machine Output Timer           |

  Scenario Outline: Validate Product Setup Timer field during update
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User enters valid Product Setup Name
    And User sets valid Machine Output Timer duration
    And User enters "<input>" for Product Setup Timer
    And User clicks Save button
    Then "<error>" should be displayed

    Examples:
      | input    | error                                  |
      |          | Product Setup Timer is required        |
      | 00:00:00 | Invalid Product Setup Timer            |

  Scenario: Validate Description field during update
    When User searches for newly created Product Setup Type Name
    And User swipes Product Setup Type record from right to left
    And User clicks on Edit button
    And User enters long Description text
    Then system should allow optional Description input without error

 