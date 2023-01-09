package com.spotify.Episodes;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.dataprovider.DataProviderFactory;
import com.spotify.dataprovider.DataProviderFileRowFilter;
import com.spotify.endpoints.Endpoints;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class GetUsersSavedEpisodes extends BaseTest{
	
	@DataProviderFileRowFilter(file = "./src/main/resources/data/Data.xlsx", sql = "Select * from Spotify_Data where REQUEST ='get users saved episodes'")
	@Test(dataProvider = "data1", dataProviderClass = DataProviderFactory.class, description = "get users saved episodes")
	
	public void test(String SL_No, String REQUEST,String ids) {

		Response response= ApiActionUtil.getMethod2(Endpoints.GET_USERS_SAVED_EPISODES, Endpoints.OK);
		ApiActionUtil.validateStatusCode(response, Endpoints.OK);
		ApiActionUtil.validateResponseTime(response, Endpoints.time);
	}

}
