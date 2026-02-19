package lib;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import java.io.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Global Cookie Manager for storing and reusing cookies across all test classes
 * Provides persistent cookie storage that survives across different JVM processes
 */
public class GlobalCookieManager {
    
    // Global storage for cookies - shared across all threads and test classes
    private static final ConcurrentHashMap<String, Set<Cookie>> globalCookieStore = new ConcurrentHashMap<>();
    
    // Key for storing cookies - using a simple static key for global access
    private static final String GLOBAL_COOKIE_KEY = "GLOBAL_SESSION_COOKIES";
    
    // File path for persistent cookie storage
    private static final String COOKIE_FILE_PATH = "target/cookies.dat";
    
    /**
     * Store cookies globally after successful login
     * @param driver WebDriver instance
     */
    public static void storeCookies(WebDriver driver) {
        try {
            Set<Cookie> cookies = driver.manage().getCookies();
            globalCookieStore.put(GLOBAL_COOKIE_KEY, cookies);
            
            // Also save to file for persistence across JVM restarts
            saveCookiesToFile(cookies);
            
            System.out.println("🌐 Global cookies stored successfully. Count: " + cookies.size());
            
            // Log cookie details for debugging
            for (Cookie cookie : cookies) {
                System.out.println("🍪 Stored: " + cookie.getName() + " = " + 
                    cookie.getValue().substring(0, Math.min(20, cookie.getValue().length())) + "...");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Failed to store global cookies: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Reuse stored cookies globally
     * @param driver WebDriver instance
     * @return true if cookies were successfully reused, false otherwise
     */
    public static boolean reuseCookies(WebDriver driver) {
        try {
            System.out.println("🔍 Checking for global stored cookies...");
            
            // First try memory store
            if (globalCookieStore.containsKey(GLOBAL_COOKIE_KEY)) {
                Set<Cookie> cookies = globalCookieStore.get(GLOBAL_COOKIE_KEY);
                System.out.println("📦 Found " + cookies.size() + " cookies in memory");
                return addCookiesToDriver(driver, cookies);
            }
            
            // If not in memory, try loading from file
            Set<Cookie> cookies = loadCookiesFromFile();
            if (cookies != null && !cookies.isEmpty()) {
                System.out.println("📦 Found " + cookies.size() + " cookies from file");
                globalCookieStore.put(GLOBAL_COOKIE_KEY, cookies);
                return addCookiesToDriver(driver, cookies);
            }
            
            System.out.println("📝 No global cookies available for reuse");
            return false;
            
        } catch (Exception e) {
            System.err.println("❌ Failed to reuse global cookies: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Add cookies to the WebDriver
     * @param driver WebDriver instance
     * @param cookies Set of cookies to add
     * @return true if cookies were added successfully
     */
    private static boolean addCookiesToDriver(WebDriver driver, Set<Cookie> cookies) {
        int addedCount = 0;
        for (Cookie cookie : cookies) {
            try {
                driver.manage().addCookie(cookie);
                addedCount++;
                System.out.println("✅ Added cookie: " + cookie.getName() + " = " + 
                    cookie.getValue().substring(0, Math.min(20, cookie.getValue().length())) + "...");
            } catch (Exception e) {
                System.out.println("⚠️ Skipping cookie: " + cookie.getName() + " - " + e.getMessage());
            }
        }
        
        System.out.println("✅ Successfully added " + addedCount + " cookies from global store");
        return addedCount > 0;
    }
    
    /**
     * Save cookies to file for persistence
     * @param cookies Set of cookies to save
     */
    private static void saveCookiesToFile(Set<Cookie> cookies) {
        try {
            File file = new File(COOKIE_FILE_PATH);
            file.getParentFile().mkdirs(); // Create directory if it doesn't exist
            
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(cookies);
                System.out.println("💾 Cookies saved to file: " + COOKIE_FILE_PATH);
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to save cookies to file: " + e.getMessage());
        }
    }
    
    /**
     * Load cookies from file
     * @return Set of cookies or null if loading failed
     */
    @SuppressWarnings("unchecked")
    private static Set<Cookie> loadCookiesFromFile() {
        try {
            File file = new File(COOKIE_FILE_PATH);
            if (!file.exists()) {
                System.out.println("📝 No cookie file found: " + COOKIE_FILE_PATH);
                return null;
            }
            
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Set<Cookie> cookies = (Set<Cookie>) ois.readObject();
                System.out.println("📂 Cookies loaded from file: " + COOKIE_FILE_PATH);
                return cookies;
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to load cookies from file: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Check if global cookies are available for reuse
     * @return true if cookies are stored, false otherwise
     */
    public static boolean hasStoredCookies() {
        // Check memory first
        if (globalCookieStore.containsKey(GLOBAL_COOKIE_KEY) && 
            !globalCookieStore.get(GLOBAL_COOKIE_KEY).isEmpty()) {
            return true;
        }
        
        // Check file
        Set<Cookie> cookies = loadCookiesFromFile();
        return cookies != null && !cookies.isEmpty();
    }
    
    /**
     * Get the number of stored global cookies
     * @return number of stored cookies
     */
    public static int getStoredCookieCount() {
        // Check memory first
        if (globalCookieStore.containsKey(GLOBAL_COOKIE_KEY)) {
            return globalCookieStore.get(GLOBAL_COOKIE_KEY).size();
        }
        
        // Check file
        Set<Cookie> cookies = loadCookiesFromFile();
        return cookies != null ? cookies.size() : 0;
    }
    
    /**
     * Clear all stored global cookies
     */
    public static void clearAllCookies() {
        globalCookieStore.clear();
        
        // Also delete the file
        try {
            File file = new File(COOKIE_FILE_PATH);
            if (file.exists()) {
                file.delete();
                System.out.println("🗑️ Cookie file deleted: " + COOKIE_FILE_PATH);
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to delete cookie file: " + e.getMessage());
        }
        
        System.out.println("🗑️ All global cookies cleared");
    }
    
    /**
     * Validate if the current session is still valid by checking for essential cookies
     * @param driver WebDriver instance
     * @return true if session appears valid, false otherwise
     */
    public static boolean isSessionValid(WebDriver driver) {
        try {
            // Check for essential authentication cookies
            Cookie ssoCookie = driver.manage().getCookieNamed("_un_sso_uid");
            Cookie csrfCookie = driver.manage().getCookieNamed("_un_csrf");
            
            boolean isValid = ssoCookie != null && csrfCookie != null;
            System.out.println("🔍 Session validation: " + (isValid ? "✅ Valid" : "❌ Invalid"));
            return isValid;
            
        } catch (Exception e) {
            System.err.println("❌ Failed to validate session: " + e.getMessage());
            return false;
        }
    }
} 