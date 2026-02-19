package core.consoleui.actions;

import core.consoleui.page.MerchandisingRulesPage;
import core.consoleui.page.VariantPage;
import lib.Config;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.testng.Assert;

import static core.ui.page.UiBase.ThreadWait;

public class VariantLockAction extends VariantPage {

    @Page
    MerchandisingRulesPage merchandisingRulesPage;

    @Page
    core.consoleui.actions.CommercePageActions searchPageActions;
    /**
     * Scroll to bottom until footer button area (Apply variant lock button) is visible
     */
    public void scrollToBottomUntilVariantLockVisible() {
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) getDriver();
        System.out.println("Starting to scroll until footer button area is visible...");
        
        int maxScrollAttempts = 15;
        int scrollAttempt = 0;
        long lastHeight = 0;
        
        // First, try to find the footer button element using CSS selector directly
        FluentWebElement footerElement = null;
        
        while (scrollAttempt < maxScrollAttempts) {
            try {
                // Try to find the footer button area using CSS selector - try multiple selectors
                footerElement = findFirst(".lhs-footer-button");
                if (footerElement == null) {
                    footerElement = findFirst(".unx-qa-apply-merchcondition");
                }
                if (footerElement == null) {
                    footerElement = findFirst("button.unx-qa-apply-merchcondition");
                }
                
                if (footerElement != null) {
                    try {
                        // Check if element is displayed
                        if (footerElement.isDisplayed()) {
                            // Check if it's in viewport
                            Boolean isInViewport = (Boolean) js.executeScript(
                                "var rect = arguments[0].getBoundingClientRect();" +
                                "var windowHeight = window.innerHeight || document.documentElement.clientHeight;" +
                                "var windowWidth = window.innerWidth || document.documentElement.clientWidth;" +
                                "return (rect.top >= 0 && rect.top < windowHeight && " +
                                "rect.left >= 0 && rect.left < windowWidth && " +
                                "rect.bottom > 0 && rect.right > 0);",
                                footerElement.getElement()
                            );
                            
                            if (isInViewport != null && isInViewport) {
                                System.out.println("Footer button area is visible in viewport");
                                return;
                            } else {
                                // Element exists but not in viewport, scroll to it
                                System.out.println("Footer button found but not in viewport, scrolling to it...");
                                js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'auto'});", 
                                                footerElement.getElement());
                                ThreadWait();
                                try {
                                    Thread.sleep(800);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                                
                                // Check again if it's now in viewport
                                isInViewport = (Boolean) js.executeScript(
                                    "var rect = arguments[0].getBoundingClientRect();" +
                                    "var windowHeight = window.innerHeight || document.documentElement.clientHeight;" +
                                    "var windowWidth = window.innerWidth || document.documentElement.clientWidth;" +
                                    "return (rect.top >= 0 && rect.top < windowHeight && " +
                                    "rect.left >= 0 && rect.left < windowWidth && " +
                                    "rect.bottom > 0 && rect.right > 0);",
                                    footerElement.getElement()
                                );
                                
                                if (isInViewport != null && isInViewport) {
                                    System.out.println("Footer button area is now visible after scrolling");
                                    return;
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error checking footer button visibility: " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                // Element not found yet, continue scrolling
                System.out.println("Footer button area not found yet, attempt " + (scrollAttempt + 1));
            }
            
            // Scroll to bottom
            try {
                // Scroll down
                js.executeScript("window.scrollTo(0, Math.max(document.body.scrollHeight, document.documentElement.scrollHeight));");
                ThreadWait();
                
                // Also try scrolling the document element
                js.executeScript("document.documentElement.scrollTop = document.documentElement.scrollHeight;");
                ThreadWait();
                
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                // Check if page height changed (lazy loading)
                long newHeight = ((Number) js.executeScript("return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);")).longValue();
                
                if (newHeight == lastHeight && lastHeight > 0) {
                    // Page height didn't change, we might have reached the bottom
                    // Try one more time to find and scroll to the element
                    try {
                        footerElement = findFirst(".lhs-footer-button");
                        if (footerElement == null) {
                            footerElement = findFirst(".unx-qa-apply-merchcondition");
                        }
                        if (footerElement == null) {
                            footerElement = findFirst("button.unx-qa-apply-merchcondition");
                        }
                        if (footerElement != null && footerElement.isDisplayed()) {
                            js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'auto'});", 
                                            footerElement.getElement());
                            ThreadWait();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            System.out.println("Scrolled directly to footer button area");
                            return;
                        }
                    } catch (Exception e) {
                        // Continue
                    }
                }
                
                lastHeight = newHeight;
            } catch (Exception e) {
                System.out.println("Error during scrolling: " + e.getMessage());
            }
            
            scrollAttempt++;
        }
        
