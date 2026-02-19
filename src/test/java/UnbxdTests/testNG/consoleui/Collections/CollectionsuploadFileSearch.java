package UnbxdTests.testNG.consoleui.Collections;

import UnbxdTests.testNG.consoleui.MerchBulkUpload.uploadTest;
import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import core.consoleui.actions.CollectionsActions;
import org.fluentlenium.core.annotation.Page;
import org.testng.annotations.Test;

import static core.ui.page.UiBase.ThreadWait;

public class CollectionsuploadFileSearch extends uploadTest {

    @Page
    CollectionsActions collectionsActions;

    /**
     * Test to navigate to Collections page and upload collections file
     */
    @Test(priority = 1, description = "Navigate to Collections page and upload collections file")
    public void uploadFileToCollections() {
        System.out.println("=== Test: Upload File to Collections ===");
        
        // Step 1: Navigate to Collections page
        System.out.println("Step 1: Navigating to Collections page");
        collectionsActions.navigateToCollections();
        
        // Step 2: Verify Collections page is loaded
        System.out.println("Step 2: Verifying Collections page is loaded");
        collectionsActions.verifyCollectionsPageLoaded();
        
        // Step 3: Click on Upload File button
        System.out.println("Step 3: Clicking on Upload File button");
        collectionsActions.clickUploadFile();
        
        // Step 4: Enter collection name
        System.out.println("Step 4: Entering collection name");
        collectionsActions.enterCollectionName();
        
        // Step 5: Upload collections CSV file
        System.out.println("Step 5: Uploading collections CSV file");
        collectionsActions.uploadCollectionsFile();
        
        // Step 6: Verify file upload success
        System.out.println("Step 6: Verifying file upload success");
        collectionsActions.verifyFileUploadSuccess();
        
        System.out.println("✓ Collections file upload test completed successfully");
    }
} 