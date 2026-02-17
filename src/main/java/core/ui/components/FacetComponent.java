package core.ui.components;

import core.ui.actions.FacetableFieldsActions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.testng.Assert;

public class FacetComponent extends FacetableFieldsActions {


    public void updateTextFacet(String facetLength, String facetSortOrder) throws InterruptedException {
        updateFacetLength(facetLength);
        updateFacetSortOrder(facetSortOrder);
        saveFacet();
        Assert.assertFalse(checkElementPresence(saveFacetButton), "saving of Facet  is failed");

    }

    public void updateRangeFacet(String rangeStart, String rangeEnd, String rangeGap)
    {
        startRangeInput.fill().with(rangeStart);
        endRangeInput.fill().with(rangeEnd);
        rangeGapInput.fill().with(rangeGap);
        saveFacet();
        Assert.assertFalse(checkElementPresence(saveFacetButton), "saving of Facet  is failed");
    }

    public void enableFacetByName(String name,String state) throws InterruptedException {
        FluentWebElement facet=getFacetUsingDisplayName(name);
        openFacetEditWindow(facet);
        updateFacetState(state);
        saveFacet();
    }
/*
    public void enableFacetByPosition(int position,String state){
        FluentWebElement facet=getFacetByPosition(position);
        openFacetEditWindow(facet);
        updateFacetState(state);
        saveFacet();
    }*/

    public String getFacetState(String name){
        FluentWebElement facet=getFacetUsingDisplayName(name);
        String state = facet.findFirst(facetState).getText().trim();
        return state;
    }


}
