package core.ui.page;

import lib.EnvironmentConfig;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.MANAGE_BROWSE_PAGE;

public class SearchableFieldsPage extends UiBase{

    public String attributeName = ".RCB-table td";
    public String productCoveragePercentage = ".search-product-coverage .progress-txt";
    public String searchWeightText = ".search-weight-dd span";
    public static By applyAiRecButton = By.cssSelector(".apply-save-searchable span");
    //public static By applyConfigButton = By.cssSelector(".RCB-btn-large");

    @FindBy(css=".RCB-table  .RCB-th:nth-child(2)")
    public FluentWebElement productCoverageText;

    @FindBy(css=".RCB-btn-large")
    public FluentWebElement applyAiRec;

    @FindBy(css=".RCB-table .RCB-tr")
    public FluentList<FluentWebElement> attributeList;

    @FindBy(css=".search-input-box input")
    public FluentWebElement attributeSearchBox;

    @FindBy(css=".search-weight-dd span")
    public FluentWebElement searchWeighDropdown;

    @FindBy(css=".apply-save-searchable span")
    public FluentWebElement applyAiRecommendedTab;

    @FindBy(css=".RCB-modal-content .RCB-btn-small")
    public FluentWebElement applyAiRecommendationPopup;

    @FindBy(css=".apply-save-searchable .RCB-btn-small")
    public FluentWebElement saveFields;

    public static By searchableFieldIdentifier = By.cssSelector(".pill-container");

    public String getUrl()
    {
        return MANAGE_BROWSE_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }

}
