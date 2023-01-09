package com.spotify.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.spotify.baseutil.BaseTest;

/**
 * Description : Creates the HTML report based on the name specified.
 * @author vikas, sajal
 * 
 */
public class ExtentReport {
	private static ExtentTest extentTtest;
	private static ExtentReports extentReports;
	
	
	/**
	 * Description : Creates the HTML report based on the name specified
	 * @author vikas, sajal
	 * @param name
	 */
	public static ExtentTest createTest(String name) {

		extentTtest = extentReports.createTest(name);
		return extentTtest;
	}

	public static ExtentReports getExtent() {
		return extentReports;
	}
	/**
	 * Description : Initializes report specified in the file path
	 * @author vikas, sajal
	 * @param filepath
	 */
	public void initReport(String filepath) {
		extentReports = new Extent().getExtent(filepath);
		BaseTest.actionUtil.info("Report Initiated");
	}

}
