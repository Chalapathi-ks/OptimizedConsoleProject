package UnbxdTests.testNG.consoleui.Collections;

import UnbxdTests.testNG.consoleui.MerchBulkUpload.uploadTest;
import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import core.consoleui.actions.CollectionsActions;
import org.fluentlenium.core.annotation.Page;
import org.testng.annotations.Test;

import static core.ui.page.UiBase.ThreadWait;

public class CollectionsTestBrowse extends uploadTest {

    @Page
    CollectionsActions collectionsActions;

    /**
     * Test to navigate to Collections browse page, verify page load, download collection file, and verify download
     */
    @Test(priority = 1, description = "Navigate to Collections browse page, download collection file, and verify download")
    public void testBrowseAndDownloadCollectionFile() {
        System.out.println("=== Test: Browse and Download Collection File from Browse Page ===");
        
        // Step 1: Navigate to Collections browse page
        System.out.println("Step 1: Navigating to Collections browse page");
        collectionsActions.navigateToCollectionsBrowse();
        
        // Step 2: Verify Collections page is loaded
        System.out.println("Step 2: Verifying Collections page is loaded");
        collectionsActions.verifyCollectionsPageLoaded();
        
        // Step 3: Click on download icon
        System.out.println("Step 3: Clicking on download icon");
        collectionsActions.clickDownloadIcon();
        
        // Step 4: Verify file is downloaded
        System.out.println("Step 4: Verifying file download");
        collectionsActions.verifyFileDownloaded();
        
        System.out.println("✓ Browse and download collection file test completed successfully");
    }
} 