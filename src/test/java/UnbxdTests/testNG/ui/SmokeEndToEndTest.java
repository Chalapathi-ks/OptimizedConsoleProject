package UnbxdTests.testNG.ui;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.ui.actions.*;
import core.ui.components.CreateSiteComponent;
import lib.Config;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import lib.compat.Page;
import lib.compat.FluentWebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static lib.enums.UnbxdEnum.MAGENTO_TAB;
import static lib.enums.UnbxdEnum.PLATFORM_UPLOAD;

public class SmokeEndToEndTest extends BaseTest
{
    @Page
    LoginActions loginActions;

    @Page
    CreateSiteActions createSiteActions;

    @Page
    CreateSiteComponent createSiteComponent;

    @Page
    FeedUploadActions feedUploadActions;

    @Page
    AutoMappingActions autoMappingActions;

    @Page
    FacetableFieldsActions facetableFieldsActions;

    @Page
    AutoSuggestActions autoSuggestActions;

    @Page
    SearchableFieldActions searchableFieldActions;

    @Page
    ContentRelevanceActions contentRelevanceActions;

    @Page
    PreviewActions previewActions;

    private String facet = "FACETABLE_FIELD";
    private String contentSection = "DICTIONARY";
    private String autoSuggestSection = "AUTO_SUGGEST";

    List<String> siteKeys=new ArrayList<>();

    private String ssoIdKeyCookie ;
    private String csrfCookie;


    @BeforeClass
    public void init() {
        super.setUp();
        initFluent(driver);
        initTest();
        System.out.println("driver is: " +driver.toString());
        // Login is now handled by BaseTest's global login mechanism
    }

