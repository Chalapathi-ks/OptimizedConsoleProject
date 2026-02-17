package lib;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.MutableCapabilities;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static org.openqa.selenium.chrome.ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY;

public class BrowserInitializer {


    private enum Browser {

        HTMLUNIT("default"),
        FIREFOX("firefox"),
        CHROME("chrome"),
        PHANTOMJS("phantomjs");

        private String name;

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
            return HTMLUNIT;
        }
    }



    private HashMap<String, Object> browserCapabilities = new HashMap<String, Object>();

    private static final String BROWSER_NAME = "browserName";
    private Browser browser;

    private static final String geckoDriverPath="/usr/local/bin/geckodriver";

    private  WebDriver driver=null;



    public BrowserInitializer() throws Exception {

        Config.loadConfig();
        EnvironmentConfig.loadConfig();
    }

    public void init() {
        browserCapabilities.put(BROWSER_NAME, Config.getBrowser());
        DesiredCapabilities capabilities = new DesiredCapabilities();
        browser = setDesiredCapabilities(capabilities);

        try {
            System.out.println("[DEBUG] hubUrl: " + System.getProperty("hubUrl"));
            System.out.println("[DEBUG] browser: " + browser);
            System.out.println("[DEBUG] capabilities: " + capabilities);
            driver = initDriver(browser, capabilities);
            if (driver == null) {
                throw new RuntimeException("WebDriver initialization failed! Driver is null.");
            }
            // Enable LocalFileDetector ONLY for remote Grid sessions (hubUrl present)
            String configuredHub = System.getProperty("hubUrl");
            if (configuredHub != null && !configuredHub.isEmpty() && driver instanceof RemoteWebDriver && !(driver instanceof ChromeDriver) && !(driver instanceof FirefoxDriver)) {
                ((RemoteWebDriver) driver).setFileDetector(new org.openqa.selenium.remote.LocalFileDetector());
                System.out.println("[INFO] Enabled LocalFileDetector for RemoteWebDriver (file uploads).");
            }
            System.out.println("[INFO] ✅ WebDriver initialized successfully.");
        } catch (Exception e) {
            System.err.println("[ERROR] Exception during WebDriver initialization:");
            e.printStackTrace();
            throw new RuntimeException("[ERROR] WebDriver initialization failed: " + e.getMessage(), e);
        }

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        Helper.initialize(driver);
    }



    private WebDriver initDriver(Browser browser, DesiredCapabilities capabilities)
    {
        WebDriver driver = null;
        String hubUrl = System.getProperty("hubUrl");

        if (hubUrl == null || hubUrl.isEmpty()) {
            try {
                hubUrl = Config.getStringValueForProperty("hubUrl");
            } catch (Exception e) {
                System.out.println("[INFO] No Grid URL found in config. Defaulting to local run.");
            }
        }

        try {
            if (hubUrl != null && !hubUrl.isEmpty()) {
                // Ensure URL is properly formatted - keep /wd/hub if present (Selenium 3 format)
                // Remove trailing slashes to avoid double slashes, but preserve /wd/hub endpoint
                hubUrl = hubUrl.trim();
                if (hubUrl.endsWith("/") && !hubUrl.endsWith("/wd/hub/")) {
                    hubUrl = hubUrl.substring(0, hubUrl.length() - 1);
                }
                System.out.println("[INFO] 🌐 Initializing RemoteWebDriver with Grid: " + hubUrl);

                // Set HTTP client timeouts (matching working project - no special timeout settings)
                // Working project doesn't set these, so we'll use defaults or minimal settings
                System.setProperty("webdriver.http.factory", "jdk-http-client");

                MutableCapabilities options;
                if (browser == Browser.CHROME) {
                    ChromeOptions chromeOptions = new ChromeOptions();
                    
                    // Minimal Chrome arguments - only what's essential for Grid
                    // Too many args can cause capability mismatch with Grid nodes
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    // REMOVED: --disable-gpu, --remote-debugging-port=0, --start-maximized
                    // These may cause nodes to reject the session
                    
                    // Optional: Set browserVersion if provided
                    String browserVersion = System.getProperty("browserVersion");
                    if (browserVersion != null && !browserVersion.isEmpty()) {
                        chromeOptions.setCapability("browserVersion", browserVersion);
                        System.out.println("[INFO] Using browserVersion: " + browserVersion);
                    }
                    
                    // Conditionally set selenoid:options only if explicitly enabled
                    // Standard Selenium Grid doesn't support selenoid:options, causing sessions to queue
                    String useSelenoid = System.getProperty("useSelenoid", "false");
                    if ("true".equalsIgnoreCase(useSelenoid) || hubUrl != null && hubUrl.contains("selenoid")) {
                    java.util.Map<String, Object> selenoidOptions = new java.util.HashMap<>();
                    selenoidOptions.put("enableVNC", true);
                    selenoidOptions.put("enableVideo", false);
                    selenoidOptions.put("name", "Merchandising_testcases");
                    selenoidOptions.put("sessionTimeout", "5m");
                    chromeOptions.setCapability("selenoid:options", selenoidOptions);
                        System.out.println("[INFO] Selenoid options enabled");
                    } else {
                        System.out.println("[INFO] Using standard Selenium Grid capabilities (selenoid:options disabled)");
                    }
                    
                    // REMOVED: platformName - Selenoid handles this differently
                    // REMOVED: loggingPrefs - causes issues
                    
                    options = chromeOptions;
                } else if (browser == Browser.FIREFOX) {
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setCapability("browserName", "firefox");
                    
                    // Conditionally set selenoid:options only if explicitly enabled
                    // Standard Selenium Grid doesn't support selenoid:options, causing sessions to queue
                    String useSelenoid = System.getProperty("useSelenoid", "false");
                    if ("true".equalsIgnoreCase(useSelenoid) || hubUrl != null && hubUrl.contains("selenoid")) {
                    java.util.Map<String, Object> selenoidOptions = new java.util.HashMap<>();
                    selenoidOptions.put("enableVNC", true);
                    selenoidOptions.put("enableVideo", false);
                    selenoidOptions.put("name", "Merchandising_testcases");
                    selenoidOptions.put("sessionTimeout", "5m");
                    firefoxOptions.setCapability("selenoid:options", selenoidOptions);
                        System.out.println("[INFO] Selenoid options enabled for Firefox");
                    } else {
                        System.out.println("[INFO] Using standard Selenium Grid capabilities for Firefox (selenoid:options disabled)");
                    }
                    
                    if (capabilities.getCapability("marionette") != null) {
                        firefoxOptions.setCapability("marionette", capabilities.getCapability("marionette"));
                    }
                    options = firefoxOptions;
                } else {
                    options = capabilities;
                }

                // REMOVED: se:downloadsEnabled - may cause capability mismatch with some Grid nodes
                // Only enable if explicitly needed
                // options.setCapability("se:downloadsEnabled", true);

                // Log capabilities being sent for debugging
                System.out.println("[INFO] Capabilities configured for Grid connection");
                System.out.println("[DEBUG] Capabilities being sent: " + options.asMap());
                
                // Enhanced Grid health check - verify nodes are registered
                if (hubUrl != null && !hubUrl.isEmpty()) {
                    try {
                        // Convert hub URL to status URL
                        String statusUrl;
                        if (hubUrl.contains("/wd/hub")) {
                            statusUrl = hubUrl.replace("/wd/hub", "/status");
                        } else if (hubUrl.endsWith("/")) {
                            statusUrl = hubUrl + "status";
                        } else {
                            statusUrl = hubUrl + "/status";
                        }
                        // Normalize any double slashes (but preserve http://)
                        statusUrl = statusUrl.replaceAll("([^:])//+", "$1/");
                        if (statusUrl.contains("selenium-hub.netcorein.com")) {
                            statusUrl = "http://selenium-hub.netcorein.com:4444/status";
                        }
                        
                        System.out.println("[INFO] Checking Selenium Grid health at: " + statusUrl);
                        java.net.URL url = new java.net.URL(statusUrl);
                        java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(5000);
                        connection.setReadTimeout(10000);
                        
                        int responseCode = connection.getResponseCode();
                        if (responseCode == 200) {
                            // Read response to check for nodes
                            java.io.BufferedReader reader = new java.io.BufferedReader(
                                new java.io.InputStreamReader(connection.getInputStream()));
                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            reader.close();
                            
                            String responseBody = response.toString();
                            System.out.println("[INFO] Selenium Grid is responsive (HTTP 200)");
                            
                            // Check if nodes are registered
                            if (responseBody.contains("\"nodes\"") || responseBody.contains("\"value\"")) {
                                // Try to parse node count
                                if (responseBody.contains("\"nodes\":") || responseBody.contains("\"nodeCount\"")) {
                                    System.out.println("[INFO] Grid nodes detected in status response");
                                } else {
                                    System.out.println("[WARN] Grid is responsive but node status unclear - check Grid UI");
                                }
                            } else {
                                System.out.println("[WARN] Could not determine node status from Grid response");
                            }
                        } else {
                            System.out.println("[WARN] Selenium Grid returned HTTP " + responseCode + ", may be experiencing issues");
                        }
                    } catch (Exception e) {
                        System.out.println("[WARN] Could not check Selenium Grid health: " + e.getMessage() + ". Continuing anyway...");
                    }
                }
                
                // Simplified: Direct connection (matching working project)
                // Selenoid handles retries better, so we don't need complex retry logic
                RemoteWebDriver remoteDriver = null;
                try {
                    System.out.println("[INFO] Attempting to connect to Selenoid Grid (timeout: 300s)...");
                    long startTime = System.currentTimeMillis();
                    
                    // Create RemoteWebDriver - Selenoid will queue session and match to nodes
                    // Read timeout is set to 300s to allow Grid time to match session to available nodes
                    remoteDriver = new RemoteWebDriver(new URL(hubUrl), options);
                    
                    long elapsed = System.currentTimeMillis() - startTime;
                    System.out.println("[INFO] ✅ Successfully connected to Grid (took " + (elapsed/1000) + "s)");
                    return remoteDriver;
                } catch (org.openqa.selenium.SessionNotCreatedException e) {
                    System.err.println("[ERROR] Session creation failed: " + e.getMessage());
                    if (e.getCause() != null) {
                        System.err.println("[ERROR] Root cause: " + e.getCause().getClass().getSimpleName() + ": " + e.getCause().getMessage());
                    }
                    throw e;
                } catch (Exception e) {
                    System.err.println("[ERROR] Connection failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                    if (e.getCause() != null) {
                        System.err.println("[ERROR] Root cause: " + e.getCause().getClass().getSimpleName() + ": " + e.getCause().getMessage());
                    }
                    throw e;
                }
            } else {
                System.out.println("[INFO] 🖥 Running tests in local browser: " + browser.getName());

                if (browser == Browser.CHROME) {
                    return new ChromeDriver(capabilities);
                } else if (browser == Browser.FIREFOX) {
                    System.setProperty("webdriver.gecko.driver", geckoDriverPath);
                    capabilities.setCapability("marionette", true);
                    return new FirefoxDriver(capabilities);
                } else {
                    System.err.println("[WARN] Unsupported browser fallback: " + browser.getName());
                    return null;
                }
            }
        } catch (org.openqa.selenium.SessionNotCreatedException e) {
            System.err.println("[ERROR] ❌ Session creation failed - Grid rejected the session request");
            System.err.println("[ERROR] =========================================");
            System.err.println("[ERROR] MOST LIKELY CAUSE: No nodes are registered/available in the Grid");
            System.err.println("[ERROR] =========================================");
            // Convert hub URL to UI URL
            String gridUiUrl = "https://selenium-hub.netcorein.com/ui/#";
            if (hubUrl != null && hubUrl.contains("selenium-hub.netcorein.com")) {
                gridUiUrl = "https://selenium-hub.netcorein.com/ui/#";
            } else if (hubUrl != null) {
                // Generic conversion for other Grid URLs
                gridUiUrl = hubUrl.replace(":4444/wd/hub", "").replace("http://", "https://").replace("https://", "https://") + "/ui/#";
            }
            System.err.println("[ERROR] Check the Grid UI: " + gridUiUrl);
            System.err.println("[ERROR] Look for:");
            System.err.println("[ERROR]   1. 'Concurrency: 0%' or '0/X' - means NO nodes are running");
            System.err.println("[ERROR]   2. Queue size > 0 but no active sessions - nodes not accepting sessions");
            System.err.println("[ERROR]   3. Go to 'Overview' tab to see if any nodes are registered");
            System.err.println("[ERROR] ");
            System.err.println("[ERROR] Other possible causes:");
            System.err.println("[ERROR]   1. Capabilities don't match any available nodes");
            System.err.println("[ERROR]   2. Nodes are unhealthy or busy");
            System.err.println("[ERROR]   3. Network connectivity issues");
            System.err.println("[ERROR] ");
            System.err.println("[ERROR] Exception details: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("[ERROR] Root cause: " + e.getCause().getClass().getSimpleName() + ": " + e.getCause().getMessage());
            }
            e.printStackTrace();
            throw new RuntimeException("Driver init failed for: " + browser.getName() + " - Session creation rejected. Check Grid nodes and capabilities.", e);
        } catch (Exception e) {
            String exceptionType = e.getClass().getSimpleName();
            String exceptionMsg = e.getMessage();
            
            System.err.println("[ERROR] Failed to initialize WebDriver: " + exceptionType);
            System.err.println("[ERROR] Exception message: " + exceptionMsg);
            System.err.println("[ERROR] Hub URL: " + (hubUrl != null ? hubUrl : "not set"));
            System.err.println("[ERROR] Browser: " + browser.getName());
            
            // Check for common connection issues
            if (exceptionMsg != null) {
                if (exceptionMsg.contains("timeout") || exceptionMsg.contains("Timeout")) {
                    System.err.println("[ERROR] Connection timeout detected - Grid may be unreachable or overloaded");
                } else if (exceptionMsg.contains("refused") || exceptionMsg.contains("connect")) {
                    System.err.println("[ERROR] Connection refused - Grid may be down or unreachable");
                } else if (exceptionMsg.contains("No route to host") || exceptionMsg.contains("Network")) {
                    System.err.println("[ERROR] Network issue - Check network connectivity to Grid");
                }
            }
            
            if (e.getCause() != null) {
                System.err.println("[ERROR] Root cause: " + e.getCause().getClass().getSimpleName() + ": " + e.getCause().getMessage());
            }
            
            e.printStackTrace();
            throw new RuntimeException("Driver init failed for: " + browser.getName() + " - " + exceptionType + ": " + exceptionMsg, e);
        }
    }


    private void setChromeCapabilities(DesiredCapabilities capabilities)
    {
        String hubUrl = System.getProperty("hubUrl");
        if ((hubUrl != null && !hubUrl.isEmpty()) || 
            (Config.getStringValueForProperty("hubUrl") != null && !Config.getStringValueForProperty("hubUrl").isEmpty())) {
            // Remote run: do NOT set ChromeDriver path or related capabilities
            capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
            // REMOVED: LoggingPreferences - causes capability mismatch with Grid nodes
            // Many Grid nodes don't support logging preferences, causing sessions to queue indefinitely
            // LoggingPreferences preferences = new LoggingPreferences();
            // preferences.enable(LogType.BROWSER, Level.ALL);
            // capabilities.setCapability(CapabilityType.LOGGING_PREFS, preferences);
            return;
        }
        String chromeDriverKey = CHROME_DRIVER_EXE_PROPERTY; // "webdriver.chrome.driver"
        String chromeDriverPath = System.getenv("CHROME_DRIVER_PATH");
        if (chromeDriverPath != null && !chromeDriverPath.isEmpty()) {
            System.out.println("[DEBUG] Using CHROME_DRIVER_PATH from environment: " + chromeDriverPath);
            System.setProperty(chromeDriverKey, chromeDriverPath);
            capabilities.setCapability(chromeDriverKey, chromeDriverPath);
        } else {
            String defaultPath = getChromeDriverPath();
            System.out.println("[DEBUG] CHROME_DRIVER_PATH not set. Using default: " + defaultPath);
            System.setProperty(chromeDriverKey, defaultPath);
            capabilities.setCapability(chromeDriverKey, defaultPath);
        }
        capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
        LoggingPreferences preferences = new LoggingPreferences();
        preferences.enable(LogType.BROWSER, Level.ALL);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, preferences);
    }

    private Browser setDesiredCapabilities(DesiredCapabilities capabilities)
    {
        browser= Browser.getBrowser(browserCapabilities.get(BROWSER_NAME).toString());
        capabilities.setBrowserName(browser.getName());
        if(browser.name().equals("CHROME"))
            setChromeCapabilities(capabilities);

        return browser;

    }


    private void maximizeBrowserWindow()
    {
        if(driver==null)
            throw new NullPointerException("The WebDriver is not Initialised ");

        Toolkit toolkit=Toolkit.getDefaultToolkit();
        int width=(int)toolkit.getScreenSize().getWidth();
        int height=(int) toolkit.getScreenSize().getHeight();
        //Maximize window according to width and height
        driver.manage().window().setSize(new Dimension(width,height));
        //make window full screen
        driver.manage().window().fullscreen();

    }


    private String getChromeDriverPath()
    {
        String path="src"+File.separator+"main"+File.separator+"resources"+File.separator+"driver"+File.separator;
        String os=System.getProperty("os.name");

        if(os.toLowerCase().contains("windows"))
            path=path+"chromedriver.exe";
        else if(os.toLowerCase().contains("mac"))
            path=path+"chromedriver";
        else
            path=null;

        return path;
    }


    public  WebDriver getDriver()
    {
        return driver;
    }
}