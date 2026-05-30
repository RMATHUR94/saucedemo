package Practice.saucedemo.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * ExtentReporterNG - Generates HTML test reports
 * Configures and provides ExtentReports instance for test reporting
 */
public class ExtentReporterNG {

	/**
	 * Create and configure ExtentReports object
	 * 
	 * @return Configured ExtentReports instance
	 */
	public static ExtentReports getReportObject() {
		String reportPath = System.getProperty("user.dir") + "\\reports\\index.html";
		
		// Create extent report configuration
		ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
		reporter.config().setReportName("SauceDemo Test Execution Report");
		reporter.config().setDocumentTitle("Test Results Report");
		
		// Create extent reports instance and attach reporter
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		
		// Add system information
		extent.setSystemInfo("Tester", "QA Automation Engineer");
		extent.setSystemInfo("Environment", "SauceDemo");
		extent.setSystemInfo("Browser", "Chrome/Firefox/Edge");
		
		return extent;
	}
}