    @FileToTest(value = "SmokeEndToEndTest.json")
    @Test(description = "This testcase will verify API end to end flow", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void smokeTestFlows(JsonObject object) throws InterruptedException {

        //InputData :
        //Auto Dimension mapping data:
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

        //AiRecommended searchable fields data:
        JsonArray fieldsList1 = object.get("searchableFields").getAsJsonArray();
        String ExpectedSearchWeight = object.get("ExpectedSearchWeight").getAsString();

        List<String> fields1 = new ArrayList<>();
        for (int i = 0; i < fieldsList1.size(); i++) {
            fields.add(fieldsList.get(i).getAsString());
        }

        //AiRecommended facetable fields data:
        String pageCount = object.get("pageCount").getAsString();

        //AutoSuggest flow data:
        //Keywords:
        JsonArray fieldsList2 = object.get("preDefinedKeywords").getAsJsonArray();
        String suggestion1 = object.get("keywordSuggestionSection").getAsString();

        List<String> expectedPredefinedKeywords = new ArrayList<>();
        for (int i = 0; i < fieldsList2.size(); i++) {
            expectedPredefinedKeywords.add(fieldsList.get(i).getAsString());
        }

        //Infields
        JsonArray fieldsList3 = object.get("preDefinedInFields").getAsJsonArray();
        String suggestion2 = object.get("infieldSuggestionSection").getAsString();

        List<String> expectedPredefinedInFields = new ArrayList<>();
        for (int i = 0; i < fieldsList3.size(); i++) {
            expectedPredefinedInFields.add(fieldsList.get(i).getAsString());
        }

        //PopularProducts
        JsonArray fieldsList4 = object.get("preDefinedPopularProducts").getAsJsonArray();
        String suggestion3 = object.get("popularProductSuggestionSection").getAsString();

        List<String> expectedPredefinedPopularProducts = new ArrayList<>();
        for (int i = 0; i < fieldsList4.size(); i++) {
            expectedPredefinedPopularProducts.add(fieldsList.get(i).getAsString());
        }

 //--------------------------------------------------------------------------------------------------------------------------------------------------------------

        try {
            //Site creation flow:
            ssoIdKeyCookie = createSiteComponent.getSsoIdCookie();
            csrfCookie = createSiteComponent.getCsrfCookie();
            createSiteComponent.createSite();
            Assert.assertTrue(createSiteActions.awaitForElementPresence(createSiteActions.catalogUploadPageTitle));
            feedUploadActions.selectUploaders(PLATFORM_UPLOAD);
            feedUploadActions.selectPlatform(MAGENTO_TAB);
            String siteKey = createSiteActions.getCreatedSiteKey();
            String secreteKey = createSiteActions.getSecreteKey();
            siteKeys.add(siteKey);

            //Feed upload flow:

            feedUploadActions.apiUpload(siteKey, secreteKey);
            feedUploadActions.clickOnProceedButton();
            feedUploadActions.waitForFeedUploadToComplete();
            feedUploadActions.awaitForElementPresence(feedUploadActions.successMessage);
            Assert.assertTrue(feedUploadActions.successMsg.getText().equalsIgnoreCase("Feed Upload is Successful"));

                //DimensionMap flow:
            click(autoMappingActions.mapCatalogButton);
            facetableFieldsActions.waitForLoaderToDisAppear(facetableFieldsActions.relevancyPageLoader, facetableFieldsActions.relevancePageLoader);
            facetableFieldsActions.waitForElementAppear(autoMappingActions.setUpSearchButtons, autoMappingActions.mappingPageLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));


            List<String> ActualMappedFields = new ArrayList<>();
            for (String field : fields) {
                String actualMappedField = autoMappingActions.getMappedFieldUsingDisplayName(field);
                ActualMappedFields.add(actualMappedField);
                    }
            Assert.assertTrue(ActualMappedFields.size() > 0);
            facetableFieldsActions.waitForElementAppear(autoMappingActions.setUpSearchButtons, autoMappingActions.mappingPageLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
            facetableFieldsActions.awaitForElementPresence(autoMappingActions.setUpSearchButton);
            Thread.sleep(15000);
            autoMappingActions.mapFields();
            click(autoMappingActions.setUpSearchButton);
                    //Assert.assertEquals(ActualMappedFields,expectedDimensionMappedField);

            //Searchable fields flow:
            facetableFieldsActions.waitForElementAppear(autoSuggestActions.skipQueryButton, facetableFieldsActions.relevancePageLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
            facetableFieldsActions.skipQueryData();
            facetableFieldsActions.waitForLoaderToDisAppear(facetableFieldsActions.relevancyPageLoader, facetableFieldsActions.relevancePageLoader);
            facetableFieldsActions.waitForElementAppear(searchableFieldActions.applyAiRecButton, facetableFieldsActions.relevancePageLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));

            for (String field : fields1) {
                FluentWebElement attributeName = searchableFieldActions.getAttributeUsingDisplayName(field);
                String weight = searchableFieldActions.getSearchWeight(attributeName);
                Assert.assertEquals(weight, ExpectedSearchWeight);
            }

            //Facetable fields flow:
            facetableFieldsActions.scrollToBottom();
            facetableFieldsActions.goToRelevancySectionsByName(UnbxdEnum.valueOf(facet));
            facetableFieldsActions.selectPageCount(pageCount);
            int aiFacetList = facetableFieldsActions.facetList.size();
            Assert.assertTrue(aiFacetList > 0);

            //ContentPage flow:
            facetableFieldsActions.goToRelevancySectionsByName(UnbxdEnum.valueOf(contentSection));
            await();
            int synonymCount = contentRelevanceActions.getSynonymCount(contentRelevanceActions.synonymContainer);
            System.out.println(synonymCount);
            int conceptsCount = contentRelevanceActions.getConceptsCount((contentRelevanceActions.conceptsContainer));
            System.out.println(conceptsCount);
            int phraseCount = contentRelevanceActions.getPhrasesCount(contentRelevanceActions.phrasesContainer);
            System.out.println(phraseCount);
            int noStemCount = contentRelevanceActions.getNoStemWordCount(contentRelevanceActions.noStemContainer);
            System.out.println(noStemCount);
            Assert.assertTrue(synonymCount > 0);
            Assert.assertTrue(conceptsCount > 0);
            Assert.assertTrue(phraseCount > 0);
            Assert.assertTrue(noStemCount > 0);

            //AutoSuggest flow :
            //KeyWordSuggestion:
            facetableFieldsActions.goToRelevancySectionsByName(UnbxdEnum.valueOf(autoSuggestSection));
            Thread.sleep(15000);
            autoSuggestActions.clickOnCustomiseButton();
            autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion1));
            await();
            List<String> actualPredefinedKeywords = autoSuggestActions.getPredefinedSuggestions();
            int keyWordSize = actualPredefinedKeywords.size();
            Assert.assertTrue(keyWordSize > 0);
            autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion1));
            // Assert.assertEquals(expectedPredefinedKeywords, actualPredefinedKeywords);

            //Infields:
            autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion2));
            await();
            List<String> actualPredefinedInFields = autoSuggestActions.getPredefinedSuggestions();
            int infields = actualPredefinedInFields.size();
            Assert.assertTrue(infields > 0);
            autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion2));
            //Assert.assertEquals(expectedPredefinedInFields, actualPredefinedIn;Fields);

            //PopularProducts:

            Thread.sleep(10000);
            autoSuggestActions.scrollToBottom();
            autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion3));
            await();
            List<String> actualPredefinedPopularProducts = autoSuggestActions.getPredefinedSuggestions();
            int popularProducts = actualPredefinedPopularProducts.size();
            Assert.assertTrue(popularProducts > 0);
            autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion3));
            //Assert.assertEquals(actualPredefinedPopularProducts, actualPredefinedPopularProducts);

            //Preview flow
            autoSuggestActions.scrollToTop();
            //facetableFieldsActions.waitForElementAppear(searchableFieldActions.applyAiRecButton,facetableFieldsActions.applyButton, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
            await();
            previewActions.awaitForElementPresence(previewActions.applyConfigButton);
            click(previewActions.applyConfigButton);
            feedUploadActions.WaitTillReindexComplete();
            await();
            previewActions.goToPreviewPage();
            previewActions.awaitForElementPresence(previewActions.productCount);
            Assert.assertTrue(previewActions.productCount.getText().trim().equalsIgnoreCase("- 1 to 12 of 200 products"));
            Assert.assertEquals(previewActions.getPreviewSnippetCount(), 12);
            Assert.assertTrue(previewActions.getFacetsCount() > 0);
        }
        catch(NoSuchElementException exception)
        {
            System.out.println("There is no such element present");
            Assert.fail("No such element failure" + exception);
        }
        catch (Exception e)
        {
            Assert.fail("SmokeTest got failed" + e);
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
