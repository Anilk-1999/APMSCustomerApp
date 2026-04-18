Feature: Machine Subscription for Newly Created Reason

Background:
When click on profile icon
Then verify that the "Account Preferences" screen is displayed
When click on "configurations" section
Then verify the "Reason" section displayed
When click on "Reasons" feature
Then verify that the user navigate to the "Reasons" list screen
And User has created a new Reason

# ==================================================

# ✅ NAVIGATION SCENARIOS

# ==================================================

Scenario: Open Machine Subscription via long press
When User searches with newly created Reason Name
Then newly created Reason should be displayed in list
When User long presses on the Reason record
Then action menu bottom sheet should be displayed
When User clicks on "Machine Subscription"
Then Machine Subscription popup should be displayed

Scenario: Open Machine Subscription using search and swipe action
When User clicks on search icon
And User taps on search input field
And User clears existing text in search field
And User enters newly created Reason Name
And User waits for search results to load
Then system should display matching Reason results
And User verifies Reason appears in list
When User swipes Reason record from right to left
Then Machine Subscription option should be visible
When User clicks on Machine Subscription option
Then Machine Subscription popup should be displayed

# ==================================================

# ✅ POSITIVE SCENARIOS (ADD FLOW)

# ==================================================

Scenario: Add machine subscription successfully
When User opens Machine Subscription popup
And User clicks "+" icon
Then Select Machines bottom sheet should be displayed
When User selects multiple machines
And clicks Submit on bottom sheet
Then selected machines should be displayed in popup
When User clicks Submit on Machine Subscription popup
Then machine subscription should be saved successfully

Scenario: Add single machine
When User opens Machine Subscription popup
And User clicks "+" icon
And selects one machine
And clicks Submit
Then selected machine should be displayed in popup

Scenario: Add multiple machines
When User opens Machine Subscription popup
And User clicks "+" icon
And selects multiple machines
And clicks Submit
Then all selected machines should be displayed in popup

Scenario: Verify persistence after saving
When User opens Machine Subscription popup
And adds machines and submits
And closes popup
And reopens Machine Subscription popup
Then previously selected machines should be displayed

Scenario: Add machine after reopening popup
When User opens Machine Subscription popup
And clicks "+" icon
And selects machines and submits
And clicks "+" icon again
And selects additional machine
And clicks Submit
Then new machine should be added to existing list

# ==================================================

# ❌ NEGATIVE SCENARIOS (ADD FLOW)

# ==================================================

Scenario: Submit without selecting machine
When User opens Machine Subscription popup
And clicks "+" icon
Then Select Machines bottom sheet should be displayed
When User clicks Submit without selecting machines
Then validation message should be displayed

Scenario: Add duplicate machine
When User opens Machine Subscription popup
And clicks "+" icon
And selects machine and submits
And clicks "+" icon again
And selects same machine
Then system should prevent duplicate selection

Scenario: Network failure during add
When User opens Machine Subscription popup
And User turns off internet
And clicks "+" icon
And selects machine
And clicks Submit
Then error message should be displayed

Scenario: API failure during add
When User opens Machine Subscription popup
And backend returns failure
Then error message should be shown

Scenario: Close selection without submit
When User opens Machine Subscription popup
And clicks "+" icon
And selects machines
And closes bottom sheet without submitting
Then machines should not be added to popup

# ==================================================

# ⚠️ EDGE CASE SCENARIOS (ADD FLOW)

# ==================================================

Scenario: Select maximum machines
When User opens Machine Subscription popup
And clicks "+" icon
And selects maximum allowed machines
And clicks Submit
Then system should handle selection correctly

Scenario: Rapid selection and deselection
When User opens Machine Subscription popup
And clicks "+" icon
And rapidly selects and deselects machines
Then correct selection state should be maintained

Scenario: Large machine list handling
When User opens Machine Subscription popup
And clicks "+" icon
Then user should be able to scroll machine list smoothly

Scenario: Special character machine names
When User opens Machine Subscription popup
And clicks "+" icon
Then machine names with special characters should display correctly

Scenario: Reopen selection retains state
When User opens Machine Subscription popup
And clicks "+" icon
And selects machines
And closes bottom sheet
And reopens bottom sheet
Then previously selected machines should remain selected

# ==================================================

# 📱 UI VALIDATION SCENARIOS (ADD FLOW)

# ==================================================

Scenario: Verify Machine Subscription popup UI
When User opens Machine Subscription popup
Then popup should display machine list, add icon, delete icon and submit button

Scenario: Verify Select Machines bottom sheet UI
When User clicks "+" icon
Then multi-selection bottom sheet should be displayed

Scenario: Verify selection highlight
When User selects machines
Then selected machines should be highlighted

Scenario: Verify scroll behavior
Then machine list should be scrollable

Scenario: Verify submit button validation
When User clicks Submit without selection
Then validation message should be displayed

Scenario: Verify popup close behavior
When User clicks Submit
Then popup should close after successful save

# ==================================================

# 🗑️ DELETE FLOW

# ==================================================

Scenario: Delete single machine subscription
When User opens Machine Subscription popup
And clicks delete icon for a machine
Then machine should be removed from popup
When User clicks Submit
Then deletion should be saved successfully

Scenario: Delete multiple machines
When User opens Machine Subscription popup
And removes multiple machines
And clicks Submit
Then all selected machines should be removed

Scenario: Verify persistence after delete
When User deletes machines and submits
And reopens popup
Then deleted machines should not be displayed

Scenario: Delete all machines
When User opens Machine Subscription popup
And deletes all machines
Then empty state should be displayed

# ==================================================

# ❌ NEGATIVE SCENARIOS (DELETE FLOW)

# ==================================================

Scenario: Delete without saving
When User opens Machine Subscription popup
And deletes machine
And closes popup without submitting
Then deletion should not be persisted

Scenario: Network failure during delete
When User opens Machine Subscription popup
And turns off internet
And deletes machine
And clicks Submit
Then error message should be displayed

Scenario: API failure during delete
When User opens Machine Subscription popup
And backend fails during delete
Then error message should be shown

# ==================================================

# ⚠️ EDGE CASE SCENARIOS (DELETE FLOW)

# ==================================================

Scenario: Rapid delete actions
When User opens Machine Subscription popup
And quickly deletes multiple machines
Then system should handle deletion correctly

Scenario: Delete last machine
When User opens Machine Subscription popup
And deletes last remaining machine
Then list should become empty

# ==================================================

# 📱 UI VALIDATION SCENARIOS (DELETE FLOW)

# ==================================================

Scenario: Verify delete icon visibility
When User opens Machine Subscription popup
Then delete icon should be visible for each machine

Scenario: Verify UI updates after deletion
When machine is deleted
Then UI should update immediately

Scenario: Verify empty state UI
When no machines exist
Then empty state message should be displayed

# ==================================================

# 🔎 FINAL VALIDATION

# ==================================================

Scenario: Validate machine removal
When machine is deleted and saved
Then it should not appear in subscription list
