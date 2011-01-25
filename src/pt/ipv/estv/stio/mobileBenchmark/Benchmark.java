package pt.ipv.estv.stio.mobileBenchmark;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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
    }
}