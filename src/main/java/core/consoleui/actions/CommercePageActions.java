package core.consoleui.actions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.consoleui.page.CampaignCreationPage;
import core.consoleui.page.CommerceSearchPage;
import lib.Helper;
import lib.enums.UnbxdEnum;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static lib.constants.UnbxdErrorConstants.*;

public class CommercePageActions extends CommerceSearchPage {

    MerchandisingActions merchandisingActions;
    CampaignCreationPage campaignCreationPage;

    public FluentWebElement queryRuleByName(String name) {
        ThreadWait();
        awaitForElementPresence(searchIcon);
        ThreadWait();
        if (queryRulesList.size() == 0)
            return null;
        searchIcon.click();
        searchInputBox.click();
        searchInputBox.clear();
        unbxdInputBoxSearch(searchInputBox, name);
        for (FluentWebElement e : queryRulesList) {
            if (getQueryNameFromQueryRule(e).trim().contains(name)) {
                return e;
            }
        }
        return null;
    }


    public FluentWebElement queryRuleByName1(Map<String, Object> campaignData) throws InterruptedException {
        String campaignName=campaignCreationPage.fillCampaignData(campaignData);
        ThreadWait();
        awaitForElementPresence(searchIcon);
        ThreadWait();
        if (queryRulesList.size() == 0)
            return null;
        searchIcon.click();
        searchInputBox.clear();
        unbxdInputBoxSearch(searchInputBox, campaignName);
        await();
        for (FluentWebElement e : queryRulesList) {
            if (getQueryNameFromQueryRule(e).trim().contains(campaignName)) {
                return e;
            }
        }
        return null;
    }


        public void deleteQueryRule(String name) {
        ThreadWait();
        ThreadWait();
//        FluentWebElement element = queryRuleByName(name);
//        if (element == null) {
//            System.out.println("Query rule with name '" + name + "' not found");
//            return;
//        }

        // Try to hover over the element to reveal menu options
//        try {
//            Helper.mouseOver(element.findFirst(campaignContainer).getElement());
//        } catch (Exception e) {
//            System.out.println("Could not hover over element: " + e.getMessage());
//        }
        
        // Check if menuIcon exists and click it


            if (awaitForElementPresence(menuIcon)) {
            click(menuIcon);

            if (awaitForElementPresence(deleteRuleButton)) {
                click(deleteRuleButton);

                if (awaitForElementPresence(modalWindow) && awaitForElementPresence(deleteYesButton)) {
                    click(deleteYesButton);

                    // Check for success or error messages
                    if (awaitForElementPresence(successMessage)) {
                        ThreadWait();
                        Assert.assertTrue(checkSuccessMessage(), SUCCESS_MESSAGE_FAILURE);
                        awaitForElementNotDisplayed(deleteYesButton);
                    } else if (awaitForElementPresence(deleteErrorMessage)) {
                        ThreadWait();
                        Assert.fail(deleteErrorMessage.getText());
                    }
                } else {
                    System.out.println("Delete confirmation dialog did not appear");
                }
            } else {
                System.out.println("Delete button not found in menu");
            }
        } else {
            System.out.println("Menu icon not found");
        }
    }

    public void goToQueryBasedBanner()
    {
        awaitForElementPresence(queryBasedBannerButon);
        click(queryBasedBannerButon);
    }

    public void goToFieldRuleBasedBanner()
    {
        awaitForElementPresence(fieldBasedBannerButon);
        click(fieldBasedBannerButon);
    }


    public void selectActionType (UnbxdEnum type, String name) {
//        FluentWebElement element = queryRuleByName(name);
//        threadWait();
//        Helper.mouseOver(element.findFirst(campaignContainer).getElement());

        switch (type) {
            case PREVIEW:
                awaitForElementPresence(queryPreviewButton);
                click(queryPreviewButton);
                return;
            case EDIT:
                awaitForElementPresence(queryruleEditButton);
                click(queryruleEditButton);
                return;
            case MORE:
                awaitForElementPresence(menuIcon);
                click(menuIcon);
                return;
            case LEFTMORE:
                awaitForElementPresence(leftMenuIcon);
                click(leftMenuIcon);
            default:
                return;
        }
    }

    public void addAnotherCampaign()
    {
        threadWait();
        awaitForElementPresence(Addanothercampaign);
        click(Addanothercampaign);
    }
    public String getConditionTitle()
    {
        threadWait();
        awaitForElementPresence(conditionTitle);
        scrollToBottom();
        return conditionTitle.getText().trim();
    }

    public int getConditionSize()
    {
        threadWait();
        return conditionSummary.size();
    }

    public int getConditionSizeForConditionType(String conditiontype)
    {
        ThreadWait();
        merchandisingActions.getMerchandisingCondition(conditiontype);
        return conditionSummary.size();
    }

    public int getSortPinConditionSize()
    {
        ThreadWait();
        return sortConditionList.size();
    }

    public void selectModelWindow(){
        awaitForElementPresence(modalWindow);
        awaitForElementPresence(deleteYesButton);
        click(deleteYesButton);
        awaitForElementPresence(successMessage);
        ThreadWait();
    }

