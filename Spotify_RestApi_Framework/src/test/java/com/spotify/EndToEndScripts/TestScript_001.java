package com.spotify.EndToEndScripts;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.dataprovider.DataProviderFactory;
import com.spotify.dataprovider.DataProviderFileRowFilter;
import com.spotify.endpoints.Endpoints;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class TestScript_001 extends BaseTest{
	
	@DataProviderFileRowFilter(file = "./src/main/resources/data/Data.xlsx", sql = "Select * from Spotify_Data where REQUEST ='script001'")
	@Test(dataProvider = "data1", dataProviderClass = DataProviderFactory.class, description = "script001")
	
	public void test(String SL_No, String REQUEST,String getAlbum,String albumTrack) {

//		Response response= ApiActionUtil.getMethod(Endpoints.GET_ALBUM, Endpoints.OK,getAlbum,albumTrack);
		
//		 response= ApiActionUtil.getMethodWithPathParam(Endpoints.ALBUM_TRACKS, Endpoints.OK,ids);
//
//		ApiActionUtil.validateStatusCode(response, Endpoints.OK);
//		ApiActionUtil.validateResponseTime(response, Endpoints.time);
		
	}

}
