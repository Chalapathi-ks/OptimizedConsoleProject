package UnbxdTests.testNG.consoleui.MerchTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonObject;
import core.consoleui.actions.BannerActions;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.actions.FacetActions;
import core.consoleui.actions.MerchandisingActions;
import core.consoleui.page.BrowseFacetsPage;
import core.consoleui.page.BrowsePage;
import core.consoleui.page.CommerceSearchPage;
import core.consoleui.page.searchableFieldsAndFacetsPage;
import core.ui.actions.FacetableFieldsActions;
import core.ui.actions.SearchableFieldActions;
import core.ui.components.FacetComponent;
import core.ui.page.FacetableFieldsPage;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import lib.compat.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static core.ui.page.UiBase.ThreadWait;

public class FieldRuleFacetTest extends MerchandisingTest {

    @Page
    MerchandisingActions merchandisingActions;

    @Page
    CommercePageActions searchPageActions;
    @Page
    BannerActions bannerActions;

    @Page
    FacetActions facetActions;

    @Page
    FacetableFieldsActions facetableFieldsActions;

    @Page
    SearchableFieldActions searchableFieldActions;
    @Page
    searchableFieldsAndFacetsPage manageSearchFacetAndSearchFieldPage;

    @Page
    FacetComponent facetComponenet;

    @Page
    BrowsePage browsePage;

    @Page
    CommerceSearchPage commerceSearchPage;
    @Page
    FacetableFieldsPage facetableFieldsPage;
    @Page
    BrowseFacetsPage browseFacetsPage;

    List<String> queryRules = new ArrayList<>();
    List<String> pageRules = new ArrayList<>();

    public String page;

