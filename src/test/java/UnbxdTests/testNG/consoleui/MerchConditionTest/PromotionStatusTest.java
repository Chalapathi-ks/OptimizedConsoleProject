package UnbxdTests.testNG.consoleui.MerchConditionTest;


import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.page.BrowsePage;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import lib.compat.Page;
import lib.compat.FluentWebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.ui.page.UiBase.ThreadWait;
import static lib.constants.UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE;

public class PromotionStatusTest extends MerchandisingTest {

    String query;

    List<String> queryRules = new ArrayList<>();
    List<String> pageRules = new ArrayList<>();

    @Page
    CommercePageActions searchPageActions;

    @Page
    BrowsePage browsePage;

    public String page;


    @FileToTest(value = "/consoleTestData/boosting.json")
    @Test(description = "SEARCH: Creates and verifies the campaign creation with Boost for Search Campaigns", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void UpcomingStatusTest(Object jsonObject) throws Exception {
        String conditionType = "boost";
        JsonObject boostJsonObject = (JsonObject) jsonObject;
        query = boostJsonObject.get("query").getAsString();

        goTo(searchPage);
        searchPage.threadWait();
        createPromotion(query, false, false);

        JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("boosting.json");

        merchandisingActions.fillCampaignDataforUpcoming(campaignData);
        Thread.sleep(1000);
        // Find calendar icon and click it
        if (merchandisingActions.calendarIcon.isDisplayed()) {
            // Scroll until calendar icon is visible and wait for it
            merchandisingActions.scrollUntilVisible(merchandisingActions.calendarIcon);
            merchandisingActions.awaitForElementPresence(merchandisingActions.calendarIcon);
            ThreadWait();
            // Use safeClick to handle click interception
            try {
                merchandisingActions.waitForElementToBeClickable(merchandisingActions.calendarIcon, "Calendar icon");
            merchandisingActions.calendarIcon.click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                // Fallback to JavaScript click if regular click is intercepted
                ((org.openqa.selenium.JavascriptExecutor) merchandisingActions.getDriver()).executeScript("arguments[0].click();", merchandisingActions.calendarIcon.getElement());
            }
            ThreadWait();
            merchandisingActions.upcomingDateSelection();
            ThreadWait();
            // Scroll until timezone is visible and wait for it
            merchandisingActions.scrollUntilVisible(merchandisingActions.timezoneDropdown);
            merchandisingActions.awaitForElementPresence(merchandisingActions.timezoneDropdown);
            ThreadWait();
            merchandisingActions.timeZoneSelection();

            if (merchandisingActions.calenderApplyButton.isDisplayed()) {
                // Scroll until apply button is visible and wait for it
                merchandisingActions.scrollUntilVisible(merchandisingActions.calenderApplyButton);
                merchandisingActions.awaitForElementPresence(merchandisingActions.calenderApplyButton);
                ThreadWait();
                // Use safeClick to handle click interception
                try {
                    merchandisingActions.waitForElementToBeClickable(merchandisingActions.calenderApplyButton, "Calendar apply button");
                    merchandisingActions.calenderApplyButton.click();
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    // Fallback to JavaScript click if regular click is intercepted
                    ((org.openqa.selenium.JavascriptExecutor) merchandisingActions.getDriver()).executeScript("arguments[0].click();", merchandisingActions.calenderApplyButton.getElement());
                }
                ThreadWait();
                    merchandisingActions.clickonNext();
                    ThreadWait();

                    merchandisingActions.goToLandingPage();
                    merchandisingActions.publishCampaign();
                    merchandisingActions.verifySuccessMessage();
                    ThreadWait();
                    Assert.assertNotNull(searchPage.queryRuleByName(query));
                    queryRules.add(query);
                    ThreadWait();
                    Assert.assertTrue(merchandisingActions.upcomingStatus.isDisplayed(), "SEARCH: PROMOTION RULE IS NOT IN UPCOMING STATE");

                goTo(searchPage);
                searchPage.threadWait();
                searchPage.queryRuleByName(query);
                ThreadWait();
                searchPageActions.deleteQueryRule(query);
                searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);


            }

            }
        }



