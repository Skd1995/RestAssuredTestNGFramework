package com.spotify.utils;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.spotify.baseutil.BaseTest;
import com.spotify.extentreports.ExtentManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class ApiActionUtil extends BaseTest{

	public static TakesScreenshot screenshot;

	/**
	 * Description Method to provide info in the log,testNg reports
	 * 
	 * @author Sajal
	 * @param message
	 */
	public static void info(String message) {
		BaseTest.logger.info(message);
		// ExtentHCLManager.getTestReport().info(message);
	}

	/**
	 * Description Method for the error updation in logs
	 * 
	 * @author Sajal
	 * @param message
	 */
	public static void error(String message) {
		BaseTest.logger.error(message);
		// ExtentHCLManager.getTestReport().error(message);
	}

	/**
	 * Description Method to provide info in the extent report
	 * 
	 * @author Sajal
	 * @param message
	 */
	public static void extentinfo(String message) {
		ExtentManager.getTestReport().info(message);
	}

	/**
	 * Description Method for the pass updation in extent Report
	 * 
	 * @author Sajal
	 * @param message
	 */
	public static void pass(String message) {
		ExtentManager.getTestReport().pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
	}

	/**
	 * Description Method for the info/error updation in extent Report
	 * 
	 * @author Sajal
	 * @param message
	 * @param color
	 */
	public static void validationinfo(String message, String color) {
		if (color.equalsIgnoreCase("blue"))
			ExtentManager.getTestReport().info(MarkupHelper.createLabel(message, ExtentColor.BLUE));
		else if (color.equalsIgnoreCase("green"))
			ExtentManager.getTestReport().pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
		else if (color.equalsIgnoreCase("red"))
			ExtentManager.getTestReport().fail(MarkupHelper.createLabel(message, ExtentColor.RED));
	}

	/**
	 * Description Method for the error updation in extent Report
	 * 
	 * @author Sajal
	 * @param message
	 */
	public static void fail(String message) {
		ExtentManager.getTestReport().fail(MarkupHelper.createLabel(message, ExtentColor.RED));
	}

	/**
	 * Description:: Method to wait for mentioned time
	 * 
	 * @author Sajal
	 * @param poll
	 */
	public static void poll(int poll) {
		try {
			Thread.sleep(poll);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description Method for fetching Current Date Time
	 * 
	 * @author Sajal
	 */
	public static String getCurrentDateTime() {
		DateFormat customFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
		Date currentDate = new Date();
		return customFormat.format(currentDate);
	}

	/**
	 * Description Method to delete the directory
	 * 
	 * @author Sajal
	 * @param pathToDeleteFolder
	 */
	public static void deleteDir(String pathToDeleteFolder) {
		File extefolder = new File(pathToDeleteFolder);
		if ((extefolder.exists())) {
			deleteFolderDir(extefolder);
		}
	}

	/**
	 * Description Method to delete folder directory
	 * 
	 * @author Sajal
	 * @param folderToDelete
	 */
	public static void deleteFolderDir(File folderToDelete) {
		File[] folderContents = folderToDelete.listFiles();
		if (folderContents != null) {
			for (File folderfile : folderContents) {
				if (!Files.isSymbolicLink(folderfile.toPath())) {
					deleteFolderDir(folderfile);
				}
			}
		}
		folderToDelete.delete();
	}

	/**
	 * Description: Capture the screenshot of the complete screen
	 * 
	 * @author Sajal
	 * @param path
	 * @return destinationPath
	 */
	public static String getScreenShot(String path) {
		File src = (screenshot.getScreenshotAs(OutputType.FILE));
		String currentDateTime = getCurrentDateTime();
		String destinationPath = path + BaseTest.className + "-" + currentDateTime + ".png";
		File destination = new File(destinationPath);
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			error("Capture Failed " + e.getMessage());
		}
		return "./Screenshots/" + BaseTest.className + "-" + currentDateTime + ".png";
	}

	public String getJsonData(Response response, String path) {
		String jsonData = response.jsonPath().getString(path);
		return jsonData;
	}

	public Response get() {
		return given().log().all().get(BaseTest.baseUrl);
	}


	public static RequestSpecification getWithParam() {
		try {
			Map<String, String> map = new HashMap<String,String>();
			map.put("key", prop_constants.getProperty("key"));
			map.put("token", prop_constants.getProperty("token"));
			return given().queryParams(map);
		}catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the Key and Token");
			Assert.fail();
		}
		return requestSpecification;
	}

	//	public static void verifyStatusCode(Response response) {
	//		try {
	//		assertEquals(String.valueOf(response.getStatusCode()).startsWith("20"), true,
	//				"value of status code is" + response.getStatusCode());
	//		}catch (Exception e) {
	//			e.printStackTrace();
	//			error("Unable to verify status code");
	//		}
	//	}

	public static void validateStatusCode(Response response,int status_code) {
		try {
			response.then().assertThat().statusCode(status_code);
			extentinfo("Status code is: "+status_code);
			info("Status code is: "+status_code);
		}catch (Exception |AssertionError e) {
			e.printStackTrace();
			error(e.getMessage());
			fail("Unable to validate status code");
			Assert.fail("Unable to validate status code");

		}
	}
	public static void jsonSchemaValidation(Response response,String jsonPath) {
		try {
			response.then().assertThat().body(matchesJsonSchemaInClasspath(jsonPath));
			extentinfo("Json Schema Validation pass ");
			info("Json Schema Validation pass ");
		}catch (Exception |AssertionError e) {
			e.printStackTrace();
			error(e.getMessage());
			fail("Json Schema Validation fail");
			Assert.fail("Json Schema Validation fail");
		}
	}

	public static void validateResponseTime(Response response, long time) {
		try {
			response.then().assertThat().time(Matchers.lessThan(time),TimeUnit.SECONDS);
			extentinfo("Response time is less than: ");
			info("Response time is less than: ");
		}catch (Exception |AssertionError e) {
			e.printStackTrace();
			error(e.getMessage());
			fail("Unable to validate status code");
			Assert.fail("Unable to validate status code");
		}
	}

	private static void validateContentType(Response response,String contentType) {
		try {
			response.then().assertThat().contentType(contentType);
			extentinfo("Content type is: "+contentType);
			info("Content type is: "+contentType);
		}catch (Exception |AssertionError e) {
			e.printStackTrace();
			error(e.getMessage());
			fail("Unable to validate content type");
			Assert.fail("Unable to validate content type");
		}
	}


	public static void verifyFailStatusCode(Response response) {
		try {
			assertEquals(String.valueOf(response.getStatusCode()).startsWith("40"), true,
					"value of status code is" + response.getStatusCode());
		}catch (Exception e) {
			e.printStackTrace();
			error("Unable to verify status code");
		}
	}

	public static void verifyResponseBody( String expected, String description,Response response,String jsonPath) {
		try {
			String actual = response.jsonPath().get(jsonPath);
			assertEquals(actual, expected, description);
		}catch (Exception e) {
			e.printStackTrace();
			error("Unable to validate response body"+expected);
		}

	}

	public static String getResponseBodyValue(Response response,String jsonPath) {
		String actual =null;
		try {
			actual = response.jsonPath().get(jsonPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actual;

	}

	public synchronized static RequestSpecification passQuerryParm() {
		RequestSpecification requestSpecification = null;
		try {
			requestSpecification= requestSpecBuilder.build();
			requestSpecification=given().spec(requestSpecification).queryParam(prop_constants.getProperty("key"), prop_constants.getProperty("token"));
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return requestSpecification;
	}
	public synchronized static Response putMethod1(String endpoint,int status_code,String pathValue) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.addPathParam("id",pathValue).build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).when().put(endpoint).then().log().all().extract().response();
			response.then().assertThat().statusCode(status_code);
			extentinfo("Get details successfully");
			info("Get details successfully");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	public synchronized static Response getMethodWithPathParam(String endpoint,int status_code,String pathValue) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.addPathParam("id",pathValue).build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).when().get(endpoint).then().log().all().extract().response();
			response.then().assertThat().statusCode(status_code);
			extentinfo("Get details successfully");
			info("Get details successfully");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	public synchronized static Response getMethod2(String endpoint,int status_code) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).when().get(endpoint).then().log().all().extract().response();
			response.then().assertThat().statusCode(status_code);
			extentinfo("Get details successfully");
			info("Get details successfully");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	public synchronized static Response getMethod(String endpoint,int status_code,String pathValue) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).when().get(endpoint+pathValue).then().log().all().extract().response();
			response.then().assertThat().statusCode(status_code);
			extentinfo("Get details successfully");
			info("Get details successfully");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}
	public synchronized static Response getMethodWithQueryParam(String endpoint,int status_code,String queryKey,String queryValue) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).queryParam(queryKey,queryValue).when().get(endpoint).then().log().all().extract().response();
			response.then().assertThat().statusCode(status_code);
			extentinfo("Get details successfully");
			info("Get details successfully");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}




	public synchronized static Response getMethodWithParam(HashMap<String, String> pathParamMap,HashMap<String, String> querParamMap, String endpoint,String status_code,String contentType) {
		Response response;
		try {
			//     		requestSpecification= requestSpecBuilder.build();
			requestSpecification= requestSpecBuilder.addPathParams(pathParamMap).addQueryParams(querParamMap).build();
			response=given().spec(requestSpecification).when().get(endpoint).then().log().all().extract().response();
			extentinfo("Updated to get the details");
			info("Updated to get the details");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}



	public static  Map<String, String> data(String key, String value) {
		Map<String, String> map = new HashMap<String,String>();
		map.put(key, value);     
		return map;	
	}

	public synchronized static Response putMethod(String endpoint,int status_code,String queryKey,String queryValue) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).queryParam(queryKey,queryValue).when().put(endpoint).then().log().all().extract().response();
			response.then().assertThat().statusCode(status_code);
			extentinfo("Updated Successfully");
			info("Updated Successfully");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to Update");
			Assert.fail();
		}
		return null;
	}

	public synchronized static Response putMethodWithPathParam(HashMap<String, String> pathParamMap,HashMap<String, String> querParamMap, String endpoint,String status_code,String contentType) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.addPathParams(pathParamMap).addQueryParams(querParamMap).build();
			response=given().spec(requestSpecification).when().put(endpoint).then().log().all().extract().response();

			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}

	public synchronized static Response postMethod(String endpoint,int status_code,String queryKey,String queryValue) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).queryParam(queryKey,queryValue).post(endpoint).then().log().all().extract().response();
			extentinfo("Unable to post the details");
			info("Unable to post the details");	
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to post the details");
			Assert.fail();
		}
		return null;
	}

	public synchronized static Response postMethodWithPathParam(HashMap<String, String> pathParamMap,HashMap<String, String> querParamMap,String endpoint,String status_code,String contentType) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.addPathParams(pathParamMap).addQueryParams(querParamMap).build();
			response=given().spec(requestSpecification).contentType(ContentType.JSON).post(endpoint).then().log().all().extract().response();
			//			validateStatusCode(response, status_code);
			//			validateContentType(response, contentType);
			//			validateResponseTime(response);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}

	public synchronized static Response deleteMethodWithQueryParam(String endpoint,int status_code,String queryKey,String queryValue) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).queryParam(queryKey,queryValue).when().delete(endpoint).then().log().all().extract().response();
			response.then().assertThat().statusCode(status_code);
			extentinfo("Get details successfully");
			info("Get details successfully");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}

	public synchronized static Response deleteMethodWithPathParam(String endpoint,int status_code,String pathValue) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.addPathParam("id",pathValue).build();
			response=given().spec(requestSpecification).auth().oauth2(accessToken).when().delete(endpoint).then().log().all().extract().response();
			response.then().assertThat().statusCode(status_code);
			extentinfo("Get details successfully");
			info("Get details successfully");
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}

	public synchronized static Response getMethodforAuthorization(HashMap<String, String> querParamMap,String endpoint,String status_code,String contentType) {
		Response response;
		try {
			//     		requestSpecification= requestSpecBuilder.addQueryParam("key",prop_constants.getProperty("key")).addQueryParam("token", prop_constants.getProperty("token")).build();
			requestSpecification= requestSpecBuilder.addQueryParams(querParamMap).build();
			response=given().spec(requestSpecification).when().get(endpoint).then().log().all().extract().response();
			//			validateStatusCode(response, status_code);
			//			validateContentType(response, contentType);
			//			validateResponseTime(response);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}

	public synchronized static Response postMethodforAuthroization(HashMap<String, String> querParamMap,String endpoint,String status_code,String contentType) {
		Response response;
		try {
			requestSpecification= requestSpecBuilder.build();
			response=given().spec(requestSpecification).formParams(querParamMap).post(endpoint).then().log().all().extract().response();
			//			validateStatusCode(response, status_code);
			//			validateContentType(response, contentType);
			//			validateResponseTime(response);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			error("Unable to get the details");
			Assert.fail();
		}
		return null;
	}

}
