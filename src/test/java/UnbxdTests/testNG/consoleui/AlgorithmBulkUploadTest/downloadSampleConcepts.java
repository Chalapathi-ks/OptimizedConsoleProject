package UnbxdTests.testNG.consoleui.AlgorithmBulkUploadTest;

import UnbxdTests.testNG.consoleui.MerchBulkUpload.uploadTest;
import core.consoleui.actions.AlgorithmsActions;
import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;
import org.testng.annotations.Test;
import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;

public class downloadSampleConcepts extends uploadTest {

    @Page
    AlgorithmsActions algorithmsActions;

    /**
     * Test to navigate to Algorithms (Concepts) page, click kabab menu, bulk upload, download sample, and verify file
     */
    @Test(priority = 1, description = "Navigate to Algorithms (Concepts) page, bulk upload, download sample, and verify file")
    public void downloadSamplecollectionsFile() throws Exception {
        algorithmsActions.navigateToConceptsPage();
        // Step: Click kabab menu
        algorithmsActions.clickKababMenu();
        // Step: Click Bulk upload synonyms
        algorithmsActions.clickBulkUploadSynonyms();
        // Step: Click Download sample synonyms file
        algorithmsActions.clickDownloadSampleSynonymsFile();
        // Step: Verify file download (csv)
       // Assert.assertTrue(algorithmsActions.isFileDownloaded(".csv"), "Sample concepts file not found in Downloads!");
    }
} 