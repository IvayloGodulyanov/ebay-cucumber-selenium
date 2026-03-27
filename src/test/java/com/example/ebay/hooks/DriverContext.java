package com.example.ebay.hooks;

import org.openqa.selenium.WebDriver;

public final class DriverContext {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverContext() {
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static void clear() {
        DRIVER.remove();
    }
}
