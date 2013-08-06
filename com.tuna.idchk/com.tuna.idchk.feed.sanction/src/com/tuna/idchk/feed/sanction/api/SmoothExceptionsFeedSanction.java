package com.tuna.idchk.feed.sanction.api;

import java.util.HashMap;
import java.util.Map;

import com.tuna.essence.exception.api.SmoothException;
import com.tuna.essence.exception.api.SmoothException.ExceptionInfo;

public class SmoothExceptionsFeedSanction {
	public static final String MODULE_NAME = "IDCHK_FEED_SANCTION_MODULE";

	public static final String FEED_URL_STREAM_READ_FAILED = "com.tuna.idchk.feed.sanction.feedUrlStreamReadFailed";

	public static void feedUrlStreamReadFailed(Exception ex){
		Map<String, Object> exDetails = new HashMap<String, Object>();
		throw new SmoothException(ei(FEED_URL_STREAM_READ_FAILED, exDetails), ex);
	}

	private static ExceptionInfo ei(String bundleKey, Map<String, Object> exDetails){
		return new ExceptionInfo(bundleKey, MODULE_NAME, exDetails);
	}
}
