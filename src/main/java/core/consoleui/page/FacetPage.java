package core.consoleui.page;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class FacetPage extends CampaignCreationPage {



    //public String displayName = "//span[normalize-space()='Display name:']";
    public String toggle = ".RCB-toggle  ";

    @FindBy(xpath=" //div[@class=\"RCB-form-el RCB-toggle  \"]")
    public FluentList<FluentWebElement> DisableToggle;

    @FindBy(css=".tbl-title-1 ")
    public FluentList<FluentWebElement> displayName;

    @FindBy(css=".facets-edit-icon")
    public FluentWebElement facetEdit;

    @FindBy(css=".facet-button.add-new-button")
    public FluentWebElement clickOnAddFacets;

    @FindBy(xpath="(//*[@class='unbxd-pill success'])|(//*[@class='RCB-form-el RCB-toggle active ']) ")
    public FluentList<FluentWebElement>  activeToggle;

    @FindBy(xpath="(//*[@class='RCB-form-el RCB-toggle active ']) ")
    public FluentList<FluentWebElement>  activeTogle;
    @FindBy(css=".merch-add-facets-header .RCB-btn-small")
    public FluentWebElement facetCloseButton;

    @FindBy(css=".disable-all-facets")
    public FluentWebElement disableAllFacet;

    @FindBy(css=".sc-jsEegq.eMxRVC.rdt_TableRow")
    public FluentList<FluentWebElement> facetList;

    @FindBy(css=".added-facets-table .RCB-table .RCB-tr")
    public FluentList<FluentWebElement> selectedFacetList;

    @FindBy(css=".facet-item-header")
    public FluentList<FluentWebElement> previewFacetListNames;

    @FindBy(css=".merch-facet-range-start .RCB-form-el")
    public FluentWebElement previewFacetRange;

    @FindBy(css=".set-rule-title .edit-icon")
    public FluentWebElement ConfigureFacetEdit;

    @FindBy(css=".update-facets-config-container .RCB-btn-small")
    public FluentWebElement ClickonOk;

    @FindBy(css=".start-value")
    public FluentWebElement facetPriceRange;

    @FindBy(css=".search-input .RCB-form-el")
    public FluentWebElement searchInputBox;

    @FindBy(css=".unx-qa-seach-Icon ")
    public FluentWebElement searchIcon;

    @FindBy(css=".rerank-text.preview-btn")
    public FluentList<FluentWebElement> reRank;

    @FindBy(xpath="//*[@type='number']")
    public FluentWebElement rankPosition;

    @FindBy(xpath="//*[contains(text(),'Update')]")
    public FluentWebElement updateRank;

    @FindBy(css=".ranking")
    public FluentWebElement afterUpdateRanking;

    @FindBy(css=".facet-item-header")
    public FluentList<FluentWebElement> previewFacetHeader;

    @FindBy(css=".range-value.start-value")
    public FluentWebElement rangeStart_value;

    @FindBy(css=".range-value.end-value")
    public FluentWebElement rangeEnd_value;
    @FindBy(css=".preview-btn.facet-expand-button")
    public FluentWebElement viewDetails;

    @FindBy(css=".facet-details-intelligence")
    public FluentWebElement facetDetails;

    @FindBy(css=".facet-details-config")
    public FluentWebElement facetDetailsConfig;

    public java.util.List<org.openqa.selenium.WebElement> getFacetLengthListByHeader(String headerText) {
        String xpath = String.format("//div[@class='facet-item-preview-header'][h6[@class='facet-item-header' and text()='%s']]//following::*[@class='facet-item']", headerText);
        return getDriver().findElements(org.openqa.selenium.By.xpath(xpath));
    }

    // Method to get elements using Selenium WebDriver and By.xpath
    public java.util.List<org.openqa.selenium.WebElement> getFacetElementsByDriverXpath(String xpath) {
        return getDriver().findElements(org.openqa.selenium.By.xpath(xpath));
    }

}
