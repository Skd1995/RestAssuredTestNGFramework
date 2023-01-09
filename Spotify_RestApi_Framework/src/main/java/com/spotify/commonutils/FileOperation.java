package com.spotify.commonutils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.spotify.baseutil.BaseTest;
import com.spotify.extentreports.ExtentReport;
import com.spotify.utils.ApiActionUtil;

/**
 * Description : Implements creation of the folder structure for Extent
 * reports,screenshots,deletion of the previously created folder.
 * 
 * @author: vikas, sajal
 */
public class FileOperation {
	FileVariables fileVariables = new FileVariables();

	/**
	 * Description:Implements setting of the path,creation of the folder structure
	 * for Extent reports,screenshot,deletion of the folder.
	 * 
	 * @author: vikas, sajal
	 */
	public void CreateFiles() {

		fileVariables.setDate(new Date());
		fileVariables.setSdf(new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss"));
		fileVariables.setsDate(fileVariables.getSdf().format(fileVariables.getDate()));
		fileVariables.setsStartTime(fileVariables.getsDate());
		fileVariables.setExtentReportFolderPath(System.getProperty("user.dir") + "/reports");
		fileVariables.setExtentDir(fileVariables.getExtentReportFolderPath() + "/Automation Report- "
				+ ApiActionUtil.getCurrentDateTime());

		fileVariables.setScreenShotPath(fileVariables.getExtentDir() + "/Screenshots/");

		BaseTest.actionUtil.info("ExtentDir:-" + fileVariables.getExtentDir());

		/* delete extent folder if it exists before running suite */
		ApiActionUtil.deleteDir(fileVariables.getExtentReportFolderPath());

		try {
			File file = new File(fileVariables.getExtentDir());
			if (!(file.exists())) {
				boolean extentFolderStatus = file.mkdirs();
				if (extentFolderStatus == true) {
					new ExtentReport().initReport(fileVariables.getExtentDir());

				}

				else
					BaseTest.actionUtil.error("--> Extent folder not created");
			}

		} catch (Exception e) {
			BaseTest.actionUtil.info("Inside on start catch block" + e.getMessage());
			e.printStackTrace();
		}

		// Setting ScreenShot Report Location
		try {
			File screenShot = new File(FileVariables.getScreenShotPath());
			if (!(screenShot.exists())) {
				boolean screenshotFolderStatus = screenShot.mkdirs();
				if (screenshotFolderStatus == true)
					BaseTest.actionUtil.info("Screenshot folder created");

				else
					BaseTest.actionUtil.info("Screenshot folder not created");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
