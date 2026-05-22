package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {
        "src/test/resources/features/CustomerLogin/01_Login.feature",
        "src/test/resources/features/CustomerLogin/Configurations/Users/UserUnitSubscription.feature"
    },
    glue = {"StepDefinitions.common", "StepDefinitions.configurations.login", "StepDefinitions.configurations.users", "hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/user-unit-subscription.html",
        "json:target/cucumber-reports/user-unit-subscription.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)
public class UserUnitSubscriptionRunner {
}