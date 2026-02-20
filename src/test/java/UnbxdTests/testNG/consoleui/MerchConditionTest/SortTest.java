package UnbxdTests.testNG.consoleui.MerchConditionTest;

import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.consoleui.actions.CommercePageActions;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import lib.compat.Page;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.ui.page.UiBase.ThreadWait;

public class SortTest extends MerchandisingTest {

    List<String> queryRules = new ArrayList<>();

    @Page
    CommercePageActions searchPageActions;

    private String query;

    List<String> pageRules = new ArrayList<>();

    @Page
    core.consoleui.page.BrowsePage browsePage;

    public String page;


    @FileToTest(value = "/consoleTestData/Sort.json")
    @Test(description = "SEARCH: Creates and verifies the campaign creation with sort for Search Campaigns", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void sortTest(Object jsonObject) throws InterruptedException
    {
        String conditionType = "Sort By";
        JsonObject sortJsonObject = (JsonObject) jsonObject;
        query = sortJsonObject.get("query").getAsString();

        goTo(searchPage);
        searchPage.threadWait();
        createPromotion(query,false,false);

        JsonArray object = sortJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("sort.json");

        // goTo(searchPage);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.SORT);
        fillPinSortMerchandisingData(object, UnbxdEnum.SORT);
        merchandisingActions.clickOnApplyButton();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);


        merchandisingActions.openPreviewAndSwitchTheTab();
        merchandisingActions.awaitForPageToLoad();

        // Wait for the preview page to load by checking the URL or a unique element
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after preview: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("preview"), "Not redirecting to preview page");
        await();
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(query));

        merchandisingActions.ClickViewHideInsight();
        merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
        merchandisingActions.MerchandisingStrategy.isDisplayed();
        verifyMerchandisingData(object, UnbxdEnum.SORT,false);

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        searchPageActions.selectActionType(UnbxdEnum.EDIT, query);
        ThreadWait();
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
        ThreadWait();
        String previewpage = driver.getCurrentUrl();
        Assert.assertTrue(previewpage.contains("preview"),"Not redirecting to preview page");
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(query));

        merchandisingActions.ClickViewHideInsight();
        merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
        merchandisingActions.MerchandisingStrategy.isDisplayed();
        verifyMerchandisingData(object, UnbxdEnum.SORT,true);

        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, query);
        ThreadWait();
        String updatedcondition = searchPageActions.getConditionTitle();
        int updatedgroup = searchPageActions.getSortPinConditionSize();
        Assert.assertTrue(updatedcondition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(updatedgroup, 1, "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROOUP IS" + group);

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
//        }
//
//    }

}
