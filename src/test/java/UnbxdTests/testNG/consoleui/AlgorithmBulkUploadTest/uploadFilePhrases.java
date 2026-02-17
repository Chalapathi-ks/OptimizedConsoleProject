package UnbxdTests.testNG.consoleui.AlgorithmBulkUploadTest;

import UnbxdTests.testNG.consoleui.MerchBulkUpload.uploadTest;
import core.consoleui.actions.AlgorithmsActions;
import org.fluentlenium.core.annotation.Page;
import org.testng.annotations.Test;
import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import org.testng.annotations.AfterClass;

public class uploadFilePhrases extends uploadTest {

    @Page
    AlgorithmsActions algorithmsActions;

    /**
     * Test to navigate to Algorithms (Phrases) page and click kabab menu and bulk upload phrases
     */
    @Test(priority = 1, description = "Navigate to Algorithms (Phrases) page and click bulk upload phrases")
    public void uploadFilePhrasesTest() throws Exception {
        // Step 0: Find algorithm file dynamically using common method
        String algorithmFileName = findBulkUploadFile("algorithm", "phrases");
        String directory = getBulkUploadDirectory("algorithm");
        System.out.println("Algorithm phrases upload test using file: " + algorithmFileName + " from " + directory);

        algorithmsActions.navigateToPhrasesPage();
        // Step: Click kabab menu
        algorithmsActions.clickKababMenu();
        // Step: Click Bulk upload synonyms (assumed same method for phrases)
        algorithmsActions.clickBulkUploadSynonyms();
        // Step: Upload phrases CSV file
        algorithmsActions.uploadPhrasesCSV();
        Thread.sleep(2000);
        // Step: Verify upload success
        org.testng.Assert.assertTrue(algorithmsActions.isUploadSuccess(), "Phrases file upload was not successful!");
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