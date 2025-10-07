package testBase;

import java.net.URL;
import java.time.Duration;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class BaseClass {

    private AndroidDriver driver;

    public AndroidDriver initDriver() throws Exception {
        if (driver == null) {
            UiAutomator2Options options = new UiAutomator2Options();
            options.setDeviceName("Galaxy A21s");
            options.setPlatformName("Android");
            options.setPlatformVersion("12.0"); 
            options.setAutomationName("UiAutomator2");
            options.setApp("C:\\Users\\ApkFiles\\APMS_Customer_19Sep25_v55.apk");
            options.setAppPackage("com.example.apms_mobile");
            options.setAppActivity("com.example.apms_mobile.MainActivity");
            options.setCapability("adbExecTimeout", 60000);
            // options.setNoReset(true);
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            System.out.println("✅ Driver initialized successfully");
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            System.out.println("✅ Driver closed successfully");
        }
    }
}
