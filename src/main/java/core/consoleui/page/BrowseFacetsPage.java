package core.consoleui.page;

import core.ui.page.UnbxdCommonPage;
import lib.EnvironmentConfig;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.MANAGE_BROWSE_PAGE;
import static lib.UrlMapper.MANAGE_SEARCH_PAGE;

public class BrowseFacetsPage extends UnbxdCommonPage {

    public String getUrl()
    {
        awaitForPageToLoad();
        return MANAGE_BROWSE_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }




}
