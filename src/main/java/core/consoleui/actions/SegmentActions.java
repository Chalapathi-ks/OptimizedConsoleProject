package core.consoleui.actions;

import core.consoleui.page.SegmentPage;
import lib.Helper;
import lib.enums.UnbxdEnum;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Map;

import static lib.constants.UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE;


public class SegmentActions extends SegmentPage {

    @Page
    CommercePageActions searchPageActions;

    public void fillLocation(UnbxdEnum type,String locationName,int indexRange) throws InterruptedException {
        FluentWebElement segmentType = getSegmentType((type));
        ThreadWait();
        segmentType.findFirst(segmentInputBox).click();
        locationSearchBox.fill().with(locationName);
        ThreadWait();
        locDropdownList.get(indexRange).click();
    }

    private static final By SEGMENT_USER_TYPE_EDIT_BY = By.xpath("//*[text()=\"User type\"]//following::*[contains(@class,\"new-summary-tag\")][1]");
    private static final By SEGMENT_USER_TYPE_LISTING_BY = By.xpath("//*[contains(normalize-space(text()),'User Type') or contains(normalize-space(text()),'User type')]//following::*[contains(@class,'seg-item-col')][1]");

    /** Wait for segment edit panel (User type value) to load after opening EDIT. */
    public void awaitForSegmentEditPanelLoaded(int timeoutSec) {
        new WebDriverWait(getDriver(), timeoutSec).until(ExpectedConditions.presenceOfElementLocated(SEGMENT_USER_TYPE_EDIT_BY));
    }

    /** Wait for segment listing row (User Type column) to appear. */
    public void awaitForSegmentUserTypeInListingPage(int timeoutSec) {
        new WebDriverWait(getDriver(), timeoutSec).until(ExpectedConditions.presenceOfElementLocated(SEGMENT_USER_TYPE_LISTING_BY));
    }

    public FluentWebElement getSegmentType(UnbxdEnum type) {
        switch (type) {
            case LOCATION:
                return locationFilter;
            case USERTYPE:
                return userTypeFilter;
            case DEVICETYPE:
                return deviceTypeFilter;
            default:
                return null;
        }
    }

    public void selectTypeValues(UnbxdEnum type,String segmentvalue) throws InterruptedException {
        shortWait();
        safeClick(userTypeFilter);
        selectDropDownValue(typeValueList,segmentvalue);
        shortWait();
        safeClick(userTypeFilter);
        shortWait();
        safeClick(outsideBox);
    }

    public void selectDeviceType(UnbxdEnum type,String segmentvalue) throws InterruptedException {
        shortWait();
        safeClick(deviceTypeFilter);
        selectDropDownValue(typeValueList,segmentvalue);
        shortWait();
        safeClick(deviceTypeFilter);
        shortWait();
        safeClick(outsideBox);
    }

    public void removeSegmentValues(String SegmentType)
    {
        String value= segmentUserTypeValues.getText().toLowerCase().trim();
        if(value.contains(SegmentType.toLowerCase()))
        {
            removeSegmentButton.click();
        }
        else{
            Assert.fail("SEGMENT VALUE TYPE IS NOT MATCHED!!! EXPECTED VALUE IS : "+SegmentType+"ACTUAL is:" +value);
        }
    }


    public void clickOnSave(){
       awaitForElementPresence(saveSegmentButton);
       click(saveSegmentButton);
    }

    public void fillSegmentName(String query)
    {
        awaitForElementPresence(segmentName);
        threadWait();
        segmentName.fill().with(query);
        threadWait();
    }

    /** Polls for the segment rule to appear in the listing (e.g. after save). Returns null if not found within maxWaitSec. */
    public FluentWebElement waitForSegmentRulePresent(String name, int maxWaitSec) {
        long deadline = System.currentTimeMillis() + maxWaitSec * 1000L;
        while (System.currentTimeMillis() < deadline) {
            FluentWebElement el = segmentRuleByName(name);
            if (el != null) return el;
            threadWait();
            threadWait();
        }
        return null;
    }

