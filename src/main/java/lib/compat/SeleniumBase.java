package lib.compat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Drop-in replacement for org.fluentlenium.core.FluentAdapter.
 * Test base classes (BaseTest) extend this instead of FluentAdapter.
 */
public class SeleniumBase implements Fluent {
    private WebDriver driver;

    public void initFluent(WebDriver driver) {
        this.driver = driver;
    }

    public void initTest() {
        PageInjector.initPages(this, driver);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public FluentList<FluentWebElement> find(String cssSelector) {
        List<WebElement> elements = driver.findElements(By.cssSelector(cssSelector));
        List<FluentWebElement> wrapped = elements.stream()
                .map(e -> new FluentWebElement(e, driver))
                .collect(Collectors.toList());
        return new FluentList<>(wrapped);
    }

    public FluentList<FluentWebElement> $(String cssSelector) {
        return find(cssSelector);
    }

    public FluentWebElement findFirst(String cssSelector) {
        WebElement element = driver.findElement(By.cssSelector(cssSelector));
        return new FluentWebElement(element, driver);
    }

    public void goTo(String url) {
        driver.get(url);
    }

    public void goTo(PageBase page) {
        String url = page.getUrl();
        if (url != null && !url.isEmpty()) {
            driver.get(url);
        }
    }

    public FluentAwait await() {
        return new FluentAwait(driver, this);
    }

    public Fluent click(FluentDefaultActions fluentObject) {
        if (fluentObject instanceof FluentWebElement) {
            ((FluentWebElement) fluentObject).click();
        } else if (fluentObject instanceof FluentList) {
            FluentList<?> list = (FluentList<?>) fluentObject;
            if (!list.isEmpty()) {
                list.get(0).click();
            }
        }
        return this;
    }

    public Fluent click(String cssSelector) {
        driver.findElement(By.cssSelector(cssSelector)).click();
        return this;
    }

    public FluentFill fill(FluentDefaultActions fluentObject) {
        if (fluentObject instanceof FluentWebElement) {
            return ((FluentWebElement) fluentObject).fill();
        }
        throw new IllegalArgumentException("Cannot fill non-FluentWebElement");
    }

    public FluentFill fill(String cssSelector) {
        WebElement element = driver.findElement(By.cssSelector(cssSelector));
        return new FluentFill(element);
    }

    public Fluent clear() {
        return this;
    }

    public WebDriver newWebDriver() {
        return null;
    }
}
