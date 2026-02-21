package UnbxdTests.testNG.ui;

import java.net.URL;

import lib.compat.SeleniumBase;
import lib.compat.Page;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import core.ui.actions.LoginActions;
import lib.BrowserInitializer;
import lib.GlobalCookieManager;
import lib.GlobalLoginManager;

public class BaseTest extends SeleniumBase {

    @Page
    protected LoginActions loginActions;

    public  WebDriver driver=null;
    public final String testDataPath="src/test/resources/testData/";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        try {
            BrowserInitializer initializer = new BrowserInitializer();
            initializer.init(this.getClass().getSimpleName());
            driver = initializer.getDriver();

            if (driver == null) {
                throw new RuntimeException("WebDriver is null! Cannot proceed with test execution. Check Selenium Grid connection and available nodes.");
            }

            initFluent(driver);
            initTest();

            GlobalLoginManager.setLoginActions(loginActions);

            System.out.println("Attempting to reuse cookies from suite login...");
            boolean reused = GlobalLoginManager.tryCookieReuse(driver);
            if (!reused) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Retrying cookie reuse after short wait...");
                reused = GlobalLoginManager.tryCookieReuse(driver);
            }
            if (reused) {
                System.out.println("Successfully reused cookies from suite login");
            } else {
                System.out.println("Cookie reuse failed, performing individual login");
                performGlobalLogin();
            }

        }
        catch(Exception e)
        {
            System.err.println("[CONFIG FAILURE] BaseTest.setUp() failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Test setup failed - WebDriver initialization error: " + e.getMessage(), e);
        }
    }

    protected void performGlobalLogin() {
        try {
            System.out.println("Performing direct login with this test's own loginActions...");
            loginActions.login(0, 1);
            GlobalCookieManager.storeCookies(driver);
        } catch (Exception e) {
            System.err.println("Global login failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void openNewTab()
    {
        ((JavascriptExecutor) driver).executeScript("window.open()");
    }

    public WebDriver newWebDriver() {
        String hubUrl = System.getProperty("hubUrl");
        ChromeOptions options = new ChromeOptions();
        try {
            return new RemoteWebDriver(new URL(hubUrl), options);
        } catch (java.net.MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeSuite(alwaysRun = true)
    public void globalSetUp() throws Exception {
        lib.EnvironmentConfig.loadConfig();

        System.out.println("Starting suite-level login setup...");

        try {
            BrowserInitializer initializer = new BrowserInitializer();
            initializer.init();
            WebDriver suiteDriver = initializer.getDriver();

            BaseTest suiteBaseTest = new BaseTest();
            suiteBaseTest.driver = suiteDriver;
            suiteBaseTest.initFluent(suiteDriver);
            suiteBaseTest.initTest();

            LoginActions suiteLoginActions = suiteBaseTest.loginActions;
            GlobalLoginManager.setLoginActions(suiteLoginActions);

            System.out.println("Performing suite login for site 0, user 1");
            GlobalLoginManager.performGlobalLogin(suiteDriver, 0, 1);

            System.out.println("Suite login completed - cookies stored for reuse");

            suiteDriver.quit();

        } catch (Exception e) {
            System.err.println("Suite login setup failed: " + e.getMessage());
            System.err.println("Continuing with individual test login...");
        }
    }

    @AfterSuite(alwaysRun = true)
    public void globalCleanup() {
        GlobalLoginManager.resetLoginStateOnly();
    }

    public String getGlobalLoginStats() {
        return GlobalLoginManager.getLoginStats();
    }
}
