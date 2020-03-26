package ru.sberbank.autotests.common;

import org.openqa.selenium.WebDriver;

public enum WebDriverManager {
    INSTANCE;
    private static final Object sync = new Object();
    private ThreadLocal<WebDriver> currentDriver = new ThreadLocal<>();

    public static WebDriverManager webDriverManager() {
        return INSTANCE;
    }

    public WebDriver currentDriver() {
        if (currentDriver.get() == null) {
            synchronized (sync) {
                if (currentDriver.get() == null) {
                    currentDriver.set(Init.newDriver());
                }
            }
        }
        return currentDriver.get();
    }

    public void killCurrentDriver() {
        WebDriver driver = currentDriver.get();
        if (driver != null) {
            driver.quit();
            currentDriver.remove();
        }
    }

}
