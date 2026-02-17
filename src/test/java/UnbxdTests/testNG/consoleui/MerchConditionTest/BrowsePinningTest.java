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

public class BrowsePinningTest extends MerchandisingTest {

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


//Test cases for browse
@FileToTest(value = "/consoleTestData/browsePinningCED.json")
@Test(description = "BROWSE: This test verifies the creation with Pinning for Search Campaigns", priority = 3, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"merchandising","sanity"})
public void browsePinningCreateTest(Object jsonObject) throws InterruptedException
{
    JsonObject PinningJsonObject = (JsonObject) jsonObject;
    page = PinningJsonObject.get("page").getAsString();

    goTo(browsePage);
    searchPage.await();
    createBrowsePromotion(page,false,false);

    JsonArray object = PinningJsonObject.get("data").getAsJsonArray();
    String pinningPosition=PinningJsonObject.get("pinningPosition").getAsString();
    Map<String, Object> campaignData = merchandisingActions.getCampaignData("browsePinningCED.json");

    searchPageActions.fillPageName(object);
    merchandisingActions.fillCampaignData(campaignData);
    merchandisingActions.await();
    merchandisingActions.goToLandingPage();
    merchandisingActions.goToSectionInMerchandising(UnbxdEnum.PIN);
    fillPinSortMerchandisingData(object, UnbxdEnum.PIN);
    merchandisingActions.clickOnApplyButton();
    Assert.assertEquals(merchandisingActions.pinnedProductIndex.getText(),pinningPosition,"BROWSE: PRODUCT IS NOT PINNED AT THE GIVEN POSITION");
    Assert.assertTrue(merchandisingActions.pinnedProductText.isDisplayed(),"BROWSE: PINNED TEXT IS NOT PRESENT AT THE GIVEN POSITION");

    merchandisingActions.publishCampaign();
    merchandisingActions.verifySuccessMessage();
    merchandisingActions.await();
    Assert.assertNotNull(searchPage.queryRuleByName(page));
    pageRules.add(page);
    merchandisingActions.openPreviewAndSwitchTheTab();
    merchandisingActions.awaitForPageToLoad();
    String previewPage = driver.getCurrentUrl();
    merchandisingActions.await();
    Assert.assertTrue(previewPage.contains("preview"),"Not redirecting to preview page");
    merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
    Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(page));

    merchandisingActions.ClickViewHideInsight();
    merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
    merchandisingActions.MerchandisingStrategy.isDisplayed();
    verifyMerchandisingData(object, UnbxdEnum.PIN,false);

    goTo(browsePage);
    searchPage.await();
    searchPage.queryRuleByName(page);
    searchPageActions.selectActionType(UnbxdEnum.EDIT, page);
    merchandisingActions.await();
    String condition = searchPageActions.getConditionTitle();
    int group = searchPageActions.getSortPinConditionSize();
    Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
    Assert.assertEquals(group, 1, "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROOUP IS" + group);


    searchPage.scrollToBottom();
    // Use robust click handling to avoid click interception issues
    merchandisingActions.scrollUntilVisible(merchandisingActions.MerchandisingStrategyEditButton);
    merchandisingActions.waitForElementToBeClickable(merchandisingActions.MerchandisingStrategyEditButton, "Edit");
    merchandisingActions.clickUsingJS(merchandisingActions.MerchandisingStrategyEditButton);
    fillupdatedPinSortMerchandisingData(object, UnbxdEnum.PIN);
    merchandisingActions.clickOnApplyButton();
    searchPage.await();
    merchandisingActions.publishCampaign();
    merchandisingActions.verifySuccessMessage();
    merchandisingActions.await();

    //Preview
    merchandisingActions.openPreviewAndSwitchTheTab();
    merchandisingActions.awaitForPageToLoad();
    merchandisingActions.await();
    String previewpage = driver.getCurrentUrl();
    Assert.assertTrue(previewpage.contains("preview"),"Not redirecting to preview page");
    merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
    Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(page));

    merchandisingActions.ClickViewHideInsight();
    merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
    merchandisingActions.MerchandisingStrategy.isDisplayed();
    verifyMerchandisingData(object, UnbxdEnum.PIN,true);


    goTo(browsePage);
    searchPage.await();
    searchPage.queryRuleByName(page);
    searchPageActions.selectActionType(UnbxdEnum.PREVIEW, page);
    merchandisingActions.await();
    String updatedcondition = searchPageActions.getConditionTitle();
    Assert.assertTrue(updatedcondition.equalsIgnoreCase(conditionType), "BROWSE: SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
    Assert.assertTrue(merchandisingActions.previewSummary.isDisplayed());
    Assert.assertTrue(merchandisingActions.pinnedProductText.isDisplayed(),"BROWSE: PINNED TEXT IS NOT PRESENT AT THE GIVEN POSITION");

    goTo(browsePage);
    searchPage.await();
    searchPage.queryRuleByName(page);
    merchandisingActions.await();
    searchPageActions.deleteQueryRule(page);
    searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
    searchPage.await();

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
//                merchandisingActions.await();
//
//            }
//
//        }
//    }

}
