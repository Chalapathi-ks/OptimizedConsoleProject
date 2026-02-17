package UnbxdTests.testNG.ui.FeedUploadsTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import UnbxdTests.testNG.ui.BaseTest;
import com.google.gson.JsonObject;
import core.ui.actions.CreateSiteActions;
import core.ui.actions.FeedUploadActions;
import core.ui.actions.LoginActions;
import core.ui.actions.ShopifyFeedUploadActions;
import core.ui.components.CreateSiteComponent;
import lib.Helper;
import lib.annotation.FileToTest;
import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ShopifyFeedUploadTest extends BaseTest {

    List<String> siteKeys=new ArrayList<>();

    private String ssoIdKeyCookie ;

    @Page
    LoginActions loginActions;

    @Page
    FeedUploadActions feedUploadActions;

    @Page
    CreateSiteComponent createSiteComponent;

    @Page
    CreateSiteActions createSiteActions;

    @Page
    ShopifyFeedUploadActions shopifyFeedUploadActions;

    @BeforeClass
    public void init() {
        super.setUp();
        initFluent(driver);
        initTest();
        // Login is now handled by BaseTest's global login mechanism
    }

    @FileToTest(value = "feedUploadTest/shopifyFeedUploadTest.json")
    @Test(description = "Verifying feed upload using url", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void shopifyFeedUploadTest(JsonObject object) throws InterruptedException
    {
        ssoIdKeyCookie = createSiteComponent.getSsoIdCookie();

        String email = object.get("email").getAsString();
        String password = object.get("password").getAsString();
        String store = object.get("store").getAsString();
        String submenu = object.get("subMenu").getAsString();
        String url = object.get("catalogUrl").getAsString();

        /*createSiteComponent.createSite();
        Assert.assertTrue(createSiteActions.awaitForElementPresence(createSiteActions.catalogUploadPageTitle));
        feedUploadActions.selectUploaders(PLATFORM_UPLOAD);
        feedUploadActions.selectPlatform(MAGENTO_TAB);
        String siteKey = createSiteActions.getCreatedSiteKey();
        siteKeys.add(siteKey);

        feedUploadActions.GoBackToPlatformsTab();
        feedUploadActions.selectUploaders(PLATFORM_UPLOAD);
        feedUploadActions.selectPlatform(SHOPIFY_TAB);*/

        //openNewTab();
        //switchTabTo(1);
        shopifyFeedUploadActions.goToShopifyUrl();
        getDriver().manage().addCookie(new Cookie("_fbp", "fb.1.1614856220291.598254779",".shopify.com"));
        getDriver().manage().addCookie(new Cookie("_gcl_au", "1.1.1629683131.1614856218",".shopify.com"));
        getDriver().manage().addCookie(new Cookie("_shopify_s", "fcee0910-31E4-456C-D24E-D1B97CC86627",".shopify.com"));
        getDriver().manage().addCookie(new Cookie("master_device_id", "45e5ce40-a6ba-47e1-a94a-2b6c636635f8",".shopify.com"));
        getDriver().manage().addCookie(new Cookie("_s", "fcee0910-31E4-456C-D24E-D1B97CC86627",".shopify.com"));
        getDriver().manage().addCookie(new Cookie("_shopify_y", "dda58f4f-2EA3-4F25-B652-631F69E2C045",".shopify.com"));
        getDriver().manage().addCookie(new Cookie("_shopify_fs", "2021-02-26T09%3A22%3A27.310Z",".shopify.com"));
        getDriver().manage().addCookie(new Cookie("_y", "dda58f4f-2EA3-4F25-B652-631F69E2C045",".shopify.com"));
        shopifyFeedUploadActions.goToShopifyUrl();
        //shopifyFeedUploadActions.shopifyLoginWith(email,password);
        //shopifyFeedUploadActions.clickOnshopifyLoginTab();
        shopifyFeedUploadActions.goToShopifyStoreTab();
        shopifyFeedUploadActions.loginShopifyStore(shopifyFeedUploadActions.shopifyStoreList,store);
       // switchTabTo(2);
        shopifyFeedUploadActions.selectShopifySubMenu(shopifyFeedUploadActions.shopifySubMenuist,submenu);
        shopifyFeedUploadActions.deleteShopifyApp();
       // switchTabTo(0);
        shopifyFeedUploadActions.fillCatalogUrl(url);
        shopifyFeedUploadActions.clickOnInstallShopifyPluginTab();
        shopifyFeedUploadActions.clickOnInstallUnlistedAppTab();
        Assert.assertTrue(feedUploadActions.awaitForElementPresence(shopifyFeedUploadActions.successfullShopifyUplaod));
        feedUploadActions.waitForFeedUploadToComplete();
        feedUploadActions.awaitForElementPresence(feedUploadActions.successMessage);
        Assert.assertEquals(feedUploadActions.successMsg.getText(),"Feed Upload is Successful");
    }

    @AfterClass(alwaysRun = true)
    public void deleteSites()
    {
        for(String siteKey:siteKeys)
        {
           // createSiteActions.deleteSite(siteKey,ssoIdKeyCookie, );
        }
    }


    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.close();
        driver.quit();
        Helper.tearDown();

    }


}
