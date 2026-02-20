package core.ui.actions;

import core.ui.page.AutoSuggestPage;
import lib.Config;
import lib.enums.UnbxdEnum;
import lib.compat.FluentWebElement;

import java.util.ArrayList;
import java.util.List;

public class AutoSuggestActions extends AutoSuggestPage {

    public void clickOnCustomiseButton()
    {
        awaitForElementPresence(autoSuggestCustomiseButton);
        click(autoSuggestCustomiseButton);
    }


    public FluentWebElement getGroup(UnbxdEnum type)
    {
        switch(type)
        {
            case KEYWORD:
                return keywordSuggestionSection;
            case INFIELD:
                return infieldSuggestionSection;
            case TOP_SEARCH:
                return topSearchQuerySection;
            case PROMOTED_SUGGESTION:
                return promotedSuggestionSection;
            case POPULAR_PRODUCTS:
                return  popularProductsSection;
            default:
                return null;
        }
    }

    public void goToSuggestionSectionsByName(UnbxdEnum type)
    {
        threadWait();
        FluentWebElement section = getGroup(type);
        click(section);
    }

    public List<String> getPredefinedSuggestions()
    {
        List<String> listOfPredefinedFields = new ArrayList<>();

        for(int i = 0; i < predefinedFieldsList.size(); i++)
        {
            listOfPredefinedFields.add(predefinedFieldsList.get(i).find("span:nth-child(1)").getText().trim());
            System.out.println("predefined suggestions are" +listOfPredefinedFields);
        }
        return listOfPredefinedFields;
    }

}


