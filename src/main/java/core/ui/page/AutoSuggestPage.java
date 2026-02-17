package core.ui.page;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class AutoSuggestPage extends UiBase {

    public static By skipQueryButton = By.cssSelector(".skip-step");


    @FindBy(css=".skip-step")
    public FluentWebElement skipQuery;

    @FindBy(css=".auto-page-content .RCB-btn-small")
    public FluentWebElement autoSuggestCustomiseButton;

    @FindBy(css=".configure-fields-content .RCB-accordian-item:nth-child(1) .RCB-accordian-title")
    public FluentWebElement keywordSuggestionSection;

    @FindBy(css=".configure-fields-content .RCB-accordian-item:nth-child(2) .RCB-accordian-title")
    public FluentWebElement infieldSuggestionSection;

    @FindBy(css=".configure-fields-content .RCB-accordian-item:nth-child(3) .RCB-accordian-title")
    public FluentWebElement topSearchQuerySection;

    @FindBy(css=".configure-fields-content .RCB-accordian-item:nth-child(4) .RCB-accordian-title")
    public FluentWebElement promotedSuggestionSection;

    @FindBy(css=".configure-fields-content .RCB-accordian-item:nth-child(5) .RCB-accordian-title")
    public FluentWebElement popularProductsSection;

    @FindBy(css=".display-fields-container .display-fields")
    public FluentList<FluentWebElement> predefinedFieldsList;

}
