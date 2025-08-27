package testBase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.URL;
import java.time.Duration;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class BaseClass {

    protected AndroidDriver driver;

    @BeforeClass
    public void appLaunch() throws Exception {
        try {
            UiAutomator2Options options = new UiAutomator2Options();
            options.setDeviceName("Galaxy A21s");
            // options.setDeviceName("emulator-5554");
            options.setPlatformName("Android");
            options.setPlatformVersion("12.0");
            // options.setPlatformVersion("28.0");  
            options.setAutomationName("UiAutomator2");
            options.setApp("E:\\apmsFiles\\APMS_Customer_31July2025_v51.apk");
            options.setCapability("appPackage", "com.example.apms_mobile");
            options.setCapability("appActivity", "com.example.apms_mobile.MainActivity");

            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            Thread.sleep(2000);
            options.setCapability("adbExecTimeout", 60000);

            System.out.println("✅ Driver initialized successfully");

        } catch (Exception e) {
            System.out.println("❌ Failed to initialize driver: " + e.getMessage());
            throw e; // rethrow so TestNG marks test as failed
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("✅ Driver closed successfully");
        }
    }
}
