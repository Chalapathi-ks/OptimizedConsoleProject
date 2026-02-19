package core.ui.page;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class ShopifyFeedUploadPage extends UiBase {


    @FindBy(name = "account[email]")
    public FluentWebElement shopifyEmailInputBox;

    @FindBy(name = "account[password]")
    public FluentWebElement shopifyPasswordInputBox;

    @FindBy(name = "commit")
    public FluentWebElement shopifyLoginTab;

    @FindBy(css=".store-list-item")
    public FluentList<FluentWebElement> shopifyStoreList;

    @FindBy(css=".Polaris-Navigation__Section_1b1h1 li")
    public FluentList<FluentWebElement> shopifySubMenuist;

    @FindBy(css=".Polaris-ResourceItem--alignmentCenter_1rtaw .Polaris-ButtonGroup_yy85z div:nth-child(2)")
    public FluentWebElement deleteAppButton;

    @FindBy(css=".Polaris-Modal-Dialog__Modal_2v9yc .Polaris-Button--destructive_zy6o5")
    public FluentWebElement deleteAppPopUpButton;

    @FindBy(css=".platform-step-card .RCB-form-el")
    public FluentWebElement shopifyUrlInputBox;

    @FindBy(css=".platform-step-card .shopify-install-btn")
    public FluentWebElement installPluginButton;

    public String installTabName = "Install tab ";

    @FindBy(css=".shopify-success")
    public FluentWebElement successfullShopifyUplaod;

    @FindBy(css=".shopify-success button")
    public FluentWebElement checkStatusButton;

}
