package core.ui.actions;

import com.google.gson.annotations.SerializedName;
import core.ui.page.FacetableFieldsPage;
import lib.Config;
import lib.EnvironmentConfig;
import lib.Helper;
import lib.api.ApiClient;
import lib.api.HttpMethod;
import lib.api.UnbxdResponse;
import lib.enums.UnbxdEnum;
import lombok.Data;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FacetableFieldsActions extends FacetableFieldsPage {

    FeedUploadActions feedUploadActions;

    public void resetRelevancy(String sitekey,String ssoIdKeyCookie,String state)
    {
        ApiClient apiClient = new ApiClient();
        HashMap<String,String> cookies = new HashMap<>();

        String url = EnvironmentConfig.getServiceUrl("console")+"/skipper/site/"+sitekey+"/ftu/reset";

        //cookies.put(Config.getStringValueForProperty("ssoIdKey"),Config.getStringValueForProperty("relevancySsoIdKeyValue"));
        cookies.put(Config.getStringValueForProperty("ssoIdKey"),ssoIdKeyCookie);
        cookies.put(Config.getStringValueForProperty("userId"),Config.getStringValueForProperty("userIdKeyValue"));

        HashMap<String,String> header = new HashMap<>();
        header.put(Config.getStringValueForProperty("postType"),Config.getStringValueForProperty("postValue"));

        //UnbxdResponse response = apiClient.executeRequest(HttpMethod.POST,url,cookies,header,null,null,"{\"stateType\":\"SETUP_SEARCH\"}");
        UnbxdResponse response = apiClient.executeRequest(HttpMethod.POST,url,cookies,header,null,null,state);

        System.out.println(response.getResponseBody());
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Data
    static class ErrorDto
    {
        @SerializedName("@class")
        String className;
        List<Map<String, String>> errors;
    }


    public void goToRelevancyPage()
    {
        click(manualSetUpButton);
        waitForLoaderToDisAppear(feedUploadActions.pageLoader,relevancePageLoader,Config.getIntValueForProperty("indexing.numOfRetries"),Config.getIntValueForProperty("indexing.wait.time"));
    }

    public String getFacetCount()
    {
        String count= facetCount.getText();
        return count;
    }

    public void goToRelevancySectionsByName(UnbxdEnum type)
    {
        waitForLoaderToDisAppear(feedUploadActions.pageLoader,relevancePageLoader,Config.getIntValueForProperty("indexing.numOfRetries"),Config.getIntValueForProperty("indexing.wait.time"));
        threadWait();
        FluentWebElement section = getGroup(type);
        await();
        click(section);
    }

    public List<String> getFacetableFields()
    {
        List<String> listOfFeaturedFacets = new ArrayList<>();

        for(int i = 0; i < facetList.size(); i++)
        {
            listOfFeaturedFacets.add(facetList.get(i).find(".field-name").getText().trim());
            System.out.println("facets are" +listOfFeaturedFacets);
        }
        return listOfFeaturedFacets;
    }

    public void selectPageCount(String pageCount)
    {
        scrollToBottom();
        awaitForElementPresence(paginationDropDown);
        click(paginationDropDown);

        for(int i=0; i < pageCountList.size(); i++)
        {
            if(pageCountList.get(i).getText().equals(pageCount))
            {
                click(pageCountList.get(i));
                await();
                break;
            }
        }
    }

    public FluentWebElement getGroup(UnbxdEnum type)
    {
        switch(type)
        {
            case SEARCHABLE_FIELD:
                return searchableFieldGroup;
            case FACETABLE_FIELD:
                return facetableFieldGroup;
            case DICTIONARY:
                return dictionaryGroup;
            case AUTO_SUGGEST:
                return autoSuggestGroup;
            default:
                return null;
        }
    }

    public void openCreateFacetForm()
    {
        safeClick(createNewFacetTab);
    }

    public void checkSelectedField(String facetAttribute) throws InterruptedException {
        awaitForElementPresence(facetAttributeDropDown);
        click(facetAttributeDropDown);
        Thread.sleep(3000);
        ThreadWait();
        attributeInputBox.fill().with(facetAttribute);
        ThreadWait();
        selectValueBYMatchingText(facetAttribute);
    }
    public void SelectedField(String facetAttribute) throws InterruptedException {
        awaitForElementPresence(facetAttributeDropDown);
        click(facetAttributeDropDown);
        Thread.sleep(3000);
        ThreadWait();
        attributeInputBox.fill().with(facetAttribute);
    }

    public String fillFacetDetails(Map<String, Object> testData) throws InterruptedException {
        String name = (String) testData.get("facetDisplayName");
        String facetAttribute = (String) testData.get("facetName");

        awaitForElementPresence(facetAttributeDropDown);
        ThreadWait();
        click(facetAttributeDropDown);
        Thread.sleep(3000);
        ThreadWait();
        attributeInputBox.fill().with(facetAttribute);
        ThreadWait();
        selectValueBYMatchingText(facetAttribute);
        await();
        awaitForElementPresence(displayNameInput);
        displayNameInput.fill().with(name);
        awaitForElementPresence(facetEnableToggle);
        if(!checkElementPresence(activeToggle))
        {
            click(facetEnableToggle);
            threadWait();
        }
        else
        {
            click(facetEnableToggle);
            threadWait();
            click(facetEnableToggle);
            awaitForElementPresence(activeToggle);
            Assert.assertTrue(activeToggle.isDisplayed());
        }


        if((testData.get("rangeStart") != null))
        {
            String rangeStart = (String) testData.get("rangeStart");
            String rangeEnd = (String) testData.get("rangeStop");
            String rangeGap = (String) testData.get("rangeGap");

            startRangeInput.fill().with(rangeStart);
            endRangeInput.fill().with(rangeEnd);
            rangeGapInput.fill().with(rangeGap);


        }else
        {
            String facetLength = (String) testData.get("facetLength");
            String order = (String) testData.get("sortOrder");

            facetLengthInput.fill().with(facetLength);
            waitForNodataOverlayThenClickSortOrder();
            ThreadWait();
            selectValueBYMatchingText(order);
        }
        return name;

    }

    public String fillUpdateFacetDetails(Map<String, Object> testData) throws InterruptedException {
        String updateName = (String) testData.get("updatedFacetDisplayName");
        String updatefacetLength = (String) testData.get("updatedfacetLength");
        awaitForElementPresence(displayNameInput);
        displayNameInput.click();
        displayNameInput.clear();
        displayNameInput.fill().with(updateName);
      if((testData.get("updatedfacetLength"))!=null){
          facetLengthInput.click();
          facetLengthInput.clear();
          facetLengthInput.fill().with(updatefacetLength);
      }

        if((testData.get("updatedRangeStart") != null))
        {
            String rangeStart = (String) testData.get("updatedRangeStart");
            String rangeEnd = (String) testData.get("updatedRangeStop");
            String rangeGap = (String) testData.get("updatedRangeGap");
            startRangeInput.clear();
            startRangeInput.fill().with(rangeStart);
            endRangeInput.clear();
            endRangeInput.fill().with(rangeEnd);
            rangeGapInput.click();
            rangeGapInput.fill().with(rangeGap);


        }else {
            String updateOrder = (String) testData.get("updateSortOrder");
            waitForNodataOverlayThenClickSortOrder();
            ThreadWait();
            selectValueBYMatchingText(updateOrder);
            ThreadWait();
        }

        awaitForElementPresence(facetEnableToggle);
         if(checkElementPresence(activeToggle))
        {
            ThreadWait();
            click(facetEnableToggle);
            threadWait();
        }
        return updateName;
    }



    public String getFieldTypeFromEditWindow(FluentWebElement facetElement) {
        return editWindowfacetType.getValue().trim();
    }

    public String getDisplayName(FluentWebElement facetElement)
    {
        await();
        return facetElement.find(displayName).getText().trim();
    }

    public String getAttributeName(FluentWebElement facetElement)
    {
        await();
        return facetElement.find(attributeName).getText().trim();
    }

    public String getFacetStatus(FluentWebElement facetElement)
    {
        await();
        return facetElement.find(facetState).getText().trim();
    }

    public String getFieldType(FluentWebElement facetElement)
    {
        await();
        return facetElement.find(facetType).getText().trim();

    }

    public void updateFacetLength(String length)
    {
        awaitForElementPresence(facetLengthInput);
        facetLengthInput.fill().with(length);
    }

    public String getFacetLength()
    {
        awaitForElementPresence(facetLengthInput);
        return facetLengthInput.getValue().trim();
    }

    public void updateFacetSortOrder(String order) throws InterruptedException {
        awaitForElementPresence(sortOrderDropDown);
        waitForNodataOverlayThenClickSortOrder();
        selectValueBYMatchingText(order);
    }

    private void waitForNodataOverlayThenClickSortOrder() {
        try {
            new WebDriverWait(getDriver(), 5).until(
                ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".nodata-wrapper-dd")));
        } catch (Exception ignored) { }
        ThreadWait();
        safeClick(sortOrderDropDown);
    }

    public String getFacetSortOrder()
    {
        awaitForElementPresence(sortOrderInput);
        return sortOrderInput.getText().trim();
    }

    public void updateFacetState(String state) throws InterruptedException {
        awaitForElementPresence(facetEnableToggle);
        threadWait();
        facetEnableToggle.click();
        selectValueBYMatchingText(state);
    }

    public void saveFacet()
    {
        awaitForElementPresence(saveFacetButton);
        safeClick(saveFacetButton);
        threadWait();
    }

    public FluentWebElement getFacetUsingDisplayName(String name)
    {
        awaitForElementPresence(facetSearchBox);
        ThreadWait();
        searchIcon.click();
        facetSearchBox.click();
        facetSearchBox.clear();
        ThreadWait();
        facetSearchBox.fill().with(name);
        ThreadWait();
        for(int i=0 ; i<facetList.size() ; i++)
        {
            if(find(".mb-3 .tbl-title-2").getText().trim().equals(name))
                return facetList.get(i);
        }
        return null;
    }

    public FluentWebElement getFacetUsingFieldName(String name)
    {
        awaitForElementPresence(facetSearchBox);
        ThreadWait();
        searchIcon.click();
        facetSearchBox.click();
        facetSearchBox.clear();
        ThreadWait();
        facetSearchBox.fill().with(name);
        ThreadWait();
        for(int i=0 ; i<facetList.size() ; i++)
        {
            if(find(".tbl-title-1.flex-vertical-center").getText().trim().equals(name))
                return facetList.get(i);
        }
        return null;
    }


    public void closeFacetCreationWindow()
    {
        awaitForElementPresence(facetWindowCloseButton);
        await();
        click(facetWindowCloseButton);
    }

    public void clearSearchBox()
    {
        awaitForElementPresence(facetSearchBox);
        facetSearchBox.clear();
    }

    public void deleteFacet(String name) throws InterruptedException
    {
        getFacetUsingDisplayName(name);
        safeClick(deleteFacetIcon);
        awaitForElementPresence(deleteConfirmationTab);
        safeClick(deleteYes);
    }

    public void deleteFacet1(String name) throws InterruptedException
    {
        getFacetUsingDisplayName(name);
        safeClick(deleteFacetIcon);
        awaitForElementPresence(deleteConfirmationTab);
        safeClick(deleteYes);
    }

    public String createFacet() throws InterruptedException {
        Map<String, Object> testData = new HashMap<>();
        //testData.put("facetDisplayName", getRandomName());
        testData.put("facetDisplayName", "price"+System.currentTimeMillis());
        //testData.put("facetName", "gender");
        testData.put("facetName", "brand");
        testData.put("facetLength", "4");
        testData.put("sortOrder", "Product Count");
        testData.put("facetDisplayValue","Enabled");

        String name = fillFacetDetails(testData);
        saveFacet();

        return name;
    }

    public FluentWebElement getFacetByPosition(int position)
    {
        if(facetList.size()>0)
        {
            for(int i=0 ; i<facetList.size();i++)
            {
                return facetList.get(position);
            }
        }
        return null;
    }

    public void selectFacetAttributeFromDropdown(String attributeName)
    {
        click(facetAttributeDropDown);
        attributeInputBox.fill().with(attributeName);

        for(int i=0 ; i<attributeList.size() ; i++)
        {
            if(attributeList.get(i).getText().equalsIgnoreCase(attributeName))
            {
                click(attributeList.get(i));
                break;
            }
        }
    }

    public void disableFacet(String name)
    {
        getFacetUsingDisplayName(name);

    }

    public void openFacetEditWindow(FluentWebElement facet) throws InterruptedException
    {
        Thread.sleep(10000);
        Helper.mouseOver(facet.getElement());
        ThreadWait();
        click(editFacetIcon);
        awaitForElementPresence(editWindow);
        Assert.assertTrue(editWindow.isDisplayed(),"Facet Edit Window is not opened");
    }

    public void skipQueryData()
    {
        awaitForElementPresence(skipQueryDataButton);
        click(skipQueryDataButton);
    }
}
