package UnbxdTests.testNG.consoleui.MerchConditionTest;


import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.page.BrowsePage;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BrowseBoostTest extends MerchandisingTest {

    String query;

    List<String> queryRules = new ArrayList<>();
    List<String> pageRules = new ArrayList<>();

    @Page
    CommercePageActions searchPageActions;

    @Page
    BrowsePage browsePage;

    public String page;



//TestCases for browse
    @FileToTest(value = "/consoleTestData/browseBoosting.json")
    @Test(description = "BROWSE: Creates and verifies the campaign creation with Boost for Search Campaigns", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void browseBoostingTest(Object jsonObject) throws InterruptedException
    {
        String conditionType = "boost";
        JsonObject boostJsonObject = (JsonObject) jsonObject;
        page = boostJsonObject.get("page").getAsString();

        goTo(browsePage);
        searchPage.await();
        createBrowsePromotion(page,false,false);
        JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("browseBoosting.json");
        merchandisingActions.await();
        searchPageActions.fillPageName(object);
        merchandisingActions.fillCampaignData(campaignData);
        merchandisingActions.await();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);

        fillMerchandisingData(object,UnbxdEnum.BOOST,false);
        merchandisingActions.clickOnApplyButton();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        merchandisingActions.await();
        Assert.assertNotNull(searchPage.queryRuleByName(page));
        pageRules.add(page);

        merchandisingActions.openPreviewAndSwitchTheTab();
        merchandisingActions.awaitForPageToLoad();
        merchandisingActions.awaitForElementPresence(merchandisingActions.showingResultinPreview);
        String previewPage = driver.getCurrentUrl();
        Assert.assertTrue(previewPage.contains("preview"),"Not redirecting to preview page");
        merchandisingActions.await();
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(page));

        merchandisingActions.ClickViewHideInsight();
        merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
        merchandisingActions.MerchandisingStrategy.isDisplayed();
        verifyMerchandisingGenericData(object, UnbxdEnum.BOOST,false);

        goTo(browsePage);
        searchPage.await();
        searchPage.queryRuleByName(page);
        searchPageActions.selectActionType(UnbxdEnum.EDIT, page);
        merchandisingActions.await();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getConditionSize();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, object.size(), "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);

        searchPage.scrollToBottom();
        merchandisingActions.await();
        // Use robust click handling to avoid click interception issues
        merchandisingActions.scrollUntilVisible(merchandisingActions.MerchandisingStrategyEditButton);
        merchandisingActions.waitForElementToBeClickable(merchandisingActions.MerchandisingStrategyEditButton, "Edit");
        merchandisingActions.clickUsingJS(merchandisingActions.MerchandisingStrategyEditButton);
        fillMerchandisingData(object,UnbxdEnum.BOOST,true);
        merchandisingActions.clickOnApplyButton();
        searchPage.await();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        merchandisingActions.await();

        //Preview
        merchandisingActions.openPreviewAndSwitchTheTab();
        merchandisingActions.awaitForPageToLoad();
        String previewpage = driver.getCurrentUrl();
        Assert.assertTrue(previewpage.contains("preview"),"Not redirecting to preview page");
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(page));

        merchandisingActions.ClickViewHideInsight();
        merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
        merchandisingActions.MerchandisingStrategy.isDisplayed();
        verifyMerchandisingGenericData(object, UnbxdEnum.BOOST,true);

        goTo(browsePage);
        searchPage.await();
        searchPage.queryRuleByName(page);
        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, page);
        merchandisingActions.await();
        String updatedCondition = searchPageActions.getConditionTitle();
        int updatedGroup = searchPageActions.getConditionSize();
        Assert.assertTrue(updatedCondition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(updatedGroup, object.size(), "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);

        goTo(browsePage);
        searchPage.await();
        searchPage.queryRuleByName(page);
        merchandisingActions.await();
        searchPageActions.deleteQueryRule(page);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.await();

    }

   @FileToTest(value = "/consoleTestData/globalRule.json")
    @Test(description = "BROWSE: verifies the global campaign creation with Boost for Search Campaigns", priority = 2, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising"})
    public void browseGlobalBoostingTest(Object jsonObject) throws InterruptedException
    {
        ArrayList<String> conditiontypes = new ArrayList<String>();
        conditiontypes.add("Boost");
        conditiontypes.add("Filter");

        JsonObject boostJsonObject = (JsonObject) jsonObject;

        goTo(browsePage);
        searchPage.await();
        await();
        createGlobalRulePromotion();

        JsonArray object = boostJsonObject.get("data").getAsJsonArray();

        merchandisingActions.await();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
        int group1=searchPageActions.conditionsList.size();
        merchandisingActions.deleteConditionIfItsPresent(group1);
        merchandisingActions.selectGlobalActionType(UnbxdEnum.GLOBALBOOST);
        fillMerchandisingData(object,UnbxdEnum.BOOST,false);
        merchandisingActions.clickOnApplyButton();

        merchandisingActions.await();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.FILTER);
        int group2=searchPageActions.conditionsList.size();
        merchandisingActions.deleteConditionIfItsPresent(group2);
        merchandisingActions.await();
        merchandisingActions.selectGlobalActionType(UnbxdEnum.GLOBALFILTER);
        fillMerchandisingData(object,UnbxdEnum.FILTER,false);
        merchandisingActions.clickOnApplyButton();

        merchandisingActions.publishGlobalRule();
        merchandisingActions.verifySuccessMessage();
        merchandisingActions.await();
        searchPage.editGlobalRule();
        merchandisingActions.await();
        int group = searchPageActions.getConditionSize();

        for (String conditiontype : conditiontypes)
        {
            Assert.assertTrue(searchPage.promotionRuleSummary.getText().contains(conditiontype),"SELECTED CONDITION TYPE :" + conditiontype + "IS NOT COMING ");
        }
        Assert.assertEquals(group,2, "BROWSE: NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);
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
//                merchandisingActions.await();
//            }
//        }
//
//       goTo(browsePage);
//
//        for(String pageRule: pageRules)
//        {
//         if(searchPage.queryRuleByName(pageRule)!=null)
//          {
//            searchPageActions.deleteQueryRule(pageRule);
//            Assert.assertNull(searchPage.queryRuleByName(pageRule),"BROWSE RULE : CREATED PAGE RULE IS NOT DELETED");
//              getDriver().navigate().refresh();
//              merchandisingActions.await();
//
//          }
//        }
//    }
}


