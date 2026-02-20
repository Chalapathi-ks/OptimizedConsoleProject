package core.consoleui.page;

import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class BannerPage extends CampaignCreationPage {

    @FindBy(css=".RCB-align-left .banner-query-rule")
    public FluentWebElement bannerQueryRuleButton;

    @FindBy(css=".RCB-align-left .banner-field-rule")
    public FluentWebElement bannerFieldRuleButton;

    @FindBy(css=".banner-tab-header .radio-tab:nth-child(1)")
    public FluentWebElement imageUrlRadioButton;

    @FindBy(css=".banner-tab-header .radio-tab:nth-child(2)")
    public FluentWebElement htmlRadioButton;

    @FindBy(css=".banner-tab-header .radio-tab:nth-child(2) input")
    public FluentWebElement htmlRadioButtonIsSelected;

    @FindBy(css=".banner-html-body .RCB-form-el")
    public FluentWebElement htmlBannerInput;

    @FindBy(css=".rule-content .RCB-form-el-block input")
    public FluentWebElement redirectInput;

    @FindBy(css=".banner-tab-header .radio-tab:nth-child(1) input")
    public FluentWebElement imgUrlRadioButtonIsSelected;

    @FindBy(xpath="//*[@name='imageUrl']")
    public FluentWebElement bannerInputImgUrl;

    @FindBy(id="redirectUrl")
    public FluentWebElement bannerInputRedirectUrl;

    @FindBy(xpath="(//*[contains(text(),'Attribute')]//following::*[@class='RCB-select-arrow'])[1]")
    public FluentWebElement fieldRuleAttributeDropdown;

    @FindBy(css=".RCB-list-item ")
    public FluentList<FluentWebElement> fieldRuleAttributeDropDownList;

    @FindBy(css=".RCB-dd-search-ip.RCB-dd-searchabledd-search-ip")
    public FluentWebElement fieldRuleAttributeValueDropdown;


    @FindBy(css=".unx-qa-field-rule-banner")
    public FluentWebElement browseFieldRuleMatchCondtion;

    @FindBy(xpath="//*[@placeholder='Search']")
    public FluentWebElement searchAttribute;

    @FindBy(css=".border-top.pad-top-10")
    public FluentWebElement bannerExperience;

    @FindBy(css=".card-header medium-text .banner-html-body,.border-top.pad-top-10")
    public FluentWebElement bannerExperienceInput;





}
