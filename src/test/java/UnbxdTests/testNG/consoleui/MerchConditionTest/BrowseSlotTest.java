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

public class BrowseSlotTest extends MerchandisingTest {

    List<String> queryRules = new ArrayList<>();

    @Page
    CommercePageActions searchPageActions;

    private String query;

    String conditionType = "slot";

    List<String> pageRules = new ArrayList<>();

    @Page
    BrowsePage browsePage;

    public String page;



    //TestCases for Browse
    @FileToTest(value = "/consoleTestData/browseSlot.json")
    @Test(description = "BROWSE: Creates and verifies the camapaign creation with Slot for Search Campaigns", priority = 3, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void browseSlotCreateTest(Object jsonObject) throws InterruptedException {

        JsonObject slotJsonObject = (JsonObject) jsonObject;

        page = slotJsonObject.get("page").getAsString();
        int endRange = slotJsonObject.get("EndRange").getAsInt();

        goTo(browsePage);
        searchPage.threadWait();
        createBrowsePromotion(page,false,false);
        JsonArray object = slotJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("browseSlot.json");

        searchPageActions.fillPageName(object);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.SLOT);
        ThreadWait();
        fillMerchandisingData(object, UnbxdEnum.SLOT,false);
        merchandisingActions.clickOnApplyButton();
        ThreadWait();
        merchandisingActions.verifySlotIconIsPresentAtGivenPosition(endRange);
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(page));
        pageRules.add(page);

        merchandisingActions.openPreviewAndSwitchTheTab();
        merchandisingActions.awaitForPageToLoad();
        ThreadWait();
        String previewPage = driver.getCurrentUrl();
        Assert.assertTrue(previewPage.contains("preview"),"Not redirecting to preview page");
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(page));

        merchandisingActions.ClickViewHideInsight();
        merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
        merchandisingActions.MerchandisingStrategy.isDisplayed();
        verifyMerchandisingGenericData(object, UnbxdEnum.SLOT,false);

        goTo(browsePage);
        searchPage.threadWait();
        searchPage.queryRuleByName(page);
        searchPageActions.selectActionType(UnbxdEnum.EDIT, page);

        ThreadWait();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getConditionSize();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, object.size(), "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROOUP IS" + group);

        searchPage.scrollToBottom();
        ThreadWait();
        // Use robust click handling to avoid click interception issues
        merchandisingActions.scrollUntilVisible(merchandisingActions.MerchandisingStrategyEditButton);
        merchandisingActions.waitForElementToBeClickable(merchandisingActions.MerchandisingStrategyEditButton, "Edit");
        merchandisingActions.clickUsingJS(merchandisingActions.MerchandisingStrategyEditButton);
        fillMerchandisingData(object,UnbxdEnum.SLOT,true);
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
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(page));

        merchandisingActions.ClickViewHideInsight();
        merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
        merchandisingActions.MerchandisingStrategy.isDisplayed();
        verifyMerchandisingGenericData(object, UnbxdEnum.FILTER,true);


        goTo(browsePage);
        searchPage.threadWait();
        searchPage.queryRuleByName(page);
        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, page);
        ThreadWait();
        merchandisingActions.verifySlotIconIsPresentAtGivenPosition(endRange);
        String Updatecondition = searchPageActions.getConditionTitle();
        int Updategroup = searchPageActions.getConditionSize();
        Assert.assertTrue(Updatecondition.equalsIgnoreCase(conditionType), "BROWSE: SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(Updategroup, object.size(), "BROWSE: NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);

        goTo(browsePage);
        searchPage.threadWait();
        searchPage.queryRuleByName(page);
        ThreadWait();
        searchPageActions.deleteQueryRule(page);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }



//    @AfterClass(alwaysRun = true,groups={"sanity"})
//    public void deleteCreatedRules() {
//        goTo(searchPage);
//
//        for (String queryRule : queryRules) {
//            if (searchPage.queryRuleByName(queryRule) != null) {
//                searchPageActions.deleteQueryRule(queryRule);
//                Assert.assertNull(searchPage.queryRuleByName(queryRule), "CREATED QUERY RULE IS NOT DELETED");
//                getDriver().navigate().refresh();
//                ThreadWait();
//            }
//        }
//        goTo(browsePage);
//
//        for (String pageRule : pageRules) {
//            if (searchPage.queryRuleByName(pageRule) != null) {
//                searchPageActions.deleteQueryRule(pageRule);
//                Assert.assertNull(searchPage.queryRuleByName(pageRule), "BROWSE RULE : CREATED PAGE RULE IS NOT DELETED");
//                getDriver().navigate().refresh();
//                ThreadWait();
//
//
//            }
//
//        }
//    }
}
