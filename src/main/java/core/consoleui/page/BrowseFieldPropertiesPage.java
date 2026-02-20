package core.consoleui.page;

import core.ui.page.UiBase;
import lib.EnvironmentConfig;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.BROWSE_FIELD_PROPERTIES_PAGE;
public class BrowseFieldPropertiesPage extends UiBase
{
    public String getUrl()
    {
        return  BROWSE_FIELD_PROPERTIES_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }
}
