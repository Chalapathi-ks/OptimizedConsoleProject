package core.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    private static ExtentReports extent;

    public synchronized static ExtentReports getInstance() {
        if (extent == null)
            createInstance("extent.html");
        return extent;
    }

    public synchronized static ExtentReports createInstance(String fileName) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileName);
        sparkReporter.config().setDocumentTitle("Unbxd SelfServe UI - Test Report");
        sparkReporter.config().setReportName("UNBXD SelfServe UI Automation Report");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setCss("img { max-width: 100%; height: auto; }");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        return extent;
    }
}
