package UnbxdTests.testNG.ui.SearchManageTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import UnbxdTests.testNG.ui.BaseTest;
import com.google.gson.JsonObject;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.actions.FacetActions;
import core.consoleui.page.BrowseFacetsPage;
import core.consoleui.page.searchableFieldsAndFacetsPage;
import core.ui.actions.FacetableFieldsActions;
import core.ui.actions.LoginActions;
import core.ui.actions.SearchableFieldActions;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import lib.compat.Page;
import lib.compat.FluentWebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import lib.EnvironmentConfig;

import java.util.*;

import static core.ui.page.UiBase.ThreadWait;
import static lib.constants.UnbxdErrorConstants.DUPLICATE_FACET_ERROR;

public class manageSearchFacetTest extends BaseTest {

    @Page
    LoginActions loginActions;

    @Page
    FacetableFieldsActions facetableFieldsActions;

    @Page
    FacetActions facetActions;

    private static List<String> createdFacets = new ArrayList<>();

    private String facetSection = "FACETABLE_FIELD";

    @Page
    public
    CommercePageActions searchPage;

    @Page
    CommercePageActions searchPageActions;

    @Page
    searchableFieldsAndFacetsPage manageSearchFacetAndSearchFieldPage;
    @Page
    BrowseFacetsPage browseFacetsPage;
    private String pageCount = "100";

    @BeforeClass
    public void init() throws InterruptedException {
        super.setUp();
        this.initFluent(driver);
        initTest();
        // Set context before navigation - using site 1 and user 1 as per ProdAPAC.yaml configuration
        EnvironmentConfig.unSetContext();
        EnvironmentConfig.setContext(2, 2);
        // Login is now handled by BaseTest's global login mechanism
        searchPage.threadWait();
    }

