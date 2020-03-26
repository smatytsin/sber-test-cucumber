package ru.sberbank.autotests.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Init {
    private static Properties properties;
    private static DriverType driverType;
    private static long commonImplicityTimeoutSecs;
    private static long loadPageImplicityTimeoutSecs;
    private static long scriptImplicityTimeoutSecs;

    static {
        String propsPath = System.getProperty("config.file", "config.properties");
        try (InputStream inputStream = new FileInputStream(propsPath)) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Ошибка при чтении файла конфигурации " + propsPath, ex);
        }
        driverType = DriverType.valueOf(properties.getProperty("driver", "CHROME").toUpperCase());
        commonImplicityTimeoutSecs = Long.parseLong(properties.getProperty("timeout.implicity.common.secs", "10"));
        loadPageImplicityTimeoutSecs = Long.parseLong(properties.getProperty("timeout.implicity.load.page.secs", "10"));
        scriptImplicityTimeoutSecs = Long.parseLong(properties.getProperty("timeout.implicity.scripts.secs", "10"));
    }

    private Init() {
    }

    public static String getProperty(String name) {
        return properties.getProperty(name);
    }

    public static String getProperty(String name, String def) {
        return properties.getProperty(name, def);
    }

    public static WebDriver newDriver() {
        WebDriver driver = driverType.newDriver();
        driver.manage().timeouts().implicitlyWait(commonImplicityTimeoutSecs, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(loadPageImplicityTimeoutSecs, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(scriptImplicityTimeoutSecs, TimeUnit.SECONDS);
        return driver;
    }

    private enum DriverType {
        CHROME {
            @Override
            public WebDriver newDriver() {
                System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.path", "src/test/resources/webdrivers/chromedriver.exe"));
                ChromeOptions options = new ChromeOptions();
                options.addArguments(properties.getProperty("chrome.capabilities", "").split(","));
                return new ChromeDriver(options);
            }
        },
        IE {
            @Override
            public WebDriver newDriver() {
                throw new UnsupportedOperationException("Драйвер IE пока не поддерживается");
            }
        },
        FF {
            @Override
            public WebDriver newDriver() {
                throw new UnsupportedOperationException("Драйвер FF пока не поддерживается");
            }
        },
        YANDEX {
            @Override
            public WebDriver newDriver() {
                throw new UnsupportedOperationException("Драйвер YANDEX пока не поддерживается");
            }
        };

        public abstract WebDriver newDriver();
    }


}
