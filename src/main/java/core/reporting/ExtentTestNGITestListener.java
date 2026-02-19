package core.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import lib.Helper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class ExtentTestNGITestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.createInstance("extent.html");
    private static ThreadLocal<ExtentTest> test = new ThreadLocal();

    ITestContext context;


    @Override
    public synchronized void onTestStart(ITestResult iTestResult) {
        // Create a top-level test entry for each test method (not as a child node)
        try {
            String testName = iTestResult.getMethod().getMethodName();
            String className = iTestResult.getTestClass().getName();
            // Extract just the class name without package
            String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
            // Create test name as: ClassName - MethodName
            String fullTestName = simpleClassName + " - " + testName;
            String description = iTestResult.getMethod().getDescription();
            if (description == null || description.isEmpty()) {
                description = "Test method: " + testName;
            }
            ExtentTest testEntry = extent.createTest(fullTestName, description);
            test.set(testEntry);
        } catch (Exception e) {
            System.err.println("Error in onTestStart: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void onTestSuccess(ITestResult iTestResult) {
        try {
            if (test.get() != null) {
                appendTestInfoInReport(Status.PASS, iTestResult);
            }
        } catch (Exception e) {
            System.err.println("Error in onTestSuccess: " + e.getMessage());
        }
    }

    @Override
    public synchronized void onTestFailure(ITestResult iTestResult) {
        try {
            if (test.get() != null) {
                appendTestInfoInReport(Status.FAIL, iTestResult);
            }
        } catch (Exception e) {
            System.err.println("Error in onTestFailure: " + e.getMessage());
        }
    }

    @Override
    public synchronized void onTestSkipped(ITestResult iTestResult) {
        try {
            if (test.get() != null) {
                appendTestInfoInReport(Status.SKIP, iTestResult);
            }
        } catch (Exception e) {
            System.err.println("Error in onTestSkipped: " + e.getMessage());
        }
    }

    @Override
    public   synchronized void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public  synchronized void onStart(ITestContext iTestContext) {
        this.context = iTestContext;
        // No need to create parent test - each test method will be a top-level entry
    }

    @Override
    public synchronized void onFinish(ITestContext iTestContext) {
        extent.flush();
        // Ensure Extent_Report exists and copy the HTML to a stable location for Jenkins artifact publishing
        try {
            File reportDir = new File("Extent_Report");
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }
            File srcHtml = new File("extent.html");
            if (srcHtml.exists()) {
                File dstHtml = new File(reportDir, "index.html");
                FileUtils.copyFile(srcHtml, dstHtml);
            } else {
                // fallback to default TestNG extent location if any future change moves it
                File alt = new File("test-output/ExtentReport.html");
                if (alt.exists()) {
                    File dstHtml = new File(reportDir, "index.html");
                    FileUtils.copyFile(alt, dstHtml);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to finalize Extent report artifacts: " + e.getMessage());
        }

    }


    private synchronized void appendTestInfoInReport(Status testStatus, ITestResult iTestResult) throws IOException
    {
        if (test.get() == null) {
            System.err.println("ExtentTest is null, skipping report update");
            return;
        }
        
        if (testStatus.equals(Status.FAIL)) {
            try {
                String destinationPath = Helper.getScreenShot(iTestResult.getMethod().getMethodName());
                if (destinationPath != null && new File(destinationPath).exists()) {
                    // Copy screenshot into Extent_Report/screenshots and use a relative path to avoid broken images in Jenkins artifacts
                    File src = new File(destinationPath);
                    File reportDir = new File("Extent_Report");
                    if (!reportDir.exists()) {
                        reportDir.mkdirs();
                    }
                    File screenshotsDir = new File(reportDir, "screenshots");
                    if (!screenshotsDir.exists()) {
                        screenshotsDir.mkdirs();
                    }
                    String fileName = src.getName();
                    File dst = new File(screenshotsDir, fileName);
                    try {
                        FileUtils.copyFile(src, dst);
                        String relativePath = "screenshots/" + fileName;
                        test.get().addScreenCaptureFromPath(relativePath, "Failure Screenshot");
                    } catch (IOException copyEx) {
                        System.err.println("Failed to copy screenshot into report directory: " + copyEx.getMessage());
                        // Fallback to original path
                        test.get().addScreenCaptureFromPath(destinationPath, "Failure Screenshot");
                    }
                }
            } catch (Exception e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
            if (iTestResult.getThrowable() != null) {
                test.get().log(testStatus, "Failure Reason : " + iTestResult.getThrowable().getMessage());
            }
        }
        if (testStatus.equals(Status.SKIP)) {
            if (iTestResult.getThrowable() != null) {
                test.get().log(testStatus, "Skipped Reason: " + iTestResult.getThrowable().getMessage());
            }
        }
        if (testStatus.equals(Status.PASS)) {
            test.get().pass("Test passed successfully");
        }
    }

}