    public String query;
    @FileToTest(value = "/consoleTestData/FieldRuleFacetCE.json")
    @Test(description = "Verifies the creation and editing of a search facet using a field rule.", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"sanity"})
    public void createAndEditSearchFieldRuleFacetTest(Object jsonObject) throws InterruptedException {
        JsonObject facetData = (JsonObject) jsonObject;
        query = facetData.get("Value").getAsString();
        String value = facetData.get("Value").getAsString();
        String Attribute = facetData.get("Attribute").getAsString();


        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();
       
        //create the rule
        createPromotion(query,false,false);
        ThreadWait();
        bannerActions.selectFieldRuleAttribute(Attribute);
        bannerActions.selectFieldRuleAttributeValue(value);
        ThreadWait();
        click(searchPageActions.nextButton);
        String facetDisplayName=facetActions.selectFacetAndGetSelectedFacetName();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        searchPage.queryRuleByName(query);
        queryRules.add(query);

        // Edit the rule
        searchPageActions.selectActionType(UnbxdEnum.EDIT,query);
        ThreadWait();
        facetActions.searchForField(facetDisplayName);
        Assert.assertTrue(facetActions.activeToggle.get(0).isDisplayed());
        facetActions.disableTheToggle();
        click(merchandisingActions.fieldRulePublishBtn);
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        searchPage.threadWait();
        searchPageActions.selectActionType(UnbxdEnum.EDIT,query);
        ThreadWait();
        facetActions.searchForField(facetDisplayName);
        facetActions.verifydTheToggleIsDisabledOrNot();
        click(merchandisingActions.fieldRulePublishBtn);
        merchandisingActions.verifySuccessMessage();

        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }

    @FileToTest(value = "/consoleTestData/FieldRuleBanner.json")
    @Test(description = "Verifies that facet ranking for a search field rule can be enabled and disabled.", priority = 8, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void enableAndDisableSearchFacetRankingTest(Object jsonObject) throws InterruptedException {
        JsonObject facetData = (JsonObject) jsonObject;
        query = facetData.get("Value").getAsString();
        String value = facetData.get("Value").getAsString();
        String Attribute = facetData.get("Attribute").getAsString();

        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();

        createPromotion(query,false,false);
        ThreadWait();
        bannerActions.selectFieldRuleAttribute(Attribute);
        bannerActions.selectFieldRuleAttributeValue(value);
        ThreadWait();
        click(searchPageActions.nextButton);
        String facetFieldName=facetActions.enableAndDisableTheRanking();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));

        searchPageActions.selectActionType(UnbxdEnum.EDIT,query);
        ThreadWait();
        facetActions.searchForField(facetFieldName);
        facetActions.afterUpdateTheRankingVerifythePosition();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();

        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }

    @FileToTest(value = "/consoleTestData/gobalFieldRule.json")
    @Test(description = "Verifies the filtering functionality for global search facets (text/range, enabled/disabled).", priority = 3,dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifyGlobalSearchFacetFiltersTest(Object jsonObject) throws InterruptedException {

        JsonObject globalData = (JsonObject) jsonObject;
        String Facet_TypeText = globalData.get("Facet_Type").getAsString();
        String FacetStatusEnabled = globalData.get("Facet_Status").getAsString();
        String Facet_TypeRange = globalData.get("FacetType").getAsString();
        String FacetStatusDisabled = globalData.get("FacetStatus").getAsString();

        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();
        createGlobalRulePromotion();

        //Text and enabled
        facetActions.facetFilterIcon();
        facetActions.applyTextAndEnabledFilter();
        facetActions.ApplyFilterButton();
        facetActions.verifyAppliedFilter(Facet_TypeText,FacetStatusEnabled);
        facetActions.verifyTextFilterAndEnabledFilter(Facet_TypeText);

        //Disable filter
        facetActions.facetFilterIcon();
        facetActions.applyTextAndEnabledFilter();
        facetActions.ApplyFilterButton();
        facetActions.verifyAppliedFilterIsNotPresent();

        //range and disabled
        facetActions.facetFilterIcon();
        facetActions.applyRangeAndDisabledFilter();
        facetActions.ApplyFilterButton();
        facetActions.verifyAppliedFilter(Facet_TypeRange,FacetStatusDisabled);
        facetActions.verifyRangeFilterAndDisabledFilter(Facet_TypeRange);
        click(merchandisingActions.fieldRulePublishBtn);
    }

    @FileToTest(value = "/consoleTestData/RangeFacetFieldRule.json")
    @Test(description = "Verifies adding, editing, and previewing a new range facet within a search field rule.", priority = 4, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"sanity"})
    public void createAndEditSearchRangeFacetInFieldRuleTest(Object data) throws InterruptedException {

        JsonObject object = (JsonObject) data;
        HashMap<String, Object> testData = Helper.convertJsonToHashMap(object.toString());
        String displayName = (String) testData.get("facetDisplayName");
        query = object.get("Value").getAsString();
        String value = object.get("Value").getAsString();
        String Attribute = object.get("Attribute").getAsString();
        String rangeStartValue = object.get("rangeStart").getAsString();
        String rangeEndValue = object.get("rangeStop").getAsString();
        String updatedRangeStartValue = object.get("updatedRangeStart").getAsString();
        String updatedRangeEndValue = object.get("updatedRangeStop").getAsString();

        goTo(manageSearchFacetAndSearchFieldPage);//Manage
        searchPage.threadWait();
        if (facetableFieldsActions.getFacetUsingDisplayName(displayName) != null) {
            click(facetableFieldsPage.deleteFacetIcon);
            facetableFieldsActions.awaitForElementPresence(facetableFieldsPage.deleteConfirmationTab);
            click(facetableFieldsPage.deleteYes);
            searchPage.threadWait();
            Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
        }
        //FieldRule facet creation Page
        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();

        //create the rule
        createPromotion(query, false, false);
        ThreadWait();
         bannerActions.selectFieldRuleAttribute(Attribute);
         bannerActions.selectFieldRuleAttributeValue(value);
        ThreadWait();
        click(searchPageActions.nextButton);
        facetableFieldsActions.openCreateFacetForm();
        String facetDisplayName = facetableFieldsActions.fillFacetDetails(testData);

        facetableFieldsActions.saveFacet();
        searchPage.threadWait();

        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));

        // Edit the rule
        searchPageActions.selectActionType(UnbxdEnum.EDIT, query);
        ThreadWait();
        facetActions.searchForField(facetDisplayName);
        facetActions.verifydTheToggleIsDisabledOrNot();
        facetActions.enableToggleByName(facetDisplayName);

        //facetActions.previewFacetHeader.getText().contains(facetDisplayName);
