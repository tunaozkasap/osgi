package com.tuna.idchk.feed.treasury;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tuna.idchk.db.api.IdRecord;
import com.tuna.idchk.feed.api.FeedStreamUtil;
import com.tuna.idchk.feed.treasury.api.SmoothExceptionsFeedTreasury;

public class TreasurySaxParser {
	public static interface ISaveAction{
		public void save(IdRecord record);
	}
	
	public void createRecords(String url, final ISaveAction action){
		FeedStreamUtil.createAndUseStream(url, new FeedStreamUtil.IUseStreamAction(){
			@Override
			public void useStream(InputStream stream) {				
				try {
					SAXParserFactory factory = SAXParserFactory.newInstance();
					SAXParser saxParser = factory.newSAXParser();
					saxParser.parse(stream, new EuropaHandler(action));
				} catch (Exception ex) {
					SmoothExceptionsFeedTreasury.saxParserCreationFailed(ex);
				}
			}
		});
	}

	private class EuropaHandler extends DefaultHandler{
		
		private ISaveAction action;
		private String currentVal;
		private Map<String, String> tagValueMap;
		private Stack<String> tagStack = new Stack<String>();
		
		public EuropaHandler(ISaveAction action){
			this.action = action;
		}
		
		public void startElement(String uri, 
				                 String localName,
				                 String qName, Attributes attributes) throws SAXException {
			tagStack.push(qName);
			if(qName.equalsIgnoreCase("sdnEntry")){				
				this.tagValueMap = new HashMap<String, String>();
			}
		}
		
		public void characters(char ch[], int start, int length) throws SAXException {
			currentVal = new String(ch, start, length);
		}

		public void endElement(String uri, String localName, String qName) throws SAXException {
			String tagKey =  createTagKey(tagStack);
			tagStack.pop();
			if(qName.equalsIgnoreCase("sdnEntry")){
				IdRecord record = new IdRecord();
				record.name = this.createName();
				record.surname = this.createSurname();
				record.citizenId = this.createCitizenId();
				record.country = this.createCountry();
				record.birthDate = this.createBirthDate();
				action.save(record);
				tagValueMap = null;
			}else{
				if(currentVal != null && tagValueMap != null){
					tagValueMap.put(tagKey, currentVal);
					currentVal = null;
				}
			}
		}
		
		private String createTagKey(Stack tagStack){
			StringBuilder builder = new StringBuilder();
			for(int i=0;i<tagStack.size();i++){
				builder.append(tagStack.get(i)).append(".");
			}
			builder.deleteCharAt(builder.length()-1);
			return builder.toString();
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
		private Date createBirthDate(){
			String dateStr = tagValueMap.get("sdnList.sdnEntry.dateOfBirthList.dateOfBirthItem.dateOfBirth");
			try {
				return dateFormat.parse(dateStr);
			} catch (Exception e) {
				return null;
			}
		}
		
		private String createCountry(){
			String idCountry = tagValueMap.get("sdnList.sdnEntry.idList.id.idCountry");
			return idCountry;
		}

		private String createCitizenId() {
			return null;
		}

		private String createSurname() {
			return tagValueMap.get("sdnList.sdnEntry.lastName");
		}

		private String createName() {
			return tagValueMap.get("sdnList.sdnEntry.firstName");
		}
	}
}
