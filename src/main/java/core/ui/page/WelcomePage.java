package core.ui.page;

import core.ui.actions.CreateSiteActions;
import core.ui.actions.FeedUploadActions;
import lib.EnvironmentConfig;
import lib.UrlMapper;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import static lib.UrlMapper.WELCOME_PAGE;

public class WelcomePage extends LoginPage{

    @Page
    FeedUploadActions feedUploadActions;

    @FindBy(css = ".action-btns a.secondary-btn.active")
    public FluentWebElement exploreDemoSite;

    @FindBy(css="a[href*='/onboarding']")
    public FluentWebElement configureFirstSite;

    //@FindBy(css=".new-user-landing .unx-welcome-text")
    @FindBy(css=".unx-welcome-text")
    public FluentWebElement welcomeText;

    //@FindBy(css=".new-user-landing .unx-welcome-logo")
    @FindBy(css=".unx-welcome-logo")
    public FluentWebElement unbxdLogo;

    public String welcomePageLoader = "welcomePage loader";

    public static By welcomePageText = By.cssSelector("..unx-welcome-text");


    public WelcomePage gotoWelcomePage()
    {
        goTo(WELCOME_PAGE.getBaseUrl());
        waitForLoaderToDisAppear(feedUploadActions.pageLoader,welcomePageLoader);
        awaitForElementPresence(welcomeText);
        //Assert.assertEquals(welcomeText.getText(),"Welcome to");
        //Assert.assertTrue(awaitForElementPresence(unbxdLogo));
        return this;
    }

    public String getUrl()
    {
        awaitForPageToLoad();
        return WELCOME_PAGE.getBaseUrl();
    }


}
