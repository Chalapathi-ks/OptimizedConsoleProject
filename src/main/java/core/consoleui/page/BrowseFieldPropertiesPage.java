package core.consoleui.page;

import core.ui.page.UiBase;
import lib.EnvironmentConfig;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.BROWSE_FIELD_PROPERTIES_PAGE;
public class BrowseFieldPropertiesPage extends UiBase
{
    public String getUrl()
    {
        awaitForPageToLoad();
        return  BROWSE_FIELD_PROPERTIES_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }
}
