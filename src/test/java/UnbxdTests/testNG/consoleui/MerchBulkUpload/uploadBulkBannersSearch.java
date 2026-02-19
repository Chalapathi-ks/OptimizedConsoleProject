package UnbxdTests.testNG.consoleui.MerchBulkUpload;


import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.consoleui.actions.ABActions;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.actions.PromotionBulkUploadActions;
import core.consoleui.page.BrowsePage;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.ui.page.UiBase.ThreadWait;

public class uploadBulkBannersSearch extends uploadTest {

    String query;

    List<String> queryRules = new ArrayList<>();
    List<String> pageRules = new ArrayList<>();

    @Page
    CommercePageActions searchPageActions;

    @Page
    BrowsePage browsePage;

    @Page
    ABActions abActions;

    @Page
    PromotionBulkUploadActions promotionBulkUploadActions;

    public String page;



    //Search test cases
    @Test(description = "BULK UPLOAD BANNERS: Test bulk upload banners functionality with file upload", 
          priority = 1, groups = {"merchandising","sanity"})
    public void bulkUploadBannersUploadFile() throws InterruptedException, IOException
    {
        // Step 0: Find search banner CSV file dynamically using common method
        String csvFileName = findBulkUploadFile("banner", "search");
        String directory = getBulkUploadDirectory("banner");
        System.out.println("Search banner bulk upload test using file: " + csvFileName + " from " + directory);
        
        // Use the inherited searchPage from MerchandisingTest base class
        goTo(searchPage);
        searchPage.threadWait();
        
        // Navigate to banner section using the existing navigation method
        merchandisingActions.goToSection(UnbxdEnum.BANNER);
        searchPageActions.awaitForPageToLoad();
        
        // Step 1: Verify banner heading is displayed (same UI pattern as promotions)
        Assert.assertTrue(promotionBulkUploadActions.isPromotionsHeadingDisplayed(), 
                         "Banner heading is not displayed on the page");
        
        // Step 2: Click on menu icon
        promotionBulkUploadActions.clickMenuIcon();
        
        // Step 3: Click on bulk upload banners option (should work for banners too)
        promotionBulkUploadActions.clickBulkUploadPromotionsOption();
        
        // Step 4: Select CSV file from test data for banner search bulk upload
        promotionBulkUploadActions.selectCSVFileFromTestData(csvFileName, directory);
        
        // Step 5: Verify upload report message is displayed
        Assert.assertTrue(promotionBulkUploadActions.isUploadReportMessageDisplayed(),
                         "Upload report message is not displayed after file upload");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
        lib.Helper.tearDown();
    }

} 