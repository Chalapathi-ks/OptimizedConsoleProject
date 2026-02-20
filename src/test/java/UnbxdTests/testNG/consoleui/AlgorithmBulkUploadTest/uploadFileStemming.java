package UnbxdTests.testNG.consoleui.AlgorithmBulkUploadTest;

import UnbxdTests.testNG.consoleui.MerchBulkUpload.uploadTest;
import core.consoleui.actions.AlgorithmsActions;
import lib.compat.Page;
import org.testng.annotations.Test;
import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import org.testng.annotations.AfterClass;

public class uploadFileStemming extends uploadTest {

    @Page
    AlgorithmsActions algorithmsActions;

    /**
     * Test to navigate to Algorithms (Stemming) page and click kabab menu and bulk upload stemming
     */
    @Test(priority = 1, description = "Navigate to Algorithms (Stemming) page and click bulk upload stemming")
    public void uploadFileStemmingTest() throws Exception {
        // Step 0: Find algorithm file dynamically using common method
        String algorithmFileName = findBulkUploadFile("algorithm", "stemming");
        String directory = getBulkUploadDirectory("algorithm");
        System.out.println("Algorithm stemming upload test using file: " + algorithmFileName + " from " + directory);

        algorithmsActions.navigateToStemmingPage();
        // Step: Click kabab menu
        algorithmsActions.clickKababMenu();
        // Step: Click Bulk upload synonyms (assumed same method for stemming)
        algorithmsActions.clickBulkUploadSynonyms();
        // Step: Upload stemming CSV file
        algorithmsActions.uploadStemmingCSV();
        // Step: Verify upload success
        org.testng.Assert.assertTrue(algorithmsActions.isUploadSuccess(), "Stemming file upload was not successful!");
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