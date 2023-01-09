package com.spotify.Categories;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.dataprovider.DataProviderFactory;
import com.spotify.dataprovider.DataProviderFileRowFilter;
import com.spotify.endpoints.Endpoints;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class SeveralBrowseCategory extends BaseTest{
	
//	@DataProviderFileRowFilter(file = "./src/main/resources/data/Data.xlsx", sql = "Select * from Spotify_Data where REQUEST ='several browse category'")
//	@Test(dataProvider = "data1", dataProviderClass = DataProviderFactory.class, description = "several browse category")
//	
//	public void test(String SL_No, String REQUEST,String ids) {
//
//		Response response= ApiActionUtil.getMethod(Endpoints.SEVERAL_BROWSE_CATEGORY, Endpoints.OK,ids);
//		ApiActionUtil.validateStatusCode(response, Endpoints.OK);
//		ApiActionUtil.validateResponseTime(response, Endpoints.time);
//		ApiActionUtil.jsonSchemaValidation(response, "SeveralBrowseCategory.json");
//	}
	@DataProviderFileRowFilter(file = "./src/main/resources/data/Data.xlsx", sql = "Select * from sheet where testCase ='2'")
	@Test(dataProvider = "data1", dataProviderClass = DataProviderFactory.class, description = "several browse category")
	
	public void test1(String testCase, String endpoints,String querryParamKey,String querryParamValue,String pathParamKey,String pathParamValue) {

		Response response= ApiActionUtil.get(endpoints, Endpoints.OK,querryParamKey,querryParamValue, pathParamKey, pathParamValue);
		ApiActionUtil.validateStatusCode(response, Endpoints.OK);
		ApiActionUtil.validateResponseTime(response, Endpoints.time);
	}

}
