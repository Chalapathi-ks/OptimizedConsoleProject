package UnbxdTests.testNG.ui.SearchManageTest;

import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import UnbxdTests.testNG.ui.BaseTest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.consoleui.actions.*;
import core.consoleui.page.*;
import core.ui.actions.*;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.ui.page.UiBase.ThreadWait;

public class FieldPropertiesTest extends MerchandisingTest {

    String query;
    @Page
    LoginActions loginActions;
    @Page
    FieldPropertiesActions fieldPropertiesActions;

    @Page
    BrowseFacetsPage browseFacetsPage;


    private static List<String> createdFields = new ArrayList<>();

    @Page
    public
    CommercePageActions searchPage;

    @Page
    AutoSuggestActions autoSuggestActions;

    @Page
    SearchManageAutoSuggestActions searchManageAutoSuggestActions;

    @Page
    searchAutosuggestPage SearchAutosuggestPage;
    @Page
    CommercePageActions searchPageActions;

    @Page
    BrowseFieldPropertiesPage browseFieldPropertiesPage;

    @Page
    FieldPropertiesPage fieldPropertiesPage;

    @Page
    FacetableFieldsActions facetableFieldsActions;

    @Page
    searchableFieldsAndFacetsPage manageSearchFacetAndSearchFieldPage;

    @Page
    BrowsePage browsePage;

    @Page
    public
    MerchandisingActions merchandisingActions;


