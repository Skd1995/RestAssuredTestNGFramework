package com.spotify.testsuitegenerator;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.FailurePolicy;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

/**
 * Description :This class is used to generate TestNG.xml (anyname.xml)
 * dynamically which is used to drive all the TestNG classes.This reads
 * Controller.xlsx file which contains package name in a column and className in
 * another column named TestcaseId .Based on the status 'Yes'/'No' ,the
 * TestNG.xml will contain only those classes where status is 'Yes'. Note:We can
 * further customize according to the requirement like grouping by
 * test,include,exclude
 * 
 * @author vikas,sajal
 */
public class TestSuiteGenerator {

	static int testcnt = 0;

	@SuppressWarnings("null")
	public static void main(String[] args) throws Exception {
		XmlSuite xmlSuite = new XmlSuite();
		xmlSuite.setConfigFailurePolicy(FailurePolicy.CONTINUE);
		xmlSuite.setName("Flipkart");

		Map<String, String> mpconfiginfo = getConfigInfo(System.getProperty("user.dir") + "/Controller.xlsx");

		xmlSuite.addListener("com.tyss.trello.android.listener.TestListners");
		XmlTest xmlTest = null;
		// xmlSuite.setParallel(ParallelMode.TESTS);
		int threadCount = Integer.parseInt(mpconfiginfo.get("ThreadCount"));
		xmlSuite.setThreadCount(threadCount);
//		System.out.println(mpconfiginfo.get("BrowserName2"));
		
		addClasses(xmlSuite, xmlTest, mpconfiginfo.get("BrowserName1"));
		
//		if (!mpconfiginfo.get("BrowserName1").equals("none") && !mpconfiginfo.get("BrowserName2").equals("none")
//				&& mpconfiginfo.get("Parallel").equalsIgnoreCase("tests")) {
//			xmlSuite.setParallel(mpconfiginfo.get("Parallel"));
//			addClasses(xmlSuite, xmlTest, mpconfiginfo.get("BrowserName1"));
//			addClasses(xmlSuite, xmlTest, mpconfiginfo.get("BrowserName2"));
//		} else if (!mpconfiginfo.get("BrowserName1").equals("none")
//				&& mpconfiginfo.get("BrowserName2").equals("none")) {
//			xmlSuite.setParallel(mpconfiginfo.get("Parallel"));
//			addClasses(xmlSuite, xmlTest, mpconfiginfo.get("BrowserName1"));
//		} else if (mpconfiginfo.get("BrowserName1").equals("none")
//				&& !mpconfiginfo.get("BrowserName2").equals("none")) {
//			xmlSuite.setParallel(mpconfiginfo.get("Parallel"));
//			addClasses(xmlSuite, xmlTest, mpconfiginfo.get("BrowserName2"));
//		} else {
//			xmlSuite.setParallel(mpconfiginfo.get("Parallel"));
//			addClasses(xmlSuite, xmlTest, mpconfiginfo.get("BrowserName1"));
//		}
		TestNG testng = new TestNG();

		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(xmlSuite);

		testng.setXmlSuites(suites);

		Files.write(Paths.get(System.getProperty("user.dir") + "/testng.xml"), xmlSuite.toXml().getBytes());

	}

	/**
	 * Description: This method fetches the TestNG classes to be run from Excel
	 * sheet (Controller.xlsx) and adds it to TestSuite.xml
	 * 
	 * @author Aatish S
	 * @param xmlSuite
	 * @param xmlTest
	 * @param browserName
	 */
	private static void addClasses(XmlSuite xmlSuite, XmlTest xmlTest, String browserName)
			throws InvalidFormatException, IOException, Exception {
		xmlTest = new XmlTest(xmlSuite);
		xmlTest.setName("Trello_Test" + ++testcnt);
		xmlTest.setThreadCount(1);
//		xmlTest.addParameter("browserName", browserName);

		String classNames[] = getRunTestClasses(System.getProperty("user.dir") + "/Controller.xlsx");
		System.out.println("=====================================");
		System.out.println("          Added classes            ");
		System.out.println("=====================================");
		List<XmlClass> list = new ArrayList<XmlClass>();
		for (int i = 0; i < classNames.length; i++) {
			System.out.println(Class.forName(classNames[i]));
			list.add(new XmlClass(Class.forName(classNames[i])));
		}
		System.out.println("=====================================");
		System.out.println(classNames.length+" classes added to testng xml");
		System.out.println("=====================================");
		xmlTest.setXmlClasses(list);

	}

