package com.tuna.flickr.srv;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuna.flickr.srv.api.IFlickrService;
import com.tuna.flickr.srv.api.SmoothExceptionsFlickr;

public class DefaultFlickrService implements IFlickrService{
	private DefaultHttpClient httpClient;
	private ObjectMapper jsonMapper;
	private Configuration conf;
	
	public DefaultFlickrService(){
		System.out.println("IN DefaultFlickrService");
		jsonMapper = new ObjectMapper();
	}
	
	public DefaultFlickrService init(){
		httpClient = createHttpClient();
		return this;
	}
	
	private DefaultHttpClient createHttpClient(){
        PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();
        connectionManager.setMaxTotal(10); 
        return wrapWithProxyInfo(new DefaultHttpClient(connectionManager));
	}
	
	private DefaultHttpClient wrapWithProxyInfo( DefaultHttpClient httpClient ){
		if ( conf.proxyHost != null ) {
			//logService.log(LogService.LOG_INFO, "Wrapping with proxyHost and proxyPort info");
            httpClient.getParams().setParameter( ConnRoutePNames.DEFAULT_PROXY, new HttpHost( conf.proxyHost, conf.proxyPort ) );
        }
        if ( conf.proxyUsername != null ) {
        	//logService.log(LogService.LOG_INFO, "Wrapping with proxyUsername and proxyPassword info");
            httpClient.getCredentialsProvider().setCredentials( new AuthScope( conf.proxyHost, conf.proxyPort ),
                    new UsernamePasswordCredentials( conf.proxyUsername, conf.proxyPassword ) );
        }
        return httpClient;
	}
	
	private HttpResponse getRequest(Map<String, String> getParams){
		try {
			HttpHost host = new HttpHost(conf.requestHost, conf.requestPort);
			HttpGet get = new HttpGet();
			URIBuilder builder = new URIBuilder("/services/rest/");
			Set<String> keySet = getParams.keySet();
			for (String key : keySet) {
				builder.addParameter(key, getParams.get(key));
			}
			builder.addParameter("api_key", conf.apiKey);
			builder.addParameter("format", "json");
			builder.addParameter("nojsoncallback", "1");
			get.setURI(builder.build());
			return httpClient.execute(host, get);
		} catch (Exception ex) {
			SmoothExceptionsFlickr.httpRequestFailed(ex);
			return null;
		}
	}
	
	private Map<String, Object> toJson(HttpResponse resp){		
		try {
			Map jsonMap = jsonMapper.readValue(resp.getEntity().getContent(), Map.class);
			return jsonMap;
		} catch (Exception ex) {
			SmoothExceptionsFlickr.jsonDeserializationFailed(ex);
			return null;
		}
	}
	
	public Map<String, Object> getBrands(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("method", "flickr.cameras.getBrands");
		HttpResponse resp = this.getRequest(params);
		return toJson(resp);
	}

	public DefaultFlickrService setConf(Configuration conf) {
		this.conf = conf;
		return this;
	}
}
