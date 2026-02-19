package UnbxdTests.testNG.consoleui.AlgorithmTest;

import org.fluentlenium.core.annotation.Page;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import core.consoleui.actions.ConceptsActions;
import core.consoleui.page.ConceptsPage;
import UnbxdTests.testNG.ui.BaseTest;
import org.testng.Assert;
import lib.constants.UnbxdErrorConstants;
import lib.Helper;
import core.ui.actions.LoginActions;
import lib.EnvironmentConfig;

public class ConceptsTest extends BaseTest {

    @Page
    LoginActions loginActions;

    @Page
    ConceptsActions conceptsActions;

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

    @Test(priority = 1, description = "This Test Creates and verifies the concepts", groups = {"concepts"})
    public void createConceptsTest() throws InterruptedException {
        goTo(conceptsActions);
    
        String keyword = conceptsActions.createKeyword();

        Assert.assertTrue(conceptsActions.checkSuccessMessage(), UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE);

        conceptsActions.deleteKeyword(keyword);

        Assert.assertTrue(conceptsActions.checkSuccessMessage(), UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE);
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
