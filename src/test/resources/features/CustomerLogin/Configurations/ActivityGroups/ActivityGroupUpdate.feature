@regression @smoke @update @p1
Feature: Update Activity Group via Swipe Action with Checklist and Status Management

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Activity Groups" feature

  # =========================================================
  # 🔍 SEARCH + SWIPE + EDIT FLOW
  # =========================================================

  @smoke @p1
  Scenario: Search and open Edit Activity Group popup
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Maintenance" section is displayed
    When User clicks on "Activity Groups" feature
    Then verify user navigates to "Activity Groups" list screen
    And User has already created an Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list

    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed

    # Field validations
    And Activity Group ID should be visible and non-editable
    And Status should be visible and clickable
    And Form Name field should be pre-filled and editable
    And Form Description field should be pre-filled and editable
    And Activity Checklist should display previously selected activities
    And Save button should be visible
    And Close "X" button should be visible
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  # =========================================================
  # 🧪 POSITIVE SCENARIOS (UPDATE)
  # =========================================================

  @smoke @regression @p1
  Scenario: Update Activity Group with all fields
    When User has already created an Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User opens Edit Activity Group popup
    And User updates Form Name with valid value
    And User updates Form Description
    And User adds new activities to checklist
    And User deletes existing activity from checklist
    And User clicks Save button
    Then Activity Group should be updated successfully
    And updated Activity Group should be reflected in list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @regression @p2
  Scenario: Update only Form Name
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User opens Edit Activity Group popup
    And User updates Form Name
    And User clicks Save button
    Then Activity Group should be updated successfully
    And Verify User should be navigate into "Activity Groups" list

  @regression @p2
  Scenario: Update only Description
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User opens Edit Activity Group popup
    And User updates Form Description
    And User clicks Save button
    Then Activity Group should be updated successfully
    And Verify User should be navigate into "Activity Groups" list

  @regression @p2
  Scenario: Update only checklist
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User opens Edit Activity Group popup
    And User adds activities
    And User deletes activities
    And User clicks Save button
    Then Activity Group should be updated successfully
    And Verify User should be navigate into "Activity Groups" list

  @regression @p2
  Scenario: Update Form Name with trimmed value
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User opens Edit Activity Group popup 
    And User enters Form Name with leading and trailing spaces
    And User clicks Save button
    Then system should trim spaces and update successfully
    And Verify User should be navigate into "Activity Groups" list

  # =========================================================
  # 🔁 CHECKLIST ADD FLOW
  # =========================================================

  @regression @p2
  Scenario: Add activities in edit popup
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User clicks "+" button in Activity Checklist
    Then "Select Activities" bottom sheet should be displayed
    When User selects multiple activities
    And User clicks Submit button
    Then selected activities should be added to checklist
    When User clicks Close "X" button in Activity Group popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @regression @p2
  Scenario: Add activities without selection
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User opens Activity Checklist bottom sheet
    And User clicks Submit without selecting
    Then checklist should remain unchanged
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list


  # =========================================================
  # 🗑️ CHECKLIST DELETE FLOW
  # =========================================================

  @regression @p2
  Scenario: Delete single activity
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User clicks delete icon on one activity
    Then that activity should be removed
    And remaining activities should be visible
    When User clicks Close "X" button in Activity Group popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list



  @regression @p2
  Scenario: Delete multiple activities
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User deletes multiple activities
    Then all selected activities should be removed correctly
    When User clicks Close "X" button in Activity Group popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list


  @regression @p2
  Scenario: Delete all activities
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User deletes all activities
    Then checklist should become empty
    When User clicks Close "X" button in Activity Group popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @regression @p2
  Scenario: Delete and re-add activities
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User deletes activity
    And User adds new activity
    Then checklist should update correctly
    When User clicks Close "X" button in Activity Group popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  # =========================================================
  # 🔁 STATUS FLOW
  # =========================================================

  @regression @p1
  Scenario: Change status from Active to Inactive
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When current status is Active
    And User clicks on Status button
    Then Status confirmation popup should be displayed
    And "Yes, Change" button should be visible
    When User clicks "Yes, Change"
    Then status should be changed to Inactive
    When User clicks Close "X" button in Activity Group popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list


  @regression @p1
  Scenario: Change status from Inactive to Active
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When current status is Inactive
    And User clicks on Status button
    And User confirms change
    Then status should be changed to Active
    When User clicks Close "X" button in Activity Group popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @negative @regression @p2
  Scenario: Cancel status change
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User clicks on Status button
    And User closes popup without confirmation
    Then status should remain unchanged
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  @negative @regression @p1
  Scenario: Update without Form Name
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User clears Form Name
    And User clicks Save button
    Then "Form Name is required" error should be displayed
    When User clicks Close "X" button in Activity Group popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @negative @regression @p2
  Scenario: Duplicate Form Name
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    And User enters existing Form Name
    And User clicks Save button
    Then Activity Group should be updated successfully
    And Verify User should be navigate into "Activity Groups" list
    

  @negative @regression @p2
  Scenario: Invalid Form Name input
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User enters only spaces into Form Name
    And User clicks Save button
    Then validation error should be displayed
    When User clicks Close "X" button in Activity Group popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @negative @regression @p3
  Scenario: Save without changes
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User clicks Save without making changes
    Then Activity Group should be updated successfully
    And Verify User should be navigate into "Activity Groups" list

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p3
  Scenario: Rapid Save clicks
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    And User clicks Save button multiple times
    Then system should prevent duplicate update
    And Verify User should be navigate into "Activity Groups" list

  @sanity @p3
  Scenario: Rapid delete clicks
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User clicks delete multiple times quickly in Activity Checklist
    Then activity should be removed only once
    When User clicks Close "X" button in Activity Group popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @sanity @p3
  Scenario: Rapid "+" clicks
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User clicks Activity Checklist "+" multiple times
    Then only one bottom sheet should open
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @sanity @p3
  Scenario: Large checklist handling
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When checklist contains many activities
    Then system should support scrolling and proper UI rendering
    When User clicks Close "X" button in Activity Group popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @regression @p2
  Scenario: Close popup without saving
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User modifies data
    When User clicks Close "X" button in Activity Group popup
    And "Confirmation Alert" popup should be displayed
    When User clicks on "Yes, Exit" button on the confirmation popup
    Then changes should not be saved
    And Verify User should be navigate into "Activity Groups" list
    

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Edit popup UI
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When all fields should be aligned properly
    And Form Name and Description should be editable
    And Activity Checklist should display correctly
    And delete icons should be visible for each activity
    And Save button should be visible
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list


  @regression @p2
  Scenario: Verify bottom sheet UI
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User clicks "+" button in Activity Checklist
    Then "Select Activities" bottom sheet should be displayed
    And activity list should be displayed
    And multi-selection should be enabled
    And Submit button should be visible
    And User clicks Submit without selecting
    When User clicks Close "X" button in Activity Group popup
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Verify User should be navigate into "Activity Groups" list

  @regression @p2
  Scenario: Verify Close (X) button
    When User has updated Activity Group
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly updated Activity Group Name
    And User waits for search results to load
    Then system should display matching Activity Groups
    And User verifies Activity Group appears in list
    When User swipes Activity Group record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Activity Group" popup should be displayed
    When User clicks Close "X" button in Activity Group popup
    Then popup should be closed
    And Verify User should be navigate into "Activity Groups" list
