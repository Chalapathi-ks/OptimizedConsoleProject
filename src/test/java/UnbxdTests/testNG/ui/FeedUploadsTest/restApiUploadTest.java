package UnbxdTests.testNG.ui.FeedUploadsTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import UnbxdTests.testNG.ui.BaseTest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.ui.actions.CreateSiteActions;
import core.ui.actions.FeedUploadActions;
import core.ui.actions.LoginActions;
import core.ui.components.CreateSiteComponent;
import lib.Config;
import lib.EnvironmentConfig;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import lib.compat.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static core.ui.page.UiBase.ThreadWait;
import static lib.enums.UnbxdEnum.MAGENTO_TAB;
import static lib.enums.UnbxdEnum.PLATFORM_UPLOAD;

public class restApiUploadTest extends BaseTest {

    @Page
    LoginActions loginActions;

    @Page
    FeedUploadActions feedUploadActions;

    @Page
    CreateSiteComponent createSiteComponent;

    @Page
    CreateSiteActions createSiteActions;

    List<String> siteKeys = new ArrayList<>();

    private String ssoIdKeyCookie ;
    private String csrfCookie;

    @BeforeClass
    public void init() {
        super.setUp();
        initFluent(driver);
        initTest();
        // Login is now handled by BaseTest's global login mechanism
    }

    //@FileToTest(value = "feedUploadTest/urlFeedUploadTest.json")
    @Test(description = "Verifying feed upload using url", priority = 1)
    public void apiFeedUploadTest() {
        try {
            ssoIdKeyCookie = createSiteComponent.getSsoIdCookie();
            csrfCookie = createSiteComponent.getCsrfCookie();


            createSiteComponent.createSite();
            //Assert.assertTrue(createSiteActions.awaitForElementPresence(createSiteActions.catalogUploadPageTitle));

            feedUploadActions.selectUploaders(PLATFORM_UPLOAD);
            feedUploadActions.selectPlatform(MAGENTO_TAB);
            String siteKey = createSiteActions.getCreatedSiteKey();
            String secreteKey = createSiteActions.getSecreteKey();
            siteKeys.add(siteKey);
            //feedUploadActions.apiUpload(EnvironmentConfig.getSiteKey(), EnvironmentConfig.getSecreteKey());
            feedUploadActions.apiUpload(siteKey, secreteKey);
            feedUploadActions.clickOnProceedButton();
            feedUploadActions.waitForFeedUploadToComplete();
            ThreadWait();
            Assert.assertTrue(feedUploadActions.successMsg.getText().equalsIgnoreCase("Feed Upload is Successful"));
        }
        catch(NoSuchElementException exception) {
            System.out.println("There is no such element present");
            Assert.fail("Found no such element exception" + exception);
        }
        catch (Exception e)
        {
            System.out.println("Got an exception");
            Assert.fail("apiFeedUploadTestCase got failed" + e);
        }
    }

    @AfterClass(alwaysRun = true)
    public void deleteSites()
    {
        for(String siteKey:siteKeys)
        {
            createSiteActions.deleteSite(siteKey,ssoIdKeyCookie,csrfCookie);
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.close();
        driver.quit();
        Helper.tearDown();

    }
}