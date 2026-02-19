package core.consoleui.page;

import core.ui.page.UiBase;
import lib.EnvironmentConfig;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.CATALOG_SEARCH_PAGE;

public class CatalogPage extends UiBase
{
    @FindBy(css=".RCB-notif-success.dimension-notif")
    public FluentWebElement dimensionSuccessMessage;;


    public String getUrl()
    {
        awaitForPageToLoad();
        return  CATALOG_SEARCH_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }
}
