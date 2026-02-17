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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.ui.page.UiBase.ThreadWait;

public class uploadPromotionsBulkBrowse extends uploadTest {

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

    //Browse test cases
    @Test(description = "BROWSE BULK UPLOAD PROMOTIONS: Test bulk upload promotions functionality with file upload", priority = 1, groups = {"merchandising","sanity"})
    public void bulkUploadPromotionsUploadFileBrowse() throws InterruptedException
    {
        // Step 0: Find promotion file dynamically using common method
        String promotionFileName = findBulkUploadFile("promotion", "browse");
        String directory = getBulkUploadDirectory("promotion");
        System.out.println("Browse promotion bulk upload test using file: " + promotionFileName + " from " + directory);
        
        // Navigate to browse page instead of search page
        goTo(browsePage);
        searchPage.threadWait();
        
        // Step 1: Verify promotions heading is displayed
        Assert.assertTrue(promotionBulkUploadActions.isPromotionsHeadingDisplayed(), 
                         "Promotions heading is not displayed on the page");
        
        // Step 2: Click on menu icon
        promotionBulkUploadActions.clickMenuIcon();
        
        // Step 3: Click on bulk upload promotions option
        promotionBulkUploadActions.clickBulkUploadPromotionsOption();
        
        // Step 4: Select file from test data using the new browse promotion file
        promotionBulkUploadActions.selectFileFromLocal(promotionFileName);
        
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