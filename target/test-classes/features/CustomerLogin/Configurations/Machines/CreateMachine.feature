Feature: Machine Creation with Complete Field and License Type Validation

  Background:
    When click on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When click on "configurations" section
    Then verify the "Machine" section displayed
    When click on "Machines" feature
    Then verify that the user navigate to the "Machines" list screen

  # ==================================================
  # ✅ NAVIGATION
  # ==================================================

  Scenario: Navigate to Add Machine screen
    When User clicks on "+ Add" button
    Then Add Machine screen should be displayed

  # ==================================================
  # ✅ COMMON FIELD POSITIVE SCENARIOS
  # ==================================================

  Scenario: Create machine with all common fields
    When User enters Machine Name
    And User enters Location
    And User enters Machine Code
    And User selects Machine Brand
    And User selects Machine Type
    And User selects IoT Device Type
    And User adds valid shifts (2 to 4 non-overlapping)
    Then shifts should be added successfully

  Scenario: Add and delete shifts
    When User clicks on "+" Add Shift
    And User selects shifts
    And User deletes one shift
    Then remaining shifts should be displayed correctly

  # ==================================================
  # ❌ SHIFT VALIDATIONS
  # ==================================================

  Scenario: Add less than minimum shifts
    When User adds only one shift
    Then validation error should be displayed

  Scenario: Add more than maximum shifts
    When User adds more than 4 shifts
    Then system should restrict selection

  Scenario: Add overlapping shifts
    When User selects overlapping shift timings
    Then overlap validation error should be displayed

  # ==================================================
  # ✅ PRODUCTION LICENSE TYPE
  # ==================================================

  Scenario: Create Production machine with all fields
    When User selects "Production" License Type
    And User enables Production Plan toggle
    And User enables Maintenance toggle
    And User enables Authorization Feature toggle
    And User enters Downtime (Days)
    And User sets Timeout Duration
    And User sets Idle Timeout Duration
    And User selects Login Type
    And User enables Machine Setup Approval
    And User enables Production Approval
    And User enables Stop Machine Without PA image
    And User enters Stoppage Timer (Min)
    And User enables Image Required for Stop Reason
    And User enters Reason Image Upload Timer (Min)
    And User enables Raw Material Entry
    And User enables Scrap Entry
    And User enables Maintenance Login
    And User sets Machine Offline Timeout (Days)
    And User sets Manual Health Reminder (Hrs)
    And clicks Submit
    Then Production machine should be created successfully

  # ==================================================
  # ❌ PRODUCTION VALIDATIONS
  # ==================================================

  Scenario: Authorization enabled without Downtime
    When User enables Authorization Feature
    And does not enter Downtime
    Then "Downtime is required" should be displayed

  Scenario: Stop Machine enabled without Stoppage Timer
    When User enables Stop Machine Without PA
    And does not enter Stoppage Timer
    Then validation error should be displayed

  Scenario: Image Required enabled without timer
    When User enables Image Required for Stop Reason
    And does not enter upload timer
    Then validation error should be displayed

  Scenario: Missing Timeout Duration
    When User does not set Timeout Duration
    Then validation error should be displayed

  Scenario: Missing Idle Timeout Duration
    When User does not set Idle Timeout Duration
    Then validation error should be displayed

  Scenario: Missing Login Type
    When User does not select Login Type
    Then validation error should be displayed

  # ==================================================
  # ✅ MONITOR LICENSE TYPE
  # ==================================================

  Scenario: Create Monitor machine with all fields
    When User selects "Monitor" License Type
    And User selects Operator
    And User enables Default Machine Status
    And User selects Reason
    And User sets Machine Offline Timeout (Days)
    And User sets Manual Health Reminder (Hrs)
    And clicks Submit
    Then Monitor machine should be created successfully

  # ==================================================
  # ❌ MONITOR VALIDATIONS
  # ==================================================

  Scenario: Missing Operator
    When User does not select Operator
    Then validation error should be displayed

  Scenario: Default status enabled without Reason
    When User enables Default Machine Status
    And does not select Reason
    Then "Reason is required" should be displayed

  # ==================================================
  # ✅ MAINTENANCE LICENSE TYPE
  # ==================================================

  Scenario: Create Maintenance machine
    When User selects "Maintenance" License Type
    And clicks Submit
    Then Maintenance machine should be created successfully

  # ==================================================
  # ❌ COMMON NEGATIVE SCENARIOS
  # ==================================================

  Scenario: Submit without mandatory fields
    When User clicks Submit without entering data
    Then all mandatory field errors should be displayed

  Scenario: Missing Machine Name
    Then "Machine Name is required" should be displayed

  Scenario: Duplicate Machine Name
    When User enters existing Machine Name
    Then duplicate error should be displayed

  Scenario: Duplicate Machine Code
    When User enters existing Machine Code
    Then duplicate error should be displayed

  # ==================================================
  # 🔎 FIELD VALIDATIONS
  # ==================================================


  Scenario Outline: Validate Downtime field
    When User enters "<input>" in Downtime
    Then "<error>" should be displayed

    Examples:
      | input | error                |
      |       | Downtime is required |
      | -1    | Invalid value        |

  # ==================================================
  # ⚠️ EDGE CASES
  # ==================================================

  Scenario: Enter only spaces in fields
    Then validation errors should be displayed

  Scenario: Rapid toggle enable/disable
    Then system should maintain correct state

  Scenario: Rapid submit clicks
    Then only one record should be created

  Scenario: Large input values
    Then system should handle correctly

  Scenario: Switch License Types repeatedly
    Then correct fields should be displayed dynamically

  # ==================================================
  # 📱 UI VALIDATIONS
  # ==================================================

  Scenario: Verify all fields visibility
    Then all machine creation fields should be displayed

  Scenario: Verify dynamic fields based on License Type
    Then respective fields should appear based on selection

  Scenario: Verify duration picker popup
    When User clicks duration field
    Then popup should be displayed with Save and Close options

  Scenario: Verify dropdown behavior
    Then dropdowns should display options correctly

  Scenario: Verify scroll functionality
    Then user should be able to scroll full form

  Scenario: Verify Submit button validation
    Then Submit should validate all fields