package core.consoleui.page;

import core.ui.page.UiBase;
import lib.EnvironmentConfig;
import lib.UrlMapper;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.CONFIGURE_SITE_SEARCH_PAGE;

public class ConfigureSitePage extends UiBase {

    @FindBy(css=".custom-nav-pills li:nth-child(1)")
    public FluentWebElement keysTab;

    @FindBy(css=".custom-nav-pills li:nth-child(2)")
    public FluentWebElement apiTab;

    @FindBy(css=".custom-nav-pills li:nth-child(3)")
    public FluentWebElement catalogTab;

    @FindBy(css=".custom-nav-pills li:nth-child(1)")
    public FluentWebElement fieldPropertiesTab;






    public String getUrl()
      {
          awaitForPageToLoad();
          return  CONFIGURE_SITE_SEARCH_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
      }

  }
