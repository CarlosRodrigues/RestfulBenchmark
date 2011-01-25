package pt.ipv.estv.stio.mobileBenchmark;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Benchmark extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //fill the spinner
        Spinner recordCountSpinner = (Spinner)findViewById(R.id.recordCountSpinner);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recordCountSpinner.setAdapter(spinnerArrayAdapter);
                
        for(int i=10;i<=1000;i+=10)
        {
        	spinnerArrayAdapter.add(i); 
        } 
        
        //set the listeners
        Button jsonButton = (Button)findViewById(R.id.jsonButton);
        jsonButton.setOnClickListener(jsonButtonListener);
    }
    
    
    private View.OnClickListener jsonButtonListener = new View.OnClickListener() {
	    @Override
		public void onClick(View v) {
	    	try{
	    	Spinner recordCountSpinner = (Spinner)findViewById(R.id.recordCountSpinner);	
	    	WebserviceHelper serviceHelper = new WebserviceHelper();
	    	ParseResult result= serviceHelper.parseRequest(
	    			WebserviceFormat.JSON,
	    			Integer.parseInt(
	    					recordCountSpinner.getSelectedItem().toString()
	    	));
	    	
	    	StringBuilder message = new StringBuilder();
	    	message.append("Items Parsed: ").append(Integer.parseInt(
	    					recordCountSpinner.getSelectedItem().toString()));
	    	message.append("\nTime elapsed (milis): ").append(result.getTime());
	    	message.append("\nResponse size(bytes): ").append(result.getRequestSize());
	    	
	    	AlertDialog ad = new AlertDialog.Builder(Benchmark.this).create();  
	    	ad.setCancelable(true); // This blocks the 'BACK' button  
	    	ad.setMessage(message.toString()); 
	    	ad.setTitle("JSON Result");
	    	 
	    	ad.show();  
	    	
	    	// Where the definition of the function is (simplest of 4 different):
	    	
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    	
			
		}
    };
    
    private View.OnClickListener xmlButtonListener = new View.OnClickListener() {
	    @Override
		public void onClick(View v) {
			
	    
			
		}
    };
    
    
}