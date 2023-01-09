package com.spotify.Genres;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.dataprovider.DataProviderFactory;
import com.spotify.dataprovider.DataProviderFileRowFilter;
import com.spotify.endpoints.Endpoints;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class GetAvailableGenres extends BaseTest{
	
	@DataProviderFileRowFilter(file = "./src/main/resources/data/Data.xlsx", sql = "Select * from Spotify_Data where REQUEST ='get available genres'")
	@Test(dataProvider = "data1", dataProviderClass = DataProviderFactory.class, description = "get available genres")
	
	public void test(String SL_No, String REQUEST,String ids) {

		Response response= ApiActionUtil.getMethod(Endpoints.GET_AVAILABLE_GENRES, Endpoints.OK,ids);
		ApiActionUtil.validateStatusCode(response, Endpoints.OK);
		ApiActionUtil.validateResponseTime(response, Endpoints.time);
		ApiActionUtil.jsonSchemaValidation(response, "GetAvailableGenres.json");
	}

}
