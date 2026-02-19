package UnbxdTests.testNG.consoleui.MerchBulkUpload;


import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import core.consoleui.actions.ABActions;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.actions.PromotionBulkUploadActions;
import core.consoleui.page.BrowsePage;
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

import static core.ui.page.UiBase.ThreadWait;

public class uploadFieldRulesBulkSearch extends uploadTest {

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
    @Test(description = "BULK UPLOAD FIELD RULES: Test bulk upload field rules functionality with file upload", priority = 1, groups = {"merchandising","sanity"})
    public void bulkUploadFieldRulesUploadFile() throws InterruptedException, IOException
    {
        // Step 0: Find field rules file dynamically using common method
        String fieldRulesFileName = findBulkUploadFile("facets", "search");
        String directory = getBulkUploadDirectory("facets");
        System.out.println("Search field rules bulk upload test using file: " + fieldRulesFileName + " from " + directory);
        
        // Use the inherited searchPage from MerchandisingTest base class
        goTo(searchPage);
        searchPage.threadWait();
        
        // Navigate to facets section using the existing navigation method (following working field rules tests)
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();
        
        // Step 1: Verify facets heading is displayed
        Assert.assertTrue(promotionBulkUploadActions.isPromotionsHeadingDisplayed(), 
                         "Facets heading is not displayed on the page");
        
        // Step 2: Click on menu icon
        promotionBulkUploadActions.clickMenuIcon();
        
        // Step 3: Click on bulk upload field rules option
        promotionBulkUploadActions.clickBulkUploadPromotionsOption();
        
        // Step 4: Select CSV file from test data for facets field rules bulk upload
        promotionBulkUploadActions.selectCSVFileFromTestData(fieldRulesFileName, directory);
        
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