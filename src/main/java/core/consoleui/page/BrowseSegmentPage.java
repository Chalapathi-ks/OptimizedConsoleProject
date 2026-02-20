package core.consoleui.page;

import lib.EnvironmentConfig;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.BROWSE_SEGMENT_PAGE;
import static lib.UrlMapper.SEGMENT_PAGE;


public class BrowseSegmentPage extends ConsoleCommonPage  {



    public String getUrl()
    {
        awaitForPageToLoad();
        return  BROWSE_SEGMENT_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }
}
