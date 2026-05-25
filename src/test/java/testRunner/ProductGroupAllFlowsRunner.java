package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {
        "src/test/resources/features/CustomerLogin/01_Login.feature",
        "src/test/resources/features/CustomerLogin/Configurations/ProductGroups/ProductGroupCreation.feature",
        "src/test/resources/features/CustomerLogin/Configurations/ProductGroups/ProductGroupUpdate.feature",
        "src/test/resources/features/CustomerLogin/Configurations/ProductGroups/ProductGroupView.feature"
    },
    glue = {"StepDefinitions.common", "StepDefinitions.configurations.login", "StepDefinitions.configurations.productgroups", "hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/product-group-all-flows.html",
        "json:target/cucumber-reports/product-group-all-flows.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)
public class ProductGroupAllFlowsRunner {
}
