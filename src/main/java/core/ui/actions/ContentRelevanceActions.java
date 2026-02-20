package core.ui.actions;

import core.ui.page.contentRelevancePage;
import lib.compat.FluentWebElement;

public class ContentRelevanceActions extends contentRelevancePage {

    public int getSynonymCount(FluentWebElement synonymCount)
    {
        int count = Integer.parseInt(synonymContainer.getText());
        return count;
    }

    public int getConceptsCount(FluentWebElement conceptsCount)
    {
        int count = Integer.parseInt(conceptsContainer.getText());
        return count;
    }

    public int getPhrasesCount(FluentWebElement phrasesCount)
    {
        int count = Integer.parseInt(phrasesContainer.getText());
        return count;
    }

    public int getNoStemWordCount(FluentWebElement stemCount)
    {
        int count = Integer.parseInt(noStemContainer.getText());
        return count;
    }

}
