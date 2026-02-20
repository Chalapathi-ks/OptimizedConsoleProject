package core.ui.actions;

import core.ui.page.WelcomePage;
import lib.EnvironmentConfig;
import lib.UrlMapper;
import lib.compat.FluentWebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.Serializable;
import java.util.Map;


public class LoginActions extends WelcomePage {

    public void login()
    {
        // Default login Uses SiteId 0 and UserId 1
        login(0,1);

    }

    public void login(int siteId,int userId)
    {
        EnvironmentConfig.setContext(userId,siteId);
        String email, password;
        email= EnvironmentConfig.getEmail();
        password=EnvironmentConfig.getPassword();
        loginWith(email,password);
    }

    public void unbxdLogin(int siteId,int userId)
    {
        goTo("https://console.unbxd.io/unbxdlogin");
        click(unbxdLoginButton);
        EnvironmentConfig.setContext(userId,siteId);
        String email, password;
        email= EnvironmentConfig.getEmail();
        password=EnvironmentConfig.getPassword();
        loginWithUnbxd(email,password);

    }

    public String getUrl(){
//        return UrlMapper.LOGIN.getUrlPath();
        return UrlMapper.LOGIN.getUrlPathFromAppUrl(EnvironmentConfig.getLoginUrl());
    }

    private void loginWith(String email,String pwd) {
        goTo(this);
        awaitForPageToLoadQuick();
        Assert.assertTrue(awaitForElementPresence(loginTitle), "Login page is not yet loaded");
        awaitForElementPresence(emailInputBox);
        click(emailInputBox);
        emailInputBox.clear();
        emailInputBox.fill().with(email);
        ThreadWait();
        awaitForElementPresence(passwordInputBox);
        click(passwordInputBox);
        passwordInputBox.clear();
        passwordInputBox.fill().with(pwd);
        ThreadWait();
        click(signIn);
        awaitForPageToLoad();
        ThreadWait();
        String currentUrl = getDriver().getCurrentUrl();
        if (currentUrl != null && currentUrl.contains("/login")) {
            System.out.println("Still on login page after sign-in attempt, login may have failed");
            if (awaitForElementPresence(getErrorMessageInLogin)==true) {
                System.out.println("Login is not working!!! The reason is " + getErrorMessageInLogin.getText());
            }
            Assert.fail("Login failed - still on login page: " + currentUrl);
        }
    }

    private void loginWithUnbxd(String email,String pwd) {
        awaitForElementPresence(unbxdLoginButton);
        click(unbxdLoginButton);
        unbxdEmailInputBox.clear();
        unbxdEmailInputBox.getElement().sendKeys(email);
        unbxdEmailInputBox.getElement().sendKeys(Keys.ENTER);
        awaitForElementPresence(unbxdPasswordInputBox);
        click(unbxdPasswordInputBox);
        unbxdPasswordInputBox.clear();
        unbxdPasswordInputBox.fill().with(pwd);
        unbxdPasswordInputBox.getElement().sendKeys(Keys.ENTER);
        threadWait();
        awaitForPageToLoad();
        if (awaitForElementPresence(unbxdEmailInputBox)==false) {
            System.out.println("UNBXD Login is working");
        } else {
            Assert.fail("UNBXD Login is not working properly");
        }
    }

}
