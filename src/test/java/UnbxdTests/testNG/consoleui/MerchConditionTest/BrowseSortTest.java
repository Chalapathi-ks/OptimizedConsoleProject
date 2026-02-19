package UnbxdTests.testNG.consoleui.MerchConditionTest;

import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.consoleui.actions.CommercePageActions;
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

public class BrowseSortTest extends MerchandisingTest {

    List<String> queryRules = new ArrayList<>();

    @Page
    CommercePageActions searchPageActions;

    private String query;

    List<String> pageRules = new ArrayList<>();

    @Page
    core.consoleui.page.BrowsePage browsePage;

    public String page;


    //Testcases for Browse
    @FileToTest(value = "/consoleTestData/browseSort.json")
    @Test(description = "BROWSE: Creates and verifies the campaign creation with sort for Search Campaigns", priority = 2, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void BrowseSortTest(Object jsonObject) throws InterruptedException
    {
        String conditionType = "Sort By";
        JsonObject sortJsonObject = (JsonObject) jsonObject;
        page = sortJsonObject.get("page").getAsString();

        goTo(browsePage);
        searchPage.threadWait();
        createBrowsePromotion(page,false,false);

        JsonArray object = sortJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("browseSort.json");

        searchPageActions.fillPageName(object);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.SORT);
        fillPinSortMerchandisingData(object, UnbxdEnum.SORT);
        merchandisingActions.clickOnApplyButton();
        ThreadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(page));
        pageRules.add(page);

        merchandisingActions.openPreviewAndSwitchTheTab();
        merchandisingActions.awaitForPageToLoad();
        ThreadWait();
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after preview: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("preview"), "Not redirecting to preview page");
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(page));

        merchandisingActions.ClickViewHideInsight();
        merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
        merchandisingActions.MerchandisingStrategy.isDisplayed();
        ThreadWait(); // Additional wait for strategy details to load
        verifyMerchandisingData(object, UnbxdEnum.SORT,false);

        goTo(browsePage);
        searchPage.threadWait();
        searchPage.queryRuleByName(page);
        searchPageActions.selectActionType(UnbxdEnum.EDIT, page);
        ThreadWait();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getSortPinConditionSize();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, 1, "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROOUP IS" + group);


        searchPage.scrollToBottom();
        ThreadWait();
        // Use robust click handling to avoid click interception issues
        merchandisingActions.scrollUntilVisible(merchandisingActions.MerchandisingStrategyEditButton);
        merchandisingActions.waitForElementToBeClickable(merchandisingActions.MerchandisingStrategyEditButton, "Edit");
        merchandisingActions.clickUsingJS(merchandisingActions.MerchandisingStrategyEditButton);
        fillupdatedPinSortMerchandisingData(object, UnbxdEnum.SORT);
        merchandisingActions.clickOnApplyButton();
        searchPage.threadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();

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
        ThreadWait(); // Additional wait for strategy details to load
        //verifyMerchandisingData(object, UnbxdEnum.SORT,true);

        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, page);
        ThreadWait();
        String updatedcondition = searchPageActions.getConditionTitle();
        int updatedgroup = searchPageActions.getSortPinConditionSize();
        Assert.assertTrue(updatedcondition.equalsIgnoreCase(conditionType), "BROWSE:SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(updatedgroup, 1, "BROWSE:NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROOUP IS" + group);

        goTo(browsePage);
        searchPage.threadWait();
        searchPage.queryRuleByName(page);
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
