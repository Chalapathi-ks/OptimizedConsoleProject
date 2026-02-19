package core.ui.actions;

import core.ui.page.AutoMappingPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class AutoMappingActions extends AutoMappingPage {


    public String getMappedFieldUsingDisplayName(String field) {
        // awaitForElementPresence(setUpSearchButton);
        for (int i = 0; i < mapList.size(); i++) {
            if (mapList.get(i).find(".flex-one .dimension-label").getText().trim().contains(field)) {
                return mapList.get(i).find(".RCB-inline-modal-btn .RCB-dd-label").getText();
                //  return dimensionMapInput.getText();
            }
        }
        return null;
    }


    public void mapFields() throws InterruptedException {
        Map<String, Object> testData = new HashMap<>();
        testData.put("Title *", "product_name");
        testData.put("Product URL", "product_image_url");
        testData.put("Image URL", "product_image_url");
        testData.put("category", "brand");

        fillMapDetails(testData);
    }

    public void fillMapDetails(Map<String, Object> testData) throws InterruptedException
    {
        for (Map.Entry a : testData.entrySet())
        {
            FluentWebElement field = findMappingField((String)a.getKey());
            ThreadWait();
            selectMappingValue(field,(String) a.getKey(),(String) a.getValue());
        }
    }

    public FluentWebElement findMappingField(String mapField){
        for (int i = 0; i < mapList.size(); i++) {
            if (mapList.get(i).find(".flex-one .dimension-label").getText().trim().equalsIgnoreCase(mapField))
                return mapList.get(i);
        }
        return null;
    }


    public void selectMappingValue(FluentWebElement field, String mapField,String mapValue) throws InterruptedException
    {
        await();
        scrollToElement(field,mapField);
        ThreadWait();
        field.find(".RCB-select-arrow").click();
        mapSearch.fill().with(mapValue);
        Thread.sleep(5000);
        selectDropDownValue(mapDropDownList,mapValue);
        ThreadWait();
        //Assert.assertEquals(getMappedFieldUsingDisplayName(mapField),mapValue);
    }

    public void selectDropDownByIndex(int i,FluentWebElement field, String mapField)
    {
        await();
        scrollToElement(field,mapField);
        await();
        field.find(".RCB-select-arrow").click();
        Assert.assertTrue(dropDownList.size()>0);
        await();
        dropDownList.get(i).click();
        Assert.assertNotEquals(getMappedFieldUsingDisplayName(mapField),"Select");
    }
    public void saveMappingChanges() throws InterruptedException {
        awaitForElementPresence(saveButton);
        ThreadWait();
        click(saveButton);
    }
    public FluentWebElement findMappingTemplateField(String field){
        for (int i = 0; i < templatemMappingList.size(); i++) {
            if (templatemMappingList.get(i).find(".flex-one .dimension-label").getText().trim().equalsIgnoreCase(field))
                return templatemMappingList.get(i);
        }
        return null;
    }

    public void selectMappingValue(String value) throws InterruptedException {
        click(previewTemplatePopupdropdown);
        awaitForElementPresence(mapSearch);
        if (dropDownList.size() > 0)
        {
            for (int i = 0; i < dropDownList.size(); i++) {
                await();
                mapSearch.fill().with(value);
                ThreadWait();
                if (dropDownList.get(i).getText().equals(value)) {
                    ThreadWait();
                    dropDownList.get(i).click();
                    ThreadWait();
                }
            }
        }
    }

    public void clearMappingValue(FluentWebElement mappingField)
    {
        await();
        mappingField.findFirst(clearMappingTab).click();
    }

    public String getMappingValue(FluentWebElement mappedField)
    {
        return mappedField.findFirst().getText();
    }
}




