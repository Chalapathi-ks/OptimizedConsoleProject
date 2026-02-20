package UnbxdTests.testNG.ui.SearchManageTest;

import UnbxdTests.testNG.ui.BaseTest;
import core.consoleui.actions.ConceptsActions;
import core.ui.actions.LoginActions;
import lib.Helper;
import lib.constants.UnbxdErrorConstants;
import lib.compat.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import core.consoleui.actions.VectorSearchAction;
import lib.EnvironmentConfig;

public class VectorSearchTest extends BaseTest {
    @Page
    VectorSearchAction vectorSearchAction;

    @Page
    LoginActions loginActions;

    @Page
    ConceptsActions conceptsActions;

    private int hybridOriginal;
    private int fallbackOriginal;

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

    @Test(priority = 1, description = "This Test configures and verifies vector search", groups = {"vector-search"})
    public void configureVectorSearchTest() throws InterruptedException {
        // Step 1: Navigate to Vector Search URL
        goTo(vectorSearchAction);
        Thread.sleep(2000);

        // Step 2: Wait for page load (implicit in goTo or add explicit wait if needed)
        // Step 3: Enable toggle if not enabled
        vectorSearchAction.enableVectorSearchIfDisabled();

        // Step 4: Select Hybrid search checkbox and increment value
        String hybridVal = vectorSearchAction.hybridSearchInput.getValue();
        if (hybridVal == null) hybridVal = vectorSearchAction.hybridSearchInput.getText();
        hybridOriginal = Integer.parseInt(hybridVal.trim());
        vectorSearchAction.selectHybridSearchAndIncrement();

        // Step 4: Repeat for Fallback mode
        String fallbackVal = vectorSearchAction.fallbackModeInput.getValue();
        if (fallbackVal == null) fallbackVal = vectorSearchAction.fallbackModeInput.getText();
        fallbackOriginal = Integer.parseInt(fallbackVal.trim());
        vectorSearchAction.selectFallbackModeAndIncrement();

        // Step 5: Save changes
        vectorSearchAction.saveChanges();

        // Step 6: Verify success message
        Assert.assertTrue(conceptsActions.checkSuccessMessage(), UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE);

        // Step 7: Verify updated values
        Assert.assertTrue(vectorSearchAction.verifyUpdatedValues(hybridOriginal + 1, fallbackOriginal + 1), "Updated values not present in Hybrid and Fallback mode");
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