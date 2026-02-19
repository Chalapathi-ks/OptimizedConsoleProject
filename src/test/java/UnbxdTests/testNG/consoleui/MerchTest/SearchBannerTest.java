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
import static lib.constants.UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE;

public class SearchBannerTest extends MerchandisingTest {

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


   @FileToTest(value = "/consoleTestData/htmlBannerForDuplicate.json")
    @Test(description = "SEARCH: This test Verifies the Banner duplicate", priority = 3, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void duplicateBannerHtmlTest(Object jsonObject) throws InterruptedException {
        JsonObject bannerData=(JsonObject) jsonObject;
        query=bannerData.get("query").getAsString();

        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();
        Thread.sleep(7000);

        //create the rule
        createPromotion(query,true,true);
        String html= bannerData.get("data").getAsString();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("htmlBannerForDuplicate.json");
        bannerActions.goToQueryRuleBanner();
        searchPageActions.fillQueryRuleData(query,null);
        merchandisingActions.fillCampaignData(campaignData);
        bannerActions.addHtmlBanner(html);
        Thread.sleep(5000);
        bannerActions.awaitForElementPresence(merchandisingActions.publishButton);
        Thread.sleep(5000);
        click(merchandisingActions.publishButton);
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);

        //Stopped the rule
        searchPageActions.selectActionType(UnbxdEnum.MORE,query);
        searchPageActions.selectActionFromMore(UnbxdEnum.STOPPED,query);
        searchPageActions.selectModelWindow();
        Assert.assertTrue(searchPageActions.checkSuccessMessage(), SUCCESS_MESSAGE_FAILURE);
        Assert.assertTrue(searchPageActions.stopCampaign.isDisplayed(),"SEARCH: BANNER RULE IS NOT IN STOPPED STATE");

        //duplicate the rule
        searchPageActions.awaitForPageToLoad();
        Thread.sleep(5000);
        searchPageActions.selectActionType(UnbxdEnum.MORE,query);
        searchPageActions.selectActionFromMore(UnbxdEnum.DUPLICATE,query);
        searchPageActions.awaitForPageToLoad();
        Assert.assertTrue(searchPageActions.campaignNameInput.getValue().contains("copy"),"SEARCH: CAMPAIGN NAME IS NOT BEEN DUPLICATED");
        Assert.assertEquals(searchPageActions.htmlPreview.getText(),html,"SEARCH: HTML URL IS NOT SAME AS GIVENe");
        bannerActions.awaitForElementPresence(merchandisingActions.publishButton);
        searchPage.threadWait();
        click(merchandisingActions.publishButton);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        merchandisingActions.campaignPromotions.getText().contains("copy");
        Assert.assertTrue(merchandisingActions.activeStatus.isDisplayed(),"SEARCH: PROMOTION RULE IS NOT IN ACTIVE STATE");

        searchPage.threadWait();
        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.deleteQueryRule(query);
        searchPage.threadWait();
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();


   }



    @FileToTest(value = "/consoleTestData/FieldRuleBanner.json")
    @Test(description = "Verifies the creation and editing of a search banner with a field rule and image URL.", priority = 2, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups={"sanity"})
    public void createAndEditSearchFieldRuleImageBannerTest(Object jsonObject) throws InterruptedException {
        JsonObject bannerData = (JsonObject) jsonObject;
        query = bannerData.get("Value").getAsString();
        String value = bannerData.get("Value").getAsString();
        String Attribute = bannerData.get("Attribute").getAsString();


        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();
        Thread.sleep(7000);

        //create the rule
        String ImgUrl= bannerData.get("data").getAsString();
        String editImgUrl=bannerData.get("editBanner").getAsString();
        createPromotion(query,true,false);
        ThreadWait();
        bannerActions.goToFieldRuleBanner();
        ThreadWait();
        bannerActions.selectFieldRuleAttribute(Attribute);
        ThreadWait();
        bannerActions.selectFieldRuleAttributeValue(value);
        // Use robust click handling to avoid click interception issues
        searchPageActions.scrollUntilVisible(searchPageActions.nextButton);
        searchPageActions.waitForElementToBeClickable(searchPageActions.nextButton, "Next Button");
        searchPageActions.clickUsingJS(searchPageActions.nextButton);
        bannerActions.addImgBanner(ImgUrl);
        ThreadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);

        // Edit the rule
        searchPageActions.selectActionType(UnbxdEnum.EDIT,query);
        ThreadWait();
        Assert.assertEquals(ImgUrl,searchPageActions.bannerInputImgUrl.getValue(),"SEARCH: IMG URL IS NOT SAME AS GIVEN");
        Assert.assertEquals(ImgUrl,searchPageActions.bannerInputRedirectUrl.getValue(),"SEARCH: REDIRECT URL IS NOT SAME AS GIVEN");
        bannerActions.addHtmlBanner(editImgUrl);
        // Use robust click handling to avoid click interception issues
        merchandisingActions.scrollUntilVisible(merchandisingActions.fieldRulePublishBtn);
        merchandisingActions.waitForElementToBeClickable(merchandisingActions.fieldRulePublishBtn, "Publish Button");
        merchandisingActions.clickUsingJS(merchandisingActions.fieldRulePublishBtn);
        merchandisingActions.verifySuccessMessage();
        searchPageActions.selectActionType(UnbxdEnum.EDIT,query);
        ThreadWait();
        Assert.assertEquals(editImgUrl,searchPageActions.htmlPreview.getText(),"SEARCH: IMG URL IS NOT SAME AS GIVEN");

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