//        Assert.assertTrue(facetActions.rangeStart_value.getTextContent().contains(rangeStartValue));
//        Assert.assertTrue(facetActions.rangeEnd_value.getTextContent().contains(rangeEndValue));
        facetableFieldsActions.editFacetIcon.click();
        String facetUpdateDisplayName = facetableFieldsActions.fillUpdateFacetDetails(testData);
        facetableFieldsActions.updateFacet.click();
        ThreadWait();

        merchandisingActions.safeClick(merchandisingActions.fieldRulePublishBtn);
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        searchPage.threadWait();

        searchPageActions.selectActionType(UnbxdEnum.EDIT, query);
        ThreadWait();
        facetActions.searchForField(facetUpdateDisplayName);
        facetActions.verifydTheToggleIsEnabledOrNot();

        // facetActions.previewFacetHeader.getText().contains(facetUpdateDisplayName);
        facetActions.awaitForElementPresence(facetActions.rangeStart_value);
        Assert.assertTrue(facetActions.rangeStart_value.getTextContent().contains(updatedRangeStartValue));
        Assert.assertTrue(facetActions.rangeEnd_value.getTextContent().contains(updatedRangeEndValue));
        facetActions.disableTheToggle();

        click(merchandisingActions.fieldRulePublishBtn);
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);

        goTo(manageSearchFacetAndSearchFieldPage);//Manage
        searchPage.threadWait();
        facetableFieldsActions.deleteFacet1(facetUpdateDisplayName);
        searchPage.threadWait();
        Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
        goTo(manageSearchFacetAndSearchFieldPage);
        searchPage.threadWait();
        Assert.assertNull(facetableFieldsActions.getFacetUsingDisplayName(facetUpdateDisplayName), "Facet creation is failing!!!");


        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }

    @FileToTest(value = "/consoleTestData/FieldRuleFacet.json")//check
    @Test(description = "Verifies adding, editing, and previewing a facet and deleting in manage section.", priority = 5, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"sanity"})
    public void FacetConfigurationFieldRuleTest(Object data) throws InterruptedException {

        JsonObject object = (JsonObject) data;
        HashMap<String, Object> testData = Helper.convertJsonToHashMap(object.toString());
        String displayName = (String) testData.get("facetDisplayName");
        query = object.get("Value").getAsString();
        String value = object.get("Value").getAsString();
        String Attribute = object.get("Attribute").getAsString();
        String facetLenght = object.get("facetLength").getAsString();
        String updatedFacetLenght = object.get("updatedfacetLength").getAsString();


        goTo(manageSearchFacetAndSearchFieldPage);//Manage
        searchPage.threadWait();
        if (facetableFieldsActions.getFacetUsingDisplayName(displayName) != null) {
            click(facetableFieldsPage.deleteFacetIcon);
            facetableFieldsActions.awaitForElementPresence(facetableFieldsPage.deleteConfirmationTab);
            click(facetableFieldsPage.deleteYes);
            searchPage.threadWait();
            Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
        }
        //FieldRule facet creation Page
        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();

        //create the rule
        createPromotion(query, false, false);
        ThreadWait();
        bannerActions.selectFieldRuleAttribute(Attribute);
        bannerActions.selectFieldRuleAttributeValue(value);
        ThreadWait();
        click(searchPageActions.nextButton);
        facetableFieldsActions.openCreateFacetForm();
        String facetDisplayName = facetableFieldsActions.fillFacetDetails(testData);
        facetableFieldsActions.saveFacet();
        searchPage.threadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));

        // Edit the rule
        searchPageActions.selectActionType(UnbxdEnum.EDIT, query);
        ThreadWait();
        facetActions.searchForField(facetDisplayName);
        facetActions.verifydTheToggleIsDisabledOrNot();
        facetActions.enableToggleByName(facetDisplayName);

        // Check if any element in the previewFacetHeader list contains the facetDisplayName
        boolean facetFound = false;
        for (int i = 0; i < facetActions.previewFacetHeader.size(); i++) {
            if (facetActions.previewFacetHeader.get(i).getText().toLowerCase().contains(facetDisplayName)) {
                facetFound = true;
                break;
            }
        }
        Assert.assertTrue(facetFound, "Facet with display name '" + facetDisplayName + "' not found in preview headers");
        int fLenght = facetActions.getFacetLengthListByHeader(displayName).size();
        Assert.assertEquals(String.valueOf(fLenght), facetLenght);
        facetableFieldsActions.editFacetIcon.click();
        String facetUpdateDisplayName = facetableFieldsActions.fillUpdateFacetDetails(testData);
        facetableFieldsActions.updateFacet.click();
        ThreadWait();

        merchandisingActions.safeClick(merchandisingActions.fieldRulePublishBtn);
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        searchPage.threadWait();

        searchPageActions.selectActionType(UnbxdEnum.EDIT, query);
        ThreadWait();
        facetActions.searchForField(facetUpdateDisplayName);
        facetActions.verifydTheToggleIsEnabledOrNot();

        // Check if any element in the previewFacetHeader list contains the facetUpdateDisplayName
        boolean facetUpdateFound = false;
        for (int i = 0; i < facetActions.previewFacetHeader.size(); i++) {
            if (facetActions.previewFacetHeader.get(i).getText().toLowerCase().contains(facetUpdateDisplayName)) {
                facetUpdateFound = true;
                break;
            }
        }
        Assert.assertTrue(facetUpdateFound, "Updated facet with display name '" + facetUpdateDisplayName + "' not found in preview headers");
        int f_Lenght = facetActions.getFacetLengthListByHeader(facetUpdateDisplayName).size();
        Assert.assertEquals(String.valueOf(f_Lenght), updatedFacetLenght);
        facetActions.disableTheToggle();

        click(merchandisingActions.fieldRulePublishBtn);
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();
        Assert.assertNotNull(searchPage.queryRuleByName(query));
        queryRules.add(query);

        goTo(manageSearchFacetAndSearchFieldPage);//Manage
        searchPage.threadWait();
        facetableFieldsActions.deleteFacet1(facetUpdateDisplayName);
        searchPage.threadWait();
        Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
        goTo(manageSearchFacetAndSearchFieldPage);
        searchPage.threadWait();
        Assert.assertNull(facetableFieldsActions.getFacetUsingDisplayName(facetUpdateDisplayName), "Facet creation is failing!!!");

        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();
    }


    @FileToTest(value = "/consoleTestData/FieldRuleFacetDetail.json")
    @Test(description = "Verifies that the 'View Details' functionality works for a facet within a search field rule.", priority = 6, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"sanity"})
    public void verifySearchFieldRuleFacetDetailsTest(Object data) throws InterruptedException {
        JsonObject object = (JsonObject) data;
        HashMap<String, Object> testData = Helper.convertJsonToHashMap(object.toString());
        String displayName = (String) testData.get("facetDisplayName") + System.currentTimeMillis();
        query = object.get("Value").getAsString();
        String value = object.get("Value").getAsString();
        String Attribute = object.get("Attribute").getAsString();
        String facetLenght = object.get("facetLength").getAsString();

        goTo(manageSearchFacetAndSearchFieldPage);//Manage
        searchPage.threadWait();
        if (facetableFieldsActions.getFacetUsingDisplayName(displayName) != null) {
            click(facetableFieldsPage.deleteFacetIcon);
            facetableFieldsActions.awaitForElementPresence(facetableFieldsPage.deleteConfirmationTab);
            click(facetableFieldsPage.deleteYes);
            searchPage.threadWait();
            Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
        }
        //FieldRule facet creation Page
        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();

        //create the rule
        createPromotion(query, false, false);
        ThreadWait();
        bannerActions.selectFieldRuleAttribute(Attribute);
        bannerActions.selectFieldRuleAttributeValue(value);
        ThreadWait();
        click(searchPageActions.nextButton);
        facetableFieldsActions.openCreateFacetForm();
        String facetDisplayName = facetableFieldsActions.fillFacetDetails(testData);
        facetableFieldsActions.saveFacet();
        searchPage.threadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(query));

        // Edit the rule
        searchPageActions.selectActionType(UnbxdEnum.EDIT, query);
        ThreadWait();
        facetActions.searchForField(facetDisplayName);
        facetActions.verifydTheToggleIsDisabledOrNot();
        facetActions.enableToggleByName(query);
        click(facetActions.viewDetails);
        Assert.assertTrue(facetActions.awaitForElementPresence(facetActions.facetDetailsConfig));
        Assert.assertTrue(facetActions.awaitForElementPresence(facetActions.facetDetails));
        searchPage.threadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();

        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.deleteQueryRule(query);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }



