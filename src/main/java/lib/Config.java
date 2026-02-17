package lib;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import static java.lang.Integer.parseInt;

public class Config {


    private static final String BROWSER = "selenium.browser";
    public static final String ENV_BROWSERVERSION = "env.browser.version";
    public static final String BROWSER_DIMENSION_X = "browser.dimension.width";
    public static final String BROWSER_DIMENSION_Y = "browser.dimension.height";

    public static final String ENVIRONMENT_PROFILE = "environment.profile";
    public static final String ENV_PLATFORM = "env.platform.id";
    public static final String ENV_OSVERSION = "env.platform.version";


    private static String configFile = "config.properties";
    private static Configuration configuration;

    private Config() {

    }

    public static void loadConfig() {
        getInstance();
    }

    public static Configuration getInstance() {
        try {
            if (configuration == null)
                configuration = new PropertiesConfiguration(loadAndGetResourceLocation(configFile));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        return configuration;

    }


    public static String loadAndGetResourceLocation(String fileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(fileName).toString();
    }

    public static String getEnvironmentProfile() {
        if (configuration == null) getInstance();
        return configuration.getString(ENVIRONMENT_PROFILE);
    }

    public static String getBrowserVersion() {
        if (configuration == null) getInstance();
        return configuration.getString(ENV_BROWSERVERSION);
    }

    public static String getOsPlatformVersion() {
        if (configuration == null) getInstance();
        return configuration.getString(ENV_OSVERSION);
    }


    public static int getBrowserDimensionX() {
        if (configuration == null) getInstance();
        return getIntValueForProperty(BROWSER_DIMENSION_X);
    }

    public static int getBrowserDimensionY() {
        if (configuration == null) getInstance();
        return getIntValueForProperty(BROWSER_DIMENSION_Y);
    }


    public static String getBrowser() {
        if (configuration == null) getInstance();
        String browser = configuration.getString(BROWSER);
        // Default to chrome when not set or unresolved (e.g. IDE run without Maven filtering)
        if (browser == null || browser.trim().isEmpty() || browser.contains("${")) {
            return "chrome";
        }
        return browser;
    }

    public static String getOsPlatform() {
        if (configuration == null) getInstance();
        return configuration.getString(ENV_PLATFORM);
    }

    public static int getIntValueForProperty(String propertyName) {
        if (configuration == null) getInstance();
        String timeOut = configuration.getString(propertyName);
        return parseInt(timeOut);
    }

    public static String getStringValueForProperty(String propertyName) {
        if (configuration == null) getInstance();
        String value = configuration.getString(propertyName);
        return value;
    }
}

