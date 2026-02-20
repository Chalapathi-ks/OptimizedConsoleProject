package core.ui.actions;

import core.ui.page.ShopifyFeedUploadPage;
import lib.Config;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.testng.Assert;

public class ShopifyFeedUploadActions extends ShopifyFeedUploadPage {


    public void shopifyLoginWith(String email,String pwd) throws InterruptedException {
        awaitForElementPresence(shopifyEmailInputBox);
        shopifyEmailInputBox.fill().with(email);
        awaitForElementPresence(shopifyLoginTab);
        ThreadWait();
        click(shopifyLoginTab);
        ThreadWait();
        awaitForElementPresence(shopifyPasswordInputBox);
        shopifyPasswordInputBox.fill().with(pwd);
    }

    public void clickOnshopifyLoginTab()
    {
        awaitForElementPresence(shopifyLoginTab);
        click(shopifyLoginTab);
    }

    public void goToShopifyUrl()
    {
        goTo("https://partners.shopify.com/1701682/stores");
    }

    public void goToShopifyStoreTab()
    {

        goTo("https://partners.shopify.com/1701682/stores");
    }

    public void loginShopifyStore(FluentList<FluentWebElement> storeList,String store)
    {
        awaitForPageToLoad();
        scrollToBottom();
        for(FluentWebElement field : storeList)
        {
            ThreadWait();
            if(field.find(".store-list-item__name").getText().equalsIgnoreCase(store)) {
                ThreadWait();
                field.find(".store-list-item__action:nth-child(2)").click();
                break;
            }
        }
    }

    public void goToApps()
    {
        awaitForPageToLoad();
        goTo("https://qacatalog.myshopify.com/admin/apps");
    }

    public void selectShopifySubMenu(FluentList<FluentWebElement> menuList,String subMenu)
    {
        awaitForPageToLoad();
        for(FluentWebElement field : menuList)
        {
            await();
            Assert.assertTrue(awaitForElementPresence(field));
            if (field.getText().equalsIgnoreCase(subMenu))
            {
                await();
                click(field);
                break;
            }
        }
    }

    public void deleteShopifyApp()
    {
        if(awaitForElementPresence(deleteAppButton)==true)
        {
            ThreadWait();
            click(deleteAppButton);
            Assert.assertTrue(awaitForElementPresence(deleteAppPopUpButton));
            threadWait();
            click(deleteAppPopUpButton);
            ThreadWait();
            awaitForElementNotDisplayed(deleteAppPopUpButton);
        }
    }

    public void fillCatalogUrl(String url)
    {
        awaitForElementPresence(shopifyUrlInputBox);
        shopifyUrlInputBox.fill().with(url);
    }

    public void clickOnInstallShopifyPluginTab() throws InterruptedException {
        Thread.sleep(8000);
        Assert.assertTrue(awaitForElementPresence(installPluginButton));
        waitForElementToBeClickable(installPluginButton,installTabName);
        click(installPluginButton);
        awaitForPageToLoad();
        Assert.assertTrue(awaitForElementPresence(shopifyLoginTab));
    }

    public void clickOnInstallUnlistedAppTab() throws InterruptedException {
        awaitForPageToLoad();
        scrollToBottom();
        Assert.assertTrue(awaitForElementPresence(shopifyLoginTab));
        waitForElementToBeClickable(shopifyLoginTab,installTabName);
        Thread.sleep(8000);
        click(shopifyLoginTab);
        awaitForPageToLoad();
        Thread.sleep(8000);
    }

    public void checkFeedStatus()
    {
        awaitForPageToLoad();
        awaitForElementPresence(checkStatusButton);
        click(checkStatusButton);
    }
}


