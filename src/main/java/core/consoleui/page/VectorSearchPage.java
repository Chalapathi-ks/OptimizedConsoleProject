package core.consoleui.page;
import core.consoleui.actions.ContentActions;
import lib.EnvironmentConfig;
import static lib.UrlMapper.VECTOR_SEARCH;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class VectorSearchPage extends ContentActions {
    // Toggle button for enabling/disabling vector search
    @FindBy(css = ".vector-toggle .RCB-toggle-knob")
    public FluentWebElement vectorToggleKnob;

    @FindBy(css = ".vector-toggle .RCB-toggle.active")
    public FluentWebElement vectorToggleActive;

    @FindBy(css=".RCB-toggle-disable")
    public FluentWebElement vectorToggleDisabled;

    // Hybrid search checkbox and input
    @FindBy(xpath = "//*[contains(text(),'Hybrid search')]//parent::*//child::input")
    public FluentWebElement hybridSearchCheckbox;

    @FindBy(xpath = "//*[contains(text(),'Hybrid search')]//parent::*//following-sibling::*//child::input")
    public FluentWebElement hybridSearchInput;

    // Fallback mode checkbox and input
    @FindBy(xpath = "//*[contains(text(),'Fallback mode')]//parent::*//child::input")
    public FluentWebElement fallbackModeCheckbox;

    @FindBy(xpath = " //*[contains(text(),'Fallback mode')]//parent::*//following-sibling::*//child::input")
    public FluentWebElement fallbackModeInput;

    // Save button
    @FindBy(css = ".action-buttons .RCB-btn-small")
    public FluentWebElement saveButton;

    public String getUrl() {
        // Use the VECTOR_SEARCH enum and pass the siteId as argument
        return VECTOR_SEARCH.getBaseUrl(EnvironmentConfig.getSiteId());
    }
} 