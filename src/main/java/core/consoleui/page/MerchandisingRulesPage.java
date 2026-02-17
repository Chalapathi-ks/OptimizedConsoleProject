package core.consoleui.page;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lib.Helper;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class    MerchandisingRulesPage extends CampaignCreationPage {

    public static final String ruleGroups=".promotion-filters-list .promotion-filter-item";
    public static final String ruleValueGroups=".boost-slot-section";
    public static final String boostSliderValue =".boost-value input";
    public static final String attribute=".RCB-dropdown.fields-dd .RCB-inline-modal-btn";
    public static final String comparator=".filter-comparator-dd .RCB-inline-modal-btn";
    public static final String valueOfAttribute=".RCB-dd-searchabledd-search-ip, .RCB-form-el-cont.RCB-form-el-inline.filter-values-text ";
    public static final String boostValueSlider=".RCB-range-wrapper input";
    public static final String addIcon = ".active-add-icon";
    public static final String boostGroups=".boost-slot-section";
    public static final String pinSortRuleGroups=".promo-rule-item";
    public static final String SortOrder=".sort-order-dd";
    public static final String pinPosition =".pin-position";
    public static final String sortAttribute=".RCB-dropdown:nth-child(1)  .RCB-form-el";

    //@FindBy(css = ".rule-content ul:nth-child(1)")
    @FindBy(css = ".summary-strategy-box")
    public FluentList<FluentWebElement> sortGroups;

    @FindBy(css = ".RCB-list .RCB-list-item")
    public FluentList<FluentWebElement> conditionList;

    @FindBy(css = ".filter-rule-item")
    public FluentList<FluentWebElement> filterGroups;

    @FindBy(css = ".preview-actions-wrapper")
    public FluentWebElement previewActionWrapper;

    @FindBy(css = ".promotion-actions-list li")
    public FluentList<FluentWebElement> merchandisingSections;

    @FindBy(css = ".rule-content .RCB-list:nth-child(2)")
    public FluentList<FluentWebElement> pinGroups;


    public static final String addRule=".filter-and-btn";

    @FindBy(css = ".another-condition-btn")
    public FluentWebElement addAndRuleButton;

    @FindBy(css = ".another-condition-btn  .plus-icon.vertical-middle")
    public FluentWebElement addNewGroup;

    @FindBy(css = ".RCB-dd-search-ip")
    public FluentWebElement attributeInput;

    @FindBy(css = ".RCB-align-left .RCB-list-item")
    public FluentList<FluentWebElement> attributeDropDownList;

    @FindBy(css = ".facet-name-wrapper")
    public FluentWebElement FacetWrapper;

    @FindBy(css = ".RCB-select-arrow")
    public FluentWebElement AttributeDropDown;

    @FindBy(css = ".RCB-align-right .RCB-list-item")
    public FluentList<FluentWebElement> attributeDropDwnList;

    @FindBy(css=".boost-slot-section ")
    public FluentWebElement boostSlideSection;

    @FindBy(xpath="//*[@name='filterValue']|//*[@label='Position']")
    public FluentWebElement Attribbutevalue;


    @FindBy(css=".boost-slot-section .RCB-range-wrapper input")
    public FluentWebElement boostSlider;

    @FindBy(css =".boost-slot-section .RCB-range-wrapper")
    public FluentWebElement boostValueSection;

    //@FindBy(css = ".slot-title")
    @FindBy(css = ".boost-slot-section.slot-section")
    public FluentWebElement slotPositionSection;

    @FindBy(css=".landing-page-toggle .RCB-toggle  ")
    public FluentWebElement landingPageToggle;

    @FindBy(css=".landing-page-toggle .active")
    public FluentWebElement landingPageEnabledToggle;

    @FindBy(css=".unx-qa-seach-browsepreview")
    public FluentWebElement seach_browsepreview;

    @FindBy(css=".typed-query")
    public FluentWebElement showingResultinPreview;

    @FindBy(css=".view-hide-insight")
    public FluentWebElement view_hide_insight;

    @FindBy(css=".wp-tab-items.Search-preview")
    public FluentWebElement SearchpreviewOption;

    @FindBy(css=".minimize-view")
    public FluentWebElement inSighttitle;

    @FindBy(xpath="(//*[@class='promotion-status-title'])[1]")
    public FluentWebElement MerchandisingStrategy;

    @FindBy(xpath="(//*[@class='action-rules']//following::ul)[1]")
    public FluentWebElement MerchandisingStrategyBoostDetails;

    @FindBy(xpath="(//*[contains(text(),'Resolved Promotion')]//following::*[contains(text(),'Merchandising Strategy')]//following::div[contains(@class,'summary-li ')])[1]//*[@class='summary-primary-tag-value']")
    public FluentWebElement MerchandisingStrategyBoostValue;

    @FindBy(xpath="(//*[contains(text(),'Resolved Promotion')]//following::*[contains(text(),'Merchandising Strategy')]//following::div[contains(@class,'summary-li ')])[1]//*[@class='summary-condition']")
    public FluentWebElement MerchandisingStrategyBoostCondition;
    @FindBy(xpath="(//*[contains(text(),'Resolved Promotion')]//following::*[contains(text(),'Merchandising Strategy')]//following::div[contains(@class,'summary-li ')])[1]//*[@class='summary-text']")
    public FluentWebElement MerchandisingStrategyBoostAttribute;

    @FindBy(xpath="//*[contains(@class,'sort')]//li[contains(@class,'sort-summary-li')][1]/span[@class='summary-primary-tag-value']")
    public FluentWebElement MerchandisingStrategySortValue;

    @FindBy(xpath="//*[contains(@class,'sort')]//li[contains(@class,'sort-summary-li')][1]/span[@class='summary-text']")
    public FluentWebElement MerchandisingStrategySortAttribute;

    @FindBy(xpath="(//*[contains(@class,'filter')]//div[contains(@class,'summary-li')])[1]/span[@class='summary-primary-tag-value']\n")
    public FluentWebElement MerchandisingStrategyFilterValue;

    @FindBy(xpath="(//*[contains(@class,'filter')]//div[contains(@class,'summary-li')])[1]/span[@class='summary-condition']")
    public FluentWebElement MerchandisingStrategyFilterCondition;

    @FindBy(xpath="(//*[contains(@class,'filter')]//div[contains(@class,'summary-li')])[1]/span[@class='summary-text']")
    public FluentWebElement MerchandisingStrategyFilterAttribute;

    @FindBy(xpath="(//*[contains(@class,'slot')]//div[contains(@class,'summary-li')])[1]/span[@class='summary-text']")
    public FluentWebElement MerchandisingStrategySlotAttribute;

    @FindBy(xpath="(//*[contains(@class,'slot')]//div[contains(@class,'summary-li')])[1]/span[@class='summary-primary-tag-value']")
    public FluentWebElement MerchandisingStrategySlotValue;

    @FindBy(xpath="(//*[contains(@class,'slot')]//div[contains(@class,'summary-li')])[1]/span[@class='summary-condition']")
    public FluentWebElement MerchandisingStrategySlotCondition;

    @FindBy(xpath="(//*[contains(text(),'Boost')]//following::*[@class='position-num'])[1]")
    public FluentWebElement MerchandisingStrategyBoostPosition;

    @FindBy(xpath="(//*[contains(text(),'Slot')]//following::*[@class='position-num'])[1]")
    public FluentWebElement MerchandisingStrategySlotPosition;

    @FindBy(xpath="(//*[contains(text(),'Pinned')]//following::*[@class='position-num pin-position-num'])")
    public FluentList<FluentWebElement> MerchandisingStrategyPinPosition;

    @FindBy(css=".unx-icon-edit")
    public FluentWebElement MerchandisingStrategyEditButton;

    @FindBy(css=".custom-date ")
    public FluentWebElement calendarIcon;

    @FindBy(xpath="//*[@class='time-zone time-headers']//following::*[@class='RCB-select-arrow']")
    public FluentWebElement timezoneDropdown;

    @FindBy(xpath="//*[@class='time-zone time-headers']//following::*[@class='RCB-dd-search-ip']")
    public FluentWebElement zoneinput;

    @FindBy(xpath="//*[@class='time-zone time-headers']//following::*[@class='RCB-list-item ']")
    public FluentWebElement timezonelist;

    @FindBy(css=".calendar-apply .RCB-btn-primary")
    public FluentWebElement calenderApplyButton;

    @FindBy(css=".similar-query-flag-title")
    public FluentWebElement applysameRuletomoreAIsuggestedqueries;

    @FindBy(css=".similar-queries-input .RCB-form-el.ltr")
    public FluentWebElement similarQueriesInput;

    @FindBy(xpath="//*[contains(text(),'Add new queries')]//following::*[contains(text(),'Add')]")
    public FluentWebElement similarQueriesAddlabel;

    @FindBy(css=".apply-changes")
    public FluentWebElement applyChanges;

    @FindBy(css=".selected-similar-queries")
    public FluentWebElement selectedSimilarQueries;

    @FindBy(css=".unx-icon-edit")
    public FluentWebElement addMoreQueriesEditIcon;

    @FindBy(css=".other-queries-tags-container")
    public FluentWebElement similarQuerySummary;


    @FindBy(css=".boost-value input")
    public FluentWebElement boostInputValue;

    @FindBy(css=".single-pill-wrapper")
    public FluentWebElement similarqueryinListpage;

    @FindBy(css=".tag-add-small-icon")
    public FluentList<FluentWebElement> AiSuggestedList;

    @FindBy(css=".total-query-item")
    public FluentWebElement AiSelectedSimilarquery;





    public Map<String, Object> getCampaignData(String fileName) {

        String testDataPath = "src/test/resources/testData/consoleTestData/";
        String file = testDataPath + fileName;
        Map<String, Object> campaignData = null;
        JsonParser parser = new JsonParser();
        try {
            JsonObject object = (JsonObject) parser.parse(new FileReader(new File(file)));
            campaignData = Helper.convertJsonToHashMap(object.toString());

        } catch (FileNotFoundException e) {
            System.out.println("File Not found " + e.getMessage());
        }

        return campaignData;
    }



}
