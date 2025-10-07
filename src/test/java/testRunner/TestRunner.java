package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"StepDefinitions", "hooks"},
    plugin = {"pretty", "html:target/cucumber-reports.html"},
    monochrome = true
)

public class TestRunner {

}
