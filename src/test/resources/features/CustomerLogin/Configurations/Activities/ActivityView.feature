@regression @view @p2
Feature: View Activity Details from Activities List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Maintenance" section is displayed
    When User clicks on "Activities" feature
    Then verify user navigates to "Activities" list screen
    And User has already created an Activity

  @smoke @p1
  Scenario: Search and open View Activity popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Activity Name
    And User waits for search results to load
    Then system should display matching Activity results
    And newly created Activity should be visible in list
    When User clicks on newly created Activity record
    Then "View Activity" popup should be displayed
    And Activity ID should be visible
    And Status should be visible
    And Activity Name should be visible
    And Description should be visible if available
    And "Is Function Applicable" toggle should be visible
    And Close "X" button should be visible

  @regression @p1
  Scenario: Verify all View Activity fields are read-only
    When User opens View Activity popup
    Then Activity ID field should be read-only
    And Status field should be read-only
    And Activity Name field should be read-only
    And Description field should be read-only
    And "Is Function Applicable" toggle should be read-only
    And no input cursor should be displayed in any field
    And user should not be able to modify any value

  @negative @p1
  Scenario: Verify Status field is not clickable or editable
    When User opens View Activity popup
    Then Status field should be visible
    When User taps on Status field
    Then status confirmation popup should NOT be displayed
    And status value should remain unchanged
    And no edit action should be triggered

  @negative @p1
  Scenario: Verify Activity Name field cannot be edited
    When User opens View Activity popup
    And User taps on Activity Name field
    Then keyboard should NOT be displayed
    And cursor should NOT be visible
    And Activity Name value should remain unchanged

  @negative @p1
  Scenario: Verify Description field cannot be edited
    When User opens View Activity popup
    And User taps on Description field
    Then keyboard should NOT be displayed
    And cursor should NOT be visible
    And Description value should remain unchanged

  @negative @p1
  Scenario: Verify toggle cannot be changed from View popup
    When User opens View Activity popup
    Then current toggle state should be captured
    When User taps on "Is Function Applicable" toggle
    Then toggle state should remain unchanged
    And no update action should be triggered

  @regression @p1
  Scenario: Verify Activity view data accuracy
    When User opens View Activity popup
    Then displayed Activity ID should match created Activity data
    And displayed Status should match created Activity data
    And displayed Activity Name should match created Activity data
    And displayed Description should match created Activity data if available
    And displayed toggle state should match created Activity data

  @negative @p1
  Scenario: Verify no Save or Edit option is available in View popup
    When User opens View Activity popup
    Then Save button should NOT be visible
    And Submit button should NOT be visible
    And Update button should NOT be visible
    And Edit option should NOT be visible

  @regression @p2
  Scenario: Verify popup close functionality
    When User opens View Activity popup
    And User clicks on Close "X" button
    Then View Activity popup should be closed
    And User should return to Activities list screen
    And searched Activity should remain visible in list

  @regression @p2
  Scenario: Verify View Activity popup UI alignment
    When User opens View Activity popup
    Then popup title should be clearly visible
    And all labels should be properly aligned
    And all values should be readable
    And Close "X" button should be correctly positioned
    And popup should not overlap with screen edges

  @sanity @p3
  Scenario: Verify rapid click on Activity record opens only one popup
    When User clicks newly created Activity record multiple times quickly
    Then only one "View Activity" popup should be displayed

  #@negative @p3
  #Scenario: Verify deleted Activity cannot be viewed
   # When Activity is deleted from backend
   # And User searches deleted Activity Name
   # Then system should display "Record not found"
   # And View Activity popup should NOT be displayed

  #@negative @p3
  #Scenario: Verify unauthorized user cannot view Activity details
   # When User without view permission searches Activity
   # And User tries to open Activity details
    #Then system should show "Access Denied"
   # And View Activity popup should NOT be displayed

 # @negative @p3
  #Scenario: Verify network failure while opening Activity view
  #  When User turns off internet connection
   # And User clicks on Activity record
  #  Then system should display network error message
  #  And View Activity popup should NOT be displayed

 ## Scenario: Verify session timeout while opening Activity view
  #  When user session expires
 #   And User clicks on Activity record
  #  Then User should be redirected to login screen

  @regression @p3
  Scenario Outline: Validate Activity search behavior before view
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters "<input>" in search field
    And User waits for search results to load
    Then system should return "<result>"

    Examples:
      | input              | result           |
      | exact name         | record found     |
      | partial name       | relevant results |
      | uppercase          | record found     |
      | invalid input      | no results       |
      | special characters | safe handling    |