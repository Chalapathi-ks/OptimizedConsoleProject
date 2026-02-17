package UnbxdTests.testNG.consoleui.Autosuggest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import UnbxdTests.testNG.ui.BaseTest;
import com.google.gson.JsonObject;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.page.PromotedSuggestionsPage;
import core.ui.actions.LoginActions;
import lib.Helper;
import lib.annotation.FileToTest;
import org.fluentlenium.core.annotation.Page;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import core.consoleui.actions.PromotedSuggestionsActions;
import lib.EnvironmentConfig;


public class PromotedSuggestionsTest extends BaseTest {

    @Page
    LoginActions loginActions;

    @Page
    public
    CommercePageActions searchPage;

    @Page
    PromotedSuggestionsPage promotedSuggestionsPage;

    @Page
    PromotedSuggestionsActions promotedSuggestionsActions;

    @BeforeClass(groups={"sanity"})
    public void setUp() {
        super.setUp();
        this.initFluent(driver);
        initTest();
        // Set context before navigation - using site 1 and user 1 as per ProdAPAC.yaml configuration
        EnvironmentConfig.unSetContext();
        EnvironmentConfig.setContext(1, 1);
        // Login is now handled by BaseTest's global login mechanism
        goTo(searchPage);
        searchPage.threadWait();
    }

    /*
    @FileToTest(value = "/consoleTestData/PromotedSugestions.json")
    @Test(description = "SEARCH: ", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void promotedSuggestionsTest(Object jsonObject) throws InterruptedException {
        JsonObject boostJsonObject = (JsonObject) jsonObject;
        String query = boostJsonObject.get("query").getAsString();
        goTo(promotedSuggestionsPage);
        searchPage.threadWait();
    }
    */

    @FileToTest(value = "/consoleTestData/PromotedSugestions.json")
    @Test(description = "Add Promoted Suggestion", priority = 2, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void addPromotedSuggestionTest(Object jsonObject) throws InterruptedException {
        JsonObject data = (JsonObject) jsonObject;
        String term = data.get("promotedSuggestionTerm").getAsString();
        String timezone = data.get("timezone").getAsString();

        goTo(promotedSuggestionsPage);
        promotedSuggestionsActions.ensureTermNotPresent(term);
        searchPage.threadWait();
        promotedSuggestionsActions.addPromotedSuggestion(term, timezone);
        searchPage.threadWait();

        java.util.List<org.openqa.selenium.WebElement> termElements = driver.findElements(org.openqa.selenium.By.cssSelector(".promoted-names"));
        for (org.openqa.selenium.WebElement el : termElements) {
            System.out.println("Found term after add: '" + el.getText().trim() + "'");
        }
        promotedSuggestionsActions.verifySuggestionActiveStatus(term);
        // Edit flow: add a new term in the edit modal
        String newTerm = data.get("editpromotedSuggestionTerm").getAsString();
        promotedSuggestionsActions.editSuggestions(term, newTerm);
        // Wait for modal to disappear before clearing the search term
        new org.openqa.selenium.support.ui.WebDriverWait(driver, 10)
                .until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated(
                        org.openqa.selenium.By.cssSelector(".RCB-modal.add-edit-modal")));
        promotedSuggestionsActions.clearSearch();
        searchPage.threadWait();
        // Retry refresh and verification up to 5 times
        int maxRetries = 5;
        boolean found = false;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            driver.navigate().refresh();
            searchPage.threadWait();
            try {
                promotedSuggestionsActions.verifyPromotedSuggestionTermsInRow(term, newTerm);
                found = true;
                break; // Success, exit loop
            } catch (AssertionError e) {
                if (attempt == maxRetries) {
                    throw e; // Rethrow on last attempt
                }
                // Otherwise, try again
            }
        }
        // Clear search before deleting terms
        // promotedSuggestionsActions.clearSearch();
        promotedSuggestionsActions.deletePromotedSuggestion(term); // Only need to delete one term since both are in the same row
        // Optionally, refresh and verify they are no longer present
        driver.navigate().refresh();
        // Wait for the table row to appear after refresh
        searchPage.threadWait();
        // Ensure neither term is present
        promotedSuggestionsActions.searchForTerm(term);
        org.testng.Assert.assertFalse(promotedSuggestionsActions.isTermPresentInListing(term), "Term should not be present after delete: " + term);
        promotedSuggestionsActions.searchForTerm(newTerm);
        org.testng.Assert.assertFalse(promotedSuggestionsActions.isTermPresentInListing(newTerm), "Term should not be present after delete: " + newTerm);
    }

    @AfterClass
    public void tearDown()
    {
        driver.close();
        driver.quit();
        Helper.tearDown();
    }
}