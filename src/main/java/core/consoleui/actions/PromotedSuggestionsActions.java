package core.consoleui.actions;
import core.consoleui.page.PromotedSuggestionsPage;
import lib.enums.UnbxdEnum;
import org.testng.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PromotedSuggestionsActions extends PromotedSuggestionsPage {
    public void selectDeviceType(UnbxdEnum type,String segmentvalue) throws InterruptedException {
        ThreadWait();
    }
    /**
     * Adds a promoted suggestion using the modal dialog, always selecting today's date.
     */
    public void addPromotedSuggestion(String term, String timezone) {
        awaitForElementPresence(addPromotedSuggestionButton);
        click(addPromotedSuggestionButton);
        awaitForElementPresence(modalTitle);
        Assert.assertEquals(modalTitle.getText().trim(), "Add Promoted Suggestion", "Modal title mismatch");
        awaitForElementPresence(termsContainer);
        click(termsContainer);
        awaitForElementPresence(visibleTermsInput);
        visibleTermsInput.fill().with(term);
        awaitForElementPresence(customDate);
        click(customDate);
        awaitForElementPresence(todayTile);
        org.openqa.selenium.WebElement realTodayTile = getDriver().findElement(org.openqa.selenium.By.cssSelector(".react-calendar__tile--now"));
        realTodayTile.click();
        // Try to select tomorrow in the current view (next enabled tile after today)
        java.util.List<org.openqa.selenium.WebElement> allTiles = getDriver().findElements(org.openqa.selenium.By.cssSelector(".react-calendar__tile"));
        boolean selectedTomorrow = false;
        for (int i = 0; i < allTiles.size(); i++) {
            if (allTiles.get(i).equals(realTodayTile)) {
                // Try to click the next enabled tile
                for (int j = i + 1; j < allTiles.size(); j++) {
                    if (allTiles.get(j).isEnabled() && allTiles.get(j).isDisplayed()) {
                        allTiles.get(j).click();
                        selectedTomorrow = true;
                        break;
                    }
                }
                break;
            }
        }
        // If not found, go to next month and select the first enabled tile
        if (!selectedTomorrow) {
            org.openqa.selenium.By nextMonthBtnSelector = org.openqa.selenium.By.cssSelector("button[class='react-calendar__navigation__arrow react-calendar__navigation__next-button']");
            org.openqa.selenium.WebElement nextMonthBtn = getDriver().findElement(nextMonthBtnSelector);
            nextMonthBtn.click();
            // Wait for the calendar to update (1s)
            try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            java.util.List<org.openqa.selenium.WebElement> newMonthTiles = getDriver().findElements(org.openqa.selenium.By.cssSelector(".react-calendar__tile"));
            for (org.openqa.selenium.WebElement tile : newMonthTiles) {
                if (tile.isEnabled() && tile.isDisplayed()) {
                    tile.click();
                    selectedTomorrow = true;
                    break;
                }
            }
        }
        if (!selectedTomorrow) {
            throw new RuntimeException("Could not find tomorrow's date tile after today, even after navigating to next month.");
        }
        // Wait until the dropdown icon is clickable, then click it
        org.openqa.selenium.By dropdownArrowSelector = org.openqa.selenium.By.cssSelector(".RCB-select-arrow");
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        org.openqa.selenium.WebElement dropdownArrow = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(dropdownArrowSelector));
        dropdownArrow.click();
        // Wait for the timezone search box to appear, then fill it
        org.openqa.selenium.By searchBoxSelector = org.openqa.selenium.By.cssSelector(".time-zones-dd .RCB-dd-search-ip");
        org.openqa.selenium.WebElement searchBox = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(searchBoxSelector));
        searchBox.clear();
        searchBox.sendKeys(timezone);
        // Wait for the timezone option to appear using XPath, then click it
        String timezoneXpath = "//ul[contains(@class,'RCB-list')]//li[contains(@class,'RCB-list-item') and contains(text(), '" + timezone + "')]";
        org.openqa.selenium.By timezoneOptionSelector = org.openqa.selenium.By.xpath(timezoneXpath);
        org.openqa.selenium.WebElement timezoneOption = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(timezoneOptionSelector));
        timezoneOption.click();
        // Add a 2 second wait before clicking Apply
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        org.openqa.selenium.By applyButtonSelector = org.openqa.selenium.By.cssSelector("button[class='RCB-btn RCB-btn-primary RCB-btn-small ']");
        org.openqa.selenium.WebElement applyButton = getDriver().findElement(applyButtonSelector);
        applyButton.click();
        // Add a 2 second wait to allow modal transitions/overlays to finish
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        // Wait until the Add button is clickable and click it
        org.openqa.selenium.By addButtonSelector = org.openqa.selenium.By.cssSelector(".modal-main-footer .RCB-btn-primary");
        org.openqa.selenium.WebElement addButton = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(addButtonSelector));
        addButton.click();
        awaitForElementPresence(successToast);
        Assert.assertTrue(successToast.isDisplayed(), "Success toast should be visible");
    }
    public void editSuggestions(String oldTerm, String newTerm) {
        // Find the row with the oldTerm
        boolean found = false;
        for (int i = 0; i < promotedNames.size(); i++) {
            if (promotedNames.get(i).getText().trim().equals(oldTerm)) {
                click(editIcons.get(i));
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, "Promoted suggestion to edit not found: " + oldTerm);
        awaitForElementPresence(modalTitle);
        Assert.assertTrue(modalTitle.getText().contains("Edit"), "Edit modal should be open");
        // Add the new term (do not clear existing terms)
        awaitForElementPresence(termsContainer);
        click(termsContainer);
        awaitForElementPresence(visibleTermsInput);
        visibleTermsInput.fill().with(newTerm);
        visibleTermsInput.getElement().sendKeys(org.openqa.selenium.Keys.ENTER);
        // Wait and click the Update button
        org.openqa.selenium.By updateButtonSelector = org.openqa.selenium.By.xpath("//button[contains(@class,'RCB-btn-primary') and text()='Update']");
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        org.openqa.selenium.WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(updateButtonSelector));
        updateButton.click();
        // Wait for and verify the success toast
        awaitForElementPresence(successToast);
        Assert.assertTrue(successToast.isDisplayed(), "Success toast should be visible after edit");
    }
    /**
     * Deletes a promoted suggestion by term.
     */
    public void deletePromotedSuggestion(String term) {
        // Find the row with the term
        boolean found = false;
        for (int i = 0; i < promotedNames.size(); i++) {
            if (promotedNames.get(i).getText().trim().equals(term)) {
                click(deleteIcons.get(i));
                found = true;
                break;
            }
        }
        Assert.assertTrue(found, "Promoted suggestion to delete not found: " + term);
        // Wait for the delete confirmation modal and click Yes
        org.openqa.selenium.By yesButtonSelector = org.openqa.selenium.By.cssSelector(".modal-footer .RCB-btn-primary");
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        org.openqa.selenium.WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(yesButtonSelector));
        yesButton.click();
        // Wait for and verify the success toast
        Assert.assertTrue(successToast.isDisplayed(), "Success toast should be visible after delete");
    }
    /**
     * Verifies that a promoted suggestion with the given term exists in the listing and has Active status.
     * @param term The dynamic term to search for
     */
    public void verifySuggestionActiveStatus(String term) {
        java.util.List<org.openqa.selenium.WebElement> termElements = getDriver().findElements(org.openqa.selenium.By.cssSelector(".promoted-names"));
        boolean found = false;
        for (org.openqa.selenium.WebElement el : termElements) {
            if (el.getText().trim().equals(term)) {
                // Found the term, now check status in the same row
                org.openqa.selenium.WebElement row = el.findElement(org.openqa.selenium.By.xpath("./ancestor::div[contains(@class,'rdt_TableRow')]"));
                String status = row.findElement(org.openqa.selenium.By.cssSelector(".custom-pill .unbxd-pill")).getText().trim();
                org.testng.Assert.assertEquals(status, "Active", "Status should be Active for the new term");
                found = true;
                break;
            }
        }
        org.testng.Assert.assertTrue(found, "Added term not found in the listing: " + term);
    }

    public void verifyPromotedSuggestionTermsInRow(String... expectedTerms) {
        int retries = 3;
        while (retries-- > 0) {
            try {
                java.util.List<org.openqa.selenium.WebElement> rows = getDriver().findElements(org.openqa.selenium.By.cssSelector(".rdt_TableRow"));
                boolean found = false;
                for (org.openqa.selenium.WebElement row : rows) {
                    java.util.List<org.openqa.selenium.WebElement> pillsContainers = row.findElements(org.openqa.selenium.By.cssSelector(".terms-pills"));
                    java.util.Set<String> termsInRow = new java.util.HashSet<>();
                    for (org.openqa.selenium.WebElement pills : pillsContainers) {
                        java.util.List<org.openqa.selenium.WebElement> termSpans = pills.findElements(org.openqa.selenium.By.tagName("span"));
                        for (org.openqa.selenium.WebElement el : termSpans) {
                            termsInRow.add(el.getText().trim());
                        }
                    }
                    boolean allPresent = true;
                    for (String term : expectedTerms) {
                        if (!termsInRow.contains(term)) {
                            allPresent = false;
                            break;
                        }
                    }
                    if (allPresent) {
                        String status = row.findElement(org.openqa.selenium.By.cssSelector(".custom-pill .unbxd-pill")).getText().trim();
                        org.testng.Assert.assertEquals(status, "Active", "Status should be Active for the terms");
                        found = true;
                        break;
                    }
                }
                org.testng.Assert.assertTrue(found, "Row with all expected terms not found: " + java.util.Arrays.toString(expectedTerms));
                return; // Success, exit method
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                // Wait a bit and retry
                try { Thread.sleep(500); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            }
        }
        org.testng.Assert.fail("StaleElementReferenceException persisted after retries in verifyPromotedSuggestionTermsInRow");
    }
    /**
     * Searches for a term using the search input and waits for results to update.
     */
    public void searchForTerm(String term) {
        awaitForElementPresence(searchIcon);
        click(searchIcon);
        awaitForElementPresence(searchInput);
        searchInput.fill().with(""); // Clear any previous value
        searchInput.fill().with(term);
        searchInput.getElement().sendKeys(org.openqa.selenium.Keys.ENTER); // Use underlying WebElement for sendKeys
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
    /**
     * Checks if a term is present in the listing.
     */
    public boolean isTermPresentInListing(String term) {
        java.util.List<org.openqa.selenium.WebElement> termElements = getDriver().findElements(org.openqa.selenium.By.cssSelector(".promoted-names"));
        for (org.openqa.selenium.WebElement el : termElements) {
            if (el.getText().trim().equals(term)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Ensures the term is not present in the listing by searching and deleting if found.
     */
    public void ensureTermNotPresent(String term) {
        searchForTerm(term);
        if (isTermPresentInListing(term)) {
            deletePromotedSuggestion(term);
            // Optionally, wait for the table to update or refresh
            try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
    /**
     * Clears the search input by clicking the clear (X) icon.
     */
    public void clearSearch() {
        awaitForElementPresence(clearSearchIcon);
        clearSearchIcon.click();
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

public void addSuggestion(String term) {

    awaitForElementPresence(addPromotedSuggestionButton);
    click(addPromotedSuggestionButton);
    awaitForElementPresence(termsContainer);
    click(termsContainer);
    awaitForElementPresence(visibleTermsInput);
    visibleTermsInput.fill().with(term);
    addButton.click();
    awaitForElementPresence(successToast);
    Assert.assertTrue(successToast.isDisplayed(), "Success toast should be visible");
}

}






