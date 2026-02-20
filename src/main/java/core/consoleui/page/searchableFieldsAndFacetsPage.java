package core.consoleui.page;

import core.ui.page.UnbxdCommonPage;
import lib.EnvironmentConfig;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.MANAGE_SEARCH_PAGE;
import static lib.UrlMapper.SEARCH_FACETS_FIELDS;

public class searchableFieldsAndFacetsPage extends UnbxdCommonPage {

    public String getUrl()
    {
        awaitForPageToLoad();
        threadWait();
        return MANAGE_SEARCH_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }


    @FindBy(css=".primary-nav .navbar-item:nth-child(2)")
    public FluentWebElement searchManageFacetSection;

    @FindBy(css=".apply-save-searchable .RCB-btn-small:nth-child(2)")
    public FluentWebElement saveButton;

    @FindBy(css=".apply-save-searchable .RCB-btn-small:nth-child(1)")
    public FluentWebElement refreshAiRecommendations;

    @FindBy(css=".test-recc-weight")
    public FluentWebElement applyAndSaveAiRecommendedSearchWeight;

    @FindBy(css=".search-weight-dd span .RCB-select-arrow")
    public FluentWebElement openDropDownArrow;

    @FindBy(css=".RCB-notif-success")
    public  FluentWebElement searchableFieldSuccessMessage;

    @FindBy(css=".fn-edit-accepted-container .fn-edit-info-wrapper")
    public  FluentWebElement updateMessageNotification;


}
