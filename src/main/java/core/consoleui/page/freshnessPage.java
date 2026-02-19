package core.consoleui.page;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class freshnessPage extends CampaignCreationPage {

    @FindBy(xpath = "//*[@for='freshnessAttribute']//following::*[@class='RCB-select-arrow']")
    public FluentWebElement AttributeDropDown;

    @FindBy(xpath = "//*[@class='freshness-form-row']//following::*[@type='number']")
    public FluentWebElement InputBox;
    @FindBy(css = ".RCB-list-item.dm-dd-item ")
    public FluentList<FluentWebElement> attributeDropDownList;
    @FindBy(css = ".RCB-dd-search-ip")
    public FluentWebElement attributeInput;
} 