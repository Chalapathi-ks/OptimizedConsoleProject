package core.consoleui.page;

import core.ui.page.UiBase;
import lib.EnvironmentConfig;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import static lib.UrlMapper.*;

public class BlacklistedSuggestionsPage extends UiBase {

    public String getUrl()
    {
        return  BLACKLISTEDSUGGESTIONS_PAGE.getBaseUrl(EnvironmentConfig.getSiteId());
    }



}