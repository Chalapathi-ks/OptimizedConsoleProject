package core.consoleui.actions;

import core.consoleui.page.DimensionMappingPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class DimensionMappingAction extends DimensionMappingPage {
    public void waitForPageLoad() {
        awaitForElementPresence(saveButton);
    }

    public void selectValueForField(String fieldLabel, String valueToSelect) {
        for (FluentWebElement row : fieldRows) {
            ThreadWait();
            String label = row.findFirst(fieldNameSelector).getText();
            if (label.equalsIgnoreCase(fieldLabel)) {
                ThreadWait();
                FluentWebElement dropdown = row.findFirst(valueDropdownSelector);
                dropdown.click();
                searchBoxInput.fill().with(valueToSelect);
                ThreadWait();
                for (FluentWebElement option : valueOptions) {
                    ThreadWait();
                    if (option.getText().equalsIgnoreCase(valueToSelect)) {
                        option.click();
                        ThreadWait();
                        return;
                    }
                }
            }
        }
    }


    public String getSelectedValueForField(String fieldLabel) {
        for (FluentWebElement row : fieldRows) {
            ThreadWait();
            String label = row.findFirst(fieldNameSelector).getText();
            ThreadWait();
            if (label.equalsIgnoreCase(fieldLabel)) {
                FluentWebElement dropdown = row.findFirst(valueDropdownValue);
                return dropdown.getText();
            }
        }
        return null;
    }

    public void saveChanges() {
        saveButton.click();
        await();
    }

    public boolean checkSuccessMessage() {
        return awaitForElementPresence(successMessage);
    }
} 