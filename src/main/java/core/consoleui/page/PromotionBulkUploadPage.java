package core.consoleui.page;

import lib.EnvironmentConfig;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.SEARCH_PAGE;

public class PromotionBulkUploadPage extends ConsoleCommonPage {

    // Promotions page heading
    @FindBy(css = "div.feature-heading")
    public FluentWebElement promotionsHeading;

    // Modal trigger and dropdown elements
    @FindBy(css = ".RCB-inline-modal-btn")
    public FluentWebElement modalButton;
    
    @FindBy(css = ".unx-qa-menu-items")
    public FluentWebElement menuIcon;
    
    @FindBy(css = ".RCB-list.more-items-modal-dd")
    public FluentWebElement moreItemsDropdown;
    
    @FindBy(css = ".action-item-merch.unx-qa-bulk-upload")
    public FluentWebElement bulkUploadPromotionsOption;
    
    @FindBy(css = ".unx-qa-bulk-upload .upload-file-icon")
    public FluentWebElement bulkUploadIcon;
    
    @FindBy(css = ".unx-qa-bulk-upload .action-item-text")
    public FluentWebElement bulkUploadText;
    
    @FindBy(css = ".action-item-merch.unx-qa-bulk-download")
    public FluentWebElement exportToEmailOption;
    
    @FindBy(css = ".unx-qa-bulk-download .export-to-email")
    public FluentWebElement exportIcon;
    
    @FindBy(css = ".unx-qa-bulk-download .action-item-text")
    public FluentWebElement exportText;
    
    // Success message
    @FindBy(css = ".success-message, .alert-success, .notification-success, .message.success, span.message, div[class*='success'], .toast-success")
    public FluentWebElement exportSuccessMessage;
    
    // Modal elements
    @FindBy(css = ".RCB-modal-title")
    public FluentWebElement modalTitle;
    
    @FindBy(css = ".RCB-modal-content")
    public FluentWebElement modalContent;
    
    @FindBy(css = ".confirm-modal-body")
    public FluentWebElement modalBody;
    
    // Drag and drop uploader elements
    @FindBy(css = ".drag-drop-uploader")
    public FluentWebElement dragDropUploader;
    
    @FindBy(css = ".drap-drop-wrapper")
    public FluentWebElement dragDropWrapper;
    
    @FindBy(css = ".RCB-notif.RCB-notif-warning.drag-drop-warning")
    public FluentWebElement warningNotification;
    
    @FindBy(css = ".cloud-icon")
    public FluentWebElement cloudIcon;
    
    @FindBy(css = ".drag-drop-text")
    public FluentWebElement dragDropText;
    
    // File upload elements
    @FindBy(css = ".RCB-form-el-cont.RCB-form-el-inline.pin-uploader")
    public FluentWebElement fileUploaderContainer;
    
    @FindBy(css = "#bulkUploadBanners")
    public FluentWebElement fileInput;
    
    @FindBy(css = "label[for='bulkUploadBanners']")
    public FluentWebElement uploadFileLabel;
    
    @FindBy(css = "label[for='bulkUploadBanners'] .RCB-no-pointer a")
    public FluentWebElement uploadFileButton;
    
    // Sample download link
    @FindBy(css = ".pin-sample-download-text")
    public FluentWebElement sampleDownloadContainer;
    
    @FindBy(css = ".pin-sample-download-text .pin-link-text")
    public FluentWebElement downloadSampleLink;
    
    // Overwrite checkbox elements
    @FindBy(css = "#bulk-upload-promotions")
    public FluentWebElement overwriteCheckbox;
    
    @FindBy(css = "label[for='bulk-upload-promotions']")
    public FluentWebElement overwriteCheckboxLabel;
    
    @FindBy(css = "label[for='bulk-upload-promotions'].UNX-icons.UNX-favourite")
    public FluentWebElement overwriteCheckboxIcon;
    
    // Upload report message
    @FindBy(css = "h2.push-top-30.publish-error-msg-header")
    public FluentWebElement uploadReportMessage;

} 