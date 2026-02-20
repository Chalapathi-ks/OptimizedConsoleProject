package core.consoleui.actions;

import core.consoleui.page.VectorSearchPage;
import lib.compat.FluentWebElement;
import org.testng.Assert;

public class VectorSearchAction extends VectorSearchPage {
    public void enableVectorSearchIfDisabled() throws InterruptedException {
        Thread.sleep(2000);
        if (awaitForElementPresence(vectorToggleDisabled)==true) {
            click(vectorToggleDisabled);
            Thread.sleep(1000);
        }
    }

    public void selectHybridSearchAndIncrement() throws InterruptedException {
        if (!hybridSearchCheckbox.isSelected()) {
            hybridSearchCheckbox.click();
        }
        Thread.sleep(500);
        String value = hybridSearchInput.getValue();
        if (value == null) value = hybridSearchInput.getText();
        int num = Integer.parseInt(value.trim());
        num++;
        hybridSearchInput.fill().with(String.valueOf(num));
    }

    public void selectFallbackModeAndIncrement() throws InterruptedException {
        if (!fallbackModeCheckbox.isSelected()) {
            fallbackModeCheckbox.click();
        }
        Thread.sleep(500);
        String value = fallbackModeInput.getValue();
        if (value == null) value = fallbackModeInput.getText();
        int num = Integer.parseInt(value.trim());
        num++;
        fallbackModeInput.fill().with(String.valueOf(num));
    }

    public void saveChanges() {
        saveButton.click();
        await();
    }

    public boolean verifyUpdatedValues(int expectedHybrid, int expectedFallback) {
        String hybridVal = hybridSearchInput.getValue();
        if (hybridVal == null) hybridVal = hybridSearchInput.getText();
        String fallbackVal = fallbackModeInput.getValue();
        if (fallbackVal == null) fallbackVal = fallbackModeInput.getText();
        int hybrid = Integer.parseInt(hybridVal.trim());
        int fallback = Integer.parseInt(fallbackVal.trim());
        return hybrid == expectedHybrid && fallback == expectedFallback;
    }
} 