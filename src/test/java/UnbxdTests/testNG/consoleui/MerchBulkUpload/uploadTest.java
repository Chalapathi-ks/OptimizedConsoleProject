package UnbxdTests.testNG.consoleui.MerchBulkUpload;

import UnbxdTests.testNG.ui.BaseTest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.actions.MerchandisingActions;
import core.consoleui.page.CommerceSearchPage;
import core.ui.actions.LoginActions;
import lib.Helper;
import lib.enums.UnbxdEnum;
import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import lib.EnvironmentConfig;
import static core.ui.page.UiBase.ThreadWait;

import java.io.File;


public class uploadTest extends BaseTest {


    @Page
    public
    CommercePageActions searchPage;

    @Page
    public
    MerchandisingActions merchandisingActions;

    @Page
    public
    CommercePageActions BrowsePage;


    @BeforeClass(groups={"sanity"})
    public void setUp() {
        super.setUp();
        this.initFluent(driver);
        initTest();
        
        // Debug: Print environment profile
        try {
            System.out.println("🔍 Debug: Environment Profile = " + lib.Config.getEnvironmentProfile());
            System.out.println("🔍 Debug: Application URL = " + lib.EnvironmentConfig.getApplicationUrl());
            System.out.println("🔍 Debug: Base URL = " + lib.EnvironmentConfig.getBaseUrl());
        } catch (Exception e) {
            System.err.println("❌ Error getting configuration: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Clear existing context and set new context for site 1 and user 1 as per ProdAPAC.yaml configuration
        EnvironmentConfig.unSetContext();
        EnvironmentConfig.setContext(2, 2);
        // Login is now handled by BaseTest's global login mechanism
       // loginActions.unbxdLogin(2,2);
        goTo(searchPage);
        ThreadWait();
    }



    protected String findBulkUploadFile(String testType, String pageType) {
        try {
            String projectRoot = System.getProperty("user.dir");

            // Define search directories in order of preference
            String[] directories = {"BulkuploadCSV", "bulkUploadFiles"};

            for (String directory : directories) {
                String testDataPath = projectRoot + "/src/test/resources/testData/" + directory + "/";
                File dir = new File(testDataPath);

                if (dir.exists() && dir.isDirectory()) {
                    File[] files = dir.listFiles((dir1, name) -> {
                        String lowerName = name.toLowerCase();
                        String lowerTestType = testType.toLowerCase();
                        String lowerPageType = pageType.toLowerCase();

                        // Handle Algorithm test cases - they all use synonyms.csv
                        if (testType.equalsIgnoreCase("algorithm")) {
                            return lowerName.contains("synonyms") && lowerName.endsWith(".csv");
                        }

                        // Check if file contains both test type and page type keywords
                        boolean containsTestType = lowerName.contains(lowerTestType);
                        boolean containsPageType = lowerName.contains(lowerPageType);

                        // Handle special cases for different file types
                        if (testType.equalsIgnoreCase("promotion")) {
                            // Promotions can be JSONL files
                            return containsTestType && containsPageType &&
                                    (lowerName.endsWith(".jsonl") || lowerName.endsWith(".csv"));
                        } else {
                            // Other types are typically CSV files
                            return containsTestType && containsPageType && lowerName.endsWith(".csv");
                        }
                    });

                    if (files != null && files.length > 0) {
                        String fileName = files[0].getName();
                        // Remove file extension
                        return fileName.substring(0, fileName.lastIndexOf('.'));
                    }
                }
            }

            // Return null if no file found
            return null;

        } catch (Exception e) {
            System.err.println("Error finding " + testType + " " + pageType + " file: " + e.getMessage());
            return null;
        }
    }

    protected String getBulkUploadDirectory(String testType) {
        if (testType.equalsIgnoreCase("promotion")) {
            return "bulkUploadFiles"; // Promotions are in bulkUploadFiles
        } else if (testType.equalsIgnoreCase("searchable")) {
            return "bulkUploadFiles"; // Searchable fields are in bulkUploadFiles
        } else if (testType.equalsIgnoreCase("algorithm")) {
            return "BulkuploadCSV"; // Algorithm files are in BulkuploadCSV
        } else {
            return "BulkuploadCSV"; // Other types are in BulkuploadCSV
        }
    }


    public void createPromotion(String query,boolean bannerOrFacet,boolean queryBasedBanner) throws InterruptedException {
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        if(searchPage.queryRuleByName(query)!=null) {
            searchPage.deleteQueryRule(query);
            if (bannerOrFacet == true) {
                searchPage.clickOnAddRule(bannerOrFacet);
                if (queryBasedBanner == true) {
                    searchPage.goToQueryBasedBanner();
                } else {
                    searchPage.goToFieldRuleBasedBanner();
                }
            } else {
                searchPage.threadWait();
                searchPage.clickOnAddRule(bannerOrFacet);
                if (searchPage.awaitForElementPresence(searchPage.newQueryRuleInput) == true) {
                    searchPage.fillQueryRuleData(query, null);
                }
            }
        }
        else
        {
            if(bannerOrFacet==true)
            {
                searchPage.clickOnAddRule(bannerOrFacet);
                if(queryBasedBanner==true) {
                    searchPage.goToQueryBasedBanner();
                }else{
                    searchPage.goToFieldRuleBasedBanner();
                }
            }
            else {
                searchPage.clickOnAddRule(bannerOrFacet);
                searchPage.threadWait();
                if (searchPage.awaitForElementPresence(searchPage.newQueryRuleInput) == true) {
                    searchPage.fillQueryRuleData(query, null);
                }
            }
        }
    }

    public void createBrowsePromotion(String page,boolean bannerOrFacet,boolean queryBasedBanner) throws InterruptedException {
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        if(searchPage.queryRuleByName(page)!=null) {
            searchPage.deleteQueryRule(page);
            if (bannerOrFacet == true) {
                searchPage.clickOnAddRule(bannerOrFacet);
                if(queryBasedBanner==true) {
                    searchPage.goToFieldRuleBasedBanner();
                }else{
                    searchPage.goToQueryBasedBanner();
                }
            } else {
                searchPage.clickOnAddRule(bannerOrFacet);
                searchPage.fillQueryRuleData(null,page);
            }
        }
        else
        {
            if(bannerOrFacet==true)
            {
                searchPage.clickOnAddRule(bannerOrFacet);
                if(queryBasedBanner==true) {
                    searchPage.goToFieldRuleBasedBanner();
                }else{
                    searchPage.goToQueryBasedBanner();
                }
            }
            else {
                searchPage.clickOnAddRule(bannerOrFacet);
                searchPage.threadWait();
                searchPage.fillQueryRuleData(null,page);
            }
        }
    }
    @AfterClass
    public void tearDown()
    {
        driver.close();
        driver.quit();
        Helper.tearDown();
    }

    @AfterSuite(alwaysRun = true)
    public void close() {
        if(getDriver() != null) {
            getDriver().quit();
        }
    }

    public String getPreviousDate(int day)
    {
        return "some string";
    }
}
