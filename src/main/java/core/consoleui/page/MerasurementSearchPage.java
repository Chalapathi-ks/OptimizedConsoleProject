package core.consoleui.page;

import core.consoleui.actions.ContentActions;
import lib.EnvironmentConfig;

import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.MEASUREMENT_SEARCH;

public class MerasurementSearchPage extends ContentActions {
    // Locator for the Enable button on Measurement Search page
    @FindBy(css = ".RCB-form-el.RCB-toggle.active")
    public FluentWebElement enableButton;

    @FindBy(css=".RCB-toggle-knob")
    public FluentWebElement disableButtonKnob;

    @FindBy(css=".primary-btn")
    public FluentWebElement enableButtonInPopUp;



    // Locator for the 'Add Attribute' button
    @FindBy(css = ".add-attribute")
    public FluentWebElement addAttributeButton;

    // Locator for the Attribute dropdown
    @FindBy(css = ".fields-dd.dm-fields-dd .RCB-select-arrow")
    public FluentWebElement attributeDropdown;

    // Locator for the Attribute search input
    @FindBy(css = ".RCB-dd-search input")
    public FluentWebElement attributeSearchInput;

    @FindBy(css=".disabled.add-attribute-section")
    public FluentWebElement addAttributeDisabledButton;

    @FindBy(xpath = "//*[@class='config-attribute-item']")
    public FluentList<FluentWebElement> configAttributeItemList;

    @FindBy(css=".config-attribute-item")
    public FluentList<FluentWebElement> aList;


    @FindBy(css=".RCB-list .tag-item")
    public FluentList<FluentWebElement> itemList;

    @FindBy(css=".RCB-inline-modal-body.RCB-align-left .RCB-list-item")
    public FluentList<FluentWebElement> attributeDropDownValuesList;

    // Locator for the Price option in the Attribute dropdown
    @FindBy(css = ".unx-qa-attribute-option-price")
    public FluentWebElement priceAttributeOption;

    // Locator for the Dimension dropdown
    @FindBy(css = ".dimension-dropdown .RCB-select-arrow")
    public FluentWebElement dimensionDropdown;

    @FindBy(css=".dimension-dropdown .RCB-list-item.dimension-box:nth-child(1)")
    public FluentWebElement dimensionOption1;
    // Locator for the Currency option in the Dimension dropdown
    @FindBy(css = ".unx-qa-dimension-option-currency")
    public FluentWebElement currencyOption;

    // Locator for the Unit dropdown
    @FindBy(css = ".unx-qa-unit-dropdown")
    public FluentWebElement unitDropdown;

    // Locator for the USD option in the Unit dropdown
    @FindBy(css = ".dimension-dropdown .RCB-align-left li:nth-child(1)")
    public FluentWebElement usdOption;

    // Locator for the Default Dimension dropdown
    @FindBy(css = ".unx-qa-default-dimension-dropdown")
    public FluentWebElement defaultDimensionDropdown;

    // Locator for the Default Unit dropdown
    @FindBy(css = ".unx-qa-default-unit-dropdown")
    public FluentWebElement defaultUnitDropdown;

    // Locator for the Save button
    @FindBy(css = ".refresh-button-mapping")
    public FluentWebElement saveButton;

    // Locator for the Success message
    @FindBy(css = ".unx-qa-success-message")
    public FluentWebElement successMessage;

    @FindBy(css = ".RCB-dropdown.dimension-dropdown .RCB-select-arrow")
    public FluentList<FluentWebElement> unitDropDown;


    public String getUrl() {
        return MEASUREMENT_SEARCH.getBaseUrl(EnvironmentConfig.getSiteId());
    }
} 