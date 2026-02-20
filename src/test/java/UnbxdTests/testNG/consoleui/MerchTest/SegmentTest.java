package UnbxdTests.testNG.consoleui.MerchTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.consoleui.actions.BannerActions;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.actions.MerchandisingActions;
import core.consoleui.actions.SegmentActions;
import core.consoleui.page.BrowsePage;
import core.consoleui.page.BrowseSegmentPage;
import core.consoleui.page.SegmentPage;
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

import static core.ui.page.UiBase.ThreadWait;

public class SegmentTest extends MerchandisingTest {

    List<String> SegmentRules = new ArrayList<>();

    @Page
    MerchandisingActions merchandisingActions;

    @Page
    CommercePageActions searchPageActions;
    @Page
    SegmentPage segmentPage;

    @Page
    BrowseSegmentPage browseSegmentPage;

    @Page
    SegmentActions segmenPageActions;
    @Page
    BannerActions bannerActions;

    @Page
    BrowsePage browsePage;

    public String segmentName;

    List<String> queryRules = new ArrayList<>();
    List<String> pageRules = new ArrayList<>();

    public String page;
    public String query;


    @FileToTest(value = "/consoleTestData/Segment.json")
    @Test(description = "Verifies the creation and editing of a search segment.", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"sanity"})
    public void createAndEditSearchSegmentTest(Object jsonObject) throws InterruptedException {
        JsonObject segmentData=(JsonObject) jsonObject;
        segmentName=segmentData.get("Segment").getAsString();
        String userTypeNew = segmentData.get("UserTypeNew").getAsString();
        String segmentDeviceType = segmentData.get("SegmentDeviceType").getAsString();
        String userTypeRepeat = segmentData.get("UserTypeRepeat").getAsString();

        ThreadWait();
        searchPageActions.awaitForPageToLoad();
        goTo(segmentPage);
        ThreadWait();

        segmenPageActions.createSegment(segmentName);
        segmenPageActions.selectTypeValues(UnbxdEnum.USERTYPE,userTypeNew);
        segmenPageActions.selectDeviceType(UnbxdEnum.DEVICETYPE,segmentDeviceType);
        segmenPageActions.clickOnSave();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        FluentWebElement segmentRule = segmenPageActions.waitForSegmentRulePresent(segmentName, 25);
        Assert.assertNotNull(segmentRule, "Segment rule not found in listing: " + segmentName);
        SegmentRules.add(segmentName);

        searchPageActions.selectActionType(UnbxdEnum.EDIT,segmentName);
        ThreadWait();
        segmenPageActions.awaitForSegmentEditPanelLoaded(30);
        segmenPageActions.awaitForElementPresence(segmenPageActions.segmentUserTypeValues);
        segmenPageActions.awaitForElementPresence(segmenPageActions.segmentDeviceTypeValues);
        segmenPageActions.segmentUserTypeValues.getText().contains(userTypeNew);
        segmenPageActions.segmentDeviceTypeValues.getText().contains(segmentDeviceType);

        segmenPageActions.removeSegmentValues(userTypeNew);
        segmenPageActions.selectTypeValues(UnbxdEnum.USERTYPE,userTypeRepeat);
        segmenPageActions.awaitForElementPresence(segmenPageActions.segmentUserTypeValues);
        segmenPageActions.segmentUserTypeValues.getText().contains(userTypeRepeat);
        segmenPageActions.clickOnSave();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        segmenPageActions.awaitForSegmentUserTypeInListingPage(30);
        segmenPageActions.awaitForElementPresence(segmenPageActions.UserTypeinListingPage);
        segmenPageActions.UserTypeinListingPage.getText().contains(userTypeRepeat);

        goTo(segmentPage);
        segmenPageActions.deleteSegmentRule(segmentName);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

    }


    @FileToTest(value = "/consoleTestData/SegmentMerchBanner.json")
    @Test(description = "Verifies that a created search segment can be used in a banner.", priority = 2, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifySearchSegmentInBannerTest(Object jsonObject) throws InterruptedException {

        JsonObject segmentData=(JsonObject) jsonObject;
        segmentName=segmentData.get("Segment").getAsString();
        String userTypeNew = segmentData.get("UserTypeNew").getAsString();
        String segmentDeviceType = segmentData.get("SegmentDeviceType").getAsString();
        String BannerUrl = segmentData.get("BnnerUrl").getAsString();


        ThreadWait();
        searchPageActions.awaitForPageToLoad();
        goTo(segmentPage);
        ThreadWait();

        segmenPageActions.createSegment(segmentName);
        segmenPageActions.selectTypeValues(UnbxdEnum.USERTYPE,userTypeNew);
        searchPage.threadWait();
        segmenPageActions.selectDeviceType(UnbxdEnum.DEVICETYPE,segmentDeviceType);
        segmenPageActions.clickOnSave();
        merchandisingActions.verifySuccessMessage();
        searchPage.threadWait();
        FluentWebElement segmentRule = segmenPageActions.waitForSegmentRulePresent(segmentName, 25);
        Assert.assertNotNull(segmentRule, "Segment rule not found in listing: " + segmentName);
        SegmentRules.add(segmentName);

        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();

        //create the rule
        createPromotion(segmentName,true,true);
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("SegmentMerchBanner.json");
        bannerActions.goToQueryRuleBanner();
        ThreadWait();
        searchPageActions.fillQueryRuleData(segmentName,null);
        merchandisingActions.fillCampaignDataforUpcoming(campaignData);
        ThreadWait();
        merchandisingActions.selectGlobalSegment();
        ThreadWait();
        merchandisingActions.selectSegment(segmentName);
        ThreadWait();
        merchandisingActions.clickonNext();
        bannerActions.addHtmlBanner(BannerUrl);
        ThreadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(segmentName));
        queryRules.add(segmentName);

        //delete
        searchPageActions.deleteQueryRule(segmentName);
        Assert.assertNull(searchPage.queryRuleByName(segmentName), "BROWSE RULE : CREATED PAGE RULE IS NOT DELETED");

        goTo(segmentPage);
        segmenPageActions.deleteSegmentRule(segmentName);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();


    }

    @FileToTest(value = "/consoleTestData/SegmentCustomAttribute.json")
    @Test(description = "Verifies creating and editing a search segment with a custom attribute.", priority = 3, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void createAndEditSearchSegmentWithCustomAttributeTest(Object jsonObject) throws InterruptedException {

        JsonObject segmentData = (JsonObject) jsonObject;
        segmentName = segmentData.get("Segment").getAsString();
        String customAttribute = segmentData.get("CustomAttribute").getAsString();

        ThreadWait();
        searchPageActions.awaitForPageToLoad();
        goTo(segmentPage);
        ThreadWait();

        String CustomAttribute=segmenPageActions.addAndSaveCustomAttribute(customAttribute);
        segmenPageActions.createSegment(segmentName);
        String CustAtrribute=segmenPageActions.selectAndEnterCustomAttribute(CustomAttribute);
        String segmentNameValue=segmenPageActions.selectAndEnterCustomValue(segmentName);
        segmenPageActions.clickOnSave();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        FluentWebElement segmentRule = segmenPageActions.waitForSegmentRulePresent(segmentName, 25);
        Assert.assertNotNull(segmentRule, "Segment rule not found in listing: " + segmentName);
        SegmentRules.add(segmentName);

        searchPageActions.selectActionType(UnbxdEnum.EDIT,segmentName);
        ThreadWait();
        segmenPageActions.selectedCustom.getText().contains(CustAtrribute);
        segmenPageActions.selectedCustomValue.getText().contains(segmentNameValue);

        segmenPageActions.selectAndEnterCustomAttribute(CustomAttribute);
        segmenPageActions.selectAndEnterCustomValue(segmentName);
        segmenPageActions.clickOnSave();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(segmenPageActions.waitForSegmentRulePresent(segmentName, 25), "Segment rule not found: " + segmentName);

        goTo(segmentPage);
        segmenPageActions.deleteSegmentRule(segmentName);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

        ThreadWait();
        segmenPageActions.deleteCustomAttribute(customAttribute);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();


    }

    @FileToTest(value = "/consoleTestData/SegmentMerchPromotion.json")
    @Test(description = "Verifies that a search segment with a custom attribute can be used in a promotion.", priority = 4, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifySearchSegmentWithCustomAttributeInPromotionTest(Object jsonObject) throws InterruptedException {

        JsonObject segmentData = (JsonObject) jsonObject;
        segmentName = segmentData.get("Segment").getAsString();
        String customAttribute = segmentData.get("CustomAttribute").getAsString();

        searchPageActions.awaitForPageToLoad();
        goTo(segmentPage);
        ThreadWait();

        String CustomAttribute = segmenPageActions.addAndSaveCustomAttribute(customAttribute);
        segmenPageActions.createSegment(segmentName);
        String CustAtrribute=segmenPageActions.selectAndEnterCustomAttribute(CustomAttribute);
        String segmentNameValue=segmenPageActions.selectAndEnterCustomValue(segmentName);
        segmenPageActions.clickOnSave();
        merchandisingActions.verifySuccessMessage();
        searchPage.threadWait();
        FluentWebElement segmentRule = segmenPageActions.waitForSegmentRulePresent(segmentName, 25);
        Assert.assertNotNull(segmentRule, "Segment rule not found in listing: " + segmentName);
        SegmentRules.add(segmentName);

        goTo(searchPage);
        searchPage.threadWait();
        createPromotion(segmentName, false, false);
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("SegmentMerchPromotion.json");
        merchandisingActions.fillCampaignDataforUpcoming(campaignData);
        merchandisingActions.selectGlobalSegment();
        merchandisingActions.selectSegment(segmentName);
        merchandisingActions.clickonNext();
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(segmentName));
        queryRules.add(segmentName);

        //Delete
        searchPageActions.deleteQueryRule(segmentName);
        ThreadWait();
        Assert.assertNull(searchPage.queryRuleByName(segmentName), "BROWSE RULE : CREATED PAGE RULE IS NOT DELETED");

        goTo(segmentPage);
        segmenPageActions.deleteSegmentRule(segmentName);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);

        ThreadWait();
        segmenPageActions.deleteCustomAttribute(customAttribute);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }
