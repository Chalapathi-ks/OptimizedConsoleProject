package lib.compat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom FieldDecorator that creates FluentWebElement / FluentList wrappers
 * for fields annotated with @FindBy, matching FluentLenium's behavior.
 */
public class FluentFieldDecorator extends DefaultFieldDecorator {
    private final WebDriver driver;

    public FluentFieldDecorator(ElementLocatorFactory factory, WebDriver driver) {
        super(factory);
        this.driver = driver;
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        if (FluentWebElement.class.isAssignableFrom(field.getType())) {
            ElementLocator locator = factory.createLocator(field);
            if (locator == null) return null;
            return new LazyFluentWebElement(locator, driver);
        }

        if (FluentList.class.isAssignableFrom(field.getType())) {
            ElementLocator locator = factory.createLocator(field);
            if (locator == null) return null;
            return new LazyFluentList(locator, driver);
        }

        return super.decorate(loader, field);
    }

    /**
     * Lazy FluentWebElement that defers element lookup until first interaction.
     */
    private static class LazyFluentWebElement extends FluentWebElement {
        private final ElementLocator locator;
        private final WebDriver driver;

        LazyFluentWebElement(ElementLocator locator, WebDriver driver) {
            super(null, driver);
            this.locator = locator;
            this.driver = driver;
        }

        private WebElement resolve() {
            return locator.findElement();
        }

        @Override public WebElement getElement() { return resolve(); }
        @Override public FluentWebElement click() { resolve().click(); return this; }
        @Override public String getText() { return resolve().getText(); }
        @Override public String getValue() { return resolve().getAttribute("value"); }
        @Override public String getTextContent() { return resolve().getAttribute("textContent"); }
        @Override public String getAttribute(String name) { return resolve().getAttribute(name); }
        @Override public boolean isDisplayed() {
            try { return resolve().isDisplayed(); } catch (Exception e) { return false; }
        }
        @Override public boolean isEnabled() { return resolve().isEnabled(); }
        @Override public boolean isSelected() { return resolve().isSelected(); }
        @Override public FluentFill fill() { return new FluentFill(resolve()); }
        @Override public void submit() { resolve().submit(); }
        @Override public void clear() { resolve().clear(); }
        @Override public FluentList<FluentWebElement> find(String css) {
            List<WebElement> children = resolve().findElements(org.openqa.selenium.By.cssSelector(css));
            List<FluentWebElement> wrapped = children.stream()
                    .map(e -> new FluentWebElement(e, driver))
                    .collect(Collectors.toList());
            return new FluentList<>(wrapped);
        }
        @Override public FluentWebElement findFirst(String css) {
            WebElement child = resolve().findElement(org.openqa.selenium.By.cssSelector(css));
            return new FluentWebElement(child, driver);
        }
        @Override public String toString() { return resolve().toString(); }
    }

    /**
     * Lazy FluentList that defers element lookup until first interaction.
     */
    private static class LazyFluentList extends FluentList<FluentWebElement> {
        private final ElementLocator locator;
        private final WebDriver driver;

        LazyFluentList(ElementLocator locator, WebDriver driver) {
            super(java.util.Collections.emptyList());
            this.locator = locator;
            this.driver = driver;
        }

        private List<FluentWebElement> resolve() {
            return locator.findElements().stream()
                    .map(e -> new FluentWebElement(e, driver))
                    .collect(Collectors.toList());
        }

        @Override public FluentWebElement get(int index) { return resolve().get(index); }
        @Override public int size() { return resolve().size(); }
        @Override public java.util.Iterator<FluentWebElement> iterator() { return resolve().iterator(); }
        @Override public FluentWebElement first() {
            List<FluentWebElement> r = resolve();
            return r.isEmpty() ? null : r.get(0);
        }
        @Override public boolean isEmpty() { return resolve().isEmpty(); }
        @Override public String getText() { return resolve().isEmpty() ? "" : resolve().get(0).getText(); }
        @Override public String getValue() { return resolve().isEmpty() ? "" : resolve().get(0).getValue(); }
        @Override public boolean isDisplayed() { return !resolve().isEmpty() && resolve().get(0).isDisplayed(); }
        @Override public FluentWebElement click() { if (!resolve().isEmpty()) resolve().get(0).click(); return resolve().isEmpty() ? null : resolve().get(0); }
    }
}
