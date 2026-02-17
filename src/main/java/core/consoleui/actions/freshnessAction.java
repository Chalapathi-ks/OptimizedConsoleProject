package core.consoleui.actions;

import core.consoleui.page.freshnessPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static core.ui.page.UiBase.ThreadWait;

public class freshnessAction extends freshnessPage {

    @Page
    VariantLockAction variantLockAction;

    public void selectAttribute(String value) throws InterruptedException {
        ThreadWait();
        AttributeDropDown.click();
        Thread.sleep(3000);
        if (attributeDropDownList.size() > 0) {
            attributeInput.clear();
            ThreadWait();
            attributeInput.fill().with(value);
            Thread.sleep(3000);
            selectDropDownValue(attributeDropDownList, value);
            Thread.sleep(3000);
        } else {
            Assert.fail("ATTRIBUTE DROPDOWN LIST IS EMPTY!!!");
        }
    }

    public void selectAttributeValue(String value) throws InterruptedException {

        InputBox.clear();
        InputBox.fill().with(value);
        ThreadWait();

    }

    /**
     * Get the freshness summary value from the promotion rules summary
     * @return The value (e.g., "10") from summary-primary-tag-value
     */
    public String getFreshnessSummaryValue() {
        ThreadWait();
        try {
            // First scroll to bottom to ensure all content is loaded
            scrollToBottom();
            ThreadWait();
            
            // Wait for promotion-rules-summary to be present
            FluentWebElement promotionSummary = findFirst(".promotion-rules-summary");
            if (promotionSummary == null) {
                System.out.println("promotion-rules-summary element not found");
                return "";
            }
            
            awaitTillElementDisplayed(promotionSummary);
            ThreadWait();
            
            // Scroll to promotion-rules-summary element
            scrollUntilVisible(promotionSummary);
            ThreadWait();
            threadWait();
            
            // Find all action-rules-list elements
            FluentList<FluentWebElement> actionRulesLists = find(".promotion-rules-summary .action-rules-list");
            System.out.println("Found " + actionRulesLists.size() + " action-rules-list elements");
            
            for (FluentWebElement actionRulesList : actionRulesLists) {
                try {
                    // Scroll to this element first
                    scrollUntilVisible(actionRulesList);
                    ThreadWait();
                    threadWait();
                    
                    // Check if it contains "freshness" text
                    FluentWebElement actionText = actionRulesList.findFirst(".action-text");
                    if (actionText != null) {
                        String actionTextValue = actionText.getText().trim().toLowerCase();
                        System.out.println("Checking action-text: " + actionTextValue);
                        if (actionTextValue.contains("freshness")) {
                            // Find the summary-primary-tag-value within this action-rules-list
                            FluentWebElement summaryValue = actionRulesList.findFirst(".summary-primary-tag-value");
                            if (summaryValue != null) {
                                // Scroll to the summary value element
                                scrollUntilVisible(summaryValue);
                                ThreadWait();
                                threadWait();
                                
                                // Wait a bit for scroll to complete
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                                
                                if (summaryValue.isDisplayed()) {
                                    String valueText = summaryValue.getText().trim();
                                    System.out.println("Found freshness summary value text: " + valueText);
                                    // Extract just the number (e.g., "10" from "10 days")
                                    String numberOnly = valueText.replaceAll("[^0-9]", "");
                                    return numberOnly;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error processing action-rules-list: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting freshness summary value: " + e.getMessage());
        }
        return "";
    }

    /**
     * Verify the freshness summary value matches the expected value
     * @param expectedValue The expected value (e.g., "10")
     */
    public void verifyFreshnessSummaryValue(String expectedValue) {
        ThreadWait();
        String actualValue = getFreshnessSummaryValue();
        System.out.println("Expected freshness summary value: " + expectedValue);
        System.out.println("Actual freshness summary value: " + actualValue);
        Assert.assertFalse(actualValue.isEmpty(), "Freshness summary value not found");
        Assert.assertEquals(actualValue, expectedValue, 
            "Freshness summary value mismatch. Expected: " + expectedValue + ", Actual: " + actualValue);
        System.out.println("Freshness summary value verification passed: " + actualValue);
    }

    /**
     * Wait for products to load and verify date_iso for first 5 products
     * Verifies that each product's date_iso is within the specified number of days from current date
     * @param daysThreshold Number of days threshold (e.g., 10)
     */
    public void verifyDateIsoForFirstFiveProducts(int daysThreshold) {
        ThreadWait();
        // Wait for products to be visible
        variantLockAction.waitForProductsToLoad();
        int productCount = variantLockAction.getProductCount();
        System.out.println("Total products found: " + productCount);
        
        if (productCount > 0) {
            // Get first product
            FluentWebElement firstProduct = variantLockAction.getFirstProduct();
            Assert.assertNotNull(firstProduct, "First product not found");
            
            // Get date_iso for first 5 products (or up to available products)
            int productsToCheck = Math.min(5, productCount);
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter[] formatters = {
                DateTimeFormatter.ISO_LOCAL_DATE,
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd")
            };
            
            System.out.println("Checking date_iso for first " + productsToCheck + " products...");
            for (int i = 0; i < productsToCheck; i++) {
                FluentWebElement product = variantLockAction.getProductByIndex(i);
                String dateIso = variantLockAction.getDateIsoFromPreview(product);
                
                if (!dateIso.isEmpty()) {
                    System.out.println("Product " + (i + 1) + " date_iso: " + dateIso);
                    
                    // Try to parse the date with different formatters
                    LocalDate productDate = null;
                    for (DateTimeFormatter formatter : formatters) {
                        try {
                            productDate = LocalDate.parse(dateIso, formatter);
                            break;
                        } catch (Exception e) {
                            // Try next formatter
                        }
                    }
                    
                    if (productDate != null) {
                        // Calculate days difference
                        long daysDifference = ChronoUnit.DAYS.between(productDate, currentDate);
                        System.out.println("Product " + (i + 1) + " date difference: " + daysDifference + " days");
                        
                        // Verify date is within specified days threshold of current date
                        Assert.assertTrue(Math.abs(daysDifference) <= daysThreshold, 
                            "Product " + (i + 1) + " date_iso (" + dateIso + ") is not within " + daysThreshold + " days of current date. Difference: " + daysDifference + " days");
                        System.out.println("Product " + (i + 1) + " date_iso is within " + daysThreshold + " days ✓");
                    } else {
                        System.out.println("Warning: Could not parse date_iso for product " + (i + 1) + ": " + dateIso);
                    }
                } else {
                    System.out.println("Warning: date_iso not found for product " + (i + 1));
                }
            }
        } else {
            System.out.println("No products found in preview");
        }
    }
}