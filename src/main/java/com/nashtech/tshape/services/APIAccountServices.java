package com.nashtech.tshape.services;

import com.nashtech.tshape.base.APIRequest;
import com.nashtech.tshape.base.APIResponse;
import com.nashtech.tshape.base.BaseSetup;
import com.nashtech.tshape.base.CommonFunctions;
import com.nashtech.tshape.base.Constants;

public class APIAccountServices extends BaseSetup {
	APIRequest request = new APIRequest();
	public String userID;
	public String userName;
	public String token;
	
	/** 
	Return create new user response status code
	@author tuananhle_SD4748
	@return int
	*/
	public int createNewUser(String url, String path, String username) throws Exception {
		APIResponse response = postWithBody(url, path, username);
		this.userID = getKeyValueFromResponseBodyObject(response, Constants.KEY_USERID);
		this.userName = getKeyValueFromResponseBodyObject(response, Constants.KEY_USERNAME);
		System.out.println(userID);
		System.out.println(userName);
		return response.getStatusCode();
	}
	
	/** 
	Return generate token response status code
	@author tuananhle_SD4748
	@return int
	*/
	public int generateToken(String url, String path, String username) throws Exception {
		APIResponse response = postWithBody(url, path, username);
		return response.getStatusCode();
	}
	
	/** 
	Return authorize response status code
	@author tuananhle_SD4748
	@return int
	*/
	public int authorize(String url, String path, String username) throws Exception {
		APIResponse response = postWithBody(url, path, username);
		return response.getStatusCode();
	}
	
	/** 
	Return user information response status code
	@author tuananhle_SD4748
	@return int
	*/
	public int getUserInfo(String url, String path, String username) throws Exception {
		APIResponse response = getWithBasic(url, path, username, Constants.PASSWORD);
		return response.getStatusCode();
	}
	
	/** 
	Return json body to implement request
	@author tuananhle_SD4748
	@return String
	*/
	private String jsonUserBody(String userName) {
		return "{\"userName\":" + "\"" + userName  + "\"" + ",\"password\":" + "\"" + Constants.PASSWORD + "\"}";
	}
	
	/** 
	Post API request that have json body
	@author tuananhle_SD4748
	@return APIResponse
	*/
	private APIResponse postWithBody(String url, String path, String userName) throws Exception {
		return request.baseUrl(url)
				.path(path)
				.addHeader("Accept", "application/json")
				.addHeader("Content-Type", "application/json")
				.body(jsonUserBody(userName))
				.post();
	}
	
	/**
	Get API request that have basic authorize
	@author tuananhle_SD4748
	@return APIResponse
	*/
	private APIResponse getWithBasic(String url, String path, String username, String password) throws Exception {
		return request.baseUrl(url)
				.path(path)
				.addHeader("Accept", "application/json")
				.addHeader("Content-Type", "application/json")
				.basic(username, password)
				.get();
	}
	
	/** 
	Get value from object response body
	@author tuananhle_SD4748
	@return String
	*/
	private String getKeyValueFromResponseBodyObject(APIResponse response, String key) {
		return response.bodyObject.getString(key);
	}
}
