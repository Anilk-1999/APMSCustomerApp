Feature: Edit Reason via Swipe Action in Mobile Application

Background:
When click on profile icon
Then verify that the "Account Preferences" screen is displayed
When click on "configurations" section
Then verify the "Reasons" section displayed
When User clicks on "Reason" in Configuration section
And User is on Reasons List screen
And User has created a new Reason

# ==================================================

# ✅ NAVIGATION SCENARIOS (SEARCH + SWIPE)

# ==================================================

Scenario: Search and open Edit Reason popup using swipe action
When User clicks on search icon
And User taps on search input field
And User clears existing text in search field
And User enters newly created Reason Name
And User waits for search results to load
Then system should display matching Reason results
And User verifies Reason appears in list
When User swipes Reason record from right to left
Then Edit option should be visible
When User clicks on Edit button
Then "Edit Reason" popup should be displayed
And Reason Name should be visible and non-editable

Scenario: Navigate to Edit using scrolling and swipe
When User scrolls Reason list
And User finds newly created Reason
And User swipes record from right to left
And clicks on Edit button
Then Edit Reason popup should be displayed

Scenario: Verify swipe reveals Edit option
When User swipes Reason record from right to left
Then Edit option should be visible

Scenario: Verify swipe sensitivity
When User performs partial swipe
Then Edit option should not trigger incorrectly

# ==================================================

# ✅ POSITIVE SCENARIOS

# ==================================================

Scenario: Update Reason Type and Group
When User updates Reason Type
And User updates Reason Group
And clicks Save
Then Reason should be updated successfully

Scenario: Enable Timeout and set valid duration
When User enables Timeout
And User clicks on Stop Trigger field
And selects duration greater than 30 seconds
And clicks Save in popup
And clicks Save
Then Reason should be updated successfully

Scenario: Disable Timeout after setting duration
When User enables Timeout and sets duration
And disables Timeout
And clicks Save
Then Stop Trigger should be cleared

Scenario: Save without modifying data
When User opens Edit Reason popup
And clicks Save without changes
Then Reason should remain unchanged

Scenario: Verify duration persistence after reopen
When User sets duration and saves
And reopens Edit Reason popup
Then selected duration should be displayed

Scenario: Update only one field
When User updates only Reason Type
And clicks Save
Then update should be successful

# ==================================================

# ❌ NEGATIVE SCENARIOS

# ==================================================

Scenario: Missing Reason Type
When User clears Reason Type
And clicks Save
Then error should be displayed

Scenario: Missing Reason Group
When User clears Reason Group
And clicks Save
Then error should be displayed

Scenario: Timeout enabled without Stop Trigger
When User enables Timeout
And does not select duration
And clicks Save
Then "Stop Trigger is required" should be displayed

Scenario: Duration less than 30 seconds
When User selects duration less than 30 seconds
Then validation error should be displayed

Scenario: Close duration popup without saving
When User opens duration popup
And selects duration
And clicks Cancel (X)
Then value should not be saved

Scenario: Invalid dropdown load
When Reason Type or Group dropdown fails to load
Then error should be handled

Scenario: API failure during update
When backend returns failure response
Then error message should be shown

Scenario: Network failure during update
When internet is disconnected
Then error message should be displayed

Scenario: Rapid multiple Save clicks
When User clicks Save repeatedly
Then system should prevent duplicate updates

# ==================================================

# ⚠️ EDGE CASE SCENARIOS

# ==================================================

Scenario: Rapid toggle Timeout multiple times
When User toggles Timeout repeatedly
Then system should maintain correct state

Scenario: Rapid popup open/close
When User quickly opens and closes popup
Then app should not crash

Scenario: Change duration multiple times before saving
When User selects multiple durations
Then last selected value should be saved

Scenario: Select boundary duration (exact 30 seconds)
When User selects exactly 30 seconds
Then validation error should be shown

Scenario: Select large duration
When User selects very high duration
Then system should accept within limits

Scenario: Scroll duration picker aggressively
Then selection should remain accurate

Scenario: Reopen popup retains last selection
Then previous value should be preselected

Scenario: Background app during popup
When app goes to background and returns
Then popup state should be maintained

# ==================================================

# 📱 UI VALIDATION SCENARIOS

# ==================================================

Scenario: Verify Edit Reason popup UI
Then popup should contain:
| Reason Name |
| Reason Type |
| Reason Group |
| Timeout Toggle |
| Stop Trigger |
| Save Button |
| Cancel Button |

Scenario: Verify Reason Name is read-only
Then Reason Name should not be editable

Scenario: Verify Stop Trigger behavior
When Timeout is disabled
Then Stop Trigger should be disabled or cleared

Scenario: Verify Save button behavior
Then Save button should validate mandatory fields

Scenario: Verify Cancel button behavior
When User clicks Cancel
Then popup should close without saving

Scenario: Verify keyboard behavior
Then keyboard should not overlap fields

Scenario: Verify alignment
Then all fields should be properly aligned

# ==================================================

# 🔎 FIELD-LEVEL VALIDATION

# ==================================================

Scenario Outline: Validate Reason Type field
When User selects "<value>" in Reason Type
And clicks Save
Then "<error>" should be displayed


Examples:
  | value | error                   |
  |       | Reason Type is required |


Scenario Outline: Validate Reason Group field
When User selects "<value>" in Reason Group
And clicks Save
Then "<error>" should be displayed

Examples:
  | value | error                    |
  |       | Reason Group is required |


Scenario Outline: Validate Stop Trigger field
When Timeout enabled and value "<value>"
Then "<error>" should be displayed


Examples:
  | value | error                         |
  |       | Stop Trigger is required      |
  | 10s   | Must be greater than 30 sec   |


Scenario: Validate duration format display
Then duration should be in HH:MM:SS

Scenario: Validate Stop Trigger cleared on toggle OFF
When Timeout is disabled
Then Stop Trigger should be empty
