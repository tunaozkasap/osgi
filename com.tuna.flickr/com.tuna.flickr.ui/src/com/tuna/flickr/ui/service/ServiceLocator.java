package com.tuna.flickr.ui.service;

import com.tuna.flickr.srv.api.IFlickrService;

public class ServiceLocator {
	
	private IFlickrService flickrService;

	public IFlickrService getFlickrService() {
		return flickrService;
	}

	public void setFlickrService(IFlickrService flickrService) {
		this.flickrService = flickrService;
	}
	
}
