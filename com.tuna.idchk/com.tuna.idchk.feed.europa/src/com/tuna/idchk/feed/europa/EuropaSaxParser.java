package com.tuna.idchk.feed.europa;

import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tuna.idchk.db.api.GenderEnum;
import com.tuna.idchk.db.api.IdRecord;
import com.tuna.idchk.feed.api.FeedStreamUtil;
import com.tuna.idchk.feed.europa.api.SmoothExceptionsFeedEuropa;

public class EuropaSaxParser {
	
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
					SmoothExceptionsFeedEuropa.saxParserCreationFailed(ex);
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
			if(qName.equalsIgnoreCase("ENTITY")){				
				this.tagValueMap = new HashMap<String, String>();
			}
		}
		
		public void characters(char ch[], int start, int length) throws SAXException {
			currentVal = new String(ch, start, length);
		}

		public void endElement(String uri, String localName, String qName) throws SAXException {
			String tagKey =  createTagKey(tagStack);
			tagStack.pop();
			if(qName.equalsIgnoreCase("ENTITY")){
				IdRecord record = new IdRecord();
				record.name = this.createName();
				record.surname = this.createSurname();
				record.citizenId = this.createCitizenId();
				record.passportId = this.createPassportId();
				record.country = this.createCountry();
				record.gender = this.createGender();
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

		private Pattern idPattern = Pattern.compile("[0-9\\.-/]+");
		private String createPassportId() {
			String num = tagValueMap.get("WHOLE.ENTITY.PASSPORT.NUMBER");
			if(num != null){				
				Matcher matcher = idPattern.matcher(num);
				if(matcher.find()){
					return matcher.group();
				}
			}
			return null;
		}
		
		private Date createBirthDate(){
			String dateStr = tagValueMap.get("WHOLE.ENTITY.BIRTH.DATE");
			try {
				return DateUtils.parseDate(dateStr, new String[]{"yyyy-MM-dd"});
			} catch (Exception e) {
				return null;
			}
		}
		
		private String createCountry(){
			String birthCountry = tagValueMap.get("WHOLE.ENTITY.BIRTH.COUNTRY");
			String citizenCountry = tagValueMap.get("WHOLE.ENTITY.CITIZEN.COUNTRY");
			
			return (citizenCountry != null)?citizenCountry:birthCountry;
		}

		private String createCitizenId() {
			return null;
		}

		private String createSurname() {
			return tagValueMap.get("LASTNAME");
		}

		private String createName() {
			String firstName = tagValueMap.get("WHOLE.ENTITY.NAME.FIRSTNAME");
			String middleName = tagValueMap.get("WHOLE.ENTITY.NAME.MIDDLENAME");
			return firstName+(StringUtils.isNotEmpty(middleName)?(" "+middleName):"");
		}
		
		private GenderEnum createGender(){
			String genderStr = tagValueMap.get("WHOLE.ENTITY.NAME.GENDER");
			if(genderStr != null){
				if(genderStr.equalsIgnoreCase("M")){
					return GenderEnum.MALE;
				}else{
					return GenderEnum.FEMALE;
				}
			}
			
			return null;
		}
	}
	
	
}
