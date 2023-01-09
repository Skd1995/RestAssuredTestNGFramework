package com.spotify.commonutils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.spotify.baseutil.BaseTest;



/**
 * Description: Implements generic methods to perform operations on Excel Workbook.
 * @author: Shreya U,Vivek Dogra,Aatish Slathia
 */
public class ExcelUtil extends BaseTest{
	/* Private constructor*/
	private ExcelUtil() {

	}
	
	

	/**
	 * Description:Writes data to Excel
	 * @author:Abhilash
	 * @param Excel Path 
	 * @param SheetName
	 * @param String[] data 
	 * @throws Exception 
	 * @throws NoSuchFieldException 
	 * */
	public static synchronized void writeSingleRowDataToExcel(String xlPath,String sheetName,Map<String,String> mapData) throws NoSuchFieldException, Exception
	{

		FileInputStream fis = null;
		Workbook wb = null;
		try {
			fis = new FileInputStream(new File(xlPath));
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		}
		
		try {
			wb = new XSSFWorkbook(fis);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		Sheet sh=wb.getSheet(sheetName);
		

		Row r = null;
		Cell c =null;
		
		
		if(sh !=null)
		{
		    r =sh.getRow(sh.getLastRowNum()+1);
		    if(r == null)
		    {
		    	r=sh.createRow(sh.getLastRowNum()+1);
		    }
		    
		    Set<String> keyData =mapData.keySet();
		    int i=0;
		    for(String singledata:keyData)
		    {
		    	String format=mapData.get(singledata);
		    	
		    	c =r.getCell(i);
		    	
		    	if( c == null)
		    	{
		    		c=r.createCell(i);
		    	}
		    	if(format.equalsIgnoreCase("number"))
		    	{
		    	
		    	  c.setCellType(CellType.NUMERIC);
		    	  c.setCellValue(Integer.parseInt(singledata));
		    	}
		    	else if(format.equalsIgnoreCase("date"))
		    	{
		    	    CellStyle cs=wb.createCellStyle();
		    	    CreationHelper ch=wb.getCreationHelper();
		    	    cs.setDataFormat(ch.createDataFormat().getFormat("M/d/yyyy"));
		    	   
		    	    Date date=new SimpleDateFormat("M/d/yyyy").parse(singledata);
		    	    c.setCellValue(singledata);
		    	    c.setCellStyle(cs);
		    	  
		    		
		    	}
		    	else if(format.equalsIgnoreCase("string"))
		    	{
		    		c.setCellValue(singledata);
		    	}
		    
		    	i++;
		    }
		    
		    
		  
		}
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(xlPath));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		try {
			wb.write(fos);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		try {
			wb.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		String arr[]=getGIMRowData(xlPath,sheetName,1);
		actionUtil.info(Arrays.toString(arr));
		
	}
	
	
	
	public static synchronized void writeToTextFile(String fileWithPath,String data)
	{
		  try {
	            Files.write(Paths.get(fileWithPath), data.getBytes());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	public static synchronized String convertArrayToFormattedString(String[][] twoDArray)
	{
		String data ="";
		
		for(int i=0;i<twoDArray.length;i++)
		{
			for(int j=0;j<twoDArray[i].length;j++)
			{
				if(twoDArray[i].length- j!=1)
				{
					data = data + twoDArray[i][j].trim() +"\t";
				}
				else
				{
					data = data + twoDArray[i][j];
				}
			}
			data = data +"\n";
		}
			
		return data;
	}
	public synchronized static String getSheetName(String xlPath)
	{
		String sheetName = null;
		try {
			FileInputStream fis = new FileInputStream(xlPath);
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			sheetName = wb.getSheetName(0);
			wb.close();
			fis.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sheetName;
	}
	
	public static synchronized String[][]  getRowDataFromExcelUsingFillo(String xlPath,String sql) 
	{
		
		Fillo fillo=new Fillo();
		String[][] tableValues= null;
		Connection connection;
		try {
			connection = fillo.getConnection(xlPath);
			Recordset recordset=connection.executeQuery(sql);
			//	WebActionUtil.info("No of rows are " + recordset.getCount());
				ArrayList<String> lstFieldNames =recordset.getFieldNames();
				//WebActionUtil.info("No of cols are " + lstFieldNames.size());
				
				 tableValues =new String[recordset.getCount()][lstFieldNames.size()];
			
				int rowno =0;
				
				while(recordset.next()){
				    
				    int colno = 0;	
				   
					for(String fieldName:lstFieldNames)
					{
						tableValues[rowno][colno++]=recordset.getField(fieldName) ;
						//WebActionUtil.info(recordset.getField(fieldName) + " ");
						
					}
					rowno++;
				   
				}
				 
				recordset.close();
				connection.close();
		} catch (FilloException e) {
			// TODO Auto-generated catch block
			System.out.println("Fillo exception");
			e.printStackTrace();
		}
		
		
		return tableValues;
	}

	/**
	 * Description:Fetches the row count in the specified sheet
	 * @author:Shreya U,Vivek Dogra
	 * @param sPath 
	 * @param sSheet
	 */
	public static synchronized int getRowCount(String sPath, String sSheet) {
		int iRowNum = 0;
		try {
			FileInputStream fis = new FileInputStream(sPath);
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet(sSheet);
			iRowNum = sht.getLastRowNum();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iRowNum;
	}

	/**
	 * Description:Fetches the column count in the specified sheet
	 * @author:Vivek Dogra
	 * @param sSheet 
	 * @parm sPath 
	 */
	public static synchronized int getColumnCount(String sPath, String sSheet) {
		int colnum = 0;
		try {
			FileInputStream fis = new FileInputStream(sPath);
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet(sSheet);
			colnum = sht.getRow(0).getPhysicalNumberOfCells();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return colnum;
	}

	/**
	 * Description:Fetches the data from the specified cell
	 * @author:Shreya U
	 * @param xlPAth 
	 * @param sheetName 
	 * @param rowNo 
	 * @param colNo
	 */
	public static  synchronized  String getCellData(String xlPAth, String sheetName, int rowNo, int colNo) {
		DataFormatter dataFormatter = new DataFormatter();

		int iRowNum = 0;
		String data = null;
		try {
			FileInputStream fis = new FileInputStream(xlPAth);
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet(sheetName);
			iRowNum = sht.getLastRowNum();
			if (rowNo <= iRowNum) {
				Cell cell = sht.getRow(rowNo).getCell(colNo);
				data = dataFormatter.formatCellValue(cell);
			} else {
				BaseTest.actionUtil.info("Please provide the valid row count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Description : Fetches the specified row data from the Excel sheet
	 * @author:Aatish Slathia
	 * @param sFilePath 
	 * @param sSheet 
	 * @param rowNo
	 */
	public static synchronized  String[] getGIMRowData(String sFilePath, String sSheet, int rowNo) {
		DataFormatter dataFormatter = new DataFormatter();
		String sData[] = null;
		try {
			FileInputStream fis = new FileInputStream(sFilePath);
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet(sSheet);
			int iCellNum =0;
			try {
			    iCellNum = sht.getRow(rowNo).getPhysicalNumberOfCells();
			}
			catch(Exception e)
			{
				return null;
			}
			sData = new String[iCellNum];
			for (int j = 0; j < iCellNum; j++) {
				Cell cell = sht.getRow(rowNo).getCell(j);
				
			    if(cell.getCellTypeEnum() ==CellType.NUMERIC)
			    {
			    	if(DateUtil.isCellDateFormatted(cell))
			    	   {
			    		
			    		SimpleDateFormat DateFor = new SimpleDateFormat("d/M/yyyy");
			    		String stringDate= DateFor.format(cell.getDateCellValue());
			    		
			    		sData[j] =stringDate;
			    	   }
			    	else
			    	{
			    		sData[j] = dataFormatter.formatCellValue(cell);
			    	}
			    }
			    else
			    {
			    	sData[j] = dataFormatter.formatCellValue(cell);
			    }
			 
			    	
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		return sData;
	}
	/**
	 * Description : Fetches the specified row data from the Excel sheet
	 * @author:Aatish Slathia
	 * @param sFilePath 
	 * @param sSheet 
	 * @param rowNo
	 */
	public static synchronized  String[] getRowData(String sFilePath, String sSheet, int rowNo) {
		DataFormatter dataFormatter = new DataFormatter();
		String sData[] = null;
		try {
			FileInputStream fis = new FileInputStream(sFilePath);
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet(sSheet);
			int iCellNum = sht.getRow(rowNo).getPhysicalNumberOfCells();
			sData = new String[iCellNum];
			for (int j = 0; j < iCellNum; j++) {
				Cell cell = sht.getRow(rowNo).getCell(j);
				sData[j] = dataFormatter.formatCellValue(cell);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return sData;
	}

	/**
	 * Description :Fetches the specified column data from the Excel  sheet
	 * @author:Vivek Dogra
	 * @param sFilePath 
	 * @param sSheet 
	 * @param colno                   
	 */
	public static synchronized  String[] getColoumData(String sFilePath, String sSheet, int colno) {
		DataFormatter dataFormatter = new DataFormatter();
		String sData[] = null;
		try {
			FileInputStream fis = new FileInputStream(sFilePath);
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet(sSheet);
			int iRowNum = sht.getLastRowNum();
			sData = new String[iRowNum];

			for (int i = 1; i <= iRowNum; i++) {
				Cell cell = sht.getRow(i).getCell(colno);
				sData[i - 1] = dataFormatter.formatCellValue(cell);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sData;
	}

	/**
	 * Description Fetches the column index for a given value of a cell value from the first/header row.
	 * @author:Shreya U
	 * @param sFilePath 
	 * @param sSheet 
	 * @param colName 
	 */
	public static synchronized  int getColoumIndex(String filepath, String sSheet, String colName) {
		String[] firstrow = getRowData(filepath, sSheet, 0);
		int index = 0;
		for (int i = 0; i < firstrow.length; i++) {
			if (firstrow[i].equalsIgnoreCase(colName)) {
				index = i;
			}
		}
		return index;

	}

	/**
	 * Description: Checks if an array contains blank/default value.
	 * @author:Aatish Slathia
	 * @param data 
	 * @return blank
	 */
	public static  synchronized  boolean doesArrayContainsBlank(String[] data) {
		boolean blank = false;

		for (int i = 0; i < data.length; i++) {
			if (data[i].isEmpty() || data[i] == null) {
				blank = true;
				break;
			}
		}
		return blank;
	}

	/**
	 * Description :Reads the Excel data from a specified Excel sheet based on TestCaseId.
	 * @author:Vivek D
	 * @param sFilePath
	 * @param sSheet
	 * @param sTestCaseId 
	 * @return SData
	 */
	public static synchronized  String[] toReadExcelData(String sFilePath, String sSheet, String sTestCaseId) {
		DataFormatter dataFormatter = new DataFormatter();
		String SData[] = null;
		try {
			// File Read
			FileInputStream fis = new FileInputStream(sFilePath);
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet(sSheet);
			int iRowNum = sht.getLastRowNum();
			for (int i = 0; i <= iRowNum; i++) {
				if (sht.getRow(i).getCell(0).toString().equals(sTestCaseId)) {
					int iCellNum = sht.getRow(i).getPhysicalNumberOfCells();
					SData = new String[iCellNum];
					for (int j = 0; j < iCellNum; j++) {
						Cell cell = sht.getRow(i).getCell(j);
						SData[j] = dataFormatter.formatCellValue(cell);
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return SData;

	}

	/**
	 * Description :Fetches the column index 
	 * @author:Shreya U
	 * @param sFilePath
	 * @param sSheet
	 * @param colName
	 * @param firstRowName 
	 * @return index
	 */
	public  static synchronized  int getColoumIndex(String filepath, String sSheet, String colName, String firstRowName) {
		String[] firstRow = ExcelUtil.toReadExcelData(filepath, sSheet, firstRowName);
		int index = 0;
		for (int i = 0; i < firstRow.length; i++) {
			if (firstRow[i].equalsIgnoreCase(colName)) {
				index = i;
			}
		}
		return index;
	}

	/**
	 * Description: Fetches  the cell count in the specified row.
	 * @author:Shreya U
	 * @param sPath
	 * @param sSheet
	 * @param sSheeet
	 * @param row 
	 * @return column
	 */
	public static  synchronized  int getCellCount(String sPath, String sSheeet, int row) {
		int colnum = 0;
		try {
			FileInputStream fis = new FileInputStream(sPath);
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet(sSheeet);
			colnum = sht.getRow(row).getPhysicalNumberOfCells();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return colnum;
	}
	
	
	/**
	 * Description: This method is used  when we want the update the Excel data with date format 
	 *               for date cells. This is not possible using Apache POI and specially important 
	 *               when the AUT(GIM) validates the Excel workbook for Cells with date format
	 *         
	 * @author:Abhilash
	 * @param vbsCode
	 * 
	 */
	

	
	
	
	public static void updateExcelFileDateCellFormatUsingVbs(String downLoadedFilePath,String downLoadedFileName) throws InterruptedException {
		String vbsCode = writeVbsCodeForExcelFileCreation(downLoadedFilePath, downLoadedFileName);
		generateVBSFileFromVbsCode(vbsCode);
	//	deleteFileIfExists(downLoadedFilePath, downLoadedFileName);
		executeVBSFileForExcelUpdate();
		actionUtil.poll(8000);

	}

	/**
	 * Description: This method is used to delete the file if at all it exists
	 *              to avoid interaction when New ExcelFile is generated
	 *         
	 * @author:Abhilash
	 * @param vbsCode
	 * 
	 */
	private static void deleteFileIfExists(String filePath,String fileName)
	{
		String[] fileNameSplit=fileName.split("[.]");
		String newFileName = fileNameSplit[0]+"_1"+".xlsx";
		Path path=Paths.get(filePath+"\\"+newFileName);
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Description: This method is used to execute the generated vbs File "UpdateExcel.vbs"
	 *         
	 * @author:Abhilash
	 * 
	 */
	private static void executeVBSFileForExcelUpdate() {
		try {
			String vbsFile = System.getProperty("user.dir") + "\\data\\resources\\UpdateExcel.vbs";
			String vbexe = "wscript.exe";
			String[] cmdArr = { vbexe, vbsFile };
			Runtime.getRuntime().exec(cmdArr);

		} catch (IOException e) {
			e.printStackTrace();

		}

	}
	
	/**
	 * Description: This method creates a vbscript file by passing vbscript code which in turn is
	 *               used for updating 
	 *               given Excel workbook for data cell 
	 *               Date cell does not work properly from Apache POI as the application (GIM)
	 *               validates the Excel workbook for the date format
	 * @author:Abhilash
	 * @param vbsCode
	 * 
	 */

	private static void generateVBSFileFromVbsCode(String vbsCode) {
		try {
			Files.write(Paths.get(System.getProperty("user.dir") + "/data/resources/UpdateExcel.vbs"),
					vbsCode.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Description: This method creates a vbscript code which in turn is used for creating vbs file for
	 *               updating given Excel workbook for data cell 
	 *               Date cell does not work properly from Apache POI as the application (GIM)
	 *               validates the Excel workbook for the date format
	 * @author:Abhilash
	 * @param xlpath
	 * @param fileName
	 * 
	 */
	private static String writeVbsCodeForExcelFileCreation(String xlpath,String fileName)
	{
		    int rowno =2;
		    
	        String vbsCode="set xl=CreateObject(\"Excel.Application\")"+"\n"+
 
				"xl.visible=false"+"\n"+
				"set wb=xl.workbooks.open(\"" + xlpath +"\\" + fileName + "\")"+"\n"+
				"set sh1=wb.worksheets(1)";
	           
	            String cellValueCode ="";

	            vbsCode =vbsCode +"\n" + cellValueCode;
	            vbsCode = vbsCode +"\n"+ "For Each rCell in sh1.UsedRange\r\n" + 
	            		          "   On Error Resume Next\r\n" + 
	            		"    If IsDate(rCell.value) Then\r\n" + 
	            		"	rCell.NumberFormat=\"m/d/yyyy\"\r\n" + 
	            		"    End if\r\n" + 
	            		"Next";
	            
	           String[] fileNameSplit=fileName.split("[.]");
	           vbsCode = vbsCode+"\n" + "wb.saveAs \""+ xlpath+"\\"+fileNameSplit[0]+"_1."+fileNameSplit[1]+"\"";
	           vbsCode = vbsCode +  "\r\n"+
	        		   "wb.save\r\n" + 
	           		   "wb.close\r\n" + 
	           		   "xl.quit\r\n" + 
	           		   "set xl =nothing\r\n" + 
	           		   "set wb=nothing\r\n" + 
	           		   "set sh=nothing";


		 	return vbsCode;

	}
	
	/**
	 * Description: This method is use to write the data into excel
	 * @author MANJU
	 * @param 
	 * @param 
	 * 
	 */
	
	public static void writedataintoexcel(String sheetname,String cellvalue, int row, int column) throws Exception{
		String Excelpath=EXCELPATH;  
		File file=new File(Excelpath);
		FileInputStream fis=new FileInputStream(file);
		XSSFWorkbook wb=new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet(sheetname);
		sheet.getRow(row).createCell(column).setCellValue(cellvalue);
		
		FileOutputStream fos=new FileOutputStream(new File(Excelpath));
		wb.write(fos);
		wb.close();
		fos.close();
	}
	
	/**
	 * This generic method is used to write the data into excel sheet
	 * @param sheetName
	 * @param rowNum
	 * @param celNum
	 * @param data
	 * @throws Throwable
	 */
	public static void setDataExcel(String sheetName , int rowNum, int celNum ,String data)  {
		Workbook wb = null;
		try {
			String excelpath=EXCELPATH;
			FileInputStream fis  = new FileInputStream(excelpath);
			wb = WorkbookFactory.create(fis);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet sh = wb.getSheet(sheetName);
		Row row = sh.getRow(rowNum);
		Cell cel = row.createCell(celNum);
		cel.setCellValue(data);
		try {
			FileOutputStream fos = new FileOutputStream(EXCELPATH);
			wb.write(fos);
			wb.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}