package UnbxdTests.testNG.consoleui.AlgorithmBulkUploadTest;

import UnbxdTests.testNG.consoleui.MerchBulkUpload.uploadTest;
import core.consoleui.actions.AlgorithmsActions;
import org.fluentlenium.core.annotation.Page;
import org.testng.annotations.Test;
import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import org.testng.annotations.AfterClass;

public class uploadFileStopWords extends uploadTest {

    @Page
    AlgorithmsActions algorithmsActions;

    /**
     * Test to navigate to Algorithms (Stop Words) page and click kabab menu and bulk upload stop words
     */
    @Test(priority = 1, description = "Navigate to Algorithms (Stop Words) page and click bulk upload stop words")
    public void uploadFileStopWordsTest() throws Exception {
        // Step 0: Find algorithm file dynamically using common method
        String algorithmFileName = findBulkUploadFile("algorithm", "stopwords");
        String directory = getBulkUploadDirectory("algorithm");
        System.out.println("Algorithm stop words upload test using file: " + algorithmFileName + " from " + directory);

        algorithmsActions.navigateToStopWordsPage();
        // Step: Click kabab menu
        algorithmsActions.clickKababMenu();
        // Step: Click Bulk upload synonyms (assumed same method for stop words)
        algorithmsActions.clickBulkUploadSynonyms();
        // Step: Upload synonyms.csv file (assumed same method for stop words)
        algorithmsActions.uploadStopwordCSV();
        // Step: Verify upload success
        org.testng.Assert.assertTrue(algorithmsActions.isUploadSuccess(), "Stop Words file upload was not successful!");
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