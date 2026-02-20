package core.ui.actions;

import core.ui.page.RelevancyPage;
import core.ui.page.UiBase;
import lib.Config;
import lib.compat.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class QueryUploadActions extends RelevancyPage {

    @Page
    FacetableFieldsActions facetableFieldsActions;

    @Page
    SearchableFieldActions searchableFieldActions;


    public void uploadQueryFile(String queryDataFile)
    {
        click(uploadQueryButton);
        WebElement uploadFile = getDriver().findElement(By.cssSelector("#contentFile"));
        //uploadFile.sendKeys("src/test/resources/testData/relevancyTest/QueryFile.csv");
        uploadFile.sendKeys(queryDataFile);
    }

    public void validateQueryUpload() throws InterruptedException {
        if(awaitForElementPresence(QueryUploadError)==true)
        {
            System.out.println("Query data file upload is failing" );
            Assert.fail("Here is the error message : " + QueryUploadError.getText() +"!!!");
        }
        facetableFieldsActions.waitForLoaderToDisAppear(facetableFieldsActions.relevancyPageLoader, facetableFieldsActions.relevancePageLoader,Config.getIntValueForProperty("indexing.numOfRetries"),Config.getIntValueForProperty("indexing.wait.time"));
        waitForLoaderToDisAppear(facetableFieldsActions.relevancyPageLoader, facetableFieldsActions.relevancePageLoader);
        facetableFieldsActions.waitForElementAppear(searchableFieldActions.applyAiRecButton, facetableFieldsActions.relevancePageLoader, Config.getIntValueForProperty("indexing.numOfRetries"), Config.getIntValueForProperty("indexing.wait.time"));
        Assert.assertEquals(searchableFieldActions.applyAiRec.getText(),"Apply configurations");
        Thread.sleep(30000);
    }
}
