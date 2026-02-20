package core.ui.actions;

import com.google.gson.annotations.SerializedName;
import core.ui.page.CreateSitePage;
import lib.Config;
import lib.EnvironmentConfig;
import lib.Helper;
import lib.api.ApiClient;
import lib.api.HttpMethod;
import lib.api.UnbxdResponse;
import lombok.Data;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lib.UrlMapper.CREATE_SITE_PAGE;
import static lib.UrlMapper.WELCOME_PAGE;


public class CreateSiteActions extends CreateSitePage {

    public void goToCreateSitePage() throws InterruptedException {
        goTo(CREATE_SITE_PAGE.getBaseUrl());
        awaitForPageToLoad();
        Thread.sleep(15000);
        Assert.assertTrue(awaitForElementPresence(proceedButton));
        //awaitForElementPresence(configureFirstSite);
        //configureFirstSite.click();
        //awaitForPageToLoad();
    }

    public void goToCreateSitePageUsingUrl()
    {
        goTo(this);
        awaitForPageToLoad();
    }

    public String getUrl()
    {
        awaitForPageToLoad();
        return CREATE_SITE_PAGE.getUrlPath();
        //return CREATE_SITE_PAGE.getUrlPath(EnvironmentConfig.getSiteId());
    }

    public void fillCreateSiteForm(String siteName,String env,String lang,String vertical,String platform){
        //SoftAssert softAssert = new SoftAssert();

        awaitForElementPresence(siteNameInput);
        siteNameInput.fill().with(siteName);
        await();
        //clearIntercomNotifications();
        selectEnvironmentValue(env);
        Assert.assertTrue(envInputText.getText().trim().equalsIgnoreCase(env));
        threadWait();
        selectVerticalValue(vertical);
        Assert.assertTrue(verticalInputText.getText().trim().equalsIgnoreCase(vertical));
        if(platform!=null) {
            await();
            selectPlatformValue(platform);
            Assert.assertTrue(platformInputText.getText().trim().equalsIgnoreCase(platform));
        }
        threadWait();
        selectLanguageValue(lang);
        Assert.assertTrue(langInputText.getText().trim().equalsIgnoreCase(lang));
        await();

        //softAssert.assertAll();
    }

    public void clearIntercomNotifications()
    {
        await();
        Helper.mouseOver(intercomSnippet.getElement());
        //Thread.sleep(1000);
        if(awaitForElementPresence(intercomClearTab) ==true )
        {
            click(intercomClearTab);
            await();
        }
    }

    public void clickOnCreateSite()
    {
        awaitForElementPresence(proceedButton);
        click(proceedButton);
        waitForElementAppear(catalogPage,catalogLoader, Config.getIntValueForProperty("element.numberOFRetries"),Config.getIntValueForProperty("element.wait.time"));
    }


    public String getNewlyCreatedSiteId()
    {
       return getDriver().getCurrentUrl().split("/")[2];
    }

    public void selectEnvironmentValue(String env)
    {
        awaitForElementPresence(environmentDropdown);
        click(environmentDropdown);
        selectDropDownValue(dropDownValues,env);
    }

    public void selectVerticalValue(String vertical)
    {
        awaitForElementPresence(verticalDropdown);
        click(verticalDropdown);
        selectDropDownValue(dropDownValues,vertical);
    }

    public void selectPlatformValue(String platform)
    {
        awaitForElementPresence(platformDropdown);
        click(platformDropdown);
        selectDropDownValue(dropDownValues,platform);

    }

    public void selectLanguageValue(String lang)
    {
        awaitForElementPresence(languageDropdown);
        ThreadWait();
        click(languageDropdown);
        selectDropDownValue(dropDownValues,lang);
    }

    public String getCreatedSiteKey()
    {
        scrollToBottom();
        Assert.assertTrue(awaitForElementPresence(siteKeyInput));
        return siteKeyInput.getText();
    }

    public String getSecreteKey()
    {
        scrollToBottom();
        awaitForElementPresence(secreteKeyInput);
        return secreteKeyInput.getText();
    }

    public String getApiKey()
    {
        scrollToBottom();
        awaitForElementPresence(apiKeyInput);
        return apiKeyInput.getText();
    }

    public void deleteSite(String siteKey, String ssoIdKeyCookie, String csrf)
    {
        ApiClient apiClient = new ApiClient();
        HashMap<String,String> ssoCookie = new HashMap<>();
        Map<String,String> headers = new HashMap<>();

        String url = EnvironmentConfig.getServiceUrl("console")+"/skipper/site/"+siteKey;

        //cookies.put(Config.getStringValueForProperty("ssoIdKey"),Config.getStringValueForProperty("deleteSiteSsoIdKeyValue"));
        //ssoCookie.put(Config.getStringValueForProperty("ssoIdKey"),EnvironmentConfig.getDeleteCookie());
        ssoCookie.put(Config.getStringValueForProperty("ssoIdKey"),ssoIdKeyCookie);
        headers.put("x-xsrf-token",csrf);

        UnbxdResponse response = apiClient.executeRequest(HttpMethod.DELETE,url,ssoCookie,headers,null,null,null);
        String response1= response.getResponseBody();
        System.out.println(response1);
        Assert.assertEquals(response.getStatusCode(),200);


        /*ErrorDto response2 = new Gson().fromJson(response1, ErrorDto.class);
        Assert.assertEquals(response2.getData().get("skipper"),"site deleted successfully.");
        Assert.assertNull(response2.getErrors());*/
        //Assert.assertEquals(response2.getErrors().get(0).get("code"),"500");
        //Assert.assertEquals(response2.getErrors().get(0).get("message"),"Error while deleting site from search platform: Error while deleting site in search");
        System.out.println("Site deleted Successfully");

        //deserialise and serialisation, json
    }


   /* public static void main(String[] args)
    {
        CreateSiteActions deleteSite1 = new CreateSiteActions();
        deleteSite1.deleteSite("ss-unbxd-AutoTestSite116141551081309431614155123");
    }*/

    @Data
    private static class ErrorDto
    {
        @SerializedName("@class")
        String className;
        List<Map<String, String>> errors;
        Map<String, Object> data;
    }




}
