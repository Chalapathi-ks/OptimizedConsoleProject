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
import org.fluentlenium.core.domain.FluentWebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        searchPage.await();
        createPromotion(query, false, false);

        JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("boosting.json");

        merchandisingActions.fillCampaignDataforUpcoming(campaignData);
        merchandisingActions.awaitForElementPresence(merchandisingActions.calendarIcon);
        // Find calendar icon and click it
        if (merchandisingActions.calendarIcon.isDisplayed()) {
            merchandisingActions.scrollUntilVisible(merchandisingActions.calendarIcon);
            merchandisingActions.awaitForElementPresence(merchandisingActions.calendarIcon);
            merchandisingActions.await();
            // Use safeClick to handle click interception
            try {
                merchandisingActions.waitForElementToBeClickable(merchandisingActions.calendarIcon, "Calendar icon");
                merchandisingActions.calendarIcon.click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                ((org.openqa.selenium.JavascriptExecutor) merchandisingActions.getDriver()).executeScript("arguments[0].click();", merchandisingActions.calendarIcon.getElement());
            }
            merchandisingActions.await();
            merchandisingActions.upcomingDateSelection();
            merchandisingActions.await();
            merchandisingActions.scrollUntilVisible(merchandisingActions.timezoneDropdown);
            merchandisingActions.awaitForElementPresence(merchandisingActions.timezoneDropdown);
            merchandisingActions.await();
            merchandisingActions.timeZoneSelection();

            if (merchandisingActions.calenderApplyButton.isDisplayed()) {
                // Scroll until apply button is visible and wait for it
                merchandisingActions.scrollUntilVisible(merchandisingActions.calenderApplyButton);
                merchandisingActions.awaitForElementPresence(merchandisingActions.calenderApplyButton);
                merchandisingActions.await();
                try {
                    merchandisingActions.waitForElementToBeClickable(merchandisingActions.calenderApplyButton, "Calendar apply button");
                    merchandisingActions.calenderApplyButton.click();
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    ((org.openqa.selenium.JavascriptExecutor) merchandisingActions.getDriver()).executeScript("arguments[0].click();", merchandisingActions.calenderApplyButton.getElement());
                }
                merchandisingActions.await();
                merchandisingActions.clickonNext();
                merchandisingActions.await();

                    merchandisingActions.goToLandingPage();
                    merchandisingActions.publishCampaign();
                    merchandisingActions.verifySuccessMessage();
                    merchandisingActions.await();
                    Assert.assertNotNull(searchPage.queryRuleByName(query));
                    queryRules.add(query);
                    merchandisingActions.await();
                    Assert.assertTrue(merchandisingActions.upcomingStatus.isDisplayed(), "SEARCH: PROMOTION RULE IS NOT IN UPCOMING STATE");

                goTo(searchPage);
                searchPage.await();
                searchPage.queryRuleByName(query);
                searchPage.await();
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
        searchPage.await();
        createPromotion(query, false, false);

        JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("boosting.json");

        // goTo(searchPage);
        merchandisingActions.fillCampaignData(campaignData);
        merchandisingActions.await();
        merchandisingActions.goToLandingPage();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        merchandisingActions.await();
        // Navigate back to search page to see the status badge
        goTo(searchPage);
        searchPage.await();
        searchPage.awaitForPageToLoad();
        merchandisingActions.await();
        FluentWebElement ruleElement = searchPage.queryRuleByName(query);
        Assert.assertNotNull(ruleElement, "Promotion rule not found: " + query);
        queryRules.add(query);
        // Wait for the active status badge to appear
        searchPage.await();
        merchandisingActions.await();
        // Wait for status badge to appear (with retry logic)
        merchandisingActions.awaitTillElementDisplayed(merchandisingActions.activeStatus);
        Assert.assertTrue(merchandisingActions.activeStatus.isDisplayed(), "SEARCH: PROMOTION RULE IS NOT IN ACTIVE STATE");

        //Stopped the rule
        searchPageActions.selectActionType(UnbxdEnum.MORE, query);
        searchPageActions.selectActionFromMore(UnbxdEnum.STOPPED, query);
        searchPageActions.selectModelWindow();
        Assert.assertTrue(searchPageActions.checkSuccessMessage(), SUCCESS_MESSAGE_FAILURE);
        merchandisingActions.await();
        Assert.assertTrue(searchPageActions.stopCampaign.isDisplayed(), "SEARCH: PROMOTION RULE IS NOT IN STOPPED STATE");

        goTo(searchPage);
        searchPage.await();
        searchPage.queryRuleByName(query);
        merchandisingActions.await();
        searchPageActions.deleteQueryRule(query);
        searchPageActions.awaitForSuccessToastPresence();
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

    }


    @FileToTest(value = "/consoleTestData/boosting.json")
    @Test(description = "SEARCH: Creates and verifies the campaign creation with Boost for Search Campaigns", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void promotionStoppedDuplicateTest(Object jsonObject) throws InterruptedException {
        String conditionType = "boost";
        JsonObject boostJsonObject = (JsonObject) jsonObject;
        query = boostJsonObject.get("query").getAsString();

        goTo(searchPage);
        searchPage.await();
        createPromotion(query, false, false);

        JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("boosting.json");

        // goTo(searchPage);
        merchandisingActions.fillCampaignData(campaignData);
        merchandisingActions.await();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);

        fillMerchandisingData(object, UnbxdEnum.BOOST, false);
        merchandisingActions.clickOnApplyButton();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        merchandisingActions.await();
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
        searchPageActions.awaitForElementPresence(searchPageActions.menuIcon);
        searchPageActions.selectActionType(UnbxdEnum.MORE,query);
        searchPageActions.selectActionFromMore(UnbxdEnum.DUPLICATE,query);
        searchPageActions.awaitForPageToLoad();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getConditionSize();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, object.size(), "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);
        merchandisingActions.awaitForElementPresence(merchandisingActions.publishButton);
        click(merchandisingActions.publishButton);
        merchandisingActions.await();
        searchPage.queryRuleByName(query);
        merchandisingActions.campaignPromotions.getText().contains("copy");
        Assert.assertTrue(merchandisingActions.activeStatus.isDisplayed(),"SEARCH: PROMOTION RULE IS NOT IN ACTIVE STATE");

        goTo(searchPage);
        searchPage.await();
        searchPage.queryRuleByName(query);
        merchandisingActions.await();
        searchPageActions.deleteQueryRule(query);
        searchPageActions.awaitForSuccessToastPresence();
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

        searchPage.await();
        searchPageActions.deleteQueryRule(query);
        searchPageActions.awaitForSuccessToastPresence();
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
        searchPage.await();
        createBrowsePromotion(page,false,false);
        JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("browseUpcomingStatusTest.json");

        searchPageActions.fillPageName(object);
        merchandisingActions.nextPage();
        merchandisingActions.fillCampaignDataforUpcoming(campaignData);
        merchandisingActions.awaitForElementPresence(merchandisingActions.calendarIcon);
        if (merchandisingActions.calendarIcon.isDisplayed()) {
            searchPageActions.await();
            merchandisingActions.scrollUntilVisible(merchandisingActions.calendarIcon);
            merchandisingActions.awaitForElementPresence(merchandisingActions.calendarIcon);
            merchandisingActions.await();
            merchandisingActions.waitForElementToBeClickable(merchandisingActions.calendarIcon, "Calendar icon");
            merchandisingActions.safeClick(merchandisingActions.calendarIcon);
            merchandisingActions.await();
            merchandisingActions.upcomingDateSelection();
            // Scroll until timezone is visible and wait for it
            merchandisingActions.scrollUntilVisible(merchandisingActions.timezoneDropdown);
            merchandisingActions.awaitForElementPresence(merchandisingActions.timezoneDropdown);
            merchandisingActions.await();
            merchandisingActions.timeZoneSelection();

            if (merchandisingActions.calenderApplyButton.isDisplayed()) {
                merchandisingActions.scrollUntilVisible(merchandisingActions.calenderApplyButton);
                merchandisingActions.awaitForElementPresence(merchandisingActions.calenderApplyButton);
                merchandisingActions.await();
                merchandisingActions.waitForElementToBeClickable(merchandisingActions.calenderApplyButton, "Calendar apply button");
                merchandisingActions.safeClick(merchandisingActions.calenderApplyButton);
                merchandisingActions.await();
                merchandisingActions.clickonNext();

                merchandisingActions.goToLandingPage();
                merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
                fillMerchandisingData(object,UnbxdEnum.BOOST,false);
                merchandisingActions.clickOnApplyButton();
                merchandisingActions.publishCampaign();
                merchandisingActions.verifySuccessMessage();
                merchandisingActions.await();
                Assert.assertNotNull(searchPage.queryRuleByName(page));
                pageRules.add(page);
                merchandisingActions.await();
                Assert.assertTrue(merchandisingActions.upcomingStatus.isDisplayed(), "SEARCH: PROMOTION RULE IS NOT IN UPCOMING STATE");

                goTo(browsePage);
                searchPage.await();
                searchPage.queryRuleByName(page);
                searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

                merchandisingActions.await();
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
        searchPage.await();
        createPromotion(page, false, false);

        JsonArray object = boostJsonObject.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("browsePromotionDuplicateTest.json");

        searchPageActions.fillPageName(object);
        merchandisingActions.nextPage();
        merchandisingActions.fillCampaignData(campaignData);
        merchandisingActions.await();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);

        fillMerchandisingData(object, UnbxdEnum.BOOST, false);
        merchandisingActions.clickOnApplyButton();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        merchandisingActions.await();
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
        searchPageActions.awaitForElementPresence(searchPageActions.menuIcon);
        searchPageActions.selectActionType(UnbxdEnum.MORE,page);
        searchPageActions.selectActionFromMore(UnbxdEnum.DUPLICATE,page);
        searchPageActions.awaitForPageToLoad();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getConditionSize();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, object.size(), "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);
        merchandisingActions.awaitForElementPresence(merchandisingActions.publishButton);
        click(merchandisingActions.publishButton);
        merchandisingActions.await();
        searchPage.queryRuleByName(page);
        merchandisingActions.campaignPromotions.getText().contains("copy");
        Assert.assertTrue(merchandisingActions.activeStatus.isDisplayed(),"SEARCH: PROMOTION RULE IS NOT IN ACTIVE STATE");

        goTo(browsePage);
        searchPage.await();
        searchPage.queryRuleByName(page);
        merchandisingActions.await();
        searchPageActions.deleteQueryRule(page);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

        searchPage.await();
        searchPageActions.deleteQueryRule(page);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.await();

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
//                merchandisingActions.await();
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
//              merchandisingActions.await();
//
//          }
//        }
//    }
}


