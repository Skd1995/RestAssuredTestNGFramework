package com.spotify.Authorization;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.commonutils.ExcelUtil;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class RenewToken extends BaseTest{

	@Test
	
	public void get_Token() throws Exception {
		
		HashMap<String, String> query_Param_Map = new HashMap<String,String>();
		
		query_Param_Map.put("client_id", prop_constants.getProperty("client_id"));
		query_Param_Map.put("client_secret", prop_constants.getProperty("client_secret"));
		query_Param_Map.put("grant_type", prop_constants.getProperty("grant_type"));
		query_Param_Map.put("refresh_token", prop_constants.getProperty("refresh_token"));
		
		Response response=ApiActionUtil.postMethodforAuthroization(query_Param_Map, prop_constants.getProperty("renewtoken_url"), prop_constants.getProperty("status_code_200"), prop_constants.getProperty("content_type_json"));
		String accesstoken = ApiActionUtil.getResponseBodyValue(response, "access_token");
		System.out.println(" Accesstoken : "+accesstoken);
		ExcelUtil.setDataExcel("Spotify", 1, 1, accesstoken);
		
	}
	
}