//browse
    @FileToTest(value = "/consoleTestData/BrowseSegment.json")
    @Test(description = "Verifies the creation and editing of a browse segment.", priority = 5, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"sanity"})
    public void createAndEditBrowseSegmentTest(Object jsonObject) throws InterruptedException {
        JsonObject segmentData=(JsonObject) jsonObject;
        segmentName=segmentData.get("Segment").getAsString();
        String userTypeNew = segmentData.get("UserTypeNew").getAsString();
        String segmentDeviceType = segmentData.get("SegmentDeviceType").getAsString();
        String userTypeRepeat = segmentData.get("UserTypeRepeat").getAsString();


        ThreadWait();
        searchPageActions.awaitForPageToLoad();
        goTo(browseSegmentPage);
        ThreadWait();

        segmenPageActions.createSegment(segmentName);
        segmenPageActions.selectTypeValues(UnbxdEnum.USERTYPE,userTypeNew);
        segmenPageActions.selectDeviceType(UnbxdEnum.DEVICETYPE,segmentDeviceType);
        segmenPageActions.clickOnSave();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        FluentWebElement segmentRule = segmenPageActions.waitForSegmentRulePresent(segmentName, 25);
        Assert.assertNotNull(segmentRule, "Segment rule not found in listing: " + segmentName);
        SegmentRules.add(segmentName);

        searchPageActions.selectActionType(UnbxdEnum.EDIT,segmentName);
        ThreadWait();
        segmenPageActions.awaitForSegmentEditPanelLoaded(30);
        segmenPageActions.awaitForElementPresence(segmenPageActions.segmentUserTypeValues);
        segmenPageActions.awaitForElementPresence(segmenPageActions.segmentDeviceTypeValues);
        segmenPageActions.segmentUserTypeValues.getText().contains(userTypeNew);
        segmenPageActions.segmentDeviceTypeValues.getText().contains(segmentDeviceType);

        segmenPageActions.removeSegmentValues(userTypeNew);
        segmenPageActions.selectTypeValues(UnbxdEnum.USERTYPE,userTypeRepeat);
        segmenPageActions.awaitForElementPresence(segmenPageActions.segmentUserTypeValues);
        segmenPageActions.segmentUserTypeValues.getText().contains(userTypeRepeat);
        segmenPageActions.clickOnSave();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        segmenPageActions.awaitForSegmentUserTypeInListingPage(30);
        segmenPageActions.awaitForElementPresence(segmenPageActions.UserTypeinListingPage);
        segmenPageActions.UserTypeinListingPage.getText().contains(userTypeRepeat);

        //Delete
        goTo(segmentPage);
        segmenPageActions.deleteSegmentRule(segmentName);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }


    @FileToTest(value = "/consoleTestData/BrowseSegmentBanner.json")
    @Test(description = "Verifies that a created browse segment can be used in a banner.", priority = 6, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifyBrowseSegmentInBannerTest(Object jsonObject) throws InterruptedException {

        JsonObject segmentData=(JsonObject) jsonObject;
        segmentName=segmentData.get("Segment").getAsString();
        String userTypeNew = segmentData.get("UserTypeNew").getAsString();
        String segmentDeviceType = segmentData.get("SegmentDeviceType").getAsString();
        String BannerUrl = segmentData.get("BnnerUrl").getAsString();


        ThreadWait();
        searchPageActions.awaitForPageToLoad();
        goTo(browseSegmentPage);
        ThreadWait();

        segmenPageActions.createSegment(segmentName);
        segmenPageActions.selectTypeValues(UnbxdEnum.USERTYPE,userTypeNew);
        segmenPageActions.selectDeviceType(UnbxdEnum.DEVICETYPE,segmentDeviceType);
        segmenPageActions.clickOnSave();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(segmenPageActions.waitForSegmentRulePresent(segmentName, 25), "Segment rule not found: " + segmentName);
        SegmentRules.add(segmentName);

        goTo(browsePage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();

        //create the rule
        createBrowsePromotion(segmentName,true,true);
        JsonArray object = segmentData.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("BrowseSegmentBanner.json");
        ThreadWait();
        bannerActions.goToQueryRuleBanner();
        searchPageActions.fillPageName(object);
        merchandisingActions.fillCampaignDataforUpcoming(campaignData);
        ThreadWait();
        merchandisingActions.selectGlobalSegment();
        merchandisingActions.selectSegment(segmentName);
        merchandisingActions.clickonNext();
        bannerActions.addHtmlBanner(BannerUrl);
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(segmentName));
        pageRules.add(segmentName);

        //delete
        searchPageActions.deleteQueryRule(segmentName);
        Assert.assertNull(searchPage.queryRuleByName(segmentName), "BROWSE RULE : CREATED PAGE RULE IS NOT DELETED");

        goTo(segmentPage);
        segmenPageActions.deleteSegmentRule(segmentName);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();
    }

    @FileToTest(value = "/consoleTestData/BrowseSegmentPromotion.json")
    @Test(description = "Verifies creating and editing a browse segment with a custom attribute.", priority = 7, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void createAndEditBrowseSegmentWithCustomAttributeInPromotionTest(Object jsonObject) throws InterruptedException {

        JsonObject segmentData = (JsonObject) jsonObject;
        segmentName = segmentData.get("Segment").getAsString();
        String customAttribute = segmentData.get("CustomAttribute").getAsString();


        ThreadWait();
        searchPageActions.awaitForPageToLoad();
        goTo(browseSegmentPage);
        ThreadWait();

        String CustomAttribute=segmenPageActions.addAndSaveCustomAttribute(customAttribute);
        segmenPageActions.createSegment(segmentName);
        ThreadWait();
        String CustAtrribute=segmenPageActions.selectAndEnterCustomAttribute(CustomAttribute);
        ThreadWait();
        String segmentNameValue=segmenPageActions.selectAndEnterCustomValue(segmentName);
        segmenPageActions.clickOnSave();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(segmenPageActions.waitForSegmentRulePresent(segmentName, 25), "Segment rule not found: " + segmentName);
        SegmentRules.add(segmentName);

        searchPageActions.selectActionType(UnbxdEnum.EDIT,segmentName);
        ThreadWait();
        merchandisingActions.awaitForElementPresence(segmenPageActions.findFirst(".custom-attribute-input"));
        Assert.assertTrue(segmenPageActions.findFirst(".custom-attribute-input").getText().contains(CustAtrribute), "Selected custom attribute mismatch");
        Assert.assertTrue(segmenPageActions.findFirst(".tag-item").getText().contains(segmentNameValue), "Selected custom value mismatch");

        segmenPageActions.selectAndEnterCustomValue(segmentName);
        segmenPageActions.clickOnSave();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(segmenPageActions.waitForSegmentRulePresent(segmentName, 25), "Segment rule not found: " + segmentName);
        SegmentRules.add(segmentName);

        goTo(browsePage);
        searchPage.threadWait();
        createBrowsePromotion(segmentName, false, false);
        JsonArray object = segmentData.get("data").getAsJsonArray();

        Map<String, Object> campaignData = merchandisingActions.getCampaignData("BrowseSegmentPromotion.json");
        searchPageActions.fillPageName(object);
        merchandisingActions.fillCampaignDataforUpcoming(campaignData);
        ThreadWait();
        merchandisingActions.selectGlobalSegment();
        ThreadWait();
        merchandisingActions.selectSegment(segmentName);
        ThreadWait();
        merchandisingActions.clickonNext();
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(segmentName));
        pageRules.add(segmentName);

        searchPageActions.deleteQueryRule(segmentName);
        ThreadWait();
        Assert.assertNull(searchPage.queryRuleByName(segmentName), "BROWSE RULE : CREATED PAGE RULE IS NOT DELETED");

        goTo(segmentPage);
        segmenPageActions.deleteSegmentRule(segmentName);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        ThreadWait();
        segmenPageActions.deleteCustomAttribute(customAttribute);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }



//    @AfterClass(alwaysRun = true,groups={"sanity"})
//    public void deleteCreatedRules() throws InterruptedException {
//
//        goTo(segmentPage);
//        merchandisingActions.goToSection(UnbxdEnum.SEGMENTS);
//        for (String SegmentRule : SegmentRules) {
//            if (segmenPageActions.segmentRuleByName(segmentName) != null) {
//                segmenPageActions.deleteSegmentRule(SegmentRule);
//                Assert.assertNull(segmenPageActions.segmentRuleByName(SegmentRule), "CREATED QUERY RULE IS NOT DELETED");
//                getDriver().navigate().refresh();
//                ThreadWait();
//
//            }
//        }
//
//        goTo(browseSegmentPage);
//        merchandisingActions.goToSection(UnbxdEnum.SEGMENTS);
//        for (String SegmentRule : SegmentRules) {
//            if (segmenPageActions.segmentRuleByName(segmentName) != null) {
//                segmenPageActions.deleteSegmentRule(SegmentRule);
//                Assert.assertNull(segmenPageActions.segmentRuleByName(SegmentRule), "CREATED QUERY RULE IS NOT DELETED");
//                getDriver().navigate().refresh();
//                ThreadWait();
//
//            }
//        }
//
//    }
}




