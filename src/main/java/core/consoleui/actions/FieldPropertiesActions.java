package core.consoleui.actions;
import core.consoleui.page.FieldPropertiesPage;
import core.consoleui.page.searchableFieldsAndFacetsPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import org.testng.Assert;

public class FieldPropertiesActions extends FieldPropertiesPage {

 

    @Page
    FieldPropertiesPage fieldPropertiesPage;

    /**
     * Selects a field by searching for it and selecting its checkbox
     * @param fieldName Name of the field to select
     */
    public void selectField(String fieldName) {
        // Click search icon
        awaitForElementPresence(searchIcon);
        ThreadWait();
        searchIcon.click();

        // Enter search text
        awaitForElementPresence(searchInput);
        ThreadWait();
        searchInput.clear();
        searchInput.fill().with(fieldName);
        searchInput.getElement().sendKeys(Keys.ENTER);
        ThreadWait();

        // Select the checkbox
        awaitForElementPresence(fieldCheckbox);
        ThreadWait();
        fieldCheckbox.click();
    }

    /**
     * Clicks on the Action dropdown button
     */
    public void clickActionDropdown() {
        awaitForElementPresence(actionButton);
        ThreadWait();
        actionButton.click();
    }

    /**
     * Selects the Bulk Enable Features option from the dropdown
     */
    public void selectBulkEnableFeatures() {
        awaitForElementPresence(bulkEnableFeaturesOption);
        ThreadWait();
        // Check if the bulk enable option is enabled
        FluentWebElement bulkEnableOption = $("li.menu-item a").first();
        if (bulkEnableOption != null && !bulkEnableOption.getElement().getAttribute("class").contains("disabled")) {
            bulkEnableOption.click();
        } else {
            throw new IllegalStateException("Bulk Enable Features option is either not found or disabled");
        }
    }

    /**
     * Selects a feature checkbox in the bulk enable modal
     * @param featureName Name of the feature to select
     */
    public void selectFeature(String featureName) {
        ThreadWait();
        switch (featureName) {
            case "Merchandisable":
                if (merchandisableCheckbox.isDisplayed()) {
                    awaitForElementPresence(merchandisableCheckbox);
                    ThreadWait();
                    merchandisableCheckbox.click();
                    ThreadWait();
                }
                break;
            case "Facetable":
                if (facetableCheckbox != null && facetableCheckbox.isDisplayed()) {
                    awaitForElementPresence(facetableCheckbox);
                    ThreadWait();
                    facetableCheckbox.click();
                    ThreadWait();
                }
                break;
            case "AutoSuggest":
                if (autoSuggestCheckbox != null && autoSuggestCheckbox.isDisplayed()) {
                    awaitForElementPresence(autoSuggestCheckbox);
                    ThreadWait();
                    autoSuggestCheckbox.click();
                    ThreadWait();
                }
                break;
            case "Field Rule":
                if (fieldRuleCheckbox != null && fieldRuleCheckbox.isDisplayed()) {
                    awaitForElementPresence(fieldRuleCheckbox);
                    ThreadWait();
                    fieldRuleCheckbox.click();
                    ThreadWait();
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown feature: " + featureName);
        }
    }

    /**
     * Clicks the bulk enable button to apply the selected features
     */
    public void clickBulkEnableButton() {
        awaitForElementPresence(bulkEnableButton);
        ThreadWait();
        bulkEnableButton.click();
    }

    public void selectBulkDisableFeatures() {
        // Adjust the locator if needed for the disable option
        FluentWebElement bulkDisableOption = $("li.menu-item a").stream()
            .filter(el -> {
                String text = el.getElement().getText();
                return text != null && text.toLowerCase().equals("bulk disable features");
            })
            .findFirst()
            .orElse(null);

        if (bulkDisableOption != null && (bulkDisableOption.getAttribute("class") == null || !bulkDisableOption.getAttribute("class").contains("disabled"))) {
            awaitForElementPresence(bulkDisableOption);
            bulkDisableOption.click();
        } else {
            throw new IllegalStateException("Bulk Disable Features option is either not found or disabled");
        }
    }

    public void clickBulkDisableButton() {
        // Adjust the locator if needed for the disable button
        FluentWebElement bulkDisableButton = $("button.RCB-btn-primary").stream()
            .filter(el -> {
                String text = el.getElement().getText();
                return text != null && text.toLowerCase().equals("bulk disable");
            })
            .findFirst()
            .orElse(null);

        if (bulkDisableButton != null && bulkDisableButton.isDisplayed()) {
            awaitForElementPresence(bulkDisableButton);
            bulkDisableButton.click();
        } else {
            throw new IllegalStateException("Bulk Disable button is either not found or not visible");
        }
    }


    public void clickEditIcon() {
        awaitForElementPresence(editIcon);
        ThreadWait();
        editIcon.click();
    }


    public void selectFeatureTile(String featureName) {
        ThreadWait();
        featureTiles.stream()
            .filter(tile -> {
                String text = tile.getElement().getText();
                return text != null && text.toLowerCase().equals(featureName.toLowerCase());
            })
            .findFirst()
            .ifPresent(tile -> {
                awaitForElementPresence(tile);
                tile.click();
            });
    }


    public void clickApplyButton() {
        awaitForElementPresence(applyButton);
        ThreadWait();
        applyButton.click();
        ThreadWait();
    }


    public void isFeatureEnabled(String featureName) {
        ThreadWait();
        String statusText = featureStatus.getText().toLowerCase();
        Assert.assertTrue(statusText.contains(featureName.toLowerCase()) ,
                "Feature '" + featureName + "' should be enabled but it's not.");
    }

    public void isFeatureDisabled(String featureName) {
        ThreadWait();
        String statusText = featureStatus.getText().toLowerCase();
        Assert.assertFalse(statusText.contains(featureName.toLowerCase()) ,
                "Feature '" + featureName + "' should be disabled but it's not.");
    }



}