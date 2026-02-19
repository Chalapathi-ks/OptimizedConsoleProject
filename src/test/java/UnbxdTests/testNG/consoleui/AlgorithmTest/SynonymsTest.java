package UnbxdTests.testNG.consoleui.AlgorithmTest;

import UnbxdTests.testNG.ui.BaseTest;
import core.ui.actions.LoginActions;
import lib.Helper;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import core.consoleui.actions.SynonymActions;
import core.consoleui.actions.ContentActions;
import lib.EnvironmentConfig;

public class SynonymsTest extends BaseTest {

    @Page
    ContentActions contentActions;

    @Page
    SynonymActions synonymActions;

    @Page
    LoginActions loginActions;

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
        } else {
            throw new RuntimeException("WebDriver initialization failed");
        }
    }

    @Test(priority = 1, description = "This Test Creates and verifies the Synonyms",groups = {"concepts"})
    public void createSynonymTest() throws InterruptedException {
        
        goTo(synonymActions);// Create synonym with keyword "add", one-way synonym "test", and two-way synonym "test2"
        String synonymName = synonymActions.createSynonym();
        
        // Verify the synonym was created successfully
        Assert.assertNotNull(synonymActions.getKeyWordsByName(synonymName), "Synonym was not created successfully");

        FluentWebElement createdSynonym = synonymActions.getKeyWordsByName(synonymName);

        String editsynonymName="autoSynoedit"+ System.currentTimeMillis();
        String edituniDirectionalName="autoSynouniedit"+ System.currentTimeMillis();
        String editbiDirectional="autoSynobiedit"+ System.currentTimeMillis();

        synonymActions.editKeyword(createdSynonym,editsynonymName,edituniDirectionalName,editbiDirectional);
        contentActions.saveChanges();

        synonymActions.deleteCreatedKeyword(editsynonymName);

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