package com.tuna.idchk.feed.api;

import java.util.HashMap;
import java.util.Map;

import com.tuna.essence.exception.api.SmoothException;
import com.tuna.essence.exception.api.SmoothException.ExceptionInfo;

public class SmoothExceptionsFeed {
	public static final String MODULE_NAME = "IDCHK_FEED_MODULE";
	
	public static final String WRONG_FEED_URL = "com.tuna.idchk.feed.sanction.wrongFeedUrl";
	public static final String FEED_URL_STREAM_FAILED = "com.tuna.idchk.feed.sanction.feedUrlStreamFailed";
	
	public static void wrongFeedUrl(Exception ex){
		Map<String, Object> exDetails = new HashMap<String, Object>();
		throw new SmoothException(ei(WRONG_FEED_URL, exDetails), ex);
	}
	
	public static void feedUrlStreamFailed(Exception ex){
		Map<String, Object> exDetails = new HashMap<String, Object>();
		throw new SmoothException(ei(FEED_URL_STREAM_FAILED, exDetails), ex);
	}
	
	private static ExceptionInfo ei(String bundleKey, Map<String, Object> exDetails){
		return new ExceptionInfo(bundleKey, MODULE_NAME, exDetails);
	}
}
