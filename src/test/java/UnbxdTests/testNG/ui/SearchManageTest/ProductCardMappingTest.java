package UnbxdTests.testNG.ui.SearchManageTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import UnbxdTests.testNG.ui.BaseTest;
import core.ui.actions.LoginActions;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.constants.UnbxdErrorConstants;
import lib.compat.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import lib.EnvironmentConfig;

import com.google.gson.JsonObject;

import core.consoleui.actions.ConceptsActions;
import core.consoleui.actions.productCardMappingAction;
import java.util.HashMap;
import java.util.Map;

import static core.ui.page.UiBase.ThreadWait;

public class ProductCardMappingTest extends BaseTest {
    @Page
    productCardMappingAction pcmAction;

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

    @FileToTest(value = "/consoleUi/pcm.json")
    @Test(priority = 1, description = "This Test configures and verifies product card mapping", dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile",groups = {"sanity"})
    public void configureProductCardMappingTest(Object jsonObject) throws InterruptedException {

        JsonObject mappingData = (JsonObject) jsonObject;
        String parentFieldValueTitle =mappingData.get("parentFieldValueTitle").getAsString();
        String varientFieldValueTitle =mappingData.get("varientFieldValueTitle").getAsString();

        String parentFieldValuePrice =mappingData.get("parentFieldValuePrice").getAsString();
        String varientFieldValuePrice =mappingData.get("parentFieldValuePrice").getAsString();

        String parentFieldValueTitleEdit =mappingData.get("parentFieldValueTitle").getAsString();
        String varientFieldValueTitleEdit =mappingData.get("varientFieldValueTitle").getAsString();

        String parentFieldValuePriceEdit =mappingData.get("parentFieldValuePrice").getAsString();
        String varientFieldValuePriceEdit =mappingData.get("parentFieldValuePrice").getAsString();

        goTo(pcmAction);
        ThreadWait();

        pcmAction.selectTitleValue( parentFieldValueTitle,varientFieldValueTitle);
        pcmAction.selectAndRemoveThirdAdditionalField();
        pcmAction.selectPriceValue(parentFieldValuePrice,parentFieldValuePrice);

        pcmAction.saveChanges();
        Assert.assertTrue(conceptsActions.checkSuccessMessage(), UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE);

        // Verify updated values
        Assert.assertTrue((parentFieldValueTitle.toLowerCase()).contains(pcmAction.parentTitleDropdown.getText()));
        Assert.assertTrue(pcmAction.parentPriceDropdown.getText().equalsIgnoreCase(parentFieldValuePrice));
        if (pcmAction.awaitForElementPresence(pcmAction.varaientTitleLable)) {
            Assert.assertTrue(pcmAction.varientTitleDropdown.getText().equalsIgnoreCase(varientFieldValueTitle));
        }
        if (pcmAction.awaitForElementPresence(pcmAction.varaientPriceLable)) {
            Assert.assertTrue(pcmAction.varientPriceDropdown.getText().equalsIgnoreCase(varientFieldValuePrice));
        }


        // Edit the values
        pcmAction.selectTitleValue( parentFieldValueTitleEdit,varientFieldValueTitleEdit);
        pcmAction.selectPriceValue(parentFieldValuePriceEdit,parentFieldValuePriceEdit);

        pcmAction.saveChanges();
        Assert.assertTrue(conceptsActions.checkSuccessMessage(), UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE);

        // Verify updated values
        Assert.assertTrue((parentFieldValueTitleEdit.toLowerCase()).contains(pcmAction.parentTitleDropdown.getText()));
        Assert.assertTrue(pcmAction.parentPriceDropdown.getText().equalsIgnoreCase(parentFieldValuePriceEdit));
        if (pcmAction.awaitForElementPresence(pcmAction.varaientTitleLable)) {
            Assert.assertTrue(pcmAction.varientTitleDropdown.getText().equalsIgnoreCase(varientFieldValueTitleEdit));
        }
        if (pcmAction.awaitForElementPresence(pcmAction.varaientPriceLable)) {
            Assert.assertTrue(pcmAction.varientPriceDropdown.getText().equalsIgnoreCase(varientFieldValuePriceEdit));
        }
        
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