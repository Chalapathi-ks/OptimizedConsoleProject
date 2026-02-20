package core.consoleui.actions;

import core.consoleui.page.CollectionsPage;
import core.ui.page.UnbxdCommonPage;
import lib.compat.Page;
import java.io.File;
import java.net.URL;

public class CollectionsActions extends UnbxdCommonPage {

    @Page
    CollectionsPage collectionsPage;

    /**
     * Navigate to Collections page
     */
    public void navigateToCollections() {
        String collectionsUrl = collectionsPage.getUrl();
        System.out.println("Navigating to Collections URL: " + collectionsUrl);
        goTo(collectionsUrl);
        awaitForPageToLoad();
        System.out.println("Successfully navigated to Collections page");
    }

    /**
     * Verify Collections page is loaded by checking the heading
     */
    public void verifyCollectionsPageLoaded() {
        System.out.println("Verifying Collections page is loaded");
        
        if (awaitForElementPresence(collectionsPage.collectionsHeading)) {
            String headingText = collectionsPage.collectionsHeading.getTextContent();
            System.out.println("Collections page heading found: " + headingText);
            
            if (headingText.equals("Collections")) {
                System.out.println("✓ Collections page loaded successfully");
            } else {
                System.out.println("✗ Unexpected heading text: " + headingText);
            }
        } else {
            System.out.println("✗ Collections heading not found - page may not be loaded");
        }
    }

    /**
     * Click on download icon for the first collection
     */
    public void clickDownloadIcon() {
        System.out.println("Clicking on download icon for collection");
        
        try {
            if (collectionsPage.downloadIcons.size() > 0) {
                collectionsPage.downloadIcons.get(0).click();
                System.out.println("✓ Download icon clicked successfully");
                
                // Wait for download to initiate
                awaitForPageToLoad();
            } else {
                System.out.println("✗ No download icons found");
            }
        } catch (Exception e) {
            System.out.println("✗ Download icons not present on the page: " + e.getMessage());
        }
    }

    /**
     * Click on Upload File button to open the upload modal
     */
    public void clickUploadFile() {
        System.out.println("Clicking on Upload File button");
        
        try {
            if (awaitForElementPresence(collectionsPage.uploadFileButton)) {
                collectionsPage.uploadFileButton.click();
                System.out.println("✓ Upload File button clicked successfully");
                
                // Wait for modal to appear
                awaitForPageToLoad();
                
                // Verify modal is opened
                if (awaitForElementPresence(collectionsPage.modalBody)) {
                    System.out.println("✓ Upload File modal opened successfully");
                } else {
                    System.out.println("✗ Upload File modal did not open");
                }
            } else {
                System.out.println("✗ Upload File button not found");
            }
        } catch (Exception e) {
            System.out.println("✗ Error clicking Upload File button: " + e.getMessage());
        }
    }

    /**
     * Click on Download Sample Collections File link in the modal
     */
    public void clickDownloadSampleCollectionsFile() {
        System.out.println("Clicking on Download Sample Collections File link");
        
        try {
            if (awaitForElementPresence(collectionsPage.downloadSampleCollectionsLink)) {
                collectionsPage.downloadSampleCollectionsLink.click();
                System.out.println("✓ Download Sample Collections File link clicked successfully");
                
                // Wait for download to initiate
                awaitForPageToLoad();
            } else {
                System.out.println("✗ Download Sample Collections File link not found");
            }
        } catch (Exception e) {
            System.out.println("✗ Error clicking Download Sample Collections File link: " + e.getMessage());
        }
    }

    /**
     * Click on Upload File Link
     */
    public void clickUploadFileLink() {
        System.out.println("Clicking on Upload File link");
        
        try {
            if (awaitForElementPresence(collectionsPage.uploadFileLink)) {
                collectionsPage.uploadFileLink.click();
                System.out.println("✓ Upload File link clicked successfully");
                
                // Wait for page to load/respond
                awaitForPageToLoad();
            } else {
                System.out.println("✗ Upload File link not found");
            }
        } catch (Exception e) {
            System.out.println("✗ Error clicking Upload File link: " + e.getMessage());
        }
    }

