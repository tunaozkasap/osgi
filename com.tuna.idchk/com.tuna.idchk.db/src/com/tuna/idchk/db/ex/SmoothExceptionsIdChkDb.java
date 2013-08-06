package com.tuna.idchk.db.ex;

import java.util.HashMap;
import java.util.Map;

import com.tuna.essence.exception.api.SmoothException;
import com.tuna.essence.exception.api.SmoothException.ExceptionInfo;

public class SmoothExceptionsIdChkDb {
	public static final String MODULE_NAME = "IDCHK_DB_MODULE";

	public static final String DB_CONNECTION_FAILED = "com.tuna.idchk.db.connectionFailed";
	public static final String DB_DRIVER_LOAD_FAILED = "com.tuna.idchk.db.driverLoadFailed";

	public static void connectionFailed(Exception ex){
		Map<String, Object> exDetails = new HashMap<String, Object>();
		throw new SmoothException(ei(DB_CONNECTION_FAILED, exDetails), ex);
	}
	
	public static void driverLoadFailed(String msg, Exception ex){
		Map<String, Object> exDetails = new HashMap<String, Object>();
		exDetails.put("msg", msg);
		throw new SmoothException(ei(DB_DRIVER_LOAD_FAILED, exDetails), ex);
	}

	private static ExceptionInfo ei(String bundleKey, Map<String, Object> exDetails){
		return new ExceptionInfo(bundleKey, MODULE_NAME, exDetails);
	}
}
