package core.consoleui.actions;

import core.consoleui.page.SynonymsPage;
import lib.constants.UnbxdErrorConstants;
import lib.compat.FluentWebElement;
import org.testng.Assert;

import static lib.constants.UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE;

public class SynonymActions extends SynonymsPage {


public String createSynonym(String synonym, String uni, String bi) throws InterruptedException
{
    awaitForElementPresence(synonymCreationButton);
    click(synonymCreationButton);
    Assert.assertTrue(awaitForElementPresence(synonymCreateWindow));

    synonymInput.fill().with(synonym);
    awaitForElementPresence(createSynonymButton);

    if(uni!=null && uni!="") {
        unidirections.click();
        unidirectionalInput.fill().with(uni);
    }

    if(bi!=null && bi!=""){
        bidirections.click();
        bidirectionalInput.fill().with(bi);
    }

    createSynonymButton.click();
    Assert.assertTrue(checkSuccessMessage(),SUCCESS_MESSAGE_FAILURE);
    awaitForElementNotDisplayed(synonymCreateWindow);
    //Assert.assertTrue(checkSuccessMessage(), UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE);
    return synonym;

}

public String createSynonym() throws InterruptedException {

    String synonymName="autoSyno"+ System.currentTimeMillis();
    String uniDirectionalName="autoSynouni"+ System.currentTimeMillis();
    String biDirectional="autoSynobi"+ System.currentTimeMillis();

    return createSynonym(synonymName,uniDirectionalName,biDirectional);
}




public FluentWebElement getSynonymByIndex(int index)
{
    return createdItemList.get(0);
}

public FluentWebElement getKeyWordsByName(String name) throws InterruptedException
{
    click(synonymsSearchBox);
    searchBoxOpen.fill().with(name);
    awaitForElementPresence(synonymsLists);
    for (int i = 0; i< find(".rdt_TableBody .rdt_TableRow").size(); i++)
    {
        if(find(".rdt_TableBody .rdt_TableRow").get(i).find(keywordCssLocator).getValue().trim().contains(name))
        {
            ThreadWait();
            return find(".rdt_TableBody .rdt_TableRow").get(i);
        }
    }
    return null;
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

    public void deleteCreatedKeyword(String synonymName) throws InterruptedException {
        FluentWebElement synonym= getKeyWordsByName(synonymName);

        Assert.assertTrue(awaitForElementPresence(deleteButton),"Delete button is not visible");
        //click(deleteButton);
        synonym.find(deleteButtonCssLocator).click();
        Assert.assertTrue(awaitForElementPresence(deleteConfirmationWindow),"Delete Confirmation Window is not getting Opened");
        deleteYesSynonymButton.click();
        awaitForElementNotDisplayed(PageLoader);
        Assert.assertTrue(checkSuccessMessage(),SUCCESS_MESSAGE_FAILURE);
    }
}
