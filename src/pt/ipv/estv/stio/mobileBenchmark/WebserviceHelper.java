package pt.ipv.estv.stio.mobileBenchmark;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class WebserviceHelper {

		private String serverUrl="http://192.168.1.67/restserver/api/benchmark/";
	
	
		public WebserviceHelper() {
			
					
		}
		
		public ParseResult parseRequest(WebserviceFormat format, int recordCount)
		{
			switch(format)
			{
			case JSON:
				return parseJson(recordCount);
				
			case XML:
				return parseXml(recordCount);				
			}
			return null;
		}

		private ParseResult parseXml(int recordCount) {
			// TODO Auto-generated method stub
			return null;
		}

		private ParseResult parseJson(int recordCount) {
			
			ParseResult result = new ParseResult();
			
			//build request URL
			StringBuilder requestUrl=new StringBuilder(serverUrl);
			requestUrl.append("/persons/format/json/count/")
					   .append(recordCount);
			
			String response = callWebService(requestUrl.toString());
			Log.d("resposta",response);			
			
			
			return result;		
			
		}
		
		private String callWebService(String requestUrl){     	
	    	
	    	String result="";
	        HttpClient httpclient = new DefaultHttpClient();  
	        HttpGet request = new HttpGet(requestUrl);  
	        
	        ResponseHandler<String> handler = new BasicResponseHandler();  
	        try {  
	            result = httpclient.execute(request, handler);  
	        } catch (ClientProtocolException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        httpclient.getConnectionManager().shutdown();  
	        return result;
	        
	    }
	
}
