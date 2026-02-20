package core.consoleui.actions;

import core.consoleui.page.SegmentPage;
import lib.Helper;
import lib.enums.UnbxdEnum;
import lib.compat.Page;
import lib.compat.FluentList;
import lib.compat.FluentWebElement;
import org.testng.Assert;

import java.util.Map;

import static lib.constants.UnbxdErrorConstants.QUERY_RULE_SEARCH_FAILURE;
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
        ThreadWait();
        click(userTypeFilter);
        selectDropDownValue(typeValueList,segmentvalue);
        ThreadWait();
        click(userTypeFilter);
        ThreadWait();
        click(outsideBox);


    }
    public void selectDeviceType(UnbxdEnum type,String segmentvalue) throws InterruptedException {
        ThreadWait();
        click(deviceTypeFilter);
        selectDropDownValue(typeValueList,segmentvalue);
        ThreadWait();
        click(deviceTypeFilter);
        ThreadWait();
        click(outsideBox);
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

    public FluentWebElement segmentRuleByName(String name) {
        ThreadWait();
        awaitForElementPresence(searchPageActions.searchIcon);
        if (searchPageActions.queryRulesList.size() == 0)
            return null;
        click(searchPageActions.searchIcon);
        ThreadWait();
        searchPageActions.searchInputBox.click();
        searchPageActions.searchInputBox.clear();
        unbxdInputBoxSearch(searchPageActions.searchInputBox, name);
        await();
        for (FluentWebElement e : searchPageActions.queryRulesList) {
            if (searchPageActions.getQueryNameFromQueryRule(e).trim().contains(name)) {
                return e;
            }
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
            click(searchPageActions.addRuleButton);
            threadWait();
            fillSegmentName(segmentname);
        }else{
            if(searchPageActions.awaitForElementPresence(searchPageActions.addRuleButton));
            ThreadWait();
            click(searchPageActions.addRuleButton);
            fillSegmentName(segmentname);
        }

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
        Helper.mouseOver(element.findFirst(searchPageActions.segmentCampaignContainer).getElement());

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
        clickMoreOptionIfPresent();
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
        click(customAttributeDropdown);
        ThreadWait();
        findFirst(".RCB-dd-search .RCB-dd-search-ip").fill().with(Value);
        // Find and click the matching option in the dropdown
        FluentList<FluentWebElement> options = find(".RCB-list-item");
        boolean found = false;
        for (FluentWebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(Value)) {
                ThreadWait();
                option.click();
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
        customAttributeTagInput.getElement().sendKeys(org.openqa.selenium.Keys.ENTER);
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