package core.ui.page;

import lib.UrlMapper;
import lib.compat.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends UiBase {

    @FindBy(css=".unx-login-title")
    public FluentWebElement loginTitle;

    @FindBy(css = ".unx-login-btn")
    public FluentWebElement signIn;

    @FindBy(xpath="//*[@type='email']")
    public FluentWebElement unbxdEmailInputBox;

    @FindBy(xpath="//*[@type='password']")
    public FluentWebElement unbxdPasswordInputBox;
    @FindBy(css=".btn.btn-danger")
    public FluentWebElement unbxdLoginButton;

    @FindBy(id = "userEmail")
    public FluentWebElement emailInputBox;

    @FindBy(id = "password")
    public FluentWebElement passwordInputBox;

    @FindBy(css=".login_error .unx-email-error")
    public FluentWebElement getErrorMessageInLogin;


    public String getUrl()
    {
        return UrlMapper.LOGIN.getUrlPath();
    }
}
