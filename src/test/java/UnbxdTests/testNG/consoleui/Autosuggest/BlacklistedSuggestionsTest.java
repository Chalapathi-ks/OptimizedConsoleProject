package UnbxdTests.testNG.consoleui.Autosuggest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import UnbxdTests.testNG.ui.BaseTest;
import com.google.gson.JsonObject;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.actions.PromotedSuggestionsActions;
import core.consoleui.page.BlacklistedSuggestionsPage;
import core.ui.actions.LoginActions;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.compat.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import lib.EnvironmentConfig;


public class BlacklistedSuggestionsTest extends BaseTest {

    @Page
    LoginActions loginActions;

    @Page
    public
    CommercePageActions searchPage;

    @Page
    BlacklistedSuggestionsPage blacklistedSuggestionsPage;

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



    @FileToTest(value = "/consoleTestData/BlacklistedSugestions.json")
    @Test(description = "addEditDeleteBlacklisted Suggestion", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void addEditDeleteBlacklistedSuggestionTest(Object jsonObject) throws InterruptedException {
        JsonObject data = (JsonObject) jsonObject;
        String term = data.get("promotedSuggestionTerm").getAsString();
        String timezone = data.get("timezone").getAsString();

        goTo(blacklistedSuggestionsPage);
        promotedSuggestionsActions.ensureTermNotPresent(term);
        searchPage.threadWait();
        promotedSuggestionsActions.addSuggestion(term);
        searchPage.threadWait();

        // Debug: print all terms found after add
        java.util.List<org.openqa.selenium.WebElement> termElements = driver.findElements(org.openqa.selenium.By.cssSelector(".promoted-names"));
        for (org.openqa.selenium.WebElement el : termElements) {
            System.out.println("Found term after add: '" + el.getText().trim() + "'");
        }
        promotedSuggestionsActions.verifySuggestionActiveStatus(term);
        // Edit flow: add a new term in the edit modal
        String newTerm = data.get("editpromotedSuggestionTerm").getAsString();
        promotedSuggestionsActions.editSuggestions(term, newTerm);

        // Wait for modal to disappear before clearing the search term
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
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
            }
        }

        promotedSuggestionsActions.deletePromotedSuggestion(term);
        driver.navigate().refresh();
        searchPage.threadWait();
        promotedSuggestionsActions.searchForTerm(term);
        Assert.assertFalse(promotedSuggestionsActions.isTermPresentInListing(term), "Term should not be present after delete: " + term);
        promotedSuggestionsActions.searchForTerm(newTerm);
        Assert.assertFalse(promotedSuggestionsActions.isTermPresentInListing(newTerm), "Term should not be present after delete: " + newTerm);
    }

    @AfterClass
    public void tearDown()
    {
        driver.close();
        driver.quit();
        Helper.tearDown();
    }
}