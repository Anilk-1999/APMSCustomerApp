Feature: Login Functionality

  # ── Onboarding (runs once at app launch) ────────────────────────────────────

  Scenario: Verify onboarding screens and navigate to login
    Given the app is launched
    And Verify the "Digitization" screen is displayed
    When the user clicks on Next
    Then Verify the "World of Machines" screen should be displayed
    When the user clicks on Next again
    Then Verify the "Accelerating Growth" screen should displayed
    When the user clicks on Finish
    Then Verify the "Login to Your Account" screen should be visible
    When the user double taps on the APMS AI footer if needed

  # ── Negative: Both fields empty ─────────────────────────────────────────────

  # Scenario: Login with empty email and password shows both validation errors
  #   Given the user is on the login screen
  #   When the user clicks on Login
  #   Then "Email is required" validation error should be displayed
  #   And "Password is required" validation error should be displayed

  # ── Negative: Email missing ──────────────────────────────────────────────────

  # Scenario: Login with password filled but email empty shows email error
  #   Given the user is on the login screen
  #   When the user enters password "1234"
  #   And the user clicks on Login
  #   Then "Email is required" validation error should be displayed

  # ── Negative: Password missing ───────────────────────────────────────────────

  # Scenario: Login with email filled but password empty shows password error
  #   Given the user is on the login screen
  #   When the user enters email "anilkumar.m@apms.ai"
  #   And the user clicks on Login
  #   Then "Password is required" validation error should be displayed

  # ── Negative: Invalid email format ───────────────────────────────────────────

  # Scenario: Login with invalid email format shows format validation error
  #   Given the user is on the login screen
  #   When the user enters email "invalidemail"
  #   And the user enters password "1234"
  #   And the user clicks on Login
  #   Then "Enter valid email" validation error should be displayed

  # ── Negative: Wrong password ─────────────────────────────────────────────────

  # Scenario: Login with valid email but wrong password fails
  #   Given the user is on the login screen
  #   When the user enters email "anilkumar.m@apms.ai"
  #   And the user enters password "wrongpassword"
  #   And the user clicks on Login
  #   Then the user should remain on the login screen with an error

  # ── Negative: Unregistered email ─────────────────────────────────────────────

  # Scenario: Login with unregistered email fails
  #   Given the user is on the login screen
  #   When the user enters email "notregistered@test.com"
  #   And the user enters password "wrongpassword"
  #   And the user clicks on Login
  #   Then the user should remain on the login screen with an error

  # ── Positive: Successful login ───────────────────────────────────────────────

  Scenario: Verify user is able to login successfully
    Given the user is on the login screen
    When the user enters email "anilkumar.m@apms.ai"
    And the user enters password "1234"
    And the user clicks on Login
    Then Verify the user should be logged in successfully
