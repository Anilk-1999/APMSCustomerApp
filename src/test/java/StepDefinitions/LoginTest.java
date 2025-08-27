package StepDefinitions;

import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.LoginPage;
import testBase.BaseClass;

public class LoginTest extends BaseClass{
    
  LoginPage loginPage;

@Given("the app is launched")
public void the_app_is_launched() throws Exception {
  appLaunch();
}
   
@Given("Verify the {string} screen is displayed")
public void verify_the_screen_is_displayed(String string) {
 loginPage = new LoginPage(driver);
  Assert.assertEquals(loginPage.getDigitizationImageContentDesc(), "Digitization screen displayed and Next clicked: ");
}

@When("the user clicks on Next")
public void the_user_clicks_on_next() {
   loginPage.clickOnNextButton();
}

@Then("Verify the {string} screen should be displayed")
public void verify_the_screen_should_be_displayed(String string) {
    Assert.assertEquals(loginPage.getWorldOfMachinesImageContentDesc(), "World of Machines screen displayed and Next clicked: ");
}

@When("the user clicks on Next again")
public void the_user_clicks_on_next_again() {
   loginPage.clickOnNextButton();
}

@Then("Verify the {string} screen should displayed")
public void verify_the_screen_should_displayed(String string) {
    Assert.assertEquals(loginPage.getAcceleratingGrowthImageContentDesc(), "Accelerating Growth screen displayed and Finish clicked: ");
}

@When("the user clicks on Finish")
public void the_user_clicks_on_finish() {
   loginPage.clickOnFinishButton();
}

@Then("Verify the {string} screen should be visible")
public void verify_the_screen_should_be_visible(String string) {
    Assert.assertTrue(loginPage.isLoginToYourAccountVisible());
}

@When("the user double taps on the APMS AI footer if needed")
public void the_user_double_taps_on_the_apms_ai_footer_if_needed() {
   loginPage.doubleClickApmsAiFooterIfNeeded();
}

@When("the user enters email {string}")
public void the_user_enters_email(String string) {
   loginPage.enterEmail(string);
}

@When("the user enters password {string}")
public void the_user_enters_password(String string) {
   loginPage.enterPassword(string);
}

@When("the user clicks on Login")
public void the_user_clicks_on_login() {
   loginPage.clickLogin();
}

@Then("Verify the user should be logged in successfully")
public void verify_the_user_should_be_logged_in_successfully() {
    Assert.assertTrue(loginPage.isLoginSuccessful());
}























    // private AndroidDriver driver; // Initialize driver using BaseClass method or your driver setup

// @Test
// public void testLoginFunctionality() throws MalformedURLException {


//   LoginPage loginPage = new LoginPage(driver);

//   String digitizationImageDesc = loginPage.getDigitizationImageContentDesc();
//   Assert.assertEquals(digitizationImageDesc, "Digitization screen displayed and Next clicked: ");
//   loginPage.clickOnNextButton();

//   Assert.assertEquals(loginPage.getWorldOfMachinesImageContentDesc(), "World of Machines screen displayed and Next clicked: ");
//   loginPage.verifyWorldOfMachinesAndClickNext();

//   Assert.assertEquals(loginPage.getAcceleratingGrowthImageContentDesc(), "Accelerating Growth screen displayed and Finish clicked: ");
//   loginPage.clickOnFinishButton();

//   Assert.assertTrue(loginPage.isLoginToYourAccountVisible());

//   loginPage.doubleClickApmsAiFooterIfNeeded();

//   loginPage.enterEmail("anil.c3@apms.ai");
//   loginPage.enterPassword("1234");
//   loginPage.clickLogin();

//   Assert.assertTrue(loginPage.isLoginSuccessful());
// }
}