package lib.compat;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;

/**
 * Drop-in replacement for org.fluentlenium.core.domain.FluentList.
 * Delegates convenience methods to the first element, matching FluentLenium's behavior.
 */
public class FluentList<E extends FluentWebElement> extends AbstractList<E> implements FluentDefaultActions {
    private final List<E> elements;

    public FluentList(List<E> elements) {
        this.elements = elements;
    }

    @Override
    public E get(int index) {
        return elements.get(index);
    }

    @Override
    public int size() {
        return elements.size();
    }

    public E first() {
        if (elements.isEmpty()) {
            return null;
        }
        return elements.get(0);
    }

    @Override
    public Iterator<E> iterator() {
        return elements.iterator();
    }

    public String getText() {
        if (elements.isEmpty()) return "";
        return elements.get(0).getText();
    }

    public String getTextContent() {
        if (elements.isEmpty()) return "";
        return elements.get(0).getTextContent();
    }

    public String getValue() {
        if (elements.isEmpty()) return "";
        return elements.get(0).getValue();
    }

    public String getAttribute(String name) {
        if (elements.isEmpty()) return "";
        return elements.get(0).getAttribute(name);
    }

    public boolean isDisplayed() {
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }

    public FluentWebElement click() {
        if (!elements.isEmpty()) {
            return elements.get(0).click();
        }
        return null;
    }

    public FluentFill fill() {
        if (elements.isEmpty()) {
            throw new IllegalStateException("Cannot fill() on empty FluentList");
        }
        return elements.get(0).fill();
    }

    public E findFirst(String cssSelector) {
        if (elements.isEmpty()) {
            return null;
        }
        return (E) elements.get(0).findFirst(cssSelector);
    }
}
