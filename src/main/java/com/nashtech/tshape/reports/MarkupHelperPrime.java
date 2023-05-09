package com.nashtech.tshape.reports;

import com.nashtech.tshape.base.APIRequest;
import com.nashtech.tshape.base.APIResponse;

public class MarkupHelperPrime {

	public static APIRequestSteps createAPIRequestBlock(APIRequest request, APIResponse response) {
		return new APIRequestSteps(request, response);
	}	
}