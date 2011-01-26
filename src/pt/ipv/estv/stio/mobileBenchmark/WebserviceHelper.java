package pt.ipv.estv.stio.mobileBenchmark;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Calendar;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


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

		
		public void startElement(String namespaceURI, String localName,
                        String qName, Attributes atts) throws SAXException {
        };
		
		private ParseResult parseXml(int recordCount) {
			
			ParseResult result = new ParseResult();
			
			//build request URL
			StringBuilder requestUrl=new StringBuilder(serverUrl);
			requestUrl.append("/persons/format/xml/count/")
					   .append(recordCount);
			
			//only for getting response size, not used for parsing
			String response = callWebService(requestUrl.toString());
			result.setRequestSize(response.length());
			
			//create the temporary persons collection
			ArrayList<Person> temporaryCollection = new ArrayList<Person>();
		
			
			try{
				//initialize the XML Document
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(requestUrl.toString());
				doc.getDocumentElement().normalize();
				
				
				NodeList nodeList = doc.getElementsByTagName("item");			
				
				//get the time stamp before starting the parsing
				long startTime = Calendar.getInstance().getTimeInMillis();
				for (int i = 0; i < nodeList.getLength(); i++) {

					//crate the person
					Person temporaryPerson = new Person();					
					

					//Parse the person
					Node node = nodeList.item(i);

					Element firstElement = (Element) node;					
					NodeList idList = firstElement.getElementsByTagName("id");
					Element idElement = (Element) idList.item(0);
					idList = idElement.getChildNodes();
					int id=Integer.parseInt(((Node)idList.item(0)).getNodeValue());		
					
					NodeList ageList = firstElement.getElementsByTagName("age");
					Element ageElement = (Element) ageList.item(0);
					ageList = ageElement.getChildNodes();
					int age=Integer.parseInt(((Node)ageList.item(0)).getNodeValue());	
					

					NodeList nameList = firstElement.getElementsByTagName("name");
					Element nameElement = (Element) nameList.item(0);
					nameList = nameElement.getChildNodes();
					String name=((Node) nameList.item(0)).getNodeValue();
					
					NodeList phoneList = firstElement.getElementsByTagName("phone");
					Element phoneElement = (Element) phoneList.item(0);
					phoneList = phoneElement.getChildNodes();
					String phone=((Node) phoneList.item(0)).getNodeValue();

					
					
					//fill the person object
					temporaryPerson.setId(id);
					temporaryPerson.setName(name);
					temporaryPerson.setPhone(phone);
					temporaryPerson.setAge(age);					
					//add person object to collection
					temporaryCollection.add(temporaryPerson);
					
				}
				//get the time stamp after ending the parsing
				Calendar cal = Calendar.getInstance();
				result.setTime( cal.getTimeInMillis() - startTime);		
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return result;
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
				//get the time stamp after ending the parsing
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
