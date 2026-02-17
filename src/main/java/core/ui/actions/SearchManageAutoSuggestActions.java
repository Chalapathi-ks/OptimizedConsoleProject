package core.ui.actions;

import core.consoleui.page.searchAutosuggestPage;
import org.testng.Assert;


public class SearchManageAutoSuggestActions extends searchAutosuggestPage {
    public void selectDropDownByValue(String value)
    {
        for (int i = 0; i < dropDownList.size(); i++) {
            await();
            autosuggestSearch.click();
            autosuggestSearch.fill().with(value);
            ThreadWait();
//            if (dropDownList.get(i).getText().equals(value)) {
//                ThreadWait();
//                dropDownList.get(i).click();
//
//            }
        }

    }

   public void deleteAttributeField(String name) {
        ThreadWait();
        if (awaitForElementPresence(deleteAutosuggestField)) {
            for (int i = 0; i<autosuggestDisplayAttributeList.size(); i++) {
                String Attribute = autosuggestDisplayAttributeList.get(i).getText();
                if (Attribute==name){
                    awaitForElementPresence(deleteAutosuggestField);
                    click(deleteAutosuggestField);
                }else{
                    System.out.println("Filed is not present");
                }

            }
        }
    }


    public void selectKeywordSuggestion(String value)
    {
        //deleteAttributeField(value);
        ThreadWait();
        click(SuggestionAddField);
        click(selectfieldOption);
        threadWait();
        selectDropDownByValue(value);
        click(saveSuggestionField);
        ThreadWait();
        awaitForElementPresence(saveAutosuggestField);
        click(saveAutosuggestField);

    }

    public void KeywordSuggestion(String value)
    {
        //deleteAttributeField(value);
        ThreadWait();
        click(SuggestionAddField);
        //click(selectfieldOption);
        threadWait();
        selectDropDownByValue(value);

    }

    public void verifySuccessMessage()
    {
        if(awaitForElementPresence(autosuggestSuccessMessage)==true)
        {
            System.out.println("ADDED FIELD SUCUESSFULLY!!!: "+autosuggestSuccessMessage.getText());
            ThreadWait();
        }
        else {
            Assert.fail("AUTOSUGGEST CONFIGURATION GOT FAILED");
        }
    }

    public void verifyPublishAutosuggestField()throws InterruptedException {
        awaitTillElementDisplayed(autosuggestPublishButton);
        awaitForElementPresence(autosuggestPublishButton);
        click(autosuggestPublishButton);
        if (awaitForElementPresence(autosuggestPublishSuccessMessage)) {
            System.out.println("CHANGES ARE IN PROGRESS: " + autosuggestPublishSuccessMessage.getText());
            threadWait();
            System.out.println("CHANGES ARE GETTING PUBLISHED AND IT WILL REFLECT IN SOMETIME :" + autosuggestPublishSuccessMessage.getText());
        } else {
            Assert.fail("AUTOSUGGEST PUBLISH IS NOT WORKING PROPERLY!!!");
        }
    }

}


