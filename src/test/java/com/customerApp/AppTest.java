package com.customerApp;

import java.net.MalformedURLException;
import org.testng.annotations.Test;
import java.net.URL;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;


public class AppTest {

   @Test
public void testMainMethod() throws MalformedURLException {
    UiAutomator2Options options = new UiAutomator2Options()
            .setDeviceName("Galaxy A21s")
            .setPlatformName("Android")
            .setPlatformVersion("12.0")
            .setApp("E:\\apmsFiles\\APMS_Customer_31July2025_v51.apk")
            .setAutomationName("UiAutomator2");

    AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
    System.out.println("hello i am automation test");

    // driver.quit();
}

}