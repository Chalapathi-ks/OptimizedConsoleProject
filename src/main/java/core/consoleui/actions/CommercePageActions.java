package core.consoleui.actions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.consoleui.page.CampaignCreationPage;
import core.consoleui.page.CommerceSearchPage;
import lib.enums.UnbxdEnum;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Map;

import static lib.constants.UnbxdErrorConstants.*;

public class CommercePageActions extends CommerceSearchPage {

    private static final int SUCCESS_TOAST_WAIT_SEC = 20;
    private static final By SUCCESS_TOAST_SELECTOR = By.cssSelector(".unx-qa-toastsucess, .unx-qa-toastsuccess");

    MerchandisingActions merchandisingActions;
    CampaignCreationPage campaignCreationPage;

    public FluentWebElement queryRuleByName(String name) {
        await();
        awaitForElementPresence(searchIcon);
        await();
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
        await();
        awaitForElementPresence(searchIcon);
        await();
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
        await();
        await();
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
                        await();
                        Assert.assertTrue(checkSuccessMessage(), SUCCESS_MESSAGE_FAILURE);
                        awaitForElementNotDisplayed(deleteYesButton);
                    } else if (awaitForElementPresence(deleteErrorMessage)) {
                        await();
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

    /** Waits for the success toast to appear in the DOM (e.g. after delete/publish). Call before asserting on ToasterSuccess. */
    public void awaitForSuccessToastPresence() {
        new WebDriverWait(getDriver(), SUCCESS_TOAST_WAIT_SEC).until(
            ExpectedConditions.presenceOfElementLocated(SUCCESS_TOAST_SELECTOR));
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
        await();
        awaitForElementPresence(Addanothercampaign);
        click(Addanothercampaign);
    }
    public String getConditionTitle()
    {
        await();
        awaitForElementPresence(conditionTitle);
        scrollToBottom();
        return conditionTitle.getText().trim();
    }

    public int getConditionSize()
    {
        await();
        return conditionSummary.size();
    }

    public int getConditionSizeForConditionType(String conditiontype)
    {
        await();
        merchandisingActions.getMerchandisingCondition(conditiontype);
        return conditionSummary.size();
    }

    public int getSortPinConditionSize()
    {
        await();
        return sortConditionList.size();
    }

    public void selectModelWindow(){
        awaitForElementPresence(modalWindow);
        awaitForElementPresence(deleteYesButton);
        click(deleteYesButton);
        awaitForElementPresence(successMessage);
        await();
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
            scrollUntilVisible(addBannerButton);
            safeClick(addBannerButton);
        }
        else {
            awaitForElementPresence(addRuleButton);
            scrollUntilVisible(addRuleButton);
            safeClick(addRuleButton);
            awaitForPageToLoad();
        }
    }

    public void editGlobalRule()  {
        awaitForPageToLoad();
        await();
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
        await();
       if(query!=null){
           awaitForElementPresence(newQueryRuleInput);
           Assert.assertTrue(awaitForElementPresence(newQueryRuleInput),"SEARCH CAMPAIGN CREATION PAGE IS NOT LOADED");
           scrollUntilVisible(newQueryRuleInput);
           safeClick(newQueryRuleInput);
           newQueryRuleInput.fill().with(queryName);
       }else if(page!=null) {
           //fillPageName(); dnt forget
       }
        if(similarQuiries!=null && similarQuiries.length>0)
        {
            awaitForElementPresence(addMoreQueryTab);
            click(addMoreQueryTab);
            for(String similarQuery:similarQuiries) {
            await();

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

            await();
            Assert.assertTrue(awaitForElementPresence(pageRuleDropdown), "BROWSE CAMPAIGN CREATION PAGE IS NOT LOADED");
            pageRuleDropdown.click();
            await();
            awaitForElementPresence(BuildPath);
            BuildPath.click();
            browseAttributeArrow.click();
            await();
            FluentWebElement browseModal = findFirst(".browse-picker-modal");
            awaitForElementPresence(browseModal);
            FluentWebElement searchInput = browseModal.findFirst(".RCB-dd-search-ip");
            searchInput.fill().with(browse_Attribute);
            await();
            FluentWebElement attributeOption = null;
            for (int retry = 0; retry < 15; retry++) {
                await();
                FluentWebElement modal = findFirst(".browse-picker-modal");
                FluentList<FluentWebElement> items = modal.find(".RCB-list-item.dm-dd-item");
                for (FluentWebElement el : items) {
                    if (el.getText().trim().equalsIgnoreCase(browse_Attribute)) {
                        attributeOption = el;
                        break;
                    }
                }
                if (attributeOption != null) break;
                threadWait();
            }
            Assert.assertNotNull(attributeOption, "Attribute option not found in browse-picker: " + browse_Attribute);
            scrollUntilVisible(attributeOption);
            waitForElementToBeClickable(attributeOption, "Attribute option");
            try {
                attributeOption.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", unwrapWebElement(attributeOption.getElement()));
            }
            await();
            FluentWebElement valueOption = null;
            for (int retry = 0; retry < 15; retry++) {
                await();
                FluentWebElement modal = findFirst(".browse-picker-modal");
                FluentList<FluentWebElement> valueItems = modal.find(".list-item");
                for (FluentWebElement el : valueItems) {
                    if (el.getText().trim().equalsIgnoreCase(browse_Value)) {
                        valueOption = el;
                        break;
                    }
                }
                if (valueOption != null) break;
                threadWait();
            }
            Assert.assertNotNull(valueOption, "Value option not found in browse-picker: " + browse_Value);
            scrollUntilVisible(valueOption);
            waitForElementToBeClickable(valueOption, "Value option");
            try {
                valueOption.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", unwrapWebElement(valueOption.getElement()));
            }
            await();
            categorypathApplyButton.click();
            awaitForPageToLoad();
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
        await();

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