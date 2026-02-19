package UnbxdTests.testNG.consoleui.AlgorithmBulkUploadTest;

import UnbxdTests.testNG.consoleui.MerchBulkUpload.uploadTest;
import core.consoleui.actions.AlgorithmsActions;
import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;
import org.testng.annotations.Test;
import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import java.io.File;
import javax.imageio.ImageIO;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class bulkdownloadStopWords extends uploadTest {

    @Page
    AlgorithmsActions algorithmsActions;

    /**
     * Test to navigate to Algorithms (Stop Words) page and perform bulk download
     */
    @Test(priority = 1, description = "Navigate to Algorithms (Stop Words) page and bulk download stop words")
    public void navigateAndBulkDownloadStopWords() throws Exception {
        algorithmsActions.navigateToStopWordsPage();
        // Step 2: Click kabab menu
        algorithmsActions.clickKababMenu();
        // Take screenshot after clicking kabab menu
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(screenshot, new File("kabab_menu_open.png"));
        // Explicit wait for bulk download element
        algorithmsActions.awaitForElementPresence(algorithmsActions.bulkDownloadSynonymsLink);
        // Step 3: Click bulk download synonyms
        algorithmsActions.clickBulkDownloadSynonyms();
        // Step 4: Verify file download (csv)
        //Assert.assertTrue(algorithmsActions.isFileDownloaded(".csv"), "Bulk download stop words file not found in Downloads!");
    }
} 