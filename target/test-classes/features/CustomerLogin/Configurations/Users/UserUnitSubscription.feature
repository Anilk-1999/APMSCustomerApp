Feature: Unit Subscription Management
  As an admin
  I want to view unit subscriptions for a user
  So that I can verify assigned units

  Background:
    When click on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When click on "configurations" section
    Then verify the "User" section displayed
    When click on "Users" feature
    Then verify that the user navigate to the "Users" list screen

  # ==================================================
  # ✅ POSITIVE SCENARIOS
  # ==================================================

  Scenario: Verify navigation to Unit Subscription popup
    When user long presses on a user record
    Then action menu bottom sheet should be displayed
    When user clicks on "Unit Subscription" option
    Then Unit Subscription popup should be displayed

  Scenario: Verify Unit list is displayed
    When user opens unit subscription popup
    Then unit list should be displayed

  Scenario: Verify popup contains close button
    When unit subscription popup is displayed
    Then close button should be visible

  Scenario: Verify user can close popup using close button
    When user clicks on close button
    Then unit subscription popup should be closed

  # ==================================================
  # ❌ NEGATIVE SCENARIOS
  # ==================================================

  Scenario: Verify behavior when unit list is empty
    When backend returns empty unit list
    Then empty state message should be displayed

  Scenario: Verify API failure while loading unit list
    When backend API fails during unit fetch
    Then error message should be displayed

  Scenario: Verify network failure
    When user opens unit subscription popup without internet
    Then proper error message should be shown

  Scenario: Verify clicking outside popup
    When user taps outside popup
    Then popup should be dismissed

  # ==================================================
  # ⚠️ EDGE CASE SCENARIOS
  # ==================================================

  Scenario: Verify large unit list handling
    When unit list is large
    Then user should be able to scroll properly

  Scenario: Verify special characters in unit names
    When unit names contain special characters
    Then they should be displayed correctly

  Scenario: Verify rapid open/close actions
    When user repeatedly opens and closes popup
    Then application should not crash

  # ==================================================
  # 📱 UI VALIDATION SCENARIOS
  # ==================================================

  Scenario: Verify Unit Subscription popup UI elements
    When unit subscription popup is displayed
    Then popup should contain:
      | Unit List |
      | Close Button |

  Scenario: Verify scroll functionality
    Then user should be able to scroll unit list

  Scenario: Verify popup alignment
    Then popup should be properly aligned on screen

  Scenario: Verify overlay behavior
    Then background should be dimmed when popup is open

  # ==================================================
  # 🔎 FIELD / DISPLAY VALIDATION
  # ==================================================

  Scenario: Verify unit names display correctly
    When unit list is displayed
    Then all unit names should be visible and readable