package Practice.saucedemo.testComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry Analyzer - Implements TestNG retry mechanism
 * Automatically retries failed tests to handle flakey test cases
 */
public class retry implements IRetryAnalyzer {

	private int count = 0;
	private static final int MAX_RETRY_COUNT = 1;

	@Override
	public boolean retry(ITestResult result) {
		if (count < MAX_RETRY_COUNT) {
			count++;
			return true;
		}
		return false;
	}
}
