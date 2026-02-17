package core;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Retries each failed test method once (per-method counter).
 * Uses a per-method key so testng-failed.xml only lists tests that actually failed after retries.
 */
public class RetryAnalyser implements IRetryAnalyzer {

    private static final int MAX_RETRY = 1;

    /** Per-method retry count so each test gets its own retries (avoids shared counter bug). */
    private final Map<String, Integer> retryCountByMethod = new ConcurrentHashMap<>();

    private static String key(ITestResult result) {
        String ctx = result.getTestContext().getName();
        String cls = result.getTestClass().getName();
        String method = result.getMethod().getMethodName();
        return ctx + "#" + cls + "#" + method;
    }

    @Override
    public boolean retry(ITestResult iTestResult) {
        String methodKey = key(iTestResult);
        int count = retryCountByMethod.getOrDefault(methodKey, 0);

        if (!iTestResult.isSuccess()) {
            if (count < MAX_RETRY) {
                retryCountByMethod.put(methodKey, count + 1);
                String testName = iTestResult.getMethod().getMethodName();
                String className = iTestResult.getTestClass().getName();
                Throwable throwable = iTestResult.getThrowable();

                System.out.println("\n🔄 RETRYING FAILED TEST:");
                System.out.println("   Test: " + className + "." + testName);
                System.out.println("   Attempt: " + (count + 2) + " of " + (MAX_RETRY + 1));
                System.out.println("   Failure reason: " + (throwable != null ? throwable.getClass().getSimpleName() : "Unknown"));
                if (throwable != null && throwable.getMessage() != null) {
                    System.out.println("   Error: " + throwable.getMessage());
                }
                System.out.println("   Retrying immediately...\n");

                iTestResult.setStatus(ITestResult.FAILURE);
                return true;
            } else {
                String testName = iTestResult.getMethod().getMethodName();
                String className = iTestResult.getTestClass().getName();
                System.out.println("\n❌ TEST FAILED AFTER " + (MAX_RETRY + 1) + " ATTEMPTS:");
                System.out.println("   Test: " + className + "." + testName);
                System.out.println("   No more retries will be attempted.\n");
                iTestResult.setStatus(ITestResult.FAILURE);
            }
        } else {
            if (count > 0) {
                String testName = iTestResult.getMethod().getMethodName();
                System.out.println("\n✅ TEST PASSED ON RETRY:");
                System.out.println("   Test: " + testName);
                System.out.println("   Passed on attempt: " + (count + 1) + "\n");
            }
            iTestResult.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }
}
