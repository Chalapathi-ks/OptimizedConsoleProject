package core.consoleui.page;

import lib.EnvironmentConfig;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.COLLECTION_PAGE;

public class CollectionsPage extends ConsoleCommonPage {

    // Page structure elements
    @FindBy(css = ".page-content")
    public FluentWebElement pageContent;

    @FindBy(css = ".merch-out-container")
    public FluentWebElement merchContainer;

    @FindBy(css = ".page-sub-header")
    public FluentWebElement pageSubHeader;

    // Collections page heading
    @FindBy(css = "div.feature-heading")
    public FluentWebElement collectionsHeading;

    @FindBy(css = ".feature-sub-heading")
    public FluentWebElement collectionsSubHeading;

    // Header actions section
    @FindBy(css = ".merch-header-actions")
    public FluentWebElement merchHeaderActions;

    // Search functionality
    @FindBy(css = ".search-input-box")
    public FluentWebElement searchInputBox;

    @FindBy(css = ".search-icon-medium.unx-qa-seach-Icon")
    public FluentWebElement searchIcon;

    @FindBy(css = "#csSearchQuery")
    public FluentWebElement searchInput;

    @FindBy(css = ".close-icon-dark")
    public FluentWebElement searchCloseIcon;

    // Action buttons
    @FindBy(css = ".refresh-button-wrapper.unx-qa-refresh img")
    public FluentWebElement refreshButton;

    @FindBy(css = ".RCB-inline-modal.merch-filters-modal.unx-qa-listing-page-filter")
    public FluentWebElement filterButton;

    @FindBy(css = ".black-filter-icon")
    public FluentWebElement filterIcon;

    @FindBy(css = "button.RCB-btn.RCB-btn-secondary.RCB-btn-small.unx-qa-sync")
    public FluentWebElement syncButton;

    @FindBy(css = "button.RCB-btn.RCB-btn-primary.RCB-btn-small.upload-button")
    public FluentWebElement uploadFileButton;

    // Collections table
    @FindBy(css = ".collections-list-main")
    public FluentWebElement collectionsListMain;

    @FindBy(css = ".sticky-table.custom-collection.nutana-table")
    public FluentWebElement collectionsTable;

    @FindBy(css = ".rdt_Table")
    public FluentWebElement dataTable;

    @FindBy(css = ".rdt_TableHead")
    public FluentWebElement tableHead;

    @FindBy(css = ".rdt_TableHeadRow")
    public FluentWebElement tableHeaderRow;

    @FindBy(css = ".rdt_TableBody")
    public FluentWebElement tableBody;

    // Table headers
    @FindBy(css = ".header-title")
    public FluentList<FluentWebElement> tableHeaders;

    // Collection details column
    @FindBy(css = "[data-column-id='1'] .header-title")
    public FluentWebElement collectionDetailsHeader;

    // Number of products column
    @FindBy(css = "[data-column-id='2'] .header-title")
    public FluentWebElement numberOfProductsHeader;

    // Associated campaigns column
    @FindBy(css = "[data-column-id='3'] .header-title")
    public FluentWebElement associatedCampaignsHeader;

    // Collection rows
    @FindBy(css = ".rdt_TableRow")
    public FluentList<FluentWebElement> collectionRows;

    // Collection name elements
    @FindBy(css = ".tbl-title-1")
    public FluentList<FluentWebElement> collectionNames;

    // Collection creation date
    @FindBy(css = ".coll-date")
    public FluentList<FluentWebElement> collectionDates;

    // Collection status
    @FindBy(css = ".unbxd-pill.success")
    public FluentList<FluentWebElement> collectionStatus;

    // Product count
    @FindBy(css = "[data-column-id='2'] .tbl-title-2")
    public FluentList<FluentWebElement> productCounts;

    // Campaign counts
    @FindBy(css = ".campaigns-count-col .tbl-title-2")
    public FluentList<FluentWebElement> campaignCounts;

    // Collection actions
    @FindBy(css = ".collection-actions")
    public FluentList<FluentWebElement> collectionActions;

    // Edit icon
    @FindBy(css = ".edit-icon-dark.unx-qa-editicon")
    public FluentList<FluentWebElement> editIcons;

    // Download icon
    @FindBy(css = ".download-file-icon")
    public FluentList<FluentWebElement> downloadIcons;

    // Delete icon
    @FindBy(css = ".delete-row-icon.unx-qa-deleteicon")
    public FluentList<FluentWebElement> deleteIcons;

