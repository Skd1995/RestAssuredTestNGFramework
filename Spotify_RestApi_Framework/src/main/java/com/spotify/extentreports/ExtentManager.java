package com.spotify.extentreports;

import com.aventstack.extentreports.ExtentTest;
/**
 * Description Implements creation of the node,or parent block  in the extent report 
 * @author vikas, sajal
 *
 */
public class ExtentManager {
	private static ThreadLocal<ExtentTest> extentReportForTestMethod = new ThreadLocal<ExtentTest>();
	/**
	 * Description : Fetches the object of Extent reports for the test methods.
	 * @author vikas, sajal
	 * @return ExtentTest
	 */
	public static ExtentTest getExtentReportForTestMethods() {
		return extentReportForTestMethod.get();
	}
	/**
	 * Description :Sets the Extent Test for the test methods.
	 * @author vikas, sajal
	 * @param ExtentTest
	 */
	public static void setExtentReportsForTestMethods(ExtentTest extent) {
		extentReportForTestMethod.set(extent);
	}

	private static ThreadLocal<ExtentTest> extentReportForClassMethod = new ThreadLocal<ExtentTest>();
	/**
	 * Description : Fetches the object of Extent Test for precondition
	 * @author vikas, sajal
	 * @return ExtentTest
	 */
	public static ExtentTest getExtentReportForClassMethods() {
		return extentReportForClassMethod.get();
	}
	/**
	 * Description :Sets the Extent Test for the class method
	 * @author vikas, sajal
	 * @param ExtentTest
	 */
	public static void setExtentReportsForClassMethods(ExtentTest extent) {
		extentReportForClassMethod.set(extent);
	}

	private static ThreadLocal<ExtentTest> extentReportForPrecondition = new ThreadLocal<ExtentTest>();
	/**
	 * Description : Fetches the object of Extent Test for precondition
	 * @author vikas, sajal
	 * @return ExtentTest
	 */
	public static ExtentTest getExtentReportForPrecondition() {
		return extentReportForPrecondition.get();
	}
	/**
	 * Description :Sets the Extent Test with the precondition
	 * @author vikas, sajal
	 * @param ExtentTest
	 */
	public static void setExtentReportsForPrecondition(ExtentTest extent) {
		extentReportForPrecondition.set(extent);
	}

	private static ThreadLocal<ExtentTest> parentReport = new ThreadLocal<ExtentTest>();
	/**
	 * Description :Sets the Extent Test for Parent node in report.
	 * @author vikas, sajal
	 * @param ExtentTest
	 */
	public static void setParentReport(ExtentTest extentTest) {
		parentReport.set(extentTest);
	}
	/**
	 * Description : Fetches the object of Extent Test for parent node in Report.
	 * @author vikas, sajal
	 * @return ExtentTest
	 */
	public static ExtentTest getParentReport() {
		return parentReport.get();
	}

	private static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();
	/**
	 * Description : Fetches the object of Extent Test for Test Report.
	 * @author vikas, sajal
	 * @return ExtentTest
	 */
	public static ExtentTest getTestReport() {
		return testReport.get();
	}
	/**
	 * Description :Sets the Extent Test for Report.
	 * @author vikas, sajal
	 * @param ExtentTest
	 */
	public static void setTestReport(ExtentTest extent) {
		testReport.set(extent);
	}
	private static ThreadLocal<ExtentTest> exteReport = new ThreadLocal<ExtentTest>();
	
	/**
	 * Description :Sets the Extent Test for Report.
	 * @author vikas, sajal
	 * @param ExtentTest
	 */
	public static void setReport(ExtentTest extentTest) {
		exteReport.set(extentTest);
	}
	
	/**
	 * Description : Fetches the object of Extent Test for Report.
	 * 
	 * @author vikas, sajal
	 * @return ExtentTest
	 */
	public static ExtentTest getReport()
	{
		return exteReport.get();
	}
}
