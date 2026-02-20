package core.consoleui.page;

import core.ui.page.UiBase;
import lib.EnvironmentConfig;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.*;

public class FieldPropertiesPage extends UiBase
{
    @FindBy(css=".RCB-notif-success.dimension-notif")
    public FluentWebElement dimensionSuccessMessage;

    @FindBy(xpath = "(//*[@class='promotion-filter-item']//following::*[@class='RCB-inline-modal-btn undefined'])[1]")
    public FluentWebElement facetableMerchFieldAttribute;

    @FindBy(css = ".field-container-data")
    public FluentWebElement fieldNameContainer;

    @FindBy(css = ".field-action-modal .RCB-btn-secondary")
    public FluentWebElement actionButton;

    @FindBy(css = "li.menu-item a:contains('Bulk Enable Features')")
    public FluentWebElement bulkEnableFeaturesOption;

    @FindBy(xpath = "//input[@type='checkbox']//following::*[@for='Merchandisable']")
    public FluentWebElement merchandisableCheckbox;

    @FindBy(xpath = "//input[@type='checkbox']//following::*[@for='Facetable']")
    public FluentWebElement facetableCheckbox;

    @FindBy(xpath = "//input[@type='checkbox']//following::*[@for='AutoSuggest']")
    public FluentWebElement autoSuggestCheckbox;

    @FindBy(xpath = "//input[@type='checkbox']//following::*[@for='Field Rule']")
    public FluentWebElement fieldRuleCheckbox;

    @FindBy(css = "button.RCB-btn-primary")
    public FluentWebElement bulkEnableButton;

    // Search related elements
    @FindBy(css = ".unx-qa-seach-Icon ")
    public FluentWebElement searchIcon;

    @FindBy(id = "csSearchQuery")
    public FluentWebElement searchInput;

    @FindBy(xpath = "(//*[@type='checkbox'])[position() > 1]")
    public FluentWebElement fieldCheckbox;

    // Edit and feature verification elements
    @FindBy(css = ".unx-qa-editicon")
    public FluentWebElement editIcon;

    @FindBy(css = ".RCB-btn-primary.btn.primary-btn")
    public FluentWebElement applyButton;

    @FindBy(css = ".features-tile")
    public FluentList<FluentWebElement> featureTiles;

    // Feature status in listing
    @FindBy(css = ".features-tiles")
    public FluentWebElement featureStatus;

    @FindBy(css = " .facet-name-wrapper")
    public FluentWebElement SelectedFacet;

    @FindBy(css = ".nodata-wrapper-dd.flex-center-column")
    public FluentWebElement manageAttributeCoundnotFind;

    @FindBy(css=".refresh-button-wrapper .unx-qa-refresh")
    public FluentWebElement refreshIcon;




    public String getUrl()
    {
        awaitForPageToLoad();
        return  SEARCH_FIELD_PROPERTIES_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }
}
