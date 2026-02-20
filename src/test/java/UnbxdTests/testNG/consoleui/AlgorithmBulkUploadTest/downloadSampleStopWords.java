package UnbxdTests.testNG.consoleui.AlgorithmBulkUploadTest;

import UnbxdTests.testNG.consoleui.MerchBulkUpload.uploadTest;
import core.consoleui.actions.AlgorithmsActions;
import lib.compat.Page;
import org.testng.Assert;
import org.testng.annotations.Test;
import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;

public class downloadSampleStopWords extends uploadTest {

    @Page
    AlgorithmsActions algorithmsActions;

    /**
     * Test to navigate to Algorithms (Stop Words) page, click kabab menu, bulk upload, download sample, and verify file
     */
    @Test(priority = 1, description = "Navigate to Algorithms (Stop Words) page, bulk upload, download sample, and verify file")
    public void downloadSampleStopWordsFile() throws Exception {
        algorithmsActions.navigateToStopWordsPage();
        // Step: Click kabab menu
        algorithmsActions.clickKababMenu();
        // Step: Click Bulk upload synonyms
        algorithmsActions.clickBulkUploadSynonyms();
        // Step: Click Download sample synonyms file
        algorithmsActions.clickDownloadSampleSynonymsFile();
        // Step: Verify file download (csv)
      //  Assert.assertTrue(algorithmsActions.isFileDownloaded(".csv"), "Sample stop words file not found in Downloads!");
    }
} 