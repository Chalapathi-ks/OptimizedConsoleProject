package core.consoleui.page;

import core.ui.page.UiBase;
import lib.EnvironmentConfig;
import static lib.UrlMapper.PROMOTEDSUGGESTIONS_PAGE;
import lib.compat.FluentWebElement;
import lib.compat.FluentList;
import org.openqa.selenium.support.FindBy;

public class PromotedSuggestionsPage extends UiBase {

    public String getUrl()
    {
        return  PROMOTEDSUGGESTIONS_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }

    @FindBy(css = ".add-button")
    public FluentWebElement addPromotedSuggestionButton;

    // Modal elements
    @FindBy(css = ".RCB-modal-title")
    public FluentWebElement modalTitle;

    @FindBy(css = ".tags-with-count input")
    public FluentWebElement termsInput;

    @FindBy(css = "#dateInput")
    public FluentWebElement dateInput;

    @FindBy(css = ".time-zones-dd .RCB-inline-modal-btn")
    public FluentWebElement timeZoneDropdown;

    @FindBy(css = ".RCB-inline-modal input[type='text']")
    public FluentWebElement timeZoneSearchBox;

    @FindBy(css = ".RCB-select-arrow")
    public FluentWebElement timeZoneDropdownArrow;

    @FindBy(css = ".calendar-apply .RCB-btn-primary")
    public FluentWebElement calendarApplyButton;

    @FindBy(css = ".modal-main-footer .RCB-btn-primary")
    public FluentWebElement modalAddButton;

    @FindBy(css = ".modal-main-footer .cancel-button")
    public FluentWebElement modalCancelButton;

    // Table row elements
    @FindBy(css = ".promoted-names")
    public FluentList<FluentWebElement> promotedNames;

    @FindBy(css = ".unx-qa-editicon")
    public FluentList<FluentWebElement> editIcons;

    @FindBy(css = ".unx-qa-deleteicon")
    public FluentList<FluentWebElement> deleteIcons;

    // Success toast
    @FindBy(css = ".toast.success")
    public FluentWebElement successToast;

    // Sub-header
    @FindBy(css = ".feature-heading")
    public FluentWebElement subHeader;

    @FindBy(css = ".tags-with-count")
    public FluentWebElement termsContainer;

    @FindBy(css = ".tags-with-count.tag-show input.tag-input.tag-show")
    public FluentWebElement visibleTermsInput;

    @FindBy(css = ".custom-date")
    public FluentWebElement customDate;

    @FindBy(css = ".react-calendar__tile--now")
    public FluentWebElement todayTile;

    @FindBy(css = "input#csSearchQuery")
    public FluentWebElement searchInput;

    @FindBy(css = ".unx-qa-seach-Icon")
    public FluentWebElement searchIcon;

    @FindBy(css = ".unx-icon-x ")
    public FluentWebElement clearSearchIcon;


    @FindBy(css = ".modal-main-footer .RCB-btn-primary")
    public FluentWebElement addButton;
}