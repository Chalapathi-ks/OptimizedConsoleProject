package UnbxdTests.testNG.ui.SearchManageTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import UnbxdTests.testNG.ui.BaseTest;
import com.google.gson.JsonObject;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.actions.manageSearchableFieldActions;
import core.consoleui.page.searchableFieldsAndFacetsPage;
import core.ui.actions.LoginActions;
import core.ui.actions.SearchableFieldActions;
import lib.Helper;
import lib.annotation.FileToTest;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import lib.EnvironmentConfig;

import java.util.ArrayList;
import java.util.List;

public class manageSearchFieldTest extends BaseTest {

    @Page
    LoginActions loginActions;

    @Page
    SearchableFieldActions searchableFieldActions;

    private static List<String> createdFacets = new ArrayList<>();

    private String facetSection = "FACETABLE_FIELD";

    @Page
    CommercePageActions searchPage;

    @Page
    manageSearchableFieldActions ManageSearchableFieldActions;

    @BeforeClass
    public void init() throws InterruptedException {
        super.setUp();
        this.initFluent(driver);
        initTest();
        // Set context before navigation - using site 1 and user 1 as per ProdAPAC.yaml configuration
        EnvironmentConfig.unSetContext();
        EnvironmentConfig.setContext(1, 1);
        // Login is now handled by BaseTest global login
        searchPage.threadWait();
    }

    @FileToTest(value = "manageFacetAndSearchableFieldTest/searchFieldTest.json")
    @Test(description = "This test Verifies the searchWeights for few aiFields", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void SearchableFieldTest(JsonObject object) throws InterruptedException {
        /*try {
            goTo(ManageSearchableFieldActions);
            JsonArray fieldsList = object.get("fields").getAsJsonArray();
            String ExpectedSearchWeight = object.get("ExpectedSearchWeight").getAsString();

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


    @FileToTest(value = "manageFacetAndSearchableFieldTest/searchableFieldTest.json")
    @Test(description = "This test verifies the product coverage section is coming or not", priority = 2,dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifyProductCoverageTest(JsonObject object) throws InterruptedException {
        try
        {

            String name = object.get("attributeName").getAsString();
            String expectedPercentage = object.get("productCoveragePercentage").getAsString();

            goTo(ManageSearchableFieldActions);
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

    @FileToTest(value = "manageFacetAndSearchableFieldTest/searchableFieldTest.json")
    @Test(description = "This testcase verifies edit functionality of searchable fields", priority = 3,dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifySearchWeightEditTest(JsonObject object) throws InterruptedException {
        try {
            String name = object.get("attributeName").getAsString();
            String editWeight = object.get("editWeight").getAsString();
            String count = object.get("pageCount").getAsString();

            goTo(ManageSearchableFieldActions);
            await();
            FluentWebElement attributeName = searchableFieldActions.getAttributeUsingDisplayName(name);
            String searchWeight = searchableFieldActions.getSearchWeight(attributeName);

            ManageSearchableFieldActions.selectSearchWeightFromDropdown(editWeight);
            ManageSearchableFieldActions.saveChanges();
            ManageSearchableFieldActions.verifySuccessMessage();;

            FluentWebElement EditedAttributeName = searchableFieldActions.getAttributeUsingDisplayName(name);
            String searchWeight2 = searchableFieldActions.getSearchWeight(EditedAttributeName);
            Assert.assertEquals(searchWeight2, editWeight);

        } catch (NoSuchElementException exception) {
            System.out.println("There is no such element present");
            Assert.fail("No such element failure" + exception);
        } catch (Exception e) {
            Assert.fail("verifySearchWeightEditTestCase got failed" + e);
        }

    }

    @FileToTest(value = "manageFacetAndSearchableFieldTest/searchableFieldTest.json")
    @Test(description = "This testcase verifies AI recommendation link functionality", priority = 4,dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifyApplyAiRecommendationTest(JsonObject object) throws InterruptedException {
        try {

            String name = object.get("attributeName1").getAsString();
            String editWeight = object.get("colorEditWeight").getAsString();

            goTo(ManageSearchableFieldActions);
            await();
            FluentWebElement attributeName = searchableFieldActions.getAttributeUsingDisplayName(name);
            String searchWeight = searchableFieldActions.getSearchWeight(attributeName);

            ManageSearchableFieldActions.selectSearchWeightFromDropdown(editWeight);
            ManageSearchableFieldActions.saveChanges();
            ManageSearchableFieldActions.verifySuccessMessage();

            getDriver().navigate().refresh();
            FluentWebElement editedAttributeName = searchableFieldActions.getAttributeUsingDisplayName(name);
            String editedsearchWeight = searchableFieldActions.getSearchWeight(editedAttributeName);
            Assert.assertEquals(editedsearchWeight, editWeight,"EDITED WEIGHT IS NOT MATCHING WITH THE ACTUAL WEIGHT");

            ManageSearchableFieldActions.refreshAiRecommendation();
            ManageSearchableFieldActions.verifySuccessMessage();
            searchPage.threadWait();
            searchPage.awaitForElementPresence(ManageSearchableFieldActions.refreshAiRecommendations);

            getDriver().navigate().refresh();
            searchPage.threadWait();
            FluentWebElement attributeName1 = searchableFieldActions.getAttributeUsingDisplayName(name);
            String searchWeight1 = searchableFieldActions.getSearchWeight(attributeName1);
            Assert.assertEquals(searchWeight1, editedsearchWeight,"EDITED WEIGHT IS NOT MATCHING WITH THE ACTUAL WEIGHT");

            Thread.sleep(10000);
            click(ManageSearchableFieldActions.applyAndSaveAiRecommendedSearchWeight);
            ManageSearchableFieldActions.verifySuccessMessage();

            getDriver().navigate().refresh();
            searchPage.threadWait();
            FluentWebElement attributeName2 = searchableFieldActions.getAttributeUsingDisplayName(name);
            String searchWeight2 = searchableFieldActions.getSearchWeight(attributeName2);
            Assert.assertEquals(searchWeight2, searchWeight,"EDITED WEIGHT IS NOT MATCHING WITH THE ACTUAL WEIGHT");


        } catch (Exception e)

        {
            Assert.fail("verifyApplyAiRecommendationTest case got failed" + e);
        }

}


    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.close();
        driver.quit();
        Helper.tearDown();*/
    }
}