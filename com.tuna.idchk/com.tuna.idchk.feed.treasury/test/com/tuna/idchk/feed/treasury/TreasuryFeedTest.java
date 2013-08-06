package com.tuna.idchk.feed.treasury;

import org.junit.Before;
import org.junit.Test;

import com.tuna.idchk.db.api.IdRecord;

public class TreasuryFeedTest {
    private TreasurySaxParser parser;
	
	@Before
	public void init(){
		parser = new TreasurySaxParser();
	}
	
	@Test
	public void testCreateRecords(){
		parser.createRecords("http://www.treasury.gov/ofac/downloads/sdn.xml", new TreasurySaxParser.ISaveAction() {
			
			@Override
			public void save(IdRecord record) {
				System.out.println("Save action called: "+record);
			}
		});
	}
}