    public FluentWebElement segmentRuleByName(String name) {
        final int maxAttempts = 10;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            if (attempt > 1) threadWait();
            shortWait();
            awaitForElementPresence(searchPageActions.searchIcon);
            if (searchPageActions.queryRulesList.size() == 0) {
                if (attempt < maxAttempts) continue;
                return null;
            }
            safeClick(searchPageActions.searchIcon);
            shortWait();
            scrollUntilVisible(searchPageActions.searchInputBox);
            safeClick(searchPageActions.searchInputBox);
            searchPageActions.searchInputBox.clear();
            unbxdInputBoxSearch(searchPageActions.searchInputBox, name);
            await();
            for (FluentWebElement e : searchPageActions.queryRulesList) {
                try {
                    if (searchPageActions.getQueryNameFromQueryRule(e).trim().contains(name))
                        return e;
                } catch (Exception ignored) { }
            }
            if (attempt < maxAttempts)
                threadWait();
        }
        return null;
    }
    public void createSegment(String segmentname) throws InterruptedException {
        searchPageActions.awaitForPageToLoad();
        searchPageActions.threadWait();
        if (segmentRuleByName(segmentname) != null) {
            deleteSegmentRule(segmentname);
            searchPageActions.awaitForElementPresence(searchPageActions.addBannerButton);
            ThreadWait();
            safeClick(searchPageActions.addRuleButton);
            shortWait();
            fillSegmentName(segmentname);
            shortWait();
        }else{
            if(searchPageActions.awaitForElementPresence(searchPageActions.addRuleButton));
            shortWait();
            safeClick(searchPageActions.addRuleButton);
            shortWait();
            fillSegmentName(segmentname);
            shortWait();
        }
        awaitForElementPresence(userTypeFilter);
    }


    public void deleteSegmentRule(String name) {
        ThreadWait();
 //       segmentRuleByName(name);
//        Assert.assertTrue((getQueryNameFromSegmentRule(element).contains(name)), QUERY_RULE_SEARCH_FAILURE);
//        Helper.mouseOver(element.findFirst(searchPageActions.segmentCampaignContainer).getElement());

        awaitForElementPresence(searchPageActions.deleteRuleButton);
        click(searchPageActions.deleteRuleButton);
        awaitForElementPresence(searchPageActions.modalWindow);
        awaitForElementPresence(searchPageActions.deleteYesButton);
        click(searchPageActions.deleteYesButton);
        awaitForElementPresence(successMessage);
        // Check for success or error messages
        if (awaitForElementPresence(successMessage)) {
            ThreadWait();
            Assert.assertTrue(checkSuccessMessage(), SUCCESS_MESSAGE_FAILURE);
            awaitForElementNotDisplayed(searchPageActions.deleteYesButton);
        } else if (awaitForElementPresence(deleteErrorMessage)) {
            ThreadWait();
            Assert.fail(deleteErrorMessage.getText());
        }

    }

    public void selectSegmentActionType (UnbxdEnum type, String name) {
        FluentWebElement element = segmentRuleByName(name);
        threadWait();
        FluentWebElement container = element.findFirst(searchPageActions.segmentCampaignContainer);
        WebElement containerEl = getConcreteWebElement(container);
        if (containerEl == null) containerEl = unwrapWebElement(container.getElement());
        if (containerEl != null) Helper.mouseOver(containerEl);

        switch (type) {
            case PREVIEW:
                awaitForElementPresence(searchPageActions.queryPreviewButton);
                click(searchPageActions.queryPreviewButton);
                return;
            case EDIT:
                awaitForElementPresence(searchPageActions.queryruleEditButton);
                click(searchPageActions.queryruleEditButton);
                return;
            case MORE:
                awaitForElementPresence(searchPageActions.menuIcon);
                click(searchPageActions.menuIcon);
            default:
                return;
        }
    }



    public String fillCampaignData(Map<String, Object> campaignData) throws InterruptedException {
        String campaignName = "AutoTest" + System.currentTimeMillis();
        awaitForElementPresence(campaignNameInput);
        campaignNameInput.fill().with(campaignName);
        awaitForElementPresence(moreOptionLink);
        click(moreOptionLink);
        threadWait();
        return campaignName;
    }

    public String addAndSaveCustomAttribute(String customAttributeName) {

        awaitForElementPresence(settingsIcon);
        click(settingsIcon);
        awaitForElementPresence(addCustomAttributeButton);
        ThreadWait();
        
        // Check if custom attribute already exists and delete it if found
        boolean attributeExists = false;
        for (int i = 0; i < attributeRows.size(); i++) {
            FluentWebElement attributeTextElement = attributeTexts.get(i);
            if (attributeTextElement != null) {
                String attributeText = attributeTextElement.getText();
                if (attributeText.contains("Custom attribute : " + customAttributeName)) {
                    attributeExists = true;
                    // Delete the existing attribute
                    FluentWebElement deleteBox = deleteBoxes.get(i);
                    if (deleteBox != null) {
                        FluentWebElement deleteIcon = deleteSmallIcons.get(i);
                        if (deleteIcon != null) {
                            click(deleteIcon);
                            threadWait();
                            DeleteYes.click();
                            threadWait();
                            break;
                        }
                    }
                }
            }
        }
        
        click(addCustomAttributeButton);
        awaitForElementPresence(customAttributeInput);
        customAttributeInput.fill().with(customAttributeName);
        String enteredValue = customAttributeInput.getAttribute("value");
        awaitForElementPresence(saveCustomAttributeButton);
        click(saveCustomAttributeButton);
        closeCustomAttributeTagInputPopup.click();
        return enteredValue;
    }

    public String selectAndEnterCustomAttribute(String Value) {
        // Click on the custom attribute dropdown
        ThreadWait();
        awaitForElementPresence(customAttributeDropdown);
        safeClick(customAttributeDropdown);
        ThreadWait();
        FluentWebElement searchInput = null;
        for (int i = 0; i < 3; i++) {
            try {
                searchInput = findFirst(".RCB-dd-search .RCB-dd-search-ip");
                if (searchInput != null && searchInput.isDisplayed()) break;
            } catch (Exception ignored) { }
            if (searchInput == null || !searchInput.isDisplayed()) {
                try {
                    searchInput = findFirst(".RCB-dd-search-ip");
                    if (searchInput != null && searchInput.isDisplayed()) break;
                } catch (Exception ignored) { }
            }
            shortWait();
        }
        if (searchInput == null)
            searchInput = findFirst(".RCB-dd-search-ip");
        if (searchInput == null)
            throw new RuntimeException("Could not find custom attribute dropdown search input (.RCB-dd-search .RCB-dd-search-ip or .RCB-dd-search-ip)");
        awaitForElementPresence(searchInput);
        scrollUntilVisible(searchInput);
        searchInput.fill().with(Value);
        // Find and click the matching option in the dropdown
        FluentList<FluentWebElement> options = find(".RCB-list-item");
        boolean found = false;
        for (FluentWebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(Value)) {
                shortWait();
                scrollUntilVisible(option);
                waitForElementToBeClickable(option, "Custom attribute option");
                try {
                    option.click();
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    clickUsingJS(option);
                }
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("' not found in dropdown after searching.");

        }
        return customAttributeTagInput.getAttribute("value");

    }

      public String  selectAndEnterCustomValue(String Value)
      {
        click(customInput);
        click(customAttributeTagInput);
        awaitForElementPresence(customAttributeTagInput);
        customAttributeTagInput.fill().with(Value);
        WebElement inputEl = unwrapWebElement(customAttributeTagInput.getElement());
        if (inputEl == null) inputEl = getDriver().findElement(org.openqa.selenium.By.cssSelector(".tag-input"));
        if (inputEl != null) inputEl.sendKeys(Keys.ENTER);
        threadWait();
        return customAttributeTagInput.getAttribute("value");



    }

    public void deleteCustomAttribute(String attributeName) {
        try {
            awaitForElementPresence(settingsIcon);
            click(settingsIcon);
            awaitForElementPresence(addCustomAttributeButton);
            threadWait();

            // Use page object elements instead of hardcoded selectors
            for (int i = 0; i < attributeRows.size(); i++) {
                FluentWebElement row = attributeRows.get(i);
                FluentWebElement attributeTextElement = attributeTexts.get(i);
                if (attributeTextElement != null) {
                    String attributeText = attributeTextElement.getText();

                    if (attributeText.contains("Custom attribute : " + attributeName)) {
                        FluentWebElement deleteBox = deleteBoxes.get(i);
                        if (deleteBox != null) {
                            FluentWebElement deleteIcon = deleteSmallIcons.get(i);
                            if (deleteIcon != null) {
                                click(deleteIcon);
                                threadWait();
                                DeleteYes.click();
                                return;
                            }
                        }
                    }
                }
            }

            throw new RuntimeException("Custom attribute '" + attributeName + "' not found in the list");

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete custom attribute '" + attributeName + "': " + e.getMessage(), e);
        }
    }

}