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
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal();

    ExtentTest parent, subChild;
    ITestContext context;


    @Override
    public synchronized void onTestStart(ITestResult iTestResult) {


    }

    @Override
    public synchronized void onTestSuccess(ITestResult iTestResult) {
        try {
            if (parentTest.get() != null) {
                ExtentTest child = parentTest.get().createNode(" Test " + iTestResult.getMethod().getMethodName(), iTestResult.getMethod().getDescription());
                test.set(child);
                appendTestInfoInReport(Status.PASS, iTestResult);
            }
        } catch (Exception e) {
            System.err.println("Error in onTestSuccess: " + e.getMessage());
        }
    }

    @Override
    public synchronized void onTestFailure(ITestResult iTestResult) {
        try {
            if (parentTest.get() != null) {
                ExtentTest child = parentTest.get().createNode("Test :" + iTestResult.getMethod().getMethodName(), iTestResult.getMethod().getDescription());
                test.set(child);
                appendTestInfoInReport(Status.FAIL, iTestResult);
            }
        } catch (Exception e) {
            System.err.println("Error in onTestFailure: " + e.getMessage());
        }
    }

    @Override
    public synchronized void onTestSkipped(ITestResult iTestResult) {
        try {
            if (parentTest.get() != null) {
                ExtentTest child = parentTest.get().createNode("Test :" + iTestResult.getMethod().getMethodName(), iTestResult.getMethod().getDescription());
                test.set(child);
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
        parent = extent.createTest(context.getName());
        parentTest.set(parent);
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
    }

}
