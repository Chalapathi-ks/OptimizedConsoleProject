package core.consoleui.actions;
import core.consoleui.page.searchableFieldsAndFacetsPage;
import core.ui.page.SearchableFieldsPage;
import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;

public class manageSearchableFieldActions extends searchableFieldsAndFacetsPage {

  /* @Page
   SearchableFieldsPage searchableFieldsPage;

    public void saveChanges()
    {
        awaitForElementPresence(saveButton);
        click(saveButton);
    }

    public void selectSearchWeightFromDropdown(String searchWeight) throws InterruptedException {
        awaitForElementPresence(openDropDownArrow);
        Thread.sleep(5000);
        click(openDropDownArrow);
        selectValueBYMatchingText(searchWeight);
    }

    public void verifySuccessMessage()
    {
        if(awaitForElementPresence(searchableFieldSuccessMessage)==true)
        {
            System.out.println("CHANGES UPDATED SUCCESSFULLY!!!: "+ searchableFieldSuccessMessage.getText());
            ThreadWait();
        }
        else {
            Assert.fail("CHANGES ARE NOT GETTING UPDATED");
        }
    }

    public void refreshAiRecommendation() throws InterruptedException {
        awaitForElementPresence(refreshAiRecommendations);
        click(refreshAiRecommendations);
        ThreadWait();
        awaitForElementPresence(searchableFieldsPage.applyAiRecommendationPopup);
        click(searchableFieldsPage.applyAiRecommendationPopup);
    }*/
   }