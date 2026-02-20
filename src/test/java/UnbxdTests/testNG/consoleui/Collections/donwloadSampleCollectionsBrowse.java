package UnbxdTests.testNG.consoleui.Collections;

import UnbxdTests.testNG.consoleui.MerchBulkUpload.uploadTest;
import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import core.consoleui.actions.CollectionsActions;
import lib.compat.Page;
import org.testng.annotations.Test;

import static core.ui.page.UiBase.ThreadWait;

public class donwloadSampleCollectionsBrowse extends uploadTest {

    @Page
    CollectionsActions collectionsActions;

    /**
     * Test to navigate to Collections browse page and download sample collections file
     */
    @Test(priority = 1, description = "Navigate to Collections browse page and download sample collections file")
    public void downloadSampleCollectionsFile() {
        System.out.println("=== Test: Download Sample Collections File from Browse Page ===");
        
        // Step 1: Navigate to Collections browse page
        System.out.println("Step 1: Navigating to Collections browse page");
        collectionsActions.navigateToCollectionsBrowse();
        
        // Step 2: Verify Collections page is loaded
        System.out.println("Step 2: Verifying Collections page is loaded");
        collectionsActions.verifyCollectionsPageLoaded();
        
        // Step 3: Click on Upload File button
        System.out.println("Step 3: Clicking on Upload File button");
        collectionsActions.clickUploadFile();
        
        // Step 4: Click on Download Sample Collections File link
        System.out.println("Step 4: Clicking on Download Sample Collections File link");
        collectionsActions.clickDownloadSampleCollectionsFile();
        
        // Step 5: Verify file is downloaded
        System.out.println("Step 5: Verifying file download");
        collectionsActions.verifyFileDownloaded();
        
        System.out.println("✓ Sample collections file download from browse page test completed successfully");
    }
} 