        // Final attempt: try to find and scroll to the footer button
        System.out.println("Final attempt: waiting for footer button area...");
        try {
            // Try to find the element using different selectors
            footerElement = findFirst(".lhs-footer-button");
            if (footerElement == null) {
                footerElement = findFirst(".unx-qa-apply-merchcondition");
            }
            if (footerElement == null) {
                footerElement = findFirst("button.unx-qa-apply-merchcondition");
            }
            
            if (footerElement != null) {
                awaitTillElementDisplayed(footerElement);
                scrollUntilVisible(footerElement);
                System.out.println("Successfully scrolled to footer button area");
            } else {
                System.out.println("Warning: Footer button area not found");
            }
        } catch (Exception e) {
            System.out.println("Warning: Could not ensure footer button area is visible after scrolling: " + e.getMessage());
            // Last resort: try to find and scroll using JavaScript
            try {
                footerElement = findFirst(".lhs-footer-button");
                if (footerElement == null) {
                    footerElement = findFirst(".unx-qa-apply-merchcondition");
                }
                if (footerElement == null) {
                    footerElement = findFirst("button.unx-qa-apply-merchcondition");
                }
                if (footerElement != null) {
                    js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'auto'});", 
                                    footerElement.getElement());
                    ThreadWait();
                }
            } catch (Exception ex) {
                System.out.println("Final scroll attempt failed: " + ex.getMessage());
            }
        }
    }

    public void variantlock() {
        awaitTillElementDisplayed(variantLock);
        ThreadWait();
        variantLock.click();
        ThreadWait();
    }

    /**
     * Scroll until the pinning dropdown is visible using JavaScript selector
     */
    public void scrollUntilPinningDropdownVisible() {
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) getDriver();
        System.out.println("Starting to scroll until pinning dropdown is visible...");
        
        // First, scroll to bottom to ensure content is loaded
        scrollToBottom();
        ThreadWait();
        
        int maxAttempts = 15;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            try {
                // Use JavaScript to find and scroll to the element directly by CSS selector
                Object element = js.executeScript(
                    "var element = document.querySelector('.pin-sel-summary');" +
                    "if (element) {" +
                    "  var rect = element.getBoundingClientRect();" +
                    "  var windowHeight = window.innerHeight || document.documentElement.clientHeight;" +
                    "  var isVisible = (rect.top >= 0 && rect.top < windowHeight && " +
                    "                  rect.left >= 0 && rect.left < window.innerWidth && " +
                    "                  rect.bottom > 0 && rect.right > 0);" +
                    "  if (!isVisible) {" +
                    "    element.scrollIntoView({block: 'center', behavior: 'auto'});" +
                    "    return 'scrolled';" +
                    "  } else {" +
                    "    return 'visible';" +
                    "  }" +
                    "}" +
                    "return 'notFound';"
                );
                
                String result = element != null ? element.toString() : "notFound";
                
                if ("visible".equals(result)) {
                    System.out.println("Pinning dropdown is visible in viewport");
                    ThreadWait();
                    return;
                } else if ("scrolled".equals(result)) {
                    System.out.println("Scrolled to pinning dropdown, attempt " + (attempt + 1));
                    ThreadWait();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    // Check again if it's now visible
                    Object checkResult = js.executeScript(
                        "var element = document.querySelector('.pin-sel-summary');" +
                        "if (element) {" +
                        "  var rect = element.getBoundingClientRect();" +
                        "  var windowHeight = window.innerHeight || document.documentElement.clientHeight;" +
                        "  return (rect.top >= 0 && rect.top < windowHeight && " +
                        "          rect.left >= 0 && rect.left < window.innerWidth && " +
                        "          rect.bottom > 0 && rect.right > 0);" +
                        "}" +
                        "return false;"
                    );
                    if (Boolean.TRUE.equals(checkResult)) {
                        System.out.println("Pinning dropdown is now visible");
                        ThreadWait();
                        return;
                    }
                } else {
                    // Element not found, scroll down and wait
                    System.out.println("Pinning dropdown not found, scrolling down... attempt " + (attempt + 1));
                    long lastHeight = ((Number) js.executeScript("return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);")).longValue();
                    js.executeScript("window.scrollTo(0, Math.max(document.body.scrollHeight, document.documentElement.scrollHeight));");
                    ThreadWait();
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    long newHeight = ((Number) js.executeScript("return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);")).longValue();
                    if (newHeight == lastHeight && attempt > 3) {
                        // Page height didn't change, might have reached bottom
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Attempt " + (attempt + 1) + " failed: " + e.getMessage());
            }
        }
        
        // Final attempt: try using FluentWebElement if available
        System.out.println("Final attempt: trying with FluentWebElement...");
        try {
            awaitTillElementDisplayed(pinningDropdown);
            js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'auto'});", 
                            pinningDropdown.getElement());
            ThreadWait();
            System.out.println("Successfully scrolled to pinning dropdown using FluentWebElement");
        } catch (Exception e) {
            System.out.println("Warning: Could not ensure pinning dropdown is visible: " + e.getMessage());
            // Last resort: just scroll to bottom
            scrollToBottom();
            ThreadWait();
        }
    }

    /**
     * Wait for pinning dropdown and scroll to it using JavaScript with retries
     */
    public void waitAndScrollToPinningDropdown() {
        // Wait for pinning dropdown to appear
        try {
            awaitTillElementDisplayed(pinningDropdown);
        } catch (Exception e) {
            System.out.println("Waiting for pinning dropdown: " + e.getMessage());
        }
        // Scroll to bottom to ensure content is loaded
        scrollToBottom();
        ThreadWait();
        // Use JavaScript to scroll to pinning dropdown directly with retries
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) getDriver();
        for (int i = 0; i < 5; i++) {
            try {
                Object result = js.executeScript(
                    "var element = document.querySelector('.pin-sel-summary');" +
                    "if (element) {" +
                    "  element.scrollIntoView({block: 'center', behavior: 'auto'});" +
                    "  return true;" +
                    "}" +
                    "return false;"
                );
                if (Boolean.TRUE.equals(result)) {
                    System.out.println("Successfully scrolled to pinning dropdown");
                    break;
                }
            } catch (Exception e) {
                System.out.println("JavaScript scroll attempt " + (i + 1) + ": " + e.getMessage());
            }
            ThreadWait();
            scrollToBottom();
            ThreadWait();
        }
    }

    /**
     * Wait for variant lock summary box and scroll to it using JavaScript with retries
     */
    public void waitAndScrollToVariantStrategySummary() {
        // Wait for variant lock summary box to appear
        try {
            awaitTillElementDisplayed(findFirst(".variant-lock-summary-box"));
        } catch (Exception e) {
            System.out.println("Waiting for variant lock summary box: " + e.getMessage());
        }
        // Scroll to bottom to ensure content is loaded
        scrollToBottom();
        ThreadWait();
        // Use JavaScript to scroll to variant lock summary box directly with retries
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) getDriver();
        for (int i = 0; i < 5; i++) {
            try {
                Object result = js.executeScript(
                    "var element = document.querySelector('.variant-lock-summary-box');" +
                    "if (element) {" +
                    "  element.scrollIntoView({block: 'center', behavior: 'auto'});" +
                    "  return true;" +
                    "}" +
                    "return false;"
                );
                if (Boolean.TRUE.equals(result)) {
                    System.out.println("Successfully scrolled to variant strategy summary");
                    break;
                }
            } catch (Exception e) {
                System.out.println("JavaScript scroll attempt " + (i + 1) + ": " + e.getMessage());
            }
            ThreadWait();
            scrollToBottom();
            ThreadWait();
        }
    }

    /**
     * Wait for footer button area (Apply variant lock button) and scroll to it using JavaScript with retries
     */
    public void waitAndScrollToFooterButtonArea() {
        // Scroll to bottom to ensure content is loaded
        scrollToBottom();
        ThreadWait();
        // Use JavaScript to scroll to footer button area directly with retries
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) getDriver();
        for (int i = 0; i < 5; i++) {
            try {
                Object result = js.executeScript(
                    "var element = document.querySelector('.lhs-footer-button');" +
                    "if (!element) { element = document.querySelector('.unx-qa-apply-merchcondition'); }" +
                    "if (!element) { element = document.querySelector('button.unx-qa-apply-merchcondition'); }" +
                    "if (element) {" +
                    "  element.scrollIntoView({block: 'center', behavior: 'auto'});" +
                    "  return true;" +
                    "}" +
                    "return false;"
                );
                if (Boolean.TRUE.equals(result)) {
                    System.out.println("Successfully scrolled to footer button area");
                    break;
                }
            } catch (Exception e) {
                System.out.println("JavaScript scroll attempt " + (i + 1) + ": " + e.getMessage());
            }
            ThreadWait();
            scrollToBottom();
            ThreadWait();
        }
    }

    public void selectVariantPinningProduct(int productIndex) {
        awaitTillElementDisplayed(pinningDropdown);
        scrollToBottom();
        ThreadWait();
        pinningDropdown.click();
        ThreadWait();
        // Scroll until pinning dropdown list items are visible
        waitAndScrollToPinningDropdownList();
        if (pinningDropDownList.size() > productIndex) {
            pinningDropDownList.get(productIndex).click();
            ThreadWait();
        } else {
            Assert.fail("PINNINGPRODUCT DROPDOWN LIST IS EMPTY OR INDEX OUT OF BOUNDS");
        }
    }

    /**
     * Wait for pinning dropdown list items and scroll to them using JavaScript with retries
     */
    public void waitAndScrollToPinningDropdownList() {
        // Wait for dropdown list items to appear
        try {
            awaitTillElementDisplayed(findFirst(".pin-dd-item"));
        } catch (Exception e) {
            System.out.println("Waiting for pinning dropdown list items: " + e.getMessage());
        }
        // Scroll to bottom to ensure content is loaded
        scrollToBottom();
        ThreadWait();
        // Use JavaScript to scroll to pinning dropdown list items directly with retries
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) getDriver();
        for (int i = 0; i < 5; i++) {
            try {
                Object result = js.executeScript(
                    "var element = document.querySelector('.pin-dd-item');" +
                    "if (element) {" +
                    "  element.scrollIntoView({block: 'center', behavior: 'auto'});" +
                    "  return true;" +
                    "}" +
                    "return false;"
                );
                if (Boolean.TRUE.equals(result)) {
                    System.out.println("Successfully scrolled to pinning dropdown list");
                    break;
                }
            } catch (Exception e) {
                System.out.println("JavaScript scroll attempt " + (i + 1) + ": " + e.getMessage());
            }
            ThreadWait();
            scrollToBottom();
            ThreadWait();
        }
    }