    // Pagination elements
    @FindBy(css = ".rdt_Pagination")
    public FluentWebElement pagination;

    @FindBy(css = "select[aria-label='Rows per page:']")
    public FluentWebElement rowsPerPageSelect;

    @FindBy(css = "#pagination-first-page")
    public FluentWebElement firstPageButton;

    @FindBy(css = "#pagination-previous-page")
    public FluentWebElement previousPageButton;

    @FindBy(css = "#pagination-next-page")
    public FluentWebElement nextPageButton;

    @FindBy(css = "#pagination-last-page")
    public FluentWebElement lastPageButton;

    // Pagination info
    @FindBy(css = ".sc-jytpVa.sc-eknHtZ")
    public FluentWebElement paginationInfo;

    // Upload File Modal Elements
    @FindBy(css = ".RCB-modal-body")
    public FluentWebElement modalBody;

    @FindBy(css = ".RCB-modal-header")
    public FluentWebElement modalHeader;

    @FindBy(css = ".RCB-modal-title")
    public FluentWebElement modalTitle;

    @FindBy(css = ".header-heading")
    public FluentWebElement modalHeaderHeading;

    @FindBy(css = ".header-subheading")
    public FluentWebElement modalHeaderSubheading;

    @FindBy(css = ".RCB-modal-close")
    public FluentWebElement modalCloseButton;

    @FindBy(css = ".RCB-modal-content")
    public FluentWebElement modalContent;

    @FindBy(css = ".confirm-modal-body")
    public FluentWebElement confirmModalBody;

    // Collection Name Input
    @FindBy(css = ".RCB-form-el-cont.RCB-form-el-inline.collection-input")
    public FluentWebElement collectionInputContainer;

    @FindBy(css = ".RCB-form-el-cont.RCB-form-el-inline.collection-input .RCB-form-el-label")
    public FluentWebElement collectionNameLabel;

    @FindBy(css = ".RCB-form-el-cont.RCB-form-el-inline.collection-input .RCB-form-el.ltr")
    public FluentWebElement collectionNameInput;

    // Drag and Drop Upload Section
    @FindBy(css = ".drag-drop-uploader")
    public FluentWebElement dragDropUploader;

    @FindBy(css = ".drap-drop-wrapper")
    public FluentWebElement dragDropWrapper;

    @FindBy(css = ".disable-upload")
    public FluentWebElement disableUpload;

    @FindBy(css = ".cloud-icon")
    public FluentWebElement cloudIcon;

    @FindBy(css = ".drag-drop-text")
    public FluentWebElement dragDropText;

    // File Upload Elements
    @FindBy(css = ".RCB-form-el-cont.RCB-form-el-inline.pin-uploader")
    public FluentWebElement pinUploaderContainer;

    @FindBy(css = "input[type='file'][name='bulkUploadBanners']")
    public FluentWebElement fileInput;

    @FindBy(css = "input#bulkUploadBanners")
    public FluentWebElement bulkUploadInput;

    @FindBy(css = "label[for='bulkUploadBanners']")
    public FluentWebElement fileInputLabel;

    @FindBy(css = "label[for='bulkUploadBanners'] .RCB-no-pointer a")
    public FluentWebElement uploadFileLink;

    // Download Sample File Link
    @FindBy(css = ".pin-sample-download-text")
    public FluentWebElement sampleDownloadContainer;

    @FindBy(css = ".pin-sample-download-text .pin-link-text")
    public FluentWebElement downloadSampleCollectionsLink;

    // Upload Progress Message
    @FindBy(css = "h2.push-top-30.publish-error-msg-header")
    public FluentWebElement uploadProgressMessage;

    /**
     * Get the URL for Collections page
     * @return Collections page URL with site ID
     */
    public String getUrl() {
        awaitForPageToLoad();
        return COLLECTION_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }

    /**
     * Get collection name from a specific collection element
     * @param collectionElement The collection web element
     * @return Collection name as string
     */
    public String getCollectionName(FluentWebElement collectionElement) {
        return collectionElement.findFirst(".tbl-title-1").getTextContent();
    }

    /**
     * Get collection status from a specific collection element
     * @param collectionElement The collection web element
     * @return Collection status as string
     */
    public String getCollectionStatus(FluentWebElement collectionElement) {
        return collectionElement.findFirst(".unbxd-pill").getTextContent();
    }
}
 