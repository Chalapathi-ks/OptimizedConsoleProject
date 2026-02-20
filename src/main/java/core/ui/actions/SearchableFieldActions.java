package core.ui.actions;

import core.ui.page.SearchableFieldsPage;
import lib.compat.FluentWebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchableFieldActions extends SearchableFieldsPage {

    FacetableFieldsActions facetableFieldsActions;

    public List<String> getAttributes()
    {
        List<String> listOfFeaturedFields = new ArrayList<>();

        for(int i = 0; i < attributeList.size(); i++)
        {
            listOfFeaturedFields.add(attributeList.get(i).find("td:nth-child(1)").getText().trim());
            System.out.println("attribute fields are" +listOfFeaturedFields);
        }
        return listOfFeaturedFields;
    }


    public FluentWebElement getAttributeUsingDisplayName(String name) throws InterruptedException {
        Thread.sleep(15000);
        awaitForElementPresence(attributeSearchBox);
        //facetSearchBox.clear();
        attributeSearchBox.fill().with(name);
        ThreadWait();
        //facetableFieldsActions.selectPageCount(pageCount);
        ThreadWait();
        for(int i=0 ; i<attributeList.size() ; i++)
        {
            if(attributeList.get(i).find(attributeName).getText().trim().equalsIgnoreCase(name))
                return attributeList.get(i);
        }
        return null;
    }

    public String getProductCoveragePercentage(FluentWebElement element)
    {
        String percentage = element.find(productCoveragePercentage).getText().trim();
        return percentage;
    }

    public String getSearchWeight(FluentWebElement element)
    {
        String searchWeight = element.find(searchWeightText).getText().trim();
        return searchWeight;
    }

    public void selectSearchWeightFromDropdown(String searchWeight) throws InterruptedException {
        awaitForElementPresence(searchWeighDropdown);
        ThreadWait();
        await();
        click(searchWeighDropdown);
        selectValueBYMatchingText(searchWeight);
    }

    public void applyAiRecommendation() throws InterruptedException {
        awaitForElementPresence(applyAiRecommendedTab);
        click(applyAiRecommendedTab);
//        ThreadWait();
//        await();
        awaitForElementPresence(applyAiRecommendationPopup);
        click(applyAiRecommendationPopup);
    }

    public void saveChanges() throws InterruptedException {
         ThreadWait();
        awaitForElementPresence(saveFields);
        click(saveFields);
    }


}
