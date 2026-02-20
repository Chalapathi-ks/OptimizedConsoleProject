package lib.compat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.lang.reflect.Field;

/**
 * Handles @Page annotation injection, replacing FluentLenium's initTest().
 * Injects @Page fields up to 2 levels deep to support nested page objects
 * (e.g. MerchandisingActions containing @Page CommercePageActions).
 */
public class PageInjector {

    public static void initPages(Object target, WebDriver driver) {
        injectPages(target, driver, 0, 2);
        initFindByFields(target, driver);
    }

    private static void injectPages(Object target, WebDriver driver, int depth, int maxDepth) {
        if (depth >= maxDepth) return;

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
                        injectPages(page, driver, depth + 1, maxDepth);
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
