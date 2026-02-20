package lib;

import core.ui.actions.LoginActions;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GlobalLoginManager {
    
    private static volatile boolean isLoggedIn = false;
    private static volatile LoginActions loginActions;
    
    public static void initialize(WebDriver driver) {
    }
    
    public static void setLoginActions(LoginActions actions) {
        loginActions = actions;
    }
    
    public static void performGlobalLogin(WebDriver driver, int siteId, int userId) {
        initialize(driver);
        
        if (isLoggedIn) {
            System.out.println("Already logged in globally, skipping login");
            return;
        }
        
        System.out.println("Performing global login for site " + siteId + ", user " + userId);
        
        try {
            loginActions.login(siteId, userId);
            GlobalCookieManager.storeCookies(driver);
            isLoggedIn = true;
            System.out.println("Global login completed successfully");
        } catch (Exception e) {
            System.err.println("Global login failed: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Try to reuse stored cookies for authentication.
     * Uses the passed driver directly for navigation to avoid race conditions
     * when multiple tests run in parallel and share the static loginActions.
     */
    public static boolean tryCookieReuse(WebDriver driver) {
        try {
            System.out.println("Attempting to reuse global cookies...");
            
            if (!GlobalCookieManager.hasStoredCookies()) {
                System.out.println("No global cookies available for reuse");
                return false;
            }
            
            System.out.println("Found " + GlobalCookieManager.getStoredCookieCount() + " global cookies");
            
            String loginUrl = EnvironmentConfig.getLoginUrl();
            if (loginUrl == null || loginUrl.isEmpty()) {
                loginUrl = EnvironmentConfig.getApplicationUrl();
            }
            
            driver.get(loginUrl);
            waitForPageLoad(driver, 5);
            
            if (!GlobalCookieManager.reuseCookies(driver)) {
                System.out.println("Failed to reuse global cookies");
                return false;
            }
            
            driver.get(loginUrl);
            waitForPageLoad(driver, 5);
            
            if (GlobalCookieManager.isSessionValid(driver)) {
                System.out.println("Session validation successful - essential cookies found");
                
                String currentUrl = driver.getCurrentUrl();
                if (currentUrl != null && !currentUrl.contains("/login")) {
                    System.out.println("Session validation successful - not on login page");
                    return true;
                } else {
                    System.out.println("Still on login page after cookie reuse, session may have expired");
                }
            } else {
                System.out.println("Session validation failed - essential cookies not found");
            }
            
            return false;
            
        } catch (Exception e) {
            System.err.println("Error during cookie reuse: " + e.getMessage());
            return false;
        }
    }
    
    private static void waitForPageLoad(WebDriver driver, int timeoutSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(d -> ((JavascriptExecutor) d)
                    .executeScript("return document.readyState").equals("complete"));
        } catch (Exception ignored) {
        }
    }
    
    public static void smartLogin(WebDriver driver, int siteId, int userId) {
        initialize(driver);
        
        System.out.println("Attempting cookie reuse first...");
        if (tryCookieReuse(driver)) {
            System.out.println("Login successful using cookie reuse");
            return;
        }
        
        System.out.println("Cookie reuse failed, performing global login");
        performGlobalLogin(driver, siteId, userId);
    }
    
    public static void reset() {
        isLoggedIn = false;
        GlobalCookieManager.clearAllCookies();
        System.out.println("Global login state reset");
    }
    
    public static void resetLoginStateOnly() {
        isLoggedIn = false;
        System.out.println("Global login state reset (cookies preserved)");
    }
    
    public static boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    public static String getLoginStats() {
        return String.format("Global Login Stats - Logged In: %s, Stored Cookies: %d", 
                           isLoggedIn, GlobalCookieManager.getStoredCookieCount());
    }
} 