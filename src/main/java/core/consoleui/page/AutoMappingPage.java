package core.consoleui.page;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import core.ui.page.UiBase;


public class AutoMappingPage extends UiBase
{

    public static By mapCatalogButtons = By.cssSelector(".map-catalog-button");
    public static By setUpSearchButtons = By.cssSelector(".setup-search-btn");
    public static By mapList1 = By.cssSelector(".dimension-cell");

    public String mappingPageLoader = "Dimension Mapping page loader";
    public String clearMappingTab= ".RCB-clear-selected";

    @FindBy(css=".RCB-select-arrow")
    public FluentWebElement previewTemplatePopupdropdown;

    @FindBy(css=".map-catalog-button")
    public FluentWebElement mapCatalogButton;

    @FindBy(css=".RCB-inline-modal-btn .RCB-dd-label")
    public FluentWebElement dimensionMapInput;

    @FindBy(css=".flex-one .dimension-label")
    public FluentWebElement dimensionMapFieldName;

    @FindBy(css=".dimension-cell")
    public FluentList<FluentWebElement> mapList;

    @FindBy(css=".setup-search-btn")
    public FluentWebElement setUpSearchButton;

    @FindBy(css=".dm-server-list .RCB-list-item")
    public FluentList<FluentWebElement> mapDropDownList;

    @FindBy(css=".RCB-dd-search-ip")
    public  FluentWebElement mapSearch;

    @FindBy(css=".modal-footer .RCB-btn-primary")
    public FluentWebElement saveButton;

    @FindBy(css=".clip-content")
    public FluentList<FluentWebElement> dropDownList;

    @FindBy(css=".flex-one")
    public FluentList<FluentWebElement> templatemMappingList;

}
