package UnbxdTests.testNG.ui;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.ui.actions.*;
import core.ui.components.CreateSiteComponent;
import core.ui.components.FeedUploadComponent;
import core.ui.page.UiBase;
import lib.Config;
import lib.EnvironmentConfig;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.compat.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static core.ui.page.FeedUploadPage.pageLoader;
import static core.ui.page.UiBase.ThreadWait;
import static lib.enums.UnbxdEnum.MAGENTO_TAB;
import static lib.enums.UnbxdEnum.PLATFORM_UPLOAD;

public class autoMappingTest extends BaseTest {

    @Page
    LoginActions loginActions;

    @Page
    FacetableFieldsActions facetableFieldsActions;

    @Page
    RelevancyPageActions relevancyPageActions;

    @Page
    CreateSiteComponent createSiteComponent;

    @Page
    AutoMappingActions autoMappingActions;

    @Page
    FeedUploadActions feedUploadActions;

    @Page
    CreateSiteActions createSiteActions;

    @Page
    FeedUploadComponent feedUploadComponent;

    List<String> siteKeys = new ArrayList<>();
    private String ssoIdKeyCookie;
    private String csrfCookie;


    @BeforeClass
    public void init() throws InterruptedException {
        super.setUp();
        initFluent(driver);
        initTest();
        // Login is now handled by BaseTest's global login mechanism
        ssoIdKeyCookie = createSiteComponent.getSsoIdCookie();
        csrfCookie = createSiteComponent.getCsrfCookie();
        //facetableFieldsActions.resetRelevancy(EnvironmentConfig.getSiteKey(),ssoIdKeyCookie, Config.getStringValueForProperty("API_UPLOAD_COMPLETE"));
    }

    @FileToTest(value = "autoDimensionMapTest.json")
    @Test(description = "This test Verifies the autoMapped fields in dimension map page", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void autoDimensionMapTest(JsonObject object) throws InterruptedException {
        try {
            //CreateSite
            createSiteComponent.createSite();
            Assert.assertTrue(createSiteActions.awaitForElementPresence(createSiteActions.catalogUploadPageTitle),"Create site is not working!!");
            Assert.assertEquals(createSiteActions.uploadPageText.getText(), "How would you like to upload your catalog today?");

            //Upload feed

            feedUploadActions.selectUploaders(PLATFORM_UPLOAD);
            feedUploadActions.selectPlatform(MAGENTO_TAB);
            String siteKey = createSiteActions.getCreatedSiteKey();
            siteKeys.add(siteKey);
            String secreteKey = createSiteActions.getSecreteKey();

            feedUploadActions.apiUpload(siteKey, secreteKey);
            feedUploadActions.clickOnProceedButton();
            feedUploadActions.waitForFeedUploadToComplete();
            ThreadWait();
            Assert.assertTrue(feedUploadActions.successMsg.getText().equalsIgnoreCase("Feed Upload is Successful"));

            JsonArray expectedMappedFieldList = object.get("mappedfields").getAsJsonArray();
            List<String> expectedDimensionMappedField = new ArrayList<>();
            for (int i = 0; i < expectedMappedFieldList.size(); i++) {
                expectedDimensionMappedField.add(expectedMappedFieldList.get(i).getAsString());
            }

            JsonArray fieldsList = object.get("fields").getAsJsonArray();
            List<String> fields = new ArrayList<>();
            for (int i = 0; i < fieldsList.size(); i++) {
                fields.add(fieldsList.get(i).getAsString());
            }

            //goto Mapping page
            //goTo(feedUploadComponent.getRelevancyPage());
            await();
            click(autoMappingActions.mapCatalogButton);
            facetableFieldsActions.waitForLoaderToDisAppear(facetableFieldsActions.relevancyPageLoader, facetableFieldsActions.relevancePageLoader);
            facetableFieldsActions.waitForElementAppear(autoMappingActions.setUpSearchButtons, autoMappingActions.mappingPageLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
            Assert.assertTrue(autoMappingActions.awaitForElementPresence(autoMappingActions.setUpSearchButton),"Mapping page is not yet loaded!!");
            Assert.assertEquals(autoMappingActions.setUpSearchButton.getText(), "Set Up Search");
            Thread.sleep(15000);

            List<String> ActualMappedFields = new ArrayList<>();
            for (String field : fields) {
                String actualMappedField = autoMappingActions.getMappedFieldUsingDisplayName(field);
                ActualMappedFields.add(actualMappedField);
            }
            Assert.assertEquals(ActualMappedFields, expectedDimensionMappedField);
        } catch (NoSuchElementException exception) {
            System.out.println("There is no such element present");
            Assert.fail("No such element failure" + exception);
        } catch (Exception e) {
            Assert.fail("autoDimensionMapTestCase got failed" + e);
        }
    }



    @AfterClass(alwaysRun = true)
    public void deleteSites()
    {
        for(String siteKey:siteKeys)
        {
            createSiteActions.deleteSite(siteKey,ssoIdKeyCookie,csrfCookie);
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.close();
        driver.quit();
        Helper.tearDown();
    }

}
