package com.tuna.flickr.srv.api;

import java.util.HashMap;
import java.util.Map;

import com.tuna.essence.exception.api.SmoothException;
import com.tuna.essence.exception.api.SmoothException.ExceptionInfo;

public class SmoothExceptionsFlickr {
	public static final String MODULE_NAME = "FLICKR_MODULE";
	
	public static final String HTTP_REQUEST_FAILED = "com.tuna.exception.flickr.httpRequestFailed";
	public static final String JSON_DESERIALIZATION_FAILED = "com.tuna.exception.flickr.jsonDeserializationFailed";
	
	public static void httpRequestFailed(Exception ex){
		Map<String, Object> exDetails = new HashMap<String, Object>();
		throw new SmoothException(ei(HTTP_REQUEST_FAILED, exDetails), ex);
	}
	
	public static void jsonDeserializationFailed(Exception ex) {
		Map<String, Object> exDetails = new HashMap<String, Object>();
		throw new SmoothException(ei(JSON_DESERIALIZATION_FAILED, exDetails), ex);
	}
	
	private static ExceptionInfo ei(String bundleKey, Map<String, Object> exDetails){
		return new ExceptionInfo(bundleKey, MODULE_NAME, exDetails);
	}
	
}
