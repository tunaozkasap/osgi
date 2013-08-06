package com.tuna.idchk.feed.sanction;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.tuna.idchk.db.api.IdRecord;
import com.tuna.idchk.feed.api.FeedStreamUtil;
import com.tuna.idchk.feed.sanction.api.SmoothExceptionsFeedSanction;

public class SanctionCsvParser {
	
	public static interface ISaveAction{
		public void save(IdRecord record);
	}
	
	private interface IUseLineAction{
		public void useLine(String line);
	}
	
	public void createRecords(String csvUrl, final ISaveAction action){
		FeedStreamUtil.createAndUseStream(csvUrl, new FeedStreamUtil.IUseStreamAction() {
			
			@Override
			public void useStream(InputStream stream) {
				readAndUseLines(stream, new IUseLineAction() {
					
					@Override
					public void useLine(String line) {
						createRecordAndSave(line, action);
					}

				});
			}
		});
	}
	
	private void createRecordAndSave(String line, ISaveAction action) {
		String[] split = line.split("[,]");
		IdRecord record = new IdRecord();
		if(split.length == 29){			
			record.birthDate = this.parseDate(split[7]);
			record.name = this.extractName(split);
			record.surname = this.extractSurname(split);
			record.nationality = split[10];
			record.citizenId = this.extractCitizenId(split[12]);
			record.country = split[21];
			action.save(record);
		}
	}
	

	private void readAndUseLines(InputStream stream, IUseLineAction action){
		try{
			LineByteArray lb = new LineByteArray(1024);
			byte cb = -1;
			while((cb = (byte)stream.read()) != -1){
				lb.add(cb);
				if(cb == 13){
					//read the line feed char also
					stream.read();
					action.useLine(new String(lb.lineBytes));
					lb.reset(1024);
				}
			}
		}catch(Exception ex){
			SmoothExceptionsFeedSanction.feedUrlStreamReadFailed(ex);
		}
	}
	
	private Date parseDate(String dateStr){
		if(dateStr != null){			
			try {
				return DateUtils.parseDate(dateStr, new String[]{"dd/MM/yyyy"});
			} catch (ParseException e) {
				return new Date();
			}
		}else{
			return new Date();
		}
	}
	
	private String extractSurname(String[] split) {
		return split[0];
	}

	private String extractName(String[] split) {
		StringBuilder builder = new StringBuilder();
		for(int i=1;i<6;i++){
			builder.append((!StringUtils.isEmpty(split[i]))?(((builder.length() > 0)?" ":"")+split[i]):"");
		}
		return builder.toString();
	}
	
	private Pattern citizenIdPattern = Pattern.compile("[0-9\\.-/]+");
	private String extractCitizenId(String idStr) {
		Matcher matcher = citizenIdPattern.matcher(idStr);
		if(matcher.find()){
			return matcher.group();
		}
		return null;
	}
}