    public void selectActionFromMore(UnbxdEnum type, String name) {
        switch (type) {
            case DELETE:
                awaitForElementPresence(deleteRuleButton);
                click(deleteRuleButton);
                return ;
            case STOPPED:
                awaitForElementPresence(stopPromotionButton);
                click(stopPromotionButton);
                return ;
            case DUPLICATE:
                awaitForElementPresence(duplicateRuleIcon);
                click(duplicateRuleIcon);
                return ;
            default:
                return ;

        }
    }

    public FluentWebElement getRowName(String query){
        if(!getQueryRules.isEmpty()) {
            for (int i = 0; i < getQueryRules.size(); i++) {
                if (getQueryRules.get(i).find(getQueryName).getText().equalsIgnoreCase(query)) {
                    return getQueryRules.get(i);
                }
            }
        }
        return null;
    }


    public void clickOnAddRule(boolean bannerOrFacet)
    {
        if (bannerOrFacet==true) {
            awaitForElementPresence(addBannerButton);
            click(addBannerButton);
        }
        else {
            awaitForElementPresence(addRuleButton);
            click(addRuleButton);
            awaitForPageToLoad();
        }
    }

    public void editGlobalRule()  {
        awaitForPageToLoad();
        threadWait();
//        if (queryRulesList.size() > 0) {
//            FluentWebElement element = queryRulesList.get(0);
//            threadWait();
//            Helper.mouseOver(element.findFirst(campaignContainer).getElement());
            awaitForElementPresence(globalruleEditButton);
            click(globalruleEditButton);
//        } else {
//            Assert.fail("GLOBAL RULE IS NOT COMING IN THE LISTING PAGE");
//        }
    }



    public String fillQueryRuleData(String query,String page,String... similarQuiries) throws InterruptedException {
    String queryName=query+System.currentTimeMillis();
        awaitForElementPresence(newQueryRuleInput);
        ThreadWait();
       if(query!=null){
           awaitForElementPresence(newQueryRuleInput);
           Assert.assertTrue(awaitForElementPresence(newQueryRuleInput),"SEARCH CAMPAIGN CREATION PAGE IS NOT LOADED");
           newQueryRuleInput.fill().with(queryName);
       }else if(page!=null) {
           //fillPageName(); dnt forget
       }
        if(similarQuiries!=null && similarQuiries.length>0)
        {
            awaitForElementPresence(addMoreQueryTab);
            click(addMoreQueryTab);
            for(String similarQuery:similarQuiries) {
            ThreadWait();

                similarQueryInput.fill().with(similarQuery);
                similarQueryInput.getElement().sendKeys(Keys.ENTER);
            }
        }
        await();
        return queryName;
    }

    public void fillPageName(JsonArray object) throws InterruptedException {
        for (int i = 0; i < object.size(); i++) {
            JsonObject group = (JsonObject) object.get(i);

            JsonArray rows = group.getAsJsonArray("rows");
            JsonObject row = (JsonObject) rows.get(i);
            String browse_Attribute, browse_Value;

            browse_Attribute = row.get("Browse_Attribute").getAsString();
            browse_Value = row.get("Browse_Value").getAsString();

            ThreadWait();
            Assert.assertTrue(awaitForElementPresence(pageRuleDropdown), "BROWSE CAMPAIGN CREATION PAGE IS NOT LOADED");
            pageRuleDropdown.click();
            threadWait();
            awaitForElementPresence(BuildPath);
            BuildPath.click();
            browseAttributeArrow.click();
            // Add search input fill before iterating attributes
            FluentWebElement searchInput = findFirst(".RCB-dd-search-ip");
            searchInput.fill().with(browse_Attribute);
            ThreadWait();
            for (FluentWebElement attribute : browseAttributeList) {
                if (attribute.getText().trim().equalsIgnoreCase(browse_Attribute)) {
                    attribute.click();
                    ThreadWait();
                    break;
                }
            }
            threadWait();
            for (FluentWebElement value : categoeyValueList) {
                if (value.getText().trim().equalsIgnoreCase(browse_Value)) {
                    // ThreadWait();
                    // value.click();
                    ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", value.getElement());
                    ThreadWait();
                    break;
                }
            }
            threadWait();
            categorypathApplyButton.click();
            Thread.sleep(5000);
        }

    }

    /**
     * Searches in a dropdown using the search input and clicks the matching option.
     * @param searchText The text to type in the search box.
     * @param optionToSelect The visible text of the option to click.
     */
    public void searchAndSelectDropdownOption(String searchText, String optionToSelect) {
        // 1. Type in the search box
        FluentWebElement searchInput = findFirst(".RCB-dd-search-ip");
        searchInput.fill().with(searchText);
        ThreadWait(); // Optionally wait for results to load

        // 2. Find all dropdown options (adjust selector if needed)
        FluentList<FluentWebElement> options = find(".RCB-list-item");
        boolean found = false;
        for (FluentWebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(optionToSelect)) {
                option.click();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("Option '" + optionToSelect + "' not found in dropdown after searching for '" + searchText + "'");
        }
    }
}