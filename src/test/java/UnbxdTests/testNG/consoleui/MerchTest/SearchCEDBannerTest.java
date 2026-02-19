package UnbxdTests.testNG.consoleui.MerchTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonObject;
import core.consoleui.actions.BannerActions;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.actions.MerchandisingActions;
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

public class SearchCEDBannerTest extends MerchandisingTest {

    List<String> queryRules = new ArrayList<>();
    List<String> pageRules = new ArrayList<>();

    @Page
    MerchandisingActions merchandisingActions;

    @Page
    CommercePageActions searchPageActions;

    @Page
    BannerActions bannerActions;

    @Page
    BrowsePage browsePage;

    public String query;

    public String page;


    @FileToTest(value = "/consoleTestData/htmlBanner.json")
    @Test(description = "Verifies the creation and editing of a search banner with HTML content.",groups="sanity",priority = 1,dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void createAndEditSearchHtmlBannerTest(Object jsonObject) throws InterruptedException {
        JsonObject bannerData=(JsonObject) jsonObject;
        query=bannerData.get("query").getAsString();
        String editImg=bannerData.get("editBanner").getAsString();

       goTo(searchPage);
       searchPage.threadWait();
       merchandisingActions.goToSection(UnbxdEnum.BANNER);
       searchPageActions.awaitForPageToLoad();

        //Create the banner rule
        createPromotion(query,true,true);
        String html= bannerData.get("data").getAsString();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("htmlBanner.json");
        bannerActions.goToQueryRuleBanner();
        searchPageActions.fillQueryRuleData(query,null);
        merchandisingActions.fillCampaignData(campaignData);
        bannerActions.addHtmlBanner(html);
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);
        ThreadWait();
        Thread.sleep(5000);

        merchandisingActions.openPreviewAndSwitchTheTab();
        merchandisingActions.awaitForPageToLoad();
        ThreadWait();
        String PreviewPage = driver.getCurrentUrl();
        Assert.assertTrue(PreviewPage.contains("preview"),"Not redirecting to preview page");
        await();
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);

        merchandisingActions.ClickViewHideInsight();
        searchPage.scrollToBottom();
        bannerActions.scrollToBannerExperienceInput();
        // Scroll the preview/insight modal to the bottom
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
            "var modal = document.querySelector('div.preview'); if(modal){modal.scrollTop = modal.scrollHeight;}"
        );
        merchandisingActions.awaitForElementPresence(bannerActions.bannerExperienceInput);
        Thread.sleep(5000);
        Assert.assertTrue(bannerActions.bannerExperienceInput.getText().contains(html),"SEARCH:  HTML URL IS NOT SAME AS GIVEN ");

        // Edit the rule
        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();
        searchPageActions.selectActionType(UnbxdEnum.EDIT,query);
        searchPage.threadWait();
        Assert.assertTrue(searchPageActions.htmlPreview.getText().contains(html),"SEARCH:  HTML URL IS NOT SAME AS GIVEN ");

        bannerActions.addImgBanner(editImg);
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        ThreadWait();
        
//        merchandisingActions.openPreviewAndSwitchTheTab();
//        merchandisingActions.awaitForPageToLoad();
//        ThreadWait();
//        String previewPage = driver.getCurrentUrl();
//        Assert.assertTrue(previewPage.contains("preview"),"Not redirecting to preview page");
//        await();
//        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
//
//        merchandisingActions.ClickViewHideInsight();
//        bannerActions.bannerExperience.isDisplayed();

        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();
        ThreadWait();
        searchPageActions.selectActionType(UnbxdEnum.PREVIEW,query);
        searchPage.threadWait();
        Assert.assertTrue(searchPageActions.bannerInputImgUrl.getValue().contains(editImg),"SEARCH: IMG URL IS NOT SAME AS GIVEN");

        searchPage.threadWait();
        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }



//    @AfterClass(alwaysRun = true,groups={"sanity"})
//    public void deleteCreatedRules() throws InterruptedException {
//        goTo(searchPage);
//        merchandisingActions.goToSection(UnbxdEnum.BANNER);
//        for (String queryRule : queryRules) {
//            if (searchPage.queryRuleByName(queryRule)!= null)
//            {
//                searchPageActions.deleteQueryRule(queryRule);
//                Assert.assertNull(searchPage.queryRuleByName(queryRule), "CREATED QUERY RULE IS NOT DELETED");
//                getDriver().navigate().refresh();
//                ThreadWait();
//            }
//        }
//
//        }
}
