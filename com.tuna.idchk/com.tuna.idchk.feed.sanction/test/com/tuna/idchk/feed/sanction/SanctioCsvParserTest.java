package com.tuna.idchk.feed.sanction;

import org.junit.Before;
import org.junit.Test;

import com.tuna.idchk.db.api.IdRecord;

public class SanctioCsvParserTest {
	
	private static SanctionCsvParser parser;
	
	@Before
	public void init(){
		parser = new SanctionCsvParser();
	}
	
	@Test
	public void testCreateRecords(){
		parser.createRecords("http://hmt-sanctions.s3.amazonaws.com/sanctionsconlist.csv", new SanctionCsvParser.ISaveAction() {
			
			@Override
			public void save(IdRecord record) {
				System.out.println("Save action called: "+record);
			}
		});
	}
}