    @FileToTest(value = "manageFacetAndSearchableFieldTest/createFacetTest.json")
    @Test(description = "This test verifies that facet ranking can be enabled, disabled, and updated for search facets.", priority = 1,dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void FacetRanking(Object data) throws InterruptedException {
        goTo(manageSearchFacetAndSearchFieldPage);
        String facetFieldName = facetActions.enableAndDisableTheRanking();
        Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
        facetActions.searchForField(facetFieldName);
        facetActions.afterUpdateTheRankingVerifythePosition();
    }

    @FileToTest(value = "manageFacetAndSearchableFieldTest/createFacet.json")
    @Test(description = "This test verifies the creation, update, and deletion of a standard search facet.", priority = 2,dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void createUpdateDeleteFacetTest(Object data) throws InterruptedException {
        JsonObject object = (JsonObject) data;
        HashMap<String, Object> testData = Helper.convertJsonToHashMap(object.toString());
        String displayName = (String) testData.get("facetDisplayName") + System.currentTimeMillis();
        String displayUpdateName = (String) testData.get("updateDisplayName") + System.currentTimeMillis();
        String facetName = (String) testData.get("facetName");


        testData.put("facetDisplayName", displayName);
        testData.put("updatedFacetDisplayName", displayUpdateName);

        goTo(manageSearchFacetAndSearchFieldPage);
        if (facetableFieldsActions.getFacetUsingFieldName(facetName) != null) {
            click(facetableFieldsActions.deleteFacetIcon);
            facetableFieldsActions.awaitForElementPresence(facetableFieldsActions.deleteConfirmationTab);
            click(facetableFieldsActions.deleteYes);
            Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
        }

        goTo(manageSearchFacetAndSearchFieldPage);
        facetableFieldsActions.openCreateFacetForm();
        String facetDisplayName = facetableFieldsActions.fillFacetDetails(testData);
        facetableFieldsActions.saveFacet();
        Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
        Assert.assertNotNull(facetableFieldsActions.getFacetUsingDisplayName(facetDisplayName),"Facet creation is failing!!!");

       // Edit
        facetableFieldsActions.editFacetIcon.click();
        String facetUpdateDisplayName = facetableFieldsActions.fillUpdateFacetDetails(testData);
        facetableFieldsActions.saveFacet();
        searchPage.awaitTillElementDisplayed(manageSearchFacetAndSearchFieldPage.updateMessageNotification);
        searchPage.waitForElementToDisappear(manageSearchFacetAndSearchFieldPage.updateMessageNotification);
        createdFacets.add(facetUpdateDisplayName);
        goTo(manageSearchFacetAndSearchFieldPage);
        Assert.assertNotNull(facetableFieldsActions.getFacetUsingDisplayName(facetUpdateDisplayName),"Facet creation is failing!!!");

       // Delete
        goTo(manageSearchFacetAndSearchFieldPage);
        facetableFieldsActions.deleteFacet(facetUpdateDisplayName);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        goTo(manageSearchFacetAndSearchFieldPage);
        Assert.assertNull(searchPage.queryRuleByName(facetUpdateDisplayName), "CREATED QUERY RULE IS NOT DELETED");
    }

    @FileToTest(value = "manageFacetAndSearchableFieldTest/RangeFacetTest.json")
    @Test(description = "This test verifies the creation, update, and deletion of a range search facet.", priority = 3,dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void createUpdateDeleteRangeFacetTest(Object data) throws InterruptedException {
        JsonObject object = (JsonObject) data;
        HashMap<String, Object> testData = Helper.convertJsonToHashMap(object.toString());
        String displayName = (String) testData.get("facetDisplayName") + System.currentTimeMillis();
        String facetName = (String) testData.get("facetName");

        goTo(manageSearchFacetAndSearchFieldPage);
        if (facetableFieldsActions.getFacetUsingFieldName(facetName) != null) {
            click(facetableFieldsActions.deleteFacetIcon);
            facetableFieldsActions.awaitForElementPresence(facetableFieldsActions.deleteConfirmationTab);
            click(facetableFieldsActions.deleteYes);
            Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
        }

        goTo(manageSearchFacetAndSearchFieldPage);
        facetableFieldsActions.openCreateFacetForm();
        String facetDisplayName = facetableFieldsActions.fillFacetDetails(testData);
        facetableFieldsActions.saveFacet();
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        Assert.assertNotNull(facetableFieldsActions.getFacetUsingDisplayName(facetDisplayName),"Facet creation is failing!!!");

        // Edit
        facetableFieldsActions.editFacetIcon.click();
        String facetUpdateDisplayName = facetableFieldsActions.fillUpdateFacetDetails(testData);
        facetableFieldsActions.saveFacet();
        searchPage.awaitTillElementDisplayed(manageSearchFacetAndSearchFieldPage.updateMessageNotification);
        searchPage.waitForElementToDisappear(manageSearchFacetAndSearchFieldPage.updateMessageNotification);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        createdFacets.add(facetUpdateDisplayName);
        goTo(manageSearchFacetAndSearchFieldPage);
        Assert.assertNotNull(facetableFieldsActions.getFacetUsingDisplayName(facetUpdateDisplayName),"Facet creation is failing!!!");

        // Delete
        goTo(manageSearchFacetAndSearchFieldPage);
        facetableFieldsActions.deleteFacet(facetUpdateDisplayName);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        goTo(manageSearchFacetAndSearchFieldPage);
        Assert.assertNull(searchPage.queryRuleByName(facetUpdateDisplayName), "CREATED QUERY RULE IS NOT DELETED");
    }



    //Browse
    @FileToTest(value = "manageFacetAndSearchableFieldTest/createFacetTest.json")
    @Test(description = "This test verifies that facet ranking can be enabled, disabled, and updated for browse facets.", priority = 4,dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void BrowseFacetRanking(Object data) throws InterruptedException {
        goTo(browseFacetsPage);
        String facetFieldName = facetActions.enableAndDisableTheRanking();
        Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
        facetActions.searchForField(facetFieldName);
        facetActions.afterUpdateTheRankingVerifythePosition();
    }

    @FileToTest(value = "manageFacetAndSearchableFieldTest/browsereateFacet.json")
    @Test(description = "This test verifies the creation, update, and deletion of a standard browse facet.", priority = 5,dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void browseCreateUpdateDeleteFacetTest(Object data) throws InterruptedException {
        JsonObject object = (JsonObject) data;
        HashMap<String, Object> testData = Helper.convertJsonToHashMap(object.toString());
        String displayName = (String) testData.get("facetDisplayName") + System.currentTimeMillis();
        String displayUpdateName = (String) testData.get("updateDisplayName") + System.currentTimeMillis();
        String facetName = (String) testData.get("facetName");


        testData.put("facetDisplayName", displayName);
        testData.put("updatedFacetDisplayName", displayUpdateName);

        goTo(browseFacetsPage);
        if (facetableFieldsActions.getFacetUsingFieldName(facetName) != null) {
            click(facetableFieldsActions.deleteFacetIcon);
            facetableFieldsActions.awaitForElementPresence(facetableFieldsActions.deleteConfirmationTab);
            click(facetableFieldsActions.deleteYes);
            Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
        }

        goTo(browseFacetsPage);
        facetableFieldsActions.openCreateFacetForm();
        String facetDisplayName = facetableFieldsActions.fillFacetDetails(testData);
        facetableFieldsActions.saveFacet();
        Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
        Assert.assertNotNull(facetableFieldsActions.getFacetUsingDisplayName(facetDisplayName),"Facet creation is failing!!!");

        // Edit
        facetableFieldsActions.editFacetIcon.click();
        String facetUpdateDisplayName = facetableFieldsActions.fillUpdateFacetDetails(testData);
        facetableFieldsActions.saveFacet();
        searchPage.awaitTillElementDisplayed(manageSearchFacetAndSearchFieldPage.updateMessageNotification);
        searchPage.waitForElementToDisappear(manageSearchFacetAndSearchFieldPage.updateMessageNotification);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        createdFacets.add(facetUpdateDisplayName);
        goTo(manageSearchFacetAndSearchFieldPage);
        Assert.assertNotNull(facetableFieldsActions.getFacetUsingDisplayName(facetUpdateDisplayName),"Facet creation is failing!!!");

        // Delete
        goTo(browseFacetsPage);
        facetableFieldsActions.deleteFacet(facetUpdateDisplayName);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        goTo(browseFacetsPage);
        Assert.assertNull(searchPage.queryRuleByName(facetUpdateDisplayName), "CREATED QUERY RULE IS NOT DELETED");
    }

    @FileToTest(value = "manageFacetAndSearchableFieldTest/BrowseRangeFacetTest.json")
    @Test(description = "This test verifies the creation, update, and deletion of a range browse facet.", priority = 6,dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void browseCreateUpdateDeleteRangeFacetTest(Object data) throws InterruptedException {
        JsonObject object = (JsonObject) data;
        HashMap<String, Object> testData = Helper.convertJsonToHashMap(object.toString());
        String displayName = (String) testData.get("facetDisplayName") + System.currentTimeMillis();
        String facetName = (String) testData.get("facetName");


        goTo(browseFacetsPage);
        if (facetableFieldsActions.getFacetUsingFieldName(facetName) != null) {
            click(facetableFieldsActions.deleteFacetIcon);
            facetableFieldsActions.awaitForElementPresence(facetableFieldsActions.deleteConfirmationTab);
            click(facetableFieldsActions.deleteYes);
            Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
        }

        goTo(browseFacetsPage);
        facetableFieldsActions.openCreateFacetForm();
        String facetDisplayName = facetableFieldsActions.fillFacetDetails(testData);
        facetableFieldsActions.saveFacet();
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        Assert.assertNotNull(facetableFieldsActions.getFacetUsingDisplayName(facetDisplayName),"Facet creation is failing!!!");

        // Edit
        facetableFieldsActions.editFacetIcon.click();
        String facetUpdateDisplayName = facetableFieldsActions.fillUpdateFacetDetails(testData);
        facetableFieldsActions.saveFacet();
        searchPage.awaitTillElementDisplayed(manageSearchFacetAndSearchFieldPage.updateMessageNotification);
        searchPage.waitForElementToDisappear(manageSearchFacetAndSearchFieldPage.updateMessageNotification);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        createdFacets.add(facetUpdateDisplayName);
        goTo(manageSearchFacetAndSearchFieldPage);
        Assert.assertNotNull(facetableFieldsActions.getFacetUsingDisplayName(facetUpdateDisplayName),"Facet creation is failing!!!");

        // Delete
        goTo(browseFacetsPage);
        facetableFieldsActions.deleteFacet(facetUpdateDisplayName);
        searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess);
        goTo(browseFacetsPage);
        Assert.assertNull(searchPage.queryRuleByName(facetUpdateDisplayName), "CREATED QUERY RULE IS NOT DELETED");
    }

//    @AfterClass(alwaysRun = true)
//    public void deleteCreatedFacets() throws InterruptedException
//    {
//        for (String name : createdFacets) {
//            goTo(manageSearchFacetAndSearchFieldPage);
//            searchPage.threadWait();
//            facetableFieldsActions.deleteFacet(name);
//            Assert.assertTrue(searchPage.awaitTillElementDisplayed(searchPageActions.ToasterSuccess));
//        }
//    }
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.close();
        driver.quit();
        Helper.tearDown();
    }
}