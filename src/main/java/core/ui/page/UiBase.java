package core.ui.page;

import com.github.javafaker.Faker;
import com.google.common.base.Function;
import core.consoleui.page.CampaignCreationPage;
import core.consoleui.page.SegmentPage;
import lib.BrowserInitializer;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.action.FluentDefaultActions;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.lang.reflect.Method;

import java.net.URISyntaxException;
import java.util.ArrayList;

import static lib.constants.UnbxdConstants.SELENIUM_MAXTIMEOUT;
import static lib.constants.UnbxdConstants.SELENIUM_MINTIMEOUT;


public class UiBase extends FluentPage {
    @Page
    CampaignCreationPage campaignCreationPage;
    public static Logger APPLICATION_LOGS = Logger.getLogger("Manasa");

    private static final String CONFIG_FILE = "config.properties";

   // @FindBy(css = "div.alert.alert-success")
    @FindBy(css = ".unx-qa-toastsucess")
    public FluentWebElement successMessage;

    @FindBy(css = "div.alert-danger")
    public FluentWebElement alertErrorMessage;

    @FindBy(css = ".RCB-notif.RCB-notif-error")
    public FluentWebElement deleteErrorMessage;

    @FindBy(css = ".RCB-align-left .RCB-list-item.selected")
    public FluentList<FluentWebElement> highlightedSearchResults;

    @FindBy(css=".RCB-align-left .dm-dd-item")
    public FluentList<FluentWebElement> dropDownList;

    @FindBy(css = ".RCB-notif-error")
    public FluentWebElement alertMessage;

    @FindBy(css=".RCB-align-left .RCB-list-item")
    public FluentList<FluentWebElement> facetDropDownlist;


    public boolean checkSuccessMessage() {
        awaitForElementPresence(successMessage);
        return successMessage.isDisplayed();
    }

    public boolean checkAlertErrorMessage() {
        if (!awaitForElementPresence(alertErrorMessage))
            return false;
        return alertErrorMessage.isDisplayed();
    }

    public String getSsoIdCookie()
    {
        awaitForPageToLoad();
        getDriver().manage().getCookies();
        return getDriver().manage().getCookieNamed("_un_sso_uid").getValue();
    }

    public String getCsrfCookie()
    {
        awaitForPageToLoad();
        getDriver().manage().getCookies();
        return getDriver().manage().getCookieNamed("_un_csrf").getValue();
    }

    private static PropertiesConfiguration getPropertiesConfiguration() {
        PropertiesConfiguration config = null;
        try {
            config = new PropertiesConfiguration(loadAndGetResourceLocation(CONFIG_FILE));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return config;
    }

    public static int getMaxTimeout() {
        return SELENIUM_MAXTIMEOUT;
    }

    public static int getMinTimeout() {
        return SELENIUM_MINTIMEOUT;
    }

    /** Unwraps proxy so the concrete WebElement can be passed to JS or ExpectedConditions (avoids "illegal type: jdk.proxy2.$Proxy13"). */
    protected WebElement unwrapWebElement(WebElement element) {
        if (element == null) return null;
        Class<?> c = element.getClass();
        if (c.getSimpleName().startsWith("$")) {
            try {
                Method m = c.getMethod("getWrappedElement");
                Object w = m.invoke(element);
                if (w instanceof WebElement) return unwrapWebElement((WebElement) w);
            } catch (Exception ignored) { }
            for (Class<?> iface : c.getInterfaces()) {
                try {
                    Method m = iface.getMethod("getWrappedElement");
                    Object w = m.invoke(element);
                    if (w instanceof WebElement) return unwrapWebElement((WebElement) w);
                    break;
                } catch (Exception ignored) { }
            }
        }
        return element;
    }

    /** Returns a concrete WebElement for JS/ExpectedConditions; re-finds by CSS if unwrap still yields a proxy. */
    protected WebElement getConcreteWebElement(FluentWebElement element) {
        WebElement w = unwrapWebElement(element.getElement());
        if (w != null && w.getClass().getSimpleName().startsWith("$")) {
            try {
                String css = getCssLocatorForFluent(element);
                if (css != null && !css.isEmpty())
                    w = getDriver().findElement(By.cssSelector(css));
            } catch (Exception ignored) { }
        }
        return w;
    }

    private static String loadAndGetResourceLocation(String fileName) throws URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(fileName).toString();
    }