    @FileToTest(value = "/consoleTestData/boosting.json")
    @Test(description = "SEARCH: Creates and verifies the campaign creation with Boost for Search Campaigns", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void promotionActiveAndStopStatusTest(Object jsonObject) throws InterruptedException {

        JsonObject boostJsonObject = (JsonObject) jsonObject;
        query = boostJsonObject.get("query").getAsString();

        goTo(searchPage);
        searchPage.threadWait();
        createPromotion(query, false, false);

        JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("boosting.json");

        // goTo(searchPage);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        // Navigate back to search page to see the status badge
        goTo(searchPage);
        searchPage.threadWait();
        searchPage.awaitForPageToLoad();
        ThreadWait();
        FluentWebElement ruleElement = searchPage.queryRuleByName(query);
        Assert.assertNotNull(ruleElement, "Promotion rule not found: " + query);
        queryRules.add(query);
        // Wait for the active status badge to appear
        searchPage.threadWait();
        ThreadWait();
        // Wait for status badge to appear (with retry logic)
        merchandisingActions.awaitTillElementDisplayed(merchandisingActions.activeStatus);
        Assert.assertTrue(merchandisingActions.activeStatus.isDisplayed(), "SEARCH: PROMOTION RULE IS NOT IN ACTIVE STATE");

        //Stopped the rule
        searchPageActions.selectActionType(UnbxdEnum.MORE, query);
        searchPageActions.selectActionFromMore(UnbxdEnum.STOPPED, query);
        searchPageActions.selectModelWindow();
        Assert.assertTrue(searchPageActions.checkSuccessMessage(), SUCCESS_MESSAGE_FAILURE);
        merchandisingActions.awaitTillElementDisplayed(searchPageActions.stopCampaign);
        Assert.assertTrue(searchPageActions.stopCampaign.isDisplayed(), "SEARCH: PROMOTION RULE IS NOT IN STOPPED STATE");

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        ThreadWait();
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

    }


    @FileToTest(value = "/consoleTestData/boosting.json")
    @Test(description = "SEARCH: Creates and verifies the campaign creation with Boost for Search Campaigns", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void promotionStoppedDuplicateTest(Object jsonObject) throws InterruptedException {
        String conditionType = "boost";
        JsonObject boostJsonObject = (JsonObject) jsonObject;
        query = boostJsonObject.get("query").getAsString();

        goTo(searchPage);
        searchPage.threadWait();
        createPromotion(query, false, false);

        JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("boosting.json");

        // goTo(searchPage);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);

        fillMerchandisingData(object, UnbxdEnum.BOOST, false);
        merchandisingActions.clickOnApplyButton();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        Assert.assertTrue(merchandisingActions.activeStatus.isDisplayed(),"SEARCH: PROMOTION RULE IS NOT IN ACTIVE STATE");

        //Stopped the rule
        searchPageActions.selectActionType(UnbxdEnum.MORE,query);
        searchPageActions.selectActionFromMore(UnbxdEnum.STOPPED,query);
        searchPageActions.selectModelWindow();
        Assert.assertTrue(searchPageActions.checkSuccessMessage(), SUCCESS_MESSAGE_FAILURE);
        merchandisingActions.awaitTillElementDisplayed(searchPageActions.stopCampaign);
        Assert.assertTrue(searchPageActions.stopCampaign.isDisplayed(),"SEARCH: PROMOTION RULE IS NOT IN STOPPED STATE");

        //Duplicate the rule
        searchPageActions.awaitForPageToLoad();
        searchPageActions.selectActionType(UnbxdEnum.MORE,query);
        searchPageActions.selectActionFromMore(UnbxdEnum.DUPLICATE,query);
        searchPageActions.awaitForPageToLoad();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getConditionSize();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, object.size(), "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);
        merchandisingActions.awaitForElementPresence(merchandisingActions.publishButton);
        click(merchandisingActions.publishButton);
        ThreadWait();
        searchPage.queryRuleByName(query);
        merchandisingActions.campaignPromotions.getText().contains("copy");
        Assert.assertTrue(merchandisingActions.activeStatus.isDisplayed(),"SEARCH: PROMOTION RULE IS NOT IN ACTIVE STATE");

        goTo(searchPage);
        searchPage.threadWait();
        searchPage.queryRuleByName(query);
        ThreadWait();
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

        searchPage.threadWait();
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);


    }



    //TestCases for browse
    @FileToTest(value = "/consoleTestData/browseUpcomingStatusTest.json")
    @Test(description = "SEARCH: Creates and verifies the campaign creation with Boost for Search Campaigns", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void browseUpcomingStatusTest(Object jsonObject) throws Exception {

        String conditionType = "boost";
        JsonObject boostJsonObject = (JsonObject) jsonObject;
        page = boostJsonObject.get("page").getAsString();

        goTo(browsePage);
        searchPage.threadWait();
        createBrowsePromotion(page,false,false);
        JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("browseUpcomingStatusTest.json");

        searchPageActions.fillPageName(object);
        merchandisingActions.fillCampaignDataforUpcoming(campaignData);
        Thread.sleep(1000);
        // Find calendar icon and click it
        if (merchandisingActions.calendarIcon.isDisplayed()) {
            searchPageActions.threadWait();
            // Scroll until calendar icon is visible and wait for it
            merchandisingActions.scrollUntilVisible(merchandisingActions.calendarIcon);
            merchandisingActions.awaitForElementPresence(merchandisingActions.calendarIcon);
            ThreadWait();
            // Use safeClick to handle click interception
            try {
                merchandisingActions.waitForElementToBeClickable(merchandisingActions.calendarIcon, "Calendar icon");
            merchandisingActions.calendarIcon.click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                // Fallback to JavaScript click if regular click is intercepted
                ((org.openqa.selenium.JavascriptExecutor) merchandisingActions.getDriver()).executeScript("arguments[0].click();", merchandisingActions.calendarIcon.getElement());
            }
            ThreadWait();
            merchandisingActions.upcomingDateSelection();
            // Scroll until timezone is visible and wait for it
            merchandisingActions.scrollUntilVisible(merchandisingActions.timezoneDropdown);
            merchandisingActions.awaitForElementPresence(merchandisingActions.timezoneDropdown);
            ThreadWait();
            merchandisingActions.timeZoneSelection();

            if (merchandisingActions.calenderApplyButton.isDisplayed()) {
                // Scroll until apply button is visible and wait for it
                merchandisingActions.scrollUntilVisible(merchandisingActions.calenderApplyButton);
                merchandisingActions.awaitForElementPresence(merchandisingActions.calenderApplyButton);
                ThreadWait();
                // Use safeClick to handle click interception
                try {
                    merchandisingActions.waitForElementToBeClickable(merchandisingActions.calenderApplyButton, "Calendar apply button");
                merchandisingActions.calenderApplyButton.click();
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    // Fallback to JavaScript click if regular click is intercepted
                    ((org.openqa.selenium.JavascriptExecutor) merchandisingActions.getDriver()).executeScript("arguments[0].click();", merchandisingActions.calenderApplyButton.getElement());
                }
                ThreadWait();
                merchandisingActions.clickonNext();

                merchandisingActions.goToLandingPage();
                merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
                fillMerchandisingData(object,UnbxdEnum.BOOST,false);
                merchandisingActions.clickOnApplyButton();
                merchandisingActions.publishCampaign();
                merchandisingActions.verifySuccessMessage();
                ThreadWait();
                Assert.assertNotNull(searchPage.queryRuleByName(page));
                pageRules.add(page);
                ThreadWait();
                Assert.assertTrue(merchandisingActions.upcomingStatus.isDisplayed(), "SEARCH: PROMOTION RULE IS NOT IN UPCOMING STATE");

                goTo(browsePage);
                searchPage.threadWait();
                searchPage.queryRuleByName(page);
                searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

                ThreadWait();
                searchPageActions.deleteQueryRule(page);
                searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

            }

        }
    }
    @FileToTest(value = "/consoleTestData/browsePromotionDuplicateTest.json")
    @Test(description = "SEARCH: Creates and verifies the campaign creation with Boost for Search Campaigns", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void browsePromotionDuplicateTest(Object jsonObject) throws InterruptedException {
        String conditionType = "boost";
        JsonObject boostJsonObject = (JsonObject) jsonObject;
        page = boostJsonObject.get("page").getAsString();

        goTo(browsePage);
        searchPage.threadWait();
        createPromotion(page, false, false);

        JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("browsePromotionDuplicateTest.json");

        searchPageActions.fillPageName(object);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);

        fillMerchandisingData(object, UnbxdEnum.BOOST, false);
        merchandisingActions.clickOnApplyButton();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(page));
        pageRules.add(page);
        Assert.assertTrue(merchandisingActions.activeStatus.isDisplayed(),"SEARCH: PROMOTION RULE IS NOT IN ACTIVE STATE");

        //Stopped the rule
        searchPageActions.selectActionType(UnbxdEnum.MORE,page);
        searchPageActions.selectActionFromMore(UnbxdEnum.STOPPED,page);
        searchPageActions.selectModelWindow();
        Assert.assertTrue(searchPageActions.checkSuccessMessage(), SUCCESS_MESSAGE_FAILURE);
        merchandisingActions.awaitTillElementDisplayed(searchPageActions.stopCampaign);
        Assert.assertTrue(searchPageActions.stopCampaign.isDisplayed(),"SEARCH: PROMOTION RULE IS NOT IN STOPPED STATE");

        //Duplicate the rule
        searchPageActions.awaitForPageToLoad();
        searchPageActions.selectActionType(UnbxdEnum.MORE,page);
        searchPageActions.selectActionFromMore(UnbxdEnum.DUPLICATE,page);
        searchPageActions.awaitForPageToLoad();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getConditionSize();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, object.size(), "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);
        merchandisingActions.awaitForElementPresence(merchandisingActions.publishButton);
        click(merchandisingActions.publishButton);
        ThreadWait();
        searchPage.queryRuleByName(page);
        merchandisingActions.campaignPromotions.getText().contains("copy");
        Assert.assertTrue(merchandisingActions.activeStatus.isDisplayed(),"SEARCH: PROMOTION RULE IS NOT IN ACTIVE STATE");

        goTo(browsePage);
        searchPage.threadWait();
        searchPage.queryRuleByName(page);
        ThreadWait();
        searchPageActions.deleteQueryRule(page);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

        searchPage.threadWait();
        searchPageActions.deleteQueryRule(page);
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
//       goTo(browsePage);
//
//        for(String pageRule: pageRules)
//        {
//         if(searchPage.queryRuleByName(pageRule)!=null)
//          {
//            searchPageActions.deleteQueryRule(pageRule);
//            Assert.assertNull(searchPage.queryRuleByName(pageRule),"BROWSE RULE : CREATED PAGE RULE IS NOT DELETED");
//              getDriver().navigate().refresh();
//              ThreadWait();
//
//          }
//        }
//    }
}


