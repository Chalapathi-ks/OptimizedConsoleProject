package UnbxdTests.testNG.ui.SearchManageTest;

import UnbxdTests.testNG.ui.BaseTest;
import core.ui.actions.LoginActions;
import lib.Helper;
import lib.constants.UnbxdErrorConstants;

import org.fluentlenium.core.annotation.Page;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.fluentlenium.core.domain.FluentWebElement;

import core.consoleui.actions.ContentActions;
import core.consoleui.actions.MerasurementSearchAction;
import lib.EnvironmentConfig;

public class MerasurementSearchTest extends BaseTest {

    @Page
    MerasurementSearchAction merasurementSearchAction;

    @Page
    ContentActions contentActions;

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

    @Test(priority = 1, description = "This Test configures and verifies measurement search", groups = {"measurement-search"})
    public void configureMeasurementSearchTest() throws InterruptedException 
    {
        goTo(merasurementSearchAction);

        merasurementSearchAction.enableMeasurementSearchIfDisabled();

        merasurementSearchAction.deleteExistingConfigAttribute();

        merasurementSearchAction.selectConfigAttributeFromDropDown();

        merasurementSearchAction.selectDimensionsFromDropDown();

        merasurementSearchAction.selectUnitFromDropDown();

        merasurementSearchAction.saveChanges();

        Assert.assertTrue(contentActions.checkSuccessMessage(), UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE);
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