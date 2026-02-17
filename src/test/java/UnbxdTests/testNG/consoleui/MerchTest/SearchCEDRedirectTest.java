package UnbxdTests.testNG.consoleui.MerchTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonObject;
import core.consoleui.actions.BannerActions;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.actions.MerchandisingActions;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.ui.page.UiBase.ThreadWait;

public class SearchCEDRedirectTest extends MerchandisingTest {

    List<String> queryRules = new ArrayList<>();

    @Page
    MerchandisingActions merchandisingActions;

    @Page
    CommercePageActions searchPageActions;

    @Page
    BannerActions bannerActions;

    public String query;

    @FileToTest(value = "/consoleTestData/redirect.json")
    @Test(description = "Verifies the creation, editing, and deletion of a search redirect.", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"sanity"})
    public void createEditDeleteRedirectTest(Object jsonObject) throws InterruptedException {
        JsonObject redirectData=(JsonObject) jsonObject;
        query=redirectData.get("query").getAsString();
        goTo(searchPage);
        searchPage.threadWait();
        searchPageActions.awaitForPageToLoad();
        merchandisingActions.goToSection(UnbxdEnum.REDIRECT);
        ThreadWait();

        //creating redirect
        createPromotion(query,false,false);
        String redirectUrl=redirectData.get("redirect").getAsString();
        String editRedirectUrl=redirectData.get("editRedirect").getAsString();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("redirect.json");
        searchPageActions.fillQueryRuleData(query,null);
        merchandisingActions.fillCampaignData(campaignData);
        bannerActions.fillRedirectURL(redirectUrl);
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);


        //Edit the redirect
        merchandisingActions.goToSection(UnbxdEnum.REDIRECT);
        searchPageActions.selectActionType(UnbxdEnum.EDIT,query);
        bannerActions.fillRedirectURL(editRedirectUrl);
        bannerActions.findFirst("body").click();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        searchPageActions.selectActionType(UnbxdEnum.PREVIEW,query);
        ThreadWait();
        Assert.assertEquals(searchPageActions.redirectPreview.getValue(),editRedirectUrl,"REDIRECT URL IS NOT SAME AS GIVEN");

        searchPage.threadWait();
        goTo(searchPage);
        merchandisingActions.goToSection(UnbxdEnum.REDIRECT);
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();
    }



//    @AfterClass(alwaysRun = true,groups={"sanity"})
//    public void deleteCreatedRules() throws InterruptedException {
//        goTo(searchPage);
//        merchandisingActions.goToSection(UnbxdEnum.REDIRECT);
//        for(String queryRule: queryRules)
//        {
//            if(searchPage.queryRuleByName(query)!=null)
//            {
//                searchPageActions.deleteQueryRule(queryRule);
//                Assert.assertNull(searchPage.queryRuleByName(queryRule),"CREATED QUERY RULE IS NOT DELETED");
//                getDriver().navigate().refresh();
//                ThreadWait();
//
//
//            }
//        }
//    }
}


