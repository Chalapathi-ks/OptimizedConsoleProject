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

import static core.ui.page.UiBase.ThreadWait;

public class  PinningTest extends MerchandisingTest {

    List<String> queryRules = new ArrayList<>();

    List<String> pageRules = new ArrayList<>();

    @Page
    CommercePageActions searchPageActions;

    private String query;

    private String pin;
    String conditionType = "Pinned";

    @Page
    BrowsePage browsePage;

    public String page;

    @FileToTest(value = "/consoleTestData/Pinning.json")
    @Test(description = "SEARCH: This test verifies the creation with Pinning for Search Campaigns", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"merchandising","sanity"})
    public void pinningCreateTest(Object jsonObject) throws InterruptedException
    {
        JsonObject PinningJsonObject = (JsonObject) jsonObject;
        query = PinningJsonObject.get("query").getAsString();

        goTo(searchPage);
        ThreadWait();
        createPromotion(query,false,false);

        JsonArray object = PinningJsonObject.get("data").getAsJsonArray();
        String pinningPosition=PinningJsonObject.get("pinningPosition").getAsString();
        String updatedPinningPosition=PinningJsonObject.get("updatedPinningPosition").getAsString();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("Pinning.json");

        // goTo(searchPage);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        ThreadWait();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.PIN);
        fillPinSortMerchandisingData(object, UnbxdEnum.PIN);
        ThreadWait();
        merchandisingActions.clickOnApplyButton();
        ThreadWait();
        for (int i = 0; i < 15; i++) {
            if (merchandisingActions.pinnedProductIndex.size() > 0
                    && pinningPosition.equals(merchandisingActions.pinnedProductIndex.get(0).getText().trim()))
                break;
            try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        Assert.assertTrue(merchandisingActions.pinnedProductIndex.size() > 0, "PRODUCT IS NOT PINNED AT THE GIVEN POSITION: pinned list empty");
        Assert.assertEquals(merchandisingActions.pinnedProductIndex.get(0).getText().trim(), pinningPosition, "PRODUCT IS NOT PINNED AT THE GIVEN POSITION");
        Assert.assertTrue(merchandisingActions.pinnedProductText.isDisplayed(),"PINNED TEXT IS NOT PRESENT AT THE GIVEN POSITION");

        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        merchandisingActions.awaitForPageToLoad();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);


        merchandisingActions.openPreviewAndSwitchTheTab();
        merchandisingActions.awaitForPageToLoad();
        ThreadWait();
        String previewPage = driver.getCurrentUrl();
        Assert.assertTrue(previewPage.contains("preview"),"Not redirecting to preview page");
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        ThreadWait();
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(query));

        merchandisingActions.ClickViewHideInsight();
        merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
        merchandisingActions.MerchandisingStrategy.isDisplayed();
        verifyMerchandisingData(object, UnbxdEnum.PIN,false);

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        searchPageActions.selectActionType(UnbxdEnum.EDIT, query);
        ThreadWait();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getSortPinConditionSize();
        ThreadWait();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, 1, "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROOUP IS" + group);


        searchPage.scrollToBottom();
        merchandisingActions.scrollUntilVisible(merchandisingActions.MerchandisingStrategyEditButton);
        merchandisingActions.waitForElementToBeClickable(merchandisingActions.MerchandisingStrategyEditButton, "Edit");
        merchandisingActions.clickUsingJS(merchandisingActions.MerchandisingStrategyEditButton);
        fillupdatedPinSortMerchandisingData(object, UnbxdEnum.PIN);
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
        ThreadWait();
        Assert.assertTrue(previewpage.contains("preview"),"Not redirecting to preview page");
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(query));

        merchandisingActions.ClickViewHideInsight();
        merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
        merchandisingActions.MerchandisingStrategy.isDisplayed();
        verifyMerchandisingData(object, UnbxdEnum.PIN,true);


        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);

        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, query);
        ThreadWait();
        String updatedcondition = searchPageActions.getConditionTitle();
        Assert.assertTrue(updatedcondition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertTrue(merchandisingActions.previewSummary.isDisplayed());
        ThreadWait();
        Assert.assertEquals(merchandisingActions.pinnedProductIndex.get(0).getText(),updatedPinningPosition,"PRODUCT IS NOT PINNED AT THE GIVEN POSITION");
        Assert.assertTrue(merchandisingActions.pinnedProductText.isDisplayed(),"PINNED TEXT IS NOT PRESENT AT THE GIVEN POSITION");

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        ThreadWait();
        searchPageActions.deleteQueryRule(query);
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
//
//        }
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
//
//        }
//    }

}
