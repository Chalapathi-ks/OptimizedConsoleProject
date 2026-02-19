package UnbxdTests.testNG.consoleui.Collections;

import UnbxdTests.testNG.consoleui.MerchBulkUpload.uploadTest;
import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import core.consoleui.actions.CollectionsActions;
import org.fluentlenium.core.annotation.Page;
import org.testng.annotations.Test;

import static core.ui.page.UiBase.ThreadWait;

public class CollectionsuplodFileBrowse extends uploadTest {

    @Page
    CollectionsActions collectionsActions;

    /**
     * Test to navigate to Collections page only
     */
    @Test(priority = 1, description = "Navigate to Collections page only")
    public void uploadFileToCollections() {
        System.out.println("=== Test: Navigate to Collections Page ONLY ===");
        
        // Step 1: Navigate to Collections page
        System.out.println("Step 1: Navigating to Collections page");
        collectionsActions.navigateToCollectionsBrowse();
        System.out.println("Step 2: Verifying Collections page is loaded");
        collectionsActions.verifyCollectionsPageLoaded();
        System.out.println("Step 3: Clicking on Upload File button");
        collectionsActions.clickUploadFile();
        System.out.println("Step 4: Entering collection name");
        collectionsActions.enterCollectionName();
        System.out.println("Step 5: Uploading collections CSV file");
        collectionsActions.uploadCollectionsFile();
        System.out.println("Step 6: Verifying file upload success");
        collectionsActions.verifyFileUploadSuccess();
        System.out.println("✓ Collections file upload test completed successfully");
    }
} 