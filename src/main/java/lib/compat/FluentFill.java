package lib.compat;

import org.openqa.selenium.WebElement;

public class FluentFill {
    private final WebElement element;

    public FluentFill(WebElement element) {
        this.element = element;
    }

    public void with(String text) {
        element.clear();
        element.sendKeys(text);
    }
}
