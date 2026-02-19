package core.consoleui.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class BannerActions extends CommercePageActions {

    public void goToFieldRuleBanner()
    {
        awaitForElementPresence(bannerFieldRuleButton);
        click(bannerFieldRuleButton);
    }

    public void goToQueryRuleBanner()
    {
            awaitForElementPresence(bannerQueryRuleButton);
            click(bannerQueryRuleButton);
    }

    public void fillHtmlBanner(String html)
    {
        awaitForElementPresence(htmlRadioButtonIsSelected);
        awaitForElementPresence(htmlBannerInput);
        threadWait();
        Assert.assertTrue(htmlRadioButtonIsSelected.isSelected(),"Html Radio Button is not selected");
        htmlBannerInput.clear();
        threadWait();
        htmlBannerInput.fill().with(html);
    }

    public void addHtmlBanner(String html)
    {
        awaitForElementPresence(htmlRadioButton);
        threadWait();
        click(htmlRadioButton);
        threadWait();
        fillHtmlBanner(html);
        threadWait();
        findFirst("body").click();
    }

    public void addImgBanner(String ImgUrl)
    {
        awaitForElementPresence(imageUrlRadioButton);
        threadWait();
        safeClick(imageUrlRadioButton);
        new WebDriverWait(getDriver(), 30).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".banner-tab-header .radio-tab:nth-child(1) input")));
        awaitForElementPresence(bannerInputImgUrl);
        Assert.assertTrue(imgUrlRadioButtonIsSelected.isSelected(),"ImageUrl Radio Button is not selected");
        bannerInputImgUrl.clear();
        threadWait();
        bannerInputImgUrl.fill().with(ImgUrl);
        bannerInputRedirectUrl.clear();
        bannerInputRedirectUrl.fill().with(ImgUrl);
        ThreadWait();
        findFirst("body").click();
    }


    public void fillRedirectURL(String redirctUrl)
    {
        new WebDriverWait(getDriver(), 25).until(
            ExpectedConditions.presenceOfElementLocated(By.cssSelector(".rule-content .RCB-form-el-block input")));
        awaitForElementPresence(redirectInput);
        ThreadWait();
        redirectInput.clear();
        ThreadWait();
        redirectInput.fill().with(redirctUrl);
        findFirst("body").click();
    }

    public void selectFieldRuleAttribute(String attribute) throws InterruptedException {
        click(fieldRuleAttributeDropdown);
        threadWait();
        click(searchAttribute);
        searchAttribute.fill().with(attribute);
        if(fieldRuleAttributeDropDownList.size() ==0){
            Assert.fail("FIELD RULE ATTRIBUTE LISTS ARE NOT DISPLAYED!!! PLEASE SELECT THE ATTRIBUTE FROM THE SETTINGS");
        }
        if (fieldRuleAttributeDropDownList.size() > 0) {
            threadWait();
            threadWait();
            selectDropDownValue(fieldRuleAttributeDropDownList,attribute);
            }
        }





    public void selectFieldRuleAttributeValue(String value) throws InterruptedException {
        click(fieldRuleAttributeValueDropdown);
        ThreadWait();
        if(fieldRuleAttributeDropDownList.size() ==0){
            Assert.fail("FIELD RULE ATTRIBUTE VALUES LISTS ARE NOT DISPLAYED!!!");
        }
        if (fieldRuleAttributeDropDownList.size() > 0) {
            ThreadWait();
            selectDropDownValue(fieldRuleAttributeDropDownList,value);
                }
            }

    /**
     * Scrolls to the bannerExperienceInput element to ensure it is in view.
     */
    public void scrollToBannerExperienceInput() {
        scrollToElement(bannerExperienceInput, "Banner Experience Input");
    }
        }