    public String getCssLocatorForFluent(FluentWebElement element) {
        if (element == null) return null;
        String val = element.getElement().toString();
        if (val == null) return null;
        int idx = val.toLowerCase().lastIndexOf("selector:");
        if (idx < 0) return null;
        String sel = val.substring(idx + 10).trim();
        if (sel.endsWith("]")) sel = sel.substring(0, sel.length() - 1).trim();
        return sel.isEmpty() ? null : sel;
    }

    public void selectByVisibleText(String text, FluentWebElement selectElement) {

        Select dropdown = new Select(selectElement.getElement());
        dropdown.selectByVisibleText(text);
    }

    public void selectByValue(String value, FluentWebElement selectElement) {
        Select dropdown = new Select(selectElement.getElement());
        dropdown.selectByValue(value);
    }

    public void selectDropDownValue(FluentList<FluentWebElement> dropDownList, String searchValue) {
        String searchLower = searchValue == null ? "" : searchValue.trim().toLowerCase();
        for (FluentWebElement value : dropDownList) {
            String optionText = value.getText().trim();
            if (!optionText.isEmpty() && optionText.toLowerCase().contains(searchLower)) {
                ThreadWait();
                scrollUntilVisible(value);
                ThreadWait();
                waitForElementToBeClickable(value, "Dropdown option");
                try {
                    value.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", unwrapWebElement(value.getElement()));
                }
                break;
            }
        }
    }

    /**
     * Select dropdown option by re-resolving the list before click to avoid stale element reference.
     */
    public void selectDropDownValueBySelector(String listCssSelector, String searchValue) {
        String searchLower = searchValue == null ? "" : searchValue.trim().toLowerCase();
        FluentList<FluentWebElement> list = find(listCssSelector);
        int matchIndex = findMatchIndex(list, listCssSelector, searchLower);
        if (matchIndex >= 0) {
            for (int clickAttempt = 0; clickAttempt < 3; clickAttempt++) {
                try {
                    list = find(listCssSelector);
                    int index = matchIndex >= list.size() ? findMatchIndex(list, listCssSelector, searchLower) : matchIndex;
                    if (index < 0) break;
                    matchIndex = index;
                    FluentWebElement value = list.get(matchIndex);
                    ThreadWait();
                    scrollUntilVisible(value);
                    ThreadWait();
                    waitForElementToBeClickable(value, "Dropdown option");
                    list = find(listCssSelector);
                    index = matchIndex >= list.size() ? findMatchIndex(list, listCssSelector, searchLower) : matchIndex;
                    if (index < 0) break;
                    value = list.get(index);
                    try {
                        value.click();
                    } catch (Exception e) {
                        clickUsingJS(value);
                    }
                    return;
                } catch (StaleElementReferenceException e) {
                    if (clickAttempt == 2) throw e;
                    ThreadWait();
                } catch (IndexOutOfBoundsException e) {
                    matchIndex = findMatchIndex(find(listCssSelector), listCssSelector, searchLower);
                    if (matchIndex < 0) throw e;
                    if (clickAttempt == 2) throw e;
                    ThreadWait();
                }
            }
        }
    }

    private int findMatchIndex(FluentList<FluentWebElement> list, String listCssSelector, String searchLower) {
        for (int i = 0; i < list.size(); i++) {
            try {
                String optionText = list.get(i).getText().trim();
                if (!optionText.isEmpty() && optionText.toLowerCase().contains(searchLower)) {
                    return i;
                }
            } catch (StaleElementReferenceException e) {
                list = find(listCssSelector);
                i--;
            }
        }
        return -1;
    }


