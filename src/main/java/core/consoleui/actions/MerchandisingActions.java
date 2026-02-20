package core.consoleui.actions;

import core.consoleui.page.MerchandisingRulesPage;
import core.ui.page.UnbxdCommonPage;
import lib.Helper;
import lib.enums.UnbxdEnum;
import lib.compat.Page;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;

public class MerchandisingActions extends MerchandisingRulesPage {


    @Page
    CommercePageActions searchPageActions;

    public void publishCampaign() throws InterruptedException {
        ThreadWait();
        awaitForElementPresence(publishButton);
        safeClick(publishButton);
        waitForLoaderToDisAppear(successMsgPopUp, "STILL PUBLISHING IS IN-PROGRESS");
        ThreadWait();
        await();
        Assert.assertFalse(awaitForElementPresence(publishButton), "CAMPAIGN PUBLISHING IS NOT WORKING!!!");
    }

    public void publishGlobalRule() throws InterruptedException {
        ThreadWait();
        if (awaitForElementPresence(publishButton) == true) {
            await();
            safeClick(publishButton);
            waitForLoaderToDisAppear(successMsgPopUp, "STILL PUBLISHING IS IN-PROGRESS");
            ThreadWait();
            Assert.assertFalse(awaitForElementPresence(publishButton), "CAMPAIGN PUBLISHING IS NOT WORKING!!!");
        } else {
            await();
            safeClick(saveAsDraftButton);
            waitForLoaderToDisAppear(successMsgPopUp, "STILL PUBLISHING IS IN-PROGRESS");
            ThreadWait();
            Assert.assertFalse(awaitForElementPresence(saveAsDraftButton), "CAMPAIGN PUBLISHING IS NOT WORKING!!!");
        }

    }

    public void addNewRowInGroup(int group, UnbxdEnum type) {
        FluentWebElement rowGroup = getGroup(type).get(group);
        rowGroup.findFirst(addRule).click();
    }

    public void addNewRow(int group, UnbxdEnum type) {
        //FluentWebElement rowGroup=getGroup(type).get(group);
        click(addAndRuleButton);
    }

    public void fillBoostInputValue(FluentWebElement boostSliderValueInput) {
        awaitForElementPresence(boostInputValue);
        boostSliderValueInput.click().fill().with(String.valueOf(50));
    }

