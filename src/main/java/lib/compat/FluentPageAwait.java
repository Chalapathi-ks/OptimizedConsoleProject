package lib.compat;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FluentPageAwait {
    private final WebDriver driver;
    private final long timeoutSeconds;

    public FluentPageAwait(WebDriver driver, long timeoutSeconds) {
        this.driver = driver;
        this.timeoutSeconds = timeoutSeconds;
    }

    public void isLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }
}
