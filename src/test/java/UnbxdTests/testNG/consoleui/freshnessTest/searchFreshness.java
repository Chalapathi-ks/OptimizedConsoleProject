package UnbxdTests.testNG.consoleui.freshnessTest;


import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonObject;
import core.consoleui.actions.ABActions;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.actions.freshnessAction;
import core.consoleui.page.BrowsePage;
import lib.EnvironmentConfig;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.ui.page.UiBase.ThreadWait;

public class searchFreshness extends MerchandisingTest {

    String query;

    List<String> queryRules = new ArrayList<>();
    List<String> pageRules = new ArrayList<>();

    @Page
    CommercePageActions searchPageActions;

    @Page
    BrowsePage browsePage;

    @Page
    freshnessAction FreshnessAction;

    @Page
    ABActions abActions;

    public String page;


    @FileToTest(value = "/consoleTestData/freshness.json")
    @Test(description = "Search: Creates, verifies and deletes the campaign creation with Freshness for Search Campaigns", dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void searchFreshnessCreateEditDeleteTest(Object jsonObject) throws InterruptedException
    {
        JsonObject freshnessJsonObject = (JsonObject) jsonObject;
        query = freshnessJsonObject.get("query").getAsString();
        String AttributeName = freshnessJsonObject.get("attribute").getAsString();
        String AttributeValue = freshnessJsonObject.get("value").getAsString();


        goTo(searchPage);
        searchPage.threadWait();
        createPromotion(query,false,false);
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("freshness.json");

        // goTo(searchPage);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        ThreadWait();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.FRESH);
        FreshnessAction.selectAttribute(AttributeName);
        FreshnessAction.selectAttributeValue(AttributeValue);
        merchandisingActions.clickOnApplyButton();
        ThreadWait();
        
        // Wait for products to be visible and verify date_iso for first 5 products
        int daysThreshold = Integer.parseInt(AttributeValue);
        FreshnessAction.verifyDateIsoForFirstFiveProducts(daysThreshold);
        searchPage.threadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();

        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, query);
        ThreadWait();
        
        // Verify freshness summary value in the promotion rules summary
        FreshnessAction.verifyFreshnessSummaryValue(AttributeValue);
        ThreadWait();

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        merchandisingActions.openPreviewAndSwitchTheTab();
        merchandisingActions.awaitForPageToLoad();
        ThreadWait();
//        String previewpage = driver.getCurrentUrl();
//        Assert.assertTrue(previewpage.contains("preview"),"Not redirecting to preview page");
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(query));

        FreshnessAction.verifyDateIsoForFirstFiveProducts(daysThreshold);
        
        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();
    }


    @FileToTest(value = "/consoleTestData/freshness.json")
    @Test(description = "Search: Creates and verifies the global campaign creation with Freshness for Search Campaigns", dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void searchGlobalFreshnessTest(Object jsonObject) throws InterruptedException
    {
        JsonObject freshnessJsonObject = (JsonObject) jsonObject;
        query = freshnessJsonObject.get("query").getAsString();
        String AttributeName = freshnessJsonObject.get("attribute").getAsString();
        String AttributeValue = freshnessJsonObject.get("value").getAsString();

        goTo(searchPage);
        ThreadWait();
        await();
        createGlobalRulePromotion();

        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.FRESH);
        FreshnessAction.selectAttribute(AttributeName);
        FreshnessAction.selectAttributeValue(AttributeValue);
        merchandisingActions.clickOnApplyButton();
        ThreadWait();

        // Wait for products to be visible and verify date_iso for first 5 products
        int daysThreshold = Integer.parseInt(AttributeValue);
        FreshnessAction.verifyDateIsoForFirstFiveProducts(daysThreshold);
        searchPage.threadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();

        searchPage.editGlobalRule();
        ThreadWait();

        // Verify freshness summary value in the promotion rules summary
        FreshnessAction.verifyFreshnessSummaryValue(AttributeValue);
        ThreadWait();
        FreshnessAction.verifyDateIsoForFirstFiveProducts(daysThreshold);

    }

}


