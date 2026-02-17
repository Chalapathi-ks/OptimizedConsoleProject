package UnbxdTests.testNG.ui.RelevancyTest;

import UnbxdTests.testNG.ui.BaseTest;
import core.ui.actions.*;
import core.ui.components.CreateSiteComponent;
import core.ui.components.FeedUploadComponent;
import lib.Config;
import lib.Helper;
import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class QueryUploadTest extends BaseTest {

    @Page
    LoginActions loginActions;

    @Page
    CreateSiteComponent createSiteComponent;

    @Page
    FeedUploadComponent feedUploadComponent;

    @Page
    FeedUploadActions feedUploadActions;

    @Page
    PreviewActions previewActions;

    @Page
    FacetableFieldsActions facetableFieldsActions;

    @Page
    QueryUploadActions queryUploadActions;

    @Page
    AutoSuggestActions autoSuggestActions;

    @Page
    CreateSiteActions createSiteActions;

    private String ssoIdKeyCookie ;
    private String csrfCookie;

    List<String> siteKeys = new ArrayList<>();


    @BeforeClass
    public void init() throws InterruptedException {
        super.setUp();
        initFluent(driver);
        initTest();
        // Login is now handled by BaseTest's global login mechanism
        ssoIdKeyCookie = createSiteComponent.getSsoIdCookie();
        csrfCookie = createSiteComponent.getCsrfCookie();
        String siteKey = feedUploadComponent.goTillFeedUpload();
        feedUploadComponent.goToRelevancyPageWithoutMapping();
        siteKeys.add(siteKey);
    }

    @Test(description = "This test uploads the query data",priority = 1)
    public void uploadQueryTest() throws InterruptedException {
        String queryDataFile=Thread.currentThread().getContextClassLoader().getResource("testData/relevancyTest/QueryFile.csv").getPath();

        goTo(feedUploadComponent.getRelevancyPage());
        feedUploadComponent.awaitForPageToLoad();

        facetableFieldsActions.waitForElementAppear(autoSuggestActions.skipQueryButton, facetableFieldsActions.relevancePageLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
        Assert.assertEquals(autoSuggestActions.skipQuery.getText(),"Skip this step");
        queryUploadActions.uploadQueryFile(queryDataFile);
        queryUploadActions.validateQueryUpload();
        click(previewActions.applyConfigButton);
        feedUploadActions.WaitTillReindexComplete();
        await();
        previewActions.goToPreviewPage();
        previewActions.awaitForElementPresence(previewActions.productCount);
        Assert.assertTrue(previewActions.productCount.getText().trim().equalsIgnoreCase("- 1 to 12 of 200 products"));
        System.out.println("SiteKey for query data uploaded sitekey:" +siteKeys);

    }

    @AfterClass(alwaysRun = true)
    public void deleteSites()
    {
        for(String siteKey:siteKeys)
        {
            createSiteActions.deleteSite(siteKey,ssoIdKeyCookie, csrfCookie);
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.close();
        driver.quit();
        Helper.tearDown();
    }
}