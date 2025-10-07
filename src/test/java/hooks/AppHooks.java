package hooks;

import io.cucumber.java.BeforeAll;
import io.appium.java_client.android.AndroidDriver;
import testBase.BaseClass;

public class AppHooks {

    private static BaseClass base;
    private static AndroidDriver driver;

    @BeforeAll
    public static void setUp() throws Exception {
        base = new BaseClass();
        driver = base.initDriver();
    }

    // @AfterAll
    // public void tearDown() {
    //     if (base != null) {
    //         base.quitDriver();
    //     }
    // }

    // Getter to provide driver to StepDefinitions
    public static AndroidDriver getDriver() {
        return driver;
    }
}
