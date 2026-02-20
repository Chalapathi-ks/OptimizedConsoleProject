package lib.compat;

import com.google.common.base.Function;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FluentAwait {
    private final WebDriver driver;
    private final Fluent fluentContext;
    private long timeoutSeconds = 30;
    private String message;
    private Class<? extends Throwable> ignoredException;

    public FluentAwait(WebDriver driver, Fluent fluentContext) {
        this.driver = driver;
        this.fluentContext = fluentContext;
    }

    public FluentAwait atMost(long timeoutMillisOrSeconds) {
        if (timeoutMillisOrSeconds > 1000) {
            this.timeoutSeconds = timeoutMillisOrSeconds / 1000;
        } else {
            this.timeoutSeconds = timeoutMillisOrSeconds;
        }
        return this;
    }

    public FluentAwait withMessage(String message) {
        this.message = message;
        return this;
    }

    public FluentAwait ignoring(Class<? extends Throwable> exceptionType) {
        this.ignoredException = exceptionType;
        return this;
    }

    /**
     * Function-based await: evaluates the function passing the Fluent context.
     * Returns the non-null result or throws TimeoutException.
     */
    public FluentWebElement until(Function<Fluent, FluentWebElement> function) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        if (message != null) {
            wait.withMessage(message);
        }
        if (ignoredException != null) {
            wait.ignoring(ignoredException);
        }
        return wait.until(d -> function.apply(fluentContext));
    }

    /**
     * CSS-selector-based await: returns a FluentCssAwait for chaining .containsText() etc.
     */
    public FluentCssAwait until(String cssSelector) {
        return new FluentCssAwait(driver, cssSelector, timeoutSeconds, ignoredException);
    }

    /**
     * Page-load await: returns a FluentPageAwait for chaining .isLoaded().
     */
    public FluentPageAwait untilPage() {
        return new FluentPageAwait(driver, timeoutSeconds);
    }
}
