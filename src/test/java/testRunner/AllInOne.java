package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {
        "src/test/resources/features/CustomerLogin/01_Login.feature",
        "src/test/resources/features/CustomerLogin/Configurations/Activities/ActivityCreation.feature",
        "src/test/resources/features/CustomerLogin/Configurations/Activities/ActivityUpdate.feature",
        "src/test/resources/features/CustomerLogin/Configurations/Activities/ActivityView.feature"
    },
    glue = {"StepDefinitions.common", "StepDefinitions.configurations.login", "StepDefinitions.configurations.activities", "hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/activities.html",
        "json:target/cucumber-reports/activities.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)
public class AllInOne {
}
