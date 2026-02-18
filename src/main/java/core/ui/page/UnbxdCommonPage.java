package core.ui.page;

import core.ui.actions.PreviewActions;
import lib.enums.UnbxdEnum;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class UnbxdCommonPage extends UiBase {

    @Page
    PreviewActions previewActions;

    public String slotIcon = ".slot-lock-icon";

    @FindBy(xpath = "//ul[contains(@class,'unx-site-sidebar-menus')]/li[contains(@class,'unx-segment-menu')]/a")
    public FluentWebElement segments;

    @FindBy(xpath = "//ul[contains(@class,'unx-site-sidebar-menus')]/li[contains(@class,'unx-merchandise-menu')]")
    public FluentWebElement merchandising;

    @FindBy(xpath = "//li[contains(@class,'unx-product-widget')/div[contains(@class,'unx-switch-prod')]]")
    public FluentWebElement switchProducts;

    @FindBy(xpath = "//ul[contains(@class,'unx-site-sidebar-menus')]/li[contains(@class,'unx-manage-menu')]")
    public FluentWebElement manageConfiguration;

    @FindBy(css=".action-rules-list .action-title span:nth-child(2)")
    public FluentWebElement conditionTitle;

    @FindBy(css=".RCB-list ul .summary-primary-tag")
    public FluentList<FluentWebElement> conditionSummary;

    @FindBy(css = ".promotion-action-btns .RCB-btn-primary")
    public FluentWebElement publishButton;

    public static By successMsgPopUp = By.cssSelector(".mask-loader");

    @FindBy(css=".rule-content .align-center .RCB-btn-primary")
    public FluentWebElement AddBoostRuleButton;

    @FindBy(css=".active-add-icon")
    public FluentWebElement AddFilterOrBoostRuleButton;

    @FindBy(css=".promotion-actions-list li:nth-child(1)")
    public FluentWebElement globalBoostButton;

    @FindBy(css=".promotion-actions-list li:nth-child(2)")
    public FluentWebElement globalFilterButton;

    @FindBy(css=".flex-space-center .RCB-btn-small:nth-child(2)")
    public FluentWebElement applyButton;

    @FindBy(css = ".promotion-action-btns button:nth-child(1)")
    public FluentWebElement saveAsDraftButton;

    @FindBy(id="slotStart")
    public FluentWebElement slotFirstPosition;

    @FindBy(id="slotEnd")
    public FluentWebElement slotEndPosition;

    @FindBy(css = ".align-right button")
    public FluentWebElement  nextButton;

    @FindBy(css = ".page-title-section .primary-nav li:nth-child(1)")
    public FluentWebElement promotionSection;

    @FindBy(css = ".page-title-section .primary-nav li:nth-child(2)")
    public FluentWebElement  bannerSection;

    @FindBy(css = ".page-title-section .primary-nav li:nth-child(3)")
    public FluentWebElement  facetSection;

    @FindBy(css = ".page-title-section .primary-nav li:nth-child(4)")
    public FluentWebElement  redirectSection;

    public static By prePublishScreenprogress = By.cssSelector(".pre-publish");

    @FindBy(css=".unx-qa-success-confirmation")
    public FluentWebElement successMessageForMerchPublish;

    @FindBy(css=".success-icon")
    public FluentWebElement successIcon;

    @FindBy(css=".preview-btn")
    public FluentWebElement websitePreviewTab;

    @FindBy(css=".msg-container .failure-icon")
    public FluentWebElement publishFailMsg;

    @FindBy(css=".msg-container .publish-error-msg-header")
    public FluentWebElement publishErrorMsg;

    @FindBy(css = ".promotion-action-btns button")
    public FluentWebElement fieldRulePublishBtn;

    @FindBy(css = ".RCB-list-item.RCB-pid-dd-item")
    public FluentList<FluentWebElement> pinningDropDownList;

    @FindBy(css = ".pin-sel-summary")
    public FluentWebElement pinningDropdown;

    @FindBy(css = ".unx-icon-Shape,.pinned-badge")
    public FluentWebElement pinnedProductText;

    @FindBy(css = ".unpinned-badge")
    public FluentList<FluentWebElement> pinTheProduct;

    @FindBy(css = ".product-card")
    public FluentList<FluentWebElement> listOfProductInPreview;
    @FindBy(css=".promotion-rules-summary")
    public FluentWebElement previewSummary;

    @FindBy(css = ".product-card:has(.pinned-badge) .product-idx,.product-card:has(.unx-icon-Shape) .product-idx")
    public FluentList<FluentWebElement>  pinnedProductIndex;

    @FindBy(css =".product-card")
    public  FluentList<FluentWebElement> productTitleCard;

    @FindBy(css=".RCB-list-item")
    public FluentList<FluentWebElement> sortAttributeList;

    @FindBy(css=".product-title")
    public FluentList<FluentWebElement> productTitle;

    @FindBy(css=".promotion-rules-summary .action-rules-list")
    public FluentList<FluentWebElement> merchandisingConditionList;

    @FindBy(css=".promotion-rules-summary .action-rules-list")
    public FluentWebElement merchandisingCondition;

    @FindBy(css=".RCB-dd-search-ip")
    public FluentWebElement searchInput;

    @FindBy(css=".merch-delete-icon")
    public FluentWebElement deleteThePromtionMerchandizingSet;

    @FindBy(css=".promotion-filters-list")
    public FluentList<FluentWebElement> conditionsList;

    @FindBy(css=".unx-qa-active")
    public FluentWebElement activeStatus;

    @FindBy(css=" .unx-qa-upcoming")
    public FluentWebElement upcomingStatus;


    @FindBy(css=".campaign-name-promotions,.campaign-name-banners")
    public FluentWebElement campaignPromotions;





    public void goTo(UnbxdEnum section)
    {

        FluentWebElement sectionElement=null;
        if(!merchandising.getAttribute("class").contains("nav-active"))
            click(merchandising.findFirst("a i.glyphicon-menu-down"));
        getModuleByName(section).click();
        awaitForPageToLoad();
    }

    public void goToConfiguration(UnbxdEnum configurationType)
    {
        manageConfiguration.findFirst("a i.glyphicon-menu-down").click();
        FluentWebElement element=manageConfiguration.findFirst("a:contains('"+configurationType.getLabel()+"')");
        element.click();
        awaitForPageToLoad();
    }


    public void switchToProduct(UnbxdEnum type)
    {

        click(switchProducts.findFirst("a"));
        getDriver().findElement(By.xpath("//ul[contains(@class,'unx-product-list')]//li//a[contains(text(),"+type.getLabel()+")]"));
        awaitForPageToLoad();
    }

    public WebElement getModuleByName(UnbxdEnum module)
    {
        String xpath="//ul[@class='sub-menu-list']//a//span[contains(text(),'"+module.getLabel()+"')]";
        return getDriver().findElement(By.xpath(xpath));

    }


    public boolean checkModalWindow()
    {
        if(findFirst("body").getAttribute("class").contains("modal-open"))
            return true;

        else
            return false;
    }

    public void goToSection(UnbxdEnum type) throws InterruptedException {
        ThreadWait();
        WebDriverWait wait = new WebDriverWait(getDriver(), 15);
        switch(type)
        {
            case PROMOTION:
                click(promotionSection);
                wait.until(ExpectedConditions.urlContains("promotions"));
                Assert.assertTrue(getDriver().getCurrentUrl().contains("promotions"));
                break;
            case BANNER:
                awaitForElementPresence(bannerSection);
                safeClick(bannerSection);
                ThreadWait();
                break;
            case FACETS:
                click(facetSection);
                wait.until(ExpectedConditions.urlContains("facets"));
                Assert.assertTrue(getDriver().getCurrentUrl().contains("facets"));
                break;
            case REDIRECT:
                click(redirectSection);
                wait.until(ExpectedConditions.urlContains("redirects"));
                Assert.assertTrue(getDriver().getCurrentUrl().contains("redirects"));
                break;
            default:
        }
    }

    public void verifyRuleCreation()
    {
        waitForLoaderToDisAppear(prePublishScreenprogress,"publish inprogress");
        await();
        if(awaitForElementPresence(publishFailMsg)==true)
        {
            Assert.fail("PROMOTIONS PUBLISHING IS FAILING");
        }
        else {
            System.out.println("PUBLISHED SUCCESSFULLY,Verifying it in promotion listing page!!!");
        }
    }

    public void goToPreviewPageFromConsole() {
        scrollToTop();
        awaitForElementPresence(websitePreviewTab);
        click(websitePreviewTab);
        awaitForPageToLoad();
        switchTabTo(1);
        if (awaitForElementPresence(previewActions.templatePopupCloseButton)) {
            click(previewActions.templatePopupCloseButton);
        }
        String previewUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(previewUrl.contains("preview"));
    }

    public void verifySuccessMessage()
    {
        awaitTillElementDisplayed(successIcon);
    }
//        if(awaitTillElementDisplayed(successIcon))
//        {
//           System.out.println("PUBLISHED SUCCESSFULLY,Verifying it in promotion listing page!!!");
//        }
//        else {
//            Assert.fail("PROMOTIONS PUBLISHING IS FAILING");
//            ThreadWait();
//        }
//    }
}
