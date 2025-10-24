package StepDefinitions;

import java.util.HashMap;
import java.util.Map;

import hooks.AppHooks;
import io.cucumber.java.en.*;
import pageObject.OperatorPage;
import pageObject.UsersPage;
import utilities.TestRandomDataGenerator;

public class OperatorTest {

    OperatorPage operatorPage;
    UsersPage usersPage;

    private Map<String, String> generatedOperatorData = new HashMap<>();

    @When("enter all the following operator details:")
    public void enter_all_the_following_operator_details(io.cucumber.datatable.DataTable dataTable) {
    // Convert DataTable to Map for easier access
    Map<String, String> operatorDetails = dataTable.asMap(String.class, String.class);

    operatorPage = new OperatorPage(AppHooks.getDriver());
    usersPage = new UsersPage(AppHooks.getDriver());

    // Generate random data for each field
    String operatorName = TestRandomDataGenerator.randomUserName();
    String emailId = TestRandomDataGenerator.randomEmail();
    String phoneNumber = TestRandomDataGenerator.randomPhoneNumber();
    String emergencyNumber = TestRandomDataGenerator.randomPhoneNumber();
    String operatorCode = TestRandomDataGenerator.randomEmpCode();
    String bloodGroup = TestRandomDataGenerator.randomBloodGroup();
    String address1 = TestRandomDataGenerator.randomAddress();
    String address2 = TestRandomDataGenerator.randomAddress();
    String pinCode = TestRandomDataGenerator.randomPinCode();
    String city = operatorDetails.get("City");
    String state = operatorDetails.get("State");
    String country = operatorDetails.get("Country");

    // Optional: Save for later verification if needed
    generatedOperatorData.put("Operator Name", operatorName);
    generatedOperatorData.put("Email Id", emailId);
    generatedOperatorData.put("Phone Number", phoneNumber);
    generatedOperatorData.put("Emergency Number", emergencyNumber);
    generatedOperatorData.put("Operator Code", operatorCode);
    generatedOperatorData.put("Blood Group", bloodGroup);
    generatedOperatorData.put("Address 1", address1);
    generatedOperatorData.put("Address 2", address2);
    generatedOperatorData.put("Pin Code", pinCode);

    // Fill operator details in app using Appium
    operatorPage.enterOperatorName(operatorName);
    operatorPage.enterOptionalEmail(emailId);
    usersPage.enterPhone(phoneNumber);
    usersPage.enterEmergencyNo(emergencyNumber);
    operatorPage.enterOperatorCode(operatorCode);
    usersPage.selectBloodGroup(bloodGroup);
    usersPage.selectDateOfBirth();
    usersPage.selectDateOfJoining();
    usersPage.enterAddress1(address1);
    usersPage.enterAddress2(address2);
    usersPage.enterPinCode(pinCode);
    operatorPage.enterCity(city);
    operatorPage.enterState(state);
    operatorPage.enterCountry(country);

    // Log the generated data
    System.out.println("Generated Operator Data:");
    generatedOperatorData.forEach((k, v) -> System.out.println(k + ": " + v));

    }
    @Then("the operator should be created successfully")
    public void the_operator_should_be_created_successfully() {
       
    }
}
