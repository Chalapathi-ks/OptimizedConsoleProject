package core.consoleui.page;

import lib.EnvironmentConfig;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.*;


public class BrowsePage extends ConsoleCommonPage  {

    public String getUrl()
    {
        return BROWSE_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }

}
