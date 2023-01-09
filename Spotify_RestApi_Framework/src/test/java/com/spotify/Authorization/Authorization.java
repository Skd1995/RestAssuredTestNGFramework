package com.spotify.Authorization;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.spotify.baseutil.BaseTest;
import com.spotify.utils.ApiActionUtil;

public class Authorization extends BaseTest{
	
	@Test
	
	public void getautorize() {
		
	HashMap<String, String> query_Param_Map = new HashMap<String,String>();
		
		query_Param_Map.put("client_id", prop_constants.getProperty("client_id"));
		query_Param_Map.put("response_type", prop_constants.getProperty("response_type"));
		query_Param_Map.put("scope", prop_constants.getProperty("scope"));
		query_Param_Map.put("redirect_ur", prop_constants.getProperty("redirect_ur"));
		ApiActionUtil.getMethodforAuthorization(query_Param_Map, prop_constants.getProperty("authorize_Url"), prop_constants.getProperty("status_code_200"), prop_constants.getProperty("content_type_json"));
	}

}
