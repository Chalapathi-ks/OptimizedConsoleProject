package UnbxdTests.testNG.ui.PreviewTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import UnbxdTests.testNG.ui.BaseTest;
import com.google.gson.JsonObject;
import core.ui.actions.CreateSiteActions;
import core.ui.actions.LoginActions;
import core.ui.actions.PreviewActions;
import core.ui.components.CreateSiteComponent;
import core.ui.components.FeedUploadComponent;
import lib.Config;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.compat.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static core.ui.page.UiBase.ThreadWait;

public class PreviewTest extends BaseTest {

    @Page
    LoginActions loginActions;

    @Page
    CreateSiteComponent createSiteComponent;

    @Page
    PreviewActions previewActions;

    @Page
    CreateSiteActions createSiteActions;

    @Page
    FeedUploadComponent feedUploadComponent;

    List<String> siteKeys = new ArrayList<>();

    private String ssoIdKeyCookie;
    private String csrfCookie;

    @BeforeClass
    public void init() throws InterruptedException {
        try {
            super.setUp();
            initFluent(driver);
            initTest();
            // Login is now handled by BaseTest's global login mechanism
            ssoIdKeyCookie = createSiteComponent.getSsoIdCookie();
            csrfCookie = createSiteComponent.getCsrfCookie();
            String siteKey = feedUploadComponent.goTillFeedUpload();
            feedUploadComponent.goTillMappingPage();
            feedUploadComponent.goTillRelevancyPage();
            feedUploadComponent.goTillPreviewPage();
            siteKeys.add(siteKey);
        } catch (Exception e) {
            Assert.fail("Preview testCase got failed" + e);
        }
    }


    @Test(description = "This test verifies the previewPage contents like facets,products and productCounts.", priority = 1)
    public void verifyPreviewContentsTest()
    {
        try {
            String previewPage= driver.getCurrentUrl();
            Assert.assertTrue(previewPage.contains("preview"),"Not redirecting to preview page");
            await();
            goTo(previewPage);
            previewActions.awaitForElementPresence(previewActions.productCount);
            await();
            Assert.assertTrue(previewActions.productCount.getText().trim().equalsIgnoreCase("- 1 to 12 of 200 products"));
            Assert.assertEquals(previewActions.getPreviewSnippetCount(), 12);
            Assert.assertTrue(previewActions.getFacetsCount() > 0);
        }
        catch(NoSuchElementException exception) {
            System.out.println("There is no such element present");
            Assert.fail("No such element failure" + exception);
        }
        catch (Exception e)
        {
            Assert.fail("verifyPreviewContentsTestCase got failed" + e);
        }
    }

    @FileToTest(value = "/previewTest/previewKeywordSuggestion.json")
    @Test(description = "This test Verifies keyWordSuggestion are coming or not in preview page ", priority = 6, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifyPreviewKeywordSuggestionTest(JsonObject object) {
        String query = object.get("query").getAsString();
        try {
            String previewPage = driver.getCurrentUrl();
            Assert.assertTrue(previewPage.contains("preview"),"Not redirecting to preview page");
            await();
            goTo(previewPage);
            ThreadWait();
            previewActions.awaitForElementPresence(previewActions.productCount);
            previewActions.passQueryToSearchBox(query);
            await();
            previewActions.verifyKeywordSuggestions(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FileToTest(value = "/previewTest/previewPopularProduct.json")
    @Test(description = "This test Verifies popularProducts are coming or not in preview page ", priority = 4, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifyPreviewPopularProductTest(JsonObject object) throws InterruptedException {
        String query = object.get("query").getAsString();
        try {
            String previewPage = driver.getCurrentUrl();
            Assert.assertTrue(previewPage.contains("preview"),"Not redirecting to preview page");
            await();
            goTo(previewPage);
            ThreadWait();
            previewActions.awaitForElementPresence(previewActions.productCount);
            previewActions.passQueryToSearchBox(query);
            await();
            previewActions.popularProducts(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FileToTest(value = "/previewTest/searchResultQuery.json")
    @Test(description = "This test Verifies SearchResults are coming or not in preview page ", priority = 2, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifySearchResultTest(JsonObject object) {
        String query = object.get("query").getAsString();
        try {
            String previewPage = driver.getCurrentUrl();
            Assert.assertTrue(previewPage.contains("preview"),"Not redirecting to preview page");
            await();
            goTo(previewPage);
            previewActions.awaitForElementPresence(previewActions.productCount);
            ThreadWait();
            previewActions.passQueryToSearchBox(query);
            previewActions.clickOnSearchIcon();
            previewActions.checkSearchedQueryResult(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FileToTest(value = "/previewTest/didYouMeanQuery.json")
    @Test(description = "This test Verifies Didyoumean functionality ", priority = 3, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifyDidYouMeanTest(JsonObject object) {
        String query = object.get("query").getAsString();
        try {
            String previewPage = driver.getCurrentUrl();
            Assert.assertTrue(previewPage.contains("preview"),"Not redirecting to preview page");
            await();
            goTo(previewPage);
            previewActions.awaitForElementPresence(previewActions.productCount);
            ThreadWait();
            previewActions.passQueryToSearchBox(query);
            previewActions.clickOnSearchIcon();
            previewActions.verifyDidYouMeanLink(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @AfterClass(alwaysRun = true)
    public void deleteSites() {
        for (String siteKey : siteKeys) {
            createSiteActions.deleteSite(siteKey, ssoIdKeyCookie, csrfCookie);
        }

    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.close();
        driver.quit();
        Helper.tearDown();
    }


}