	/**
	 * Description: This method returns an array of String containing TestNG class
	 * names which are used for running in the TestSuite.xml
	 * 
	 * @author Aatish S
	 * @param strXlFilePath
	 */
	private static String[] getRunTestClasses(String strXlFilePath)
			throws IOException, Exception, InvalidFormatException {
//		CommonConfig.logger.info(strXlFilePath);
		FileInputStream fis = new FileInputStream(new File(strXlFilePath));

		Workbook wb = (Workbook) WorkbookFactory.create(fis);

		Sheet sh1 = wb.getSheet("PackageController");
		Sheet sh2 = wb.getSheet("TestController");
		ArrayList<String> lstClassNames = new ArrayList<String>();

		String[] strClassNames = null;

		if (sh1 != null) {

			int rowcnt_sh1 = sh1.getPhysicalNumberOfRows();
			for (int i = 1; i <= rowcnt_sh1; i++) {
				Row r_sh1 = null;
				r_sh1 = sh1.getRow(i);

				if (r_sh1 != null) {
					if (r_sh1.getCell(2).getStringCellValue().equalsIgnoreCase("yes")) {
						String flowName = r_sh1.getCell(1).getStringCellValue();

						if (sh2 != null) {
							int rowcnt_sh2 = sh2.getPhysicalNumberOfRows();

							for (int j = 1; j <= rowcnt_sh2; j++) {
								Row r_sh2 = null;
								r_sh2 = sh2.getRow(j);
								String flowNameSheet2 = null;

								if (r_sh2 != null) {
									String pkgName = r_sh2.getCell(1).getStringCellValue();
									String[] pkNameSplit = pkgName.split("[.]");
									flowNameSheet2 = pkNameSplit[3];
									if (flowName.equals(flowNameSheet2)
											&& r_sh2.getCell(3).getStringCellValue().equalsIgnoreCase("yes")) {
										
										lstClassNames.add(r_sh2.getCell(1).getStringCellValue() + "."
												+ r_sh2.getCell(2).getStringCellValue());
									}
								}

							}
						}
					}
				}
			}

		}
		wb.close();
		fis.close();
		strClassNames = new String[lstClassNames.size()];
		for (int k = 0; k < lstClassNames.size(); k++) {
			strClassNames[k] = lstClassNames.get(k);
//			CommonConfig.logger.info(strClassNames[k]);
		}
		return strClassNames;
	}

	/**
	 * Description: This method returns LinkeHashMap containing information of
	 * parallel ,thread count,browser count read from Controller.xlsx
	 * 
	 * @author Vivek
	 * @param filepath
	 */
	private static Map<String, String> getConfigInfo(String filepath) {

		LinkedHashMap<String, String> configinfo = new LinkedHashMap<String, String>();

		try {
			FileInputStream fis = new FileInputStream(new File(filepath));

			Workbook wb = (Workbook) WorkbookFactory.create(fis);

			Sheet sh = wb.getSheet("Configuration");

			Row r = null;

			for (int i = 1; i <= 4; i++) {

				r = sh.getRow(i);
				if (r != null) {

					Cell cell = r.getCell(1);

					CellType celltype = cell.getCellTypeEnum();

					if (celltype == CellType.NUMERIC) {
						configinfo.put(r.getCell(0).getStringCellValue(),
								Integer.toString((int) cell.getNumericCellValue()));

					} else if (celltype == CellType.STRING) {
						configinfo.put(r.getCell(0).getStringCellValue(), cell.getStringCellValue());

					}

				}
			}

			fis.close();
			wb.close();
		} catch (Exception e) {

		}

		return configinfo;
	}

	/**
	 * Description: This method returns the mode of execution reading from
	 * Controller.xlsx
	 * 
	 * @author Aatish S
	 * @param mode
	 */
	private static ParallelMode getParallelMode(String mode) {

		if (mode.equalsIgnoreCase("none")) {
			return ParallelMode.NONE;

		} else if (mode.equalsIgnoreCase("tests")) {
			return ParallelMode.TESTS;

		}

		return ParallelMode.NONE;
	}

	private static String getBrowserName() {
		String browserName = null;

		return browserName;

	}
}