//Test case for browse

    @FileToTest(value = "/consoleTestData/BrowseFieldRuleFacet.json")
    @Test(description = "Verifies that the 'View Details' functionality works for a facet within a browse field rule.", priority = 7, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"sanity"})
    public void verifyBrowseFieldRuleFacetDetailsTest(Object data) throws InterruptedException {
        JsonObject object = (JsonObject) data;
        HashMap<String, Object> testData = Helper.convertJsonToHashMap(object.toString());
        String displayName = (String) testData.get("facetDisplayName") + System.currentTimeMillis();
        page = object.get("Value").getAsString();
        String value = object.get("Value").getAsString();
        String Attribute = object.get("Attribute").getAsString();
        String facetLenght = object.get("facetLength").getAsString();


        //Manage
        goTo(browseFacetsPage);
        searchPage.threadWait();
        if (facetableFieldsActions.getFacetUsingDisplayName(displayName) != null) {
            click(facetableFieldsPage.deleteFacetIcon);
            facetableFieldsActions.awaitForElementPresence(facetableFieldsPage.deleteConfirmationTab);
            click(facetableFieldsPage.deleteYes);
            searchPage.threadWait();
            Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
        }
        //FieldRule facet creation Page
        goTo(browsePage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();

        //create the rule
        createBrowsePromotion(page,false,false);
        ThreadWait();
        bannerActions.selectFieldRuleAttribute(Attribute);
        bannerActions.selectFieldRuleAttributeValue(value);
        ThreadWait();
        click(searchPageActions.nextButton);
        facetableFieldsActions.openCreateFacetForm();
        String facetDisplayName = facetableFieldsActions.fillFacetDetails(testData);
        facetableFieldsActions.saveFacet();
        searchPage.threadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(page));

        // Edit the rule
        searchPageActions.selectActionType(UnbxdEnum.EDIT, page);
        ThreadWait();
        facetActions.searchForField(facetDisplayName);
        facetActions.verifydTheToggleIsDisabledOrNot();
        facetActions.enableToggleByName(page);
        click(facetActions.viewDetails);
        Assert.assertTrue(facetActions.awaitForElementPresence(facetActions.facetDetailsConfig));
        Assert.assertTrue(facetActions.awaitForElementPresence(facetActions.facetDetails));
        searchPage.threadWait();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();

        goTo(browsePage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.deleteQueryRule(page);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

    }



    @FileToTest(value = "/consoleTestData/FieldRuleBrowseBanner.json")
    @Test(description = "Verifies the creation and editing of a browse facet using a field rule.", priority = 2, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"sanity"})
    public void createAndEditBrowseFieldRuleFacetTest(Object jsonObject) throws InterruptedException {
        JsonObject facetData = (JsonObject) jsonObject;
        page = facetData.get("Value").getAsString();
        String value = facetData.get("Value").getAsString();
        String Attribute = facetData.get("Attribute").getAsString();

        goTo(browsePage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();

        //create the rule
        //createBrowsePromotion(page,false,false);
        createBrowsePromotion(page,false,false);
        ThreadWait();
        bannerActions.selectFieldRuleAttribute(Attribute);
        bannerActions.selectFieldRuleAttributeValue(value);
        ThreadWait();
        click(searchPageActions.nextButton);
        String facetDisplayName=facetActions.selectFacetAndGetSelectedFacetName();
        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(page));
        pageRules.add(page);

        // Edit the rule
        searchPageActions.selectActionType(UnbxdEnum.EDIT,page);
        ThreadWait();
        facetActions.searchForField(facetDisplayName);
        Assert.assertTrue(facetActions.activeToggle.get(0).isDisplayed());
        facetActions.disableTheToggle();
        click(merchandisingActions.fieldRulePublishBtn);
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        searchPage.threadWait();
        searchPageActions.selectActionType(UnbxdEnum.EDIT,page);
        ThreadWait();
        facetActions.searchForField(facetDisplayName);
        facetActions.verifydTheToggleIsDisabledOrNot();
        click(merchandisingActions.fieldRulePublishBtn);
        merchandisingActions.verifySuccessMessage();

        //searchPageActions.fillPageName(page);
        // click(bannerActions.browseFieldRuleMatchCondtion);
        goTo(browsePage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.deleteQueryRule(page);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();


    }

    @FileToTest(value = "/consoleTestData/gobalFieldRule.json")
    @Test(description = "Verifies the filtering functionality for global browse facets (text/range, enabled/disabled).", priority = 9,dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void browseGlobalFacetWithFilterTest(Object jsonObject) throws InterruptedException {

        JsonObject globalData = (JsonObject) jsonObject;
        String Facet_TypeText = globalData.get("Facet_Type").getAsString();
        String FacetStatusEnabled = globalData.get("Facet_Status").getAsString();
        String Facet_TypeRange = globalData.get("FacetType").getAsString();
        String FacetStatusDisabled = globalData.get("FacetStatus").getAsString();

        goTo(browsePage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();
        createGlobalRulePromotion();

        //Text and enabled
        facetActions.facetFilterIcon();
        facetActions.applyTextAndEnabledFilter();
        facetActions.ApplyFilterButton();
        facetActions.verifyAppliedFilter(Facet_TypeText,FacetStatusEnabled);
        facetActions.verifyTextFilterAndEnabledFilter(Facet_TypeText);

        //Disable filter
        facetActions.facetFilterIcon();
        facetActions.applyTextAndEnabledFilter();
        facetActions.ApplyFilterButton();
        facetActions.verifyAppliedFilterIsNotPresent();

        //range and disabled
        facetActions.facetFilterIcon();
        facetActions.applyRangeAndDisabledFilter();
        facetActions.ApplyFilterButton();
        facetActions.verifyAppliedFilter(Facet_TypeRange,FacetStatusDisabled);
        facetActions.verifyRangeFilterAndDisabledFilter(Facet_TypeRange);
        click(merchandisingActions.fieldRulePublishBtn);
    }

    @FileToTest(value = "/consoleTestData/FieldRuleFacetTestRanking.json")
    @Test(description = "Verifies adding, editing, and previewing a facet and deleting in manage section.", priority = 5, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"sanity"})
    public void BrowseFacetConfigurationFieldRuleTest(Object data) throws InterruptedException {

        JsonObject object = (JsonObject) data;
        HashMap<String, Object> testData = Helper.convertJsonToHashMap(object.toString());
        String displayName = (String) testData.get("facetDisplayName");
        page = object.get("Value").getAsString();
        String value = object.get("Value").getAsString();
        String Attribute = object.get("Attribute").getAsString();
        String FacetLenght = object.get("facetLength").getAsString();
        String updatedFacetLenght = object.get("updatedfacetLength").getAsString();

       //Manage
        goTo(browseFacetsPage);
        searchPage.threadWait();
        if (facetableFieldsActions.getFacetUsingDisplayName(displayName) != null) {
            searchPage.threadWait();
            click(facetableFieldsPage.deleteFacetIcon);
            facetableFieldsActions.awaitForElementPresence(facetableFieldsPage.deleteConfirmationTab);
            click(facetableFieldsPage.deleteYes);
            searchPage.threadWait();
            Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
        }
        //FieldRule facet creation Page
        goTo(browsePage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();

        //create the rule
        createBrowsePromotion(page,false,false);
        ThreadWait();
        bannerActions.selectFieldRuleAttribute(Attribute);
        bannerActions.selectFieldRuleAttributeValue(value);
        ThreadWait();
        click(searchPageActions.nextButton);
        facetableFieldsActions.openCreateFacetForm();
        String facetDisplayName = facetableFieldsActions.fillFacetDetails(testData);

        facetableFieldsActions.saveFacet();
        searchPage.threadWait();

        merchandisingActions.publishCampaign();
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        Assert.assertNotNull(searchPage.queryRuleByName(page));

        // Edit the rule
        searchPageActions.selectActionType(UnbxdEnum.EDIT,page);
        ThreadWait();
        facetActions.searchForField(facetDisplayName);
        facetActions.verifydTheToggleIsDisabledOrNot();
        facetActions.enableToggleByName(facetDisplayName);

        // Check if any element in the previewFacetHeader list contains the facetDisplayName
        boolean facetFound = false;
        for (int i = 0; i < facetActions.previewFacetHeader.size(); i++) {
            if (facetActions.previewFacetHeader.get(i).getText().toLowerCase().contains(facetDisplayName)) {
                facetFound = true;
                break;
            }
        }
        Assert.assertTrue(facetFound, "Facet with display name '" + facetDisplayName + "' not found in preview headers");
        int facetLength = facetActions.getFacetLengthListByHeader(facetDisplayName).size();
        Assert.assertEquals(String.valueOf(facetLength), FacetLenght);
        facetableFieldsActions.editFacetIcon.click();
        String facetUpdateDisplayName = facetableFieldsActions.fillUpdateFacetDetails(testData);
        facetableFieldsActions.updateFacet.click();
        ThreadWait();

        merchandisingActions.safeClick(merchandisingActions.fieldRulePublishBtn);
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        searchPage.threadWait();

        searchPageActions.selectActionType(UnbxdEnum.EDIT,page);
        ThreadWait();
        facetActions.searchForField(facetUpdateDisplayName);
        facetActions.verifydTheToggleIsEnabledOrNot();

        // Check if any element in the previewFacetHeader list contains the facetUpdateDisplayName
        boolean facetUpdateFound = false;
        for (int i = 0; i < facetActions.previewFacetHeader.size(); i++) {
            if (facetActions.previewFacetHeader.get(i).getText().toLowerCase().contains(facetUpdateDisplayName)) {
                facetUpdateFound = true;
                break;
            }
        }
        Assert.assertTrue(facetUpdateFound, "Updated facet with display name '" + facetUpdateDisplayName + "' not found in preview headers");
        int f_Lenght = facetActions.getFacetLengthListByHeader(facetUpdateDisplayName).size();
        Assert.assertEquals(String.valueOf(f_Lenght), updatedFacetLenght);
        facetActions.disableTheToggle();

        click(merchandisingActions.fieldRulePublishBtn);
        merchandisingActions.verifySuccessMessage();
        ThreadWait();
        goTo(searchPage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.awaitForPageToLoad();

        goTo(searchableFieldActions);
        searchPage.threadWait();
        facetableFieldsActions.deleteFacet1(facetUpdateDisplayName);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();

        goTo(browsePage);
        searchPage.threadWait();
        merchandisingActions.goToSection(UnbxdEnum.FACETS);
        searchPageActions.deleteQueryRule(page);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        searchPage.threadWait();
    }



//    @AfterClass(alwaysRun = true,groups={"sanity"})
//    public void deleteCreatedRules() throws InterruptedException {
//        goTo(searchPage);
//        merchandisingActions.goToSection(UnbxdEnum.FACETS);
//        for(String queryRule: queryRules)
//        {
//            if(searchPage.queryRuleByName(queryRule)!=null)
//            {
//                searchPageActions.deleteQueryRule(queryRule);
//                Assert.assertNull(searchPage.queryRuleByName(queryRule),"SEARCH RULE: CREATED QUERY RULE IS NOT DELETED");
//                getDriver().navigate().refresh();
//                ThreadWait();
//            }
//        }
//
//        goTo(browsePage);
//        merchandisingActions.goToSection(UnbxdEnum.FACETS);
//        for(String pageRule: pageRules)
//        {
//            if(searchPage.queryRuleByName(pageRule)!=null)
//            {
//                searchPageActions.deleteQueryRule(pageRule);
//                Assert.assertNull(searchPage.queryRuleByName(pageRule),"BROWSE RULE : CREATED PAGE RULE IS NOT DELETED");
//                getDriver().navigate().refresh();
//                ThreadWait();
//
//
//            }
//        }
//    }

}