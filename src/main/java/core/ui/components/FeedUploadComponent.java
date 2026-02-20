package core.ui.components;


import core.consoleui.actions.CatalogPageActions;
import core.consoleui.page.CatalogPage;
import core.ui.actions.*;
import core.ui.page.UiBase;
import lib.Config;
import lib.UrlMapper;
import lib.compat.Page;
import org.testng.Assert;

import java.util.regex.Pattern;

import static core.ui.page.FeedUploadPage.pageLoader;
import static lib.enums.UnbxdEnum.MAGENTO_TAB;
import static lib.enums.UnbxdEnum.PLATFORM_UPLOAD;

public class FeedUploadComponent extends UiBase {

    @Page
    FeedUploadActions feedUploadActions;

    @Page
    CreateSiteActions createSiteActions;

    @Page
    CreateSiteComponent createSiteComponent;

    @Page
    AutoMappingActions autoMappingActions;

    @Page
    FacetableFieldsActions facetableFieldsActions;

    @Page
    AutoSuggestActions autoSuggestActions;

    @Page
    SearchableFieldActions searchableFieldActions;

    @Page
    PreviewActions previewActions;

    @Page
    CatalogPage catalogPage;

    @Page
    CatalogPageActions catalogPageActions;



    public String goTillFeedUpload() throws InterruptedException {

        //CreateSite
        createSiteComponent.createSite();
        Assert.assertTrue(createSiteActions.awaitForElementPresence(createSiteActions.catalogUploadPageTitle),"Site has not been created");
        Assert.assertEquals(createSiteActions.uploadPageText.getText(), "How would you like to upload your catalog today?");

        //Upload feed

        feedUploadActions.selectUploaders(PLATFORM_UPLOAD);
        feedUploadActions.selectPlatform(MAGENTO_TAB);
        String siteKey = createSiteActions.getCreatedSiteKey();
        String secreteKey = createSiteActions.getSecreteKey();

        feedUploadActions.apiUpload(siteKey, secreteKey);
        feedUploadActions.clickOnProceedButton();
        feedUploadActions.waitForFeedUploadToComplete();
        ThreadWait();
        Assert.assertTrue(feedUploadActions.successMsg.getText().equalsIgnoreCase("Feed Upload is Successful"));


        //goto Mapping page
        click(autoMappingActions.mapCatalogButton);
        // Thread.sleep(15000);
        facetableFieldsActions.waitForLoaderToDisAppear(facetableFieldsActions.relevancyPageLoader1, facetableFieldsActions.relevancePageLoader);
        facetableFieldsActions.waitForElementAppear(autoMappingActions.setUpSearchButtons, autoMappingActions.mappingPageLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
        facetableFieldsActions.waitForElementAppear(autoMappingActions.mapList1, autoMappingActions.mappingPageLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
        Assert.assertEquals(autoMappingActions.setUpSearchButton.getText(), "Set Up Search");
        return siteKey;
    }

    public void goTillMappingPage() throws InterruptedException
    {
        //Map fields
        Thread.sleep(15000);
        autoMappingActions.mapFields();
        threadWait();
        click(autoMappingActions.setUpSearchButton);
        ThreadWait();
        Assert.assertFalse(awaitForElementPresence(autoMappingActions.setUpSearchButton),"setUp Search Button is not working properly");
    }

    public void goToRelevancyPageWithoutMapping() throws InterruptedException
    {
        ThreadWait();
        click(autoMappingActions.setUpSearchButton);
        ThreadWait();
        Assert.assertFalse(awaitForElementPresence(autoMappingActions.setUpSearchButton),"setUp Search Button is not working properly");
    }

    public void goTillRelevancyPage() throws InterruptedException {

        //Go to Relevancy page
        facetableFieldsActions.waitForElementAppear(autoSuggestActions.skipQueryButton, facetableFieldsActions.relevancePageLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
        Thread.sleep(15000);
        Assert.assertEquals(autoSuggestActions.skipQuery.getText(), "Skip this step");
        facetableFieldsActions.skipQueryData();
        threadWait();
        await();
        Assert.assertFalse(awaitForElementPresence(facetableFieldsActions.skipQueryDataButton),"skip this step is not working properly");
        facetableFieldsActions.waitForLoaderToDisAppear(facetableFieldsActions.relevancyPageLoader, facetableFieldsActions.relevancePageLoader);
        facetableFieldsActions.waitForElementAppear(searchableFieldActions.searchableFieldIdentifier, facetableFieldsActions.relevancePageLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
        Assert.assertEquals(searchableFieldActions.applyAiRec.getText(), "Apply configurations");

    }

    public void goTillPreviewPage() throws InterruptedException
    {
        previewActions.threadWait();
        awaitForElementPresence(previewActions.applyConfigButton);
        click(previewActions.applyConfigButton);
        threadWait();
        Assert.assertFalse(awaitForElementPresence(previewActions.applyConfigButton),"apply Configuration Button is not working properly");
        feedUploadActions.WaitTillReindexComplete();
        await();
        previewActions.goToPreviewPage();
        previewActions.awaitForElementPresence(previewActions.productCount);
        await();
    }

    public String getStatusByIdFromUrl() {
        String currentUrl = getDriver().getCurrentUrl();

        String[] urlParts = currentUrl.split(Pattern.quote("/"));
        String statusId = urlParts[5];
        return statusId;
    }


    public String getRelevancyPage() {
        awaitForPageToLoad();
        return UrlMapper.RELEVANCY_PAGE.getBaseUrl(getStatusByIdFromUrl());
    }


}