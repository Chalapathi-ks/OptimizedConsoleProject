package core.consoleui.page;

import lib.EnvironmentConfig;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.*;


public class BrowsePage extends ConsoleCommonPage  {

    public String getUrl()
    {
        return  BROWSE_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }

}
