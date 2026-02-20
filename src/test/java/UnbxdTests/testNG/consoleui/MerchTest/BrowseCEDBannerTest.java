package UnbxdTests.testNG.consoleui.MerchTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.consoleui.actions.BannerActions;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.actions.MerchandisingActions;
import core.consoleui.page.BrowsePage;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import lib.compat.Page;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.ui.page.UiBase.ThreadWait;

public class BrowseCEDBannerTest extends MerchandisingTest {

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



   //Test cases for Browse
    @FileToTest(value = "/consoleTestData/htmlBrowseBanner.json")
    @Test(description = "Verifies the creation and editing of a browse banner with HTML content.", priority = 3, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"sanity"})
    public void createAndEditBrowseHtmlBannerTest(Object jsonObject) throws InterruptedException {
        JsonObject bannerData = (JsonObject) jsonObject;
        page = bannerData.get("page").getAsString();
        String editHtml = bannerData.get("editBanner").getAsString();

        goTo(browsePage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();

        //Create the banner rule
        JsonArray object = bannerData.get("data").getAsJsonArray();
        createBrowsePromotion(page,true,true);
        String html = bannerData.get("Banner").getAsString();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("htmlBrowseBanner.json");
        ThreadWait();
        bannerActions.goToQueryRuleBanner();
        searchPageActions.fillPageName(object);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        bannerActions.addHtmlBanner(html);
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
        await();
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);

        merchandisingActions.ClickViewHideInsight();
        searchPage.scrollToBottom();
        bannerActions.scrollToBannerExperienceInput();
        // Scroll the preview/insight modal to the bottom
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "var modal = document.querySelector('div.preview'); if(modal){modal.scrollTop = modal.scrollHeight;}"
        );
        bannerActions.bannerExperience.isDisplayed();
        ThreadWait();
        Assert.assertTrue(bannerActions.bannerExperienceInput.getText().contains(html),"BROWSE:  HTML URL IS NOT SAME AS GIVEN ");


        // Edit the rule
        goTo(browsePage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPage.queryRuleByName(page);
        searchPageActions.selectActionType(UnbxdEnum.EDIT,page);
        bannerActions.addHtmlBanner(editHtml);
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();

        goTo(browsePage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();
        ThreadWait();
        searchPage.queryRuleByName(page);
        searchPageActions.selectActionType(UnbxdEnum.PREVIEW,page);
        searchPage.threadWait();
        Assert.assertTrue(searchPageActions.htmlPreview.getText().contains(editHtml),"BROWSE: HTML URL IS NOT SAME AS GIVEN");

        searchPage.threadWait();
        goTo(browsePage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.deleteQueryRule(page);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }



}
