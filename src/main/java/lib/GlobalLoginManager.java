package lib;

import core.ui.actions.LoginActions;
import org.openqa.selenium.WebDriver;

/**
 * Global Login Manager for handling login once and reusing cookies across test classes
 * Ensures login happens only once per test suite and subsequent classes reuse cookies
 */
public class GlobalLoginManager {
    
    private static boolean isLoggedIn = false;
    private static LoginActions loginActions;
    
    /**
     * Initialize the global login manager
     * @param driver WebDriver instance
     */
    public static void initialize(WebDriver driver) {
        // This will be called by BaseTest with properly initialized LoginActions
    }
    
    /**
     * Set the login actions instance (called by BaseTest)
     * @param actions Initialized LoginActions instance
     */
    public static void setLoginActions(LoginActions actions) {
        loginActions = actions;
    }
    
    /**
     * Perform global login - login once and store cookies for reuse
     * @param driver WebDriver instance
     * @param siteId Site ID
     * @param userId User ID
     */
    public static void performGlobalLogin(WebDriver driver, int siteId, int userId) {
        initialize(driver);
        
        if (isLoggedIn) {
            System.out.println("✅ Already logged in globally, skipping login");
            return;
        }
        
        System.out.println("🌐 Performing global login for site " + siteId + ", user " + userId);
        
        try {
            // Perform the actual login
            loginActions.login(siteId, userId);
            
            // Store cookies globally after successful login
            GlobalCookieManager.storeCookies(driver);
            
            // Mark as logged in
            isLoggedIn = true;
            
            System.out.println("✅ Global login completed successfully");
            
        } catch (Exception e) {
            System.err.println("❌ Global login failed: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Try to reuse stored cookies for authentication
     * @param driver WebDriver instance
     * @return true if cookie reuse was successful, false otherwise
     */
    public static boolean tryCookieReuse(WebDriver driver) {
        try {
            System.out.println("🔄 Attempting to reuse global cookies...");
            
            // Check if we have stored cookies
            if (!GlobalCookieManager.hasStoredCookies()) {
                System.out.println("📝 No global cookies available for reuse");
                return false;
            }
            
            System.out.println("🍪 Found " + GlobalCookieManager.getStoredCookieCount() + " global cookies");
            
            // Navigate to the application URL first
            loginActions.goTo(loginActions);
            loginActions.awaitForPageToLoadQuick();
            
            // Try to reuse cookies
            if (!GlobalCookieManager.reuseCookies(driver)) {
                System.out.println("Failed to reuse global cookies");
                return false;
            }
            
            // Navigate to a protected page to verify session
            loginActions.goTo(loginActions);
            loginActions.awaitForPageToLoadQuick();
            
            // Check if session is valid
            if (GlobalCookieManager.isSessionValid(driver)) {
                System.out.println("✅ Session validation successful - essential cookies found");
                
                // Additional verification: check if we're still on login page
                if (!loginActions.awaitForElementPresence(loginActions.loginTitle) && 
                    !loginActions.awaitForElementPresence(loginActions.emailInputBox)) {
                    System.out.println("✅ Session validation successful - not on login page");
                    return true;
                } else {
                    System.out.println("⚠️ Still on login page after cookie reuse, session may have expired");
                }
            } else {
                System.out.println("❌ Session validation failed - essential cookies not found");
            }
            
            return false;
            
        } catch (Exception e) {
            System.err.println("❌ Error during cookie reuse: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Smart login method - tries cookies first, falls back to full login if needed
     * @param driver WebDriver instance
     * @param siteId Site ID
     * @param userId User ID
     */
    public static void smartLogin(WebDriver driver, int siteId, int userId) {
        initialize(driver);
        
        // Always try cookie reuse first
        System.out.println("🔄 Attempting cookie reuse first...");
        if (tryCookieReuse(driver)) {
            System.out.println("✅ Login successful using cookie reuse");
            return;
        }
        
        // If cookie reuse fails, perform global login
        System.out.println("❌ Cookie reuse failed, performing global login");
        performGlobalLogin(driver, siteId, userId);
    }
    
    /**
     * Reset the global login state (useful for test suite cleanup)
     */
    public static void reset() {
        isLoggedIn = false;
        GlobalCookieManager.clearAllCookies();
        System.out.println("🔄 Global login state reset");
    }
    
    /**
     * Reset only the login state flag, preserve cookies for reuse
     */
    public static void resetLoginStateOnly() {
        isLoggedIn = false;
        System.out.println("🔄 Global login state reset (cookies preserved)");
    }
    
    /**
     * Check if global login has been performed
     * @return true if logged in, false otherwise
     */
    public static boolean isLoggedIn() {
        return isLoggedIn;
    }
    
    /**
     * Get login statistics
     * @return String containing login information
     */
    public static String getLoginStats() {
        return String.format("Global Login Stats - Logged In: %s, Stored Cookies: %d", 
                           isLoggedIn, GlobalCookieManager.getStoredCookieCount());
    }
} 