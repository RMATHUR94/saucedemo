package Practice.saucedemo.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.File;

/**
 * ExtentReporterNG - Generates comprehensive HTML test reports with advanced configuration
 * Configures and provides ExtentReports instance for test reporting with system details
 */
public class ExtentReporterNG {

	/**
	 * Create and configure ExtentReports object with advanced settings
	 * 
	 * @return Configured ExtentReports instance
	 */
	public static ExtentReports getReportObject() {
		// Create reports directory if it doesn't exist
		String reportsDir = System.getProperty("user.dir") + File.separator + "reports";
		File reportsDirFile = new File(reportsDir);
		if (!reportsDirFile.exists()) {
			reportsDirFile.mkdirs();
		}
		
		String reportPath = reportsDir + File.separator + "index.html";
		
		// Create extent report configuration with advanced settings
		ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
		
		// Configure reporter appearance and behavior
		reporter.config().setReportName("SauceDemo Selenium Test Execution Report");
		reporter.config().setDocumentTitle("SauceDemo - Automated Test Results");
		reporter.config().setTheme(Theme.DARK);
		reporter.config().setTimeStampFormat("dd-MMM-yyyy HH:mm:ss");
		
		// Create extent reports instance and attach reporter
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		
		// Add comprehensive system information
		extent.setSystemInfo("Tester Name", "QA Automation Engineer");
		extent.setSystemInfo("Environment", "SauceDemo Application");
		extent.setSystemInfo("Browser Support", "Chrome / Firefox / Edge");
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("OS Version", System.getProperty("os.version"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Project Path", System.getProperty("user.dir"));
		
		return extent;
	}
}
