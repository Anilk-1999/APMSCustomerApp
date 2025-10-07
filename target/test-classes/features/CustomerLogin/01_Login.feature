Feature: Login Functionality

  Scenario: Verify user is able to login successfully

    Given the app is launched
    And Verify the "Digitization" screen is displayed
    When the user clicks on Next
    Then Verify the "World of Machines" screen should be displayed
    When the user clicks on Next again
    Then Verify the "Accelerating Growth" screen should displayed
    When the user clicks on Finish
    Then Verify the "Login to Your Account" screen should be visible
    When the user double taps on the APMS AI footer if needed
    And the user enters email "anil.c3@apms.ai"
    And the user enters password "Anil@123"
    And the user clicks on Login
    Then Verify the user should be logged in successfully