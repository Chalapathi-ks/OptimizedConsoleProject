/*package UnbxdTests.testNG.ui.SearchManageTest;

import UnbxdTests.testNG.dataProvider.ResourceLoader;
import UnbxdTests.testNG.ui.BaseTest;
import com.google.gson.JsonObject;
import core.consoleui.actions.AutoSuggestActions;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.actions.SearchManageAutoSuggestActions;
import core.consoleui.page.searchAutosuggestPage;
import core.ui.actions.LoginActions;
import lib.Helper;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import lib.compat.Page;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import lib.EnvironmentConfig;

import java.util.ArrayList;
import java.util.List;

public class manageSearchAutosuggestTest extends BaseTest {

    @Page
    LoginActions loginActions;



    @Page
    AutoSuggestActions autoSuggestActions;

    @Page
    SearchManageAutoSuggestActions searchManageAutoSuggestActions;

    @Page
    searchAutosuggestPage SearchAutosuggestPage;

    @Page
    public
    CommercePageActions searchPage;

    private String autoSuggestSection = "AUTO_SUGGEST";

    private String pageCount = "100";



    @BeforeClass
    public void init() throws InterruptedException {
        super.setUp();
        this.initFluent(driver);
        initTest();
        // Set context before navigation - using site 1 and user 1 as per ProdAPAC.yaml configuration
        EnvironmentConfig.unSetContext();
        EnvironmentConfig.setContext(1, 1);
        // Login is now handled by BaseTest global login
        searchPage.threadWait();
    }

    @FileToTest(value = "/manageFacetAndSearchableFieldTest/predefinedKeyword.json")
    @Test(description = "This test Verifies predefined keyWordSuggestion are coming or not", priority = 10, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void KeywordSuggestionTest(JsonObject object) throws InterruptedException
    {
       /* String suggestion = object.get("suggestionSection").getAsString();
        String dropDownValue = object.get("dropDownValue").getAsString();

        goTo(SearchAutosuggestPage);
        searchPage.threadWait();
        autoSuggestActions.clickOnCustomiseButton();
        autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion));
        searchManageAutoSuggestActions.selectKeywordSuggestion(dropDownValue);
        searchPage.threadWait();
        searchManageAutoSuggestActions.verifySuccessMessage();

    }

    @FileToTest(value = "/manageFacetAndSearchableFieldTest/predefinedInfields.json")
    @Test(description = "This test Verifies predefined infields are coming or not", priority = 11, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void InfieldTest(JsonObject object) throws InterruptedException {
        String suggestion = object.get("suggestionSection").getAsString();
        String dropDownValue = object.get("dropDownValue").getAsString();

        goTo(SearchAutosuggestPage);
        autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion));

        goTo(SearchAutosuggestPage);
        searchPage.threadWait();
        autoSuggestActions.clickOnCustomiseButton();
        autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion));
        searchManageAutoSuggestActions.selectKeywordSuggestion(dropDownValue);
        searchPage.threadWait();
        searchManageAutoSuggestActions.verifySuccessMessage();

    }

    @FileToTest(value = "/manageFacetAndSearchableFieldTest/predefinedPopularProducts.json")
    @Test(description = "This test Verifies predefined popular products are coming or not", priority = 12, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile")
    public void PopularProductsTest(JsonObject object) throws InterruptedException {
        String suggestion = object.get("suggestionSection").getAsString();
        String dropDownValue = object.get("dropDownValue").getAsString();


        goTo(SearchAutosuggestPage);
        autoSuggestActions.scrollToElement(autoSuggestActions.popularProductsSection, "popularProduct");
        autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion));

        goTo(SearchAutosuggestPage);
        searchPage.threadWait();
        autoSuggestActions.clickOnCustomiseButton();
        autoSuggestActions.goToSuggestionSectionsByName(UnbxdEnum.valueOf(suggestion));
        searchManageAutoSuggestActions.selectKeywordSuggestion(dropDownValue);
        searchPage.threadWait();
        searchManageAutoSuggestActions.verifySuccessMessage();
        searchManageAutoSuggestActions.verifyPublishAutosuggestField();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.close();
        driver.quit();
        Helper.tearDown();*/
//    }
//}
