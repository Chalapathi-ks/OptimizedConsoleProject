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
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.ui.page.UiBase.ThreadWait;

public class PinTheProductFromConsoleTest extends MerchandisingTest {

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
    @Test(description = "SEARCH: Creates and verifies the camapaign creation with Pinning From the Console Preview", priority = 2, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"merchandising"})
    public void pinTheProductFromConsolePreviewTest(Object jsonObject) throws InterruptedException {

        JsonObject PinningJsonObject = (JsonObject) jsonObject;
        String query = PinningJsonObject.get("query").getAsString();
        String pinningPosition=PinningJsonObject.get("pinningPosition").getAsString();

        goTo(searchPage);
        ThreadWait();
        await();
        createPromotion(query,false,false);
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("Pinning.json");

        // goTo(searchPage);
        searchPageActions.fillQueryRuleData(query,null);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.PIN);
        merchandisingActions.pinProductFromConsolePreview(pinningPosition);
        //merchandisingActions.clickOnApplyButton();
        merchandisingActions.publishCampaign();

        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);
        searchPageActions.selectActionType(UnbxdEnum.PREVIEW,query);
        ThreadWait();
        String condition = searchPageActions.getConditionTitle();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertTrue(merchandisingActions.previewSummary.isDisplayed());
        Assert.assertEquals(merchandisingActions.pinnedProductIndex.getText(),"2","PRODUCT IS NOT PINNED AT THE GIVEN POSITION");
        Assert.assertTrue(merchandisingActions.pinnedProductText.isDisplayed(),"PINNED TEXT IS NOT PRESENT AT THE GIVEN POSITION");

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        ThreadWait();
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }
//Browse
    @FileToTest(value = "/consoleTestData/browsePinning.json")
    @Test(description = "BROWSE: Creates and verifies the camapaign creation with Pinning From the Console Preview", priority = 4, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"merchandising"})
    public void browsePinTheProductFromConsolePreviewTest(Object jsonObject) throws InterruptedException {

        JsonObject PinningJsonObject = (JsonObject) jsonObject;
        String page = PinningJsonObject.get("page").getAsString();
        String pinningPosition=PinningJsonObject.get("pinningPosition").getAsString();

        goTo(browsePage);
        searchPage.threadWait();
        createBrowsePromotion(page,false,false);
        JsonArray object = PinningJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("browsePinning.json");

        searchPageActions.fillPageName(object);
        merchandisingActions.fillCampaignData(campaignData);
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.PIN);
        merchandisingActions.pinProductFromConsolePreview(pinningPosition);
        merchandisingActions.publishCampaign();

        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(page));
        pageRules.add(page);

        searchPageActions.selectActionType(UnbxdEnum.PREVIEW,page);
        ThreadWait();
        String condition = searchPageActions.getConditionTitle();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "BROWSE: SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertTrue(merchandisingActions.previewSummary.isDisplayed());
        Assert.assertEquals(merchandisingActions.pinnedProductIndex.getText(),"2","BROWSE: PRODUCT IS NOT PINNED AT THE GIVEN POSITION");
        Assert.assertTrue(merchandisingActions.pinnedProductText.isDisplayed(),"BROWSE: PINNED TEXT IS NOT PRESENT AT THE GIVEN POSITION");

        goTo(browsePage);
        searchPage.threadWait();
        searchPage.queryRuleByName(page);
        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, page);
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
