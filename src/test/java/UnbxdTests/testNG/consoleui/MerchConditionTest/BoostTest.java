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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.ui.page.UiBase.ThreadWait;
import static lib.constants.UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE;

public class BoostTest extends MerchandisingTest {

    String query;

    List<String> queryRules = new ArrayList<>();
    List<String> pageRules = new ArrayList<>();

    @Page
    CommercePageActions searchPageActions;

    @Page
    BrowsePage browsePage;

    public String page;

    @FileToTest(value = "/consoleTestData/boosting.json")
    @Test(description = "SEARCH: Creates and verifies the campaign creation with Boost for Search Campaigns", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void boostingTest(Object jsonObject) throws InterruptedException
    {
        String conditionType = "boost";
        JsonObject boostJsonObject = (JsonObject) jsonObject;
        query = boostJsonObject.get("query").getAsString();

        goTo(searchPage);
        searchPage.threadWait();
        createPromotion(query,false,false);

        JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("boosting.json");

        // goTo(searchPage);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);

        fillMerchandisingData(object,UnbxdEnum.BOOST,false);
        merchandisingActions.clickOnApplyButton();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);

        merchandisingActions.openPreviewAndSwitchTheTab();
        merchandisingActions.awaitForPageToLoad();
        ThreadWait();
        String previewPage = driver.getCurrentUrl();
        Assert.assertTrue(previewPage.contains("preview"),"Not redirecting to preview page");
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(query));

        merchandisingActions.ClickViewHideInsight();
        merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
        ThreadWait();
        merchandisingActions.MerchandisingStrategy.isDisplayed();
        ThreadWait();
        verifyMerchandisingGenericData(object, UnbxdEnum.BOOST,false);

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        searchPageActions.selectActionType(UnbxdEnum.EDIT, query);
        ThreadWait();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getConditionSize();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, object.size(), "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);

        searchPage.scrollToBottom();
        // Use robust click handling to avoid click interception issues
        merchandisingActions.scrollUntilVisible(merchandisingActions.MerchandisingStrategyEditButton);
        merchandisingActions.waitForElementToBeClickable(merchandisingActions.MerchandisingStrategyEditButton, "Edit");
        merchandisingActions.clickUsingJS(merchandisingActions.MerchandisingStrategyEditButton);
        fillMerchandisingData(object,UnbxdEnum.BOOST,true);
        merchandisingActions.clickOnApplyButton();
        searchPage.threadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();

        //Preview
        merchandisingActions.openPreviewAndSwitchTheTab();
        merchandisingActions.awaitForPageToLoad();
        ThreadWait();
        String previewpage = driver.getCurrentUrl();
        Assert.assertTrue(previewpage.contains("preview"),"Not redirecting to preview page");
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(query));

        merchandisingActions.ClickViewHideInsight();
        merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
        merchandisingActions.MerchandisingStrategy.isDisplayed();
        ThreadWait();
        verifyMerchandisingGenericData(object, UnbxdEnum.BOOST,true);

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, query);
        ThreadWait();
        String updatedCondition = searchPageActions.getConditionTitle();
        int updatedGroup = searchPageActions.getConditionSize();
        Assert.assertTrue(updatedCondition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(updatedGroup, object.size(), "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();
    }




   @FileToTest(value = "/consoleTestData/globalRule.json")
    @Test(description = "SEARCH: verifies the global campaign creation with Boost for Search Campaigns", priority = 2, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising"})
    public void globalBoostingTest(Object jsonObject) throws InterruptedException
    {
        ArrayList<String> conditiontypes = new ArrayList<String>();
        conditiontypes.add("Boost");
        conditiontypes.add("Filter");

        JsonObject boostJsonObject = (JsonObject) jsonObject;

        goTo(searchPage);
        ThreadWait();
        await();
        createGlobalRulePromotion();

        JsonArray object = boostJsonObject.get("data").getAsJsonArray();

        ThreadWait();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
        int group1=searchPageActions.conditionsList.size();
        merchandisingActions.deleteConditionIfItsPresent(group1);
        merchandisingActions.selectGlobalActionType(UnbxdEnum.GLOBALBOOST);
        fillMerchandisingData(object,UnbxdEnum.BOOST,false);
        merchandisingActions.clickOnApplyButton();

        ThreadWait();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.FILTER);
        int group2=searchPageActions.conditionsList.size();
        merchandisingActions.deleteConditionIfItsPresent(group2);
        merchandisingActions.selectGlobalActionType(UnbxdEnum.GLOBALFILTER);
        fillMerchandisingData(object,UnbxdEnum.FILTER,false);
        merchandisingActions.clickOnApplyButton();

        merchandisingActions.publishGlobalRule();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        searchPage.editGlobalRule();
        ThreadWait();
        int group = searchPageActions.getConditionSize();

        for (String conditiontype : conditiontypes)
        {
            Assert.assertTrue(searchPage.promotionRuleSummary.getText().contains(conditiontype),"SELECTED CONDITION TYPE :" + conditiontype + "IS NOT COMING ");
        }
       Assert.assertEquals(group,2, "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);
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
//       goTo(browsePage);
//
//        for(String pageRule: pageRules)
//        {
//         if(searchPage.queryRuleByName(pageRule)!=null)
//          {
//            searchPageActions.deleteQueryRule(pageRule);
//            Assert.assertNull(searchPage.queryRuleByName(pageRule),"BROWSE RULE : CREATED PAGE RULE IS NOT DELETED");
//              getDriver().navigate().refresh();
//              ThreadWait();
//
//          }
//        }
//    }
}


