package core.ui.actions;

import core.ui.page.FeedUploadPage;
import lib.Config;
import lib.EnvironmentConfig;
import lib.UnbxdFileUtils;
import lib.api.ApiClient;
import lib.api.HttpMethod;
import lib.api.UnbxdResponse;
import lib.enums.UnbxdEnum;
import lib.compat.Page;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

import java.io.File;
import java.util.HashMap;
import java.util.NoSuchElementException;

import static org.testng.Assert.assertEquals;

public class FeedUploadActions extends FeedUploadPage {

    private final String testFilePath="target"+File.separator+"test-classes"+File.separator+"testData"+File.separator;

    public void uploadFeedUrl(String feedUrl)
    {
        openUrlImportWindow();
        fillFeedUploadUrl(feedUrl);
        clickOnStartImportTab();
        //waitForLoaderToDisAppear(pageLoader,importLoaderName);
    }

    public void selectUploaders(UnbxdEnum type)
    {
        switch(type)
        {
            case PLATFORM_UPLOAD:
                click(platformUpload);
            case PIM_UPLOAD:
                click(pimUpload);
            case API_UPLOAD:
                click(apiUpload);
            default:
        }

    }

    public void WaitTillReindexComplete()
    {
        waitForLoaderToDisAppear(progressBarLoader, feedUploadLoaderName);
        if (awaitForElementPresence(feedFailMsg) == true) {
            Assert.assertTrue(feedFailMsg.getText().equalsIgnoreCase("Feed Indexing Failed"));
            System.out.println("Reindexing is failing");
            Assert.fail("Reindexing got failed");
        } else {
            waitForElementAppear(previewButton, previewPageLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
        }
    }

    public void GoBackToPlatformsTab()
    {
        await();
        scrollToTop();
        awaitForElementPresence(BackToPlatformTab);
        click(BackToPlatformTab);
    }

    public void selectPlatform(UnbxdEnum platform)
    {
        switch (platform)
        {
            case MAGENTO_TAB:
                click(magentoTab);
            case HYBRIS_TAB:
                click(hybrisTab);
            case SHOPIFY_TAB:
                click(shopifyTab);
            default:
        }
    }

    public void openUrlImportWindow()
    {
        await();
        getDriver().switchTo().frame("pimIframe");
        waitForElementAppear(urlUpload,feedUploaderName,2,180);
        click(urlUploadTab);
        Assert.assertTrue(importPopUpTittle.getText().equalsIgnoreCase("Import from external URL"));
    }

    public void clickOnPrepareImportTab()
    {
        awaitForElementPresence(prePareForImportTab);
        click(prePareForImportTab);
    }


    public void fillFeedUploadUrl(String feedUrl)
    {
        awaitForElementPresence(feedUrlInput);
        feedUrlInput.fill().with(feedUrl);
    }

    public void clickOnStartImportTab()
    {
        awaitForElementPresence(startImportButton);
        click(startImportButton);
        waitForLoaderToDisAppear(loadingOverLay,importLoaderName);

        //getDriver().switchTo().defaultContent();
    }

    /*public void mapMandatoryProperties(String productId,String productName,String category) throws InterruptedException {
        awaitForElementPresence(mapImportText);
        awaitForElementPresence(importSave);
        selectPropertyFromDropDown(mandatoryMappingFieldsList,"Product Id",productId);
        Thread.sleep(3000);
        selectPropertyFromDropDown(mandatoryMappingFieldsList,"Product Name",productName);
        Thread.sleep(3000);
        selectPropertyFromDropDown(mandatoryMappingFieldsList,"Category",category);
        Thread.sleep(3000);
    }*/

    public void mapMandatoryProperties(String uniqueId) throws InterruptedException {
        awaitForElementPresence(mapImportText);
        awaitForElementPresence(importSave);
        selectPropertyFromDropDown(mandatoryMappingFieldsList,"UniqueId",uniqueId);
        Thread.sleep(3000);
    }

    public void startImport()
    {
        ((JavascriptExecutor) getDriver())
                .executeScript("window.scrollTo(0, 0)");
        awaitForElementPresence(preImportSaveButton);
        click(preImportSaveButton);
        click(reviewImportButton);
        awaitForPageToLoad();
    }

    public void associateAnAdapter(String adapterName)
    {
        awaitForElementPresence(adapterInput);
        adapterInput.fill().with(adapterName);
        click(adapterSaveButton);
        waitForLoaderToDisAppear(loadingOverLay,importLoaderName);

    }

    public void waitForProductPreImportToGetCompleted() {
       waitForLoaderToDisAppear(imageLoader, importLoaderName);
        //waitForLoaderToDisAppear(PimImportLoader,importLoaderName);
        waitForLoaderToDisAppear(imageLoader,importLoaderName);
        waitForElementToBeClickable(importSave, saveButtonName);
    }

    public void waitForProductImportToComplete() {
        waitForLoaderToDisAppear(PimImportLoader, importLoaderName);
        waitForLoaderToDisAppear(imageLoader,importLoaderName);
        waitForLoaderToDisAppear(imageLoader,importLoaderName,Config.getIntValueForProperty("indexing.numOfRetries"),Config.getIntValueForProperty("indexing.wait.time"));
        awaitForElementPresence(setUpSearchButton);
        //waitForElementAppear(setUpSearch,setUpSearchName,Config.getIntValueForProperty("indexing.numOfRetries"),Config.getIntValueForProperty("indexing.wait.time"));
        //waitForElementAppear(consoleUi,setUpSearchName,2,180);
        //awaitForElementPresence(consoleUi);

        //Assert.assertTrue(indexingPercentage.getText().equals("100%"),"Indexing is not yet completed");
        //waitForElementToLoad(importCompletedMsg, importCompletedMsgName);
        //waitForElementToLoad(totalProductsCount, totalProductsCountName);
        //waitForElementToLoad(failedProductsCount,failedProductsCountName);

    }

    public void waitForFeedUploadToComplete() {
            waitForLoaderToDisAppear(progressBarLoader,feedUploadLoaderName);
            await();

            if (awaitForElementPresence(feedFailMsg) == true) {
                Assert.assertTrue(feedFailMsg.getText().equalsIgnoreCase("Feed Indexing Failed"));
                System.out.println("Feed upload is failing");
                Assert.fail("Feed indexing got failed");
            }else {
                waitForElementAppear(successTick, successFeedUploadName, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
                await();
                awaitForElementPresence(successMessage);
                Assert.assertTrue(successMsg.getText().equalsIgnoreCase("Feed Upload is Successful"));
            }
    }

    public void waitForIndexingToComplete()
    {

        String percentage = findFirst(".RCB-progress-value").getAttribute("style").trim();
        await().until(String.valueOf(percentage.equalsIgnoreCase("width: 100%;")));
        //Assert.assertTrue(percentage.equalsIgnoreCase("width: 100%;"));
    }

    public void selectPropertyFromDropDown(FluentList<FluentWebElement> mandatoryMappingFieldsList,String propertyName, String property) throws InterruptedException {

        for(FluentWebElement field : mandatoryMappingFieldsList)
        {
            waitForElementToBeClickable(field," dropDown ");
            awaitForElementPresence(importSave);
            if(field.find(".mandatory-prop-text").getText().equalsIgnoreCase(propertyName)) {
                await();
                field.find(".drop-down-button .caret").click();
                break;
            }
        }
        selectProperty(property);
        awaitForPageToLoad();
    }

    public void selectProperty(String property) throws InterruptedException {
        awaitForElementPresence(mappingSearchBox);
        mappingSearchBox.fill().with(property);
        Thread.sleep(3000);
        FluentList<FluentWebElement> propertiesList = find(".ReactVirtualized__List li");
        selectDropDownValue(propertiesList,property);
        waitForLoaderToDisAppear(imageLoader,importLoaderName);
        awaitForPageToLoad();
    }


    public String getImportSuccessMsg() {
        return retrieveText(importCompletedMsg, importCompletedMsgName);

    }

    public void verifyFailedProductsCount()
    {
        Assert.assertTrue(getFailedProductCount().equals("0"),"REPORT HAS SOME PRODUCT FAILURES! PLEASE CHECK THE REPORT");
        if (getFailedProductCount().equals("0"))
        {
            System.out.println("Product failed count is zero");
        }
        System.out.println("FEED INDEXING IS FAILED ! PLEASE CHECK THE FAILURES");

    }

    public String getFailedProductCount() {
        awaitForElementPresence(failedProductsCount);
        return retrieveText(failedProductsCount, failedProductsCountName);
    }

    public String getTotalProductCount() {
        awaitForElementPresence(totalProductsCount);
        return retrieveText(totalProductsCount, totalProductsCountName);
    }

    public String getProcessedProductCount() {
        awaitForElementPresence(processedProductsCount);
        return retrieveText(processedProductsCount,processedProductsCountName);
    }

    public void verifyTotalProductsCount(String productCount)
    {
        Assert.assertTrue(getTotalProductCount().equals(productCount),"FEED INDEXING FAILED! TOTAL_INDEXED_PRODUCT COUNT IS LESSTHAN THE EXPECTED");

        if(getTotalProductCount().equals(productCount))
        {
            System.out.println("FEED INDEXED SUCCESSFULLY");
        }
        System.out.println("FEED INDEXING IS FAILED ! PLEASE CHECK THE FAILURES");
    }

    public void verifyProcessedProductsCount(String productCount)
    {
        Assert.assertTrue(getProcessedProductCount().equals(productCount),"FEED INDEXING IS FAILED ! PROCESSED_PRODUCT COUNT IS LESSTHAN THE EXPECTED");
        if (getProcessedProductCount().equals(productCount))
        {
            System.out.println("FEED INDEXED SUCCESSFULLY");
        }
        System.out.println("FEED INDEXING IS FAILED ! PROCESSED_PRODUCT COUNT IS LESSTHAN THE EXPECTED");
    }

    public void goToConsoleDashboard()
    {
        awaitForElementPresence(consoleUi);
        click(consoleUi);
        awaitForPageToLoad();
    }

    public void goToOverViewPage()
    {
        awaitForElementPresence(consoleOverViewTab);
        click(consoleOverViewTab);
    }


    public void apiUpload(String siteKey,String secreteKey) {
        try {
            String lang = System.getProperty("lang");

            ApiClient apiClient = new ApiClient();
            HashMap<String, String> cookies = new HashMap<>();

            String url = EnvironmentConfig.getServiceUrl("feed") + "/api/" + siteKey + "/upload/catalog/full";

            HashMap<String, String> header = new HashMap<>();
            header.put(Config.getStringValueForProperty("auth"), secreteKey);


            //File file =  File("/testData/feedUploadTest/7productExpressS3.json");
            //File file = new File(UnbxdFileUtils.normalizePath(testFilePath + "feedUploadTest/7productExpressS3.json"));

            File file = new File(UnbxdFileUtils.normalizePath(testFilePath+ "/feedUploadTest/" + lang + "/hsnFeedS3.json"));
            System.out.println(file);

            UnbxdResponse response = apiClient.executeRequest(HttpMethod.POST, url, null, header, null, file);

            System.out.println(response.getResponseBody());
            Assert.assertEquals(response.getStatusCode(), 200,"Feed upload api is throwing "+response.getStatusCode()+"!!!");
        } catch (Exception e) {
            System.out.println("There is an api upload error");
            Assert.fail("Feed upload API is throwing some error");
        }
    }



    public void clickOnProceedButton()
    {
        Assert.assertTrue(awaitForElementPresence(proceedButton));
        click(proceedButton);
    }
}



