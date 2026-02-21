package core.consoleui.actions;

import core.consoleui.page.SynonymsPage;
import lib.constants.UnbxdErrorConstants;
import lib.compat.FluentWebElement;
import org.testng.Assert;

import static lib.constants.UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE;

public class ContentActions extends SynonymsPage {



    public String createStemming() throws InterruptedException {
        String stem="autoStem"+ System.currentTimeMillis();
        String stemmed="autoStemmed"+ System.currentTimeMillis();

        awaitForElementPresence(synonymCreationButton);
        click(synonymCreationButton);
        Assert.assertTrue(awaitForElementPresence(synonymCreateWindow));

        synonymInput.fill().with(stem);
       // stemmedWordInput.fill().with(stemmed);
        unidirectionalInput.fill().with(stemmed);

        awaitForElementPresence(createSynonymButton);
        createSynonymButton.click();
        awaitForElementNotDisplayed(synonymCreateWindow);
        return stem;
    }

    public FluentWebElement getSynonymByIndex(int index)
    {
        return createdItemList.get(0);
    }

    public void editKeyword(FluentWebElement element, String keyword, String uni, String bi)
    {
        if(keyword!=null && keyword!="") {
            element.findFirst(keyWord).click();
            keyWordInputEdit.fill().with(keyword);
        }
        if(uni!=null && uni!="") {
            element.findFirst(unidirection).click();
            unidirectionalInputEdit.fill().with(uni);
        }
        if(bi!=null && bi!="") {
            element.findFirst(bidirection).click();
            bidirectionalInputEdit.fill().with(bi);
        }
    }

    public FluentWebElement getKeyWordsByName(String name) throws InterruptedException
    {
        click(synonymsSearchBox);
        click(searchBoxOpen);
        ThreadWait();
        synonymsSearchBox.clear();
        synonymsSearchBox.fill().with(name);
        awaitForElementPresence(synonymsLists);
        for (int i = 0; i< createdItemList.size(); i++)
        {
            if(createdItemList.get(i).find(keywordCssLocator).getValue().trim().equals(name))
            {
                ThreadWait();
                return createdItemList.get(i);
            }
        }
        return null;
    }

    public void selectCount()
    {
        awaitForElementPresence(countDropDown);
        click(countDropDown);
        awaitForElementPresence(selectCount100);
        click(selectCount100);
    }

    public FluentWebElement getUniDirectionalSynonym(FluentWebElement synonym)
    {
        return synonym.findFirst(unidirectionalCssLocator);
    }

    public FluentWebElement getStemmedWord(FluentWebElement stem)
    {
        return stem.findFirst(stemmedWord);
    }


    public FluentWebElement getBiDirectionalSynonym(FluentWebElement synonym)
    {
        return synonym.findFirst(biDirectionalCssLocator);
    }

    public FluentWebElement getKeyWord(FluentWebElement synonym)
    {
        return synonym.findFirst(keywordCssLocator);
    }



    public void saveChanges()
    {
        awaitForElementPresence(saveChangesButton);
        saveChangesButton.click();
    }

    public int getSynonymsSize(String keyword) throws InterruptedException {
        ThreadWait();
        if(createdItemList.size()==0)
            return 0;
        if(!searchBoxOpen.getAttribute("class").contains("hide")) {

            searchBoxOpen.click();
        }

        synonymsSearchBox.fill().with(keyword);
        awaitForElementNotDisplayed(PageLoader);
        int size= createdItemList.size();

        synonymsSearchBox.clear();
        awaitForElementNotDisplayed(PageLoader);

        return size;

    }

    public void deleteCreatedKeyword(String synonymName) throws InterruptedException {
        FluentWebElement synonym= getKeyWordsByName(synonymName);
        ThreadWait();
        synonym.findFirst(deleteCssLocator).click();
        Assert.assertTrue(awaitForElementPresence(deleteButton),"Delete button is not visible");
        click(deleteButton);
        Assert.assertTrue(awaitForElementPresence(deleteConfirmationWindow),"Delete Confirmation Window is not getting Opened");
        deleteYesSynonymButton.click();
        awaitForElementNotDisplayed(PageLoader);
        Assert.assertTrue(checkSuccessMessage(),SUCCESS_MESSAGE_FAILURE);
    }


    public void createSynonym(String keyword, String unidirectional, String bidirectional) {
        awaitForElementPresence(synonymCreationButton);
        synonymCreationButton.click();
        
        awaitForElementPresence(synonymCreateWindow);
        awaitForElementPresence(synonymInput);
        synonymInput.fill().with(keyword);
        
        awaitForElementPresence(unidirections);
        awaitForElementPresence(unidirectionalInput);
        unidirectionalInput.fill().with(unidirectional);
        
        awaitForElementPresence(bidirections);
        awaitForElementPresence(bidirectionalInput);
        bidirectionalInput.fill().with(bidirectional);
        
        awaitForElementPresence(createSynonymButton);
        createSynonymButton.click();
        
        // Wait for the window to disappear
        awaitForElementNotDisplayed(synonymCreateWindow);
    }

}
