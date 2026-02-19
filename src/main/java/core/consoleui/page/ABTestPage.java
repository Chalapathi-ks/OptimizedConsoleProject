package core.consoleui.page;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class ABTestPage extends CampaignCreationPage {

    @FindBy(css=".ab-toggle")
    public FluentWebElement abTestToggle;

    @FindBy(css=".ab-toggle .active")
    public FluentWebElement abTestEnabledToggle;

    @FindBy(css=".ab-edit-mode-btns .ab-edit-save")
    public FluentWebElement abConditionApplyButton;

    @FindBy(css=".ab-edit-mode-btns .ab-edit-cancel")
    public FluentWebElement abConditionCancelButton;

    @FindBy(css=".ab-test-winner-options .RCB-dd-label")
    public FluentWebElement winnerDecidedBy;

    @FindBy(css=".ab-test-preferred-options .RCB-dd-label")
    public FluentWebElement preferredOption;

    @FindBy(css=".ab-test-tabs-list .abtest-tab:nth-child(2)")
    public FluentWebElement variantionBTab;

    @FindBy(css=".ab-test-tabs-list .abtest-tab:nth-child(1)")
    public FluentWebElement variantionATab;


    @FindBy(css=".ab-more-details-container")
    public FluentWebElement abPreviewSummary;

    @FindBy(css=".ab-summary-view-tag")
    public FluentWebElement viewMore;

    @FindBy(css=".ab-variant-variantB .RCB-form-el")
    public FluentWebElement persentageTabVariantB;

    @FindBy(css=".ab-variant-variantA .RCB-form-el")
    public FluentWebElement persentageTabVariantA;

    @FindBy(css=".ab-variant-unbxdVariant .RCB-form-el")
    public FluentWebElement persentageTabDefault;

    @FindBy(css=".summary-unbxdVariant .percent-summary")
    public FluentWebElement percentageUnbxdDefaulSummary;

    @FindBy(css=".summary-variantA .percent-summary")
    public FluentWebElement percentageVariantASummary;

    @FindBy(css=".summary-variantB .percent-summary")
    public FluentWebElement percentageVariantBSummary;








}
