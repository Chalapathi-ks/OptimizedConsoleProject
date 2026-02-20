package core.consoleui.page;

import lib.EnvironmentConfig;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.SEGMENT_PAGE;


public class SegmentPage extends ConsoleCommonPage  {


    @FindBy(css=".RCB-checkbox-wrapper,.RCB-list-item,.browse-dd-item")
    public FluentList<FluentWebElement> typeValueList;

    public String segmentInputBox =".RCB-dd-search-ip";


    @FindBy(xpath="//*[text()=\"User type\"]//following::*[contains(@class,\"new-summary-tag\")][1]")
    public FluentWebElement segmentUserTypeValues;

    @FindBy(xpath="//*[text()=\"Device\"]//following::*[contains(@class,\"new-summary-tag\")][1]")
    public FluentWebElement segmentDeviceTypeValues;

    @FindBy(css=".RCB-dd-search-ip")
    public FluentWebElement locationFilter;

    @FindBy(xpath="//*[text()=\"User Type\"]//following::*[contains(@class,\"seg-item-col\")][1]")
    public FluentWebElement UserTypeinListingPage;

    @FindBy(xpath="//*[text()=\"Add visitor types\"]//following::*[contains(@class,\"RCB-select-arrow\")][1]")
    public FluentWebElement userTypeFilter;

    @FindBy(xpath="//*[text()=\"Add devices\"]//following::*[contains(@class,\"RCB-select-arrow\")][1]")
    public FluentWebElement deviceTypeFilter;

    @FindBy(css=".add-field-box .RCB-dd-search-ip")
    public FluentWebElement locationSearchBox;

    @FindBy(xpath="(//*[@class='empty-wrapper'])[1]")
    public FluentWebElement outsideBox;

    @FindBy(xpath="//*[@placeholder='Add segment name']")
    public FluentWebElement segmentName;

    @FindBy(css=".tag-name-label")
    public FluentList<FluentWebElement> locDropdownList;

    @FindBy(css=".save-segment")
    public FluentWebElement saveSegmentButton;

    @FindBy(xpath="(//*[contains(text(),'User Type')]//following::*[contains(text(),'new')]) | (//*[contains(text(),'User Type')]//following::*[contains(text(),'repeat')])")
    public FluentWebElement getUserTypeText;

    @FindBy(xpath="(//*[contains(text(),'Device')]//following::*[contains(text(),'browser')])")
    public FluentWebElement getDeviceTypeText;

    public String segmentButtonType =".preview-tag-item";

    @FindBy(css=".light-close-icon")
    public FluentWebElement removeSegmentButton;
   // public String removeSegmentButton =".close-icon-light";

    @FindBy(css = ".unx-icon-settings")
    public FluentWebElement settingsIcon;

    @FindBy(xpath = "//*[contains(text(),'Yes')]")
    public FluentWebElement DeleteYes;


    @FindBy(css = ".custom-att-button")
    public FluentWebElement addCustomAttributeButton;

    @FindBy(css = "input[placeholder='Enter custom attribute']")
    public FluentWebElement customAttributeInput;

    @FindBy(css = ".modal-footer .RCB-btn-primary")
    public FluentWebElement saveCustomAttributeButton;

    @FindBy(css = ".pin-sel-summary")
    public FluentWebElement customAttributeDropdown;

    @FindBy(css = ".tag-input")
    public FluentWebElement customAttributeTagInput;

    @FindBy(css = ".tags-container.attribute-dropdown.attribute-tag-input")
    public FluentWebElement customInput;


    @FindBy(css = ".RCB-modal-close")
    public FluentWebElement closeCustomAttributeTagInputPopup;

    @FindBy(css = ".custom-attribute-input")
    public FluentWebElement selectedCustom;

    @FindBy(css = " .tag-item")
    public FluentWebElement selectedCustomValue;

    // Locators for deleteCustomAttribute method
    @FindBy(css = ".attribute-row")
    public FluentList<FluentWebElement> attributeRows;

    @FindBy(css = ".attribute-text")
    public FluentList<FluentWebElement> attributeTexts;

    @FindBy(css = ".delete-box")
    public FluentList<FluentWebElement> deleteBoxes;

    @FindBy(css = ".small-delete")
    public FluentList<FluentWebElement> deleteSmallIcons;


    public String getQueryNameFromSegmentRule(FluentWebElement queryRule)  {
        return queryRule.findFirst(".RCB-tr td:nth-child(1)").getTextContent();

    }

    public String getUrl()
    {
        awaitForPageToLoad();
        return  SEGMENT_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }
}
