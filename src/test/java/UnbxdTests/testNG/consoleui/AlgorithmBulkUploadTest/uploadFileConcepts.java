package UnbxdTests.testNG.consoleui.AlgorithmBulkUploadTest;

import UnbxdTests.testNG.consoleui.MerchBulkUpload.uploadTest;
import core.consoleui.actions.AlgorithmsActions;
import lib.compat.Page;
import org.testng.annotations.Test;
import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import org.testng.annotations.AfterClass;

public class uploadFileConcepts extends uploadTest {

    @Page
    AlgorithmsActions algorithmsActions;

    /**
     * Test to navigate to Algorithms (Concepts) page and click kabab menu and bulk upload concepts
     */
    @Test(priority = 1, description = "Navigate to Algorithms (Concepts) page and click bulk upload concepts")
    public void uploadFileConceptsTest() throws Exception {
        // Step 0: Find algorithm file dynamically using common method
        String algorithmFileName = findBulkUploadFile("algorithm", "concepts");
        String directory = getBulkUploadDirectory("algorithm");
        System.out.println("Algorithm concepts upload test using file: " + algorithmFileName + " from " + directory);

        algorithmsActions.navigateToConceptsPage();
        // Step: Click kabab menu
        algorithmsActions.clickKababMenu();
        // Step: Click Bulk upload synonyms (assumed same method for concepts)
        algorithmsActions.clickBulkUploadSynonyms();
        // Step: Upload concepts CSV file
        algorithmsActions.uploadConceptsCSV();
        Thread.sleep(2000);
        // Step: Verify upload success
        org.testng.Assert.assertTrue(algorithmsActions.isUploadSuccess(), "Concepts file upload was not successful!");
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