package testRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {
        "src/test/resources/features/CustomerLogin/01_Login.feature",
        "src/test/resources/features/CustomerLogin/Configurations/ProductSetupTypes/ProductSetupTypeView.feature"
    },
    glue = {"StepDefinitions.common", "StepDefinitions.configurations.login", "StepDefinitions.configurations.productsetuptypes", "hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/product-setup-type-view.html",
        "json:target/cucumber-reports/product-setup-type-view.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true
)
public class ProductSetupTypeViewRunner {
}
