Feature: Reason Groups List and View in Mobile Application

Background:
When click on profile icon
Then verify that the "Account Preferences" screen is displayed
When click on "configurations" section
Then verify the "Reason" section displayed
When click on "Reason Groups" feature
Then verify that the user navigate to the "Reason Groups" list screen

# ==================================================

# ✅ NAVIGATION SCENARIOS

# ==================================================

Scenario: Navigate to Reason Groups screen
When User clicks on "Reason Groups" in Configuration section
Then User should be navigated to Configuration > Reason Groups screen

# ==================================================

# ✅ POSITIVE SCENARIOS (LIST VIEW)

# ==================================================

Scenario: Verify Reason Groups list is displayed
Then Reason Groups list should be displayed
And total count of Reason Groups should be visible

Scenario: Verify Reason Group card details
Then each Reason Group record should display:
| Group ID     |
| Group Name   |
| Description  |
| Status       |

Scenario: Verify status display
When Reason Group is marked as used
Then status should be displayed as "Used"

Scenario: Verify status for unused Reason Group
When Reason Group is not used
Then status should be displayed as "Not Used"

Scenario: Verify list scrolling
When list contains multiple Reason Groups
Then user should be able to scroll smoothly

Scenario: Verify Reason Groups count accuracy
Then displayed count should match number of records in list

Scenario: Verify search functionality for Reason Groups
When User searches with valid Group Name
Then matching Reason Groups should be displayed

Scenario: Verify search with no matching results
When User searches with invalid Group Name
Then no results or empty state message should be displayed

Scenario: Verify pull to refresh functionality
When User performs pull to refresh
Then Reason Groups list should be refreshed

Scenario: Verify loading indicator for Reason Groups list
When User opens Reason Groups screen
Then loading indicator should be displayed until data loads

Scenario: Verify state retention after navigation
When User scrolls list and opens a record
And navigates back
Then list should retain previous scroll position

# ==================================================

# ❌ NEGATIVE SCENARIOS (LIST VIEW)

# ==================================================

Scenario: Verify no Reason Groups available
When no Reason Groups exist
Then empty state message should be displayed

Scenario: Verify API failure while loading list
When backend API fails
Then error message should be displayed

Scenario: Verify retry option on API failure
When API fails while loading Reason Groups
Then retry option should be displayed
When User clicks retry
Then list should reload successfully

Scenario: Verify network failure
When internet is disconnected
Then proper error message should be shown

Scenario: Verify access restriction for Reason Groups
When user does not have permission
Then Reason Groups option should not be visible

# ==================================================

# ⚠️ EDGE CASE SCENARIOS (LIST VIEW)

# ==================================================

Scenario: Verify long group name handling
When Group Name is very long
Then it should be truncated or wrapped properly

Scenario: Verify long description handling
When Description is very long
Then it should wrap correctly

Scenario: Verify special characters in data
When Group data contains special characters
Then it should display correctly

Scenario: Verify large list performance
When list contains many Reason Groups
Then performance should not degrade

# ==================================================

# 📱 UI VALIDATION SCENARIOS (LIST VIEW)

# ==================================================

Scenario: Verify header UI
Then screen title "Reason Groups" should be displayed
And back button should be visible

Scenario: Verify list alignment
Then all Reason Group cards should be properly aligned

Scenario: Verify status UI
Then status badge should be visually distinguishable

Scenario: Verify scroll indicator
Then scroll indicator should be visible when scrolling

# ==================================================

# 🔎 FIELD-LEVEL / DISPLAY VALIDATION (LIST VIEW)

# ==================================================

Scenario: Verify Group ID display
Then Group ID should be displayed correctly

Scenario: Verify Group Name display
Then Group Name should be displayed correctly

Scenario: Verify Description display
Then Description should be displayed correctly

Scenario: Verify Status display
Then Status should be displayed correctly

# ==================================================

# 👁️ SINGLE RECORD VIEW (DETAIL VIEW)

# ==================================================

# ==================================================

# ✅ POSITIVE SCENARIOS (VIEW)

# ==================================================

Scenario: Navigate to Reason Group view
When User clicks on any Reason Group record
Then Reason Group detail view should be displayed

Scenario: Verify Reason Group details in view
Then following details should be displayed:
| Group ID     |
| Group Name   |
| Description  |
| Status       |

Scenario: Verify data accuracy in view
When User opens Reason Group details
Then data should match list view record

# ==================================================

# ❌ NEGATIVE SCENARIOS (VIEW)

# ==================================================

Scenario: Verify invalid Reason Group selection
When User selects invalid record
Then error message should be displayed

Scenario: Verify API failure in view screen
When backend API fails
Then error message should be displayed

Scenario: Verify network failure in view screen
When internet is disconnected
Then proper error message should be shown

# ==================================================

# ⚠️ EDGE CASE SCENARIOS (VIEW)

# ==================================================

Scenario: Verify long text handling in view
When Group Name or Description is very long
Then it should display properly

Scenario: Verify special characters in view
When data contains special characters
Then it should display correctly

Scenario: Verify rapid navigation
When User quickly opens multiple records
Then application should not crash

# ==================================================

# 📱 UI VALIDATION SCENARIOS (VIEW)

# ==================================================

Scenario: Verify Reason Group view UI
Then all fields should be visible and properly aligned

Scenario: Verify fields are read-only
Then all fields should be non-editable

Scenario: Verify back navigation
When User clicks back button
Then Reason Groups list screen should be displayed

Scenario: Verify back navigation using device gesture
When User performs back gesture
Then Reason Groups list screen should be displayed

Scenario: Verify scroll functionality in view
Then user should be able to scroll details screen

# ==================================================

# 🔎 FIELD-LEVEL VALIDATION (VIEW)

# ==================================================

Scenario: Verify Group ID in view
Then Group ID should match selected record

Scenario: Verify Group Name in view
Then Group Name should match selected record

Scenario: Verify Description in view
Then Description should match selected record

Scenario: Verify Status in view
Then Status should match selected record
