package UnbxdTests.testNG.ui;

import core.ui.actions.SignUpActions;
import lib.constants.UnbxdErrorConstants;
import org.openqa.selenium.json.Json;
import org.testng.annotations.BeforeClass;
import com.google.gson.JsonObject;
import core.ui.actions.CreateSiteActions;
import core.ui.actions.LoginActions;
import lib.Helper;
import lib.annotation.FileToTest;
import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;
import org.testng.annotations.*;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import org.testng.asserts.SoftAssert;

import java.util.NoSuchElementException;

public class SignUpTest extends BaseTest {

    @Page
    SignUpActions signUpActions;

    @BeforeClass
    public void init() {
        super.setUp();
        initFluent(driver);
        initTest();
    }

    @FileToTest(value = "signUpTest.json")
    @Test(description = "Do signUp and verifies welcomePage is coming after signUp ", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void signUpTest(JsonObject object)
    {
        try
        {

        String userName = object.get("userName").getAsString();
        String email = signUpActions.generateRandomEmail(13);
        String pwd = object.get("password").getAsString();
        String region = object.get("region").getAsString();

        signUpActions.goToSignUpPage();
        Assert.assertTrue(signUpActions.signUpTittle.getText().equalsIgnoreCase("Sign Up"));
        signUpActions.fillSignUpForm(userName,email,pwd,region);
        signUpActions.signUp();
        Assert.assertTrue(signUpActions.checkSignUpSuccessMessage(), UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE);
        }
        catch(NoSuchElementException exception) {
            System.out.println("There is no such element present");
        }
        catch (Exception e)
        {
            System.out.println("Got an exception");
        }
    }

    @FileToTest(value = "signUpTest.json")
    @Test(description = "SignUp using already registered emailId and verify the error message", priority = 2, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void duplicateUserSignUp(JsonObject object)
    {
        try
        {
        String userName = object.get("userName").getAsString();
        String email = object.get("email").getAsString();
        String pwd = object.get("password").getAsString();
        String region = object.get("region").getAsString();

        signUpActions.goToSignUpPage();
        Assert.assertTrue(signUpActions.signUpTittle.getText().equalsIgnoreCase("Sign Up"));
        signUpActions.fillSignUpForm(userName,email,pwd,region);
        signUpActions.signUp();
        Assert.assertTrue(signUpActions.checkAlertErrorMessage(),UnbxdErrorConstants.SIGNUP_FAILURE_MESSAGE);
        }
        catch(NoSuchElementException exception) {
            System.out.println("There is no such element present");
        }
        catch (Exception e)
        {
            System.out.println("Got an exception");
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.close();
        driver.quit();
        Helper.tearDown();
    }


}
