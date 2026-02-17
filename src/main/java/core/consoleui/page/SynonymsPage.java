package core.consoleui.page;

import core.ui.page.UiBase;
import lib.EnvironmentConfig;
import static lib.UrlMapper.SYNONYMS;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class SynonymsPage extends UiBase {

    public   String keywordCssLocator=".search-keyword-container input";
    public  String unidirectionalCssLocator="td:nth-child(2) .tag-item:nth-child(2)";
    public  String biDirectionalCssLocator="td:nth-child(3) .tag-item:nth-child(2)";
    public String unidirection=".unx-qa-edit-onewaysynonym";
    public String bidirection = ".unx-qa-edit-twowaysynonym";
    public  String deleteCssLocator=".menu-icon-active";
    public String keyWord = ".unx-qa-edit-keyword";
    public String stemmedWord = "td:nth-child(2) .RCB-form-el-cont input";
    public String deleteButtonCssLocator=".unx-qa-deleteicon";

    @FindBy(css = ".RCB-modal-body")
    public FluentWebElement deleteConfirmationWindow;

    @FindBy(css = ".unx-qa-deleteicon")
    public FluentWebElement deleteButton;

    @FindBy(css=".RCB-modal-body .RCB-btn-primary")
    public FluentWebElement deleteYesSynonymButton;

    @FindBy(css=".RCB-inline-modal-btn .RCB-btn-small")
    public FluentWebElement synonymCreationButton;

    @FindBy(css=".RCB-align-right")
    public FluentWebElement synonymCreateWindow;

    @FindBy(css=".unx-qa-addkeyword input")
    public FluentWebElement synonymInput;

    @FindBy(css=".unx-qa-input-keyword1 input")
    public FluentWebElement unidirectionalInput;

    @FindBy(css=".unx-qa-input-keyword2 input")
    public FluentWebElement stemmedWordInput;

    @FindBy(css="td:nth-child(2) .RCB-form-el-cont input")
    public FluentWebElement stemmedWordEdit;

    @FindBy(css=".RCB-align-right div:nth-child(2) .tags-container")
    public FluentWebElement unidirections;

    @FindBy(css=".RCB-align-right div:nth-child(3) .tags-container")
    public FluentWebElement bidirections;

    @FindBy(css=".search-keyword-container input")
    public FluentWebElement synonymKeyWordEdit;

    @FindBy(css=".unx-qa-edit-onewaysynonym input")
    public FluentWebElement unidirectionalInputEdit;

    @FindBy(css=".unx-qa-edit-keyword input")
    public FluentWebElement keyWordInputEdit;

    @FindBy(css=".unx-qa-edit-twowaysynonym input")
    public FluentWebElement bidirectionalInputEdit;

    @FindBy(css=".RCB-align-right div:nth-child(3) input")
    public FluentWebElement bidirectionalInput;

    @FindBy(css=".align-right .RCB-btn-small")
    public FluentWebElement createSynonymButton;

    @FindBy(css=".unx-qa-seach-input input")
    public FluentWebElement searchBoxOpen;

    @FindBy(css=".unx-qa-seach-Icon")
    public FluentWebElement synonymsSearchBox;

    @FindBy(css=".RCB-inline-modal-btn .RCB-select-summary")
    public FluentWebElement countDropDown;

    @FindBy(css=".RCB-align-left .RCB-list li:nth-child(4)")
    public FluentWebElement selectCount100;

    @FindBy(css=".page-loader")
    public FluentWebElement PageLoader;

    @FindBy(css=".rdt_TableRow")
    public FluentWebElement synonymsLists;

    @FindBy(css=".rdt_TableBody .rdt_TableRow")
    public FluentList<FluentWebElement> createdItemList;

    @FindBy(css=".unx-qa-savechanges")
    public FluentWebElement saveChangesButton;

    public String getUrl()
    {
        awaitForPageToLoad();
        return SYNONYMS.getBaseUrl(EnvironmentConfig.getSiteId());
    }
}
