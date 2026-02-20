package UnbxdTests.testNG.consoleui.MerchConditionTest;


import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.consoleui.actions.BannerActions;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.page.BrowsePage;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import lib.compat.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.ui.page.UiBase.ThreadWait;
import static lib.constants.UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE;

public class SimilarQueryTest extends MerchandisingTest {

    String query;

    List<String> queryRules = new ArrayList<>();
    List<String> pageRules = new ArrayList<>();

    @Page
    CommercePageActions searchPageActions;

    @Page
    BannerActions bannerActions;

    @Page
    BrowsePage browsePage;

    public String page;


    @FileToTest(value = "/consoleTestData/similarQuery.json")
    @Test(description = "SEARCH: Creates and verifies the similar query campaign creation for Search Campaigns", dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void similarQueryTest(Object jsonObject) throws InterruptedException {

        JsonObject boostJsonObject = (JsonObject) jsonObject;
        query = boostJsonObject.get("query").getAsString();
        String similarQuery= boostJsonObject.get("similarQuery").getAsString()+System.currentTimeMillis();
        String similarQueryData= boostJsonObject.get("similarQueryData").getAsString()+System.currentTimeMillis();

        goTo(searchPage);
        searchPage.threadWait();
        createPromotion(query, false, false);

//        JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("similarQuery.json");

        // goTo(searchPage);
        merchandisingActions.selectSimilarQueryData(similarQuery);
        ThreadWait();
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        searchPage.queryRuleByName(query);
        queryRules.add(query);

        merchandisingActions.listinPageAddMoreQueriesEditIcon();
        ThreadWait();
         Assert.assertTrue(merchandisingActions.selectedSimilarQueries.getText().contains(similarQuery),"SELECTED SIMILAR QUERY IS NOT SAME");
        merchandisingActions.selectSimilarQueryData(similarQueryData);
        Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));

        searchPageActions.awaitForPageToLoad();
        searchPageActions.awaitForElementPresence(searchPageActions.searchInputBox);
        ThreadWait();
        Assert.assertTrue(merchandisingActions.similarqueryinListpage.getText().contains(similarQueryData));
        Assert.assertTrue(merchandisingActions.similarqueryinListpage.getText().contains(similarQuery));

