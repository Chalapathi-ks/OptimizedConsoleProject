package core.ui.page;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class FeedUploadPage extends UiBase{


    public static By previewButton = By.cssSelector(".RCB-btn-small");
    public String previewPageLoader = "Reindexing/preview page loader";

    @FindBy(css=".qa-new-import button.secondary-btn:nth-child(1)")
    public FluentWebElement feedFetchFromComputer;

    @FindBy(css=".qa-new-import button.secondary-btn:nth-child(4)")
    public FluentWebElement urlUploadTab;

    @FindBy(css=".modal-title")
    public FluentWebElement importPopUpTittle;

    @FindBy(css=".upload-sources .upload-card:nth-child(1) button")
    public FluentWebElement platformUpload;

    @FindBy(css=".back-header")
    public FluentWebElement BackToPlatformTab;

    @FindBy(css=".upload-sources .upload-card:nth-child(2) button")
    public FluentWebElement pimUpload;

    @FindBy(css=".upload-sources .upload-card:nth-child(3) button")
    public FluentWebElement apiUpload;

    @FindBy(css=".RCB-list .platform-card:nth-child(1) button")
    public FluentWebElement magentoTab;

    @FindBy(css=".RCB-list .platform-card:nth-child(2) button")
    public FluentWebElement hybrisTab;

    @FindBy(css=".RCB-list .platform-card:nth-child(3) button")
    public FluentWebElement shopifyTab;

    @FindBy(id = "pimIframe")
    public FluentWebElement switchToIFrame;

    @FindBy(css = ".primary-btn")
    public FluentWebElement prePareForImportTab;


    @FindBy(css=".qa-new-import button.secondary-btn:nth-child(2)")
    public FluentWebElement sftpUploadTab;

    @FindBy(css=".qa-new-import button.secondary-btn:nth-child(4)")
    public FluentWebElement channelUploadTab;

    @FindBy(name = "importUrl")
    //@FindBy(css=".element-importUrl input")
    public FluentWebElement feedUrlInput;



    @FindBy(css=".primary-btn")
    public FluentWebElement startImportButton;

    @FindBy(css=".mapping-description")
    public FluentWebElement mapImportText;

    @FindBy(css=".ReactVirtualized__List li")
    public FluentList<FluentWebElement> propertiesList;

    @FindBy(css=".drop-down-body li")
    public FluentList<FluentWebElement> mandatoryMappingFieldsList;

    @FindBy(css=".float-right button")
    public FluentWebElement importSave;

    @FindBy(css=".drop-down-input")
    public FluentWebElement mappingSearchBox;

    @FindBy(css=".import-mapping .import-start")
    public FluentWebElement preImportSaveButton;

    @FindBy(css=".loader-img")
    public FluentWebElement loader;

    @FindBy(css=".tab-heading .import-start")
    public FluentWebElement reviewImportButton;

    @FindBy(css=".import-adapter-creation .element-adapterName input")
    public FluentWebElement adapterInput;

    @FindBy(css=".loading-btn button")
    public FluentWebElement adapterSaveButton;

    @FindBy(css=".import-completed-msg")
    public FluentWebElement importCompletedMsg;

    @FindBy(css = ".pre-import-processing .import-processing-summary:nth-child(7) .import-total .count")
    public FluentWebElement totalProductsCount;

    @FindBy(css = ".pre-import-processing .import-processing-summary:nth-child(5)  .import-failed .count")
    public FluentWebElement failedProductsCount;

    @FindBy(css = ".import-processed .count")
    public FluentWebElement processedProductsCount;

    @FindBy(css=" .progress-text")
    public FluentWebElement indexingPercentage;

    @FindBy(css=".status-progress-msg")
    public FluentWebElement indexStatus;

    @FindBy(css=".align-center a")
    public FluentWebElement consoleUi;

    @FindBy(css=".overview-menu")
    public FluentWebElement consoleOverViewTab;

    @FindBy(css=".status")
    public FluentWebElement feedUploadStatus;

    @FindBy(css=".number-products")
    public FluentWebElement noOfUploadedProducts;

    @FindBy(css=".RCB-btn")
    public FluentWebElement proceedButton;

    @FindBy(css=".feed-index-progress .feed-check-msg")
    public FluentWebElement successMsg;

    @FindBy(css=".setup-search-btn")
    public FluentWebElement setUpSearchButton;

    @FindBy(css=".status-fail-msg")
    public FluentWebElement feedFailMsg;

    public static By preImportPage = By.cssSelector(".mapping-summary-title");
    public static By progressBarLoader = By.cssSelector(".RCB-progress-bar");
    public static By PimImportLoader=By.cssSelector(".progress-bar-striped.active");
    public static By feedUploadLoader = By.cssSelector(".circle-loader");
    public static By successTick = By.cssSelector(".success-tick");
    public String importLoaderName = "Import Loader";

    public String saveButtonName = " Save Button ";
    public String importCompletedMsgName = "import Completed Msg";
    public String totalProductsCountName = "Total Products Count";
    public String failedProductsCountName = "Failed Products Count";
    public String processedProductsCountName = "Processed Products Count";
    public String feedUploadLoaderName = "indexing in progress";
    public String feedUploaderName = "Feed uploaders";
    public String successFeedUploadName = "Successful feed uploaded";

    public static By loadingOverLay =  By.cssSelector(".loading-btn-overlay");
    public static By pageLoader = By.cssSelector(".progress-bar-contapp-body .page-loader");
    public static By imageLoader= By.cssSelector(".loader-img");


    public static By consoleUi1 = By.cssSelector(".align-center a");
    public static By urlUpload = By.cssSelector(".qa-new-import button.secondary-btn:nth-child(3)");

    public final static String type="div div.configure-table__column:nth-child(4)";
}
