package core.consoleui.actions;

import core.consoleui.page.productCardMappingPage;
import lib.compat.FluentWebElement;

public class productCardMappingAction extends productCardMappingPage {
   
   
    public void selectTitleValue(String parentfieldValue, String varientFieldValue) {
        awaitForPageToLoad();
        awaitForElementPresence(TitleLable);
        if(TitleLable.getText().contains("Product Title*")==true)
        {
            parentTitleDropdown.click();
            await();
            selectFieldValue(parentfieldValue);
            await();
            parentTitleDropdown.click();

            if (awaitForElementPresence(varaientTitleLable)) {
                varientTitleDropdown.click();
                selectFieldValue(varientFieldValue);
                varientTitleDropdown.click();
                await();
            }
        }
    }
    

       
    public void selectPriceValue(String parentfieldValue, String varientFieldValue) {
        if(priceLabel.getText().contains("Price of the Product*")==true)
        {
            click(parentPriceDropdown);
            selectFieldValue(parentfieldValue);
            await();

            if (awaitForElementPresence(varaientPriceLable)) {
                click(varientTitleDropdown);
                selectFieldValue(varientFieldValue);
                click(parentPriceDropdown);
                await();
            }
        }
    }

    public void selectFieldValue(String valueToSelect)
    {
        searchBoxInput.fill().with(valueToSelect);
        for (FluentWebElement option : valueOptions) {
            if (option.getText().equalsIgnoreCase(valueToSelect)) {
                option.click();
                await();
                return;
            }
        }
    }

    

    public void saveChanges() {
        saveButton.click();
        await();
    }

    public boolean checkSuccessMessage() {
        return awaitForElementPresence(successMessage);
    }

    public boolean verifyUpdatedValue(String fieldLabel, String expectedValue) {
        for (FluentWebElement row : fieldRows) {
            String label = row.findFirst(fieldNameSelector).getText();
            if (label.equalsIgnoreCase(fieldLabel)) {
                FluentWebElement dropdown = row.findFirst(variantFieldDropdownSelector);
                return dropdown.getText().equalsIgnoreCase(expectedValue);
            }
        }
        return false;
    }

    /**
     * Select first index from "Add additional fields for preview" dropdown and remove it
     */
    public void selectAndRemoveFirstAdditionalField() {
        awaitForPageToLoad();
        await();
        
        // Check if dropdown is present
        if (awaitForElementPresence(additionalFieldsDropdown)) {
            // Click on the dropdown to open it
            additionalFieldsDropdown.click();
            await();
            
            // Wait for options to be visible - check for checkbox inputs
            if (additionalFieldsOptions.size() > 0) {
                // Select the first option (index 0) by clicking the checkbox
                FluentWebElement firstOption = additionalFieldsOptions.get(0);
                if (firstOption != null) {
                    // Scroll to make it visible if needed
                    scrollUntilVisible(firstOption);
                    await();
                    // Click the checkbox to select it
                    firstOption.click();
                    await();
                    System.out.println("[INFO] Selected first additional field from dropdown");
                }
            }
            
            // Close the dropdown by clicking the dropdown button again
            additionalFieldsDropdown.click();
            await();
            
            // Wait for the selected field to appear in the summary
            if (selectedAdditionalFields.size() > 0) {
                // Remove the first selected field by clicking the remove button
                if (removeAdditionalFieldButtons.size() > 0) {
                    FluentWebElement removeButton = removeAdditionalFieldButtons.get(0);
                    if (removeButton != null && removeButton.isDisplayed()) {
                        scrollUntilVisible(removeButton);
                        await();
                        removeButton.click();
                        await();
                        System.out.println("[INFO] Removed first additional field");
                    }
                } else {
                    // Alternative: Try to find and click the close icon within the first selected field
                    FluentWebElement firstSelectedField = selectedAdditionalFields.get(0);
                    FluentWebElement closeIcon = firstSelectedField.findFirst(".RCB-clear-icon");
                    if (closeIcon != null && closeIcon.isDisplayed()) {
                        scrollUntilVisible(closeIcon);
                        await();
                        closeIcon.click();
                        await();
                        System.out.println("[INFO] Removed first additional field using close icon");
                    }
                }
            }
        } else {
            System.out.println("[WARN] Additional fields dropdown not found");
        }
    }

