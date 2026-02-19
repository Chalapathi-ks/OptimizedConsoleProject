package core.ui.page;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class contentRelevancePage  extends UiBase {

    @FindBy(css=".RCB-tab-content .pill-container:nth-child(2) .pill-highlight")
    public FluentWebElement synonymContainer;

    @FindBy(css=".RCB-tab-content .pill-container:nth-child(3) .pill-highlight")
    public FluentWebElement conceptsContainer;

    @FindBy(css=".RCB-tab-content .pill-container:nth-child(4) .pill-highlight")
    public FluentWebElement phrasesContainer;

    @FindBy(css=".RCB-tab-content .pill-container:nth-child(5) .pill-highlight")
    public FluentWebElement noStemContainer;
}
