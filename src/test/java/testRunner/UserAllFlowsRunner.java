package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {
        "src/test/resources/features/CustomerLogin/01_Login.feature",
        "src/test/resources/features/CustomerLogin/Configurations/Users/UserCreation.feature",
        "src/test/resources/features/CustomerLogin/Configurations/Users/UserUpdate.feature",
        "src/test/resources/features/CustomerLogin/Configurations/Users/UserMachineSubscription.feature",
        "src/test/resources/features/CustomerLogin/Configurations/Users/UserUnitSubscription.feature",
        "src/test/resources/features/CustomerLogin/Configurations/Users/ViewUserDetails.feature"
    },
    glue = {"StepDefinitions.common", "StepDefinitions.configurations.login", "StepDefinitions.configurations.users", "hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/user-all-flows.html",
        "json:target/cucumber-reports/user-all-flows.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)
public class UserAllFlowsRunner {
}
