package UnbxdTests.testNG.consoleui.AlgorithmBulkUploadTest;

import UnbxdTests.testNG.consoleui.MerchBulkUpload.uploadTest;
import core.consoleui.actions.AlgorithmsActions;
import org.fluentlenium.core.annotation.Page;
import org.testng.annotations.Test;
import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import org.testng.annotations.AfterClass;

public class uploadFileSynonyms extends uploadTest {

    @Page
    AlgorithmsActions algorithmsActions;

    /**
     * Test to navigate to Algorithms (Synonyms) page and click kabab menu and bulk upload synonyms
     */
    @Test(priority = 1, description = "Navigate to Algorithms (Synonyms) page and click bulk upload synonyms")
    public void uploadFileSynonymsTest() throws Exception {
        // Step 0: Find algorithm file dynamically using common method
        String algorithmFileName = findBulkUploadFile("algorithm", "synonyms");
        String directory = getBulkUploadDirectory("algorithm");
        System.out.println("Algorithm synonyms upload test using file: " + algorithmFileName + " from " + directory);

        algorithmsActions.navigateToAlgorithmsPage();
        // Step: Click kabab menu
        algorithmsActions.clickKababMenu();
        // Step: Click Bulk upload synonyms
        algorithmsActions.clickBulkUploadSynonyms();
        // Step: Upload synonyms.csv file using common method
        algorithmsActions.uploadSynonymsCSV();
        // Step: Verify upload success
        org.testng.Assert.assertTrue(algorithmsActions.isUploadSuccess(), "Synonyms file upload was not successful!");
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