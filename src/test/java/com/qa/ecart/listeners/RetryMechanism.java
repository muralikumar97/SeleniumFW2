package com.qa.ecart.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryMechanism implements IRetryAnalyzer{
	
	private int count = 0;
	private static int maxTry = 3;

	@Override
	public boolean retry(ITestResult iTestResult) {
		
		if (!iTestResult.isSuccess()) {
			// Check if test not succeed
			
			if (count < maxTry) { 
				count++; 
				iTestResult.setStatus(ITestResult.FAILURE); // Mark test case as failed
				return true; // Tells TestNG to re-run the test
			} 
			else {
				iTestResult.setStatus(ITestResult.FAILURE); // If maxCount reached,test marked as failed
			}
			
		} 
		else {
			iTestResult.setStatus(ITestResult.SUCCESS); // If test passes, TestNG marks it as passed
		}
		return false;
	}
	
}
