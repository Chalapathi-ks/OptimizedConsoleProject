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
        searchPage.await();
        createBrowsePromotion(page,false,false);
        searchPage.await();
        searchPage.awaitForPageToLoad();
        merchandisingActions.await();

        JsonArray object = filterJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("browseFilters.json");

        searchPageActions.fillPageName(object);
        merchandisingActions.fillCampaignData(campaignData);
        merchandisingActions.await();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.FILTER);

        fillMerchandisingData(object, UnbxdEnum.FILTER,false);
        searchPage.await();
        searchPage.awaitForPageToLoad();
        merchandisingActions.awaitForElementPresence(merchandisingActions.publishButton);
        merchandisingActions.clickOnApplyButton();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        merchandisingActions.await();
        Assert.assertNotNull(searchPage.queryRuleByName(page));
        pageRules.add(page);
        merchandisingActions.await();

        merchandisingActions.openPreviewAndSwitchTheTab();
        merchandisingActions.awaitForPageToLoad();
        merchandisingActions.await();
        String previewPage = driver.getCurrentUrl();
        Assert.assertTrue(previewPage.contains("preview"),"Not redirecting to preview page");
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(page));

        merchandisingActions.ClickViewHideInsight();
        merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
        merchandisingActions.MerchandisingStrategy.isDisplayed();
        merchandisingActions.await();
        verifyMerchandisingGenericData(object, UnbxdEnum.FILTER,false);

        goTo(browsePage);
        searchPage.await();
        searchPage.queryRuleByName(page);
        searchPageActions.selectActionType(UnbxdEnum.EDIT, page);
        merchandisingActions.await();
        merchandisingActions.scrollUntilVisible(merchandisingActions.MerchandisingStrategyEditButton);
        merchandisingActions.scrollDown();
        merchandisingActions.await();
        merchandisingActions.scrollToBottom();
        merchandisingActions.await();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getConditionSize();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, object.size(), "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROOUP IS" + group);

        searchPage.scrollToBottom();
        merchandisingActions.await();
        merchandisingActions.waitForElementToBeClickable(merchandisingActions.MerchandisingStrategyEditButton, "Edit");
        merchandisingActions.safeClick(merchandisingActions.MerchandisingStrategyEditButton);
        fillMerchandisingData(object,UnbxdEnum.FILTER,true);
        searchPage.await();
        searchPage.awaitForPageToLoad();
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
        verifyMerchandisingGenericData(object, UnbxdEnum.FILTER,true);

        goTo(browsePage);
        searchPage.await();
        searchPage.queryRuleByName(page);
        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, page);
        merchandisingActions.await();
        merchandisingActions.scrollUntilVisible(searchPageActions.conditionTitle);
        merchandisingActions.await();
        String Updatedcondition = searchPageActions.getConditionTitle();
        int Updatedgroup = searchPageActions.getConditionSize();
        Assert.assertTrue(Updatedcondition.equalsIgnoreCase(conditionType), "BROWSE: SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(Updatedgroup, object.size(), "BROWSE: NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROOUP IS" + group);

        goTo(browsePage);
        searchPage.await();
        searchPage.queryRuleByName(page);
        merchandisingActions.await();
        searchPageActions.deleteQueryRule(page);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.await();


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
//                merchandisingActions.await();
//            }
//        }
//        goTo(browsePage);
//
//        for (String pageRule : pageRules) {
//            if (searchPage.queryRuleByName(pageRule) != null) {
//                searchPageActions.deleteQueryRule(pageRule);
//                Assert.assertNull(searchPage.queryRuleByName(pageRule), "BROWSE RULE : CREATED PAGE RULE IS NOT DELETED");
//                getDriver().navigate().refresh();
//                merchandisingActions.await();
//
//
//            }
//
//        }
//    }
}
