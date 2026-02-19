package core.ui.page;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpPage extends WelcomePage {

    @FindBy(css=".unx-signup-title")
    public FluentWebElement signUpTittle;

    @FindBy(name="userName")
    public FluentWebElement userNameInputBox;

    @FindBy(name="email")
    public FluentWebElement userEmailInputBox;

    @FindBy(css=".unx-signup-checkbox")
    public FluentWebElement termsAndConditionCheckBox;

    @FindBy(css=".unx-signup-submit-btn")
    public FluentWebElement signUpButton;

    @FindBy(css=".select2-selection__arrow")
    public FluentWebElement regionDropDown;

    @FindBy(css=".select2-results__options li")
    public FluentList<FluentWebElement> regionList;

    @FindBy(css=".success-text")
    public FluentWebElement signUpMessageText;

}