    /**
     * Remove first additional field if already present, otherwise select and remove it
     */
    public void removeFirstAdditionalField() {
        awaitForPageToLoad();
        await();
        
        if (!selectedAdditionalFields.isEmpty()) {
            // Already present, just remove it
            if (!removeAdditionalFieldButtons.isEmpty()) {
                removeAdditionalFieldButtons.get(0).click();
                await();
            } else {
                FluentWebElement closeIcon = selectedAdditionalFields.get(0).findFirst(".RCB-clear-icon");
                if (closeIcon != null) {
                    closeIcon.click();
                    await();
                }
            }
        } else {
            // Not present, select and then remove
            selectAndRemoveFirstAdditionalField();
        }
    }

    /**
     * Select and remove the 3rd additional field (index 3)
     * If already present at index 3, only remove it; otherwise select and remove
     */
    public void selectAndRemoveThirdAdditionalField() {
        awaitForPageToLoad();
        await();
        int targetIndex = 3;
        
        // Check if field at index 3 is already present
        if (selectedAdditionalFields.size() > targetIndex) {
            // Already present at index 3, just remove it
            if (removeAdditionalFieldButtons.size() > targetIndex) {
                removeAdditionalFieldButtons.get(targetIndex).click();
                await();
            } else {
                FluentWebElement closeIcon = selectedAdditionalFields.get(targetIndex).findFirst(".RCB-clear-icon");
                if (closeIcon != null) {
                    closeIcon.click();
                    await();
                }
            }
        } else {
            // Not present, need to select it first
            if (awaitForElementPresence(additionalFieldsDropdown)) {
                // Click on the dropdown to open it
                additionalFieldsDropdown.click();
                await();
                
                // Wait for options to be visible - check for checkbox inputs
                if (additionalFieldsOptions.size() > targetIndex) {
                    // Select the option at index 3 by clicking the checkbox
                    FluentWebElement thirdOption = additionalFieldsOptions.get(targetIndex);
                    if (thirdOption != null) {
                        // Scroll to make it visible if needed
                        scrollUntilVisible(thirdOption);
                        await();
                        // Click the checkbox to select it
                        thirdOption.click();
                        await();
                        System.out.println("[INFO] Selected additional field at index " + targetIndex + " from dropdown");
                    }
                }
                
                // Close the dropdown by clicking the dropdown button again
                additionalFieldsDropdown.click();
                await();
                
                // Wait for the selected field to appear in the summary and remove it
                if (selectedAdditionalFields.size() > targetIndex) {
                    // Remove the field at index 3 by clicking the remove button
                    if (removeAdditionalFieldButtons.size() > targetIndex) {
                        FluentWebElement removeButton = removeAdditionalFieldButtons.get(targetIndex);
                        if (removeButton != null && removeButton.isDisplayed()) {
                            scrollUntilVisible(removeButton);
                            await();
                            removeButton.click();
                            await();
                            System.out.println("[INFO] Removed additional field at index " + targetIndex);
                        }
                    } else {
                        // Alternative: Try to find and click the close icon within the selected field
                        FluentWebElement selectedField = selectedAdditionalFields.get(targetIndex);
                        FluentWebElement closeIcon = selectedField.findFirst(".RCB-clear-icon");
                        if (closeIcon != null && closeIcon.isDisplayed()) {
                            scrollUntilVisible(closeIcon);
                            await();
                            closeIcon.click();
                            await();
                            System.out.println("[INFO] Removed additional field at index " + targetIndex + " using close icon");
                        }
                    }
                }
            } else {
                System.out.println("[WARN] Additional fields dropdown not found");
            }
        }
    }
} 