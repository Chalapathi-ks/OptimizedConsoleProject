package core;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyser implements IRetryAnalyzer {

    private int count = 0;
    // Retry failed tests only once immediately (total 2 attempts: initial + 1 retry)
    private static int maxCount = 1;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {
            if (count < maxCount) {
                count++;
                String testName = iTestResult.getMethod().getMethodName();
                String className = iTestResult.getTestClass().getName();
                Throwable throwable = iTestResult.getThrowable();
                
                System.out.println("\n🔄 RETRYING FAILED TEST:");
                System.out.println("   Test: " + className + "." + testName);
                System.out.println("   Attempt: " + (count + 1) + " of " + (maxCount + 1));
                System.out.println("   Failure reason: " + (throwable != null ? throwable.getClass().getSimpleName() : "Unknown"));
                if (throwable != null && throwable.getMessage() != null) {
                    System.out.println("   Error: " + throwable.getMessage());
                }
                System.out.println("   Retrying immediately...\n");
                
                iTestResult.setStatus(ITestResult.FAILURE);
                return true;
            } else {
                // Max retries reached, mark as final failure
                String testName = iTestResult.getMethod().getMethodName();
                String className = iTestResult.getTestClass().getName();
                System.out.println("\n❌ TEST FAILED AFTER " + (maxCount + 1) + " ATTEMPTS:");
                System.out.println("   Test: " + className + "." + testName);
                System.out.println("   No more retries will be attempted.\n");
                iTestResult.setStatus(ITestResult.FAILURE);
            }
        } else {
            // Test passed
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
