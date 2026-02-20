package core.consoleui.page;

import core.ui.page.UiBase;
import lib.EnvironmentConfig;
import static lib.UrlMapper.PHRASES;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class PhrasesPage extends UiBase {
    @FindBy(css = ".unx-qa-add-phrases")
    public FluentWebElement addPhrasesButton;

    @FindBy(css = ".unx-qa-phrase-name")
    public FluentWebElement phraseNameInput;

    @FindBy(css = ".unx-qa-proceed")
    public FluentWebElement proceedButton;

    @FindBy(css = ".unx-qa-success-message")
    public FluentWebElement successMessage;

    public String getUrl() {
        awaitForPageToLoad();
        return PHRASES.getBaseUrl(EnvironmentConfig.getSiteId());
    }
} 