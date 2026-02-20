package UnbxdTests.testNG.consoleui.AlgorithmTest;

import UnbxdTests.testNG.ui.BaseTest;
import core.ui.actions.LoginActions;
import lib.Helper;
import lib.compat.Page;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import core.consoleui.actions.PhrasesActions;
import core.consoleui.actions.SynonymActions;
import core.consoleui.actions.ContentActions;
import core.consoleui.page.PhrasesPage;
import lib.compat.FluentWebElement;
import org.testng.Assert;
import lib.EnvironmentConfig;

public class PhrasesTest extends BaseTest {

    @Page
    PhrasesActions phrasesActions;

    @Page
    SynonymActions synonymActions;

    @Page
    ContentActions contentActions;

    @Page
    LoginActions loginActions;

    private PhrasesPage phrasesPage;

    @BeforeClass
    public void setUp() {
        super.setUp();
        if (driver != null) {
            initFluent(driver);
            initTest();
            // Set context before navigation - using site 1 and user 1 as per ProdAPAC.yaml configuration
            EnvironmentConfig.unSetContext();
        EnvironmentConfig.setContext(2, 2);
            // Login is now handled by BaseTest's global login mechanism
            phrasesPage = new PhrasesPage();
        } else {
            throw new RuntimeException("WebDriver initialization failed");
        }
    }

    @Test(priority = 1, description = "This Test Creates, edits, and deletes a Phrase")
    public void createEditDeletePhraseTest() throws InterruptedException {
        goTo(phrasesActions);

        String phraseName = phrasesActions.createPhrase();

       // Assert.assertNotNull(synonymActions.getKeyWordsByName(phraseName), "Phrase was not created successfully");

        // Edit phrase name using editKeyword from SynonymActions
        FluentWebElement createdPhrase = synonymActions.getKeyWordsByName(phraseName);
        String editPhraseName = "edit phrase" + System.currentTimeMillis();
        synonymActions.editKeyword(createdPhrase, editPhraseName, null, null);
        contentActions.saveChanges();

        // Delete created phrase using deleteCreatedKeyword from SynonymActions
        synonymActions.deleteCreatedKeyword(editPhraseName);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
            Helper.tearDown();
        }
    }
} 