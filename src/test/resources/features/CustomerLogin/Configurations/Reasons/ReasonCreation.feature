Feature: Create New Reason in Mobile Application

Background:
When click on profile icon
Then verify that the "Account Preferences" screen is displayed
When click on "configurations" section
Then verify the "Reason" section displayed
When click on "Reasons" feature
Then verify that the user navigate to the "Reasons" list screen

# ==================================================

# ✅ NAVIGATION SCENARIOS

# ==================================================

Scenario: Navigate to Reason screen
When User clicks on "Reason" in Configuration section
Then User should be navigated to Configuration > Reason screen

Scenario: Open Add New Reason popup
When User clicks on "+ Add" button
Then Add New Reason popup should be displayed

# ==================================================

# ✅ POSITIVE SCENARIOS

# ==================================================

Scenario: Create Reason with all mandatory fields
When User selects Reason Name from dropdown
And User selects Reason Type from dropdown
And User selects Reason Group from dropdown
And clicks Submit
Then Reason should be created successfully

Scenario: Create Reason with Timeout disabled
When User fills all mandatory fields
And Timeout toggle is disabled
And clicks Submit
Then Reason should be created successfully

Scenario: Create Reason with Timeout enabled and valid duration
When User enables Timeout toggle
And User clicks on Stop Trigger field
Then duration picker should be displayed
When User selects duration more than 30 seconds
And clicks Submit
Then Reason should be created successfully

Scenario: Verify duration selection format
When User selects duration
Then duration should be in HH:MM:SS format

Scenario: Verify dropdown selections
When User selects values in dropdowns
Then selected values should be displayed correctly

# ==================================================

# ❌ NEGATIVE SCENARIOS

# ==================================================

Scenario: Submit without mandatory fields
When User clicks Submit without selecting any field
Then error should be displayed below mandatory fields

Scenario: Missing Reason Name
When User does not select Reason Name
Then "Reason Name is required" should be displayed

Scenario: Missing Reason Type
When User does not select Reason Type
Then "Reason Type is required" should be displayed

Scenario: Missing Reason Group
When User does not select Reason Group
Then "Reason Group is required" should be displayed

Scenario: Timeout enabled without Stop Trigger
When User enables Timeout toggle
And does not select duration
And clicks Submit
Then "Stop Trigger is required" should be displayed

Scenario: Duration less than 30 seconds
When User selects duration less than 30 seconds
Then validation error should be displayed

Scenario: Invalid duration selection
When User selects invalid duration format
Then error message should be displayed

Scenario: API failure during creation
When backend returns failure response
Then error message should be shown

Scenario: Network failure during creation
When internet is disconnected
Then error message should be displayed

# ==================================================

# ⚠️ EDGE CASE SCENARIOS

# ==================================================

Scenario: Select minimum valid duration (just above 30 seconds)
When User selects duration of 31 seconds
Then Reason should be created successfully

Scenario: Select maximum duration
When User selects maximum allowed duration
Then system should handle correctly

Scenario: Rapid toggle of Timeout
When User repeatedly enables and disables Timeout toggle
Then system should maintain correct state

Scenario: Rapid dropdown selection
When User quickly selects dropdown values
Then selections should persist correctly

Scenario: Large dropdown list handling
When dropdown list is large
Then user should be able to scroll properly

# ==================================================

# 📱 UI VALIDATION SCENARIOS

# ==================================================

Scenario: Verify Add Reason popup UI
Then popup should contain:
| Reason Name Dropdown |
| Reason Type Dropdown |
| Reason Group Dropdown |
| Timeout Toggle |
| Stop Trigger Field |
| Submit Button |

Scenario: Verify dropdown UI behavior
When User clicks dropdown
Then dropdown list should be displayed

Scenario: Verify duration picker UI
When User clicks Stop Trigger field
Then vertical scroll picker for HH:MM:SS should be displayed

Scenario: Verify Timeout toggle UI
Then toggle should be visible and interactive

Scenario: Verify Stop Trigger field behavior
When Timeout is disabled
Then Stop Trigger field should be disabled or optional

Scenario: Verify scroll inside popup
Then user should be able to scroll popup if needed

Scenario: Verify Submit button behavior
Then Submit button should validate all mandatory fields

# ==================================================

# 🔎 FIELD-LEVEL VALIDATION SCENARIOS

# ==================================================

Scenario Outline: Validate Reason Name field
When User selects "<value>" in Reason Name
Then "<error>" should be displayed


Examples:
  | value | error                     |
  |       | Reason Name is required   |


Scenario Outline: Validate Reason Type field
When User selects "<value>" in Reason Type
Then "<error>" should be displayed


Examples:
  | value | error                     |
  |       | Reason Type is required   |


Scenario Outline: Validate Reason Group field
When User selects "<value>" in Reason Group
Then "<error>" should be displayed


Examples:
  | value | error                      |
  |       | Reason Group is required   |


Scenario Outline: Validate Stop Trigger field
When Timeout is enabled
And User enters "<value>" in Stop Trigger
Then "<error>" should be displayed


Examples:
  | value | error                         |
  |       | Stop Trigger is required      |
  | 10s   | Duration must be > 30 seconds |