    @FileToTest(value = "/manageFacetAndSearchableFieldTest/bulkEnableFeatures.json")
    @Test(description = "This test verifies bulk enabling and disabling of features for field properties", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void bulkEnableAndDisableFieldFeatures(Object jsonObject) throws InterruptedException {
        // Navigate to field properties page
        goTo(fieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();

        JsonObject fieldProperties = (JsonObject) jsonObject;
        String FieldName = fieldProperties.get("fieldName").getAsString();
        String Merchandisable = fieldProperties.get("featureMerchandisable").getAsString();
        String Facetable = fieldProperties.get("featureFacetable").getAsString();
        String Autosuggest = fieldProperties.get("featureAutosuggest").getAsString();
        String FieldRule = fieldProperties.get("featureFieldRule").getAsString();


        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickActionDropdown();
        fieldPropertiesActions.selectBulkEnableFeatures();
        fieldPropertiesActions.selectFeature(Merchandisable);
        fieldPropertiesActions.clickBulkEnableButton();
        searchPage.awaitTillElementDisplayed(searchPageActions.successMessage);
        fieldPropertiesActions.refreshIcon.click();
        searchPage.threadWait();
        goTo(fieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickActionDropdown();
        fieldPropertiesActions.selectBulkDisableFeatures();
        fieldPropertiesActions.selectFeature(Merchandisable);
        fieldPropertiesActions.clickBulkDisableButton();
        searchPage.threadWait();
        searchPage.awaitTillElementDisplayed(searchPageActions.successMessage);
        fieldPropertiesActions.refreshIcon.click();
        searchPage.threadWait();
    }


    @FileToTest(value = "/manageFacetAndSearchableFieldTest/EnableAndDisableFeatures.json")
    @Test(description = "This test verifies editing and then enabling and disabling of features for field properties", priority = 2, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void EnableAndDisableFieldFeatures(Object jsonObject) throws InterruptedException {
        // Navigate to field properties page
        goTo(fieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();

        JsonObject fieldProperties = (JsonObject) jsonObject;
        String FieldName = fieldProperties.get("fieldName").getAsString();
        String Merchandisable = fieldProperties.get("featureMerchandisable").getAsString();
        String Facetable = fieldProperties.get("featureFacetable").getAsString();
        String Autosuggest = fieldProperties.get("featureAutosuggest").getAsString();
        String FieldRule = fieldProperties.get("featureFieldRule").getAsString();


        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickEditIcon();
        fieldPropertiesActions.selectFeature(Merchandisable);
        fieldPropertiesActions.selectFeature(Facetable);
        fieldPropertiesActions.selectFeature(Autosuggest);
        fieldPropertiesActions.selectFeature(FieldRule);
        fieldPropertiesActions.clickApplyButton();
        searchPage.awaitTillElementDisplayed(searchPageActions.successMessage);
        fieldPropertiesActions.refreshIcon.click();
        searchPage.threadWait();
        fieldPropertiesActions.isFeatureEnabled(Merchandisable);
        fieldPropertiesActions.isFeatureEnabled(Facetable);
        fieldPropertiesActions.isFeatureEnabled(Autosuggest);
        fieldPropertiesActions.isFeatureEnabled(FieldRule);


        //Disable
        goTo(fieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickEditIcon();
        fieldPropertiesActions.selectFeature(Merchandisable);
        fieldPropertiesActions.selectFeature(Facetable);
        fieldPropertiesActions.selectFeature(Autosuggest);
        fieldPropertiesActions.selectFeature(FieldRule);
        fieldPropertiesActions.clickApplyButton();
        searchPage.threadWait();
        searchPage.awaitTillElementDisplayed(searchPageActions.successMessage);
        fieldPropertiesActions.refreshIcon.click();
        fieldPropertiesActions.refreshIcon.click();
        searchPage.threadWait();
        fieldPropertiesActions.isFeatureDisabled(Merchandisable);
        fieldPropertiesActions.isFeatureDisabled(Facetable);
        fieldPropertiesActions.isFeatureDisabled(Autosuggest);
        fieldPropertiesActions.isFeatureDisabled(FieldRule);

    }

    @FileToTest(value = "/manageFacetAndSearchableFieldTest/EnableFeatureInManage&Promotion.json")//
    @Test(description = "This test verifies that after enabling features, they are available in Manage Facet and Promotions", priority = 3, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void EnableFieldFeaturesAndcheckInManageFacetAndPromotion(Object jsonObject) throws InterruptedException {
        // Navigate to field properties page
        goTo(fieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();

        JsonObject fieldProperties = (JsonObject) jsonObject;
        String FieldName = fieldProperties.get("fieldName").getAsString();
        String Merchandisable = fieldProperties.get("featureMerchandisable").getAsString();
        String Facetable = fieldProperties.get("featureFacetable").getAsString();
        String query = fieldProperties.get("query").getAsString();

        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickEditIcon();
        fieldPropertiesActions.selectFeature(Facetable);
        fieldPropertiesActions.selectFeature(Merchandisable);
        fieldPropertiesActions.clickApplyButton();
        searchPage.threadWait();
        searchPage.awaitTillElementDisplayed(searchPageActions.successMessage);
        fieldPropertiesActions.refreshIcon.click();
        fieldPropertiesActions.refreshIcon.click();
        searchPage.threadWait();
        fieldPropertiesActions.isFeatureEnabled(Facetable);
        fieldPropertiesActions.isFeatureEnabled(Merchandisable);

        //Manage
        goTo(manageSearchFacetAndSearchFieldPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        facetableFieldsActions.openCreateFacetForm();
        facetableFieldsActions.checkSelectedField(FieldName);
        ThreadWait();
        String FacetAttribute=merchandisingActions.FacetWrapper.getText();
        Assert.assertTrue(FacetAttribute.contains(FieldName));

        //promotion
        goTo(searchPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        createPromotion(query,false,false);
        JsonArray object = fieldProperties.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("bulkEnableFeatures.json");
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
        merchandisingActions.selectAttributeValue(FieldName);
        String FieldAttribute=merchandisingActions.attributeDropDownList.getText();
        Assert.assertTrue(FieldAttribute.contains(FieldAttribute),FieldName);

        //Disable feature
        goTo(fieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickEditIcon();
        fieldPropertiesActions.selectFeature(Facetable);
        fieldPropertiesActions.selectFeature(Merchandisable);
        fieldPropertiesActions.clickApplyButton();
        fieldPropertiesActions.refreshIcon.click();
        searchPage.threadWait();
        fieldPropertiesActions.isFeatureDisabled(Facetable);
        fieldPropertiesActions.isFeatureDisabled(Merchandisable);

        goTo(manageSearchFacetAndSearchFieldPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        facetableFieldsActions.openCreateFacetForm();
        facetableFieldsActions.SelectedField(FieldName);
        ThreadWait();
        Assert.assertTrue(fieldPropertiesActions.manageAttributeCoundnotFind.isDisplayed());

        //promotion
        goTo(searchPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        createPromotion(query,false,false);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
        merchandisingActions.selectAttributeValue(FieldName);
        Assert.assertTrue(fieldPropertiesActions.manageAttributeCoundnotFind.isDisplayed());
    }

    @FileToTest(value = "/manageFacetAndSearchableFieldTest/EnableFeaturesInAutosuggestAndFieldRule.json")//
    @Test(description = "This test verifies that after enabling features, they are available in Field Rule and Autosuggest", priority = 4, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void EnableFieldFeaturesAndcheckInFieldRuleAndAutosuggest(Object jsonObject) throws InterruptedException {
        // Navigate to field properties page
        goTo(fieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();

        JsonObject fieldProperties = (JsonObject) jsonObject;
        String FieldName = fieldProperties.get("fieldName").getAsString();
        String Autosuggest = fieldProperties.get("featureAutosuggest").getAsString();
        String FieldRule = fieldProperties.get("featureFieldRule").getAsString();
        String query = fieldProperties.get("query").getAsString();
        String suggestion = fieldProperties.get("suggestionSection").getAsString();


        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickEditIcon();
        fieldPropertiesActions.selectFeature(Autosuggest);
        fieldPropertiesActions.selectFeature(FieldRule);
        fieldPropertiesActions.clickApplyButton();
        searchPage.threadWait();
        searchPage.awaitTillElementDisplayed(searchPageActions.successMessage);
        fieldPropertiesActions.refreshIcon.click();
        fieldPropertiesActions.refreshIcon.click();
        searchPage.threadWait();
        fieldPropertiesActions.isFeatureEnabled(Autosuggest);
        fieldPropertiesActions.isFeatureEnabled(FieldRule);

        //FieldRule
        goTo(searchPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();
        searchPage.threadWait();
        createPromotion(query,false,false);
        ThreadWait();
        merchandisingActions.selectAttributeValue(FieldName);
        ThreadWait();
        String FacetAttribute=merchandisingActions.attributeDropDownList.getText();
        ThreadWait();
        Assert.assertEquals(FacetAttribute,FieldName);

        //Autosuggest
        goTo(SearchAutosuggestPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        autoSuggestActions.clickOnCustomiseButton();
        autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion));
        searchManageAutoSuggestActions.KeywordSuggestion(FieldName);
        searchPage.threadWait();
        String AutosuggestAttribute=searchManageAutoSuggestActions.dropDownList.getText();
        Assert.assertEquals(AutosuggestAttribute,FieldName);


        //Disable feature
        goTo(fieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickEditIcon();
        fieldPropertiesActions.selectFeature(FieldRule);
        fieldPropertiesActions.selectFeature(Autosuggest);
        fieldPropertiesActions.clickApplyButton();
        searchPage.threadWait();
        fieldPropertiesActions.refreshIcon.click();
        searchPage.threadWait();
        fieldPropertiesActions.isFeatureDisabled(Autosuggest);
        fieldPropertiesActions.isFeatureDisabled(FieldRule);


        //FieldRule
        goTo(searchPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();
        searchPage.threadWait();
        createPromotion(query,false,false);
        ThreadWait();
        merchandisingActions.selectAttributeValue(FieldName);
        ThreadWait();
        fieldPropertiesActions.manageAttributeCoundnotFind.isDisplayed();

        goTo(SearchAutosuggestPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        autoSuggestActions.clickOnCustomiseButton();
        autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion));
        searchManageAutoSuggestActions.KeywordSuggestion(FieldName);
        searchPage.threadWait();
        fieldPropertiesActions.manageAttributeCoundnotFind.isDisplayed();


    }


    //Browse
    @FileToTest(value = "/manageFacetAndSearchableFieldTest/bulkEnableFeaturesforBrowse.json")
    @Test(description = "This test verifies bulk enabling and disabling of features for browse field properties", priority = 5, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void BrowsebulkEnableAndDisableFieldFeatures(Object jsonObject) throws InterruptedException {
        // Navigate to field properties page
        goTo(browseFieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();

        JsonObject fieldProperties = (JsonObject) jsonObject;
        String FieldName = fieldProperties.get("fieldName").getAsString();
        String Merchandisable = fieldProperties.get("featureMerchandisable").getAsString();
        String Facetable = fieldProperties.get("featureFacetable").getAsString();
        String Autosuggest = fieldProperties.get("featureAutosuggest").getAsString();
        String FieldRule = fieldProperties.get("featureFieldRule").getAsString();

        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickActionDropdown();
        fieldPropertiesActions.selectBulkEnableFeatures();
        fieldPropertiesActions.selectFeature(Merchandisable);
        fieldPropertiesActions.clickBulkEnableButton();
        searchPage.awaitTillElementDisplayed(searchPageActions.successMessage);
        fieldPropertiesActions.refreshIcon.click();
        searchPage.threadWait();
        goTo(browseFieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickActionDropdown();
        fieldPropertiesActions.selectBulkDisableFeatures();
        fieldPropertiesActions.selectFeature(Merchandisable);
        fieldPropertiesActions.clickBulkDisableButton();
        searchPage.threadWait();
        searchPage.awaitTillElementDisplayed(searchPageActions.successMessage);

    }


    @FileToTest(value = "/manageFacetAndSearchableFieldTest/BrowsebulkEnableFeature.json")//
    @Test(description = "This test verifies editing and then enabling and disabling of features for browse field properties", priority = 6, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void BrowseEnableAndDisableFieldFeatures(Object jsonObject) throws InterruptedException {
        // Navigate to field properties page
        goTo(browseFieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();

        JsonObject fieldProperties = (JsonObject) jsonObject;
        String FieldName = fieldProperties.get("fieldName").getAsString();
        String Merchandisable = fieldProperties.get("featureMerchandisable").getAsString();
        String Facetable = fieldProperties.get("featureFacetable").getAsString();
        String Autosuggest = fieldProperties.get("featureAutosuggest").getAsString();
        String FieldRule = fieldProperties.get("featureFieldRule").getAsString();


        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickEditIcon();
        fieldPropertiesActions.selectFeature(Merchandisable);
        fieldPropertiesActions.selectFeature(Facetable);
        fieldPropertiesActions.selectFeature(Autosuggest);
        fieldPropertiesActions.selectFeature(FieldRule);
        fieldPropertiesActions.clickApplyButton();
        searchPage.awaitTillElementDisplayed(searchPageActions.successMessage);
        searchPage.threadWait();
        fieldPropertiesActions.refreshIcon.click();
        fieldPropertiesActions.refreshIcon.click();
        searchPage.threadWait();
        fieldPropertiesActions.isFeatureEnabled(Merchandisable);
        fieldPropertiesActions.isFeatureEnabled(Facetable);
        fieldPropertiesActions.isFeatureEnabled(Autosuggest);
        fieldPropertiesActions.isFeatureEnabled(FieldRule);


        //Disable
        goTo(browseFieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickEditIcon();
        fieldPropertiesActions.selectFeature(Merchandisable);
        fieldPropertiesActions.selectFeature(Facetable);
        fieldPropertiesActions.selectFeature(Autosuggest);
        fieldPropertiesActions.selectFeature(FieldRule);
        fieldPropertiesActions.clickApplyButton();
        searchPage.awaitTillElementDisplayed(searchPageActions.successMessage);
        searchPage.threadWait();
        fieldPropertiesActions.refreshIcon.click();
        searchPage.threadWait();
        fieldPropertiesActions.isFeatureDisabled(Merchandisable);
        fieldPropertiesActions.isFeatureDisabled(Facetable);
        fieldPropertiesActions.isFeatureDisabled(Autosuggest);
        fieldPropertiesActions.isFeatureDisabled(FieldRule);

    }

    @FileToTest(value = "/manageFacetAndSearchableFieldTest/browseEnableFeatureInManage&Promotion.json")
    @Test(description = "This test verifies that after enabling features for browse, they are available in Manage Facet and Promotions", priority = 7, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void BrowseEnableFieldFeaturesAndcheckInManageFacetAndPromotion(Object jsonObject) throws InterruptedException {
        // Navigate to field properties page
        goTo(browseFieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();

        JsonObject fieldProperties = (JsonObject) jsonObject;
        String FieldName = fieldProperties.get("fieldName").getAsString();
        String Merchandisable = fieldProperties.get("featureMerchandisable").getAsString();
        String Facetable = fieldProperties.get("featureFacetable").getAsString();
        String page = fieldProperties.get("query").getAsString();

        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickEditIcon();
        fieldPropertiesActions.selectFeature(Facetable);
        fieldPropertiesActions.selectFeature(Merchandisable);
        fieldPropertiesActions.clickApplyButton();
        searchPage.awaitTillElementDisplayed(searchPageActions.successMessage);

        fieldPropertiesActions.refreshIcon.click();
        searchPage.threadWait();
        fieldPropertiesActions.isFeatureEnabled(Facetable);
        fieldPropertiesActions.isFeatureEnabled(Merchandisable);

        //Manage
        goTo(browseFacetsPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        facetableFieldsActions.openCreateFacetForm();
        facetableFieldsActions.checkSelectedField(FieldName);
        ThreadWait();
        String FacetAttribute=merchandisingActions.FacetWrapper.getText();
        Assert.assertTrue(FacetAttribute.contains(FieldName));

        //promotion
        goTo(browsePage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        createBrowsePromotion(page,false,false);
        JsonArray object = fieldProperties.get("data").getAsJsonArray();
        Map<String, Object> campaignData = merchandisingActions.getCampaignData("browseBoosting.json");
        ThreadWait();
        searchPageActions.fillPageName(object);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
        merchandisingActions.selectAttributeValue(FieldName);
        String FieldAttribute=merchandisingActions.attributeDropDownList.getText();
        Assert.assertTrue(FieldAttribute.contains(FieldAttribute),FieldName);

        //Disable feature
        goTo(browseFieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickEditIcon();
        fieldPropertiesActions.selectFeature(Facetable);
        fieldPropertiesActions.selectFeature(Merchandisable);
        fieldPropertiesActions.clickApplyButton();
        fieldPropertiesActions.refreshIcon.click();
        searchPage.threadWait();
        fieldPropertiesActions.isFeatureDisabled(Facetable);
        fieldPropertiesActions.isFeatureDisabled(Merchandisable);


        goTo(browseFacetsPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        facetableFieldsActions.openCreateFacetForm();
        facetableFieldsActions.checkSelectedField(FieldName);
        ThreadWait();
        Assert.assertTrue(fieldPropertiesActions.manageAttributeCoundnotFind.isDisplayed());

        //promotion
        goTo(browsePage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        createBrowsePromotion(page,false,false);
        searchPageActions.fillPageName(object);
        merchandisingActions.fillCampaignData(campaignData);
        ThreadWait();
        merchandisingActions.goToLandingPage();
        merchandisingActions.goToSectionInMerchandising(UnbxdEnum.BOOST);
        merchandisingActions.selectAttributeValue(FieldName);
        Assert.assertTrue(fieldPropertiesActions.manageAttributeCoundnotFind.isDisplayed());
    }

    @FileToTest(value = "/manageFacetAndSearchableFieldTest/browseEnableFeaturesInAutosuggestAndFieldRule.json")
    @Test(description = "This test verifies that after enabling features for browse, they are available in Field Rule and Autosuggest", priority = 8, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void BrowseEnableFieldFeaturesAndcheckInFieldRule(Object jsonObject) throws InterruptedException {
        // Navigate to field properties page
        goTo(browseFieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();

        JsonObject fieldProperties = (JsonObject) jsonObject;
        String FieldName = fieldProperties.get("fieldName").getAsString();
        String Autosuggest = fieldProperties.get("featureAutosuggest").getAsString();
        String FieldRule = fieldProperties.get("featureFieldRule").getAsString();
        String query = fieldProperties.get("query").getAsString();
        String suggestion = fieldProperties.get("suggestionSection").getAsString();


        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickEditIcon();
        fieldPropertiesActions.selectFeature(Autosuggest);
        fieldPropertiesActions.selectFeature(FieldRule);
        fieldPropertiesActions.clickApplyButton();
        searchPage.awaitTillElementDisplayed(searchPageActions.successMessage);

        fieldPropertiesActions.refreshIcon.click();
        searchPage.threadWait();
        fieldPropertiesActions.isFeatureEnabled(Autosuggest);
        fieldPropertiesActions.isFeatureEnabled(FieldRule);

        //FieldRule
        goTo(browsePage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();
        searchPage.threadWait();
        createPromotion(query,false,false);
        ThreadWait();
        merchandisingActions.selectAttributeValue(FieldName);
        ThreadWait();
        String FacetAttribute=merchandisingActions.attributeDropDownList.getText();
        Assert.assertTrue(FacetAttribute.contains(FieldName));


        //Disable feature
        goTo(browseFieldPropertiesPage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        fieldPropertiesActions.selectField(FieldName);
        fieldPropertiesActions.clickEditIcon();
        fieldPropertiesActions.selectFeature(FieldRule);
        fieldPropertiesActions.selectFeature(Autosuggest);
        fieldPropertiesActions.clickApplyButton();
        fieldPropertiesActions.refreshIcon.click();
        searchPage.threadWait();
        fieldPropertiesActions.isFeatureDisabled(Autosuggest);
        fieldPropertiesActions.refreshIcon.click();
        fieldPropertiesActions.isFeatureDisabled(FieldRule);


        //FieldRule
        goTo(browsePage);
        searchPage.awaitForPageToLoad();
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();
        searchPage.threadWait();
        createPromotion(query,false,false);
        ThreadWait();
        merchandisingActions.selectAttributeValue(FieldName);
        ThreadWait();
        Assert.assertTrue(fieldPropertiesActions.manageAttributeCoundnotFind.isDisplayed());

    }
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.close();
        driver.quit();
        Helper.tearDown();
    }
}