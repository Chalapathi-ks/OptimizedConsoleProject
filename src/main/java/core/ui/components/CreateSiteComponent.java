package core.ui.components;

import core.ui.actions.CreateSiteActions;
import core.ui.actions.FeedUploadActions;
import core.ui.page.UiBase;
import lib.Config;
import org.apache.commons.lang.RandomStringUtils;
import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;

public class CreateSiteComponent extends UiBase{

    @Page
    FeedUploadActions feedUploadActions;

    @Page
    CreateSiteActions createSiteActions;


public void createSiteForUpload(String siteName, String env, String lang, String vertical, String platform) throws InterruptedException {
    createSiteActions.goToCreateSitePage();
    waitForLoaderToDisAppear(feedUploadActions.pageLoader, feedUploadActions.importLoaderName);
   // waitForElementAppear(createSiteActions.onboardingPageLoader, createSiteActions.catalogLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
    Assert.assertTrue(createSiteActions.awaitForElementPresence(createSiteActions.onboardingTitle));
    createSiteActions.fillCreateSiteForm(siteName, env, lang, vertical, platform);
    createSiteActions.clickOnCreateSite();
    waitForLoaderToDisAppear(feedUploadActions.progressBarLoader, createSiteActions.createSiteLoader);
}
    public void createSite(String siteName,String env,String lang,String vertical,String platform) {
        try {
            boolean flag=false;
            createSiteActions.gotoWelcomePage();
            waitForLoaderToDisAppear(feedUploadActions.pageLoader, feedUploadActions.importLoaderName);
            createSiteForUpload(siteName,env,lang,vertical,platform);
            ThreadWait();
            if(awaitForElementPresence(createSiteActions.siteCreationFailIcon) && awaitForElementPresence(createSiteActions.siteCreationFailureMsg)==true) {
                System.out.println("Site creation is failing,Trying to create site again!!! here is the reason for failure :  " + createSiteActions.siteCreationFailureReason.getText());
                for (int i = 0; i <= 2; i++) {
                    createSiteForUpload(siteName, env, lang, vertical, platform);
                    if (awaitForElementPresence(createSiteActions.siteCreationFailIcon) && awaitForElementPresence(createSiteActions.siteCreationFailureMsg) == true) {
                    flag =true;
                    }else{
                        break;
                    }
                }
                if (flag) {
                    System.out.println("Site creation is failing,!!! here is the reason for failure :  " + createSiteActions.siteCreationFailureReason.getText());
                    Assert.fail("Site creation is failing : Reason : " + createSiteActions.siteCreationFailureReason.getText());
                }
            }
            waitForElementAppear(createSiteActions.catalogPage, createSiteActions.catalogLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
            Assert.assertEquals(createSiteActions.uploadPageText.getText(), "How would you like to upload your catalog today?");
        }
        catch(Exception e)
        {
            Assert.fail("Create site data is not filled" +e);
        }
    }

    public void createSite() {
        String lang = System.getProperty("lang");

        String siteName= "RegressionSite"+System.currentTimeMillis();
       // String siteName = generateRandomSiteName(13)+System.currentTimeMillis();
       createSite(siteName,"Dev",lang,"Fashion & Apparel",null);
    }

    public static String generateRandomSiteName(int length)
    {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz" + "1234567890";
        String name = "";
        String temp = RandomStringUtils.random(length, allowedChars);
        name = temp.substring(0, temp.length() - 9);
        return name;
    }

}
