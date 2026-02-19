package core.ui.page;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class FacetableFieldsPage extends RelevancyPage {

    public static By setUpSearch = By.cssSelector(".setup-search-btn");

    public String setUpSearchName = "Setup search";



    @FindBy(css = ".manual-setup-btn")
    public FluentWebElement manualSetUpButton;

    public String relevancePageLoader = "Relevancy page loader";
    public String applyButton = "Apply button";
    public static By relevancyPageLoader = By.cssSelector(".page-loader");
    public static By relevancyPageLoader1 = By.cssSelector(".mask-loader");
    public static By facetSection = By.cssSelector(".RCB-tab-title:nth-child(2)");

    @FindBy(css = ".RCB-tab-title:nth-child(2)")
    public FluentWebElement facetableFieldGroup;


    @FindBy(css = ".RCB-tab-title:nth-child(1)")
    public FluentWebElement searchableFieldGroup;

    @FindBy(css = ".RCB-tab-title:nth-child(3)")
    public FluentWebElement dictionaryGroup;

    @FindBy(css=".RCB-tab-title:nth-child(4)")
    public FluentWebElement autoSuggestGroup;

    @FindBy(css=".RCB-modal-close")
    public FluentWebElement facetWindowCloseButton;

    @FindBy(css = ".pill-container b")
    public FluentWebElement facetCount;

    @FindBy(css=".tbl-title-1.flex-vertical-center")
    public FluentList<FluentWebElement> facetList;

    @FindBy(css = ".RCB-select-arrow")
    public FluentWebElement paginationDropDown;

    @FindBy(css = ".RCB-inline-modal-body .RCB-list li")
    public FluentList<FluentWebElement> pageCountList;

    @FindBy(css=".add-new-button")
    public FluentWebElement createNewFacetTab;

    @FindBy(xpath="//*[@class='display-name']//input|//input[@id='display_name']")
    public FluentWebElement displayNameInput;

    @FindBy(xpath="(//*[@label='Facet length'])|(//*[@name='facet_length'])")
    public FluentWebElement facetLengthInput;

    @FindBy(name="facet_type")
    public FluentWebElement editWindowfacetType;

    @FindBy(css=".new-facet-form .RCB-dropdown:nth-child(7) .RCB-select-arrow")
    public FluentWebElement rangeFacetEnableDisableDropdown;

    @FindBy(css=".RCB-form-el.RCB-toggle  ")
    public FluentWebElement facetEnableToggle;

    @FindBy(css=".RCB-form-el.RCB-toggle.active")
    public FluentWebElement activeToggle;
    @FindBy(css=".new-facet-form .RCB-dropdown:nth-child(1) .RCB-select-arrow")
    public FluentWebElement facetAttributeDropDown;

    @FindBy(css=".new-facet-form .RCB-dropdown:nth-child(1) .RCB-inline-modal-btn")
    public FluentWebElement attributeInput;

    @FindBy(css=".new-facet-form .RCB-dropdown:nth-child(6) .RCB-inline-modal-btn")
    public FluentWebElement facetEnableDisableInput;

    @FindBy(css=".new-facet-form .RCB-dropdown:nth-child(7) .RCB-inline-modal-btn")
    public FluentWebElement rangeFacetEnableDisableInput;

    @FindBy(css=".RCB-selection-wrapper")
    public FluentWebElement sortOrderDropDown;

    @FindBy(css=".new-facet-form .RCB-dropdown:nth-child(3) .RCB-inline-modal-btn span")
    public FluentWebElement sortOrderInput;


    @FindBy(css=".RCB-dd-search-ip")
    public FluentWebElement attributeInputBox;

    @FindBy(css=".RCB-align-left .RCB-list-item")
    public FluentList<FluentWebElement> attributeList;

    @FindBy(xpath="(//*[@name='range_start'] | //*[@label='Range Start'])")
    public FluentWebElement startRangeInput;

    @FindBy(xpath = "(//*[@name='range_end'] | //*[@label='Range End'])")
    public FluentWebElement endRangeInput;

    @FindBy(xpath = "(//*[@name='range_gap'] | //*[@label='Range Gap'])")
    public FluentWebElement rangeGapInput;

    @FindBy(css=".new-facet-container .RCB-btn")
    public FluentWebElement saveFacetButton;

    @FindBy(xpath="//*[contains(text(),'Update facet')]")
    public FluentWebElement updateFacet;
    @FindBy(css=".search-input-box .RCB-form-el")
    public FluentWebElement facetSearchBox;

    @FindBy(css=".unx-icon-trash-2")
    public FluentWebElement deleteFacetIcon;

    @FindBy(css=".confirm-msg")
    public FluentWebElement deleteConfirmationTab;

    @FindBy(css=".facet-notifier-container .RCB-notif-success")
    public FluentWebElement facetSuccessMessage;

    @FindBy(css=".unx-icon-edit,.edit-icon-dark")
    public FluentWebElement editFacetIcon;

    @FindBy(css=".new-facet-container .RCB-modal-body")
    public FluentWebElement editWindow;

    @FindBy(css=".unx-qa-seach-Icon ")
    public FluentWebElement searchIcon;
    @FindBy(css=".RCB-btn-small.btn.primary-btn ")
    public FluentWebElement deleteYes;

    @FindBy(css=".skip-step")
    public FluentWebElement skipQueryDataButton;

    public String facetState =".facet-status";
    public String attributeName = ".field-name:nth-child(2)";
    public String displayName = ".field-name:nth-child(3)";
    public String facetType = ".list-item:nth-child(4)";


    @FindBy(css=".unx-icon-filter")
    public FluentWebElement facetFilterIcon;

    @FindBy(xpath="//*[@id='text']//following::label[@for='text']")
    public FluentWebElement textFilterOption;

    @FindBy(xpath="//*[@id='enabled']//following::label[@for='enabled']")
    public FluentWebElement enabledFilterOption;

    @FindBy(xpath="//*[@id='range']//following::label[@for='range']")
    public FluentWebElement rangeFilterOption;

    @FindBy(xpath="//*[@id='disabled']//following::label[@for='disabled']")
    public FluentWebElement disabledFilterOption;

    @FindBy(xpath="//span[text()[normalize-space()='Type:']]/following-sibling::span[@class='tbl-title-2']")
    public FluentList<FluentWebElement> facetTypeTextRange;

    @FindBy(css=".RCB-list.selected-filters-list")
    public FluentWebElement typeFacetHeader;

    @FindBy(css=".apply-filters")
    public FluentWebElement applyFilterButton;




}












