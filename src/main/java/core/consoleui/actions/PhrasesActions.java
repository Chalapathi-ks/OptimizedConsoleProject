package core.consoleui.actions;

import core.consoleui.page.PhrasesPage;
import core.ui.page.UiBase;
import org.fluentlenium.core.domain.FluentWebElement;
import org.testng.Assert;
import java.util.List;
import org.fluentlenium.core.annotation.Page;
import core.consoleui.actions.SynonymActions;


public class PhrasesActions extends PhrasesPage {

@Page SynonymActions synonymActions;

        
        public String createPhrase(String synonym, String uni, String bi) throws InterruptedException
            {
    String phraseName = "auto phrases" + System.currentTimeMillis();
    Thread.sleep(10000);
    awaitForElementPresence(synonymActions.synonymCreationButton);
    click(synonymActions.synonymCreationButton);
    awaitForElementPresence(synonymActions.synonymCreateWindow);
    Thread.sleep(4000);
    Assert.assertTrue(awaitForElementPresence(synonymActions.synonymCreateWindow));

    synonymActions.synonymInput.fill().with(synonym);
    awaitForElementPresence(synonymActions.createSynonymButton);

    if(uni!=null && uni!="") {
        await();
        synonymActions.unidirections.click();
        await();
        synonymActions.unidirectionalInput.fill().with(uni);
    }

    if(bi!=null && bi!=""){
        await();
        synonymActions.bidirections.click();
        await();
        synonymActions.bidirectionalInput.fill().with(bi);
    }

    synonymActions.createSynonymButton.click();
    Assert.assertTrue(checkSuccessMessage(),"Phrase was not created successfully");
    awaitForElementNotDisplayed(synonymActions.synonymCreateWindow);
    //Assert.assertTrue(checkSuccessMessage(), UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE);
    return synonym;
}
       
        
    

    public FluentWebElement getPhraseByName(String name) throws InterruptedException {
        await();
        List<FluentWebElement> phraseRows = find(".rdt_TableBody .rdt_TableRow");
        for (FluentWebElement row : phraseRows) {
            String rowText = row.getText();
            if (rowText != null && rowText.contains(name)) {
                return row;
            }
        }
        return null;
    }

    public String createPhrase() throws InterruptedException {
        String phraseName = "auto phrases" + System.currentTimeMillis();
        return createPhrase(phraseName, null, null);
    }

    // You can add edit and delete methods here if needed, or use inherited ones if present
} 