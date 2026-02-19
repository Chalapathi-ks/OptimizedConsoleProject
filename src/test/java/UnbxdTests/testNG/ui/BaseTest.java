package UnbxdTests.testNG.ui;

import java.net.URL;

import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import core.ui.actions.LoginActions;
import lib.BrowserInitializer;
import lib.GlobalLoginManager;

public class BaseTest extends FluentAdapter {

    @Page
    protected LoginActions loginActions;

    public  WebDriver driver=null;
    public final String testDataPath="src/test/resources/testData/";




    @BeforeClass(alwaysRun = true)
    public void setUp() {
        try {
            BrowserInitializer initializer = new BrowserInitializer();
            initializer.init();
            driver = initializer.getDriver();
            
            // Critical check: Ensure driver is not null before proceeding
            if (driver == null) {
                throw new RuntimeException("WebDriver is null! Cannot proceed with test execution. Check Selenium Grid connection and available nodes.");
            }
            
            initFluent(driver);
            initTest();
            
            // Set the automatically initialized LoginActions in GlobalLoginManager
            GlobalLoginManager.setLoginActions(loginActions);
            
            // 🍪 Use stored cookies from BeforeSuite login
            System.out.println("🍪 Attempting to reuse cookies from suite login...");
            if (GlobalLoginManager.tryCookieReuse(driver)) {
                System.out.println("✅ Successfully reused cookies from suite login");
            } else {
                System.out.println("⚠️ Cookie reuse failed, performing individual login");
                performGlobalLogin();
            }
            
        }
        catch(Exception e)
        {
            System.err.println("❌ Browser Initialization failed with Exception: " + e.getMessage());
            e.printStackTrace();
            // Re-throw to fail the test instead of continuing with null driver
            throw new RuntimeException("Test setup failed - WebDriver initialization error: " + e.getMessage(), e);
        }
    }
    
    /**
     * Perform global login - tries cookies first, falls back to full login if needed
     */
    protected void performGlobalLogin() {
        try {
            // Use default site and user (can be overridden by subclasses)
            GlobalLoginManager.smartLogin(driver, 0, 1);
        } catch (Exception e) {
            System.err.println("Global login failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void openNewTab()
    {
        ((JavascriptExecutor) driver).executeScript("window.open()");
    }




   /* @AfterClass(alwaysRun = true)
    public void removeContextForTest()
    {
        EnvironmentConfig.unSetContext();
    }
*/

    public WebDriver newWebDriver() {
        String hubUrl = System.getProperty("hubUrl");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        try {
            return new RemoteWebDriver(new URL(hubUrl), capabilities);
        } catch (java.net.MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeSuite(alwaysRun = true)
    public void globalSetUp() throws Exception {
        lib.EnvironmentConfig.loadConfig();
        
        // 🚀 Suite-level login setup - ALWAYS perform fresh login
        System.out.println("🚀 Starting suite-level login setup...");
        System.out.println("🔄 Performing fresh global login for environment...");
        
        try {
            // Initialize a single browser for suite login
            BrowserInitializer initializer = new BrowserInitializer();
            initializer.init();
            WebDriver suiteDriver = initializer.getDriver();
            
            // Create a temporary BaseTest instance for suite login
            BaseTest suiteBaseTest = new BaseTest();
            suiteBaseTest.driver = suiteDriver;
            suiteBaseTest.initFluent(suiteDriver);
            suiteBaseTest.initTest();
            
            // Get LoginActions for suite login
            LoginActions suiteLoginActions = suiteBaseTest.loginActions;
            
            // Set up GlobalLoginManager for suite login
            GlobalLoginManager.setLoginActions(suiteLoginActions);
            
            // Perform suite login and store cookies
            System.out.println("🌐 Performing suite login for site 0, user 1");
            GlobalLoginManager.performGlobalLogin(suiteDriver, 0, 1);
            
            System.out.println("✅ Suite login completed - cookies stored for parallel tests");
            
            // Close the suite browser - cookies are now stored in file
            suiteDriver.quit();
            
        } catch (Exception e) {
            System.err.println("❌ Suite login setup failed: " + e.getMessage());
            System.err.println("⚠️ Continuing with individual test login...");
            // Don't throw exception - let individual tests handle their own login
        }
    }
    
    @AfterSuite(alwaysRun = true)
    public void globalCleanup() {
        // Don't clear cookies - let them persist for next test run
        // Only reset the login state flag, keep cookies for reuse
        GlobalLoginManager.resetLoginStateOnly();
    }
    
    /**
     * Get global login statistics
     * @return String containing login information
     */
    public String getGlobalLoginStats() {
        return GlobalLoginManager.getLoginStats();
    }
}
