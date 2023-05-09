package com.nashtech.tshape.base;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.aventstack.extentreports.markuputils.Markup;

import okhttp3.MultipartBody.Builder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIRequest{

	enum Method {
		GET, POST, PUT, DELETE,PATCH;
	}

	private final OkHttpClient httpClient;

	private String baseUrl;
	private String path;
	public String fullUrl;

	private RequestBody bodyEntity;
	private Request request;

//	private Map<String, String> parameters = new HashMap<String, String>();
	private String paramaters = "";
	private Map<String, String> headers = new HashMap<String, String>();
	public String jsonBody;
	private Builder multipartBuilder;
	private List<NameValuePair> formData;
	
	
	public APIRequest() {
		httpClient = new OkHttpClient().newBuilder().connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS).writeTimeout(120, TimeUnit.SECONDS).build();
	}

	public APIRequest baseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
		return this;
	}

	public APIRequest path(String path) {
		this.path = path;
		return this;
	}

	public APIRequest addParam(String key, String value) throws UnsupportedEncodingException {
		
		if(paramaters.equals("")) {
			paramaters += "?" + key + "=" + URLEncoder.encode(value,"UTF-8");
		}
		else {
			paramaters += "&" + key + "=" + URLEncoder.encode(value,"UTF-8");
		}
		return this;
	}
	
	public APIRequest addParam(String key, int[] values) {
		
		if(paramaters.equals("")) {
			paramaters = "?";
		}
		
		StringJoiner joiner = new StringJoiner("&");
		for(int i = 0; i< values.length; i++) {
			joiner.add(String.join("=", key, String.valueOf(values[i])));
		}
		
		paramaters += joiner.toString();
		return this;
	}

	public APIRequest addHeader(String key, String value) {
		headers.put(key, value);
		return this;
	}

	public APIRequest basic(String username, String password) {
		String key = String.join(":", username, password);
		String encode = Base64.getUrlEncoder().encodeToString(key.getBytes());
		headers.put(HttpHeaders.AUTHORIZATION, "Basic " + encode);
		return this;
	}

	public APIRequest oauth2(String accessToken) {
		headers.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
		return this;
	}

	public APIRequest body(String body) {
		this.jsonBody = body;
		return this;
	}

	public APIRequest addFormData(String key, String value) {
		if (formData == null) {
			formData = new ArrayList<NameValuePair>();
		}

		formData.add(new BasicNameValuePair(key, value));
		return this;
	}

	public APIRequest addFormDataPart(String name, String value) {
		if (multipartBuilder == null) {
			multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		}
		multipartBuilder.addFormDataPart(name, value);
		return this;
	}

	public APIRequest addFormDataPart(String name, String filepath, String fileType) {
		if (multipartBuilder == null) {
			multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		}
		multipartBuilder.addFormDataPart(name, filepath,
				RequestBody.create(MediaType.parse(fileType), new File(filepath)));
		return this;
	}

	private String urlBuilder() {
		fullUrl = baseUrl + path + paramaters;
		return fullUrl;
	}

	private RequestBody bodyBuilder() throws UnsupportedEncodingException {

		if (bodyEntity == null && jsonBody != null) {
			//bodyEntity = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody);
			bodyEntity = RequestBody.create(null, jsonBody);
			return bodyEntity;
		}

		if (bodyEntity == null && multipartBuilder != null) {
			bodyEntity = multipartBuilder.build();
			return bodyEntity;
		}

		return null;

	}

	private APIResponse execute(Method method) throws Exception {

		okhttp3.Request.Builder builder = new Request.Builder();
		RequestBody body = bodyBuilder();

		for (Entry<String, String> header : headers.entrySet()) {
			builder.addHeader(header.getKey(), header.getValue());
		}

		switch (method) {
			case GET:
				request = builder.url(urlBuilder()).get().build();
				break;
	
			case POST:
				if (body != null) {
					request = builder.url(urlBuilder()).post(body).build();
				} else {
					request = builder.url(urlBuilder()).post(RequestBody.create(null, "")).build();
				}
				break;
	
			case PUT:
				if (body != null) {
					request = builder.url(urlBuilder()).put(body).build();
				} else {
					request = builder.url(urlBuilder()).put(RequestBody.create(null, "")).build();
				}
				break;
			case PATCH:
				if (body != null) {
					request = builder.url(urlBuilder()).patch(body).build();
				} else {
					request = builder.url(urlBuilder()).patch(RequestBody.create(null, "")).build();
				}
				break;
	
			case DELETE:
				if (body != null) {
					request = builder.url(urlBuilder()).delete(body).build();
				} else {
					request = builder.url(urlBuilder()).delete().build();
				}
				break;
	
			default:
				throw new Exception(String.format("This method [%s] has not been implemented", method.name()));
		}
		Response response = httpClient.newCall(request).execute();
		Thread.sleep(3000);
        System.out.println(String.format("[%s] [%s]", method, request.url()));
        //initResponseValidation();
		
		return new APIResponse(response, this);
	}

	public APIResponse get() throws Exception {
		return execute(Method.GET);
	}

	public APIResponse post() throws Exception {
		return execute(Method.POST);
	}
	
	public APIResponse patch() throws Exception {
		return execute(Method.PATCH);
	}

	public APIResponse put() throws Exception {
		return execute(Method.PUT);
	}

	public APIResponse delete() throws Exception {
		return execute(Method.DELETE);
	}
	

}