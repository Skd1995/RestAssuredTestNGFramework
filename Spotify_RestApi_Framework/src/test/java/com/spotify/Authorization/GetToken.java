package com.spotify.Authorization;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.utils.ApiActionUtil;

import io.restassured.response.Response;

public class GetToken extends BaseTest{
	
	@Test
	
	public void get_token() {
		
HashMap<String, String> query_Param_Map = new HashMap<String,String>();
		
		query_Param_Map.put("client_id", prop_constants.getProperty("client_id"));
		query_Param_Map.put("client_secret", prop_constants.getProperty("client_secret"));
		query_Param_Map.put("grant_type", prop_constants.getProperty("grant_type_gettoken"));
		query_Param_Map.put("redirect_uri", prop_constants.getProperty("redirect_uri"));
		query_Param_Map.put("code", prop_constants.getProperty("code"));
		
		Response response=ApiActionUtil.postMethodforAuthroization(query_Param_Map, prop_constants.getProperty("renewtoken_url"), prop_constants.getProperty("status_code_200"), prop_constants.getProperty("content_type_json"));
		String accesstoken = ApiActionUtil.getResponseBodyValue(response, "refresh_token");
		System.out.println(accesstoken);
		
	}

}
