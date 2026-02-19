package core.ui.page;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

public class CreateSitePage extends WelcomePage{

    //@FindBy(css=".unx-onboard-title")
    @FindBy(css=".unx-site-name .RCB-form-el-label")
    public FluentWebElement onboardingTitle;

    @FindBy(css=".unx-env-list .RCB-select-arrow")
    public FluentWebElement environmentDropdown;

    @FindBy(css=".unx-vertical-list .RCB-select-arrow")
    public FluentWebElement verticalDropdown;

    @FindBy(css=".unx-platform-list .RCB-select-arrow")
    public FluentWebElement platformDropdown;

    @FindBy(css=".unx-language-list .RCB-select-arrow")
    public FluentWebElement languageDropdown;

    @FindBy(name="siteName")
    public FluentWebElement siteNameInput;

    @FindBy(css=".unx-create-site .RCB-btn-small")
    public FluentWebElement proceedButton;

    @FindBy(css=".unx-vertical-list span")
    public FluentWebElement verticalInputText;

    @FindBy(css=".unx-platform-list span")
    public FluentWebElement platformInputText;

    @FindBy(css=".unx-language-list span")
    public FluentWebElement langInputText;

    @FindBy(css=".intercom-notifications button")
    public FluentWebElement intercomClearTab;

    @FindBy(css = ".intercom-snippet")
    public FluentWebElement intercomSnippet;

    @FindBy(css=".unx-env-list span")
    public FluentWebElement envInputText;

    @FindBy(css=".catalog-question")
    public FluentWebElement catalogUploadPageTitle;


    @FindBy(css = ".RCB-list li")
    public FluentList<FluentWebElement> dropDownValues;

    @FindBy(css=".keys-container:nth-child(1) .key-value")
    public FluentWebElement siteKeyInput;

    @FindBy(css=".keys-container:nth-child(2) .key-value")
    public FluentWebElement apiKeyInput;

    @FindBy(css=".keys-container:nth-child(3) .key-value")
    public FluentWebElement secreteKeyInput;

    @FindBy(css=".catalog-question")
    public FluentWebElement uploadPageText;

    @FindBy(css=".failure-icon")
    public FluentWebElement siteCreationFailIcon;

    @FindBy(css=".status-fail-msg")
    public FluentWebElement siteCreationFailureMsg;

    @FindBy(css=".status-fail-reason")
    public FluentWebElement siteCreationFailureReason;

    public static By creatingSiteLoadingButton= By.cssSelector(".RCB-btn-loading");

    public static By catalogPage = By.cssSelector(".catalog-question");


    public static By onboardingPageLoader =By.cssSelector(".unx-onboard-title");

    public String catalogLoader= "catalogPage loader";
    public String createSiteLoader = "CreateSite Loader";








}
