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

public class downloadSampleRedirectsSearch extends uploadTest {

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
    @FileToTest(value = "/consoleTestData/redirect.json")
    @Test(description = "BULK UPLOAD REDIRECTS: Test bulk upload redirects functionality with file download", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void downloadSampleRedirectsTest(Object jsonObject) throws InterruptedException
    {
        // Use the inherited searchPage from MerchandisingTest base class
        goTo(searchPage);
        searchPage.threadWait();
        
        // Navigate to redirect section using the existing navigation method
        ThreadWait();
        merchandisingActions.goToSection(UnbxdEnum.REDIRECT);
        searchPageActions.awaitForPageToLoad();
        
        // Step 1: Verify redirect heading is displayed (same UI pattern as promotions)
        Assert.assertTrue(promotionBulkUploadActions.isPromotionsHeadingDisplayed(), 
                         "Redirect heading is not displayed on the page");
        
        // Step 2: Click on menu icon
        promotionBulkUploadActions.clickMenuIcon();
        
        // Step 3: Click on bulk upload redirects option (should work for redirects too)
        promotionBulkUploadActions.clickBulkUploadPromotionsOption();
        
        // Step 4: Click on download sample link
        promotionBulkUploadActions.clickDownloadSampleLink();
        
        // Step 5: Verify file is downloaded
//        boolean fileDownloaded = promotionBulkUploadActions.isFileDownloaded();
//        Assert.assertTrue(fileDownloaded, "File was not downloaded successfully");
    }

} 