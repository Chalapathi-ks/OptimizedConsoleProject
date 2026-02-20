package core.ui.actions;


import core.ui.page.SignUpPage;
import lib.UrlMapper;
import org.apache.commons.lang.RandomStringUtils;
import org.testng.Assert;

public class SignUpActions extends SignUpPage {

    public String getUrl()
    {
        awaitForPageToLoad();
        return UrlMapper.SIGN_UP.getUrlPath();
    }

    public void goToSignUpPage()
    {
        goTo(this);
        awaitForPageToLoad();
        awaitForElementPresence(signUpTittle);

    }

    public void fillSignUpForm(String username,String email,String pwd,String region)
    {
        awaitForElementPresence(userNameInputBox);
        userNameInputBox.fill().with(username);
        awaitForElementPresence(userEmailInputBox);
        userEmailInputBox.fill().with(email);
        passwordInputBox.fill().with(pwd);
        getRegion(region);
        termsAndConditionCheckBox.click();
        Assert.assertTrue(termsAndConditionCheckBox.isSelected());
    }

    // Generating a Random email String
    public static String generateRandomEmail(int length)
    {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz" + "1234567890" + "_-.";
        String email = "";
        String temp = RandomStringUtils.random(length, allowedChars);
        email = temp.substring(0, temp.length() - 9) + "@gmail.com";
        return email;
    }

    public void signUp()
    {
        click(signUpButton);
        awaitForPageToLoad();
    }

    public boolean checkSignUpSuccessMessage()
    {
        awaitForElementPresence(signUpMessageText);
        return signUpMessageText.isDisplayed();
    }

    public void getRegion(String region)
    {
        click(regionDropDown);
        for(int i=0;i<regionList.size();i++)
        {
            if(regionList.get(i).getText().equalsIgnoreCase(region))
            {
                click(regionList.get(i));
                break;
            }
        }
    }
}
