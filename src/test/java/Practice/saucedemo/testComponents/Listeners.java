package Practice.saucedemo.testComponents;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Practice.saucedemo.resources.ExtentReporterNG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listeners Class - Implements TestNG listener for handling test events
 * Captures screenshots on test failure and generates comprehensive HTML reports using ExtentReports
 * Provides detailed logging and screenshot attachment for failed tests
 */
public class Listeners extends BaseTest implements ITestListener {

	private ExtentReports extent = ExtentReporterNG.getReportObject();
	private ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	private static final Logger logger = LoggerFactory.getLogger(Listeners.class);

	/**
	 * Called when a test is about to start
	 * Creates a test entry in the ExtentReports with test method name and start time
	 */
	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentTest test = extent.createTest(testName);
		test.info("Test Started: " + testName);
		test.info("Start Time: " + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date()));
		extentTest.set(test);
	}

	/**
	 * Called when a test passes successfully
	 * Logs the success status and test duration
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		long duration = result.getEndMillis() - result.getStartMillis();
		extentTest.get().log(Status.PASS, "✅ Test Passed Successfully");
		extentTest.get().info("Test Duration: " + duration + " ms");
	}

	/**
	 * Called when a test fails
	 * Captures screenshot of the failure point and attaches it to the report
	 * Logs the exception details and failure information
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		
		// Log exception details
		extentTest.get().log(Status.FAIL, "❌ Test Failed: " + testName);
		logger.info("Test started: {}", testName);
		extentTest.get().log(Status.FAIL, "Failure Cause: " + result.getThrowable());
		
		long duration = result.getEndMillis() - result.getStartMillis();
		extentTest.get().info("Test Duration: " + duration + " ms");
		
		// Retrieve WebDriver instance from test class in a safe manner
		WebDriver driver = null;
		try {
			Object testInstance = result.getInstance();
			if (testInstance instanceof BaseTest) {
				driver = ((BaseTest) testInstance).getDriver();
			} else {
				// Fallback to reflection if test class does not extend BaseTest
				driver = (WebDriver) result.getTestClass().getRealClass()
						.getField("driver").get(result.getInstance());
			}
		} catch (Exception e) {
			extentTest.get().log(Status.WARNING, "Could not retrieve WebDriver instance: " + e.getMessage());
			logger.warn("Could not retrieve WebDriver instance via reflection: {}", e.getMessage());
		}
		
		// Capture screenshot on failure
		if (driver != null) {
			String filePath = null;
			try {
				// Generate timestamp for unique screenshot name
				String timestamp = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss").format(new Date());
				filePath = captureScreenshot(testName + "_" + timestamp, driver);
				extentTest.get().log(Status.INFO, "📸 Screenshot captured at failure point");
				logger.info("Screenshot captured for {} at {}", testName, filePath);
				
				if (filePath != null) {
					// Attach screenshot to report with descriptive title
					extentTest.get().addScreenCaptureFromPath(filePath, 
						"Failure Screenshot - " + testName);
					extentTest.get().log(Status.INFO, "Screenshot saved: " + filePath);
				}
			} catch (IOException e) {
				extentTest.get().log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
				logger.warn("Failed to capture screenshot for {}: {}", testName, e.getMessage());
			}
		} else {
			extentTest.get().log(Status.WARNING, "⚠️ WebDriver instance is null - Screenshot not captured");
			logger.warn("WebDriver is null - screenshot not captured for {}", testName);
		}
	}

	/**
	 * Called when a test is skipped
	 * Logs the skip status with reason
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.get().log(Status.SKIP, "⏭️ Test Skipped: " + result.getMethod().getMethodName());
		if (result.getThrowable() != null) {
			extentTest.get().log(Status.SKIP, "Reason: " + result.getThrowable().getMessage());
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		extentTest.get().log(Status.WARNING, "⚠️ Test Failed But Within Success Percentage");
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		extentTest.get().log(Status.FAIL, "❌ Test Failed - Timeout occurred");
	}

	/**
	 * Called when test suite starts
	 * Logs suite start information
	 */
	@Override
	public void onStart(ITestContext context) {
		extent.createTest(context.getName())
			.info("Test Suite Started: " + context.getName());
	}

	/**
	 * Called when test suite finishes
	 * Flushes all pending logs and generates final HTML report
	 */
	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
		System.out.println("\n✅ Test report generated successfully!");
		System.out.println("📊 Report location: " + System.getProperty("user.dir") + "/reports/index.html");
	}
}
