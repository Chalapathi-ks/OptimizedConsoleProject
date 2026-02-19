package core.consoleui.actions;

import lib.enums.UnbxdEnum;
import lib.Helper;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.File;
import java.net.URL;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;
import core.consoleui.page.PromotionBulkUploadPage;

public class PromotionBulkUploadActions extends PromotionBulkUploadPage {

    @Page
    PromotionBulkUploadPage promotionBulkUploadPage;

    /**
     * Verify if promotions heading is present on the page
     * @return boolean - true if promotions heading is displayed
     */
    public boolean isPromotionsHeadingDisplayed() {
        try {
            threadWait();
            return promotionBulkUploadPage.promotionsHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click on the menu icon to open the dropdown
     */
    public void clickMenuIcon() {
        awaitForElementPresence(promotionBulkUploadPage.menuIcon);
        promotionBulkUploadPage.menuIcon.click();
        threadWait();
    }

    /**
     * Verify if more items dropdown is displayed
     * @return boolean - true if dropdown is displayed
     */
    public boolean isMoreItemsDropdownDisplayed() {
        try {
            awaitForElementPresence(promotionBulkUploadPage.moreItemsDropdown);
            return promotionBulkUploadPage.moreItemsDropdown.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click on export to email option
     */
    public void clickExportToEmailOption() {
        awaitForElementPresence(promotionBulkUploadPage.exportToEmailOption);
        promotionBulkUploadPage.exportToEmailOption.click();
        threadWait();
    }

    /**
     * Click on bulk upload promotions option
     */
    public void clickBulkUploadPromotionsOption() {
        awaitForElementPresence(promotionBulkUploadPage.bulkUploadPromotionsOption);
        promotionBulkUploadPage.bulkUploadPromotionsOption.click();
        threadWait();
    }

    /**
     * Click on download sample promotions file link
     */
    public void clickDownloadSampleLink() {
        awaitForElementPresence(promotionBulkUploadPage.downloadSampleLink);
        promotionBulkUploadPage.downloadSampleLink.click();
        threadWait();
    }

    /**
     * Verify if export success message is displayed
     * @return boolean - true if success message is displayed
     */
    public boolean isExportSuccessMessageDisplayed() {
        try {
            awaitForElementPresence(promotionBulkUploadPage.exportSuccessMessage);
            return promotionBulkUploadPage.exportSuccessMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the text of the export success message
     * @return String - success message text
     */
    public String getExportSuccessMessageText() {
        if (isExportSuccessMessageDisplayed()) {
            return promotionBulkUploadPage.exportSuccessMessage.getText();
        }
        return "";
    }

    /**
     * Check if a file is downloaded in the downloads directory
     * @return boolean - true if any new file is downloaded
     */
    public boolean isFileDownloaded() {
        try {
            // Get the default downloads directory
            String downloadsPath = System.getProperty("user.home") + "/Downloads";
            File downloadsDir = new File(downloadsPath);
            
            if (downloadsDir.exists() && downloadsDir.isDirectory()) {
                // Wait a few seconds for download to complete
                Thread.sleep(3000);
                
                // Check for common file extensions that might be downloaded
                String[] extensions = {".jsonl", ".csv", ".xlsx", ".json", ".txt"};
                
                for (String extension : extensions) {
                    File[] files = downloadsDir.listFiles((dir, name) -> 
                        name.toLowerCase().endsWith(extension.toLowerCase()));
                    if (files != null && files.length > 0) {
                        // Check if any file was modified recently (within last 30 seconds)
                        long currentTime = System.currentTimeMillis();
                        for (File file : files) {
                            if ((currentTime - file.lastModified()) < 30000) { // 30 seconds
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click on upload file button
     */
    public void clickUploadFileButton() {
        awaitForElementPresence(promotionBulkUploadPage.uploadFileLabel);
        promotionBulkUploadPage.uploadFileLabel.click();
        threadWait();
    }

    /**
     * Select file from local machine without opening file browser
     * @param fileName - name of the file to select (without extension)
     */
    public void selectFileFromLocal(String fileName) {
        try {
            String filePath = null;
            // Only look for JSON/JSONL files for promotion uploads
            String[] extensions = {".json", ".jsonl"};
            
            // First, try to find file in project test resources
            try {
                for (String extension : extensions) {
                    String resourcePath = "testData/bulkUploadFiles/" + fileName + extension;
                    URL resourceUrl = getClass().getClassLoader().getResource(resourcePath);
                    if (resourceUrl != null) {
                        filePath = new File(resourceUrl.toURI()).getAbsolutePath();
                        System.out.println("Found JSON file in project resources: " + filePath);
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error accessing project resources: " + e.getMessage());
            }
            
            // If not found in resources, try Downloads folder
            if (filePath == null) {
                String downloadsPath = System.getProperty("user.home") + "/Downloads";
                for (String extension : extensions) {
                    File file = new File(downloadsPath + "/" + fileName + extension);
                    if (file.exists()) {
                        filePath = file.getAbsolutePath();
                        System.out.println("Found JSON file in Downloads: " + filePath);
                        break;
                    }
                }
            }
            
            if (filePath != null) {
                // Wait for the file input element to be present
                awaitForElementPresence(promotionBulkUploadPage.fileInput);
                
                // Directly send the file path to the hidden file input element
                // This bypasses the file browser dialog
                promotionBulkUploadPage.fileInput.getElement().sendKeys(filePath);
                threadWait();
                
                System.out.println("JSON file uploaded successfully: " + filePath);
            } else {
                throw new RuntimeException("JSON file not found: " + fileName + ".json/.jsonl in project resources or Downloads folder");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error selecting JSON file: " + e.getMessage());
        }
    }

    /**
     * Verify if upload report message is displayed
     * @return boolean - true if upload report message is displayed
     */
    public boolean isUploadReportMessageDisplayed() {
        try {
            awaitForElementPresence(promotionBulkUploadPage.uploadReportMessage);
            return promotionBulkUploadPage.uploadReportMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Select CSV file from project directory for banner bulk upload
     * @param fileName - name of the CSV file to select (without extension)
     */
    public void selectCSVFileFromLocal(String fileName) {
        try {
            String filePath = null;
            // Only look for CSV files for banner uploads
            String[] extensions = {".csv"};
            
            // First, try to find file in project test resources
            try {
                for (String extension : extensions) {
                    String resourcePath = "testData/bulkUploadFiles/" + fileName + extension;
                    URL resourceUrl = getClass().getClassLoader().getResource(resourcePath);
                    if (resourceUrl != null) {
                        filePath = new File(resourceUrl.toURI()).getAbsolutePath();
                        System.out.println("Found CSV file in project resources: " + filePath);
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error accessing project resources: " + e.getMessage());
            }
            
            // If not found in resources, try Downloads folder
            if (filePath == null) {
                String downloadsPath = System.getProperty("user.home") + "/Downloads";
                for (String extension : extensions) {
                    File file = new File(downloadsPath + "/" + fileName + extension);
                    if (file.exists()) {
                        filePath = file.getAbsolutePath();
                        System.out.println("Found CSV file in Downloads: " + filePath);
                        break;
                    }
                }
            }
            
            if (filePath != null) {
                // Wait for the file input element to be present
                awaitForElementPresence(promotionBulkUploadPage.fileInput);
                
                // Directly send the file path to the hidden file input element
                // This bypasses the file browser dialog
                promotionBulkUploadPage.fileInput.getElement().sendKeys(filePath);
                threadWait();
                
                System.out.println("CSV file uploaded successfully: " + filePath);
            } else {
                throw new RuntimeException("CSV file not found: " + fileName + ".csv in project resources or Downloads folder");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error selecting CSV file: " + e.getMessage());
        }
    }

    /**
     * Select CSV file from project test data directory for banner bulk upload
     * @param fileName - name of the CSV file to select (without extension)
     * @param directory - subdirectory in testData (e.g., "BulkuploadCSV", "bulkUploadFiles")
     */
    public void selectCSVFileFromTestData(String fileName, String directory) {
        try {
            String filePath = null;
            String[] extensions = {".csv"};
            
            // Get the project root directory
            String projectRoot = System.getProperty("user.dir");
            String testDataPath = projectRoot + "/src/test/resources/testData/" + directory + "/";
            
            // Look for the file in test data directory
            for (String extension : extensions) {
                File file = new File(testDataPath + fileName + extension);
                if (file.exists()) {
                    filePath = file.getAbsolutePath();
                    System.out.println("Found CSV file in test data: " + filePath);
                    break;
                }
            }
            
            if (filePath == null) {
                throw new RuntimeException("CSV file not found in test data: " + fileName + ".csv in " + testDataPath);
            }
            
            // Wait for the file input element to be present
            awaitForElementPresence(promotionBulkUploadPage.fileInput);
            
            // Directly send the file path to the hidden file input element
            // This bypasses the file browser dialog
            promotionBulkUploadPage.fileInput.getElement().sendKeys(filePath);
            threadWait();
            
            System.out.println("CSV file uploaded successfully from test data: " + filePath);
            
        } catch (Exception e) {
            throw new RuntimeException("Error selecting CSV file from test data: " + e.getMessage());
        }
    }

} 