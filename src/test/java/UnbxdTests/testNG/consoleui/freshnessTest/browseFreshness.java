package UnbxdTests.testNG.consoleui.freshnessTest;


import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonObject;
import core.consoleui.actions.ABActions;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.actions.VariantLockAction;
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

public class browseFreshness extends MerchandisingTest {


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

    @Page
    VariantLockAction variantLockAction;

    public String page;

    @FileToTest(value = "/consoleTestData/freshness.json")
    @Test(description = "Browse: Creates, verifies and deletes the campaign creation with Freshness for Browse Campaigns", dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising", "sanity"})
    public void browseFreshnessCreateEditDeleteTest(Object jsonObject) throws InterruptedException {
        JsonObject freshnessJsonObject = (JsonObject) jsonObject;
        page = freshnessJsonObject.get("query").getAsString();
        String AttributeName = freshnessJsonObject.get("attribute").getAsString();
        String AttributeValue = freshnessJsonObject.get("value").getAsString();


        goTo(browsePage);
        searchPage.threadWait();
        createPromotion(page, false, false);
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("freshness.json");
        searchPageActions.pageRuleDropdown.click();
        searchPageActions.threadWait();
        searchPageActions.awaitForElementPresence(searchPageActions.BuildPath);
        searchPageActions.BuildPath.click();
        searchPageActions.SelectedCategoryPathDisplay.fill().with(page);
        searchPageActions.threadWait();
        searchPageActions.categorypathApplyButton.click();
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

        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, page);
        ThreadWait();
        // Verify freshness summary value in the promotion rules summary
        FreshnessAction.verifyFreshnessSummaryValue(AttributeValue);
        ThreadWait();

        goTo(browsePage);
        searchPage.threadWait();
        searchPage.queryRuleByName(page);
        merchandisingActions.openPreviewAndSwitchTheTab();
        merchandisingActions.awaitForPageToLoad();
        ThreadWait();
//        String previewpage = driver.getCurrentUrl();
//        Assert.assertTrue(previewpage.contains("preview"),"Not redirecting to preview page");
//        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(page));

        FreshnessAction.verifyDateIsoForFirstFiveProducts(daysThreshold);

        goTo(browsePage);
        searchPage.threadWait();
        searchPage.queryRuleByName(page);
        searchPageActions.deleteQueryRule(page);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();
    }


    @FileToTest(value = "/consoleTestData/freshness.json")
    @Test(description = "Browse: Creates and verifies the global campaign creation with Freshness for Browse Campaigns", dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising", "sanity"})
    public void browseGlobalFreshnessTest(Object jsonObject) throws InterruptedException {
        JsonObject freshnessJsonObject = (JsonObject) jsonObject;
        page = freshnessJsonObject.get("query").getAsString();
        String AttributeName = freshnessJsonObject.get("attribute").getAsString();
        String AttributeValue = freshnessJsonObject.get("value").getAsString();

        goTo(browsePage);
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
        searchPage.scrollToBottom();
        ThreadWait();
        searchPage.scrollToBottom();
        // Verify freshness summary value in the promotion rules summary
        FreshnessAction.verifyFreshnessSummaryValue(AttributeValue);
        ThreadWait();
        FreshnessAction.verifyDateIsoForFirstFiveProducts(daysThreshold);

    }

}
