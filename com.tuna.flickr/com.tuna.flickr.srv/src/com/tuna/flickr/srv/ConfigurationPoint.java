package com.tuna.flickr.srv;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Properties;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import com.tuna.flickr.srv.api.IFlickrService;

public class ConfigurationPoint implements ManagedService{

	public static Configuration conf = new Configuration();
	public DefaultFlickrService flickrService;
	
	@Override
	public void updated(Dictionary dict) throws ConfigurationException {
		if(dict != null){
			conf.loadProps(toProps(dict));
			flickrService.setConf(conf);
			flickrService.init();
		}
	}
	
	private Properties toProps(Dictionary dict){
		Properties props = new Properties();
		Enumeration keys = dict.keys();
		while(keys.hasMoreElements()){
			Object key = keys.nextElement();
			props.setProperty(key.toString(), dict.get(key).toString());
		}
		return props;
	}

	public void setFlickrService(IFlickrService flickrService) {
		this.flickrService = (DefaultFlickrService)flickrService;
	}

}
