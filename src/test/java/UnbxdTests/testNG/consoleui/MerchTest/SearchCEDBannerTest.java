package UnbxdTests.testNG.consoleui.MerchTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonObject;
import core.consoleui.actions.BannerActions;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.actions.MerchandisingActions;
import core.consoleui.page.BrowsePage;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SearchCEDBannerTest extends MerchandisingTest {

    private static final int WAIT_TIMEOUT_SEC = 20;

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
       searchPageActions.awaitForPageToLoad();
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
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);

        merchandisingActions.openPreviewAndSwitchTheTab();
        merchandisingActions.awaitForPageToLoad();
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        Assert.assertTrue(driver.getCurrentUrl().contains("preview"), "Not redirecting to preview page");

        merchandisingActions.ClickViewHideInsight();
        searchPage.scrollToBottom();
        bannerActions.scrollToBannerExperienceInput();
        // Scroll the preview/insight modal to the bottom
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
            "var modal = document.querySelector('div.preview'); if(modal){modal.scrollTop = modal.scrollHeight;}"
        );
        new WebDriverWait(merchandisingActions.getDriver(), WAIT_TIMEOUT_SEC).until(
            (ExpectedCondition<Boolean>) d -> d != null && (
                d.findElements(By.cssSelector(".border-top.pad-top-10")).size() > 0
                || d.findElements(By.cssSelector(".card-header.medium-text .banner-html-body")).size() > 0));
        Assert.assertTrue(bannerActions.bannerExperienceInput.getText().contains(html), "SEARCH:  HTML URL IS NOT SAME AS GIVEN ");

        // Edit the rule
        goTo(searchPage);
        searchPageActions.awaitForPageToLoad();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();
        searchPageActions.selectActionType(UnbxdEnum.EDIT, query);
        new WebDriverWait(merchandisingActions.getDriver(), WAIT_TIMEOUT_SEC).until(
            ExpectedConditions.presenceOfElementLocated(By.cssSelector(".banner-rule-textarea")));
        merchandisingActions.scrollUntilVisible(searchPageActions.htmlPreview);
        Assert.assertTrue(searchPageActions.htmlPreview.getText().contains(html), "SEARCH:  HTML URL IS NOT SAME AS GIVEN ");

        bannerActions.addImgBanner(editImg);
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        Assert.assertNotNull(searchPage.queryRuleByName(query));

//        merchandisingActions.openPreviewAndSwitchTheTab();
//        merchandisingActions.awaitForPageToLoad();
//        merchandisingActions.await();
//        String previewPage = driver.getCurrentUrl();
//        Assert.assertTrue(previewPage.contains("preview"),"Not redirecting to preview page");
//        await();
//        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
//
//        merchandisingActions.ClickViewHideInsight();
//        bannerActions.bannerExperience.isDisplayed();

        goTo(searchPage);
        searchPageActions.awaitForPageToLoad();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();
        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, query);
        merchandisingActions.awaitForElementPresence(searchPageActions.bannerInputImgUrl);
        Assert.assertTrue(searchPageActions.bannerInputImgUrl.getValue().contains(editImg), "SEARCH: IMG URL IS NOT SAME AS GIVEN");

        goTo(searchPage);
        searchPageActions.awaitForPageToLoad();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

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
//                merchandisingActions.await();
//            }
//        }
//
//        }
}
