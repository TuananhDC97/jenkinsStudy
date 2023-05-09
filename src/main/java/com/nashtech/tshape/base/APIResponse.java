package com.nashtech.tshape.base;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nashtech.tshape.reports.HtmlReporter;

import okhttp3.Response;

public class APIResponse {

	private String responseInString;
	private JSONObject responseInJsonObject;
	public JSONArray bodyArray;
	public JSONObject bodyObject;
	public Response response;
	private int statusCode;

	public APIResponse(Response response, APIRequest request) throws IOException {
		responseInString = response.body().string();
		statusCode = response.code();
		if(String.valueOf(responseInString.charAt(0)).equals("[")) {
			bodyArray = new JSONArray(responseInString);
		}
		if (String.valueOf(responseInString.charAt(0)).equals("{")){
			bodyObject = new JSONObject(responseInString);
		}
		HtmlReporter.createAPIRequestBlock(request, this);
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public JSONObject getResponseBodyInJson() {
		return this.responseInJsonObject;
	}

	public String getStringResponse(){
		return responseInString;
	}

	public String getValueFromResponse(String keychains) throws Exception {
		//JsonParser jsonParser = new JsonParser(responseInJsonObject);
		//return jsonParser.extractJsonValue(keychains);
		return "";
	}

}
