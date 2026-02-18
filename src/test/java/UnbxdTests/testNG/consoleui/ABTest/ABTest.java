package UnbxdTests.testNG.consoleui.ABTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import core.consoleui.actions.ABActions;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.page.BrowsePage;
import static core.ui.page.UiBase.ThreadWait;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;

public class ABTest extends MerchandisingTest {

    String query;

    List<String> queryRules = new ArrayList<>();
    List<String> pageRules = new ArrayList<>();

    @Page
    CommercePageActions searchPageActions;

    @Page
    BrowsePage browsePage;

    @Page
    ABActions abActions;

    public String page;


    @FileToTest(value = "/consoleTestData/ABboost.json")
    @Test(description = "BROWSE AB RULE: Creates and verifies the campaign creation with Boost and AB condition ", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void abCreateAndPreviewTest(Object jsonObject) throws InterruptedException
    {
        String conditionType = "boost";
        JsonObject ABData = (JsonObject) jsonObject;
        page = ABData.get("page").getAsString();
        int VariationA=ABData.get("VariationA").getAsInt();
        int VariationB=ABData.get("VariationB").getAsInt();

        goTo(browsePage);
        searchPage.threadWait();
        createBrowsePromotion(page,false,false);
        JsonArray object = ABData.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("ABboost.json");


        searchPageActions.fillPageName(object);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        ThreadWait();
        abActions.enableABToggle();
        ThreadWait();
        abActions.fillABPercentage(UnbxdEnum.VARIATIONA, VariationA);
        abActions.scrollToBottomOfRuleContent();
        ThreadWait();
        abActions.fillABPercentage(UnbxdEnum.VARIATIONB, VariationB);
        ThreadWait();
        String winnerDecidedBy = ABData.get("winnerDecidedBy").getAsString();
        abActions.selectWinnerDecidedByabData(winnerDecidedBy);
        String winner=abActions.getWinnerDecidedByValue();
        ThreadWait();
        abActions.selectPreferredOptionABData();
        String PreferredOption=abActions.getPreferredOptionABData();
        click(abActions.abConditionApplyButton);

        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
        fillMerchandisingData(object,UnbxdEnum.BOOST,false);
        merchandisingActions.clickOnApplyButton();

        abActions.selectABConfiguration(UnbxdEnum.VARIATIONB);
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
        fillMerchandisingData(object,UnbxdEnum.BOOST,false);
        merchandisingActions.clickOnApplyButton();

        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(page));
        pageRules.add(page);
        ThreadWait();

        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, page);
        ThreadWait();
        merchandisingActions.scrollUntilVisible(searchPageActions.conditionTitle);
        ThreadWait();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getConditionSize();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType) || condition.equalsIgnoreCase("ab"),
            "BROWSE: SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + condition + " expected boost or AB");
        Assert.assertEquals(group, object.size(), "BROWSE: NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);

        abActions.selectABConfiguration(UnbxdEnum.VARIATIONB);
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType) || condition.equalsIgnoreCase("ab"),
            "BROWSE: SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + condition + " expected boost or AB");
        Assert.assertEquals(group, object.size(), "BROWSE: NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);

//        click(abActions.viewMore);
//        Assert.assertTrue(abActions.abPreviewSummary.getText().contains(winner));
//        Assert.assertTrue(abActions.abPreviewSummary.getText().contains(PreferredOption));
//
//        Assert.assertTrue(abActions.percentageVariantASummary.getText().contains(String.valueOf(VariationA)));
//        Assert.assertTrue(abActions.percentageVariantBSummary.getText().contains(String.valueOf(VariationB)));
        searchPage.threadWait();
        goTo(browsePage);
        searchPage.threadWait();
        searchPageActions.deleteQueryRule(page);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }

    @FileToTest(value = "/consoleTestData/ABUpcomingBoost.json")
    @Test(description = "BROWSE AB RULE: Creates and verifies the campaign creation with Boost and AB condition ", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void abUpcomingCreateAndEditTest(Object jsonObject) throws InterruptedException
    {
        String conditionType = "boost";
        JsonObject ABData = (JsonObject) jsonObject;
        page = ABData.get("page").getAsString()+System.currentTimeMillis();
        int VariationA=ABData.get("VariationA").getAsInt();
        int VariationB=ABData.get("VariationB").getAsInt();

        goTo(browsePage);
        searchPage.threadWait();
        ThreadWait();
        createBrowsePromotion(page,false,false);
        JsonArray object = ABData.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("ABUpcomingboosting.json");

        searchPageActions.pageRuleDropdown.click();
        searchPageActions.threadWait();
        searchPageActions.awaitForElementPresence(searchPageActions.BuildPath);
        searchPageActions.BuildPath.click();
        searchPageActions.SelectedCategoryPathDisplay.fill().with(page);
        searchPageActions.threadWait();
        searchPageActions.categorypathApplyButton.click();
        searchPageActions.threadWait();
        merchandisingActions.fillCampaignDataforUpcoming(campaignData);
        ThreadWait();
        // Find calendar icon and click it
        if (merchandisingActions.calendarIcon.isDisplayed()) {
            searchPageActions.threadWait();
            // First scroll down to ensure page content is loaded
            merchandisingActions.scrollDown();
            ThreadWait();
            // Scroll until calendar icon is visible using JavaScript for better reliability
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) merchandisingActions.getDriver();
            js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", merchandisingActions.calendarIcon.getElement());
            ThreadWait();
            merchandisingActions.awaitForElementPresence(merchandisingActions.calendarIcon);
            ThreadWait();
            // Use safeClick to handle click interception
            try {
                merchandisingActions.waitForElementToBeClickable(merchandisingActions.calendarIcon, "Calendar icon");
            merchandisingActions.calendarIcon.click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                // Fallback to JavaScript click if regular click is intercepted
                js.executeScript("arguments[0].click();", merchandisingActions.calendarIcon.getElement());
            }
            ThreadWait();
            merchandisingActions.upcomingDateSelection();
            ThreadWait();
            // Scroll until timezone is visible using JavaScript for better reliability
            js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", merchandisingActions.timezoneDropdown.getElement());
            ThreadWait();
            merchandisingActions.awaitForElementPresence(merchandisingActions.timezoneDropdown);
            ThreadWait();
            merchandisingActions.timeZoneSelection();}

            if (merchandisingActions.calenderApplyButton.isDisplayed()) {
                // Scroll until apply button is visible using JavaScript for better reliability
                org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) merchandisingActions.getDriver();
                js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", merchandisingActions.calenderApplyButton.getElement());
                ThreadWait();
                merchandisingActions.awaitForElementPresence(merchandisingActions.calenderApplyButton);
                ThreadWait();
                // Use safeClick to handle click interception
                try {
                    merchandisingActions.waitForElementToBeClickable(merchandisingActions.calenderApplyButton, "Calendar apply button");
                    merchandisingActions.calenderApplyButton.click();
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    // Fallback to JavaScript click if regular click is intercepted
                    js.executeScript("arguments[0].click();", merchandisingActions.calenderApplyButton.getElement());
                }
                ThreadWait();
            }
                merchandisingActions.clickonNext();

        merchandisingActions.goToLandingPage();
        ThreadWait();
        abActions.enableABToggle();
        ThreadWait();
        abActions.fillABPercentage(UnbxdEnum.VARIATIONA, VariationA);
        ThreadWait();
        abActions.scrollToBottomOfRuleContent();
        ThreadWait();
        abActions.fillABPercentage(UnbxdEnum.VARIATIONB, VariationB);
        String winnerDecidedBy = ABData.get("winnerDecidedBy").getAsString();
        abActions.selectWinnerDecidedByabData(winnerDecidedBy);
        ThreadWait();
        String winner=abActions.getWinnerDecidedByValue();
        abActions.scrollToBottomOfRuleContent();
        abActions.selectPreferredOptionABData();
        String PreferredOption=abActions.getPreferredOptionABData();
        click(abActions.abConditionApplyButton);
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
        fillMerchandisingData(object,UnbxdEnum.BOOST,false);
        merchandisingActions.clickOnApplyButton();

        abActions.selectABConfiguration(UnbxdEnum.VARIATIONB);
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
        fillMerchandisingData(object,UnbxdEnum.BOOST,false);
        ThreadWait();
        merchandisingActions.clickOnApplyButton();

        ThreadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(page));
        pageRules.add(page);
        ThreadWait();

        searchPageActions.selectActionType(UnbxdEnum.EDIT, page);
        ThreadWait();
        merchandisingActions.scrollUntilVisible(searchPageActions.conditionTitle);
        ThreadWait();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getConditionSize();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, object.size(), "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);

        abActions.selectABConfiguration(UnbxdEnum.VARIATIONB);
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "BROWSE: SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, object.size(), "BROWSE: NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);
        abActions.scrollToBottomOfRuleContent();
        // merchandisingActions.MerchandisingStrategyEditButton.click();
        // fillMerchandisingData(object,UnbxdEnum.BOOST,true);
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.FILTER);
        fillMerchandisingData(object, UnbxdEnum.FILTER,false);
        merchandisingActions.clickOnApplyButton();
        abActions.selectABConfiguration(UnbxdEnum.VARIATIONA);
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.FILTER);
        fillMerchandisingData(object, UnbxdEnum.FILTER,false);
        merchandisingActions.clickOnApplyButton();
        searchPage.threadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();

        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, page);
        ThreadWait();
        merchandisingActions.scrollUntilVisible(searchPageActions.conditionTitle);
        ThreadWait();
        String Fcondition = searchPageActions.getConditionTitle();
        Assert.assertTrue(Fcondition.equalsIgnoreCase(conditionType), "BROWSE: SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);

        abActions.selectABConfiguration(UnbxdEnum.VARIATIONB);
        ThreadWait();
        merchandisingActions.scrollUntilVisible(searchPageActions.conditionTitle);
        ThreadWait();
        String Filtercondition = searchPageActions.getConditionTitle();
        Assert.assertTrue(Filtercondition.equalsIgnoreCase(conditionType), "BROWSE: SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);


//        click(abActions.viewMore);
//        Assert.assertTrue(abActions.abPreviewSummary.getText().contains(winner));
//        Assert.assertTrue(abActions.abPreviewSummary.getText().contains(PreferredOption));
//
//        Assert.assertTrue(abActions.percentageVariantASummary.getText().contains(String.valueOf(VariationA)));
//        Assert.assertTrue(abActions.percentageVariantBSummary.getText().contains(String.valueOf(VariationB)));
        searchPage.threadWait();
        goTo(searchPage);
        searchPage.threadWait();
        searchPageActions.deleteQueryRule(page);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();
    }

    //Search test cases
    @FileToTest(value = "/consoleTestData/ABsearchBoost.json")
    @Test(description = "SEARCH PAGE AB RULE: Creates and verifies the campaign creation with Boost and AB condition ", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void abCreateAndPreviewTestForSearch(Object jsonObject) throws InterruptedException
    {
        String conditionType = "boost";
        JsonObject ABData = (JsonObject) jsonObject;
        query = ABData.get("query").getAsString();
        int VariationA=ABData.get("VariationA").getAsInt();
        int VariationB=ABData.get("VariationB").getAsInt();

        goTo(searchPage);
        searchPage.threadWait();
        createPromotion(query,false,false);
        JsonArray object = ABData.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("ABsearchBoost.json");

        merchandisingActions.fillCampaignData(campaignData);
        merchandisingActions.goToLandingPage();

        abActions.enableABToggle();
        ThreadWait();
        abActions.fillABPercentage(UnbxdEnum.VARIATIONA, VariationA);
        abActions.scrollToBottomOfRuleContent();
        ThreadWait();
        abActions.fillABPercentage(UnbxdEnum.VARIATIONB, VariationB);
        String winnerDecidedBy = ABData.get("winnerDecidedBy").getAsString();
        abActions.selectWinnerDecidedByabData(winnerDecidedBy);
        ThreadWait();
        String winner=abActions.getWinnerDecidedByValue();
        ThreadWait();
        abActions.selectPreferredOptionABData();
        String PreferredOption=abActions.getPreferredOptionABData();
        click(abActions.abConditionApplyButton);

        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
        fillMerchandisingData(object,UnbxdEnum.BOOST,false);
        ThreadWait();
        merchandisingActions.clickOnApplyButton();

        abActions.selectABConfiguration(UnbxdEnum.VARIATIONB);
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
        fillMerchandisingData(object,UnbxdEnum.BOOST,false);
        merchandisingActions.clickOnApplyButton();
        ThreadWait();

        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        pageRules.add(query);

        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, query);
        ThreadWait();
        merchandisingActions.scrollUntilVisible(searchPageActions.conditionTitle);
        ThreadWait();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getConditionSize();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "BROWSE: SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, object.size(), "BROWSE: NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);

        abActions.selectABConfiguration(UnbxdEnum.VARIATIONB);
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "BROWSE: SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, object.size(), "BROWSE: NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);