    @Override
    @SuppressWarnings("rawtypes")
    public org.fluentlenium.core.Fluent click(org.fluentlenium.core.action.FluentDefaultActions fluentObject) {
        FluentWebElement element = (FluentWebElement) fluentObject;
        awaitForElementPresence(element);
        try {
            return super.click(fluentObject);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void selectHighlitedValueInDropDown() {
        Assert.assertTrue(highlightedSearchResults.size() > 0, "No Search Results are coming for the given Input");
        highlightedSearchResults.get(0).click();
    }

    public void selectDropDownByIndex(int i)
    {
        Assert.assertTrue(dropDownList.size()>0);
        dropDownList.get(i).click();

    }

    public void selectValueBYMatchingText(String value) throws InterruptedException {
        for (FluentWebElement e : find(".RCB-list-item")) {
            threadWait();
            if (e.getText().trim().equalsIgnoreCase(value)) {
               Thread.sleep(4000);
                e.click();
                break;
            }
        }
    }

    public String getRandomName() {
        Faker faker = new Faker();

        return faker.name().firstName();

    }
    

    public Boolean awaitForElementPresence(final FluentWebElement element) {

        Function<Fluent, FluentWebElement> function = new Function<Fluent, FluentWebElement>() {
            public FluentWebElement apply(Fluent fluent) {
                if (element.isDisplayed()) {
                    return element;
                }
                return null;
            }
        };
        try {
            await().atMost(getMaxTimeout()).until(function);
        } catch (WebDriverException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Boolean awaitForPageToLoad() {
        try {
            await().atMost(getMaxTimeout()).withMessage("Waiting for the Page Load is failed").untilPage().isLoaded();
            return true;
        } catch (Exception e) {
            System.out.println("Waiting for the Page Load is failed");
            return false;
        }
    }

    public void switchTabTo(int tabnumber)
    {
        ArrayList<String> tabs = new ArrayList<String> (getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(tabnumber));
    }

    public Boolean awaitForElementNotDisplayed(final FluentWebElement element) {
        Function<Fluent, FluentWebElement> isNotDisplayedFunction = new Function<Fluent, FluentWebElement>() {
            public FluentWebElement apply(Fluent fluent) {
                if (!element.isDisplayed()) {
                    return element;
                }
                return null;
            }
        };
        try {
            await().atMost(getMaxTimeout()).until(isNotDisplayedFunction);
            return true;
        } catch (WebDriverException e) {
            System.out.println("awaitForElementNotDisplayed is failed for element . Exception is : " + e.getMessage());
            return false;
        }
    }


    public void threadWait() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void threadWaitForBackendVerification() {
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public Boolean awaitTillElementDisplayed(FluentWebElement element) {
        int count = 0;
        while (count < 12) {
            try {
                if (!element.isDisplayed())
                    try {
                        threadWait();
                        count++;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;

                    }
                else
                    return true;
            } catch (WebDriverException e) {
                return false;
            }
        }
        System.out.println("awaitTillElementDisplayed is failed for the element ");
        return false;
    }


    public Boolean awaitTillElementHasText(FluentWebElement element, String text) {
        try {
            await().atMost(getMaxTimeout()).ignoring(RuntimeException.class).until(getCssLocatorForFluent(element)).containsText(text);
            return true;
        } catch (Exception e) {
            System.out.println("awaitTillElementHasText is failed for elment ");
            return false;
        }
    }

    /**
     * Waits up to timeoutSeconds for an element (found by locator) to contain the given substring in its text.
     * Re-resolves the element on each poll to avoid stale reference.
     */
    public boolean waitForElementTextToContain(By locator, String substring, int timeoutSeconds) {
        try {
            new WebDriverWait(getDriver(), timeoutSeconds).until((ExpectedCondition<Boolean>) d -> {
                try {
                    WebElement el = getDriver().findElement(locator);
                    return el != null && el.getText().contains(substring);
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean awaitTillElementHasText(String value, String text) {
        try {
            await().atMost(getMaxTimeout()).ignoring(RuntimeException.class).until(value).containsText(text);
            //await().atMost(getMaxTimeout()).ignoring(RuntimeException.class).until(getCssLocatorForFluent(element)).containsText(text);
            return true;
        } catch (Exception e) {
            System.out.println("awaitTillElementHasText is failed for elment ");
            return false;
        }
    }

    public boolean checkElementPresence(FluentWebElement element) {
        try {

            if (element.isDisplayed())
                return true;
            else
                return false;
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    public void unbxdInputBoxSearch(FluentWebElement element, String name) {
        try {
            //String campaignName = campaignCreationPage.fillCampaignData();
            awaitForElementPresence(element);
            threadWait();
            element.getElement().sendKeys(name);
            threadWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void handleAlert(String action) {

        org.openqa.selenium.Alert alert = getDriver().switchTo().alert();
        switch (action) {

            case "accept":
                alert.accept();
                break;
            case "dismiss":
                alert.dismiss();
                break;
            default:
                return;
        }


    }

    public void waitForLoaderToDisAppear(By locator, String name) {
        String result = null;
        WebDriverWait wait = new WebDriverWait(getDriver(), 300);
        // FluentWebElement element;
        try {
            APPLICATION_LOGS.debug("waiting for " + name + " to disappear");
            System.out.println("waiting for " + name + " to disappear");

            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));


            APPLICATION_LOGS.debug("waited for " + name + " to disappear");
            System.out.println("waited for" + name + " to disappear");

        } catch (Throwable ElementNotFoundException) {

            APPLICATION_LOGS
                    .debug("Exception came while waiting for " + name + " to load");
            System.err
                    .println("Exception came while waiting for " + name + " to load");


        }
    }

    public void waitForLoaderToDisAppear(By locator, String name, int numOfRetries, int interval) {


        //String result = null;
        WebDriverWait wait = new WebDriverWait(getDriver(), 180);
        // FluentWebElement element;
        try {
            APPLICATION_LOGS.debug("waiting for " + name + " to disappear");
            System.out.println("waiting for " + name + " to disappear");

            boolean result = wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            if (result) {
                return;
            }

            APPLICATION_LOGS.debug("waited for " + name + " to disappear");
            System.out.println("waited for" + name + " to disappear");

        } catch (Throwable ElementNotFoundException) {

            APPLICATION_LOGS
                    .debug("Exception came while waiting for " + name + " to load");
            System.err
                    .println("Exception came while waiting for " + name + " to load");


        }
    }

    public void waitForElementAppear(By locator, String name, int numOfRetries, int interval) {
        //String result = null;
        WebDriverWait wait = new WebDriverWait(getDriver(), interval);
        // FluentWebElement element;
        for (int i = 0; i < numOfRetries; i++) {
            try {
                APPLICATION_LOGS.debug("waiting for " + name + " to appear ");
                System.out.println("waiting for " + name + " to appear " + i + " round ");

                WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                if (result.isDisplayed()) {
                    return;
                }

                APPLICATION_LOGS.debug("waited for " + name + " to appear");
                System.out.println("waited for " + name + " to appear");


            } catch (Exception e) {

                APPLICATION_LOGS
                        .debug("Exception came while waiting for " + name + " to load");
                System.err
                        .println("Exception came while waiting for " + name + " to load");


            }
        }
    }

    public void waitForElementToAppear(By locator, String name) {
        //String result = null;
        WebDriverWait wait = new WebDriverWait(getDriver(), 180);
        // FluentWebElement element;
        try {
            APPLICATION_LOGS.debug("waiting for " + name + " to appear");
            System.out.println("waiting for " + name + " to appear");

            WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            if (result.isDisplayed()) {
                return;
            }

            APPLICATION_LOGS.debug("waited for " + name + " to appear");
            System.out.println("waited for" + name + " to appear");

        } catch (Throwable ElementNotFoundException) {

            APPLICATION_LOGS
                    .debug("Exception came while waiting for " + name + " to load");
            System.err
                    .println("Exception came while waiting for " + name + " to load");
        }
    }



    public static void awaitTillFrameToBeAvailable(FluentWebElement frame)
    {
        ExpectedConditions.frameToBeAvailableAndSwitchToIt((WebElement) frame);
    }

    public  void waitForElementToBeClickable(FluentWebElement locator, String elemName, int numOfRetries, int interval) {

        String result = null;
        WebDriverWait wait = new WebDriverWait(getDriver(), interval);
       // WebDriverWait wait = new WebDriverWait(getDriver(), 15);
        // FluentWebElement element;
        for (int i = 0; i < numOfRetries; i++) {
        try {
            APPLICATION_LOGS.debug("waiting for " + elemName + "to appear");
            System.out.println("waiting for " + elemName + "to appear");

                if (locator.isEnabled()) {
                    WebElement el = getConcreteWebElement(locator);
                    if (el == null) el = unwrapWebElement(locator.getElement());
                    if (el != null)
                        wait.until(ExpectedConditions.elementToBeClickable(el));
                }

            APPLICATION_LOGS.debug("waited for " + elemName + "to appear");
            System.out.println("waited for " + elemName + "to appear");

        } catch (Throwable ElementNotFoundException) {

            APPLICATION_LOGS
                    .debug("Exception came while waiting for element to load");
            System.err
                    .println("Exception came while waiting for element to load");

          }
        }
    }

    public  void waitForElementToBeClickable(FluentWebElement locator, String elemName) {

        String result = null;
         WebDriverWait wait = new WebDriverWait(getDriver(), 15);

            try {
                APPLICATION_LOGS.debug("waiting for " + elemName + "to appear");
                System.out.println("waiting for " + elemName + "to appear");

                if (locator.isEnabled()) {
                    WebElement el = getConcreteWebElement(locator);
                    if (el == null) el = unwrapWebElement(locator.getElement());
                    if (el != null)
                        wait.until(ExpectedConditions.elementToBeClickable(el));
                }

                APPLICATION_LOGS.debug("waited for " + elemName + "to appear");
                System.out.println("waited for " + elemName + "to appear");

            } catch (Throwable ElementNotFoundException) {

                APPLICATION_LOGS
                        .debug("Exception came while waiting for element to load");
                System.err
                        .println("Exception came while waiting for element to load");

            }
        }






    /**
     * public static ExpectedCondition<FluentWebElement>
     * visibilityOfElementLocated(final FluentWebElement locator) method specification :-
     *
     * 1) Waits for the web element to appear on the page 2) FluentWebElement
     * toReturn.isDisplayed() -> Returns true if displayed on the page, else
     * returns false
     *
     * @param : Locator to locate the web element
     * @return : ExpectedCondition about the web element
     */

    public static ExpectedCondition<FluentWebElement> visibilityOfElementLocated(final FluentWebElement locator) {

        return new ExpectedCondition<FluentWebElement>() {

            public FluentWebElement apply(WebDriver driver) {

                // Store the web element
                FluentWebElement toReturn = locator;

                // Check whether the web element is displayed on the page
                if (toReturn.isDisplayed() && toReturn.isEnabled())
                    return toReturn;

                return null;

            }

        };

    }

    /**
     * public static void waitForElementToLoad(FluentWebElement locator) method specification
     * :-
     *
     * 1) Waits for the web element to appear on the page 2) new
     * WebDriverWait(BrowserInitializer.getDriver(), 60) -> Waits for 60 seconds 3)
     * wait.until((ExpectedCondition<Boolean>) -> Wait until expected condition
     * (All documents present on the page get ready) met
     *
     * @param : no parameters passed
     * @return : void
     */

    public  void waitForElementToLoad(final FluentWebElement locator, String eleName) {

        APPLICATION_LOGS.debug("Waiting for "+eleName+" to load on the page");
        System.out.println("Waiting for "+eleName+" to load on the page");

        try {
            if(locator.isEnabled()){
                // Waits for 60 seconds
                Wait<WebDriver> wait = new WebDriverWait(getDriver(), 60);
                // Wait until the element is located on the page
                @SuppressWarnings("unused")

                FluentWebElement element = wait
                        .until(visibilityOfElementLocated(locator));
            }

            // Log result
            APPLICATION_LOGS
                    .debug("Waiting ends ... "+eleName +" loaded on the page");
            System.out
                    .println("Waiting ends ... "+eleName +" loaded on the page");

        }
        catch (StaleElementReferenceException staleException) {
            System.out.println("Caught Stale element exception for " +eleName + "+...Retrying...");
            Wait<WebDriver> wait = new WebDriverWait(getDriver(), 30);
            wait.until(visibilityOfElementLocated(locator));
        }
        catch (Throwable waitForElementException) {

            // Log error
            APPLICATION_LOGS
                    .debug("Error came while waiting for "+eleName + " to appear : "
                            + waitForElementException.getMessage());
            System.err
                    .println("Error came while waiting for " +eleName + " to appear : "
                            + waitForElementException.getMessage());

        }

    }

    /**
     * public static String retrieveText(FluentWebElement locator,String elemName) method
     * specification :-
     *
     * 1) Return retrieved text from webpage 2)
     * locator.getText() -> Retrieves text from the web
     * element targeted by specified locator
     *
     * @param : Locator for the web element, Name of the web element
     * @return : Text retrieved from the webpage
     */

    public  String retrieveText(FluentWebElement locator, String elemName) {

        String retrievedText = null;

        APPLICATION_LOGS.debug("Retrieving Text from : " + elemName);
        System.out.println("Retrieving Text from : " + elemName);

        try {
            // Wait for web element to load on the page
            waitForElementToBeClickable(locator, elemName);

            // Highlight the web element
            highlightElement(locator);

            if(locator.isEnabled() && locator.isDisplayed()){
                // Retrieve text from web element
                retrievedText = locator.getText().trim();
            }
            else {
                ThreadWait();
                retrievedText = locator.getText().trim();
            }

            // Log result
            APPLICATION_LOGS.debug("Retrieved text : " + retrievedText);
            System.out.println("Retrieved text : " + retrievedText);

        }
        catch (StaleElementReferenceException getTextStaleException) {

            System.err.println("Stale Element Exception is handled for '" + elemName
                    + "' : " + getTextStaleException.getMessage());
            ThreadWait();

            // Wait for link to appear on the page
            waitForElementToLoad(locator, elemName);

            // Click on the link
            retrievedText = locator.getText().trim();

            // Log result
            System.out.println("Retrying...Clicked on : " + elemName);
            APPLICATION_LOGS.debug("Retrying...Clicked on : " + elemName);

        }
        catch (Throwable retrieveTextException) {

            // Log error
            System.err.println("Error while Getting Text from '" + elemName
                    + "' : " + retrieveTextException.getMessage());
            APPLICATION_LOGS.debug("Error while Getting Text from '" + elemName
                    + "' : " + retrieveTextException.getMessage());

        }
        return retrievedText;
    }

    public static void ThreadWait() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * public static void highlightElement(WebDriver BrowserInitializer.getDriver(), By Locator) method
     * specification
     *
     * This method is used to highlight the element of choice
     *
     * @param : BrowserInitializer.getDriver()
     * @param : (Locator) locator of the element to highlight
     */
    public  void highlightElement(FluentWebElement Locator) {

        try {

            for (int i = 0; i < 3; i++) {
                JavascriptExecutor js = (JavascriptExecutor) getDriver();
                // js.executeScript("arguments[0].setAttribute('style', arguments[1]);",BrowserInitializer.getDriver().findElement(Locator),
                // "color: red; border: 2px solid red;");
                js.executeScript(
                        "arguments[0].setAttribute('style', arguments[1]);",
                        unwrapWebElement(Locator.getElement()),
                        "background-color: yellow; outline: 1px solid rgb(136, 255, 136);");

            }
        }
        catch (Throwable t) {
            System.out.println("Error came : " + t.getMessage());
            APPLICATION_LOGS.debug("Error came : " + t.getMessage());
        }

    }

    public void scrollUntilVisible(org.fluentlenium.core.domain.FluentWebElement element) {
        try {
            WebElement webElement = getConcreteWebElement(element);
            if (webElement == null) webElement = unwrapWebElement(element.getElement());
            JavascriptExecutor js = (JavascriptExecutor) getDriver();

            // Check if element is already visible
            if (isElementInViewport(element)) {
                System.out.println("Element is already visible");
                return;
            }

            // Scroll to element - center it in viewport
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'});", webElement);
            threadWait();

            // Verify it's now visible
            if (isElementInViewport(element)) {
                System.out.println("Element scrolled into view successfully");
            } else {
                System.out.println("Warning: Element may not be fully visible");
            }

        } catch (Exception e) {
            System.out.println("Error scrolling to element: " + e.getMessage());
        }
    }

    /**
     * Checks if element is visible in the current viewport
     * @param element FluentWebElement to check
     * @return true if visible, false otherwise
     */
    public boolean isElementInViewport(org.fluentlenium.core.domain.FluentWebElement element) {
        try {
            WebElement webElement = getConcreteWebElement(element);
            if (webElement == null) webElement = unwrapWebElement(element.getElement());
            JavascriptExecutor js = (JavascriptExecutor) getDriver();

            // Check if element is displayed first
            if (!webElement.isDisplayed()) {
                return false;
            }

            // Check if element is in viewport using JavaScript
            Boolean isVisible = (Boolean) js.executeScript(
                    "var rect = arguments[0].getBoundingClientRect();" +
                            "return (" +
                            "    rect.top >= 0 &&" +
                            "    rect.left >= 0 &&" +
                            "    rect.bottom <= window.innerHeight &&" +
                            "    rect.right <= window.innerWidth" +
                            ");",
                    webElement
            );

            return isVisible;

        } catch (Exception e) {
            System.out.println("Error checking element visibility: " + e.getMessage());
            return false;
        }
    }


    public void scrollToElement(FluentWebElement locator, String name) {

        APPLICATION_LOGS.debug("Scrolling to "+name);
        System.out.println("Scrolling to "+name);

        // Initialize Javascript executor
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        try {

            js.executeScript("arguments[0].scrollIntoView(true);", unwrapWebElement(locator.getElement()));
            System.out.println("Scrolled to "+name);
            APPLICATION_LOGS.debug("Scrolled to "+name);

        }

        catch (Throwable scrollException) {

            // Log error
            System.err.println("Error while scrolling "+name);
            APPLICATION_LOGS.debug("Error while scrolling "+name);

        }
    }

    public void scrollToBottom() {
        ThreadWait();
        APPLICATION_LOGS.debug("Scrolling to bottom of page");
        System.out.println("Scrolling to bottom of page");

        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            long lastHeight = (long) js.executeScript("return document.body.scrollHeight");

            while (true) {
                js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                try {
                    // A short pause to allow content to load.
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                long newHeight = (long) js.executeScript("return document.body.scrollHeight");
                if (newHeight == lastHeight) {
                    break;
                }
                lastHeight = newHeight;
            }

            // Post-scroll guards: allow overlays/loaders to clear before next click
            try {
                waitForLoaderToDisAppear(By.cssSelector(".mask-loader"), "global loader", 1, 5);
            } catch (Throwable t) {
                // ignore optional guard failures
            }

            int guard = 0;
            while (checkModalWindow() && guard < 3) {
                ThreadWait();
                guard++;
            }

            System.out.println("Scrolled to bottom of page");
            APPLICATION_LOGS.debug("Scrolled to bottom of page");

        }

        catch (Throwable scrollException) {

            // Log error
            System.err.println("Error while scrolling down to bottom of the page: " + scrollException.getMessage());
            APPLICATION_LOGS.error("Error while scrolling down to bottom of the page", scrollException);

        }
    }

    public void scrollDown() {
        APPLICATION_LOGS.debug("Scrolling down one page");
        System.out.println("Scrolling down one page");
        try {
            ((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, window.innerHeight);");
            APPLICATION_LOGS.debug("Scrolled down one page");
            System.out.println("Scrolled down one page");
        } catch (Throwable scrollException) {
            System.err.println("Error while scrolling down: " + scrollException.getMessage());
            APPLICATION_LOGS.error("Error while scrolling down", scrollException);
        }
    }

    public void clickUsingJS(FluentWebElement fluentWebElement) {
        JavascriptExecutor ex = (JavascriptExecutor) getDriver();
        try {
            ex.executeScript("arguments[0].click();", unwrapWebElement(fluentWebElement.getElement()));
        } catch (Exception e) {
            try {
                String css = getCssLocatorForFluent(fluentWebElement);
                WebElement concrete = getDriver().findElement(By.cssSelector(css));
                ex.executeScript("arguments[0].click();", concrete);
            } catch (Exception inner) {
                throw e;
            }
        }
    }

    public void scrollToTop() {

        APPLICATION_LOGS.debug("Scrolling to top of page");
        System.out.println("Scrolling to top of page");

        try {
            ((JavascriptExecutor) getDriver())
                    .executeScript("window.scrollTo(0, 0)");

            System.out.println("Scrolled to top of page");
            APPLICATION_LOGS.debug("Scrolled to top of page");

        }

        catch (Throwable scrollException) {

            // Log error
            System.err.println("Error while scrolling to top of the page");
            APPLICATION_LOGS.debug("Error while scrolling to top of the page");

        }
    }

    public boolean checkModalWindow()
    {
        return findFirst("body").getAttribute("class").contains("modal-open");
    }

    /**
     * Wait for an element to disappear from the page
     * @param element The element to wait for its disappearance
     */
    public void waitForElementToDisappear(FluentWebElement element) throws InterruptedException {
        try {
            System.out.println("Waiting for element to disappear");
            WebDriverWait wait = new WebDriverWait(getDriver(), getMaxTimeout());
            
            // Use the getCssLocatorForFluent method to get a reliable CSS selector
            String cssSelector = getCssLocatorForFluent(element);
            
            // Wait for the element to become invisible
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(cssSelector)));
            
            ThreadWait(); // Additional small wait to ensure UI has updated
            System.out.println("Element disappeared successfully");
        } catch (Exception e) {
            System.err.println("Error waiting for element to disappear: " + e.getMessage());
           ThreadWait();
        }
    }

    /**
     * Safely clicks on an element by ensuring it is visible in the viewport,
     * waiting for it to be clickable, and falling back to a JavaScript click
     * if a standard click is intercepted.
     */
    public void safeClick(org.fluentlenium.core.domain.FluentWebElement element) {
        final FluentWebElement target = element;
        try {
            awaitTillElementDisplayed(target);
            scrollUntilVisible(target);
            waitForElementToBeClickable(target, "element");
            target.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            try {
                WebElement el = getConcreteWebElement(target);
                if (el == null) el = unwrapWebElement(target.getElement());
                if (el != null) {
                    ((JavascriptExecutor) getDriver())
                            .executeScript("arguments[0].scrollIntoView({behavior:'instant',block:'center',inline:'center'});", el);
                    ThreadWait();
                    ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", el);
                }
            } catch (Exception ex) {
                System.err.println("safeClick JS fallback failed: " + ex.getMessage());
                throw ex;
            }
        } catch (WebDriverException e) {
            try {
                WebElement el = getConcreteWebElement(target);
                if (el == null) el = unwrapWebElement(target.getElement());
                if (el != null)
                    ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", el);
            } catch (Exception ex) {
                System.err.println("safeClick fallback failed: " + ex.getMessage());
                throw ex;
            }
        } finally {
            ThreadWait();
        }
    }

    private void robustClick(org.fluentlenium.core.domain.FluentWebElement element) {
        try {
            ThreadWait();
            element.click();
        } catch (Exception e) {
            WebElement el = getConcreteWebElement(element);
            if (el == null) el = unwrapWebElement(element.getElement());
            if (el != null)
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", el);
        }
    }
}
