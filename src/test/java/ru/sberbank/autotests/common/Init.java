package ru.sberbank.autotests.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Init {
    private static final String PARAM_REMOTE_DRIVER_URL="remote.wevdriver.url";
    private static final String PARAM_DRIVER="driver";
    private static final String DEFAULT_DRIVER="chrome";
    private static Properties properties;
    private static DriverType driverType;
    private static long commonImplicityTimeoutSecs;
    private static long loadPageImplicityTimeoutSecs;
    private static long scriptImplicityTimeoutSecs;

    static {
        String propsPath = System.getProperty("config.file", "config.properties");
        try (InputStream inputStream = new FileInputStream(propsPath)) {
            properties = new Properties(System.getProperties());
            properties.load(inputStream);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Ошибка при чтении файла конфигурации " + propsPath, ex);
        }
        driverType = DriverType.valueOf(properties.getProperty(PARAM_DRIVER, DEFAULT_DRIVER).toUpperCase());
        commonImplicityTimeoutSecs = Long.parseLong(properties.getProperty("timeout.implicity.common.secs", "30"));
        loadPageImplicityTimeoutSecs = Long.parseLong(properties.getProperty("timeout.implicity.load.page.secs", "30"));
        scriptImplicityTimeoutSecs = Long.parseLong(properties.getProperty("timeout.implicity.scripts.secs", "30"));
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
        WebDriver driver = driverType.newDriver(properties);
        driver.manage().timeouts().implicitlyWait(commonImplicityTimeoutSecs, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(loadPageImplicityTimeoutSecs, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(scriptImplicityTimeoutSecs, TimeUnit.SECONDS);
        return driver;
    }

    private enum DriverType {
        CHROME {
            @Override
            public WebDriver newDriver(Properties properties) {
                if (isRemote(properties)) {
                    DesiredCapabilities capability = DesiredCapabilities.chrome();
                    // platform=ANY version = ""
                    // не стал добавлять версию хрома, имя теста, версию и тип ОС итд.
                    return new RemoteWebDriver(getRemoteDriverUrl(properties.getProperty(PARAM_REMOTE_DRIVER_URL)), capability);
                }
                System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.path", "src/test/resources/webdrivers/chromedriver.exe"));
                ChromeOptions options = new ChromeOptions();
                options.addArguments(properties.getProperty("chrome.capabilities", "").split(","));
                return new ChromeDriver(options);
            }
        },
        IE {
            @Override
            public WebDriver newDriver(Properties properties) {
                throw new UnsupportedOperationException("Драйвер IE пока не поддерживается");
            }
        },
        FF {
            @Override
            public WebDriver newDriver(Properties properties) {
                throw new UnsupportedOperationException("Драйвер FF пока не поддерживается");
            }
        },
        YANDEX {
            @Override
            public WebDriver newDriver(Properties properties) {
                throw new UnsupportedOperationException("Драйвер YANDEX пока не поддерживается");
            }
        };

        public abstract WebDriver newDriver(Properties properties);

        public static boolean isRemote(Properties properties) {
            String remoteDriverUrl = properties.getProperty(PARAM_REMOTE_DRIVER_URL);
            return (remoteDriverUrl!=null && !remoteDriverUrl.isEmpty());
        }

        public static URL getRemoteDriverUrl(String remoteDriverUrl) {
             try {
                 return new URL(properties.getProperty(PARAM_REMOTE_DRIVER_URL));
             } catch (MalformedURLException e) {
                 throw new IllegalArgumentException("Ошибка при получении URL удаленного вебдрайвера", e);
             }
        }


    }


}
