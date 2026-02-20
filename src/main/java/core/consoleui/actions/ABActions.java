package core.consoleui.actions;

import core.consoleui.page.ABTestPage;
import lib.enums.UnbxdEnum;
import org.fluentlenium.core.annotation.Page;
import org.testng.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ABActions extends ABTestPage {

    @Page
    CommercePageActions searchPageActions;

    @Page
    SegmentActions segmentActions;

    public void enableABToggle() {
        if (awaitForElementPresence(abTestToggle) == true) {
            click(abTestToggle);
            new WebDriverWait(getDriver(), 25).until(
                ExpectedConditions.visibilityOf(abTestEnabledToggle.getElement()));
            Assert.assertTrue(awaitForElementPresence(abTestEnabledToggle), "AB CONDITION TOGGLE IS NOT ENABLED");
        }else{
            Assert.fail("AB TEST TOGGLE BUTTON IS NOT PRESENT");
        }
    }
    public void selectWinnerDecidedByabData(String expectedValue) {
        awaitForElementPresence(winnerDecidedBy);
        threadWait();
        click(winnerDecidedBy);

        // Wait for dropdown options to be present and at least one to have non-empty text
        int maxTries = 15;
        int tries = 0;
        while (tries < maxTries) {
            ThreadWait();
            if (segmentActions.typeValueList.size() > 0) {
                boolean hasMatchingOrNonEmpty = false;
                for (org.fluentlenium.core.domain.FluentWebElement ele : segmentActions.typeValueList) {
                    try {
                        String text = ele.getText();
                        if (text != null && !text.trim().isEmpty()) {
                            hasMatchingOrNonEmpty = true;
                            if (text.trim().equalsIgnoreCase(expectedValue != null ? expectedValue.trim() : "")) {
                                click(ele);
                                return;
                            }
                        }
                    } catch (Exception ignored) { }
                }
                if (hasMatchingOrNonEmpty) {
                    // Options loaded but no exact match yet; retry once more for timing
                    ThreadWait();
                    for (org.fluentlenium.core.domain.FluentWebElement ele : segmentActions.typeValueList) {
                        try {
                            if (ele.getText().trim().equalsIgnoreCase(expectedValue != null ? expectedValue.trim() : "")) {
                                click(ele);
                                return;
                            }
                        } catch (Exception ignored) { }
                    }
                    break;
                }
            }
            tries++;
        }
        if (segmentActions.typeValueList.size() == 0) {
            Assert.fail("No values to select from the dropdown for winnerDecidedBy after waiting. Possible UI/data/selector issue.");
        }
        if (expectedValue == null || expectedValue.trim().isEmpty()) {
            Assert.fail("'winnerDecidedBy' value is missing or empty.");
        }
        final String expectedTrim = expectedValue.trim();
        for (org.fluentlenium.core.domain.FluentWebElement ele : segmentActions.typeValueList) {
            try {
                if (ele.getText().trim().equalsIgnoreCase(expectedTrim)) {
                    click(ele);
                    return;
                }
            } catch (Exception ignored) { }
        }
        String ev = expectedValue != null ? expectedValue : "";
        Assert.fail("No matching value '" + ev + "' found in the dropdown for winnerDecidedBy. Available options: " + segmentActions.typeValueList.stream().map(e -> { try { return e.getText(); } catch (Exception ex) { return ""; } }).collect(java.util.stream.Collectors.toList()));
    }

     public String getWinnerDecidedByValue(){
         return winnerDecidedBy.getText();
     }
    // Wait for page to load and scroll to the complete end of the page (loop until bottom)
    private void waitAndScrollToPageBottom() {
        awaitForPageToLoad();
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) getDriver();
        long lastHeight = ((Number) js.executeScript("return document.body.scrollHeight;")).longValue();
        while (true) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            try { Thread.sleep(700); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            long newHeight = ((Number) js.executeScript("return document.body.scrollHeight;")).longValue();
            if (newHeight == lastHeight) {
                break;
            }
            lastHeight = newHeight;
        }
    }

    // Scroll to the ab-summary-strategy-box element
    private void scrollToAbSummaryStrategyBox() {
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) getDriver();
        org.openqa.selenium.By by = org.openqa.selenium.By.cssSelector("div.ab-summary-strategy-box");
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        org.openqa.selenium.WebElement element = null;
        try {
            element = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            throw new RuntimeException("ab-summary-strategy-box element not found or not visible", e);
        }
        js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", unwrapWebElement(element));
        try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public void selectPreferredOptionABData(){
        waitAndScrollToPageBottom();
        scrollToAbSummaryStrategyBox();
        awaitForElementPresence(preferredOption);
        threadWait();
        click(preferredOption);
        threadWait();
        scrollToAbSummaryStrategyBox();

        String targetText = "Variation A"; // Change as needed
        org.fluentlenium.core.domain.FluentWebElement targetElement = null;
        for (org.fluentlenium.core.domain.FluentWebElement ele : segmentActions.typeValueList) {
            if (ele.getText().trim().equalsIgnoreCase(targetText)) {
                targetElement = ele;
                break;
            }
        }
        if (targetElement == null && segmentActions.typeValueList.size() > 0) {
            targetElement = segmentActions.typeValueList.get(0); // fallback to first
        }
        if (targetElement == null) {
            throw new RuntimeException("No element found to click in typeValueList");
        }
        // Simple click only
        targetElement.click();
    }
    public String getPreferredOptionABData(){
        return preferredOption.getText();
    }

    public void selectABConfiguration(UnbxdEnum type) {
        switch (type) {
            case VARIATIONA:
                click(variantionATab);
                break;
            case VARIATIONB:
                click(variantionBTab);
                break;
            default:
        }
    }
        public void fillABPercentage(UnbxdEnum abType, int percentage){
            switch (abType) {
                case UNBXDDEFAULT:
                    click(persentageTabDefault).clear();
                    persentageTabDefault.fill().with(String.valueOf(percentage));
                    ThreadWait();
                   break;
                case VARIATIONA:
                    click(persentageTabVariantA);
                    persentageTabVariantA.fill().with(String.valueOf(percentage));
                    ThreadWait();
                    break;
                case VARIATIONB:
                    click(persentageTabVariantB);
                    persentageTabVariantB.fill().with(String.valueOf(percentage));
                    ThreadWait();
                    break;
                default:
            }
        }

    // General method to scroll to the bottom of any element by CSS selector (simple version, no retry)
    private void scrollToBottomOfElement(String cssSelector) {
        org.openqa.selenium.WebDriver driver = getDriver();
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        org.openqa.selenium.By by = org.openqa.selenium.By.cssSelector(cssSelector);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        org.openqa.selenium.WebElement element = null;
        try {
            element = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            throw new RuntimeException("Element not found or not visible: " + cssSelector, e);
        }
        js.executeScript("arguments[0].scrollIntoView({block: 'end', behavior: 'smooth'});", unwrapWebElement(element));
        try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    // Convenience wrapper for rule-content
    public void scrollToBottomOfRuleContent() {
        scrollToBottomOfElement("div.rule-content");
    }

}


