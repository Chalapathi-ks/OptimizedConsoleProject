package core.ui.page;

import lib.EnvironmentConfig;
import lib.UrlMapper;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class RelevancyPage extends UiBase {


    public String getUrl()
    {
        return UrlMapper.RELEVANCY_PAGE.getBaseUrl(EnvironmentConfig.getStatusById());
    }

    @FindBy(css=".RCB-btn-small")
    public FluentWebElement uploadQueryButton;

    @FindBy(css=".browse-text")
    public FluentWebElement browseQueryFile;

    @FindBy(css=".drag-drop-error")
    public FluentWebElement QueryUploadError;







}

