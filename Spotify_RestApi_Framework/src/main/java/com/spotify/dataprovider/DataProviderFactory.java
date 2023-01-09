package com.spotify.dataprovider;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.codoid.products.exception.FilloException;
import com.spotify.commonutils.ExcelUtil;

/**
 * Description: This class implements data provider logic which fetches the data
 * from the Excel sheet.
 * 
 * @author Shreya U, Vivek Dogra
 */
public class DataProviderFactory {

	@DataProvider(name = "data")
	public static Object[][] getData(Method method) throws IOException {
		DataProviderFile file = method.getAnnotation(DataProviderFile.class);

		String filepath = System.getProperty("user.dir") + file.file();

		int noofrows = ExcelUtil.getRowCount(filepath, file.sheet());
		int noofcols = ExcelUtil.getColumnCount(filepath, file.sheet());
		Object[][] workflowdata = new Object[noofrows][noofcols];

		for (int i = 1; i <= noofrows; i++) {
			String data[] = ExcelUtil.getRowData(filepath, file.sheet(), i);

			for (int j = 0; j < data.length; j++) {

				workflowdata[i - 1][j] = data[j];
			}

		}
		return workflowdata;

	}

	@DataProvider(name = "data1")
	public static Object[][] getFilterRowData(Method method) throws IOException, FilloException {
		DataProviderFileRowFilter file = method.getAnnotation(DataProviderFileRowFilter.class);

		String filepath = System.getProperty("user.dir") + file.file();

		String filterData[][] = ExcelUtil.getRowDataFromExcelUsingFillo(filepath, file.sql());

		return filterData;
	}

}
