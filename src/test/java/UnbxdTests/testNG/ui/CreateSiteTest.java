package UnbxdTests.testNG.ui;

import com.google.gson.JsonObject;
import core.ui.actions.CreateSiteActions;
import core.ui.actions.FeedUploadActions;
import core.ui.actions.LoginActions;
import core.ui.components.CreateSiteComponent;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.compat.Page;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import org.testng.asserts.SoftAssert;

import java.util.*;

import static lib.enums.UnbxdEnum.MAGENTO_TAB;
import static lib.enums.UnbxdEnum.PLATFORM_UPLOAD;

public class CreateSiteTest extends BaseTest{

    @Page
    LoginActions loginActions;

    @Page
    CreateSiteActions createSiteActions;

    @Page
    CreateSiteComponent createSiteComponent;

    @Page
    FeedUploadActions feedUploadActions;

    List<String> siteKeys=new ArrayList<>();

    private String ssoIdKeyCookie ;
    private String CsrfCookie ;


    @BeforeClass
    public void init() {
        super.setUp();
        initFluent(driver);
        initTest();
        System.out.println("driver is: " +driver.toString());
        // Login is now handled by BaseTest's global login mechanism
    }

    @FileToTest(value = "createSiteTest.json")
    @Test(description = "Creates new site and verifies the created site in the checking new page load", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void createSiteTest(JsonObject object)
    {
        try {
            ssoIdKeyCookie = createSiteComponent.getSsoIdCookie();
            CsrfCookie =createSiteComponent.getCsrfCookie();

            String siteName = object.get("siteName").getAsString() + System.currentTimeMillis();
            String env = object.get("env").getAsString();
            String lang = object.get("lang").getAsString();
            String vertical = object.get("vertical").getAsString();
            String platform = object.get("platform").getAsString();

            createSiteComponent.createSite(siteName, env, lang, vertical, platform);
            Assert.assertTrue(createSiteActions.awaitForElementPresence(createSiteActions.catalogUploadPageTitle));
            feedUploadActions.selectUploaders(PLATFORM_UPLOAD);
            feedUploadActions.selectPlatform(MAGENTO_TAB);
            String siteKey = createSiteActions.getCreatedSiteKey();
            siteKeys.add(siteKey);
        }
        catch(NoSuchElementException exception) {
            System.out.println("There is no such element present");
            Assert.fail("No such element failure for createSiteTest" + exception);
        }
        catch (Exception e)
        {
            Assert.fail("createSiteTestcase failure" + e);
        }

    }

    @AfterClass(alwaysRun = true)
    public void deleteSites()
    {
        for(String siteKey:siteKeys)
        {
                createSiteActions.deleteSite(siteKey,ssoIdKeyCookie,CsrfCookie);
            }

    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.close();
        driver.quit();
        Helper.tearDown();
    }





}
