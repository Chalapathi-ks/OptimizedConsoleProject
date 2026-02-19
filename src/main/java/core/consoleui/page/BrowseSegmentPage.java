package core.consoleui.page;

import lib.EnvironmentConfig;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.BROWSE_SEGMENT_PAGE;
import static lib.UrlMapper.SEGMENT_PAGE;


public class BrowseSegmentPage extends ConsoleCommonPage  {



    public String getUrl()
    {
        return BROWSE_SEGMENT_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }
}
