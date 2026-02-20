package UnbxdTests.testNG.consoleui.ManagePageTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import UnbxdTests.testNG.ui.BaseTest;
import core.consoleui.actions.CatalogPageActions;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.page.CatalogPage;
import core.consoleui.page.ConfigureSitePage;
import core.ui.actions.AutoMappingActions;
import core.ui.actions.LoginActions;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.compat.Page;
import lib.compat.FluentWebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import lib.EnvironmentConfig;

public class catalogPageTest extends BaseTest {

    @Page
    CatalogPage catalogPage;

    @Page
    CatalogPageActions catalogPageActions;

    @Page
    AutoMappingActions autoMappingActions;

    @Page
    LoginActions loginActions;

    @Page
    ConfigureSitePage configureSitePage;

    @Page
    public
    CommercePageActions searchPage;

    @BeforeClass
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


    @Test(description = "SEARCH: CATALOG PAGE EDIT FUNCTIONALITY CHECK", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void catalogUpdateTest(Object jsonObject) throws InterruptedException
    {

        goTo(catalogPage);
        catalogPage.awaitForPageToLoad();
        FluentWebElement field = autoMappingActions.findMappingField("SKU");
        autoMappingActions.clearMappingValue(field);
        Assert.assertTrue(autoMappingActions.getMappingValue(field).equalsIgnoreCase("Select"));
        click(autoMappingActions.setUpSearchButton);
        Assert.assertTrue(autoMappingActions.awaitForElementPresence(catalogPageActions.dimensionSuccessMessage),"MAPPING IS NOT SAVED SUCCESSFULLY!!!");
        Assert.assertTrue(autoMappingActions.getMappingValue(field).equalsIgnoreCase("Select"));

        FluentWebElement field1 = autoMappingActions.findMappingField("SKU");
        autoMappingActions.selectMappingValue(field1,"SKU","SKU");
        Assert.assertTrue(autoMappingActions.getMappingValue(field).equalsIgnoreCase("SKU"));
        click(autoMappingActions.setUpSearchButton);
        Assert.assertTrue(autoMappingActions.awaitForElementPresence(catalogPageActions.dimensionSuccessMessage),"MAPPING IS NOT SAVED SUCCESSFULLY!!!");
        Assert.assertTrue(autoMappingActions.getMappingValue(field).equalsIgnoreCase("SKU"));
    }

    @Test(description = "SEARCH: CATALOG PAGE EDIT FUNCTIONALITY CHECK", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void configureSiteTest()
    {
        goTo(configureSitePage);
        catalogPage.awaitForPageToLoad();
        Assert.assertTrue(catalogPageActions.awaitForElementPresence(configureSitePage.keysTab),"CONFIGURE SITE KEYS TAB IS NOT PRESENT");

        click(configureSitePage.apiTab);
        Assert.assertTrue(catalogPageActions.awaitForElementPresence(configureSitePage.keysTab),"CONFIGURE SITE KEYS TAB IS NOT PRESENT");

    }

    @AfterClass
    public void tearDown()
    {
        driver.close();
        driver.quit();
        Helper.tearDown();
    }
}
