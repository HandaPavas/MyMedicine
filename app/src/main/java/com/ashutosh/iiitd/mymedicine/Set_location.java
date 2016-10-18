package com.ashutosh.iiitd.mymedicine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.view.Menu;

public class Set_location extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);

        String []cities = {"Mumbai","Kanpur","New Delhi","Delhi","Gorakhpur","Haridwar","Dehradun","Pune","Bangalore"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,cities);
        AutoCompleteTextView actv = (AutoCompleteTextView)findViewById(R.id.cityfinder);
        actv.setThreshold(1);
        actv.setAdapter(adapter);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }
}
