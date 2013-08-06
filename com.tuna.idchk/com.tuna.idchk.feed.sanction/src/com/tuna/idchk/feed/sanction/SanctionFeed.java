package com.tuna.idchk.feed.sanction;

import java.util.List;

import com.tuna.idchk.db.api.IdRecord;
import com.tuna.idchk.feed.api.IFeed;

public class SanctionFeed implements IFeed{

	public SanctionFeed(){
		System.out.println("SanctionFeed is created");
	}
	
	@Override
	public String getFeedName() {
		return "Sanction";
	}

	@Override
	public void updateFeed(String url) {
	}

	@Override
	public List<IdRecord> retrieveIdrecords(int page, int rowCount) {
		return null;
	}

}
