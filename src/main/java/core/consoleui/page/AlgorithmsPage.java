package core.consoleui.page;

import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;
import core.ui.page.UiBase;

public class AlgorithmsPage extends UiBase {

    // Example: Synonyms input field
    @FindBy(css = "input#synonym-input")
    public FluentWebElement synonymInput;

    // Example: Synonyms table
    @FindBy(css = "table#synonyms-table")
    public FluentWebElement synonymsTable;

    // Heading: Content
    @FindBy(css = ".feature-heading")
    public FluentWebElement featureHeading;

    // Sub-heading
    @FindBy(css = ".feature-sub-heading")
    public FluentWebElement featureSubHeading;

    // Kabab menu (three dots)
    @FindBy(css = ".RCB-inline-modal-btn .menu-icon-black.merch-menu-items")
    public FluentWebElement kababMenuIcon;

    // Bulk upload synonyms (in kabab menu)
    @FindBy(css = ".unx-qa-menu-items")
    public FluentWebElement bulkUploadSynonymsMenuItem;

    // Bulk download synonyms (in kabab menu)
    @FindBy(css = ".RCB-inline-modal-body .action-item-merch.unx-qa-bulk-download")
    public FluentWebElement bulkDownloadSynonymsMenuItem;

    // Bulk download synonyms link (inside menu item)
    @FindBy(css = ".RCB-inline-modal-body .action-item-merch.unx-qa-bulk-download .action-item-text")
    public FluentWebElement bulkDownloadSynonymsLink;

    // Upload file input (in modal)
    @FindBy(css = "#bulkUploadBanners")
    public FluentWebElement uploadFileInput;

    // Upload file label (for clicking)
    @FindBy(css = "label[for='bulkUploadBanners'] .RCB-no-pointer a")
    public FluentWebElement uploadFileLink;

    // Download sample synonyms file link (in modal)
    @FindBy(css = ".pin-sample-download-text .pin-link-text")
    public FluentWebElement downloadSampleSynonymsLink;

    // Modal title (Bulk upload synonyms)
    @FindBy(css = ".RCB-modal-title")
    public FluentWebElement modalTitle;

    // Modal content
    @FindBy(css = ".RCB-modal-content")
    public FluentWebElement modalContent;

    // Success message (if any)
    @FindBy(css = "span.message")
    public FluentWebElement successMessage;

    // TODO: Add more elements as needed for Algorithms page
} 