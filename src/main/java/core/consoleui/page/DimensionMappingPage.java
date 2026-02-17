package core.consoleui.page;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;
import core.consoleui.actions.ContentActions;
import lib.EnvironmentConfig;

import static lib.UrlMapper.DIMENSION_MAPPING;

public class DimensionMappingPage extends ContentActions {
    // Locator for all field rows
    @FindBy(css = ".dimension-cell.dm-cell-right")
    public FluentList<FluentWebElement> fieldRows;

    // Locator for field name in a row
    public String fieldNameSelector = ".dimension-label";

    // Locator for value dropdown in a row
    public String valueDropdownSelector = " .RCB-select-arrow";

    public String valueDropdownValue = ".RCB-dd-label";

    // Locator for value options in dropdown
    @FindBy(css = ".RCB-list-item.dm-dd-item ")
    public FluentList<FluentWebElement> valueOptions;

    // Locator for save button
    @FindBy(css = ".unx-qa-savechanges")
    public FluentWebElement saveButton;

    // Locator for success message
    @FindBy(css = ".toast-success")
    public FluentWebElement successMessage;

    @FindBy(css = ".RCB-dd-search-ip")
    public FluentWebElement searchBoxInput;

    public String getUrl() {
        awaitForPageToLoad();
        return DIMENSION_MAPPING.getBaseUrl(EnvironmentConfig.getSiteId());
    }
} 