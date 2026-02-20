package core.ui.actions;

import core.ui.page.PreviewPage;
import lib.Config;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PreviewActions extends PreviewPage {


    public int getPreviewSnippetCount() {
        return productSnippet.size();
    }

    public int getFacetsCount() {
        return filterSnippet.size();
    }



    public void goToPreviewPage() {
        awaitForElementPresence(previewButtonTab);
        click(previewButtonTab);
        awaitForPageToLoad();
        switchTabTo(1);
        if (awaitForElementPresence(templatePopupCloseButton)) {
            click(templatePopupCloseButton);
        }
        String previewUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(previewUrl.contains("preview"));
    }

    public void passQueryToSearchBox(String query) throws InterruptedException {
        awaitForElementPresence(searchInputBox);
        click(searchInputBox);
        searchInputBox.clear();
        ThreadWait();
        searchInputBox.fill().with(query);
        ThreadWait();

    }

    public void clickOnSearchIcon() throws InterruptedException {
        awaitForElementPresence(searchIcon);
        ThreadWait();
        click(searchIcon);

    }

    public void verifyKeywordSuggestions(String query) {
        try {
            if (KeywordSuggestionList.size() >0 && awaitForElementPresence(keywordSuggestionPresence)) {
                System.out.println("Searched Keyword: '" + query + "' is matched with Suggested query ");
            } else {
                Assert.assertTrue(awaitForElementPresence(keywordSuggestionPresence) == false, "KeywordSuggestion is not present for the query: - " + query);
                Assert.fail("KeywordSuggestion is not present!!! for the query :"+query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifyInfields(String query) {
        try {
            if (suggestInfield.size() >0 && awaitForElementPresence(keywordSuggestionPresence) == true) {
                for (int i = 0; i < suggestInfield.size(); i++) {
                    String infieldSuggest = suggestInfield.get(i).getText();
                    if (infieldSuggest.startsWith("in")) {
                        System.out.println("Infields value is displayed '" + infieldSuggest + "' for the searched keyword -" + query);
                    }
                }
            } else {
                Assert.assertTrue(awaitForElementPresence(keywordSuggestionPresence) == false, "Infields Suggestion is not present for the query: " + query);
                Assert.fail("Infield Suggestion is not present!!!! for the query :"+query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void popularProducts(String query) {
        try {
            waitForElementAppear(pageLoader,websitePreviewPopularProductPage, Config.getIntValueForProperty("popularProduct.wait.time"),Config.getIntValueForProperty("popularProduct.numOfRetries"));
            if (popularProduct.size() > 0 && awaitForElementPresence(popularProductPresence) == true) {
                for (int i = 0; i < popularProduct.size(); i++) {
                    if (popularProduct.get(i).isDisplayed()) {
                        System.out.println("Popular products are displayed for the searched query -" + query);
                    }
                }
            } else {
                Assert.assertTrue(awaitForElementPresence(popularProductPresence) == false, "Popular products are not displayed for the query: " + query);
                Assert.fail("Popular products are not present!!!!! for the query :"+query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSearchedQueryCount() {
        String[] arrResult = productCount.getText().split("to")[1].split("of")[1].split("products");
        String SearchResultCount = arrResult[0].trim();
        int searchedProductCount = Integer.parseInt(SearchResultCount);
        return searchedProductCount;

    }

    public void checkSearchedQueryResult(String query) {
        try {
            waitForElementAppear(pageLoader,websitePreviewKeywordSuggestionPage, Config.getIntValueForProperty("popularProduct.wait.time"),Config.getIntValueForProperty("popularProduct.numOfRetries"));
            int searchedProductCount = getSearchedQueryCount();
            if (searchedProductCount > 0) {
                ThreadWait();
                System.out.println("Searched Result Count '" + searchedProductCount + "' for the the Searched query " + query);
            } else {
                System.out.println("Searched result count is not displayed for the query " + query);
                Assert.fail("Search API is not working!!!!! for the query :"+query);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void verifyTitleContainsQuery(String query) {
        for (int i = 0; i < ProductTitles.size(); i++) {
            if(ProductTitles.get(i).getText().toLowerCase().contains(query.toLowerCase())){
                System.out.println("searched query "+query+ " results are coming correctly!!! Titles are : "+ProductTitles.get(i).getText());
            }else{
                Assert.fail("searched query "+query+" results are not coming correctly!!! Titles are : "+ProductTitles.get(i).getText());
            }
        }
    }
    public void verifyDidYouMeanLink(String query) throws InterruptedException {
        waitForElementAppear(pageLoader,websitePreviewKeywordSuggestionPage, Config.getIntValueForProperty("popularProduct.wait.time"),Config.getIntValueForProperty("popularProduct.numOfRetries"));
        int searchedProductCount = getSearchedQueryCount();
        if (didYouMeanLink.getText().contains("Did you mean") && didYouMeanLink.getText().contains(query)) {
            if (searchedProductCount > 0) {
                ThreadWait();
                System.out.println("Product are displayed " + didYouMeanLink.getText());
            } else {
                System.out.println("DidyouMean is not Displayed");
                Assert.fail("Product count is not displayed!!!!! for the query :" + query);
            }
        }
    }



}