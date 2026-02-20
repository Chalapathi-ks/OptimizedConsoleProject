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
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.ui.page.UiBase.ThreadWait;
import static lib.constants.UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE;

public class BrowseBannerTest extends MerchandisingTest {

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

    @FileToTest(value = "/consoleTestData/CEDBanner.json")
    @Test(description = "Verifies the creation and editing of a browse banner with a field rule and image URL.", priority = 4, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups={"sanity"})
    public void createAndEditBrowseFieldRuleImageBannerTest(Object jsonObject) throws InterruptedException {
        JsonObject bannerData = (JsonObject) jsonObject;
        String Attribute = bannerData.get("Attribute").getAsString();
        String value = bannerData.get("Value").getAsString();
        page = bannerData.get("page").getAsString();

        goTo(browsePage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();

        //create the rule
        String ImgUrl= bannerData.get("data").getAsString();
        String editImgUrl=bannerData.get("editBanner").getAsString();
        createBrowsePromotion(page,true,false);
        bannerActions.goToFieldRuleBanner();
        bannerActions.selectFieldRuleAttribute(Attribute);
        ThreadWait();
        bannerActions.selectFieldRuleAttributeValue(value);
        click(searchPageActions.nextButton);
        bannerActions.addHtmlBanner(ImgUrl);
        ThreadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(page));
        pageRules.add(page);

        // Edit the rule
        searchPageActions.selectActionType(UnbxdEnum.EDIT,page);
        ThreadWait();
        Assert.assertTrue(searchPageActions.htmlBannerInput.getValue().contains(ImgUrl),"BROWSE RULE : IMG URL IS NOT SAME AS GIVEN");
        bannerActions.addImgBanner(editImgUrl);
        click(merchandisingActions.fieldRulePublishBtn);
        merchandisingActions.verifySuccessMessage();
        searchPageActions.selectActionType(UnbxdEnum.EDIT,page);
        ThreadWait();
        Assert.assertTrue(searchPageActions.bannerInputImgUrl.getValue().contains(editImgUrl),"BROWSE RULE : IMG URL IS NOT SAME AS GIVEN");

        searchPage.threadWait();
        goTo(browsePage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.deleteQueryRule(page);
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
//            goTo(browsePage);
//            merchandisingActions.goToSection(UnbxdEnum.BANNER);
//            for (String pageRule : pageRules) {
//                if (searchPage.queryRuleByName(pageRule) != null) {
//                    searchPageActions.deleteQueryRule(pageRule);
//                    Assert.assertNull(searchPage.queryRuleByName(pageRule), "BROWSE RULE : CREATED PAGE RULE IS NOT DELETED");
//                    getDriver().navigate().refresh();
//                    ThreadWait();
//
//
//                }
//            }
//        }
}
