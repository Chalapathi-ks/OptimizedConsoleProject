package UnbxdTests.testNG.consoleui.MerchConditionTest;

import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.page.BrowsePage;
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

public class  PinningTest extends MerchandisingTest {

    List<String> queryRules = new ArrayList<>();

    List<String> pageRules = new ArrayList<>();

    @Page
    CommercePageActions searchPageActions;

    private String query;

    private String pin;
    String conditionType = "Pinned";

    @Page
    BrowsePage browsePage;

    public String page;

    @FileToTest(value = "/consoleTestData/Pinning.json")
    @Test(description = "SEARCH: This test verifies the creation with Pinning for Search Campaigns", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"merchandising","sanity"})
    public void pinningCreateTest(Object jsonObject) throws InterruptedException
    {
        JsonObject PinningJsonObject = (JsonObject) jsonObject;
        query = PinningJsonObject.get("query").getAsString();

        goTo(searchPage);
        ThreadWait();
        createPromotion(query,false,false);

        JsonArray object = PinningJsonObject.get("data").getAsJsonArray();
        String pinningPosition=PinningJsonObject.get("pinningPosition").getAsString();
        String updatedPinningPosition=PinningJsonObject.get("updatedPinningPosition").getAsString();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("Pinning.json");

        // goTo(searchPage);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        ThreadWait();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.PIN);
        fillPinSortMerchandisingData(object, UnbxdEnum.PIN);
        ThreadWait();
        merchandisingActions.clickOnApplyButton();
        ThreadWait();
        ThreadWait();
        // Wait for pinned position to appear (remote/Grid can be slower than local; poll up to 45s)
        boolean foundPinnedAtPosition = false;
        for (int i = 0; i < 45; i++) {
            merchandisingActions.await();
            if (merchandisingActions.pinnedProductIndex.size() > 0
                    && pinningPosition.equals(merchandisingActions.pinnedProductIndex.get(0).getText().trim())) {
                foundPinnedAtPosition = true;
                break;
            }
            try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); break; }
        }
        Assert.assertTrue(merchandisingActions.pinnedProductIndex.size() > 0, "PRODUCT IS NOT PINNED AT THE GIVEN POSITION: pinned list empty");
        Assert.assertTrue(foundPinnedAtPosition, "PRODUCT IS NOT PINNED AT THE GIVEN POSITION: " + pinningPosition + " (waited 45s for remote)");
        String actualPosition = null;
        for (int r = 0; r < 3; r++) {
            if (merchandisingActions.pinnedProductIndex.size() > 0) {
                try { actualPosition = merchandisingActions.pinnedProductIndex.get(0).getText().trim(); break; } catch (IndexOutOfBoundsException e) { merchandisingActions.await(); }
            }
            if (r < 2) try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        Assert.assertNotNull(actualPosition, "PRODUCT IS NOT PINNED AT THE GIVEN POSITION: could not read pinned index");
        Assert.assertEquals(actualPosition, pinningPosition, "PRODUCT IS NOT PINNED AT THE GIVEN POSITION");
        Assert.assertTrue(merchandisingActions.pinnedProductText.isDisplayed(),"PINNED TEXT IS NOT PRESENT AT THE GIVEN POSITION");

        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        merchandisingActions.awaitForPageToLoad();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);


        merchandisingActions.openPreviewAndSwitchTheTab();
        merchandisingActions.awaitForPageToLoad();
        ThreadWait();
        long deadline = System.currentTimeMillis() + 20000;
        while (System.currentTimeMillis() < deadline && !driver.getCurrentUrl().contains("preview")) {
            ThreadWait();
        }
        String previewPage = driver.getCurrentUrl();
        Assert.assertTrue(previewPage.contains("preview"),"Not redirecting to preview page");
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        merchandisingActions.awaitForElementPresence(merchandisingActions.showingResultinPreview);
        ThreadWait();
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(query));

        merchandisingActions.ClickViewHideInsight();
        merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
        merchandisingActions.MerchandisingStrategy.isDisplayed();
        verifyMerchandisingData(object, UnbxdEnum.PIN,false);

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        searchPageActions.selectActionType(UnbxdEnum.EDIT, query);
        ThreadWait();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getSortPinConditionSize();
        ThreadWait();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, 1, "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROOUP IS" + group);


        searchPage.scrollToBottom();
        merchandisingActions.scrollUntilVisible(merchandisingActions.MerchandisingStrategyEditButton);
        merchandisingActions.waitForElementToBeClickable(merchandisingActions.MerchandisingStrategyEditButton, "Edit");
        merchandisingActions.clickUsingJS(merchandisingActions.MerchandisingStrategyEditButton);
        fillupdatedPinSortMerchandisingData(object, UnbxdEnum.PIN);
        merchandisingActions.clickOnApplyButton();
        searchPage.threadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();

        //Preview
        merchandisingActions.openPreviewAndSwitchTheTab();
        merchandisingActions.awaitForPageToLoad();
        ThreadWait();
        long deadline2 = System.currentTimeMillis() + 20000;
        while (System.currentTimeMillis() < deadline2 && !driver.getCurrentUrl().contains("preview")) {
            ThreadWait();
        }
        String previewpage = driver.getCurrentUrl();
        ThreadWait();
        Assert.assertTrue(previewpage.contains("preview"),"Not redirecting to preview page");
        merchandisingActions.awaitForElementPresence(merchandisingActions.SearchpreviewOption);
        merchandisingActions.awaitForElementPresence(merchandisingActions.showingResultinPreview);
        Assert.assertTrue(merchandisingActions.showingResultinPreview.getText().contains(query));

        merchandisingActions.ClickViewHideInsight();
        merchandisingActions.awaitForElementPresence(merchandisingActions.inSighttitle);
        merchandisingActions.MerchandisingStrategy.isDisplayed();
        verifyMerchandisingData(object, UnbxdEnum.PIN,true);


        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);

        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, query);
        ThreadWait();
        String updatedcondition = searchPageActions.getConditionTitle();
        Assert.assertTrue(updatedcondition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertTrue(merchandisingActions.previewSummary.isDisplayed());
        ThreadWait();
        Assert.assertEquals(merchandisingActions.pinnedProductIndex.get(0).getText(),updatedPinningPosition,"PRODUCT IS NOT PINNED AT THE GIVEN POSITION");
        Assert.assertTrue(merchandisingActions.pinnedProductText.isDisplayed(),"PINNED TEXT IS NOT PRESENT AT THE GIVEN POSITION");

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        ThreadWait();
        searchPageActions.deleteQueryRule(query);
        searchPageActions.awaitForSuccessToastPresence();
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
//
//        }
//        goTo(browsePage);
//
//        for(String pageRule: pageRules)
//        {
//            if(searchPage.queryRuleByName(pageRule)!=null)
//            {
//                searchPageActions.deleteQueryRule(pageRule);
//                Assert.assertNull(searchPage.queryRuleByName(pageRule),"BROWSE RULE : CREATED PAGE RULE IS NOT DELETED");
//                getDriver().navigate().refresh();
//                ThreadWait();
//
//            }
//
//        }
//    }

}
