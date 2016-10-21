package com.ashutosh.iiitd.mymedicine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class EditAlarmActivity extends AppCompatActivity {
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);
        Button save;
        timePicker=(TimePicker)findViewById(R.id.timePicker);
        save=(Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent();
                int hr=timePicker.getCurrentHour();
                int min=timePicker.getCurrentMinute();
                intent.putExtra("Hour",hr);
                intent.putExtra("Minute",min);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}