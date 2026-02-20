package lib.compat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.stream.Collectors;

public class FluentWebElement implements FluentDefaultActions {
    private final WebElement element;
    private WebDriver driver;

    public FluentWebElement(WebElement element) {
        this.element = element;
    }

    public FluentWebElement(WebElement element, WebDriver driver) {
        this.element = element;
        this.driver = driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getElement() {
        return element;
    }

    public FluentWebElement click() {
        element.click();
        return this;
    }

    public void doubleClick() {
        if (driver == null) {
            throw new UnsupportedOperationException("No driver available for doubleClick()");
        }
        new Actions(driver).doubleClick(element).perform();
    }

    public String getText() {
        return element.getText();
    }

    public String getTextContent() {
        return element.getAttribute("textContent");
    }

    public String getValue() {
        return element.getAttribute("value");
    }

    public String getAttribute(String name) {
        return element.getAttribute(name);
    }

    public boolean isDisplayed() {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEnabled() {
        return element.isEnabled();
    }

    public boolean isSelected() {
        return element.isSelected();
    }

    public FluentFill fill() {
        return new FluentFill(element);
    }

    public void submit() {
        element.submit();
    }

    public void clear() {
        element.clear();
    }

    /**
     * Find child elements matching a CSS selector (returns FluentList like FluentLenium).
     */
    public FluentList<FluentWebElement> find(String cssSelector) {
        List<WebElement> children = element.findElements(By.cssSelector(cssSelector));
        List<FluentWebElement> wrapped = children.stream()
                .map(e -> new FluentWebElement(e, driver))
                .collect(Collectors.toList());
        return new FluentList<>(wrapped);
    }

    /**
     * Find the first child element matching a CSS selector.
     */
    public FluentWebElement findFirst(String cssSelector) {
        WebElement child = element.findElement(By.cssSelector(cssSelector));
        return new FluentWebElement(child, driver);
    }

    /**
     * No-arg findFirst: returns the first child element.
     */
    public FluentWebElement findFirst() {
        List<WebElement> children = element.findElements(By.cssSelector("*"));
        if (children.isEmpty()) {
            return this;
        }
        return new FluentWebElement(children.get(0), driver);
    }

    public boolean isEmpty() {
        return false;
    }

    @Override
    public String toString() {
        return element.toString();
    }
}
