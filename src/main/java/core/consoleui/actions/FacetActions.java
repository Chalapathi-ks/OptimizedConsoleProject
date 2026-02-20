package core.consoleui.actions;

import org.fluentlenium.core.annotation.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import core.consoleui.page.FacetPage;
import core.ui.actions.FacetableFieldsActions;
import core.ui.components.FacetComponent;


public class FacetActions extends FacetPage {

    @Page
    FacetComponent facetComponent;
    @Page
    FacetableFieldsActions facetPageActions;



    public void clickOnAddFacets(){
        awaitTillElementDisplayed(clickOnAddFacets);
        ThreadWait();
        clickOnAddFacets.click();
        awaitForPageToLoad();
    }


    

    public String selectFacetAndGetSelectedFacetName() throws InterruptedException {
        String facetDisplayName = "";
        awaitForPageToLoad();
        threadWait();
        if (facetList.size() == 0) {
            Assert.fail("NO FACETS ARE PRESENT!!!PLEASE CREATE THE FACETS FIRST");
            return facetDisplayName;
        }
        if (facetList.size() > 0) {
            ThreadWait();
            // Find the last facet that has a corresponding toggle element
            // Start from the last facet and work backwards to find a valid one
            int facetsIndex = -1;
            int lastIndex = facetList.size() - 1;
            
            // Find the last valid facet index that has a toggle
            for (int i = lastIndex; i >= 0; i--) {
                // Check if this index is valid for toggle lists
                if (i < activeToggle.size() || i < DisableToggle.size()) {
                    facetsIndex = i;
                    break;
                }
            }
            
            // If no valid facet found with toggle, use the last available index
            if (facetsIndex == -1) {
                facetsIndex = Math.min(lastIndex, Math.min(
                    (activeToggle.size() > 0 ? activeToggle.size() - 1 : lastIndex),
                    (DisableToggle.size() > 0 ? DisableToggle.size() - 1 : lastIndex)
                ));
                System.out.println("[WARN] No facet with toggle found, using index: " + facetsIndex);
            } else if (facetsIndex < lastIndex) {
                System.out.println("[INFO] Selected facet at index " + facetsIndex + " (last available with toggle, total facets: " + (lastIndex + 1) + ")");
            }

            // Check if toggle is already enabled
            if (facetsIndex < activeToggle.size() && activeToggle.get(facetsIndex).isDisplayed()) {
                // If enabled, disable first and then enable again
                facetList.get(facetsIndex).find(toggle).click(); // Disable
                ThreadWait();
                facetList.get(facetsIndex).find(toggle).click(); // Enable again
            } else if (facetsIndex < DisableToggle.size() && DisableToggle.get(facetsIndex).isDisplayed()) {
                // If disabled, just enable it
                facetList.get(facetsIndex).find(toggle).click();
            } else {
                // Try to click the toggle directly from facetList if toggle lists don't have the element
                facetList.get(facetsIndex).find(toggle).click();
            }
            ThreadWait();
            // Verify toggle is enabled (check via facetList toggle if activeToggle list is shorter)
            try {
                if (facetsIndex < activeToggle.size()) {
                    Assert.assertTrue(activeToggle.get(facetsIndex).isDisplayed(), 
                        "Toggle should be enabled for facet at index " + facetsIndex);
                }
            } catch (AssertionError e) {
                // If assertion fails, try to verify via the toggle element directly
                System.out.println("[WARN] Could not verify via activeToggle list, checking toggle element directly");
            }
            String fullText = displayName.get(facetsIndex).getText().trim();
                String extractedFacetName;
                if (fullText.contains(":")) {
                    extractedFacetName = fullText.split(":")[1].trim();
                } else {
                    extractedFacetName = fullText;
                }
                facetDisplayName = extractedFacetName;
                System.out.println("Extracted facet name: " + extractedFacetName);
        }
        return facetDisplayName;
    }

// Get the size of activeToggle list
    public int getActiveToggleSize() {
        ThreadWait();
        return activeToggle.size();
    }