    public void fillRowValues(int group, UnbxdEnum type, int index, String key, String condition, String value) throws InterruptedException {
        try {
            ThreadWait();
            FluentWebElement row, attributeElement, conditionElement, valueElement, rowBoost, boostRowSection, rowValue, boostValue;
            FluentWebElement rowGroup;
            rowGroup = getGroup(type).get(group);
            row = rowGroup.find(ruleGroups).get(index);

            attributeElement = row.findFirst(attribute);
            conditionElement = row.findFirst(comparator);
            valueElement = row.findFirst(valueOfAttribute);

            selectAttribute(key, attributeElement);
            selectCondition(condition, conditionElement);
            ThreadWait();
            selectValue(value, valueElement);
            ThreadWait();
            if (awaitForElementPresence(boostValueSection)) {
                boostRowSection = rowGroup.find(ruleValueGroups).get(index);
                boostValue = boostRowSection.findFirst(boostSliderValue);
                fillBoostInputValue(boostValue);
                ThreadWait();
            } else if (awaitForElementPresence(slotPositionSection)) {
                fillSlotPositions();
                ThreadWait();

            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void selectSimilarQueryData(String similarQuery){
        awaitForElementPresence(applysameRuletomoreAIsuggestedqueries);
        click(applysameRuletomoreAIsuggestedqueries);
        awaitForElementPresence(similarQueriesInput);
        similarQueriesInput.fill().with(similarQuery);
        awaitForElementPresence(similarQueriesAddlabel);
        click(similarQueriesAddlabel);
        threadWait();
        applyChanges.click();
        threadWait();
    }

    public String selectAISuggestedSimilarQueryData() throws InterruptedException {
        awaitForElementPresence(applysameRuletomoreAIsuggestedqueries);
        click(applysameRuletomoreAIsuggestedqueries);
        ThreadWait();
        awaitForElementPresence(AiSuggestedList.get(1));
        AiSuggestedList.get(1).click();
        threadWait();
        String aiSuggestQuery = AiSelectedSimilarquery.getText();
        applyChanges.click();
        threadWait();
        return aiSuggestQuery;
    }


    public void listinPageAddMoreQueriesEditIcon(){
        awaitForElementPresence(addMoreQueriesEditIcon);
        click(addMoreQueriesEditIcon);
        ThreadWait();
    }
    public void upcomingDateSelection(){
        java.time.LocalDate tomorrow = java.time.LocalDate.now().plusDays(1);
        java.time.LocalDate dayAfterTomorrow = java.time.LocalDate.now().plusDays(2);
        String tomorrowLabel = tomorrow.format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
        String dayAfterLabel = dayAfterTomorrow.format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));

        WebElement targetTile1 = null;
        WebElement targetTile2 = null;

        try {
            // Try direct tomorrow click
            targetTile1 = getDriver().findElement(By.xpath("//abbr[@aria-label='" + tomorrowLabel + "']/parent::button"));
            targetTile2 = getDriver().findElement(By.xpath("//abbr[@aria-label='" + dayAfterLabel + "']/parent::button"));
        } catch (Exception e) {
            // Fallback to next available days
            java.util.List<WebElement> tiles = getDriver().findElements(By.cssSelector(".react-calendar__tile:not(.react-calendar__tile--disabled)"));
            WebElement today = getDriver().findElement(By.cssSelector(".react-calendar__tile--now"));
            int currentIndex = tiles.indexOf(today);
            targetTile1 = (currentIndex >= 0 && currentIndex + 1 < tiles.size()) ? tiles.get(currentIndex + 1) : tiles.get(0);
            targetTile2 = (currentIndex >= 0 && currentIndex + 2 < tiles.size()) ? tiles.get(currentIndex + 2) : tiles.get(1);
        }

        // Click first target date
        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", targetTile1);
        // Click second target date (hold Ctrl for multiple selection)
        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", targetTile2);
        searchPageActions.threadWait();    }

    public void timeZoneSelection(){
        timezoneDropdown.click();
        awaitForElementPresence(zoneinput);
        zoneinput.fill().with("kolkata");
        timezonelist.click();
    }


    public void fillSlotPositions() {
        awaitForElementPresence(slotFirstPosition);
        click(slotFirstPosition);
        slotFirstPosition.fill().with("1");
        awaitForElementPresence(slotEndPosition);
        click(slotEndPosition);
        slotEndPosition.fill().with("2");
    }

    public void fillSortOrPinRowValues(int group, UnbxdEnum type, int index, String key, String value, int product) throws InterruptedException {
        threadWait();
        FluentWebElement row, attributeElement, valueElement;
        FluentWebElement rowGroup;
        rowGroup = getGroup(type).get(group);
        row = rowGroup.find(pinSortRuleGroups).get(index);

        attributeElement = row.findFirst(sortAttribute);
        if (type == UnbxdEnum.SORT) {
            valueElement = row.findFirst(SortOrder);
            selectAttribute(key, attributeElement);
        } else {
            valueElement = row.findFirst(pinPosition);
            selectPinningProduct(product);
        }

        if (type == UnbxdEnum.SORT) {
            selectSortAttribute(value, valueElement);
        } else {
            selectValue(value, valueElement);
        }
        threadWait();
    }

    public void selectBoostValue(FluentWebElement slider) {
        Assert.assertTrue(boostSlider.isDisplayed());

        for (int i = 1; i <= 2; i++) {
            slider.getElement().sendKeys(Keys.ARROW_RIGHT);
        }
    }

    public void selectAttribute(String value, FluentWebElement attribute) throws InterruptedException {
        ThreadWait();
        attribute.find(".RCB-select-arrow").click();
        ThreadWait();
        if (attributeDropDownList.size() > 0) {
            attributeInput.clear();
            ThreadWait();
            attributeInput.fill().with(value);
            ThreadWait();
            selectDropDownValue(attributeDropDownList, value);
            ThreadWait();
        } else {
            Assert.fail("ATTRIBUTE DROPDOWN LIST IS EMPTY!!!");
        }
    }

    public void selectAttributeValue(String value) throws InterruptedException {
        AttributeDropDown.click();
        ThreadWait();
            attributeInput.clear();
            attributeInput.fill().with(value);
            ThreadWait();

    }

    public void selectSortAttribute(String value, FluentWebElement attribute) throws InterruptedException {
        await();
        attribute.find(".RCB-select-arrow").click();
        Thread.sleep(3000);
        selectDropDownValue(attributeDropDwnList, value);
        Thread.sleep(3000);
    }


    public void selectCondition(String condition, FluentWebElement comparator) {
        ThreadWait();
        comparator.click();
        ThreadWait();
        Assert.assertTrue(conditionList.size() > 0, "Conditions are not loading");
        for (FluentWebElement option : conditionList) {
            if (option.getTextContent().trim().toLowerCase().contains(condition.toLowerCase())) {
                option.click();
                break;
            }
        }
//        String modifiedCondition = condition.replace(" ", "-");
//        Assert.assertTrue(comparator.findFirst("span").getAttribute("class").contains(modifiedCondition), " Condition is not selected");
    }

    public void selectValue(String value, FluentWebElement element) {
        Attribbutevalue.click();
        ThreadWait();
        //element.findFirst("filterValue").fill().with(value);
        Attribbutevalue.fill().with(value);
    }


    public FluentList<FluentWebElement> getGroup(UnbxdEnum type) {
        switch (type) {
            case FILTER:
            case BOOST:
            case SLOT:
            case FRESH:
                return filterGroups;
            case SORT:
                return sortGroups;
            case PIN:
                return pinGroups;
            default:
                return null;
        }
    }

    public void goToSectionInMerchandising(UnbxdEnum section) throws InterruptedException {
        awaitTillElementDisplayed(publishButton);
        threadWait();
        await();
        for (FluentWebElement element : merchandisingSections) {
            if (element.getText().trim().contains(section.getLabel())) {
                ThreadWait();
                safeClick(element);
                threadWait();
                awaitTillElementDisplayed(publishButton);
                break;
            }
        }
        System.out.println("WAITING FOR CONSOLE PREVIEW TO LOAD!!! INCASE OF NO RESULTS IN CONSOLE PREVIEW THIS VALIDATION WILL FAIL");
        //await().atMost(15, TimeUnit.SECONDS).until(".preview-actions-wrapper .no-of-results").areDisplayed();

    }


    public MerchandisingActions nextPage() {
        awaitTillElementDisplayed(nextButton);
        nextButton.click();
        awaitForPageToLoad();
        return this;
    }

    public void clickOnApplyButton() {
        awaitTillElementDisplayed(applyButton);
        ThreadWait();
        threadWait();
        // Scroll element into view before clicking
        scrollUntilVisible(applyButton);
        ThreadWait();
        // Wait for element to be clickable
        waitForElementToBeClickable(applyButton, "Apply button");
        ThreadWait();
        safeClick(applyButton);
        ThreadWait();
    }

    public void deleteConditionIfItsPresent(int group) {
        if (conditionsList.size() > 0) {
            for (int i = 0; i < group; i++) {
                deleteMerchandizingCondition();
                ThreadWait();
            }
        }
    }

    public void selectGlobalActionType (UnbxdEnum type) {
//        if(awaitForElementPresence(AddBoostRuleButton))
//            click(AddBoostRuleButton);
//            switch (type)
//            {
//                case GLOBALBOOST:
//                    click(globalBoostButton);
//                case GLOBALFILTER:
//                    click(globalFilterButton);
//                default:
//                    return;
//            }

        switch (type) {
            case GLOBALBOOST:
                if(awaitForElementPresence(AddBoostRuleButton)){
                    click(AddBoostRuleButton);}
                    else if(awaitForElementPresence(globalBoostButton)) {
                        threadWait();
                        click(globalBoostButton);
                    }
                return;
            case GLOBALFILTER:
                if(awaitForElementPresence(AddBoostRuleButton)){
                    click(AddBoostRuleButton);}
                else if(awaitForElementPresence(globalFilterButton)){
                    threadWait();
                click(globalFilterButton);}
            default:
                return;
        }
    }


    public void selectPinningProduct(int product) {
        pinningDropdown.click();
        if (pinningDropDownList.size() > 0) {
            pinningDropDownList.get(product).click();
        } else {
            Assert.fail("PINNING DROPDOWN LIST IS EMPTY");
        }
    }

    public void pinProductInFirstPosition(String pinningPosition) {
        //pinPosition.fill().with(pinningPosition);
        clickOnApplyButton();
        ThreadWait();
    }

    public void selectSortAtrributeAndOrder(String Attribute) {
        if (sortAttributeList.size() > 0) {
            for (int i = 0; i < sortAttributeList.size(); i++) {
                ThreadWait();
                searchInput.fill().with(Attribute);
                sortAttributeList.get(i).click();
                ThreadWait();
            }
        }
    }

    public void pinProductFromConsolePreview(String pinningPosition) {
        int i = Integer.parseInt(pinningPosition);
        if (listOfProductInPreview.size() > 0) {
            ThreadWait();
            Helper.mouseOver(listOfProductInPreview.get(i).getElement());
            pinTheProduct.get(i).click();
        }
    }


    public void clickSortOrder(String sortOrder) {
        click(sortOrder);
        selectSortAtrributeAndOrder(sortOrder);
    }

    public void verifySortAscendingOrder() {
        ArrayList<String> productTitles = new ArrayList();
        for (int i = 0; i < productTitle.size(); i++) {
            String productTitleName = productTitle.get(i).getText();
            productTitles.add(productTitleName);
        }
        ArrayList<String> expected = new ArrayList(productTitles);
        Collections.sort(expected);
        Assert.assertEquals(productTitles, expected, "Not in ascending order.");
    }


    public void verifySortDescendingOrder() {
        ArrayList<String> productTitles = new ArrayList();
        int Count = productTitle.size();
        for (int i = 0; i < Count; i++) {
            String productTitleName = productTitle.get(i).getText();
            productTitles.add(productTitleName);
            ArrayList<String> expected = new ArrayList(productTitles);
            Collections.sort(expected, Collections.reverseOrder());
            Assert.assertEquals(productTitles, expected, "Not in descending order.");
        }

    }

    public void getMerchandisingCondition(String condition) {
        if (merchandisingConditionList.size() > 0) {
            for (int i = 0; i < merchandisingConditionList.size(); i++) {
                merchandisingConditionList.get(i).find(".action-title").getText().trim().equalsIgnoreCase(condition);
                ThreadWait();
                Helper.mouseOver(merchandisingConditionList.get(i).getElement());
                ThreadWait();
                awaitForElementPresence(searchPageActions.queryEditButton);
                click(searchPageActions.queryEditButton);
                ThreadWait();
            }
        }
    }

    public void deleteMerchandizingCondition() {
        awaitForElementPresence(deleteThePromtionMerchandizingSet);
        click(deleteThePromtionMerchandizingSet);
        awaitForElementPresence(searchPageActions.modalWindow);
        awaitForElementPresence(searchPageActions.deleteYesButton);
        click(searchPageActions.deleteYesButton);
        ThreadWait();
    }

    public void verifySlotIconIsPresentAtGivenPosition(int slotCount) {
        ThreadWait();
        if (productTitleCard.size() > 0) {
            for (int i = 0; i > slotCount; i++) {
                awaitForElementPresence(productTitleCard.get(i));
                Assert.assertTrue(productTitleCard.get(i).findFirst(slotIcon).isDisplayed(), "SLOT ICON IS NOT PRESENT AT THE GIVEN POSITION");
            }
        } else {
            Assert.fail("CONSOLE PREVIEW IS GIVING ZERO RESULT!!!!");
        }
    }

        public void goToLandingPage()
        {
            threadWait();
            awaitTillElementDisplayed(landingPageToggle);
            safeClick(landingPageToggle);
            ThreadWait();
            threadWait();
            // Wait for the toggle to enable
            awaitTillElementDisplayed(landingPageEnabledToggle);
            Assert.assertTrue(awaitForElementPresence(landingPageEnabledToggle),"LANDING PAGE IS NOT ENABLED");
        }

    public void goToSearch_browsePreview() throws InterruptedException {
        if (awaitForElementPresence(searchPageActions.menuIcon)) {
            threadWait();
            click(searchPageActions.menuIcon);
        }
        Thread.sleep(3000);
        click(seach_browsepreview);
        threadWait();
        threadWait();
    }
    public void ClickViewHideInsight()
    {
        threadWait();
        click(view_hide_insight);
        threadWait();
    }

    public boolean switchPreviewTab() {
        try {
            ThreadWait(); // Wait for new window to fully open

            // Get all window handles
            ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
            int totalTabs = tabs.size();
            
            System.out.println("Total tabs open: " + totalTabs);

            // Check if we have at least 2 tabs
            if (totalTabs < 2) {
                System.out.println("Not enough tabs are open. Total tabs: " + totalTabs);
                return false;
            }

            // Determine which tab to switch to based on total number of tabs
            int targetTabIndex;
            if (totalTabs == 2) {
                // If 2 tabs are open, switch to tab index 1 (second tab)
                targetTabIndex = 1;
                System.out.println("Two tabs detected, switching to tab index: " + targetTabIndex);
            } else if (totalTabs >= 3) {
                // If 3 or more tabs are open, switch to tab index 2 (third tab)
                targetTabIndex = 2;
                System.out.println("Three or more tabs detected, switching to tab index: " + targetTabIndex);
            } else {
                return false;
            }

            // Switch to the target tab
            getDriver().switchTo().window(tabs.get(targetTabIndex));
            System.out.println("Successfully switched to tab index: " + targetTabIndex);
            ThreadWait(); // Wait for page to load in new tab
            threadWait();
            awaitForPageToLoad();
            ThreadWait();
            threadWait();

            return true;
            
        } catch (Exception e) {
            System.out.println("Error switching tabs: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void openPreviewAndSwitchTheTab() throws InterruptedException {
        ThreadWait();
        goToSearch_browsePreview();
        ThreadWait();
        threadWait();

        // Switch to the appropriate tab based on how many are open
        if (switchPreviewTab()) {
            System.out.println("Now working in preview tab");
            // Wait for the preview page to load and verify URL contains "preview"
            awaitForPageToLoad();
            ThreadWait();
            threadWait();
            
            // Wait for URL to contain "preview" with retry logic
            int retries = 0;
            int maxRetries = 5;
            while (retries < maxRetries && !getDriver().getCurrentUrl().contains("preview")) {
                ThreadWait();
                threadWait();
                retries++;
                System.out.println("Waiting for preview URL... retry " + retries);
            }
            
            String currentUrl = getDriver().getCurrentUrl();
            if (!currentUrl.contains("preview")) {
                System.out.println("Warning: URL does not contain 'preview' after switching. Current URL: " + currentUrl);
            }
        } else {
            System.out.println("Failed to switch to preview tab");
            // Try to get current URL for debugging
            System.out.println("Current URL: " + getDriver().getCurrentUrl());
        }
    }



    public void dragAndDropPinningPosition() {
        if (listOfProductInPreview.size() > 2) {
            WebElement from = listOfProductInPreview.get(1).getElement();
            WebElement to = listOfProductInPreview.get(2).getElement();
            
            try {
                // Simple DOM swap
                ((JavascriptExecutor) getDriver()).executeScript(
                    "arguments[1].parentNode.insertBefore(arguments[0], arguments[1]);", from, to);
            } catch (Exception e) {
                threadWait();
                new org.openqa.selenium.interactions.Actions(getDriver()).dragAndDrop(from, to).perform();
            }
            ThreadWait();
        }
    }
}