        searchPageActions.selectActionType(UnbxdEnum.EDIT, query);
        ThreadWait();
        Assert.assertTrue(merchandisingActions.similarQuerySummary.getText().contains(similarQueryData));
        Assert.assertTrue(merchandisingActions.similarQuerySummary.getText().contains(similarQuery));

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        ThreadWait();
        searchPageActions.deleteQueryRule(query);
        ThreadWait();
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

    }

    @FileToTest(value = "/consoleTestData/similarQueryForBanner.json")
    @Test(description = "SEARCH: Creates and verifies the similar query campaign creation for Search Campaigns", dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void similarQueryTestBanner(Object jsonObject) throws InterruptedException {

        JsonObject boostJsonObject = (JsonObject) jsonObject;
        query = boostJsonObject.get("query").getAsString();
        String similarQuery= boostJsonObject.get("similarQuery").getAsString();
        String similarQueryData= boostJsonObject.get("similarQueryData").getAsString();
        String html= boostJsonObject.get("bannerURL").getAsString();

        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("similarQueryForBanner.json");

        // goTo(searchPage);
        createPromotion(query,true,true);
        bannerActions.goToQueryRuleBanner();
        searchPageActions.fillQueryRuleData(query,null);
        merchandisingActions.selectSimilarQueryData(similarQuery);
        merchandisingActions.fillCampaignData(campaignData);
        bannerActions.addHtmlBanner(html);
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);
        ThreadWait();

        merchandisingActions.listinPageAddMoreQueriesEditIcon();
        Assert.assertEquals(similarQuery,merchandisingActions.selectedSimilarQueries.getText(),"SELECTED SIMILAR QUERY IS NOT SAME");
        merchandisingActions.selectSimilarQueryData(similarQueryData);
        Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));

        searchPageActions.awaitForPageToLoad();
        searchPageActions.awaitForElementPresence(searchPageActions.searchInputBox);
        ThreadWait();
        Assert.assertTrue(merchandisingActions.similarqueryinListpage.getText().contains(similarQueryData));
        Assert.assertTrue(merchandisingActions.similarqueryinListpage.getText().contains(similarQuery));

        searchPageActions.selectActionType(UnbxdEnum.EDIT, query);
        ThreadWait();
        Assert.assertTrue(merchandisingActions.similarQuerySummary.getText().contains(similarQueryData));
        Assert.assertTrue(merchandisingActions.similarQuerySummary.getText().contains(similarQuery));

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        ThreadWait();
        searchPageActions.deleteQueryRule(query);
        ThreadWait();
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();


    }

    @FileToTest(value = "/consoleTestData/similarQueryForRedirect.json")
    @Test(description = "This test Verifies the redirect creation and edit", dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"sanity"})
    public void similarQueryTestRedirect(Object jsonObject) throws InterruptedException {
        JsonObject boostJsonObject = (JsonObject) jsonObject;
        query = boostJsonObject.get("query").getAsString();
        String similarQuery= boostJsonObject.get("similarQuery").getAsString();
        String similarQueryData= boostJsonObject.get("similarQueryData").getAsString();
        String redirectUrl= boostJsonObject.get("redirect").getAsString();


        goTo(searchPage);
        searchPage.threadWait();
        searchPageActions.awaitForPageToLoad();
        merchandisingActions.goToSection(UnbxdEnum.REDIRECT);
        ThreadWait();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("similarQueryForRedirect.json");

        //creating redirect
        createPromotion(query, false, false);
        searchPageActions.fillQueryRuleData(query, null);
        merchandisingActions.selectSimilarQueryData(similarQuery);
        merchandisingActions.fillCampaignData(campaignData);
        bannerActions.fillRedirectURL(redirectUrl);
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);

        merchandisingActions.listinPageAddMoreQueriesEditIcon();
        Assert.assertEquals(similarQuery,merchandisingActions.selectedSimilarQueries.getText(),"SELECTED SIMILAR QUERY IS NOT SAME");
        merchandisingActions.selectSimilarQueryData(similarQueryData);
        Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));

        searchPageActions.awaitForPageToLoad();
        searchPageActions.awaitForElementPresence(searchPageActions.searchInputBox);
        ThreadWait();
        Assert.assertTrue(merchandisingActions.similarqueryinListpage.getText().contains(similarQueryData));
        Assert.assertTrue(merchandisingActions.similarqueryinListpage.getText().contains(similarQuery));

        searchPageActions.selectActionType(UnbxdEnum.EDIT, query);
        ThreadWait();
        Assert.assertTrue(merchandisingActions.similarQuerySummary.getText().contains(similarQueryData));
        Assert.assertTrue(merchandisingActions.similarQuerySummary.getText().contains(similarQuery));

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        ThreadWait();
        searchPageActions.deleteQueryRule(query);
        ThreadWait();
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }
    @FileToTest(value = "/consoleTestData/similarQueryForPromotion.json")
    @Test(description = "SEARCH: Creates and verifies the AI suggested similar query campaign creation for Search Campaigns", dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void AIsuggestedSimilarQueryTest(Object jsonObject) throws InterruptedException {

        JsonObject boostJsonObject = (JsonObject) jsonObject;
        query = boostJsonObject.get("query").getAsString();
        goTo(searchPage);
        searchPage.threadWait();
        createPromotion(query, false, false);

        //JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("similarQueryForPromotion.json");

        // goTo(searchPage);
        String AISuggestedQuery=merchandisingActions.selectAISuggestedSimilarQueryData();
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);

        merchandisingActions.listinPageAddMoreQueriesEditIcon();
        Assert.assertEquals(AISuggestedQuery,merchandisingActions.selectedSimilarQueries.getText(),"SELECTED SIMILAR QUERY IS NOT SAME");
        String AISuggestedQueryInListinPage=merchandisingActions.selectAISuggestedSimilarQueryData();
        Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));

        searchPageActions.awaitForPageToLoad();
        searchPageActions.awaitForElementPresence(searchPageActions.searchInputBox);
        ThreadWait();
        Assert.assertTrue(merchandisingActions.similarqueryinListpage.getText().contains(AISuggestedQueryInListinPage));
        Assert.assertTrue(merchandisingActions.similarqueryinListpage.getText().contains(AISuggestedQuery));

        searchPageActions.selectActionType(UnbxdEnum.EDIT, query);
        ThreadWait();
        Assert.assertTrue(merchandisingActions.similarQuerySummary.getText().contains(AISuggestedQueryInListinPage));
        Assert.assertTrue(merchandisingActions.similarQuerySummary.getText().contains(AISuggestedQuery));

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        ThreadWait();
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }


    @FileToTest(value = "/consoleTestData/PromotionAddAnotherRule.json")
    @Test(description = "SEARCH: Creates and verifies the campaign creation with AddAnother campaign for Search Campaigns", dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void promotionAddAnotherRule(Object jsonObject) throws InterruptedException {

        JsonObject boostJsonObject = (JsonObject) jsonObject;
        query = boostJsonObject.get("query").getAsString();

        goTo(searchPage);
        searchPage.threadWait();
        createPromotion(query, false, false);

       // JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("PromotionAddAnotherRule.json");

        // goTo(searchPage);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);
        Assert.assertTrue(merchandisingActions.activeStatus.isDisplayed(), "SEARCH: PROMOTION RULE IS NOT IN ACTIVE STATE");

        //Stopped the rule
        searchPageActions.selectActionType(UnbxdEnum.MORE, query);
        searchPageActions.selectActionFromMore(UnbxdEnum.STOPPED, query);
        searchPageActions.selectModelWindow();
        Assert.assertTrue(searchPageActions.checkSuccessMessage(), SUCCESS_MESSAGE_FAILURE);
        Assert.assertTrue(searchPageActions.stopCampaign.isDisplayed(), "SEARCH: PROMOTION RULE IS NOT IN STOPPED STATE");

        searchPageActions.selectActionType(UnbxdEnum.LEFTMORE, query);
        searchPageActions.addAnotherCampaign();
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        ThreadWait();
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

        ThreadWait();
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();


    }

    @FileToTest(value = "/consoleTestData/browseAddAnotherRule.json")
    @Test(description = "BROWSE: Creates and verifies the campaign creation with AddAnother campaign for Search Campaigns", dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void browsePromotionAddAnotherRule(Object jsonObject) throws InterruptedException {

        JsonObject boostJsonObject = (JsonObject) jsonObject;
        page = boostJsonObject.get("page").getAsString();

        goTo(browsePage);
        searchPage.threadWait();
        createBrowsePromotion(page,false,false);
        JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("browseAddAnotherRule.json");

        searchPageActions.fillPageName(object);
        merchandisingActions.fillCampaignData(campaignData);
        Thread.sleep(5000);
        merchandisingActions.goToLandingPage();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(page));
        pageRules.add(page);
        Assert.assertTrue(merchandisingActions.activeStatus.isDisplayed(), "SEARCH: PROMOTION RULE IS NOT IN ACTIVE STATE");

        //Stopped the rule
        searchPageActions.selectActionType(UnbxdEnum.MORE, page);
        searchPageActions.selectActionFromMore(UnbxdEnum.STOPPED, page);
        searchPageActions.selectModelWindow();
        Assert.assertTrue(searchPageActions.checkSuccessMessage(), SUCCESS_MESSAGE_FAILURE);
        Assert.assertTrue(searchPageActions.stopCampaign.isDisplayed(), "SEARCH: PROMOTION RULE IS NOT IN STOPPED STATE");

        searchPageActions.selectActionType(UnbxdEnum.LEFTMORE, page);
        searchPageActions.addAnotherCampaign();
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(page));

        goTo(browsePage);
        searchPage.threadWait();
        searchPage.queryRuleByName(page);
        ThreadWait();
        searchPageActions.deleteQueryRule(page);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

        ThreadWait();
        searchPageActions.deleteQueryRule(page);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();



    }

//    @AfterClass(alwaysRun = true,groups={"sanity"})
//    public void deleteCreatedRules()
//    {
//        goTo(searchPage);
//
//        for(String queryRule: queryRules)
//        {
//            if(searchPage.queryRuleByName(queryRule)!=null)
//            {
//                searchPageActions.deleteQueryRule(queryRule);
//                Assert.assertNull(searchPage.queryRuleByName(queryRule),"CREATED QUERY RULE IS NOT DELETED");
//                getDriver().navigate().refresh();
//                ThreadWait();
//            }
//        }
//
//        goTo(browsePage);
//
//        for(String pageRule: pageRules)
//        {
//            if(searchPage.queryRuleByName(pageRule)!=null)
//            {
//                searchPageActions.deleteQueryRule(pageRule);
//                Assert.assertNull(searchPage.queryRuleByName(pageRule),"BROWSE RULE : CREATED PAGE RULE IS NOT DELETED");
//                getDriver().navigate().refresh();
//                ThreadWait();
//
//            }
//        }
//    }
}


