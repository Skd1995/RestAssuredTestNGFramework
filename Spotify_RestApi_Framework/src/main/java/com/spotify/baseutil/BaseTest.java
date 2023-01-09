package com.spotify.baseutil;

import java.io.FileInputStream;
import java.util.Properties;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.spotify.commonutils.ExcelUtil;
import java.lang.reflect.Method;
import com.aventstack.extentreports.ExtentTest;
import com.spotify.commonutils.FileOperation;
import com.spotify.extentreports.ExtentManager;
import com.spotify.extentreports.ExtentReport;
import com.spotify.utils.ApiActionUtil;

import io.restassured.builder.RequestSpecBuilder;

import io.restassured.specification.RequestSpecification;

/***********************************************************************
 * Description : Implements Application Precondition and Postcondition.
 * 
 * @BeforeSuite: Creates all the folder structure for Extent Reports
 */

public class BaseTest {
	public static WebDriver driver;
	public static Properties prop;
	public static Properties prop_constants;
	public static long get_max_response_time =2000L;
	public static String sDirPath = System.getProperty("user.dir");
	public static Logger logger = LoggerFactory.getLogger(BaseTest.class);
	public static final String EXCELPATH = sDirPath + "./src/main/resources/data/Data.xlsx";
	public static ApiActionUtil actionUtil;
	public String testCaseName;
	public static String className;
	public static Properties properties;
	public static final String CONFIGPATHEN = sDirPath + "./src/main/resources/EnvironmentVariables/config.properties";
	public static final String VALIDATIONCONSTANTS = sDirPath
			+ "./src/main/resources/TestData/Validation_Constants.properties";
	public static RequestSpecBuilder requestSpecBuilder;
	public static RequestSpecification requestSpecification;
	public static String baseUrl = "";
	public static String accessToken=ExcelUtil.getCellData(EXCELPATH, "Spotify", 1, 1);


	static {
		try {
			prop_constants = new Properties();
			properties = new Properties();
			FileInputStream fis = new FileInputStream(CONFIGPATHEN);
			properties.load(fis);

			FileInputStream fis1 = new FileInputStream(VALIDATIONCONSTANTS);
			prop_constants.load(fis1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Description : Creates folder structure for Extent reports.
	 * 
	 * @author:Sanjay
	 */
	@BeforeSuite(alwaysRun = true)
	public synchronized void createFiles() {
		try {
			logger.info("Folder creation for Extent");
			FileOperation fileOperation = new FileOperation();
			fileOperation.CreateFiles();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception while report inititation");
		}

	}
	
	/**
	 * Description: Launches the browser as specified in the parameter
	 * 
	 * @author:Sanjay
	 */
	@BeforeClass(alwaysRun = true)
	public synchronized void launchApp() {
		className = getClass().getSimpleName();
		 logger =  LoggerFactory.getLogger(BaseTest.class);
		ExtentTest parentExtentTest = ExtentReport.createTest(getClass().getSimpleName());
		ExtentManager.setParentReport(parentExtentTest);
			try {

			}catch(Exception e){
				e.printStackTrace();
				logger.info("Able to set Report");	
			}
		}

	/**
	 * Description: Creates nodes for the test methods in Extent report.
	 * 
	 * @author:Sanjay
	 * @param: methodName
	 */
	@BeforeMethod(alwaysRun = true)
	public synchronized void setExtentReport( Method methodName) {
		this.testCaseName = methodName.getName();
		String description = methodName.getAnnotation(Test.class).description();

		try {
			String baseUrl = "https://api.spotify.com/v1";
			
     		requestSpecBuilder = new RequestSpecBuilder().setBaseUri(baseUrl);	
     
			ExtentTest testReport = ExtentManager.getParentReport().createNode(testCaseName, description);
			ExtentManager.setTestReport(testReport);
			
		} catch (Exception e) {
			e.printStackTrace();
			ApiActionUtil.fail(e.getMessage());
			ApiActionUtil.error("Failed to set Extent Report");
			
		}
	}





}