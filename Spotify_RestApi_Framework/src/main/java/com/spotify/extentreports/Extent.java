package com.spotify.extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.spotify.commonutils.FileVariables;
import com.spotify.utils.ApiActionUtil;

/**
 * Description: Implements the creation of the Extent Html report with specified
 * name after loading the Extent config file from specified location.
 * 
 * @author vikas, sajal
 */
public class Extent {
	
	private ExtentReports extent;
	FileVariables fileVariables = new FileVariables();
	/**
	 * Description: Creates of HTML report in specified path
	 * 
	 * @author vikas, sajal
	 */
	public  ExtentReports getExtent(String filepath) {

		if (extent == null) {
			try {
				extent = new ExtentReports();
				extent.attachReporter(getHtmlReporter(filepath+  "/Automation_Report.html"));
				return extent; 
			} catch (Exception e) {
				ApiActionUtil.info("Exception while creating report html file.");
				
			}
		}
		return extent;
	}
	/**
	 * Description :Loads the Extent-config file specified from the location
	 * 
	 * @author vikas, sajal
	 */
	private static ExtentHtmlReporter getHtmlReporter(String filePath) {
		
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
		htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"./src\\main\\resources\\ExtentConfiguration\\extent-config.xml");
		return htmlReporter;
	}

}
