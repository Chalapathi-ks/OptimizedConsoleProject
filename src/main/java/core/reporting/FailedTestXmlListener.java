package core.reporting;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FailedTestXmlListener implements ITestListener {
    private FileWriter writer;

    @Override
    public void onStart(ITestContext context) {
        try {
            writer = new FileWriter("failed-tests.xml");
            writer.write("<failed-tests>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath = "";
        // Try to take a screenshot if WebDriver is available
        Object testInstance = result.getInstance();
        try {
            // Assumes your test classes have a 'driver' field of type WebDriver
            WebDriver driver = (WebDriver) testInstance.getClass().getField("driver").get(testInstance);
            if (driver != null) {
                File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File destDir = new File("screenshots");
                if (!destDir.exists()) destDir.mkdirs();
                screenshotPath = "screenshots/" + result.getName() + "_" + System.currentTimeMillis() + ".png";
                File destFile = new File(screenshotPath);
                Files.copy(srcFile.toPath(), destFile.toPath());
            }
        } catch (Exception e) {
            // Ignore if driver is not available or screenshot fails
        }

        try {
            writer.write("  <testcase name=\"" + result.getName() + "\" class=\"" + result.getTestClass().getName() + "\" screenshot=\"" + screenshotPath + "\"/>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            writer.write("</failed-tests>\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Unused methods
    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
} 