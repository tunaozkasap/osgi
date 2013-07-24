package com.tuna.flickr.srv;

import java.util.Properties;

public class Configuration{
	
	public String proxyHost;
	public Integer proxyPort;
	public String proxyUsername;
	public String proxyPassword;
	public String requestHost;
	public Integer requestPort;
	public String requestFormat;
	public String apiKey;
	public String apiSecret;
	
	public void loadProps(Properties props) {
		proxyHost = props.getProperty("flickr.api.proxy.host");
		proxyPort = intVal(props, "flickr.api.proxy.port");
		proxyUsername = props.getProperty("flickr.api.proxy.username");
		proxyPassword = props.getProperty("flickr.api.proxy.userpass");
		requestHost = props.getProperty("flickr.api.request.host");
		requestPort = intVal(props, "flickr.api.request.port");
		requestFormat = props.getProperty("flickr.api.request.format");
		apiKey = props.getProperty("flickr.api.key");
		apiSecret = props.getProperty("flickr.api.secret");
	}
	
	private Integer intVal(Properties props, String propertyName){
		String val = props.getProperty(propertyName);
		if(val != null){
			return Integer.parseInt(val);
		}
		return null;
	}
}
