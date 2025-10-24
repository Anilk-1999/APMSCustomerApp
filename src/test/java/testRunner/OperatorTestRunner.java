package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {"src/test/resources/features/CustomerLogin/01_Login.feature", "src/test/resources/features/CustomerLogin/Configurations/Operators/03_OperatorCreation.feature"},
    glue = {"StepDefinitions", "hooks"},
    plugin = {"pretty", "html:target/cucumber-reports.html"},
    monochrome = true
)

public class OperatorTestRunner {

}
