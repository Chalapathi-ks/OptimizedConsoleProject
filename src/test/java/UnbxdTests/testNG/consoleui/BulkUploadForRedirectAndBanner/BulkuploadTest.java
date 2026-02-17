package UnbxdTests.testNG.consoleui.BulkUploadForRedirectAndBanner;


import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonObject;
import core.consoleui.actions.BannerBulkUploadActions;
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

import static core.ui.page.UiBase.ThreadWait;

public class BulkuploadTest extends MerchandisingTest {
    List<String> BulkBannerQuries = new ArrayList<>();
    List<String> BulkBrowseBannerQuries = new ArrayList<>();
    List<String> BulkRedirectQuries = new ArrayList<>();

    @Page
    CommercePageActions searchPageActions;

    @Page
    BannerBulkUploadActions bannerBulkUploadActions;

    @Page
    BrowsePage browsePage;

    public String page;


    @FileToTest(value = "/BulkuploadJsonFiles/BulkUploadBanner.json")
    @Test(description = "SEARCH: Bulk Upload Test For Banner", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void bulkUploadBannerTest(Object jsonObject) throws InterruptedException {
       /* JsonObject BannerData = (JsonObject) jsonObject;
        String bannerQuery=BannerData.get("BannerQueryRule").getAsString();
        BulkBannerQuries.add(bannerQuery);
        String bannerQuery1=BannerData.get("BannerQueryRule1").getAsString();
        BulkBannerQuries.add(bannerQuery1);
        String BannerFieldRule=BannerData.get("BannerFieldRule").getAsString();
        BulkBannerQuries.add(BannerFieldRule);
        String BannerFieldRule1=BannerData.get("BannerFieldRule1").getAsString();
        BulkBannerQuries.add(BannerFieldRule1);
        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();
        bannerBulkUploadActions.openBulkUploadPopup();

        FluentWebElement upload= bannerBulkUploadActions.bannerFileUploadButton;
        String bulkFileupload = System.getProperty("user.dir") + "/src/test/resources/testData/BulkuploadCSV/SearchBulkUploadBannerData.csv";
        upload.fill().with(bulkFileupload);
        if(searchPage.awaitForElementPresence(bannerBulkUploadActions.bulkUploadSuccessIcon)){
            System.out.println(bannerBulkUploadActions.bulkUploadedSuccessMessage.getText());
        }else {
            Assert.fail("BANNER FILE UPLOAD IS FAILED REASON IS :"+bannerBulkUploadActions.bulkUploadErrorMessage.getText());
        }
        bannerBulkUploadActions.clickOnbulkUploadDoneButton();
        driver.navigate().refresh();
        ThreadWait();
        for (String Rule : BulkBannerQuries) {
            Assert.assertNotNull(searchPage.queryRuleByName(Rule));
            ThreadWait();
        }
    }

    @FileToTest(value = "/BulkuploadJsonFiles/BulkUploadBrowseBanner.json")
    @Test(description = "BROWSE: Bulk Upload Test For Browse Banner", priority = 2, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void browseBulkUploadBannerTest(Object jsonObject) throws InterruptedException {
        JsonObject BannerData = (JsonObject) jsonObject;
        String bannerQuery=BannerData.get("BannerQueryRule").getAsString();
        BulkBrowseBannerQuries.add(bannerQuery);
        String bannerQuery1=BannerData.get("BannerQueryRule1").getAsString();
        BulkBrowseBannerQuries.add(bannerQuery1);
        String BannerFieldRule=BannerData.get("BannerFieldRule").getAsString();
        BulkBrowseBannerQuries.add(BannerFieldRule);
        String BannerFieldRule1=BannerData.get("BannerFieldRule1").getAsString();
        BulkBrowseBannerQuries.add(BannerFieldRule1);
        goTo(browsePage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();
        bannerBulkUploadActions.openBulkUploadPopup();

        FluentWebElement upload= bannerBulkUploadActions.bannerFileUploadButton;
        String bulkFileupload = System.getProperty("user.dir") + "/src/test/resources/testData/BulkuploadCSV/browseBulkUploadBannerData.csv";
        upload.fill().with(bulkFileupload);
        if(searchPage.awaitForElementPresence(bannerBulkUploadActions.bulkUploadSuccessIcon)){
            System.out.println(bannerBulkUploadActions.bulkUploadedSuccessMessage.getText());
        }else {
            Assert.fail("BANNER FILE UPLOAD IS FAILED REASON IS :"+bannerBulkUploadActions.bulkUploadErrorMessage.getText());
        }
        bannerBulkUploadActions.clickOnbulkUploadDoneButton();
        driver.navigate().refresh();
        ThreadWait();
        for (String Rule : BulkBrowseBannerQuries) {
            Assert.assertNotNull(searchPage.queryRuleByName(Rule));
            ThreadWait();
        }
    }

    @FileToTest(value = "/BulkuploadJsonFiles/BulkUploadRedirect.json")
    @Test(description = "SEARCH: Bulk Upload Test For Redirect", priority = 3, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void BulkUploadRedirectTest(Object jsonObject) throws InterruptedException {
        JsonObject RedirectData = (JsonObject) jsonObject;
        String RedirectQuery=RedirectData.get("RedirectQueryRule").getAsString();
        BulkRedirectQuries.add(RedirectQuery);
        String RedirectQuery1=RedirectData.get("RedirectQueryRule1").getAsString();
        BulkRedirectQuries.add(RedirectQuery1);

        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.REDIRECT);
        searchPageActions.awaitForPageToLoad();
        bannerBulkUploadActions.openBulkUploadPopup();

        FluentWebElement upload= bannerBulkUploadActions.bannerFileUploadButton;
        String bulkFileupload = System.getProperty("user.dir") + "/src/test/resources/testData/BulkuploadCSV/SearchBulkUploadRedirectData.csv";
        upload.fill().with(bulkFileupload);
        if(searchPage.awaitForElementPresence(bannerBulkUploadActions.bulkUploadSuccessIcon)){
            System.out.println(bannerBulkUploadActions.bulkUploadedSuccessMessage.getText());
        }else {
            Assert.fail("REDIRECT FILE UPLOAD IS FAILED REASON IS :"+bannerBulkUploadActions.bulkUploadErrorMessage.getText());
        }
        bannerBulkUploadActions.clickOnbulkUploadDoneButton();
        driver.navigate().refresh();
        ThreadWait();
        for (String Rule : BulkRedirectQuries) {
            Assert.assertNotNull(searchPage.queryRuleByName(Rule));
            ThreadWait();
        }
    }


    @AfterClass(alwaysRun = true,groups={"sanity"})
    public void deleteCreatedRules() throws InterruptedException {
        goTo(searchPage);
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        for (String Rule : BulkBannerQuries) {
            if (searchPage.queryRuleByName(Rule)!= null) {
                searchPageActions.deleteQueryRule(Rule);
                Assert.assertNull(searchPage.queryRuleByName(Rule), "CREATED BANNER QUERY RULE IS NOT DELETED");
                getDriver().navigate().refresh();
                ThreadWait();
            }
        }

        goTo(searchPage);
        merchandisingActions.goToSection(UnbxdEnum.REDIRECT);
        for (String Rule : BulkRedirectQuries) {
            if (searchPage.queryRuleByName(Rule)!= null) {
                searchPageActions.deleteQueryRule(Rule);
                Assert.assertNull(searchPage.queryRuleByName(Rule), "CREATED REDIRECT RULE IS NOT DELETED");
                getDriver().navigate().refresh();
                ThreadWait();
            }
        }

        goTo(browsePage);
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        for (String Rule : BulkBrowseBannerQuries) {
            if (searchPage.queryRuleByName(Rule) != null) {
                searchPageActions.deleteQueryRule(Rule);
                Assert.assertNull(searchPage.queryRuleByName(Rule), "BROWSE RULE : CREATED BANNER PAGE RULE IS NOT DELETED");
                getDriver().navigate().refresh();
                ThreadWait();

            }
        }*/
    }
}
