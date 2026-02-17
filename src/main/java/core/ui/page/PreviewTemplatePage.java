package core.ui.page;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class PreviewTemplatePage extends UiBase {

    public static By previewButton = By.cssSelector(".RCB-btn-small");
    public String previewPageLoader = "Reindexing/preview page loader";
    public String templates = "respective template";

    @FindBy(css=".RCB-btn-small")
    public FluentWebElement previewButtonTab;

    @FindBy(css= ".RCB-btn-large")
    public FluentWebElement applyConfigButton;

    @FindBy(css=".UNX-product-list .UNX-parent-image")
    public FluentList<FluentWebElement> productSnippet;

    @FindBy(css=".UNX-facet-item-d")
    public FluentList<FluentWebElement> filterSnippet;

    @FindBy(css=".UNX-result-info")
    public FluentWebElement productCount;

    @FindBy(css=".RCB-modal-close")
    public FluentWebElement templatePopupCloseButton;

    @FindBy(css=".view-link")
    public FluentWebElement viewTemplateLink;

    @FindBy(css=".RCB-list li:nth-child(1)")
    public FluentWebElement previewTemplate1;

    @FindBy(css=".UNX-ribbon")
    public FluentList<FluentWebElement> previewTemplateBadges;

    @FindBy(css=".UNX-product-title")
    public FluentList<FluentWebElement> productTitle;

    @FindBy(css=".UNX-id-row")
    public FluentList<FluentWebElement> previewTemplateSku;

    @FindBy(css=".green-banner")
    public  FluentWebElement appliedBanner;

    @FindBy(css=".RCB-list li:nth-child(2)")
    public FluentWebElement previewTemplate2;

    @FindBy(css=".RCB-list li:nth-child(3)")
    public FluentWebElement previewTemplate3;

    @FindBy(css=".RCB-list li:nth-child(4)")
    public FluentWebElement previewTemplate4;

    @FindBy(css=".RCB-list li:nth-child(5)")
    public FluentWebElement previewTemplate5;

    @FindBy(css=".RCB-list li:nth-child(6)")
    public FluentWebElement previewTemplate6;

    @FindBy(css=".RCB-list li:nth-child(7)")
    public FluentWebElement previewTemplate7;

    @FindBy(css=".RCB-list li:nth-child(8)")
    public FluentWebElement previewTemplate8;

    @FindBy(css=".group-name")
    public FluentWebElement appliedTemplateName;



}
