package core.consoleui.page;

import lib.EnvironmentConfig;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.*;


public class CommerceSearchPage extends ConsoleCommonPage  {

    public String campaignContainer = ".campaign-container";

    public String segmentCampaignContainer = ".segemnt-details-column";

    @FindBy(css=".search-input .RCB-form-el")
    public FluentWebElement searchInputBox;

    @FindBy(css=".unx-qa-seach-Icon ")
    public FluentWebElement searchIcon;


    @FindBy(xpath="(//*[@class='query-name query-row tbl-title-1 ']) | (//*[@class='tbl-title-1 '])")
    public FluentList<FluentWebElement> queryRulesList;

    @FindBy(css=".unx-qa-kebabmenu-right")
    public FluentWebElement menuIcon;

    @FindBy(css=".unx-qa-kebabmenu-left")
    public FluentWebElement leftMenuIcon;

    @FindBy(css=".unx-qa-duplicaterule")
    public FluentWebElement duplicateRuleIcon;

    @FindBy(css=".unx-qa-deleterule,.unx-qa-deleteicon")
    public FluentWebElement deleteRuleButton;

    //@FindBy(css=".RCB-align-right ul .campaign-menu-item .stop-icon")
    @FindBy(css=".unx-qa-stoprule")
    public FluentWebElement stopPromotionButton;

    @FindBy(css=".unx-qa-editicon")
    public FluentWebElement queryruleEditButton;

    @FindBy(css=".unx-qa-editicon.global-edit")
    public FluentWebElement globalruleEditButton;

    @FindBy(css=".edit-icon-blue")
    public FluentWebElement queryEditButton;

    @FindBy(css=".unx-qa-readonlyicon")
    public FluentWebElement queryPreviewButton;

    @FindBy(css=".RCB-modal-body")
    public FluentWebElement modalWindow;

    @FindBy(css=".RCB-modal-body .modal-footer .primary-btn")
    public FluentWebElement deleteYesButton;

    @FindBy(css=".banner-rule-textarea")
    public FluentWebElement htmlPreview;

    @FindBy(css=".merch-header-actions .RCB-btn-primary")
    public FluentWebElement addRuleButton;

    @FindBy(css=".merch-header-actions .RCB-btn-primary li:nth-child(1)")
    public FluentWebElement queryBasedBannerButon;

    @FindBy(css=".merch-header-actions .RCB-btn-primary li:nth-child(2)")
    public FluentWebElement fieldBasedBannerButon;

    @FindBy(css=".RCB-btn.RCB-btn-primary.RCB-btn-small, .merch-header-actions .add-rule-btn-dropdown ")
    public FluentWebElement addBannerButton;

    @FindBy(css=".input-drop-wrapper .side-input .RCB-form-el")
    public FluentWebElement newQueryRuleInput;

    @FindBy(css=".other-queries-btn")
    public FluentWebElement addMoreQueryTab;

    @FindBy(css=".rule-queries-wrapper .tag-input")
    public FluentWebElement similarQueryInput;

    @FindBy(css=".query-name.query-row")
    public FluentList<FluentWebElement> getQueryRules;

    public String getQueryName = ".query-name";

    @FindBy(css=".unx-qa-stop")
    public FluentWebElement stopCampaign;

    @FindBy(css=".publish-error-msg-header")
    public FluentWebElement publishErrorMsg;


    @FindBy(css=".RCB-form-el-cont .RCB-form-el")
    public FluentWebElement redirectPreview;

    @FindBy(css=".action-rules-list .action-title span:nth-child(2)")
    public FluentWebElement conditionTitle;

    @FindBy(css=".promotion-rules-summary")
    public FluentWebElement promotionRuleSummary;

    @FindBy(css=".RCB-list .rule-summary-row")
    public FluentList<FluentWebElement> conditionSummary;

    @FindBy(xpath="(//*[@class='sort-summary-li'])|(//*[@class='pin-summary-li pin-summary-box'])")
    public FluentList<FluentWebElement> sortConditionList;

    @FindBy(css=".add-new-cat-page button")
    public FluentWebElement addButton;

    @FindBy(css=".RCB-dd-search-ip")
    public FluentWebElement pageRuleInput;

    @FindBy(css=".add-another-rule-action")
    public FluentWebElement Addanothercampaign;

    @FindBy(css=".unx-qa-toastsucess")
    public FluentWebElement ToasterSuccess;

    @FindBy(css = "button.RCB-btn.RCB-btn-secondary.RCB-btn-small.unx-qa-sync")
    public FluentWebElement syncButton;

    // Sync confirmation modal elements
    @FindBy(css = ".RCB-modal-content .modal-content")
    public FluentWebElement syncModalContent;

    @FindBy(xpath = "//div[contains(@class,'modal-content')]//div[contains(@class,'title') and contains(text(),'Confirm Sync')]")
    public FluentWebElement syncConfirmTitle;

    @FindBy(css = ".confirm-msg")
    public FluentWebElement syncConfirmMessage;

    @FindBy(xpath = "//div[contains(@class,'modal-footer')]//button[contains(@class,'RCB-btn-primary') and contains(text(),'Yes')]")
    public FluentWebElement syncConfirmYesButton;

    @FindBy(xpath = "//div[contains(@class,'modal-footer')]//button[contains(@class,'RCB-btn-secondary') and contains(text(),'Cancel')]")
    public FluentWebElement syncConfirmCancelButton;

    // Syncing status element
    @FindBy(css = ".status-btn__variant")
    public FluentWebElement syncingStatus;

    // Campaign type elements
    @FindBy(css = ".campaign-type-text")
    public FluentList<FluentWebElement> campaignTypeTexts;

    @FindBy(xpath = "//*[contains(text(),'Variant Locking')]")
    public FluentWebElement variantLockingCampaignType;

    // Toast notification elements
    @FindBy(css = ".notification-outer-wrapper .toast.info.unx-qa-toasterror")
    public FluentWebElement syncInfoToast;

    @FindBy(css = ".notification-outer-wrapper .toast.info .message")
    public FluentWebElement syncInfoToastMessage;

    public String getQueryNameFromQueryRule(FluentWebElement queryRule)  {
       // return queryRule.findFirst(".query-name").getTextContent();
        return queryRule.getTextContent();
    }

    public String getUrl()
    {
        awaitForPageToLoad();
        return  SEARCH_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }

}
