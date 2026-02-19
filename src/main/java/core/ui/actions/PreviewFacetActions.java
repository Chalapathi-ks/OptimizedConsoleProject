package core.ui.actions;

import core.ui.page.PreviewPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class PreviewFacetActions extends PreviewPage {


    public int getPreviewSnippetCount() {
        return productSnippet.size();
    }

    public int getFacetsCount() {
        return filterSnippet.size();
    }


    public void goToPreviewPage() {
        awaitForElementPresence(previewButtonTab);
        click(previewButtonTab);
        awaitForPageToLoad();
        switchTabTo(1);
        if (awaitForElementPresence(templatePopupCloseButton)) {
            click(templatePopupCloseButton);
        }
        String previewUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(previewUrl.contains("preview"));
    }

    public void passQueryToSearchBox(String query) throws InterruptedException {
        awaitForElementPresence(searchInputBox);
        click(searchInputBox);
        searchInputBox.clear();
        Thread.sleep(7000);
        searchInputBox.fill().with(query);

    }

    public void clickOnSearchIcon() {
        awaitForElementPresence(searchIcon);
        click(searchIcon);

    }

    public int getSearchedQueryCount() {
        String[] arrResult = productCount.getText().split("to")[1].split("of")[1].split("products");
        String SearchResultCount = arrResult[0].trim();
        int searchedProductCount = Integer.parseInt(SearchResultCount);
        return searchedProductCount;

    }

    public int getFilterCount(String facet_value, String facet) {
        int filterCount = 0;
        FluentWebElement fElement = getFacet(facet);
        if (fElement!=null) {
            for (int i = 0; i < fElement.find("button").size(); i++) {
                if (fElement.find(".UNX-facets-all .UNX-facet-text").get(i).getText().equalsIgnoreCase(facet_value)) {
                    String FilterCount = fElement.find("button").get(i).getText().split("\n")[1].trim();
                    String getNumber = FilterCount.replace("(", "").replace(")", "").trim();
                    filterCount = Integer.parseInt(getNumber);
                    System.out.println("intial filter Count of the facet :" + filterCount);
                    break;
                }
            }
        }
        return filterCount;
    }

    public FluentWebElement getFacet(String facet) {
        //FluentWebElement fElement = null;
        if (!allFilters.isEmpty()) {
            for (int i = 0; i < allFilters.size(); i++) {
                if (allFilters.get(i).find(facetHeader).getText().equalsIgnoreCase(facet)) {
                    return allFilters.get(i);
                }
            }
        }
        return null;
    }

    public int getProductCount() {
        String[] arrResult = productCount.getText().split("to")[1].split("of")[1].split("products");
        String SearchResultCount = arrResult[0].trim();
        int searchedProductCount = Integer.parseInt(SearchResultCount);
        return searchedProductCount;

    }

    public void selectFacetValue(String facet_value, String facet) throws InterruptedException {
        try{
            FluentWebElement fElement = getFacet(facet);
            if (fElement!=null) {
//                if(fElement.find(facetValueText).isEmpty())
//                {
//                    click(fElement.find(facetHeader));
//                }
                for (int i = 0; i < fElement.find("button").size(); i++) {
                    if (fElement.find(facetValueText).get(i).getText().equalsIgnoreCase(facet_value)) {
                        waitForLoaderToDisAppear(pageLoader, websitePreviewPageLoader);
                        fElement.find(facetValueText).get(i).doubleClick();
                        Thread.sleep(10000);
                    }
                }
            }else{
                System.out.println(facet+"Facet is not present/matched" );
                Assert.fail("facets are not displayed");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifyProductCountForSingleSelectedFacet(String facet_value, String facet) throws InterruptedException {
            int filterCount = getFilterCount(facet_value, facet);
            //int intialProductCount = getProductCount();
            awaitForElementPresence(selectedFacetTag);
            await();
            int FilteredProductCount = getProductCount();
            if (filterCount == FilteredProductCount) {
                System.out.println("Facet Counts are matched,and Initial count is :" + filterCount + " after filtered count is :" + FilteredProductCount);
            } else {
                Assert.fail("Facet Counts are not matched,and Initial count is :" + filterCount + " after filtered count is :" + FilteredProductCount);
            }
    }

    public void closeTemplatePopupInCaseOpened(){
        try{
            ThreadWait();
            if (awaitForElementPresence(templatePopupCloseButton) == true) {
            click(templatePopupCloseButton);}
        } catch (Exception e) {
            e.printStackTrace();}
    }

    public  List<String> getSelectedFacetList() {
        List<String> selectedFacets=null;
        selectedFacets = new ArrayList<>();
        for (int i = 0; i < SelectedFacetNames.size(); i++) {
            selectedFacets.add(SelectedFacetNames.get(i).getText().toLowerCase().replace("x", " ").replace("/n", " ").trim());
        }
        if (selectedFacets.size() == 0) {
            Assert.fail("facets are not selected");
        }
     return selectedFacets;
    }



    public void categoryPath(String facet_value, String  facet) throws InterruptedException {
        closeTemplatePopupInCaseOpened();
        selectFacetValue(facet_value, facet);
        Assert.assertTrue(awaitForElementPresence(CategoryPathBreadcrumb) == true, "facet tag is not displayed");

    }
}