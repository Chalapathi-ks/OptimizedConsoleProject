package UnbxdTests.testNG.ui.PreviewTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import UnbxdTests.testNG.ui.BaseTest;
import com.google.gson.JsonObject;
import core.ui.actions.*;
import core.ui.page.PreviewPage;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static core.ui.page.UiBase.ThreadWait;

public class PreviewTemplateAndFacetTest extends BaseTest {

    @Page
    LoginActions loginActions;

    @Page
    PreviewTemplateActions previewTemplateActions;

    @Page
    PreviewActions previewActions;

    @Page
    AutoMappingActions autoMappingActions;

    @Page
    PreviewFacetActions previewFacetActions;

    @Page
    PreviewPage previewPage;

    @BeforeClass
    public void init() {
            super.setUp();
            initFluent(driver);
            initTest();
            // Login is now handled by BaseTest's global login mechanism
    }

   @FileToTest(value = "/previewTest/previewTemplate.json")
    @Test(description = "This test verifies the previewPage Templates ", priority = 2, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifyAppliedTemplateTest(JsonObject object) throws InterruptedException {

        UnbxdEnum template = UnbxdEnum.valueOf(object.get("template").getAsString());
        String appliedTemplateName = object.get("verifyAppliedTemplate").getAsString();
        //String previewPage = driver.getCurrentUrl();
        goTo(previewPage);
        previewActions.awaitForPageToLoad();
        ThreadWait();
        await();
        previewFacetActions.closeTemplatePopupInCaseOpened();
        previewTemplateActions.awaitForElementPresence(previewActions.productCount);
        previewTemplateActions.awaitForElementPresence(previewTemplateActions.viewTemplateLink);
        previewFacetActions.closeTemplatePopupInCaseOpened();
        await();
        previewTemplateActions.awaitForElementPresence(previewTemplateActions.viewTemplateLink);
        Thread.sleep(5000);
        click(previewTemplateActions.viewTemplateLink);
        Thread.sleep(5000);
        previewTemplateActions.selectTemplate(template);
        previewTemplateActions.awaitForElementPresence(previewTemplateActions.appliedTemplateName);
        Assert.assertTrue(previewTemplateActions.getAppliedTemplateName().equalsIgnoreCase(appliedTemplateName));
        Assert.assertTrue(previewTemplateActions.awaitForElementPresence(previewTemplateActions.appliedBanner), "Template is not applied");
    }


    @FileToTest(value = "/previewTest/previewTemplateField.json")
    @Test(description = "This test verifies the  previewPage Templates Fields ", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifyTemplatesFieldsTest(JsonObject object) throws InterruptedException {
            UnbxdEnum template = UnbxdEnum.valueOf(object.get("template").getAsString());
            UnbxdEnum verifyField = UnbxdEnum.valueOf(object.get("verifyField").getAsString());
            String appliedTemplateName = object.get("verifyAppliedTemplate").getAsString();
            String field = object.get("mapField").getAsString();
            String value = object.get("value").getAsString();

            goTo(previewPage);
            previewActions.awaitForPageToLoad();
            ThreadWait();
            await();
            previewTemplateActions.closePopUpIfOpened();
            previewTemplateActions.awaitForElementPresence(previewActions.productCount);
            previewTemplateActions.awaitForElementPresence(previewTemplateActions.viewTemplateLink);
            previewTemplateActions.closePopUpIfOpened();
            click(previewTemplateActions.viewTemplateLink);
            await();
            previewTemplateActions.selectTemplate(template);
            ThreadWait();
            Assert.assertTrue(previewTemplateActions.getAppliedTemplateName().equalsIgnoreCase(appliedTemplateName));
            if (previewActions.awaitForElementPresence(previewActions.templatePopupCloseButton) == true) {
                FluentWebElement fieldName = autoMappingActions.findMappingTemplateField(field);
                if (fieldName != null) {
                    autoMappingActions.selectMappingValue(value);
                    ThreadWait();;
                    autoMappingActions.saveMappingChanges();
                }
            }
            Assert.assertTrue(previewTemplateActions.verifyFieldPresence(verifyField) > 0, "verified field name : " + field + " is not present!!!");
            System.out.println(verifyField);
    }
//facets
    @FileToTest(value = "/previewTest/filterFacet.json")
    @Test(description = "This test Verifies facet functionality ", priority = 3, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifyFacetTest(JsonObject object) {
        String facet= object.get("facet").getAsString();
        String facetValue= object.get("facet_value").getAsString();
        try {
            goTo(previewPage);
            previewActions.awaitForPageToLoad();
            ThreadWait();
            await();
            previewFacetActions.closeTemplatePopupInCaseOpened();
            previewFacetActions.selectFacetValue(facetValue, facet);
            ThreadWait();
            previewFacetActions.verifyProductCountForSingleSelectedFacet(facetValue,facet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FileToTest(value = "/previewTest/mulptileFilterFacet.json")
    @Test(description = "This test Verifies facet functionality ", priority = 4, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifyMultipleFacetTest(JsonObject object) throws InterruptedException {
        String facet = object.get("facet").getAsString();
        String facetValueList = object.get("facet_value").getAsString();
        String facet1 = object.get("facet1").getAsString();
        String facetValueList1 = object.get("facet_value1").getAsString();

        goTo(previewPage);
        previewActions.awaitForPageToLoad();
        ThreadWait();
        await();
        previewFacetActions.closeTemplatePopupInCaseOpened();
        int filterCount = 0,filterCount1=0, totalFilterCount = 0;
        filterCount = previewFacetActions.getFilterCount(facetValueList, facet);
        previewFacetActions.selectFacetValue(facetValueList,facet);
        if(facet1.length()>0) {
            filterCount1 = previewFacetActions.getFilterCount(facetValueList1, facet1);
            previewFacetActions.selectFacetValue(facetValueList1,facet1);}
        if (facet1.equals(facet)) {
            totalFilterCount = filterCount + filterCount1;
        }else {
            totalFilterCount = filterCount1;}
        List<String> selectedFacets =previewFacetActions.getSelectedFacetList();
        previewFacetActions.awaitTillElementDisplayed(previewFacetActions.selectedFacetTag);
        ThreadWait();
        Assert.assertTrue(selectedFacets.contains(facetValueList1.toLowerCase()) && selectedFacets.contains(facetValueList.toLowerCase()));
        int FilteredProductCount = previewFacetActions.getProductCount();
        System.out.println("totalFilterCount is :" + totalFilterCount + " FilterProductcount is : " + FilteredProductCount);
        ThreadWait();
        Assert.assertEquals(totalFilterCount, FilteredProductCount, "Facets Counts are not matched with filtered facet count");
    }

    @FileToTest(value = "/previewTest/filterFacet.json")
    @Test(description = "This test Verifies facet clear all functionality ", priority = 5, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifyFacetClearAllTest(JsonObject object) throws InterruptedException {
        String facet= object.get("facet").getAsString();
        String facetValue= object.get("facet_value").getAsString();
        goTo(previewPage);
        previewActions.awaitForPageToLoad();
        ThreadWait();
        await();
        previewFacetActions.closeTemplatePopupInCaseOpened();
        previewFacetActions.selectFacetValue(facetValue, facet);
        ThreadWait();
        Assert.assertTrue(previewFacetActions.awaitForElementPresence(previewFacetActions.clearAllFacetTag), "Clear all facet tag is not displayed");
        previewFacetActions.click(previewFacetActions.clearAllFacetTag);
        ThreadWait();
        Thread.sleep(2000);
        Assert.assertFalse(previewFacetActions.awaitForElementPresence(previewFacetActions.selectedFacetTag), "CLEAR ALL FACET TAB IS NOT WORKING");
    }

    @FileToTest(value = "/previewTest/filterPriceRangeFacet.json")
    @Test(description = "This test Verifies facet priceRange functionality ", priority = 6, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifyPriceRange(JsonObject object) throws InterruptedException
    {
        goTo(previewPage);
        previewActions.awaitForPageToLoad();
        ThreadWait();
        await();
        previewFacetActions.closeTemplatePopupInCaseOpened();
        previewFacetActions.awaitForElementPresence(previewFacetActions.priceFacetSlider);
        click(previewFacetActions.priceFacetSlider);
        List<String> selectedFacets =previewFacetActions.getSelectedFacetList();
        for(int i=0;i<selectedFacets.size();i++) {
            previewFacetActions.awaitTillElementDisplayed(previewFacetActions.selectedFacetTag);
            ThreadWait();
            Assert.assertTrue(selectedFacets.get(i).endsWith(previewFacetActions.selectedPriceFacet.getText().replace("$", "")),"PRICE FACET IS NOT SELECTED");
        }
    }

    @FileToTest(value = "/previewTest/categoryPath.json")
    @Test(description = "This test Verifies facet categoryPath functionality ", priority = 7, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void verifyCategoryPath(JsonObject object) throws InterruptedException
    {
        String facet= object.get("facet").getAsString();
        String facetValue= object.get("facet_value").getAsString();
        goTo(previewPage);
        previewActions.awaitForPageToLoad();
        ThreadWait();
        await();
        previewFacetActions.closeTemplatePopupInCaseOpened();
        previewFacetActions.awaitForElementPresence(previewFacetActions.priceFacetSlider);
        previewFacetActions.selectFacetValue(facetValue,facet);
        Assert.assertTrue(previewFacetActions.CategoryPathBreadcrumb.getText().contains(facetValue), "CATEGORY PATH BREADCRUMB IS NOT SELECTED");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.close();
        driver.quit();
        Helper.tearDown();
    }


}

