package pt.ipv.estv.stio.mobileBenchmark;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.text.method.DateTimeKeyListener;
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
			result.setRequestSize(response.length());
			
			try{
				//create the temporary persons collection
				ArrayList<Person> temporaryCollection = new ArrayList<Person>();				
				//parse the JSON response
				JSONArray arrayObject = new JSONArray(response); 		
				int objectNumber = arrayObject.length();
				
				
				
				//get the time stamp before starting the parsing
				long startTime = Calendar.getInstance().getTimeInMillis();
				
				for(int i=0;i<objectNumber;i++)
				{
					//build the Person object
					Person temporaryPerson = new Person();
					
					JSONObject currentObject = (JSONObject) arrayObject.get(i);
					
					int id = currentObject.getInt("id");
									
					int age = currentObject.getInt("age");					
					String name = currentObject.getString("name");
					String phone = currentObject.getString("phone");
					temporaryPerson.setId(id);
					temporaryPerson.setName(name);
					temporaryPerson.setAge(age);
					temporaryPerson.setPhone(phone);					
					//add to the collection
					temporaryCollection.add(temporaryPerson);
				}
				//get the time stamp before starting the parsing
				Calendar cal = Calendar.getInstance();
				result.setTime( cal.getTimeInMillis() - startTime);		
			
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
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
