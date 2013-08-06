package com.tuna.idchk.ui.service;

import java.util.List;

import org.osgi.service.event.EventAdmin;

import com.tuna.idchk.feed.api.IFeed;

public class ServiceLocator {
	public static EventAdmin eventAdmin;
	public static List<IFeed> feedList;

	public void setEventAdmin(EventAdmin eventAdmin) {
		ServiceLocator.eventAdmin = eventAdmin;
	}

	public void setFeedList(List<IFeed> feedList) {
		ServiceLocator.feedList = feedList;
	}
}
