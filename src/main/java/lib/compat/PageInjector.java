package lib.compat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.lang.reflect.Field;

/**
 * Handles @Page annotation injection, replacing FluentLenium's initTest().
 * Matches FluentLenium 0.10.9 behavior: creates page instances, sets driver,
 * and initializes @FindBy fields - but does NOT recursively inject @Page on children.
 */
public class PageInjector {

    /**
     * Process all @Page-annotated fields on the target object.
     * Creates instances, sets the driver, and initializes @FindBy fields.
     */
    public static void initPages(Object target, WebDriver driver) {
        Class<?> clazz = target.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Page.class)) {
                    field.setAccessible(true);
                    try {
                        Object page = field.getType().getDeclaredConstructor().newInstance();

                        if (page instanceof PageBase) {
                            ((PageBase) page).setDriverInternal(driver);
                        }

                        initFindByFields(page, driver);
                        field.set(target, page);
                    } catch (Exception e) {
                        throw new RuntimeException(
                                "Failed to inject @Page field: " + field.getName() +
                                " of type " + field.getType().getName(), e);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }

        initFindByFields(target, driver);
    }

    /**
     * Initialize @FindBy-annotated FluentWebElement / FluentList fields
     * using Selenium's PageFactory with our custom FluentFieldDecorator.
     */
    public static void initFindByFields(Object page, WebDriver driver) {
        PageFactory.initElements(
                new FluentFieldDecorator(
                        new DefaultElementLocatorFactory(driver), driver),
                page);
    }
}
