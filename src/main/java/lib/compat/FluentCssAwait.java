package lib.compat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FluentCssAwait {
    private final WebDriver driver;
    private final String cssSelector;
    private final long timeoutSeconds;
    private final Class<? extends Throwable> ignoredException;

    public FluentCssAwait(WebDriver driver, String cssSelector, long timeoutSeconds,
                          Class<? extends Throwable> ignoredException) {
        this.driver = driver;
        this.cssSelector = cssSelector;
        this.timeoutSeconds = timeoutSeconds;
        this.ignoredException = ignoredException;
    }

    public void containsText(String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        if (ignoredException != null) {
            wait.ignoring(ignoredException);
        }
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector(cssSelector), text));
    }

    public void isDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        if (ignoredException != null) {
            wait.ignoring(ignoredException);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
    }
}
