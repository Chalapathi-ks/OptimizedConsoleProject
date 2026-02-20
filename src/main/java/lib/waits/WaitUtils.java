package lib.waits;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class WaitUtils {

    private static final Logger log = LoggerFactory.getLogger(WaitUtils.class);
    private static final int DEFAULT_TIMEOUT = 15;
    private static final int SHORT_TIMEOUT = 5;
    private static final int LONG_TIMEOUT = 45;

    public static WebElement waitForElement(WebDriver driver, By locator) {
        return waitForElement(driver, locator, DEFAULT_TIMEOUT);
    }

    public static WebElement waitForElement(WebDriver driver, By locator, int timeoutSeconds) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            log.warn("Timeout after {}s waiting for element: {}", timeoutSeconds, locator);
            throw e;
        }
    }

    public static WebElement waitForElementClickable(WebDriver driver, By locator) {
        return waitForElementClickable(driver, locator, DEFAULT_TIMEOUT);
    }

    public static WebElement waitForElementClickable(WebDriver driver, By locator, int timeoutSeconds) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            log.warn("Timeout after {}s waiting for clickable: {}", timeoutSeconds, locator);
            throw e;
        }
    }

    public static boolean waitForElementToDisappear(WebDriver driver, By locator) {
        return waitForElementToDisappear(driver, locator, DEFAULT_TIMEOUT);
    }

    public static boolean waitForElementToDisappear(WebDriver driver, By locator, int timeoutSeconds) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            log.warn("Timeout after {}s waiting for element to disappear: {}", timeoutSeconds, locator);
            throw e;
        }
    }

    public static void waitForOverlayToDisappear(WebDriver driver, By overlayLocator) {
        waitForOverlayToDisappear(driver, overlayLocator, LONG_TIMEOUT);
    }

    public static void waitForOverlayToDisappear(WebDriver driver, By overlayLocator, int timeoutSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));
        } catch (TimeoutException e) {
            log.warn("Overlay did not disappear within {}s", timeoutSeconds);
        }
    }

    public static void waitForPageLoad(WebDriver driver) {
        waitForPageLoad(driver, DEFAULT_TIMEOUT);
    }

    public static void waitForPageLoad(WebDriver driver, int timeoutSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(d -> ((JavascriptExecutor) d)
                            .executeScript("return document.readyState").equals("complete"));
        } catch (TimeoutException e) {
            log.warn("Page did not reach 'complete' state within {}s", timeoutSeconds);
        }
    }

    public static void waitForSuccessToast(WebDriver driver, By toastLocator) {
        waitForSuccessToast(driver, toastLocator, DEFAULT_TIMEOUT);
    }

    public static void waitForSuccessToast(WebDriver driver, By toastLocator, int timeoutSeconds) {
        try {
            WebElement toast = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(toastLocator));
            if (toast.isDisplayed()) {
                new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                        .until(ExpectedConditions.invisibilityOf(toast));
            }
        } catch (TimeoutException e) {
            log.warn("Toast did not appear/disappear within {}s", timeoutSeconds);
        }
    }

    /**
     * Use only when no condition-based wait is possible. Prefer waitForElement/waitForOverlay instead.
     */
    public static void safeWait(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Wait interrupted");
        }
    }
}
