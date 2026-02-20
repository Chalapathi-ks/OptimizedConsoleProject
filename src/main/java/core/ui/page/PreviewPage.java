package core.ui.page;

import lib.EnvironmentConfig;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.PREVIEW_PAGE;

public class PreviewPage extends UiBase {


    @FindBy(css=".RCB-btn-small")
    public FluentWebElement previewButtonTab;

    @FindBy(css= ".RCB-btn-large")
    public FluentWebElement applyConfigButton;

    @FindBy(css=".UNX-product-list .UNX-parent-image")
    public FluentList<FluentWebElement> productSnippet;

    @FindBy(css=".UNX-facet-item-d")
    public FluentList<FluentWebElement> filterSnippet;

    @FindBy(css=".UNX-result-info")
    public FluentWebElement productCount;

    @FindBy(css=".RCB-modal-close")
    public FluentWebElement templatePopupCloseButton;

    @FindBy(xpath="//*[@id='unbxdInput']")
    public FluentWebElement searchInputBox;


    @FindBy(css=".unbxd-as-sorted-suggestion")
    public FluentList<FluentWebElement> KeywordSuggestionList;


    @FindBy(css=".unbxd-as-insuggestion")
    public FluentList<FluentWebElement> suggestInfield;


    @FindBy(css=".unbxd-as-popular-product-image-container")
    public FluentList<FluentWebElement> popularProduct;

    @FindBy(id="searchBtn")
    public FluentWebElement searchIcon;


    @FindBy(css=".UNX-suggestion-p strong")
    public FluentWebElement searchedQueryName;

    @FindBy(id="didYouMeanWrapper")
    public FluentWebElement didYouMeanLink;

    @FindBy(css=".unbxd-as-maincontent")
    public FluentWebElement keywordSuggestionPresence;

    @FindBy(css=".unbxd-as-sidecontent")
    public FluentWebElement popularProductPresence;

    @FindBy(css=".UNX-selected-facet-header")
    public FluentWebElement selectedFilterHeader;

    @FindBy(css=".UNX-facet-text")
    public FluentList<FluentWebElement> FilterFacet;

    @FindBy(css=".UNX-product-title")
    public FluentList<FluentWebElement> ProductTitles;

    @FindBy(css=".UNX-selected-facets-inner")
    public FluentWebElement selectedFacetTag;

    @FindBy(css=".UNX-breadcrumbs-block")
    public FluentWebElement CategoryPathBreadcrumb;
    @FindBy(css=".UNX-selected-facets-wrap")
    public FluentList<FluentWebElement> SelectedFacetNames;

    public String Facets = ".UNX-facets-all button";

    public  String facetHeader = ".UNX-facet-header";

    public String facetValues = ".UNX-facets-all";

    public  String facetValueText =".UNX-facet-text";

    @FindBy(css=".UNX-facets-results-block .UNX-facet-item-d")
    public FluentList<FluentWebElement> allFilters;

    @FindBy(css=".UNX-facets-results-block .UNX-facet-item-d")
    public FluentWebElement allFilterFacet;
    @FindBy(css=".UNX-clear-facet")
    public FluentWebElement clearAllFacetTag;

    @FindBy(css=".UNX-selected-facet-header")
    public FluentList<FluentWebElement> facetTag;

    @FindBy(xpath="//*[@data-test-id='UNX_uFilter'])[1]")
    public FluentList<FluentWebElement> filterTags;

    public String websitePreviewPageLoader = "preview loader";
    public static By pageLoader = By.cssSelector(".progress-bar-contapp-body .page-loader");

    @FindBy(css=".noUi-connect")
    public FluentWebElement priceFacetSlider;

    @FindBy(css=".UNX-value-right")
    public FluentWebElement selectedPriceFacet;

    public String getUrl()
    {
        return  PREVIEW_PAGE.getBaseUrl(EnvironmentConfig.getSiteKey());
    }
    public String websitePreviewPopularProductPage = ".unbxd-as-sidecontent";
    public String websitePreviewKeywordSuggestionPage = ".unbxd-as-maincontent";
}
