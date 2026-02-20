package UnbxdTests.testNG.ui.FeedUploadsTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import UnbxdTests.testNG.ui.BaseTest;
import com.google.gson.JsonObject;
import core.ui.actions.CreateSiteActions;
import core.ui.actions.FeedUploadActions;
import core.ui.actions.LoginActions;
import core.ui.components.CreateSiteComponent;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import lib.compat.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class localFeedUploadTest extends BaseTest {

    @Page
    LoginActions loginActions;

    @Page
    FeedUploadActions feedUploadActions;

    @Page
    CreateSiteComponent createSiteComponent;

    @Page
    CreateSiteActions createSiteActions;

    List<String> siteKeys=new ArrayList<>();

    private String ssoIdKeyCookie ;

    @BeforeClass
    public void init() {
        super.setUp();
        initFluent(driver);
        initTest();
        // Login is now handled by BaseTest's global login mechanism
    }

    @FileToTest(value = "feedUploadTest/urlFeedUploadTest.json")
    @Test(description="Verifying feed upload using url", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void urlFeedUploadTest(JsonObject object) throws InterruptedException {
        ssoIdKeyCookie = createSiteComponent.getSsoIdCookie();

        String url = object.get("url").getAsString();
        String UniqueId = object.get("UniqueId").getAsString();
        String adapter = object.get("AdapterName").getAsString();
        String feedUplaodStatus = object.get("feedUploadStatus").getAsString();
        String noOfProducts = object.get("noOfProducts").getAsString();
        String uploader = object.get("uploader").getAsString();

        createSiteComponent.createSite();
        Assert.assertTrue(createSiteActions.awaitForElementPresence(createSiteActions.catalogUploadPageTitle));
        //String siteKey = createSiteActions.getSiteKey();
        //siteKeys.add(siteKey);

        feedUploadActions.selectUploaders(UnbxdEnum.valueOf(uploader));
        feedUploadActions.uploadFeedUrl(url);
        feedUploadActions.clickOnPrepareImportTab();
        feedUploadActions.waitForProductPreImportToGetCompleted();
        Assert.assertTrue(feedUploadActions.awaitForElementPresence(feedUploadActions.mapImportText));
        feedUploadActions.mapMandatoryProperties(UniqueId);

        feedUploadActions.startImport();
        feedUploadActions.associateAnAdapter(adapter);
        feedUploadActions.waitForProductImportToComplete();
        Assert.assertTrue(feedUploadActions.indexStatus.getText().equalsIgnoreCase("Import to Unbxd Search Successful!!"));
        feedUploadActions.goToConsoleDashboard();
        feedUploadActions.awaitForPageToLoad();

        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        feedUploadActions.goToOverViewPage();
        feedUploadActions.awaitForPageToLoad();
        Assert.assertTrue(feedUploadActions.feedUploadStatus.getText().equalsIgnoreCase(feedUplaodStatus));
        Assert.assertTrue(feedUploadActions.noOfUploadedProducts.getText().equals(noOfProducts));
        //Assert.assertEquals(feedUploadActions.getImportSuccessMsg(), "Import Completed!");
        //feedUploadActions.verifyFailedProductsCount();
        //feedUploadActions.verifyTotalProductsCount(totalProductCount);
        //feedUploadActions.verifyProcessedProductsCount(totalProductCount);
    }

    @AfterClass(alwaysRun = true)
    public void deleteSites()
    {
        for(String siteKey:siteKeys)
        {
            //createSiteActions.deleteSite(siteKey,ssoIdKeyCookie);
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.close();
        driver.quit();
        Helper.tearDown();

    }


}
