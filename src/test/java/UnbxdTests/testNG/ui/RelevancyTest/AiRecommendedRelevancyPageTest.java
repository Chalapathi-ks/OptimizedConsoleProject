package UnbxdTests.testNG.ui.RelevancyTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import UnbxdTests.testNG.ui.BaseTest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.ui.actions.*;
import core.ui.components.CreateSiteComponent;
import core.ui.components.FeedUploadComponent;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static lib.constants.UnbxdErrorConstants.DUPLICATE_FACET_ERROR;

public class AiRecommendedRelevancyPageTest extends BaseTest {

    @Page
    LoginActions loginActions;

    @Page
    FacetableFieldsActions facetableFieldsActions;

    @Page
    CreateSiteComponent createSiteComponent;

    @Page
    AutoSuggestActions autoSuggestActions;

    @Page
    FeedUploadComponent feedUploadComponent;

    @Page
    CreateSiteActions createSiteActions;

    @Page
    FeedUploadActions feedUploadActions;

    @Page
    SearchableFieldActions searchableFieldActions;

    @Page
    ContentRelevanceActions contentRelevanceActions;

    private String contentSection = "DICTIONARY";
    private String ssoIdKeyCookie ;
    private String csrfCookie;

    List<String> siteKeys = new ArrayList<>();

    private static List<String> createdFacets = new ArrayList<>();

    private String facetSection = "FACETABLE_FIELD";
    private String autoSuggestSection = "AUTO_SUGGEST";

    private String pageCount = "100";

    @BeforeClass
    public void init() throws InterruptedException {
        super.setUp();
        initFluent(driver);
        initTest();
        // Login is now handled by BaseTest's global login mechanism
        ssoIdKeyCookie = createSiteComponent.getSsoIdCookie();
        csrfCookie = createSiteComponent.getCsrfCookie();
        String siteKey = feedUploadComponent.goTillFeedUpload();
        feedUploadComponent.goTillMappingPage();
        feedUploadComponent.goTillRelevancyPage();
        siteKeys.add(siteKey);
    }

