package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {
        "src/test/resources/features/CustomerLogin/01_Login.feature",
        "src/test/resources/features/CustomerLogin/Configurations/RawMaterials/RawMaterialCreation.feature",
        "src/test/resources/features/CustomerLogin/Configurations/RawMaterials/RawMaterialUpdate.feature",
        "src/test/resources/features/CustomerLogin/Configurations/RawMaterials/RawMaterialView.feature"
    },
    glue = {"StepDefinitions.common", "StepDefinitions.configurations.login", "StepDefinitions.configurations.rawmaterials", "hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/raw-material-all-flows.html",
        "json:target/cucumber-reports/raw-material-all-flows.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)
public class RawMaterialAllFlowsRunner {
}
