package core.consoleui.page;

import core.ui.page.UnbxdCommonPage;
import lib.EnvironmentConfig;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.SEARCH_AUTOSUGGEST_PAGE;

public class searchAutosuggestPage extends UnbxdCommonPage {

    public String getUrl()
    {
        awaitForPageToLoad();
        return SEARCH_AUTOSUGGEST_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }
    @FindBy(xpath = "(//*[contains(text(),'Keyword suggestions')]//following::*[@class='select-field'])[1]")
    public FluentWebElement SuggestionAddField;

    @FindBy(css=".display-fields .title-caret")
    public FluentWebElement selectfieldOption;

    @FindBy(xpath="//*[@class='RCB-list-item '] | //*[@class='field-name clip-content']")
    public FluentList<FluentWebElement> dropDownList;

    @FindBy(css=".save-fields-btn")
    public FluentWebElement saveSuggestionField;

    @FindBy(css=".save-field-btn")
    public  FluentWebElement saveAutosuggestField;

    @FindBy(css=".RCB-notif-success")
    public  FluentWebElement autosuggestSuccessMessage;

    @FindBy(css=".publish-container .RCB-btn-medium")
    public  FluentWebElement autosuggestPublishButton;

    @FindBy(css=".push-bottom-20")
    public  FluentWebElement autosuggestPublishSuccessMessage;

    @FindBy(xpath="//*[@class='RCB-dd-search-ip'] | //*[@id='fieldsInput']")
    public  FluentWebElement autosuggestSearch;

    @FindBy(css=".display-fields")
    public  FluentList<FluentWebElement> autosuggestDisplayAttributeList;

    @FindBy(xpath="//*[contains(text(),'catalogId')]//following-sibling::*[@class='delete-field']")
    public  FluentWebElement deleteAutosuggestField;




}
