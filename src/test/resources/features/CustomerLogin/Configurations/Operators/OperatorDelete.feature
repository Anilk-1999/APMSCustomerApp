@regression @smoke @delete @p1
Feature: Delete Operator via Swipe Action

  Background:
    When User clicks on profile icon
    When User clicks on "Configurations" section
    When User clicks on "Operators" feature


  # =========================================================
  # NAVIGATION & SWIPE — REVEAL DELETE OPTION
  # =========================================================

  @smoke @p1
  Scenario: Search newly created Operator and verify in list
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    And User verifies Operator appears in list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @smoke @p1
  Scenario: Swipe Operator record right-to-left reveals Edit, Delete and Duplicate options
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User swipes Operator record from right to left
    Then Edit option should be visible
    And delete icon should be clearly visible after swipe
    And Duplicate option should be visible after swipe
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # DELETE CONFIRMATION POPUP — UI VALIDATION
  # =========================================================

  @smoke @regression @p1
  Scenario: Delete Confirmation popup appears after clicking Delete option
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User swipes Operator record from right to left
    Then Edit option should be visible
    And delete icon should be clearly visible after swipe
    When User clicks on "Delete" option
    Then Delete Confirmation popup should be displayed
    And delete confirmation message should be displayed
    And "Delete" button should be visible in Delete Confirmation popup
    And X close button should be visible in Delete Confirmation popup
    When User closes Delete Confirmation popup without deleting
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # POSITIVE SCENARIOS — CONFIRM DELETE
  # =========================================================

  @smoke @regression @p1
  Scenario: Delete Operator successfully via Delete Confirmation popup
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User swipes Operator record from right to left
    Then Edit option should be visible
    And delete icon should be clearly visible after swipe
    When User clicks on "Delete" option
    Then Delete Confirmation popup should be displayed
    When User confirms operator deletion
    Then operator should be deleted successfully
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p1
  Scenario: Deleted Operator no longer appears in search results
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User swipes Operator record from right to left
    And delete icon should be clearly visible after swipe
    When User clicks on "Delete" option
    Then Delete Confirmation popup should be displayed
    When User confirms operator deletion
    Then operator should be deleted successfully
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then no results should be displayed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Operator list updates immediately after deletion
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User swipes Operator record from right to left
    And delete icon should be clearly visible after swipe
    When User clicks on "Delete" option
    Then Delete Confirmation popup should be displayed
    When User confirms operator deletion
    Then operator should be deleted successfully
    And list UI should update immediately
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @regression @p2
  Scenario: Verify deletion persistence — Operator absent after re-search
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User swipes Operator record from right to left
    And delete icon should be clearly visible after swipe
    When User clicks on "Delete" option
    Then Delete Confirmation popup should be displayed
    When User confirms operator deletion
    Then operator should be deleted successfully
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then deleted operator should not be present
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # NEGATIVE SCENARIOS — CANCEL DELETE
  # =========================================================

  @negative @regression @p1
  Scenario: Close Delete Confirmation popup with X button cancels deletion
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User swipes Operator record from right to left
    Then Edit option should be visible
    And delete icon should be clearly visible after swipe
    When User clicks on "Delete" option
    Then Delete Confirmation popup should be displayed
    When User closes Delete Confirmation popup without deleting
    Then operator should not be deleted
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @negative @regression @p2
  Scenario: Operator still visible in list after cancelling deletion
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User swipes Operator record from right to left
    And delete icon should be clearly visible after swipe
    When User clicks on "Delete" option
    Then Delete Confirmation popup should be displayed
    When User closes Delete Confirmation popup without deleting
    Then operator should not be deleted
    And operator record should be displayed in list
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # EDGE CASE SCENARIOS
  # =========================================================

  @sanity @p3
  Scenario: Partial swipe should not reveal Delete option
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When user performs partial swipe
    Then delete option should not trigger incorrectly
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @sanity @p3
  Scenario: Rapid swipe and Delete does not crash the app
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User swipes Operator record from right to left
    And delete icon should be clearly visible after swipe
    When User clicks on "Delete" option
    Then Delete Confirmation popup should be displayed
    When User closes Delete Confirmation popup without deleting
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list

  @negative @p3
  Scenario: Network failure during delete shows error message
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User swipes Operator record from right to left
    And delete icon should be clearly visible after swipe
    When User clicks on "Delete" option
    Then Delete Confirmation popup should be displayed
    When User confirms operator deletion
    Then error message should be displayed
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list


  # =========================================================
  # UI VALIDATION SCENARIOS
  # =========================================================

  @regression @p2
  Scenario: Verify Delete Confirmation popup elements are correctly laid out
    And User has already created an Operator
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Operator Name
    And User waits for search results to load
    Then system should display matching Operator results
    When User swipes Operator record from right to left
    Then Edit option should be visible
    And delete icon should be clearly visible after swipe
    And Duplicate option should be visible after swipe
    When User clicks on "Delete" option
    Then Delete Confirmation popup should be displayed
    And delete confirmation message should be displayed
    And "Delete" button should be visible in Delete Confirmation popup
    And X close button should be visible in Delete Confirmation popup
    When User closes Delete Confirmation popup without deleting
    When User clicks search close X button
    Then search field should be closed
    And module list should be in normal state
    And Operator should be navigate into "Operators" list