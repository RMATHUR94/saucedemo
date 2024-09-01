package Practice.saucedemo.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

	public static ExtentReports getReportObject()
	{
		// ExtentsReports , ExtentsSparkReport
		String path = System.getProperty("user.dir") + "\\reports\\index.html";

		ExtentSparkReporter reporter = new ExtentSparkReporter(path);

		reporter.config().setReportName("Extent Report check result");

		reporter.config().setDocumentTitle("Test Results");

		ExtentReports extent = new ExtentReports();

		extent.attachReporter(reporter);

		extent.setSystemInfo("Tester", "Rahul_QA_M");
        
		return extent;
		// extent.createTest(path);
	}
	
}