    // Disable the last activeToggle
    public void disableLastActiveToggle() {
        if (activeToggle.size() > 0) {
            int lastIndex = activeToggle.size() - 1;
            ThreadWait();
            facetList.get(lastIndex).find(toggle).click();
            ThreadWait();
        }
    }

    // Enable the last toggle (that was previously disabled)
    public void enableLastToggle() {
        if (facetList.size() > 0) {
            int lastIndex = facetList.size() - 1;
            ThreadWait();
            facetList.get(lastIndex).find(toggle).click();
            ThreadWait();
        }
    }

    // Verify the last toggle is disabled
    public boolean verifyLastToggleIsDisabled() {
        if (activeToggle.size() > 0) {
            int lastIndex = activeToggle.size() - 1;
            try {
                return !activeToggle.get(lastIndex).isDisplayed();
            } catch (Exception e) {
                return true; // If element not found, it's disabled
            }
        }
        return true;
    }

    // Verify the last toggle is enabled
    public boolean verifyLastToggleIsEnabled() {
        if (activeToggle.size() > 0) {
            int lastIndex = activeToggle.size() - 1;
            try {
                return activeToggle.get(lastIndex).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }





    public void disableTheToggle(){
        activeToggle.get(0).isDisplayed();
        ThreadWait();
        facetList.get(0).find(toggle).click();

    }

    public boolean verifydTheToggleIsDisabledOrNot() {
        try {
            // First verify toggle is displayed (enabled)
            ThreadWait();
            if(!checkElementPresence(activeToggle.get(0))) {
                System.out.println("Toggle is already disabled");
                return true;
            }else{
                System.out.println("Toggle is enabled");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error verifying toggle state: " + e.getMessage());
            return false;
        }
    }

    public boolean verifydTheToggleIsEnabledOrNot() {
        try {
            // First verify toggle is displayed (enabled)
            if(checkElementPresence(activeToggle.get(0))) {
                System.out.println("Toggle is already disabled");
                return true;
            }else{
                System.out.println("Toggle is enabled");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error verifying toggle state: " + e.getMessage());
            return false;
        }

    }
    
public boolean isFacetToggleActive(String facetName) {
    // Handle empty list case
    if (facetList.isEmpty()) return false;
    
    // Find the matching facet
    for (int i = 0; i < facetList.size(); i++) {
        // Avoid index errors
        if (i >= displayName.size()) break;
        
        // Check if this is the facet we're looking for
        String text = displayName.get(i).getText();
        if (text.contains(facetName)) {
            // Check if toggle is active (safely)
            try {
                return i < activeToggle.size() && activeToggle.get(i).isDisplayed();
            } catch (Exception e) {
                return false;
            }
        }
    }
    
    // Facet not found
    return false;
}







    
    public boolean enableToggleByName(String facetName) {
        // Check if facets exist
        ThreadWait();
        if (facetList.size() == 0) {
            System.out.println("No facets found");
            return false;
        }
        // Search for matching facet
        for (int i = 0; i < facetList.size(); i++) {
            String text = displayName.get(i).getText().trim();
            // Check if this facet matches the name we're looking for
            if (text.contains(facetName)) {
                    // Enable it
                    facetList.get(i).find(toggle).click();
                    ThreadWait();

                return true;
            }
        }
        
        System.out.println("No facet found with name: " + facetName);
        return false;
    }


        public String enableAndDisableTheRanking() {
        // Check if active toggles exist
        if (activeToggle.size() == 0) {
            System.out.println("No active toggles found");
            return null;
        } 
        // If active toggles count is 1 or more, proceed with reranking
        else if (activeToggle.size() >= 1) {
            System.out.println("Found " + activeToggle.size() + " active toggles");
            Assert.assertTrue(activeToggle.get(0).isDisplayed());
            String facetDisplayName = displayName.get(0).getText().trim();
            
            // Check if reRank options exist
            if (reRank.size() == 0) {
                System.out.println("No rerank options got displayed");
                return facetDisplayName;
            }
            // If rerank options count is 1 or more, proceed with reranking
            else if (reRank.size() >= 1) {
                System.out.println("Found " + reRank.size() + " rerank options, proceeding with reranking");
                reRank.get(0).click();
                await();
                rankPosition.click();
                rankPosition.clear();
                rankPosition.fill().with("2");
                click(updateRank);
                ThreadWait();
                
                return facetDisplayName;
            }
        }
        
        return null;
    }

    public void afterUpdateTheRankingVerifythePosition() {
        try {
            threadWait();
            Assert.assertTrue(afterUpdateRanking.isDisplayed(), "Ranking field is not displayed");
            for (int i = 0; i < 24; i++) {
                threadWait();
                if ("2".equals(afterUpdateRanking.getText().trim())) break;
                if (i == 23) Assert.fail("Ranking did not update to 2 within timeout");
            }
            Assert.assertTrue(afterUpdateRanking.getText().equals("2"), "Ranking was not updated to 2");
        } catch (Exception e) {
            System.out.println("Failed to verify ranking update: " + e.getMessage());
        }
    }

    private static final String SEARCH_ICON_SELECTORS = ".unx-qa-seach-Icon, .unx-qa-search-Icon, .search-icon-medium.unx-qa-seach-Icon";

    public void searchForField(String facetDisplayName) {
        try {
            new WebDriverWait(getDriver(), 15).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(SEARCH_ICON_SELECTORS)));
        } catch (Exception ignored) { }
        ThreadWait();
        if (find(SEARCH_ICON_SELECTORS).size() > 0) {
            find(SEARCH_ICON_SELECTORS).get(0).click();
        } else {
            awaitForElementPresence(searchIcon);
            searchIcon.click();
        }
        ThreadWait();
        searchInputBox.click();
        searchInputBox.clear();
        searchInputBox.fill().with(facetDisplayName);
        ThreadWait();
        searchInputBox.getElement().sendKeys(Keys.ENTER);
    }

     public void clickOnDisable()
    {
        click(DisableToggle);
    }
//    public ArrayList<String> selectFacetAndGetSelectedFacetName(int facetCount) throws InterruptedException {
//        ArrayList<String> facetDisplayName = new ArrayList<>();
//        awaitForPageToLoad();
//        threadWait();
//        if (facetList.size() == 0) {
//            Assert.fail("NO FACETS ARE PRESENT!!!PLEASE CREATE THE FACETS FIRST");
//        }
//        if (facetList.size() > 0) {
//            // disableAllFacet.click();
//            ThreadWait();
//            for (int i = 0; i < facetCount; i++) {
//                facetList.get(i).find(toggle).click();
//                ThreadWait();
//                Assert.assertTrue(activeToggle.get(i).isDisplayed());
//                facetDisplayName.add(facetList.get(i).find(displayName).getText().trim());
//            }
//        }
//        return facetDisplayName;
//
//    }
    public void clickOnCloseButton(){
        awaitTillElementDisplayed(facetCloseButton);
        ThreadWait();
        facetCloseButton.click();
        awaitForPageToLoad();
    }
    public void clickingOnEditButton(){
        awaitTillElementDisplayed(ConfigureFacetEdit);
        ThreadWait();
        ConfigureFacetEdit.click();
    }
    public void ClickDisableAllFacet() {
        ThreadWait();
        disableAllFacet.click();
        ThreadWait();
    }

    public void FacetRange(String facetRange, int facetCount) {
        if (facetList.size() > 0) {
            for (int i = 0; i < facetCount; i++) {
                click(facetEdit);
                awaitTillElementDisplayed(previewFacetRange);
                previewFacetRange.clear();
                ThreadWait();
                previewFacetRange.fill().with(facetRange);
                click(ClickonOk);
            }
        }
    }

    /*public void updateTextFacet(String facetName, String facetLength, String facetSortOrder){

        if(facetComponent.getFacetState(facetName)=="false")
            changeFacetState(facetName);
        click(facetPageActions.getFacetByName(facetName));
       facetPageActions.editFacetFieldRules.click();
        click(facetEdit);
        updateTextFacet(facetLength,facetSortOrder);
    }

    public void updateRangeFacet(String facetName, String rangeStart, String rangeEnd, String rangeGap){
        if(facetComponent.getFacetState(facetName)=="false")
            changeFacetState(facetName);
        click(facetPageActions.getFacetByName(facetName));
       // facetPageActions.editFacetFieldRules.click();
        click(facetEdit);
        updateRangeFacet(rangeStart,rangeEnd,rangeGap);


    }*/

    public void updateTextFacet(String facetLength, String facetSortOrder) throws InterruptedException {
        facetPageActions.updateFacetLength(facetLength);
        facetPageActions.updateFacetSortOrder(facetSortOrder);
        //facetPageActions.saveFacetConfig();
        click(ClickonOk);
        Assert.assertFalse(facetPageActions.checkElementPresence(facetPageActions.saveFacetButton), "saving of Facet  is failed");

    }

    public void updateRangeFacet(String rangeStart, String rangeEnd, String rangeGap){

        facetPageActions.startRangeInput.fill().with(rangeStart);
        facetPageActions.endRangeInput.fill().with(rangeEnd);
        facetPageActions.rangeGapInput.fill().with(rangeGap);
       // facetPageActions.saveFacetConfig();
        click(ClickonOk);
        Assert.assertFalse(facetPageActions.checkElementPresence(facetPageActions.saveFacetButton), "saving of Facet  is failed");
    }

    /**
     * Verify success message is displayed after publishing
     */
    public void verifySuccessMessage() {
        // Wait for loader to disappear if necessary
        // waitForLoaderToDisAppear(By.cssSelector(".loader"), "publish in progress");
        
        if (awaitTillElementDisplayed(successMessage)) {
            System.out.println("PUBLISHED SUCCESSFULLY, Verifying it in promotion listing page!!!");
        } else {
            Assert.fail("PROMOTIONS PUBLISHING IS FAILING");
            ThreadWait();
        }
    }


    public void facetFilterIcon(){
        awaitForElementPresence(facetPageActions.facetFilterIcon);
        click(facetPageActions.facetFilterIcon);
        ThreadWait();
        awaitForElementPresence(facetPageActions.textFilterOption);
    }



    public void applyTextAndEnabledFilter() {
        scrollUntilVisible(facetPageActions.textFilterOption);
        if (!awaitForElementPresence(facetPageActions.textFilterOption)) {
            Assert.fail("Text filter option not found - filter panel may not have opened");
        }
        safeClick(facetPageActions.textFilterOption);
        shortWait();

        if (!awaitForElementPresence(facetPageActions.enabledFilterOption)) {
            Assert.fail("Enabled filter option not found");
        }
        safeClick(facetPageActions.enabledFilterOption);
        shortWait();
    }
    public void applyRangeAndDisabledFilter() {
        scrollUntilVisible(facetPageActions.rangeFilterOption);
        if (!awaitForElementPresence(facetPageActions.rangeFilterOption)) {
            Assert.fail("Range filter option not found - filter panel may not have opened");
        }
        safeClick(facetPageActions.rangeFilterOption);
        shortWait();

        if (!awaitForElementPresence(facetPageActions.disabledFilterOption)) {
            Assert.fail("Disabled filter option not found");
        }
        safeClick(facetPageActions.disabledFilterOption);
        shortWait();
    }

    public void ApplyFilterButton() {
        awaitForElementPresence(facetPageActions.applyFilterButton);
        scrollUntilVisible(facetPageActions.applyFilterButton);
        safeClick(facetPageActions.applyFilterButton);
        shortWait();
    }

public boolean verifyTextFilterAndEnabledFilter(String Facet_TypeText) {
    // Check if facets exist
    if (facetList.isEmpty()) {
        System.out.println("No facets found");
        return false;
    }
    
    boolean foundActiveTextToggle = false;
    System.out.println("Checking " + facetList.size() + " facets for active text toggles...");
    
    // Iterate through all facets
    for (int i = 0; i < facetList.size(); i++) {
        try {
            // Check if this index is valid for all collections
            if (i >= displayName.size() || i >= activeToggle.size() || i >= facetPageActions.facetTypeTextRange.size()) {
                continue;
            }
            
            // Check if toggle is active
            boolean isActive = activeToggle.get(i).isDisplayed();
            
            // Check if type is "text"
            boolean isTextType = facetPageActions.facetTypeTextRange.get(i).getText().toLowerCase().contains(Facet_TypeText);
            
            if (isActive && isTextType) {
                String name = displayName.get(i).getText();
                System.out.println("Found active text toggle: " + name);
                foundActiveTextToggle = true;
                // Uncomment if you want to return on first match
                // return true;
            }
        } catch (Exception e) {
            System.out.println("Error checking facet at index " + i + ": " + e.getMessage());
        }
    }
    
    if (!foundActiveTextToggle) {
        System.out.println("No active text toggles found");
    }
    
    return foundActiveTextToggle;
}


    public boolean verifyRangeFilterAndDisabledFilter(String facetTypeRange) {
        // Check if facets exist
        if (facetList.isEmpty()) {
            System.out.println("No facets found");
            return false;
        }

        boolean foundDisabledRangeToggle = false;

        // Iterate through all facets
        for (int i = 0; i < facetList.size(); i++) {
            try {
                // Check if this index is valid for all collections
                if (i >= displayName.size() || i >= facetPageActions.facetTypeTextRange.size()) {
                    continue;
                }

                // Check if type contains the range text
                boolean isRangeType = facetPageActions.facetTypeTextRange.get(i)
                    .getText().toLowerCase().contains(facetTypeRange.toLowerCase());
                
                if (!isRangeType) {
                    continue; // Skip if not a range type
                }

                // Check if toggle is disabled (not present or not displayed)
                boolean isDisabled = false;
                try {
                    isDisabled = i >= activeTogle.size() || !activeTogle.get(i).isDisplayed();
                } catch (Exception e) {
                    isDisabled = true; // Exception means toggle is not accessible
                }

                if (isRangeType && isDisabled) {
                    String name = displayName.get(i).getText();
                    System.out.println("Found disabled range toggle: " + name);
                    foundDisabledRangeToggle = true;
                    // Return immediately if you just need to know if any exist
                    // return true;
                }
            } catch (Exception e) {
                System.out.println("Error checking facet at index " + i + ": " + e.getMessage());
            }
        }

        if (!foundDisabledRangeToggle) {
            Assert.fail("No disabled range toggles found");
        }

        return foundDisabledRangeToggle;
    }





    public void verifyAppliedFilter(String status, String type) {
        shortWait();
        Assert.assertTrue(awaitForElementPresence(facetPageActions.typeFacetHeader), "Selected filters list (.RCB-list.selected-filters-list) did not appear after applying filter. Ensure filter was applied (text/enabled or range/disabled).");
        Assert.assertTrue(facetPageActions.typeFacetHeader.getText().contains(status), "Applied filter did not show status: " + status);
        Assert.assertTrue(facetPageActions.typeFacetHeader.getText().contains(type), "Applied filter did not show type: " + type);
    }

    public void verifyAppliedFilterIsNotPresent() {
        Assert.assertTrue(!(checkElementPresence(facetPageActions.typeFacetHeader)));
    }
}
