package lib.entities;

public class FieldRuleEntity {

    private String page;
    private String facet;
    private String facetValue;

    public FieldRuleEntity(String page, String facet, String facetValue)
    {
        this.page=page;
        this.facet=facet;
        this.facetValue=facetValue;
    }

    public FieldRuleEntity()
    {

    }

    public String getPage()
    {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setFacet(String facet) {
        this.facet = facet;
    }

    public void setFacetValue(String facetValue) {
        this.facetValue = facetValue;
    }

    public String getFacet()
    {
        return facet;
    }
    public String getFacetValue()
    {
        return facetValue;
    }
}
