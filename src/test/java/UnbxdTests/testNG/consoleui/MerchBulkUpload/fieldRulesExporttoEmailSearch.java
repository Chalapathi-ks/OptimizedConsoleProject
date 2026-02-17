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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.ui.page.UiBase.ThreadWait;

public class fieldRulesExporttoEmailSearch extends uploadTest {

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
    @FileToTest(value = "/consoleTestData/FieldRuleBanner.json")
    @Test(description = "FIELD RULES BULK UPLOAD: Test field rules bulk upload functionality with export to email", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void fieldRulesBulkUploadTest(Object jsonObject) throws InterruptedException
    {
        // Use the inherited searchPage from MerchandisingTest base class
        goTo(searchPage);
        searchPage.threadWait();
        
        // Navigate to facets section using the existing navigation method
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();
        
        // Step 1: Verify facets heading is displayed
        Assert.assertTrue(promotionBulkUploadActions.isPromotionsHeadingDisplayed(), 
                         "Facets heading is not displayed on the page");
       
        
        // Step 2: Click on menu icon
        
        promotionBulkUploadActions.clickMenuIcon();
        
        
        // Step 3: Verify more items dropdown is displayed
      
        Assert.assertTrue(promotionBulkUploadActions.isMoreItemsDropdownDisplayed(),
                         "More items dropdown is not displayed after clicking menu icon");
        
        
       
       
        promotionBulkUploadActions.clickExportToEmailOption();
        
        
       
//        Assert.assertTrue(promotionBulkUploadActions.isExportSuccessMessageDisplayed(),
//                         "Export success message is not displayed after clicking export option");
//
//        String successMessage = promotionBulkUploadActions.getExportSuccessMessageText();
//
//
//        // Verify the success message contains expected text
//        Assert.assertTrue(successMessage.contains("successfully"), "Success message does not contain expected text");
//
        
    }

} 