package com.tuna.idchk.feed.europa.api;

import java.util.HashMap;
import java.util.Map;

import com.tuna.essence.exception.api.SmoothException;
import com.tuna.essence.exception.api.SmoothException.ExceptionInfo;

public class SmoothExceptionsFeedEuropa {
	public static final String MODULE_NAME = "IDCHK_FEED_EUROPA_MODULE";

	public static final String SAX_PARSER_CREATION_FAILED = "com.tuna.idchk.feed.europa.saxParserCreationFailed";

	public static void saxParserCreationFailed(Exception ex){
		Map<String, Object> exDetails = new HashMap<String, Object>();
		throw new SmoothException(ei(SAX_PARSER_CREATION_FAILED, exDetails), ex);
	}

	private static ExceptionInfo ei(String bundleKey, Map<String, Object> exDetails){
		return new ExceptionInfo(bundleKey, MODULE_NAME, exDetails);
	}
}
