package UnbxdTests.testNG.consoleui.BulkUpload;


import UnbxdTests.testNG.consoleui.MerchTest.MerchandisingTest;
import UnbxdTests.testNG.dataProvider.ResourceLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.consoleui.actions.ABActions;
import core.consoleui.actions.CommercePageActions;
import core.consoleui.page.BrowsePage;
import lib.annotation.FileToTest;
import lib.enums.UnbxdEnum;
import lib.compat.Page;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static core.ui.page.UiBase.ThreadWait;

public class promotionBulkUpload extends MerchandisingTest {

    String query;

    List<String> queryRules = new ArrayList<>();
    List<String> pageRules = new ArrayList<>();

    @Page
    CommercePageActions searchPageActions;

    @Page
    BrowsePage browsePage;

    @Page
    ABActions abActions;

    public String page;



    //Search test cases
    @FileToTest(value = "/consoleTestData/ABsearchBoost.json")
    @Test(description = "SEARCH PAGE AB RULE: Creates and verifies the campaign creation with Boost and AB condition ", priority = 1, dataProviderClass = ResourceLoader.class, dataProvider = "getTestDataFromFile", groups = {"merchandising","sanity"})
    public void abCreateAndPreviewTestForSearch(Object jsonObject) throws InterruptedException
    {

        goTo(searchPage);
        searchPage.threadWait();

    }


}

