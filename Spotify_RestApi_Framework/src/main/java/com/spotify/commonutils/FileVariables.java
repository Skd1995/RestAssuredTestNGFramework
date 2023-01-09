package com.spotify.commonutils;




import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Description : Implements creation of file variables required to perform various file operations.
 * @author: vikas, sajal
 *  
 */
public class FileVariables {
	private String extentDir;
	private static String screenShotPath;
	private String sStartTime;
	private String extentReportFolderPath;
	private Date date;
	private SimpleDateFormat sdf;
	private String sDate;
	private String hash;
	/**
	 * Description:Gets the  screenshot path 
	 * @author: vikas, sajal
	 * @param screenShotPath
	 */
	public static String getScreenShotPath() {
		return screenShotPath;
	}
	/**
	 * Description:Sets the  screenshot path 
	 * @author:Shreya U,Vivek Dogra
	 * @param screenShotPath
	 */
	public void setScreenShotPath(String screenShotPath) {
		FileVariables.screenShotPath = screenShotPath;
	}

	/**
	 * Description:Gets start time
	 * @author:Shreya U,Vivek Dogra
	 * @return sStartTime
	 */

	public String getsStartTime() {
		return sStartTime;
	}
	/**
	 * Description:Sets the  start time 
	 * @author:Shreya U,Vivek Dogra
	 * @param sStartTime
	 */
	public void setsStartTime(String sStartTime) {
		this.sStartTime = sStartTime;
	}
	/**
	 * Description:Gets Extent report folder path
	 * @author:Shreya U,Vivek Dogra
	 * @return extentReportFolderPath
	 */
	public String getExtentReportFolderPath() {
		return extentReportFolderPath;
	}
	/**
	 * Description:Sets the  Extent report folder path 
	 * @author:Shreya U,Vivek Dogra
	 * @param extentReportFolderPath
	 */
	public void setExtentReportFolderPath(String extentReportFolderPath) {
		this.extentReportFolderPath = extentReportFolderPath;
	}
	/**
	 * Description:Gets Date
	 * @author:Shreya U,Vivek Dogra
	 * @return date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Description:Sets the  date 
	 * @author:Shreya U,Vivek Dogra
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * Description:Gets simple date format
	 * @author:Shreya U,Vivek Dogra
	 * @return sdf
	 */
	public SimpleDateFormat getSdf() {
		return sdf;
	}
	/**
	 * Description:Sets the simple date format
	 * @author:Shreya U,Vivek Dogra
	 * @param sdf
	 */
	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}
	/**
	 * Description:Gets Date
	 * @author:Shreya U,Vivek Dogra
	 * @return sDate
	 */
	public String getsDate() {
		return sDate;
	}
	/**
	 * Description:Sets the date
	 * @author:Shreya U,Vivek Dogra
	 * @param sDate
	 */
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	/**
	 * Description:Gets Hash
	 * @author:Shreya U,Vivek Dogra
	 * @return hash
	 */
	public String getHash() {
		return hash;
	}
	/**
	 * Description Sets the hash
	 * @author:Shreya U,Vivek Dogra
	 * @param  hash
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}
	/**
	 * Description:Gets extent directory
	 * @author:Shreya U,Vivek Dogra
	 * @return extentDir
	 */
	public String getExtentDir() {
		return extentDir;
	}
	/**
	 * Description:Sets extent dir
	 * @author:Shreya U,Vivek Dogra
	 * @param extentDir
	 */
	public void setExtentDir(String extentDir) {
		this.extentDir = extentDir;
	}
}
