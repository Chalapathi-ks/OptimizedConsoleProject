package core.consoleui.page;

import org.fluentlenium.core.annotation.Page;

import lib.EnvironmentConfig;
import static lib.UrlMapper.STOP_WORD;

import core.ui.page.UiBase;

public class StopWordPage extends UiBase {

    



    public String getUrl()
    {
        awaitForPageToLoad();
        return STOP_WORD.getBaseUrl(EnvironmentConfig.getSiteId());

    }
    
    
}
