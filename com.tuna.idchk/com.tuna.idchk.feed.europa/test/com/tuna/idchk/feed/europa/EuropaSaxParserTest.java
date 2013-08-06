package com.tuna.idchk.feed.europa;

import org.junit.Before;
import org.junit.Test;

import com.tuna.idchk.db.api.IdRecord;

public class EuropaSaxParserTest {
	
	private EuropaSaxParser parser;
	
	@Before
	public void init(){
		parser = new EuropaSaxParser();
	}
	
	@Test
	public void testCreateRecords(){
		parser.createRecords("http://ec.europa.eu/external_relations/cfsp/sanctions/list/version4/global/global.xml", new EuropaSaxParser.ISaveAction() {
			
			@Override
			public void save(IdRecord record) {
				System.out.println("Save action called: "+record);
			}
		});
	}
}
