package com.spotify.Albums;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.dataprovider.DataProviderFactory;
import com.spotify.dataprovider.DataProviderFileRowFilter;
import com.spotify.endpoints.Endpoints;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class GetAlbumTracks extends BaseTest{
	
	@DataProviderFileRowFilter(file = "./src/main/resources/data/Data.xlsx", sql = "Select * from Spotify_Data where REQUEST ='album tracks'")
	@Test(dataProvider = "data1", dataProviderClass = DataProviderFactory.class, description = "album tracks")
	
	public void test(String SL_No, String REQUEST,String ids) {

		Response response= ApiActionUtil.getMethodWithPathParam(Endpoints.ALBUM_TRACKS, Endpoints.OK,ids);
		ApiActionUtil.validateStatusCode(response, Endpoints.OK);
		ApiActionUtil.validateResponseTime(response, Endpoints.time);
	}

}
