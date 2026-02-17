package core.consoleui.actions;

import core.consoleui.page.AlgorithmsPage;
import org.fluentlenium.core.annotation.Page;
import java.io.File;
import static lib.UrlMapper.SYNONYMS;
import static lib.UrlMapper.PHRASES;
import static lib.UrlMapper.STEMMING;
import static lib.UrlMapper.CONCEPTS;
import static lib.UrlMapper.STOP_WORD;
import lib.EnvironmentConfig;

public class AlgorithmsActions extends AlgorithmsPage {
    @Page
    public AlgorithmsPage algorithmsPage;

    /**
     * Navigate to Algorithms (Synonyms) page
     */
    public void navigateToAlgorithmsPage() {
        String url = SYNONYMS.getBaseUrl(EnvironmentConfig.getSiteId());
        goTo(url);
        awaitForPageToLoad();
    }

    /**
     * Navigate to Algorithms (Phrases) page using direct URL construction
     */
    public void navigateToPhrasesPage() {
        String phrasesUrl = PHRASES.getBaseUrl(EnvironmentConfig.getSiteId());
        getDriver().get(phrasesUrl);
        awaitForPageToLoad();
    }

    /**
     * Navigate to Algorithms (Stemming) page using direct URL construction
     */
    public void navigateToStemmingPage() {
        String stemmingUrl = STEMMING.getBaseUrl(EnvironmentConfig.getSiteId());
        getDriver().get(stemmingUrl);
        awaitForPageToLoad();
    }

    /**
     * Navigate to Algorithms (Concepts) page using direct URL construction
     */
    public void navigateToConceptsPage() {
        String conceptsUrl = CONCEPTS.getBaseUrl(EnvironmentConfig.getSiteId());
        getDriver().get(conceptsUrl);
        awaitForPageToLoad();
    }

    /**
     * Navigate to Algorithms (Stop Words) page using direct URL construction
     */
    public void navigateToStopWordsPage() {
        String stopWordsUrl = STOP_WORD.getBaseUrl(EnvironmentConfig.getSiteId());
        getDriver().get(stopWordsUrl);
        awaitForPageToLoad();
    }

    /**
     * Click the Bulk Download Synonyms option
     */
    public void clickBulkDownloadSynonyms() {
        try {
            // Debug: Screenshot before click
            File beforeClick = ((org.openqa.selenium.TakesScreenshot) getDriver()).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            org.openqa.selenium.io.FileHandler.copy(beforeClick, new File("before_bulk_download_click.png"));

            // Debug: Print visibility, enabled status, and text
            org.openqa.selenium.WebElement element = algorithmsPage.bulkDownloadSynonymsLink.getElement();
            System.out.println("[DEBUG] isDisplayed: " + element.isDisplayed());
            System.out.println("[DEBUG] isEnabled: " + element.isEnabled());
            System.out.println("[DEBUG] Text: '" + element.getText() + "'");

            // Try normal click
            try {
                element.click();
                System.out.println("Bulk download synonyms clicked (normal click)");
            } catch (Exception e) {
                // Fallback: JavaScript click
                System.out.println("Normal click failed, trying JavaScript click: " + e.getMessage());
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
                System.out.println("Bulk download synonyms clicked (JavaScript click)");
            }

            // Debug: Screenshot after click
            File afterClick = ((org.openqa.selenium.TakesScreenshot) getDriver()).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            org.openqa.selenium.io.FileHandler.copy(afterClick, new File("after_bulk_download_click.png"));
        } catch (Exception e) {
            System.out.println("Failed to click bulk download synonyms: " + e.getMessage());
            try {
                File errorShot = ((org.openqa.selenium.TakesScreenshot) getDriver()).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
                org.openqa.selenium.io.FileHandler.copy(errorShot, new File("error_bulk_download_click.png"));
            } catch (Exception ex) {
                System.out.println("Failed to take error screenshot: " + ex.getMessage());
            }
        }
    }

    /**
     * Click the Bulk Upload Synonyms option
     */
    public void clickBulkUploadSynonyms() {
        try {
            algorithmsPage.bulkUploadSynonymsMenuItem.click();
            System.out.println("Bulk upload synonyms clicked");
        } catch (Exception e) {
            System.out.println("Failed to click bulk upload synonyms: " + e.getMessage());
        }
    }

    /**
     * Click the Download Sample Synonyms File link
     */
    public void clickDownloadSampleSynonymsFile() {
        try {
            algorithmsPage.downloadSampleSynonymsLink.click();
            System.out.println("Download sample synonyms file clicked");
        } catch (Exception e) {
            System.out.println("Failed to click download sample synonyms file: " + e.getMessage());
        }
    }

    /**
     * Resolve default Downloads directory path across OS (Windows/Mac/Linux)
     */
    private String resolveDownloadsPath() {
        String os = System.getProperty("os.name", "").toLowerCase();
        String userHome = System.getProperty("user.home", "");
        if (os.contains("win")) {
            String userProfile = System.getenv("USERPROFILE");
            String base = (userProfile != null && !userProfile.isEmpty()) ? userProfile : userHome;
            return base + File.separator + "Downloads";
        }
        // Mac and Linux default to ~/Downloads
        return userHome + File.separator + "Downloads";
    }

