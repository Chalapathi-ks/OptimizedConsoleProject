package core.consoleui.page;

import core.consoleui.actions.ContentActions;
import lib.EnvironmentConfig;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.CONCEPTS;

public class ConceptsPage extends ContentActions {

    public String keywordLocator = ".RCB-list .tag-item";
    public String conceptsCloseButton = ".close-icon-light";

    @FindBy(css=".unx-qa-addkeyword input")
    public FluentWebElement conceptsKeyWordInput;

    @FindBy(css=".RCB-list .tag-item")
    public FluentList<FluentWebElement> itemList;

    @FindBy(css=".RCB-list .tag-item")
    public FluentWebElement itemLists;

    @FindBy(css=".RCB-modal-content")
    public FluentWebElement deleteConfirmationWindow;


    public String getUrl()
    {
        awaitForPageToLoad();
        return CONCEPTS.getBaseUrl(EnvironmentConfig.getSiteId());

    }
}
