package core.consoleui.page;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import core.ui.page.UnbxdCommonPage;

public class CampaignCreationPage extends UnbxdCommonPage {

    @Page
    SegmentPage segmentPage;

    public String deviceTypeName = ".segments-dd-item";

//    @FindBy(css = ".campaign-name-search .RCB-form-el")
//    public FluentWebElement campaignNameInput;

    @FindBy(xpath = "//*[@id='ruleName']")
    public FluentWebElement campaignNameInput;
    @FindBy(css = ".RCB-align-left .flex-display")
    public FluentList<FluentWebElement> deviceTypeList;

    @FindBy(css = ".RCB-form-el-block textarea")
    public FluentWebElement campaignDescription;

    @FindBy(css = ".grey-link-text")
    public FluentWebElement moreOptionLink;

    @FindBy(css = ".dropdown-select")
    public FluentWebElement deviceDropDownButton;

    @FindBy(css = ".browse-dd-item")
    public FluentList<FluentWebElement> segmentDropDownList;

    @FindBy(css=".default-selected")
    public FluentWebElement pageRuleDropdown;

    @FindBy(xpath=" //*[contains(text(),'Build a path')]")
    public FluentWebElement BuildPath;

    @FindBy(css=" .merch-display-name-input .ltr")
    public FluentWebElement SelectedCategoryPathDisplay;

    @FindBy(xpath = "//*[contains(text(),'Select field')]//following::*[@class='RCB-select-arrow']")
    public FluentWebElement browseAttributeArrow;

    @FindBy(css = ".RCB-list-item.dm-dd-item ")
    public FluentList<FluentWebElement> browseAttributeList;

    @FindBy(xpath = "(//*[@class='RCB-form-el-label'])[7]")
    public FluentWebElement BrowseSelectValue;

    @FindBy(css = ".list-item")
    public FluentList<FluentWebElement> categoeyValueList;

    @FindBy(xpath = "//DIV[DIV[.='Selected category path']]/descendant::BUTTON[normalize-space(.)='Apply']")
    public FluentWebElement categorypathApplyButton;


    @FindBy(xpath="(//*[contains(text(),'Segment*')]//following::span[contains(@class,\"RCB-select-arrow\")])[1]")
    public FluentWebElement SSegmentDropDown;

    @FindBy(css=".custom-date")
    public FluentWebElement customDate;

    @FindBy(css=".right-chevron-light")
    public FluentWebElement dateNextButton;

    @FindBy(css=".react-calendar__tile:nth-child(10)")
    public FluentWebElement selectStartDate;

    @FindBy(css=".time-zones-dd .RCB-inline-modal-btn")
    public FluentWebElement timeZone;

    @FindBy(css=".react-calendar__tile:nth-child(11)")
    public FluentWebElement selectEndtDate;

    @FindBy(css=".calendar-apply .RCB-btn-small")
    public FluentWebElement calenderApplyButton;

    @FindBy(css=".RCB-dd-search-ip")
    public FluentWebElement searchSegment;

    public String fillCampaignDataforUpcoming(Map<String, Object> campaignData) throws InterruptedException {
        String campaignName = "AutoTestupcoming" + System.currentTimeMillis();
        awaitForElementPresence(campaignNameInput);
        await();
        campaignNameInput.fill().with(campaignName);
        awaitForElementPresence(moreOptionLink);
        await();
        click(moreOptionLink);
        await();
        //selectDevices((ArrayList<String>) campaignData.get("devices"));
        //Thread.sleep(7000);
//        if (awaitForElementPresence(SelectSegment)) {
//            selectGlobalSegment();
//        }
        return campaignName;
    }

    public void clickonNext()  {
        awaitTillElementDisplayed(nextButton);
        scrollUntilVisible(nextButton);
        awaitForElementPresence(nextButton);
        try {
            waitForElementToBeClickable(nextButton, "Next button");
            nextButton.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", nextButton.getElement());
        }
        awaitForPageToLoad();
        await();
    }

    public String fillCampaignData(Map<String, Object> campaignData) throws InterruptedException {
        String campaignName = "AutoTest" + System.currentTimeMillis();
        awaitForElementPresence(campaignNameInput);
        await();
        campaignNameInput.fill().with(campaignName);
        awaitForElementPresence(moreOptionLink);
        await();
        click(moreOptionLink);
        await();

        awaitTillElementDisplayed(nextButton);
        waitForElementToBeClickable(nextButton, "Next button");
        nextButton.click();
        awaitForPageToLoad();
        await();

        return campaignName;

    }

    public String fillCampaignDataForAB(Map<String, Object> campaignData,String timZone) throws InterruptedException {
        String campaignName = "AutoTest" + System.currentTimeMillis();
        awaitForElementPresence(campaignNameInput);
        await();
        campaignNameInput.fill().with(campaignName);
        awaitForElementPresence(moreOptionLink);
        click(moreOptionLink);
        if(awaitForElementPresence(SSegmentDropDown))
        {
            selectGlobalSegment();
        }

        awaitTillElementDisplayed(nextButton);
        waitForElementToBeClickable(nextButton, "Next button");
        nextButton.click();
        awaitForPageToLoad();
        await();

        return campaignName;

    }
    public void Datepicker(String timeZoneText) throws InterruptedException {
        customDate.click();
        awaitForElementPresence(dateNextButton);
        dateNextButton.click();
        await();
        click(selectStartDate);
        await();
        click(selectEndtDate);
        awaitForElementPresence(timeZone);
        click(timeZone);
        selectDropDownValue(segmentPage.typeValueList,timeZoneText);


    }

    public static Date getNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
        } else {
            calendar.roll(Calendar.MONTH, true);
        }

        return calendar.getTime();
    }
  
    public void selectSegment(String segment) {
        SSegmentDropDown.click();
        searchSegment.fill().with(segment);
        await();
        selectDropDownValue(segmentDropDownList, segment);
        await();
        SSegmentDropDown.click();
    }
    public void selectGlobalSegment()  {
        await();
        SSegmentDropDown.click();
        await();
        selectDropDownValue(segmentDropDownList, "global");
        await();
        SSegmentDropDown.click();
    }


    public void unSelectAllDeviceType() {
        for (int i = 0; i < deviceTypeList.size(); i++) {
            deviceTypeList.get(i).click();
            await();
            Assert.assertFalse(deviceTypeList.findFirst(".segments-dd-checkbox").isSelected());
        }
    }


    public void selectDevices(ArrayList<String> devices) {
        if (devices == null)
            return;
        awaitForElementPresence(deviceDropDownButton);
        click(deviceDropDownButton);
        scrollToBottom();
        unSelectAllDeviceType();
        for (String device : devices)
        {
            for (int i = 0; i < deviceTypeList.size(); i++)
            {
                if(deviceTypeList.get(i).find(deviceTypeName).getText().equals(device))
                {
                    await();
                    click(deviceTypeList.get(i));
                    Assert.assertTrue(deviceTypeList.get(i).findFirst(".segments-dd-checkbox input").isSelected());
                }
            }
        }
        awaitForElementPresence(deviceDropDownButton);
        click(deviceDropDownButton);
    }


}
