package core.consoleui.page;

import core.consoleui.actions.ContentActions;
import lib.EnvironmentConfig;
import lib.UrlMapper;
import static lib.UrlMapper.PRODUCT_CARD_MAPPING;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class productCardMappingPage extends ContentActions {
    // Locator for all field rows
    @FindBy(css = ".pcm-row")
    public FluentList<FluentWebElement> fieldRows;

    @FindBy(xpath = "//*[@class='title-block']//*[contains(text(),'Variant field')]")
    public FluentWebElement varaientTitleLable;

    @FindBy(xpath = "//*[@class='imageUrl-block']//*[contains(text(),'Variant field')]")
    public FluentWebElement varaientImageLable;

    @FindBy(xpath = "//*[@class='price-block']//*[contains(text(),'Variant field')]")
    public FluentWebElement varaientPriceLable;

    @FindBy(css = ".title-block .long-labels")
    public FluentWebElement TitleLable;

    @FindBy(css = ".price-block .long-labels")
    public FluentWebElement priceLabel;

    @FindBy(css = ".title-block .d-contents:nth-child(1) .RCB-inline-modal-btn")
    public FluentWebElement parentTitleDropdown;

    @FindBy(css = ".price-block .d-contents:nth-child(1) .RCB-inline-modal-btn")
    public FluentWebElement parentPriceDropdown;

    @FindBy(css = ".title-block .d-contents:nth-child(2) .RCB-inline-modal-btn")
    public FluentWebElement varientTitleDropdown;

    @FindBy(css = ".price-block .d-contents:nth-child(2) .RCB-inline-modal-btn")
    public FluentWebElement varientPriceDropdown;

    // Locator for field name in a row
    public String fieldNameSelector = ".pcm-label";

    // Locator for variant field dropdown in a row
    public String variantFieldDropdownSelector = ".pcm-variant-dropdown";

    // Locator for value options in dropdown
    @FindBy(css = ".dm-server-list .RCB-list-item")
    public FluentList<FluentWebElement> valueOptions;

    // Locator for save button
    @FindBy(css = ".unx-qa-savechanges")
    public FluentWebElement saveButton;

    @FindBy(css=".RCB-dd-search input")
    public FluentWebElement searchBoxInput;

    // Locator for success message
    @FindBy(css = ".unx-qa-success-message, .RCB-toaster")
    public FluentWebElement successMessage;

    // Locators for "Add additional fields for preview" section
    @FindBy(css = ".tags-drop-block .fields-dd .RCB-inline-modal-btn")
    public FluentWebElement additionalFieldsDropdown;

    @FindBy(css = ".tags-drop-block .RCB-inline-modal-body .RCB-checkbox-wrapper input[type='checkbox']")
    public FluentList<FluentWebElement> additionalFieldsOptions;

    @FindBy(css = ".summary-tags-wrapper .merch-tag-box")
    public FluentList<FluentWebElement> selectedAdditionalFields;

    @FindBy(css = ".summary-tags-wrapper .merch-tag-box .RCB-clear-icon")
    public FluentList<FluentWebElement> removeAdditionalFieldButtons;

    public String getUrl()
    {
        return PRODUCT_CARD_MAPPING.getBaseUrl(EnvironmentConfig.getSiteId());
    }
} 