package lib;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.MutableCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class BrowserInitializer {

    private static final Logger log = LoggerFactory.getLogger(BrowserInitializer.class);

    private enum Browser {
        HTMLUNIT("default"),
        FIREFOX("firefox"),
        CHROME("chrome");

        private final String name;

        Browser(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static Browser getBrowser(String name) {
            for (Browser browser : values()) {
                if (browser.getName().equalsIgnoreCase(name)) {
                    return browser;
                }
            }
            return CHROME;
        }
    }

    private final HashMap<String, Object> browserCapabilities = new HashMap<>();
    private static final String BROWSER_NAME = "browserName";
    private Browser browser;
    private WebDriver driver = null;

    public BrowserInitializer() throws Exception {
        Config.loadConfig();
        EnvironmentConfig.loadConfig();
    }

    public void init() {
        browserCapabilities.put(BROWSER_NAME, Config.getBrowser());
        browser = Browser.getBrowser(browserCapabilities.get(BROWSER_NAME).toString());

        try {
            driver = initDriver(browser);
            if (driver == null) {
                throw new RuntimeException("WebDriver initialization failed - driver is null");
            }
            log.info("WebDriver initialized successfully for: {}", browser.getName());
        } catch (Exception e) {
            log.error("WebDriver initialization failed: {}", e.getMessage());
            throw new RuntimeException("WebDriver initialization failed: " + e.getMessage(), e);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        Helper.initialize(driver);
    }

    private WebDriver initDriver(Browser browser) {
        String hubUrl = resolveHubUrl();

        if (hubUrl != null && !hubUrl.isEmpty()) {
            return initRemoteDriver(browser, hubUrl);
        } else {
            return initLocalDriver(browser);
        }
    }

    private String resolveHubUrl() {
        String hubUrl = System.getProperty("hubUrl");
        if (hubUrl == null || hubUrl.isEmpty()) {
            try {
                hubUrl = Config.getStringValueForProperty("hubUrl");
            } catch (Exception e) {
                log.info("No Grid URL found in config - defaulting to local run");
            }
        }
        if (hubUrl != null) {
            hubUrl = hubUrl.trim();
            if (hubUrl.endsWith("/") && !hubUrl.endsWith("/wd/hub/")) {
                hubUrl = hubUrl.substring(0, hubUrl.length() - 1);
            }
        }
        return hubUrl;
    }

    private WebDriver initLocalDriver(Browser browser) {
        log.info("Running tests locally with browser: {}", browser.getName());

        if (browser == Browser.CHROME) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            return new ChromeDriver(options);
        } else if (browser == Browser.FIREFOX) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            return new FirefoxDriver(options);
        } else {
            log.error("Unsupported browser: {}", browser.getName());
            return null;
        }
    }

    private WebDriver initRemoteDriver(Browser browser, String hubUrl) {
        log.info("Initializing RemoteWebDriver with Grid: {}", hubUrl);
        checkGridHealth(hubUrl);

        MutableCapabilities options;
        if (browser == Browser.CHROME) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");

            String browserVersion = System.getProperty("browserVersion");
            if (browserVersion != null && !browserVersion.isEmpty()) {
                chromeOptions.setCapability("browserVersion", browserVersion);
            }

            addSelenoidOptionsIfNeeded(chromeOptions, hubUrl);
            options = chromeOptions;
        } else if (browser == Browser.FIREFOX) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            addSelenoidOptionsIfNeeded(firefoxOptions, hubUrl);
            options = firefoxOptions;
        } else {
            throw new RuntimeException("Unsupported browser for remote: " + browser.getName());
        }

        try {
            long startTime = System.currentTimeMillis();
            RemoteWebDriver remoteDriver = new RemoteWebDriver(new URL(hubUrl), options);
            remoteDriver.setFileDetector(new LocalFileDetector());
            long elapsed = System.currentTimeMillis() - startTime;
            log.info("Connected to Grid in {}s", elapsed / 1000);
            return remoteDriver;
        } catch (Exception e) {
            log.error("Grid connection failed: {}", e.getMessage());
            throw new RuntimeException("Remote driver init failed: " + e.getMessage(), e);
        }
    }

    private void addSelenoidOptionsIfNeeded(MutableCapabilities options, String hubUrl) {
        String useSelenoid = System.getProperty("useSelenoid", "false");
        if ("true".equalsIgnoreCase(useSelenoid) || (hubUrl != null && hubUrl.contains("selenoid"))) {
            var selenoidOptions = new HashMap<String, Object>();
            selenoidOptions.put("enableVNC", true);
            selenoidOptions.put("enableVideo", false);
            selenoidOptions.put("name", "Merchandising_testcases");
            selenoidOptions.put("sessionTimeout", "5m");
            options.setCapability("selenoid:options", selenoidOptions);
            log.info("Selenoid options enabled");
        }
    }

    private void checkGridHealth(String hubUrl) {
        try {
            String statusUrl;
            if (hubUrl.contains("/wd/hub")) {
                statusUrl = hubUrl.replace("/wd/hub", "/status");
            } else {
                statusUrl = hubUrl + "/status";
            }
            statusUrl = statusUrl.replaceAll("([^:])//+", "$1/");

            log.info("Checking Grid health at: {}", statusUrl);
            var connection = (java.net.HttpURLConnection) new URL(statusUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                log.info("Grid is responsive (HTTP 200)");
            } else {
                log.warn("Grid returned HTTP {} - may be experiencing issues", responseCode);
            }
        } catch (Exception e) {
            log.warn("Could not check Grid health: {}. Continuing anyway.", e.getMessage());
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
