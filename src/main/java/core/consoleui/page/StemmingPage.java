package core.consoleui.page;

import core.ui.page.UiBase;
import lib.EnvironmentConfig;
import static lib.UrlMapper.STEMMING;
import lib.compat.Page;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class StemmingPage extends UiBase {

    @FindBy(css = ".unx-qa-input-keyword2 input")
    private FluentWebElement keywordInput;

    @FindBy(css = ".unx-qa-btn-save")
    private FluentWebElement saveButton;

    @FindBy(css = ".unx-qa-btn-delete")
    private FluentWebElement deleteButton;

    @FindBy(css = ".unx-qa-btn-confirm-delete")
    private FluentWebElement confirmDeleteButton;

    @FindBy(css = ".notification-outer-wrapper .unx-qa-toastsucess")
    private FluentWebElement successMessage;

    public String getUrl()
    {
        awaitForPageToLoad();
        return STEMMING.getBaseUrl(EnvironmentConfig.getSiteId());

    }
    
    public FluentWebElement getKeywordInput() {
        return keywordInput;
    }

    public FluentWebElement getSaveButton() {
        return saveButton;
    }

    public FluentWebElement getDeleteButton() {
        return deleteButton;
    }

    public FluentWebElement getConfirmDeleteButton() {
        return confirmDeleteButton;
    }

    public FluentWebElement getSuccessMessage() {
        return successMessage;
    }
}