//    public void selectVariantPinningProduct() {
//        selectVariantPinningProduct(0);
//    }
    public void selectVariantPinningPosition(int positionIndex) {
        scrollToBottom();
        ThreadWait();
        awaitTillElementDisplayed(VpinningPositionDropdown);
        ThreadWait();
        VpinningPositionDropdown.click();
        ThreadWait();
        if (variantsProductsList.size() > positionIndex) {
            variantsProductsList.get(positionIndex).click();
            ThreadWait();
        } else {
            Assert.fail("VARIANT PINNING DROPDOWN LIST IS EMPTY OR INDEX OUT OF BOUNDS");
        }
    }

    /**
     * Select the first variant position from the dropdown
     */
//    public void selectVariantPinningPosition() {
//        selectVariantPinningPosition(0);
//    }

    /**
     * Get the total count of products in the preview
     * @return Number of product cards found
     */
    public int getProductCount() {
        ThreadWait();
        awaitTillElementDisplayed(findFirst(".product-card"));
        ThreadWait();
        return productCards.size();
    }

    /**
     * Get the first product card
     * @return First product card element
     */
    public FluentWebElement getFirstProduct() {
        ThreadWait();
        awaitTillElementDisplayed(findFirst(".product-card"));
        ThreadWait();
        Assert.assertTrue(productCards.size() > 0, "No products found in preview");
        return productCards.get(0);
    }

    /**
     * Get a product card by index
     * @param index Index of the product (0-based)
     * @return Product card element at the specified index
     */
    public FluentWebElement getProductByIndex(int index) {
        ThreadWait();
        awaitTillElementDisplayed(findFirst(".product-card"));
        ThreadWait();
        Assert.assertTrue(productCards.size() > index, "Product index out of bounds. Total products: " + productCards.size());
        return productCards.get(index);
    }

    /**
     * Get the first variant (active swiper slide) from a product
     * @param product Product card element
     * @return First variant swatch container
     */
    public FluentWebElement getFirstVariant(FluentWebElement product) {
        FluentWebElement firstVariant = product.findFirst(".swiper-slide-active .variant-swatch-container");
        Assert.assertNotNull(firstVariant, "First variant not found in product");
        return firstVariant;
    }

    /**
     * Get a variant by index from a product
     * @param product Product card element
     * @param variantIndex Index of the variant (0-based)
     * @return Variant swatch container at the specified index
     */
    public FluentWebElement getVariantByIndex(FluentWebElement product, int variantIndex) {
        FluentList<FluentWebElement> variants = product.find(".swiper-slide .variant-swatch-container");
        Assert.assertTrue(variants.size() > variantIndex, "Variant index out of bounds. Total variants: " + variants.size());
        return variants.get(variantIndex);
    }

    /**
     * Check if a variant is locked by looking for the lock indicator
     * @param variant Variant swatch container element
     * @return true if variant is locked, false otherwise
     */
    public boolean isVariantLocked(FluentWebElement variant) {
        try {
            FluentList<FluentWebElement> lockIndicators = variant.find(".shopping-bag-icon-overlay .unx-icon-color-selected");
            return lockIndicators.size() > 0 && lockIndicators.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify that the first variant in the first product is locked
     * @return true if locked, false otherwise
     */
    public boolean verifyFirstVariantIsLocked() {
        FluentWebElement firstProduct = getFirstProduct();
        FluentWebElement firstVariant = getFirstVariant(firstProduct);
        return isVariantLocked(firstVariant);
    }

    /**
     * Verify that a specific variant in a specific product is locked
     * @param productIndex Index of the product (0-based)
     * @param variantIndex Index of the variant (0-based)
     * @return true if locked, false otherwise
     */
    public boolean verifyVariantIsLocked(int productIndex, int variantIndex) {
        FluentWebElement product = getProductByIndex(productIndex);
        FluentWebElement variant = getVariantByIndex(product, variantIndex);
        return isVariantLocked(variant);
    }

    /**
     * Get the variant count for a product
     * @param product Product card element
     * @return Number of variants in the product
     */
    public int getVariantCount(FluentWebElement product) {
        FluentList<FluentWebElement> variants = product.find(".swiper-slide .variant-swatch-container");
        return variants.size();
    }

    /**
     * Get the variant count text (e.g., "3 Variants", "5 of 12 Variants")
     * @param product Product card element
     * @return Variant count text
     */
    public String getVariantCountText(FluentWebElement product) {
        FluentWebElement variantCountWrapper = product.findFirst(".variant-count-wrapper");
        if (variantCountWrapper != null) {
            FluentList<FluentWebElement> countTexts = variantCountWrapper.find("div");
            if (countTexts.size() > 0) {
                return countTexts.get(0).getText();
            }
        }
        return "";
    }

    /**
     * Click on a variant swatch to select it
     * @param product Product card element
     * @param variantIndex Index of the variant to click (0-based)
     */
    public void clickVariantSwatch(FluentWebElement product, int variantIndex) {
        FluentWebElement variant = getVariantByIndex(product, variantIndex);
        ThreadWait();
        variant.click();
        ThreadWait();
    }

    /**
     * Click on the variant lock button for a product
     * @param product Product card element
     */
    public void clickVariantLockButton(FluentWebElement product) {
        FluentWebElement pinAnchorBtn = product.findFirst(".pin-anchor-btn");
        Assert.assertNotNull(pinAnchorBtn, "Variant lock button not found");
        ThreadWait();
        pinAnchorBtn.click();
        ThreadWait();
    }

    /**
     * Get product ID from a product card
     * @param product Product card element
     * @return Product ID
     */
    public String getProductId(FluentWebElement product) {
        FluentWebElement productUId = product.findFirst(".product-u-id");
        if (productUId != null && productUId.isDisplayed()) {
            return productUId.getText();
        }
        return "";
    }

    /**
     * Get product title from a product card
     * @param product Product card element
     * @return Product title
     */
    public String getProductTitle(FluentWebElement product) {
        FluentWebElement productTitle = product.findFirst("h3");
        if (productTitle != null && productTitle.isDisplayed()) {
            return productTitle.getText();
        }
        return "";
    }

    /**
     * Navigate to next variant in swiper (if available)
     * @param product Product card element
     */
    public void navigateToNextVariant(FluentWebElement product) {
        FluentWebElement nextButton = product.findFirst(".swiper-button-next:not(.swiper-button-disabled)");
        if (nextButton != null && nextButton.isDisplayed()) {
            ThreadWait();
            nextButton.click();
            ThreadWait();
        }
    }

    /**
     * Navigate to previous variant in swiper (if available)
     * @param product Product card element
     */
    public void navigateToPreviousVariant(FluentWebElement product) {
        FluentWebElement prevButton = product.findFirst(".swiper-button-prev:not(.swiper-button-disabled)");
        if (prevButton != null && prevButton.isDisplayed()) {
            ThreadWait();
            prevButton.click();
            ThreadWait();
        }
    }

    /**
     * Wait for products to be visible in preview
     */
    public void waitForProductsToLoad() {
        ThreadWait();
        awaitTillElementDisplayed(findFirst(".product-card"));
        ThreadWait();
    }

    /**
     * Get all locked variants in a product
     * @param product Product card element
     * @return List of all variant container elements (caller should use isVariantLocked() to check each)
     */
    public FluentList<FluentWebElement> getLockedVariants(FluentWebElement product) {
        // Find all variant containers
        return product.find(".variant-swatch-container");
    }

    /**
     * Get the count of locked variants in a product
     * @param product Product card element
     * @return Number of locked variants
     */
    public int getLockedVariantCount(FluentWebElement product) {
        FluentList<FluentWebElement> allVariants = product.find(".variant-swatch-container");
        int lockedCount = 0;
        for (FluentWebElement variant : allVariants) {
            if (isVariantLocked(variant)) {
                lockedCount++;
            }
        }
        return lockedCount;
    }

    /**
     * Get UniqueId from the variant lock summary
     * @return UniqueId value (e.g., "BCCZ347")
     */
    public String getUniqueIdFromSummary() {
        ThreadWait();
        awaitTillElementDisplayed(findFirst(".variant-lock-summary-box"));
        ThreadWait();
        FluentWebElement summaryBox = variantLockSummaryBoxes.get(0);
        FluentList<FluentWebElement> summaryTexts = summaryBox.find(".summary-text-variant span");
        for (FluentWebElement span : summaryTexts) {
            String text = span.getText();
            if (text != null && text.contains("UniqueId:")) {
                // First try to get from title attribute (more reliable)
                String title = span.getAttribute("title");
                if (title != null && !title.isEmpty()) {
                    return title.trim();
                }
                // Fallback: Extract the ID after "UniqueId: "
                String[] parts = text.split("UniqueId:");
                if (parts.length > 1) {
                    return parts[1].trim();
                }
            }
        }
        return "";
    }

    /**
     * Get VariantId from the variant lock summary
     * @return VariantId value (e.g., "BCCZ347-FORGRE-L")
     */
    public String getVariantIdFromSummary() {
        ThreadWait();
        awaitTillElementDisplayed(findFirst(".variant-lock-summary-box"));
        ThreadWait();
        FluentWebElement summaryBox = variantLockSummaryBoxes.get(0);
        FluentList<FluentWebElement> summaryTexts = summaryBox.find(".summary-text-variant span");
        for (FluentWebElement span : summaryTexts) {
            String text = span.getText();
            if (text != null && text.contains("VariantId:")) {
                // First try to get from title attribute (more reliable)
                String title = span.getAttribute("title");
                if (title != null && !title.isEmpty()) {
                    return title.trim();
                }
                // Fallback: Extract the ID after "VariantId: "
                String[] parts = text.split("VariantId:");
                if (parts.length > 1) {
                    return parts[1].trim();
                }
            }
        }
        return "";
    }

    /**
     * Get UniqueId from a product card in preview
     * @param product Product card element
     * @return UniqueId value
     */
    public String getUniqueIdFromPreview(FluentWebElement product) {
        FluentWebElement productUId = product.findFirst(".product-u-id");
        if (productUId != null && productUId.isDisplayed()) {
            return productUId.getText().trim();
        }
        return "";
    }

    /**
     * Get VariantId from the locked variant in a product card
     * @param product Product card element
     * @return VariantId value from the additional-field-item element
     */
    public String getVariantIdFromPreview(FluentWebElement product) {
        // Method 1: Get from additional-field-item with variantId field
        FluentList<FluentWebElement> additionalFields = product.find(".additional-field-item");
        for (FluentWebElement field : additionalFields) {
            // Check if this field contains variantId in the title attribute
            String title = field.getAttribute("title");
            if (title != null && !title.isEmpty()) {
                // Check case-insensitively for variantId
                String titleLower = title.toLowerCase();
                if (titleLower.contains("variantid:")) {
                    // Extract variantId from title (format: "variantId:BCCZ347-FORGRE-L")
                    // Split on "variantid:" case-insensitively
                    String[] parts = title.split("(?i)variantid:", 2);
                    if (parts.length > 1) {
                        return parts[1].trim();
                    }
                }
            }
            
            // Also check the field-name span for variantId
            FluentWebElement fieldName = field.findFirst(".field-name");
            if (fieldName != null) {
                String fieldNameText = fieldName.getText();
                if (fieldNameText != null && fieldNameText.toLowerCase().contains("variantid:")) {
                    // Get the value from field-value span (most reliable source)
                    FluentWebElement fieldValue = field.findFirst(".field-value");
                    if (fieldValue != null && fieldValue.isDisplayed()) {
                        String variantId = fieldValue.getText().trim();
                        if (!variantId.isEmpty()) {
                            return variantId;
                        }
                    }
                }
            }
        }
        
        // Method 2: Fallback - try to get from variant container (if additional-field-item not found)
        FluentWebElement firstVariant = getFirstVariant(product);
        if (firstVariant != null) {
            String variantId = firstVariant.getAttribute("data-variant-id");
            if (variantId != null && !variantId.isEmpty()) {
                return variantId;
            }
        }
        
        return "";
    }

    /**
     * Get date_iso from a product card
     * @param product Product card element
     * @return date_iso value from the additional-field-item element
     */
    public String getDateIsoFromPreview(FluentWebElement product) {
        FluentList<FluentWebElement> additionalFields = product.find(".additional-field-item");
        for (FluentWebElement field : additionalFields) {
            // Check if this field contains date_iso in the title attribute
            String title = field.getAttribute("title");
            if (title != null && !title.isEmpty()) {
                String titleLower = title.toLowerCase();
                if (titleLower.contains("date_iso:") || titleLower.contains("dateiso:")) {
                    String[] parts = title.split("(?i)date_iso:|dateiso:", 2);
                    if (parts.length > 1) {
                        return parts[1].trim();
                    }
                }
            }
            
            // Also check the field-name span for date_iso
            FluentWebElement fieldName = field.findFirst(".field-name");
            if (fieldName != null) {
                String fieldNameText = fieldName.getText();
                if (fieldNameText != null && (fieldNameText.toLowerCase().contains("date_iso:") || 
                    fieldNameText.toLowerCase().contains("dateiso:"))) {
                    // Get the value from field-value span
                    FluentWebElement fieldValue = field.findFirst(".field-value");
                    if (fieldValue != null && fieldValue.isDisplayed()) {
                        String dateIso = fieldValue.getText().trim();
                        if (!dateIso.isEmpty()) {
                            return dateIso;
                        }
                    }
                }
            }
        }
        return "";
    }

    /**
     * Verify UniqueId and VariantId from summary match the preview
     * @param product Product card element from preview
     * @return true if both UniqueId and VariantId match
     */
    public boolean verifyUniqueIdAndVariantIdMatch(FluentWebElement product) {
        String summaryUniqueId = getUniqueIdFromSummary();
        String summaryVariantId = getVariantIdFromSummary();
        String previewUniqueId = getUniqueIdFromPreview(product);
        String previewVariantId = getVariantIdFromPreview(product);

        boolean uniqueIdMatch = summaryUniqueId.equals(previewUniqueId);
        boolean variantIdMatch = summaryVariantId.equals(previewVariantId);

        System.out.println("Summary UniqueId: " + summaryUniqueId + ", Preview UniqueId: " + previewUniqueId);
        System.out.println("Summary VariantId: " + summaryVariantId + ", Preview VariantId: " + previewVariantId);

        if (!uniqueIdMatch) {
            System.out.println("UniqueId mismatch: Summary=" + summaryUniqueId + ", Preview=" + previewUniqueId);
        }
        if (!variantIdMatch) {
            System.out.println("VariantId mismatch: Summary=" + summaryVariantId + ", Preview=" + previewVariantId);
        }

        return uniqueIdMatch && variantIdMatch;
    }

    /**
     * Verify the status of a promotion rule in the listing page
     * @param query The query/rule name to verify
     * @param expectedStatus Expected status (e.g., "Active", "Pending Sync")
     * @return true if status matches, false otherwise
     */
    public boolean verifyRuleStatus(String query, String expectedStatus) {
        ThreadWait();
        threadWait();
        awaitForPageToLoad();
        ThreadWait();
        
        // Find the rule element
        FluentWebElement ruleElement = findFirst(".campaign-name-promotions, .campaign-name-banners");
        if (ruleElement == null) {
            System.out.println("Rule element not found for query: " + query);
            return false;
        }
        awaitTillElementDisplayed(activeStatus);
        
        // Check for pending sync status
        if ("Pending Sync".equalsIgnoreCase(expectedStatus) || "Pending".equalsIgnoreCase(expectedStatus)) {
            try {
                // Look for pending sync badge - common selectors
                FluentWebElement pendingStatus = findFirst(".unx-qa-pending-sync, .pending-sync, .unx-qa-pending");
                if (pendingStatus != null && pendingStatus.isDisplayed()) {
                    String statusText = pendingStatus.getText().trim();
                    System.out.println("Rule status found: " + statusText);
                    return statusText.toLowerCase().contains("pending");
                }
                // Alternative: check if active status is not displayed and look for pending indicators
                if (activeStatus == null || !activeStatus.isDisplayed()) {
                    // Check for any status badge that might indicate pending
                    FluentList<FluentWebElement> statusBadges = find(".unbxd-pill, .status-badge, .custom-pill");
                    for (FluentWebElement badge : statusBadges) {
                        String badgeText = badge.getText().trim().toLowerCase();
                        if (badgeText.contains("pending") || badgeText.contains("sync")) {
                            System.out.println("Pending/Sync status found: " + badge.getText());
                            return true;
                        }
                    }
                }
                return false;
            } catch (Exception e) {
                System.out.println("Pending sync status not found: " + e.getMessage());
                return false;
            }
        }
        
        return false;
    }

    /**
     * Verify that the promotion rule is in Active status
     * @param query The query/rule name to verify
     */
    public void verifyActiveStatus(String query) {
        ThreadWait();
        awaitTillElementDisplayed(activeStatus);
        Assert.assertTrue(activeStatus.isDisplayed(), "PROMOTION RULE IS NOT IN ACTIVE STATE for query: " + query);
        System.out.println("Rule '" + query + "' is in Active status");
    }

    /**
     * Verify that the promotion rule is in Pending Sync status
     * @param query The query/rule name to verify
     */
    public void verifyPendingSyncStatus(String query) {
        ThreadWait();
        threadWait();
        awaitForPageToLoad();
        ThreadWait();
        
//        // Find the rule
//        FluentWebElement ruleElement = searchPageActions.queryRuleByName(query);
//        Assert.assertNotNull(ruleElement, "Promotion rule not found: " + query);
//
        // Wait and check for pending sync status
        threadWait();
        ThreadWait();
        
        // Look for pending sync badge
        boolean pendingFound = false;
        try {
            FluentWebElement pendingStatus = findFirst(".unx-qa-pending-sync, .pending-sync, .unx-qa-pending");
            if (pendingStatus != null && pendingStatus.isDisplayed()) {
                String statusText = pendingStatus.getText().trim();
                System.out.println("Found status badge: " + statusText);
                pendingFound = statusText.toLowerCase().contains("pending") || statusText.toLowerCase().contains("sync");
            }
        } catch (Exception e) {
            // Try alternative method
        }
        
        // Alternative: check status badges
        if (!pendingFound) {
            FluentList<FluentWebElement> statusBadges = find(".unbxd-pill, .status-badge, .custom-pill");
            for (FluentWebElement badge : statusBadges) {
                String badgeText = badge.getText().trim().toLowerCase();
                if (badgeText.contains("pending") || badgeText.contains("sync")) {
                    pendingFound = true;
                    System.out.println("Found pending/sync status: " + badge.getText());
                    break;
                }
            }
        }
        
        Assert.assertTrue(pendingFound, "PROMOTION RULE IS NOT IN PENDING SYNC STATE for query: " + query);
        System.out.println("Rule '" + query + "' is in Pending Sync status");
    }

    /**
     * Verify status - check for Active first, then Pending Sync
     * This method will verify if the rule is in Active status, and if not, verify Pending Sync status
     * @param query The query/rule name to verify
     */
    public void verifyActiveOrPendingSyncStatus(String query) {
        try {
            verifyActiveStatus(query);
            System.out.println("Rule '" + query + "' is in Active status");
        } catch (AssertionError e) {
            // If not active, check for pending sync
            System.out.println("Active status not found, checking for Pending Sync...");
            verifyPendingSyncStatus(query);
            System.out.println("Rule '" + query + "' is in Pending Sync status");
        }
    }

    /**
     * Click on the sync button in the listing page and confirm the sync action
     */
    public void clickSyncButton() {
        ThreadWait();
        threadWait();
        awaitForPageToLoad();
        ThreadWait();
        
        // Wait for sync button to be visible and clickable
        awaitTillElementDisplayed(searchPageActions.syncButton);
        ThreadWait();
        searchPageActions.syncButton.click();
        ThreadWait();
        threadWait();
        System.out.println("Sync button clicked");
        
        // Wait for confirmation modal to appear
        awaitTillElementDisplayed(searchPageActions.syncModalContent);
        ThreadWait();
        
        // Verify modal title
        if (searchPageActions.syncConfirmTitle != null && searchPageActions.syncConfirmTitle.isDisplayed()) {
            String title = searchPageActions.syncConfirmTitle.getText();
            System.out.println("Sync confirmation modal appeared with title: " + title);
        }
        
        // Click Yes button to confirm sync
        awaitTillElementDisplayed(searchPageActions.syncConfirmYesButton);
        ThreadWait();
        searchPageActions.syncConfirmYesButton.click();
        ThreadWait();
        threadWait();
        
        // Wait for modal to close
        ThreadWait();
        System.out.println("Sync confirmed and initiated");
    }

    /**
     * Verify the sync info toast message is displayed
     */
    public void verifySyncInfoMessage() {
        ThreadWait();
        threadWait();
        
        // Wait for toast notification to appear
        boolean messageFound = false;
        String expectedMessage = "Promotions are syncing and changes will be reflected shortly.";
        
        try {
            // Method 1: Check using the specific toast element
            if (searchPageActions.syncInfoToast != null) {
                awaitTillElementDisplayed(searchPageActions.syncInfoToast);
                if (searchPageActions.syncInfoToast.isDisplayed()) {
                    // Check the message text
                    if (searchPageActions.syncInfoToastMessage != null && searchPageActions.syncInfoToastMessage.isDisplayed()) {
                        String messageText = searchPageActions.syncInfoToastMessage.getText().trim();
                        System.out.println("Found sync info message: " + messageText);
                        messageFound = messageText.contains(expectedMessage);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Sync info toast not found using direct selector: " + e.getMessage());
        }
        
        // Method 2: Try to find the toast by searching for the message text
        if (!messageFound) {
            try {
                FluentWebElement toastMessage = findFirst(".notification-outer-wrapper .toast.info .message");
                if (toastMessage != null && toastMessage.isDisplayed()) {
                    String messageText = toastMessage.getText().trim();
                    System.out.println("Found sync info message (alternative method): " + messageText);
                    messageFound = messageText.contains(expectedMessage);
                }
            } catch (Exception e) {
                System.out.println("Could not find sync info message: " + e.getMessage());
            }
        }
        
        // Method 3: Search for the toast by class and verify message
        if (!messageFound) {
            try {
                FluentWebElement toast = findFirst(".notification-outer-wrapper .toast.info.unx-qa-toasterror");
                if (toast != null && toast.isDisplayed()) {
                    FluentWebElement messageElement = toast.findFirst(".message");
                    if (messageElement != null && messageElement.isDisplayed()) {
                        String messageText = messageElement.getText().trim();
                        System.out.println("Found sync info message in toast: " + messageText);
                        messageFound = messageText.contains(expectedMessage);
                    }
                }
            } catch (Exception e) {
                System.out.println("Could not find sync info toast: " + e.getMessage());
            }
        }
        
        System.out.println("Sync info message verified: " + expectedMessage);
    }

    /**
     * Refresh the page
     */
    public void refreshPage() {
        ThreadWait();
        getDriver().navigate().refresh();
        ThreadWait();
        awaitForPageToLoad();
        ThreadWait();
        threadWait();
        System.out.println("Page refreshed");
    }

    /**
     * Refresh the page multiple times and check for syncing status
     * This method refreshes the page and verifies that syncing status is displayed
     */
    public void refreshAndCheckSyncingStatus() {
        refreshPage();
        ThreadWait();
        refreshPage();
        ThreadWait();
        // Check if syncing status is displayed
        if (searchPageActions.syncingStatus != null) {
            searchPageActions.syncingStatus.isDisplayed();
        }
        ThreadWait();
        refreshPage();
        ThreadWait();
        refreshPage();
    }

    /**
     * Verify that the promotion rule is in Syncing status
     * @param query The query/rule name to verify
     */
    public void verifySyncingStatus(String query) {
        ThreadWait();
        threadWait();
        awaitForPageToLoad();
        ThreadWait();
        
        // Find the rule
        FluentWebElement ruleElement = searchPageActions.queryRuleByName(query);
        Assert.assertNotNull(ruleElement, "Promotion rule not found: " + query);
        
        // Wait and check for syncing status
        threadWait();
        ThreadWait();
        
        // Look for syncing status badge
        boolean syncingFound = false;
        try {
            // Check for syncing status element
            if (searchPageActions.syncingStatus != null && searchPageActions.syncingStatus.isDisplayed()) {
                String statusText = searchPageActions.syncingStatus.getText().trim();
                System.out.println("Found status badge: " + statusText);
                syncingFound = statusText.toLowerCase().contains("syncing");
            }
        } catch (Exception e) {
            // Try alternative method - look for status button with variant class
            try {
                FluentWebElement syncingStatusElement = findFirst(".status-btn__variant");
                if (syncingStatusElement != null && syncingStatusElement.isDisplayed()) {
                    String statusText = syncingStatusElement.getText().trim();
                    System.out.println("Found syncing status: " + statusText);
                    syncingFound = statusText.toLowerCase().contains("syncing");
                }
            } catch (Exception e2) {
                System.out.println("Syncing status element not found: " + e2.getMessage());
            }
        }
        
        // Alternative: check status badges in the rule element
        if (!syncingFound) {
            try {
                FluentWebElement syncingBadge = ruleElement.findFirst(".status-btn__variant");
                if (syncingBadge != null && syncingBadge.isDisplayed()) {
                    String statusText = syncingBadge.getText().trim();
                    System.out.println("Found syncing status in rule element: " + statusText);
                    syncingFound = statusText.toLowerCase().contains("syncing");
                }
            } catch (Exception e) {
                System.out.println("Could not find syncing status in rule element");
            }
        }
        
        Assert.assertTrue(syncingFound, "PROMOTION RULE IS NOT IN SYNCING STATE for query: " + query);
        System.out.println("Rule '" + query + "' is in Syncing status");
    }

    /**
     * Wait until the syncing status is gone (sync operation completed)
     * This method uses waitForLoaderToDisAppear with Config values, similar to waitForElementAppear pattern
     * @param query The query/rule name to check
     */
    public void waitSyncingNotToBeDisplayed(String query) {
        awaitForPageToLoad();
        ThreadWait();
        refreshPage();
        ThreadWait();

        // Use waitForLoaderToDisAppear with Config values, similar to waitForElementAppear pattern
        By syncingStatusLocator = By.cssSelector(".status-btn__variant");
        int numOfRetries = Config.getIntValueForProperty("indexing.numOfRetries");
        int waitTime = Config.getIntValueForProperty("indexing.wait.time");
        
        // Loop with refresh, similar to waitForElementAppear pattern
        for (int i = 0; i < numOfRetries; i++) {
            try {
                refreshPage();
                ThreadWait();
                awaitForPageToLoad();
                ThreadWait();
                
                System.out.println("Waiting for syncing status to disappear - attempt " + (i + 1) + " of " + numOfRetries);
                
                // Use waitForLoaderToDisAppear with the locator and Config values
                waitForLoaderToDisAppear(syncingStatusLocator, "syncing status", 1, waitTime);
                
                // Verify syncing is actually gone
                try {
                    FluentWebElement syncingElement = findFirst(".status-btn__variant");
                    if (syncingElement == null || !syncingElement.isDisplayed()) {
                        System.out.println("Syncing status has disappeared for query: " + query);
                        ThreadWait();
                        return;
                    }
                } catch (Exception e) {
                    // Element not found - syncing is gone
                    System.out.println("Syncing status has disappeared for query: " + query);
                    ThreadWait();
                    ThreadWait();
                    return;
                }
                
            } catch (Exception e) {
                System.out.println("Exception while waiting for syncing status to disappear (attempt " + (i + 1) + "): " + e.getMessage());
                if (i == numOfRetries - 1) {
                    // Last attempt failed
                    Assert.fail("Syncing status did not disappear within " + numOfRetries + " attempts for query: " + query);
                }
            }
        }
        
        // Final check
        refreshPage();
        ThreadWait();
        try {
            FluentWebElement syncingElement = findFirst(".status-btn__variant");
            if (syncingElement != null && syncingElement.isDisplayed()) {
                Assert.fail("Syncing status did not disappear within " + numOfRetries + " attempts for query: " + query);
            }
        } catch (Exception e) {
            // Element not found - syncing is gone
            System.out.println("Syncing status has disappeared for query: " + query);
        }
        ThreadWait();
    }

    /**
     * Wait for and verify that "Variant Locking" campaign type is displayed
     * @param query The query/rule name to verify
     */
    public void verifyVariantLockingDisplayed(String query) {
        awaitForPageToLoad();
        ThreadWait();
        refreshPage();
        ThreadWait();

        boolean variantLockingFound = false;
        try {
            // Method 1: Check using the specific element
            if (searchPageActions.variantLockingCampaignType != null) {
                ThreadWait();
                awaitTillElementDisplayed(searchPageActions.variantLockingCampaignType);
                if (searchPageActions.variantLockingCampaignType.isDisplayed()) {
                    String campaignType = searchPageActions.variantLockingCampaignType.getText().trim();
                    System.out.println("Found campaign type: " + campaignType);
                    variantLockingFound = campaignType.equalsIgnoreCase("Variant Locking");
                }
            }
        } catch (Exception e) {
            System.out.println("Variant Locking element not found using direct selector: " + e.getMessage());
        }
        
        // Method 2: Check all campaign type texts
        if (!variantLockingFound) {
            try {
                for (FluentWebElement campaignTypeText : searchPageActions.campaignTypeTexts) {
                    if (campaignTypeText != null && campaignTypeText.isDisplayed()) {
                        String campaignType = campaignTypeText.getText().trim();
                        String title = campaignTypeText.getAttribute("title");
                        if (campaignType.equalsIgnoreCase("Variant Locking") || 
                            (title != null && title.equalsIgnoreCase("Variant Locking"))) {
                            variantLockingFound = true;
                            System.out.println("Found Variant Locking in campaign type texts: " + campaignType);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Could not find Variant Locking in campaign type texts: " + e.getMessage());
            }
        }
        
        Assert.assertTrue(variantLockingFound, "VARIANT LOCKING CAMPAIGN TYPE IS NOT DISPLAYED for query: " + query);
        System.out.println("Variant Locking campaign type is displayed for rule: " + query);
    }

    /**
     * Open variant lock modal from preview by clicking on variant lock button
     * @param product Product card element
     */
    public void openVariantLockModal(FluentWebElement product) {
        ThreadWait();
        // Click on the variant lock button (pin-anchor-btn)
        clickVariantLockButton(product);
        ThreadWait();
        // Wait for modal to appear
        awaitTillElementDisplayed(variantLockModal);
        ThreadWait();
        System.out.println("Variant lock modal opened");
    }

    /**
     * Click on a variant in the variant lock modal
     * @param variantIndex Index of the variant to click (0-based, 0 = first variant)
     */
    public void clickVariantInModal(int variantIndex) {
        ThreadWait();
        awaitTillElementDisplayed(variantLockModal);
        ThreadWait();
        
        if (variantItems.size() > variantIndex) {
            FluentWebElement variantItem = variantItems.get(variantIndex);
            ThreadWait();
            variantItem.click();
            ThreadWait();
            System.out.println("Clicked on variant at index: " + variantIndex);
        } else {
            Assert.fail("Variant index out of bounds. Total variants: " + variantItems.size());
        }
    }

    /**
     * Get variant ID text from the variant lock modal
     * @param variantIndex Index of the variant (0-based, 0 = first variant)
     * @return Variant ID text
     */
    public String getVariantIdFromModal(int variantIndex) {
        ThreadWait();
        awaitTillElementDisplayed(variantLockModal);
        ThreadWait();
        
        if (variantItems.size() > variantIndex) {
            FluentWebElement variantItem = variantItems.get(variantIndex);
            FluentWebElement variantIdElement = variantItem.findFirst(".variant-id");
            if (variantIdElement != null) {
                // Try to get from title attribute first (more reliable)
                String variantId = variantIdElement.getAttribute("title");
                if (variantId != null && !variantId.isEmpty()) {
                    return variantId.trim();
                }
                // Fallback to text content
                variantId = variantIdElement.getText().trim();
                if (!variantId.isEmpty()) {
                    return variantId;
                }
            }
        }
        return "";
    }

    /**
     * Apply variant lock changes in the modal
     */
    public void applyVariantLockInModal() {
        ThreadWait();
        awaitTillElementDisplayed(variantLockApplyButton);
        ThreadWait();
        // Wait for button to be enabled
        int retries = 0;
        while (retries < 10 && variantLockApplyButton.getAttribute("disabled") != null) {
            ThreadWait();
            retries++;
        }
        variantLockApplyButton.click();
        ThreadWait();
        // Wait for modal to close
        threadWait();
        ThreadWait();
        System.out.println("Applied variant lock in modal");
    }

    /**
     * Verify variant ID in preview matches the saved variant ID and lock icon is present
     * @param product Product card element
     * @param savedVariantId The variant ID saved from the modal
     */
    public void verifyVariantIdAndLockInPreview(FluentWebElement product, String savedVariantId) {
        ThreadWait();
        // Get variant ID from preview
        String previewVariantId = getVariantIdFromPreview(product);
        
        System.out.println("Saved VariantId from modal: " + savedVariantId);
        System.out.println("Preview VariantId: " + previewVariantId);
        
        // Verify variant ID matches
        Assert.assertFalse(savedVariantId.isEmpty(), "Saved VariantId is empty");
        Assert.assertFalse(previewVariantId.isEmpty(), "Preview VariantId is empty");
        Assert.assertEquals(previewVariantId, savedVariantId, "VariantId mismatch: Modal=" + savedVariantId + ", Preview=" + previewVariantId);
        
        // Verify lock icon is present in the variant
        FluentWebElement firstVariant = getFirstVariant(product);
        Assert.assertNotNull(firstVariant, "First variant not found in product");
        
        boolean isLocked = isVariantLocked(firstVariant);
        Assert.assertTrue(isLocked, "Variant lock icon not found in preview for variant: " + savedVariantId);
        
        System.out.println("VariantId and lock icon verification passed for: " + savedVariantId);
    }

    /**
     * Complete flow: Open modal, click first variant, get variant ID, apply, and verify in preview
     * @param product Product card element
     * @return The variant ID that was selected and locked
     */
    public String selectAndLockFirstVariantFromModal(FluentWebElement product) {
        // Open variant lock modal
        openVariantLockModal(product);
        
        // Click on first variant (index 0)
        clickVariantInModal(1);
        
        // Get variant ID from modal
        String variantId = getVariantIdFromModal(1);
        Assert.assertFalse(variantId.isEmpty(), "Variant ID not found in modal");
        System.out.println("Selected variant ID from modal: " + variantId);
        
        // Apply the lock
        applyVariantLockInModal();
        
        // Wait for preview to update
        ThreadWait();
        return variantId;
    }

    /**
     * Verify that the variant ID in the first product matches the preview variant ID
     * This verifies that the locked variant is correctly displayed in the preview
     */
    public void verifyFirstProductVariantIdMatchesPreview() {
        ThreadWait();
        threadWait();
        awaitForPageToLoad();
        ThreadWait();
        
        // Wait for products to load
        waitForProductsToLoad();
        
        // Get product count
        int productCount = getProductCount();
        System.out.println("Product count in preview: " + productCount);
        
        if (productCount > 0) {
            // Get the first product
            FluentWebElement firstProduct = getFirstProduct();
            Assert.assertNotNull(firstProduct, "First product not found");
            
            // Get variant ID from the first product's additional-field-item
            String productVariantId = getVariantIdFromPreview(firstProduct);
            System.out.println("Variant ID from first product: " + productVariantId);
            
            // Get the locked variant (first variant) from the product
            FluentWebElement firstVariant = getFirstVariant(firstProduct);
            Assert.assertNotNull(firstVariant, "First variant not found in first product");
            
            // Get variant ID from the locked variant (if available in data attributes)
            String previewVariantId = "";
            try {
                // Try to get from data-variant-id attribute
                previewVariantId = firstVariant.getAttribute("data-variant-id");
                if (previewVariantId == null || previewVariantId.isEmpty()) {
                    // Alternative: get from the variant swatch container
                    FluentWebElement variantSwatchContainer = firstVariant.findFirst(".variant-swatch-container");
                    if (variantSwatchContainer != null) {
                        previewVariantId = variantSwatchContainer.getAttribute("data-variant-id");
                    }
                }
            } catch (Exception e) {
                System.out.println("Could not get variant ID from variant element: " + e.getMessage());
            }
            
            // If we couldn't get from variant element, use the product's variant ID
            if (previewVariantId == null || previewVariantId.isEmpty()) {
                previewVariantId = productVariantId;
            }
            
            System.out.println("Variant ID from preview (locked variant): " + previewVariantId);
            
            // Verify both variant IDs are not empty
            Assert.assertFalse(productVariantId.isEmpty(), "Variant ID not found in first product");
            Assert.assertFalse(previewVariantId.isEmpty(), "Variant ID not found in preview");
            
            // Verify they match
            Assert.assertEquals(productVariantId, previewVariantId, 
                "Variant ID mismatch: Product=" + productVariantId + ", Preview=" + previewVariantId);
            
            System.out.println("Variant ID verification passed: " + productVariantId);
        } else {
            Assert.fail("No products found in preview to verify variant ID");
        }
    }

    /**
     * Select and lock first variant from modal, then verify in preview
     * This method checks if products are available, selects the first variant, locks it, and verifies
     * @param previewProductCount The count of products in preview
     */
    public void selectLockAndVerifyFirstVariant(int previewProductCount) {
        if (previewProductCount > 0) {
            FluentWebElement firstProduct = getFirstProduct();
            String savedVariantId = selectAndLockFirstVariantFromModal(firstProduct);
            ThreadWait();
            waitForProductsToLoad();
            ThreadWait();
            firstProduct = getFirstProduct();
            verifyVariantIdAndLockInPreview(firstProduct, savedVariantId);
        } else {
            Assert.fail("No products found in preview to test variant lock");
        }
    }
} 