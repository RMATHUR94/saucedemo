package Practice.saucedemo.testComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Practice.saucedemo.resources.ExtentReporterNG;

//Taking Screenshot on Test Fail( main method written on the BaseTest 
public class Listeners extends BaseTest implements ITestListener{

	//getting object from ExtentReporterNG class
	
	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	//Creating multi-Threading Threadlocal object due to parallel Run that cretaing error in test object of 
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal();
	@Override
	public void onTestStart(ITestResult result) {
		
		test = extent.createTest(result.getMethod().getMethodName());
		
		extentTest.set(test);// unique thread id(ErrorValidationTest)->
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		//test.log(Status.PASS,"Test Passed");
		
		extentTest.get().log(Status.PASS,"Test Passed");
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
	   //test.fail(result.getThrowable());
		
		extentTest.get().fail(result.getThrowable());
	  
		//Adding life to the driver
		
	  try {
		driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	  
	   String filePath = null;
	 
	   //On Test Failure Screenshot
	  try {
		filePath = getScreenShot(result.getMethod().getMethodName(),driver);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	  
	  //test.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	
	}

	@Override
	public void onTestSkipped(ITestResult result) {
	
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
	 extent.flush();
	}

	
}
