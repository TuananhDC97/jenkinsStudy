package com.nashtech.tshape.reports;

import com.aventstack.extentreports.markuputils.Markup;
import com.nashtech.tshape.base.APIRequest;
import com.nashtech.tshape.base.APIResponse;

public class APIRequestSteps implements Markup {
	private APIRequest request;
	private APIResponse response;
	
	public APIRequestSteps(APIRequest request, APIResponse response) {
		this.request = request;
		this.response = response;
	}
	
	public String getMarkup() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p> URL: " + request.fullUrl + "</p>");
		sb.append("<p> Request Body : " + request.jsonBody + "</p>");
		sb.append("<p> Response code : " + response.getStatusCode() + "</p>");
		sb.append("<p> Response body : " + response.getStringResponse() + "</p>");
		return sb.toString();
	}
}
