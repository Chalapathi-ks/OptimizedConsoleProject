package core.consoleui.page;

import core.consoleui.actions.ContentActions;
import lib.EnvironmentConfig;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.VECTOR_SEARCH;

public class VariantPage extends CampaignCreationPage {

    @FindBy(css = ".variant-rule")
    public FluentWebElement variantLock;

    @FindBy(xpath = "//*[contains(text(),'Variant ID')]//following::*[@class='pin-sel-summary']")
    public FluentWebElement VpinningPositionDropdown;

    @FindBy(css = ".text-transform-none.clip-content")
    public FluentList<FluentWebElement> variantsProductsList;

    // Pinning dropdown elements
    @FindBy(css = ".pin-sel-summary")
    public FluentWebElement pinningDropdown;

    @FindBy(css = ".RCB-list-item.RCB-pid-dd-item ")
    public FluentList<FluentWebElement> pinningDropDownList;

    // Preview page elements
    @FindBy(css = ".preview-main-block")
    public FluentWebElement previewMainBlock;

    @FindBy(css = ".preview-card-container")
    public FluentWebElement previewCardContainer;

    @FindBy(css = ".column-view")
    public FluentList<FluentWebElement> columnViews;

    // Product card elements
    @FindBy(css = ".product-card")
    public FluentList<FluentWebElement> productCards;

    @FindBy(css = ".product-idx")
    public FluentList<FluentWebElement> productIndices;

    @FindBy(css = ".pin-label-btn")
    public FluentList<FluentWebElement> pinLabelButtons;

    @FindBy(css = ".img-card")
    public FluentList<FluentWebElement> imageCards;

    @FindBy(css = ".main-image-container")
    public FluentList<FluentWebElement> mainImageContainers;

    @FindBy(css = ".main-image")
    public FluentList<FluentWebElement> mainImages;

    @FindBy(css = ".product-description")
    public FluentList<FluentWebElement> productDescriptions;

    @FindBy(css = ".product-u-id")
    public FluentList<FluentWebElement> productUIds;

    @FindBy(css = ".additional-info")
    public FluentList<FluentWebElement> additionalInfoSections;

    // Variant section elements
    @FindBy(css = ".variant-section")
    public FluentList<FluentWebElement> variantSections;

    @FindBy(css = ".variant-count-wrapper")
    public FluentList<FluentWebElement> variantCountWrappers;

    @FindBy(css = ".pin-anchor-btn")
    public FluentList<FluentWebElement> pinAnchorButtons;

    @FindBy(css = ".variant-container")
    public FluentList<FluentWebElement> variantContainers;

    @FindBy(css = ".swiper-container")
    public FluentList<FluentWebElement> swiperContainers;

    @FindBy(css = ".swiper-wrapper")
    public FluentList<FluentWebElement> swiperWrappers;

    @FindBy(css = ".swiper-slide")
    public FluentList<FluentWebElement> swiperSlides;

    @FindBy(css = ".swiper-slide-active")
    public FluentList<FluentWebElement> activeSwiperSlides;

    @FindBy(css = ".variant-swatch-container")
    public FluentList<FluentWebElement> variantSwatchContainers;

    @FindBy(css = ".selected-variant-swatch")
    public FluentList<FluentWebElement> selectedVariantSwatches;

    @FindBy(css = ".variant-swatch-container img")
    public FluentList<FluentWebElement> variantSwatchImages;

    // Lock indicator elements
    @FindBy(css = ".shopping-bag-icon-overlay")
    public FluentList<FluentWebElement> shoppingBagIconOverlays;

    @FindBy(css = ".unx-icon-color-selected")
    public FluentList<FluentWebElement> unxIconColorSelected;

    @FindBy(css = ".shopping-bag-icon-overlay .unx-icon-color-selected")
    public FluentList<FluentWebElement> lockIndicators;

    // Swiper navigation buttons
    @FindBy(css = ".swiper-button-prev")
    public FluentList<FluentWebElement> swiperButtonPrev;

    @FindBy(css = ".swiper-button-next")
    public FluentList<FluentWebElement> swiperButtonNext;

    @FindBy(css = ".swiper-button-lock")
    public FluentList<FluentWebElement> swiperButtonLock;

    // Performance data elements
    @FindBy(css = ".performance-data")
    public FluentList<FluentWebElement> performanceDataSections;

    @FindBy(css = ".clickstream-site")
    public FluentList<FluentWebElement> clickstreamSites;

    @FindBy(css = ".clickstream-query")
    public FluentList<FluentWebElement> clickstreamQueries;

    @FindBy(css = ".clickstream-title")
    public FluentList<FluentWebElement> clickstreamTitles;

    @FindBy(css = ".clickstream-query-box")
    public FluentList<FluentWebElement> clickstreamQueryBoxes;

    @FindBy(css = ".clickstream-item")
    public FluentList<FluentWebElement> clickstreamItems;

    @FindBy(css = ".count")
    public FluentList<FluentWebElement> countElements;

    // Additional field items
    @FindBy(css = ".additional-field-item")
    public FluentList<FluentWebElement> additionalFieldItems;

    @FindBy(css = ".field-name")
    public FluentList<FluentWebElement> fieldNames;

    @FindBy(css = ".field-value")
    public FluentList<FluentWebElement> fieldValues;

    // Infinite loader
    @FindBy(css = ".infinite-loader")
    public FluentWebElement infiniteLoader;

    // Variant lock summary elements
    @FindBy(css = ".variant-lock-summary-box")
    public FluentList<FluentWebElement> variantLockSummaryBoxes;

    @FindBy(css = ".summary-text-variant")
    public FluentList<FluentWebElement> summaryTextVariants;

    @FindBy(css = ".variant-title")
    public FluentList<FluentWebElement> variantTitles;

    @FindBy(css = ".variant-lock-summary-li")
    public FluentList<FluentWebElement> variantLockSummaryItems;

    @FindBy(css = ".variant-lock-summary-parent")
    public FluentWebElement variantLockSummaryParent;

    @FindBy(css = ".variant-img")
    public FluentList<FluentWebElement> variantImages;

    // Variant lock modal elements
    @FindBy(css = ".RCB-modal-body")
    public FluentWebElement variantLockModal;

    @FindBy(css = ".RCB-modal-close")
    public FluentWebElement variantLockModalClose;

    @FindBy(css = ".variant-item")
    public FluentList<FluentWebElement> variantItems;

    @FindBy(css = ".variant-id")
    public FluentList<FluentWebElement> variantIds;

    @FindBy(xpath = "//*[@class='variants-grid-container' ]//following::*[contains(text(),'Apply')]")
    public FluentWebElement variantLockApplyButton;

    @FindBy(css = ".product-thumbnail")
    public FluentWebElement productThumbnail;

    @FindBy(css = ".product-id")
    public FluentWebElement productIdInModal;

    @FindBy(css = ".variants-grid")
    public FluentWebElement variantsGrid;

    @FindBy(css = ".anchor-variant-icon")
    public FluentList<FluentWebElement> anchorVariantIcons;

} 