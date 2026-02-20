package core.consoleui.actions;

import core.consoleui.page.MerchandisingRulesPage;
import lib.Helper;
import lib.enums.UnbxdEnum;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;

public class MerchandisingActions extends MerchandisingRulesPage {


    @Page
    CommercePageActions searchPageActions;

    public void publishCampaign() throws InterruptedException {
        awaitForElementPresence(publishButton);
        safeClick(publishButton);
        waitForLoaderToDisAppear(successMsgPopUp, "STILL PUBLISHING IS IN-PROGRESS");
        await();
        shortWait();
        Assert.assertTrue(isElementInvisible(successMsgPopUp, 15), "CAMPAIGN PUBLISHING IS NOT WORKING!!!");
    }

    public void publishGlobalRule() throws InterruptedException {
        if (awaitForElementPresence(publishButton) == true) {
            await();
            safeClick(publishButton);
            waitForLoaderToDisAppear(successMsgPopUp, "STILL PUBLISHING IS IN-PROGRESS");
            await();
            shortWait();
            Assert.assertTrue(isElementInvisible(successMsgPopUp, 15), "CAMPAIGN PUBLISHING IS NOT WORKING!!!");
        } else {
            await();
            safeClick(saveAsDraftButton);
            waitForLoaderToDisAppear(successMsgPopUp, "STILL PUBLISHING IS IN-PROGRESS");
            Assert.assertTrue(isElementInvisible(successMsgPopUp, 15), "CAMPAIGN PUBLISHING IS NOT WORKING!!!");
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
            FluentWebElement row, attributeElement, conditionElement, valueElement, boostRowSection, boostValue;
            FluentWebElement rowGroup;
            rowGroup = getGroup(type).get(group);
            row = rowGroup.find(ruleGroups).get(index);

            attributeElement = row.findFirst(attribute);
            conditionElement = row.findFirst(comparator);
            valueElement = row.findFirst(valueOfAttribute);

            selectAttribute(key, attributeElement);
            selectCondition(condition, conditionElement);
            valueElement = row.findFirst(valueOfAttribute);
            selectValue(value, valueElement);
            if (awaitForElementPresence(boostValueSection)) {
                boostRowSection = rowGroup.find(ruleValueGroups).get(index);
                boostValue = boostRowSection.findFirst(boostSliderValue);
                fillBoostInputValue(boostValue);
            } else if (awaitForElementPresence(slotPositionSection)) {
                fillSlotPositions();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void selectSimilarQueryData(String similarQuery){
        awaitForElementPresence(applysameRuletomoreAIsuggestedqueries);
        scrollUntilVisible(applysameRuletomoreAIsuggestedqueries);
        safeClick(applysameRuletomoreAIsuggestedqueries);
        awaitForElementPresence(similarQueriesInput);
        scrollUntilVisible(similarQueriesInput);
        similarQueriesInput.fill().with(similarQuery);
        awaitForElementPresence(similarQueriesAddlabel);
        scrollUntilVisible(similarQueriesAddlabel);
        safeClick(similarQueriesAddlabel);
        awaitForElementPresence(applyChanges);
        scrollUntilVisible(applyChanges);
        waitForSimilarQueriesModalNotBlocking();
        waitForElementToBeClickable(applyChanges, "Apply changes (similar queries)", 1, 25);
        shortWait();
        clickUsingJS(applyChanges);
        await();
    }

    public String selectAISuggestedSimilarQueryData() throws InterruptedException {
        awaitForElementPresence(applysameRuletomoreAIsuggestedqueries);
        click(applysameRuletomoreAIsuggestedqueries);
        new WebDriverWait(getDriver(), 15).until((ExpectedCondition<Boolean>) d -> AiSuggestedList.size() > 1);
        awaitForElementPresence(AiSuggestedList.get(1));
        AiSuggestedList.get(1).click();
        await();
        String aiSuggestQuery = AiSelectedSimilarquery.getText();
        waitForSimilarQueriesModalNotBlocking();
        waitForElementToBeClickable(applyChanges, "Apply changes (AI suggested)", 1, 25);
        clickUsingJS(applyChanges);
        await();
        return aiSuggestQuery;
    }


    /** Wait for similar-queries modal overlay (opacity 0.6) to stop blocking so Apply button can be clicked. */
    private void waitForSimilarQueriesModalNotBlocking() {
        try {
            new WebDriverWait(getDriver(), 20).until((ExpectedCondition<Boolean>) d -> {
                if (d == null) return true;
                try {
                    WebElement modal = d.findElement(By.cssSelector(".similar-queries-modal"));
                    String opacity = modal.getCssValue("opacity");
                    return opacity != null && !opacity.trim().startsWith("0.");
                } catch (NoSuchElementException e) {
                    return true;
                }
            });
        } catch (TimeoutException e) {
            // Proceed; button may still be clickable via JS
        }
    }

    public void listinPageAddMoreQueriesEditIcon(){
        awaitForElementPresence(addMoreQueriesEditIcon);
        click(addMoreQueriesEditIcon);
        await();
    }
    public void upcomingDateSelection(){
        java.time.LocalDate tomorrow = java.time.LocalDate.now().plusDays(1);
        java.time.LocalDate dayAfterTomorrow = java.time.LocalDate.now().plusDays(2);
        String tomorrowLabel = tomorrow.format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
        String dayAfterLabel = dayAfterTomorrow.format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));

        String xpath1 = "//abbr[@aria-label='" + tomorrowLabel.replace("'", "\\'") + "']/parent::button";
        String xpath2 = "//abbr[@aria-label='" + dayAfterLabel.replace("'", "\\'") + "']/parent::button";

        // Click via JS using XPath in-browser only (no WebElement passed = no jdk.proxy2.$Proxy13)
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String script = "var el = document.evaluate(arguments[0], document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; if(el) { el.click(); return true; } return false;";
        Boolean ok1 = (Boolean) js.executeScript(script, xpath1);
        await();
        Boolean ok2 = (Boolean) js.executeScript(script, xpath2);
        await();
        // Fallback when aria-label format doesn't match (e.g. locale): click next two enabled tiles after today
        if (!Boolean.TRUE.equals(ok1)) {
            js.executeScript(
                "var tiles = document.querySelectorAll('.react-calendar__tile:not(.react-calendar__tile--disabled)');" +
                "var today = document.querySelector('.react-calendar__tile--now'); var i = today ? [].indexOf.call(tiles, today) : 0;" +
                "if (i >= 0 && tiles[i+1]) tiles[i+1].click();");
            await();
            js.executeScript(
                "var tiles = document.querySelectorAll('.react-calendar__tile:not(.react-calendar__tile--disabled)');" +
                "var today = document.querySelector('.react-calendar__tile--now'); var i = today ? [].indexOf.call(tiles, today) : 0;" +
                "if (i >= 0 && tiles[i+2]) tiles[i+2].click();");
            await();
        }
    }

    public void timeZoneSelection(){
        scrollUntilVisible(timezoneDropdown);
        waitForElementToBeClickable(timezoneDropdown, "Timezone dropdown");
        // Click via in-browser XPath so we never pass a WebElement proxy to executeScript (avoids jdk.proxy2.$Proxy13)
        String timezoneDropdownXpath = "//*[@class='time-zone time-headers']//following::*[@class='RCB-select-arrow']";
        ((JavascriptExecutor) getDriver()).executeScript(
            "var el = document.evaluate(arguments[0], document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; if(el) el.click();",
            timezoneDropdownXpath);
        await();
        // Re-find zoneinput after dropdown opens to avoid StaleElementReferenceException
        for (int i = 0; i < 3; i++) {
            try {
                awaitForElementPresence(zoneinput);
                zoneinput.fill().with("kolkata");
                break;
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                await();
                if (i == 2) throw e;
            }
        }
        await();
        // Wait for list to be in DOM without holding a reference; then click via XPath in JS (no stale ref)
        new WebDriverWait(getDriver(), 20).until(
            ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='time-zone time-headers']//following::*[contains(@class,'RCB-list-item')]")));
        String timezonelistXpath = "//*[@class='time-zone time-headers']//following::*[contains(@class,'RCB-list-item')]";
        ((JavascriptExecutor) getDriver()).executeScript(
            "var el = document.evaluate(arguments[0], document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; if(el) el.click();",
            timezonelistXpath);
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
        new WebDriverWait(getDriver(), 20).until((ExpectedCondition<Boolean>) d -> getGroup(type).size() > group);
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                FluentWebElement rowGroup = getGroup(type).get(group);
                new WebDriverWait(getDriver(), 20).until((ExpectedCondition<Boolean>) d -> rowGroup.find(pinSortRuleGroups).size() > index);
                FluentWebElement row = rowGroup.find(pinSortRuleGroups).get(index);
                FluentWebElement attributeElement = row.findFirst(sortAttribute);
                FluentWebElement valueElement;
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
                await();
                return;
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                await();
            } catch (IndexOutOfBoundsException e) {
                await();
            }
        }
    }

    public void selectBoostValue(FluentWebElement slider) {
        Assert.assertTrue(boostSlider.isDisplayed());

        for (int i = 1; i <= 2; i++) {
            slider.getElement().sendKeys(Keys.ARROW_RIGHT);
        }
    }

    public void selectAttribute(String value, FluentWebElement attribute) throws InterruptedException {
        scrollUntilVisible(attribute);
        await();
        FluentWebElement attributeArrow = attribute.findFirst(".RCB-select-arrow");
        waitForElementToBeClickable(attributeArrow, "Attribute dropdown arrow");
        safeClick(attributeArrow);
        new WebDriverWait(getDriver(), 20).until((ExpectedCondition<Boolean>) d -> attributeDropDownList.size() > 0);
        if (attributeDropDownList.size() > 0) {
            attributeInput.clear();
            attributeInput.fill().with(value);
            new WebDriverWait(getDriver(), 20).until((ExpectedCondition<Boolean>) d -> attributeDropDownList.size() > 0);
            await();
            WebElement optionToSelect = new WebDriverWait(getDriver(), 20).until((ExpectedCondition<WebElement>) d -> {
                for (FluentWebElement el : attributeDropDownList) {
                    if (value != null && !value.trim().isEmpty()
                            && el.getText().trim().toLowerCase().contains(value.trim().toLowerCase())) {
                        try {
                            WebElement we = getConcreteWebElement(el);
                            if (we == null) we = unwrapWebElement(el.getElement());
                            if (we != null && we.isDisplayed() && we.isEnabled()) return we;
                        } catch (Exception ignored) { }
                    }
                }
                return null;
            });
            if (optionToSelect != null) {
                WebElement toClick = unwrapWebElement(optionToSelect);
                if (toClick == null) toClick = optionToSelect;
                new WebDriverWait(getDriver(), 5).until(ExpectedConditions.elementToBeClickable(toClick));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", toClick);
            } else {
                try {
                    WebElement inputEl = getConcreteWebElement(attributeInput);
                    if (inputEl == null) inputEl = unwrapWebElement(attributeInput.getElement());
                    if (inputEl != null) {
                        inputEl.sendKeys(Keys.ARROW_DOWN);
                        await();
                        inputEl.sendKeys(Keys.ENTER);
                    }
                } catch (Exception e) {
                    selectAttributeOptionInOpenDropdown(value);
                }
            }
            await();
        } else {
            Assert.fail("ATTRIBUTE DROPDOWN LIST IS EMPTY!!!");
        }
    }

    private void selectAttributeOptionInOpenDropdown(String value) {
        String searchLower = value == null ? "" : value.trim().toLowerCase();
        java.util.List<WebElement> options = getDriver().findElements(
            By.cssSelector(".RCB-inline-modal-body .RCB-align-left .RCB-list-item, .RCB-inline-modal .RCB-align-left .RCB-list-item"));
        for (WebElement opt : options) {
            if (!opt.isDisplayed()) continue;
            String text = opt.getText().trim();
            if (!text.isEmpty() && text.toLowerCase().contains(searchLower)) {
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", unwrapWebElement(opt));
                await();
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", unwrapWebElement(opt));
                return;
            }
        }
        selectDropDownValue(attributeDropDownList, value);
    }

    public void selectAttributeValue(String value) throws InterruptedException {
        AttributeDropDown.click();
        new WebDriverWait(getDriver(), 20).until((ExpectedCondition<Boolean>) d -> attributeDropDownList.size() > 0);
        attributeInput.clear();
        attributeInput.fill().with(value);
        await();
    }

    public void selectSortAttribute(String value, FluentWebElement attribute) throws InterruptedException {
        await();
        scrollUntilVisible(attribute);
        FluentWebElement sortArrow = attribute.findFirst(".RCB-select-arrow");
        waitForElementToBeClickable(sortArrow, "Sort attribute dropdown arrow");
        safeClick(sortArrow);
        new WebDriverWait(getDriver(), 20).until((ExpectedCondition<Boolean>) d -> attributeDropDwnList.size() > 0);
        selectDropDownValue(attributeDropDwnList, value);
        await();
    }


    public void selectCondition(String condition, FluentWebElement comparator) {
        scrollUntilVisible(comparator);
        await();
        waitForElementToBeClickable(comparator, "Condition comparator");
        safeClick(comparator);
        new WebDriverWait(getDriver(), 5).until((ExpectedCondition<Boolean>) d -> conditionList.size() > 0);
        Assert.assertTrue(conditionList.size() > 0, "Conditions are not loading");
        for (FluentWebElement option : conditionList) {
            if (option.getTextContent().trim().toLowerCase().contains(condition.toLowerCase())) {
                scrollUntilVisible(option);
                safeClick(option);
                break;
            }
        }
    }

    public void selectValue(String value, FluentWebElement element) {
        if (element == null) return;
        scrollUntilVisible(element);
        await();
        waitForElementToBeClickable(element, "Filter value input");
        safeClick(element);
        await();
        FluentWebElement targetInput = null;
        try {
            if (Attribbutevalue != null && awaitForElementPresence(Attribbutevalue) && Attribbutevalue.isDisplayed()) {
                targetInput = Attribbutevalue;
            }
        } catch (Exception ignored) { }
        if (targetInput == null && element != null) {
            try {
                WebElement el = element.getElement();
                if (el != null && "input".equalsIgnoreCase(el.getTagName())) {
                    targetInput = element;
                } else if (element.find("input").size() > 0) {
                    targetInput = element.findFirst("input");
                } else {
                    targetInput = element;
                }
            } catch (Exception e) {
                targetInput = element;
            }
        }
        if (targetInput != null) {
            targetInput.clear();
            targetInput.fill().with(value);
        }
        await();
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
        await();
        for (FluentWebElement element : merchandisingSections) {
            if (element.getText().trim().contains(section.getLabel())) {
                safeClick(element);
                awaitTillElementDisplayed(publishButton);
                break;
            }
        }
        System.out.println("WAITING FOR CONSOLE PREVIEW TO LOAD!!! INCASE OF NO RESULTS IN CONSOLE PREVIEW THIS VALIDATION WILL FAIL");
    }


    public MerchandisingActions nextPage() {
        try {
            new WebDriverWait(getDriver(), 20).until(
                ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".browse-picker-modal")));
        } catch (Exception ignored) {
        }
        awaitTillElementDisplayed(nextButton);
        scrollUntilVisible(nextButton);
        waitForElementToBeClickable(nextButton, "Next button");
        try {
            nextButton.click();
        } catch (ElementClickInterceptedException e) {
            WebElement el = getConcreteWebElement(nextButton);
            if (el == null) el = unwrapWebElement(nextButton.getElement());
            if (el != null) ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", el);
        }
        awaitForPageToLoad();
        return this;
    }

    public void clickOnApplyButton() {
        awaitTillElementDisplayed(applyButton);
        scrollUntilVisible(applyButton);
        waitForElementToBeClickable(applyButton, "Apply button");
        safeClick(applyButton);
        await();
    }

    public void deleteConditionIfItsPresent(int group) {
        if (conditionsList.size() > 0) {
            for (int i = 0; i < group; i++) {
                deleteMerchandizingCondition();
                await();
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
                        click(globalBoostButton);
                    }
                return;
            case GLOBALFILTER:
                if(awaitForElementPresence(AddBoostRuleButton)){
                    click(AddBoostRuleButton);}
                else if(awaitForElementPresence(globalFilterButton)){
                click(globalFilterButton);}
            default:
                return;
        }
    }


    public void selectPinningProduct(int product) {
        scrollUntilVisible(pinningDropdown);
        try {
            pinningDropdown.click();
        } catch (ElementClickInterceptedException e) {
            WebElement el = getConcreteWebElement(pinningDropdown);
            if (el == null) el = unwrapWebElement(pinningDropdown.getElement());
            if (el != null) ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", el);
        }
        await();
        if (pinningDropDownList.size() > 0) {
            FluentWebElement productItem = pinningDropDownList.get(product);
            scrollUntilVisible(productItem);
            try {
                productItem.click();
            } catch (ElementClickInterceptedException e) {
                WebElement el = getConcreteWebElement(productItem);
                if (el == null) el = unwrapWebElement(productItem.getElement());
                if (el != null) ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", el);
            }
        } else {
            Assert.fail("PINNING DROPDOWN LIST IS EMPTY");
        }
    }

    public void pinProductInFirstPosition(String pinningPosition) {
        clickOnApplyButton();
        await();
    }

    public void selectSortAtrributeAndOrder(String Attribute) {
        if (sortAttributeList.size() > 0) {
            for (int i = 0; i < sortAttributeList.size(); i++) {
                await();
                searchInput.fill().with(Attribute);
                sortAttributeList.get(i).click();
                await();
            }
        }
    }

    /**
     * Waits for console preview to load: at least (positionIndex + 1) product cards and pin icons.
     * Fails the test if not found within timeout.
     */
    public void waitForConsolePreviewToLoad(int positionIndex) {
        int timeoutSeconds = 30;
        try {
            new WebDriverWait(getDriver(), timeoutSeconds).until(
                (ExpectedCondition<Boolean>) d -> getDriver().findElements(By.cssSelector(".product-card")).size() > positionIndex);
            new WebDriverWait(getDriver(), 15).until(
                (ExpectedCondition<Boolean>) d -> getDriver().findElements(By.cssSelector(".unpinned-badge")).size() > positionIndex);
        } catch (Exception e) {
            Assert.fail("Console preview did not load: no product or pin icon at position " + (positionIndex + 1)
                + " within " + timeoutSeconds + "s. " + (e.getMessage() != null ? e.getMessage() : ""));
        }
    }

    public void pinProductFromConsolePreview(String pinningPosition) {
        int i = Integer.parseInt(pinningPosition);
        waitForConsolePreviewToLoad(i);
        if (listOfProductInPreview.size() <= i) {
            Assert.fail("Console preview has no product at position " + (i + 1));
        }
        if (pinTheProduct.size() <= i) {
            Assert.fail("Console preview has no pin icon at position " + (i + 1));
        }
        awaitForElementPresence(pinTheProduct.get(i));
        scrollUntilVisible(listOfProductInPreview.get(i));
        Helper.mouseOver(listOfProductInPreview.get(i).getElement());
        ThreadWait();
        safeClick(pinTheProduct.get(i));
    }


    public void clickSortOrder(String sortOrder) {
        click(sortOrder);
        selectSortAtrributeAndOrder(sortOrder);
    }

    public void verifySortAscendingOrder() {
        ArrayList<String> productTitles = new ArrayList<>();
        for (int i = 0; i < productTitle.size(); i++) {
            String productTitleName = productTitle.get(i).getText();
            productTitles.add(productTitleName);
        }
        ArrayList<String> expected = new ArrayList<>(productTitles);
        Collections.sort(expected);
        Assert.assertEquals(productTitles, expected, "Not in ascending order.");
    }


    public void verifySortDescendingOrder() {
        ArrayList<String> productTitles = new ArrayList<>();
        int Count = productTitle.size();
        for (int i = 0; i < Count; i++) {
            String productTitleName = productTitle.get(i).getText();
            productTitles.add(productTitleName);
            ArrayList<String> expected = new ArrayList<>(productTitles);
            Collections.sort(expected, Collections.reverseOrder());
            Assert.assertEquals(productTitles, expected, "Not in descending order.");
        }

    }

    public void getMerchandisingCondition(String condition) {
        if (merchandisingConditionList.size() > 0) {
            for (int i = 0; i < merchandisingConditionList.size(); i++) {
                merchandisingConditionList.get(i).find(".action-title").getText().trim().equalsIgnoreCase(condition);
                Helper.mouseOver(merchandisingConditionList.get(i).getElement());
                awaitForElementPresence(searchPageActions.queryEditButton);
                click(searchPageActions.queryEditButton);
                await();
            }
        }
    }

    public void deleteMerchandizingCondition() {
        awaitForElementPresence(deleteThePromtionMerchandizingSet);
        click(deleteThePromtionMerchandizingSet);
        awaitForElementPresence(searchPageActions.modalWindow);
        awaitForElementPresence(searchPageActions.deleteYesButton);
        click(searchPageActions.deleteYesButton);
        await();
    }

    public void verifySlotIconIsPresentAtGivenPosition(int slotCount) {
        await();
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
            awaitTillElementDisplayed(landingPageToggle);
            safeClick(landingPageToggle);
            new WebDriverWait(getDriver(), 20).until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".landing-page-toggle .active")));
            Assert.assertTrue(awaitForElementPresence(landingPageEnabledToggle), "LANDING PAGE IS NOT ENABLED");
        }

    public void goToSearch_browsePreview() throws InterruptedException {
        if (awaitForElementPresence(searchPageActions.menuIcon)) {
            click(searchPageActions.menuIcon);
        }
        awaitForElementPresence(seach_browsepreview);
        click(seach_browsepreview);
        await();
    }
    public void ClickViewHideInsight()
    {
        await();
        click(view_hide_insight);
        await();
    }

    public boolean switchPreviewTab() {
        try {
            new WebDriverWait(getDriver(), 20).until((ExpectedCondition<Boolean>) d -> getDriver().getWindowHandles().size() >= 2);

            ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
            int totalTabs = tabs.size();
            System.out.println("Total tabs open: " + totalTabs);

            if (totalTabs < 2) {
                System.out.println("Not enough tabs are open. Total tabs: " + totalTabs);
                return false;
            }

            int targetTabIndex;
            if (totalTabs == 2) {
                targetTabIndex = 1;
                System.out.println("Two tabs detected, switching to tab index: " + targetTabIndex);
            } else if (totalTabs >= 3) {
                targetTabIndex = 2;
                System.out.println("Three or more tabs detected, switching to tab index: " + targetTabIndex);
            } else {
                return false;
            }

            getDriver().switchTo().window(tabs.get(targetTabIndex));
            System.out.println("Successfully switched to tab index: " + targetTabIndex);
            awaitForPageToLoad();
            new WebDriverWait(getDriver(), 15).until((ExpectedCondition<Boolean>) d -> getDriver().getCurrentUrl().contains("preview"));

            return true;
        } catch (Exception e) {
            System.out.println("Error switching tabs: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void openPreviewAndSwitchTheTab() throws InterruptedException {
        goToSearch_browsePreview();

        if (switchPreviewTab()) {
            System.out.println("Now working in preview tab");
            awaitForPageToLoad();
            String currentUrl = getDriver().getCurrentUrl();
            if (!currentUrl.contains("preview")) {
                System.out.println("Warning: URL does not contain 'preview' after switching. Current URL: " + currentUrl);
            }
        } else {
            System.out.println("Failed to switch to preview tab");
            System.out.println("Current URL: " + getDriver().getCurrentUrl());
        }
    }



    public void dragAndDropPinningPosition() {
        if (listOfProductInPreview.size() > 2) {
            WebElement from = listOfProductInPreview.get(1).getElement();
            WebElement to = listOfProductInPreview.get(2).getElement();
            try {
                ((JavascriptExecutor) getDriver()).executeScript(
                    "arguments[1].parentNode.insertBefore(arguments[0], arguments[1]);", unwrapWebElement(from), unwrapWebElement(to));
            } catch (Exception e) {
                await();
                new org.openqa.selenium.interactions.Actions(getDriver()).dragAndDrop(unwrapWebElement(from), unwrapWebElement(to)).perform();
            }
            await();
        }
    }
}

