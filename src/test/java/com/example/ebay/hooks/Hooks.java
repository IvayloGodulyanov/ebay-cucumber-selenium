package com.example.ebay.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class Hooks {

    @Before
    public void setUp() {
        SoftAssertionsContext.set(new SoftAssertions());

        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        DriverContext.setDriver(driver);
    }

    @After
    public void tearDown() {
        try {
            SoftAssertions softly = SoftAssertionsContext.get();
            if (softly != null) {
                softly.assertAll();
            }
        } finally {
            WebDriver driver = DriverContext.getDriver();
            if (driver != null) {
                driver.quit();
            }
            DriverContext.clear();
            SoftAssertionsContext.clear();
        }
    }
}
