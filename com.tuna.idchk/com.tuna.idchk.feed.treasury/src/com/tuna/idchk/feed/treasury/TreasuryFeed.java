package com.tuna.idchk.feed.treasury;

import java.util.List;

import com.tuna.idchk.db.api.IdRecord;
import com.tuna.idchk.feed.api.IFeed;

public class TreasuryFeed implements IFeed{

	public TreasuryFeed(){
		System.out.println("TreasuryFeed is created");
	}
	
	@Override
	public String getFeedName() {
		return "Treasury";
	}

	@Override
	public void updateFeed(String url) {
		
	}

	@Override
	public List<IdRecord> retrieveIdrecords(int page, int rowCount) {
		return null;
	}

}