    @FileToTest(value = "relevancyTest/aiRecommendedFieldTest.json")
    @Test(description = "This test Verifies the searchWeights for few aiFields", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void aiRecommendedSearchableFieldTest(JsonObject object) throws InterruptedException {
        try {
            JsonArray fieldsList = object.get("fields").getAsJsonArray();
            String ExpectedSearchWeight = object.get("ExpectedSearchWeight").getAsString();
            String count = object.get("pageCount").getAsString();

            List<String> fields = new ArrayList<>();
            for (int i = 0; i < fieldsList.size(); i++) {
                fields.add(fieldsList.get(i).getAsString());
            }

            for (String field : fields) {
                FluentWebElement attributeName = searchableFieldActions.getAttributeUsingDisplayName(field);
                String weight = searchableFieldActions.getSearchWeight(attributeName);
                Assert.assertEquals(weight, ExpectedSearchWeight);
            }
        }
        catch(NoSuchElementException exception) {
            System.out.println("There is no such element present");
            Assert.fail("No such element failure" + exception);
        }
        catch (Exception e)
        {
            Assert.fail("aiRecommendedSearchableFieldTest got failed" + e);
        }
    }


    @FileToTest(value = "relevancyTest/searchableFieldTest.json")
    @Test(description = "This test verifies the product coverage section is coming or not", priority = 2,groups = "searchableFields",dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifyProductCoverageTest(JsonObject object) throws InterruptedException {
        try
        {
            String name = object.get("attributeName").getAsString();
            String expectedPercentage = object.get("productCoveragePercentage").getAsString();
            String count = object.get("pageCount").getAsString();

            goTo(feedUploadComponent.getRelevancyPage());
            feedUploadComponent.awaitForPageToLoad();
            await();
            Thread.sleep(5000);
            FluentWebElement attributeName = searchableFieldActions.getAttributeUsingDisplayName(name);
            String percentage = searchableFieldActions.getProductCoveragePercentage(attributeName);

            Assert.assertEquals(percentage,expectedPercentage);
            Assert.assertEquals(searchableFieldActions.productCoverageText.getText(),"Product coveragei");
        }
        catch(NoSuchElementException exception) {
            System.out.println("There is no such element present");
            Assert.fail("No such element failure" + exception);
        }
        catch (Exception e)
        {
            Assert.fail("verifyProductCoverageTestCase got failed" + e);
        }
    }

    @FileToTest(value = "relevancyTest/searchableFieldTest.json")
    @Test(description = "This testcase verifies edit functionality of searchable fields", priority = 3,dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = "searchableFields")
    public void verifySearchWeightEditTest(JsonObject object) throws InterruptedException {
        try
        {
            String name = object.get("attributeName").getAsString();
            String editWeight = object.get("editWeight").getAsString();
            String count = object.get("pageCount").getAsString();

            goTo(feedUploadComponent.getRelevancyPage());
            feedUploadComponent.awaitForPageToLoad();
            await();
            FluentWebElement attributeName = searchableFieldActions.getAttributeUsingDisplayName(name);
            String searchWeight = searchableFieldActions.getSearchWeight(attributeName);
            //Assert.assertEquals(searchWeight,ActualWeight);

            searchableFieldActions.selectSearchWeightFromDropdown(editWeight);

            String searchWeight2 = searchableFieldActions.getSearchWeight(attributeName);
            Assert.assertEquals(searchWeight2,editWeight);
        }
        catch(NoSuchElementException exception) {
            System.out.println("There is no such element present");
            Assert.fail("No such element failure" + exception);
        }
        catch (Exception e)
        {
            Assert.fail("verifySearchWeightEditTestCase got failed" +e);
        }

        //searchableFieldActions.applyAiRecommendation();
    }

    @FileToTest(value = "relevancyTest/searchableFieldTest.json")
    @Test(description = "This testcase verifies AI recommendation link functionality", priority = 4,dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = "searchableFields")
    public void verifyApplyAiRecommendationTest(JsonObject object) throws InterruptedException {
        try {
            String name = object.get("attributeName1").getAsString();
            String editWeight = object.get("colorEditWeight").getAsString();
            String count = object.get("pageCount").getAsString();

            goTo(feedUploadComponent.getRelevancyPage());
            feedUploadComponent.awaitForPageToLoad();
            await();

            FluentWebElement attributeName = searchableFieldActions.getAttributeUsingDisplayName(name);
            String searchWeight = searchableFieldActions.getSearchWeight(attributeName);

            //Assert.assertEquals(searchWeight,ActualWeight);

            searchableFieldActions.selectSearchWeightFromDropdown(editWeight);
            searchableFieldActions.saveChanges();

            FluentWebElement attributeName1 = searchableFieldActions.getAttributeUsingDisplayName(name);
            String searchWeight2 = searchableFieldActions.getSearchWeight(attributeName1);
            Assert.assertEquals(searchWeight2, editWeight);

            searchableFieldActions.applyAiRecommendation();

            FluentWebElement attributeName2 = searchableFieldActions.getAttributeUsingDisplayName(name);
            String searchWeight3 = searchableFieldActions.getSearchWeight(attributeName2);
            Assert.assertEquals(searchWeight3, searchWeight);
        }
        catch(NoSuchElementException exception) {
            System.out.println("There is no such element present");
            Assert.fail("No such element failure" + exception);
        }
        catch (Exception e)
        {
            Assert.fail("verifyApplyAiRecommendationTestCase got failed" + e);
        }
    }

    @Test(description = "This test verify synonym/phrase/concept/noStemWord counts", priority = 5,groups = "relevance")
    public void contentRelevanceDataTest() throws InterruptedException {
        try {
            facetableFieldsActions.goToRelevancySectionsByName(UnbxdEnum.valueOf(contentSection));
            Thread.sleep(50000);
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
        }catch(NoSuchElementException exception) {
            System.out.println("There is no such element present");
            Assert.fail("No such element failure" + exception);
        }
        catch (Exception e)
        {
            Assert.fail("contentRelevanceDataTestCase got failed" + e);
        }
    }


    @FileToTest(value = "relevancyTest/aiRecommendedFacetTest.json")
    @Test(description = "This test Verifies featured facets are coming or not", priority = 6, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void aiRecommendedFacetsTest(JsonObject object) throws InterruptedException
    {
        String pageCount = object.get("pageCount").getAsString();
        facetableFieldsActions.scrollToBottom();
        facetableFieldsActions.goToRelevancySectionsByName(UnbxdEnum.valueOf(facetSection));
        Thread.sleep(10000);
        facetableFieldsActions.selectPageCount(pageCount);

        int aiFacetList = facetableFieldsActions.facetList.size();
        Assert.assertTrue(aiFacetList > 0);
    }
         /*JsonArray facetList = object.get("facet").getAsJsonArray();
        List<String> expectedFacets = new ArrayList<>();
        for (int i = 0; i < facetList.size(); i++) {
            expectedFacets.add(facetList.get(i).getAsString());
        }
        List<String> actualFacets = facetableFieldsActions.getFacetableFields();
        Assert.assertEquals(expectedFacets, actualFacets);*/


    @FileToTest(value = "relevancyTest/createFacetTest.json")
    @Test(description = "This test verifies the creation and verification of facets", priority = 7,dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = "facets")
    public void createFacetTest(Object data) throws InterruptedException {

        JsonObject object = (JsonObject) data;
        HashMap<String, Object> testData = Helper.convertJsonToHashMap(object.toString());
        String displayName = (String) testData.get("facetDisplayName") + System.currentTimeMillis();
        testData.put("facetDisplayName", displayName);

        goTo(feedUploadComponent.getRelevancyPage());
        await();
        facetableFieldsActions.goToRelevancySectionsByName(UnbxdEnum.valueOf(facetSection));
        Thread.sleep(10000);
        facetableFieldsActions.openCreateFacetForm();
        String facetDisplayName = facetableFieldsActions.fillFacetDetails(testData);
        facetableFieldsActions.saveFacet();
        await();
        createdFacets.add(facetDisplayName);
        Assert.assertNotNull(facetableFieldsActions.getFacetUsingDisplayName(facetDisplayName),"Facet creation is failing!!!");
    }

   /* @Test(description = "This test verified the facet updation and verification of updated facets",priority = 3,groups = "facets")
    public void deleteFacetTest() throws InterruptedException {
        try
        {
            goTo(feedUploadComponent.getRelevancyPage());
            await();
            facetableFieldsActions.goToRelevancySectionsByName(UnbxdEnum.valueOf(facetSection));
            facetableFieldsActions.openCreateFacetForm();
            String name = facetableFieldsActions.createFacet();

            //FluentWebElement facet = facetableFieldsActions.getFacetUsingDisplayName(name);

            driver.navigate().refresh();
            facetableFieldsActions.goToRelevancySectionsByName(UnbxdEnum.valueOf(facetSection));
            Assert.assertTrue(facetableFieldsActions.deleteFacet(name, pageCount), "Facet Deletion is failing");
        }
        catch(NoSuchElementException exception) {
            System.out.println("There is no such element present" + exception);
            Assert.fail("No such element failure");
        }
        catch (Exception e)
        {
            Assert.fail("createFacetTestCase got failed" + e);
        }
    }*/


    @Test(description = "This test verified the facet updation and verification of updated facets",priority = 8,groups = "facets")
    public void updateFacetTest() throws InterruptedException {

        String length = "100";
        String order = "Product Count";
        String facetDisplayValue = "Disabled";

        goTo(feedUploadComponent.getRelevancyPage());
        await();
        facetableFieldsActions.goToRelevancySectionsByName(UnbxdEnum.valueOf(facetSection));
        Thread.sleep(10000);
        facetableFieldsActions.openCreateFacetForm();
        String name = facetableFieldsActions.createFacet();

        FluentWebElement facet = facetableFieldsActions.getFacetUsingDisplayName(name);

        facetableFieldsActions.openFacetEditWindow(facet);
        facetableFieldsActions.updateFacetLength(length);
        facetableFieldsActions.updateFacetSortOrder(order);
        facetableFieldsActions.saveFacet();
        driver.navigate().refresh();
        facetableFieldsActions.goToRelevancySectionsByName(UnbxdEnum.valueOf(facetSection));
        await();
        FluentWebElement facet1 = facetableFieldsActions.getFacetUsingDisplayName(name);
        facetableFieldsActions.openFacetEditWindow(facet1);
        Assert.assertTrue(facetableFieldsActions.getFacetLength().equals(length));
        Assert.assertTrue(facetableFieldsActions.getFacetSortOrder().equals(order));
        facetableFieldsActions.updateFacetState(facetDisplayValue);
        facetableFieldsActions.saveFacet();
        await();
        facetableFieldsActions.closeFacetCreationWindow();
        createdFacets.add(name);
    }


    @Test(description = "This test verified the facet duplication flow",priority = 9,groups = "facets")
    public void duplicateFacetValidationTest() throws InterruptedException
    {
        String facetDisplayValue = "Disabled";

        Map<String, Object> testData = new HashMap<>();

        goTo(feedUploadComponent.getRelevancyPage());
        await();
        facetableFieldsActions.goToRelevancySectionsByName(UnbxdEnum.valueOf(facetSection));
        Thread.sleep(10000);
        facetableFieldsActions.openCreateFacetForm();
        String name = facetableFieldsActions.createFacet();

        FluentWebElement facet = facetableFieldsActions.getFacetUsingDisplayName(name);
        createdFacets.add(name);
        //FluentWebElement facet = facetableFieldsActions.getFacetByPosition(0);
        testData.put("facetDisplayName", facetableFieldsActions.getDisplayName(facet));
        testData.put("facetName", facetableFieldsActions.getAttributeName(facet));
        testData.put("facetDisplayValue", facetableFieldsActions.getFacetStatus(facet));


        if (facetableFieldsActions.getFieldType(facet).equalsIgnoreCase("Text")) {
            testData.put("facetLength", "4");
            testData.put("sortOrder", "Product Count");
        } else {
            testData.put("rangeStart", "4");
            testData.put("rangeStop", "12");
            testData.put("rangeGap", "3");
        }

        facetableFieldsActions.openCreateFacetForm();
        String facetDisplayName = facetableFieldsActions.fillFacetDetails(testData);
        facetableFieldsActions.saveFacet();
        Assert.assertTrue(facetableFieldsActions.alertMessage.getTextContent().contains(DUPLICATE_FACET_ERROR), "Able to Create Duplicate Facet with the Same Name");
        facetableFieldsActions.closeFacetCreationWindow();

        facetableFieldsActions.openFacetEditWindow(facet);
        facetableFieldsActions.updateFacetState(facetDisplayValue);
        facetableFieldsActions.saveFacet();
        facetableFieldsActions.closeFacetCreationWindow();
        facetableFieldsActions.clearSearchBox();
    }

    @FileToTest(value = "/preDefinedSuggestions/predefinedKeyword.json")
    @Test(description = "This test Verifies predefined keyWordSuggestion are coming or not", priority = 10, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void aiRecommendedKeywordTest(JsonObject object) throws InterruptedException
    {
        JsonArray fieldsList = object.get("preDefinedFields").getAsJsonArray();
        String suggestion = object.get("suggestionSection").getAsString();

        List<String> expectedPredefinedFields = new ArrayList<>();
        for (int i = 0; i < fieldsList.size(); i++) {
            expectedPredefinedFields.add(fieldsList.get(i).getAsString());
        }

        facetableFieldsActions.scrollToBottom();
        facetableFieldsActions.goToRelevancySectionsByName(UnbxdEnum.valueOf(autoSuggestSection));
        Thread.sleep(50000);
        autoSuggestActions.clickOnCustomiseButton();
        Thread.sleep(5000);
        autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion));

        List<String> actualPredefinedFields = autoSuggestActions.getPredefinedSuggestions();
        System.out.println("Predefined keywordSuggestions are :" +actualPredefinedFields);
        Assert.assertTrue(actualPredefinedFields.size() > 0);
        //Assert.assertEquals(expectedPredefinedFields, actualPredefinedFields);

    }

    @FileToTest(value = "/preDefinedSuggestions/predefinedInfields.json")
    @Test(description = "This test Verifies predefined infields are coming or not", priority = 11, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void aiRecommendedInfieldTest(JsonObject object) throws InterruptedException {
        JsonArray fieldsList = object.get("preDefinedFields").getAsJsonArray();
        String suggestion = object.get("suggestionSection").getAsString();

        List<String> expectedPredefinedFields = new ArrayList<>();
        for (int i = 0; i < fieldsList.size(); i++) {
            expectedPredefinedFields.add(fieldsList.get(i).getAsString());
        }

        goTo(feedUploadComponent.getRelevancyPage());
        //facetableFieldsActions.waitForElementAppear(autoSuggestActions.skipQueryButton,facetableFieldsActions.relevancePageLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
        facetableFieldsActions.goToRelevancySectionsByName(UnbxdEnum.valueOf(autoSuggestSection));
        Thread.sleep(5000);
        autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion));

        List<String> actualPredefinedFields = autoSuggestActions.getPredefinedSuggestions();
        System.out.println("Predefined infields are :" +actualPredefinedFields);
        Assert.assertTrue(actualPredefinedFields.size() > 0);
        //Assert.assertEquals(expectedPredefinedFields, actualPredefinedFields);

    }

    @FileToTest(value = "/preDefinedSuggestions/predefinedPopularProducts.json")
    @Test(description = "This test Verifies predefined popular products are coming or not", priority = 12, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void aiRecommendedPopularProductsTest(JsonObject object) throws InterruptedException {
        JsonArray fieldsList = object.get("preDefinedFields").getAsJsonArray();
        String suggestion = object.get("suggestionSection").getAsString();

        List<String> expectedPredefinedFields = new ArrayList<>();
        for (int i = 0; i < fieldsList.size(); i++) {
            expectedPredefinedFields.add(fieldsList.get(i).getAsString());
        }

        goTo(feedUploadComponent.getRelevancyPage());
        //facetableFieldsActions.waitForElementAppear(autoSuggestActions.skipQueryButton,facetableFieldsActions.relevancePageLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
        facetableFieldsActions.goToRelevancySectionsByName(UnbxdEnum.valueOf(autoSuggestSection));
        autoSuggestActions.scrollToElement(autoSuggestActions.popularProductsSection, "popularProduct");
        Thread.sleep(5000);
        autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion));

        List<String> actualPredefinedFields = autoSuggestActions.getPredefinedSuggestions();
        Assert.assertEquals(expectedPredefinedFields, actualPredefinedFields);
    }

    @AfterGroups(groups = "facets", alwaysRun = true)
    public void deleteCreatedFacets() throws InterruptedException
    {
        goTo(feedUploadComponent.getRelevancyPage());
        await();

        for (String name : createdFacets) {
            driver.navigate().refresh();
            facetableFieldsActions.goToRelevancySectionsByName(UnbxdEnum.valueOf(facetSection));
            Thread.sleep(10000);
            facetableFieldsActions.deleteFacet(name);
            Assert.assertNull(facetableFieldsActions.getFacetUsingDisplayName(name),"Facet Deletion is failing!!!");
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