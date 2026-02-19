package UnbxdTests.testNG.ui.SearchManageTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import UnbxdTests.testNG.ui.BaseTest;
import com.google.gson.JsonObject;
import core.consoleui.actions.ConceptsActions;
import core.consoleui.actions.DimensionMappingAction;
import core.ui.actions.LoginActions;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.constants.UnbxdErrorConstants;
import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import lib.EnvironmentConfig;

public class DimensionMappingTest extends BaseTest {
    @Page
    DimensionMappingAction dimensionMappingAction;

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

    @FileToTest(value = "/consoleUi/dimensionMapping.json")
    @Test(priority = 1, description = "Dimension Mapping scenario", dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"sanity"})
    public void dimensionMappingUpdate(Object jsonObject) {
        JsonObject mappingData = (JsonObject) jsonObject;
        String fieldName1 =mappingData.get("fieldName1").getAsString();
        String fieldValue1 =mappingData.get("fieldValue1").getAsString();

        String fieldName2 =mappingData.get("fieldName2").getAsString();
        String fieldValue2 =mappingData.get("fieldValue2").getAsString();

        String fieldValue1Edit =mappingData.get("fieldValue1Edit").getAsString();
        String fieldValue2Edit =mappingData.get("fieldValue2Edit").getAsString();

        goTo(dimensionMappingAction);
        dimensionMappingAction.waitForPageLoad();

        // Step 3 & 4: Set Title* to 'title', Price to 'price'
        dimensionMappingAction.selectValueForField(fieldName1, fieldValue1);
        dimensionMappingAction.selectValueForField(fieldName2, fieldValue2);
        dimensionMappingAction.saveChanges();
        Assert.assertTrue(dimensionMappingAction.checkSuccessMessage(), UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE);

        // Step 7: Validate selected values
        Assert.assertEquals(dimensionMappingAction.getSelectedValueForField(fieldName1), fieldValue1);
        Assert.assertEquals(dimensionMappingAction.getSelectedValueForField(fieldName2), fieldValue2);

        // Step 8: Change values to 'availability' and 'discount'
        dimensionMappingAction.selectValueForField(fieldName1, fieldValue1Edit);
        dimensionMappingAction.selectValueForField(fieldName2, fieldValue2Edit);
        dimensionMappingAction.saveChanges();
        //Assert.assertTrue(dimensionMappingAction.checkSuccessMessage(), UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE);
        Assert.assertTrue(conceptsActions.checkSuccessMessage(), UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE);


        // Step 11: Validate changed values
        Assert.assertEquals(dimensionMappingAction.getSelectedValueForField(fieldName1), fieldValue1Edit);
        Assert.assertEquals(dimensionMappingAction.getSelectedValueForField(fieldName2), fieldValue2Edit);
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