//        click(abActions.viewMore);
//        Assert.assertTrue(abActions.abPreviewSummary.getText().contains(winner));
//        Assert.assertTrue(abActions.abPreviewSummary.getText().contains(PreferredOption));
//
//        Assert.assertTrue(abActions.percentageVariantASummary.getText().contains(String.valueOf(VariationA)));
//        Assert.assertTrue(abActions.percentageVariantBSummary.getText().contains(String.valueOf(VariationB)));
        searchPage.threadWait();
        goTo(searchPage);
        searchPage.threadWait();
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }

    @FileToTest(value = "/consoleTestData/ABupcomingSearch.json")
    @Test(description = "SEARCH PAGE AB RULE: Creates and verifies the campaign creation with Boost and AB condition ", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void abUpcomingCreateAndEditTestForSearch(Object jsonObject) throws InterruptedException
    {
        String conditionType = "boost";
        JsonObject ABData = (JsonObject) jsonObject;
        query = ABData.get("query").getAsString();
        int VariationA=ABData.get("VariationA").getAsInt();
        int VariationB=ABData.get("VariationB").getAsInt();
        String timZone= ABData.get("timZone").getAsString();

        goTo(searchPage);;
        searchPage.threadWait();
        ThreadWait();
        createPromotion(query,false,false);
        JsonArray object = ABData.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("ABsearchBoost.json");


        merchandisingActions.fillCampaignDataforUpcoming(campaignData);
        ThreadWait();
        // Find calendar icon and click it
        if (merchandisingActions.calendarIcon.isDisplayed()) {
            searchPageActions.threadWait();
            // First scroll down to ensure page content is loaded
            merchandisingActions.scrollDown();
            ThreadWait();
            // Scroll until calendar icon is visible using JavaScript for better reliability
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) merchandisingActions.getDriver();
            js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", merchandisingActions.calendarIcon.getElement());
            ThreadWait();
            merchandisingActions.awaitForElementPresence(merchandisingActions.calendarIcon);
            ThreadWait();
            // Use safeClick to handle click interception
            try {
                merchandisingActions.waitForElementToBeClickable(merchandisingActions.calendarIcon, "Calendar icon");
            merchandisingActions.calendarIcon.click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                // Fallback to JavaScript click if regular click is intercepted
                js.executeScript("arguments[0].click();", merchandisingActions.calendarIcon.getElement());
            }
            ThreadWait();
            merchandisingActions.upcomingDateSelection();
            ThreadWait();
            // Scroll until timezone is visible using JavaScript for better reliability
            js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", merchandisingActions.timezoneDropdown.getElement());
            ThreadWait();
            merchandisingActions.awaitForElementPresence(merchandisingActions.timezoneDropdown);
            ThreadWait();
            merchandisingActions.timeZoneSelection();}

        if (merchandisingActions.calenderApplyButton.isDisplayed()) {
            // Scroll until apply button is visible using JavaScript for better reliability
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) merchandisingActions.getDriver();
            js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", merchandisingActions.calenderApplyButton.getElement());
            ThreadWait();
            merchandisingActions.awaitForElementPresence(merchandisingActions.calenderApplyButton);
            ThreadWait();
            // Use safeClick to handle click interception
            try {
                merchandisingActions.waitForElementToBeClickable(merchandisingActions.calenderApplyButton, "Calendar apply button");
                merchandisingActions.calenderApplyButton.click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                // Fallback to JavaScript click if regular click is intercepted
                js.executeScript("arguments[0].click();", merchandisingActions.calenderApplyButton.getElement());
            }
            ThreadWait();
        }
        merchandisingActions.clickonNext();
        merchandisingActions.goToLandingPage();

        abActions.enableABToggle();
        ThreadWait();
        abActions.fillABPercentage(UnbxdEnum.VARIATIONA, VariationA);
        abActions.scrollToBottomOfRuleContent();
        ThreadWait();
        abActions.fillABPercentage(UnbxdEnum.VARIATIONB, VariationB);
        String winnerDecidedBy = ABData.get("winnerDecidedBy").getAsString();
        abActions.selectWinnerDecidedByabData(winnerDecidedBy);
        ThreadWait();
        String winner=abActions.getWinnerDecidedByValue();
        ThreadWait();
        abActions.selectPreferredOptionABData();
        String PreferredOption=abActions.getPreferredOptionABData();
        click(abActions.abConditionApplyButton);
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
        fillMerchandisingData(object,UnbxdEnum.BOOST,false);
        merchandisingActions.clickOnApplyButton();

        abActions.selectABConfiguration(UnbxdEnum.VARIATIONB);
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
        fillMerchandisingData(object,UnbxdEnum.BOOST,false);
        merchandisingActions.clickOnApplyButton();

        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);

        searchPageActions.selectActionType(UnbxdEnum.EDIT, query);
        ThreadWait();
        merchandisingActions.scrollUntilVisible(searchPageActions.conditionTitle);
        ThreadWait();
        String condition = searchPageActions.getConditionTitle();
        int group = searchPageActions.getConditionSize();
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, object.size(), "NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);

        abActions.selectABConfiguration(UnbxdEnum.VARIATIONB);
        Assert.assertTrue(condition.equalsIgnoreCase(conditionType), "BROWSE: SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);
        Assert.assertEquals(group, object.size(), "BROWSE: NUMBER OF CONDITION GROUP IS WRONG!!! SELECTED CONDITION GROUP IS : " + group);
        abActions.scrollToBottomOfRuleContent();
        // merchandisingActions.MerchandisingStrategyEditButton.click();
        // fillMerchandisingData(object,UnbxdEnum.BOOST,true);
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.FILTER);
        fillMerchandisingData(object, UnbxdEnum.FILTER,false);
        merchandisingActions.clickOnApplyButton();
        abActions.selectABConfiguration(UnbxdEnum.VARIATIONA);
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.FILTER);
        fillMerchandisingData(object, UnbxdEnum.FILTER,false);
        merchandisingActions.clickOnApplyButton();
        searchPage.threadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();

        searchPageActions.selectActionType(UnbxdEnum.PREVIEW, query);
        ThreadWait();
        merchandisingActions.scrollUntilVisible(searchPageActions.conditionTitle);
        ThreadWait();
        String Fcondition = searchPageActions.getConditionTitle();
        Assert.assertTrue(Fcondition.equalsIgnoreCase(conditionType), "BROWSE: SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);

        abActions.selectABConfiguration(UnbxdEnum.VARIATIONB);
        ThreadWait();
        merchandisingActions.scrollUntilVisible(searchPageActions.conditionTitle);
        ThreadWait();
        String Filtercondition = searchPageActions.getConditionTitle();
        Assert.assertTrue(Filtercondition.equalsIgnoreCase(conditionType), "BROWSE: SELECTED CONDITION TYPE IS WRONG!!! SELECTED CONDITION IS : " + conditionType);


        searchPage.threadWait();
        goTo(searchPage);
        searchPage.threadWait();
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

//        click(abActions.viewMore);
//        Assert.assertTrue(abActions.abPreviewSummary.getText().contains(winner));
//        Assert.assertTrue(abActions.abPreviewSummary.getText().contains(PreferredOption));
//
//        Assert.assertTrue(abActions.percentageVariantASummary.getText().contains(String.valueOf(VariationA)));
//        Assert.assertTrue(abActions.percentageVariantBSummary.getText().contains(String.valueOf(VariationB)));
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
//            }
//
//        }
//    }


}

