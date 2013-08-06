package com.tuna.idchk.ui.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.event.Event;

import com.tuna.idchk.feed.api.IFeed;
import com.tuna.idchk.ui.ui;

public class FeedServiceListener {
	
	public List<IFeed> feedList = new ArrayList<IFeed>();
	
	public void bind(IFeed feed) {
		if(feed != null){
			feedList.add(feed);
		}
		
		Map<String, Object> eventProps = new HashMap<String, Object>();
		Event event = new Event(ui.event.feedServicesUpdate, eventProps);
		ServiceLocator.eventAdmin.postEvent(event);
	}
	
	public void unbind(IFeed feed) {
		feedList.remove(feed);
	}
}
