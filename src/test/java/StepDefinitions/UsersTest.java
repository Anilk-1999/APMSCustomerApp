package StepDefinitions;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import hooks.AppHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.UsersPage;
import utilities.TestRandomDataGenerator;

public class UsersTest {

    AndroidDriver driver;
    UsersPage usersPage;
    AppHooks hooks;

    public UsersTest(AppHooks hooks) {
        this.driver = hooks.getDriver();
    }

    @When("click on profile icon")
    public void click_on_profile_icon() {
        usersPage = new UsersPage(driver);
        usersPage.clickOnProfileIcon();
    }

@Then("verify that the {string} screen is displayed")
public void verify_that_the_screen_is_displayed(String accountPreferenceHeaderName) throws InterruptedException {
    Assert.assertEquals(usersPage.getAccountPreferenceHeader(), accountPreferenceHeaderName);
}
@When("click on {string} section")
public void click_on_section(String configurationExplorer) {
    usersPage.clickOnSectionHeader(configurationExplorer);
}
@Then("verify the {string} section displayed")
public void verify_the_section_displayed(String sectionHeaderText) {
    Assert.assertEquals(usersPage.getSectionHeaderText(sectionHeaderText), sectionHeaderText);
}
@When("click on {string} feature")
public void click_on_feature(String featureName) {
    usersPage.clickOnFeature(featureName);
}
@Then("verify that the user navigate to the {string} list screen")
public void verify_that_the_user_navigate_to_the_list_screen(String userListScreenTitle) {
    Assert.assertEquals(usersPage.getListPageTitle(), userListScreenTitle);
}
@When("click on add button")
public void click_on_add_button() {
    usersPage.clickOnAddButton();
}
@Then("verify that the user should navigate to the {string} screen")
public void verify_that_the_user_should_navigate_to_the_screen(String screenTitle) {
    Assert.assertEquals(usersPage.getPageHeaderText(), screenTitle);
}
// @When("enter all the following user details:")
// public void enter_all_the_following_user_details(io.cucumber.datatable.DataTable dataTable) {

//     // Convert the DataTable to a Map
//     Map<String, String> userDetails = dataTable.asMap(String.class, String.class);

//     // Example: Access values by key
//     String userName = userDetails.get("User Name");
//     String emailId = userDetails.get("Email Id");
//     String phoneNumber = userDetails.get("Phone Number");
//     String emergencyNumber = userDetails.get("Emergency Number");
//     String empCode = userDetails.get("Emp Code");
//     String bloodGroup = userDetails.get("Blood Group");
//     // String dateOfBirth = userDetails.get("Date of Birth");
//     // String dateOfJoining = userDetails.get("Date of Joining");
//     String address1 = userDetails.get("Address 1");
//     String address2 = userDetails.get("Address 2");
//     String pinCode = userDetails.get("Pin Code");
//     String roles = userDetails.get("Roles");

//     int date=22;
//     int month=01;
//     int year=1999;

//     int date1=02;
//     int month1=9;
//     int year1=2025;

//        usersPage.enterUserName(userName);
//        usersPage.enterEmail(emailId);
//        usersPage.enterPhone(phoneNumber);
//        usersPage.enterEmergencyNo(emergencyNumber);
//        usersPage.enterEmpCode(empCode);
//        usersPage.selectBloodGroup(bloodGroup);
//     //    usersPage.enterDOB(date, month, year);
//     //    usersPage.enterDOJ(date1, month1, year1);
//        usersPage.selectDateOfBirth();
//        usersPage.selectDateOfJoining();
//        usersPage.enterAddress1(address1);
//        usersPage.enterAddress2(address2);
//        usersPage.enterPinCode(pinCode);
//        usersPage.selectRole(roles);
// }


private Map<String, String> generatedUserData = new HashMap<>();

@When("enter all the following user details:")
public void enter_all_the_following_user_details(io.cucumber.datatable.DataTable dataTable) {

    // Convert DataTable to a Map
    Map<String, String> userDetails = dataTable.asMap(String.class, String.class);

    // Generate random data for each field
    String userName = TestRandomDataGenerator.randomUserName();
    String emailId = TestRandomDataGenerator.randomEmail();
    String phoneNumber = TestRandomDataGenerator.randomPhoneNumber();
    String emergencyNumber = TestRandomDataGenerator.randomPhoneNumber();
    String empCode = TestRandomDataGenerator.randomEmpCode();
    String bloodGroup = TestRandomDataGenerator.randomBloodGroup();
    String address1 = TestRandomDataGenerator.randomAddress();
    String address2 = TestRandomDataGenerator.randomAddress();
    String pinCode = TestRandomDataGenerator.randomPinCode();
    String roles = TestRandomDataGenerator.randomRole();

    // Optional: Save for later verification if needed
    generatedUserData.put("User Name", userName);
    generatedUserData.put("Email Id", emailId);
    generatedUserData.put("Phone Number", phoneNumber);
    generatedUserData.put("Emergency Number", emergencyNumber);
    generatedUserData.put("Emp Code", empCode);
    generatedUserData.put("Blood Group", bloodGroup);
    usersPage.selectDateOfBirth();
    usersPage.selectDateOfJoining();
    generatedUserData.put("Address 1", address1);
    generatedUserData.put("Address 2", address2);
    generatedUserData.put("Pin Code", pinCode);
    generatedUserData.put("Roles", roles);

    // Fill user details in app using Appium
    usersPage.enterUserName(userName);
    usersPage.enterEmail(emailId);
    usersPage.enterPhone(phoneNumber);
    usersPage.enterEmergencyNo(emergencyNumber);
    usersPage.enterEmpCode(empCode);
    usersPage.selectBloodGroup(bloodGroup);
    usersPage.selectDateOfBirth();
    usersPage.selectDateOfJoining();
    usersPage.enterAddress1(address1);
    usersPage.enterAddress2(address2);
    usersPage.enterPinCode(pinCode);
    usersPage.selectRole(roles);

    // Log the generated data
    System.out.println("Generated User Data:");
    generatedUserData.forEach((k, v) -> System.out.println(k + ": " + v));
}



@When("I click on the submit button")
public void i_click_on_the_submit_button() {
    usersPage.clickOnSubmitButton();
}
@Then("the user should be created successfully")
public void the_user_should_be_created_successfully() {
    Assert.assertEquals(usersPage.getListPageTitle(), "Users");
}
@Then("I should see a confirmation message {string}")
public void i_should_see_a_confirmation_message(String string) throws InterruptedException {
    usersPage.getConfirmationMsg();
}



}
