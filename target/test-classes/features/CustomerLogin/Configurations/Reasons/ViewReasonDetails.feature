Feature: View Reason Details from Reason List

Background:
When click on profile icon
Then verify that the "Account Preferences" screen is displayed
When click on "configurations" section
Then verify the "Reason" section displayed
When click on "Reason" feature
Then verify that the user navigate to the "Reason" list screen
And User has created a new Reason

# ==================================================

# ✅ NAVIGATION SCENARIOS

# ==================================================

Scenario: Navigate to Reason View via search
When User clicks on search icon
And User taps on search input field
And User clears existing text in search field
And User enters newly created Reason Name
And User waits for search results to load
Then system should display matching Reason results
When User clicks on the Reason record
Then Reason View screen should be displayed

Scenario: Navigate to Reason View via scrolling
When User scrolls the Reason list
And finds the newly created Reason
And clicks on the Reason record
Then Reason View screen should be displayed

# ==================================================

# ✅ POSITIVE SCENARIOS

# ==================================================

Scenario: Verify all Reason details are displayed
When User opens Reason View screen
Then following fields should be displayed:
| Reason Name   |
| Reason Type   |
| Reason Group  |
| Timeout       |
| Stop Trigger  |

Scenario: Verify data accuracy for newly created Reason
When User views Reason details
Then all displayed data should match created Reason data

Scenario: Verify Timeout OFF state
When Timeout is disabled
Then Stop Trigger should not be displayed

Scenario: Verify Timeout ON state
When Timeout is enabled
Then Stop Trigger should be displayed with correct duration

Scenario: Verify duration format display
When Stop Trigger is displayed
Then duration should be in HH:MM:SS format

Scenario: Verify optional behavior of Stop Trigger
When Timeout is toggled OFF
Then Stop Trigger value should be empty or hidden

# ==================================================

# ❌ NEGATIVE SCENARIOS

# ==================================================

Scenario: Verify invalid Reason selection
When User selects invalid or deleted Reason
Then error message should be displayed

Scenario: Verify API failure while loading view
When backend API fails
Then error message should be displayed

Scenario: Verify network failure while opening view
When internet is disconnected
Then proper error message should be shown

# ==================================================

# ⚠️ EDGE CASE SCENARIOS

# ==================================================

Scenario: Verify long Reason Name handling
When Reason Name is very long
Then it should be properly displayed or truncated

Scenario: Verify long Reason Group handling
When Reason Group is very long
Then text should wrap correctly

Scenario: Verify special characters display
When Reason data contains special characters
Then they should be displayed correctly

Scenario: Verify empty optional fields
When optional fields are empty
Then UI should not break

Scenario: Verify rapid navigation
When User quickly opens multiple Reason records
Then application should not crash

# ==================================================

# 📱 UI VALIDATION SCENARIOS

# ==================================================

Scenario: Verify Reason View screen UI elements
Then all labels and values should be visible

Scenario: Verify fields are read-only
Then all fields should be non-editable

Scenario: Verify scroll functionality
Then user should be able to scroll entire screen

Scenario: Verify layout alignment
Then all fields should be properly aligned

Scenario: Verify back navigation
When User clicks back button
Then Reason list screen should be displayed

Scenario: Verify loading indicator
When User opens Reason View screen
Then loading indicator should be displayed until data loads

# ==================================================

# 🔎 FIELD-LEVEL VALIDATION

# ==================================================

Scenario: Verify Reason Name display
Then Reason Name should be displayed correctly

Scenario: Verify Reason Type display
Then Reason Type should be displayed correctly

Scenario: Verify Reason Group display
Then Reason Group should be displayed correctly

Scenario: Verify Timeout display
Then Timeout status should be displayed correctly

Scenario: Verify Stop Trigger display
Then Stop Trigger should be displayed correctly when enabled
