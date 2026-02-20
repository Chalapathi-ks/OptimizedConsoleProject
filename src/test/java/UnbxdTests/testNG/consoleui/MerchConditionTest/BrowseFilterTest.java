package UnbxdTests.testNG.consoleui.MerchConditionTest;

import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

public class BrowseFilterTest extends MerchandisingTest {

    List<String> queryRules = new ArrayList<>();
    List<String> pageRules = new ArrayList<>();

    @Page
    CommercePageActions searchPageActions;


    private String query;

    @Page
    BrowsePage browsePage;

    public String page;


    //TestCases for browse
    @FileToTest(value = "/consoleTestData/browseFilters.json")
    @Test(description = "Creates and verifies the camapaign creation with Filters for Search Campaigns", dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void browseFiltersTest(Object jsonObject) throws InterruptedException {

        String conditionType = "filter";
        JsonObject filterJsonObject = (JsonObject) jsonObject;
        page = filterJsonObject.get("page").getAsString();

        goTo(browsePage);
        searchPage.threadWait();
        createBrowsePromotion(page,false,false);
        searchPage.threadWait();
        searchPage.awaitForPageToLoad();
        ThreadWait();

        JsonArray object = filterJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("browseFilters.json");

        searchPageActions.fillPageName(object);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.FILTER);

        fillMerchandisingData(object, UnbxdEnum.FILTER,false);
        searchPage.threadWait();
        searchPage.awaitForPageToLoad();
        Thread.sleep(3000);
        merchandisingActions.clickOnApplyButton();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(page));
        pageRules.add(page);
        ThreadWait();

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
        ThreadWait();
        verifyMerchandisingGenericData(object, UnbxdEnum.FILTER,false);

        goTo(browsePage);
        searchPage.threadWait();
        searchPage.queryRuleByName(page);
        searchPageActions.selectActionType(UnbxdEnum.EDIT, page);
        ThreadWait();
        merchandisingActions.scrollUntilVisible(merchandisingActions.MerchandisingStrategyEditButton);
        merchandisingActions.scrollDown();
        ThreadWait();
        merchandisingActions.scrollToBottom();
        ThreadWait();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getConditionSize();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, object.size(), "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROOUP IS" + group);

        searchPage.scrollToBottom();
        ThreadWait();
        merchandisingActions.MerchandisingStrategyEditButton.click();
        fillMerchandisingData(object,UnbxdEnum.FILTER,true);
        searchPage.threadWait();
        searchPage.awaitForPageToLoad();
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
        merchandisingActions.scrollUntilVisible(searchPageActions.conditionTitle);
        ThreadWait();
        String Updatedcondition = searchPageActions.getConditionTitle();
        int Updatedgroup = searchPageActions.getConditionSize();
        Assert.assertTrue(Updatedcondition.equalsIgnoreCase(conditionType), "BROWSE: SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(Updatedgroup, object.size(), "BROWSE: NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROOUP IS" + group);

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
