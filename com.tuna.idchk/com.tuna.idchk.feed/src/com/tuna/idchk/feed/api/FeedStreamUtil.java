package com.tuna.idchk.feed.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class FeedStreamUtil {

	public static interface IUseStreamAction{
		public void useStream(InputStream stream);
	}

	public static void createAndUseStream(String url, IUseStreamAction action){
		InputStream stream = null;
		try {
			stream = new URL(url).openStream();
			action.useStream(stream);
		} catch (MalformedURLException ex) {
			SmoothExceptionsFeed.wrongFeedUrl(ex);
		} catch (IOException ex) {
			SmoothExceptionsFeed.feedUrlStreamFailed(ex);
		}finally{
			if(stream != null){
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