    /**
     * Verify if a file with the given extension is downloaded in the user's default Downloads directory.
     * Works for Mac, Windows, and typical Linux desktops when the browser is running locally.
     * For remote Grid runs, prefer asserting via DevTools events or Grid-specific download endpoints.
     */
    public boolean isFileDownloaded(String extension) {
        // If running against a remote grid with managed downloads, check via /se/files
        try {
            org.openqa.selenium.WebDriver drv = getDriver();
            if (drv instanceof org.openqa.selenium.remote.RemoteWebDriver) {
                org.openqa.selenium.remote.RemoteWebDriver rwd = (org.openqa.selenium.remote.RemoteWebDriver) drv;
                String hub = System.getProperty("hubUrl");
                if (hub != null && !hub.isEmpty()) {
                    String base = hub.replaceAll("/wd/hub/?$", "");
                    String endpoint = base + "/session/" + rwd.getSessionId() + "/se/files";
                    java.net.HttpURLConnection conn = (java.net.HttpURLConnection) new java.net.URL(endpoint).openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(30000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        java.io.InputStream is = conn.getInputStream();
                        java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
                        byte[] buf = new byte[4096];
                        int len;
                        while ((len = is.read(buf)) != -1) bos.write(buf, 0, len);
                        String body = new String(bos.toByteArray(), java.nio.charset.StandardCharsets.UTF_8);
                        String needle = extension.toLowerCase();
                        if (body.toLowerCase().contains(needle)) {
                            System.out.println("Found downloaded file on grid (by extension '" + extension + ")");
                            return true;
                        }
                    } else {
                        System.out.println("Grid /se/files returned HTTP " + code);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Grid managed download check failed, falling back to local check: " + e.getMessage());
        }

        String downloadsPath = resolveDownloadsPath();
        File downloadsDir = new File(downloadsPath);
        long timeout = 60_000; // 60 seconds
        long pollInterval = 1_000; // 1 second
        long startTime = System.currentTimeMillis();

        System.out.println("Checking downloads in: " + downloadsDir.getAbsolutePath());

        while (System.currentTimeMillis() - startTime < timeout) {
            File[] files = downloadsDir.listFiles();
            long now = System.currentTimeMillis();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().toLowerCase().endsWith(extension) && now - file.lastModified() < timeout) {
                        System.out.println("Downloaded file found: " + file.getName());
                        return true;
                    }
                }
            }
            try {
                Thread.sleep(pollInterval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        System.out.println("No downloaded file with extension " + extension + " found in last " + (timeout / 1000) + " seconds at " + downloadsDir.getAbsolutePath());
        return false;
    }

    /**
     * Upload the synonyms.csv file using the file input element
     */
    public void uploadSynonymsCSV() {
        uploadCSVFile("synonyms.csv");
    }

    public void uploadStopwordCSV() {
        uploadCSVFile("bulkUploadStopwordsData.csv");
    }

    /**
     * Upload the phrases CSV file using the file input element
     */
    public void uploadPhrasesCSV() {
        uploadCSVFile("bulkUploadPhrasesData.csv");
    }

    /**
     * Upload the stemming CSV file using the file input element
     */
    public void uploadStemmingCSV() {
        uploadCSVFile("bulkUploadStemmingData.csv");
    }

    /**
     * Upload the concepts CSV file using the file input element
     */
    public void uploadConceptsCSV() {
        uploadCSVFile("bulkUploadConceptsData.csv");
    }

    /**
     * Upload the stopwords CSV file using the file input element
     */
    public void uploadStopwordsCSV() {
        uploadCSVFile("bulkUploadStopwordsData.csv");
    }

    /**
     * Generic method to upload CSV files using the file input element
     */
    private void uploadCSVFile(String fileName) {
        try {
            String resourcePath = "testData/BulkuploadCSV/" + fileName;
            java.net.URL resourceUrl = getClass().getClassLoader().getResource(resourcePath);
            if (resourceUrl == null) {
                throw new RuntimeException(fileName + " not found in resources: " + resourcePath);
            }
            String filePath = new File(resourceUrl.toURI()).getAbsolutePath();
            awaitForElementPresence(algorithmsPage.uploadFileInput);
            algorithmsPage.uploadFileInput.getElement().sendKeys(filePath);
            System.out.println("Successfully uploaded file: " + fileName);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading " + fileName + ": " + e.getMessage(), e);
        }
    }

    /**
     * Verify if the file upload was successful by checking for a success message
     */
    public boolean isUploadSuccess() {
        try {
            awaitForElementPresence(algorithmsPage.successMessage);
            String message = algorithmsPage.successMessage.getText();
            return message != null && !message.trim().isEmpty() && message.toLowerCase().contains("upload");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clicks the kabab menu icon on the algorithms page with wait and debug screenshot.
     */
    public void clickKababMenu() {
        try {
            // Wait for the kabab menu icon to be present and visible
            awaitForElementPresence(algorithmsPage.kababMenuIcon);
            // Take screenshot before clicking
            File beforeClick = ((org.openqa.selenium.TakesScreenshot) getDriver()).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            org.openqa.selenium.io.FileHandler.copy(beforeClick, new File("before_kabab_click.png"));
            // Click the kabab menu icon
            algorithmsPage.kababMenuIcon.click();
            System.out.println("Kabab menu icon clicked");
        } catch (Exception e) {
            System.out.println("Failed to click kabab menu: " + e.getMessage());
            try {
                File errorShot = ((org.openqa.selenium.TakesScreenshot) getDriver()).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
                org.openqa.selenium.io.FileHandler.copy(errorShot, new File("error_kabab_click.png"));
            } catch (Exception ex) {
                System.out.println("Failed to take error screenshot: " + ex.getMessage());
            }
        }
    }

    // TODO: Add more action methods for interacting with Algorithms page
} 