package core.consoleui.page;

import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class BannerBulkUploadPage extends CampaignCreationPage {

    @FindBy(css=".merch-data-modal .merch-menu-items")
    public FluentWebElement bannerUploadMenuButton;

    @FindBy(css=".action-item-text")
    public FluentWebElement bulkUploadOrDownloadButton;

    @FindBy(xpath="//input[@id='bulkUploadBanners']")
    public FluentWebElement bannerFileUploadButton;

    @FindBy(css=".success-icon")
    public FluentWebElement bulkUploadSuccessIcon;

    @FindBy(css=".publish-error-msg-header")
    public FluentWebElement bulkUploadedSuccessMessage;

    @FindBy(css=".error-color")
    public FluentWebElement bulkUploadErrorMessage;

    @FindBy(css=".RCB-btn-primary.save-changes")
    public FluentWebElement bulkUploadDoneButton;

}