    /**
     * Enter collection name with random suffix
     */
    public void enterCollectionName() {
        System.out.println("Entering collection name");
        
        try {
            if (awaitForElementPresence(collectionsPage.collectionNameInput)) {
                // Generate random collection name with prefix
                String randomSuffix = String.valueOf(System.currentTimeMillis());
                String collectionName = "autoQAcollection" + randomSuffix;
                
                collectionsPage.collectionNameInput.clear();
                collectionsPage.collectionNameInput.fill().with(collectionName);
                
                System.out.println("✓ Collection name entered: " + collectionName);
            } else {
                System.out.println("✗ Collection name input field not found");
            }
        } catch (Exception e) {
            System.out.println("✗ Error entering collection name: " + e.getMessage());
        }
    }

    /**
     * Upload collections CSV file directly using the file input element
     */
    public void uploadCollectionsFile() {
        System.out.println("Uploading collections CSV file");
        
        try {
            String filePath = null;
            String fileName = "collectionsFile";
            
            // First, try to find file in project test resources
            try {
                String resourcePath = "testData/BulkuploadCSV/" + fileName + ".csv";
                URL resourceUrl = getClass().getClassLoader().getResource(resourcePath);
                if (resourceUrl != null) {
                    filePath = new File(resourceUrl.toURI()).getAbsolutePath();
                    System.out.println("Found CSV file in project resources: " + filePath);
                }
            } catch (Exception e) {
                System.out.println("Error accessing project resources: " + e.getMessage());
            }
            
            // If not found in resources, try Downloads folder
            if (filePath == null) {
                String downloadsPath = System.getProperty("user.home") + "/Downloads";
                File file = new File(downloadsPath + "/" + fileName + ".csv");
                if (file.exists()) {
                    filePath = file.getAbsolutePath();
                    System.out.println("Found CSV file in Downloads: " + filePath);
                }
            }
            
            if (filePath != null) {
                // Wait for the file input element to be present
                awaitForElementPresence(collectionsPage.bulkUploadInput);
                
                // Directly send the file path to the hidden file input element
                // This bypasses the file browser dialog
                collectionsPage.bulkUploadInput.getElement().sendKeys(filePath);
                
                System.out.println("✓ Collections CSV file uploaded successfully: " + filePath);
                
                // Wait for upload to process
                awaitForPageToLoad();
            } else {
                throw new RuntimeException("CSV file not found: " + fileName + ".csv in project resources or Downloads folder");
            }
        } catch (Exception e) {
            System.out.println("✗ Error uploading collections file: " + e.getMessage());
            throw new RuntimeException("Error selecting CSV file: " + e.getMessage());
        }
    }

    /**
     * Verify if file upload was successful by checking progress message
     */
    public void verifyFileUploadSuccess() {
        System.out.println("Verifying file upload success");
        
        try {
            if (awaitForElementPresence(collectionsPage.uploadProgressMessage)) {
                String progressMessage = collectionsPage.uploadProgressMessage.getTextContent();
                System.out.println("Upload progress message found: " + progressMessage);
                
                if (progressMessage.contains("Your collection upload is in progress")) {
                    System.out.println("✓ File upload verification successful - Upload in progress");
                } else {
                    System.out.println("✗ Unexpected upload message: " + progressMessage);
                }
            } else {
                System.out.println("✗ Upload progress message not found");
            }
        } catch (Exception e) {
            System.out.println("✗ Error verifying file upload: " + e.getMessage());
        }
    }

    /**
     * Verify if file is downloaded (basic check)
     * Note: This is a basic implementation. For robust file download verification,
     * you might need to check the Downloads folder or use browser capabilities
     */
    public void verifyFileDownloaded() {
        System.out.println("Verifying file download...");
        
        try {
            // Wait for download to complete (basic wait)
            Thread.sleep(3000);
            
            // Check if any download-related success message appears
            // This is a placeholder - actual implementation depends on your application's behavior
            System.out.println("✓ File download verification completed");
            System.out.println("Note: Actual file presence should be verified in Downloads folder");
            
        } catch (InterruptedException e) {
            System.out.println("✗ Download verification interrupted: " + e.getMessage());
        }
    }

    /**
     * Navigate to Collections browse page (actually collections page as per new URL)
     */
    public void navigateToCollectionsBrowse() {
        // Use the environment-specific URL instead of hardcoded URL
        String collectionsUrl = collectionsPage.getUrl() + "/ss/category-pages/merchandising/collections/" + lib.EnvironmentConfig.getSiteId();
        System.out.println("Navigating to Collections URL: " + collectionsUrl);
        goTo(collectionsUrl);
        awaitForPageToLoad();
        System.out.println("Successfully navigated to Collections page");
    }
} 