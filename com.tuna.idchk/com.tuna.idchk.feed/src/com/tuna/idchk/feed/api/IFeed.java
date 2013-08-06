package com.tuna.idchk.feed.api;

import java.util.List;

import com.tuna.idchk.db.api.IdRecord;

public interface IFeed {
	
	public String getFeedName();
	
	public void updateFeed(String url);
	
	public List<IdRecord> retrieveIdrecords(int page, int rowCount);
	
}
