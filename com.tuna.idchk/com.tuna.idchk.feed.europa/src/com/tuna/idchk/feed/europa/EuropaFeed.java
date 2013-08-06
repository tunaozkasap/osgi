package com.tuna.idchk.feed.europa;

import java.util.List;

import com.tuna.idchk.db.api.IdRecord;
import com.tuna.idchk.feed.api.IFeed;

public class EuropaFeed implements IFeed{

	public EuropaFeed(){
		System.out.println("EuropaFeed is created");
	}
	
	@Override
	public String getFeedName() {
		return "Europa";
	}

	@Override
	public void updateFeed(String url) {
		
	}

	@Override
	public List<IdRecord> retrieveIdrecords(int page, int rowCount) {
		return null;
	}

}
